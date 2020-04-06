<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="save-project-form" class="form-horizontal" role="form">

    <div class="form-group hidden">
        <input id="project-id" name="id" type="number" value="${PROJECT.id}" readonly>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-lg-4 control-label">项目名</label>
        <div class="col-md-7">
            <input id="project-name" name="name" value="${PROJECT.name}" type="text" class="form-control" disabled>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-lg-4 control-label">大纲</label>
        <div class="col-md-7">
            <input class="form-control" id="project-syllabus" name="name" value="${PROJECT.syllabus.level} ${PROJECT.syllabus.version}" type="text" disabled>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-lg-4 control-label">状态</label>
        <div class="col-md-7 col-lg-7">
            <select id="project-status" class="form-control" data-placeholder="选择状态" required>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-lg-4 control-label">开始日期</label>
        <div class="col-md-7 col-lg-5">
            <input id="project-start" name="start" value="${PROJECT.startDate}" type="text" class="datepicker form-control" required>
        </div>
    </div>
    <div class="form-group">
        <label class="col-md-5 col-lg-4 control-label">结束日期</label>
        <div class="col-md-7 col-lg-5">
            <input id="project-finish" name="finish" value="${PROJECT.finishDate}" type="text" class="datepicker form-control" required>
        </div>
    </div>
    <div class="form-group">
        <div class="col-md-offset-5 col-md-7 col-lg-offset-4 col-lg-4">
            <button id="save-project-btn" class="btn btn-primary btn-block" type="submit">保存</button>
        </div>
    </div>
    <br>
    <br>
    <br>
</form>

<script id="status-list-template" type="text/x-handlebars-template">
    {{#each statuses}}
    {{#is id "===" ../project.status.id}}
    <option value="{{id}}" data-index="{{@index}}" selected>{{name}}</option>
    {{else}}
    <option value="{{id}}" data-index="{{@index}}">{{name}}</option>
    {{/is}}
    {{/each}}
</script>
