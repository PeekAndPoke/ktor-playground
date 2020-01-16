$.fn.formidableListField = function () {
    console.log(this);

    // TODO: check for any additional fields that are already present !
    let additionalCounter = 0;

    // Link all remove buttons
    this.find("[data-formidable=remove]").on("click", function () {

        let $this = $(this);
        // console.log($this);

        let $item = $this.closest("[data-formidable=item]").first();
        // console.log("item", $item);

        $item.remove();
    });

    // Link the add button
    this.find("[data-formidable=add]").on("click", function () {

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

        // replace the names of all form-elements
        $clone.find("*").each(function () {
            let $cloneChild = $(this);

            let id = $cloneChild.attr("id") || "";
            let name = $cloneChild.attr("name") || "";

            // is this one of the form fields within the clone?
            if (name.indexOf("[DUMMY]") !== -1) {
                console.log($cloneChild);
                $cloneChild.attr("name", name.split("[DUMMY]").join(`[ADD-${additionalCounter}]`));
                $cloneChild.attr("id", id.split("-DUMMY-").join(`-ADD-${additionalCounter}-`));
            }
        });

        // Increase the internal counter
        additionalCounter++;

        // Append the clone before the "add" button
        $clone.insertBefore($this);
    })
};

$(function () {
    $("[data-formidable=container]").formidableListField();
});
