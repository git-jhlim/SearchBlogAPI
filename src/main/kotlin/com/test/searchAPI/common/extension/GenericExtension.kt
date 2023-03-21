package com.test.searchAPI.common.extension

import org.springframework.core.ParameterizedTypeReference

inline fun <reified T> pTypeRef() = object: ParameterizedTypeReference<T>() {}