<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<nav class="toolbar navbar navbar-default">
    <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
            <li>
                <a id="edit-syllabus-form-btn" href="#edit-syllabus-form" class="btn btn-primary navbar-btn-tm" data-toggle="collapse" role="button"><i class="glyphicon glyphicon-plus-sign"></i>编辑大纲</a>
            </li>
            <li>
                <form class="navbar-form" role="search" onsubmit="return false">
                    <div class="form-group">
                        <input type="text" class="form-control" id="syllabus-keyword" placeholder="大纲级别">
                        <a id="search-syllabus-btn" class="btn btn-danger navbar-btn-tm"><i class="glyphicon glyphicon-search"></i></a>
                    </div>
                </form>
            </li>
        </ul>
    </div>
</nav>
<div class="form-group">

    <form id="edit-syllabus-form" action="${ctx}/syllabus/update.action" class="collapse form-inline">
        <div class="form-group hidden">
            <label class="control-label">ID</label>
            <input id="syllabus-id" name="id" type="number" class="form-control" readonly>
        </div>
        <div class="form-group">
            <label for="syllabus-level" class="control-label">级别</label>
            <input id="syllabus-level" name="level" list="levels" minlength="2" maxlength="20" type="text" class="form-control" required>
            <datalist id="levels">
                <option value="Foundation Level">
                <option value="Advanced Level-TA">
                <option value="Advanced Level-TM">
                <option value="Advanced Level-TTA">
                <option value="软件测试-理论应用课程">
                <option value="软件测试-实训模拟课程">
            </datalist>
        </div>
        <div class="form-group">
            <label for="syllabus-version" class="control-label">版本</label>
            <input id="syllabus-version" name="version" minlength="2" maxlength="20" type="text" class="form-control" required>
        </div>
        <div id="syllabus-state-field" class="form-group hidden">
            <label for="syllabus-state" class="control-label">废弃状态</label>
            <input id="syllabus-state" name="disabled" type="checkbox" class="form-control">
        </div>
        <button type="submit" id="update-syllabus-btn" class="btn btn-primary">提交</button>
        <button type="reset" id="cancel-update-syllabus-btn" class="btn btn-default">取消</button>
    </form>
</div>
<table id="syllabus-list-table" class="table table-bordered table-striped table-condensed table-hover">
</table>


<script id="syllabus-data-template" type="text/x-handlebars-template">
    <thead>
    <tr>
        <th>ID</th>
        <th>级别</th>
        <th>版本</th>
        <th>废弃状态</th>
        <th>创建日期</th>
        <th>修改日期</th>
        <th class="action-col-2"></th>
    </tr>
    </thead>
    <tbody>
    {{#each aaData}}
    <tr class="item-row" data-index="{{@index}}">
        <td data-attr="id">{{id}}</td>
        <td data-attr="level">{{level}}</td>
        <td data-attr="version">{{version}}</td>
        <td data-attr="disabled">
            {{#if disabled}}
            <label class="label-lg label-danger"><i class="glyphicon glyphicon-off"></i>已废弃</label>
            {{/if}}
        </td>
        <td data-attr="createdOn">{{date createdOn}}</td>
        <td data-attr="updatedOn">{{date updatedOn}}</td>
        <td>
            <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
            <a href="#" class="delete-item"><i class="glyphicon glyphicon-remove"></i></a>
        </td>
    </tr>
    {{/each}}
    </tbody>
</script>

<script id="delete-syllabus-template" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-danger" role="alert">确定要删除以下大纲吗？</p>
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
                    <td>{{level}}</td>
                </tr>
                <tr>
                    <td><b>版本</b></td>
                    <td>{{version}}</td>
                </tr>
                <tr>
                    <td><b>废弃状态</b></td>
                    <td>
                        {{#if disabled}}
                            <span class="label-lg label-danger">是</span>
                        {{else}}
                            <span class="label-lg label-info">否</span>
                        {{/if}}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</script>
