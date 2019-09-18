package de.peekandpoke.module.cms

import de.peekandpoke.karango.Karango
import de.peekandpoke.karango.addon.Timestamped
import de.peekandpoke.karango.addon.Timestamps
import de.peekandpoke.karango.addon.UserRecord
import de.peekandpoke.karango.addon.WithUserRecord
import de.peekandpoke.ultra.mutator.Mutable

@Karango
@Mutable
data class CmsPage(
    val name: String,
    val slug: String,
    val markup: String,
    override val _ts: Timestamps? = null,
    override val _userRecord: UserRecord? = null
) : Timestamped, WithUserRecord {

    companion object {
        fun empty() = CmsPage(name = "", slug = "", markup = "")
    }
}
