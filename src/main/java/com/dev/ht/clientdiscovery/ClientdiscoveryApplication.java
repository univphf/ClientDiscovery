package com.dev.ht.clientdiscovery;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class ClientdiscoveryApplication {
    
    @Autowired DiscoveryClient discoveryClient;	
    RestTemplate restTemplate=new RestTemplate();
 
    @RequestMapping("/infosDiscoveryClient")
    String infos() throws Exception{
    	final StringBuilder sb=new StringBuilder();
    	final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT,Locale.FRANCE);

    	discoveryClient.getInstances("service-client-eureka")
    			.forEach( 
    			  si ->
    			   {                  
                             sb.append(si.getHost()); 
                             sb.append(" uri="); 
                             sb.append(si.getUri());
                             sb.append(si.getPort());
    			   }
    			);
		return  "<h2>Infos (à "+ df.format(new Date()) + ") : "+sb.toString()+"</h2>\n";
	}
     
    @RequestMapping({"/info","/"}) 
    String restInfos() throws Exception{
         final String retour = restTemplate.getForObject("http://service-client-eureka/infosDiscoveryClient",String.class);
         return retour;
   }
    
    	public static void main(String[] args) {
		SpringApplication.run(ClientdiscoveryApplication.class, args);
	}  
        
@Bean
public RestTemplate restTemplate() {
    return new RestTemplate();
}
}
