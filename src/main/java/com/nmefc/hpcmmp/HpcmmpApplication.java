package com.nmefc.hpcmmp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nmefc.hpcmmp.dao")
public class HpcmmpApplication {

	public static void main(String[] args) {
		SpringApplication.run(HpcmmpApplication.class, args);
	}


}

