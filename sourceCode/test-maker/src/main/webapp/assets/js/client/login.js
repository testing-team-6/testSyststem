$(function () {
    var authenticateForm = $('#authenticateForm');
    var loginForm = $('#loginForm');


    var loginSection = $('#login-section');
    var projectList = $('#project-list');
    var loginBtn = $('#login-button');

    initialize();

    function initialize() {
        if (!_.isUndefined(CONTEXT.user)) {
            console.warn('User already authenticated');
        }
    }


    function switchOnLoginControls(option) {
        loginSection.prop('disabled', !option);
        projectList.prop('disabled', !option);
        loginBtn.prop('disabled', !option);
    }
    authenticateForm.validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        }
    });

    authenticateForm.submit(function (e) {

        AjaxUtils.postData(CONTEXT.ctx + '/web/client/authenticate.action', {
            username: $('#username').val(),
            password: $('#password').val()

        }, true)
            .done(function (data, textStatus, jqXHR) {
                console.log('text status: %s', textStatus);
                console.dir(data);
                console.dir(jqXHR);
                var projects=data.projects;
                if (projects) {
                    console.log("The user has %s projects", projects.length);
                    projectList.empty();
                    $.each(projects, function (index, value) {
                        var option = '<option value="' + value.id + '" data-name="'+value.name+'">' + value.name + '</option>';
                        projectList.append(option);
                    });
                    switchOnLoginControls(true);
                }

            })
            .error(function () {
                switchOnLoginControls(false);
            })
        ;

        e.preventDefault();
        $(e).unbind();
    });

    projectList.change(function () {
        var selected = $(this).val();
        console.log('Selected value: %s', selected);
        if (selected <1) {
            Dialogs.error('请选择项目！');
            $('#login-button').prop('disabled', true);
            return false;
        }
    });

    loginBtn.click(function (e) {
        e.preventDefault();
        var opt=$('#project-list option:selected').val();
        $.post('login.action', {
            projectId: opt
        }, function (data) {
            console.log('User login OK');
            console.dir(data);

            window.location.href='welcome.action';
        });
    });
});
