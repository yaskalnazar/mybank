package ua.yaskal.controller.listener;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.service.ScheduledService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ContextListener implements ServletContextListener {
    private ScheduledExecutorService executorService;
    private ScheduledService scheduledService;
    private final static Logger logger = Logger.getLogger(SessionListener.class);


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("----------------------------------------------------------");
        logger.info("Starting project");
        logger.info("----------------------------------------------------------");
        executorService = Executors.newScheduledThreadPool(5);
        scheduledService = new ScheduledService(executorService, DAOFactory.getInstance());

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        executorService.shutdownNow();
    }
}
