package com.cafe.crm.configs;

import com.cafe.crm.configs.filters.CardFilter;
import com.cafe.crm.configs.filters.CompanyConfigurationFilter;
import com.cafe.crm.configs.filters.CompanyConfigurationFilter;
import com.cafe.crm.configs.filters.ShiftOpenFilter;
import com.cafe.crm.configs.property.AdvertisingProperties;
import com.cloudinary.Cloudinary;
import com.google.common.cache.CacheBuilder;
import com.yc.easytransformer.Transformer;
import com.yc.easytransformer.impl.EasyTransformer;
import net.sf.ehcache.config.CacheConfiguration;
import org.hibernate.collection.internal.PersistentBag;
import org.joda.time.DateTime;
import com.yc.easytransformer.Transformer;
import com.yc.easytransformer.impl.EasyTransformer;
import org.hibernate.collection.internal.PersistentBag;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.interceptor.*;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAspectJAutoProxy
@EnableCaching
public class CommonConfig {

	@Bean
	public Cloudinary cloudinary(@Autowired AdvertisingProperties advertisingProperties) {
		HashMap<String, String> config = new HashMap<>();
		config.put("cloud_name", advertisingProperties.getCloud().getName());
		config.put("api_key", advertisingProperties.getCloud().getKey());
		config.put("api_secret", advertisingProperties.getCloud().getSecret());
		return new Cloudinary(config);
	}

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public TemplateResolver springThymeleafTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setOrder(1);
		resolver.setCacheable(false);
		return resolver;
	}

	@Bean
	public TemplateResolver dbTemplateResolver() {
		DbTemplateResolver resolver = new DbTemplateResolver();
		resolver.setOrder(2);
		return resolver;
	}

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

	@Bean
	public SpringTemplateEngine thymeleafTemplateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addDialect(java8TimeDialect());
		engine.addDialect(springSecurityDialect());
		HashSet<TemplateResolver> templateResolvers = new HashSet<>();
		templateResolvers.add(springThymeleafTemplateResolver());
		templateResolvers.add(dbTemplateResolver());
		engine.setTemplateResolvers(templateResolvers);
		return engine;
	}

	@Bean
	public FilterRegistrationBean shiftOpenFilterRegistrationBean(@Autowired ShiftOpenFilter shiftOpenFilter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(shiftOpenFilter);
		filterRegistrationBean.setOrder(Integer.MAX_VALUE - 1);
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean cardFilterRegistrationBean(@Autowired CardFilter cardFilter, @Value("${card.enable}") String cardEnable) {
		cardFilter.setEnable(!Boolean.valueOf(cardEnable));
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(cardFilter);
		filterRegistrationBean.setOrder(Integer.MAX_VALUE);
		return filterRegistrationBean;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public CacheManager cacheManager() {
		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
		cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject());
		return cacheManager;
	}

	@Bean
	public KeyGenerator keyGenerator() {
		return new SimpleKeyGenerator();
	}

	@Bean
	public CacheResolver cacheResolver()    {
		return new SimpleCacheResolver(cacheManager());
	}

	@Bean
	public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler();
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		ehCacheManagerFactoryBean.setCacheManagerName("user");
		ehCacheManagerFactoryBean.setShared(true);
		return ehCacheManagerFactoryBean;
	}

	@Bean
	public Transformer getTransformer() {
		Transformer transformer = new EasyTransformer();
		transformer.addInstanceFunction(PersistentBag.class, v -> new ArrayList<>());
		transformer.addInstanceFunction(DateTime.class, DateTime::new);
		return transformer;
	}

	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean() {
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
		methodInvokingFactoryBean.setTargetMethod("setStrategyName");
		methodInvokingFactoryBean.setArguments(new String[]{SecurityContextHolder.MODE_INHERITABLETHREADLOCAL});
		return methodInvokingFactoryBean;
	}

	@Bean
	public FilterRegistrationBean companyConfigurationFilterRegistrationBean(@Autowired CompanyConfigurationFilter companySettingFilter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(companySettingFilter);
		filterRegistrationBean.setOrder(Integer.MAX_VALUE - 2);
		return filterRegistrationBean;
	}
}
