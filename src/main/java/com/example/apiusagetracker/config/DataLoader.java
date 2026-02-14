//package com.example.apiusagetracker.config;
//
//import com.example.apiusagetracker.entity.Api;
//import com.example.apiusagetracker.entity.ApiUsage;
//import com.example.apiusagetracker.entity.User;
//import com.example.apiusagetracker.repository.ApiRepository;
//import com.example.apiusagetracker.repository.ApiUsageRepository;
//import com.example.apiusagetracker.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDateTime;
//
//@Configuration
//public class DataLoader {
//
//    @Bean
//    CommandLineRunner loadData(
//            UserRepository userRepository,
//            ApiRepository apiRepository,
//            ApiUsageRepository apiUsageRepository
//    ) {
//        return args -> {
//
//            //User
//            User user = new User();
//            user.setName("Gayathri");
//            user.setEmail("gayathri@test.com");
//            userRepository.save(user);
//
//            //Api
//            Api api = new Api();
//            api.setName("CREATE_USER");
//            api.setEndpoint("/users");
//            apiRepository.save(api);
//
//            //ApiUsage
//            ApiUsage usage = new ApiUsage();
//            usage.setUser(user);
//            usage.setApi(api);
//            usage.setCalledAt(LocalDateTime.now());
//            usage.setCount(1);
//            apiUsageRepository.save(usage);
//        };
//    }
//}
