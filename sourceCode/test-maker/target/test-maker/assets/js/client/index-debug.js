(function () {

    var debugUserTable = $('#debug-user-info');
    var debugProjectTable = $('#debug-project-info');
    var user;
    var project;


    function initDebugInfo() {
        AjaxUtils.postData(CONTEXT.ctx+'/web/client/debug.action',{},true).done(function () {
            Dialogs.success('已成功生成测试数据！');
        });
    }

    function showDebugInfo() {
        showUserInfoTable();
        showProjectInfoTable();
        $('#debug-init').prop('disabled', true);
    }


    function showUserInfoTable () {
        if (!user) {
            Dialogs.error('无法加载测试/调试数据，请将浏览器调试窗口内容截图，发给管理员寻求支持！');
            return false;
        }
        debugUserTable.find('tbody').empty()
            .append('<tr><td>用户 ID</td><td>'+ user.id+'</td></tr>')
            .append('<tr><td>用户名</td><td>'+ user.username+'</td></tr>')
            .append('<tr><td>邮件地址</td><td>'+ user.email+'</td></tr>')
            .append('<tr><td>是否管理员</td><td>'+ (user.admin?'是':'否')+'</td></tr>')
            .append('<tr><td>上次登录</td><td>'+ (user.lastLogin?user.lastLogin:'')+'</td></tr>')
    }

    function showProjectInfoTable () {
        if (!project) {
            Dialogs.error('无法加载测试/调试数据，请将浏览器调试窗口内容截图，发给管理员寻求支持！');
            return false;
        }
        debugProjectTable.find('tbody')
            .empty()
            .append('<tr><td>项目 ID</td><td>'+ project.id+'</td></tr>')
            .append('<tr><td>项目名</td><td>'+ project.name+'</td></tr>')
            .append('<tr><td>主持人</td><td>'+ project.facilitator.username+'</td></tr>')
            .append('<tr><td>状态</td><td>'+ project.status.name+'</td></tr>')
            .append('<tr><td>项目成员数</td><td>'+ project.users.length+'</td></tr>')
            .append('<tr><td>创建时间</td><td>'+ project.createdOn+'</td></tr>')
            .append('<tr><td>更新时间</td><td>'+ project.updatedOn+'</td></tr>')
    }

    $('#debug-init').click(initDebugInfo);
    $('#debug-show-details').click(function () {
        $.get('debug-loadData.action')
            .done(function (data, textStatus, jqXHR) {
                user=data.user;
                project=data.project;
                showDebugInfo();
                $('#debug-show-details').prop('disabled', true);
            });
    });
})();
