<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="page-header">
    <h1>考题辅助信息管理</h1>
</div>
<div class="container-fluid">
    <div role="tabpanel" class="form-panel">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a id="type-tab-title" href="#question-type-tab" role="tab" data-toggle="tab">题目类型</a></li>
            <li role="presentation"><a id="language-tab-title" href="#question-language-tab" role="tab" data-toggle="tab">题目语言管理</a></li>
            <li role="presentation"><a id="status-tab-title" href="#question-status-tab" role="tab" data-toggle="tab">题目状态管理</a></li>
            <li role="presentation"><a id="transition-tab-title" href="#transition-tab" role="tab" data-toggle="tab">题目状态转换设定</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="question-type-tab">
                <jsp:include page="/WEB-INF/partials/admin/question/type-tab.jsp"/>
            </div>
            <div role="tabpanel" class="tab-pane fade" id="question-language-tab">
                <jsp:include page="/WEB-INF/partials/admin/question/language-tab.jsp"/>
            </div>
            <div role="tabpanel" class="tab-pane fade" id="question-status-tab">
                <jsp:include page="/WEB-INF/partials/admin/question/status-tab.jsp"/>
            </div>
            <%-- project status tab --%>
            <div role="tabpanel" class="tab-pane fade" id="transition-tab">
                <jsp:include page="/WEB-INF/partials/admin/question/transition-tab.jsp"/>
            </div>

        </div>
    </div>
</div>
