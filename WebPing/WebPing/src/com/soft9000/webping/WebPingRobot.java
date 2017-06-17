/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soft9000.webping;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The will manage a single session. 
 * Object is invalid after a stop() - will need to re-create after same.
 *
 * @author profnagy
 */
class WebPingRobot {

    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public WebPingRobot() {
    }

    public boolean start(guiWebPinger parent, long lSeconds, int iPoints) {
        try {
            WebPingRobotTask task = new WebPingRobotTask(parent, iPoints);
            scheduler.scheduleAtFixedRate(task, 2L, lSeconds, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            parent.setLogMessage(e.getMessage());
            return false;
        }
    }

    public void stop() {
        scheduler.shutdownNow();
    }
}

