function Utils(){}

/**
 * Loads the URL parameter value
 * @param paramName parameter name in the URL
 * @returns {undefined} or the parameter value
 */
Utils.getUrlParameter = function (paramName){
    paramName = paramName.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + paramName + "=([^&#]*)"),
        results = regex.exec(location.search);
    return results === null ? undefined: decodeURIComponent(results[1].replace(/\+/g, " "));
};

/**
 * Create an instance of CKEDITOR with the given name
 * @param name Cab be the value of id and name attribute. ID attribute value is preferred.
 * @returns {*} The created editor instance of type CKEDITOR.editor
 */
Utils.createCKEditor = function (name, height) {
    var editor = CKEDITOR.instances[name];
    if (editor) {
        editor.destroy(true);
    }

/*    CKEDITOR.on('instanceDestroyed', function(evt){
        CKEDITOR.replace(name,{
            height: height === undefined? '100%':height
        });
    });*/

    return CKEDITOR.replace(name,{
        height: height === undefined? '100%':height
    });
};


Utils.initTinyMCE = function () {
    tinyMCE.remove();
    $('.rich-editable').tinymce({
        language: 'zh_CN',
        theme : "modern",
        content_css : CONTEXT.ctx + "/assets/css/app.min.css",
        plugins: [
            "advlist autolink lists link image charmap preview hr",
            "searchreplace wordcount visualblocks visualchars fullscreen",
            "insertdatetime nonbreaking save table contextmenu directionality",
            "template paste textcolor colorpicker textpattern"
        ],
        toolbar1: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image preview | forecolor backcolor",
        image_advtab: true
    });
};
