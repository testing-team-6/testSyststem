<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="page-header">
    <h1>组卷管理
        <small>${PROJECT.name}</small>
    </h1>
</div>

<div class="container-fluid">
    <div class="row">
        <nav class="toolbar navbar navbar-default">
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">

                    <li>
                        <a id="reload-paper-btn" href="#" class="btn btn-primary navbar-btn-tm" role="button"><i
                                class="glyphicon glyphicon-refresh"></i>刷新</a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="row">
        <jsp:include page="/WEB-INF/partials/client/paper-management/new-paper-form.jsp"/>
    </div>

    <jsp:include page="/WEB-INF/partials/pagination-snippet.jsp"/>

    <div class="msg-area form-group">
    </div>
    <div class="row">
        <table id="paper-mgmt-table" class="table table-striped table-bordered table-responsive table-condensed">
        </table>
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
                确定要删除以下试卷吗？
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
                    <td><b>名称</b></td>
                    <td>{{name}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</script>

<script src="${ctx}/assets/js/client/paper-management/manage-papers.js"></script>

