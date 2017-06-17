<?php

function HtmlHeader() {
    echo '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
    echo "\n";
    echo '<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">';
    echo "\n";
    echo '<head>';
    echo "\n";

    echo '<title>WebPing - Verion 2.0</title>';
    echo "\n";
    echo '<link rel="stylesheet" href="style.css"/>';
    echo "\n";
    echo '<link rel="shortcut icon" href="webping.ico"/>';
    echo "\n";
    echo '<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />';
    echo "\n";
    echo '</head>';
    echo "\n";
    echo '<body>';
    echo "\n";
}

function webPingTools() {
    echo '<table class="menubar" width="123">';
    echo "<tr><td><center>&nbsp;</center></td></tr>\n";
    echo "<tr><td class='menuwarn'><center><a href='webping.php?control=delete'>[Delete All Samples]</a></center></td></tr>\n";
    echo "<tr><td><center>&nbsp;</center></td></tr>\n";
    echo "<tr><td><center>&nbsp;</center></td></tr>\n";
    echo "<tr><td class='menuitem'><center><a href='webping.php?show=0'>[First Samples]</a></center></td></tr>\n";
    echo "<tr><td class='menuitem'><center><a href='webping.php?control=last'>[Last Samples]</a></center></td></tr>\n";
    echo "<tr><td><center>&nbsp;</center></td></tr>\n";
    echo '</table>';
}

function countLines($file) {
    $tally = 0;
    $fh = fopen($file, 'r');
    if ($fh != false) {
        while (feof($fh) == false) {
            $str = fgets($fh);
            if ($str == false)
                break;
            $tally++;
        }
    }
    fclose($fh);
    return $tally;
}

function doControl($file, $op) {
    if ($op == 'delete') {
        unlink($file);
        echo $file . ' has been deleted.';
        return;
    }
    if ($op == 'last') {
        $start = 0;
        $end = countLines($file);
        if ($end > 100) {
            $start = $end - 100;
        }
        showHistory($file, $start, $end);
        return;
    }
    if ($op == 'stat') {
        $end = countLines($file);
        echo "WebPingStat($file, $end)";
        return;
    }
}

function mkLine($ss, $str) {
    if ($ss == 0) {
        if (strchr($str, ',') == false)
            return ("Sample file created on " . $str);
    }
    list($ver, $ip, $time, $ping) = split(",", $str);
    return "$ss.) $time - Recorded WebPing <b>$ping</b> on <i>$ip</i>";
}

function showHistory($file, $start, $end) {
    $fh = fopen($file, 'r');
    $bHasMore = true;
    if ($fh != false) {
        echo '<table class="aqua">';
        $color = 'item_odd';
        for ($ss = 0; $ss < $end; $ss++) {
            $str = fgets($fh);
            if ($str == false) {
                $color = 'menuwarn';
                echo "<tr><td class=$color><center>(samples end)</center></td></tr>\n";
                $bHasMore = false;
                break;
            }
            if ($ss < $start)
                continue;
            // Normal sample data display
            $str = mkLine($ss, $str);
            echo "<tr><td class=$color>$str</td></tr>\n";
            if ($color == 'item_even')
                $color = 'item_odd';
            else
                $color = 'item_even';
        }
        echo "<tr><td><center><a href='webping.php?show=0'>[Show First]</a></center></td></tr>\n";
        echo "<tr><td><center><a href='webping.php?control=last'>[Show Last]</a></center></td></tr>\n";
        if ($bHasMore == true) {
            echo "<tr><td><center><a href='webping.php?show=$end'>[Show Next]</a></center></td></tr>\n";
        } else {
            echo "<tr><td><center><a href='webping.php?show=$start'>[Refresh]</a></center></td></tr>\n";
        }
        echo "<tr><td><center>Save As Location: <a href='$file'>http://$file</a></center></td></tr>\n";
        echo '<table>';
    }
    fclose($fh);
}

function saveStat($file, $ip, $lastTime, $lastPing) {
    $safe = $ip . $lastTime . $lastPing;
    if(strlen($safe) > 40) {
        echo "Error: Size overflow.";
        return;
    }
    $br = file_put_contents($file, 'v2,' . $ip . ',' . $lastTime . ',' . $lastPing . "\n", FILE_APPEND | LOCK_EX);
    if ($br != true)
        echo "Error: You sent $lastTime for $lastPing, but the data was not saved.";
    else
        echo "Success: You sent $lastTime for $lastPing.";
}

function HtmlFooter() {
    echo "\n";
    echo "\n<a href='https://sourceforge.net/projects/webping/'>WebPing Home</a>";
    echo "\n";
    echo '</body>';
    echo '</html>';
    echo "\n";
}
?>

<?php

HtmlHeader();

// echo "Gotcha: $GLOBALS[cindex]" . '<br>';
// test_case();

echo '<table>';

echo '<tr>';
echo "<td>";
$time = time();
echo '<td>WebPing Server</td><td>[' . $time . ']</td>';
$ip = $_SERVER['REMOTE_ADDR'];
echo "<td></td><td><b>IP Address= $ip</b></td>";
echo "</td>";
echo '</tr>';

echo '<tr valign="top">';
echo '<td valign="top">';
echo webPingTools();
echo '</td>';
echo '<td>';

$file = 'WebPing2.csv';
if (file_exists($file) == false) {
    file_put_contents($file, "$ip\n", LOCK_EX);
    chmod($file, 0666);
}

$control = $_GET['control'];
if ($control) {
    doControl($file, $control);
} else {
    $ping = $_GET['ping'];
    if ($ping) {
        $time = $_GET['time'];
        saveStat($file, $ip, $time, $ping);
    } else {
        $start = 0;
        $end = 100;
        $show = $_GET['show'];
        if ($show) {
            $start = $show;
            $end = $start + 100;
        }
        showHistory($file, $start, $end);
    }
}
echo '</td>';
echo '</tr>';
echo '</table>';

HtmlFooter();
?>
