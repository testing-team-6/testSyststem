(function () {
    $(function () {

        /*
         * Definition of DOM elements
         */
        var dataTable = $('#user-data-table');
        var dataTemplate = $('#user-table-tmpl');

        var createUserForm = $('#create-user-form');
        var submitFormBtn = $('#submit-form-btn');
        var switchUserStateBtn = $('#switch-state-btn');
        var switchUserAdminStateBtn = $('#switch-admin-btn');
        var resetPasswordBtn = $('#reset-password-btn');

        var selectAllCbx = $('.item-select-all');
        var searchBox = $('#user-filter');
        var tableFilter;
        /*
         * Definition of data variables
         */
        var selected={}, users=[];
        var selectedUsers=[];

        /**
         * The first method to execute automatically once the page finishes loading
         */
        initialize();



        /**
         * Initialize the users page when the page is loaded.
         */
        function initialize () {

            loadData();
            tableFilter = new TableFilter(dataTable, searchBox);
            createUserForm.find('input[type="checkbox"]').bootstrapSwitch();
        }

        createUserForm.validate({
            rules: {
                password: 'required',
                passwordConfirm:{
                    equalTo: "#user-password"
                }
            }
        });

        /**
         * Submit create user form
         */
        submitFormBtn.click(function (e) {
            var isFormValid = createUserForm.valid();
            console.log('Is form valid? %s', isFormValid);
            if(!isFormValid) return false;

            bindFormToModel();
            var url = CONTEXT.ctx + '/web/user/create.action';

            var data = {
                user: selected
            };
            AjaxUtils.postData(url, data).done(wrapUp);
            e.preventDefault();
        });

        /**
         * Disable/enable toolbar buttons about user management operations. If no user is selected, the buttons
         * should be disabled.
         */
        dataTable.on('click', '.item-selection', function (e) {
            var numOfRowsSelected = dataTable.find('.item-selection:checked').length;
            if (numOfRowsSelected>0) {
                toggleToolbarButtons(false);
            }else {
                toggleToolbarButtons(true);
            }
        });

        /**
         * Switch user state action
         */
        switchUserStateBtn.click(function (e) {
            bindSelectedRowsToUsers();
            Dialogs.confirm('确定要修改所选用户的状态吗？', function (result) {
                if (result) {
                    var url = CONTEXT.ctx + '/web/user/user-toggleState.action';
                    var data = {
                        users: selectedUsers
                    };
                    AjaxUtils.postData(url, data).done(wrapUp);
                }
            });
        });

        /**
         * action for toggling user admin state
         */
        switchUserAdminStateBtn.click(function (e) {
            bindSelectedRowsToUsers();
            Dialogs.confirm('确定要修改所选用户的状态吗？', function (result) {
                if (result) {
                    var url = CONTEXT.ctx + '/web/user/user-toggleAdminState.action';
                    var data = {
                        users: selectedUsers
                    };
                    AjaxUtils.postData(url, data).done(wrapUp);
                }
            });
        });

        /**
         * action for resetting user password
         */
        resetPasswordBtn.click(function (e) {
            bindSelectedRowsToUsers();
            var _user=selectedUsers.shift();
            var msg = '重置密码不支持对多个用户同时操作，确定要重置所选用户 <label class="label-lg label-warning">'+_user.username+'</label>的密码吗？';
            Dialogs.confirm(msg, function (result) {
                if (result) {
                    var url = CONTEXT.ctx + '/web/user/resetPassword.action';
                    var data = {
                        user: _user
                    };
                    AjaxUtils.postData(url, data, true).done(function (data, textStatus, jqXHR) {
                        Dialogs.success('新密码已生成，请妥善保存，另外请注意查收电子邮件。<span class="bg-danger">'+data.tempPassword+'</span>');
                    }).done(wrapUp);
                }
            });
        });

        /**
         * Select all function
         */
        selectAllCbx.change(function (e) {
            var isSelected = $(this).prop('checked');

            dataTable.find('input.item-selection').prop('checked', isSelected);
            toggleToolbarButtons(!isSelected);
        });

        /**
         * wrap up function after submit, clic, etc
         */
        function wrapUp() {
            createUserForm[0].reset();
            selectedUsers=[];
            selectAllCbx.prop('checked', false);
            toggleToolbarButtons(true);
            createUserForm.collapse('hide');
            loadData();
        }

        /**
         * Bind the form data to the user model (variable: selected)
         */
        function bindFormToModel() {
            selected.username=createUserForm.find('[name="username"]').val();
            selected.fullName=createUserForm.find('[name="fullName"]').val();
            selected.email=createUserForm.find('[name="email"]').val();
            selected.phone=createUserForm.find('[name="phone"]').val();
            selected.password=window.btoa(createUserForm.find('[name="password"]').val());

            console.log('User bound to model: %s', JSON.stringify(selected));
        }

        /**
         * Loads user data from server and populate it to user list table
         */
        function loadData() {
            var url = CONTEXT.ctx + '/web/user/list-all.action';
            AjaxUtils.getData(url, undefined)
                .done(function (data, textStatus, jqXHR) {
                    users=data.users;
                    console.log('%s users loaded from server.', users.length);
                    var source = dataTemplate.html();
                    var template = Handlebars.compile(source);

                    dataTable.find('tbody').remove();
                    dataTable.append(template(data));
                });
        }


        /**
         * Enable toolbar buttons
         */
        function toggleToolbarButtons(flag) {
            $('nav.toolbar li>a:not([data-toggle="collapse"])').toggleClass('disabled',flag);
        }

        /**
         * Put the users in the selected rows in selectedUsers model variable
         */
        function bindSelectedRowsToUsers() {
            var selectedRows = dataTable.find('td > input:checked');
            console.log('Rows selected: %s', selectedRows);
            $.each(selectedRows, function (index, element) {
                var row = $(this).closest('tr');
                var _user = users[row.data('index')];
                selectedUsers.push(_user);
            });

            console.log('Users bound to model: %s', selectedUsers.length);
        }
    });
})();
