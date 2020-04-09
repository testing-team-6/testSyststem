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
                    <form id="edit-paper-form" class="form-horizontal title-border-form">
                        <div id="p-id-section" class="form-group hidden">
                            <label class="col-md-2 control-label">ID</label>
                            <div class="col-md-3">
                                <input id="paper-id" class="form-control" type="text" readonly>
                            </div>
                            <label class="cold-md-2">当前状态</label>
                            <span id="paper-status" class="form-control-static"></span>
                            <span id="change-status-container"></span>
                        </div>
                        <fieldset>
                            <legend>${PROJECT.syllabus.level} (${PROJECT.syllabus.version})</legend> //edit
                            <div class="form-group">
                                <label class="col-md-2 control-label">选题</label>
                                <div class="col-md-4 col-lg-4">
                                    <select id="question-select" class="form-control select-list" data-placeholder="选择题目" multiple="multiple">
                                        <option></option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label class="col-md-2 control-label">组卷名</label>
                                        <input id="paper-name" class="form-control input-group-sm">
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