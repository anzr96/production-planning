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
</head>
<body>
<div id="wrapper">
    <!-- Navigation -->
    <div id="page-wrapper">
        <div class="row">
            <div class="col-md-12">
                <form id="form">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 class="panel-title">ساخت ادمین</h4>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-3">
                                    <p>نام</p>
                                </div>
                                <div class="col-md-3">
                                    <input id="name" type="text" class="form-group" name="name"/>
                                </div>
                            </div>
                            <br/>
                            <div class="row">
                                <div class="col-md-3">
                                    <p>نام کاربری</p>
                                </div>
                                <div class="col-md-3">
                                    <input id="username" type="text" class="form-group" name="username"/>
                                </div>
                                <div class="col-md-3">
                                    <p>رمز عبور</p>
                                </div>
                                <div class="col-md-3">
                                    <input id="password" type="password" class="form-group" name="password"/>
                                </div>
                            </div>
                            <br/>
                            <div class="row">
                                <div class="col-md-3">
                                    <button type="button" class="btn btn-outline btn-primary" onclick="createUser()">ثبت</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<script>
    function createUser() {
        alertify.prompt('Input (text):').set('type', 'password');
        alertify.prompt( 'Admin authentication', 'please enter password', ''
                , function(evt, value) {
                    $.ajax("Authentication/create.do",{
                        data:{P:md5(value), username:$("#username").val(), name:$("#name").val(), password:md5($("#password").val()), role:$("#role").val()},
                        type:"POST"
                    }).done(function (data) {
                        alertify.success("کاربر جدید ثبت گردید");
                    }).fail(function (data) {
                        errors(data);
                    });
                }
                , function() { alertify.error('Cancel') });
    }
</script>
</body>
</html>
