package com.zzz.framework.starter.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/21-17:18
 * @desc: 登录用户信息
 * </pre>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ZzzUser implements Serializable {

    private static final long serialVersionUID = -5617041399002249883L;

    /**
     * 租户Id
     */
    private Long tenantId;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 部门Id
     */
    private Long deptId;

    /**
     * 职位Id
     */
    private Long postId;

    /**
     * 角色列表
     */
    private List<Long> roleIdList;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;
}
