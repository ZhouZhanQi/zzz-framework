package com.zzz.framework.starter.security.authorization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.starter.cache.RedisCacheHelper;
import com.zzz.framework.starter.security.model.code.SecurityRedisKeyPrefix;
import com.zzz.framework.starter.security.model.bo.ClientDetailBo;
import com.zzz.framework.starter.security.model.code.SecurityExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
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
    private final static int REFRESH_TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * 30;

    /**
     * 请求令牌有效期默认 12 小时
     */
    private final static int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 12;

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
                .clientSecret(clientDetail.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
//        // 授权模式
        Optional.ofNullable(clientDetail.getAuthorizedGrantTypes())
                .ifPresent(grants -> StringUtils.commaDelimitedListToSet(grants)
                        .forEach(grantType -> builder.authorizationGrantType(new AuthorizationGrantType(grantType))));
        // 回调地址
        Optional.ofNullable(clientDetail.getRedirectUri()).ifPresent(builder::redirectUri);
        // scope
        Optional.ofNullable(clientDetail.getScope()).ifPresent(builder::scope);
        return builder.tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                        .accessTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientDetail.getAccessTokenValidity()).orElse(ACCESS_TOKEN_VALIDITY_SECONDS)))
                        .refreshTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(clientDetail.getRefreshTokenValidity()).orElse(REFRESH_TOKEN_VALIDITY_SECONDS)))
                        .build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(!clientDetail.getAutoapprove()).build())
                .build();
    }
}
