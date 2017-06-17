/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soft9000.webping;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Manage any and all options associated with the server endpoint.
 * 
 * @author profnagy
 */
public class WebPingServerOps {

    /**
     * Return the proper URL encoding for the default server-side implementation.
     * 
     * @param server Task information
     * @return <b>NULL on error</b>
     */
    public static URL Encode(WebPingTaksInfo server) {
        if (server == null || server.isNull()) {
            return null;
        }
        return server.url;
    }

    /**
     * Return the proper URL encoding for the data-saving server-side implementation.
     * @param server Task information
     * @param data The data to save on the server.
     * @return <b>NULL on error</b>
     */
    public static URL EncodePingResults(WebPingTaksInfo server, WebPingRobotData data) {
        if (data == null || server == null || server.isNull()) {
            return null;
        }

        URL url = server.url;
        StringBuilder sb = new StringBuilder();
        sb.append(url.toString());
        sb.append("?ping=");
        sb.append((long)(data.lPingBack - data.lPingOut));
        sb.append("&time=");
        String str = com.soft9000.dates.DateX.Format(data.date, "MM/dd/yyyy HH:mm:ss");
        str = str.replace(" ", "%20");
        sb.append(str);
        sb.append("&");
        try {
            return new URL(sb.toString());
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    public static URL GetDeleteDataRequest(WebPingTaksInfo server) {
        if (server == null || server.isNull()) {
            return null;
        }

        URL url = server.url;
        StringBuilder sb = new StringBuilder();
        sb.append(url.toString());
        sb.append("?control=delete");
        try {
            return new URL(sb.toString());
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    public static URL GetStatRequest(WebPingTaksInfo server) {
        if (server == null || server.isNull()) {
            return null;
        }

        URL url = server.url;
        StringBuilder sb = new StringBuilder();
        sb.append(url.toString());
        sb.append("?control=stat");
        try {
            return new URL(sb.toString());
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    public static boolean IsServerScripted(WebPingTaksInfo server) {
        boolean br = false;
        URL url = GetStatRequest(server);
        String str = com.soft9000.file.FileX.Download(url);
        if (str != null) {
            if (str.indexOf("WebPingStat(") != -1) {
                br = true;
            }
        }
        return br;
    }
}
