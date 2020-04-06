<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@include file="meta-head-styles.jsp"%>
        <title><tiles:insertAttribute name="title" ignore="true" /></title>
        <%@include file="/WEB-INF/partials/index-session-def.jsp"%>
    </head>
    <body>
    <nav class="masthead navbar navbar-primary navbar-fixed-top" role="navigation">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand navbar-caption" href="${ctx}/web/">
                    <span style="font-size: 36px;">出题系统</span>
                </a>
            </div>
        </div>
    </nav>
        <!-- Main jumbotron for a primary marketing message or call to action -->

    <div class="main-panel">
        <tiles:insertAttribute name="body" />
    </div>
        <%@include file ="./meta-head-scripts.jsp" %>
    </body>
</html>
