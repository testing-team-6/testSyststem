<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="page-header">
    <h1>评审任务
        <small>${PROJECT.name} 题目评审</small>
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
                                <input id="q-review-keyword" type="text" class="form-control" placeholder="题目关键字">
                                <a id="search-syllabus-btn" class="btn btn-danger navbar-btn-tm"><i
                                        class="glyphicon glyphicon-search"></i></a>
                            </div>
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
    </div>

    <div id="q-workspace-container" class="row">
        <div class="msg-area form-group">
        </div>
        <table id="qa-table" class="table table-striped table-bordered table-responsive table-condensed">
        </table>
    </div>
    <div id="q-details-container" class="row"></div>
</div>
<jsp:include page="/WEB-INF/partials/client/review-list-template.jsp"/>
<jsp:include page="/WEB-INF/partials/client/review-form-template.jsp"/>
<script src="${ctx}/assets/js/client/question-qa/question-qa.js"></script>
