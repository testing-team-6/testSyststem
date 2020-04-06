<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div id="authoring-question-modal" class="modal fade" tabindex="-1" role="dialog" data-backdrop="static">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h3 class="modal-title">编写题目
                    <small>
                        <span class="label-lg label-primary">No.<span id="author-q-id"></span></span>
                    </small>
                </h3>
            </div>
            <div class="modal-body">
                <form id="question-author-form" class="form-horizontal" role="form">
                    <div class="form-group">
                        <nav class="toolbar navbar navbar-modal">
                            <div class="collapse navbar-collapse">
                                <ul class="nav navbar-nav">
                                    <li>
                                        <a class="btn btn-info navbar-btn-tm" data-toggle="collapse"
                                           href="#question-info-section" role="button"><i id="question-well-collapse"
                                                                                          class="glyphicon glyphicon-adjust"></i>显示题目属性</a>
                                    </li>
                                    <li>
                                        <a class="btn btn-info navbar-btn-tm" data-toggle="collapse"
                                           data-target="#img-panel-body" role="button"><i
                                                class="glyphicon glyphicon-adjust"></i>显示图片详情</a>
                                    </li>
                                </ul>
                            </div>
                        </nav>
                    </div>
                    <div id="question-info-section" class="collapse">
                        <table class="table table-bordered table-condensed table-responsive">
                            <tr>
                                <td class="control-label"><label>知识点</label></td>
                                <td colspan="5"><span id="author-q-kp"></span></td>
                            </tr>
                            <tr>
                                <td class="control-label"><label>语言</label></td>
                                <td><span id="author-q-language" class="col-md-8"></span></td>
                                <td class="control-label"><label>类型</label></td>
                                <td><span id="author-q-type" class="col-md-6"></span></td>
                                <td class="control-label"><label>状态</label></td>
                                <td class="bg-warning">
                                    <div class="col-md-8">
                                        <label id="author-q-status" class="form-control-static"
                                               style="font-size: 120%"></label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="control-label"><label for="author-q-difficulty">难度</label></td>
                                <td>
                                    <div class="col-md-9">
                                        <select id="author-q-difficulty" name="difficulty"
                                                class="form-control selectpicker" required>
                                            <option value="1">简单</option>
                                            <option value="2">一般</option>
                                            <option value="3">困难</option>
                                        </select>
                                    </div>
                                </td>
                                <td class="control-label"><label>K 值</label></td>
                                <td><span id="q-k-value" class="col-md-4"></span></td>
                                <td class="control-label"><label for="author-q-score">分值</label></td>
                                <td>
                                    <div class="col-lg-8">
                                        <label id="author-q-score" class="form-control-static"></label>
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td class="control-label"><label for="author-q-multipleChoice">多选</label></td>
                                <td>
                                    <div class="col-lg-8">
                                        <input id="author-q-multipleChoice" name="multipleChoice" type="checkbox"
                                               class="form-control">
                                    </div>
                                </td>
                                <td class="control-label"><label>出题时限</label></td>
                                <td>
                                    <div class="col-md-10 col-lg-8">
                                        <span id="author-start"></span> - <span id="author-finish"></span>
                                    </div>
                                </td>
                                <td class="control-label"><label>评审时限</label></td>
                                <td>
                                    <div class="col-md-10 col-lg-8">
                                        <span id="review-start"></span> - <span id="review-finish"></span>
                                    </div>
                                </td>
                            </tr>

                        </table>
                    </div>
                    <%--                    <div class="form-group">
                                            <div class="form-panel panel panel-info">
                                                <div class="panel-heading">
                                                    <div class="panel-title"><span>评审信息</span></div>
                                                </div>
                                                <div class="panel-body">
                                                </div>
                                            </div>
                                        </div>--%>

                    <div class="form-group">
                        <jsp:include page="/WEB-INF/partials/client/question-authoring/image-mgm.jsp"/>
                    </div>

                    <div class="form-group">
                        <div class="form-panel panel panel-primary">
                            <div class="panel-heading">
                                <div class="panel-title"><span>题目详情</span></div>
                            </div>
                            <div class="panel-body">
                                <div role="tabpanel" class="form-panel">
                                    <!-- Nav tabs -->
                                    <ul class="nav nav-tabs" role="tablist">
                                        <li role="presentation" class="active"><a href="#scenario-tab"
                                                                                  aria-controls="scenario-tab"
                                                                                  role="tab" data-toggle="tab">场景描述</a>
                                        </li>
                                        <li role="presentation"><a id="stem-tab-caption" href="#stem-tab"
                                                                   aria-controls="stem-tab" role="tab"
                                                                   data-toggle="tab">题干</a></li>
                                        <li role="presentation"><a id="remark-tab-caption" href="#remark-tab"
                                                                   aria-controls="remark-tab" role="tab"
                                                                   data-toggle="tab">备注</a></li>
                                        <li role="presentation"><a id="choice-tab-header" href="#author-q-choice-tab"
                                                                   aria-controls="choice-tab" role="tab"
                                                                   data-toggle="tab">题目选项</a></li>
                                    </ul>

                                    <!-- Tab panes -->
                                    <div class="tab-content">
                                        <div role="tabpanel" class="tab-pane fade in active" id="scenario-tab">
                                            <textarea id="author-form-q-scenario" name="scenario" class="rich-editable"
                                                      cols="80" rows="5">
                                            </textarea>
                                        </div>
                                        <div role="tabpanel" class="tab-pane fade" id="stem-tab">
                                            <textarea id="author-form-q-stem" name="stem" class="rich-editable"
                                                      cols="80" rows="10" required>
                                            </textarea>
                                        </div>
                                        <div role="tabpanel" class="tab-pane fade" id="remark-tab">
                                            <textarea id="author-form-q-remark" name="remark" class="rich-editable"
                                                      cols="80" rows="10" required>
                                            </textarea>
                                        </div>
                                        <%-- question choice tab--%>
                                        <div role="tabpanel" class="tab-pane fade" id="author-q-choice-tab">
                                            <jsp:include
                                                    page="/WEB-INF/partials/client/question-authoring/choice-tab.jsp"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <div class="form-group">

                    <button id="submit-edit-form-btn" type="button" class="btn btn-primary">保存并关闭</button>
                    <span id="change-status-container"></span>
                    <button id="cancel-edit-form-btn" type="button" class="btn btn-default" data-dismiss="modal">取消
                    </button>
                </div>

            </div>
        </div>
    </div>
</div>

<div id="choice-image-upload-modal" class="modal fade" data-backdrop="static">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
                <p>请选择要上传的图片</p>
                <s:url var="saveChoiceImageURL" action="save" namespace="/web/question/choice/image"/>
                <form id="choice-image-upload-form" name="choice-img-form" class="form-horizontal"
                      action="${saveChoiceImageURL}" enctype="multipart/form-data" method="post"
                        >
                    <input id="choice-id" name="choiceId" class="form-control hidden" type="text" readonly hidden>

                    <div class="form-group">
                        <label for="image-caption" class="col-md-4 control-label">图片描述</label>

                        <div class="col-md-8">
                            <input id="image-caption" name="caption" class="control-label">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="choice-image" class="col-md-4 control-label">图片</label>

                        <div class="col-md-8">
                            <input id="choice-image" name="upload" placeholder="请选择要上传的图片" class="form-control"
                                   type="file" accept="image/*" required>
                        </div>
                    </div>
                </form>
                <div id="choice-image-preview">
                </div>
            </div>
            <div class="modal-footer">
                <button id="upload-choice-img-btn" type="button" class="btn btn-primary">上传图片</button>
                <button id="cancel-upload-btn" type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>

<script id="delete-choice-template" type="text/x-handlebars-template">
    <div class="form-group">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-danger" role="alert">确定要删除以下题目选项吗？</p>
        </div>
    </div>
    <div class="form-group">
        <table class="table table-striped table-bordered">
            <tbody>
            <tr>
                <td><b>ID</b></td>
                <td>{{id}}</td>
                <td><b>选项</b></td>
                <td>{{choiceLabel}}</td>
                <td><b>正确答案</b></td>
                <td>
                    {{#if isCorrectAnswer}}
                    <span class="label-lg label-success">是</span>
                    {{else}}
                    <span class="label-lg label-default">否</span>
                    {{/if}}
                </td>
            </tr>
            <tr>
                <td><b>内容</b></td>
                <td colspan="5">
                    <div>{{content}}</div>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</script>
<script id="question-image-list" type="text/x-handlebars-template">
    {{#each images}}
    <div class="col-md-3">
        <div class="thumbnail" data-index="{{@index}}" data-id="{{id}}">
            <a href="${ctx}{{path}}" target="_blank">
                <img src="${ctx}{{path}}" class="img-responsive img-thumbnail" title="{{caption}}">
            </a>

            <div class="caption">
                <h3 id="image-caption" contenteditable="true">{{caption}}</h3>

                <p>
                    <button id="update-question-image" class="btn-sm btn-primary">保存</button>
                    <button id="delete-question-image" class="btn-sm btn-danger">删除</button>
                </p>
                <p><em>${ctx}{{path}}</em></p>
            </div>
        </div>
    </div>
    {{/each}}
</script>

<div id="question-image-modal" class="modal fade" data-backdrop="static">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">上传题目图片</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <form id="question-image-form">
                        <div class="form-group">
                            <label for="question-image-caption" class="col-md-4 control-label">图片描述</label>

                            <div class="col-md-6">
                                <input id="question-image-caption" name="caption" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="question-image" class="col-md-4 control-label">图片</label>

                            <div class="col-md-6">
                                <input id="question-image" name="upload" placeholder="请选择要上传的图片" class="form-control"
                                       type="file" accept="image/*" required>
                            </div>
                        </div>
                        <div id="question-image-preview" class="form-group"></div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button id="submit-question-img" type="button" class="btn btn-primary">上传图片</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>
</div>
