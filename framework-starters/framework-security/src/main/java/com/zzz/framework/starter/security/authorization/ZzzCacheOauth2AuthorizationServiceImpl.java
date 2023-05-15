package com.zzz.framework.starter.security.authorization;

import org.dromara.hutool.core.text.StrUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.common.util.JacksonUtils;
import com.zzz.framework.starter.cache.RedisCacheHelper;
import com.zzz.framework.starter.security.model.bo.Authorization;
import com.zzz.framework.starter.security.model.code.SecurityExceptionCode;
import com.zzz.framework.starter.security.model.code.SecurityRedisKeyPrefix;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author zhouzq
 */
@RequiredArgsConstructor
public class ZzzCacheOauth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

    private final static Long TIMEOUT = 10L;

    private final RegisteredClientRepository registeredClientRepository;

    private final RedisCacheHelper<Authorization> redisCacheHelper;

    @Override
    public void save(OAuth2Authorization oauth2Authorization) {
        AssertUtils.checkNotNull(oauth2Authorization, SecurityExceptionCode.OAUTH2_AUTHORIZATION_INFO_IS_NULL);
        Authorization authorization = convert2Authorization(oauth2Authorization);
        Optional.ofNullable((String) oauth2Authorization.getAttribute("state")).ifPresent(token -> {
            redisCacheHelper.set(SecurityRedisKeyPrefix.TOKEN_STATE, token, authorization, TIMEOUT, TimeUnit.MINUTES);
        });

        Optional.ofNullable(oauth2Authorization.getToken(OAuth2AuthorizationCode.class)).ifPresent(authorizationCode -> {
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            long between = ChronoUnit.MINUTES.between(Objects.requireNonNull(authorizationCodeToken.getIssuedAt()),
                    authorizationCodeToken.getExpiresAt());
            redisCacheHelper.set(SecurityRedisKeyPrefix.TOKEN_CODE, authorizationCodeToken.getTokenValue(),
                    authorization, between, TimeUnit.MINUTES);
        });

        //刷新token缓存
        Optional.ofNullable(oauth2Authorization.getRefreshToken()).ifPresent(token -> {
            long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(token.getToken().getIssuedAt()), token.getToken().getExpiresAt());
            redisCacheHelper.set(SecurityRedisKeyPrefix.TOKEN_REFRESH_TOKEN, token.getToken().getTokenValue(),
                    authorization, between, TimeUnit.SECONDS);
        });

        //令牌
        Optional.ofNullable(oauth2Authorization.getAccessToken()).ifPresent(token -> {
            long between = ChronoUnit.SECONDS.between(Objects.requireNonNull(token.getToken().getIssuedAt()), token.getToken().getExpiresAt());
            redisCacheHelper.set(SecurityRedisKeyPrefix.TOKEN_ACCESS_TOKEN, token.getToken().getTokenValue(),
                    authorization, between, TimeUnit.SECONDS);
        });
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        AssertUtils.checkNotNull(authorization, SecurityExceptionCode.OAUTH2_AUTHORIZATION_INFO_IS_NULL);
        Optional.ofNullable((String) authorization.getAttribute("state")).ifPresent(token -> {
            redisCacheHelper.delete(SecurityRedisKeyPrefix.TOKEN_STATE, token);
        });

        Optional.ofNullable(authorization.getToken(OAuth2AuthorizationCode.class)).map(OAuth2Authorization.Token::getToken).ifPresent(token -> {
            redisCacheHelper.delete(SecurityRedisKeyPrefix.TOKEN_CODE, token.getTokenValue());
        });

        Optional.ofNullable(authorization.getRefreshToken()).ifPresent(token -> {
            redisCacheHelper.delete(SecurityRedisKeyPrefix.TOKEN_REFRESH_TOKEN, token.getToken().getTokenValue());
        });

        Optional.ofNullable(authorization.getAccessToken()).ifPresent(token -> {
            redisCacheHelper.delete(SecurityRedisKeyPrefix.TOKEN_ACCESS_TOKEN, token.getToken().getTokenValue());
        });
    }

    @Override
    public OAuth2Authorization findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        AssertUtils.checkArgument(StrUtil.isNotBlank(token), SecurityExceptionCode.TOKEN_INFO_IS_EMPTY);
        AssertUtils.checkNotNull(tokenType, SecurityExceptionCode.TOKEN_TYPE_IS_NULL);

        if (OAuth2TokenType.ACCESS_TOKEN.getValue().equals(tokenType.getValue())) {
            return convert2Oauth2Authorization(redisCacheHelper.get(SecurityRedisKeyPrefix.TOKEN_ACCESS_TOKEN, token));
        }

        if (OAuth2TokenType.REFRESH_TOKEN.getValue().equals(tokenType.getValue())) {
            return convert2Oauth2Authorization(redisCacheHelper.get(SecurityRedisKeyPrefix.TOKEN_REFRESH_TOKEN, token));
        }
        return null;
    }


    /**
     * oauth token信息转换
     * @param oauth2Authorization
     * @return
     */
    private Authorization convert2Authorization(OAuth2Authorization oauth2Authorization) {
        Authorization entity = new Authorization();
        entity.setId(oauth2Authorization.getId());
        entity.setRegisteredClientId(oauth2Authorization.getRegisteredClientId());
        entity.setPrincipalName(oauth2Authorization.getPrincipalName());
        entity.setAuthorizationGrantType(oauth2Authorization.getAuthorizationGrantType().getValue());
        entity.setAuthorizedScopes(Joiner.on(',').join(oauth2Authorization.getAuthorizedScopes()));
        entity.setAttributes(JacksonUtils.pojo2Json(oauth2Authorization.getAttributes()));
        entity.setState(oauth2Authorization.getAttribute(OAuth2ParameterNames.STATE));

        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode =
                oauth2Authorization.getToken(OAuth2AuthorizationCode.class);
        setTokenValues(
                authorizationCode,
                entity::setAuthorizationCodeValue,
                entity::setAuthorizationCodeIssuedAt,
                entity::setAuthorizationCodeExpiresAt,
                entity::setAuthorizationCodeMetadata
        );

        OAuth2Authorization.Token<OAuth2AccessToken> accessToken =
                oauth2Authorization.getToken(OAuth2AccessToken.class);
        setTokenValues(
                accessToken,
                entity::setAccessTokenValue,
                entity::setAccessTokenIssuedAt,
                entity::setAccessTokenExpiresAt,
                entity::setAccessTokenMetadata
        );
        if (accessToken != null && accessToken.getToken().getScopes() != null) {
            entity.setAccessTokenScopes(Joiner.on(',').join(accessToken.getToken().getScopes()));
        }

        OAuth2Authorization.Token<OAuth2RefreshToken> refreshToken =
                oauth2Authorization.getToken(OAuth2RefreshToken.class);
        setTokenValues(
                refreshToken,
                entity::setRefreshTokenValue,
                entity::setRefreshTokenIssuedAt,
                entity::setRefreshTokenExpiresAt,
                entity::setRefreshTokenMetadata
        );

        OAuth2Authorization.Token<OidcIdToken> oidcIdToken =
                oauth2Authorization.getToken(OidcIdToken.class);
        setTokenValues(
                oidcIdToken,
                entity::setOidcIdTokenValue,
                entity::setOidcIdTokenIssuedAt,
                entity::setOidcIdTokenExpiresAt,
                entity::setOidcIdTokenMetadata
        );
        if (oidcIdToken != null) {
            entity.setOidcIdTokenClaims(JacksonUtils.pojo2Json(oidcIdToken.getClaims()));
        }

        return entity;
    }

    private OAuth2Authorization convert2Oauth2Authorization(Authorization entity) {

        RegisteredClient registeredClient = this.registeredClientRepository.findByClientId(entity.getRegisteredClientId());
        AssertUtils.checkNotNull(registeredClient, SecurityExceptionCode.CLIENT_INFO_IS_NULL);

        OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .id(entity.getId())
                .principalName(entity.getPrincipalName())
                .authorizationGrantType(resolveAuthorizationGrantType(entity.getAuthorizationGrantType()))
                .authorizedScopes(Splitter.on(",").splitToStream(entity.getAuthorizedScopes()).collect(Collectors.toSet()))

                .attributes(attributes -> attributes.putAll(JacksonUtils.json2Map(entity.getAttributes())));
        if (entity.getState() != null) {
            builder.attribute(OAuth2ParameterNames.STATE, entity.getState());
        }

        if (entity.getAuthorizationCodeValue() != null) {
            OAuth2AuthorizationCode authorizationCode = new OAuth2AuthorizationCode(
                    entity.getAuthorizationCodeValue(),
                    entity.getAuthorizationCodeIssuedAt(),
                    entity.getAuthorizationCodeExpiresAt());
            builder.token(authorizationCode, metadata -> metadata.putAll(JacksonUtils.json2Map(entity.getAuthorizationCodeMetadata())));
        }

        if (entity.getAccessTokenValue() != null) {
            OAuth2AccessToken accessToken = new OAuth2AccessToken(
                    OAuth2AccessToken.TokenType.BEARER,
                    entity.getAccessTokenValue(),
                    entity.getAccessTokenIssuedAt(),
                    entity.getAccessTokenExpiresAt(),
                    Splitter.on(",").splitToStream(entity.getAccessTokenScopes()).collect(Collectors.toSet()));
            builder.token(accessToken, metadata -> metadata.putAll(JacksonUtils.json2Map(entity.getAccessTokenMetadata())));
        }

        if (entity.getRefreshTokenValue() != null) {
            OAuth2RefreshToken refreshToken = new OAuth2RefreshToken(
                    entity.getRefreshTokenValue(),
                    entity.getRefreshTokenIssuedAt(),
                    entity.getRefreshTokenExpiresAt());
            builder.token(refreshToken, metadata -> metadata.putAll(JacksonUtils.json2Map(entity.getRefreshTokenMetadata())));
        }

        if (entity.getOidcIdTokenValue() != null) {
            OidcIdToken idToken = new OidcIdToken(
                    entity.getOidcIdTokenValue(),
                    entity.getOidcIdTokenIssuedAt(),
                    entity.getOidcIdTokenExpiresAt(),
                    JacksonUtils.json2Map(entity.getOidcIdTokenClaims()));
            builder.token(idToken, metadata -> metadata.putAll(JacksonUtils.json2Map(entity.getOidcIdTokenMetadata())));
        }

        return builder.build();
    }


    /**
     * 设置token值
     * @param token
     * @param tokenValueConsumer
     * @param issuedAtConsumer
     * @param expiresAtConsumer
     * @param metadataConsumer
     */
    private void setTokenValues(
            OAuth2Authorization.Token<?> token,
            Consumer<String> tokenValueConsumer,
            Consumer<Instant> issuedAtConsumer,
            Consumer<Instant> expiresAtConsumer,
            Consumer<String> metadataConsumer) {
        if (token != null) {
            OAuth2Token oAuth2Token = token.getToken();
            tokenValueConsumer.accept(oAuth2Token.getTokenValue());
            issuedAtConsumer.accept(oAuth2Token.getIssuedAt());
            expiresAtConsumer.accept(oAuth2Token.getExpiresAt());
            metadataConsumer.accept(JacksonUtils.pojo2Json(token.getMetadata()));
        }
    }


    private static AuthorizationGrantType resolveAuthorizationGrantType(String authorizationGrantType) {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        }
        return new AuthorizationGrantType(authorizationGrantType);              // Custom authorization grant type
    }

}
