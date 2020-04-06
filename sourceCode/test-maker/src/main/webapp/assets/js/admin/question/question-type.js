(function () {

    /*
     * DOM variables
     */
    var dataTab=$('#question-type-tab');
    var dataTable=$('#qtype-data-table');
    var saveQTypeForm=$('#save-qtype-form');
    var searchBox=$('#qtype-keyword');

    /*
     * model variables
     */
    var selected={}, qtypes;


    //entry point
    initialize();

    function initialize() {
        new TableFilter(dataTable, searchBox);
        loadData();
    }

    $('#type-tab-title').on('show.bs.tab', function (e) {
        console.log('showing question type tab');
        //entry point
        initialize();
    });

    dataTab.on('click', '.edit-item', function (e) {
        e.preventDefault();
        var index = $(this).closest('tr').data('index');
        console.log('data index: %s', index);
        selected = qtypes[index];
        saveQTypeForm.collapse('show');
        bindToForm();
    });

    dataTab.on('click', '.delete-item', function (e) {
        e.preventDefault();
        var index = $(this).closest('tr').data('index');
        selected = qtypes[index];

        var source = $('#delete-qtype-msg').html();
        var template = Handlebars.compile(source);
        var msg = template(selected);
        Dialogs.confirm(msg, function (result) {
            if (result) {
                deleteData();
                wrapUp();
            }
        })
    });

    saveQTypeForm.submit(function (e) {
        if (!saveQTypeForm.valid()) {
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
        _postData(CONTEXT.ctx + '/web/question/type/create.action');
    }

    function deleteData() {
        _postData(CONTEXT.ctx + '/web/question/type/delete.action');
    }

    function _postData(url) {
        var data = {
            questionType: selected
        };

        AjaxUtils.postData(url, data).done(wrapUp);
    }

    /**
     * Loads all question types from server
     */
    function loadData() {
        var url = CONTEXT.ctx + '/web/question/type/list.action';
        AjaxUtils.getData(url)
            .done(function (data, textStatus, jqXHR) {
                if(_.has(data,'aaData')) {
                    qtypes=data.aaData;
                    var dataTemplate = Handlebars.compile($('#qtype-data-template').html());
                    dataTable.empty();
                    dataTable.html(dataTemplate(data));
                }
            });
    }

    /**
     * Refresh the model with the form value
     */
    function bindToModel() {
        selected.name = saveQTypeForm.find('#qtype-name').val();
        console.log('Model updated with form value.');
        console.dir(selected);
    }

    /**
     * bind the selected question type to the save form for editing
     */
    function bindToForm() {
        saveQTypeForm.find('#qtype-id').val(selected.id);
        saveQTypeForm.find('#qtype-name').val(selected.name);
    }

    /**
     * cleaning up work
     */
    function wrapUp() {
        selected = {};
        saveQTypeForm[0].reset();
        saveQTypeForm.collapse('hide');
        loadData();
    }
})();
