<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="page-header">
    <h1>项目管理</h1>
</div>
<div class="container-fluid">
    <div role="tabpanel" class="form-panel">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a id="project-tab-title" href="#project-tab" aria-controls="syllabus-tab" role="tab" data-toggle="tab">项目信息管理</a></li>
            <li role="presentation"><a id="status-tab-title" href="#project-status-tab" aria-controls="chapter-tab" role="tab" data-toggle="tab">项目状态</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="project-tab">
                <jsp:include page="/WEB-INF/partials/admin/project/project-tab.jsp"/>
            </div>
            <%-- project status tab --%>
            <div role="tabpanel" class="tab-pane fade" id="project-status-tab">
                <jsp:include page="/WEB-INF/partials/admin/project/projectStatus-tab.jsp"/>
            </div>

        </div>
    </div>
</div>
<script src="${ctx}/assets/js/utils/project-util.js"></script>
<script src="${ctx}/assets/js/admin/project/projects.js"></script>
<script src="${ctx}/assets/js/admin/project/project-status.js"></script>
