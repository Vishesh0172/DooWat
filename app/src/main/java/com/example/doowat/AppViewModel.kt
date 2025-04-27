package com.example.doowat

import androidx.compose.material3.ColorScheme
import androidx.lifecycle.ViewModel
import com.example.doowat.domain.model.Condition
import com.example.doowat.ui.theme.clearDark
import com.example.doowat.ui.theme.getColorScheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel(

): ViewModel() {

    private val _state = MutableStateFlow(ThemeState())
    val state = _state.asStateFlow()

    fun changeTheme(condition: Condition){
        _state.update { it.copy(colorScheme = getColorScheme(condition)) }
    }
}

data class ThemeState(
    val colorScheme: ColorScheme = clearDark
)