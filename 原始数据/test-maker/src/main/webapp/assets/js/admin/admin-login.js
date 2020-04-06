(function () {
    /**
     * DOM variables
     *
     */
    var loginForm = $('#adminLoginForm');


    /**
     * Model variables
     */
    var username, password;

    $(function () {
        initialize();
    });

    function initialize() {
        if (!_.isUndefined(CONTEXT.admin)) {
            console.warn('User already authenticated');
//            goToWelcome();
        }
    }


    loginForm.submit(function (e) {
        e.preventDefault();
        if (!loginForm.valid()) {
            return false;
        }
        bindToModel();
        doLogin();
    });

    function bindToModel() {
        username=$('input[name="username"]').val();
        password = $('input[name="password"]').val();
    }

    function doLogin() {
        var url = CONTEXT.ctx + '/web/admin/login.action';
        AjaxUtils.getData(url, {username: username, password: password})
            .done(goToWelcome)
            .error(formReset);
    }

    function goToWelcome() {
        window.location.href = 'welcome.action';
    }

    function formReset() {
        loginForm[0].reset();
    }
})();
