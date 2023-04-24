package org.example.config;

import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import java.nio.charset.StandardCharsets;

public class WebInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {

        //Spring Framework 프로젝트 설정을 위한 클래스의 객체생성
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(WebMvcConfig.class);

        //요청 발생 시 처리를 DispatcherServlet 설정
        DispatcherServlet dispatcherServlet = new DispatcherServlet(webApplicationContext);
        ServletRegistration.Dynamic appServlet = servletContext.addServlet("dispatcherServlet", dispatcherServlet);

        //부가설정
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");

        // 인코딩 설정
        FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        filter.setInitParameter("encoding", StandardCharsets.UTF_8.name());

        //dispatcher에 의해서 편집한 경로 UTF-8로 세팅
        filter.addMappingForServletNames(null, false, "dispatcher");

    }
}
