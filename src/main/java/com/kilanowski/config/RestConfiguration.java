package com.kilanowski.config;


import com.fasterxml.jackson.databind.Module;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Configuration
@EnableSpringDataWebSupport
class RestConfiguration {

    @Bean
    Module allowUsingVavrCollectionsAndTypesInJacksonMapping() {
        return new VavrModule();
    }

}
