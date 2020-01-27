package com.thebase

import com.thebase._sortme_.AppI18n
import com.thebase.apps.admin.AdminTemplate
import com.thebase.apps.admin.AdminWebResources
import com.thebase.apps.www.WwwTemplate
import com.thebase.apps.www.WwwWebResources
import com.thebase.config.TheBaseConfig
import de.peekandpoke.demos.forms.formDemos
import de.peekandpoke.demos.semanticui.semanticUi
import de.peekandpoke.karango.karango
import de.peekandpoke.ktorfx.common.config.AppConfig
import de.peekandpoke.ktorfx.common.config.ConfigMapper
import de.peekandpoke.ktorfx.security.KtorFXSecurityConfig
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.ktorfx.webresources.AppMeta
import de.peekandpoke.modules.cms.cmsAdmin
import de.peekandpoke.modules.cms.cmsCommon
import de.peekandpoke.modules.cms.cmsPublic
import de.peekandpoke.modules.depot.depotAdmin
import de.peekandpoke.modules.got.gameOfThrones
import de.peekandpoke.ultra.kontainer.kontainer
import de.peekandpoke.ultra.security.user.StaticUserRecordProvider
import de.peekandpoke.ultra.security.user.UserRecord
import de.peekandpoke.ultra.vault.ultraVault
import io.ktor.application.Application
import io.ktor.application.ApplicationEnvironment
import io.ktor.config.ApplicationConfig
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.KtorFXConfig
import io.ultra.ktor_tools.ktorFx
import java.net.InetAddress

@KtorExperimentalAPI
class ApplicationDi(private val app: Application) {

    private val configuration = ConfigMapper().map(app.environment.config, TheBaseConfig::class)

    private val appMeta = object : AppMeta() {}

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
            instance(appMeta.cacheBuster())

            // application ///////////////////////////////////////////////////////////////////////////////////////////////////////

            instance(Application::class, app)
            instance(ApplicationEnvironment::class, app.environment)
            @Suppress("EXPERIMENTAL_API_USAGE")
            instance(ApplicationConfig::class, app.environment.config)

            // configuration
            instance(AppConfig::class, configuration)

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
            theBase()
            gameOfThrones(configuration.gameOfThrones)
            semanticUi()
            formDemos()

        }
    }
}
