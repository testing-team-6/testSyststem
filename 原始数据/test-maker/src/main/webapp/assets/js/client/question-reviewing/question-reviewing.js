/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao []
 * Date: 2015/2/4
 * Time: 23:41
 */
(function () {
    'use strict';
    /**
     * Define page wide dom objects and variables
     */
    var dataTable = $('#q-review-table');
    var transitionContainer = $('#change-status-container');
    var reviewWindowId = 'q-review-modal';
    var reviewWindow;
    var reviewSubmitBtnId = 'q-review-submit';
    var transitionListId = 'review-transition-list';
    var reloadBtn = $('#reload-question-btn');
    var detailsContainer = $('#q-details-container');
    var searchBox = $('#q-review-keyword');

    /*
     * Review Comment form modal controls
     */
    var commentFormDialogTemplate;
    var commentFormDialog;
    var commentFormId = 'q-comment-form';
    var commentFormModalId = 'q-comment-form-modal';
    var commentSubmitBtnId = 'submit-comment';
    var commentSubmitBtnSelector = '#'+commentSubmitBtnId;
    var commentBoxSelector ='#qa-comment';

    /*
     * Definition of model variables
     */
    var selectedQuestion={},questions, transitions;
    var qReview = {}, nextStatus;

    var currentUser=CONTEXT.user.username;
    /**
     * Initialize controls
     *
     */


    initialize();



    /*
     * initializes the question choice label array
     */
    function initialize() {
        loadData();
        commentFormDialogTemplate = Handlebars.compile($('#review-form-template').html());

        new TableFilter(dataTable, searchBox);
    }

    /**
     * initialize the review form modal.
     * The form has to be initialized in this way since it's loaded via handlebars template
     */
    function initReviewFormModal() {
        reviewWindow=$('#'+reviewWindowId);
        reviewWindow.modal({
            backdrop: false
        });
    }

    /**
     * what to happen when user clicks the 'edit' button
     */
    dataTable.on('click','.edit-item', function (e) {
        e.preventDefault();

        //gets the data-index attribute in the table and set selected kp per this index
        var index = $(this).closest('tr').data('index');
        selectedQuestion = questions[index];
        QuestionUtils.showQuestionReviewModal({
            question: selectedQuestion,
            title: '评审题目 #'+selectedQuestion.id,
            modalId: reviewWindowId,
            showFooter: true,
            showModal: false,
            showQAPanel: true
        }).done(function (data) {
            loadTransitions().done(function (data) {
                transitions = data.transitions;
                initReviewFormModal();
                reviewWindow.modal('show');
            });
        });
    });

    function initializeCommentDialog() {
        $('.ui-dialog').remove();
        var dialogContent = commentFormDialogTemplate({
            formId: commentFormId,
            reviewModalId:commentFormModalId,
            submitBtnId: reviewSubmitBtnId,
            statuses: transitions,
            statusTransitionListId: transitionListId
        });
        commentFormDialog = $(dialogContent).dialog({
            autoOpen: false,
            height: 330,
            width: 640,
            modal: true,
            buttons: [
                {
                    text: '发布评价',
                    id: commentSubmitBtnId,
                    disabled: true,
                    click: function () {
                        var result = saveReviewComment();
                        if (_.isBoolean(result) && !result) {//form is invalid
                            return false;
                        }

                        result.done(wrapUp);
                        $( this ).dialog( "close" );
                        $( this ).dialog( "destroy" ).remove();
                    }
                },
                {
                    text: '重写',
                    click: function () {
                        $(this).find('form')[ 0 ].reset();
                        toggleCommentFormState(true);
                    }
                },
                {
                    text: '取消',
                    click:function() {
                        console.log('Destroying current dialog');
//                        $( this ).dialog( "close" );
                        $( this ).dialog( "destroy" ).remove();
                    }
                }

            ]
        });
    }

    /**
     * Popup a modal of question details
     */
    dataTable.on('click','.view-item', function (e) {
        e.preventDefault();

        //gets the data-index attribute in the table and set selected kp per this index
        var index = $(this).closest('tr').data('index');
        selectedQuestion = questions[index];
        QuestionUtils.showQuestionDetailsModal({
            question: selectedQuestion,
            showQAPanel: true
        });
    });

    /*
     * Event handling for deleting review comment
     */
    $(document).on('click','.delete-review', function (e) {
        e.preventDefault();
        e.stopImmediatePropagation();
        var row = $(this).closest('tr');
        var reviewId =row.data('id');
        console.log('About to delete review [%s]', reviewId);
        var details = row.clone();
        details.find('td:last-of-type').remove();
        var msg = '确定要删除选定的评论吗？评论详情 <br> <blockquote>' + details.html()+'</blockquote>';
        Dialogs.confirm(msg, function (result) {
            if(result) {
                QuestionUtils.deleteReview(reviewId).done(refreshReviewComments);
            }
        },true);
    });


    reloadBtn.click(function (e) {
        e.preventDefault();
        loadData();
    });

    /**
     * toggle comment form state
     * @param flag true to disable form; false to enable form.
     */
    function toggleCommentFormState(flag) {
        var msg='<span id="comment-error" class="label-lg label-danger">请提供评审信息！</span>';
        $(document).find('#comment-error').remove();
        $(document).find(commentSubmitBtnSelector).button('option','disabled', flag);
        if (flag) {
            $(document).find(commentBoxSelector).parent().addClass('has-error');
            $(document).find(commentBoxSelector).after(msg);
        }else {
            $(document).find(commentBoxSelector).parent().removeClass('has-error');
            $(document).find(commentBoxSelector).parent().addClass('has-success');
        }
    }

    /**
     * Close and destroy the comment dialog
     */
    function closeAndDestroyCommentDialog() {
        $('.ui-dialog').remove();
    }

    $(document).on('input', '#qa-comment', function (e) {
        var comment = $(this).val();
        if (_.trim(comment).length === 0) {
            toggleCommentFormState(true);
        }else{
            toggleCommentFormState(false);
        }
    });

    $(document).on('click', '#'+reviewWindowId+'-comment-dlg-btn', function (e) {
        initializeCommentDialog();
        commentFormDialog.dialog('open');
    });

    /**
     * document container must be used because the DOM is dynamically changed by handlebars
     */
    $(document).on('click', '#'+reviewSubmitBtnId, function (e) {
        e.preventDefault();
        e.stopImmediatePropagation();
        console.log('about to save data bubbling');
        saveReviewComment();
    });


    /**
     * Submit all changes include status
     */
    $(document).on('click','[data-btn-form='+commentFormId+']',function (e) {
        e.preventDefault();
        e.stopPropagation();
        console.log('Attempting to change status...');

        if(!validateReviewForm()) {
            return false;
        }

        var transitionName = $(this).text();
        var nextStatusId = $(this).data('id');
        console.log('Transition selected: %s', transitionName);

        Dialogs.confirm('确定要提交问题评审并将其状态改为 ' + transitionName + ' 状态吗？', function (result) {
            if(result) {
                selectedQuestion.status = transitions[nextStatusId];
                saveReviewAndChangeStatus(nextStatusId);
            }
        }, false);
    });

    /**
     * Controls what to do when the modal is closed
     */
    detailsContainer.on('hidden.bs.modal','#'+reviewWindowId, function () {
        wrapUpModal();
        closeAndDestroyCommentDialog();
    });

    /**
     * Loads questions for reviewer role and fill the data table
     */
    function loadData() {
        QuestionUtils.loadReviewerQuestions(currentUser,function (data) {
            questions=data.questions;
            AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/question-list-table.hbs.html', function (template) {
                var templateData = {
                    questions: questions,
                    showDetails: true,
                    showActions: true,
                    showDelete: false
                };
                dataTable.empty();
                dataTable.html(template(templateData));
                Dialogs.showAjaxLoadInfo($('.msg-area'), questions.length);
            });
        });
    }

    /**
     * loads available statuses for selected question
     */
    function loadTransitions() {
        return QuestionUtils.loadTransitions(selectedQuestion, 'REVIEWER',function (data) {
            transitions = data.transitions;
            console.log('loaded question statuses');
            console.dir(transitions);
            AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/transition-dropdown-menu.hbs.html', function (template) {
                transitionContainer.empty();
                transitionContainer.html(template({statuses: transitions, dropDownListId: 'review-transition-dropdown'}));
            });
        });
    }

    function refreshReviewComments() {
        var url = CONTEXT.ctx + '/web/question/review/list.action';
        AjaxUtils.loadData(url,{id: selectedQuestion.id}, false).done(function (data) {
            console.log('template source');
            var template = Handlebars.compile($('#review-list-template').html());
            $('#q-review-container div#comments-list').html(template({
                comments: data.comments,
                user: CONTEXT.user
            }));
        })
    }

    /**
     * submit the review comment
     * @returns {boolean}
     */
    function saveReviewComment() {
        if(!validateReviewForm()){
            return false;
        }

        bindToModel();

        return AjaxUtils.postData(CONTEXT.ctx + '/web/question/review/save.action', {comment: qReview, reviewName: currentUser}, true);//.done(wrapUp);
    }

    function saveReviewAndChangeStatus(nextStatusId) {
        if(!validateReviewForm()){
            return false;
        }
        saveReviewComment();
        return QuestionUtils.changeStatus(selectedQuestion.id,nextStatusId,function () {
                    closeAndDestroyCommentDialog();
                    reviewWindow.modal('hide');
                    wrapUpModal();
                });
    }

    function bindToModel() {
        qReview.content = $(document).find(commentBoxSelector).val();
        qReview.question = selectedQuestion;
    }

    function validateReviewForm() {
        var commentBox = $(document).find(commentBoxSelector);
        var comment=commentBox.val();
        //if(_.trim(comment).length===0) {
        //    toggleCommentFormState(true);
        //    return false;
        //}
        return true;
    }

    /**
     * wrap up action for submit a review comment
     */
    function wrapUp() {
        refreshReviewComments();
    }

    /**
     * the action to take when the modal is closed
     */
    function wrapUpModal() {
        loadData();
        selectedQuestion = {};
    }
})();
