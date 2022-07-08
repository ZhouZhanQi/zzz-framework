package com.zzz.framework.starters.security.authorization;

import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;

/**
 * @author zhouzq
 */
public class ZzzCacheOAuth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

    @Override
    public void save(OAuth2AuthorizationConsent authorizationConsent) {

    }

    @Override
    public void remove(OAuth2AuthorizationConsent authorizationConsent) {

    }

    @Override
    public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
        return null;
    }
}
