$(() => {
    $(".insights-bar-placeholder").each((idx, placeholder) => {

        const $placeholder = $(placeholder);

        const bucket = $placeholder.data("insights-bucket");
        const filename = $placeholder.data("insights-filename");

        $.ajax(`/_/insights/bar/${bucket}/${filename}`)
            .then(result => {
                $placeholder.replaceWith($(result));

                $('#close-insights-bar').on("click", () => $('#insights-bar').remove())
            })
            .catch(console.error)
    });
});
