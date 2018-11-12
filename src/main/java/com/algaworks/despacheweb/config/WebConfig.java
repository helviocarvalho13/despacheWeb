package com.algaworks.despacheweb.config;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.cache.Caching;

import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.algaworks.despacheweb.controller.InicioController;
import com.algaworks.despacheweb.controller.converter.CidadeConverter;
import com.algaworks.despacheweb.controller.converter.EstadoConverter;
import com.algaworks.despacheweb.controller.converter.GrupoConverter;
import com.algaworks.despacheweb.thymeleaf.processor.DespacheWebDialect;
import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration 
/*Anotação para dizer que esssa classe é de configuração*/

@ComponentScan(basePackageClasses = {InicioController.class}) 
/* Anotação onde digo os controllers do sistema*/

@EnableWebMvc
/* Habilitar o projeto para o desenvolvimento WEB*/
@EnableSpringDataWebSupport
@EnableCaching
@EnableAsync
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware/**/{

	
	/*
	 * Classe para configurar o Spring para identificar os controllers
	 * extende de WebMvcConfigurerAdapter, com alguns métodos já feito para a manipulação
	 */
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Bean
	public ViewResolver viewResolver(){
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		/*Encontrar as paginas html, processar os objetos*/
		thymeleafViewResolver.setTemplateEngine(templateEngine());
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		thymeleafViewResolver.setContentType("text/html; charset=UTF-8");
		/*Padrão de Acentuação*/
		thymeleafViewResolver.setOrder(1);
		return thymeleafViewResolver;
	}	
	
	@Bean
	/*Deixa disponível dentro do contexto do Spring*/
	public TemplateEngine templateEngine(){
		SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
		/*Dependencia do Template Resolver, e consegue processar os arquivos HTML*/
		springTemplateEngine.setEnableSpringELCompiler(true);
		springTemplateEngine.setTemplateResolver(templateResolver());
		springTemplateEngine.addDialect(new LayoutDialect());
		springTemplateEngine.addDialect(new DespacheWebDialect());
		springTemplateEngine.addDialect(new DataAttributeDialect());
		springTemplateEngine.addDialect(new SpringSecurityDialect());
		return springTemplateEngine;
	}
	
 	@Bean(name = "multipartResolver")
    public StandardServletMultipartResolver resolver() {
        return new StandardServletMultipartResolver();
    }
	
	private ITemplateResolver templateResolver(){
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		/*SpringResourceTemplateResolver resolve os templates do Spring*/
		resolver.setApplicationContext(applicationContext);
		/*A documentação do Thymeleaf exige esse set*/
		resolver.setPrefix("classpath:/templates/");
		/*Lugar onde estão os templates*/
		resolver.setSuffix(".html");
		/*Acrescentar tipo dos arquivos Template*/
		resolver.setTemplateMode(TemplateMode.HTML);
		resolver.setCharacterEncoding("UTF-8");
		/*Tipo de Template*/
		return resolver;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
	@Bean
	public FormattingConversionService mvcConversionService(){
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		conversionService.addConverter(new CidadeConverter());
		conversionService.addConverter(new EstadoConverter());
		conversionService.addConverter(new GrupoConverter());
		
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
		dateTimeFormatter.registerFormatters(conversionService);
		
		return conversionService;
	}
	
	@Bean
	public LocaleResolver localeResolver(){
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	@Bean
	public DomainClassConverter<FormattingConversionService> domainClassConverter() {
		return new DomainClassConverter<FormattingConversionService>(mvcConversionService());
	}
	
	@Bean
	public CacheManager cacheManager() throws Exception{
		return new JCacheCacheManager(Caching.getCachingProvider().getCacheManager(getClass().getResource("/cache/ehcache.xml").toURI(), getClass().getClassLoader()));
	}

}
