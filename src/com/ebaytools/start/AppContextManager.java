package com.ebaytools.start;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppContextManager {
    private static AppContextManager instance;
    private ApplicationContext ctx;
    private AppContextManager() {
        this.ctx = new ClassPathXmlApplicationContext("app-context.xml");
    }

    public synchronized static AppContextManager getInstance() {
        if (instance == null) {
            instance = new AppContextManager();
        }
        return instance;
    }

    public ApplicationContext getCtx() {
        return ctx;
    }
}
