package eu.wawrzyczek.findflights

import eu.wawrzyczek.findflights.common.DateProvider
import org.koin.dsl.module.module

val applicationModule = module {
    single { DateProvider() }
}