(function () {
    /*
     * DOM variables
     */
    var editProjectUserForm = $('#edit-project-user-form');
    var userSelectList = $('#project-user-list');

    /*
     * model variables
     */
    var projectName = CONTEXT.projectName;
    var project=CONTEXT.project;
    var projectUsers, allUsers;

    /*
     * action urls
     */
    var listAllUserURL = CONTEXT.ctx + '/web/user/list-active.action';
    var listProjectUserURL = CONTEXT.ctx + '/web/project/current/list-users.action';
    var saveProjectUserURL = CONTEXT.ctx + '/web/project/current/save-users.action';

    /*
     * entry point
     */
    initialize();

    function initialize() {
        userSelectList.bootstrapDualListbox(BbootstrapDualListbox.options);
        loadData();
    }

    editProjectUserForm.submit(function (e) {
        e.preventDefault();
        saveData()
    });

    function saveData() {
        bindToModel();
        console.log('Project users to be saved: %s', projectUsers.length);
        console.dir(projectUsers);
        var data = {
            projectName:projectName,
            users: projectUsers
        };
        AjaxUtils.postData(saveProjectUserURL, data).done(wrapUp);
    }

    /**
     * Bind the user selected transitions to model: transitions
     */
    function bindToModel() {
        var selectedOptions = userSelectList.find(':selected');
        var selectedUsers = [];
        $.each(selectedOptions, function (index, item) {
            var option = $(item);
            var selectedIndex = option.data('index');
            selectedUsers.push(allUsers[selectedIndex]);
        });
        projectUsers = selectedUsers;
    }


    /**
     * Load current user's project
     */
    function loadData() {
        //load form from Handlebars template
        var data = {projectName: CONTEXT.projectName};
        $.when(
            AjaxUtils.getData(listAllUserURL, null),
            AjaxUtils.getData(listProjectUserURL, data)
        ).done(function (a1,a2) {
                allUsers=a1[0].users;
                projectUsers=a2[0].users;
                initSelectList();
        });
    }

    /**
     * dynamically initializes the select list with the data from server.
     * data property: statuses
     */
    function initSelectList() {
        var source = $('#project-user-options').html();
        var template = Handlebars.compile(source);
        userSelectList.empty();
        userSelectList.append(template({users: allUsers}));
        userSelectList.bootstrapDualListbox('refresh');
        refreshTransitionStates();
    }

    /**
     * Fill available statuses select list
     * This is done by using all statuses excluding current status itself and those start states
     */
    function refreshTransitionStates() {
        var options = userSelectList.find('option');
        console.log('project user count: %s', projectUsers.length);
        $.each(options, function (index, item) {
            var opt = $(item);
            $.each(projectUsers, function (index, item) {
                if (item.id == opt.val()) {
                    opt.prop('selected', true);
                }
            });
        });

        userSelectList.bootstrapDualListbox('refresh');
    }

    function wrapUp() {

    }
})();
