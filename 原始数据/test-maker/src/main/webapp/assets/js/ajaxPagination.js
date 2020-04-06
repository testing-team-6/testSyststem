/**
 * Created with IntelliJ IDEA.
 * User: Jian-Min Gao []
 * Date: 2015/1/13
 * Time: 21:59
 */

var pageIndex = 1; //页索引
var where = " where 1=1";
$(function() {
    BindData();
// GetTotalCount(); //总记录条数
//GetPageCount(); //总页数绑定
//第一页按钮click事件
    $("#first").click(function() {
        pageIndex = 1;
        $("#lblCurent").text(1);
        BindData();
    });
//上一页按钮click事件
    $("#previous").click(function() {
        if (pageIndex != 1) {
            pageIndex--;
            $("#lblCurent").text(pageIndex);
        }
        BindData();
    });
//下一页按钮click事件
    $("#next").click(function() {
        var pageCount = parseInt($("#lblPageCount").text());
        if (pageIndex != pageCount) {
            pageIndex++;
            $("#lblCurent").text(pageIndex);
        }
        BindData();
    });
//最后一页按钮click事件
    $("#last").click(function() {
        var pageCount = parseInt($("#lblPageCount").text());
        pageIndex = pageCount;
        BindData();
    });
//查询
    $("#btnSearch").click(function() {
        where = " where 1=1";
        var csbh = $("#txtCSBH").val();
        if (csbh != null && csbh != NaN) {
            pageIndex = 1;
            where += " and csbh like '%" + csbh + "%'";
        }
        BindData();
    });
})
//AJAX方法取得数据并显示到页面上
function BindData() {
    $.ajax({
        type: "get", //使用get方法访问后台
        dataType: "json", //返回json格式的数据
        url: "../AjaxService/JgcsService.ashx", //要访问的后台地址
        data: { "pageIndex": pageIndex, "where": where }, //要发送的数据
        ajaxStart: function() { $("#load").show(); },
        complete: function() { $("#load").hide(); }, //AJAX请求完成时隐藏loading提示
        success: function(msg) {//msg为返回的数据，在这里做数据绑定
            var data = msg.table;
            if (data.length != 0) {
                var t = document.getElementById("tb_body"); //获取展示数据的表格
                while (t.rows.length != 0) {
                    t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除
                }
            }
            $.each(data, function(i, item) {
                $("#jgcsTable").append("<tr><td>" + item.CSBH + "</td><td>" + item.K + " </td><td>" + item.C +
                    " </td><td>" + item.S + " </td><td>" + item.DSB + " </td><td>" + item.TCBJ +
                    "</td><td>" + item.LHDCYL + " </td><td>" + item.BJJL + "</td><td>" + item.YLXS +
                    " </td><td>" + item.FCTH + " </td><td><a href='AjaxPaging.htm' target='blank'>" +
                    "<img src='../images/icon_06.gif' alt='查看详细信息'" +
                    "id='btnInsert'style='border-width:0px;' /></a></td></tr>");
            })
        },
        error: function() {
            var t = document.getElementById("tb_body"); //获取展示数据的表格
            while (t.rows.length != 0) {
                t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除
            }
            alert("加载数据失败");
        } //加载失败，请求错误处理
//ajaxStop:$("#load").hide()
    });
    GetTotalCount();
    GetPageCount();
    bindPager();
}
// 页脚属性设置
function bindPager() {
//填充分布控件信息
    var pageCount = parseInt($("#lblPageCount").text()); //总页数
    if (pageCount == 0) {
        document.getElementById("lblCurent").innerHTML = "0";
    }
    else {
        if (pageIndex > pageCount) {
            $("#lblCurent").text(1);
        }
        else {
            $("#lblCurent").text(pageIndex); //当前页
        }
    }
    document.getElementById("first").disabled = (pageIndex == 1 || $("#lblCurent").text() == "0") ? true : false;
    document.getElementById("previous").disabled = (pageIndex <= 1 || $("#lblCurent").text() == "0") ? true : false;
    document.getElementById("next").disabled = (pageIndex >= pageCount) ? true : false;
    document.getElementById("last").disabled = (pageIndex == pageCount || $("#lblCurent").text() == "0") ? true : false;
}
//AJAX方法取得总页数
function GetPageCount() {
    var pageCount;
    $.ajax({
        type: "get",
        dataType: "text",
        url: "../AjaxService/JgcsService.ashx",
        data: { "wherePageCount": where }, //"wherePageCount" + where,个人建议不用这种方式
        async: false,
        success: function(msg) {
            document.getElementById("lblPageCount").innerHTML = msg;
        }
    });
}
//AJAX方法取得记录总数
function GetTotalCount() {
    var pageCount;
    $.ajax({
        type: "get",
        dataType: "text",
        url: "../AjaxService/JgcsService.ashx",
        data: { "whereCount": where },
        async: false,
        success: function(msg) {
            document.getElementById("lblToatl").innerHTML = msg;
        }
    });
}