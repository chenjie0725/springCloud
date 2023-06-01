package com.jlg.springcloud.gateway.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @Package: org.uppeak.pck.filter
 * @FileName: GatewayHiddenHttpMethodFilter.java
 * @ClassName: GatewayHiddenHttpMethodFilter
 * @Description: 重写HiddenHttpMethodFilter过滤器
 * @Author: yeshengguang
 * @CreateDate: 2019/8/13 17:29
 * @UpdateUser: yeshengguang
 * @UpdateDate: 2019/8/13 17:29
 * @UpdateRemark: 说明本次修改内容
 * @Version: v1.0
 */
@Configuration
public class GatewayHiddenHttpMethodFilter {

    /**
     * 解决Springboot2.0.5版本问题
     * 问题异常： Only one connection receive subscriber allowed.
     * 问题原因： spring-cloud-gateway反向代理的原理是，首先读取原请求的数据，然后构造一个新的请求，将原请求的数据封装到新
     * 的请求中，然后再转发出去。然而在它封装之前读取了一次request body，而request body只能读取一次
     * HiddenHttpMethodFilter是spring boot在2.0.5版本
     * 解决思路： 覆盖hiddenHttpMethodFilter方法实现，对requestBody数据不读取
     */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                return chain.filter(exchange);
            }
        };
    }

}
