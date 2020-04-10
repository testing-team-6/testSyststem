<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="page-header">
    <h1>我的项目 <small>${PROJECT.name}</small></h1>
</div>

<div class="container-fluid">
    <s:if test="%{#session.FACILITATOR}">
        <jsp:include page="/WEB-INF/partials/client/dashboard-facilitator.jsp"/>
    </s:if>
    <s:else>
        <jsp:include page="/WEB-INF/partials/client/dashboard-user.jsp"/>
    </s:else>
</div>

<%--<script id="task-overview-template" type="text/x-handlebars-template">--%>
    <%--<thead>--%>
    <%--<tr>--%>
        <%--<th>类型</th>--%>
        <%--<th>进行中</th>--%>
        <%--<th>总计</th>--%>
    <%--</tr>--%>
    <%--</thead>--%>
    <%--<tbody>--%>
    <%--{{#each tasks}}--%>
    <%--<tr>--%>
        <%--<td>--%>
            <%--<strong>--%>
            <%--{{#is type "===" "AUTHOR"}}--%>
                <%--编辑--%>
            <%--{{/is}}--%>
            <%--{{#is type "===" "REVIEWER"}}--%>
                <%--评审--%>
            <%--{{/is}}--%>
            <%--{{#is type "===" "QA"}}--%>
                <%--再审--%>
            <%--{{/is}}--%>
            <%--</strong>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<a class="btn btn-default btn-block" role="button">--%>
                <%--<span class="badge">{{pending}}</span>--%>
            <%--</a>--%>
        <%--</td>--%>
        <%--<td>--%>
            <%--<span class="label-lg label-success">{{total}}</span>--%>
        <%--</td>--%>
    <%--</tr>--%>
    <%--{{/each}}--%>
    <%--</tbody>--%>
<%--</script>--%>


<script src="${ctx}/assets/js/client/dashboard.js"></script>
