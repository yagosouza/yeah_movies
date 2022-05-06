package com.yagosouza.yeahmovies.di

import com.yagosouza.yeahmovies.MainActivity
import com.yagosouza.yeahmovies.ui.HomeFragment
import dagger.Subcomponent

@Subcomponent(modules = [])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
}