package net.namlongadv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpEncodingProperties;
import org.springframework.boot.web.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;

@SpringBootApplication
public class NamlongadvApplication {
	public static void main(String[] args) {
		SpringApplication.run(NamlongadvApplication.class, args);
	}
	
	@Autowired
	private HttpEncodingProperties httpEncodingProperties;

	@Bean
	public OrderedCharacterEncodingFilter characterEncodingFilter() {
	    OrderedCharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
	    filter.setEncoding(this.httpEncodingProperties.getCharset().name());
	    filter.setForceEncoding(this.httpEncodingProperties.isForce());
	    filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    return filter;
	}
}
