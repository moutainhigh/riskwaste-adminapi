package com.my.battery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.battery.util.R;

@SpringBootApplication
@MapperScan("com.my.battery.mapper*")
@RestController
public class RiskwasteAdminapiApplication {
    public static void main(final String[] args) {
        SpringApplication.run(RiskwasteAdminapiApplication.class, args);
    }

    @GetMapping("/")
    public R<?> Home() {
        return R.ok();
    }
}
