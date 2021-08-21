package com.matxowy

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.matxowy.dogfacts.data.db.DogFactsDatabase
import com.matxowy.dogfacts.data.network.*
import com.matxowy.dogfacts.data.repository.DogFactsRepository
import com.matxowy.dogfacts.data.repository.DogFactsRepositoryImpl
import com.matxowy.dogfacts.ui.list.ListOfDogFactsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class DogFactsApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@DogFactsApplication))

        bind() from singleton { DogFactsDatabase(instance()) }
        bind() from singleton { instance<DogFactsDatabase>().dogFactsDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { DogFactsApiService(instance()) }
        bind<DogFactsNetworkDataSource>() with singleton { DogFactsNetworkDataSourceImpl(instance()) }
        bind<DogFactsRepository>() with singleton { DogFactsRepositoryImpl(instance(), instance()) }
        bind() from provider { ListOfDogFactsViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}