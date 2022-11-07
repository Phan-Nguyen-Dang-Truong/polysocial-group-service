package com.polysocial;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cloudinary.Cloudinary;
import com.polysocial.service.impl.GroupServiceImpl;

@SpringBootApplication
public class GroupApplication {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public GroupServiceImpl groupServiceImpl() {
        return new GroupServiceImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(GroupApplication.class, args);
    }

    @Bean
    public Cloudinary cloudinaryConfig() {
        Cloudinary cloudinary = null;
        Map config = new HashMap();
        config.put("cloud_name", "dwc7dkxy7");
        config.put("api_key", "914855124788275");
        config.put("api_secret", "au9oMdvudygCWWn__i1jRKtvvvs");
        cloudinary = new Cloudinary(config);
        return cloudinary;
    }

}
