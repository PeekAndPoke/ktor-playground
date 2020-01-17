package de.peekandpoke.ktorfx.semanticui

import kotlinx.html.FlowContent
import kotlinx.html.i

@SemanticUiDslMarker val FlowContent.icon get() = SemanticIcon(this)

@Suppress("PropertyName", "FunctionName")
class SemanticIcon(private val parent: FlowContent) {

    private val cssClasses = mutableListOf<String>()

    private operator fun plus(cls: String) = apply { cssClasses.add(cls) }

    private fun render(classes: String): Unit = when {
        cssClasses.isEmpty() -> parent.i(classes = classes)

        else -> parent.i(classes = "${cssClasses.plus(classes).joinToString(" ")} $classes")
    }

    @SemanticUiConditionalMarker fun custom(cls: String) = render("$cls icon")

    @SemanticUiConditionalMarker fun with(cls: String) = this + cls

    // conditional classes

    @SemanticUiConditionalMarker fun given(condition: Boolean, action: SemanticIcon.() -> SemanticIcon) = when (condition) {
        false -> this
        else -> this.action()
    }

    @SemanticUiConditionalMarker val then get() = this

    // coloring ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker val red get() = this + "red"
    @SemanticUiCssMarker val orange get() = this + "orange"
    @SemanticUiCssMarker val yellow get() = this + "yellow"
    @SemanticUiCssMarker val olive get() = this + "olive"
    @SemanticUiCssMarker val green get() = this + "green"
    @SemanticUiCssMarker val teal get() = this + "teal"
    @SemanticUiCssMarker val blue get() = this + "blue"
    @SemanticUiCssMarker val violet get() = this + "violet"
    @SemanticUiCssMarker val purple get() = this + "purple"
    @SemanticUiCssMarker val pink get() = this + "pink"
    @SemanticUiCssMarker val brown get() = this + "brown"
    @SemanticUiCssMarker val grey get() = this + "grey"
    @SemanticUiCssMarker val black get() = this + "black"

    @SemanticUiCssMarker val inverted get() = this + "inverted"

    // Accessibility ///////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun american_sign_language_interpreting() = render("american sign language interpreting icon")

    @SemanticUiCssMarker fun assistive_listening_systems() = render("assistive listening systems icon")

    @SemanticUiCssMarker fun audio_description() = render("audio description icon")

    @SemanticUiCssMarker fun blind() = render("blind icon")

    @SemanticUiCssMarker fun braille() = render("braille icon")

    @SemanticUiCssMarker fun closed_captioning() = render("closed captioning icon")

    @SemanticUiCssMarker fun closed_captioning_outline() = render("closed captioning outline icon")

    @SemanticUiCssMarker fun deaf() = render("deaf icon")

    @SemanticUiCssMarker fun low_vision() = render("low vision icon")

    @SemanticUiCssMarker fun phone_volume() = render("phone volume icon")

    @SemanticUiCssMarker fun question_circle() = render("question circle icon")

    @SemanticUiCssMarker fun question_circle_outline() = render("question circle outline icon")

    @SemanticUiCssMarker fun sign_language() = render("sign language icon")

    @SemanticUiCssMarker fun tty() = render("tty icon")

    @SemanticUiCssMarker fun universal_access() = render("universal access icon")

    @SemanticUiCssMarker fun wheelchair() = render("wheelchair icon")

    // Arrows ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun angle_double_down() = render("angle double down icon")

    @SemanticUiCssMarker fun angle_double_left() = render("angle double left icon")

    @SemanticUiCssMarker fun angle_double_right() = render("angle double right icon")

    @SemanticUiCssMarker fun angle_double_up() = render("angle double up icon")

    @SemanticUiCssMarker fun angle_down() = render("angle down icon")

    @SemanticUiCssMarker fun angle_left() = render("angle left icon")

    @SemanticUiCssMarker fun angle_right() = render("angle right icon")

    @SemanticUiCssMarker fun angle_up() = render("angle up icon")

    @SemanticUiCssMarker fun arrow_alternate_circle_down() = render("arrow alternate circle down icon")

    @SemanticUiCssMarker fun arrow_alternate_circle_down_outline() = render("arrow alternate circle down outline icon")

    @SemanticUiCssMarker fun arrow_alternate_circle_left() = render("arrow alternate circle left icon")

    @SemanticUiCssMarker fun arrow_alternate_circle_left_outline() = render("arrow alternate circle left outline icon")

    @SemanticUiCssMarker fun arrow_alternate_circle_right() = render("arrow alternate circle right icon")

    @SemanticUiCssMarker fun arrow_alternate_circle_right_outline() = render("arrow alternate circle right outline icon")

    @SemanticUiCssMarker fun arrow_alternate_circle_up() = render("arrow alternate circle up icon")

    @SemanticUiCssMarker fun arrow_alternate_circle_up_outline() = render("arrow alternate circle up outline icon")

    @SemanticUiCssMarker fun arrow_circle_down() = render("arrow circle down icon")

    @SemanticUiCssMarker fun arrow_circle_left() = render("arrow circle left icon")

    @SemanticUiCssMarker fun arrow_circle_right() = render("arrow circle right icon")

    @SemanticUiCssMarker fun arrow_circle_up() = render("arrow circle up icon")

    @SemanticUiCssMarker fun arrow_down() = render("arrow down icon")

    @SemanticUiCssMarker fun arrow_left() = render("arrow left icon")

    @SemanticUiCssMarker fun arrow_right() = render("arrow right icon")

    @SemanticUiCssMarker fun arrow_up() = render("arrow up icon")

    @SemanticUiCssMarker fun arrows_alternate() = render("arrows alternate icon")

    @SemanticUiCssMarker fun arrows_alternate_horizontal() = render("arrows alternate horizontal icon")

    @SemanticUiCssMarker fun arrows_alternate_vertical() = render("arrows alternate vertical icon")

    @SemanticUiCssMarker fun caret_down() = render("caret down icon")

    @SemanticUiCssMarker fun caret_left() = render("caret left icon")

    @SemanticUiCssMarker fun caret_right() = render("caret right icon")

    @SemanticUiCssMarker fun caret_square_down() = render("caret square down icon")

    @SemanticUiCssMarker fun caret_square_down_outline() = render("caret square down outline icon")

    @SemanticUiCssMarker fun caret_square_left() = render("caret square left icon")

    @SemanticUiCssMarker fun caret_square_left_outline() = render("caret square left outline icon")

    @SemanticUiCssMarker fun caret_square_right() = render("caret square right icon")

    @SemanticUiCssMarker fun caret_square_right_outline() = render("caret square right outline icon")

    @SemanticUiCssMarker fun caret_square_up() = render("caret square up icon")

    @SemanticUiCssMarker fun caret_square_up_outline() = render("caret square up outline icon")

    @SemanticUiCssMarker fun caret_up() = render("caret up icon")

    @SemanticUiCssMarker fun cart_arrow_down() = render("cart arrow down icon")

    @SemanticUiCssMarker fun chart_line() = render("chart line icon")

    @SemanticUiCssMarker fun chevron_circle_down() = render("chevron circle down icon")

    @SemanticUiCssMarker fun chevron_circle_left() = render("chevron circle left icon")

    @SemanticUiCssMarker fun chevron_circle_right() = render("chevron circle right icon")

    @SemanticUiCssMarker fun chevron_circle_up() = render("chevron circle up icon")

    @SemanticUiCssMarker fun chevron_down() = render("chevron down icon")

    @SemanticUiCssMarker fun chevron_left() = render("chevron left icon")

    @SemanticUiCssMarker fun chevron_right() = render("chevron right icon")

    @SemanticUiCssMarker fun chevron_up() = render("chevron up icon")

    @SemanticUiCssMarker fun cloud_download() = render("cloud download icon")

    @SemanticUiCssMarker fun cloud_upload() = render("cloud upload icon")

    @SemanticUiCssMarker fun download() = render("download icon")

    @SemanticUiCssMarker fun exchange_alternate() = render("exchange alternate icon")

    @SemanticUiCssMarker fun expand_arrows_alternate() = render("expand arrows alternate icon")

    @SemanticUiCssMarker fun external_alternate() = render("external alternate icon")

    @SemanticUiCssMarker fun external_square_alternate() = render("external square alternate icon")

    @SemanticUiCssMarker fun hand_point_down() = render("hand point down icon")

    @SemanticUiCssMarker fun hand_point_down_outline() = render("hand point down outline icon")

    @SemanticUiCssMarker fun hand_point_left() = render("hand point left icon")

    @SemanticUiCssMarker fun hand_point_left_outline() = render("hand point left outline icon")

    @SemanticUiCssMarker fun hand_point_right() = render("hand point right icon")

    @SemanticUiCssMarker fun hand_point_right_outline() = render("hand point right outline icon")

    @SemanticUiCssMarker fun hand_point_up() = render("hand point up icon")

    @SemanticUiCssMarker fun hand_point_up_outline() = render("hand point up outline icon")

    @SemanticUiCssMarker fun hand_pointer() = render("hand pointer icon")

    @SemanticUiCssMarker fun hand_pointer_outline() = render("hand pointer outline icon")

    @SemanticUiCssMarker fun history() = render("history icon")

    @SemanticUiCssMarker fun level_down_alternate() = render("level down alternate icon")

    @SemanticUiCssMarker fun level_up_alternate() = render("level up alternate icon")

    @SemanticUiCssMarker fun location_arrow() = render("location arrow icon")

    @SemanticUiCssMarker fun long_arrow_alternate_down() = render("long arrow alternate down icon")

    @SemanticUiCssMarker fun long_arrow_alternate_left() = render("long arrow alternate left icon")

    @SemanticUiCssMarker fun long_arrow_alternate_right() = render("long arrow alternate right icon")

    @SemanticUiCssMarker fun long_arrow_alternate_up() = render("long arrow alternate up icon")

    @SemanticUiCssMarker fun mouse_pointer() = render("mouse pointer icon")

    @SemanticUiCssMarker fun play() = render("play icon")

    @SemanticUiCssMarker fun random() = render("random icon")

    @SemanticUiCssMarker fun recycle() = render("recycle icon")

    @SemanticUiCssMarker fun redo() = render("redo icon")

    @SemanticUiCssMarker fun redo_alternate() = render("redo alternate icon")

    @SemanticUiCssMarker fun reply() = render("reply icon")

    @SemanticUiCssMarker fun reply_all() = render("reply all icon")

    @SemanticUiCssMarker fun retweet() = render("retweet icon")

    @SemanticUiCssMarker fun share() = render("share icon")

    @SemanticUiCssMarker fun share_square() = render("share square icon")

    @SemanticUiCssMarker fun share_square_outline() = render("share square outline icon")

    @SemanticUiCssMarker fun sign_in_alternate() = render("sign in alternate icon")

    @SemanticUiCssMarker fun sign_out_alternate() = render("sign out alternate icon")

    @SemanticUiCssMarker fun sort() = render("sort icon")

    @SemanticUiCssMarker fun sort_alphabet_down() = render("sort alphabet down icon")

    @SemanticUiCssMarker fun sort_alphabet_up() = render("sort alphabet up icon")

    @SemanticUiCssMarker fun sort_amount_down() = render("sort amount down icon")

    @SemanticUiCssMarker fun sort_amount_up() = render("sort amount up icon")

    @SemanticUiCssMarker fun sort_down() = render("sort down icon")

    @SemanticUiCssMarker fun sort_numeric_down() = render("sort numeric down icon")

    @SemanticUiCssMarker fun sort_numeric_up() = render("sort numeric up icon")

    @SemanticUiCssMarker fun sort_up() = render("sort up icon")

    @SemanticUiCssMarker fun sync() = render("sync icon")

    @SemanticUiCssMarker fun sync_alternate() = render("sync alternate icon")

    @SemanticUiCssMarker fun text_height() = render("text height icon")

    @SemanticUiCssMarker fun text_width() = render("text width icon")

    @SemanticUiCssMarker fun undo() = render("undo icon")

    @SemanticUiCssMarker fun undo_alternate() = render("undo alternate icon")

    @SemanticUiCssMarker fun upload() = render("upload icon")


    // Audio and Video ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun backward() = render("backward icon")

    @SemanticUiCssMarker fun circle() = render("circle icon")

    @SemanticUiCssMarker fun circle_outline() = render("circle outline icon")

    @SemanticUiCssMarker fun compress() = render("compress icon")

    @SemanticUiCssMarker fun eject() = render("eject icon")

    @SemanticUiCssMarker fun expand() = render("expand icon")

    @SemanticUiCssMarker fun fast_backward() = render("fast backward icon")

    @SemanticUiCssMarker fun fast_forward() = render("fast forward icon")

    @SemanticUiCssMarker fun file_audio() = render("file audio icon")

    @SemanticUiCssMarker fun file_audio_outline() = render("file audio outline icon")

    @SemanticUiCssMarker fun file_video() = render("file video icon")

    @SemanticUiCssMarker fun file_video_outline() = render("file video outline icon")

    @SemanticUiCssMarker fun film() = render("film icon")

    @SemanticUiCssMarker fun forward() = render("forward icon")

    @SemanticUiCssMarker fun headphones() = render("headphones icon")

    @SemanticUiCssMarker fun microphone() = render("microphone icon")

    @SemanticUiCssMarker fun microphone_slash() = render("microphone slash icon")

    @SemanticUiCssMarker fun music() = render("music icon")

    @SemanticUiCssMarker fun pause() = render("pause icon")

    @SemanticUiCssMarker fun pause_circle() = render("pause circle icon")

    @SemanticUiCssMarker fun pause_circle_outline() = render("pause circle outline icon")

    @SemanticUiCssMarker fun play_circle() = render("play circle icon")

    @SemanticUiCssMarker fun play_circle_outline() = render("play circle outline icon")

    @SemanticUiCssMarker fun podcast() = render("podcast icon")

    @SemanticUiCssMarker fun rss() = render("rss icon")

    @SemanticUiCssMarker fun rss_square() = render("rss square icon")

    @SemanticUiCssMarker fun step_backward() = render("step backward icon")

    @SemanticUiCssMarker fun step_forward() = render("step forward icon")

    @SemanticUiCssMarker fun stop() = render("stop icon")

    @SemanticUiCssMarker fun stop_circle() = render("stop circle icon")

    @SemanticUiCssMarker fun stop_circle_outline() = render("stop circle outline icon")

    @SemanticUiCssMarker fun video() = render("video icon")

    @SemanticUiCssMarker fun volume_down() = render("volume down icon")

    @SemanticUiCssMarker fun volume_off() = render("volume off icon")

    @SemanticUiCssMarker fun volume_up() = render("volume up icon")


    // Business //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun address_book() = render("address book icon")

    @SemanticUiCssMarker fun address_book_outline() = render("address book outline icon")

    @SemanticUiCssMarker fun address_card() = render("address card icon")

    @SemanticUiCssMarker fun address_card_outline() = render("address card outline icon")

    @SemanticUiCssMarker fun archive() = render("archive icon")

    @SemanticUiCssMarker fun balance_scale() = render("balance scale icon")

    @SemanticUiCssMarker fun birthday_cake() = render("birthday cake icon")

    @SemanticUiCssMarker fun book() = render("book icon")

    @SemanticUiCssMarker fun briefcase() = render("briefcase icon")

    @SemanticUiCssMarker fun building() = render("building icon")

    @SemanticUiCssMarker fun building_outline() = render("building outline icon")

    @SemanticUiCssMarker fun bullhorn() = render("bullhorn icon")

    @SemanticUiCssMarker fun bullseye() = render("bullseye icon")

    @SemanticUiCssMarker fun calculator() = render("calculator icon")

    @SemanticUiCssMarker fun calendar() = render("calendar icon")

    @SemanticUiCssMarker fun calendar_outline() = render("calendar outline icon")

    @SemanticUiCssMarker fun calendar_alternate() = render("calendar alternate icon")

    @SemanticUiCssMarker fun calendar_alternate_outline() = render("calendar alternate outline icon")

    @SemanticUiCssMarker fun certificate() = render("certificate icon")

    @SemanticUiCssMarker fun chart_area() = render("chart area icon")

    @SemanticUiCssMarker fun chart_bar() = render("chart bar icon")

    @SemanticUiCssMarker fun chart_bar_outline() = render("chart bar outline icon")

    @SemanticUiCssMarker fun chart_pie() = render("chart pie icon")

    @SemanticUiCssMarker fun clipboard() = render("clipboard icon")

    @SemanticUiCssMarker fun clipboard_outline() = render("clipboard outline icon")

    @SemanticUiCssMarker fun coffee() = render("coffee icon")

    @SemanticUiCssMarker fun columns() = render("columns icon")

    @SemanticUiCssMarker fun compass() = render("compass icon")

    @SemanticUiCssMarker fun compass_outline() = render("compass outline icon")

    @SemanticUiCssMarker fun copy() = render("copy icon")

    @SemanticUiCssMarker fun copy_outline() = render("copy outline icon")

    @SemanticUiCssMarker fun copyright() = render("copyright icon")

    @SemanticUiCssMarker fun copyright_outline() = render("copyright outline icon")

    @SemanticUiCssMarker fun cut() = render("cut icon")

    @SemanticUiCssMarker fun edit() = render("edit icon")

    @SemanticUiCssMarker fun edit_outline() = render("edit outline icon")

    @SemanticUiCssMarker fun envelope() = render("envelope icon")

    @SemanticUiCssMarker fun envelope_outline() = render("envelope outline icon")

    @SemanticUiCssMarker fun envelope_open() = render("envelope open icon")

    @SemanticUiCssMarker fun envelope_open_outline() = render("envelope open outline icon")

    @SemanticUiCssMarker fun envelope_square() = render("envelope square icon")

    @SemanticUiCssMarker fun eraser() = render("eraser icon")

    @SemanticUiCssMarker fun fax() = render("fax icon")

    @SemanticUiCssMarker fun file() = render("file icon")

    @SemanticUiCssMarker fun file_outline() = render("file outline icon")

    @SemanticUiCssMarker fun file_alternate() = render("file alternate icon")

    @SemanticUiCssMarker fun file_alternate_outline() = render("file alternate outline icon")

    @SemanticUiCssMarker fun folder() = render("folder icon")

    @SemanticUiCssMarker fun folder_outline() = render("folder outline icon")

    @SemanticUiCssMarker fun folder_open() = render("folder open icon")

    @SemanticUiCssMarker fun folder_open_outline() = render("folder open outline icon")

    @SemanticUiCssMarker fun globe() = render("globe icon")

    @SemanticUiCssMarker fun industry() = render("industry icon")

    @SemanticUiCssMarker fun paperclip() = render("paperclip icon")

    @SemanticUiCssMarker fun paste() = render("paste icon")

    @SemanticUiCssMarker fun pen_square() = render("pen square icon")

    @SemanticUiCssMarker fun pencil_alternate() = render("pencil alternate icon")

    @SemanticUiCssMarker fun percent() = render("percent icon")

    @SemanticUiCssMarker fun phone() = render("phone icon")

    @SemanticUiCssMarker fun phone_square() = render("phone square icon")

    @SemanticUiCssMarker fun registered() = render("registered icon")

    @SemanticUiCssMarker fun registered_outline() = render("registered outline icon")

    @SemanticUiCssMarker fun save() = render("save icon")

    @SemanticUiCssMarker fun save_outline() = render("save outline icon")

    @SemanticUiCssMarker fun sitemap() = render("sitemap icon")

    @SemanticUiCssMarker fun sticky_note() = render("sticky note icon")

    @SemanticUiCssMarker fun sticky_note_outline() = render("sticky note outline icon")

    @SemanticUiCssMarker fun suitcase() = render("suitcase icon")

    @SemanticUiCssMarker fun table() = render("table icon")

    @SemanticUiCssMarker fun tag() = render("tag icon")

    @SemanticUiCssMarker fun tags() = render("tags icon")

    @SemanticUiCssMarker fun tasks() = render("tasks icon")

    @SemanticUiCssMarker fun thumbtack() = render("thumbtack icon")

    @SemanticUiCssMarker fun trademark() = render("trademark icon")


    // Code ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun barcode() = render("barcode icon")

    @SemanticUiCssMarker fun bath() = render("bath icon")

    @SemanticUiCssMarker fun bug() = render("bug icon")

    @SemanticUiCssMarker fun code() = render("code icon")

    @SemanticUiCssMarker fun code_branch() = render("code branch icon")

    @SemanticUiCssMarker fun file_code() = render("file code icon")

    @SemanticUiCssMarker fun file_code_outline() = render("file code outline icon")

    @SemanticUiCssMarker fun filter() = render("filter icon")

    @SemanticUiCssMarker fun fire_extinguisher() = render("fire extinguisher icon")

    @SemanticUiCssMarker fun fork() = render("fork icon")

    @SemanticUiCssMarker fun qrcode() = render("qrcode icon")

    @SemanticUiCssMarker fun shield_alternate() = render("shield alternate icon")

    @SemanticUiCssMarker fun terminal() = render("terminal icon")

    @SemanticUiCssMarker fun user_secret() = render("user secret icon")

    @SemanticUiCssMarker fun window_close() = render("window close icon")

    @SemanticUiCssMarker fun window_close_outline() = render("window close outline icon")

    @SemanticUiCssMarker fun window_maximize() = render("window maximize icon")

    @SemanticUiCssMarker fun window_maximize_outline() = render("window maximize outline icon")

    @SemanticUiCssMarker fun window_minimize() = render("window minimize icon")

    @SemanticUiCssMarker fun window_minimize_outline() = render("window minimize outline icon")

    @SemanticUiCssMarker fun window_restore() = render("window restore icon")

    @SemanticUiCssMarker fun window_restore_outline() = render("window restore outline icon")


    // Communication /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun at() = render("at icon")

    @SemanticUiCssMarker fun bell() = render("bell icon")

    @SemanticUiCssMarker fun bell_outline() = render("bell outline icon")

    @SemanticUiCssMarker fun bell_slash() = render("bell slash icon")

    @SemanticUiCssMarker fun bell_slash_outline() = render("bell slash outline icon")

    @SemanticUiCssMarker fun comment() = render("comment icon")

    @SemanticUiCssMarker fun comment_outline() = render("comment outline icon")

    @SemanticUiCssMarker fun comment_alternate() = render("comment alternate icon")

    @SemanticUiCssMarker fun comment_alternate_outline() = render("comment alternate outline icon")

    @SemanticUiCssMarker fun comments() = render("comments icon")

    @SemanticUiCssMarker fun comments_outline() = render("comments outline icon")

    @SemanticUiCssMarker fun inbox() = render("inbox icon")

    @SemanticUiCssMarker fun language() = render("language icon")

    @SemanticUiCssMarker fun mobile() = render("mobile icon")

    @SemanticUiCssMarker fun mobile_alternate() = render("mobile alternate icon")

    @SemanticUiCssMarker fun paper_plane() = render("paper plane icon")

    @SemanticUiCssMarker fun paper_plane_outline() = render("paper plane outline icon")

    @SemanticUiCssMarker fun wifi() = render("wifi icon")


    // Computers ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun desktop() = render("desktop icon")

    @SemanticUiCssMarker fun hdd() = render("hdd icon")

    @SemanticUiCssMarker fun hdd_outline() = render("hdd outline icon")

    @SemanticUiCssMarker fun keyboard() = render("keyboard icon")

    @SemanticUiCssMarker fun keyboard_outline() = render("keyboard outline icon")

    @SemanticUiCssMarker fun laptop() = render("laptop icon")

    @SemanticUiCssMarker fun microchip() = render("microchip icon")

    @SemanticUiCssMarker fun plug() = render("plug icon")

    @SemanticUiCssMarker fun power_off() = render("power off icon")

    @SemanticUiCssMarker fun print() = render("print icon")

    @SemanticUiCssMarker fun server() = render("server icon")

    @SemanticUiCssMarker fun settings() = render("settings icon")

    @SemanticUiCssMarker fun tablet() = render("tablet icon")

    @SemanticUiCssMarker fun tablet_alternate() = render("tablet alternate icon")

    @SemanticUiCssMarker fun tv() = render("tv icon")


    // Currency ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun dollar_sign() = render("dollar sign icon")

    @SemanticUiCssMarker fun euro_sign() = render("euro sign icon")

    @SemanticUiCssMarker fun lira_sign() = render("lira sign icon")

    @SemanticUiCssMarker fun money_bill_alternate() = render("money bill alternate icon")

    @SemanticUiCssMarker fun money_bill_alternate_outline() = render("money bill alternate outline icon")

    @SemanticUiCssMarker fun pound_sign() = render("pound sign icon")

    @SemanticUiCssMarker fun ruble_sign() = render("ruble sign icon")

    @SemanticUiCssMarker fun rupee_sign() = render("rupee sign icon")

    @SemanticUiCssMarker fun shekel_sign() = render("shekel sign icon")

    @SemanticUiCssMarker fun won_sign() = render("won sign icon")

    @SemanticUiCssMarker fun yen_sign() = render("yen sign icon")


    // Date & Time //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun calendar_check() = render("calendar check icon")

    @SemanticUiCssMarker fun calendar_check_outline() = render("calendar check outline icon")

    @SemanticUiCssMarker fun calendar_minus() = render("calendar minus icon")

    @SemanticUiCssMarker fun calendar_minus_outline() = render("calendar minus outline icon")

    @SemanticUiCssMarker fun calendar_plus() = render("calendar plus icon")

    @SemanticUiCssMarker fun calendar_plus_outline() = render("calendar plus outline icon")

    @SemanticUiCssMarker fun calendar_times() = render("calendar times icon")

    @SemanticUiCssMarker fun calendar_times_outline() = render("calendar times outline icon")

    @SemanticUiCssMarker fun clock() = render("clock icon")

    @SemanticUiCssMarker fun clock_outline() = render("clock outline icon")

    @SemanticUiCssMarker fun hourglass() = render("hourglass icon")

    @SemanticUiCssMarker fun hourglass_outline() = render("hourglass outline icon")

    @SemanticUiCssMarker fun hourglass_end() = render("hourglass end icon")

    @SemanticUiCssMarker fun hourglass_half() = render("hourglass half icon")

    @SemanticUiCssMarker fun hourglass_start() = render("hourglass start icon")

    @SemanticUiCssMarker fun stopwatch() = render("stopwatch icon")


    // Design ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun adjust() = render("adjust icon")

    @SemanticUiCssMarker fun clone() = render("clone icon")

    @SemanticUiCssMarker fun clone_outline() = render("clone outline icon")

    @SemanticUiCssMarker fun crop() = render("crop icon")

    @SemanticUiCssMarker fun crosshairs() = render("crosshairs icon")

    @SemanticUiCssMarker fun dropdown() = render("dropdown icon")

    @SemanticUiCssMarker fun eye() = render("eye icon")

    @SemanticUiCssMarker fun eye_dropper() = render("eye dropper icon")

    @SemanticUiCssMarker fun eye_slash() = render("eye slash icon")

    @SemanticUiCssMarker fun eye_slash_outline() = render("eye slash outline icon")

    @SemanticUiCssMarker fun object_group() = render("object group icon")

    @SemanticUiCssMarker fun object_group_outline() = render("object group outline icon")

    @SemanticUiCssMarker fun object_ungroup() = render("object ungroup icon")

    @SemanticUiCssMarker fun object_ungroup_outline() = render("object ungroup outline icon")

    @SemanticUiCssMarker fun paint_brush() = render("paint brush icon")

    @SemanticUiCssMarker fun tint() = render("tint icon")


    // Editors //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun align_center() = render("align center icon")

    @SemanticUiCssMarker fun align_justify() = render("align justify icon")

    @SemanticUiCssMarker fun align_left() = render("align left icon")

    @SemanticUiCssMarker fun align_right() = render("align right icon")

    @SemanticUiCssMarker fun bold() = render("bold icon")

    @SemanticUiCssMarker fun font() = render("font icon")

    @SemanticUiCssMarker fun heading() = render("heading icon")

    @SemanticUiCssMarker fun i_cursor() = render("i cursor icon")

    @SemanticUiCssMarker fun indent() = render("indent icon")

    @SemanticUiCssMarker fun italic() = render("italic icon")

    @SemanticUiCssMarker fun linkify() = render("linkify icon")

    @SemanticUiCssMarker fun list() = render("list icon")

    @SemanticUiCssMarker fun list_alternate() = render("list alternate icon")

    @SemanticUiCssMarker fun list_alternate_outline() = render("list alternate outline icon")

    @SemanticUiCssMarker fun list_ol() = render("list ol icon")

    @SemanticUiCssMarker fun list_ul() = render("list ul icon")

    @SemanticUiCssMarker fun outdent() = render("outdent icon")

    @SemanticUiCssMarker fun paragraph() = render("paragraph icon")

    @SemanticUiCssMarker fun quote_left() = render("quote left icon")

    @SemanticUiCssMarker fun quote_right() = render("quote right icon")

    @SemanticUiCssMarker fun strikethrough() = render("strikethrough icon")

    @SemanticUiCssMarker fun subscript() = render("subscript icon")

    @SemanticUiCssMarker fun superscript() = render("superscript icon")

    @SemanticUiCssMarker fun th() = render("th icon")

    @SemanticUiCssMarker fun th_large() = render("th large icon")

    @SemanticUiCssMarker fun th_list() = render("th list icon")

    @SemanticUiCssMarker fun trash() = render("trash icon")

    @SemanticUiCssMarker fun trash_alternate() = render("trash alternate icon")

    @SemanticUiCssMarker fun trash_alternate_outline() = render("trash alternate outline icon")

    @SemanticUiCssMarker fun underline() = render("underline icon")

    @SemanticUiCssMarker fun unlink() = render("unlink icon")


    // Files ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun file_archive() = render("file archive icon")

    @SemanticUiCssMarker fun file_archive_outline() = render("file archive outline icon")

    @SemanticUiCssMarker fun file_excel() = render("file excel icon")

    @SemanticUiCssMarker fun file_excel_outline() = render("file excel outline icon")

    @SemanticUiCssMarker fun file_image() = render("file image icon")

    @SemanticUiCssMarker fun file_image_outline() = render("file image outline icon")

    @SemanticUiCssMarker fun file_pdf() = render("file pdf icon")

    @SemanticUiCssMarker fun file_pdf_outline() = render("file pdf outline icon")

    @SemanticUiCssMarker fun file_powerpoint() = render("file powerpoint icon")

    @SemanticUiCssMarker fun file_powerpoint_outline() = render("file powerpoint outline icon")

    @SemanticUiCssMarker fun file_word() = render("file word icon")

    @SemanticUiCssMarker fun file_word_outline() = render("file word outline icon")


    // Genders //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun genderless() = render("genderless icon")

    @SemanticUiCssMarker fun mars() = render("mars icon")

    @SemanticUiCssMarker fun mars_double() = render("mars double icon")

    @SemanticUiCssMarker fun mars_stroke() = render("mars stroke icon")

    @SemanticUiCssMarker fun mars_stroke_horizontal() = render("mars stroke horizontal icon")

    @SemanticUiCssMarker fun mars_stroke_vertical() = render("mars stroke vertical icon")

    @SemanticUiCssMarker fun mercury() = render("mercury icon")

    @SemanticUiCssMarker fun neuter() = render("neuter icon")

    @SemanticUiCssMarker fun transgender() = render("transgender icon")

    @SemanticUiCssMarker fun transgender_alternate() = render("transgender alternate icon")

    @SemanticUiCssMarker fun venus() = render("venus icon")

    @SemanticUiCssMarker fun venus_double() = render("venus double icon")

    @SemanticUiCssMarker fun venus_mars() = render("venus mars icon")


    // Hands & Gestures /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun hand_lizard() = render("hand lizard icon")

    @SemanticUiCssMarker fun hand_lizard_outline() = render("hand lizard outline icon")

    @SemanticUiCssMarker fun hand_paper() = render("hand paper icon")

    @SemanticUiCssMarker fun hand_paper_outline() = render("hand paper outline icon")

    @SemanticUiCssMarker fun hand_peace() = render("hand peace icon")

    @SemanticUiCssMarker fun hand_peace_outline() = render("hand peace outline icon")

    @SemanticUiCssMarker fun hand_rock() = render("hand rock icon")

    @SemanticUiCssMarker fun hand_rock_outline() = render("hand rock outline icon")

    @SemanticUiCssMarker fun hand_scissors() = render("hand scissors icon")

    @SemanticUiCssMarker fun hand_scissors_outline() = render("hand scissors outline icon")

    @SemanticUiCssMarker fun hand_spock() = render("hand spock icon")

    @SemanticUiCssMarker fun hand_spock_outline() = render("hand spock outline icon")

    @SemanticUiCssMarker fun handshake() = render("handshake icon")

    @SemanticUiCssMarker fun handshake_outline() = render("handshake outline icon")

    @SemanticUiCssMarker fun thumbs_down() = render("thumbs down icon")

    @SemanticUiCssMarker fun thumbs_down_outline() = render("thumbs down outline icon")

    @SemanticUiCssMarker fun thumbs_up() = render("thumbs up icon")

    @SemanticUiCssMarker fun thumbs_up_outline() = render("thumbs up outline icon")


    // Health ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun ambulance() = render("ambulance icon")

    @SemanticUiCssMarker fun h_square() = render("h square icon")

    @SemanticUiCssMarker fun heart() = render("heart icon")

    @SemanticUiCssMarker fun heart_outline() = render("heart outline icon")

    @SemanticUiCssMarker fun heartbeat() = render("heartbeat icon")

    @SemanticUiCssMarker fun hospital() = render("hospital icon")

    @SemanticUiCssMarker fun hospital_outline() = render("hospital outline icon")

    @SemanticUiCssMarker fun medkit() = render("medkit icon")

    @SemanticUiCssMarker fun plus_square() = render("plus square icon")

    @SemanticUiCssMarker fun plus_square_outline() = render("plus square outline icon")

    @SemanticUiCssMarker fun stethoscope() = render("stethoscope icon")

    @SemanticUiCssMarker fun user_md() = render("user md icon")


    // Images ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun bolt() = render("bolt icon")

    @SemanticUiCssMarker fun camera() = render("camera icon")

    @SemanticUiCssMarker fun camera_retro() = render("camera retro icon")

    @SemanticUiCssMarker fun id_badge() = render("id badge icon")

    @SemanticUiCssMarker fun id_badge_outline() = render("id badge outline icon")

    @SemanticUiCssMarker fun id_card() = render("id card icon")

    @SemanticUiCssMarker fun id_card_outline() = render("id card outline icon")

    @SemanticUiCssMarker fun image() = render("image icon")

    @SemanticUiCssMarker fun image_outline() = render("image outline icon")

    @SemanticUiCssMarker fun images() = render("images icon")

    @SemanticUiCssMarker fun images_outline() = render("images outline icon")

    @SemanticUiCssMarker fun sliders_horizontal() = render("sliders horizontal icon")


    // Interfaces ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun ban() = render("ban icon")

    @SemanticUiCssMarker fun bars() = render("bars icon")

    @SemanticUiCssMarker fun beer() = render("beer icon")

    @SemanticUiCssMarker fun check() = render("check icon")

    @SemanticUiCssMarker fun check_circle() = render("check circle icon")

    @SemanticUiCssMarker fun check_circle_outline() = render("check circle outline icon")

    @SemanticUiCssMarker fun check_square() = render("check square icon")

    @SemanticUiCssMarker fun check_square_outline() = render("check square outline icon")

    @SemanticUiCssMarker fun close() = render("close icon")

    @SemanticUiCssMarker fun cloud() = render("cloud icon")

    @SemanticUiCssMarker fun cog() = render("cog icon")

    @SemanticUiCssMarker fun cogs() = render("cogs icon")

    @SemanticUiCssMarker fun database() = render("database icon")

    @SemanticUiCssMarker fun dot_circle() = render("dot circle icon")

    @SemanticUiCssMarker fun dot_circle_outline() = render("dot circle outline icon")

    @SemanticUiCssMarker fun ellipsis_horizontal() = render("ellipsis horizontal icon")

    @SemanticUiCssMarker fun ellipsis_vertical() = render("ellipsis vertical icon")

    @SemanticUiCssMarker fun exclamation() = render("exclamation icon")

    @SemanticUiCssMarker fun exclamation_circle() = render("exclamation circle icon")

    @SemanticUiCssMarker fun exclamation_triangle() = render("exclamation triangle icon")

    @SemanticUiCssMarker fun flag() = render("flag icon")

    @SemanticUiCssMarker fun flag_outline() = render("flag outline icon")

    @SemanticUiCssMarker fun flag_checkered() = render("flag checkered icon")

    @SemanticUiCssMarker fun frown() = render("frown icon")

    @SemanticUiCssMarker fun frown_outline() = render("frown outline icon")

    @SemanticUiCssMarker fun hashtag() = render("hashtag icon")

    @SemanticUiCssMarker fun home() = render("home icon")

    @SemanticUiCssMarker fun info() = render("info icon")

    @SemanticUiCssMarker fun info_circle() = render("info circle icon")

    @SemanticUiCssMarker fun magic() = render("magic icon")

    @SemanticUiCssMarker fun meh() = render("meh icon")

    @SemanticUiCssMarker fun meh_outline() = render("meh outline icon")

    @SemanticUiCssMarker fun minus() = render("minus icon")

    @SemanticUiCssMarker fun minus_circle() = render("minus circle icon")

    @SemanticUiCssMarker fun minus_square() = render("minus square icon")

    @SemanticUiCssMarker fun minus_square_outline() = render("minus square outline icon")

    @SemanticUiCssMarker fun plus() = render("plus icon")

    @SemanticUiCssMarker fun plus_circle() = render("plus circle icon")

    @SemanticUiCssMarker fun question() = render("question icon")

    @SemanticUiCssMarker fun search() = render("search icon")

    @SemanticUiCssMarker fun search_minus() = render("search minus icon")

    @SemanticUiCssMarker fun search_plus() = render("search plus icon")

    @SemanticUiCssMarker fun share_alternate() = render("share alternate icon")

    @SemanticUiCssMarker fun share_alternate_square() = render("share alternate square icon")

    @SemanticUiCssMarker fun signal() = render("signal icon")

    @SemanticUiCssMarker fun smile() = render("smile icon")

    @SemanticUiCssMarker fun smile_outline() = render("smile outline icon")

    @SemanticUiCssMarker fun star() = render("star icon")

    @SemanticUiCssMarker fun star_outline() = render("star outline icon")

    @SemanticUiCssMarker fun star_half() = render("star half icon")

    @SemanticUiCssMarker fun star_half_outline() = render("star half outline icon")

    @SemanticUiCssMarker fun times() = render("times icon")

    @SemanticUiCssMarker fun times_circle() = render("times circle icon")

    @SemanticUiCssMarker fun times_circle_outline() = render("times circle outline icon")

    @SemanticUiCssMarker fun toggle_off() = render("toggle off icon")

    @SemanticUiCssMarker fun toggle_on() = render("toggle on icon")

    @SemanticUiCssMarker fun trophy() = render("trophy icon")

    @SemanticUiCssMarker fun user() = render("user icon")

    @SemanticUiCssMarker fun user_outline() = render("user outline icon")

    @SemanticUiCssMarker fun user_circle() = render("user circle icon")

    @SemanticUiCssMarker fun user_circle_outline() = render("user circle outline icon")


    // Logistics ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun box() = render("box icon")

    @SemanticUiCssMarker fun boxes() = render("boxes icon")

    @SemanticUiCssMarker fun clipboard_check() = render("clipboard check icon")

    @SemanticUiCssMarker fun clipboard_list() = render("clipboard list icon")

    @SemanticUiCssMarker fun dolly() = render("dolly icon")

    @SemanticUiCssMarker fun dolly_flatbed() = render("dolly flatbed icon")

    @SemanticUiCssMarker fun pallet() = render("pallet icon")

    @SemanticUiCssMarker fun shipping_fast() = render("shipping fast icon")

    @SemanticUiCssMarker fun truck() = render("truck icon")

    @SemanticUiCssMarker fun warehouse() = render("warehouse icon")


    // Maps /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun anchor() = render("anchor icon")

    @SemanticUiCssMarker fun bed() = render("bed icon")

    @SemanticUiCssMarker fun bicycle() = render("bicycle icon")

    @SemanticUiCssMarker fun binoculars() = render("binoculars icon")

    @SemanticUiCssMarker fun bomb() = render("bomb icon")

    @SemanticUiCssMarker fun bookmark() = render("bookmark icon")

    @SemanticUiCssMarker fun bookmark_outline() = render("bookmark outline icon")

    @SemanticUiCssMarker fun car() = render("car icon")

    @SemanticUiCssMarker fun fighter_jet() = render("fighter jet icon")

    @SemanticUiCssMarker fun fire() = render("fire icon")

    @SemanticUiCssMarker fun flask() = render("flask icon")

    @SemanticUiCssMarker fun gamepad() = render("gamepad icon")

    @SemanticUiCssMarker fun gavel() = render("gavel icon")

    @SemanticUiCssMarker fun gift() = render("gift icon")

    @SemanticUiCssMarker fun glass_martini() = render("glass martini icon")

    @SemanticUiCssMarker fun graduation_cap() = render("graduation cap icon")

    @SemanticUiCssMarker fun key() = render("key icon")

    @SemanticUiCssMarker fun leaf() = render("leaf icon")

    @SemanticUiCssMarker fun lemon() = render("lemon icon")

    @SemanticUiCssMarker fun lemon_outline() = render("lemon outline icon")

    @SemanticUiCssMarker fun life_ring() = render("life ring icon")

    @SemanticUiCssMarker fun life_ring_outline() = render("life ring outline icon")

    @SemanticUiCssMarker fun lightbulb() = render("lightbulb icon")

    @SemanticUiCssMarker fun lightbulb_outline() = render("lightbulb outline icon")

    @SemanticUiCssMarker fun magnet() = render("magnet icon")

    @SemanticUiCssMarker fun male() = render("male icon")

    @SemanticUiCssMarker fun map() = render("map icon")

    @SemanticUiCssMarker fun map_outline() = render("map outline icon")

    @SemanticUiCssMarker fun map_marker() = render("map marker icon")

    @SemanticUiCssMarker fun map_marker_alternate() = render("map marker alternate icon")

    @SemanticUiCssMarker fun map_pin() = render("map pin icon")

    @SemanticUiCssMarker fun map_signs() = render("map signs icon")

    @SemanticUiCssMarker fun motorcycle() = render("motorcycle icon")

    @SemanticUiCssMarker fun newspaper() = render("newspaper icon")

    @SemanticUiCssMarker fun newspaper_outline() = render("newspaper outline icon")

    @SemanticUiCssMarker fun paw() = render("paw icon")

    @SemanticUiCssMarker fun plane() = render("plane icon")

    @SemanticUiCssMarker fun road() = render("road icon")

    @SemanticUiCssMarker fun rocket() = render("rocket icon")

    @SemanticUiCssMarker fun ship() = render("ship icon")

    @SemanticUiCssMarker fun shopping_bag() = render("shopping bag icon")

    @SemanticUiCssMarker fun shopping_basket() = render("shopping basket icon")

    @SemanticUiCssMarker fun shop() = render("shop icon")

    @SemanticUiCssMarker fun shopping_cart() = render("shopping cart icon")

    @SemanticUiCssMarker fun shower() = render("shower icon")

    @SemanticUiCssMarker fun street_view() = render("street view icon")

    @SemanticUiCssMarker fun subway() = render("subway icon")

    @SemanticUiCssMarker fun taxi() = render("taxi icon")

    @SemanticUiCssMarker fun ticket_alternate() = render("ticket alternate icon")

    @SemanticUiCssMarker fun train() = render("train icon")

    @SemanticUiCssMarker fun tree() = render("tree icon")

    @SemanticUiCssMarker fun umbrella() = render("umbrella icon")

    @SemanticUiCssMarker fun university() = render("university icon")

    @SemanticUiCssMarker fun utensil_spoon() = render("utensil spoon icon")

    @SemanticUiCssMarker fun utensils() = render("utensils icon")

    @SemanticUiCssMarker fun wrench() = render("wrench icon")


    // Medical //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun band_aid() = render("band aid icon")

    @SemanticUiCssMarker fun dna() = render("dna icon")

    @SemanticUiCssMarker fun first_aid() = render("first aid icon")

    @SemanticUiCssMarker fun hospital_symbol() = render("hospital symbol icon")

    @SemanticUiCssMarker fun pills() = render("pills icon")

    @SemanticUiCssMarker fun syringe() = render("syringe icon")

    @SemanticUiCssMarker fun thermometer() = render("thermometer icon")

    @SemanticUiCssMarker fun weight() = render("weight icon")


    // Objects //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun bus() = render("bus icon")

    @SemanticUiCssMarker fun cube() = render("cube icon")

    @SemanticUiCssMarker fun cubes() = render("cubes icon")

    @SemanticUiCssMarker fun futbol() = render("futbol icon")

    @SemanticUiCssMarker fun futbol_outline() = render("futbol outline icon")

    @SemanticUiCssMarker fun gem() = render("gem icon")

    @SemanticUiCssMarker fun gem_outline() = render("gem outline icon")

    @SemanticUiCssMarker fun lock() = render("lock icon")

    @SemanticUiCssMarker fun lock_open() = render("lock open icon")

    @SemanticUiCssMarker fun moon() = render("moon icon")

    @SemanticUiCssMarker fun moon_outline() = render("moon outline icon")

    @SemanticUiCssMarker fun puzzle_piece() = render("puzzle piece icon")

    @SemanticUiCssMarker fun snowflake() = render("snowflake icon")

    @SemanticUiCssMarker fun snowflake_outline() = render("snowflake outline icon")

    @SemanticUiCssMarker fun space_shuttle() = render("space shuttle icon")

    @SemanticUiCssMarker fun sun() = render("sun icon")

    @SemanticUiCssMarker fun sun_outline() = render("sun outline icon")

    @SemanticUiCssMarker fun tachometer_alternate() = render("tachometer alternate icon")

    @SemanticUiCssMarker fun unlock() = render("unlock icon")

    @SemanticUiCssMarker fun unlock_alternate() = render("unlock alternate icon")


    // Payments & Shopping //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun cart_plus() = render("cart plus icon")

    @SemanticUiCssMarker fun credit_card() = render("credit card icon")

    @SemanticUiCssMarker fun credit_card_outline() = render("credit card outline icon")


    // Shapes ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun square() = render("square icon")

    @SemanticUiCssMarker fun square_outline() = render("square outline icon")


    // Spinners /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun asterisk() = render("asterisk icon")

    @SemanticUiCssMarker fun circle_notch() = render("circle notch icon")

    @SemanticUiCssMarker fun spinner() = render("spinner icon")


    // Sports ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun baseball_ball() = render("baseball ball icon")

    @SemanticUiCssMarker fun basketball_ball() = render("basketball ball icon")

    @SemanticUiCssMarker fun bowling_ball() = render("bowling ball icon")

    @SemanticUiCssMarker fun football_ball() = render("football ball icon")

    @SemanticUiCssMarker fun golf_ball() = render("golf ball icon")

    @SemanticUiCssMarker fun hockey_puck() = render("hockey puck icon")

    @SemanticUiCssMarker fun quidditch() = render("quidditch icon")

    @SemanticUiCssMarker fun table_tennis() = render("table tennis icon")

    @SemanticUiCssMarker fun volleyball_ball() = render("volleyball ball icon")


    // Status ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun battery_empty() = render("battery empty icon")

    @SemanticUiCssMarker fun battery_full() = render("battery full icon")

    @SemanticUiCssMarker fun battery_half() = render("battery half icon")

    @SemanticUiCssMarker fun battery_quarter() = render("battery quarter icon")

    @SemanticUiCssMarker fun battery_three_quarters() = render("battery three quarters icon")

    @SemanticUiCssMarker fun thermometer_empty() = render("thermometer empty icon")

    @SemanticUiCssMarker fun thermometer_full() = render("thermometer full icon")

    @SemanticUiCssMarker fun thermometer_half() = render("thermometer half icon")

    @SemanticUiCssMarker fun thermometer_quarter() = render("thermometer quarter icon")

    @SemanticUiCssMarker fun thermometer_three_quarters() = render("thermometer three quarters icon")


    // User & People ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun child() = render("child icon")

    @SemanticUiCssMarker fun female() = render("female icon")

    @SemanticUiCssMarker fun user_plus() = render("user plus icon")

    @SemanticUiCssMarker fun user_times() = render("user times icon")

    @SemanticUiCssMarker fun users() = render("users icon")


    // Brands ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @SemanticUiCssMarker fun `500px`() = render("500px icon")

    @SemanticUiCssMarker fun accessible_icon() = render("accessible icon icon")

    @SemanticUiCssMarker fun accusoft() = render("accusoft icon")

    @SemanticUiCssMarker fun adn() = render("adn icon")

    @SemanticUiCssMarker fun adversal() = render("adversal icon")

    @SemanticUiCssMarker fun affiliatetheme() = render("affiliatetheme icon")

    @SemanticUiCssMarker fun algolia() = render("algolia icon")

    @SemanticUiCssMarker fun amazon() = render("amazon icon")

    @SemanticUiCssMarker fun amazon_pay() = render("amazon pay icon")

    @SemanticUiCssMarker fun amilia() = render("amilia icon")

    @SemanticUiCssMarker fun android() = render("android icon")

    @SemanticUiCssMarker fun angellist() = render("angellist icon")

    @SemanticUiCssMarker fun angrycreative() = render("angrycreative icon")

    @SemanticUiCssMarker fun angular() = render("angular icon")

    @SemanticUiCssMarker fun app_store() = render("app store icon")

    @SemanticUiCssMarker fun app_store_ios() = render("app store ios icon")

    @SemanticUiCssMarker fun apper() = render("apper icon")

    @SemanticUiCssMarker fun apple() = render("apple icon")

    @SemanticUiCssMarker fun apple_pay() = render("apple pay icon")

    @SemanticUiCssMarker fun asymmetrik() = render("asymmetrik icon")

    @SemanticUiCssMarker fun audible() = render("audible icon")

    @SemanticUiCssMarker fun autoprefixer() = render("autoprefixer icon")

    @SemanticUiCssMarker fun avianex() = render("avianex icon")

    @SemanticUiCssMarker fun aviato() = render("aviato icon")

    @SemanticUiCssMarker fun aws() = render("aws icon")

    @SemanticUiCssMarker fun bandcamp() = render("bandcamp icon")

    @SemanticUiCssMarker fun behance() = render("behance icon")

    @SemanticUiCssMarker fun behance_square() = render("behance square icon")

    @SemanticUiCssMarker fun bimobject() = render("bimobject icon")

    @SemanticUiCssMarker fun bitbucket() = render("bitbucket icon")

    @SemanticUiCssMarker fun bitcoin() = render("bitcoin icon")

    @SemanticUiCssMarker fun bity() = render("bity icon")

    @SemanticUiCssMarker fun black_tie() = render("black tie icon")

    @SemanticUiCssMarker fun blackberry() = render("blackberry icon")

    @SemanticUiCssMarker fun blogger() = render("blogger icon")

    @SemanticUiCssMarker fun blogger_b() = render("blogger b icon")

    @SemanticUiCssMarker fun bluetooth() = render("bluetooth icon")

    @SemanticUiCssMarker fun bluetooth_b() = render("bluetooth b icon")

    @SemanticUiCssMarker fun btc() = render("btc icon")

    @SemanticUiCssMarker fun buromobelexperte() = render("buromobelexperte icon")

    @SemanticUiCssMarker fun buysellads() = render("buysellads icon")

    @SemanticUiCssMarker fun cc_amazon_pay() = render("cc amazon pay icon")

    @SemanticUiCssMarker fun cc_amex() = render("cc amex icon")

    @SemanticUiCssMarker fun cc_apple_pay() = render("cc apple pay icon")

    @SemanticUiCssMarker fun cc_diners_club() = render("cc diners club icon")

    @SemanticUiCssMarker fun cc_discover() = render("cc discover icon")

    @SemanticUiCssMarker fun cc_jcb() = render("cc jcb icon")

    @SemanticUiCssMarker fun cc_mastercard() = render("cc mastercard icon")

    @SemanticUiCssMarker fun cc_paypal() = render("cc paypal icon")

    @SemanticUiCssMarker fun cc_stripe() = render("cc stripe icon")

    @SemanticUiCssMarker fun cc_visa() = render("cc visa icon")

    @SemanticUiCssMarker fun centercode() = render("centercode icon")

    @SemanticUiCssMarker fun chrome() = render("chrome icon")

    @SemanticUiCssMarker fun cloudscale() = render("cloudscale icon")

    @SemanticUiCssMarker fun cloudsmith() = render("cloudsmith icon")

    @SemanticUiCssMarker fun cloudversify() = render("cloudversify icon")

    @SemanticUiCssMarker fun codepen() = render("codepen icon")

    @SemanticUiCssMarker fun codiepie() = render("codiepie icon")

    @SemanticUiCssMarker fun connectdevelop() = render("connectdevelop icon")

    @SemanticUiCssMarker fun contao() = render("contao icon")

    @SemanticUiCssMarker fun cpanel() = render("cpanel icon")

    @SemanticUiCssMarker fun creative_commons() = render("creative commons icon")

    @SemanticUiCssMarker fun css3() = render("css3 icon")

    @SemanticUiCssMarker fun css3_alternate() = render("css3 alternate icon")

    @SemanticUiCssMarker fun cuttlefish() = render("cuttlefish icon")

    @SemanticUiCssMarker fun d_and_d() = render("d and d icon")

    @SemanticUiCssMarker fun dashcube() = render("dashcube icon")

    @SemanticUiCssMarker fun delicious() = render("delicious icon")

    @SemanticUiCssMarker fun deploydog() = render("deploydog icon")

    @SemanticUiCssMarker fun deskpro() = render("deskpro icon")

    @SemanticUiCssMarker fun deviantart() = render("deviantart icon")

    @SemanticUiCssMarker fun digg() = render("digg icon")

    @SemanticUiCssMarker fun digital_ocean() = render("digital ocean icon")

    @SemanticUiCssMarker fun discord() = render("discord icon")

    @SemanticUiCssMarker fun discourse() = render("discourse icon")

    @SemanticUiCssMarker fun dochub() = render("dochub icon")

    @SemanticUiCssMarker fun docker() = render("docker icon")

    @SemanticUiCssMarker fun draft2digital() = render("draft2digital icon")

    @SemanticUiCssMarker fun dribbble() = render("dribbble icon")

    @SemanticUiCssMarker fun dribbble_square() = render("dribbble square icon")

    @SemanticUiCssMarker fun dropbox() = render("dropbox icon")

    @SemanticUiCssMarker fun drupal() = render("drupal icon")

    @SemanticUiCssMarker fun dyalog() = render("dyalog icon")

    @SemanticUiCssMarker fun earlybirds() = render("earlybirds icon")

    @SemanticUiCssMarker fun edge() = render("edge icon")

    @SemanticUiCssMarker fun elementor() = render("elementor icon")

    @SemanticUiCssMarker fun ember() = render("ember icon")

    @SemanticUiCssMarker fun empire() = render("empire icon")

    @SemanticUiCssMarker fun envira() = render("envira icon")

    @SemanticUiCssMarker fun erlang() = render("erlang icon")

    @SemanticUiCssMarker fun ethereum() = render("ethereum icon")

    @SemanticUiCssMarker fun etsy() = render("etsy icon")

    @SemanticUiCssMarker fun expeditedssl() = render("expeditedssl icon")

    @SemanticUiCssMarker fun facebook() = render("facebook icon")

    @SemanticUiCssMarker fun facebook_f() = render("facebook f icon")

    @SemanticUiCssMarker fun facebook_messenger() = render("facebook messenger icon")

    @SemanticUiCssMarker fun facebook_square() = render("facebook square icon")

    @SemanticUiCssMarker fun firefox() = render("firefox icon")

    @SemanticUiCssMarker fun first_order() = render("first order icon")

    @SemanticUiCssMarker fun firstdraft() = render("firstdraft icon")

    @SemanticUiCssMarker fun flickr() = render("flickr icon")

    @SemanticUiCssMarker fun flipboard() = render("flipboard icon")

    @SemanticUiCssMarker fun fly() = render("fly icon")

    @SemanticUiCssMarker fun font_awesome() = render("font awesome icon")

    @SemanticUiCssMarker fun font_awesome_alternate() = render("font awesome alternate icon")

    @SemanticUiCssMarker fun font_awesome_flag() = render("font awesome flag icon")

    @SemanticUiCssMarker fun fonticons() = render("fonticons icon")

    @SemanticUiCssMarker fun fonticons_fi() = render("fonticons fi icon")

    @SemanticUiCssMarker fun fort_awesome() = render("fort awesome icon")

    @SemanticUiCssMarker fun fort_awesome_alternate() = render("fort awesome alternate icon")

    @SemanticUiCssMarker fun forumbee() = render("forumbee icon")

    @SemanticUiCssMarker fun foursquare() = render("foursquare icon")

    @SemanticUiCssMarker fun free_code_camp() = render("free code camp icon")

    @SemanticUiCssMarker fun freebsd() = render("freebsd icon")

    @SemanticUiCssMarker fun get_pocket() = render("get pocket icon")

    @SemanticUiCssMarker fun gg() = render("gg icon")

    @SemanticUiCssMarker fun gg_circle() = render("gg circle icon")

    @SemanticUiCssMarker fun git() = render("git icon")

    @SemanticUiCssMarker fun git_square() = render("git square icon")

    @SemanticUiCssMarker fun github() = render("github icon")

    @SemanticUiCssMarker fun github_alternate() = render("github alternate icon")

    @SemanticUiCssMarker fun github_square() = render("github square icon")

    @SemanticUiCssMarker fun gitkraken() = render("gitkraken icon")

    @SemanticUiCssMarker fun gitlab() = render("gitlab icon")

    @SemanticUiCssMarker fun gitter() = render("gitter icon")

    @SemanticUiCssMarker fun glide() = render("glide icon")

    @SemanticUiCssMarker fun glide_g() = render("glide g icon")

    @SemanticUiCssMarker fun gofore() = render("gofore icon")

    @SemanticUiCssMarker fun goodreads() = render("goodreads icon")

    @SemanticUiCssMarker fun goodreads_g() = render("goodreads g icon")

    @SemanticUiCssMarker fun google() = render("google icon")

    @SemanticUiCssMarker fun google_drive() = render("google drive icon")

    @SemanticUiCssMarker fun google_play() = render("google play icon")

    @SemanticUiCssMarker fun google_plus() = render("google plus icon")

    @SemanticUiCssMarker fun google_plus_g() = render("google plus g icon")

    @SemanticUiCssMarker fun google_plus_square() = render("google plus square icon")

    @SemanticUiCssMarker fun google_wallet() = render("google wallet icon")

    @SemanticUiCssMarker fun gratipay() = render("gratipay icon")

    @SemanticUiCssMarker fun grav() = render("grav icon")

    @SemanticUiCssMarker fun gripfire() = render("gripfire icon")

    @SemanticUiCssMarker fun grunt() = render("grunt icon")

    @SemanticUiCssMarker fun gulp() = render("gulp icon")

    @SemanticUiCssMarker fun hacker_news() = render("hacker news icon")

    @SemanticUiCssMarker fun hacker_news_square() = render("hacker news square icon")

    @SemanticUiCssMarker fun hips() = render("hips icon")

    @SemanticUiCssMarker fun hire_a_helper() = render("hire a helper icon")

    @SemanticUiCssMarker fun hooli() = render("hooli icon")

    @SemanticUiCssMarker fun hotjar() = render("hotjar icon")

    @SemanticUiCssMarker fun houzz() = render("houzz icon")

    @SemanticUiCssMarker fun html5() = render("html5 icon")

    @SemanticUiCssMarker fun hubspot() = render("hubspot icon")

    @SemanticUiCssMarker fun imdb() = render("imdb icon")

    @SemanticUiCssMarker fun instagram() = render("instagram icon")

    @SemanticUiCssMarker fun internet_explorer() = render("internet explorer icon")

    @SemanticUiCssMarker fun ioxhost() = render("ioxhost icon")

    @SemanticUiCssMarker fun itunes() = render("itunes icon")

    @SemanticUiCssMarker fun itunes_note() = render("itunes note icon")

    @SemanticUiCssMarker fun jenkins() = render("jenkins icon")

    @SemanticUiCssMarker fun joget() = render("joget icon")

    @SemanticUiCssMarker fun joomla() = render("joomla icon")

    @SemanticUiCssMarker fun js() = render("js icon")

    @SemanticUiCssMarker fun js_square() = render("js square icon")

    @SemanticUiCssMarker fun jsfiddle() = render("jsfiddle icon")

    @SemanticUiCssMarker fun keycdn() = render("keycdn icon")

    @SemanticUiCssMarker fun kickstarter() = render("kickstarter icon")

    @SemanticUiCssMarker fun kickstarter_k() = render("kickstarter k icon")

    @SemanticUiCssMarker fun korvue() = render("korvue icon")

    @SemanticUiCssMarker fun laravel() = render("laravel icon")

    @SemanticUiCssMarker fun lastfm() = render("lastfm icon")

    @SemanticUiCssMarker fun lastfm_square() = render("lastfm square icon")

    @SemanticUiCssMarker fun leanpub() = render("leanpub icon")

    @SemanticUiCssMarker fun less() = render("less icon")

    @SemanticUiCssMarker fun linechat() = render("linechat icon")

    @SemanticUiCssMarker fun linkedin() = render("linkedin icon")

    @SemanticUiCssMarker fun linkedin_in() = render("linkedin in icon")

    @SemanticUiCssMarker fun linode() = render("linode icon")

    @SemanticUiCssMarker fun linux() = render("linux icon")

    @SemanticUiCssMarker fun lyft() = render("lyft icon")

    @SemanticUiCssMarker fun magento() = render("magento icon")

    @SemanticUiCssMarker fun maxcdn() = render("maxcdn icon")

    @SemanticUiCssMarker fun medapps() = render("medapps icon")

    @SemanticUiCssMarker fun medium() = render("medium icon")

    @SemanticUiCssMarker fun medium_m() = render("medium m icon")

    @SemanticUiCssMarker fun medrt() = render("medrt icon")

    @SemanticUiCssMarker fun meetup() = render("meetup icon")

    @SemanticUiCssMarker fun microsoft() = render("microsoft icon")

    @SemanticUiCssMarker fun mix() = render("mix icon")

    @SemanticUiCssMarker fun mixcloud() = render("mixcloud icon")

    @SemanticUiCssMarker fun mizuni() = render("mizuni icon")

    @SemanticUiCssMarker fun modx() = render("modx icon")

    @SemanticUiCssMarker fun monero() = render("monero icon")

    @SemanticUiCssMarker fun napster() = render("napster icon")

    @SemanticUiCssMarker fun nintendo_switch() = render("nintendo switch icon")

    @SemanticUiCssMarker fun node() = render("node icon")

    @SemanticUiCssMarker fun node_js() = render("node js icon")

    @SemanticUiCssMarker fun npm() = render("npm icon")

    @SemanticUiCssMarker fun ns8() = render("ns8 icon")

    @SemanticUiCssMarker fun nutritionix() = render("nutritionix icon")

    @SemanticUiCssMarker fun odnoklassniki() = render("odnoklassniki icon")

    @SemanticUiCssMarker fun odnoklassniki_square() = render("odnoklassniki square icon")

    @SemanticUiCssMarker fun opencart() = render("opencart icon")

    @SemanticUiCssMarker fun openid() = render("openid icon")

    @SemanticUiCssMarker fun opera() = render("opera icon")

    @SemanticUiCssMarker fun optin_monster() = render("optin monster icon")

    @SemanticUiCssMarker fun osi() = render("osi icon")

    @SemanticUiCssMarker fun page4() = render("page4 icon")

    @SemanticUiCssMarker fun pagelines() = render("pagelines icon")

    @SemanticUiCssMarker fun palfed() = render("palfed icon")

    @SemanticUiCssMarker fun patreon() = render("patreon icon")

    @SemanticUiCssMarker fun paypal() = render("paypal icon")

    @SemanticUiCssMarker fun periscope() = render("periscope icon")

    @SemanticUiCssMarker fun phabricator() = render("phabricator icon")

    @SemanticUiCssMarker fun phoenix_framework() = render("phoenix framework icon")

    @SemanticUiCssMarker fun php() = render("php icon")

    @SemanticUiCssMarker fun pied_piper() = render("pied piper icon")

    @SemanticUiCssMarker fun pied_piper_alternate() = render("pied piper alternate icon")

    @SemanticUiCssMarker fun pied_piper_pp() = render("pied piper pp icon")

    @SemanticUiCssMarker fun pinterest() = render("pinterest icon")

    @SemanticUiCssMarker fun pinterest_p() = render("pinterest p icon")

    @SemanticUiCssMarker fun pinterest_square() = render("pinterest square icon")

    @SemanticUiCssMarker fun playstation() = render("playstation icon")

    @SemanticUiCssMarker fun product_hunt() = render("product hunt icon")

    @SemanticUiCssMarker fun pushed() = render("pushed icon")

    @SemanticUiCssMarker fun python() = render("python icon")

    @SemanticUiCssMarker fun qq() = render("qq icon")

    @SemanticUiCssMarker fun quinscape() = render("quinscape icon")

    @SemanticUiCssMarker fun quora() = render("quora icon")

    @SemanticUiCssMarker fun ravelry() = render("ravelry icon")

    @SemanticUiCssMarker fun react() = render("react icon")

    @SemanticUiCssMarker fun rebel() = render("rebel icon")

    @SemanticUiCssMarker fun redriver() = render("redriver icon")

    @SemanticUiCssMarker fun reddit() = render("reddit icon")

    @SemanticUiCssMarker fun reddit_alien() = render("reddit alien icon")

    @SemanticUiCssMarker fun reddit_square() = render("reddit square icon")

    @SemanticUiCssMarker fun rendact() = render("rendact icon")

    @SemanticUiCssMarker fun renren() = render("renren icon")

    @SemanticUiCssMarker fun replyd() = render("replyd icon")

    @SemanticUiCssMarker fun resolving() = render("resolving icon")

    @SemanticUiCssMarker fun rocketchat() = render("rocketchat icon")

    @SemanticUiCssMarker fun rockrms() = render("rockrms icon")

    @SemanticUiCssMarker fun safari() = render("safari icon")

    @SemanticUiCssMarker fun sass() = render("sass icon")

    @SemanticUiCssMarker fun schlix() = render("schlix icon")

    @SemanticUiCssMarker fun scribd() = render("scribd icon")

    @SemanticUiCssMarker fun searchengin() = render("searchengin icon")

    @SemanticUiCssMarker fun sellcast() = render("sellcast icon")

    @SemanticUiCssMarker fun sellsy() = render("sellsy icon")

    @SemanticUiCssMarker fun servicestack() = render("servicestack icon")

    @SemanticUiCssMarker fun shirtsinbulk() = render("shirtsinbulk icon")

    @SemanticUiCssMarker fun simplybuilt() = render("simplybuilt icon")

    @SemanticUiCssMarker fun sistrix() = render("sistrix icon")

    @SemanticUiCssMarker fun skyatlas() = render("skyatlas icon")

    @SemanticUiCssMarker fun skype() = render("skype icon")

    @SemanticUiCssMarker fun slack() = render("slack icon")

    @SemanticUiCssMarker fun slack_hash() = render("slack hash icon")

    @SemanticUiCssMarker fun slideshare() = render("slideshare icon")

    @SemanticUiCssMarker fun snapchat() = render("snapchat icon")

    @SemanticUiCssMarker fun snapchat_ghost() = render("snapchat ghost icon")

    @SemanticUiCssMarker fun snapchat_square() = render("snapchat square icon")

    @SemanticUiCssMarker fun soundcloud() = render("soundcloud icon")

    @SemanticUiCssMarker fun speakap() = render("speakap icon")

    @SemanticUiCssMarker fun spotify() = render("spotify icon")

    @SemanticUiCssMarker fun stack_exchange() = render("stack exchange icon")

    @SemanticUiCssMarker fun stack_overflow() = render("stack overflow icon")

    @SemanticUiCssMarker fun staylinked() = render("staylinked icon")

    @SemanticUiCssMarker fun steam() = render("steam icon")

    @SemanticUiCssMarker fun steam_square() = render("steam square icon")

    @SemanticUiCssMarker fun steam_symbol() = render("steam symbol icon")

    @SemanticUiCssMarker fun sticker_mule() = render("sticker mule icon")

    @SemanticUiCssMarker fun strava() = render("strava icon")

    @SemanticUiCssMarker fun stripe() = render("stripe icon")

    @SemanticUiCssMarker fun stripe_s() = render("stripe s icon")

    @SemanticUiCssMarker fun studiovinari() = render("studiovinari icon")

    @SemanticUiCssMarker fun stumbleupon() = render("stumbleupon icon")

    @SemanticUiCssMarker fun stumbleupon_circle() = render("stumbleupon circle icon")

    @SemanticUiCssMarker fun superpowers() = render("superpowers icon")

    @SemanticUiCssMarker fun supple() = render("supple icon")

    @SemanticUiCssMarker fun telegram() = render("telegram icon")

    @SemanticUiCssMarker fun telegram_plane() = render("telegram plane icon")

    @SemanticUiCssMarker fun tencent_weibo() = render("tencent weibo icon")

    @SemanticUiCssMarker fun themeisle() = render("themeisle icon")

    @SemanticUiCssMarker fun trello() = render("trello icon")

    @SemanticUiCssMarker fun tripadvisor() = render("tripadvisor icon")

    @SemanticUiCssMarker fun tumblr() = render("tumblr icon")

    @SemanticUiCssMarker fun tumblr_square() = render("tumblr square icon")

    @SemanticUiCssMarker fun twitch() = render("twitch icon")

    @SemanticUiCssMarker fun twitter() = render("twitter icon")

    @SemanticUiCssMarker fun twitter_square() = render("twitter square icon")

    @SemanticUiCssMarker fun typo3() = render("typo3 icon")

    @SemanticUiCssMarker fun uber() = render("uber icon")

    @SemanticUiCssMarker fun uikit() = render("uikit icon")

    @SemanticUiCssMarker fun uniregistry() = render("uniregistry icon")

    @SemanticUiCssMarker fun untappd() = render("untappd icon")

    @SemanticUiCssMarker fun usb() = render("usb icon")

    @SemanticUiCssMarker fun ussunnah() = render("ussunnah icon")

    @SemanticUiCssMarker fun vaadin() = render("vaadin icon")

    @SemanticUiCssMarker fun viacoin() = render("viacoin icon")

    @SemanticUiCssMarker fun viadeo() = render("viadeo icon")

    @SemanticUiCssMarker fun viadeo_square() = render("viadeo square icon")

    @SemanticUiCssMarker fun viber() = render("viber icon")

    @SemanticUiCssMarker fun vimeo() = render("vimeo icon")

    @SemanticUiCssMarker fun vimeo_square() = render("vimeo square icon")

    @SemanticUiCssMarker fun vimeo_v() = render("vimeo v icon")

    @SemanticUiCssMarker fun vine() = render("vine icon")

    @SemanticUiCssMarker fun vk() = render("vk icon")

    @SemanticUiCssMarker fun vnv() = render("vnv icon")

    @SemanticUiCssMarker fun vuejs() = render("vuejs icon")

    @SemanticUiCssMarker fun wechat() = render("wechat icon")

    @SemanticUiCssMarker fun weibo() = render("weibo icon")

    @SemanticUiCssMarker fun weixin() = render("weixin icon")

    @SemanticUiCssMarker fun whatsapp() = render("whatsapp icon")

    @SemanticUiCssMarker fun whatsapp_square() = render("whatsapp square icon")

    @SemanticUiCssMarker fun whmcs() = render("whmcs icon")

    @SemanticUiCssMarker fun wikipedia_w() = render("wikipedia w icon")

    @SemanticUiCssMarker fun windows() = render("windows icon")

    @SemanticUiCssMarker fun wordpress() = render("wordpress icon")

    @SemanticUiCssMarker fun wordpress_simple() = render("wordpress simple icon")

    @SemanticUiCssMarker fun wpbeginner() = render("wpbeginner icon")

    @SemanticUiCssMarker fun wpexplorer() = render("wpexplorer icon")

    @SemanticUiCssMarker fun wpforms() = render("wpforms icon")

    @SemanticUiCssMarker fun xbox() = render("xbox icon")

    @SemanticUiCssMarker fun xing() = render("xing icon")

    @SemanticUiCssMarker fun xing_square() = render("xing square icon")

    @SemanticUiCssMarker fun y_combinator() = render("y combinator icon")

    @SemanticUiCssMarker fun yahoo() = render("yahoo icon")

    @SemanticUiCssMarker fun yandex() = render("yandex icon")

    @SemanticUiCssMarker fun yandex_international() = render("yandex international icon")

    @SemanticUiCssMarker fun yelp() = render("yelp icon")

    @SemanticUiCssMarker fun yoast() = render("yoast icon")

    @SemanticUiCssMarker fun youtube() = render("youtube icon")

    @SemanticUiCssMarker fun youtube_square() = render("youtube square icon")
}
