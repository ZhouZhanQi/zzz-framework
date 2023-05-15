package com.zzz.framework.starter.security.authorization;

import org.dromara.hutool.core.text.StrUtil;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.starter.cache.RedisCacheHelper;
import com.zzz.framework.starter.security.model.code.SecurityExceptionCode;
import com.zzz.framework.starter.security.model.code.SecurityRedisKeyPrefix;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;

/**
 * @author zhouzq
 */
@RequiredArgsConstructor
public class ZzzCacheOAuth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

    private final RedisCacheHelper<OAuth2AuthorizationConsent> redisCacheHelper;

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {
        AssertUtils.checkNotNull(authorizationConsent, SecurityExceptionCode.OAUTH2_AUTHORIZATION_CONSENT_IS_NULL);
        redisCacheHelper.set(SecurityRedisKeyPrefix.TOKEN_CONSENT, String.join(":", authorizationConsent.getRegisteredClientId(),authorizationConsent.getPrincipalName()), authorizationConsent);
    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {
        AssertUtils.checkNotNull(authorizationConsent, SecurityExceptionCode.OAUTH2_AUTHORIZATION_CONSENT_IS_NULL);
        redisCacheHelper.delete(SecurityRedisKeyPrefix.TOKEN_CONSENT, String.join(":", authorizationConsent.getRegisteredClientId(),authorizationConsent.getPrincipalName()));
    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        AssertUtils.checkArgument(StrUtil.isAllNotBlank(registeredClientId, principalName));
        return redisCacheHelper.get(SecurityRedisKeyPrefix.TOKEN_CONSENT, String.join(":", registeredClientId, principalName));
    }
}
