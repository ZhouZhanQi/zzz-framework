package com.zzz.framework.common.util;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.zzz.framework.common.exceptions.UtilException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/7/20-19:38
 * @desc: jackson工具类
 * </pre>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JacksonUtils {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 日期序列化为long
        OBJECT_MAPPER.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 反序列化时, 忽略不认识的字段, 而不是抛出异常
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // 日期序列化支持LocalDateTime LocalDate
        JavaTimeModule timeModule = new JavaTimeModule();
        timeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
        timeModule.addSerializer(LocalDate.class,
                new LocalDateSerializer(DatePattern.NORM_DATE_FORMATTER));
        timeModule.addSerializer(LocalTime.class,
                new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));
        timeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(DatePattern.NORM_DATETIME_FORMATTER));
        timeModule.addDeserializer(LocalDate.class,
                new LocalDateDeserializer(DatePattern.NORM_DATE_FORMATTER));
        timeModule.addDeserializer(LocalTime.class,
                new LocalTimeDeserializer(DatePattern.NORM_TIME_FORMATTER));
        OBJECT_MAPPER.registerModule(timeModule);
        OBJECT_MAPPER.registerModule(new Jdk8Module());
    }

    /**
     * 仅读取整个json中的某个属性值,若该属性是一个json对象返回json字符串
     *
     * @param jsonStr
     * @param fieldName
     * @return
     */
    public static String readField(String jsonStr, String fieldName) {
        try {
            JsonNode root = OBJECT_MAPPER.readTree(jsonStr);
            JsonNode node = root.get(fieldName);
            if (Objects.isNull(node))  {
                return null;
            }
            return node.isValueNode() ? node.asText() : OBJECT_MAPPER.writeValueAsString(node);
        } catch (IOException e) {
            throw new UtilException("error reading json field", e);
        }
    }

    /**
     * 将json字符串转换为指定类型的java对象
     *
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static <T> T json2Pojo(String jsonStr, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (IOException e) {
            throw new UtilException("error transform json to pojo", e);
        }
    }

    /**
     * 将json字符串转换为指定类型的java对象, 此方法用于目标类包含泛型的java对象.
     * <br/>
     * Example:<br/>
     * <code>    JacksonUtils.json2pojo("...", new TypeReference&lt;PageResult&lt;User&gt;&gt;(){});</code>
     *
     * @param jsonStr
     * @param typeReference
     * @return
     */
    public static <T> T json2Pojo(String jsonStr, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, typeReference);
        } catch (IOException e) {
            throw new UtilException("error transform json to pojo", e);
        }
    }


    /**
     * 将json字符串转换为HashMap(json里的子对象也将转换为Map)
     *
     * @param jsonStr
     * @return
     */
    public static Map<String, String> json2Map(String jsonStr) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, new TypeReference<HashMap<String, String>>() {});
        } catch (IOException e) {
            throw new UtilException("error transform json to map", e);
        }
    }

    /**
     * 将java对象转换为json字符串
     *
     * @param pojo
     * @return
     */
    public static String pojo2Json(Object pojo) {
        try {
            return OBJECT_MAPPER.writeValueAsString(pojo);
        } catch (IOException e) {
            throw new UtilException("error transform pojo to json", e);
        }
    }

    /**
     * 将java对象转换为map对象
     *
     * @param pojo
     * @return
     */
    public static Map<String, String> pojo2Map(String pojo) {
        return json2Map(pojo2Json(pojo));
    }

    /**
     * 将json字符串转换为List<T>
     *
     * @param jsonStr
     * @param collectionClass
     * @param elementClasses
     * @param <T>
     * @return
     */
    public static <T> T json2List(String jsonStr, Class<T> collectionClass, Class<?>... elementClasses) {
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return OBJECT_MAPPER.readValue(jsonStr, javaType);
        } catch (IOException e) {
            throw new UtilException("error transform to ObjList", e);
        }
    }
}
