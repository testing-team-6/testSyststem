/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao []
 * Date: 2015/2/7
 * Time: 2:32
 */
(function () {
    $(function () {
        var projectTab = $('#project-tab');
        var dataTable = $('#project-table');
        var projectNameInput = $('#projectName');
        var indicatorDiv = $('.check-indicator');
        var idInput = $('#project-id');
        var startDateInput = $('#startDate');
        var finishDateInput = $('#finishDate');
        var statusList = $('#project-status-list');
        var syllabusSelectList = $('#project-syllabus-list');
        var facilitatorList = $('#facilitator-list');
        var createProjectForm = $('#adm-save-project-form');
        var resetFormBtn = createProjectForm.find('[type="reset"]');
        var searchBox = $('#project-keyword');

        var facilitator={}, users;
        var syllabus={}, syllabuses;
        var selected={}, projects, status={}, statuses;


        initialize();
        initDatePicker();

        $('#project-tab-title').bind('show.bs.tab click', function (e) {
            console.log('project tab clicked');
            initialize();
        });

        function initialize() {
//            $( document).tooltip();
            $('.select-list').select2({width: '100%'});
//            statusList.select2({width: '100%'});
            new TableFilter(dataTable, searchBox);
            loadData();
            loadSyllabuses();
            loadActiveUsers();
            loadProjectStatuses();
        }

        createProjectForm.validate({
            rules: {
                projectName: {
                    required: true
                },
                facilitator: {
                    required: true
                }
            }
        });

        function initDatePicker() {
            DatePickerUtil.setupDateRange(startDateInput, finishDateInput);

        }

        /**
         * submit save form to server
         */
        createProjectForm.submit(function (e) {
            if(!validateForm()){return false;}
            console.log('about to save project....');
            bindToModel();
            saveProject();
            e.preventDefault()
        });

        /**
         * Event handling for reset form button
         */
        resetFormBtn.click(function (e) {
            e.preventDefault();
            resetForm();
        });

        /**
         * when the user clicks on the edit icon, the current row will be bound to the model
         */
        projectTab.on('click', '.edit-item', function (e) {
            var index = $(this).closest('tr').data('index');
            console.log('User clicked on row #%s', index);
            selected = projects[index];
            console.dir(selected);
            statusList.prop('disabled', !selected.id);
            bindRowToForm();
            createProjectForm.collapse('show');
            e.preventDefault();
        });

        projectTab.on('click', '.export-item', function (e) {
            var index = $(this).closest('tr').data('index');
            console.log('User clicked on row #%s', index);
            var targetProject = projects[index];
            exportProject(targetProject.name);
            e.preventDefault();
        });

        projectTab.on('click', '.view-item', function (e) {
            var index = $(this).closest('tr').data('index');
            console.log('User clicked on row #%s', index);
            var targetProject = projects[index];
            ProjectUtils.showProjectDetailsModal(targetProject);
            e.preventDefault();
        });



        projectTab.on('ready', function () {
            $('[data-toggle="tooltip"]').tooltip();

        });
        projectNameInput.on('blur', function () {
            var inputText=projectNameInput.val();
            if(!inputText || $.trim(inputText).length < 1) {
                $(this).parent().addClass('has-error');
                return false;
            }
            if (createProjectForm.valid()===true) {
                checkProjectNameExists(inputText);
            }
        });
        projectNameInput.focus(function () {
            indicatorDiv.empty();
        });
        projectNameInput.on('input', function () {
            var jqElement = $(this);
            var text=jqElement.val();
            if(text.length>= jqElement.attr('minlength')){
                projectNameInput.parent().removeClass('has-error');
            }
        });

        syllabusSelectList.on('select2:select', function () {
            var index = syllabusSelectList.find(':selected').data('index');
            syllabus = syllabuses[index];
        });

        /**
         * dynamically assign the facilitator model per user select.
         */
        facilitatorList.on('select2:select', function (e) {
            var userIndex = facilitatorList.find(':selected').data('index');
            facilitator = users[userIndex];
        });

        statusList.on('select2:select', function (e) {
            var statusIndex = statusList.find(':selected').data('index');
            status = statuses[statusIndex];
            if (status.start && selected.id) {
                console.log('User wants to go to initial state. Stop this.');
                Dialogs.error('不能把进行中的或已结束的项目改为初始状态！');
                statusList.val('').trigger('change');
            }
        });

        /**
         * Ajax action for save form via JSON object
         */
        function saveProject() {
            var url = CONTEXT.ctx + '/web/project/create.action';
            var data = {
                project: selected
            };
            AjaxUtils.postData(url, data).done(wrapUp);
        }

        /**
         * action for export projects
         * @param projectName
         */
        function exportProject(projectName) {
            if (!projectName) {
                console.error('Project name is required');
            }
            Dialogs.confirm('确定要导出项目 <b>'+projectName+'</b> 的所有题目吗？操作可能会花费较长时间。', function (result) {
                if (result) {
                    AjaxUtils.getData(CONTEXT.ctx + '/web/admin/export-project.action',{projectName: projectName})
                        .done(function (data) {
                            Dialogs.info('导出成功。共导出 ' + data.count + ' 道题目。');
                            loadData();
                        });
                }
            });
        }

        /**
         * Bind the form data to model variable: selected
         */
        function bindToModel() {
            var projectIdFieldVal = idInput.val();
            selected.id = projectIdFieldVal === '' ? null : projectIdFieldVal;
            selected.name=createProjectForm.find('[name="name"]').val();
            selected.facilitator = facilitator;
            facilitatorList.val(selected.facilitator.id);
            if (syllabusSelectList.val()!=='') {
                selected.syllabus = syllabus;
            }

            //set status list to readonly if it's a create form
            statusList.prop('disabled', !selected.id);
            if (statusList.val() !=='') {
                selected.status = status;
            }

            selected.startDate = DatePickerUtil.getDate(startDateInput);
            selected.finishDate = DatePickerUtil.getDate(finishDateInput);

            console.log('updated model in bindToModel');
            console.dir(selected);
        }

        /**
         * bind the selected project to save form
         */
        function bindRowToForm() {
            createProjectForm.find('[name="id"]').val(selected.id);
            createProjectForm.find('[name="name"]').val(selected.name);
            facilitator=selected.facilitator;
            status=selected.status;
            if (selected.syllabus) {
                syllabus=selected.syllabus;
                syllabusSelectList.val(selected.syllabus.id).trigger('change');
            }else{
                syllabusSelectList.val('').trigger('change');
                syllabus=null;
            }
            facilitatorList.val(selected.facilitator.id).trigger('change');
            statusList.val(selected.status.id).trigger('change');
            DatePickerUtil.setDate(startDateInput, selected.startDate);
            DatePickerUtil.setDate(finishDateInput,selected.finishDate);
            console.log('Project model built from form.');
            console.dir(selected);
        }

        /**
         * Load projects data
         */
        function loadData() {
            var url = CONTEXT.ctx + '/web/project/list.action';
            AjaxUtils.getData(url)
                .done(function( data, textStatus, jqXHR ) {
                    projects=data.projects;
                    if (projects) {
                        console.log('%s projects loaded.', projects.length);
                        var source = $('#project-data-template').html();
                        var template = Handlebars.compile(source);
                        dataTable.empty();
                        dataTable.append(template(data));
                    }
                });
        }

        function loadSyllabuses() {
            var url = CONTEXT.ctx + '/web/syllabus/list-active.action';
            var templatePath = CONTEXT.ctx + '/assets/templates/syllabus/syllabus-list-options.hbs.html';
                $.get(url).done(function (data, textStatus, jqXHR) {
                syllabuses=data.aaData;
                AjaxUtils.getTemplateAjax(templatePath, function (template) {
                    syllabusSelectList.empty();
                    syllabusSelectList.append('<option></option>');
                    syllabusSelectList.append(template(data));
                });
            });
        }

        /**
         * Load project statuses from server
         */
        function loadProjectStatuses() {
            var url = CONTEXT.ctx + '/web/project/status/list.action';
            AjaxUtils.getData(url, null)
                .done(function (data, textStatus, jqXHR) {
                    statuses=data.statuses;
                    if (statuses) {
                        console.log('%s statuses loaded', statuses.length);
                        var source = $('#project-status-list-template').html();
                        var template = Handlebars.compile(source);
                        statusList.empty();
                        statusList.append('<option></option>');
                        statusList.append(template(data));
                    }
                });
        }
        /**
         * Load active users from the system
         */
        function loadActiveUsers() {
            var url = CONTEXT.ctx + '/web/user/list-active.action';
            AjaxUtils.getData(url, null)
                .done(function (data, textStatus, jqXHR) {
                    users=data.users;
                    console.log('%s active users loaded.', users.length);
                    populateFacilitatorSelectList(data);
                });
        }

        /**
         * Fill the users to the facilitator select list
         * @param data plainObject in the format of {users: users}.
         */
        function populateFacilitatorSelectList(data) {
            var source = $('#facilitator-option-list-template').html();
            var template = Handlebars.compile(source);
            facilitatorList.empty();
            facilitatorList.append('<option></option>');
            facilitatorList.append(template(data))
        }

        function checkProjectNameExists (projectName) {
            var fieldMinLength = $(projectNameInput).attr('minlength');
            console.log('field input length min: %s', fieldMinLength);
            var parameters = {
                projectName: projectName,
                questionId: $('#item-id').text()
            };
            if (projectName >=fieldMinLength) {
                var url = CONTEXT.ctx + '/web/project/checkName.action';
                $.get(url,parameters)
                    .done(function( data, textStatus, jqXHR ) {
                        console.log('Is project name exists? %s', data.exists);
                        //if the name exists, give hint to user
                        var exists=data.exists;
                        indicatorDiv.empty();
                        if (exists) {
                            projectNameInput.parent().addClass('has-error');
                            indicatorDiv.append('<label class="label-lg label-danger"><span class="glyphicon glyphicon-remove-sign"></span></label>');
                        }else {
                            indicatorDiv.append('<label class="label-lg label-success"><span class="glyphicon glyphicon-ok-sign"></span></label>');
                        }
                        $('.placeholder').width(indicatorDiv.width());
                    })
                    .fail(function( jqXHR, textStatus, errorThrown ) {
                        console.log('Failed to check name. Error: %s', errorThrown);
                    });
            }
        }

        function validateForm() {
            if (!createProjectForm.valid()) {
                return false;
            }
/*            if (syllabusSelectList.val()==='') {
                Dialogs.warning('必须选择大纲');
                return false;
            }*/
            if (facilitatorList.val()==='') {
                Dialogs.warning('必须选择主持人');
                return false;
            }

            if (!selected.id && statusList.val() !== '') {
                Dialogs.warning('新创建的项目状态会由系统自动设定，无需手动设置');
                statusList.val('').trigger('change');
                return false;
            }

            return true;
        }
        /**
         * resets save project form
         */
        function resetForm() {
            createProjectForm[0].reset();
            syllabusSelectList.val('').trigger('change');
            facilitatorList.val('').trigger('change');
            statusList.val('').trigger('change');
        }

        /**
         * wrapping up current page after form submision
         */
        function wrapUp() {
            resetForm();
            selected = {};
            createProjectForm.collapse('hide');
            loadData();
        }
    });
})();
