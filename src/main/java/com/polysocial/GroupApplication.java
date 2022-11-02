package com.polysocial;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.polysocial.service.impl.GroupServiceImpl;

@SpringBootApplication
public class GroupApplication {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public GroupServiceImpl groupServiceImpl() {
    	return new GroupServiceImpl();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(GroupApplication.class, args);
    }

}
