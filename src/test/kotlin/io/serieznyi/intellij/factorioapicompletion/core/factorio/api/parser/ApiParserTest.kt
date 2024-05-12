package io.serieznyi.intellij.factorioapicompletion.core.factorio.api.parser

import com.intellij.openapi.util.io.findOrCreateDirectory
import io.serieznyi.intellij.factorioapicompletion.core.cache.FileCache
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source.FileCacheDataSourceFactory
import io.serieznyi.intellij.factorioapicompletion.core.factorio.api.source.HttpDataSourceFactory
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersion
import io.serieznyi.intellij.factorioapicompletion.core.factorio.version.ApiVersionResolverHolder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.io.CleanupMode
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path
import java.nio.file.Paths
import java.time.Duration
import java.util.stream.Stream

class ApiParserTest {
    private lateinit var apiParser: ApiParser

    @BeforeEach
    fun setUp(@TempDir(cleanup = CleanupMode.NEVER) tempDir: Path) {
        val cacheDir = Paths.get(System.getenv("BUILD_DIR_PATH")).resolve(ApiParserTest::class.simpleName.toString())

        apiParser = ApiParser(
            FileCacheDataSourceFactory(
                HttpDataSourceFactory(),
                FileCache(cacheDir.findOrCreateDirectory(), Duration.ofDays(14))
            ),
            throwOnUnknownType = true
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @ParameterizedTest
    @MethodSource("parseProvider")
    fun parse(apiVersion: ApiVersion) {
        assertDoesNotThrow {
            apiParser.parse(apiVersion)
        }
    }

    companion object {
        @JvmStatic
        fun parseProvider(): Stream<ApiVersion> {
            return ApiVersionResolverHolder.get().supportedVersions().reversed().stream()
        }
    }
}