<%--suppress ALL --%>
<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="/document/head.jsp" />
    <style>
        .ff-center{
            margin-top:15px;
        }
    </style>
</head>
<body>
<div id="wrapper">
    <!-- Navigation -->
    <jsp:include page="/document/nav.jsp" />

    <div id="page-wrapper">

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
<script>
    $(document).ready(function () {
        $("#page-wrapper").load("/document/home.jsp");
    });
</script>
</body>
</html>
