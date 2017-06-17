/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soft9000.webping;

import java.util.Date;

/**
 *
 * @author profnagy
 */
public class WebPingRobotData {

    public long lPingOut = 0L;
    public long lPingBack = 0L;
    public Date date = com.soft9000.dates.DateX.GetToday();
    private boolean bBadSample = true;

    /**
     * Test for the null condition. 
     * (i.s. If either value is zero, then the object is worthless.)
     * 
     * @return  True if either value is zero. Returns false if the values are usable.
     */
    public boolean isNull() {
        if (lPingOut == 0L) {
            return true;            
        }
        if (lPingOut == 0L) {
            return true;
        }
        return false;
    }

    public void setBad(boolean b) {
        bBadSample = b;
    }
    
    public boolean isSampleBad() {
        return bBadSample;
    }
}
