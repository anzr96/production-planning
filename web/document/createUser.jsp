<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<br/>
<div class="row">
    <div class="col-md-12">
        <form id="form">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">کاربر جدید</h4>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>نام</p>
                        </div>
                        <div class="col-md-3">
                            <input type="text" class="form-group" name="name"/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>نام کاربری</p>
                        </div>
                        <div class="col-md-3">
                            <input type="text" class="form-group" name="username"/>
                        </div>
                        <div class="col-md-3">
                            <p>رمز عبور</p>
                        </div>
                        <div class="col-md-3">
                            <input type="password" class="form-group" name="password"/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>دسترسی</p>
                        </div>
                        <div class="col-md-3">
                            <select id="role" name="role" class="form-control">
                            </select>
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

<script>
    $(document).ready(ajaxLoad());
    function ajaxLoad() {
        $.ajax("/document/role/get.do", {
            type:"GET",
            dataType:"JSON"
        }).done(function (data) {
            $("#role").children().remove();
            for (var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].name);
                $("#role").append(option);
            }
        }).fail(function (data) {
            errors(data);
        });
    }
    function createUser() {
        var data = $("#form").serialize();
        $.post("/document/user/create.do", data).done(function (data) {
            alertify.success("کاربر جدید ثبت گردید");
        }).fail(function (data) {
            errors(data);
        });
    }
</script>
