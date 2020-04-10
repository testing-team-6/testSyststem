<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- Toolbar section --%>
<div class="container-fluid">
    <nav class="toolbar navbar navbar-default">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li>
                    <a id="create-status-btn" class="btn btn-primary navbar-btn-tm" href="#save-qtype-form" data-toggle="collapse" role="button">
                        <i class="glyphicon glyphicon-plus-sign"></i>编辑题目类型
                    </a>
                </li>
                <li>
                    <form class="navbar-form" role="search" onsubmit="return false;">
                        <div class="form-group">
                            <input id="qtype-keyword" type="text" class="form-control" placeholder="类型">
                            <a id="search-user-btn" class="btn btn-danger navbar-btn-tm"><i class="glyphicon glyphicon-search"></i></a>
                        </div>
                    </form>
                </li>
            </ul>
        </div>
    </nav>

    <form id="save-qtype-form" class="collapse form-inline" role="form">
        <div id="status-id-row" class="form-group">
            <label for="qtype-id" class="control-label">ID</label>
            <input type="text" id="qtype-id" name="id" minlength="2" class="form-control" readonly>
        </div>
        <div class="form-group">
            <label for="qtype-name" class="control-label">名称</label>
            <input type="text" id="qtype-name" name="name" minlength="2" class="form-control" required>
        </div>
        <button id="save-qtype-btn" type="submit" class="btn btn-primary">保存</button>
        <button id="reset-qtype-btn" type="reset" class="btn btn-warning">重置</button>
    </form>
    <br>
    <table id="qtype-data-table" class="table table-condensed table-bordered table-striped table-responsive table-hover">

    </table>
</div>


<script id="qtype-data-template" type="text/x-handlebars-template">
    <thead>
    <tr>
        <th>ID</th>
        <th>名称</th>
        <th>创建时间</th>
        <th>修改时间</th>
        <th class="action-col-2"></th>
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

<script id="delete-qtype-msg" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-danger" role="alert">确定要删除以下题目类型吗？</p>
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
<script src="${ctx}/assets/js/admin/question/question-type.js"></script>
