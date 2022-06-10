package com.zzz.framework.common.util;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.zzz.framework.common.exceptions.AssertException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.annotation.CheckForNull;
import java.util.function.Supplier;

import static com.google.common.base.Strings.lenientFormat;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/20-17:11
 * @desc: 断言工具类
 *
 * <a href="https://github.com/google/guava">source of Preconditions</a>
 *
 * </pre>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertUtils {

    /**
     * 断言布尔表达式
     * @param expression 布尔表达式
     * @throws AssertException 断言异常 if {@code expression} is false
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new AssertException();
        }
    }

    /**
     * 断言布尔表达式
     * @param expression 布尔表达式
     * @param errorMessage 断言失败异常信息,将会被转换为字符串 {@link String#valueOf(Object)}
     * @throws AssertException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, @CheckForNull Object errorMessage) {
        if (!expression) {
            throw new AssertException(String.valueOf(errorMessage));
        }
    }

    /**
     * 断言布尔表达式
     * @param expression 布尔表达式
     * @param errorMessageTemplate 异常信息模板  {@code %s}占位符
     * @param errorMessageArgs 异常信息参数
     * @throws AssertException if {@code expression} is false
     */
    public static void checkArgument(
            boolean expression,
            String errorMessageTemplate,
            @CheckForNull @Nullable Object... errorMessageArgs) {
        if (!expression) {
            throw new AssertException(lenientFormat(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * 断言传入参数不能为null
     * @param expression
     * @return
     * @throws Throwable
     */
    @SneakyThrows
    @CanIgnoreReturnValue
    public static <X extends Throwable> void checkArgument(boolean expression, Supplier<? extends X> exceptionSupplier) {
        if (expression) {
            return;
        }
        throw exceptionSupplier.get();
    }

    /**
     * 断言传入对象不能为null
     * @param reference 传入对象
     * @return 不为null的对象
     * @throws AssertException is {@code reference} is null
     */
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@CheckForNull T reference) {
        if (reference == null) {
            throw new AssertException();
        }
        return reference;
    }

    /**
     * 断言传入对象不能为null
     * @param reference 传入对象
     * @param errorMessage 错误消息
     * @return 不为null的对象
     * @throws AssertException is {@code reference} is null
     */
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(@CheckForNull T reference, @CheckForNull Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * 断言传入对象不能为null
     * @param reference 传入对象
     * @param errorMessageTemplate 异常信息模板  {@code %s}占位符
     * @param errorMessageArgs 异常信息参数
     * @return 不为null的对象
     * @throws AssertException is {@code reference} is null
     */
    @CanIgnoreReturnValue
    public static <T> T checkNotNull(
            @CheckForNull T reference,
            String errorMessageTemplate,
            @CheckForNull @Nullable Object... errorMessageArgs) {
        if (reference == null) {
            throw new AssertException(lenientFormat(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    /**
     * 断言传入参数不能为null
     * @param reference
     * @param exceptionSupplier
     * @param <T>
     * @param <X>
     * @return
     * @throws Throwable
     */
    @SneakyThrows
    @CanIgnoreReturnValue
    public static <T, X extends Throwable> T checkNotNull(@CheckForNull T reference, Supplier<? extends X> exceptionSupplier)  {
        if (reference == null) {
            throw exceptionSupplier.get();
        }
        return reference;
    }
}
