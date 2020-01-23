$(function () {

    $("textarea.markdown-editor").each(function () {
        new SimpleMDE({
            element: this,
            spellChecker: false,
        });
    })
});
