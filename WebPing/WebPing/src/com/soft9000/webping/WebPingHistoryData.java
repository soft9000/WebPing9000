/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soft9000.webping;

import com.soft9000.graphics.BarChart.DataPoint;

/**
 *
 * @author profnagy
 */
public class WebPingHistoryData extends WebPingRobotData {


    public DataPoint[] points = null;

    public WebPingHistoryData(int iSampleSize) {
        points = new DataPoint[iSampleSize];
        clear();
    }

    public void clear() {
        for (int ss = 0; ss < points.length; ss++) {
            points[ss] = new DataPoint();
        }
    }
}
