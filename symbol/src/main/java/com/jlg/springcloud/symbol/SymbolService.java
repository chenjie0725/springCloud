package com.jlg.springcloud.symbol;

import com.jlg.springcloud.common.api.symbol.SymbolApi;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SymbolService implements SymbolApi {

    @Override
    public void getSymbol() {
        System.out.println("返回的Symbol:{}");
    }
}
