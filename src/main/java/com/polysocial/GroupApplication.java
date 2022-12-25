package com.polysocial;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.cloudinary.Cloudinary;
import com.polysocial.dto.NotificationsDTO;
import com.polysocial.entity.Exercises;
import com.polysocial.entity.Members;
import com.polysocial.entity.Users;
import com.polysocial.repository.ExercisesRepository;
import com.polysocial.repository.GroupRepository;
import com.polysocial.repository.MemberRepository;
import com.polysocial.repository.UserRepository;
import com.polysocial.service.ExercisesService;
import com.polysocial.service.impl.ExercisesServiceImpl;
import com.polysocial.service.impl.GroupServiceImpl;
import com.polysocial.utils.SendMailDeadline;

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
        ConfigurableApplicationContext app = SpringApplication.run(GroupApplication.class, args);
        ExercisesService exercises = app.getBean(ExercisesService.class);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

        Runnable task = new Runnable() {
            public void run() {
                exercises.sendNotiDeadline();
            }
        };
        Runnable taskDeadline = new Runnable() {
            public void run() {
                exercises.checkEndDate();
            }
        };

        // Đặt lịch cho tác vụ chạy mỗi ngày lúc 8h sáng (theo giờ Việt Nam)
        // executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.DAYS);
        // executor.scheduleAtFixedRate(taskDeadline, 0, 1, TimeUnit.MINUTES);
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
