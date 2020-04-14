(function () {

    /*
     * Definition of DOM variables
     */
    var dataTable = $('#question-mgmt-table'); //songli: this part is the core of displaying the questions
    var questionForm = $('#edit-question-form');
    var paperForm =$ ('#generate-paper-form'); //new paperForm
    var transitionContainer = $('#change-status-container');

    //form elements
    var idSection = $('#q-id-section');
    var questionStatusField = $('#question-status');
    var chapterSelectList =$('#chapter-select');
    var knowledgePointSelectList=$('#knowledge-point-select');
    var authorSelectList = $('#author-list');
    var reviewerSelectList = $('#reviewer-list');
    var qaSelectList = $('#question-qa-list');
    var questionLanguageSelectList = $('#question-language-list');
    var questionTypeSelectList = $('#question-type-list');
    var authorStartDate = $('#authorStartDate');
    var authorFinishDate = $('#authorFinishDate');
    var reviewStartDate = $('#reviewStartDate');
    var reviewFinishDate = $('#reviewFinishDate');

    var toggleFormBtn = $('#show-edit-question-form-btn');
    var newQuestionModal = $('#new-question-modal'); //there is one redundant usage
    var newPaperModal=$('#new-paper-modal'); //new PaperModal
    var submitPaperBtn = $('#submit-paper-btn');
    var searchBox = $('#question-keyword');
    var batchUpdateStatusBtn=$('#batch-update-initial-status-btn');
    var batchChangeInitialStatusModal = $('#batch-update-status-modal');


    //add by jxy dialog: warn to select question
    var warnSelectQuestionModal = $("#select-question-dialog");


    var paperName=$('#paperName'); //new form element paper-name
    var paperDescription=$('#paper-description'); //new form element paper-description

    /*
     * Definition of model variables
     */
    var selectedQuestion={}, questions,transitions;
    /* new type */
    var newPaper={};

    var selectedQuestionId=[];


    var syllabus=CONTEXT.project.syllabus;
    var selectedChapter={}, chapters;
    var knowledgePoints, languages, questionTypes;
    var projectUsers;
    var transitions4InitialStatus;

    /*
     * action urls
     */
    var listQTypeURL = CONTEXT.ctx + '/web/question/type/list.action';
    var listProjectUserURL=CONTEXT.ctx + '/web/project/current/list-users.action';
    var listQuestionsURL = CONTEXT.ctx + '/web/project/current/list-questions.action';
    var questionPagingUrl=CONTEXT.ctx + '/web/project/current/paging.action';
    var saveQuestionURL= CONTEXT.ctx + '/web/project/current/save-question.action';
    var savePaperURL = CONTEXT.ctx + '/web/project/current/save-paper.action';

    var listQuestionsPublishedURL=CONTEXT.ctx+'/web/project/current/list-questions-published.action';

    /* modified and fixed paging displaying bug */
    var pagingHelper = new PaginationHelper(questionPagingUrl, listQuestionsPublishedURL, function (data) {
        questions=data.questions;
        console.log('%s questions loaded.', questions.length);
        displayQuestions(questions);
        loadTransitionsForInitialStatus();
    });
    /*
     * Default function when the page loads
     */
    initialize();

    /**
     * Page initialization
     */
    function initialize() {
        pagingHelper.loadPagingInfo();
        loadData();
        new TableFilter(dataTable, searchBox);
        initNewQuestionModal();
    }

    $('#reload-question-btn').click(function (e) {
        initialize();
    });

    /**
     * Update current chapter with user selection
     */
    chapterSelectList.on('select2:select',function (e) {
        var value = chapterSelectList.find(':selected').data('index');
        console.log('user selected chapter index: %s', value);
        //set user selected syllabus to current
        selectedChapter = chapters[value];
        loadKnowledgePoints(selectedChapter);
    });

    knowledgePointSelectList.on('select2:select',function (e) {
        var index = knowledgePointSelectList.find(':selected').data('index');
        console.log('user selected index: %s', index);
        //set user selected syllabus to current
        selectedQuestion.knowledgePoint = knowledgePoints[index];
    });

    questionLanguageSelectList.on('select2:select',function (e) {
        var index = $(this).find(':selected').data('index');
        selectedQuestion.language = languages[index];
    });

    questionTypeSelectList.on('select2:select',function (e) {
        var index = questionTypeSelectList.find(':selected').data('index');
        selectedQuestion.type=questionTypes[index];
    });

    batchUpdateStatusBtn.click(function (e) {
//        var filtered=_.filter(transitions4InitialStatus, {finish: false});
        var source = $('#initial-status-transition').html();
        var template=Handlebars.compile(source);
        batchChangeInitialStatusModal.find('#init-transition-actions-container').html(template({transitions: transitions4InitialStatus}));
        batchChangeInitialStatusModal.modal('show');
    });//useless


    batchChangeInitialStatusModal.on('click','.transition-initial', function (e) {
        console.log('about to change status for initial');
        var targetStatusId = $(this).data('id');
        console.log('transition id: %d', targetStatusId);
        Dialogs.confirm('确定要批量将初始状态的题目改为 <b>' + $(this).text() +'</b> 状态？', function (result) {
            if(result) {
                var url = CONTEXT.ctx + '/web/question/status/batch-update-initial.action';
                AjaxUtils.postData(url, {targetStatusId: targetStatusId}, true).done(function (data) {
                    Dialogs.success('批量修改状态成功，共修改 ' + data.count + ' 个题目！');
                    batchChangeInitialStatusModal.modal('hide');
                    loadData();
                });
            }
        }, false );
    });//useless


    /*newly added paper name */
    paperName.on('input propertychange',function(){

        newPaper.name=$(this).val();

        //$('b').html(val);
    });



    /* modified by wsl from newQuestionModal -> newPaperModal done*/
    toggleFormBtn.click(function (e) {
        var check = $("table#question-mgmt-table input[type=checkbox]:checked");
        check.each(function () {
            var id =$(this).closest("tr").find("td:eq(1)").text();//获取选中行的question id
            selectedQuestionId.push(id);
        });
        if (selectedQuestionId.length===0) { //没有选中，提示弹窗
            warnSelectQuestionModal.modal("show");
        }else{
            newPaperModal.modal('toggle');
        }
    });



    /**
     * Resets form when the modal is hidden for whatever reason
     */
    /* modified by wsl from newQuestionModal -> newPaperModal */
    newPaperModal.on('show.bs.modal', function (e) {
        if (!selectedQuestion.id) {
            idSection.addClass('hidden');
        }
    });


    newPaperModal.on('hidden.bs.modal', wrapUp);/* modified by wsl from newQuestionModal -> newPaperModal */
    submitPaperBtn.click(function (e) { //this line has be replaced
        paperForm.submit();
    });
    /**
     * Submit question form
     */
    paperForm.submit(function (e) { /* modified by wsl from questionForm -> paperForm */
        e.preventDefault();
        if (!validatePaperForm()) { //this line has been replaced done
            return false;
        }
        savePaper(); //this line has be replaced
    });



    function savePaper() {
        //todo: get questions id and bind to newPaper.questions

        var ids = selectedQuestionId.join('.');  //拼接后的ids
        selectedQuestionId = [];
        AjaxUtils.postData(savePaperURL, {paper: newPaper, ids: ids}, false).done(function () {
            //if it's an update action, just update current page. otherwise go to the last page.
            if(!_.isUndefined(selectedQuestion.id)) {
                pagingHelper.highlightCurrentPage();
            }else{
                pagingHelper.goToLastPage(!selectedQuestion.id);
            }
            newPaperModal.modal('hide');
            wrapUp();
            loadData();
        });
    }


    function initNewQuestionModal() {
        loadChapters();
        loadLanguages();
        loadQuestionTypes();
        initUserSelectList();
        DatePickerUtil.setupDateRange(authorStartDate, authorFinishDate);
        DatePickerUtil.setupDateRange(reviewStartDate, reviewFinishDate);
        $('.select-list').select2({width: '100%'});
        $('.select-list-simple').select2({width: '100%', minimumResultsForSearch: -1});
    }

    function initUserSelectList() {
        loadProjectUsers().done(function () {
            populateUserSelectList(authorSelectList);
            populateUserSelectList(reviewerSelectList);
            populateUserSelectList(qaSelectList);
        });
    }

    /**
     * Populates the project user select list with live project user data
     * @param jqSelectList the select control like author, reviewer list
     */
    function populateUserSelectList(jqSelectList) {
        return AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/user-list-options.hbs.html', function (template) {
            jqSelectList.empty();
            jqSelectList.append('<option></option>');
            jqSelectList.append(template({users:projectUsers}));
        });

    }



    function validatePaperForm() {
        if (!paperForm.valid()) {
            return false;
        }

        if (paperName.val() === '') {
            Dialogs.warning('请输入试卷名！');
            return false;
        }

        return true;
    }



    /**
     * Popup a modal of question details
     */
    dataTable.on('click','.view-item', function (e) {
        e.preventDefault();

        //gets the data-index attribute in the table and set selected kp per this index
        var index = $(this).closest('tr').data('index');
        selectedQuestion = questions[index];
        QuestionUtils.showQuestionDetailsModal({question: selectedQuestion});
    });



    function displayQuestions(questions) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/question-list-table-another.hbs.html', function (template) {
            var templateData = {
                questions: questions,
                showActions: true,
                showEdit:false,
                showDelete: false,
                showDetails: true
            };
            dataTable.empty();
            dataTable.append(template(templateData));
//            Dialogs.showAjaxLoadInfo($('.msg-area'), questions.length);
        });
    }
    function loadData() {
        /* modified by wsl and jxy done*/
        return $.get(listQuestionsPublishedURL, {
            pageSize: pagingHelper.pageSize,
            pageNumber: pagingHelper.currentPage
        })
            .done(function (data, textStatus, jqXHR) {
                questions=data.questions;
                displayQuestions(questions);  //wsl: this function is critical for displaying
            })
            .done(loadTransitionsForInitialStatus);
    }

    function loadTransitionsForInitialStatus() {
        var url = CONTEXT.ctx + '/web/question/status/get-initial-status.action';
        AjaxUtils.loadData(url, null).done(function (data) {
            transitions4InitialStatus = data.transitions;
            batchUpdateStatusBtn.removeClass('disabled');
        });
    }
    function loadProjectUsers() {
        return $.get(listProjectUserURL, {projectName: CONTEXT.projectName})
            .done(function (data, textStatus, jqXHR) {
                projectUsers=data.users;
                console.log('%s project users found for %s', projectUsers.length, CONTEXT.projectName);
            });
    }
    /**
     * Loads all question types from server
     */
    function loadLanguages() {
        var url = CONTEXT.ctx + '/web/question/language/list.action';
        AjaxUtils.getData(url)
            .done(function (data, textStatus, jqXHR) {
                console.log('Loaded languages');
                console.dir(data);
                if (_.has(data,'aaData')) {
                    languages=data.aaData;
                    var source = $('#language-list-template').html();
                    var template = Handlebars.compile(source);
                    questionLanguageSelectList.empty();
                    questionLanguageSelectList.append('<option></option>');
                    questionLanguageSelectList.append(template(data));
                }
            });
    }

    /**
     * Loads all question types from server
     */
    function loadQuestionTypes() {
        $.get(listQTypeURL)
            .done(function (data, textStatus, jqXHR) {
                if (_.has(data,'aaData')) {
                    questionTypes=data.aaData;
                    var source = $('#question-type-list-template').html();
                    var template = Handlebars.compile(source);
                    questionTypeSelectList.empty();
                    questionTypeSelectList.append('<option></option>');
                    questionTypeSelectList.append(template(data));
                }
            });
    }

    /**
     * Load all chapters for the syllabus selected and refresh the chapter select list
     */
    function loadChapters() {
        return AjaxUtils.loadData(CONTEXT.ctx + '/web/chapter/list.action', {syllabusId: CONTEXT.project.syllabus.id})
            .done(function (data, textStatus, jqXHR) {
                chapters = data.aaData;//refresh the list box with live data
                fillChapterSelectList(data);
            });

    }

    function fillChapterSelectList(data) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/syllabus/chapter-list-options.hbs.html', function (template) {
            chapterSelectList.html(template(data));
        });
    }

    /**
     * Load all knowledge points for the chapter selected in the chapter list.
     * Refresh the kp list with the data from server
     */
    function loadKnowledgePoints(chapter) {
        var url = CONTEXT.ctx + '/web/knowledge-point/list.action';
        console.log('Finding knowledge points from: %s', url);
        return AjaxUtils.loadData(url,{chapter: chapter})
            .done(function (data, textStatus, jqXHR) {
                knowledgePoints=data.aaData;
                fillKnowledgePoints(data);
            });
    }

    function fillKnowledgePoints(data) {
        var source = $('#knowledge-point-list-template').html();
        var template = Handlebars.compile(source);
        knowledgePointSelectList.empty();
        knowledgePointSelectList.append('<option></option>');
        knowledgePointSelectList.select2('val','');
        knowledgePointSelectList.append(template(data));
    }



    function wrapUp() {
        selectedQuestion = {};
        questionForm[0].reset();
        transitionContainer.empty();
        questionStatusField.text('');
        chapterSelectList.select2('val', '');
        knowledgePointSelectList.select2('val', '');
        questionTypeSelectList.val('').trigger('change');
        questionLanguageSelectList.val('').trigger('change');
        authorSelectList.val('').trigger('change');
        reviewerSelectList.val('').trigger('change');
        qaSelectList.val('').trigger('change');
        newPaperModal.modal('hide');
//        loadData(); //disabled to improve performance
    }
})();
