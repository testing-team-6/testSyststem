'use strict';
function SyllabusUtils(){}

SyllabusUtils.loadSyllabuses = function (activeOnly) {
    var url = '';
    if (activeOnly || _.isUndefined(activeOnly)) {
        url = CONTEXT.ctx + '/web/syllabus/list-active.action';
    }else {
        url = CONTEXT.ctx + '/web/syllabus/list.action';
    }
    console.log('Loading syllabuses from url: %s', url);
    return AjaxUtils.get(url);
};

SyllabusUtils.loadSyllabusAsSelectList = function (listSelector, activeOnly) {
    return SyllabusUtils.loadSyllabuses(activeOnly).done(function (data) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/syllabus/syllabus-list-options.hbs.html', function (template) {
            listSelector.html(template(data));
        });
    });
};

SyllabusUtils.loadChapters = function (syllabus) {
    if (_.isUndefined(syllabus) ||!_.isObject(syllabus)) {
        console.error('Syllabus is required!');
        return false;
    }
    return AjaxUtils.loadData(CONTEXT.ctx + '/web/chapter/list.action', {syllabusId: syllabus.id});
};

SyllabusUtils.loadChaptersAsSelectList = function (syllabus, listSelector) {
    return SyllabusUtils.loadChapters(syllabus).done(function (data) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/syllabus/chapter-list-options.hbs.html', function (template) {
            listSelector.html(template(data));
        });
    });
};


SyllabusUtils.saveKnowledgePoint = function (knowledgePoint) {
    console.log('Saving knowledge point...');
    return AjaxUtils.postData(CONTEXT.ctx+'/web/knowledge-point/save.action', {knowledgePoint: knowledgePoint});
};

SyllabusUtils.loadKnowledgePointsBySyllabus = function (syllabus) {
    console.log('Finding knowledge points for syllabus %s', syllabus);
    return AjaxUtils.loadData(CONTEXT.ctx + '/web/knowledge-point/list-by-syllabus.action', {syllabus: syllabus}, true);
};
