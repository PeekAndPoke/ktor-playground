package de.peekandpoke.modules.cms.db

import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.aql.*
import de.peekandpoke.karango.vault.EntityRepository
import de.peekandpoke.karango.vault.KarangoDriver
import de.peekandpoke.modules.cms.domain.CmsPage
import de.peekandpoke.modules.cms.domain.name
import de.peekandpoke.modules.cms.domain.uri
import de.peekandpoke.ultra.common.kType
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.Stored
import de.peekandpoke.ultra.vault.hooks.WithTimestamps
import de.peekandpoke.ultra.vault.hooks.WithUserRecord

val Database.cmsPages get() = getRepository<CmsPagesRepository>()

val CmsPages = EntityCollection<CmsPage>("cms_pages", kType())

@WithTimestamps
@WithUserRecord
class CmsPagesRepository(driver: KarangoDriver) : EntityRepository<CmsPage>(driver, CmsPages) {

    fun findAllSorted(search: String = ""): Cursor<Stored<CmsPage>> = find {

        FOR(coll) { page ->

            if (search.isNotBlank()) {
                FILTER(CONTAINS(LOWER(page.name), LOWER(search.aql)))
            }

            SORT(page.name.ASC)
            RETURN(page)
        }
    }

    fun findByUri(path: String): Stored<CmsPage>? = findFirst {

        FOR(coll) { page ->
            FILTER(page.uri EQ path.trimStart('/').trim())
            RETURN(page)
        }
    }
}
