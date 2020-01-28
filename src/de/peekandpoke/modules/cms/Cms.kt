package de.peekandpoke.modules.cms

import com.thebase._sortme_.karsten.ChildFinder
import de.peekandpoke.modules.cms.db.CmsPagesRepository
import de.peekandpoke.modules.cms.db.CmsSnippetsRepository
import de.peekandpoke.modules.cms.domain.*
import de.peekandpoke.ultra.vault.Ref
import de.peekandpoke.ultra.vault.Stored
import kotlin.reflect.KClass

class Cms(
    modules: List<Module>,
    val errorPages: CmsErrorPages,
    val markdown: SemanticMarkdown,
    private val pagesRepository: CmsPagesRepository,
    private val snippetsRepository: CmsSnippetsRepository
) {
    abstract class Module(
        val layouts: Map<KClass<out CmsLayout>, CmsLayout>,
        val elements: Map<KClass<out CmsElement>, CmsElement>
    )

    /** All available layouts */
    val layouts: Map<KClass<out CmsLayout>, CmsLayout> = modules.flatMap { it.layouts.toList() }.toMap()

    /** All available elements */
    val elements: Map<KClass<out CmsElement>, CmsElement> = modules.flatMap { it.elements.toList() }.toMap()

    /** All pages */
    private val allPages by lazy { pagesRepository.findAllSorted().toList() }

    /** Cache for links used in pages */
    private val linksInPage = mutableMapOf<CmsPage, List<Link>>()

    /** Cache for all snippets in pages */
    private val snippetsInPage = mutableMapOf<CmsPage, List<Ref<CmsSnippet>>>()

    /** All snippets */
    private val allSnippets by lazy { snippetsRepository.findAllSorted().toList() }

    /** Cache for links used in snippets */
    private val linksInSnippet = mutableMapOf<CmsSnippet, List<Link>>()

    /**
     * Get a layout by its class
     */
    fun getLayout(cls: String): CmsLayout {

        return layouts.toList()
            .filter { it.first.qualifiedName == cls }
            .map { it.second }
            .firstOrNull() ?: CmsLayout.Empty
    }

    /**
     * Get an element by its class
     */
    fun getElement(cls: String): CmsElement {

        return elements.toList()
            .filter { it.first.qualifiedName == cls }
            .map { it.second }
            .firstOrNull() ?: CmsElement.Empty
    }

    fun validate(): CmsValidity {

        val errors = mutableListOf<String>()

        // Check if all internal links are ok
        allPages.forEach { page ->
            val allLinks = page.value.getAllLinks()

            val unmatchedLinks = allLinks
                .filter { it.isInternalLink }
                .filter { allPages.none { page -> page.value.matchesUri(it.url) } }

            unmatchedLinks.forEach {
                errors.add("Page '${page.value.uri} (${page._key})' has a broken internal link to '${it.url}'")
            }
        }

        return CmsValidity(errors)
    }

    fun hasPageByUri(uri: String): Boolean {
        return pagesRepository.findByUri(uri) != null
    }

    @JvmName("canDeleteCmsPage")
    fun canDelete(subject: Stored<CmsPage>): Boolean {

        if (subject.value.isHomepage) {
            return false
        }

        // Check that all other page have NO link to the subject page
        val pagesOk = allPages.all { page ->
            page.value.getAllLinks().none {
                subject.value.matchesUri(it.url)
            }
        }

        // Check that all other snippets have NO link to the subject page
        val snippetsOk = allSnippets.all { snippet ->
            snippet.value.getAllLinks().none {
                subject.value.matchesUri(it.url)
            }
        }

        return pagesOk && snippetsOk
    }

    @JvmName("canDeleteCmsSnippet")
    fun canDelete(subject: Stored<CmsSnippet>): Boolean {
        return allPages.all { page ->
            page.value.getAllSnippets().none { it._id == subject._id }
        }
    }

    private fun CmsPage.getAllLinks() = linksInPage.getOrPut(this) {
        ChildFinder.find(Link::class, this)
    }

    private fun CmsPage.getAllSnippets(): List<Ref<CmsSnippet>> = snippetsInPage.getOrPut(this) {
        @Suppress("UNCHECKED_CAST")
        ChildFinder.find(Ref::class, this) { it.value is CmsSnippet } as List<Ref<CmsSnippet>>
    }

    private fun CmsSnippet.getAllLinks() = linksInSnippet.getOrPut(this) {
        ChildFinder.find(Link::class, this)
    }
}

