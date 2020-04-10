(function () {

    /*
     * DOM variables
     */
    var dataTab=$('#question-language-tab');
    var dataTable=$('#qlanguage-data-table');
    var saveqlanguageForm=$('#save-qlanguage-form');
    var searchBox=$('#qlanguage-keyword');
    var dataTemplate;

    /*
     * model variables
     */
    var selected={}, qlanguages;


    //entry point
    $('#language-tab-title').on('shown.bs.tab', function () {
        initialize();
    });



    function initialize() {
        console.log('Initializing language tag');
        dataTemplate = Handlebars.compile($('#qlanguage-data-template').html());
        new TableFilter(dataTable, searchBox);
        loadData();
    }

    dataTab.on('click', '.edit-item', function (e) {
        e.preventDefault();
        var index = $(this).closest('tr').data('index');
        console.log('data index: %s', index);
        selected = qlanguages[index];
        saveqlanguageForm.collapse('show');
        bindToForm();
    });

    dataTab.on('click', '.delete-item', function (e) {
        e.preventDefault();
        var index = $(this).closest('tr').data('index');
        selected = qlanguages[index];

        var source = $('#delete-qlanguage-msg').html();
        var template = Handlebars.compile(source);
        var msg = template(selected);
        Dialogs.confirm(msg, function (result) {
            if (result) {
                deleteData();
                wrapUp();
            }
        })
    });

    saveqlanguageForm.submit(function (e) {
        if (!saveqlanguageForm.valid()) {
            return false;
        }
        saveData();
        wrapUp();
        e.preventDefault();
    });
    /**
     * action for saving form data
     */
    function saveData() {
        bindToModel();
        _postData(CONTEXT.ctx + '/web/question/language/create.action');
    }

    function deleteData() {
        _postData(CONTEXT.ctx + '/web/question/language/delete.action');
    }

    function _postData(url) {
        var data = {
            questionLanguage: selected
        };

        AjaxUtils.postData(url, data).done(wrapUp);
    }

    /**
     * Loads all question types from server
     */
    function loadData() {
        var url = CONTEXT.ctx + '/web/question/language/list.action';
        AjaxUtils.getData(url)
            .done(function (data, textStatus, jqXHR) {
                if(_.has(data,'aaData')) {
                    qlanguages=data.aaData;
                    dataTable.empty();
                    dataTable.html(dataTemplate(data));
                }
            });
    }

    /**
     * Refresh the model with the form value
     */
    function bindToModel() {
        selected.name = saveqlanguageForm.find('#qlanguage-name').val();
        console.log('Model updated with form value.');
        console.dir(selected);
    }

    /**
     * bind the selected question type to the save form for editing
     */
    function bindToForm() {
        saveqlanguageForm.find('#qlanguage-id').val(selected.id);
        saveqlanguageForm.find('#qlanguage-name').val(selected.name);
    }

    /**
     * cleaning up work
     */
    function wrapUp() {
        selected = {};
        saveqlanguageForm[0].reset();
        saveqlanguageForm.collapse('hide');
        loadData();
    }
})();
