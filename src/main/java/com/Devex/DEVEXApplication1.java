package com.Devex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DEVEXApplication1 {
	/*
	 * @Autowired
	 * private TwilioConfig twilioConfig;
	 * 
	 * @PostConstruct
	 * public void setup() {
	 * Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	 * }
	 */
	public static void main(String[] args) {
		SpringApplication.run(DEVEXApplication1.class, args);
	}

}
