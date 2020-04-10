<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<ul class="nav nav-pills nav-stacked">
    <li role="presentation"><a href="dashboard.action" class="nav-menu-item">我的任务</a></li>
</ul>
<s:if test="%{#session.FACILITATOR}">
    <!-- menu for facilitator -->
    <ul class="nav nav-pills nav-stacked">
        <li role="presentation"><a href="project.action" class="nav-menu-item">项目管理</a></li>
        <li role="presentation"><a href="item-management.action" class="nav-menu-item">题目管理</a></li>
        <li role="presentation"><a href="paper-management.action" class="nav-menu-item">组卷管理</a></li>
    </ul>
</s:if>
<ul class="nav nav-pills nav-stacked">
    <%--<li role="presentation" id="item-create" ><a href="item-create.action" class="nav-menu-item">出题</a></li>--%>
    <li role="presentation" ><a href="question-authoring.action" class="nav-menu-item">编辑任务</a></li>
    <li role="presentation" ><a href="question-reviewing.action" class="nav-menu-item">评审任务</a></li>
    <li role="presentation" ><a href="question-qa.action" class="nav-menu-item">再审任务</a></li>
</ul>
<%--<ul class="nav nav-pills nav-stacked">
    <li role="presentation" ><a href="file-upload.action" class="nav-menu-item">文件上传</a></li>
</ul>--%>
<script src="${ctx}/assets/js/sidebar-navigation.js"></script>
