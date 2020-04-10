<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="page-header">
    <h1>编辑任务
        <small>${PROJECT.name} 题目编写</small>
    </h1>
</div>

<div class="container-fluid">
    <div class="row">
        <nav class="toolbar navbar navbar-default">
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li>
                        <a id="reload-question-btn" href="#" class="btn btn-primary navbar-btn-tm" role="button"><i
                                class="glyphicon glyphicon-refresh"></i>刷新</a>
                    </li>
                    <li>
                        <form class="navbar-form" role="search" onsubmit="return false;">
                            <div class="form-group">
                                <input id="author-q-keyword" type="text" class="form-control" placeholder="题目关键字">
                                <a id="search-syllabus-btn" class="btn btn-danger navbar-btn-tm"><i
                                        class="glyphicon glyphicon-search"></i></a>
                            </div>
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
    <div id="form-container" class="form-group">
        <jsp:include page="/WEB-INF/partials/client/question-authoring/authoring-form.jsp"/>
    </div>
    <div class="row" id="q-workspace-container">
        <div class="msg-area form-group">
        </div>
        <table id="question-mgmt-table" class="table table-striped table-bordered table-responsive table-condensed">
        </table>
    </div>

    <div id="q-details-container"></div>
</div>


<script src="${ctx}/assets/js/client/question-authoring/question-authoring.js"></script>
