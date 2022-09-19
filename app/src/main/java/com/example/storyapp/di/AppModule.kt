package com.example.storyapp.di

import com.example.storyapp.domain.IStoriesUseCase
import com.example.storyapp.domain.StoriesInteractor
import com.example.storyapp.presentasion.viewmodel.StoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IStoriesUseCase> { StoriesInteractor(get()) }
}

val viewModelModule = module {
    viewModel { StoriesViewModel(get() ) }
}