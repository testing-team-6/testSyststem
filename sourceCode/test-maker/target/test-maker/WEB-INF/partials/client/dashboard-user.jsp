<%@ page contentType="text/html; charset=UTF-8" %>
<h3><strong>进度</strong></h3>

<div class="progress">
    <div id="task-progress-bar" class="progress-bar progress-bar-info progress-bar-striped active" style="min-width: 2em;"
         role="progressbar"></div>
</div>

<div class="row">
    <div class="col-md-3">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h3 class="panel-title">任务分配情况</h3>
            </div>
            <div class="panel-body">
                <table id="task-overview-table" class="table table-bordered table-striped">
                    <thead>
                    <tr>
                        <th>类型</th>
                        <th>进行中</th>
                        <th>总计</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <strong>编辑</strong>
                        </td>
                        <td>
                            <a class="btn btn-default btn-block" role="button">
                                <span class="badge" id="author-pending"> </span>
                            </a>
                        </td>
                        <td>
                            <span class="label-lg label-success" id="author-total"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong>评审</strong>
                        </td>
                        <td>
                            <a class="btn btn-default btn-block" role="button">
                                <span class="badge" id="review-pending"> </span>
                            </a>
                        </td>
                        <td>
                            <span class="label-lg label-success" id="review-total"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <strong>再审</strong>
                        </td>
                        <td>
                            <a class="btn btn-default btn-block" role="button">
                                <span class="badge" id="qa-pending"> </span>
                            </a>
                        </td>
                        <td>
                            <span class="label-lg label-success" id="qa-total"></span>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">到期任务</h3>
    </div>
    <div class="panel-body">
        <table id="expiring-questions-table" class="table table-condensed table-striped table-bordered"></table>
    </div>
</div>
