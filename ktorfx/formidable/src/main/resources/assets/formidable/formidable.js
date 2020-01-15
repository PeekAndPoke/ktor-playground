$(function () {

    $("[data-formidable=remove]").on("click", function () {
        let $this = $(this);
        console.log($this);

        let $item = $this.closest("[data-formidable=item]");
        console.log($item);

        $item.remove();
    });
});
