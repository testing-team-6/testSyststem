function PaperUtils(papers, dataTable){
    this.papers = papers;
    this.dataTable = dataTable;
}
PaperUtils.loadPaperDetails = function (paperId, callback) {
    if (!paperId) {
        console.error('Paper id is required to load its choices');
        return false;
    }
    return AjaxUtils.getData(CONTEXT.ctx + '/web/project/current/view-paper.action', {id: qpaperId})
        .done(callback);
};

PaperUtils.showPaperDetailsModal = function (options){
    if (!_.isPlainObject(options)) {
        console.error('The options must be enclosed in a project.');
        return false;
    }
    if(!_.has(options, 'question') ) {
        console.error('The [question] property is required!');
        return false;
    }
    return PaperUtils.loadPaperDetails(options.question.id, function (data) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/papers/paper-details-modal.hbs.html', function (template) {
            //template is the Handlebars template object/function
            var defaultParams = {
                user: CONTEXT.user,
                syllabus: CONTEXT.project.syllabus,
                ctx: CONTEXT.ctx,
                title:'试卷详情',
                modalId: 'paper-details-modal',
                container: '#content-panel',
                quetions: data.questions,
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
    });};