package com.example.petfinder.ui.util

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed class UiText {
    data class DynamicString(val value: String) : UiText()
    class Resource(val resource: StringResource) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is Resource -> stringResource(resource)
        }
    }
}
