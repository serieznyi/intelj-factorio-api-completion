package io.serieznyi.intellij.factorioapicompletition.core.factorio.api.parser

import io.serieznyi.intellij.factorioapicompletition.core.factorio.version.ApiVersion
import io.serieznyi.intellij.factorioapicompletition.core.factorio.version.ApiVersionResolver
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ApiParserTest {
    private val apiParser = ApiParser();

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @ParameterizedTest
    @MethodSource("parseProvider")
    fun parse(apiVersion: ApiVersion) {
        var api = apiParser.parse(apiVersion)

        assertDoesNotThrow {}
    }

    companion object {
        @JvmStatic
        fun parseProvider(): Stream<ApiVersion> {
            return ApiVersionResolver().supportedVersions().reversed().stream()
        }
    }
}