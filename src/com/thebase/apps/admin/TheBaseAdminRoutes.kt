package com.thebase.apps.admin

import com.thebase.domain.Organisation
import com.thebase.domain.ResidentialProperty
import de.peekandpoke.ktorfx.broker.OutgoingConverter
import de.peekandpoke.ktorfx.broker.Routes
import de.peekandpoke.ultra.vault.Stored

class TheBaseAdminRoutes(converter: OutgoingConverter) : Routes(converter, "") {

    val index = route("/")

    //////////////////////////////////////

    val installFixtures = route("/install-fixtures")

    //////////////////////////////////////

    data class ListResidentialProperties(val org: Stored<Organisation>)

    val listResidentialProperties = route(ListResidentialProperties::class, "/{org}/properties")

    fun listResidentialProperties(org: Stored<Organisation>) = listResidentialProperties(ListResidentialProperties(org))

    //////////////////////////////////////

    data class ListRentableRooms(val org: Stored<Organisation>, val property: Stored<ResidentialProperty>)

    val listRentableRooms = route(ListRentableRooms::class, "/{org}/{property}/rooms")

    fun listRentableRooms(property: Stored<ResidentialProperty>) = listRentableRooms(
        ListRentableRooms(property.value.organisation.asStored, property)
    )
}
