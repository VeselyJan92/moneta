package cz.janvesely.nba.di

import cz.janvesely.nba.network.api.NbaAPI
import cz.janvesely.nba.repository.NBARepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideNbaRepository(nbaAPI: NbaAPI): NBARepository {
        return NBARepository(nbaAPI)
    }
}