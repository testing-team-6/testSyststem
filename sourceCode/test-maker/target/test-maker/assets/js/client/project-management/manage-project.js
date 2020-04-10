(function () {

    /*
     * DOM variables
     */
    var saveProjectPanel = $('#modify-project-panel');
    var saveProjectForm = $('#save-project-form');
    var startDateInput=$('#project-start');
    var finishDateInput=$('#project-finish');

    var statusSelectList = $('#project-status');

    /*
     * model variables
     */
    var project=CONTEXT.project;
    var selectedStatus, statuses;
    /*
     * entry point
     */
    initialize();

    function initialize() {
        getCurrentProject();
//        loadData(APP_SESSION.project);
        setupControls();
    }

    function setupControls() {
//        statusSelectList.selectpicker();
        DatePickerUtil.setupDateRange(startDateInput, finishDateInput);
    }

    statusSelectList.change(function (e) {
        var index = $(this).find(':selected').data('index');
        selectedStatus = statuses[index];
    });

    function getCurrentProject() {
        var url = CONTEXT.ctx + '/web/project/current/get.action';
        AjaxUtils.getData(url).done(function (data) {
            project=data.project;
            loadData(project);
        });
    }

    /**
     * Load current user's project
     */
    function loadData(project) {
        var url = CONTEXT.ctx + '/web/project/status/list.action';
        AjaxUtils.loadData(url).done(function (data, textStatus, jqXHR) {
            statuses = data.statuses;
            var hbsData={
                project: project,
                statuses: statuses
            };
            console.log('parameters for project form');
            console.log(hbsData);
            var source = $('#status-list-template').html();
            var template = Handlebars.compile(source);
            statusSelectList.empty();
            statusSelectList.append(template(hbsData));
            $('#exported-on').text(project.exportedOn);
            if(!_.isUndefined(project.exportedBy)) {
                $('#exported-by').text(project.exportedBy.fullName);
            }
        });
    }

    saveProjectForm.submit(function (e) {
        e.preventDefault();
        bindToModel();
        var url=CONTEXT.ctx+'/web/project/current/update.action';
        var data={
            project:project
        };
        console.log('updated project');
        console.dir(project);
        AjaxUtils.postData(url, data).done(wrapUp);
    });

    function bindToModel() {
        project.startDate = DatePickerUtil.getDate(startDateInput);
        project.finishDate = DatePickerUtil.getDate(finishDateInput);
        if (selectedStatus) {
            console.log('Selected status: %s', selectedStatus.name);
            project.status=selectedStatus;
        }
    }

    $('#export-current-project').click(function (e) {
        exportProject();
    });

    /**
     * action for export projects
     * @param projectName
     */
    function exportProject() {
        Dialogs.confirm('确定要导出当前项目的所有题目吗？操作可能会花费较长时间。', function (result) {
            if (result) {
                AjaxUtils.getData(CONTEXT.ctx + '/web/admin/export-project.action',{})
                    .done(function (data) {
                        Dialogs.info('导出成功。共导出 ' + data.count + ' 道题目。');
                        loadData(project);
                    });
            }
        });
    }
    function wrapUp() {
    }

})();
