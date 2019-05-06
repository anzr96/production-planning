<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 1/25/17
  Time: 6:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<br/>
<div class="row">
    <div class="col-md-12">
        <form id="form">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4 class="panel-title">پروفایل</h4>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>نام</p>
                        </div>
                        <div class="col-md-3">
                            <p id="name"></p>
                        </div>
                        <div class="col-md-3">
                            <p>دسترسی</p>
                        </div>
                        <div class="col-md-3">
                            <p id="role"></p>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>نام کاربری</p>
                        </div>
                        <div class="col-md-3">
                            <p id="username"></p>
                        </div>
                    </div>
                    <hr/>
                    <div class="row">
                        <div class="col-md-3">
                            <h4>تغییر رمز عبور</h4>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>رمز عبور فعلی</p>
                        </div>
                        <div class="col-md-3">
                            <input id="password" name="password" class="form-control" type="password"/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>رمز عبور جدید</p>
                        </div>
                        <div class="col-md-3">
                            <input id="newpassword" name="newpassword" class="form-control" type="password"/>
                        </div>
                        <div class="col-md-3">
                            <p>تکرار رمز عبور جدید</p>
                        </div>
                        <div class="col-md-3">
                            <input id="repeatpassword" name="repeatpassword" class="form-control" type="password"/>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-default btn-outline" onclick="check()">تغییر رمز عبور</button>
                        </div>
                        <div class="col-md-pull-6 col-md-3">
                            <button type="button" class="btn btn-danger" onclick="remove()">حذف کاربر</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    $(document).ready(ajaxData());
    function ajaxData() {
        $.get("/document/user/data.do").done(function (data) {
            var json = JSON.parse(data);
            $("#name").text(json.name);
            $("#role").text(json.role);
            $("#username").text(json.username);
        });
    }
    function check() {
        var newpassword = $("#newpassword").val();
        var repeatpassword = $("#repeatpassword").val();
        if (newpassword != "" && repeatpassword != "" && $("#password").val() != ""){
            if (newpassword == repeatpassword && newpassword != $("#password").val()){
                alertify.confirm("تغییر رمز عبور", "ایا مطمئن هستید", function () {
                    ajaxChangePassword();
                }, function () {
                    alertify.error('تغییر صورت نگرفت');
                }).setting({
                    'labels': {ok:"تایید",cancel:"لغو"},
                    'closableByDimmer': true,
                    'closable': false,
                    'moveBounded': true,
                    'transition':'zoom'
                });
            }else {
                alertify.error("دوباره وارد کنید")
            }
        }else {
            alertify.error("فیلدها را پر کنید")
        }
    }
    function ajaxChangePassword() {
        var password = md5($("#password").val());
        var newpassword = md5($("#newpassword").val());
        var repeatpassword = md5($("#repeatpassword").val());
        $.ajax("/document/user/change.do",{
            data: {password:password,newpassword:newpassword,repeatpassword:repeatpassword},
            type:"POST"
        }).done(function () {
            window.location.replace("/logout.jsp");
        }).fail(function (data) {
            errors(data);
        });
    }
    function remove() {
        alertify.confirm("حذف کاربر", "ایا مطمئن هستید", function () {
            alertify.prompt("حذف کاربر", "لطفا رمز عبور خود را وارد نمایید", "", function (evt, value) {
                $.ajax("/document/user/delete.do",{
                    data:{password:md5(value)},
                    type:"POST"
                }).done(function () {
                    window.location.replace("/login.jsp");
                }).fail(function () {
                    alertify.error('کاربر حذف نشد');
                })
            }, function () {
                alertify.error('تغییر صورت نگرفت');
            });
        }, function () {
            alertify.error('تغییر صورت نگرفت');
        }).setting({
            'labels': {ok:"تایید",cancel:"لغو"},
            'closableByDimmer': true,
            'closable': false,
            'moveBounded': true,
            'transition':'zoom'
        });
    }
</script>