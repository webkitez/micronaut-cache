package com.example

import io.micronaut.cache.annotation.CacheConfig
import io.micronaut.cache.annotation.CacheInvalidate
import io.micronaut.cache.annotation.CachePut
import io.micronaut.cache.annotation.Cacheable
import java.time.Month
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
@CacheConfig("headlines")
class NewsService {
    val headlines = mutableMapOf(
            Month.NOVEMBER to listOf("Micronaut Graduates to Trial Level in Thoughtworks technology radar Vol.1",
                    "Micronaut AOP: Awesome flexibility without the complexity"),
            Month.OCTOBER to listOf("Micronaut AOP: Awesome flexibility without the complexity"))

    @Cacheable
    fun headlines(month: Month): List<String>? {
        return try {
            TimeUnit.SECONDS.sleep(3)
            headlines[month]
        } catch (e: InterruptedException) {
            null
        }
    }

    @CachePut(parameters = ["month"])
    fun addHeadline(month: Month, headline: String): List<String>? {
        val l = headlines.getOrDefault(month, emptyList()).toMutableList()
        l.add(headline)
        headlines[month] = l
        return headlines[month]
    }

    @CacheInvalidate(parameters = ["month"])
    fun removeHeadline(month: Month, headline: String?) {
        val l = headlines.getOrDefault(month, emptyList()).toMutableList()
        l.remove(headline)
        headlines[month] = l
    }
}