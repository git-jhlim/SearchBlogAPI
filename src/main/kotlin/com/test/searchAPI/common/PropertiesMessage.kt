package com.test.searchAPI.common

import org.springframework.context.support.ResourceBundleMessageSource
import java.util.*

class PropertiesMessage {
    companion object {
        private val messageSource = ResourceBundleMessageSource()
            .apply {
                addBasenames("message/error-messages")
                setDefaultEncoding("UTF-8")
            }
        fun getMessage(key: String): String {
            return messageSource.getMessage(key, arrayOf<Any>(), Locale.getDefault())
        }

        fun getMessage(key: String, args: Array<Any?>): String {
            return messageSource.getMessage(key, args, Locale.getDefault())
        }
    }
}