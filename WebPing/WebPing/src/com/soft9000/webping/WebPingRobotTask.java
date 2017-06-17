/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soft9000.webping;

import com.soft9000.graphics.BarChart;
import com.soft9000.graphics.BarChart.DataPoint;
import java.util.ArrayList;

/**
 *
 * @author profnagy
 */
class WebPingRobotTask extends WebPingHistoryData implements Runnable {

    boolean bFirst = true;
    ArrayList<Double> bag = null;
    private guiWebPinger parent = null;
    private WebPingRobotData previous = new WebPingRobotData();

    public WebPingRobotTask(guiWebPinger parent, int iPoints) {
        super(iPoints);
        bag = new ArrayList<Double>(iPoints);
        for (int ss = 0; ss < points.length; ss++) {
            bag.add(BarChart.NULL_VALUE);
        }
        parent.setLogMessage("WebPingRobotTask initialized.");
        this.parent = parent;
    }

    @Override
    public void run() {
        WebPingTaksInfo info = parent.getServerInfo();
        if (info == null) {
            parent.setLogMessage("Error: WebPingTaksInfo is null.");
            return;
        }
        long lCurrent = com.soft9000.dates.DateX.GetMiliseconds();
        if (bFirst) {
            // Prime it
            bFirst = false;
        } else {
            String str = com.soft9000.file.FileX.Download(WebPingServerOps.EncodePingResults(info, previous));
            if (str == null) {
                parent.setLogMessage("Warnig: Problematic sample upload?");
            }
        }
        // Save next upload data -
        previous.lPingBack = com.soft9000.dates.DateX.GetMiliseconds();
        previous.lPingOut = lCurrent;
        previous.date = com.soft9000.dates.DateX.GetToday();

        while (bag.size() >= points.length) {
            bag.remove(0);
        }
        long lDelta = previous.lPingBack - previous.lPingOut;
        if (lDelta == 0) {
            return; // first one is a primer - ignore it
        }
        bag.add(new Double(lDelta));
        parent.setLogMessage("Response = " + lDelta + "ms, (" + (double) (lDelta / 1000) + " sec.)");
        for (int ss = 0; ss < points.length; ss++) {
            points[ss] = new DataPoint(bag.get(ss));
        }
        parent.setPingResult(this);
    }
}
