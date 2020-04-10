(function () {

    /*
     * Definition of DOM variables
     */
    var dataTable = $('#paper-mgmt-table');
    var questionForm = $('#edit-paper-form');

    var toggleFormBtn = $('#show-edit-paper-form-btn');
    var newPaperModal = $('#new-paper-modal');


    //form elements
    var questionSelectList=$('#question-select-list');
    var searchBox = $('#paper-keyword');
    var submitPaperBtn = $('#save-paper-btn');
    var paperName = $('#paper-name');

    var questionList;

    dataTable.on('click','.edit-item', function (e) {

    });

    /**
     * Popup a modal of paper details
     */
    dataTable.on('click','.view-item', function (e) {

    });


    /**
     * clicking the remove sign will delete the selected item
     */
    dataTable.on('click', '.delete-item', function (e) {

    });

    function displayPapers(papers) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/paper-list-table.hbs.html', function (template) {
            var templateData = {
                papers: papers,
                showActions: true,
                showDelete: true,
                showDetails: true
            };
            dataTable.empty();
            dataTable.append(template(templateData));
//            Dialogs.showAjaxLoadInfo($('.msg-area'), questions.length);
        });
    }
    function loadData() {
        return $.get(listPapersURL, {
            pageSize: pagingHelper.pageSize,
            pageNumber: pagingHelper.currentPage
        })
            .done(function (data, textStatus, jqXHR) {
                papers=data.papers;
                displayPapers(papers);
            })
            .done(loadTransitionsForInitialStatus);
    }


    function loadProjectUsers() {
        return $.get(listProjectUserURL, {projectName: CONTEXT.projectName})
            .done(function (data, textStatus, jqXHR) {
                projectUsers=data.users;
                console.log('%s project users found for %s', projectUsers.length, CONTEXT.projectName);
            });
    }

    $('#export-paper-btn').click(function (e) {
        exportPaper();
    });

    /**
     * action for export paper
     * @param paperId
     */
    function exportPaper() {
        Dialogs.confirm('确定要导出组卷吗？操作可能会花费较长时间。', function (result) {
            if (result) {
                AjaxUtils.getData(CONTEXT.ctx + '/web/admin/export-paper.action',{})
                    .done(function (data) {
                        Dialogs.info('导出成功');

                        var blob = new Blob([data], {type:"application/octet-stream"});
                        var objectUrl = URL.createObjectURL(blob);
                        console.log(objectUrl);
                        window.location.href  = objectUrl;
                    });
            }
        });
    }

    function loadQuestions() {
        var url = CONTEXT.ctx + '/web/quetions/list.action';
        console.log('Finding quetions from: %s', url);
        return AjaxUtils.loadData(url)
            .done(function (data, textStatus, jqXHR) {
                questionList=data.aaData;
                fillQuestionSelectList(data);
            });
    }
    function fillQuestionSelectList(data) {
        var source = $('#question-select-list-template').html();
        var template = Handlebars.compile(source);
        questionSelectList.empty();
        questionSelectList.append('<option></option>');
        questionSelectList.select2('val','');
        questionSelectList.append(template(data));
    }
})();
