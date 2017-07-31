package com.dobbydo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{
	@Value("${dobbydo.file.upload.dir}")
	private String DOBBYDO_FILE_UPLOAD_DIR;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	//http://www.baeldung.com/spring-mvc-static-resources
	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	    //registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");//,"classpath:/other-resources/");
	    //registry.addResourceHandler("/static/**").addResourceLocations("/resources/static");
	    //registry.addResourceHandler("/js/**").addResourceLocations("/resources/static/js");
	    //registry.addResourceHandler("/images/**").addResourceLocations("/resources/static/images");
	    registry.addResourceHandler("/files/**").addResourceLocations("file:///"+DOBBYDO_FILE_UPLOAD_DIR + "/").setCachePeriod(0);
	    //registry.addResourceHandler("/other-files/**").addResourceLocations("/resources/static/other-files");
	    
	    
	    //registry.addResourceHandler("/files/**").addResourceLocations("file:/opt/files/");
	}
	
}
