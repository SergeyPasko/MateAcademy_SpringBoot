package lesson24.springboot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lesson24.springboot.filter.ThreadContextFilter;

/**
 * @author spasko
 */
@Configuration
public class FilterConfig {
	@Bean
	public FilterRegistrationBean<ThreadContextFilter> loggingFilter() {
		FilterRegistrationBean<ThreadContextFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(new ThreadContextFilter());
		registrationBean.addUrlPatterns("/*");

		return registrationBean;
	}
}
