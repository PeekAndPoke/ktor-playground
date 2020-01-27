$(function () {

    // Initialize SimpleMDE markdown editor
    $("textarea.markdown-editor").each(function () {
        new SimpleMDE({
            element: this,
            spellChecker: false,
        });
    });
});
