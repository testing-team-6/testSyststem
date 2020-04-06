<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<nav class="toolbar navbar navbar-default">
    <div class="collapse navbar-collapse">
        <ul class="nav navbar-nav">
            <li>
                <a id="create-kp-btn" class="btn btn-primary navbar-btn-tm" role="button" data-toggle="modal" data-target="#create-kp-modal">
                    <i class="glyphicon glyphicon-plus-sign"></i>新增知识点</a>
            </li>
            <li>
                <form class="navbar-form" role="search" onsubmit="return false">
                    <div class="form-group">
                        <input id="kp-filter" type="text" class="form-control" placeholder="知识点关键字">
                        <a id="search-kp-btn" class="btn btn-danger navbar-btn-tm"><i class="glyphicon glyphicon-search"></i></a>
                    </div>
                </form>
            </li>
        </ul>
    </div>
</nav>

<div class="panel panel-default">
    <div class="panel-body">
        <form id="list-kp-form" class="form-horizontal">
            <div class="form-group">
                <label for="syllabus-filter" class="col-md-2 col-lg-2" style="width: auto">选取大纲</label>
                <div class="col-md-4">
                    <select id="syllabus-filter" class="form-control" data-placeholder="选择大纲">
                        <option></option>
                    </select>
                </div>
            </div>
        </form>
    </div>
</div>

<div id="edit-kp-panel" class="panel panel-primary collapse">
    <div class="panel-heading">
        <h3 class="panel-title">编辑知识点</h3>
    </div>
    <div class="panel-body">
        <form id="save-kp-form" class="form-horizontal">
            <div class="form-group">
                <label class="col-md-1 control-label" for="kp-syllabus">大纲</label>
                <div class="col-md-4 col-lg-2">
                    <span id="kp-syllabus" class="form-control"></span>
                </div>
                <label for="kp-chapter" class="col-md-1 control-label">章节</label>
                <div class="col-md-5 col-lg-3">
                    <span id="kp-chapter" class="form-control"></span>
                </div>
            </div>
            <div class="form-group">
                <div id="kp-id-div" class="hidden">
                    <label for="kp-id" class="col-md-1 control-label">ID</label>
                    <div class="col-md-2 col-lg-1">
                        <input id="kp-id" name="number" minlength="2" type="text" class="form-control" readonly>
                    </div>
                </div>
                <label for="kp-number" class="col-md-1 control-label">编号</label>
                <div class="col-md-2 col-lg-1">
                    <input id="kp-number" name="number" minlength="2" type="text" class="form-control" placeholder="例如：2.9.1" required>
                </div>
                <label for="kp-level" class="col-md-1 control-label">K 级</label>
                <div class="col-md-2 col-lg-1">
                    <select id="kp-level" name="level" class="form-control"data-placeholder="选择 K 级">
                        <option></option>
                        <option value="K1">K1</option>
                        <option value="K2">K2</option>
                        <option value="K3">K3</option>
                        <option value="K4">K4</option>
                    </select>
                </div>
                <label for="kp-score" class="col-md-1 control-label">分数</label>
                <div class="col-md-1">
                    <input id="kp-score" name="score" class="form-control" type="text" pattern="[1-5]" min="1" max="5" step="1" required>
                </div>

            </div>
            <div class="form-group">
                <label for="kp-name" class="col-md-1 control-label">知识点</label>
                <div class="col-md-9 col-lg-6">
                    <input id="kp-name" name="name" minlength="2" type="text" class="form-control" placeholder="知识点" required>
                </div>
                <div class="col-md-2 col-lg-2">
                    <button id="submit-kp-btn" type="submit" class="btn btn-primary" style="display: inline-block"><i class="glyphicon glyphicon-ok"></i></button>
                    <button id="reset-kp-btn" type="reset" class="btn btn-default" style="display: inline-block"><i class="glyphicon glyphicon-remove"></i></button>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="form-group">

</div>

<div id="create-kp-modal" class="modal fade" data-backdrop="static" data-keyboard="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header bg-primary">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增知识点</h4>
            </div>
            <div class="modal-body">
                <div>
                    <form id="create-kp-form" class="form-horizontal title-bordered-form">
                        <div class="form-group">
                            <label class="col-md-2 control-label" for="syllabus-list">大纲</label>
                            <div class="col-md-10">
                                <select id="syllabus-list" name="syllabus" data-placeholder="选择大纲" class="form-control" required>
                                    <option></option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="kp-chapter" class="col-md-2 control-label">章节</label>
                            <div class="col-md-10">
                                <select id="chapter-list" name="chapter" data-placeholder="选择章节" class="form-control" required>
                                    <option></option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="kp-number" class="col-md-2 control-label">编号</label>
                            <div class="col-md-4">
                                <input id="kp-number" name="number" minlength="2" type="text" class="form-control" placeholder="例如：2.9.1" required>
                            </div>
                            <label for="kp-level" class="col-md-2 control-label">K 级</label>
                            <div class="col-md-4">
                                <select name="level" class="form-control" data-placeholder="选择 K 级" required>
                                    <option></option>
                                    <option value="K1">K1</option>
                                    <option value="K2">K2</option>
                                    <option value="K3">K3</option>
                                    <option value="K4">K4</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="kp-score" class="col-md-2 control-label">分数</label>
                            <div class="col-md-4">
                                <input id="kp-score" name="score" class="form-control" type="text" pattern="[1-5]" min="1" max="5" step="1" required>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="kp-title" class="col-md-2 control-label">知识点</label>
                            <div class="col-md-10">
                                <input id="kp-title" name="title" minlength="2" type="text" class="form-control" placeholder="知识点" required>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button id="submit-newKP-btn" type="button" class="btn btn-primary">提交</button>
            </div>
        </div>
    </div>
</div>

<div id="kp-list" class="form-group">
    <table id="kp-list-table" class="table table-bordered table-striped table-condensed table-hover">
    </table>
</div>
<script id="kp-tmpl" type="text/x-handlebars-template">
    <thead>
    <tr>
        <th>ID</th>
        <th>编号</th>
        <th>标题</th>
        <th>K-Level</th>
        <th>分数</th>
        <th>大纲</th>
        <th>章节</th>
        <th>创建时间</th>
        <th>修改时间</th>
        <th class="action-col-2"></th>
    </tr>
    </thead>
    <tbody id="kp-list-table-body">
    {{#each aaData}}
    {{#if chapter.syllabus.disabled}}
        <tr class="item-row danger" data-index="{{@index}}">
            <td>{{id}}</td>
            <td>{{number}}</td>
            <td>{{title}}</td>
            <td>{{kLevel}}</td>
            <td>{{score}}</td>
            <td>{{chapter.syllabus.level}} ({{chapter.syllabus.version}})</td>
            <td>{{chapter.title}}</td>
            <td>{{date createdOn format='LL'}}</td>
            <td>{{date updatedOn format='LL'}}</td>
            <td>
                <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
                <a href="#" class="delete-item"><i class="glyphicon glyphicon-remove"></i></a>
            </td>
        </tr>
    {{else}}
        <tr class="item-row" data-index="{{@index}}">
            <td>{{id}}</td>
            <td>{{number}}</td>
            <td>{{title}}</td>
            <td>{{kLevel}}</td>
            <td>{{score}}</td>
            <td>{{chapter.syllabus.level}} ({{chapter.syllabus.version}})</td>
            <td>{{chapter.title}}</td>
            <td>{{date createdOn format='LL'}}</td>
            <td>{{date updatedOn format='LL'}}</td>
            <td>
                <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
                <a href="#" class="delete-item"><i class="glyphicon glyphicon-remove"></i></a>
            </td>
        </tr>
    {{/if}}
    {{/each}}
    </tbody>
</script>
<script id="syllabus-selectlist-tmpl" type="text/x-handlebars-template">
            <option></option>
    {{#each aaData}}
            <option data-index="{{@index}}" value="{{id}}">{{level}} ({{version}})</option>
    {{/each}}
</script>
<script id="chapter-selectlist-tmpl" type="text/x-handlebars-template">
            <option></option>
    {{#each aaData}}
            <option data-index="{{@index}}" value="{{id}}">{{number}} {{title}}</option>
    {{/each}}
</script>
<script id="delete-kp-msg" type="text/x-handlebars-template">
    <div class="row">
        <div class="col-md-offset-2 col-md-8">
            <p class=" alert alert-danger" role="alert">确定要删除以下知识点吗？</p>
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
                    <td><b>知识点</b></td>
                    <td>{{number}} {{title}}</td>
                </tr>
                <tr>
                    <td><b>所属章节</b></td>
                    <td>{{chapter.title}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</script>
<script src="${ctx}/assets/js/admin/syllabus/knowledge-point.js"></script>
