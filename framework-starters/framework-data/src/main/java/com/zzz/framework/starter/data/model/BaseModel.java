package com.zzz.framework.starter.data.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zzz.framework.starter.core.model.BaseDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author zhouzhanqi
 * @date 2021/7/22 10:32 上午
 * @desc 基础数据模型
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseModel extends BaseDomain {

    private static final long serialVersionUID = 1L;

    /**
     * 创建者Id
     */
    @TableField(value = "creator_id", fill = FieldFill.INSERT)
    private Long creatorId;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String creator;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者Id
     */
    @TableField(value = "updater_id", fill = FieldFill.INSERT_UPDATE)
    private Long updaterId;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updater;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 调用链Id
     */
    @TableField(value = "trace_id", fill = FieldFill.INSERT_UPDATE)
    private String traceId;

    /**
     * 是否删除
     */
    @TableLogic
    @TableField(value = "is_deleted")
    private Boolean isDeleted;
}
