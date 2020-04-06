<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- Toolbar section --%>
<div class="container-fluid">
    <nav class="toolbar navbar navbar-default">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a id="create-project-btn" class="btn btn-primary navbar-btn-tm" href="#form-section" data-toggle="collapse" role="button"><i class="glyphicon glyphicon-plus-sign"></i>编辑项目状态</a>
                </li>
                <li>
                    <form id="search-form" class="navbar-form" role="search" onsubmit="return false">
                        <div class="form-group">
                            <input id="project-status-keyword" name="statusName" type="text" class="form-control" placeholder="状态">
                            <a id="search-btn" class="btn btn-danger navbar-btn-tm"><i class="glyphicon glyphicon-search"></i></a>
                        </div>
                    </form>
                </li>
            </ul>
        </div>
    </nav>

    <div id="form-section" class="collapse form-group">
        <form id="save-project-status-form" name="newProjectForm" class="form-inline" role="form">
            <div id="status-id-row" class="form-group hidden">
                <label for="project-status-id">ID</label>
                <input type="text" id="project-status-id" name="id" minlength="2" class="form-control" readonly>
            </div>
            <div class="form-group">
                <label for="project-status-name">状态</label>
                <input type="text" id="project-status-name" name="name" minlength="2" class="form-control" autofocus required>
            </div>
            <div class="checkbox">
                <label>
                    <input id="project-start-status" name="special" type="radio"> 起始状态
                </label>
            </div>

            <div class="checkbox">
                <label>
                    <input id="project-finish-status" name="special" type="radio"> 结束状态
                </label>
            </div>

            <button id="save-status-btn" type="submit" class="btn btn-primary">保存</button>
            <button id="reset-status-btn" type="reset" class="btn btn-warning">重置</button>
        </form>
    </div>
    <table id="project-status-table" class="table table-bordered table-striped table-responsive table-hover">
        <thead>
        <tr>
            <th class="action-col-2"></th>
            <th>ID</th>
            <th>状态</th>
            <th>初始状态</th>
            <th>结束状态</th>
            <th>创建日期</th>
            <th>更新日期</th>
        </tr>
        </thead>
    </table>
</div>


<script id="status-data-template" type="text/x-handlebars-template">
    <tbody>
    {{#each statuses}}
    <tr class="item-row" data-index="{{@index}}">
        <td>
            <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
            <a href="#" class="delete-item"><i class="glyphicon glyphicon-remove"></i></a>
        </td>
        <td>{{id}}</td>
        <td>{{name}}</td>
        <td>
            {{#if start}}
            <span class="label-lg label-danger">初始状态</span>
            {{/if}}
        </td>
        <td>
            {{#if finish}}
            <span class="label-lg label-danger">结束状态</span>
            {{/if}}
        </td>
        <td>{{date createdOn}}</td>
        <td>{{date updatedOn}}</td>
    </tr>
    {{/each}}
    </tbody>
</script>
<script id="delete-status-msg" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-danger" role="alert">确定要删除以下项目状态吗？</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
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
                <tr>
                    <td><b>初始状态</b></td>
                    <td>
                        {{#if start}}
                        <span class="label-lg label-danger">初始状态</span>
                        {{/if}}
                    </td>
                </tr>
                <tr>
                    <td><b>结束状态</b></td>
                    <td>
                        {{#if finish}}
                        <span class="label-lg label-danger">结束状态</span>
                        {{/if}}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</script>
