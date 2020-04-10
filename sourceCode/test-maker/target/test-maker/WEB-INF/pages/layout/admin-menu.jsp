<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <ul class="nav nav-pills nav-stacked">
        <li role="presentation"><a href="overview.action" class="nav-menu-item"><i class="glyphicon glyphicon-cog"></i>欢迎</a></li>
        <li role="presentation"><a href="users.action" class="nav-menu-item"><i class="glyphicon glyphicon-user"></i>用户管理</a></li>
        <li role="presentation"><a href="projects.action" class="nav-menu-item"><i class="glyphicon glyphicon-tasks"></i>项目管理</a></li>
        <li role="presentation"><a href="syllabus.action" class="nav-menu-item"><i class="glyphicon glyphicon-book"></i>大纲管理</a></li>
        <li role="presentation"><a href="question-status.action" class="nav-menu-item"><i class="glyphicon glyphicon-stats"></i>属性设置</a></li>
    </ul>
<script src="${ctx}/assets/js/sidebar-navigation.js"></script>
