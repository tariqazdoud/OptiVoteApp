package com.example.optivote.dbConnection

import com.example.optivote.repository.AuthenticationRepository
import com.example.optivote.repository.AuthenticationRepositoryImpl
import com.example.optivote.repository.UserRepository
import com.example.optivote.repository.UserRepositoryImp
import com.example.optivote.repository.VoteRecordRepository
import com.example.optivote.repository.VoteRecordRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    fun provideLoginRepesetory(auth:Auth): AuthenticationRepository = AuthenticationRepositoryImpl(auth)
    @Provides
    fun provideUserRepository(auth: Auth,postgrest:Postgrest) : UserRepository = UserRepositoryImp(auth,postgrest)
    @Provides
    fun provideVoteRecordRepository(auth: Auth,postgrest:Postgrest) : VoteRecordRepository = VoteRecordRepositoryImp(auth, postgrest)
}