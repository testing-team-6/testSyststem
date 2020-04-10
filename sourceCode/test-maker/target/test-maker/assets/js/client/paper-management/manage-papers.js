(function () {

    /*
     * Definition of DOM variables
     */
    var dataTable = $('#paper-mgmt-table');
    var questionForm = $('#edit-paper-form');

    var toggleFormBtn = $('#show-edit-paper-form-btn');
    var newPaperModal = $('#new-paper-modal');


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
})();
