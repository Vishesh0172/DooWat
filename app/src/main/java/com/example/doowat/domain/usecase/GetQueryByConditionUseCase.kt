package com.example.doowat.domain.usecase

import com.example.doowat.domain.model.Condition

class GetQueryByConditionUseCase{



    operator fun invoke(condition: Condition): String{

        return when (condition) {

            is Condition.ClearDay              -> "outdoor activities"
            is Condition.ClearDark             -> "star gazing"

            is Condition.PartlyCloudyDay       -> "nature parks"
            is Condition.PartlyCloudyDark      -> "quiet cafes"

            is Condition.OvercastDay           -> "art galleries"
            is Condition.OvercastDark          -> "cozy restaurants"

            is Condition.FoggyDay              -> "scenic viewpoints"
            is Condition.FoggyDark             -> "tea cafes"

            is Condition.LightRainyDay         -> "indoor cafes"
            is Condition.LightRainyDark        -> "romantic restaurants"

            is Condition.HeavyRainyDay         -> "shopping malls"
            is Condition.HeavyRainyDark        -> "gaming zones"

            is Condition.LightSnowyDay         -> "ski resorts"
            is Condition.LightSnowyDark        -> "lodges with cafes"

            is Condition.HeavySnowyDay         -> "ski resorts"
            is Condition.HeavySnowyDark        -> "spa resorts"

            is Condition.SleetOrIcyDay         -> "indoor museums"
            is Condition.SleetOrIcyDark        -> "spas"

            is Condition.ThunderstormDay       -> "amusement centers"
            is Condition.ThunderstormDark      -> "board game cafes"

            is Condition.UnstableWeatherDay    -> "indoor activity centers"
            is Condition.UnstableWeatherDark   -> "bars with indoor seating"
        }
    }


}