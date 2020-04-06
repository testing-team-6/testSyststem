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
<h3>${facilitator.fullName}，您好：</h3>
<div>
    <p>非常高兴的通知您，您被任命为 CSTQB 出题项目 <b>${project.name}</b> 的主持人。作为主持人，您可以编写题目知识点框架，指定作者、评审、再审等角色；您还需要
    负责项目的进展情况。</p>
    <p>以下是项目详细信息：</p>
</div>


<table border="1" class="table table-bordered table-condensed table-striped table-hover">
    <tbody>

    <tr>
        <td>ID</td>
        <td>${project.id}</td>
        <td class="active">项目名</td>
        <td>${project.name}</td>
        <td>大纲</td>
        <td>
            <#if project.syllabus ??>
                ${project.syllabus.level} &ndash; ${project.syllabus.version}
            </#if>
        </td>
    </tr>
    <tr>
        <td>状态</td>
        <td>${project.status.name}</td>
        <td>开始日期</td>
        <td>${project.startDate?date?string.long}</td>
        <td>结束日期</td>
        <td>${project.finishDate?date?string.long}</td>
    </tr>
    <tr>
        <td>项目成员列表</td>
        <td colspan="5">
            <ul>
            <#list users as user>
                <li>
                    <label>${user.username} (${user.fullName})</label>
                    <#if !user.enabled>
                        <i>//帐户已禁用</i>
                    </#if>
                </li>
            </#list>
            </ul>
        </td>
    </tr>
    </tbody>
</table>

<br/><br/>
<div style="font-style:italic;margin-left:40px">
        CSTQB
</div>
</body>
</html>
