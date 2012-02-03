package com.ebaytools.util;

import com.ebaytools.start.AppContextManager;
import org.springframework.scheduling.quartz.CronTriggerBean;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Calendar;

public class Clock  {
    protected boolean done = false;
    private DecimalFormat tflz;
    private String lbNextRun;

    public Clock(final JLabel jLabel) {
        tflz = new DecimalFormat("00");
        //CronTriggerBean cronBean = (CronTriggerBean) AppContextManager.getInstance().getCtx().getBean("cronTrigger");
        Calendar nextRun = Calendar.getInstance();
        //nextRun.setTime(cronBean.getNextFireTime());
        StringBuilder sb = new StringBuilder();
        sb.append(" next run ");
        sb.append(nextRun.get(Calendar.HOUR_OF_DAY));
        sb.append(':');
        sb.append(tflz.format(nextRun.get(Calendar.MINUTE)));
        sb.append(':');
        sb.append(tflz.format(nextRun.get(Calendar.SECOND)));
        lbNextRun = sb.toString();

        new Thread(new Runnable() {
            public void run() {
                while (!done) {
                    jLabel.setText(buildTime());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) { /* do nothing */
                    }
                }
            }
        }).start();
    }

    public void stop() {
        done = true;
    }

    private String buildTime() {
        Calendar myCal = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(myCal.get(Calendar.HOUR_OF_DAY));
        sb.append(':');
        sb.append(tflz.format(myCal.get(Calendar.MINUTE)));
        sb.append(':');
        sb.append(tflz.format(myCal.get(Calendar.SECOND)));
        return sb.append(lbNextRun).toString();
    }
}
