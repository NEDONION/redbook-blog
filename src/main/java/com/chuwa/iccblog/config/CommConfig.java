package com.chuwa.iccblog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommConfig {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
