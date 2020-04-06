<%@ taglib prefix="s" uri="/struts-tags"%>
<script src="${ctx}/bower_components/tinymce/tinymce.min.js"></script>

<s:if test="#{devMode }">
    <%--<script src="${ctx}/bower_components/jquery-ui/ui/jquery-ui.js" ></script>--%>
    <%--<script src="${ctx}/bower_components/jquery-ui/ui/i18n/jquery.ui.datepicker-zh-CN.js" ></script>--%>
    <script src="${ctx}/lib/bootstrap/dist/js/bootstrap.js" ></script>
    <%--<script src="${ctx}/bower_components/jasny-bootstrap/dist/js/jasny-bootstrap.js" ></script>--%>
    <script src="${ctx}/lib/jquery-validation/dist/jquery.validate.js"></script>
    <script src="${ctx}/lib/jquery-validation/dist/additional-methods.js"></script>
    <script src="${ctx}/lib/jquery-validation/dist/localization/messages_zh.js"></script>
    <script src="${ctx}/bower_components/bootstrap-select/dist/js/bootstrap-select.js"></script>
    <script src="${ctx}/bower_components/bootstrap-select/dist/js/i18n/defaults-zh_CN.js"></script>
    <script src="${ctx}/lib/bootstrap-dual-list-box/dual-list-box.js"></script>
    <script src="${ctx}/bower_components/moment/moment.js"></script>
    <script src="${ctx}/bower_components/moment/locale/zh-cn.js"></script>
    <script src="${ctx}/bower_components/select2/dist/js/select2.js"></script>
    <script src="${ctx}/bower_components/select2/dist/js/i18n/zh-CN.js"></script>
    <script src="${ctx}/bower_components/bootstrap3-dialog/dist/js/bootstrap-dialog.js"></script>
    <script src="${ctx}/bower_components/handlebars/handlebars.js"></script>
</s:if>
<s:else>
    <%--<script src="${ctx}/bower_components/jquery-ui/ui/minified/jquery-ui.min.js" ></script>--%>
    <%--<script src="${ctx}/bower_components/jquery-ui/ui/minified/i18n/jquery.ui.datepicker-zh-CN.min.js" ></script>--%>
    <script src="${ctx}/lib/bootstrap/dist/js/bootstrap.min.js" ></script>
    <%--<script src="${ctx}/bower_components/jasny-bootstrap/dist/js/jasny-bootstrap.min.js" ></script>--%>
    <script src="${ctx}/lib/bootstrap-validator/dist/validator.min.js"></script>
    <script src="${ctx}/lib/jquery-validation/dist/jquery.validate.min.js"></script>
    <script src="${ctx}/lib/jquery-validation/dist/additional-methods.min.js"></script>
    <script src="${ctx}/lib/jquery-validation/dist/localization/messages_zh.min.js"></script>
    <script src="${ctx}/bower_components/bootstrap-select/dist/js/bootstrap-select.min.js"></script>
    <script src="${ctx}/bower_components/bootstrap-select/dist/js/i18n/defaults-zh_CN.min.js"></script>
    <script src="${ctx}/lib/bootstrap-dual-list-box/dual-list-box.min.js"></script>
    <script src="${ctx}/bower_components/moment/min/moment.min.js"></script>
    <script src="${ctx}/bower_components/moment/locale/zh-cn.js"></script>
    <script src="${ctx}/bower_components/select2/dist/js/select2.min.js"></script>
    <script src="${ctx}/bower_components/select2/dist/js/i18n/zh-CN.js"></script>
    <script src="${ctx}/bower_components/bootstrap3-dialog/dist/js/bootstrap-dialog.min.js"></script>
    <script src="${ctx}/bower_components/handlebars/handlebars.min.js"></script>
</s:else>


    <script src="${ctx}/bower_components/bootstrap-switch/dist/js/bootstrap-switch.min.js"></script>
    <script src="${ctx}/bower_components/lodash/lodash.min.js"></script>


<%--<script src="${ctx}/bower_components/datatables/media/js/jquery.dataTables.js"></script>--%>
<%--<script src="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.min.js"></script>--%>
<%--<link rel="stylesheet" href="${ctx}/bower_components/datatables-plugins/integration/bootstrap/3/dataTables.bootstrap.css">--%>

<%-- libaries for feasibility research go here --%>
<%--<script src="${ctx}/bower_components/bootstrap-combobox/js/bootstrap-combobox.js"></script>--%>

<!-- jquery-ui datepicker-->
<script src="${ctx}/bower_components/jquery-ui/jquery-ui.js"></script>
<%--<script src="${ctx}/bower_components/jquery-ui/ui/tooltip.js"></script>--%>
<script src="${ctx}/bower_components/jquery-ui/ui/i18n/datepicker-zh-CN.js"></script>

<script src="${ctx}/bower_components/handlebars-helpers/src/helpers.js"></script>

<script src="${ctx}/bower_components/bootstrap-duallistbox/dist/jquery.bootstrap-duallistbox.js"></script>

<script src="${ctx}/bower_components/tinymce/jquery.tinymce.min.js"></script>
<%--<script src="${ctx}/bower_components/tinymce/tinymce.jquery.js"></script>--%>

<%-- Application specific shared custom scripts go here --%>
<script src="${ctx}/assets/js/jquery-validator-defaults.js"></script>
<script src="${ctx}/assets/js/handlebars-helpers.js" ></script>
<script src="${ctx}/assets/js/application-defaults.js" ></script>
<script src="${ctx}/assets/js/ajax-utils.js" ></script>
<script src="${ctx}/assets/js/dialogs.js" ></script>
<script src="${ctx}/assets/js/date-time-picker-utils.js" ></script>
<script src="${ctx}/assets/js/application-utils.js" ></script>
<script src="${ctx}/assets/js/application-navigation.js"></script>
<script src="${ctx}/assets/js/utils/navigation.js"></script>


<%-- Business specific scripts go here --%>
<script src="${ctx}/assets/js/utils/pagination-helper.js"></script>
<script src="${ctx}/assets/js/utils/question-utils.js"></script>
<script src="${ctx}/assets/js/utils/syllabus-utils.js"></script>
