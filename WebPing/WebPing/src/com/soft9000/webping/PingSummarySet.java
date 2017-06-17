/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soft9000.webping;

import java.util.ArrayList;

/**
 * Calculate & manage a "mean of means."
 * 
 * @author profnagy
 */
public class PingSummarySet implements java.io.Serializable {

    public ArrayList<PingSummary> list = null;
    private int iTimes = 0;

    /**
     * Captures the historical. Calculates an inventory based upon the
     * DataPoint size. Will record same to the array list upon every n-th time 
     * of invocation. In order to average it all properly, this function <b>must 
     * be called after each sample.</b>
     * 
     * @param ref The WebPingHistoryData. As provided after each sample.
     * @return True when a summary has been saved, False when not.
     */
    public boolean setHistorical(final WebPingHistoryData ref) {
        if (list == null) {
            list = new ArrayList<PingSummary>(ref.points.length);
            for(int ss = 0; ss < ref.points.length; ss++) {
                list.add(new PingSummary());
            }
        }
        iTimes++;
        if (iTimes == ref.points.length) {
            iTimes = 0;
            PingSummary dat = PingSummary.Get(ref);            
            list.add(0, dat);
            while (list.size() > ref.points.length) {
                list.remove(list.size() - 1);
            }
            return true;
        }
        return false;
    }
    
    public int getCountup() {
        return iTimes;
    }

    public boolean isNull() {
        return (list == null);
    }
}
