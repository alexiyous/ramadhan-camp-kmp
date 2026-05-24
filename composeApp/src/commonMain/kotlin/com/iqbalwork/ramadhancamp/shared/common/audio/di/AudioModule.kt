package com.iqbalwork.ramadhancamp.shared.common.audio.di

import com.iqbalwork.ramadhancamp.shared.common.audio.AudioController
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val audioModule = module {
    factoryOf(::AudioController)
}