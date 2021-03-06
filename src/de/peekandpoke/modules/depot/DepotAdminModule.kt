package de.peekandpoke.modules.depot

import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.common.depot
import de.peekandpoke.ktorfx.templating.respond
import de.peekandpoke.modules.depot.views.buckets
import de.peekandpoke.modules.depot.views.files
import de.peekandpoke.modules.depot.views.index
import de.peekandpoke.modules.depot.views.repositories
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import io.ktor.application.call
import io.ktor.features.NotFoundException
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.util.KtorExperimentalAPI

fun KontainerBuilder.depotAdmin() = module(DepotAdminModule)

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

    @KtorExperimentalAPI
    fun Route.mount() {

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
            val bucketList = repository.listNewest()

            respond {
                buckets(this@DepotAdmin, repository, bucketList)
            }
        }

        get(routes.getBucket) { data ->

            val repository = depot.get(data.repository)
            val bucket = repository.get(data.bucket)
            val fileList = bucket.listNewest()

            respond {
                files(this@DepotAdmin, repository, bucket, fileList)
            }
        }

        get(routes.getFile) { data ->

            val repository = depot.get(data.repository)
            val bucket = repository.get(data.bucket)

//            call.response.header(
//                HttpHeaders.ContentDisposition,
//                ContentDisposition.Attachment.withParameter(ContentDisposition.Parameters.FileName, file.name).toString()
//            )

            when (val file = bucket.getFile(data.file)) {
                null -> throw NotFoundException()
                else -> call.respond(file.getContentBytes())
            }
        }
    }
}
