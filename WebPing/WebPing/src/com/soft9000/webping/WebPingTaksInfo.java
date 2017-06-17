/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soft9000.webping;

import java.net.URL;

/**
 *
 * @author profnagy
 */
public class WebPingTaksInfo {

    public URL url = null;
    public int seconds = 0;

    public boolean isNull() {
        return (url == null);
    }
}
