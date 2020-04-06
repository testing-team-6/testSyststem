<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <%@include file="./meta-head-styles.jsp"%>
        <title><s:text name="label.application"/></title>
    </head>
    <body>
        <%@include file="/WEB-INF/partials/index-session-def.jsp"%>
        <jsp:include page="masthead.jsp"/>
        <div class="container-fluid content-panel ">
            <div class="row">
                <div class="col-md-2 navigation-panel">
                    <div id="navigation-panel" class="nav-menu" role="navigation">
                        <tiles:insertAttribute name="menu" />
                    </div>
                </div>
                <div id="content-panel" class="col-md-10 main">
                    <tiles:insertAttribute name="body" ignore="true" />
                </div>
            </div>

        </div>

        <footer class="footer">
            <tiles:insertAttribute name="footer" ignore="true" />
        </footer>

        <%@include file ="./meta-head-scripts.jsp" %>
    </body>
</html>
