(function () {

    //define DOM variables
    var dataTab = $('#transition-tab-title');
    var formSection = $('#form-section');
    var dataTable = $('#transition-table');
    var saveTransitionForm = $('#save-transition-form');
    var statusStartSwitch = $('#question-start-status');

    var transitionalSelectList = $('#transitional-states');

    //data binding variables
    var selectedStatus = {}; // the status currently worked on
    var statuses; // all statuses in the system and should be read only
    var map;// the transition statuses matrix
    var availableStatuses;//current statuses in the system
    var transitions;//transitional states a status can have
    var possibleTransitions;

    /**
     * conditional initialization when the tab is opened
     */
    dataTab.on('show.bs.tab',function (e) {
        init();
    });

    function init() {
        transitionalSelectList.bootstrapDualListbox(BbootstrapDualListbox.options);
        loadData();
    }

    dataTable.on('click', '.edit-item', function (e) {
        e.preventDefault();
        formSection.collapse('show');
        bindRowToModel($(this).closest('tr'));
        bindToForm();
        refreshTransitionStates();
    });

    /**
     * Delete all the existing transitions for the selected status
     */
    dataTable.on('click', '.delete-item', function (e) {
        e.preventDefault();
        bindRowToModel($(this).closest('tr'));
        if (transitions === undefined || transitions.length <1) {
            console.warn('There is no transition to delete');
            return false;
        }

        var msgSource = $('#delete-transitions-msg').html();
        var msgTemplate = Handlebars.compile(msgSource);
        var data = {
            status: selectedStatus,
            statuses: transitions
        };
        Dialogs.confirm(msgTemplate(data), function (result) {
            if (result) {
                var url = CONTEXT.ctx + '/web/question/status/delete-transitions.action';
                AjaxUtils.postData(url,data).done(wrapUp);
            }
        });
    });

    saveTransitionForm.submit(function (e) {
        e.preventDefault();
        bindToModel();
        var msgSource = $('#save-transitions-msg').html();
        var msgTemplate = Handlebars.compile(msgSource);
        var data = {
            status: selectedStatus,
            statuses: transitions
        };
        Dialogs.confirm(msgTemplate(data), function (result) {
            if (result) {
                saveData(data);
            }
        });
    });

    function bindRowToModel(row) {
        var index = row.data('index');
        transitions = map[index];
        selectedStatus = statuses[index];
    }

    /**
     * Save transitional states
     * @param data
     */
    function saveData(data) {
        var url = CONTEXT.ctx + '/web/question/status/save-transitions.action';
        AjaxUtils.postData(url,data).done(wrapUp);
    }


    function loadData() {
        var url = CONTEXT.ctx + '/web/question/status/list-all-transitions.action';
        AjaxUtils.getData(url, null)
            .done(function (data, textStatus, jqXHR) {
                statuses=data.statuses;
                map=data.map;
                console.log('%s statuses loaded', statuses.length);
                var source = $('#transition-data-template').html();
                var template = Handlebars.compile(source);
                dataTable.empty();
                dataTable.append(template(data));
                initSelectList()
            });
    }

    function bindToForm() {
        //bind question status
        saveTransitionForm.find('[name="id"]').val(selectedStatus.id);
        saveTransitionForm.find('[name="name"]').val(selectedStatus.name);
        statusStartSwitch.removeClass('label-success');
        if (selectedStatus.start) {
            statusStartSwitch.addClass('label-success');
        }
    }

    /**
     * Bind the user selected transitions to model: transitions
     */
    function bindToModel() {
        var selectedOptions = transitionalSelectList.find(':selected');
        var selectedTransitions = [];
        $.each(selectedOptions, function (index, item) {
            var opt = $(item);
            console.log('selected option text: %s', opt.text());
            var id = opt.val();
            if ( selectedStatus.name !== opt.text()) {
                _.each(statuses, function (s) {
                    if (s.id == id ) {
                        selectedTransitions.push(s);
                    }
                });
            }
        });

        transitions = selectedTransitions;
        console.log('selected transitions: %s', transitions.length);
    }

    /**
     * dynamically initializes the select list with the data from server.
     * data property: statuses
     */
    function initSelectList() {
        availableUsers = _.filter(statuses, function (status) {
            return status.id !== selectedStatus.id
                && !status.start;
        });
        var source = $('#transition-list-items').html();
        var template = Handlebars.compile(source);
        transitionalSelectList.empty();
        transitionalSelectList.append(template({statuses: availableUsers}));
        transitionalSelectList.bootstrapDualListbox('refresh');

    }

    /**
     * Fill available statuses select list
     * This is done by using all statuses excluding current status itself and those start states
     */
    function refreshTransitionStates() {
        possibleTransitions = _.filter(statuses, function (status) {
            return status.id !== selectedStatus.id
                && !status.start;
        });
        var options = transitionalSelectList.find('option');
        options.show();
        console.log('transitions count: %s', transitions.length);
        $.each(options, function (index, item) {
            var opt = $(item);
            opt.prop('selected', false);
            var val = opt.val();
            if (opt.val() == selectedStatus.id) {
                opt.hide();
            }
            _.each(transitions, function (t) {
                if (t.id == val) {
                    opt.prop('selected', true);
                }
            });
        });

        transitionalSelectList.bootstrapDualListbox('refresh');
    }

    /**
     *
     */
    function wrapUp() {
        selectedStatus = {};
        transitions = [];
        saveTransitionForm[0].reset();
        formSection.collapse('hide');
        transitionalSelectList.find('option').prop('selected', false);
        loadData();
    }
})();
