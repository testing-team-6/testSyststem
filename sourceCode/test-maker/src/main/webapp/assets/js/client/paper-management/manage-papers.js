(function () {

    /*
     * Definition of DOM variables
     */
    var dataTable = $('#paper-mgmt-table');
    
    //form elements
    var searchBox = $('#paper-keyword');

    var papers;
    var selectedPaper = {};
    /*
     * action urls
     */
    var listPapersURL = CONTEXT.ctx + '/web/project/current/list-papers.action';
    var paperPagingUrl=CONTEXT.ctx + '/web/project/current/paging-paper.action';
    var deletePaperURL = CONTEXT.ctx + '/web/project/current/delete-paper.action';

    var pagingHelper = new PaginationHelper(paperPagingUrl, listPapersURL, function (data) {
        papers = data.papers;
        console.log('%s papers loaded.', papers.length);
        displayPapers(papers);
    });
    /*
    *    Default function when the page loads
    * */
    initialize();
    /**
     * Page initialization
     */
    function initialize() {
        pagingHelper.loadPagingInfo();
        loadData();
        new TableFilter(dataTable, searchBox);
    }



    /**
     * clicking the remove sign will delete the selected item
     */
    dataTable.on('click', '.delete-item', function (e) {
        e.preventDefault();

        //gets the data-index attribute in the table
        var index = $(this).closest('tr').data('index');
        console.log('Select row #%s for editing', index);
        selectedPaper= papers[index];

        deletePaper();
    });

    dataTable.on('click', '.print-item', function (e) {
        e.preventDefault();
        var index = $(this).closest('tr').data('index');
        console.log('Select row #%s for print', index);
        exportPaper(papers[index].id);
    });

    function displayPapers(papers) {
        AjaxUtils.getTemplateAjax(CONTEXT.ctx +'/assets/templates/papers/paper-list-table.hbs.html', function (template) {
            var templateData = {
                papers: papers,
                showActions: true,
                showDelete: true,
                showDetails: false,
                showPrint: true
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
            });
    }

    function deletePaper() {

        var source = $('#delete-msg-template').html();
        var template = Handlebars.compile(source);
        var msg = template(selectedPaper);
        Dialogs.confirm(msg, function (result) {
            if (result) {
                //user selected OK
                dataTable.find('[data-id="'+selectedPaper.id+'"]').hide();
                var ids = selectedPaper.id;  //拼接后的ids
                AjaxUtils.postData(deletePaperURL, {id: ids}, false).done(function () {
                    loadData();
                });
            }else {
                //cancel the operation
            }
        });
    }

    /**
     * action for export paper
     * @param paperId
     */
    function exportPaper(paperId) {
        Dialogs.confirm('确定要导出组卷吗？操作可能会花费较长时间。', function (result) {
            if (result) {
                var url = CONTEXT.ctx + '/web/admin/export-paper.action?paperId=' + paperId;
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
                            a.download = 'paper'+paperId+'.pdf';
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


})();
