package com.algaworks.despacheweb.config.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.algaworks.despacheweb.config.JPAConfig;
import com.algaworks.despacheweb.config.SecurityConfig;
import com.algaworks.despacheweb.config.ServiceConfig;
import com.algaworks.despacheweb.config.WebConfig;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	
	/*
	 * Configuração do DispatcherServlet extendendo de AbstractAnnotationConfigDispatcherServletInitializer
	 */

	@Override
	protected Class<?>[] getRootConfigClasses() {
		/*
		 * Configuração de serviços, e processos antes da WEB.
		 * */
		return new Class<?>[] {JPAConfig.class, ServiceConfig.class, SecurityConfig.class};	
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		/*
		 * Configurar o Spring para achar os controllers, deve-se criar uma classe chamada
		 * WebConfig
		 * */
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		/*
		 * Padrão de URL para encaminhar para esse Servlet, receber as requisições WEB.
		 * */
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		/*Forçar enconding para UTF-8, para resolver problema de acentuação.*/
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();
		return new Filter[] {characterEncodingFilter, httpPutFormContentFilter};
	}
	
	@Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setMultipartConfig(getMultipartConfigElement());
    }
 
    private MultipartConfigElement getMultipartConfigElement() {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement( LOCATION, MAX_FILE_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
        return multipartConfigElement;
    }
    
    private static final String LOCATION = "C:/temp/"; // Temporary location where files will be stored
    
    private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
                                                        // Beyond that size spring will throw exception.
    private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total request size containing Multi part.
     
    private static final int FILE_SIZE_THRESHOLD = 0;
}
