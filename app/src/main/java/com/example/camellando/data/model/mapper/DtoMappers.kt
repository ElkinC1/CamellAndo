package com.example.camellando.data.model.mapper

import com.example.camellando.data.model.AccountStatus
import com.example.camellando.data.model.PostImage
import com.example.camellando.data.model.Profile
import com.example.camellando.data.model.ServicePost
import com.example.camellando.data.model.ServicePostStatus
import com.example.camellando.data.model.UserRole
import com.example.camellando.data.model.dto.PostImageDto
import com.example.camellando.data.model.dto.ProfileDto
import com.example.camellando.data.model.dto.ServicePostDto
import kotlin.math.roundToLong
import com.example.camellando.data.model.Chat
import com.example.camellando.data.model.ChatMessage
import com.example.camellando.data.model.ContractReview
import com.example.camellando.data.model.Offer
import com.example.camellando.data.model.OfferStatus
import com.example.camellando.data.model.Portfolio
import com.example.camellando.data.model.PortfolioItem
import com.example.camellando.data.model.PortfolioItemImage
import com.example.camellando.data.model.ServiceContract
import com.example.camellando.data.model.ServiceContractStatus
import com.example.camellando.data.model.UserReport
import com.example.camellando.data.model.UserReportStatus
import com.example.camellando.data.model.Notification
import com.example.camellando.data.model.dto.ChatDto
import com.example.camellando.data.model.dto.ChatMessageDto
import com.example.camellando.data.model.dto.ContractReviewDto
import com.example.camellando.data.model.dto.OfferDto
import com.example.camellando.data.model.dto.PortfolioDto
import com.example.camellando.data.model.dto.PortfolioItemDto
import com.example.camellando.data.model.dto.PortfolioItemImageDto
import com.example.camellando.data.model.dto.ServiceContractDto
import com.example.camellando.data.model.dto.UserReportDto
import com.example.camellando.data.model.dto.NotificationDto

fun ProfileDto.toDomain(): Profile =
    Profile(
        id = id,
        displayName = displayName,
        role = UserRole.valueOf(role),
        bio = bio,
        avatarPath = avatarPath,
        accountStatus = AccountStatus.valueOf(accountStatus),
        cvPath = cvPath,
        cvVisible = cvVisible,
        createdAt = createdAt,
        location = location,
        birthDate = birthDate,
        identification = identification,
        occupation = occupation,
        experienceYears = experienceYears,
        minPrice = minPrice,
        maxPrice = maxPrice
    )

fun ServicePostDto.toDomain(): ServicePost =
    ServicePost(
        id = id,
        providerId = providerId,
        title = title,
        description = description,
        category = category,
        priceCents = price?.let { (it * 100).roundToLong() },
        currency = currency,
        status = ServicePostStatus.valueOf(status),
        createdAt = createdAt
    )

fun PostImageDto.toDomain(): PostImage =
    PostImage(
        id = id,
        postId = postId,
        storagePath = storagePath,
        position = position,
        createdAt = createdAt
    )

fun OfferDto.toDomain(): Offer =
    Offer(
        id = id,
        postId = postId,
        clientId = clientId,
        providerId = providerId,
        parentOfferId = parentOfferId,
        amountCents = (amount * 100).roundToLong(),
        currency = currency,
        message = message,
        status = OfferStatus.valueOf(status),
        createdAt = createdAt
    )

fun ChatDto.toDomain(): Chat =
    Chat(
        id = id,
        userAId = userAId,
        userBId = userBId,
        createdAt = createdAt
    )

fun ChatMessageDto.toDomain(): ChatMessage =
    ChatMessage(
        id = id,
        chatId = chatId,
        senderId = senderId,
        content = content,
        createdAt = createdAt
    )

fun PortfolioDto.toDomain(): Portfolio =
    Portfolio(
        id = id,
        providerId = providerId,
        title = title,
        description = description,
        createdAt = createdAt
    )

fun PortfolioItemDto.toDomain(): PortfolioItem =
    PortfolioItem(
        id = id,
        portfolioId = portfolioId,
        servicePostId = servicePostId,
        title = title,
        description = description,
        position = position,
        createdAt = createdAt
    )

fun PortfolioItemImageDto.toDomain(): PortfolioItemImage =
    PortfolioItemImage(
        id = id,
        portfolioItemId = portfolioItemId,
        storagePath = storagePath,
        position = position,
        createdAt = createdAt
    )

fun ServiceContractDto.toDomain(): ServiceContract =
    ServiceContract(
        id = id,
        servicePostId = servicePostId,
        acceptedOfferId = acceptedOfferId,
        clientId = clientId,
        providerId = providerId,
        agreedPriceCents = (agreedPrice * 100).roundToLong(),
        currency = currency,
        status = ServiceContractStatus.valueOf(status),
        createdAt = createdAt,
        completedAt = completedAt,
        cancelledAt = cancelledAt
    )

fun ContractReviewDto.toDomain(): ContractReview =
    ContractReview(
        id = id,
        contractId = contractId,
        reviewerId = reviewerId,
        reviewedUserId = reviewedUserId,
        rating = rating,
        review = review,
        createdAt = createdAt
    )

fun UserReportDto.toDomain(): UserReport =
    UserReport(
        id = id,
        reporterId = reporterId,
        reportedUserId = reportedUserId,
        reason = reason,
        details = details,
        status = UserReportStatus.valueOf(status),
        createdAt = createdAt
    )

fun NotificationDto.toDomain(): Notification =
    Notification(
        id = id,
        recipientId = recipientId,
        actorId = actorId,
        type = type,
        title = title,
        body = body,
        relatedType = relatedType,
        relatedId = relatedId,
        isRead = isRead,
        createdAt = createdAt
    )
