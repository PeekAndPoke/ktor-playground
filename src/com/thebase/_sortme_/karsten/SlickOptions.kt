package com.thebase._sortme_.karsten

import kotlinx.html.FlowContent
import kotlin.reflect.full.memberProperties

/**
 * See https://kenwheeler.github.io/slick for details
 */
fun FlowContent.slickOptions(
    slidesToShow: Int = 1,
    slidesToScroll: Int = 1,
    infinite: Boolean = true,
    dots: Boolean = true,
    appendDots: String? = null,
    variableWidth: Boolean = false,
    adaptiveHeight: Boolean = false,
    fade: Boolean = false,
    arrows: Boolean = true,
    autoplay: Boolean = false
) {
    val it = SlickOptions(
        slidesToShow = slidesToShow,
        slidesToScroll = slidesToScroll,
        infinite = infinite,
        dots = dots,
        appendDots = appendDots,
        variableWidth = variableWidth,
        adaptiveHeight = adaptiveHeight,
        fade = fade,
        arrows = arrows,
        autoplay = autoplay
    )

    attributes["data-slick"] = it.toJson()
}

data class SlickOptions(
    val slidesToShow: Int = 1,
    val slidesToScroll: Int = 1,
    val infinite: Boolean = true,
    val dots: Boolean = true,
    val appendDots: String? = null,
    val variableWidth: Boolean = false,
    val adaptiveHeight: Boolean = false,
    val fade: Boolean = false,
    val arrows: Boolean = false,
    val autoplay: Boolean = false
) {

    fun toJson(): String {
        val parts = SlickOptions::class.memberProperties
            .filter { it.get(this) != null }
            .map {

                val name = "\"${it.name}\""

                when (val value = it.getter(this)) {

                    is String -> "$name: \"${value.split("\"").joinToString("\\\"")}\""

                    else -> "$name: $value"
                }
            }

        return "{ ${parts.joinToString(", ")} }"
    }
}
