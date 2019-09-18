package de.peekandpoke.module.cms

import de.peekandpoke.karango.*
import de.peekandpoke.karango.aql.ASC
import de.peekandpoke.karango.aql.EQ
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.aql.RETURN
import de.peekandpoke.ultra.vault.Database
import de.peekandpoke.ultra.vault.Stored
import de.peekandpoke.ultra.vault.Vault
import de.peekandpoke.ultra.vault.type

fun Vault.Builder.registerCmsCollections() {
    add { CmsPagesRepository(it.get(karangoDefaultDriver)) }
}

internal val Database.cmsPages get() = getRepository<CmsPagesRepository>()

val CmsPages = EntityCollection<CmsPage>("cms_pages", type())

class CmsPagesRepository(driver: KarangoDriver) : EntityRepository<CmsPage>(driver, CmsPages) {

    fun findAllSorted(): Cursor<Stored<CmsPage>> = find {

        FOR(coll) { page ->
            SORT(page.name.ASC)
            RETURN(page)
        }
    }

    fun findBySlug(path: String): Stored<CmsPage>? = findFirst {

        FOR(coll) { page ->
            FILTER(page.slug EQ path)
            RETURN(page)
        }
    }
}
