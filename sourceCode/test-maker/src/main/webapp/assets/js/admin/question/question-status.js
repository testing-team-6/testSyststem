(function () {

    /*
     * Variables definition
     */
    //DOM elements
    var statusTab=$('#question-status-tab');
    var dataTable = $('#status-data-table');
    var saveStatusForm = $('#save-status-form');
    var searchBox = $('#status-keyword');

    //data binding
    var selected={}, statuses;

    function init() {
        new TableFilter(dataTable, searchBox);
        loadData();
    }

    $('#status-tab-title').on('show.bs.tab', function (e) {
        init();
    });

    /**
     *
     */
    saveStatusForm.submit(function (e) {
        if (!saveStatusForm.valid()) {
            console.error('Form is invalid');
            return false;
        }
        e.preventDefault();

        console.log('Begin to save status form');
        bindToModel();

        var url = CONTEXT.ctx + '/web/question/status/save.action';
        var data = {
            status: selected
        };
        console.log('status to be saved');
        console.dir(selected);
        AjaxUtils.postData(url, data)
            .done(wrapUp);
    });

    /**
     * edit button event handling
     */
    statusTab.on('click', '.edit-item', function (e) {
        var index = $(this).closest('tr').data('index');
        console.log('User clicked on row #%s', index);
        selected = statuses[index];
        console.dir(selected);
        saveStatusForm.collapse('show');
        bindToForm();
        e.preventDefault();
    });

    /**
     *
     */
    statusTab.on('click', '.delete-item', function (e) {
        var index = $(this).closest('tr').data('index');
        console.log('Row index: %s', index);
        selected = statuses[index];
        var msgSource = $('#delete-status-msg').html();
        var msgTemplate = Handlebars.compile(msgSource);
        Dialogs.confirm(msgTemplate(selected), function (result) {
            if (result) {
                var url = CONTEXT.ctx + '/web/question/status/delete.action';
                var data = {
                    status: selected
                };
                AjaxUtils.postData(url, data).done(wrapUp);
            }
        });
    });

    function loadData() {
        var url = CONTEXT.ctx + '/web/question/status/list.action';
        AjaxUtils.getData(url, null)
            .done(function (data, textStatus, jqXHR) {
                statuses=data.statuses;
                console.log('%s statuses loaded', statuses.length);
                var source = $('#status-data-template').html();
                var template = Handlebars.compile(source);
                dataTable.empty();
                dataTable.append(template(data));
            });
    }

    /**
     * Used when clicking on a row - editing
     */
    function bindToForm() {
        saveStatusForm.find('#status-id-row').removeClass('hidden');
        saveStatusForm.find('[name="id"]').val(selected.id);
        saveStatusForm.find('[name="name"]').val(selected.name);
        saveStatusForm.find('#start-status').prop('checked', selected.start);
        saveStatusForm.find('#finish-status').prop('checked', selected.finish);
        saveStatusForm.find('#status-acl-facilitator').prop('checked', selected.accessibleByFacilitator);
        saveStatusForm.find('#status-acl-author').prop('checked', selected.accessibleByAuthor);
        saveStatusForm.find('#status-acl-reviewer').prop('checked', selected.accessibleByReviewer);
        saveStatusForm.find('#status-acl-qa').prop('checked', selected.accessibleByQualityAdmin);
    }

    /**
     * Used when saving the form
     */
    function bindToModel() {
        selected.id=saveStatusForm.find('[name="id"]').val() === ''?null:saveStatusForm.find('[name="id"]').val();
        selected.name=saveStatusForm.find('[name="name"]').val();
        selected.start = saveStatusForm.find('#start-status').prop('checked');
        selected.finish = saveStatusForm.find('#finish-status').prop('checked');
        selected.accessibleByAuthor = saveStatusForm.find('#status-acl-author').prop('checked');
        selected.accessibleByFacilitator = saveStatusForm.find('#status-acl-facilitator').prop('checked');
        selected.accessibleByReviewer = saveStatusForm.find('#status-acl-reviewer').prop('checked');
        selected.accessibleByQualityAdmin = saveStatusForm.find('#status-acl-qa').prop('checked');
    }

    /**
     * wrapping up current page after form submision
     */
    function wrapUp() {
        saveStatusForm[0].reset();
        selected = {};
        saveStatusForm.collapse('hide');
        loadData();
    }
})();
