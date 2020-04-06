<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#if ctx??>
    <link rel="stylesheet" href="${ctx}/assets/css/app.min.css" />
<#else>
    <link rel="stylesheet" href="D:/eclipse/workspace-mine/test-maker/src/main/webapp/assets/css/app.min.css" />
</#if>
</head>
<body class="container">
<h3>${user.fullName}，您好：</h3>
<div>
    <p>感谢您参与 CSTQB 出题项目 <b>${project.name}</b>。您有 <span class="label-lg label-primary">${count}</span> 个问题已经过期，请您下次注意及时完成任务并更新状态。</p>
    <p>以下是已经过期的问题：</p>
</div>


<table border="1" class="table table-bordered table-condensed table-striped table-hover">
    <thead>
    <th>ID</th>
    <th>知识点</th>
    <th>状态</th>
    <th>作者</th>
    <th>编辑开始日期</th>
    <th>编辑结束日期</th>
    <th>评审</th>
    <th>评审开始日期</th>
    <th>评审结束日期</th>
    </thead>
    <tbody>

    <#list questions as question>
    <tr>
        <td>${question.id}</td>
        <td>
        ${question.knowledgePoint.title!""}
        </td>
        <td>${question.status.name}</td>
        <td>
            <#if question.author.fullName == user.fullName>
                <span class="label-lg label-warning">${question.author.fullName}</span>
            <#else>
                <span>${question.author.fullName}</span>
            </#if>
        </td>
        <td>${question.authoringStartDate?date?string.long}</td>
        <td>${question.authoringFinishDate?date?string.long}</td>
        <td>
            <#list question.reviewers as reviewer>
                <#if reviewer.fullName == user.fullName>
                    <span class="label-lg label-warning">${user.fullName}</span>
                <#else>
                    <span>${reviewer.fullName}</span>
                </#if>
            </#list>
        </td>
        <td>${question.reviewingStartDate?date?string.long}</td>
        <td>${question.reviewingFinishDate?date?string.long}</td>
        <!--<td>${question.qualityAdmin.fullName}</td>-->
    </tr>
    </#list>
    </tbody>
</table>

<br/><br/>
<div style="font-style:italic;margin-left:40px">
    CSTQB
</div>
</body>
</html>
