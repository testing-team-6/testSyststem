function ProjectUtils(){
}

ProjectUtils.showProjectDetailsModal = function (project) {
    if (!_.isObject(project)) {
        console.error('The project object is required.');
        return false;
    }

    return AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/project-management/project-details-modal.hbs.html', function (template) {
        //template is the Handlebars template object/function
        var data = {
            ctx: CONTEXT.ctx,
            project: project
        };
        $(_.unescape(template(data))).modal('show');
    });
};
