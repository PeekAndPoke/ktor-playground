package io.ultra.ktor_tools.semanticui

import kotlinx.html.FlowContent
import kotlinx.html.i

@SemanticUiDslMarker val FlowContent.icon get() = SemanticIcon(this)

/*

 */

@Suppress("PropertyName", "FunctionName")
class SemanticIcon(private val parent: FlowContent) {


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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun render(classes: String): Unit = parent.i(classes = classes)
}
