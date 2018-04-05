package com.jamesvanhallen.hotnews.di

import com.jamesvanhallen.hotnews.repository.NewsRepository
import com.jamesvanhallen.hotnews.ui.adapter.ArticleAdapter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class)])
interface AppComponent {

    fun inject(obj: NewsRepository)

    fun inject(obj: ArticleAdapter.ViewHolder)

}