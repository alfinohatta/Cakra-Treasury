package com.example.cakratreasury.data.mapper

import com.example.cakratreasury.data.local.entity.*
import com.example.cakratreasury.domain.model.*

fun CompanyEntity.toDomain(): Company {
    return Company(
        id = id,
        companyName = companyName,
        industry = industry,
        taxId = taxId,
        companySize = companySize,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun Company.toEntity(): CompanyEntity {
    return CompanyEntity(
        id = id,
        companyName = companyName,
        industry = industry,
        taxId = taxId,
        companySize = companySize,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun BankAccountEntity.toDomain(): BankAccount {
    return BankAccount(
        id = id,
        companyId = companyId,
        bankName = bankName,
        accountNumber = accountNumber,
        accountName = accountName,
        currencyCode = currencyCode,
        currentBalance = currentBalance,
        status = status
    )
}

fun BankAccount.toEntity(): BankAccountEntity {
    return BankAccountEntity(
        id = id,
        companyId = companyId,
        bankName = bankName,
        accountNumber = accountNumber,
        accountName = accountName,
        currencyCode = currencyCode,
        currentBalance = currentBalance,
        status = status
    )
}

fun TransactionEntity.toDomain(): Transaction {
    return Transaction(
        id = id,
        bankAccountId = bankAccountId,
        transactionDate = transactionDate,
        type = type,
        category = category,
        description = description,
        amount = amount,
        currencyCode = currencyCode,
        createdAt = createdAt
    )
}

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        bankAccountId = bankAccountId,
        transactionDate = transactionDate,
        type = type,
        category = category,
        description = description,
        amount = amount,
        currencyCode = currencyCode,
        createdAt = createdAt
    )
}

fun CashFlowForecastEntity.toDomain(): CashFlowForecast {
    return CashFlowForecast(
        id = id,
        companyId = companyId,
        forecastDate = forecastDate,
        forecastPeriod = forecastPeriod,
        predictedInflow = predictedInflow,
        predictedOutflow = predictedOutflow,
        predictedBalance = predictedBalance,
        confidenceScore = confidenceScore,
        createdAt = createdAt
    )
}

fun TreasuryRecommendationEntity.toDomain(thoughts: List<AgentThought> = emptyList()): TreasuryRecommendation {
    return TreasuryRecommendation(
        id = id,
        companyId = companyId,
        type = type,
        recommendationText = recommendationText,
        expectedReturn = expectedReturn,
        riskScore = riskScore,
        status = status,
        reasoning = thoughts,
        createdAt = createdAt
    )
}

fun AiAgentEntity.toDomain(): AiAgent {
    return AiAgent(
        id = id,
        agentName = agentName,
        agentType = agentType,
        status = status,
        createdAt = createdAt
    )
}

fun AiAgent.toEntity(): AiAgentEntity {
    return AiAgentEntity(
        id = id,
        agentName = agentName,
        agentType = agentType,
        status = status,
        createdAt = createdAt
    )
}

fun FxExposureEntity.toDomain(): FxExposure {
    return FxExposure(
        id = id,
        companyId = companyId,
        currencyCode = currencyCode,
        exposureAmount = exposureAmount,
        riskLevel = riskLevel,
        createdAt = createdAt
    )
}

fun InvoiceEntity.toDomain(): Invoice {
    return Invoice(
        id = id,
        companyId = companyId,
        invoiceNumber = invoiceNumber,
        customerName = customerName,
        invoiceAmount = invoiceAmount,
        dueDate = dueDate,
        status = status,
        createdAt = createdAt
    )
}

fun SupplierPayableEntity.toDomain(): SupplierPayable {
    return SupplierPayable(
        id = id,
        companyId = companyId,
        supplierName = supplierName,
        payableAmount = payableAmount,
        dueDate = dueDate,
        status = status,
        createdAt = createdAt
    )
}

fun AgentThoughtEntity.toDomain(): AgentThought {
    return AgentThought(
        agentName = agentName,
        thought = thought
    )
}

fun ExecutionLogEntity.toDomain(recommendationType: String = "Unknown"): ExecutionLog {
    return ExecutionLog(
        id = id,
        recommendationId = recommendationId,
        recommendationType = recommendationType,
        status = status,
        message = message,
        executedAt = executedAt
    )
}

fun UserEntity.toDomain(): User {
    return User(
        id = id,
        companyId = companyId,
        fullName = fullName,
        email = email,
        role = role,
        createdAt = createdAt
    )
}

fun User.toEntity(passwordHash: String): UserEntity {
    return UserEntity(
        id = id,
        companyId = companyId,
        fullName = fullName,
        email = email,
        passwordHash = passwordHash,
        role = role,
        createdAt = createdAt
    )
}
