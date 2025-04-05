package com.priorDev.pokerroutejc.utils

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.internalToRoute
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class SavedStateHandleRule(
    private val route: Any
) : BeforeEachCallback, AfterEachCallback {

    val savedStateHandleMock: SavedStateHandle = mockk()

    override fun beforeEach(context: ExtensionContext?) {
        mockkStatic("androidx.navigation.SavedStateHandleKt")
        every { savedStateHandleMock.internalToRoute<Any>(any(), any()) } returns route
    }

    override fun afterEach(context: ExtensionContext?) {
        unmockkStatic("androidx.navigation.SavedStateHandleKt")
    }
}
