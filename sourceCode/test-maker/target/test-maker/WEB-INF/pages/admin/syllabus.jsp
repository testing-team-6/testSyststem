<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div>

    <div class="page-header">
        <h1><i class="glyphicon glyphicon-book"></i>大纲管理
            <small>大纲知识点</small>
        </h1>
    </div>


    <div role="tabpanel" class="form-panel">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#syllabus-tab" aria-controls="syllabus-tab" role="tab"
                                                      data-toggle="tab">大纲</a></li>
            <li role="presentation"><a id="chapter-tab-title" href="#chapter-tab" aria-controls="chapter-tab" role="tab"
                                       data-toggle="tab">章节</a></li>
            <li role="presentation"><a id="kp-tab-title" href="#kp-tab" aria-controls="kp-tab" role="tab"
                                       data-toggle="tab">知识点</a></li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="syllabus-tab">
                <jsp:include page="/WEB-INF/partials/admin/syllabus/syllabus-tab.jsp"/>
            </div>

            <div role="tabpanel" class="tab-pane fade" id="chapter-tab">
                <jsp:include page="/WEB-INF/partials/admin/syllabus/chapter-tab.jsp"/>
            </div>

            <%-- question choice tab--%>
            <div role="tabpanel" class="tab-pane fade" id="kp-tab">
                <jsp:include page="/WEB-INF/partials/admin/syllabus/knowledge-point-tab.jsp"/>

            </div>
        </div>
    </div>


</div>

<script src="${ctx}/assets/js/utils/syllabus-utils.js"></script>
<script src="${ctx}/assets/js/admin/syllabus/syllabus.js"></script>
<script src="${ctx}/assets/js/admin/syllabus/chapter.js"></script>
