package com.diviso.graeshoppe.client;

import org.springframework.context.annotation.Bean;

import feign.RequestInterceptor;

import com.diviso.graeshoppe.security.oauth2.AuthorizationHeaderUtil;

@ExcludeFromComponentScan
public class OAuth2InterceptedFeignConfiguration {

    @Bean(name = "oauth2RequestInterceptor")
    public RequestInterceptor getOAuth2RequestInterceptor(AuthorizationHeaderUtil authorizationHeaderUtil) {
        return new TokenRelayRequestInterceptor(authorizationHeaderUtil);
    }
}
