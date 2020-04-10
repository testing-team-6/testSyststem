<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="page-header">
    <h1>出题 <small>编写新考题</small></h1>
</div>

<div class="container-fluid">
    <form id="create-item-form" class="form-horizontal title-border-form" action="">
        <fieldset>
            <legend>知识点</legend>
            <div class="form-group">
                <label class="col-md-2 control-label">大纲</label>
                <div class="col-md-3 col-lg-1">
                    <select id="syllabus-select" class="form-control select-list" data-placeholder="选择大纲">
                        <option></option>
                    </select>
                </div>
                <label class="col-md-1 control-label">章节</label>
                <div class="col-md-3 col-lg-2">
                    <select id="chapter-select" class="form-control select-list" data-placeholder="选择章节">
                        <option></option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-md-2 control-label">知识点</label>
                <div class="col-md-10">
                    <%--<button type="button" class="btn btn-primary">分配选中知识点</button>--%>
                    <select id="knowledge-point-select" class="form-control select-list" data-placeholder="选择知识点">
                        <option></option>
                    </select>
                </div>
            </div>
        </fieldset>

        <div class="form-group">
            <label class="col-md-2 control-label">题目类型</label>
            <div class="col-md-2">
                <select id="question-type-list" class="form-control">
                    <option value="-1">类型</option>
                </select>
            </div>
            <label class="col-md-2 col-lg-1 control-label">题目语言</label>
            <div class="col-md-2">
                <select id="question-language-list" class="form-control">
                    <option value="-1">语言</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">作者</label>
            <div class="col-md-2 col-lg-2">
                <select id="author-list" class="select-list form-control" data-placeholder="选择作者">
                    <option></option>
                    <option value="1">张三</option>
                    <option value="2">李四</option>
                    <option value="3">大海</option>
                    <option value="5">大海1</option>
                </select>
            </div>
            <label class="col-md-2 col-lg-1 control-label">开始日期</label>
            <div class="col-md-2">
                <input id='authorStartDate' type='text' class="datepicker form-control" />
            </div>
            <label class="col-md-2 col-lg-1 control-label">结束日期</label>
            <div class="col-md-2">
                <input id="authorFinishDate" type='text' class="form-control datepicker" />
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">评审</label>
            <div class="col-md-2">
                <select id="reviewer-list" class="select-list form-control" data-placeholder="选择评审">
                    <option></option>
                    <option value="1">张三</option>
                    <option value="2">李四</option>
                    <option value="3">大海</option>
                    <option value="5">大海1</option>
                </select>
            </div>
            <label class="col-md-2 col-lg-1 control-label">开始日期</label>
            <div class="col-md-2">
                <input id='reviewStartDate' type='text' class="datepicker form-control" />
            </div>
            <label class="col-md-2 col-lg-1 control-label">结束日期</label>
            <div class="col-md-2">
                <input id='reviewFinishDate' type='text' class="datepicker form-control" />
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-offset-3 col-md-2">
                <button type="button" class="btn btn-primary btn-block">提交题目</button>
            </div>
            <div class="col-md-2">
                <button type="button" class="btn btn-default btn-block">重填</button>
            </div>
        </div>
    </form>
    <div class="row">
    </div>
</div>
<script id="syllabus-list-template" type="text/x-handlebars-template">
    {{#each aaData}}
            <option value="{{id}}" data-index="{{@index}}">{{level}} ({{version}})</option>
    {{/each}}
</script>
<script id="chapter-list-template" type="text/x-handlebars-template">
    {{#each aaData}}
            <option value="{{id}}" data-index="{{@index}}">{{title}}</option>
    {{/each}}
</script>
<script id="knowledge-point-list-template" type="text/x-handlebars-template">
    {{#each aaData}}
            <div class="radio">
                <label>
                    <input type="radio" name="knowledge-point" value="{{id}}" data-index="{{@index}}">
                    {{number}} {{title}}
                </label>
            </div>
    {{/each}}
</script>
<script src="${ctx}/assets/js/client/question-management/manage-questions.js"></script>
