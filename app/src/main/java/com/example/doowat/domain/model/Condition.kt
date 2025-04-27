package com.example.doowat.domain.model

sealed interface Condition {
    data object ClearDay : Condition
    data object ClearDark : Condition

    data object PartlyCloudyDay : Condition
    data object PartlyCloudyDark : Condition

    data object OvercastDay : Condition
    data object OvercastDark : Condition

    data object FoggyDay : Condition
    data object FoggyDark : Condition

    data object LightRainyDay : Condition
    data object LightRainyDark : Condition

    data object HeavyRainyDay : Condition
    data object HeavyRainyDark : Condition

    data object LightSnowyDay : Condition
    data object LightSnowyDark : Condition

    data object HeavySnowyDay : Condition
    data object HeavySnowyDark : Condition

    data object SleetOrIcyDay : Condition
    data object SleetOrIcyDark : Condition

    data object ThunderstormDay : Condition
    data object ThunderstormDark : Condition

    data object UnstableWeatherDay : Condition
    data object UnstableWeatherDark : Condition
}
