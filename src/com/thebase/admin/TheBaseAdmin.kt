package com.thebase.admin

import com.thebase.admin.views.index
import com.thebase.admin.views.listRentableRooms
import com.thebase.admin.views.listResidentialProperties
import com.thebase.db.organisations
import com.thebase.db.rentableRooms
import com.thebase.db.residentialProperties
import com.thebase.fixtures.FixtureInstaller
import de.peekandpoke.AdminTemplate
import de.peekandpoke.ktorfx.broker.get
import de.peekandpoke.ktorfx.common.kontainer
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ultra.ktor_tools.database
import kotlinx.html.div

class TheBaseAdmin(val routes: TheBaseAdminRoutes) {

    fun Route.mount() {

        get(routes.index) {

            val organisations = database.organisations.findAll().toList()

            respond<AdminTemplate> {
                index(organisations)
            }
        }

        get(routes.listResidentialProperties) { data ->

            val properties = database.residentialProperties.findByOrganisation(data.org).toList()

            respond<AdminTemplate> {
                listResidentialProperties(properties)
            }
        }

        get(routes.listRentableRooms) { data ->

            val rooms = database.rentableRooms.findByResidentialProperty(data.property).toList()

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
