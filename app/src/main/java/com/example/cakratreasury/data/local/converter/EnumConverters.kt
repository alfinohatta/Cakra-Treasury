package com.example.cakratreasury.data.local.converter

import androidx.room.TypeConverter
import com.example.cakratreasury.domain.model.*

class EnumConverters {
    @TypeConverter
    fun fromCompanySize(value: CompanySize): String = value.name
    @TypeConverter
    fun toCompanySize(value: String): CompanySize = CompanySize.valueOf(value)

    @TypeConverter
    fun fromUserRole(value: UserRole): String = value.name
    @TypeConverter
    fun toUserRole(value: String): UserRole = UserRole.valueOf(value)

    @TypeConverter
    fun fromBankAccountStatus(value: BankAccountStatus): String = value.name
    @TypeConverter
    fun toBankAccountStatus(value: String): BankAccountStatus = BankAccountStatus.valueOf(value)

    @TypeConverter
    fun fromTransactionType(value: TransactionType): String = value.name
    @TypeConverter
    fun toTransactionType(value: String): TransactionType = TransactionType.valueOf(value)

    @TypeConverter
    fun fromInvoiceStatus(value: InvoiceStatus): String = value.name
    @TypeConverter
    fun toInvoiceStatus(value: String): InvoiceStatus = InvoiceStatus.valueOf(value)

    @TypeConverter
    fun fromPayableStatus(value: PayableStatus): String = value.name
    @TypeConverter
    fun toPayableStatus(value: String): PayableStatus = PayableStatus.valueOf(value)

    @TypeConverter
    fun fromForecastPeriod(value: ForecastPeriod): String = value.name
    @TypeConverter
    fun toForecastPeriod(value: String): ForecastPeriod = ForecastPeriod.valueOf(value)

    @TypeConverter
    fun fromRiskLevel(value: RiskLevel): String = value.name
    @TypeConverter
    fun toRiskLevel(value: String): RiskLevel = RiskLevel.valueOf(value)

    @TypeConverter
    fun fromAgentType(value: AgentType): String = value.name
    @TypeConverter
    fun toAgentType(value: String): AgentType = AgentType.valueOf(value)

    @TypeConverter
    fun fromAgentStatus(value: AgentStatus): String = value.name
    @TypeConverter
    fun toAgentStatus(value: String): AgentStatus = AgentStatus.valueOf(value)

    @TypeConverter
    fun fromRecommendationType(value: RecommendationType): String = value.name
    @TypeConverter
    fun toRecommendationType(value: String): RecommendationType = RecommendationType.valueOf(value)

    @TypeConverter
    fun fromRecommendationStatus(value: RecommendationStatus): String = value.name
    @TypeConverter
    fun toRecommendationStatus(value: String): RecommendationStatus = RecommendationStatus.valueOf(value)

    @TypeConverter
    fun fromApprovalStatus(value: ApprovalStatus): String = value.name
    @TypeConverter
    fun toApprovalStatus(value: String): ApprovalStatus = ApprovalStatus.valueOf(value)

    @TypeConverter
    fun fromExecutionStatus(value: ExecutionStatus): String = value.name
    @TypeConverter
    fun toExecutionStatus(value: String): ExecutionStatus = ExecutionStatus.valueOf(value)
}
