<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- Toolbar section --%>
<div class="container-fluid">
    <nav class="toolbar navbar navbar-default">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a id="create-project-btn" class="btn btn-primary navbar-btn-tm" href="#adm-save-project-form" data-toggle="collapse" role="button"><i class="glyphicon glyphicon-plus-sign"></i>编辑项目</a>
                </li>
                <li>
                    <form class="navbar-form" role="search" onsubmit="return false;">
                        <div class="form-group">
                            <input id="project-keyword" type="text" class="form-control" name="projectName" placeholder="项目名">
                            <a id="search-user-btn" class="btn btn-danger navbar-btn-tm"><i class="glyphicon glyphicon-search"></i></a>
                        </div>
                    </form>
                </li>
            </ul>
        </div>
    </nav>

    <form id="adm-save-project-form" name="newProjectForm" class="collapse titled-border-form form-horizontal" role="form">
        <fieldset>
            <legend>&nbsp;</legend>
            <div class="form-group" >
                <label for="project-id" class="col-md-2 col-lg-1 control-label">ID</label>
                <div class="col-md-3 col-lg-2">
                    <input id="project-id" type="text" name="id" class="form-control" readonly>
                </div>
                <label for="project-name" class="col-md-2 col-lg-1 control-label">项目名</label>
                <div class="col-md-3 col-lg-2">
                    <input type="text" id="project-name" name="name" minlength="4" class="form-control" autofocus required>
                </div>
                <div class="col-md-1 check-indicator"></div>
            </div>
            <div class="form-group" >
                <label for="project-syllabus-list" class="col-md-2 col-lg-1 control-label">对应大纲</label>
                <div class="col-md-3 col-lg-2">
                    <select id="project-syllabus-list" name="syllabus" data-placeholder="选择大纲" data-live-search="true" class="select-list form-control" required>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="facilitator-list" class="col-md-2 col-lg-1 control-label">主持人</label>
                <div class="col-md-3 col-lg-2">
                    <select id="facilitator-list" name="facilitator" data-placeholder="选择主持人" data-live-search="true" class="select-list form-control" required>
                    </select>
                </div>
                <label for="project-status-list" class="col-md-2 col-lg-1 control-label">状态</label>
                <div class="col-md-3 col-lg-2">
                    <select id="project-status-list" name="status" data-placeholder="选择状态" data-live-search="true" class="select-list form-control">
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 col-lg-1 control-label">开始日期</label>
                <div class="col-md-3 col-lg-2">
                    <input id="startDate" name="startDate" type="text" class="datepicker form-control" required>
                </div>
                <label for="finishDate" class="col-md-2 col-lg-1 control-label">结束日期</label>
                <div class="col-md-3 col-lg-2">
                    <input id="finishDate" name="finishDate" type="text" class="datepicker form-control" required>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-2 col-lg-offset-1 col-md-2 col-lg-1">
                    <button id="save-project-btn" type="submit" class="btn btn-primary">提交</button>
                </div>
                <div class="col-md-2 col-lg-1">
                    <button type="reset" class="btn btn-default">重置</button>
                </div>
            </div>
        </fieldset>
    </form>

    <table id="project-table" class="table table-bordered table-striped table-responsive table-hover">

    </table>
</div>


<div>
</div>
<script id="project-data-template" type="text/x-handlebars-template">
    <thead>
    <tr>
        <th>ID</th>
        <th>项目名</th>
        <th>大纲</th>
        <th>主持人</th>
        <th>状态</th>
        <th>开始日期</th>
        <th>结束日期</th>
        <th>导出时间</th>
        <th>导出人</th>
        <th class="action-col-3"></th>
    </tr>
    </thead>
    <tbody id="project-table-body">
        {{#each projects}}
        <tr class="item-row" data-index="{{@index}}">
            <td>{{id}}</td>
            <td title="创建时间：{{date createdOn}}，更新时间：{{date updatedOn}}">{{name}}</td>
            <td>
                {{#if syllabus}}
                    {{syllabus.level}} ({{syllabus.version}})
                {{/if}}
            </td>
            <td>{{facilitator.username}}</td>
            <td>
                {{#if status.start}}
                    <span class="label-lg label-success">{{status.name}}</span>
                {{else if status.finish}}
                    <span class="label-lg label-danger">{{status.name}}</span>
                {{else}}
                    <span>{{status.name}}</span>
                {{/if}}
            </td>
            <td>{{date startDate format='LL' }}</td>
            <td>{{date finishDate format='LL'}}</td>
            <td>{{date exportedOn format='LLL'}}</td>
            <td>{{exportedBy.fullName}}</td>
            <td>
<%--                {{#if status.finish}}
                <i class="glyphicon glyphicon-off" title="项目已结束，不能编辑！"></i>
                {{else}}
                <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
                {{/if}}--%>
                <a href="#" class="edit-item" title="编辑项目"><i class="glyphicon glyphicon-edit"></i></a>
                <a href="#" class="view-item" title="查看项目详情"><i class="glyphicon glyphicon-file"></i></a>
                <a href="#" class="export-item" title="导出项目"><i class="glyphicon glyphicon-export"></i></a>
            </td>
        </tr>
        {{/each}}
    </tbody>
</script>

<script id="facilitator-option-list-template" type="text/x-handlebars-template">
    {{#each users}}
    <option value="{{id}}" data-index="{{@index}}" title="{{email}}" class="option-item">{{username}}</option>
    {{/each}}
</script>

<script id="project-status-list-template" type="text/x-handlebars-template">
    {{#each statuses}}
    <option value="{{id}}" data-index="{{@index}}" class="option-item">{{name}}</option>
    {{/each}}
</script>
