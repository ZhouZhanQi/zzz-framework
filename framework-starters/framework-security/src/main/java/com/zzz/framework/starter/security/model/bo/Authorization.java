package com.zzz.framework.starter.security.model.bo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

/**
 * @Project : zzz-framework
 * @Desc : 缓存token信息
 * @Author : Zzz
 * @Datetime : 2023/5/11 14:15
 */
@Data
@NoArgsConstructor
public class Authorization implements Serializable {

    private String id;

    private String registeredClientId;

    private String principalName;

    private String authorizationGrantType;

    private String authorizedScopes;

    private String attributes;

    private String state;

    private String authorizationCodeValue;

    private Instant authorizationCodeIssuedAt;

    private Instant authorizationCodeExpiresAt;

    private String authorizationCodeMetadata;

    private String accessTokenValue;

    private Instant accessTokenIssuedAt;

    private Instant accessTokenExpiresAt;

    private String accessTokenMetadata;

    private String accessTokenType;

    private String accessTokenScopes;

    private String refreshTokenValue;

    private Instant refreshTokenIssuedAt;

    private Instant refreshTokenExpiresAt;

    private String refreshTokenMetadata;

    private String oidcIdTokenValue;

    private Instant oidcIdTokenIssuedAt;

    private Instant oidcIdTokenExpiresAt;

    private String oidcIdTokenMetadata;

    private String oidcIdTokenClaims;
}
