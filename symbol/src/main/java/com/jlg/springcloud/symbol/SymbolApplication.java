package com.jlg.springcloud.symbol;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients
public class SymbolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SymbolApplication.class, args);
    }

}
