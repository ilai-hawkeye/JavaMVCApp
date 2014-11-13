package ro.z2h;

import org.codehaus.jackson.map.ObjectMapper;
import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.controller.DepartmentController;
import ro.z2h.controller.EmployeeController;
import ro.z2h.fmk.AnnotationScanUtils;
import ro.z2h.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;


public class MyDispatcherServlet extends HttpServlet {

    HashMap<String, MethodAttributes>  hashMap = new HashMap<String, MethodAttributes>();
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

                    Method[] methods1 = aClass.getMethods();
                    for (Method method : methods1)
                        if (method.isAnnotationPresent(MyRequestMethod.class)) {
                            MyRequestMethod myRM = (MyRequestMethod) method.getAnnotation(MyRequestMethod.class);
                            System.out.println(myRM.urlPath());
                            MethodAttributes ma = new MethodAttributes();
                            ma.setControllerClass((aClass.getName()));
                            ma.setMethodName(method.getName());
                            ma.setMethodType(myRM.methodType());
                            ma.setParameters(method.getParameterTypes());

                            hashMap.put((mc.urlPath() + myRM.urlPath()),ma);
                        }
                }
            }
            System.out.println(hashMap);
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

//        if(pathInfo.startsWith("/employees")) {
//            EmployeeController ec = new EmployeeController();
//            return ec.getAllEmployees();
//        } else if(pathInfo.startsWith("/department")) {
//            DepartmentController dc = new DepartmentController();
//            return dc.getAllDepartments();
//        }

        String pathInfo = req.getPathInfo();
        req.getParameterMap();


        MethodAttributes methodAttributes = hashMap.get(pathInfo);

        if(methodAttributes != null){
            try {
                Class<?> controllerClass = Class.forName(methodAttributes.getControllerClass());
                Object controllerInstance = controllerClass.newInstance();
                Method method = controllerClass.getMethod(methodAttributes.getMethodName(),methodAttributes.getParameters());
                Object response = method.invoke(controllerInstance);
                System.out.println(response);

                return response;

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /* Used to send the view to the client. */
    private void reply(Object objDispatch, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //TODO
        PrintWriter writer = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(objDispatch);

        writer.printf(s);

    }

    /* Used to send an exception to the client. */
    private void sendException(Exception ex, HttpServletRequest req, HttpServletResponse resp) {
        //TODO
        System.out.println("There was an exception!");
    }
}
