package sohagmedia.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ১. ব্রাউজারে /uploads/** লিখে রিকোয়েস্ট পাঠালে...
        // ২. স্প্রিং বুট প্রজেক্টের রুট ডিরেক্টরির 'uploads' ফোল্ডার থেকে ফাইলটি এনে দেবে।
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:upload/");
    }
}