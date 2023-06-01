package com.jlg.cloud.nacos;

import com.jlg.springcloud.common.api.symbol.SymbolApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
public class TestA {

    @Autowired
    SymbolApi symbolApi;


    @GetMapping("")
    public String resources(){

        return "访问资源A服务成功";
    }

    @GetMapping("/b")
    public String resourcesB(){

        symbolApi.getSymbol();
        return "访问资源B服务成功";
    }

}
