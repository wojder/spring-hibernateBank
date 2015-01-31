package pl.training;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer{

    @Override
    public void onStartup(ServletContext container) throws javax.servlet.ServletException {
        AnnotationConfigWebApplicationContext applicationContext = 
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(BeansConfig.class);
        
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", 
                new DispatcherServlet(applicationContext));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
    }
    
}
