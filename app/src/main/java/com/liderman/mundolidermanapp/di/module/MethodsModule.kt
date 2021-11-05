package com.liderman.mundolidermanapp.di.module

import android.content.Context
import com.liderman.mundolidermanapp.utils.Methods
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author by GERARDO on 6/04/2018.
 */
@Module
class MethodsModule {

    @Provides
    @Singleton
    fun provideMethods(context: Context) = Methods(context)
}