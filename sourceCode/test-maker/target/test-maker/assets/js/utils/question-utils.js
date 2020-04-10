'use strict';
function QuestionUtils(questions, dataTable){
    this.questions = questions;
    this.dataTable = dataTable;
}



/**
 * Loads all question choices for the given question id
 * @param questionId
 * @param callback callback to handle the result. The callback parameter follows the done method for the deferred object
 * @returns {*} deferred object for the ajax call. Can be used for result handling.
 */
QuestionUtils.loadChoices = function (questionId, callback) {
    if (!questionId) {
        console.error('Question id is required to load its choices');
        return false;
    }

    if (questionId < 1) {
        Dialogs.error('题目 ID 无效，ID 必须大于 0。');
        return false;
    }

    console.log('Loading question choices for question: %d', questionId);
    return AjaxUtils.getData( CONTEXT.ctx + '/web/question/choice/list.action', {questionId: questionId})
        .done(callback);
};

QuestionUtils.displayChoices = function (contentHolder, questionId) {
    return QuestionUtils.loadChoices(questionId,function (data, textStatus, jqXHR) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/choice-list-table.hbs.html', function (template) {
            contentHolder.empty();
            contentHolder.html(_.unescape(template({choices: data.choices, showDelete: true})))
        });
    });
};

/**
 * Load the details for the question with the given id.
 * @param questionId
 * @param callback
 * @returns {*}
 */
QuestionUtils.loadQuestionDetails = function (questionId, callback) {
    if (!questionId) {
        console.error('Question id is required to load its choices');
        return false;
    }
    return AjaxUtils.getData(CONTEXT.ctx + '/web/project/current/view-question.action', {id: questionId})
        .done(callback);
};

QuestionUtils.showQuestionDetailsModal = function (options) {
    if (!_.isPlainObject(options)) {
        console.error('The options must be enclosed in a project.');
        return false;
    }
    if(!_.has(options, 'question') ) {
        console.error('The [question] property is required!');
        return false;
    }
    return QuestionUtils.loadQuestionDetails(options.question.id, function (data) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/question-details-modal.hbs.html', function (template) {
            //template is the Handlebars template object/function
            var defaultParams = {
                user: CONTEXT.user,
                syllabus: CONTEXT.project.syllabus,
                ctx: CONTEXT.ctx,
                title:'题目详情',
                modalId: 'question-details-modal',
                container: '#content-panel',
                choices: data.choices,
                comments: data.comments,
                qaComments: data.qaComments,
                showModal: true,
                showQAPanel: true
            };
            var merged = _.extend(defaultParams, options);

            if(!_.has(merged, 'container') ) {
                console.error('The [container] property is required!');
                return false;
            }

            var modalId=merged.modalId;
            $(merged.container).find('#'+modalId).remove();
            $(merged.container).append(_.unescape(template(merged)));

            if (merged.showModal) {
                $('#' + modalId).modal('show');
            }
        });
    });
};
QuestionUtils.showQuestionAuthoringModal = function (options) {
    if (!_.isPlainObject(options)) {
        console.error('The options must be enclosed in a object.');
        return false;
    }
    if(!_.has(options, 'question') ) {
        console.error('The [question] property is required!');
        return false;
    }

    return QuestionUtils.loadQuestionDetails(options.question.id, function (data) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/question-authoring-modal.hbs.html', function (template) {
            //template is the Handlebars template object/function
            var defaultParams = {
                user: CONTEXT.user,
                syllabus: CONTEXT.project.syllabus,
                ctx: CONTEXT.ctx,
                modalId: 'authoring-question-modal',
                container: '#content-panel',
                choices: data.choices,
                showModal: true
            };
            var merged = _.extend(defaultParams, options);

            if(!_.has(merged, 'container') ) {
                console.error('The [container] property is required!');
                return false;
            }

            var modalId=merged.modalId;
            $(merged.container).find('#'+modalId).remove();
            $(merged.container).append(_.unescape(template(merged)));

            if (merged.showModal) {
                $('#' + modalId).modal('show');
            }
        });
    });
};

QuestionUtils.showQuestionReviewModal = function (options) {
    if (!_.isPlainObject(options)) {
        console.error('The options must be enclosed in a object.');
        return false;
    }
    if(!_.has(options, 'question') ) {
        console.error('The [question] property is required!');
        return false;
    }
    return QuestionUtils.loadQuestionDetails(options.question.id, function (data) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/questions/question-review-modal.hbs.html', function (template) {
            //template is the Handlebars template object/function
            var defaultParams = {
                user: CONTEXT.user,
                syllabus: CONTEXT.project.syllabus,
                ctx: CONTEXT.ctx,
                title:'题目详情',
                modalId: 'question-details-modal',
                container: '#content-panel',
                choices: data.choices,
                comments: data.comments,
                qaComments: data.qaComments,
                showModal: true
            };
            var merged = _.extend(defaultParams, options);

            if(!_.has(merged, 'container') ) {
                console.error('The [container] property is required!');
                return false;
            }

            var modalId=merged.modalId;
            $(merged.container).find('#'+modalId).remove();
            $(merged.container).append(_.unescape(template(merged)));

            if (merged.showModal) {
                $('#' + modalId).modal('show');
            }
        });
    });
};

QuestionUtils.loadAuthorQuestions = function (username, callback) {

    if (!username) {
        console.error('username is required for loading questions');
        return false;
    }
    if (_.isPlainObject(username)) {
        console.error("The user name as string is required, but given an object.");
        return false;
    }
    console.log('Loading questions for %s', username);
    var url = CONTEXT.ctx + '/web/project/current/list-author-questions.action';
    var deferred= AjaxUtils.getData(url, {username: username});
    if (_.isUndefined(callback)) {
        return deferred;
    }else{
        return deferred.done(callback);
    }
};


QuestionUtils.loadReviewerQuestions = function (username, callback) {
    if (!username) {
        console.error('username is required for loading questions');
        return false;
    }

    console.log('Loading questions for %s', username);
    var url = CONTEXT.ctx + '/web/project/current/list-reviewer-questions.action';
    var deferred= AjaxUtils.getData(url, {username: username});
    if (_.isUndefined(callback)) {
        return deferred;
    }else{
        return deferred.done(callback);
    }
};

/**
 *
 * @param username
 * @param callback
 * @returns {*}
 */
QuestionUtils.loadQAQuestions = function (username, callback) {
    if (!username) {
        console.error('username is required for loading questions');
        return false;
    }

    console.log('Loading questions for %s', username);
    var url = CONTEXT.ctx + '/web/project/current/list-qa-questions.action';
    var deferred= AjaxUtils.getData(url, {username: username});
    if (_.isUndefined(callback)) {
        return deferred;
    }else{
        return deferred.done(callback);
    }
};

/**
 *
 * @param questionId
 * @param nextStatusId
 * @param callback
 * @returns {*}
 */
QuestionUtils.changeStatus = function (questionId,nextStatusId,callback) {
    var url = CONTEXT.ctx + '/web/question/change-status.action';
    var deferred= AjaxUtils.postData(url,{questionId: questionId, statusId: nextStatusId});
    if (_.isUndefined(callback) || _.isNull(callback)) {
        return deferred;
    }
    if (_.isFunction(callback)) {
        deferred.done(callback);
    }
    return deferred;
};

/**
 *
 * @param question
 * @param callback
 * @returns {*}
 */
QuestionUtils.saveQuestion = function (question,callback) {
    if (_.isUndefined(question)) {
        console.error('Incoming question is undefined!');
        return false;
    }
    var deferred= AjaxUtils.postData(CONTEXT.ctx + '/web/project/current/save-question.action',
        {question: question}
        ,true);
    if (!_.isUndefined(callback) && _.isFunction(callback)) {
        deferred.done(callback);
    }
    return deferred;
};

QuestionUtils.loadTransitions = function (question, accessRole, callback) {
    var url = CONTEXT.ctx + '/web/question/status/list-transitions.action';
    return $.get(url,{statusId: question.status.id}).done(function (data, textStatus, jqXHR) {
        var transitions = data.transitions;
        console.log('%d transition statuses loaded for selected question status %d', transitions.length, question.status.id);
        if (!_.isUndefined(callback) && _.isFunction(callback)) {
            callback(data);
        }
    });
};

QuestionUtils.deleteReview = function (reviewId) {
    if (!reviewId || _.isNaN(reviewId) || reviewId < 1) {
        Dialogs.error('评审信息 ID 无效，操作无法继续！');
        return false;
    }
    return AjaxUtils.postData(CONTEXT.ctx + '/web/question/review/delete.action', {
        id: reviewId,
        operatorUserName: CONTEXT.user.username
    }, false);
};
