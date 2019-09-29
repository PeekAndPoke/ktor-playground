package de.peekandpoke.module.semanticui.views

import de.peekandpoke.ktorfx.prismjs.Language
import de.peekandpoke.ktorfx.prismjs.prism
import de.peekandpoke.ktorfx.semanticui.icon
import de.peekandpoke.ktorfx.semanticui.ui
import de.peekandpoke.ktorfx.templating.SimpleTemplate
import kotlinx.html.*

@Suppress("DuplicatedCode")
internal fun SimpleTemplate.icons() {

    content {

        style(
            "text/css",
            """
                h3 {
                    margin-top: 40px;
                }
                
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
                    prism(Language.Kotlin) {
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
                    prism(Language.Kotlin) {
                        """
                            icon.question_circle_outline()
                        """.trimIndent()
                    }
                }
            }

            ui.row {
                ui.column { +"Icons can have colors" }
                ui.column {
                    icon.red.question_circle()
                    icon.orange.question_circle()
                    icon.yellow.question_circle()
                    icon.olive.question_circle()
                    icon.green.question_circle()
                    icon.teal.question_circle()
                    icon.blue.question_circle()
                    icon.violet.question_circle()
                    icon.purple.question_circle()
                    icon.pink.question_circle()
                    icon.brown.question_circle()
                    icon.grey.question_circle()
                    icon.black.question_circle()
                }
                ui.column {
                    prism(Language.Kotlin) {
                        """
                            icon.red.question_circle()
                            icon.orange.question_circle()
                            icon.yellow.question_circle()
                            icon.olive.question_circle()
                            icon.green.question_circle()
                            icon.teal.question_circle()
                            icon.blue.question_circle()
                            icon.violet.question_circle()
                            icon.purple.question_circle()
                            icon.pink.question_circle()
                            icon.brown.question_circle()
                            icon.grey.question_circle()
                            icon.black.question_circle()
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
                icon.fork()
                +"fork icon"
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
                icon.settings()
                +"settings"
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
            ui.column {
                icon.ban()
                +"ban"
            }

            ui.column {
                icon.barcode()
                +"barcode"
            }

            ui.column {
                icon.bars()
                +"bars"
            }

            ui.column {
                icon.beer()
                +"beer"
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
                icon.bug()
                +"bug"
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
                icon.certificate()
                +"certificate"
            }

            ui.column {
                icon.check()
                +"check"
            }

            ui.column {
                icon.check_circle()
                +"check circle"
            }

            ui.column {
                icon.check_circle_outline()
                +"check circle outline"
            }

            ui.column {
                icon.check_square()
                +"check square"
            }

            ui.column {
                icon.check_square_outline()
                +"check square outline"
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
                icon.cloud()
                +"cloud"
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
                icon.coffee()
                +"coffee"
            }

            ui.column {
                icon.cog()
                +"cog"
            }

            ui.column {
                icon.cogs()
                +"cogs"
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
                icon.database()
                +"database"
            }

            ui.column {
                icon.dot_circle()
                +"dot circle"
            }

            ui.column {
                icon.dot_circle_outline()
                +"dot circle outline"
            }

            ui.column {
                icon.download()
                +"download"
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
                icon.ellipsis_horizontal()
                +"ellipsis horizontal"
            }

            ui.column {
                icon.ellipsis_vertical()
                +"ellipsis vertical"
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
                icon.eraser()
                +"eraser"
            }

            ui.column {
                icon.exclamation()
                +"exclamation"
            }

            ui.column {
                icon.exclamation_circle()
                +"exclamation circle"
            }

            ui.column {
                icon.exclamation_triangle()
                +"exclamation triangle"
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
                icon.eye()
                +"eye"
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
                icon.filter()
                +"filter"
            }

            ui.column {
                icon.flag()
                +"flag"
            }

            ui.column {
                icon.flag_outline()
                +"flag outline"
            }

            ui.column {
                icon.flag_checkered()
                +"flag checkered"
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
                icon.frown()
                +"frown"
            }

            ui.column {
                icon.frown_outline()
                +"frown outline"
            }

            ui.column {
                icon.hashtag()
                +"hashtag"
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
                icon.history()
                +"history"
            }

            ui.column {
                icon.home()
                +"home"
            }

            ui.column {
                icon.i_cursor()
                +"i cursor"
            }

            ui.column {
                icon.info()
                +"info"
            }

            ui.column {
                icon.info_circle()
                +"info circle"
            }

            ui.column {
                icon.language()
                +"language"
            }

            ui.column {
                icon.magic()
                +"magic"
            }

            ui.column {
                icon.meh()
                +"meh"
            }

            ui.column {
                icon.meh_outline()
                +"meh outline"
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
                icon.minus()
                +"minus"
            }

            ui.column {
                icon.minus_circle()
                +"minus circle"
            }

            ui.column {
                icon.minus_square()
                +"minus square"
            }

            ui.column {
                icon.minus_square_outline()
                +"minus square outline"
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
                icon.plus()
                +"plus"
            }

            ui.column {
                icon.plus_circle()
                +"plus circle"
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
                icon.qrcode()
                +"qrcode"
            }

            ui.column {
                icon.question()
                +"question"
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
                icon.rss()
                +"rss"
            }

            ui.column {
                icon.rss_square()
                +"rss square"
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
                icon.search()
                +"search"
            }

            ui.column {
                icon.search_minus()
                +"search minus"
            }

            ui.column {
                icon.search_plus()
                +"search plus"
            }

            ui.column {
                icon.share()
                +"share"
            }

            ui.column {
                icon.share_alternate()
                +"share alternate"
            }

            ui.column {
                icon.share_alternate_square()
                +"share alternate square"
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
                icon.shield_alternate()
                +"shield alternate"
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
                icon.signal()
                +"signal"
            }

            ui.column {
                icon.sitemap()
                +"sitemap"
            }

            ui.column {
                icon.sliders_horizontal()
                +"sliders horizontal"
            }

            ui.column {
                icon.smile()
                +"smile"
            }

            ui.column {
                icon.smile_outline()
                +"smile outline"
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
                icon.star()
                +"star"
            }

            ui.column {
                icon.star_outline()
                +"star outline"
            }

            ui.column {
                icon.star_half()
                +"star half"
            }

            ui.column {
                icon.star_half_outline()
                +"star half outline"
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

            ui.column {
                icon.times()
                +"times"
            }

            ui.column {
                icon.times_circle()
                +"times circle"
            }

            ui.column {
                icon.times_circle_outline()
                +"times circle outline"
            }

            ui.column {
                icon.toggle_off()
                +"toggle off"
            }

            ui.column {
                icon.toggle_on()
                +"toggle on"
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
                icon.trophy()
                +"trophy"
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

            ui.column {
                icon.user()
                +"user"
            }

            ui.column {
                icon.user_outline()
                +"user outline"
            }

            ui.column {
                icon.user_circle()
                +"user circle"
            }

            ui.column {
                icon.user_circle_outline()
                +"user circle outline"
            }

            ui.column {
                icon.wifi()
                +"wifi"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Logistics" }

        ui.ten.column.grid {
            ui.column {
                icon.box()
                +"box"
            }

            ui.column {
                icon.boxes()
                +"boxes"
            }

            ui.column {
                icon.clipboard_check()
                +"clipboard check"
            }

            ui.column {
                icon.clipboard_list()
                +"clipboard list"
            }

            ui.column {
                icon.dolly()
                +"dolly"
            }

            ui.column {
                icon.dolly_flatbed()
                +"dolly flatbed"
            }

            ui.column {
                icon.pallet()
                +"pallet"
            }

            ui.column {
                icon.shipping_fast()
                +"shipping fast"
            }

            ui.column {
                icon.truck()
                +"truck"
            }

            ui.column {
                icon.warehouse()
                +"warehouse"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Maps" }

        ui.ten.column.grid {
            ui.column {
                icon.ambulance()
                +"ambulance"
            }

            ui.column {
                icon.anchor()
                +"anchor"
            }

            ui.column {
                icon.balance_scale()
                +"balance scale"
            }

            ui.column {
                icon.bath()
                +"bath"
            }

            ui.column {
                icon.bed()
                +"bed"
            }

            ui.column {
                icon.beer()
                +"beer"
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
                icon.bicycle()
                +"bicycle"
            }

            ui.column {
                icon.binoculars()
                +"binoculars"
            }

            ui.column {
                icon.birthday_cake()
                +"birthday cake"
            }

            ui.column {
                icon.blind()
                +"blind"
            }

            ui.column {
                icon.bomb()
                +"bomb"
            }

            ui.column {
                icon.book()
                +"book"
            }

            ui.column {
                icon.bookmark()
                +"bookmark"
            }

            ui.column {
                icon.bookmark_outline()
                +"bookmark outline"
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
                icon.car()
                +"car"
            }

            ui.column {
                icon.coffee()
                +"coffee"
            }

            ui.column {
                icon.crosshairs()
                +"crosshairs"
            }

            ui.column {
                icon.dollar_sign()
                +"dollar sign"
            }

            ui.column {
                icon.eye()
                +"eye"
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
                icon.fighter_jet()
                +"fighter jet"
            }

            ui.column {
                icon.fire()
                +"fire"
            }

            ui.column {
                icon.fire_extinguisher()
                +"fire extinguisher"
            }

            ui.column {
                icon.flag()
                +"flag"
            }

            ui.column {
                icon.flag_outline()
                +"flag outline"
            }

            ui.column {
                icon.flag_checkered()
                +"flag checkered"
            }

            ui.column {
                icon.flask()
                +"flask"
            }

            ui.column {
                icon.gamepad()
                +"gamepad"
            }

            ui.column {
                icon.gavel()
                +"gavel"
            }

            ui.column {
                icon.gift()
                +"gift"
            }

            ui.column {
                icon.glass_martini()
                +"glass martini"
            }

            ui.column {
                icon.globe()
                +"globe"
            }

            ui.column {
                icon.graduation_cap()
                +"graduation cap"
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
                icon.home()
                +"home"
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
                icon.industry()
                +"industry"
            }

            ui.column {
                icon.info()
                +"info"
            }

            ui.column {
                icon.info_circle()
                +"info circle"
            }

            ui.column {
                icon.key()
                +"key"
            }

            ui.column {
                icon.leaf()
                +"leaf"
            }

            ui.column {
                icon.lemon()
                +"lemon"
            }

            ui.column {
                icon.lemon_outline()
                +"lemon outline"
            }

            ui.column {
                icon.life_ring()
                +"life ring"
            }

            ui.column {
                icon.life_ring_outline()
                +"life ring outline"
            }

            ui.column {
                icon.lightbulb()
                +"lightbulb"
            }

            ui.column {
                icon.lightbulb_outline()
                +"lightbulb outline"
            }

            ui.column {
                icon.location_arrow()
                +"location arrow"
            }

            ui.column {
                icon.low_vision()
                +"low vision"
            }

            ui.column {
                icon.magnet()
                +"magnet"
            }

            ui.column {
                icon.male()
                +"male"
            }

            ui.column {
                icon.map()
                +"map"
            }

            ui.column {
                icon.map_outline()
                +"map outline"
            }

            ui.column {
                icon.map_marker()
                +"map marker"
            }

            ui.column {
                icon.map_marker_alternate()
                +"map marker alternate"
            }

            ui.column {
                icon.map_pin()
                +"map pin"
            }

            ui.column {
                icon.map_signs()
                +"map signs"
            }

            ui.column {
                icon.medkit()
                +"medkit"
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
                icon.motorcycle()
                +"motorcycle"
            }

            ui.column {
                icon.music()
                +"music"
            }

            ui.column {
                icon.newspaper()
                +"newspaper"
            }

            ui.column {
                icon.newspaper_outline()
                +"newspaper outline"
            }

            ui.column {
                icon.paw()
                +"paw"
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
                icon.plane()
                +"plane"
            }

            ui.column {
                icon.plug()
                +"plug"
            }

            ui.column {
                icon.plus()
                +"plus"
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
                icon.print()
                +"print"
            }

            ui.column {
                icon.recycle()
                +"recycle"
            }

            ui.column {
                icon.road()
                +"road"
            }

            ui.column {
                icon.rocket()
                +"rocket"
            }

            ui.column {
                icon.search()
                +"search"
            }

            ui.column {
                icon.search_minus()
                +"search minus"
            }

            ui.column {
                icon.search_plus()
                +"search plus"
            }

            ui.column {
                icon.ship()
                +"ship"
            }

            ui.column {
                icon.shopping_bag()
                +"shopping bag"
            }

            ui.column {
                icon.shopping_basket()
                +"shopping basket"
            }

            ui.column {
                icon.shopping_cart()
                +"shopping cart"
            }

            ui.column {
                icon.shower()
                +"shower"
            }

            ui.column {
                icon.street_view()
                +"street view"
            }

            ui.column {
                icon.subway()
                +"subway"
            }

            ui.column {
                icon.suitcase()
                +"suitcase"
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
                icon.taxi()
                +"taxi"
            }

            ui.column {
                icon.thumbtack()
                +"thumbtack"
            }

            ui.column {
                icon.ticket_alternate()
                +"ticket alternate"
            }

            ui.column {
                icon.tint()
                +"tint"
            }

            ui.column {
                icon.train()
                +"train"
            }

            ui.column {
                icon.tree()
                +"tree"
            }

            ui.column {
                icon.trophy()
                +"trophy"
            }

            ui.column {
                icon.truck()
                +"truck"
            }

            ui.column {
                icon.tty()
                +"tty"
            }

            ui.column {
                icon.umbrella()
                +"umbrella"
            }

            ui.column {
                icon.university()
                +"university"
            }

            ui.column {
                icon.utensil_spoon()
                +"utensil spoon"
            }

            ui.column {
                icon.utensils()
                +"utensils"
            }

            ui.column {
                icon.wheelchair()
                +"wheelchair"
            }

            ui.column {
                icon.wifi()
                +"wifi"
            }

            ui.column {
                icon.wrench()
                +"wrench"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Medical" }

        ui.ten.column.grid {
            ui.column {
                icon.ambulance()
                +"ambulance"
            }

            ui.column {
                icon.band_aid()
                +"band aid"
            }

            ui.column {
                icon.dna()
                +"dna"
            }

            ui.column {
                icon.first_aid()
                +"first aid"
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
                icon.hospital_symbol()
                +"hospital symbol"
            }

            ui.column {
                icon.pills()
                +"pills"
            }

            ui.column {
                icon.plus()
                +"plus"
            }

            ui.column {
                icon.stethoscope()
                +"stethoscope"
            }

            ui.column {
                icon.syringe()
                +"syringe"
            }

            ui.column {
                icon.thermometer()
                +"thermometer"
            }

            ui.column {
                icon.user_md()
                +"user md"
            }

            ui.column {
                icon.weight()
                +"weight"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Objects" }

        ui.ten.column.grid {
            ui.column {
                icon.ambulance()
                +"ambulance"
            }

            ui.column {
                icon.anchor()
                +"anchor"
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
                icon.bath()
                +"bath"
            }

            ui.column {
                icon.bed()
                +"bed"
            }

            ui.column {
                icon.beer()
                +"beer"
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
                icon.bicycle()
                +"bicycle"
            }

            ui.column {
                icon.binoculars()
                +"binoculars"
            }

            ui.column {
                icon.birthday_cake()
                +"birthday cake"
            }

            ui.column {
                icon.bomb()
                +"bomb"
            }

            ui.column {
                icon.book()
                +"book"
            }

            ui.column {
                icon.bookmark()
                +"bookmark"
            }

            ui.column {
                icon.bookmark_outline()
                +"bookmark outline"
            }

            ui.column {
                icon.briefcase()
                +"briefcase"
            }

            ui.column {
                icon.bug()
                +"bug"
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
                icon.bus()
                +"bus"
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
                icon.camera()
                +"camera"
            }

            ui.column {
                icon.camera_retro()
                +"camera retro"
            }

            ui.column {
                icon.car()
                +"car"
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
                icon.cloud()
                +"cloud"
            }

            ui.column {
                icon.coffee()
                +"coffee"
            }

            ui.column {
                icon.cog()
                +"cog"
            }

            ui.column {
                icon.cogs()
                +"cogs"
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
                icon.cube()
                +"cube"
            }

            ui.column {
                icon.cubes()
                +"cubes"
            }

            ui.column {
                icon.cut()
                +"cut"
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
                icon.fax()
                +"fax"
            }

            ui.column {
                icon.fighter_jet()
                +"fighter jet"
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
                icon.film()
                +"film"
            }

            ui.column {
                icon.fire()
                +"fire"
            }

            ui.column {
                icon.fire_extinguisher()
                +"fire extinguisher"
            }

            ui.column {
                icon.flag()
                +"flag"
            }

            ui.column {
                icon.flag_outline()
                +"flag outline"
            }

            ui.column {
                icon.flag_checkered()
                +"flag checkered"
            }

            ui.column {
                icon.flask()
                +"flask"
            }

            ui.column {
                icon.futbol()
                +"futbol"
            }

            ui.column {
                icon.futbol_outline()
                +"futbol outline"
            }

            ui.column {
                icon.gamepad()
                +"gamepad"
            }

            ui.column {
                icon.gavel()
                +"gavel"
            }

            ui.column {
                icon.gem()
                +"gem"
            }

            ui.column {
                icon.gem_outline()
                +"gem outline"
            }

            ui.column {
                icon.gift()
                +"gift"
            }

            ui.column {
                icon.glass_martini()
                +"glass martini"
            }

            ui.column {
                icon.globe()
                +"globe"
            }

            ui.column {
                icon.graduation_cap()
                +"graduation cap"
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
                icon.heart()
                +"heart"
            }

            ui.column {
                icon.heart_outline()
                +"heart outline"
            }

            ui.column {
                icon.home()
                +"home"
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
                icon.hourglass()
                +"hourglass"
            }

            ui.column {
                icon.hourglass_outline()
                +"hourglass outline"
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
                icon.industry()
                +"industry"
            }

            ui.column {
                icon.key()
                +"key"
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
                icon.leaf()
                +"leaf"
            }

            ui.column {
                icon.lemon()
                +"lemon"
            }

            ui.column {
                icon.lemon_outline()
                +"lemon outline"
            }

            ui.column {
                icon.life_ring()
                +"life ring"
            }

            ui.column {
                icon.life_ring_outline()
                +"life ring outline"
            }

            ui.column {
                icon.lightbulb()
                +"lightbulb"
            }

            ui.column {
                icon.lightbulb_outline()
                +"lightbulb outline"
            }

            ui.column {
                icon.lock()
                +"lock"
            }

            ui.column {
                icon.lock_open()
                +"lock open"
            }

            ui.column {
                icon.magic()
                +"magic"
            }

            ui.column {
                icon.magnet()
                +"magnet"
            }

            ui.column {
                icon.map()
                +"map"
            }

            ui.column {
                icon.map_outline()
                +"map outline"
            }

            ui.column {
                icon.map_marker()
                +"map marker"
            }

            ui.column {
                icon.map_marker_alternate()
                +"map marker alternate"
            }

            ui.column {
                icon.map_pin()
                +"map pin"
            }

            ui.column {
                icon.map_signs()
                +"map signs"
            }

            ui.column {
                icon.medkit()
                +"medkit"
            }

            ui.column {
                icon.microchip()
                +"microchip"
            }

            ui.column {
                icon.microphone()
                +"microphone"
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
                icon.money_bill_alternate()
                +"money bill alternate"
            }

            ui.column {
                icon.money_bill_alternate_outline()
                +"money bill alternate outline"
            }

            ui.column {
                icon.moon()
                +"moon"
            }

            ui.column {
                icon.moon_outline()
                +"moon outline"
            }

            ui.column {
                icon.motorcycle()
                +"motorcycle"
            }

            ui.column {
                icon.newspaper()
                +"newspaper"
            }

            ui.column {
                icon.newspaper_outline()
                +"newspaper outline"
            }

            ui.column {
                icon.paint_brush()
                +"paint brush"
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
                icon.paste()
                +"paste"
            }

            ui.column {
                icon.paw()
                +"paw"
            }

            ui.column {
                icon.pencil_alternate()
                +"pencil alternate"
            }

            ui.column {
                icon.phone()
                +"phone"
            }

            ui.column {
                icon.plane()
                +"plane"
            }

            ui.column {
                icon.plug()
                +"plug"
            }

            ui.column {
                icon.print()
                +"print"
            }

            ui.column {
                icon.puzzle_piece()
                +"puzzle piece"
            }

            ui.column {
                icon.road()
                +"road"
            }

            ui.column {
                icon.rocket()
                +"rocket"
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
                icon.search()
                +"search"
            }

            ui.column {
                icon.shield_alternate()
                +"shield alternate"
            }

            ui.column {
                icon.shopping_bag()
                +"shopping bag"
            }

            ui.column {
                icon.shopping_basket()
                +"shopping basket"
            }

            ui.column {
                icon.shopping_cart()
                +"shopping cart"
            }

            ui.column {
                icon.shower()
                +"shower"
            }

            ui.column {
                icon.snowflake()
                +"snowflake"
            }

            ui.column {
                icon.snowflake_outline()
                +"snowflake outline"
            }

            ui.column {
                icon.space_shuttle()
                +"space shuttle"
            }

            ui.column {
                icon.star()
                +"star"
            }

            ui.column {
                icon.star_outline()
                +"star outline"
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
                icon.stopwatch()
                +"stopwatch"
            }

            ui.column {
                icon.subway()
                +"subway"
            }

            ui.column {
                icon.suitcase()
                +"suitcase"
            }

            ui.column {
                icon.sun()
                +"sun"
            }

            ui.column {
                icon.sun_outline()
                +"sun outline"
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
                icon.tachometer_alternate()
                +"tachometer alternate"
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
                icon.taxi()
                +"taxi"
            }

            ui.column {
                icon.thumbtack()
                +"thumbtack"
            }

            ui.column {
                icon.ticket_alternate()
                +"ticket alternate"
            }

            ui.column {
                icon.train()
                +"train"
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
                icon.tree()
                +"tree"
            }

            ui.column {
                icon.trophy()
                +"trophy"
            }

            ui.column {
                icon.truck()
                +"truck"
            }

            ui.column {
                icon.tv()
                +"tv"
            }

            ui.column {
                icon.umbrella()
                +"umbrella"
            }

            ui.column {
                icon.university()
                +"university"
            }

            ui.column {
                icon.unlock()
                +"unlock"
            }

            ui.column {
                icon.unlock_alternate()
                +"unlock alternate"
            }

            ui.column {
                icon.utensil_spoon()
                +"utensil spoon"
            }

            ui.column {
                icon.utensils()
                +"utensils"
            }

            ui.column {
                icon.wheelchair()
                +"wheelchair"
            }

            ui.column {
                icon.wrench()
                +"wrench"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Payments & Shopping" }

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
                icon.bookmark()
                +"bookmark"
            }

            ui.column {
                icon.bookmark_outline()
                +"bookmark outline"
            }

            ui.column {
                icon.bullhorn()
                +"bullhorn"
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
                icon.cart_arrow_down()
                +"cart arrow down"
            }

            ui.column {
                icon.cart_plus()
                +"cart plus"
            }

            ui.column {
                icon.certificate()
                +"certificate"
            }

            ui.column {
                icon.credit_card()
                +"credit card"
            }

            ui.column {
                icon.credit_card_outline()
                +"credit card outline"
            }

            ui.column {
                icon.gem()
                +"gem"
            }

            ui.column {
                icon.gem_outline()
                +"gem outline"
            }

            ui.column {
                icon.gift()
                +"gift"
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
                icon.heart()
                +"heart"
            }

            ui.column {
                icon.heart_outline()
                +"heart outline"
            }

            ui.column {
                icon.key()
                +"key"
            }

            ui.column {
                icon.shop()
                +"shop bag"
            }

            ui.column {
                icon.shopping_bag()
                +"shopping bag"
            }

            ui.column {
                icon.shopping_basket()
                +"shopping basket"
            }

            ui.column {
                icon.shopping_cart()
                +"shopping cart"
            }

            ui.column {
                icon.star()
                +"star"
            }

            ui.column {
                icon.star_outline()
                +"star outline"
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

            ui.column {
                icon.trophy()
                +"trophy"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Shapes" }

        ui.ten.column.grid {
            ui.column {
                icon.bookmark()
                +"bookmark"
            }

            ui.column {
                icon.bookmark_outline()
                +"bookmark outline"
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
                icon.certificate()
                +"certificate"
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
                icon.cloud()
                +"cloud"
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
                icon.file()
                +"file"
            }

            ui.column {
                icon.file_outline()
                +"file outline"
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
                icon.heart()
                +"heart"
            }

            ui.column {
                icon.heart_outline()
                +"heart outline"
            }

            ui.column {
                icon.map_marker()
                +"map marker"
            }

            ui.column {
                icon.play()
                +"play"
            }

            ui.column {
                icon.square()
                +"square"
            }

            ui.column {
                icon.square_outline()
                +"square outline"
            }

            ui.column {
                icon.star()
                +"star"
            }

            ui.column {
                icon.star_outline()
                +"star outline"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Spinners" }

        ui.ten.column.grid {
            ui.column {
                icon.asterisk()
                +"asterisk"
            }

            ui.column {
                icon.certificate()
                +"certificate"
            }

            ui.column {
                icon.circle_notch()
                +"circle notch"
            }

            ui.column {
                icon.cog()
                +"cog"
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
                icon.crosshairs()
                +"crosshairs"
            }

            ui.column {
                icon.life_ring()
                +"life ring"
            }

            ui.column {
                icon.life_ring_outline()
                +"life ring outline"
            }

            ui.column {
                icon.snowflake()
                +"snowflake"
            }

            ui.column {
                icon.snowflake_outline()
                +"snowflake outline"
            }

            ui.column {
                icon.spinner()
                +"spinner"
            }

            ui.column {
                icon.sun()
                +"sun"
            }

            ui.column {
                icon.sun_outline()
                +"sun outline"
            }

            ui.column {
                icon.sync()
                +"sync"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Sports" }

        ui.ten.column.grid {

            ui.column {
                icon.baseball_ball()
                +"baseball ball"
            }

            ui.column {
                icon.basketball_ball()
                +"basketball ball"
            }

            ui.column {
                icon.bowling_ball()
                +"bowling ball"
            }

            ui.column {
                icon.football_ball()
                +"football ball"
            }

            ui.column {
                icon.futbol()
                +"futbol"
            }

            ui.column {
                icon.futbol_outline()
                +"futbol outline"
            }

            ui.column {
                icon.golf_ball()
                +"golf ball"
            }

            ui.column {
                icon.hockey_puck()
                +"hockey puck"
            }

            ui.column {
                icon.quidditch()
                +"quidditch"
            }

            ui.column {
                icon.table_tennis()
                +"table tennis"
            }

            ui.column {
                icon.volleyball_ball()
                +"volleyball ball"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Status" }

        ui.ten.column.grid {
            ui.column {
                icon.ban()
                +"ban"
            }

            ui.column {
                icon.battery_empty()
                +"battery empty"
            }

            ui.column {
                icon.battery_full()
                +"battery full"
            }

            ui.column {
                icon.battery_half()
                +"battery half"
            }

            ui.column {
                icon.battery_quarter()
                +"battery quarter"
            }

            ui.column {
                icon.battery_three_quarters()
                +"battery three quarters"
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
                icon.cart_arrow_down()
                +"cart arrow down"
            }

            ui.column {
                icon.cart_plus()
                +"cart plus"
            }

            ui.column {
                icon.exclamation()
                +"exclamation"
            }

            ui.column {
                icon.exclamation_circle()
                +"exclamation circle"
            }

            ui.column {
                icon.exclamation_triangle()
                +"exclamation triangle"
            }

            ui.column {
                icon.eye()
                +"eye"
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
                icon.info()
                +"info"
            }

            ui.column {
                icon.info_circle()
                +"info circle"
            }

            ui.column {
                icon.lock()
                +"lock"
            }

            ui.column {
                icon.lock_open()
                +"lock open"
            }

            ui.column {
                icon.minus()
                +"minus"
            }

            ui.column {
                icon.minus_circle()
                +"minus circle"
            }

            ui.column {
                icon.minus_square()
                +"minus square"
            }

            ui.column {
                icon.minus_square_outline()
                +"minus square outline"
            }

            ui.column {
                icon.plus()
                +"plus"
            }

            ui.column {
                icon.plus_circle()
                +"plus circle"
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
                icon.question()
                +"question"
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
                icon.shield_alternate()
                +"shield alternate"
            }

            ui.column {
                icon.shopping_cart()
                +"shopping cart"
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
                icon.thermometer_empty()
                +"thermometer empty"
            }

            ui.column {
                icon.thermometer_full()
                +"thermometer full"
            }

            ui.column {
                icon.thermometer_half()
                +"thermometer half"
            }

            ui.column {
                icon.thermometer_quarter()
                +"thermometer quarter"
            }

            ui.column {
                icon.thermometer_three_quarters()
                +"thermometer three quarters"
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

            ui.column {
                icon.toggle_off()
                +"toggle off"
            }

            ui.column {
                icon.toggle_on()
                +"toggle on"
            }

            ui.column {
                icon.unlock()
                +"unlock"
            }

            ui.column {
                icon.unlock_alternate()
                +"unlock alternate"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"User & People" }

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
                icon.bed()
                +"bed"
            }

            ui.column {
                icon.blind()
                +"blind"
            }

            ui.column {
                icon.child()
                +"child"
            }

            ui.column {
                icon.female()
                +"female"
            }

            ui.column {
                icon.frown()
                +"frown"
            }

            ui.column {
                icon.frown_outline()
                +"frown outline"
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
                icon.male()
                +"male"
            }

            ui.column {
                icon.meh()
                +"meh"
            }

            ui.column {
                icon.meh_outline()
                +"meh outline"
            }

            ui.column {
                icon.power_off()
                +"power off"
            }

            ui.column {
                icon.smile()
                +"smile"
            }

            ui.column {
                icon.smile_outline()
                +"smile outline"
            }

            ui.column {
                icon.street_view()
                +"street view"
            }

            ui.column {
                icon.user()
                +"user"
            }

            ui.column {
                icon.user_outline()
                +"user outline"
            }

            ui.column {
                icon.user_circle()
                +"user circle"
            }

            ui.column {
                icon.user_circle_outline()
                +"user circle outline"
            }

            ui.column {
                icon.user_md()
                +"user md"
            }

            ui.column {
                icon.user_plus()
                +"user plus"
            }

            ui.column {
                icon.user_secret()
                +"user secret"
            }

            ui.column {
                icon.user_times()
                +"user times"
            }

            ui.column {
                icon.users()
                +"users"
            }

            ui.column {
                icon.wheelchair()
                +"wheelchair"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Vehicles" }

        ui.ten.column.grid {
            ui.column {
                icon.ambulance()
                +"ambulance"
            }

            ui.column {
                icon.bicycle()
                +"bicycle"
            }

            ui.column {
                icon.bus()
                +"bus"
            }

            ui.column {
                icon.car()
                +"car"
            }

            ui.column {
                icon.fighter_jet()
                +"fighter jet"
            }

            ui.column {
                icon.motorcycle()
                +"motorcycle"
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
                icon.plane()
                +"plane"
            }

            ui.column {
                icon.rocket()
                +"rocket"
            }

            ui.column {
                icon.ship()
                +"ship"
            }

            ui.column {
                icon.shopping_cart()
                +"shopping cart"
            }

            ui.column {
                icon.space_shuttle()
                +"space shuttle"
            }

            ui.column {
                icon.subway()
                +"subway"
            }

            ui.column {
                icon.taxi()
                +"taxi"
            }

            ui.column {
                icon.train()
                +"train"
            }

            ui.column {
                icon.truck()
                +"truck"
            }

            ui.column {
                icon.wheelchair()
                +"wheelchair"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Writing" }

        ui.ten.column.grid {
            ui.column {
                icon.archive()
                +"archive"
            }

            ui.column {
                icon.book()
                +"book"
            }

            ui.column {
                icon.bookmark()
                +"bookmark"
            }

            ui.column {
                icon.bookmark_outline()
                +"bookmark outline"
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
                icon.newspaper()
                +"newspaper"
            }

            ui.column {
                icon.newspaper_outline()
                +"newspaper outline"
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
                icon.pen_square()
                +"pen square"
            }

            ui.column {
                icon.pencil_alternate()
                +"pencil alternate"
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
                icon.sticky_note()
                +"sticky note"
            }

            ui.column {
                icon.sticky_note_outline()
                +"sticky note outline"
            }

            ui.column {
                icon.thumbtack()
                +"thumbtack"
            }
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ui.dividing.header H3 { +"Brands" }

        ui.ten.column.grid {
            ui.column {
                icon.`500px`()
                +"500px"
            }

            ui.column {
                icon.accessible_icon()
                +"accessible icon"
            }

            ui.column {
                icon.accusoft()
                +"accusoft"
            }

            ui.column {
                icon.adn()
                +"adn"
            }

            ui.column {
                icon.adversal()
                +"adversal"
            }

            ui.column {
                icon.affiliatetheme()
                +"affiliatetheme"
            }

            ui.column {
                icon.algolia()
                +"algolia"
            }

            ui.column {
                icon.amazon()
                +"amazon"
            }

            ui.column {
                icon.amazon_pay()
                +"amazon pay"
            }

            ui.column {
                icon.amilia()
                +"amilia"
            }

            ui.column {
                icon.android()
                +"android"
            }

            ui.column {
                icon.angellist()
                +"angellist"
            }

            ui.column {
                icon.angrycreative()
                +"angrycreative"
            }

            ui.column {
                icon.angular()
                +"angular"
            }

            ui.column {
                icon.app_store()
                +"app store"
            }

            ui.column {
                icon.app_store_ios()
                +"app store ios"
            }

            ui.column {
                icon.apper()
                +"apper"
            }

            ui.column {
                icon.apple()
                +"apple"
            }

            ui.column {
                icon.apple_pay()
                +"apple pay"
            }

            ui.column {
                icon.asymmetrik()
                +"asymmetrik"
            }

            ui.column {
                icon.audible()
                +"audible"
            }

            ui.column {
                icon.autoprefixer()
                +"autoprefixer"
            }

            ui.column {
                icon.avianex()
                +"avianex"
            }

            ui.column {
                icon.aviato()
                +"aviato"
            }

            ui.column {
                icon.aws()
                +"aws"
            }

            ui.column {
                icon.bandcamp()
                +"bandcamp"
            }

            ui.column {
                icon.behance()
                +"behance"
            }

            ui.column {
                icon.behance_square()
                +"behance square"
            }

            ui.column {
                icon.bimobject()
                +"bimobject"
            }

            ui.column {
                icon.bitbucket()
                +"bitbucket"
            }

            ui.column {
                icon.bitcoin()
                +"bitcoin"
            }

            ui.column {
                icon.bity()
                +"bity"
            }

            ui.column {
                icon.black_tie()
                +"black tie"
            }

            ui.column {
                icon.blackberry()
                +"blackberry"
            }

            ui.column {
                icon.blogger()
                +"blogger"
            }

            ui.column {
                icon.blogger_b()
                +"blogger b"
            }

            ui.column {
                icon.bluetooth()
                +"bluetooth"
            }

            ui.column {
                icon.bluetooth_b()
                +"bluetooth b"
            }

            ui.column {
                icon.btc()
                +"btc"
            }

            ui.column {
                icon.buromobelexperte()
                +"buromobelexperte"
            }

            ui.column {
                icon.buysellads()
                +"buysellads"
            }

            ui.column {
                icon.cc_amazon_pay()
                +"cc amazon pay"
            }

            ui.column {
                icon.cc_amex()
                +"cc amex"
            }

            ui.column {
                icon.cc_apple_pay()
                +"cc apple pay"
            }

            ui.column {
                icon.cc_diners_club()
                +"cc diners club"
            }

            ui.column {
                icon.cc_discover()
                +"cc discover"
            }

            ui.column {
                icon.cc_jcb()
                +"cc jcb"
            }

            ui.column {
                icon.cc_mastercard()
                +"cc mastercard"
            }

            ui.column {
                icon.cc_paypal()
                +"cc paypal"
            }

            ui.column {
                icon.cc_stripe()
                +"cc stripe"
            }

            ui.column {
                icon.cc_visa()
                +"cc visa"
            }

            ui.column {
                icon.centercode()
                +"centercode"
            }

            ui.column {
                icon.chrome()
                +"chrome"
            }

            ui.column {
                icon.cloudscale()
                +"cloudscale"
            }

            ui.column {
                icon.cloudsmith()
                +"cloudsmith"
            }

            ui.column {
                icon.cloudversify()
                +"cloudversify"
            }

            ui.column {
                icon.codepen()
                +"codepen"
            }

            ui.column {
                icon.codiepie()
                +"codiepie"
            }

            ui.column {
                icon.connectdevelop()
                +"connectdevelop"
            }

            ui.column {
                icon.contao()
                +"contao"
            }

            ui.column {
                icon.cpanel()
                +"cpanel"
            }

            ui.column {
                icon.creative_commons()
                +"creative commons"
            }

            ui.column {
                icon.css3()
                +"css3"
            }

            ui.column {
                icon.css3_alternate()
                +"css3 alternate"
            }

            ui.column {
                icon.cuttlefish()
                +"cuttlefish"
            }

            ui.column {
                icon.d_and_d()
                +"d and d"
            }

            ui.column {
                icon.dashcube()
                +"dashcube"
            }

            ui.column {
                icon.delicious()
                +"delicious"
            }

            ui.column {
                icon.deploydog()
                +"deploydog"
            }

            ui.column {
                icon.deskpro()
                +"deskpro"
            }

            ui.column {
                icon.deviantart()
                +"deviantart"
            }

            ui.column {
                icon.digg()
                +"digg"
            }

            ui.column {
                icon.digital_ocean()
                +"digital ocean"
            }

            ui.column {
                icon.discord()
                +"discord"
            }

            ui.column {
                icon.discourse()
                +"discourse"
            }

            ui.column {
                icon.dochub()
                +"dochub"
            }

            ui.column {
                icon.docker()
                +"docker"
            }

            ui.column {
                icon.draft2digital()
                +"draft2digital"
            }

            ui.column {
                icon.dribbble()
                +"dribbble"
            }

            ui.column {
                icon.dribbble_square()
                +"dribbble square"
            }

            ui.column {
                icon.dropbox()
                +"dropbox"
            }

            ui.column {
                icon.drupal()
                +"drupal"
            }

            ui.column {
                icon.dyalog()
                +"dyalog"
            }

            ui.column {
                icon.earlybirds()
                +"earlybirds"
            }

            ui.column {
                icon.edge()
                +"edge"
            }

            ui.column {
                icon.elementor()
                +"elementor"
            }

            ui.column {
                icon.ember()
                +"ember"
            }

            ui.column {
                icon.empire()
                +"empire"
            }

            ui.column {
                icon.envira()
                +"envira"
            }

            ui.column {
                icon.erlang()
                +"erlang"
            }

            ui.column {
                icon.ethereum()
                +"ethereum"
            }

            ui.column {
                icon.etsy()
                +"etsy"
            }

            ui.column {
                icon.expeditedssl()
                +"expeditedssl"
            }

            ui.column {
                icon.facebook()
                +"facebook"
            }

            ui.column {
                icon.facebook_f()
                +"facebook f"
            }

            ui.column {
                icon.facebook_messenger()
                +"facebook messenger"
            }

            ui.column {
                icon.facebook_square()
                +"facebook square"
            }

            ui.column {
                icon.firefox()
                +"firefox"
            }

            ui.column {
                icon.first_order()
                +"first order"
            }

            ui.column {
                icon.firstdraft()
                +"firstdraft"
            }

            ui.column {
                icon.flickr()
                +"flickr"
            }

            ui.column {
                icon.flipboard()
                +"flipboard"
            }

            ui.column {
                icon.fly()
                +"fly"
            }

            ui.column {
                icon.font_awesome()
                +"font awesome"
            }

            ui.column {
                icon.font_awesome_alternate()
                +"font awesome alternate"
            }

            ui.column {
                icon.font_awesome_flag()
                +"font awesome flag"
            }

            ui.column {
                icon.fonticons()
                +"fonticons"
            }

            ui.column {
                icon.fonticons_fi()
                +"fonticons fi"
            }

            ui.column {
                icon.fort_awesome()
                +"fort awesome"
            }

            ui.column {
                icon.fort_awesome_alternate()
                +"fort awesome alternate"
            }

            ui.column {
                icon.forumbee()
                +"forumbee"
            }

            ui.column {
                icon.foursquare()
                +"foursquare"
            }

            ui.column {
                icon.free_code_camp()
                +"free code camp"
            }

            ui.column {
                icon.freebsd()
                +"freebsd"
            }

            ui.column {
                icon.get_pocket()
                +"get pocket"
            }

            ui.column {
                icon.gg()
                +"gg"
            }

            ui.column {
                icon.gg_circle()
                +"gg circle"
            }

            ui.column {
                icon.git()
                +"git"
            }

            ui.column {
                icon.git_square()
                +"git square"
            }

            ui.column {
                icon.github()
                +"github"
            }

            ui.column {
                icon.github_alternate()
                +"github alternate"
            }

            ui.column {
                icon.github_square()
                +"github square"
            }

            ui.column {
                icon.gitkraken()
                +"gitkraken"
            }

            ui.column {
                icon.gitlab()
                +"gitlab"
            }

            ui.column {
                icon.gitter()
                +"gitter"
            }

            ui.column {
                icon.glide()
                +"glide"
            }

            ui.column {
                icon.glide_g()
                +"glide g"
            }

            ui.column {
                icon.gofore()
                +"gofore"
            }

            ui.column {
                icon.goodreads()
                +"goodreads"
            }

            ui.column {
                icon.goodreads_g()
                +"goodreads g"
            }

            ui.column {
                icon.google()
                +"google"
            }

            ui.column {
                icon.google_drive()
                +"google drive"
            }

            ui.column {
                icon.google_play()
                +"google play"
            }

            ui.column {
                icon.google_plus()
                +"google plus"
            }

            ui.column {
                icon.google_plus_g()
                +"google plus g"
            }

            ui.column {
                icon.google_plus_square()
                +"google plus square"
            }

            ui.column {
                icon.google_wallet()
                +"google wallet"
            }

            ui.column {
                icon.gratipay()
                +"gratipay"
            }

            ui.column {
                icon.grav()
                +"grav"
            }

            ui.column {
                icon.gripfire()
                +"gripfire"
            }

            ui.column {
                icon.grunt()
                +"grunt"
            }

            ui.column {
                icon.gulp()
                +"gulp"
            }

            ui.column {
                icon.hacker_news()
                +"hacker news"
            }

            ui.column {
                icon.hacker_news_square()
                +"hacker news square"
            }

            ui.column {
                icon.hips()
                +"hips"
            }

            ui.column {
                icon.hire_a_helper()
                +"hire a helper"
            }

            ui.column {
                icon.hooli()
                +"hooli"
            }

            ui.column {
                icon.hotjar()
                +"hotjar"
            }

            ui.column {
                icon.houzz()
                +"houzz"
            }

            ui.column {
                icon.html5()
                +"html5"
            }

            ui.column {
                icon.hubspot()
                +"hubspot"
            }

            ui.column {
                icon.imdb()
                +"imdb"
            }

            ui.column {
                icon.instagram()
                +"instagram"
            }

            ui.column {
                icon.internet_explorer()
                +"internet explorer"
            }

            ui.column {
                icon.ioxhost()
                +"ioxhost"
            }

            ui.column {
                icon.itunes()
                +"itunes"
            }

            ui.column {
                icon.itunes_note()
                +"itunes note"
            }

            ui.column {
                icon.jenkins()
                +"jenkins"
            }

            ui.column {
                icon.joget()
                +"joget"
            }

            ui.column {
                icon.joomla()
                +"joomla"
            }

            ui.column {
                icon.js()
                +"js"
            }

            ui.column {
                icon.js_square()
                +"js square"
            }

            ui.column {
                icon.jsfiddle()
                +"jsfiddle"
            }

            ui.column {
                icon.keycdn()
                +"keycdn"
            }

            ui.column {
                icon.kickstarter()
                +"kickstarter"
            }

            ui.column {
                icon.kickstarter_k()
                +"kickstarter k"
            }

            ui.column {
                icon.korvue()
                +"korvue"
            }

            ui.column {
                icon.laravel()
                +"laravel"
            }

            ui.column {
                icon.lastfm()
                +"lastfm"
            }

            ui.column {
                icon.lastfm_square()
                +"lastfm square"
            }

            ui.column {
                icon.leanpub()
                +"leanpub"
            }

            ui.column {
                icon.less()
                +"less"
            }

            ui.column {
                icon.linechat()
                +"linechat"
            }

            ui.column {
                icon.linkedin()
                +"linkedin"
            }

            ui.column {
                icon.linkedin_in()
                +"linkedin in"
            }

            ui.column {
                icon.linode()
                +"linode"
            }

            ui.column {
                icon.linux()
                +"linux"
            }

            ui.column {
                icon.lyft()
                +"lyft"
            }

            ui.column {
                icon.magento()
                +"magento"
            }

            ui.column {
                icon.maxcdn()
                +"maxcdn"
            }

            ui.column {
                icon.medapps()
                +"medapps"
            }

            ui.column {
                icon.medium()
                +"medium"
            }

            ui.column {
                icon.medium_m()
                +"medium m"
            }

            ui.column {
                icon.medrt()
                +"medrt"
            }

            ui.column {
                icon.meetup()
                +"meetup"
            }

            ui.column {
                icon.microsoft()
                +"microsoft"
            }

            ui.column {
                icon.mix()
                +"mix"
            }

            ui.column {
                icon.mixcloud()
                +"mixcloud"
            }

            ui.column {
                icon.mizuni()
                +"mizuni"
            }

            ui.column {
                icon.modx()
                +"modx"
            }

            ui.column {
                icon.monero()
                +"monero"
            }

            ui.column {
                icon.napster()
                +"napster"
            }

            ui.column {
                icon.nintendo_switch()
                +"nintendo switch"
            }

            ui.column {
                icon.node()
                +"node"
            }

            ui.column {
                icon.node_js()
                +"node js"
            }

            ui.column {
                icon.npm()
                +"npm"
            }

            ui.column {
                icon.ns8()
                +"ns8"
            }

            ui.column {
                icon.nutritionix()
                +"nutritionix"
            }

            ui.column {
                icon.odnoklassniki()
                +"odnoklassniki"
            }

            ui.column {
                icon.odnoklassniki_square()
                +"odnoklassniki square"
            }

            ui.column {
                icon.opencart()
                +"opencart"
            }

            ui.column {
                icon.openid()
                +"openid"
            }

            ui.column {
                icon.opera()
                +"opera"
            }

            ui.column {
                icon.optin_monster()
                +"optin monster"
            }

            ui.column {
                icon.osi()
                +"osi"
            }

            ui.column {
                icon.page4()
                +"page4"
            }

            ui.column {
                icon.pagelines()
                +"pagelines"
            }

            ui.column {
                icon.palfed()
                +"palfed"
            }

            ui.column {
                icon.patreon()
                +"patreon"
            }

            ui.column {
                icon.paypal()
                +"paypal"
            }

            ui.column {
                icon.periscope()
                +"periscope"
            }

            ui.column {
                icon.phabricator()
                +"phabricator"
            }

            ui.column {
                icon.phoenix_framework()
                +"phoenix framework"
            }

            ui.column {
                icon.php()
                +"php"
            }

            ui.column {
                icon.pied_piper()
                +"pied piper"
            }

            ui.column {
                icon.pied_piper_alternate()
                +"pied piper alternate"
            }

            ui.column {
                icon.pied_piper_pp()
                +"pied piper pp"
            }

            ui.column {
                icon.pinterest()
                +"pinterest"
            }

            ui.column {
                icon.pinterest_p()
                +"pinterest p"
            }

            ui.column {
                icon.pinterest_square()
                +"pinterest square"
            }

            ui.column {
                icon.playstation()
                +"playstation"
            }

            ui.column {
                icon.product_hunt()
                +"product hunt"
            }

            ui.column {
                icon.pushed()
                +"pushed"
            }

            ui.column {
                icon.python()
                +"python"
            }

            ui.column {
                icon.qq()
                +"qq"
            }

            ui.column {
                icon.quinscape()
                +"quinscape"
            }

            ui.column {
                icon.quora()
                +"quora"
            }

            ui.column {
                icon.ravelry()
                +"ravelry"
            }

            ui.column {
                icon.react()
                +"react"
            }

            ui.column {
                icon.rebel()
                +"rebel"
            }

            ui.column {
                icon.redriver()
                +"redriver"
            }

            ui.column {
                icon.reddit()
                +"reddit"
            }

            ui.column {
                icon.reddit_alien()
                +"reddit alien"
            }

            ui.column {
                icon.reddit_square()
                +"reddit square"
            }

            ui.column {
                icon.rendact()
                +"rendact"
            }

            ui.column {
                icon.renren()
                +"renren"
            }

            ui.column {
                icon.replyd()
                +"replyd"
            }

            ui.column {
                icon.resolving()
                +"resolving"
            }

            ui.column {
                icon.rocketchat()
                +"rocketchat"
            }

            ui.column {
                icon.rockrms()
                +"rockrms"
            }

            ui.column {
                icon.safari()
                +"safari"
            }

            ui.column {
                icon.sass()
                +"sass"
            }

            ui.column {
                icon.schlix()
                +"schlix"
            }

            ui.column {
                icon.scribd()
                +"scribd"
            }

            ui.column {
                icon.searchengin()
                +"searchengin"
            }

            ui.column {
                icon.sellcast()
                +"sellcast"
            }

            ui.column {
                icon.sellsy()
                +"sellsy"
            }

            ui.column {
                icon.servicestack()
                +"servicestack"
            }

            ui.column {
                icon.shirtsinbulk()
                +"shirtsinbulk"
            }

            ui.column {
                icon.simplybuilt()
                +"simplybuilt"
            }

            ui.column {
                icon.sistrix()
                +"sistrix"
            }

            ui.column {
                icon.skyatlas()
                +"skyatlas"
            }

            ui.column {
                icon.skype()
                +"skype"
            }

            ui.column {
                icon.slack()
                +"slack"
            }

            ui.column {
                icon.slack_hash()
                +"slack hash"
            }

            ui.column {
                icon.slideshare()
                +"slideshare"
            }

            ui.column {
                icon.snapchat()
                +"snapchat"
            }

            ui.column {
                icon.snapchat_ghost()
                +"snapchat ghost"
            }

            ui.column {
                icon.snapchat_square()
                +"snapchat square"
            }

            ui.column {
                icon.soundcloud()
                +"soundcloud"
            }

            ui.column {
                icon.speakap()
                +"speakap"
            }

            ui.column {
                icon.spotify()
                +"spotify"
            }

            ui.column {
                icon.stack_exchange()
                +"stack exchange"
            }

            ui.column {
                icon.stack_overflow()
                +"stack overflow"
            }

            ui.column {
                icon.staylinked()
                +"staylinked"
            }

            ui.column {
                icon.steam()
                +"steam"
            }

            ui.column {
                icon.steam_square()
                +"steam square"
            }

            ui.column {
                icon.steam_symbol()
                +"steam symbol"
            }

            ui.column {
                icon.sticker_mule()
                +"sticker mule"
            }

            ui.column {
                icon.strava()
                +"strava"
            }

            ui.column {
                icon.stripe()
                +"stripe"
            }

            ui.column {
                icon.stripe_s()
                +"stripe s"
            }

            ui.column {
                icon.studiovinari()
                +"studiovinari"
            }

            ui.column {
                icon.stumbleupon()
                +"stumbleupon"
            }

            ui.column {
                icon.stumbleupon_circle()
                +"stumbleupon circle"
            }

            ui.column {
                icon.superpowers()
                +"superpowers"
            }

            ui.column {
                icon.supple()
                +"supple"
            }

            ui.column {
                icon.telegram()
                +"telegram"
            }

            ui.column {
                icon.telegram_plane()
                +"telegram plane"
            }

            ui.column {
                icon.tencent_weibo()
                +"tencent weibo"
            }

            ui.column {
                icon.themeisle()
                +"themeisle"
            }

            ui.column {
                icon.trello()
                +"trello"
            }

            ui.column {
                icon.tripadvisor()
                +"tripadvisor"
            }

            ui.column {
                icon.tumblr()
                +"tumblr"
            }

            ui.column {
                icon.tumblr_square()
                +"tumblr square"
            }

            ui.column {
                icon.twitch()
                +"twitch"
            }

            ui.column {
                icon.twitter()
                +"twitter"
            }

            ui.column {
                icon.twitter_square()
                +"twitter square"
            }

            ui.column {
                icon.typo3()
                +"typo3"
            }

            ui.column {
                icon.uber()
                +"uber"
            }

            ui.column {
                icon.uikit()
                +"uikit"
            }

            ui.column {
                icon.uniregistry()
                +"uniregistry"
            }

            ui.column {
                icon.untappd()
                +"untappd"
            }

            ui.column {
                icon.usb()
                +"usb"
            }

            ui.column {
                icon.ussunnah()
                +"ussunnah"
            }

            ui.column {
                icon.vaadin()
                +"vaadin"
            }

            ui.column {
                icon.viacoin()
                +"viacoin"
            }

            ui.column {
                icon.viadeo()
                +"viadeo"
            }

            ui.column {
                icon.viadeo_square()
                +"viadeo square"
            }

            ui.column {
                icon.viber()
                +"viber"
            }

            ui.column {
                icon.vimeo()
                +"vimeo"
            }

            ui.column {
                icon.vimeo_square()
                +"vimeo square"
            }

            ui.column {
                icon.vimeo_v()
                +"vimeo v"
            }

            ui.column {
                icon.vine()
                +"vine"
            }

            ui.column {
                icon.vk()
                +"vk"
            }

            ui.column {
                icon.vnv()
                +"vnv"
            }

            ui.column {
                icon.vuejs()
                +"vuejs"
            }

            ui.column {
                icon.wechat()
                +"wechat"
            }

            ui.column {
                icon.weibo()
                +"weibo"
            }

            ui.column {
                icon.weixin()
                +"weixin"
            }

            ui.column {
                icon.whatsapp()
                +"whatsapp"
            }

            ui.column {
                icon.whatsapp_square()
                +"whatsapp square"
            }

            ui.column {
                icon.whmcs()
                +"whmcs"
            }

            ui.column {
                icon.wikipedia_w()
                +"wikipedia w"
            }

            ui.column {
                icon.windows()
                +"windows"
            }

            ui.column {
                icon.wordpress()
                +"wordpress"
            }

            ui.column {
                icon.wordpress_simple()
                +"wordpress simple"
            }

            ui.column {
                icon.wpbeginner()
                +"wpbeginner"
            }

            ui.column {
                icon.wpexplorer()
                +"wpexplorer"
            }

            ui.column {
                icon.wpforms()
                +"wpforms"
            }

            ui.column {
                icon.xbox()
                +"xbox"
            }

            ui.column {
                icon.xing()
                +"xing"
            }

            ui.column {
                icon.xing_square()
                +"xing square"
            }

            ui.column {
                icon.y_combinator()
                +"y combinator"
            }

            ui.column {
                icon.yahoo()
                +"yahoo"
            }

            ui.column {
                icon.yandex()
                +"yandex"
            }

            ui.column {
                icon.yandex_international()
                +"yandex international"
            }

            ui.column {
                icon.yelp()
                +"yelp"
            }

            ui.column {
                icon.yoast()
                +"yoast"
            }

            ui.column {
                icon.youtube()
                +"youtube"
            }

            ui.column {
                icon.youtube_square()
                +"youtube square"
            }
        }
    }
}
