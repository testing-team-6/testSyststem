<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#if ctx??>
    <link rel="stylesheet" href="${ctx}/assets/css/app.min.css"/>
<#else>
    <link rel="stylesheet" href="D:/eclipse/workspace-mine/test-maker/src/main/webapp/assets/css/app.min.css"/>
</#if>

</head>
<body class="container">
<h3>${user.fullName}，您好：</h3>

<div>
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
</div>
<h2>题目详情</h2>

<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title"><a href="#q-attr-table" data-toggle="collapse" class="btn">题目属性</a></h3>
        </div>
        <div class="panel-body">
            <div id="q-attr-table" class="collapse in">
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <td class="active"><label>ID</label></td>
                        <td>${question.id}</td>
                        <td class="active"><label>知识点</label></td>
                        <td colspan="3">
                        <#if question.knowledgePoint??>
                            <p>${question.knowledgePoint.number} (${question.knowledgePoint.kLevel}
                                ) ${question.knowledgePoint.title}</p>
                        <#else>
                            <p>暂无知识点信息</p>
                        </#if>
                        </td>
                    </tr>
                    <tr>
                        <td class="active"><label>状态</label></td>
                        <td>${question.status.name}</td>
                        <td class="active"><label>类型</label></td>
                        <td>${question.type.name}</td>
                        <td class="active"><label>语言</label></td>
                        <td>${question.language.name}</td>
                    </tr>
                    <tr>
                        <td class="active"><label>难度</label></td>
                        <td>${question.difficulty}</td>
                        <td class="active"><label>分值</label></td>
                        <td>
                        ${question.score!""}</td>
                        <td class="active"><label>K值</label></td>
                        <td>
                        <#if question.knowledgePoint??>
                                ${question.knowledgePoint.kLevel!""}
                            </#if>
                        </td>
                    </tr>

                    <#--<tr>-->
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
                    <#--</tr>-->

                    <tr>
                        <td class="active"><label>出题期限</label></td>
                        <td colspan="3">
                                        <span>${question.authoringStartDate?date?string.long} &ndash;
                                        ${question.authoringFinishDate?date?string.long}
                                        </span>
                        </td>
                        <td class="active"><label>评审期限</label></td>
                        <td colspan="3">
                                        <span>${question.reviewingStartDate?date?string.long} &ndash;
                                        ${question.reviewingFinishDate?date?string.long}
                                        </span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title"><a href="#q-details-panels" data-toggle="collapse" class="btn">试题详情</a></h3>
        </div>
        <div class="panel-body">
            <div id="q-details-panels" class="collapse in">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">场景描述</div>
                    </div>
                    <div class="panel-body">
                    <#if question.scenario??>
                        <div>${question.scenario}</div>
                    </#if>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">题干</div>
                    </div>
                    <div id="question-stem" class="panel-body">
                    <#if question.stem??>
                        <div>${question.stem}</div>
                    </#if>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">备注</div>
                    </div>
                    <div id="question-remark" class="panel-body">
                    <#if question.remark??>
                        <div>${question.remark}</div>
                    </#if>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="panel-title">题目选项</div>
                    </div>
                    <div class="panel-body" style="padding-left: 15px">
                    <#if choices??>
                        <table class="table table-condensed table-striped table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>选项</th>
                                <th align="center">内容</th>
                            </tr>
                            </thead>
                            <tbody>
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
                    </#if>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<br/><br/>

<div style="font-style:italic;margin-left:40px">
    CSTQB
</div>
</body>
</html>
