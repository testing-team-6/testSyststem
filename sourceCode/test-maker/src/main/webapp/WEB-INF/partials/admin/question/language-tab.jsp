<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- Toolbar section --%>
<div class="container-fluid">
    <nav class="toolbar navbar navbar-default">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a id="create-qlanguage-btn" class="btn btn-primary navbar-btn-tm" href="#save-qlanguage-form" data-toggle="collapse" role="button">
                        <i class="glyphicon glyphicon-plus-sign"></i>编辑题目语言
                    </a>
                </li>
                <li>
                    <form class="navbar-form" role="search" onsubmit="return false;">
                        <div class="form-group">
                            <input id="qlanguage-keyword" type="text" class="form-control" placeholder="语言">
                            <a id="search-user-btn" class="btn btn-danger navbar-btn-tm"><i class="glyphicon glyphicon-search"></i></a>
                        </div>
                    </form>
                </li>
            </ul>
        </div>
    </nav>

    <form id="save-qlanguage-form" class="collapse form-inline" role="form">
        <div id="status-id-row" class="form-group">
            <label for="qlanguage-id" class="control-label">ID</label>
            <input type="text" id="qlanguage-id" name="id" class="form-control" readonly>
        </div>
        <div class="form-group">
            <label for="qlanguage-name" class="control-label">名称</label>
            <input type="text" id="qlanguage-name" name="name" minlength="2" class="form-control" required>
        </div>
        <button id="save-qlanguage-btn" type="submit" class="btn btn-primary">保存</button>
        <button id="reset-qlanguage-btn" type="reset" class="btn btn-warning">重置</button>
    </form>
    <br>
    <table id="qlanguage-data-table" class="table table-condensed table-bordered table-striped table-responsive table-hover">
    </table>
</div>


<script id="qlanguage-data-template" type="text/x-handlebars-template">
    <thead>
    <tr>
        <th class="action-col-2"></th>
        <th>ID</th>
        <th>名称</th>
        <th>创建时间</th>
        <th>修改时间</th>
    </tr>
    </thead>
    <tbody>
    {{#each aaData}}
        <tr class="item-row" data-index="{{@index}}">
            <td>{{id}}</td>
            <td>{{name}}</td>
            <td>{{date createdOn}}</td>
            <td>{{date updatedOn}}</td>
            <td>
                <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
                <a href="#" class="delete-item"><i class="glyphicon glyphicon-remove"></i></a>
            </td>
        </tr>
    {{/each}}
    </tbody>
</script>

<script id="delete-qlanguage-msg" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-danger" role="alert">确定要删除以下题目语言吗？</p>
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
                </tbody>
            </table>
        </div>
    </div>
</script>
<script src="${ctx}/assets/js/admin/question/question-language.js"></script>
