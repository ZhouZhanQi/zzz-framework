package com.zzz.framework.starters.security.authorization;

import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.starter.cache.RedisCacheHelper;
import com.zzz.framework.starters.security.model.bo.ClientDetailBo;
import com.zzz.framework.starters.security.model.code.SecurityExceptionCode;
import com.zzz.framework.starters.security.model.code.SecurityRedisKeyPrefix;
import com.zzz.framework.starters.security.model.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

/**
 * @author zhouzq
 */
@RequiredArgsConstructor
public class ZzzCacheRegisteredClientServiceImpl implements RegisteredClientRepository {

    /**
     * 刷新令牌有效期默认 30 天
     */
    private final static int refreshTokenValiditySeconds = 60 * 60 * 24 * 30;

    /**
     * 请求令牌有效期默认 12 小时
     */
    private final static int accessTokenValiditySeconds = 60 * 60 * 12;

    private final RedisCacheHelper<ClientDetailBo> redisCacheHelper;

    @Override
    public void save(RegisteredClient registeredClient) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        //从缓存中获取客户端信息获取客户端信息
        AssertUtils.checkNotNull(redisCacheHelper.hasKey(SecurityRedisKeyPrefix.OAUTH_CLIENT, clientId), SecurityExceptionCode.CLIENT_INFO_IS_NULL);
        ClientDetailBo clientDetail = redisCacheHelper.get(SecurityRedisKeyPrefix.OAUTH_CLIENT, clientId);

        RegisteredClient.Builder builder = RegisteredClient.withId(clientDetail.getClientId())
                .clientId(clientDetail.getClientId())
                .clientSecret(SecurityConstants.NOOP + clientDetail.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
//        // 授权模式
        Optional.ofNullable(clientDetail.getAuthorizedGrantTypes())
                .ifPresent(grants -> StringUtils.commaDelimitedListToSet(grants)
                        .forEach(s -> builder.authorizationGrantType(new AuthorizationGrantType(s))));
        // 回调地址
        Optional.ofNullable(clientDetail.getRedirectUri()).ifPresent(builder::redirectUri);
        // scope
        Optional.ofNullable(clientDetail.getScope()).ifPresent(builder::scope);
        return builder.tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientDetail.getAccessTokenValidity()).orElse(accessTokenValiditySeconds)))
                        .refreshTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientDetail.getRefreshTokenValidity()).orElse(refreshTokenValiditySeconds)))
                        .build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(!clientDetail.getAutoapprove()).build())
                .build();
    }
}
