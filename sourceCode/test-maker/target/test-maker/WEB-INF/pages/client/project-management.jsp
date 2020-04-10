<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="page-header">
    <h1>项目管理
        <small>${PROJECT.name}</small>
    </h1>
</div>

<div class="container-fluid">
    <div class="row">
        <nav class="toolbar navbar navbar-default">
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li>
                        <button id="export-current-project" class="btn btn-primary navbar-btn-tm"><i
                                class="glyphicon glyphicon-export"></i>导出当前项目
                        </button>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <div class="row">
        <div class="col-md-4">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">修改项目信息</h3>
                </div>
                <div id="modify-project-panel" class="panel-body">
                    <jsp:include page="/WEB-INF/partials/client/project-management/project-form.jsp"/>
                </div>
            </div>
        </div>
        <div class="col-md-8">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">管理项目用户</h3>
                </div>
                <div class="panel-body">
                    <div></div>
                    <div>
                        <form id="edit-project-user-form" class="form-horizontal">
                            <div class="form-group">
                                <div class="col-md-12 col-lg-12">
                                    <select id="project-user-list" class="form-control" size="9" multiple></select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-3 col-lg-3">
                                    <button id="save-project-users" class="btn btn-primary btn-block">保存修改</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script id="project-user-options" type="text/x-handlebars-template">
    {{#each users}}
    <option value="{{id}}" title="{{username}}" data-index="{{@index}}">
        {{#if fullName}}
        {{username}} ({{fullName}})
        {{else}}
        {{username}}
        {{/if}}
    </option>
    {{/each}}
</script>
<script src="${ctx}/assets/js/client/project-management/manage-project.js"></script>
<script src="${ctx}/assets/js/client/project-management/manage-users.js"></script>
