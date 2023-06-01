package com.jlg.springcloud.gateway.handler;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * chengyangbing special annotation
 *
 * @Package: org.uppeak.pck.handler
 * @FileName: GatewayErrorWebExceptionHandler.java
 * @ClassName: GatewayErrorWebExceptionHandler
 * @Description: 网关统一异常处理
 * @Author: chengyangbing
 * @CreateDate: 2019-07-09 12:46
 * @UpdateUser: chengyangbing
 * @UpdateDate: 2019-07-09 12:46
 * @UpdateRemark: 说明本次修改内容
 * @Version: v1.0
 */
@Slf4j
public class GatewayErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {


    public GatewayErrorWebExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties
            , ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable error = super.getError(request);
        log.error("Gateway Failed to handle request [{}], Cause :", error.getMessage(), error);
        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            return buildResponse(HttpStatus.NOT_FOUND.value()
                    , ErrorMessage.getErrorMessage(HttpStatus.NOT_FOUND.value())
                    , error.getMessage());
        } else {
            return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()
                    , ErrorMessage.getErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    , error.getMessage());
        }
    }

    @Override
    protected HttpStatus getHttpStatus(Map<String, Object> errorAttributes) {
        Integer statusCode = (Integer) errorAttributes.get("code");
        return HttpStatus.valueOf(statusCode);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private static final String GATEWAY_ERROR_RESULT = "Gateway error , cause : {%s}";

    private static Map<String, Object> buildResponse(Integer code, String message, String result) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("code",code);
        retMap.put("message",message);
        retMap.put("result", String.format(GATEWAY_ERROR_RESULT, result));

        return retMap;
    }

    /**
     * chengyangbing special annotation
     *
     * @Package: org.uppeak.pck.handler
     * @FileName: GatewayErrorWebExceptionHandler.java
     * @ClassName: GatewayErrorWebExceptionHandler
     * @Description: 网关错误消息
     * @Author: chengyangbing
     * @CreateDate: 2019-07-09 12:46
     * @UpdateUser: chengyangbing
     * @UpdateDate: 2019-07-09 12:46
     * @UpdateRemark: 说明本次修改内容
     * @Version: v1.0
     */
    private enum ErrorMessage {
        /**
         * 404 服务不可用错误
         */
        RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND.value(), "No available service found !"),
        /**
         * 500 网关内部服务错误
         */
        SERVICE_NOT_AVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Gateway internal service error !");

        @Getter
        @Setter
        private Integer key;

        @Getter
        @Setter
        private String value;

        ErrorMessage(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getErrorMessage(Integer code) {
            AtomicReference<ErrorMessage> result = new AtomicReference<>();
            Arrays.stream(ErrorMessage.values()).forEach(item -> {
                if (item.getKey().equals(code)) {
                    result.set(item);
                }
            });
            return result.get().getValue();
        }
    }


}
