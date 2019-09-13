package de.peekandpoke.module.cms

import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.DbEntityCollection
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.aql.*

fun Db.Builder.registerCmsCollections() {
    addEntityCollection { db -> CmsPagesCollection(db) }
}

internal val Db.cmsPages get() = getEntityCollection<CmsPagesCollection>()

val CmsPages = EntityCollection<CmsPage>("cms_pages", type())

class CmsPagesCollection(db: Db) : DbEntityCollection<CmsPage>(db, CmsPages) {

    fun findAllSorted(): Cursor<CmsPage> = query {

        FOR(coll) { page ->
            SORT(page.name.ASC)
            RETURN(page)
        }
    }

    fun findBySlug(path: String): CmsPage? = queryFirst {

        FOR(coll) { page ->
            FILTER(page.slug EQ path)
            RETURN(page)
        }
    }
}
