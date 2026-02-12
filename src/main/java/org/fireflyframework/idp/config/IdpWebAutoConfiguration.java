/*
 * Copyright (c) 2024 Firefly Software Solutions Inc.
 */
package org.fireflyframework.idp.config;

import lombok.extern.slf4j.Slf4j;
import org.fireflyframework.idp.adapter.IdpAdapter;
import org.fireflyframework.idp.web.IdpController;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@Slf4j
@AutoConfiguration
@ConditionalOnBean(IdpAdapter.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
@EnableConfigurationProperties(IdpProperties.class)
public class IdpWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public IdpController idpController(IdpAdapter idpAdapter) {
        log.info("Registering IDP REST controller");
        return new IdpController(idpAdapter);
    }
}
