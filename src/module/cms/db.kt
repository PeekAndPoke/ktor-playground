package de.peekandpoke.module.cms

import de.peekandpoke.karango.*
import de.peekandpoke.karango.aql.*

fun Db.Builder.registerCmsCollections() {
    addEntityCollection { db -> CmsPagesCollection(db) }
}

internal val Db.cmsPages get() = getEntityCollection<CmsPagesCollection>()

val CmsPages = EntityCollection<CmsPage>("cms_pages", type())

class CmsPagesCollection(db: Db) : DbEntityCollection<CmsPage>(db, CmsPages) {

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
