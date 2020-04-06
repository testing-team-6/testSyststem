<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">
        body {
            margin: 0;
            padding: 20px;
            font: 13px "Lucida Grande", "Lucida Sans Unicode", Helvetica, Arial, sans-serif;
        }

        /* ---- Some Resets ---- */

        p,
        table, caption, td, tr, th {
            margin: 0;
            padding: 0;
            font-weight: normal;
        }

        /* ---- Paragraphs ---- */

        p {
            margin-bottom: 15px;
        }

        /* ---- Table ---- */

        table {
            border-collapse: collapse;
            margin-bottom: 15px;
            width: 90%;
        }

        caption {
            text-align: left;
            font-size: 15px;
            padding-bottom: 10px;
        }

        table td,
        table th {
            padding: 5px;
            border: 1px solid #fff;
            border-width: 0 1px 1px 0;
        }

        thead th {
            background: #91c5d4;
        }

        thead th[colspan],
        thead th[rowspan] {
            background: #66a9bd;
        }

        tbody th,
        tfoot th {
            text-align: left;
            background: #91c5d4;
        }

        tbody td,
        tfoot td {
            text-align: center;
            background: #d5eaf0;
        }

        tfoot th {
            background: #b0cc7f;
        }

        tfoot td {
            background: #d7e1c5;
            font-weight: bold;
        }

        tbody tr.odd td {
            background: #bcd9e1;
        }
    </style>
</head>
<body>
<h3>${user.fullName}，您好：</h3>

<p>您分派到了新的题目。您在本题目中的角色是<b>
<#switch role>
    <#case "Author">
        作者
        <#break>
    <#case "Reviewer">
        评审
        <#break>
    <#case "QA">
        质管
        <#break>
    <#default>
        未知
</#switch>
</b>
</p>

<h2>题目详情</h2>

<table id="travel" summary="Question summary">
    <thead>题目属性</thead>

    <tbody>
    <tr>
        <th>ID</th>
        <td>${question.id}</td>
        <th>知识点</th>
        <td colspan="3">
        <#if question.knowledgePoint??>
            <p>${question.knowledgePoint.number} (${question.knowledgePoint.kLevel}
                ) ${question.knowledgePoint.title}</p>
        <#else>
            <p>暂无知识点</p>
        </#if>
        </td>
    </tr>
    <tr>
        <th>状态</th>
        <td>${question.status.name}</td>
        <th>类型</th>
        <td>${question.type.name}</td>
        <th>语言</th>
        <td>${question.language.name}</td>
    </tr>
    <tr>
        <th>难度</th>
        <td>${question.difficulty}</td>
        <th>分值</th>
        <td>${question.score!""}</td>
        <th>K值</th>
        <td>
        <#if question.knowledgePoint??>
                ${question.knowledgePoint.kLevel!""}
            </#if>
        </td>
    </tr>
    </tbody>
</table>

<table id="travel" summary="Question content">
    <thead>试题详情</thead>
    <tbody>
    <tr>
        <th colspan="2">场景描述</th>
    </tr>
    <tr>
        <td colspan="2">
        <#if question.scenario??>
            <div>${question.scenario}</div>
        </#if>
        </td>
    </tr>
    <tr>
        <th colspan="2">题干</th>
    </tr>
    <tr>
        <td colspan="2">
        <#if question.stem??>
            <div>${question.stem}</div>
        </#if>
        </td>
    </tr>
    <tr>
        <th colspan="2">备注</th>
    </tr>
    <tr>
        <td colspan="2">
        <#if question.remark??>
            <div>${question.remark}</div>
        </#if>
        </td>
    </tr>
    <tr>
        <th colspan="2">题目选项</th>
    </tr>
    <tr>
        <th>选项</th>
        <th>内容</th>
    </tr>
    <#list choices as choice>
    <tr>
        <td valign="top" style="width: 40px;">
            <#if choice.isCorrectAnswer>
                <label class="label-lg label-success">${choice.choiceLabel}. </label>
            <#else>
                <label class="label-lg label-default">${choice.choiceLabel}. </label>
            </#if>
        </td>
        <td>
            <div>${choice.content}</div>
        </td>
    </tr>
    </#list>
    </tbody>
</table>


<#--<div class="container">-->
<#--<div class="panel panel-primary">-->
<#--<div class="panel-heading">-->
<#--<h3 class="panel-title"><a href="#q-attr-table" data-toggle="collapse" class="btn">题目属性</a></h3>-->
<#--</div>-->
<#--<div class="panel-body">-->
<#--<div id="q-attr-table" class="collapse in">-->
<#--<table class="table table-bordered">-->
<#--<tbody>-->
<#--<tr>-->
<#--<td class="active"><label>ID</label></td>-->
<#--<td>${question.id}</td>-->
<#--<td class="active"><label>知识点</label></td>-->
<#--<td colspan="3">-->
<#--<#if question.knowledgePoint??>-->
<#--<p>${question.knowledgePoint.number} (${question.knowledgePoint.kLevel}-->
<#--) ${question.knowledgePoint.title}</p>-->
<#--<#else>-->
<#--<p>暂无知识点信息</p>-->
<#--</#if>-->
<#--</td>-->
<#--</tr>-->
<#--<tr>-->
<#--<td class="active"><label>状态</label></td>-->
<#--<td>${question.status.name}</td>-->
<#--<td class="active"><label>类型</label></td>-->
<#--<td>${question.type.name}</td>-->
<#--<td class="active"><label>语言</label></td>-->
<#--<td>${question.language.name}</td>-->
<#--</tr>-->
<#--<tr>-->
<#--<td class="active"><label>难度</label></td>-->
<#--<td>${question.difficulty}</td>-->
<#--<td class="active"><label>分值</label></td>-->
<#--<td>-->
<#--${question.score!""}</td>-->
<#--<td class="active"><label>K 值</label></td>-->
<#--<td>-->
<#--<#if question.knowledgePoint??>-->
<#--${question.knowledgePoint.kLevel!""}-->
<#--</#if>-->
<#--</td>-->
<#--</tr>-->

<#--&lt;#&ndash;                    <tr>-->
<#--<td class="active"><label>作者</label></td>-->
<#--<td>${question.author.fullName}</td>-->
<#--<td class="active"><label>评审</label></td>-->
<#--<td>-->
<#--${#each question.reviewers}-->
<#--<p>${this.fullName}</p>-->
<#--${/each}-->

<#--</td>-->
<#--<td class="active"><label>质管</label></td>-->
<#--<td>${question.qualityAdmin.fullName}</td>-->
<#--</tr>&ndash;&gt;-->
<#--<tr>-->
<#--<td class="active"><label>出题期限</label></td>-->
<#--<td colspan="3">-->
<#--<span>${question.authoringStartDate?date?string.long} &ndash;-->
<#--${question.authoringFinishDate?date?string.long}-->
<#--</span>-->
<#--</td>-->
<#--<td class="active"><label>评审期限</label></td>-->
<#--<td colspan="3">-->
<#--<span>${question.reviewingStartDate?date?string.long} &ndash;-->
<#--${question.reviewingFinishDate?date?string.long}-->
<#--</span>-->
<#--</td>-->
<#--</tr>-->
<#--</tbody>-->
<#--</table>-->
<#--</div>-->
<#--</div>-->
<#--</div>-->


<#--<div class="panel panel-primary">-->
<#--<div class="panel-heading">-->
<#--<h3 class="panel-title"><a href="#q-details-panels" data-toggle="collapse" class="btn">试题详情</a></h3>-->
<#--</div>-->
<#--<div class="panel-body">-->
<#--<div id="q-details-panels" class="collapse in">-->
<#--<div class="panel panel-default">-->
<#--<div class="panel-heading">-->
<#--<div class="panel-title">场景描述</div>-->
<#--</div>-->
<#--<div class="panel-body">-->
<#--<#if question.scenario??>-->
<#--<div>${question.scenario}</div>-->
<#--</#if>-->
<#--</div>-->
<#--</div>-->

<#--<div class="panel panel-default">-->
<#--<div class="panel-heading">-->
<#--<div class="panel-title">题干</div>-->
<#--</div>-->
<#--<div id="question-stem" class="panel-body">-->
<#--<#if question.stem??>-->
<#--<div>${question.stem}</div>-->
<#--</#if>-->
<#--</div>-->
<#--</div>-->

<#--<div class="panel panel-default">-->
<#--<div class="panel-heading">-->
<#--<div class="panel-title">备注</div>-->
<#--</div>-->
<#--<div id="question-remark" class="panel-body">-->
<#--<#if question.remark??>-->
<#--<div>${question.remark}</div>-->
<#--</#if>-->
<#--</div>-->
<#--</div>-->

<#--<div class="panel panel-default">-->
<#--<div class="panel-heading">-->
<#--<div class="panel-title">题目选项</div>-->
<#--</div>-->
<#--<div class="panel-body" style="padding-left: 15px">-->
<#--<#if choices??>-->
<#--<table class="table table-condensed table-striped table-hover table-bordered">-->
<#--<thead>-->
<#--<tr>-->
<#--<th>选项</th>-->
<#--<th align="center">内容</th>-->
<#--</tr>-->
<#--</thead>-->
<#--<tbody>-->
<#--<#list choices as choice>-->
<#--<tr>-->
<#--<td valign="top" style="width: 40px;">-->
<#--<#if choice.isCorrectAnswer>-->
<#--<label class="label-lg label-success">${choice.choiceLabel}. </label>-->
<#--<#else>-->
<#--<label class="label-lg label-default">${choice.choiceLabel}. </label>-->
<#--</#if>-->
<#--</td>-->
<#--<td>-->
<#--<div>${choice.content}</div>-->
<#--</td>-->
<#--</tr>-->
<#--</#list>-->
<#--</tbody>-->
<#--</table>-->
<#--</#if>-->
<#--</div>-->
<#--</div>-->
<#--</div>-->

<#--</div>-->
<#--</div>-->
<#--</div>-->
<#--<br/><br/>-->

<#--<div style="font-style:italic;margin-left:40px">-->
<#--CSTQB-->
<#--</div>-->
</body>
</html>
