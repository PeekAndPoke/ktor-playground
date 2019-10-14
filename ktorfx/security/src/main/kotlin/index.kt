package de.peekandpoke.ktorfx.security

import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.security.csrf.CsrfProtection
import de.peekandpoke.ultra.security.csrf.StatelessCsrfProtection
import de.peekandpoke.ultra.security.user.UserRecordProvider

data class KtorFXSecurityConfig(val csrfSecret: String, val csrfTtlMillis: Int)

fun KontainerBuilder.ktorFxSecurity(config: KtorFXSecurityConfig) = module(KtorFX_Security, config)

val KtorFX_Security = module { config: KtorFXSecurityConfig ->

    dynamic(CsrfProtection::class) { userRecordProvider: UserRecordProvider ->
        StatelessCsrfProtection(config.csrfSecret, config.csrfTtlMillis, userRecordProvider)
    }
}
