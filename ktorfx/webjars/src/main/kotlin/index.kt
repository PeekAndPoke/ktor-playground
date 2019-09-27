package de.peekandpoke.ktorfx.webjars

import io.ktor.application.ApplicationCall
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.ApplicationFeature
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.LastModifiedVersion
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.versions
import io.ktor.http.defaultForFilePath
import io.ktor.request.httpMethod
import io.ktor.request.uri
import io.ktor.response.respond
import io.ktor.util.AttributeKey
import io.ktor.util.KtorExperimentalAPI
import io.ktor.util.cio.KtorDefaultPool
import io.ktor.util.pipeline.PipelineContext
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.io.jvm.javaio.toByteReadChannel
import kotlinx.io.core.ExperimentalIoApi
import org.webjars.MultipleMatchesException
import org.webjars.WebJarAssetLocator
import java.io.InputStream
import java.nio.file.Paths
import java.time.ZoneId
import java.time.ZonedDateTime

@KtorExperimentalAPI
class BetterWebjars(private val configuration: Configuration) {

    private fun fileName(path: String): String = Paths.get(path).fileName?.toString() ?: ""

    private fun extractWebJar(path: String): String {
        val firstDelimiter = if (path.startsWith("/")) 1 else 0
        val nextDelimiter = path.indexOf("/", 1)
        val webjar = if (nextDelimiter > -1) path.substring(firstDelimiter, nextDelimiter) else ""
        val partialPath = path.substring(nextDelimiter + 1)

        return locator.getFullPath(webjar, partialPath)
    }

    private val locator = WebJarAssetLocator()
    private val lastModified = ZonedDateTime.now(configuration.zone)

    @KtorExperimentalAPI
    class Configuration {
        var path = "/vendor/"
            set(value) {
                val buffer = StringBuilder(value)
                if (!value.startsWith("/")) {
                    buffer.insert(0, "/")
                }
                if (!buffer.endsWith("/")) {
                    buffer.append("/")
                }
                field = buffer.toString()
            }

        var zone: ZoneId = ZoneId.systemDefault()

        var loader: ClassLoader = BetterWebjars::class.java.classLoader
    }

    private suspend fun intercept(context: PipelineContext<Unit, ApplicationCall>) {

        val fullPath = context.call.request.uri.split("?").first()
        val fileName = fileName(fullPath)

        if (fullPath.startsWith(configuration.path) && context.call.request.httpMethod == HttpMethod.Get && fileName.isNotEmpty()) {

            val resourcePath = fullPath.removePrefix(configuration.path)

            try {
                val location = extractWebJar(resourcePath)
                val inputStream = configuration.loader.getResourceAsStream(location)

                if (inputStream == null) {
                    context.call.respond(HttpStatusCode.NotFound)

                } else {
                    context.call.respond(
                        InputStreamContent(inputStream, ContentType.defaultForFilePath(fileName), lastModified)
                    )
                }

            } catch (multipleFiles: MultipleMatchesException) {
                context.call.respond(HttpStatusCode.InternalServerError)
            } catch (notFound: IllegalArgumentException) {
            }

            // important ... stop any other route matching etc.
            context.finish()
        }
    }

    @KtorExperimentalAPI
    companion object Feature : ApplicationFeature<ApplicationCallPipeline, Configuration, BetterWebjars> {

        override val key = AttributeKey<BetterWebjars>("BetterWebjars")

        override fun install(pipeline: ApplicationCallPipeline, configure: Configuration.() -> Unit): BetterWebjars {

            val configuration = Configuration().apply(configure)
            val feature = BetterWebjars(configuration)

            pipeline.intercept(ApplicationCallPipeline.Features) {
                feature.intercept(this)
            }

            return feature
        }

    }

}

private class InputStreamContent(
    val input: InputStream,
    override val contentType: ContentType,
    lastModified: ZonedDateTime
) : OutgoingContent.ReadChannelContent() {

    init {
        versions = versions.plus(LastModifiedVersion(lastModified))
    }

    @KtorExperimentalAPI
    @ExperimentalIoApi
    override fun readFrom(): ByteReadChannel = input.toByteReadChannel(pool = KtorDefaultPool)
}
