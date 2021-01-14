package servlets;

import sdm.engine.CutomSDMClasses.EngineManager;

import javax.servlet.ServletContext;

public class UtilsServlet {

    private static final String ENGINE_MANAGER_ATTRIBUTE_NAME = "engineManager";
//    private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";

    /*
    Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
    the actual fetch of them is remained un-synchronized for performance POV
     */
    private static final Object engineManagerLock = new Object();
//    private static final Object chatManagerLock = new Object();

    public static EngineManager getEngineManager(ServletContext servletContext) {

        synchronized (engineManagerLock) {
            if (servletContext.getAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME, new EngineManager());
            }
        }
        return (EngineManager) servletContext.getAttribute(ENGINE_MANAGER_ATTRIBUTE_NAME);
    }
}
