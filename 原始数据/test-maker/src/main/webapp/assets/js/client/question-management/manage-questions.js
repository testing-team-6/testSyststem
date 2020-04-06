(function () {

    /*
     * Definition of DOM variables
     */
    var dataTable = $('#question-mgmt-table');
    var questionForm = $('#edit-question-form');
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
    var newQuestionModal = $('#new-question-modal');
    var submitQuestionBtn = $('#save-question-btn');
    var searchBox = $('#question-keyword');
    var batchUpdateStatusBtn=$('#batch-update-initial-status-btn');
    var batchChangeInitialStatusModal = $('#batch-update-status-modal');
    /*
     * Definition of model variables
     */
    var selectedQuestion={}, questions,transitions;
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

    var pagingHelper = new PaginationHelper(questionPagingUrl, listQuestionsURL, function (data) {
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
    });


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
    });

    authorSelectList.on('select2:select',function (e) {
        console.log('selecting author');
        var index = $(this).find(':selected').data('index');
        selectedQuestion.author=projectUsers[index];
    });

    reviewerSelectList.on('select2:select',function (e) {
        console.log('selecting reviewer');
        var index = $(this).find(':selected').data('index');
        selectedQuestion.reviewers=[projectUsers[index]];
    });

    qaSelectList.on('select2:select',function (e) {
        console.log('selecting QA');
        var index = $(this).find(':selected').data('index');
        selectedQuestion.qualityAdmin=projectUsers[index];
    });

    toggleFormBtn.click(function (e) {
        newQuestionModal.modal('toggle');
    });



    /**
     * Resets form when the modal is hidden for whatever reason
     */
    newQuestionModal.on('show.bs.modal', function (e) {
        if (!selectedQuestion.id) {
            idSection.addClass('hidden');
        }
    });

    newQuestionModal.on('hidden.bs.modal', wrapUp);
    submitQuestionBtn.click(function (e) {
        questionForm.submit();
    });
    /**
     * Submit question form
     */
    questionForm.submit(function (e) {
        e.preventDefault();
        if (!validateQuestionForm()) {
            return false;
        }
        saveQuestion();
    });


    /**
     * The core function to submit question to the server.
     */
    function saveQuestion() {
        bindToModel();
        AjaxUtils.postData(saveQuestionURL, {question: selectedQuestion}, false).done(function () {
            //if it's an update action, just update current page. otherwise go to the last page.
            if(!_.isUndefined(selectedQuestion.id)) {
                pagingHelper.highlightCurrentPage();
            }else{
                pagingHelper.goToLastPage(!selectedQuestion.id);
            }
            wrapUp();
            loadData();
        });
    }

    /**
     * Binds the form values to the question model variable
     */
    function bindToModel() {
        var qId = questionForm.find('#question-id').val();
        if (qId!=='') {
            selectedQuestion.id=qId;
        }
        selectedQuestion.authoringStartDate = DatePickerUtil.getDate(authorStartDate);
        selectedQuestion.authoringFinishDate = DatePickerUtil.getDate(authorFinishDate);
        selectedQuestion.reviewingStartDate = DatePickerUtil.getDate(reviewStartDate);
        selectedQuestion.reviewingFinishDate = DatePickerUtil.getDate(reviewFinishDate);
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

    function bindSelectedToForm() {
        console.log('The selected question is as below');
        console.dir(selectedQuestion);

        //knowledge point & chapter information
        var kp = selectedQuestion.knowledgePoint;
        if (kp) {
            var _chapters=_.filter(chapters, function (ch) {
                return ch.id === kp.chapter.id;
            });
            chapterSelectList.val(kp.chapter.id).trigger('change');
            loadKnowledgePoints(_chapters[0]).done(function () { //update the knowledge point value after ajax load
                knowledgePointSelectList.val(kp.id).trigger('change');
            });
        }


        idSection.removeClass('hidden');
        idSection.find('input').val(selectedQuestion.id);
        questionStatusField.text(selectedQuestion.status.name);
        //author information
        populateUserSelectList(authorSelectList).done(function () {
            authorSelectList.val(selectedQuestion.author.id).trigger('change');
        });
        DatePickerUtil.setDate(authorStartDate, selectedQuestion.authoringStartDate);
        DatePickerUtil.setDate(authorFinishDate, selectedQuestion.authoringFinishDate);

        //reviewer information
        populateUserSelectList(reviewerSelectList).done(function () {
            var reviewer = selectedQuestion.reviewers[0];
            if (reviewer) {
                reviewerSelectList.select2('val',reviewer.id);
            }
        });

        DatePickerUtil.setDate(reviewStartDate, selectedQuestion.reviewingStartDate);
        DatePickerUtil.setDate(reviewFinishDate, selectedQuestion.reviewingFinishDate);

        questionTypeSelectList.val(selectedQuestion.type.id).trigger('change');
        questionLanguageSelectList.val(selectedQuestion.language.id).trigger('change');
        populateUserSelectList(qaSelectList).done(function () {
            qaSelectList.val(selectedQuestion.qualityAdmin.id).trigger('change');
        });
    }

    function validateQuestionForm() {
        if (!questionForm.valid()) {
            return false;
        }

        if (authorSelectList.val() === '') {
            Dialogs.warning('请选择作者！');
            return false;
        }
        if (qaSelectList.val() === '') {
            Dialogs.warning('请选择质管！');
            return false;
        }
        if (reviewerSelectList.val() === '') {
            Dialogs.warning('请选择评审！');
            return false;
        }

        var formUsers=[];
        formUsers.push(authorSelectList.val());
        if(formUsers.indexOf(qaSelectList.val()) === -1) {
            formUsers.push(qaSelectList.val());
        }
        if(formUsers.indexOf(reviewerSelectList.val()) === -1) {
            formUsers.push(reviewerSelectList.val());
        }
        if (formUsers.length <3) {
            Dialogs.error("作者、评审和终审不能为同一人，请确认！");
            return false;
        }
        return true;
    }

    /**
     * what to happen when user clicks the 'edit' button
     */
    dataTable.on('click','.edit-item', function (e) {
        e.preventDefault();

        //gets the data-index attribute in the table and set selected kp per this index
        var index = $(this).closest('tr').data('index');
        selectedQuestion = questions[index];
        //populate the selected to save form
        newQuestionModal.modal('show');
        loadTransitions();
        bindSelectedToForm();
    });

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

    /**
     * Submit all changes include status
     */
    $(document).on('click','#transition-dropdown .transition-item',function (e) {
        e.preventDefault();
        e.stopPropagation();

        var transitionName = $(this).text();
        var index = $(this).data('index');
        var msg = '确定要将问题改为 <b>' + transitionName + '</b> 状态吗？ <p>新状态要在点击<b>提交题目</b>按钮之后才会生效！</p>';
        Dialogs.confirm(msg, function (result) {
            if (result) {
                selectedQuestion.status = transitions[index];
                questionStatusField.text(selectedQuestion.status.name);
                /*
                 * The change just applies to current select question object. Saving the question will take effect.
                 */
            }
        });
    });
    /**
     * clicking the remove sign will delete the selected item
     */
    dataTable.on('click', '.delete-item', function (e) {
        e.preventDefault();

        //gets the data-index attribute in the table and set selected kp per this index
        var index = $(this).closest('tr').data('index');
        console.log('Select row #%s for editing', index);
        selectedQuestion = questions[index];

        deleteQuestion();
    });

    function displayQuestions(questions) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/question-list-table.hbs.html', function (template) {
            var templateData = {
                questions: questions,
                showActions: true,
                showDelete: false,
                showDetails: true
            };
            dataTable.empty();
            dataTable.append(template(templateData));
//            Dialogs.showAjaxLoadInfo($('.msg-area'), questions.length);
        });
    }
    function loadData() {
        return $.get(listQuestionsURL, {
            pageSize: pagingHelper.pageSize,
            pageNumber: pagingHelper.currentPage
        })
            .done(function (data, textStatus, jqXHR) {
                questions=data.questions;
                displayQuestions(questions);
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
    /**
     * loads available statuses for selected question
     */
    function loadTransitions() {
        if(!selectedQuestion.status.accessibleByFacilitator){
            return false;
        }
        return QuestionUtils.loadTransitions(selectedQuestion, 'FACILITATOR',function (data) {
            transitions = data.transitions;
            AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/transition-dropdown-menu.hbs.html', function (template) {
                transitionContainer.empty();
                transitionContainer.html(template({statuses: transitions, dropDownListId: 'transition-dropdown'}));
            });
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

    function deleteQuestion() {

        var source = $('#delete-msg-template').html();
        var template = Handlebars.compile(source);
        var msg=template(selectedQuestion);
        Dialogs.confirm(msg, function (result) {
            if (result) {
                //user selected OK
                dataTable.find('[data-id="'+selectedQuestion.id+'"]').hide();
/*                var data = {
                    knowledgePoint: kp
                };
                var url=CONTEXT.ctx+'/knowledge-point/delete.action';
                AjaxUtils.postData(url, data)
                    .done(function (data,textStatus,jqXHR) {
                        listData(null);
                    });*/
            }else {
                //cancel the operation
            }
        });
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
        newQuestionModal.modal('hide');
//        loadData(); //disabled to improve performance
    }
})();
