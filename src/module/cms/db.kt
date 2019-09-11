package de.peekandpoke.module.cms

import de.peekandpoke.karango.Cursor
import de.peekandpoke.karango.Db
import de.peekandpoke.karango.DbEntityCollection
import de.peekandpoke.karango.EntityCollection
import de.peekandpoke.karango.aql.EQ
import de.peekandpoke.karango.aql.FOR
import de.peekandpoke.karango.aql.type

fun Db.Builder.registerCmsCollections() {
    addEntityCollection { db -> CmsPagesCollection(db) }
}

internal val Db.cmsPages get() = getEntityCollection<CmsPagesCollection>()

val CmsPages = EntityCollection<CmsPage>("cms_pages", type())

class CmsPagesCollection(db: Db) : DbEntityCollection<CmsPage>(db, CmsPages) {

    fun findAllSorted(): Cursor<CmsPage> = db.query {

        FOR(coll) { page ->
            SORT(page.name)
            RETURN(page)
        }
    }

    fun findBySlug(path: String): CmsPage? = db.queryFirst {
        FOR(coll) { page ->
            FILTER(page.slug EQ path)
            RETURN(page)
        }
    }
}
