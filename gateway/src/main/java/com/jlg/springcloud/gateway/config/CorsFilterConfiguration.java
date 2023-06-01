package com.jlg.springcloud.gateway.config;

import com.jlg.springcloud.gateway.filter.GatewayCorsWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;
import java.util.List;

/**
 * chengyangbing special annotation
 *
 * @Package: org.uppeak.pck
 * @FileName: CorsFilterConfiguration.java
 * @ClassName: CorsFilterConfiguration
 * @Description: 网关跨域过滤器配置
 * @Author: chengyangbing
 * @CreateDate: 2019-05-31 11:35
 * @UpdateUser: chengyangbing
 * @UpdateDate: 2019-05-31 11:35
 * @UpdateRemark: 说明本次修改内容
 * @Version: v1.0
 */
@Configuration
public class CorsFilterConfiguration {

    private static final List<String> corsExposedHeaders =
            Arrays.asList(new String[]{"access-control-allow-headers", "access-control-allow-methods", "access-control-allow-origin", "access-control-max-age", "Authorization", "Token", "_Token", "Autograph", "Language", "X-Frame-Options"});

    @Bean
    public GatewayCorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.setExposedHeaders(corsExposedHeaders);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new GatewayCorsWebFilter(source);
    }

}
