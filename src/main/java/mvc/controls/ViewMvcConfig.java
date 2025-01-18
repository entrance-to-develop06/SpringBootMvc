package mvc.controls;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/**").setViewName("login");
        registry.addViewController("/mvc/login").setViewName("login");
        registry.addViewController("/mvc/add").setViewName("add");
        registry.addViewController("/mvc/edit").setViewName("edit");
    }

}