(function () {
    /**
     * DOM variables
     */
    var expiringQuestionTable = $('#expiring-questions-table');
    var taskOverviewTable = $('#task-overview-table');
    var projectExpiringQuestionTable = $('#project-expiring-questions');
    var projectProgressBar = $('#project-progress-bar');
    var taskProgressBar = $('#task-progress-bar');
    var authorTaskInfo = $('#author-task-info');
    var reviewTaskInfo = $('#review-task-info');
    var qaTaskInfo = $('#qa-task-info');
    var authorPending = $('#author-pending');
    var authorTotal = $('#author-total');
    var reviewPending = $('#review-pending');
    var reviewTotal = $('#review-total');
    var qaPending = $('#qa-pending');
    var qaTotal = $('#qa-total');

    /**
     * model variables
     */
    var taskSummary, tasks = [];

    initialize();

    function initialize() {
        loadData();
        if (CONTEXT.facilitator) {
            updateProjectDashboard();
        }
    }

    $('#send-reminder-btn').click(function (e) {
        e.preventDefault();
        e.stopPropagation();
        Dialogs.confirm('确定要发送提醒邮件给相关问题负责人吗？', function (result) {
            if (result) {
                AjaxUtils.getData(CONTEXT.ctx+'/web/reporting/project/send-reminders.action').done(function () {
                    Dialogs.info('邮件已发送', 1500);
                });
            }
        });
    });
    authorTaskInfo.parent().click(function () {
        Navigation.loadPageByActionName('question-authoring.action');
    });

    reviewTaskInfo.parent().click(function () {
        Navigation.loadPageByActionName('question-reviewing.action');
    });
    qaTaskInfo.parent().click(function () {
        Navigation.loadPageByActionName('question-qa.action');
    });

    function loadData() {
        AjaxUtils.loadData(CONTEXT.ctx + '/web/reporting/question/summary.action')
            .done(function (data, textStatus, jqXHR) {
                taskSummary = data.summary;
                tasks=data.tasks;
                updateTaskStatus();
                //loadTaskList(data);
                loadTaskData(data);
                //updateTaskOverview(data);
                //listExpiringQuestions(expiringQuestionTable,data.questions);
            }).done(function(data){
                listExpiringQuestions(expiringQuestionTable,data.questions);
                console.log(data.questions);
            });
    }

    function updateTaskStatus() {
        //update task summary
        var finished = taskSummary.total - taskSummary.pending;
        var percentage=taskSummary.total==0?0:(finished / taskSummary.total * 100).toFixed();
        var taskProgress=  percentage+ '%';
        taskProgressBar.css('width', taskProgress);
        taskProgressBar.text(taskProgress);
        taskProgressBar.attr('title', '完成 ' + finished + ' 题，共 ' + taskSummary.total+' 题');
    }

    function updateProjectDashboard() {
        AjaxUtils.loadData(CONTEXT.ctx + '/web/reporting/project/summary.action')
            .done(function (data, textStatus, jqXHR) {
                //update project summary
                var projectSummary=data.summary;
                var finished = projectSummary.total - projectSummary.pending;
                var projectProgress=(finished/projectSummary.total * 100).toFixed(1)+'%';
                console.log('Project percentage: %s', projectProgress);
                projectProgressBar.css('width', projectProgress);
                projectProgressBar.text(projectProgress);
                projectProgressBar.attr('title', '已完成 ' + finished + '，共 ' + projectSummary.total);
            })
            .done(function (data) {
                listExpiringQuestions(projectExpiringQuestionTable,data.questions);
            });
    }

    function updateTaskOverview(data) {
        var source = $('#task-overview-template').html();
        var template = Handlebars.compile(source);
        taskOverviewTable.html(template(data));
    }
    function loadTaskList(data) {
        var tasks=data.tasks;
        _.each(tasks, function (task) {
            switch (task.type){
                case 'AUTHOR':
                    authorTaskInfo.text(task.pending);
                    break;
                case 'REVIEWER':
                    reviewTaskInfo.text(task.pending);
                    break;
                case 'QA':
                    qaTaskInfo.text(task.pending);
                    break;
            }
        });
    }

    function loadTaskData(data) {
        var tasks = data.tasks;
        _.each(tasks, function(task) {
           switch(task.type) {
               case 'AUTHOR':
                   authorPending.html(task.pending);
                   authorTotal.html(task.total);
                   break;
               case 'REVIEWER':
                   reviewPending.text(task.pending);
                   reviewTotal.text(task.total);
                   break;
               case 'QA':
                   qaPending.text(task.pending);
                   qaTotal.text(task.total);
                   break;
           }
        });
    }

    function listExpiringQuestions(dataTable, questions) {
        var currentUser = CONTEXT.user.fullName;
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/question-expire-list-table.hbs.html', function (template) {
            dataTable.html(template({
                questions: questions,
                currentUser: currentUser
            }));
            dataTable.find('.username').filter(':contains("' + currentUser + '")')
                .addClass('label label-success')
                .css('font-size','110%');
        });
    }
})();
