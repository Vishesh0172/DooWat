package com.example.doowat.domain.usecase

import com.example.doowat.domain.model.Condition
import com.example.doowat.domain.model.Condition.*

class GetConditionUseCase {

    operator fun invoke(conditionCode: Int, isDay: Boolean): Condition {
        return when (conditionCode) {
            1000 -> if (isDay) ClearDay else ClearDark

            1003 -> if (isDay) PartlyCloudyDay else PartlyCloudyDark
            1006, 1009 -> if (isDay) OvercastDay else OvercastDark

            1030, 1135, 1147 -> if (isDay) FoggyDay else FoggyDark

            1063, 1150, 1153, 1180, 1183, 1240 -> if (isDay) LightRainyDay else LightRainyDark
            1186, 1189, 1192, 1195, 1243, 1246, 1201 -> if (isDay) HeavyRainyDay else HeavyRainyDark

            1066, 1210, 1213, 1216, 1255 -> if (isDay) LightSnowyDay else LightSnowyDark
            1114, 1117, 1219, 1222, 1225, 1258 -> if (isDay) HeavySnowyDay else HeavySnowyDark

            1069, 1072, 1168, 1171, 1204, 1207, 1237, 1261, 1264 -> if (isDay) SleetOrIcyDay else SleetOrIcyDark

            1087, 1273, 1276, 1279, 1282 -> if (isDay) ThunderstormDay else ThunderstormDark


            else -> if (isDay) ClearDay else ClearDark
        }
    }

}


