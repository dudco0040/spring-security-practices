package ex01.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ex01.filter.MyFilter;

@Configuration
public class Appconfig {
	@Bean
	public Filter myFilter() {  // id: myFilter
		return new MyFilter();
	}
}
