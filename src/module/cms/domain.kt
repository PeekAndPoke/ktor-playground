package de.peekandpoke.module.cms

import de.peekandpoke.karango.Karango
import de.peekandpoke.ultra.mutator.Mutable
import de.peekandpoke.ultra.vault.hooks.Timestamped
import de.peekandpoke.ultra.vault.hooks.Timestamps
import de.peekandpoke.ultra.vault.hooks.UserRecord
import de.peekandpoke.ultra.vault.hooks.WithUserRecord

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
