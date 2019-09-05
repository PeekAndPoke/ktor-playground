package de.peekandpoke.module.semanticui.views

import de.peekandpoke.module.semanticui.Template
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.util.KtorExperimentalAPI
import io.ultra.ktor_tools.prismjs.Lang
import io.ultra.ktor_tools.prismjs.prism
import io.ultra.ktor_tools.semanticui.icon
import io.ultra.ktor_tools.semanticui.ui
import kotlinx.html.*

@Suppress("DuplicatedCode")
@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
internal fun Template.icons() {

    content {

        style(
            "text/css",
            """
                i.icon {
                    font-size: 2em;
                    margin: 0em auto 0.25em;
                    display: block;
                }
            """.trimIndent()
        )

        ui.vertical.basic.segment {
            h1 { +"Icon" }
            p {
                +"An icon is a glyph used to represent something else. Click for "
                a(href = "https://semantic-ui.com/elements/icon.html", target = "_blank") { +"Details" }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"General usage" }

        ui.three.column.grid {

            ui.row {
                ui.column {
                    +"All icons are available through the "
                    b { +"icon" }
                    +" helper"
                }
                ui.column {
                    icon.question_circle()
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                        icon.question_circle()
                    """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column {
                    +"Some icons have an "
                    b { +"outline" }
                    +" version"
                }
                ui.column {
                    icon.question_circle_outline()
                }
                ui.column {
                    prism(Lang.Kotlin) {
                        """
                            icon.question_circle_outline()
                        """.trimIndent()
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Accessibility" }

        ui.ten.column.grid {
            ui.column {
                icon.american_sign_language_interpreting()
                +"american sign language interpreting"
            }

            ui.column {
                icon.assistive_listening_systems()
                +"assistive listening systems"
            }

            ui.column {
                icon.audio_description()
                +"audio description"
            }

            ui.column {
                icon.blind()
                +"blind"
            }

            ui.column {
                icon.braille()
                +"braille"
            }

            ui.column {
                icon.closed_captioning()
                +"closed captioning"
            }

            ui.column {
                icon.closed_captioning_outline()
                +"closed captioning outline"
            }

            ui.column {
                icon.deaf()
                +"deaf"
            }

            ui.column {
                icon.low_vision()
                +"low vision"
            }

            ui.column {
                icon.phone_volume()
                +"phone volume"
            }

            ui.column {
                icon.question_circle()
                +"question circle"
            }

            ui.column {
                icon.question_circle_outline()
                +"question circle outline"
            }

            ui.column {
                icon.sign_language()
                +"sign language"
            }

            ui.column {
                icon.tty()
                +"tty"
            }

            ui.column {
                icon.universal_access()
                +"universal access"
            }

            ui.column {
                icon.wheelchair()
                +"wheelchair"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Arrows" }

        ui.ten.column.grid {

            ui.column {
                icon.angle_double_down()
                +"angle double down"
            }

            ui.column {
                icon.angle_double_left()
                +"angle double left"
            }

            ui.column {
                icon.angle_double_right()
                +"angle double right"
            }

            ui.column {
                icon.angle_double_up()
                +"angle double up"
            }

            ui.column {
                icon.angle_down()
                +"angle down"
            }

            ui.column {
                icon.angle_left()
                +"angle left"
            }

            ui.column {
                icon.angle_right()
                +"angle right"
            }

            ui.column {
                icon.angle_up()
                +"angle up"
            }

            ui.column {
                icon.arrow_alternate_circle_down()
                +"arrow alternate circle down"
            }

            ui.column {
                icon.arrow_alternate_circle_down_outline()
                +"arrow alternate circle down outline"
            }

            ui.column {
                icon.arrow_alternate_circle_left()
                +"arrow alternate circle left"
            }

            ui.column {
                icon.arrow_alternate_circle_left_outline()
                +"arrow alternate circle left outline"
            }

            ui.column {
                icon.arrow_alternate_circle_right()
                +"arrow alternate circle right"
            }

            ui.column {
                icon.arrow_alternate_circle_right_outline()
                +"arrow alternate circle right outline"
            }

            ui.column {
                icon.arrow_alternate_circle_up()
                +"arrow alternate circle up"
            }

            ui.column {
                icon.arrow_alternate_circle_up_outline()
                +"arrow alternate circle up outline"
            }

            ui.column {
                icon.arrow_circle_down()
                +"arrow circle down"
            }

            ui.column {
                icon.arrow_circle_left()
                +"arrow circle left"
            }

            ui.column {
                icon.arrow_circle_right()
                +"arrow circle right"
            }

            ui.column {
                icon.arrow_circle_up()
                +"arrow circle up"
            }

            ui.column {
                icon.arrow_down()
                +"arrow down"
            }

            ui.column {
                icon.arrow_left()
                +"arrow left"
            }

            ui.column {
                icon.arrow_right()
                +"arrow right"
            }

            ui.column {
                icon.arrow_up()
                +"arrow up"
            }

            ui.column {
                icon.arrows_alternate()
                +"arrows alternate"
            }

            ui.column {
                icon.arrows_alternate_horizontal()
                +"arrows alternate horizontal"
            }

            ui.column {
                icon.arrows_alternate_vertical()
                +"arrows alternate vertical"
            }

            ui.column {
                icon.caret_down()
                +"caret down"
            }

            ui.column {
                icon.caret_left()
                +"caret left"
            }

            ui.column {
                icon.caret_right()
                +"caret right"
            }

            ui.column {
                icon.caret_square_down()
                +"caret square down"
            }

            ui.column {
                icon.caret_square_down_outline()
                +"caret square down outline"
            }

            ui.column {
                icon.caret_square_left()
                +"caret square left"
            }

            ui.column {
                icon.caret_square_left_outline()
                +"caret square left outline"
            }

            ui.column {
                icon.caret_square_right()
                +"caret square right"
            }

            ui.column {
                icon.caret_square_right_outline()
                +"caret square right outline"
            }

            ui.column {
                icon.caret_square_up()
                +"caret square up"
            }

            ui.column {
                icon.caret_square_up_outline()
                +"caret square up outline"
            }

            ui.column {
                icon.caret_up()
                +"caret up"
            }

            ui.column {
                icon.cart_arrow_down()
                +"cart arrow down"
            }

            ui.column {
                icon.chart_line()
                +"chart line"
            }

            ui.column {
                icon.chevron_circle_down()
                +"chevron circle down"
            }

            ui.column {
                icon.chevron_circle_left()
                +"chevron circle left"
            }

            ui.column {
                icon.chevron_circle_right()
                +"chevron circle right"
            }

            ui.column {
                icon.chevron_circle_up()
                +"chevron circle up"
            }

            ui.column {
                icon.chevron_down()
                +"chevron down"
            }

            ui.column {
                icon.chevron_left()
                +"chevron left"
            }

            ui.column {
                icon.chevron_right()
                +"chevron right"
            }

            ui.column {
                icon.chevron_up()
                +"chevron up"
            }

            ui.column {
                icon.cloud_download()
                +"cloud download"
            }

            ui.column {
                icon.cloud_upload()
                +"cloud upload"
            }

            ui.column {
                icon.download()
                +"download"
            }

            ui.column {
                icon.exchange_alternate()
                +"exchange alternate"
            }

            ui.column {
                icon.expand_arrows_alternate()
                +"expand arrows alternate"
            }

            ui.column {
                icon.external_alternate()
                +"external alternate"
            }

            ui.column {
                icon.external_square_alternate()
                +"external square alternate"
            }

            ui.column {
                icon.hand_point_down()
                +"hand point down"
            }

            ui.column {
                icon.hand_point_down_outline()
                +"hand point down outline"
            }

            ui.column {
                icon.hand_point_left()
                +"hand point left"
            }

            ui.column {
                icon.hand_point_left_outline()
                +"hand point left outline"
            }

            ui.column {
                icon.hand_point_right()
                +"hand point right"
            }

            ui.column {
                icon.hand_point_right_outline()
                +"hand point right outline"
            }

            ui.column {
                icon.hand_point_up()
                +"hand point up"
            }

            ui.column {
                icon.hand_point_up_outline()
                +"hand point up outline"
            }

            ui.column {
                icon.hand_pointer()
                +"hand pointer"
            }

            ui.column {
                icon.hand_pointer_outline()
                +"hand pointer outline"
            }

            ui.column {
                icon.history()
                +"history"
            }

            ui.column {
                icon.level_down_alternate()
                +"level down alternate"
            }

            ui.column {
                icon.level_up_alternate()
                +"level up alternate"
            }

            ui.column {
                icon.location_arrow()
                +"location arrow"
            }

            ui.column {
                icon.long_arrow_alternate_down()
                +"long arrow alternate down"
            }

            ui.column {
                icon.long_arrow_alternate_left()
                +"long arrow alternate left"
            }

            ui.column {
                icon.long_arrow_alternate_right()
                +"long arrow alternate right"
            }

            ui.column {
                icon.long_arrow_alternate_up()
                +"long arrow alternate up"
            }

            ui.column {
                icon.mouse_pointer()
                +"mouse pointer"
            }

            ui.column {
                icon.play()
                +"play"
            }

            ui.column {
                icon.random()
                +"random"
            }

            ui.column {
                icon.recycle()
                +"recycle"
            }

            ui.column {
                icon.redo()
                +"redo"
            }

            ui.column {
                icon.redo_alternate()
                +"redo alternate"
            }

            ui.column {
                icon.reply()
                +"reply"
            }

            ui.column {
                icon.reply_all()
                +"reply all"
            }

            ui.column {
                icon.retweet()
                +"retweet"
            }

            ui.column {
                icon.share()
                +"share"
            }

            ui.column {
                icon.share_square()
                +"share square"
            }

            ui.column {
                icon.share_square_outline()
                +"share square outline"
            }

            ui.column {
                icon.sign_in_alternate()
                +"sign in alternate"
            }

            ui.column {
                icon.sign_out_alternate()
                +"sign out alternate"
            }

            ui.column {
                icon.sort()
                +"sort"
            }

            ui.column {
                icon.sort_alphabet_down()
                +"sort alphabet down"
            }

            ui.column {
                icon.sort_alphabet_up()
                +"sort alphabet up"
            }

            ui.column {
                icon.sort_amount_down()
                +"sort amount down"
            }

            ui.column {
                icon.sort_amount_up()
                +"sort amount up"
            }

            ui.column {
                icon.sort_down()
                +"sort down"
            }

            ui.column {
                icon.sort_numeric_down()
                +"sort numeric down"
            }

            ui.column {
                icon.sort_numeric_up()
                +"sort numeric up"
            }

            ui.column {
                icon.sort_up()
                +"sort up"
            }

            ui.column {
                icon.sync()
                +"sync"
            }

            ui.column {
                icon.sync_alternate()
                +"sync alternate"
            }

            ui.column {
                icon.text_height()
                +"text height"
            }

            ui.column {
                icon.text_width()
                +"text width"
            }

            ui.column {
                icon.undo()
                +"undo"
            }

            ui.column {
                icon.undo_alternate()
                +"undo alternate"
            }

            ui.column {
                icon.upload()
                +"upload"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Audio and Video" }

        ui.ten.column.grid {
            ui.column {
                icon.audio_description()
                +"audio description"
            }

            ui.column {
                icon.backward()
                +"backward"
            }

            ui.column {
                icon.circle()
                +"circle"
            }

            ui.column {
                icon.circle_outline()
                +"circle outline"
            }

            ui.column {
                icon.closed_captioning()
                +"closed captioning"
            }

            ui.column {
                icon.closed_captioning_outline()
                +"closed captioning outline"
            }

            ui.column {
                icon.compress()
                +"compress"
            }

            ui.column {
                icon.eject()
                +"eject"
            }

            ui.column {
                icon.expand()
                +"expand"
            }

            ui.column {
                icon.expand_arrows_alternate()
                +"expand arrows alternate"
            }

            ui.column {
                icon.fast_backward()
                +"fast backward"
            }

            ui.column {
                icon.fast_forward()
                +"fast forward"
            }

            ui.column {
                icon.file_audio()
                +"file audio"
            }

            ui.column {
                icon.file_audio_outline()
                +"file audio outline"
            }

            ui.column {
                icon.file_video()
                +"file video"
            }

            ui.column {
                icon.file_video_outline()
                +"file video outline"
            }

            ui.column {
                icon.film()
                +"film"
            }

            ui.column {
                icon.forward()
                +"forward"
            }

            ui.column {
                icon.headphones()
                +"headphones"
            }

            ui.column {
                icon.microphone()
                +"microphone"
            }

            ui.column {
                icon.microphone_slash()
                +"microphone slash"
            }

            ui.column {
                icon.music()
                +"music"
            }

            ui.column {
                icon.pause()
                +"pause"
            }

            ui.column {
                icon.pause_circle()
                +"pause circle"
            }

            ui.column {
                icon.pause_circle_outline()
                +"pause circle outline"
            }

            ui.column {
                icon.phone_volume()
                +"phone volume"
            }

            ui.column {
                icon.play()
                +"play"
            }

            ui.column {
                icon.play_circle()
                +"play circle"
            }

            ui.column {
                icon.play_circle_outline()
                +"play circle outline"
            }

            ui.column {
                icon.podcast()
                +"podcast"
            }

            ui.column {
                icon.random()
                +"random"
            }

            ui.column {
                icon.redo()
                +"redo"
            }

            ui.column {
                icon.redo_alternate()
                +"redo alternate"
            }

            ui.column {
                icon.rss()
                +"rss"
            }

            ui.column {
                icon.rss_square()
                +"rss square"
            }

            ui.column {
                icon.step_backward()
                +"step backward"
            }

            ui.column {
                icon.step_forward()
                +"step forward"
            }

            ui.column {
                icon.stop()
                +"stop"
            }

            ui.column {
                icon.stop_circle()
                +"stop circle"
            }

            ui.column {
                icon.stop_circle_outline()
                +"stop circle outline"
            }

            ui.column {
                icon.sync()
                +"sync"
            }

            ui.column {
                icon.sync_alternate()
                +"sync alternate"
            }

            ui.column {
                icon.undo()
                +"undo"
            }

            ui.column {
                icon.undo_alternate()
                +"undo alternate"
            }

            ui.column {
                icon.video()
                +"video"
            }

            ui.column {
                icon.volume_down()
                +"volume down"
            }

            ui.column {
                icon.volume_off()
                +"volume off"
            }

            ui.column {
                icon.volume_up()
                +"volume up"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Business" }

        ui.ten.column.grid {
            ui.column {
                icon.address_book()
                +"address book"
            }

            ui.column {
                icon.address_book_outline()
                +"address book outline"
            }

            ui.column {
                icon.address_card()
                +"address card"
            }

            ui.column {
                icon.address_card_outline()
                +"address card outline"
            }

            ui.column {
                icon.archive()
                +"archive"
            }

            ui.column {
                icon.balance_scale()
                +"balance scale"
            }

            ui.column {
                icon.birthday_cake()
                +"birthday cake"
            }

            ui.column {
                icon.book()
                +"book"
            }

            ui.column {
                icon.briefcase()
                +"briefcase"
            }

            ui.column {
                icon.building()
                +"building"
            }

            ui.column {
                icon.building_outline()
                +"building outline"
            }

            ui.column {
                icon.bullhorn()
                +"bullhorn"
            }

            ui.column {
                icon.bullseye()
                +"bullseye"
            }

            ui.column {
                icon.calculator()
                +"calculator"
            }

            ui.column {
                icon.calendar()
                +"calendar"
            }

            ui.column {
                icon.calendar_outline()
                +"calendar outline"
            }

            ui.column {
                icon.calendar_alternate()
                +"calendar alternate"
            }

            ui.column {
                icon.calendar_alternate_outline()
                +"calendar alternate outline"
            }

            ui.column {
                icon.certificate()
                +"certificate"
            }

            ui.column {
                icon.chart_area()
                +"chart area"
            }

            ui.column {
                icon.chart_bar()
                +"chart bar"
            }

            ui.column {
                icon.chart_bar_outline()
                +"chart bar outline"
            }

            ui.column {
                icon.chart_line()
                +"chart line"
            }

            ui.column {
                icon.chart_pie()
                +"chart pie"
            }

            ui.column {
                icon.clipboard()
                +"clipboard"
            }

            ui.column {
                icon.clipboard_outline()
                +"clipboard outline"
            }

            ui.column {
                icon.coffee()
                +"coffee"
            }

            ui.column {
                icon.columns()
                +"columns"
            }

            ui.column {
                icon.compass()
                +"compass"
            }

            ui.column {
                icon.compass_outline()
                +"compass outline"
            }

            ui.column {
                icon.copy()
                +"copy"
            }

            ui.column {
                icon.copy_outline()
                +"copy outline"
            }

            ui.column {
                icon.copyright()
                +"copyright"
            }

            ui.column {
                icon.copyright_outline()
                +"copyright outline"
            }

            ui.column {
                icon.cut()
                +"cut"
            }

            ui.column {
                icon.edit()
                +"edit"
            }

            ui.column {
                icon.edit_outline()
                +"edit outline"
            }

            ui.column {
                icon.envelope()
                +"envelope"
            }

            ui.column {
                icon.envelope_outline()
                +"envelope outline"
            }

            ui.column {
                icon.envelope_open()
                +"envelope open"
            }

            ui.column {
                icon.envelope_open_outline()
                +"envelope open outline"
            }

            ui.column {
                icon.envelope_square()
                +"envelope square"
            }

            ui.column {
                icon.eraser()
                +"eraser"
            }

            ui.column {
                icon.fax()
                +"fax"
            }

            ui.column {
                icon.file()
                +"file"
            }

            ui.column {
                icon.file_outline()
                +"file outline"
            }

            ui.column {
                icon.file_alternate()
                +"file alternate"
            }

            ui.column {
                icon.file_alternate_outline()
                +"file alternate outline"
            }

            ui.column {
                icon.folder()
                +"folder"
            }

            ui.column {
                icon.folder_outline()
                +"folder outline"
            }

            ui.column {
                icon.folder_open()
                +"folder open"
            }

            ui.column {
                icon.folder_open_outline()
                +"folder open outline"
            }

            ui.column {
                icon.globe()
                +"globe"
            }

            ui.column {
                icon.industry()
                +"industry"
            }

            ui.column {
                icon.paperclip()
                +"paperclip"
            }

            ui.column {
                icon.paste()
                +"paste"
            }

            ui.column {
                icon.pen_square()
                +"pen square"
            }

            ui.column {
                icon.pencil_alternate()
                +"pencil alternate"
            }

            ui.column {
                icon.percent()
                +"percent"
            }

            ui.column {
                icon.phone()
                +"phone"
            }

            ui.column {
                icon.phone_square()
                +"phone square"
            }

            ui.column {
                icon.phone_volume()
                +"phone volume"
            }

            ui.column {
                icon.registered()
                +"registered"
            }

            ui.column {
                icon.registered_outline()
                +"registered outline"
            }

            ui.column {
                icon.save()
                +"save"
            }

            ui.column {
                icon.save_outline()
                +"save outline"
            }

            ui.column {
                icon.sitemap()
                +"sitemap"
            }

            ui.column {
                icon.sticky_note()
                +"sticky note"
            }

            ui.column {
                icon.sticky_note_outline()
                +"sticky note outline"
            }

            ui.column {
                icon.suitcase()
                +"suitcase"
            }

            ui.column {
                icon.table()
                +"table"
            }

            ui.column {
                icon.tag()
                +"tag"
            }

            ui.column {
                icon.tags()
                +"tags"
            }

            ui.column {
                icon.tasks()
                +"tasks"
            }

            ui.column {
                icon.thumbtack()
                +"thumbtack"
            }

            ui.column {
                icon.trademark()
                +"trademark"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Code" }

        ui.ten.column.grid {
            ui.column {
                icon.archive()
                +"archive"
            }

            ui.column {
                icon.barcode()
                +"barcode"
            }

            ui.column {
                icon.bath()
                +"bath"
            }

            ui.column {
                icon.bug()
                +"bug"
            }

            ui.column {
                icon.code()
                +"code"
            }

            ui.column {
                icon.code_branch()
                +"code branch"
            }

            ui.column {
                icon.coffee()
                +"coffee"
            }

            ui.column {
                icon.file()
                +"file"
            }

            ui.column {
                icon.file_outline()
                +"file outline"
            }

            ui.column {
                icon.file_alternate()
                +"file alternate"
            }

            ui.column {
                icon.file_alternate_outline()
                +"file alternate outline"
            }

            ui.column {
                icon.file_code()
                +"file code"
            }

            ui.column {
                icon.file_code_outline()
                +"file code outline"
            }

            ui.column {
                icon.filter()
                +"filter"
            }

            ui.column {
                icon.fire_extinguisher()
                +"fire extinguisher"
            }

            ui.column {
                icon.folder()
                +"folder"
            }

            ui.column {
                icon.folder_outline()
                +"folder outline"
            }

            ui.column {
                icon.folder_open()
                +"folder open"
            }

            ui.column {
                icon.folder_open_outline()
                +"folder open outline"
            }

            ui.column {
                icon.keyboard()
                +"keyboard"
            }

            ui.column {
                icon.keyboard_outline()
                +"keyboard outline"
            }

            ui.column {
                icon.microchip()
                +"microchip"
            }

            ui.column {
                icon.qrcode()
                +"qrcode"
            }

            ui.column {
                icon.shield_alternate()
                +"shield alternate"
            }

            ui.column {
                icon.sitemap()
                +"sitemap"
            }

            ui.column {
                icon.terminal()
                +"terminal"
            }

            ui.column {
                icon.user_secret()
                +"user secret"
            }

            ui.column {
                icon.window_close()
                +"window close"
            }

            ui.column {
                icon.window_close_outline()
                +"window close outline"
            }

            ui.column {
                icon.window_maximize()
                +"window maximize"
            }

            ui.column {
                icon.window_maximize_outline()
                +"window maximize outline"
            }

            ui.column {
                icon.window_minimize()
                +"window minimize"
            }

            ui.column {
                icon.window_minimize_outline()
                +"window minimize outline"
            }

            ui.column {
                icon.window_restore()
                +"window restore"
            }

            ui.column {
                icon.window_restore_outline()
                +"window restore outline"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Communication" }

        ui.ten.column.grid {
            ui.column {
                icon.address_book()
                +"address book"
            }

            ui.column {
                icon.address_book_outline()
                +"address book outline"
            }

            ui.column {
                icon.address_card()
                +"address card"
            }

            ui.column {
                icon.address_card_outline()
                +"address card outline"
            }

            ui.column {
                icon.american_sign_language_interpreting()
                +"american sign language interpreting"
            }

            ui.column {
                icon.assistive_listening_systems()
                +"assistive listening systems"
            }

            ui.column {
                icon.at()
                +"at"
            }

            ui.column {
                icon.bell()
                +"bell"
            }

            ui.column {
                icon.bell_outline()
                +"bell outline"
            }

            ui.column {
                icon.bell_slash()
                +"bell slash"
            }

            ui.column {
                icon.bell_slash_outline()
                +"bell slash outline"
            }

            ui.column {
                icon.bullhorn()
                +"bullhorn"
            }

            ui.column {
                icon.comment()
                +"comment"
            }

            ui.column {
                icon.comment_outline()
                +"comment outline"
            }

            ui.column {
                icon.comment_alternate()
                +"comment alternate"
            }

            ui.column {
                icon.comment_alternate_outline()
                +"comment alternate outline"
            }

            ui.column {
                icon.comments()
                +"comments"
            }

            ui.column {
                icon.comments_outline()
                +"comments outline"
            }

            ui.column {
                icon.envelope()
                +"envelope"
            }

            ui.column {
                icon.envelope_outline()
                +"envelope outline"
            }

            ui.column {
                icon.envelope_open()
                +"envelope open"
            }

            ui.column {
                icon.envelope_open_outline()
                +"envelope open outline"
            }

            ui.column {
                icon.envelope_square()
                +"envelope square"
            }

            ui.column {
                icon.fax()
                +"fax"
            }

            ui.column {
                icon.inbox()
                +"inbox"
            }

            ui.column {
                icon.language()
                +"language"
            }

            ui.column {
                icon.microphone()
                +"microphone"
            }

            ui.column {
                icon.microphone_slash()
                +"microphone slash"
            }

            ui.column {
                icon.mobile()
                +"mobile"
            }

            ui.column {
                icon.mobile_alternate()
                +"mobile alternate"
            }

            ui.column {
                icon.paper_plane()
                +"paper plane"
            }

            ui.column {
                icon.paper_plane_outline()
                +"paper plane outline"
            }

            ui.column {
                icon.phone()
                +"phone"
            }

            ui.column {
                icon.phone_square()
                +"phone square"
            }

            ui.column {
                icon.phone_volume()
                +"phone volume"
            }

            ui.column {
                icon.rss()
                +"rss"
            }

            ui.column {
                icon.rss_square()
                +"rss square"
            }

            ui.column {
                icon.tty()
                +"tty"
            }

            ui.column {
                icon.wifi()
                +"wifi"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Computers" }

        ui.ten.column.grid {

            ui.column {
                icon.desktop()
                +"desktop"
            }

            ui.column {
                icon.download()
                +"download"
            }

            ui.column {
                icon.hdd()
                +"hdd"
            }

            ui.column {
                icon.hdd_outline()
                +"hdd outline"
            }

            ui.column {
                icon.headphones()
                +"headphones"
            }

            ui.column {
                icon.keyboard()
                +"keyboard"
            }

            ui.column {
                icon.keyboard_outline()
                +"keyboard outline"
            }

            ui.column {
                icon.laptop()
                +"laptop"
            }

            ui.column {
                icon.microchip()
                +"microchip"
            }

            ui.column {
                icon.mobile()
                +"mobile"
            }

            ui.column {
                icon.mobile_alternate()
                +"mobile alternate"
            }

            ui.column {
                icon.plug()
                +"plug"
            }

            ui.column {
                icon.power_off()
                +"power off"
            }

            ui.column {
                icon.print()
                +"print"
            }

            ui.column {
                icon.save()
                +"save"
            }

            ui.column {
                icon.save_outline()
                +"save outline"
            }

            ui.column {
                icon.server()
                +"server"
            }

            ui.column {
                icon.tablet()
                +"tablet"
            }

            ui.column {
                icon.tablet_alternate()
                +"tablet alternate"
            }

            ui.column {
                icon.tv()
                +"tv"
            }

            ui.column {
                icon.upload()
                +"upload"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Currency" }

        ui.ten.column.grid {
            ui.column {
                icon.dollar_sign()
                +"dollar sign"
            }

            ui.column {
                icon.euro_sign()
                +"euro sign"
            }

            ui.column {
                icon.lira_sign()
                +"lira sign"
            }

            ui.column {
                icon.money_bill_alternate()
                +"money bill alternate"
            }

            ui.column {
                icon.money_bill_alternate_outline()
                +"money bill alternate outline"
            }

            ui.column {
                icon.pound_sign()
                +"pound sign"
            }

            ui.column {
                icon.ruble_sign()
                +"ruble sign"
            }

            ui.column {
                icon.rupee_sign()
                +"rupee sign"
            }

            ui.column {
                icon.shekel_sign()
                +"shekel sign"
            }

            ui.column {
                icon.won_sign()
                +"won sign"
            }

            ui.column {
                icon.yen_sign()
                +"yen sign"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Date & Time" }

        ui.ten.column.grid {

            ui.column {
                icon.bell()
                +"bell"
            }

            ui.column {
                icon.bell_outline()
                +"bell outline"
            }

            ui.column {
                icon.bell_slash()
                +"bell slash"
            }

            ui.column {
                icon.bell_slash_outline()
                +"bell slash outline"
            }

            ui.column {
                icon.calendar()
                +"calendar"
            }

            ui.column {
                icon.calendar_outline()
                +"calendar outline"
            }

            ui.column {
                icon.calendar_alternate()
                +"calendar alternate"
            }

            ui.column {
                icon.calendar_alternate_outline()
                +"calendar alternate outline"
            }

            ui.column {
                icon.calendar_check()
                +"calendar check"
            }

            ui.column {
                icon.calendar_check_outline()
                +"calendar check outline"
            }

            ui.column {
                icon.calendar_minus()
                +"calendar minus"
            }

            ui.column {
                icon.calendar_minus_outline()
                +"calendar minus outline"
            }

            ui.column {
                icon.calendar_plus()
                +"calendar plus"
            }

            ui.column {
                icon.calendar_plus_outline()
                +"calendar plus outline"
            }

            ui.column {
                icon.calendar_times()
                +"calendar times"
            }

            ui.column {
                icon.calendar_times_outline()
                +"calendar times outline"
            }

            ui.column {
                icon.clock()
                +"clock"
            }

            ui.column {
                icon.clock_outline()
                +"clock outline"
            }

            ui.column {
                icon.hourglass()
                +"hourglass"
            }

            ui.column {
                icon.hourglass_outline()
                +"hourglass outline"
            }

            ui.column {
                icon.hourglass_end()
                +"hourglass end"
            }

            ui.column {
                icon.hourglass_half()
                +"hourglass half"
            }

            ui.column {
                icon.hourglass_start()
                +"hourglass start"
            }

            ui.column {
                icon.stopwatch()
                +"stopwatch"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Design" }

        ui.ten.column.grid {
            ui.column {
                icon.adjust()
                +"adjust"
            }

            ui.column {
                icon.clone()
                +"clone"
            }

            ui.column {
                icon.clone_outline()
                +"clone outline"
            }

            ui.column {
                icon.copy()
                +"copy"
            }

            ui.column {
                icon.copy_outline()
                +"copy outline"
            }

            ui.column {
                icon.crop()
                +"crop"
            }

            ui.column {
                icon.crosshairs()
                +"crosshairs"
            }

            ui.column {
                icon.cut()
                +"cut"
            }

            ui.column {
                icon.edit()
                +"edit"
            }

            ui.column {
                icon.edit_outline()
                +"edit outline"
            }

            ui.column {
                icon.eraser()
                +"eraser"
            }

            ui.column {
                icon.eye()
                +"eye"
            }

            ui.column {
                icon.eye_dropper()
                +"eye dropper"
            }

            ui.column {
                icon.eye_slash()
                +"eye slash"
            }

            ui.column {
                icon.eye_slash_outline()
                +"eye slash outline"
            }

            ui.column {
                icon.object_group()
                +"object group"
            }

            ui.column {
                icon.object_group_outline()
                +"object group outline"
            }

            ui.column {
                icon.object_ungroup()
                +"object ungroup"
            }

            ui.column {
                icon.object_ungroup_outline()
                +"object ungroup outline"
            }

            ui.column {
                icon.paint_brush()
                +"paint brush"
            }

            ui.column {
                icon.paste()
                +"paste"
            }

            ui.column {
                icon.pencil_alternate()
                +"pencil alternate"
            }

            ui.column {
                icon.save()
                +"save"
            }

            ui.column {
                icon.save_outline()
                +"save outline"
            }

            ui.column {
                icon.tint()
                +"tint"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Editors" }

        ui.ten.column.grid {
            ui.column {
                icon.align_center()
                +"align center"
            }

            ui.column {
                icon.align_justify()
                +"align justify"
            }

            ui.column {
                icon.align_left()
                +"align left"
            }

            ui.column {
                icon.align_right()
                +"align right"
            }

            ui.column {
                icon.bold()
                +"bold"
            }

            ui.column {
                icon.clipboard()
                +"clipboard"
            }

            ui.column {
                icon.clipboard_outline()
                +"clipboard outline"
            }

            ui.column {
                icon.clone()
                +"clone"
            }

            ui.column {
                icon.clone_outline()
                +"clone outline"
            }

            ui.column {
                icon.columns()
                +"columns"
            }

            ui.column {
                icon.copy()
                +"copy"
            }

            ui.column {
                icon.copy_outline()
                +"copy outline"
            }

            ui.column {
                icon.cut()
                +"cut"
            }

            ui.column {
                icon.edit()
                +"edit"
            }

            ui.column {
                icon.edit_outline()
                +"edit outline"
            }

            ui.column {
                icon.eraser()
                +"eraser"
            }

            ui.column {
                icon.file()
                +"file"
            }

            ui.column {
                icon.file_outline()
                +"file outline"
            }

            ui.column {
                icon.file_alternate()
                +"file alternate"
            }

            ui.column {
                icon.file_alternate_outline()
                +"file alternate outline"
            }

            ui.column {
                icon.font()
                +"font"
            }

            ui.column {
                icon.heading()
                +"heading"
            }

            ui.column {
                icon.i_cursor()
                +"i cursor"
            }

            ui.column {
                icon.indent()
                +"indent"
            }

            ui.column {
                icon.italic()
                +"italic"
            }

            ui.column {
                icon.linkify()
                +"linkify"
            }

            ui.column {
                icon.list()
                +"list"
            }

            ui.column {
                icon.list_alternate()
                +"list alternate"
            }

            ui.column {
                icon.list_alternate_outline()
                +"list alternate outline"
            }

            ui.column {
                icon.list_ol()
                +"list ol"
            }

            ui.column {
                icon.list_ul()
                +"list ul"
            }

            ui.column {
                icon.outdent()
                +"outdent"
            }

            ui.column {
                icon.paper_plane()
                +"paper plane"
            }

            ui.column {
                icon.paper_plane_outline()
                +"paper plane outline"
            }

            ui.column {
                icon.paperclip()
                +"paperclip"
            }

            ui.column {
                icon.paragraph()
                +"paragraph"
            }

            ui.column {
                icon.paste()
                +"paste"
            }

            ui.column {
                icon.pencil_alternate()
                +"pencil alternate"
            }

            ui.column {
                icon.print()
                +"print"
            }

            ui.column {
                icon.quote_left()
                +"quote left"
            }

            ui.column {
                icon.quote_right()
                +"quote right"
            }

            ui.column {
                icon.redo()
                +"redo"
            }

            ui.column {
                icon.redo_alternate()
                +"redo alternate"
            }

            ui.column {
                icon.reply()
                +"reply"
            }

            ui.column {
                icon.reply_all()
                +"reply all"
            }

            ui.column {
                icon.share()
                +"share"
            }

            ui.column {
                icon.strikethrough()
                +"strikethrough"
            }

            ui.column {
                icon.subscript()
                +"subscript"
            }

            ui.column {
                icon.superscript()
                +"superscript"
            }

            ui.column {
                icon.sync()
                +"sync"
            }

            ui.column {
                icon.sync_alternate()
                +"sync alternate"
            }

            ui.column {
                icon.table()
                +"table"
            }

            ui.column {
                icon.tasks()
                +"tasks"
            }

            ui.column {
                icon.text_height()
                +"text height"
            }

            ui.column {
                icon.text_width()
                +"text width"
            }

            ui.column {
                icon.th()
                +"th"
            }

            ui.column {
                icon.th_large()
                +"th large"
            }

            ui.column {
                icon.th_list()
                +"th list"
            }

            ui.column {
                icon.trash()
                +"trash"
            }

            ui.column {
                icon.trash_alternate()
                +"trash alternate"
            }

            ui.column {
                icon.trash_alternate_outline()
                +"trash alternate outline"
            }

            ui.column {
                icon.underline()
                +"underline"
            }

            ui.column {
                icon.undo()
                +"undo"
            }

            ui.column {
                icon.undo_alternate()
                +"undo alternate"
            }

            ui.column {
                icon.unlink()
                +"unlink"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Files" }

        ui.ten.column.grid {
            ui.column {
                icon.archive()
                +"archive"
            }

            ui.column {
                icon.clone()
                +"clone"
            }

            ui.column {
                icon.clone_outline()
                +"clone outline"
            }

            ui.column {
                icon.copy()
                +"copy"
            }

            ui.column {
                icon.copy_outline()
                +"copy outline"
            }

            ui.column {
                icon.cut()
                +"cut"
            }

            ui.column {
                icon.file()
                +"file"
            }

            ui.column {
                icon.file_outline()
                +"file outline"
            }

            ui.column {
                icon.file_alternate()
                +"file alternate"
            }

            ui.column {
                icon.file_alternate_outline()
                +"file alternate outline"
            }

            ui.column {
                icon.file_archive()
                +"file archive"
            }

            ui.column {
                icon.file_archive_outline()
                +"file archive outline"
            }

            ui.column {
                icon.file_audio()
                +"file audio"
            }

            ui.column {
                icon.file_audio_outline()
                +"file audio outline"
            }

            ui.column {
                icon.file_code()
                +"file code"
            }

            ui.column {
                icon.file_code_outline()
                +"file code outline"
            }

            ui.column {
                icon.file_excel()
                +"file excel"
            }

            ui.column {
                icon.file_excel_outline()
                +"file excel outline"
            }

            ui.column {
                icon.file_image()
                +"file image"
            }

            ui.column {
                icon.file_image_outline()
                +"file image outline"
            }

            ui.column {
                icon.file_pdf()
                +"file pdf"
            }

            ui.column {
                icon.file_pdf_outline()
                +"file pdf outline"
            }

            ui.column {
                icon.file_powerpoint()
                +"file powerpoint"
            }

            ui.column {
                icon.file_powerpoint_outline()
                +"file powerpoint outline"
            }

            ui.column {
                icon.file_video()
                +"file video"
            }

            ui.column {
                icon.file_video_outline()
                +"file video outline"
            }

            ui.column {
                icon.file_word()
                +"file word"
            }

            ui.column {
                icon.file_word_outline()
                +"file word outline"
            }

            ui.column {
                icon.folder()
                +"folder"
            }

            ui.column {
                icon.folder_outline()
                +"folder outline"
            }

            ui.column {
                icon.folder_open()
                +"folder open"
            }

            ui.column {
                icon.folder_open_outline()
                +"folder open outline"
            }

            ui.column {
                icon.paste()
                +"paste"
            }

            ui.column {
                icon.save()
                +"save"
            }

            ui.column {
                icon.save_outline()
                +"save outline"
            }

            ui.column {
                icon.sticky_note()
                +"sticky note"
            }

            ui.column {
                icon.sticky_note_outline()
                +"sticky note outline"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Genders" }

        ui.ten.column.grid {
            ui.column {
                icon.genderless()
                +"genderless"
            }

            ui.column {
                icon.mars()
                +"mars"
            }

            ui.column {
                icon.mars_double()
                +"mars double"
            }

            ui.column {
                icon.mars_stroke()
                +"mars stroke"
            }

            ui.column {
                icon.mars_stroke_horizontal()
                +"mars stroke horizontal"
            }

            ui.column {
                icon.mars_stroke_vertical()
                +"mars stroke vertical"
            }

            ui.column {
                icon.mercury()
                +"mercury"
            }

            ui.column {
                icon.neuter()
                +"neuter"
            }

            ui.column {
                icon.transgender()
                +"transgender"
            }

            ui.column {
                icon.transgender_alternate()
                +"transgender alternate"
            }

            ui.column {
                icon.venus()
                +"venus"
            }

            ui.column {
                icon.venus_double()
                +"venus double"
            }

            ui.column {
                icon.venus_mars()
                +"venus mars"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Hands & Gestures" }

        ui.ten.column.grid {

            ui.column {
                icon.hand_lizard()
                +"hand lizard"
            }

            ui.column {
                icon.hand_lizard_outline()
                +"hand lizard outline"
            }

            ui.column {
                icon.hand_paper()
                +"hand paper"
            }

            ui.column {
                icon.hand_paper_outline()
                +"hand paper outline"
            }

            ui.column {
                icon.hand_peace()
                +"hand peace"
            }

            ui.column {
                icon.hand_peace_outline()
                +"hand peace outline"
            }

            ui.column {
                icon.hand_point_down()
                +"hand point down"
            }

            ui.column {
                icon.hand_point_down_outline()
                +"hand point down outline"
            }

            ui.column {
                icon.hand_point_left()
                +"hand point left"
            }

            ui.column {
                icon.hand_point_left_outline()
                +"hand point left outline"
            }

            ui.column {
                icon.hand_point_right()
                +"hand point right"
            }

            ui.column {
                icon.hand_point_right_outline()
                +"hand point right outline"
            }

            ui.column {
                icon.hand_point_up()
                +"hand point up"
            }

            ui.column {
                icon.hand_point_up_outline()
                +"hand point up outline"
            }

            ui.column {
                icon.hand_pointer()
                +"hand pointer"
            }

            ui.column {
                icon.hand_pointer_outline()
                +"hand pointer outline"
            }

            ui.column {
                icon.hand_rock()
                +"hand rock"
            }

            ui.column {
                icon.hand_rock_outline()
                +"hand rock outline"
            }

            ui.column {
                icon.hand_scissors()
                +"hand scissors"
            }

            ui.column {
                icon.hand_scissors_outline()
                +"hand scissors outline"
            }

            ui.column {
                icon.hand_spock()
                +"hand spock"
            }

            ui.column {
                icon.hand_spock_outline()
                +"hand spock outline"
            }

            ui.column {
                icon.handshake()
                +"handshake"
            }

            ui.column {
                icon.handshake_outline()
                +"handshake outline"
            }

            ui.column {
                icon.thumbs_down()
                +"thumbs down"
            }

            ui.column {
                icon.thumbs_down_outline()
                +"thumbs down outline"
            }

            ui.column {
                icon.thumbs_up()
                +"thumbs up"
            }

            ui.column {
                icon.thumbs_up_outline()
                +"thumbs up outline"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Health" }

        ui.ten.column.grid {

            ui.column {
                icon.ambulance()
                +"ambulance"
            }

            ui.column {
                icon.h_square()
                +"h square"
            }

            ui.column {
                icon.heart()
                +"heart"
            }

            ui.column {
                icon.heart_outline()
                +"heart outline"
            }

            ui.column {
                icon.heartbeat()
                +"heartbeat"
            }

            ui.column {
                icon.hospital()
                +"hospital"
            }

            ui.column {
                icon.hospital_outline()
                +"hospital outline"
            }

            ui.column {
                icon.medkit()
                +"medkit"
            }

            ui.column {
                icon.plus_square()
                +"plus square"
            }

            ui.column {
                icon.plus_square_outline()
                +"plus square outline"
            }

            ui.column {
                icon.stethoscope()
                +"stethoscope"
            }

            ui.column {
                icon.user_md()
                +"user md"
            }

            ui.column {
                icon.wheelchair()
                +"wheelchair"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Images" }

        ui.ten.column.grid {
            ui.column {
                icon.adjust()
                +"adjust"
            }

            ui.column {
                icon.bolt()
                +"bolt"
            }

            ui.column {
                icon.camera()
                +"camera"
            }

            ui.column {
                icon.camera_retro()
                +"camera retro"
            }

            ui.column {
                icon.clone()
                +"clone"
            }

            ui.column {
                icon.clone_outline()
                +"clone outline"
            }

            ui.column {
                icon.compress()
                +"compress"
            }

            ui.column {
                icon.expand()
                +"expand"
            }

            ui.column {
                icon.eye()
                +"eye"
            }

            ui.column {
                icon.eye_dropper()
                +"eye dropper"
            }

            ui.column {
                icon.eye_slash()
                +"eye slash"
            }

            ui.column {
                icon.eye_slash_outline()
                +"eye slash outline"
            }

            ui.column {
                icon.file_image()
                +"file image"
            }

            ui.column {
                icon.file_image_outline()
                +"file image outline"
            }

            ui.column {
                icon.film()
                +"film"
            }

            ui.column {
                icon.id_badge()
                +"id badge"
            }

            ui.column {
                icon.id_badge_outline()
                +"id badge outline"
            }

            ui.column {
                icon.id_card()
                +"id card"
            }

            ui.column {
                icon.id_card_outline()
                +"id card outline"
            }

            ui.column {
                icon.image()
                +"image"
            }

            ui.column {
                icon.image_outline()
                +"image outline"
            }

            ui.column {
                icon.images()
                +"images"
            }

            ui.column {
                icon.images_outline()
                +"images outline"
            }

            ui.column {
                icon.sliders_horizontal()
                +"sliders horizontal"
            }

            ui.column {
                icon.tint()
                +"tint"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Interfaces" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Logistics" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Maps" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Medical" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Objects" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Payments & Shopping" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Shapes" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Spinners" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Sports" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Status" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"User & People" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Vehicles" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Writing" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Brands" }

        ui.ten.column.grid {
            +"TODO"
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Code" }

        ui.ten.column.grid {
            +"TODO"
        }

    }
}
