package com.zzz.framework.starter.core.model;

import javax.validation.groups.Default;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2022/9/1-17:24
 * @desc: 校验分组
 * </pre>
 */
public class BaseValidateGroup {

    /**
     * 新增数据校验分组
     */
    public interface InsertGroup extends Default{
    }


    /**
     * 更新数据校验分组
     */
    public interface UpdateGroup extends Default{
    }
}
