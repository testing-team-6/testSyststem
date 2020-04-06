<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<nav class="toolbar navbar navbar-default">
    <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
            <li>
                <a id="create-chapter-btn" href="#create-chapter-form" data-toggle="collapse" class="btn btn-primary navbar-btn-tm" role="button"><i class="glyphicon glyphicon-plus-sign"></i>编辑章节</a>
            </li>
            <li>
                <form class="navbar-form" role="search">
                    <div class="form-group">
                        <input id="chapter-keyword" type="text" class="form-control" placeholder="章节名">
                        <a id="search-chapter-btn" class="btn btn-danger navbar-btn-tm"><i class="glyphicon glyphicon-search"></i></a>
                    </div>
                </form>
            </li>
        </ul>
    </div>
</nav>
<div class="form-group">
    <form id="create-chapter-form" class="collapse form-horizontal">
        <div class="form-group">
            <label class="col-md-2 control-label" for="syllabus-select-list">大纲</label>
            <div class="col-md-6">
                <select id="syllabus-select-list" name="syllabus" data-placeholder="选择大纲" class="form-control" required>
                    <option></option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="chapter-number" class="col-md-2 control-label">编号</label>
            <div class="col-md-2">
                <input id="chapter-number" name="number" type="text" class="form-control" placeholder="编号" size="10" required>
            </div>
            <div id="chapter-id-div" class="hidden">
                <label class="col-md-1 control-label" for="chapter-id">ID</label>
                <div class="col-md-1">
                    <input id="chapter-id" name="id" type="text" class="form-control" readonly>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="chapter-name" class="col-md-2 control-label">标题</label>
            <div class="col-md-6">
                <input id="chapter-name" name="title" minlength="2" type="text" class="form-control" placeholder="章节标题" size="50" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-2 col-md-1">
                <button id="submit-chapter-btn" type="submit" class="btn btn-primary">保存</button>
            </div>
            <div class="col-md-1">
                <button id="reset-form-btn" type="reset" class="btn btn-default">重置</button>
            </div>
        </div>
    </form>
</div>

<table id="chapter-list-table" class="table table-bordered table-striped table-condensed table-hover">
    <%--<tbody id="chapter-list-table-body" data-link="row" class="rowlink"></tbody>--%>
    <%--<tbody id="chapter-list-table-body"></tbody>--%>
</table>
<script id="syllabus-list-template" type="text/x-handlebars-template">
    {{#each aaData}}
            <option value="{{id}}" data-index="{{@index}}">{{level}} ({{version}})</option>
    {{/each}}
</script>

<script id="chapter-html" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <table class="table table-striped table-bordered">
                <tbody>
                    <tr>
                        <td><b>ID</b></td>
                        <td>{{id}}</td>
                    </tr>
                    <tr>
                        <td><b>大纲</b></td>
                        <td>{{syllabus.level}}</td>
                    </tr>
                    <tr>
                        <td><b>章节</b></td>
                        <td>{{title}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</script>
