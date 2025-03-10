package com.example.currencyexchangerates

sealed interface UserEvent {
    data class ChangeCurrency(val newCode: String): UserEvent
    data class ChangeBaseAmount(val newAmount: String): UserEvent
    data class ChangeTargetAmount(val newAmount: String): UserEvent
    data class SearchCurrency(val search: String): UserEvent
    data class GoToChangeCurrencyScreen(val type: String): UserEvent
    object doNothing: UserEvent
}