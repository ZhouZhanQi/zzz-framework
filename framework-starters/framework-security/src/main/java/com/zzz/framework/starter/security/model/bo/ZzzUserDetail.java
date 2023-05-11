package com.zzz.framework.starter.security.model.bo;

import com.zzz.framework.starter.core.model.ZzzUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @Project : zzz-framework
 * @Desc : 平台用户详情
 * @Author : Zzz
 * @Datetime : 2023/5/9 16:24
 */
public class ZzzUserDetail extends User {

    public ZzzUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public ZzzUserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }


    private ZzzUser zzzUser;


    public ZzzUser getZzzUser() {
        return zzzUser;
    }

    public void setZzzUser(ZzzUser zzzUser) {
        this.zzzUser = zzzUser;
    }
}
