package com.test.kakaobank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class KakaoBankApplication

fun main(args: Array<String>) {
    runApplication<KakaoBankApplication>(*args)
}
