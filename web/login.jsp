<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 1/24/17
  Time: 12:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="/document/head.jsp" />
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-pull-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">لطفا وارد شوید</h3>
                </div>
                <div class="panel-body">
                    <form id="form">
                        <fieldset>
                            <input name="login" value="login" hidden/>
                            <div class="form-group">
                                <label>نام کاربری</label>
                                <input id="username" class="form-control"  placeholder="نام کاربری" name="username" onkeypress="return enter(event)" autofocus required/>
                            </div>
                            <div class="form-group">
                                <label>رمز عبور</label>
                                <input id="password" class="form-control" placeholder="رمز عبور" name="password" type="password" onkeypress="return enter(event)" required/>
                            </div>
                            <div class="form-group">
                                <label> مرا به خاطر بسپار</label>
                                <input id="rememberMe" name="rememberMe" type="checkbox"/>
                            </div>
                            <br/>
                            <!-- Change this to a button or input when using this as a form -->
                            <div class="form-group">
                                <button class="btn btn-lg btn-success btn-block" type="button" onclick="encrypt()">ورود</button>
                            </div>
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    function enter(e) {
        if (e.keyCode == 13) {
            encrypt();
            return false;
        }
    }
    function encrypt() {
        var user = $("#username").val(), p = md5($("#password").val());
        $.ajax("/Authentication/login.do",{
            data:{username:user, password:p, rememberMe:$('#rememberMe').is(":checked")},
            type:"POST"
        }).done(function () {
            window.location.replace("/document/MainPage.jsp");
        }).fail(function (data) {
            if (data.status == 404){
                alertify.error("نام کاربری یا رمز عبور اشتباه است");
            }else if (data.status == 400){
                alertify.error("کاربر متصل است");
            }else if (data.status == 500){
                alertify.error("خطا در سرور");
            };
        });
    }
</script>
</body>

</html>
