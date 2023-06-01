package com.jlg.springcloud.common.api.symbol;

import com.jlg.springcloud.common.api.Service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(Service.PCK_SYMBOL)
public interface SymbolApi {


    /**
     * saveLog
     * 保存日志
     *
     */
    @PostMapping("/api/getSymbol")
    void getSymbol();
}
