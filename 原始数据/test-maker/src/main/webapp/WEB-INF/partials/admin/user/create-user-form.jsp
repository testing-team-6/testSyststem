<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<form id="create-user-form" class="collapse form-horizontal">
    <fieldset>
        <legend>&nbsp;</legend>
        <div class="form-group">
            <label for="user-username" class="col-md-2 col-lg-1 control-label"><s:text name="label.username"/></label>
            <div class="col-md-3 col-lg-2">
                <input id="user-username" name="username" class="form-control" type="text" minlength="4" required>
            </div>
            <label for="user-email" class="col-md-2 col-lg-1 control-label">全名</label>
            <div class="col-md-3 col-lg-2">
                <input id="user-fullName" name="fullName" class="form-control" type="text" minlength="2" required>
            </div>
        </div>
        <div class="form-group">
            <label for="user-phone" class="col-md-2 col-lg-1 control-label"><s:text name="label.entity.user.phone"/></label>
            <div class="col-md-3 col-lg-2">
                <input id="user-phone" name="phone" pattern="^\+?\d{3,}[\d\-\(\)\*]*" class="form-control" type="tel" minlength="6" required>
            </div>
            <label for="user-email" class="col-md-2 col-lg-1 control-label"><s:text name="label.entity.user.email"/></label>
            <div class="col-md-3 col-lg-2">
                <input id="user-email" name="email" class="form-control" type="email" required>
            </div>
        </div>
        <div class="form-group">
            <label for="user-password" class="col-md-2 col-lg-1 control-label"><s:text name="label.password"/></label>
            <div class="col-md-3 col-lg-2">
                <input id="user-password" name="password" class="form-control" type="password" minlength="4" required>
            </div>
            <label for="user-password-confirm" class="col-md-2 col-lg-1 control-label"><s:text name="label.password.retype"/></label>
            <div class="col-md-3 col-lg-2">
                <input id="user-password-confirm" name="passwordConfirm" data-match="#user-password" class="form-control" type="password" required>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-offset-2 col-md-2 col-lg-offset-1 col-lg-1">
                <button id="submit-form-btn" type="submit" class="btn btn-primary">提交</button>
            </div>
            <div class="col-md-2 col-lg-1">
                <button id="reset-form-btn" type="reset" class="btn btn-default">重置</button>
            </div>
        </div>
    </fieldset>

</form>
