package de.peekandpoke.ktorfx.common.config.ktor

import com.fasterxml.jackson.annotation.JsonIgnore

data class KtorConfig(
    val deployment: Deployment,
    val application: Application,
    val security: Security? = null
) {
    data class Deployment(
        val environment: String = "development",
        val host: String = "0.0.0.0",
        val port: Int = 80,
        val sslPort: Int = 8443,
        val autoreload: Boolean = false,
        val watch: List<String> = listOf()
    )

    data class Application(
        val id: String = "Application",
        val modules: List<String>
    )

    data class Security(
        val keyStore: String? = null,
        val keyAlias: String? = null,
        @get:JsonIgnore
        val keyStorePassword: String? = null,
        @get:JsonIgnore
        val privateKeyPassword: String? = null
    )
}
