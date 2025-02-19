package com.priorDev.pokerroutejc.di

import com.priorDev.pokerroutejc.data.TypeRepo
import com.priorDev.pokerroutejc.data.TypeRepoImp
import com.priorDev.pokerroutejc.data.database.MyDataBase
import com.priorDev.pokerroutejc.data.network.pkType.TypeNetService
import com.priorDev.pokerroutejc.data.network.pkType.TypeNetServiceImp
import com.priorDev.pokerroutejc.presentation.typeDetails.DetailsTypeViewModel
import com.priorDev.pokerroutejc.presentation.typeList.ListTypeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val kTypeModule = module {
    single<TypeNetService> {
        TypeNetServiceImp(
            networkService = get()
        )
    }

    single {
        get<MyDataBase>().typeDao
    }

    single<TypeRepo> {
        TypeRepoImp(
            service = get(),
            dao = get()
        )
    }

    viewModel {
        ListTypeViewModel(
            repository = get(),
            globalEvent = get()
        )
    }

    viewModel {
        DetailsTypeViewModel(
            repository = get(),
            savedStateHandle = get()
        )
    }
}
