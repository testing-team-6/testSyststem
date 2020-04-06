<%@ taglib prefix="s" uri="/struts-tags"%>
<s:if test="#{devMode }">
    <%--<link rel="stylesheet" href="${ctx}/lib/bootstrap-table/src/bootstrap-table.css">--%>
    <link rel="stylesheet" href="${ctx}/lib/bootstrap-select/dist/css/bootstrap-select.css">
    <link rel="stylesheet" href="${ctx}/assets/css/app.css" />
    <link rel="stylesheet" href="${ctx}/bower_components/jasny-bootstrap/dist/css/jasny-bootstrap.css" />
    <link rel="stylesheet" href="${ctx}/bower_components/select2/dist/css/select2.css">
    <%--<link rel="stylesheet" href="${ctx}/bower_components/select2/select2-bootstrap.css">--%>
    <%--<link rel="stylesheet" href="${ctx}/bower_components/select2-bootstrap-css/select2-bootstrap.css">--%>
    <link rel="stylesheet" href="${ctx}/bower_components/bootstrap3-dialog/dist/css/bootstrap-dialog.css">

    <%-- jquery needs to be loaded before all other javascript files/libraries since other libaries are placed
      before the end tag of bod --%>
    <script src="${ctx}/lib/jquery/jquery-1.11.2.js" ></script>
</s:if>
<s:else>
    <%--<link rel="stylesheet" href="${ctx}/lib/bootstrap-table/dist/bootstrap-table.min.css">--%>
    <link rel="stylesheet" href="${ctx}/lib/bootstrap-select/dist/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="${ctx}/assets/css/app.min.css" />
    <link rel="stylesheet" href="${ctx}/bower_components/jasny-bootstrap/dist/css/jasny-bootstrap.min.css" />
    <link rel="stylesheet" href="${ctx}/bower_components/select2/dist/css/select2.min.css">
    <%--<link rel="stylesheet" href="${ctx}/bower_components/select2-bootstrap-css/select2-bootstrap.css">--%>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/css/app.min.css" />
    <link rel="stylesheet" href="${ctx}/bower_components/bootstrap3-dialog/dist/css/bootstrap-dialog.min.css">
    <script src="${ctx}/lib/jquery/jquery-1.11.2.min.js" ></script>

</s:else>
<link rel="stylesheet" href="${ctx}/lib/jquery-ui-bootstrap/css/custom-theme/jquery-ui-1.10.3.custom.css">
<link rel="stylesheet" href="${ctx}/bower_components/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.min.css">

<%-- Research libraries go here. Once the library is decided, move it up --%>
<link rel="stylesheet" href="${ctx}/bower_components/bootstrap-combobox/css/bootstrap-combobox.css">
<link rel="stylesheet" href="${ctx}/lib/jquery-ui-bootstrap/css/custom-theme/jquery-ui-1.10.3.custom.css">
<link rel="stylesheet" href="${ctx}/bower_components/bootstrap-duallistbox/dist/bootstrap-duallistbox.css">
