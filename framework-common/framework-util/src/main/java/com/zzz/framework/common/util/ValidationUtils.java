package com.zzz.framework.common.util;

import com.zzz.framework.common.model.ValidResult;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.validator.HibernateValidator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/20-21:44
 * @desc: 校验工具类
 * </pre>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationUtils {

    //快速结束模式
    private static Validator failFastValidator = Validation.byProvider(HibernateValidator.class)
            .configure()
            .failFast(true)
            .buildValidatorFactory().getValidator();

    //全部校验模式
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 注解验证参数(快速失败模式)
     *
     * @param obj
     */
    public static <T> ValidResult fastFailValidate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = failFastValidator.validate(obj);
        //返回异常result
        if (constraintViolations.size() > 0) {
            return ValidResult.fail(constraintViolations.iterator().next().getPropertyPath().toString(), constraintViolations.iterator().next().getMessage());
        }
        return ValidResult.success();
    }

    /**
     * 注解验证参数(全部校验)
     *
     * @param obj
     */
    public static <T> ValidResult allCheckValidate(T obj) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        //返回异常result
        if (constraintViolations.size() > 0) {
            List<String> errorMessages = new LinkedList<String>();
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> violation = iterator.next();
                errorMessages.add(String.format("%s:%s", violation.getPropertyPath().toString(), violation.getMessage()));
            }
            return ValidResult.fail(errorMessages);
        }
        return ValidResult.success();
    }
}
