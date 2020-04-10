<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: wushuang
  Date: 2015/2/4
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>success</title>
</head>
<body>
  <p>上传成功</p>
  <p>文件标题：<s:property value="'/uploadFiles/' + uploadFileName" /></p>
</body>
</html>
