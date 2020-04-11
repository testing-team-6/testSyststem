(function () {

    /*
     * Definition of DOM variables
     */
    var dataTable = $('#paper-mgmt-table');
    var paperForm = $('#edit-paper-form');

    var toggleFormBtn = $('#show-edit-paper-form-btn');
    var newPaperModal = $('#new-paper-modal');
    
    //form elements
    var questionSelectList=$('#question-select-list');
    var searchBox = $('#paper-keyword');
    var submitPaperBtn = $('#save-paper-btn');
    var paperName = $('#paper-name');

    var papers,questionList;

    /*
     * action urls
     */
    var listProjectUserURL=CONTEXT.ctx + '/web/project/current/list-users.action';
    var paperPagingUrl=CONTEXT.ctx + '/web/project/current/paging.action';
    var savePaperURL= CONTEXT.ctx + '/web/project/current/save-paper.action';
    var listPapersURL = CONTEXT.ctx + '/web/project/current/list-papers.action';

    var pagingHelper = new PaginationHelper(paperPagingUrl, listPapersURL, function (data) {
        papers = data.papers;
        console.log('%s papers loaded.', papers.length);
        displayPapers(papers);
        loadTransitionsForInitialStatus();
    });
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
    toggleFormBtn.click(function (e) {
        newPaperModal.modal('toggle');
    });
    submitPaperBtn.click(function (e) {
        paperForm.submit();
    });
    /**
     * Submit paper form
     */
    paperForm.submit(function (e) {
        e.preventDefault();
        if (!validatePaperForm()) {
            return false;
        }
        savePaper();
    });
    //check form is valid or not
    function validatePaperForm() {
        if (!paperForm.valid()) {
            return false;
        }
        if(paperName.val() === ''){
            Dialogs.warning('请输入试卷名！');
            return false;
        }
        if (questionSelectList.val() === ''){
            Dialogs.warning('请选择试题！');
            return false;
        }
        return true;
    }

    /**
     * The core function to submit paper to the server.
     */
    function savePaper() {
    }
    /**
     * action for export paper
     * @param paperId
     */
    function exportPaper() {
        Dialogs.confirm('确定要导出组卷吗？操作可能会花费较长时间。', function (result) {
            if (result) {
                var url = CONTEXT.ctx + '/web/admin/export-paper.action';
                var xhr = new XMLHttpRequest();
                xhr.open('GET', url, true);
                xhr.responseType = "blob";    // 返回类型blob
                xhr.onload = function () {
                    // 请求完成
                    if (this.status === 200) {
                        // 返回200
                        Dialogs.info('导出成功');
                        var blob = this.response;
                        var reader = new FileReader();
                        reader.readAsDataURL(blob);
                        reader.onload = function (e) {
                            // 创建一个a标签用于下载
                            var a = document.createElement('a');
                            a.download = 'paper.pdf';
                            a.href = e.target.result;
                            $("body").append(a);
                            a.click();
                            $(a).remove();
                        };
                    }
                };
                // 发送ajax请求
                xhr.send();
            }
        });
    }

    function loadQuestions() {
        var url = CONTEXT.ctx + '/web/quetions/question-list.action';
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
