package pl.training;

import javax.servlet.ServletContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class WebInitializer implements WebApplicationInitializer{

    @Override
    public void onStartup(ServletContext container) throws javax.servlet.ServletException {
        AnnotationConfigWebApplicationContext applicationContext = 
                new AnnotationConfigWebApplicationContext();
        applicationContext.register(BeansConfig.class);
        
        container.addListener(new ContextLoaderListener(applicationContext));
    }
    
}
