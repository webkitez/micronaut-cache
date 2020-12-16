package com.example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.time.Month

@Controller
class NewsController(private val newsService: NewsService){


    @Get("/news")
    fun getNews(): List<String>? {
        return newsService.headlines(Month.NOVEMBER)
    }

}