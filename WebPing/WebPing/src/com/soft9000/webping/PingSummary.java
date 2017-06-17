/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soft9000.webping;

import com.soft9000.dates.DateInfo;

/**
 *
 * @author profnagy
 */
public class PingSummary implements java.io.Serializable {

    public DateInfo info = new DateInfo();
    public double dHigh = 0;
    public double dLow = 0;
    public double dAverage = 0;

    public PingSummary() {
    }

    public boolean setWeight(final WebPingHistoryData ref) {
        if (ref == null) {
            return false;
        }
        dLow = Double.MAX_VALUE;
        double dTotal = 0;
        for (int ss = 0; ss < ref.points.length; ss++) {
            if (ref.points[ss].isNull() == true) {
                continue;
            }
            if (ref.points[ss].isError() == true) {
                continue;
            }
            double ddd = ref.points[ss].getValue();
            if (dHigh < ddd) {
                dHigh = ddd;
            }
            if (dLow > ddd) {
                dLow = ddd;
            }
            dTotal += ddd;
        }
        dAverage = dTotal / ref.points.length;
        return true;
    }

    public static PingSummary Get(final WebPingHistoryData ref) {
        PingSummary result = new PingSummary();
        result.setWeight(ref);
        return result;
    }
}
