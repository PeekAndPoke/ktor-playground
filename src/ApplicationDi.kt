package de.peekandpoke

import com.thebase.joinTheBase
import de.peekandpoke.demos.forms.formDemos
import de.peekandpoke.karango.karango
import de.peekandpoke.ktorfx.security.KtorFXSecurityConfig
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.module.cms.cmsAdmin
import de.peekandpoke.module.cms.cmsCommon
import de.peekandpoke.module.cms.cmsPublic
import de.peekandpoke.module.got.gameOfThrones
import de.peekandpoke.module.semanticui.semanticUi
import de.peekandpoke.modules.depot.depotAdmin
import de.peekandpoke.resources.AdminWebResources
import de.peekandpoke.resources.AppI18n
import de.peekandpoke.resources.WwwWebResources
import de.peekandpoke.ultra.kontainer.kontainer
import de.peekandpoke.ultra.security.user.StaticUserRecordProvider
import de.peekandpoke.ultra.security.user.UserRecord
import de.peekandpoke.ultra.vault.ultraVault
import io.ktor.application.Application
import io.ktor.application.ApplicationEnvironment
import io.ktor.config.ApplicationConfig
import io.ultra.ktor_tools.KtorFXConfig
import io.ultra.ktor_tools.ktorFx
import java.net.InetAddress

class ApplicationDi(private val app: Application) {

    fun systemKontainer() = commonKontainerBlueprint.useWith(
        // user record provider
        StaticUserRecordProvider(
            UserRecord("system", InetAddress.getLocalHost().canonicalHostName)
        )
    )

    val wwwKontainerBlueprint by lazy {

        commonKontainerBlueprint.extend {
            // set default html template
            prototype(SimpleTemplate::class, WwwTemplate::class)
        }
    }

    val adminKontainerBlueprint by lazy {

        commonKontainerBlueprint.extend {
            // set default html template
            prototype(SimpleTemplate::class, AdminTemplate::class)
        }
    }

    private val commonKontainerBlueprint by lazy {

        // TODO: get the config from application.environment.config

        val config = KtorFXConfig(
            KtorFXSecurityConfig("super-secret", 300_000)
        )

        kontainer {

            // Database //////////////////////////////////////////////////////////////////////////////////////////////////////////
            ultraVault()

            // Add karango and provide connection to arango database
            karango()
            instance(arangoDatabase)


            // KtorFX ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ktorFx(config)
            // create a app specific cache buster
            instance(AppMeta.cacheBuster())

            // application ///////////////////////////////////////////////////////////////////////////////////////////////////////

            instance(Application::class, app)
            instance(ApplicationEnvironment::class, app.environment)
            @Suppress("EXPERIMENTAL_API_USAGE")
            instance(ApplicationConfig::class, app.environment.config)

            // i18n
            singleton(AppI18n::class)

            // web resources
            singleton(AdminWebResources::class)
            singleton(WwwWebResources::class)

            // helper modules
            cmsCommon()
            cmsAdmin()
            cmsPublic()

            depotAdmin()

            // application modules
            joinTheBase()
            gameOfThrones()
            semanticUi()
            formDemos()

        }
    }
}
