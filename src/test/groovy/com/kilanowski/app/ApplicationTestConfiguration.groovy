package com.kilanowski.app

import com.kilanowski.time.CurrentTimeService
import com.mongodb.MongoClient
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

import java.time.Instant

@TestConfiguration
@EnableMongoRepositories
@SpringBootApplication
@ComponentScan(basePackages = "com.kilanowski.app")
class ApplicationTestConfiguration {

    public static final Instant CURRENT_TIME = Instant.ofEpochMilli(1545037200000L)

    private static final String MONGO_DB_URL = "localhost"
    private static final String MONGO_DB_NAME = "embeded_db"

    @Bean
    @Primary
    CurrentTimeService currentTimeService() {
        new CurrentTimeService() {
            @Override
            Instant now() {
                return CURRENT_TIME
            }
        }
    }

    @Bean
    @Primary
    MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean()
        mongo.setBindIp(MONGO_DB_URL)
        MongoClient mongoClient = mongo.getObject()
        new MongoTemplate(mongoClient, MONGO_DB_NAME)
    }
}
