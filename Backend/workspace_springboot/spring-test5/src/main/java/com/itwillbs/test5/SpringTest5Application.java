package com.itwillbs.test5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import jakarta.persistence.EntityListeners;

@SpringBootApplication
@EnableJpaAuditing // @EntityListeners(AuditingEntityListener.class) 어노테이션을 통한 감사 기능 활성화
public class SpringTest5Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringTest5Application.class, args);
	}

}
