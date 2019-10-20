package de.peekandpoke.jointhebase.admin

import de.peekandpoke.AdminTemplate
import de.peekandpoke.jointhebase.admin.views.index
import de.peekandpoke.jointhebase.admin.views.listRentableRooms
import de.peekandpoke.jointhebase.admin.views.listResidentialProperties
import de.peekandpoke.jointhebase.db.organisations
import de.peekandpoke.jointhebase.db.rentableRooms
import de.peekandpoke.jointhebase.db.residentialProperties
import de.peekandpoke.jointhebase.fixtures.FixtureInstaller
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ultra.ktor_tools.database
import kotlinx.html.div

class JtbAdmin(val routes: JtbAdminRoutes) {

    fun Route.mount() {

        get(routes.index) {

            val organisations = database.organisations.findAll()

            respond<AdminTemplate> {
                index(organisations)
            }
        }

        get(routes.listResidentialProperties) { data ->

            val properties = database.residentialProperties.findByOrganisation(data.org)

            respond<AdminTemplate> {
                listResidentialProperties(properties)
            }
        }

        get(routes.listRentableRooms) { data ->

            val rooms = database.rentableRooms.findByResidentialProperty(data.property)

            respond<AdminTemplate> {
                listRentableRooms(rooms)
            }
        }

        get(routes.installFixtures) {

            val result = kontainer.use(FixtureInstaller::class) {
                install()
            }

            respond {
                content {

                    if (result == null) {
                        +"Fixture installer failed"
                    } else {
                        div {
                            +"Following fixtures where installed"
                        }
                        ui.list {
                            result.forEach {
                                ui.item {
                                    +(it::class.qualifiedName ?: "n/a")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
