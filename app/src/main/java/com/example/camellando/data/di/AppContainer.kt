package com.example.camellando.data.di

import com.example.camellando.BuildConfig
import com.example.camellando.data.repository.AuthRepository
import com.example.camellando.data.repository.ChatRepository
import com.example.camellando.data.repository.ContractReviewRepository
import com.example.camellando.data.repository.NotificationRepository
import com.example.camellando.data.repository.OfferRepository
import com.example.camellando.data.repository.PortfolioRepository
import com.example.camellando.data.repository.ProfileRepository
import com.example.camellando.data.repository.ServiceContractRepository
import com.example.camellando.data.repository.ServicePostRepository
import com.example.camellando.data.repository.StorageRepository
import com.example.camellando.data.repository.UserReportRepository
import com.example.camellando.data.repository.remote.SupabaseAuthRepository
import com.example.camellando.data.repository.remote.SupabaseChatRepository
import com.example.camellando.data.repository.remote.SupabaseContractReviewRepository
import com.example.camellando.data.repository.remote.SupabaseNotificationRepository
import com.example.camellando.data.repository.remote.SupabaseOfferRepository
import com.example.camellando.data.repository.remote.SupabasePortfolioRepository
import com.example.camellando.data.repository.remote.SupabaseProfileRepository
import com.example.camellando.data.repository.remote.SupabaseServiceContractRepository
import com.example.camellando.data.repository.remote.SupabaseServicePostRepository
import com.example.camellando.data.repository.remote.SupabaseStorageRepository
import com.example.camellando.data.repository.remote.SupabaseUserReportRepository
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.FlowType
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.realtime.Realtime

object AppContainer {
    val supabase by lazy {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_PUBLISHABLE_KEY
        ) {
            install(Auth) {
                flowType = FlowType.PKCE
                scheme = "com.example.camellando"
                host = "auth-callback"
            }

            install(Postgrest)
            install(Storage)
            install(Realtime)
        }
    }

    val notificationRepository: NotificationRepository by lazy {
        SupabaseNotificationRepository(supabase)
    }

    val authRepository: AuthRepository by lazy { SupabaseAuthRepository(supabase) }
    val servicePostRepository: ServicePostRepository by lazy {
        SupabaseServicePostRepository(supabase)
    }
    val profileRepository: ProfileRepository by lazy { SupabaseProfileRepository(supabase) }
    val offerRepository: OfferRepository by lazy { SupabaseOfferRepository(supabase) }
    val chatRepository: ChatRepository by lazy { SupabaseChatRepository(supabase) }
    val portfolioRepository: PortfolioRepository by lazy {
        SupabasePortfolioRepository(supabase)
    }
    val serviceContractRepository: ServiceContractRepository by lazy {
        SupabaseServiceContractRepository(supabase)
    }
    val contractReviewRepository: ContractReviewRepository by lazy {
        SupabaseContractReviewRepository(supabase)
    }
    val userReportRepository: UserReportRepository by lazy {
        SupabaseUserReportRepository(supabase)
    }
    val storageRepository: StorageRepository by lazy {
        SupabaseStorageRepository(supabase)
    }
}
