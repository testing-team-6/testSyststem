<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="/WEB-INF/pages/layout/meta-head-styles.jsp" %>

    <title>test maker</title>
    <script>
        var loginRedirect = '${ctx}/web/client/welcome.action';
        console.log('Login redirect: %s', loginRedirect);

        function redirect() {
            window.location.href = loginRedirect;
        }
    </script>
</head>
<body>
<%@include file="/WEB-INF/partials/index-session-def.jsp" %>
<!-- Main jumbotron for a primary marketing message or call to action -->

<div class="jumbotron jumbotron-bg">
    <div class="container">
        <h1>出题系统</h1>

        <p/>

        <p>欢迎使用出题系统</p>

        <div class="row">
            <div class="col-md-4">
                <s:actionerror id="action-err" class="list-group"
                               cssErrorClass="list-group-item list-group-item-danger"/>
            </div>

        </div>
        <s:if test="%{#session.USER !=null && #session.PROJECT !=null}">
            <script>
                function redirectToLanding() {
                    console.log('Redirecting to [%s]', loginRedirect);
                    window.location.href = loginRedirect;
                }
                $(function () {
                    Dialogs.confirm("您已登录，直接进入,是否继续？", function (result) {
                        if (result) {
                            redirectToLanding();
                        }
                    });
                });
            </script>
            <div class="row">
                <div class="col-md-3">
                    <div class="alert alert-success alert-dismissible fade in" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <span>您已登录，直接进入</span>
                    </div>

                </div>
                <div class="col-md-4">
                    <div class="popover right">
                        <div class="arrow"></div>
                        <h3 class="popover-title">Popover right</h3>

                        <div class="popover-content">
                            <p>Sed posuere consectetur est at lobortis. Aenean eu leo quam. Pellentesque ornare sem
                                lacinia quam venenatis vestibulum.</p>
                        </div>
                    </div>
                    <button onclick="redirectToLanding()" class="btn btn-success btn-lg" data-toggle="tooltip"
                            data-placement="left" title="Tooltip on left">进入出题系统
                    </button>
                </div>
            </div>
        </s:if>
        <s:else>
            <form id="authenticateForm" method="post" class="form-horizontal" role="form">
                <p id="error-container" class="bg-danger"></p>
                <fieldset>

                    <div class="form-group">
                        <s:set var="ulabel"><s:text name="label.username"/></s:set>
                        <label class="col-md-2 control-label" for="username"><s:text name="label.username"/></label>

                        <div class="col-md-3">
                            <input type="text" aria-label="user name" id="username" name="username" class="form-control"
                                   pattern="[\w\-]{2,}"
                                   autofocus placeholder="${ulabel}" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <s:set var="plabel"><s:text name="label.password"/></s:set>
                        <label class="col-md-2 control-label" for="password" key="label.password">${plabel}</label>

                        <div class="col-md-3">
                            <input type="password" id="password" class="form-control"
                                   pattern="\w{3,}"
                                   name="password" placeholder="${plabel}" required>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-offset-3 col-md-2">
                            <button id="auth-button" type="submit" class="btn btn-success btn-block"><s:text
                                    name="label.authenticate"/></button>
                        </div>
                    </div>
                </fieldset>
            </form>
            <form id="loginForm" action="login.action" method="post" class="form-horizontal" role="form">
                <fieldset id="login-section" disabled>
                    <div class="form-group">
                        <label class="col-md-2 control-label">项目：</label>

                        <div class="col-md-3">
                            <select id="project-list" name="project-list" class="form-control">
                                <option value="-1"></option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-offset-3 col-md-2">
                            <button id="login-button" type="submit"
                                    class="btn btn-success btn-block"><s:text name="label.login"/></button>
                        </div>
                    </div>
                </fieldset>
            </form>
        </s:else>


    </div>
</div>

<s:if test="%{#application.devMode }">
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <div class="panel panel-primary">
                    <div class="panel-heading">开发测试页面导航栏</div>
                    <div class="panel-body">
                        <ul class="list-group">
                            <s:if test="%{#session.USER ==null}">
                                <li class="list-group-item">
                                    <button id="debug-init" class="btn btn-primary">生成测试数据</button>
                                    <label class="label label-warning">不会自动登录</label></li>
                            </s:if>
                            <s:else>
                                <li class="list-group-item">
                                    <button id="debug-show-details" class="btn btn-primary">显示当前用户项目数据</button>
                                </li>

                            </s:else>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <h4>测试数据 <span class="label label-default">用户信息</span></h4>
                <table id="debug-user-info" class="table table-bordered">
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="col-md-4">
                <h4>测试数据 <span class="label label-default">项目信息</span></h4>
                <table id="debug-project-info" class="table table-bordered">
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>


    </div>
</s:if>

<!-- Loading scripts before the end of body tag for performance consideration -->
<%@include file="/WEB-INF/pages/layout/meta-head-scripts.jsp" %>
<script src="${ctx}/assets/js/jquery-validator-defaults.js"></script>
<script src="${ctx}/assets/js/client/login.js"></script>
<s:if test="%{#application.devMode }">
    <script src="${ctx}/assets/js/client/index-debug.js"></script>
</s:if>

</body>
</html>
