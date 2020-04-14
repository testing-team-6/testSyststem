<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="new-paper-modal" class="modal fade" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h3 class="modal-title" id="myModalLabel">组成试卷</h3>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form id="generate-paper-form" class="form-horizontal title-border-form">
                        <fieldset>
                            <legend>${PROJECT.syllabus.level} (${PROJECT.syllabus.version})</legend>
                        </fieldset>

                        <div class="form-group">
                            <label class="col-md-2 control-label">作者</label>
                            <div class="col-md-2 col-lg-2">
                                <select id="author-list" class="select-list user-select-list form-control" data-placeholder="选择作者" required>
                                    <option></option>
                                </select>
                            </div>

                        </div>

                        <div class="form-group">
                            <label class="col-md-2 control-label">试卷名称</label>
                            <div class="col-md-2">
                                <input id='paperName' name="paperName" type='text'  required>
                            </div>
                        </div>
                    </form>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="submit-paper-btn" type="button" class="btn btn-primary">提交试卷</button>
            </div>
        </div>
    </div>
</div>
