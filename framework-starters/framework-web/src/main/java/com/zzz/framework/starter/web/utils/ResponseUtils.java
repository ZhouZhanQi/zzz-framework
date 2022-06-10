package com.zzz.framework.starter.web.utils;

import com.zzz.framework.common.util.JacksonUtils;
import com.zzz.framework.starter.core.model.ResponseData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-11:10
 * @desc: 响应消息工具类
 * </pre>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

    /**
     * 设置响应信息
     * @param response HttpServletResponse
     * @param contentType content-type
     * @param status http状态码
     * @param data 响应内容
     * @throws IOException
     */
    public static void responseWriter(HttpServletResponse response, String contentType,
                                      int status, Object data) throws IOException {
        response.setContentType(contentType);
        response.setStatus(status);
        response.getOutputStream().write(JacksonUtils.pojo2Json(data).getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 设置webflux模型响应
     * @param response ServerHttpResponse
     * @param contentType content-type
     * @param status http状态码
     * @param data 响应内容
     * @return
     */
    public static Mono<Void> webFluxResponseWriter(ServerHttpResponse response, String contentType,
                                                   HttpStatus status, Object data) {
        response.setStatusCode(status);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, contentType);

        DataBuffer dataBuffer = response.bufferFactory().wrap(JacksonUtils.pojo2Json(ResponseData.fail(status.value(), data.toString()))
                .getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(dataBuffer));
    }
}
