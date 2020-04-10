<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- Toolbar section --%>
<div class="container-fluid">
    <nav class="toolbar navbar navbar-default">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a id="create-status-btn" class="btn btn-primary navbar-btn-tm" href="#save-status-form" data-toggle="collapse" role="button"><i class="glyphicon glyphicon-plus-sign"></i>编辑题目状态</a>
                </li>
                <li>
                    <form class="navbar-form" role="search" onsubmit="return false;">
                        <div class="form-group">
                            <input id="status-keyword" type="text" class="form-control" placeholder="状态">
                            <a id="search-user-btn" class="btn btn-danger navbar-btn-tm"><i class="glyphicon glyphicon-search"></i></a>
                        </div>
                    </form>
                </li>
            </ul>
        </div>
    </nav>

    <form id="save-status-form" class="collapse titled-border-form form-horizontal" role="form">
        <fieldset>
            <legend>&nbsp;</legend>
            <div id="status-id-row" class="form-group hidden">
                <label for="status-id" class="col-md-2 col-lg-1 control-label">ID</label>
                <div class="col-md-1">
                    <input type="text" id="status-id" name="id" minlength="2" class="form-control" readonly>
                </div>
            </div>
            <div class="form-group">
                <label for="status-name" class="col-md-2 col-lg-1 control-label">状态</label>
                <div class="col-md-3 col-lg-2">
                    <input type="text" id="status-name" name="name" minlength="2" class="form-control" required>
                </div>
                <label class="checkbox-inline">
                    <input id="start-status" name="special" type="radio"> 起始状态
                </label>
                <label class="checkbox-inline">
                    <input id="finish-status" name="special" type="radio"> 结束状态
                </label>
                <label class="checkbox-inline">
                    <input id="na-status" name="special" type="radio"> 取消
                </label>
            </div>

            <div class="form-group">
                <label class="col-md-2 col-lg-1 control-label">权限分配</label>
                <div class="col-md-6">
                    <label class="checkbox-inline">
                        <input type="checkbox" id="status-acl-facilitator" name="accessibleByFacilitator"> 主持人
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="status-acl-author" name="accessibleByAuthor"> 作者
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="status-acl-reviewer" name="accessibleByReviewer"> 评审
                    </label>
                    <label class="checkbox-inline">
                        <input type="checkbox" id="status-acl-qa" name="accessibleByQualityAdmin" checked> 质管
                    </label>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-2 col-md-2 col-lg-offset-1 col-lg-1">
                    <button id="save-status-btn" type="submit" class="btn btn-primary">保存</button>
                </div>
                <div class="col-md-2 col-lg-1">
                    <button id="reset-status-btn" type="reset" class="btn btn-warning">重置</button>
                </div>
            </div>
        </fieldset>
    </form>

    <table id="status-data-table" class="table table-condensed table-bordered table-striped table-responsive table-hover">
    </table>
</div>


<script id="status-data-template" type="text/x-handlebars-template">
    <thead>
    <tr>
        <th class="action-col-2"></th>
        <th>ID</th>
        <th>状态</th>
        <th>初始状态</th>
        <th>结束状态</th>
        <th>
            <div class="row">
                <label class="col-md-offset-4 col-md-4 label-lg label-info">权限控制</label>
            </div>
            <div class="row">
                <span class="col-md-3">主持人</span>
                <span class="col-md-3">作者</span>
                <span class="col-md-3">评审</span>
                <span class="col-md-3">质管</span>
            </div>
        </th>
    </tr>
    </thead>
    <tbody>
    {{#each statuses}}
    <tr class="item-row" data-index="{{@index}}">
        <td>
            <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
            <a href="#" class="delete-item"><i class="glyphicon glyphicon-remove"></i></a>
        </td>
        <td>{{id}}</td>
        <td title="
        创建日期：{{date createdOn}}
更新日期：{{date updatedOn}}">{{name}}</td>
        <td>
            {{#if start}}
            <span class="label-lg label-success">初始状态</span>
            {{/if}}
        </td>
        <td>
            {{#if finish}}
            <span class="label-lg label-danger">结束状态</span>
            {{/if}}
        </td>
        <td>
            <span class="col-md-3">
                {{#if accessibleByFacilitator}}
                    <i class="label-lg label-success glyphicon glyphicon-ok"></i>
                {{else}}
                    <i class="label-lg label-default glyphicon glyphicon-remove"></i>
                {{/if}}
            </span>
            <span class="col-md-3">
                {{#if accessibleByAuthor}}
                    <i class="label-lg label-success glyphicon glyphicon-ok"></i>
                {{else}}
                    <i class="label-lg label-default glyphicon glyphicon-remove"></i>
                {{/if}}
            </span>
            <span class="col-md-3">
                {{#if accessibleByReviewer}}
                    <i class="label-lg label-success glyphicon glyphicon-ok"></i>
                {{else}}
                    <i class="label-lg label-default glyphicon glyphicon-remove"></i>
                {{/if}}
            </span>
            <span class="col-md-3">
                {{#if accessibleByQualityAdmin}}
                    <i class="label-lg label-success glyphicon glyphicon-ok"></i>
                {{else}}
                    <i class="label-lg label-default glyphicon glyphicon-remove"></i>
                {{/if}}
            </span>
        </td>
    </tr>
    {{/each}}
    </tbody>
</script>

<script id="delete-status-msg" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-danger" role="alert">确定要删除以下状态吗？</p>
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
<script src="${ctx}/assets/js/admin/question/question-status.js"></script>
