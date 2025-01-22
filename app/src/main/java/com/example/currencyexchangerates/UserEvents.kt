package com.example.currencyexchangerates

sealed interface UserEvent {
    object ChangeBaseCurrency: UserEvent
    object ChangeTargetCurrency: UserEvent
    data class ChangeBaseAmount(val newAmount: String): UserEvent
    data class ChangeTargetAmount(val newAmount: String): UserEvent
    object doNothing: UserEvent
}