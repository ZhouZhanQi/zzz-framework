package com.zzz.framework.starters.security.authorization;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.starters.security.model.code.SecurityExceptionCode;
import com.zzz.framework.starters.security.model.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.core.OAuth2TokenType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.util.CollectionUtils;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouzq
 */
@RequiredArgsConstructor
public class ZzzCacheOauth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

    private final static Long TIMEOUT = 10L;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(OAuth2Authorization authorization) {
        AssertUtils.checkNotNull(authorization, SecurityExceptionCode.OAUTH2_AUTHORIZATION_INFO_IS_NULL);
        Optional.ofNullable((String) authorization.getAttribute("state")).ifPresent(token -> {
            redisTemplate.opsForValue().set(buildCacheKey(OAuth2ParameterNames.STATE, token), authorization, TIMEOUT,
                    TimeUnit.MINUTES);
        });

        Optional.ofNullable(authorization.getToken(OAuth2AuthorizationCode.class)).ifPresent(authorizationCode -> {
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            long between = ChronoUnit.MINUTES.between(authorizationCodeToken.getIssuedAt(),
                    authorizationCodeToken.getExpiresAt());
            redisTemplate.opsForValue().set(buildCacheKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()),
                    authorization, between, TimeUnit.MINUTES);
        });

        //刷新token缓存
        Optional.ofNullable(authorization.getRefreshToken()).ifPresent(token -> {
            long between = ChronoUnit.SECONDS.between(token.getToken().getIssuedAt(), token.getToken().getExpiresAt());
            redisTemplate.opsForValue().set(buildCacheKey(OAuth2ParameterNames.REFRESH_TOKEN, token.getToken().getTokenValue()),
                    authorization, between, TimeUnit.SECONDS);
        });

        //令牌
        Optional.ofNullable(authorization.getAccessToken()).ifPresent(token -> {
            long between = ChronoUnit.SECONDS.between(token.getToken().getIssuedAt(), token.getToken().getExpiresAt());
            redisTemplate.opsForValue().set(buildCacheKey(OAuth2ParameterNames.ACCESS_TOKEN, token.getToken().getTokenValue()),
                    authorization, between, TimeUnit.SECONDS);
        });
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        AssertUtils.checkNotNull(authorization, SecurityExceptionCode.OAUTH2_AUTHORIZATION_INFO_IS_NULL);
        List<String> keys = Lists.newArrayList();
        Optional.ofNullable((String) authorization.getAttribute("state")).ifPresent(token -> {
            keys.add(buildCacheKey(OAuth2ParameterNames.STATE, token));
        });

        Optional.ofNullable(authorization.getToken(OAuth2AuthorizationCode.class)).map(authorizationCode -> authorizationCode.getToken()).ifPresent(token -> {
            keys.add(buildCacheKey(OAuth2ParameterNames.STATE, token.getTokenValue()));
        });

        Optional.ofNullable(authorization.getRefreshToken()).ifPresent(token -> {
            keys.add(buildCacheKey(OAuth2ParameterNames.REFRESH_TOKEN, token.getToken().getTokenValue()));
        });

        Optional.ofNullable(authorization.getAccessToken()).ifPresent(token -> {
            keys.add(buildCacheKey(OAuth2ParameterNames.ACCESS_TOKEN, token.getToken().getTokenValue()));
        });

        if (!CollectionUtils.isEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public OAuth2Authorization findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        AssertUtils.checkArgument(StrUtil.isNotBlank(token), SecurityExceptionCode.TOKEN_INFO_IS_EMPTY);
        AssertUtils.checkNotNull(tokenType, SecurityExceptionCode.TOKEN_TYPE_IS_NULL);
        return (OAuth2Authorization) redisTemplate.opsForValue().get(buildCacheKey(tokenType.getValue(), token));
    }

    private String buildCacheKey(String type, String id) {
        return String.format("%s::%s::%s", SecurityConstants.AUTHORIZATION, type, id);
    }
}
