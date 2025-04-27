package com.example.doowat.ui.theme


import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.doowat.domain.model.Condition


val lightBlue = Color(0xFF3EE0F6)
val overcastBlue = Color(0xFF426270)
val rainyBlue = Color(0xFFAEC6CF)
val thunderBlue = Color(0xFFAEC6CF)
val snowLightBlue = Color(0xFF7FA5C0)
val black = Color.Black

// Day ColorSchemes
val clearLight = lightColorScheme(background = lightBlue, onPrimary = Color.Yellow, onBackground = Color.White, surface = lightBlue, onSurface = Color.White)
val partlyCloudyLight = lightColorScheme(background = lightBlue)
val overcastLight = lightColorScheme(background = overcastBlue)
val foggyLight = lightColorScheme(background = rainyBlue)
val rainyLight = lightColorScheme(background = rainyBlue)
val snowyLight = lightColorScheme(background = snowLightBlue, onBackground = Color.Black)
val sleetLight = lightColorScheme(background = Color.White, onBackground = Color.Black)
val thunderstormLight = lightColorScheme(background = thunderBlue)
val unstableLight = lightColorScheme(background = thunderBlue)

// Night ColorSchemes
val clearDark = darkColorScheme(background = black, onBackground = Color.White)
val partlyCloudyDark = darkColorScheme(background = black, onBackground = Color.White)
val overcastDark = darkColorScheme(background = black, onBackground = Color.White)
val foggyDark = darkColorScheme(background = rainyBlue, onBackground = Color.White)
val rainyDark = darkColorScheme(background = black, onBackground = Color.White)
val snowyDark = darkColorScheme(background = black, onBackground = Color.LightGray)
val sleetDark = darkColorScheme(background = Color.White, onBackground = Color.Black)
val thunderstormDark = darkColorScheme(background = black, onBackground = Color.White)
val unstableDark = darkColorScheme(background = black, onBackground = Color.White)


fun getColorScheme(condition: Condition): ColorScheme{

    return when(condition) {
        Condition.ClearDark -> clearDark
        Condition.ClearDay -> clearLight

        Condition.FoggyDark -> foggyDark
        Condition.FoggyDay -> foggyLight

        Condition.HeavyRainyDark -> rainyDark
        Condition.HeavyRainyDay -> rainyLight

        Condition.HeavySnowyDark -> snowyDark
        Condition.HeavySnowyDay -> snowyLight

        Condition.LightRainyDark -> rainyDark
        Condition.LightRainyDay -> rainyLight

        Condition.LightSnowyDark -> snowyDark
        Condition.LightSnowyDay -> snowyLight

        Condition.OvercastDark -> overcastDark
        Condition.OvercastDay -> overcastLight

        Condition.PartlyCloudyDark -> partlyCloudyDark
        Condition.PartlyCloudyDay -> partlyCloudyLight

        Condition.SleetOrIcyDark -> sleetDark
        Condition.SleetOrIcyDay -> sleetLight

        Condition.ThunderstormDark -> thunderstormDark
        Condition.ThunderstormDay -> thunderstormLight

        Condition.UnstableWeatherDark -> unstableDark
        Condition.UnstableWeatherDay -> unstableLight
    }
}
