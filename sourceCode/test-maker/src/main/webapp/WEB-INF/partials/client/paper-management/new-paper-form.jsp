<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!-- Modal -->
<div id="new-paper-modal" class="modal fade" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h3 class="modal-title" id="myModalLabel">新建组卷</h3>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form id="edit-paper-form" class="form-horizontal title-border-form col-md-9">
                        <div id="p-id-section" class="form-group hidden">
                            <label class="col-md-2 control-label">ID</label>
                            <div class="col-md-3">
                                <input id="paper-id" class="form-control" type="text" readonly>
                            </div>
                        </div>
                        <fieldset>
                            <legend>${PROJECT.syllabus.level} (${PROJECT.syllabus.version})</legend>
                            <div class="form-group">
                                <label class="col-md-6 control-label">选题</label>
                                <div class="col-md-4">
                                    <select id="question-select-list" class="form-control select-list" data-placeholder="选择题目" multiple="multiple">
                                        <option></option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-4 control-label">组卷名</label>
                                        <input id="paper-name" class="form-control input-group-md">
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="save-question-btn" type="button" class="btn btn-primary">提交组卷</button>
            </div>
        </div>
    </div>
</div>
<script id="question-select-list-template" type="text/x-handlebars-template">
    {{#each aaData}}
    <option value="{{id}}" data-index="{{@index}}">{{number}} {{title}}</option>
    {{/each}}
</script>