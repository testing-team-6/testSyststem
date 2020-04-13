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
                        <a id="show-edit-paper-form-btn" class="btn btn-primary navbar-btn-tm" data-toggle="modal"
                           role="button"><i class="glyphicon glyphicon-plus-sign"></i>新建组卷</a>
                    </li>

                    <li>
                        <a id="reload-paper-btn" href="#" class="btn btn-primary navbar-btn-tm" role="button"><i
                                class="glyphicon glyphicon-refresh"></i>刷新</a>
                    </li>
                    <li>
                        <form class="navbar-form" role="search" onsubmit="return false;">
                            <div class="form-group">
                                <input id="paper-keyword" type="text" class="form-control" placeholder="试卷关键字">
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


<script src="${ctx}/assets/js/client/paper-management/manage-papers.js"></script>

