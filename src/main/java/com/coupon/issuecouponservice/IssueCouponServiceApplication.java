package com.coupon.issuecouponservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
public class IssueCouponServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueCouponServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		Properties props = new Properties();
		try (FileInputStream fis = new FileInputStream("src/test/resources/application-test.properties")) {
			props.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.setProperty("spring.datasource.url", props.getProperty("DB_URL"));
		System.setProperty("spring.datasource.username", props.getProperty("DB_USER_NAME"));
		System.setProperty("spring.datasource.password", props.getProperty("DB_PASSWORD"));
	}

}
