(function () {

    var statusTab = $('#project-status-tab');
    var dataTable = $('#project-status-table');
    var saveStatusForm = $('#save-project-status-form');
    var searchBox = $('#project-status-keyword');

    var selected={}, statuses;
    var updateMode=false;


    /**
     * conditional initialization when the tab is opened
     */
    $('#status-tab-title').on('show.bs.tab',function (e) {
//        dataTable.find('tbody').empty();
        init();
    });

    function init () {
        new TableFilter(dataTable, searchBox);
        loadData();
    }

    /**
     * Load project statuses from server
     */
    function loadData() {
        var url = CONTEXT.ctx + '/web/project/status/list.action';
        AjaxUtils.getData(url, null)
            .done(function (data, textStatus, jqXHR) {
                statuses=data.statuses;
                console.log('%s statuses loaded', statuses.length);
                var source = $('#status-data-template').html();
                var template = Handlebars.compile(source);
                dataTable.find('tbody').empty();
                dataTable.append(template(data));
            });
    }

    /**
     * submit save status form
     */
    saveStatusForm.submit(function (e) {
        if (!saveStatusForm.valid()) {
            console.error('Form is invalid');
            return false;
        }
        e.preventDefault();

        bindFormToModel();

        var url = CONTEXT.ctx + '/web/project/status/projectStatus.action';
        var data = {
            status: selected
        };
        AjaxUtils.postData(url, data).done(wrapUp);
    });

    /**
     * edit button event handling
     */
    statusTab.on('click', '.edit-item', function (e) {
        var index = $(this).closest('tr').data('index');
        console.log('Row index: %s', index);
        selected = statuses[index];
        console.dir(selected);
        bindModelToForm();
    });

    /**
     * delete button event handling
     */
    statusTab.on('click', '.delete-item', function (e) {
        var index = $(this).closest('tr').data('index');
        console.log('Row index: %s', index);
        selected = statuses[index];
        var msgSource = $('#delete-status-msg').html();
        var msgTemplate = Handlebars.compile(msgSource);
        Dialogs.confirm(msgTemplate(selected), function (result) {
            if (result) {
                var url = CONTEXT.ctx + '/web/project/status/deleteprojectStatus.action';
                var data = {
                    status: selected
                };
                AjaxUtils.postData(url, data).done(wrapUp);
            }
        });
    });

    /**
     * bind the selected model to the save form.
     */
    function bindModelToForm() {
        $('#form-section').collapse('show');
        saveStatusForm.find('#status-id-row').removeClass('hidden');
        saveStatusForm.find('[name="id"]').val(selected.id);
        saveStatusForm.find('[name="name"]').val(selected.name);
        saveStatusForm.find('#project-start-status').prop('checked', selected.start);
        saveStatusForm.find('#project-finish-status').prop('checked', selected.finish);
    }

    /**
     * Bind form data to model variable: selected
     */
    function bindFormToModel() {
        selected.id=saveStatusForm.find('[name="id"]').val() === ''?null:saveStatusForm.find('[name="id"]').val();
        selected.name=saveStatusForm.find('[name="name"]').val();
        selected.start = saveStatusForm.find('#project-start-status').prop('checked');
        selected.finish = saveStatusForm.find('#project-finish-status').prop('checked');
        console.log('model from form');
        console.dir(selected);
    }

    /**
     * cleaning up / resetting after form submit, button clicking,etc.
     */
    function wrapUp() {
        saveStatusForm[0].reset();
        $('#form-section').collapse('hide');
        saveStatusForm.find('#status-id-row').addClass('hidden');
        updateMode=false;
        loadData();
    }
})();
