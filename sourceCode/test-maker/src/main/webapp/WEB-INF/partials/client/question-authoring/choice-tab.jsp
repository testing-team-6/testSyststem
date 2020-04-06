<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<div class="panel panel-primary">
    <div class="panel-heading" role="tab" id="headingOne">
        <h4 class="panel-title">
            <a class="btn" role="button" data-toggle="collapse" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                题目选项详情
            </a>
        </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
        <div class="panel-body">
            <table id="q-choice-table" class="table table-bordered table-striped table-condensed table-responsive table-hover">
            </table>
        </div>
    </div>
</div>


<div class="panel panel-info">
    <div class="panel-heading" role="tab" id="headingTwo">
        <h4 class="panel-title">
            <a class="btn" data-toggle="collapse" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                题目选项编辑框
            </a>
        </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwo">
        <div class="panel-body">
            <div id="choice-image-container" class="form-group">
            </div>
            <div class="form-group">
                <div class="col-md-5">
                    <div  class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <strong>注意！</strong> 要新增或者编辑题目选项，请先选择题目序号！
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="q-choice-id" class="col-md-1 control-label">ID</label>
                <div class="col-md-1">
                    <input id="q-choice-id" type="text" class="form-control" disabled>
                </div>
                <label for="q-choice-label-select" class="col-md-2 col-lg-1 control-label">序号</label>
                <div class="choice-label-list col-md-3" style="width: auto;">
                    <select id="q-choice-label-select" name="choiceLabel" class="form-control" required>
                    </select>
                </div>

                <label for="q-choice-answer" class="col-md-2 control-label">正确答案</label>
                <div class="col-md-2">
                    <input type="checkbox" id="q-choice-answer" value="option1"> <strong></strong>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-12">
                    <textarea id="item-choice-content" name="choice-content" class="rich-editable" rows="5">
                    </textarea>
                </div>
            </div>
            <div class="form-group">
                <div class="col-md-offset-1 col-md-2">
                    <button id="save-choice-btn" type="button" class="btn btn-success">保存题目选项</button>
                </div>
            </div>
            <div class="msg-area form-group">
            </div>
        </div>
    </div>
</div>
<script id="choice-image-list" type="text/x-handlebars-template">
    {{#each images}}
        <div class="col-md-3">
            <div class="thumbnail" data-index="{{@index}}" data-id="{{id}}">
                <a href="${ctx}{{path}}" target="_blank">
                    <img src="${ctx}{{path}}" class="img-responsive img-thumbnail" title="{{caption}}">
                </a>
                <div class="caption">
                    <h3 id="image-caption" contenteditable="true">{{caption}}</h3>
                    <p>
                        <button id="save-choice-image-btn" class="btn-sm btn-primary">保存</button>
                        <button id="delete-choice-image-btn" class="btn-sm btn-danger">删除</button>
                    </p>
                    <p><em>${ctx}{{path}}</em></p>
                </div>
            </div>
        </div>
    {{/each}}
</script>
