<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<div class="container-fluid">
    <nav class="navbar navbar-primary navbar-fixed-top" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <%--<a class="navbar-brand navbar-caption" href="${ctx}/web/client/index.action" target="_blank">--%>
            <%--<span style="font-size: 36px;">CSTQB 出题系统</span>--%>
            <%--</a>--%>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <a class="navbar-brand navbar-caption" href="${ctx}/web/client/index.action" target="_blank">
                <span style="font-size: 36px;">test maker</span>
            </a>
            <ul class="nav navbar-nav navbar-right">
                <jsp:include page="toolbar.jsp"/>
            </ul>
        </div>
    </nav>
</div>
