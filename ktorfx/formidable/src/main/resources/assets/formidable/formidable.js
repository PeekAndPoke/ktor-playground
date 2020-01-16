$(function () {

    $("[data-formidable=remove]").on("click", function () {

        let $this = $(this);
        console.log($this);

        let $item = $this.closest("[data-formidable=item]").first();
        console.log("item", $item);

        $item.remove();
    });

    $("[data-formidable=add]").on("click", function () {

        let $this = $(this);
        console.log($this);

        let $container = $this.closest("[data-formidable=container]").first();
        console.log("container", $container);

        // Get the dummy item
        let $dummy = $container.find("[data-formidable=dummy]").first();
        console.log("dummy", $dummy);

        // Get direct children of the dummy element. This the actual content we want to attach to the container.
        let $dummyChildren = $dummy.children();
        console.log("dummyChildren", $dummyChildren);

        // Get a clone of the dummy
        let $clone = $dummyChildren.clone(true, true);

        // Append the clone before the "add" button
        $clone.insertBefore($this);
    })
});
