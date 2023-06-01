package com.jlg.springcloud.gateway.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsProcessor;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.cors.reactive.DefaultCorsProcessor;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * chengyangbing special annotation
 *
 * @Package: org.uppeak.pck.filter
 * @FileName: GatewayCorsWebFilter.java
 * @ClassName: GatewayCorsWebFilter
 * @Description: 网关跨域过滤器
 * @Author: chengyangbing
 * @CreateDate: 2019-05-31 13:00
 * @UpdateUser: chengyangbing
 * @UpdateDate: 2019-05-31 13:00
 * @UpdateRemark: 说明本次修改内容
 * @Version: v1.0
 */
public class GatewayCorsWebFilter implements WebFilter {

    private final CorsConfigurationSource configSource;

    private final CorsProcessor processor;

    public GatewayCorsWebFilter(CorsConfigurationSource configSource) {
        this(configSource, new DefaultCorsProcessor());
    }

    public GatewayCorsWebFilter(CorsConfigurationSource configSource, CorsProcessor processor) {
        Assert.notNull(configSource, "CorsConfigurationSource must not be null");
        Assert.notNull(processor, "CorsProcessor must not be null");
        this.configSource = configSource;
        this.processor = processor;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (CorsUtils.isCorsRequest(request)) {
            CorsConfiguration corsConfiguration = this.configSource.getCorsConfiguration(exchange);
            if (corsConfiguration != null) {
                boolean isValid = this.processor.process(corsConfiguration, exchange);
                if (!isValid || isPreFlightRequest(request)) {
                    return Mono.empty();
                }
            }
        }
        return chain.filter(exchange);
    }

    public static boolean isPreFlightRequest(ServerHttpRequest request) {
        return request.getMethod() == HttpMethod.OPTIONS && CorsUtils.isCorsRequest(request);
    }
}
