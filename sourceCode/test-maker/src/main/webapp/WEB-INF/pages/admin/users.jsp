<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>

    <div class="page-header">
        <h1><i class="glyphicon glyphicon-user"></i>用户管理
            <small>系统用户管理</small>
        </h1>
    </div>

    <%-- Toolbar section --%>
    <div class="container-fluid">
        <nav class="toolbar navbar navbar-default">
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li>
                        <a id="create-user-btn" class="btn btn-primary navbar-btn-tm" href="#create-user-form"
                           data-toggle="collapse" role="button"><i class="glyphicon glyphicon-plus-sign"></i>新增用户</a>
                    </li>
                    <li>
                        <a id="switch-state-btn" class="disabled btn btn-primary navbar-btn-tm" role="button"><i
                                class="glyphicon glyphicon-off"></i>启用/禁用用户</a>
                    </li>
                    <li>
                        <a id="switch-admin-btn" class="disabled btn btn-primary navbar-btn-tm" role="button"><i
                                class="glyphicon glyphicon-user"></i>设定/取消管理员权限</a>
                    </li>
                    <li><a id="reset-password-btn" class="disabled btn btn-primary navbar-btn-tm" role="button">重设密码</a>
                    </li>
                    <li>
                        <form class="navbar-form" role="search">
                            <div class="form-group">
                                <input id="user-filter" type="text" class="form-control" placeholder="用户关键字">
                                <a id="search-user-btn" class="btn btn-danger navbar-btn-tm"><i
                                        class="glyphicon glyphicon-search"></i></a>
                            </div>
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
        <jsp:include page="/WEB-INF/partials/admin/user/create-user-form.jsp"/>
        <table id="user-data-table" class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th><input class="item-select-all" type="checkbox"></th>
                <th>ID</th>
                <th><s:text name="label.username"/></th>
                <th><s:text name="label.fullName"/></th>
                <th><s:text name="label.entity.user.email"/></th>
                <th><s:text name="label.phone"/></th>
                <th><s:text name="label.entity.user.isEnabled"/></th>
                <th align="center"><s:text name="label.entity.user.isAdmin"/></th>
                <%--<th><s:text name="label.entity.user.loginCount"/></th>--%>
                <th><s:text name="label.entity.user.lastLogin"/></th>
                <%--<th><s:text name="label.createdOn"/></th>--%>
                <%--<th><s:text name="label.updatedOn"/></th>--%>
            </tr>
            </thead>
        </table>
        <%--    <nav>
                <ul class="pager">
                    <li id="prevPage" class="disabled"><a href="#" class="btn btn-default"><i class="glyphicon glyphicon-chevron-left"></i><s:text name="nav.previous"/></a></li>
                    <li id="nextPage"><a href="#" class="btn btn-default"><s:text name="nav.next"/><i class="glyphicon glyphicon-chevron-right"></i></a></li>
                </ul>
            </nav>--%>
    </div>

    <script id="user-table-tmpl" type="text/x-handlebars-template">
        <tbody>
        {{#each users}}
        <tr class="item-row" data-index="{{@index}}">
            <td>

                <input type="checkbox" class="item-selection">

                <%--              <a href="#" class="edit-item"><i class="glyphicon glyphicon-edit"></i></a>
                              <a href="#" class="delete-item"><i class="glyphicon glyphicon-remove"></i></a>--%>
            </td>
            <td data-attr="id">{{id}}</td>
            <td data-attr="username">{{username}}</td>
            <td>{{fullName}}</td>
            <td>{{email}}</td>
            <td>{{phone}}</td>
            <td>
                {{#if enabled}}
                <span class="label-lg label-success">活动</span>
                {{else}}
                <span class="label-lg label-warning">已禁用</span>
                {{/if}}
            </td>
            <td>
                {{#if admin}}
                <span class="label-lg label-danger">管理员</span>
                {{else}}
                <span class="label-lg label-success">普通用户</span>
                {{/if}}
            </td>
            <%--<td>{{loginCount}}</td>--%>
            <td>
                {{#if lastLogin}}
                {{date lastLogin}}
                {{else}}

                {{/if}}
            </td>
            <%--<td>{{date createdOn}}</td>--%>
            <%--<td>{{date updatedOn}}</td>--%>
        </tr>
        {{/each}}
        </tbody>
    </script>
</div>
<%--<script src="${ctx}/assets/js/admin/user-pagination.js"></script>--%>
<script src="${ctx}/assets/js/admin/users.js"></script>
