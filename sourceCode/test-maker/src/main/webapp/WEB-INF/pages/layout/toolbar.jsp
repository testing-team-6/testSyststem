<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<s:if test="%{#session.USER !=null}">
    <li>
        <a href="#" class="navbar-text">
            <i class="glyphicon glyphicon-user"></i><span class="current-user">${USER.username}</span>
        </a>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle navbar-text" data-toggle="dropdown" role="button"><i class="glyphicon glyphicon-info-sign"></i>个人信息 <span class="caret"></span></a>
        <ul class="dropdown-menu" role="menu">
            <li><a href="#" data-toggle="modal" role="button" aria-expanded="false" data-target="#user-details">更新信息</a></li>
            <li><a href="#" data-toggle="modal" role="button" aria-expanded="false" data-target="#change-password">修改密码</a></li>
        </ul>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle navbar-text" data-toggle="dropdown" role="button">
            <i class="glyphicon glyphicon-question-sign"></i><s:text name="label.help"/> <span class="caret"/>
        </a>
        <ul class="dropdown-menu" role="menu">
            <li><a href="#" role="button" data-toggle="modal" data-target="#about-dialog"><s:text name="label.about"/></a></li>
        </ul>
    </li>
    <li><a id="logout-btn" class="navbar-text" href="logout.action"><i class="glyphicon glyphicon-log-out"></i>退出</a></li>

    <s:set var="dialog-title" value="getText('label.application')"/>


    <%-- Change password form modal --%>
    <div id="change-password" class="modal" data-backdrop="static" data-keyboard="true" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4><strong><s:text name="label.password.change"/></strong></h4>
                </div>
                <div class="modal-body">
                    <form id="change-password-form" class="form-horizontal" role="form" action="">
                        <div class="form-group">
                            <label for="oldPassword" class="control-label col-md-4"><s:text name="label.password.old"/></label>
                            <div class="col-md-8">
                                <input type="password" class="form-control" id="oldPassword" name="oldPassword" minLength="4" required>
                                <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="newPassword" class="control-label col-md-4"><s:text name="label.password.new"/></label>
                            <div class="col-md-8">
                                <input type="password" class="form-control" id="newPassword" name="newPassword" minLength="4" required>
                                <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="retypePassword" class="control-label col-md-4"><s:text name="label.password.retype"/></label>
                            <div class="col-md-8">
                                <input type="password" class="form-control" id="retypePassword"
                                       name="retypePassword" data-match-error="Whoops, these don't match" minLength="4" required>
                                <div class="help-block with-errors"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-4 col-md-offset-4">
                                <button id="change-passwd-submit" type="button" class="form-control btn btn-primary">提交</button>
                            </div>
                            <div class="col-md-4">
                                <button type="button" class="form-control btn btn-default" id="cancelBtn" data-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal -->
    <div class="modal" id="user-details" tabindex="-1" data-backdrop="static" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel"><s:text name="user-details.title"/></h4>
                </div>
                <div class="modal-body">
                    <form id="updateProfileForm" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="control-label col-md-3" for="fullName"><s:text name="label.entity.user.fullName"/></label>
                            <div class="col-md-9">
                                <input type="text" id="fullName" name="fullName" class="form-control" value="${USER.fullName}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3" for="email"><s:text name="label.entity.user.email"/></label>
                            <div class="col-md-9">
                                <input type="email" id="email" name="email" class="form-control" value="${USER.email}" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3" for="phone"><s:text name="label.phone"/></label>
                            <div class="col-md-9">
                                <input type="text" id="phone" name="phone" class="form-control" value="${USER.phone}">
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="submit-profile-form-btn" type="button" class="btn btn-primary" disabled><i class="glyphicon glyphicon-save"></i><s:text name="button.label.save"/></button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"><s:text name="label.close"/></button>
                </div>
            </div>
        </div>
    </div>

    <div id="about-dialog" class="modal" data-backdrop="static" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h3 class="modal-title"><img src="${ctx}/assets/images/logo.jpg"><strong><s:text name="label.application"/></strong></h3>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <label class="control-label col-md-3 col-md-offset-2">版本号</label>
                            <div class="col-md-6">
                                <span>${RELEASE.version}</span>
                            </div>
                        </div>
                        <div class="row">
                            <label class="control-label col-md-3 col-md-offset-2">构建号</label>
                            <div class="col-md-6">
                                <span>${RELEASE.buildNumber}</span>
                            </div>
                        </div>
                        <div class="row">
                            <label class="control-label col-md-3 col-md-offset-2">构建日期</label>
                            <div class="col-md-6">
                                <span>${RELEASE.buildDate}</span>
                            </div>
                        </div>
                        <div class="row">
                            <label class="control-label col-md-3 col-md-offset-2">SVN 版本号</label>
                            <div class="col-md-6">
                                <span>${RELEASE.revision}</span>
                            </div>
                        </div>

                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal"><s:text name="label.close"/></button>
                </div>
            </div>
        </div>
    </div>
</s:if>
<script src="${ctx}/assets/js/toolbar.js"></script>
