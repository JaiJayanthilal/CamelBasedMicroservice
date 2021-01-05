package oracle.banking.ba.obtfpm;

import org.apache.camel.spring.boot.properties.PropertiesComponentAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.WebApplicationInitializer;


@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration(exclude = {JmxAutoConfiguration.class, PropertiesComponentAutoConfiguration.class})
@ImportResource("classpath:CamelContext.xml")
public class Application extends SpringBootServletInitializer implements WebApplicationInitializer {	
    public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		//System.out.println("Hello from Spring!");
	}

}
