package de.peekandpoke.modules.cms.db

import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.aql.ASC
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.karango.vault.EntityRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.modules.cms.domain.CmsSnippet
import de.peekandpoke.modules.cms.domain.name
import de.peekandpoke.ultra.common.kType
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.Stored
import de.peekandpoke.ultra.vault.hooks.WithTimestamps
import de.peekandpoke.ultra.vault.hooks.WithUserRecord

val Database.cmsSnippets get() = getRepository<CmsSnippetsRepository>()

val CmsSnippets = EntityCollection<CmsSnippet>("cms_snippets", kType())

@WithTimestamps
@WithUserRecord
class CmsSnippetsRepository(driver: KarangoDriver) : EntityRepository<CmsSnippet>(driver, CmsSnippets) {

    fun findAllSorted(): Cursor<Stored<CmsSnippet>> = find {

        FOR(coll) { snippet ->
            SORT(snippet.name.ASC)
            RETURN(snippet)
        }
    }
}
