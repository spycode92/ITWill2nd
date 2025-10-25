package com.example.spring_test1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class SpringTest1Application { // 현재 스프링부트 애플리케이션 시작점이 되는 클래스

	public static void main(String[] args) {
		SpringApplication.run(SpringTest1Application.class, args);
	}

}
