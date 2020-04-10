<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- Toolbar section --%>
<div class="container-fluid">
    <nav class="toolbar navbar navbar-default">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
<%--                <li>
                    <a id="create-project-btn" class="btn btn-primary navbar-btn-tm" href="#form-section" data-toggle="collapse" role="button"><i class="glyphicon glyphicon-plus-sign"></i>编辑题目状态转换</a>
                </li>--%>
<%--                <li>
                    <form id="search-form" class="navbar-form" role="search" onsubmit="return false">
                        <div class="form-group">
                            <input id="project-status-keyword" name="statusName" type="text" class="form-control" placeholder="状态">
                            <a id="search-btn" class="btn btn-danger navbar-btn-tm"><i class="glyphicon glyphicon-search"></i></a>
                        </div>
                    </form>
                </li>--%>
            </ul>
        </div>
    </nav>

    <div id="form-section" class="collapse form-group">
        <form id="save-transition-form" class="form-horizontal" role="form">
            <div class="form-group">
                <div class="col-md-4 col-lg-3">
                    <div class="form-group">
                        <div class="col-md-4 col-lg-4">
                            <label for="status-name" class="control-label">ID</label>
                        </div>
                        <div class="col-md-8 col-lg-6">
                            <input type="text" id="status-id" name="id" minlength="2" class="form-control" readonly disabled required>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-4 col-lg-4">
                            <label for="status-name" class="control-label">状态名</label>
                        </div>
                        <div class="col-md-8 col-lg-6">
                            <input type="text" id="status-name" name="name" minlength="2" class="form-control" readonly required>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-offset-4 col-lg-offset-4 col-md-8 col-lg-6">
                            <label id="question-start-status" class="label-lg">初始状态</label>
                        </div>
                    </div>
                </div>

                <div class="col-md-7">
                    <select id="transitional-states" class="form-control" size="9" multiple>
                    </select>

                </div>
            </div>

            <div class="form-group">
                <div class="col-md-2 col-md-offset-4 col-lg-1 col-lg-offset-3">
                    <button id="save-status-btn" type="submit" class="btn btn-block btn-primary">保存</button>
                </div>
                <div class="col-md-2 col-lg-1">
                    <button id="reset-status-btn" type="reset" class="btn btn-block btn-warning">重置</button>
                </div>
            </div>
        </form>
    </div>
    <table id="transition-table" class="table table-condensed table-bordered table-striped table-responsive table-hover">

    </table>
</div>


<script id="transition-data-template" type="text/x-handlebars-template">
    <thead>
    <tr class="warning">
        <th  class="action-col-2"></th>
        <th class="col-md-2 col-lg-1 column-separator">状态</th>
        <th colspan="{{maxTransitions}}"><!--{{@index}}--></th>
    </tr>
    </thead>
    <tbody>
    {{#each statuses}}
        <tr class="item-row" data-id="{{id}}" data-index="{{@index}}">
            <td class="warning">
                {{#unless finish}}
                    <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
                {{/unless}}
                    <a href="#" class="delete-item"><i class="glyphicon glyphicon-remove"></i></a>
            </td>
            <td class="warning column-separator" title="ID: {{id}}">
                {{#if start}}
                <span class="label-lg label-success">{{name}}</span>
                {{else if finish}}
                <span class="label-lg label-danger">{{name}}</span>
                {{else}}
                <span>{{name}}</span>
                {{/if}}
            </td>
            {{#getMapArrayValue ../map @index ../maxTransitions}}
            {{#is name "!==" ../name}}
                <td title="ID: {{id}}">
                    {{#if start}}
                        <span class="label-lg label-success">{{name}}</span>
                    {{else if finish}}
                        <span class="label-lg label-danger">{{name}}</span>
                    {{else}}
                        <span>{{name}}</span>
                    {{/if}}
                </td>
            {{else}}
                <td></td>
            {{/is}}
            {{/getMapArrayValue}}
        </tr>
    {{else}}
        <tr class="item-row" data-id="{{id}}" data-index="{{@index}}">
            <td class="warning">
                <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
            </td>
        </tr>
    {{/each}}
    </tbody>
</script>
<script id="save-transitions-msg" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-warning" role="alert">确定要修改 <b>{{status.name}}</b> 的状态转换吗？</p>
            <p>状态过度细节如下：</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-offset-1 col-md-10">
            <table class="table table-striped table-bordered">
                <thead>
                    <th>ID</th>
                    <th>名称</th>
                    <th>开始状态</th>
                    <th>结束状态</th>
                </thead>
                <tbody>
                {{#each statuses}}
                <tr>
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
                </tr>
                {{/each}}
                </tbody>
            </table>
        </div>
    </div>
</script>
<script id="delete-transitions-msg" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-danger" role="alert">确定要删除 <b>{{status.name}}</b> 的全部状态转换吗？</p>
            <p>状态过度细节如下：</p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-offset-1 col-md-10">
            <table class="table table-striped table-bordered">
                <thead>
                <th>ID</th>
                <th>名称</th>
                <th>开始状态</th>
                <th>结束状态</th>
                </thead>
                <tbody>
                {{#each statuses}}
                <tr>
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
                </tr>
                {{/each}}
                </tbody>
            </table>
        </div>
    </div>
</script>
<script id="transition-list-items" type="text/x-handlebars-template">
    {{#each statuses}}
            <option value="{{id}}" title="{{name}}">{{name}}</option>
    {{/each}}
</script>
<script src="${ctx}/assets/js/admin/question/question-status-transition.js"></script>
