<%@ page import="cn.cstqb.exam.testmaker.json.serialization.JsonSerializer" %>
<%@ page import="cn.cstqb.exam.testmaker.configuration.AppInjector" %>
<%@ page import="cn.cstqb.exam.testmaker.configuration.Constants" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
    APP_SESSION={
        ctx: '${ctx}',
        devMode: ${devMode}
    };
</script>

<s:if test="%{#session.USER != null}">
    <script>
        APP_SESSION.user={
            username: '${sessionScope["USER"].username}',
            fullName: '${sessionScope["USER"].fullName}'
        };
    </script>
</s:if>

<s:if test="%{#session.ADMIN != null}">
    <script>
        APP_SESSION.admin='${sessionScope["ADMIN"]}';
    </script>
</s:if>

<s:if test="%{#session.PROJECT !=null }">
    <script>
        var project = <%= AppInjector.getInstance().getInjector().getInstance(JsonSerializer.class).write(session.getAttribute(Constants.ATTR_PROJECT)) %>
        APP_SESSION.projectName='${sessionScope["PROJECT"].name}';
        APP_SESSION.project = project;
        APP_SESSION.facilitator = ${sessionScope["FACILITATOR"]};
    </script>

</s:if>
