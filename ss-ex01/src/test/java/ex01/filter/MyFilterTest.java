package ex01.filter;

import javax.servlet.Filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import ex01.config.Appconfig;
import ex01.config.WebConfig;

@ExtendWith(SpringExtension.class) // 스프링 환경에서 테스트 하기 위해 
@ContextConfiguration(classes= {WebConfig.class, Appconfig.class})
@WebAppConfiguration
public class MyFilterTest {
	private MockMvc mvc;
	
	@BeforeEach
	public void setup(WebApplicationContext applicationContext) {
		Filter myFilter = applicationContext.getBean("myFilter", Filter.class);
		mvc = MockMvcBuilders
				.webAppContextSetup(applicationContext)
				.addFilter(new DelegatingFilterProxy("myFilter"), "/*")  // "/*": 모든 경로(path 지정)
				.build();
	}
	
	// 실제 Test 코드 
	@Test
	public void testMyFilter() throws Throwable{
		mvc
			.perform(get("/hello2"))
			.andExpect(status().isOk())
			.andExpect(cookie().value("MyFilter", "Works"))  // 응답의 value가 있는지
			.andDo(print());
	}
}
