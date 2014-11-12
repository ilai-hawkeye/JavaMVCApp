package ro.z2h;

import ro.z2h.annotation.MyController;
import ro.z2h.controller.DepartmentController;
import ro.z2h.controller.EmployeeController;
import ro.z2h.fmk.AnnotationScanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;

/**
 * Created by Dumitru on 11.11.2014.
 */
public class MyDispatcherServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        /* Initialize controller pool. */
        try {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.z2h.controller");
            System.out.println(classes.toString());

            for (Class aClass : classes) {
                if(aClass.isAnnotationPresent(MyController.class)) {
                    MyController mc = (MyController)aClass.getAnnotation(MyController.class);
                    System.out.println(mc.urlPath());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("GET", req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply("POST", req, resp);
    }

    private void dispatchReply(String httpMethod, HttpServletRequest req, HttpServletResponse resp) {
        //TODO
        /*Dispatch*/
        Object objDispatch = dispatch(httpMethod, req, resp);

        /*Reply*/
        try {
            reply(objDispatch, req, resp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Catch errors*/
        Exception ex = null;
        sendException(ex, req, resp);
    }

    /* Where an application controller should be called. */
    private Object dispatch(String httpMethod, HttpServletRequest req, HttpServletResponse resp) {
        //TODO
        /* pentru /test = Hello! */
        /* pentru /employee = allEmployees de la app controller */

        String pathInfo = req.getPathInfo();
        if(pathInfo.startsWith("/employees")) {
            EmployeeController ec = new EmployeeController();
            return ec.getAllEmployees();
        } else if(pathInfo.startsWith("/department")) {
            DepartmentController dc = new DepartmentController();
            return dc.getAllDepartments();
        }

        return "Hello from Z2H!";
    }

    /* Used to send the view to the client. */
    private void reply(Object objDispatch, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //TODO
        PrintWriter writer = resp.getWriter();
        writer.printf(objDispatch.toString());
    }

    /* Used to send an exception to the client. */
    private void sendException(Exception ex, HttpServletRequest req, HttpServletResponse resp) {
        //TODO
        System.out.println("There was an exception!");
    }
}
