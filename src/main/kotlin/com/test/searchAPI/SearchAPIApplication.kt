package com.test.searchAPI

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class SearchAPIApplication

fun main(args: Array<String>) {
    runApplication<SearchAPIApplication>(*args)
}
