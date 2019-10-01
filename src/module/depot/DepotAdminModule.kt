package de.peekandpoke.module.depot

import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import de.peekandpoke.ktorfx.templating.defaultTemplate
import de.peekandpoke.module.depot.views.buckets
import de.peekandpoke.module.depot.views.files
import de.peekandpoke.module.depot.views.index
import de.peekandpoke.module.depot.views.repositories
import de.peekandpoke.ultra.depot.Depot
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.html.respondHtmlTemplate
import io.ktor.http.ContentDisposition
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.response.header
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.util.pipeline.PipelineContext

val DepotAdminModule = module {

    // config
    config("depotAdminMountPoint", "/depot")

    singleton(DepotAdminRoutes::class)
    singleton(DepotAdmin::class)
}

class DepotAdminRoutes(converter: OutgoingConverter, depotAdminMountPoint: String) : Routes(converter, depotAdminMountPoint) {

    val index = route("")

    val repositories = route("/repositories")

    data class GetRepository(val repository: String)

    val getRepository = route(GetRepository::class, "/repositories/{repository}")
    fun getDriver(repository: String) = getRepository(GetRepository(repository))

    data class GetBucket(val repository: String, val bucket: String)

    val getBucket = route(GetBucket::class, "/repositories/{repository}/{bucket}")
    fun getBucket(repository: String, bucket: String) = getBucket(GetBucket(repository, bucket))

    data class GetFile(val repository: String, val bucket: String, val file: String)

    val getFile = route(GetFile::class, "/repositories/{repository}/{bucket}/{file}")
    fun getFile(repository: String, bucket: String, file: String) = getFile(GetFile(repository, bucket, file))
}

class DepotAdmin(val routes: DepotAdminRoutes) {

    // TODO: move this one into ktorfx::templating
    private suspend fun PipelineContext<Unit, ApplicationCall>.respond(
        status: HttpStatusCode = HttpStatusCode.OK,
        body: SimpleTemplate.() -> Unit
    ) {
        call.respondHtmlTemplate(defaultTemplate, status, body)
    }

    // TODO: create helper method in ktorfx::depot
    val ApplicationCall.depot get() = kontainer.get(Depot::class)
    val PipelineContext<Unit, ApplicationCall>.depot get() = kontainer.get(Depot::class)

    fun mount(route: Route) = with(route) {


        get(routes.index) {

            respond {
                index()
            }
        }

        get(routes.repositories) {

            respond {
                repositories(this@DepotAdmin, depot.all())
            }
        }

        get(routes.getRepository) { data ->

            val repository = depot.get(data.repository)
            val bucketList = repository.listBuckets()

            respond {
                buckets(this@DepotAdmin, repository, bucketList)
            }

        }

        get(routes.getBucket) { data ->

            val repository = depot.get(data.repository)
            val bucket = repository.get(data.bucket)
            val fileList = bucket.listFiles()

            respond {
                files(this@DepotAdmin, repository, bucket, fileList)
            }

        }

        get(routes.getFile) { data ->

            val repository = depot.get(data.repository)
            val bucket = repository.get(data.bucket)
            val file = bucket.getFile(data.file)

            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, file.name).toString()
            )

            call.respond(file.getContentBytes())
        }
    }
}
