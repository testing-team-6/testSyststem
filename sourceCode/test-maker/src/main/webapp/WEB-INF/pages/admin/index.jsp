<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>


<div class="jumbotron jumbotron-bg">
    <div class="container">
        <h1><s:text name="label.console"/></h1>
        <p/>
        <p>这里是出题系统管理后台。在管理控制台可以系统配置，进行用户、角色管理以及考试大纲、章节和知识点的管理。</p>

        <div class="row">
            <div class="col-md-4">
                <s:actionerror id="action-err" class="list-group" cssErrorClass="list-group-item list-group-item-danger" />
            </div>

        </div>
        <s:if test="%{#session.admin !=null}">
            <div class="row">
                <div class="col-md-3">
                    <div class="alert alert-success" role="alert">
                        <span>您已登录，直接进入</span>
                    </div>

                </div>
                <div class="col-md-4">
                    <s:url id="welcome" action="welcome" namespace="/web/admin" />
                    <a href="${welcome}" class="btn btn-success btn-lg btn-block">进入后台</a>
                </div>
            </div>
        </s:if>
        <s:else>
            <form id="adminLoginForm" action="login.action" method="post" class="form-horizontal" role="form">
                <span id="error-container" class="label label-danger"></span>
                <div class="form-group">
                    <s:set var="ulabel"><s:text name="label.username" /></s:set>
                    <label class="col-md-2 control-label" for="username"><s:text name="label.username" /></label>
                    <div class="col-md-3">
                        <input type="text" aria-label="user name" id="username" name="username" class="form-control"
                               pattern="[\w\-]{2,}"
                               autofocus placeholder="${ulabel}" required>
                    </div>
                </div>

                <div class="form-group">
                    <s:set var="plabel"><s:text name="label.password" /></s:set>
                    <label class="col-md-2 control-label" for="password" key="label.password">${plabel}</label>
                    <div class="col-md-3">
                        <input type="password" id="password" class="form-control"
                               pattern="\w{4,}"
                               name="password" placeholder="${plabel}" required>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-offset-2 col-md-2">
                        <button id="login-button" type="submit"
                                class="btn btn-success btn-block"><s:text name="label.login" /></button>
                    </div>

                </div>
            </form>
        </s:else>


        <span id="info"></span>
    </div>
</div>
<script src="${ctx}/assets/js/admin/admin-login.js"></script>
