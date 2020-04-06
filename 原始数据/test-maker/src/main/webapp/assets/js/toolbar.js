(function () {
    $(function () {

        /*
         * DOM variables
         */
        var profileModal = $('#user-details');
        var updateProfileForm = $('#updateProfileForm');
        var submitProfileBtn = $('#submit-profile-form-btn');
        var profileForm_fullName = $('input[name="fullName"]');
        var profileForm_email = $('input[name="email"]');
        var profileForm_phone = $('input[name="phone"]');

        var changePasswdModal = $('#change-password');
        var changePasswdForm = $('#change-password-form');
        var changePasswdSubmit = $('#change-passwd-submit');

        /*
         * model variables
         */

        profileForm_fullName.keyup(function () {
            submitProfileBtn.prop('disabled', false);
        });
        profileForm_email.keyup(function () {
            submitProfileBtn.prop('disabled', false);
        });
        profileForm_phone.keyup(function () {
            submitProfileBtn.prop('disabled', false);
        });

        submitProfileBtn.click(function (e) {
            console.log('about to save profile');
            updateProfile();
        });

        profileModal.on('hidden.bs.modal', function () {
            updateProfileForm[0].reset();
        });

        changePasswdSubmit.click(passwd);

        changePasswdModal.on('hidden.bs.modal', function () {
            changePasswdForm[0].reset();
        });

        changePasswdForm.validate({
            rules: {
                oldPassword: {
                    required: true
                },
                newPassword: {
                    required: true
                },
                retypePassword: {
                    equalTo: '#newPassword'
                }
            },
            debug: true,
            highlight: function(element) {
                $(element).parent().addClass('has-error');
            },
            unhighlight: function(element) {
                $(element).parent().removeClass('has-error');
            },
            errorElement: 'span',
            errorClass: 'help-block',
            errorPlacement: function(error, element) {
                if(element.parent('.input-group').length) {
                    error.insertAfter(element.parent());
                } else {
                    error.insertAfter(element);
                }
            }
        });


        /**
         * updates user profile
         * @returns {boolean}
         */
        function updateProfile() {
            if (!updateProfileForm.valid()) {
                return false;
            }

            var data = {
                fullName: profileForm_fullName.val(),
                email: profileForm_email.val(),
                phone: profileForm_phone.val()
            };

            AjaxUtils.get(CONTEXT.ctx + '/web/client/check-modified.action', data).done(function (result) {
                if (_.isBoolean(result.modified) && result.modified) {
                    AjaxUtils.postData(CONTEXT.ctx+'/web/client/update-profile.action', data, true).done(function () {
                        profileModal.modal('hide');
                        Dialogs.info('信息更新成功！请重新登录查看修改内容。');
                    });
                }else {
                    Dialogs.warning('信息未修改，无需提交！');
                }
            });
        }

        function passwd() {
            if (!changePasswdForm.valid()) {
                return false;
            }
            AjaxUtils.postData(CONTEXT.ctx+'/web/client/change-password.action', {
                oldPassword: changePasswdForm.find('input[name="oldPassword"]').val(),
                newPassword: changePasswdForm.find('input[name="newPassword"]').val()
            }, true).done(function () {
                changePasswdModal.modal('hide');
                Dialogs.info('密码修改成功！');
            });
        }
    });

})();
