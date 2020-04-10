<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="page-header">
    <h1>题目管理
        <small>${PROJECT.name}</small>
    </h1>
</div>

<div class="container-fluid">
    <div class="row">
        <nav class="toolbar navbar navbar-default">
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li>
                        <a id="show-edit-question-form-btn" class="btn btn-primary navbar-btn-tm" data-toggle="modal"
                           role="button"><i class="glyphicon glyphicon-plus-sign"></i>新增题目</a>
                    </li>
                    <li>
                        <a id="reload-question-btn" href="#" class="btn btn-primary navbar-btn-tm" role="button"><i
                                class="glyphicon glyphicon-refresh"></i>刷新</a>
                    </li>
                    <li>
                        <a id="batch-update-initial-status-btn" href="#" class="btn btn-primary navbar-btn-tm disabled"
                           role="button"><i class="glyphicon glyphicon-pencil"></i>批量修改状态</a>
                    </li>
                    <li>
                        <form class="navbar-form" role="search" onsubmit="return false;">
                            <div class="form-group">
                                <input id="question-keyword" type="text" class="form-control" placeholder="题目关键字">
                                <a id="search-syllabus-btn" class="btn btn-danger navbar-btn-tm"><i
                                        class="glyphicon glyphicon-search"></i></a>
                            </div>
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="row">
        <jsp:include page="/WEB-INF/partials/client/question-management/new-question-form.jsp"/>
    </div>

    <jsp:include page="/WEB-INF/partials/pagination-snippet.jsp"/>

    <div class="msg-area form-group">
    </div>
    <div class="row">
        <table id="question-mgmt-table" class="table table-striped table-bordered table-responsive table-condensed">
        </table>
    </div>
</div>

<div id="batch-update-status-modal" class="modal fade" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header modal-header-warning">
                <div>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <span class="modal-title">批量修改状态</span>
                </div>
            </div>
            <div class="modal-body">
                <h4>请慎重选择要将初始状态的问题转换到的状态。如不做修改，请直接关闭对话框。</h4>

                <div id="init-transition-actions-container"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
<script id="initial-status-transition" type="text/x-handlebars-template">
    <ul class="list-inline">
        {{#each transitions}}
        <li>
            <button class="transition-initial btn btn-primary" data-id="{{id}}">{{name}}</button>
        </li>
        {{/each}}
    </ul>
</script>
<script id="delete-msg-template" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-danger" role="alert">
                确定要删除以下题目吗？
            </p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-offset-1 col-md-10">
            <table class="table table-striped table-bordered">
                <tbody>
                <tr>
                    <td><b>ID</b></td>
                    <td>{{id}}</td>
                </tr>
                <tr>
                    <td><b>题干</b></td>
                    <td>{{stem}}</td>
                </tr>
                <tr>
                    <td><b>知识点</b></td>
                    <td>
                        {{#each knowledgePoints}}
                        <span>{{number}}&nbsp;{{title}}</span>
                        {{/each}}
                    </td>
                </tr>
                <tr>
                    <td><b>状态</b></td>
                    <td>{{status.name}}</td>
                </tr>
                <tr>
                    <td><b>作者</b></td>
                    <td>{{author.username}}</td>
                </tr>
                <tr>
                    <td><b>质管</b></td>
                    <td>{{qualityAdmin.username}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</script>

<script src="${ctx}/assets/js/client/question-management/manage-questions.js"></script>
