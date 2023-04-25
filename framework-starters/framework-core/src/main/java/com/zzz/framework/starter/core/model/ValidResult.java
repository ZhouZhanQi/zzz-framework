package com.zzz.framework.starter.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhouzhanqi
 * @date 2021/7/21 11:34 上午
 * @desc valid校验返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidResult implements Serializable {

    @Serial
    private static final long serialVersionUID = 3646800555540682885L;

    /**
     * 是否通过校验
     */
    private boolean success;

    /**
     * 校验参数名(快速失败模式)
     */
    private String paramName;

    /**
     * 未通过校验错误信息
     */
    private String paramErrorMsg;

    /**
     * 错误信息，全部校验
     */
    private List<String> errorMessages;


    public ValidResult(boolean success, String paramName, String paramErrorMsg) {
        this.success = success;
        this.paramName = paramName;
        this.paramErrorMsg = paramErrorMsg;
    }

    public ValidResult(boolean success, List<String> errorMessages) {
        this.success = success;
        this.errorMessages = errorMessages;
    }

    public static ValidResult success() {
        ValidResult result = new ValidResult();
        result.setSuccess(true);
        return result;
    }

    public static ValidResult fail(String paramName, String paramErrorMsg) {
        return new ValidResult(false, paramName, paramErrorMsg);
    }

    public static ValidResult fail(List<String> errorMessages) {
        return new ValidResult(false, errorMessages);
    }

    /**
     * 获取快速失败消息
     */
    public String getFailFastMsg() {
        return String.format("%s:%s", this.paramName, this.paramErrorMsg);
    }
}
