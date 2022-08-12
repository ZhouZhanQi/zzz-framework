package com.zzz.framework.starter.core.model;

import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zzz.framework.common.model.code.BaseExceptionCode;
import com.zzz.framework.common.util.JacksonUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/16-13:57
 * @desc: 响应信息
 * </pre>
 */
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"success", "data", "securityData", "errCode", "errMsg", "errDetail"})
public class ResponseData<T> {

    /**
     * 成功标记
     */
    private boolean success;

    @JsonIgnore
    private String securityKey;

    /**
     * 加密保存的字段
     */
    private String securityData;

    /**
     * 错误码
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int errCode;

    /**
     * 错误信息
     */
    private String errMsg;

    /**
     * 错误详细
     */
    private Object errDetail;

    /**
     * 响应的数据
     */
    private T data;


    public ResponseData(boolean success, String securityKey) {
        this.success = success;
        this.securityKey = securityKey;
    }

    public ResponseData(boolean success, int errCode, String errMsg, Object errDetail) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.errDetail = errDetail;
    }


    public static <T> ResponseData<T> success() {
        return success(null);
    }

    /**
     * 构造成功响应信息
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> success(T data) {
        return success(null, data);
    }

    /**
     * 构造成功响应加密信息
     * @param securityKey
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> success(String securityKey, T data) {
        ResponseData<T> responseData = new ResponseData<>(true, securityKey);
        responseData.setData(data);
        return responseData;
    }

    public static <T, R extends BaseExceptionCode> ResponseData<T> fail(R responseCode) {
        return fail(responseCode.getCode(), responseCode.getMessage());
    }

    public static <T> ResponseData<T> fail(String errMsg) {
        return fail(500, errMsg);
    }

    public static <T> ResponseData<T> fail(int errCode, String errMsg) {
        return fail(errCode, errMsg, null);
    }

    /**
     * 构造失败响应对象
     * @param errCode 错误编码
     * @param errMsg 错误消息
     * @param errDetail 错误详情
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> fail(int errCode, String errMsg, Object errDetail) {
        return new ResponseData<>(false, errCode, errMsg, errDetail);
    }

    public void setData(T data) {
        //不加密
        if (StringUtils.isBlank(securityKey)) {
            this.data = data;
            return;
        }

        String encryptData;
        //加密
        if (data instanceof String) {
            encryptData = String.valueOf(data);
        } else {
            encryptData = JacksonUtils.pojo2Json(data);
        }
        this.securityData = SecureUtil.aes(securityKey.getBytes(StandardCharsets.UTF_8)).encryptBase64(encryptData, StandardCharsets.UTF_8);
    }
}
