<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div role="tabpanel">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#task-tab" role="tab" data-toggle="tab">我的任务概况</a></li>
        <li role="presentation"><a href="#project-tab" role="tab" data-toggle="tab">项目概况</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane fade in active" id="task-tab">
            <jsp:include page="dashboard-user.jsp"/>
        </div>

        <div role="tabpanel" class="tab-pane fade" id="project-tab">
            <nav class="toolbar navbar navbar-default">
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li>
                            <button id="send-reminder-btn" class="btn btn-primary navbar-btn-tm" type="button">发送邮件提醒到期任务</button>
                        </li>
                    </ul>
                </div>
            </nav>
            <div class="row">
                <div class="col-md-12">
                    <h3><strong>项目进度</strong></h3>
                    <div class="progress">
                        <div id="project-progress-bar" class="progress-bar progress-bar-primary progress-bar-striped active" role="progressbar"></div>
                    </div>
                </div>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">到期任务</h3>
                </div>
                <div class="panel-body">
                    <table id="project-expiring-questions" class="table table-condensed table-striped table-bordered"></table>
                </div>
            </div>
        </div>
    </div>
</div>
