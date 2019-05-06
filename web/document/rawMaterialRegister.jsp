<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  ProductInTime: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<br/>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">مشخصات ماده اولیه</h3>
            </div>
            <div class="panel-body">
                <form id="form">
                    <div class="row">
                        <div class="col-md-3">
                            <p> کد ماده اولیه : </p>
                            <input type="text" name="rawCode" placeholder="کد ماده اولیه" autofocus>
                        </div>
                        <div class="col-md-3">
                            <p> نام ماده اولیه : </p>
                            <input type="text" name="rawName" placeholder="نام ماده اولیه">
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p> نام شرکت تولید کننده : </p>
                            <input type="text" name="companyName" placeholder="نام شرکت">
                        </div>
                        <div class="col-md-3">
                            <p> داخلی/خارجی : </p>
                            <select name="inneroutter" class="form-control">
                                <option value="0">داخلی</option>
                                <option value="1">خارجی</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <p> نقطه سفارش : </p>
                            <input type="number" name="orderpoint" placeholder="نقطه سفارش">
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-8">
                            <p> توضیحات : </p>
                            <textarea name="description" class="form-control" maxlength="255"></textarea>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-2">
                            <button class="btn btn-default" type="button" onclick="ajax()">تایید</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    function ajax() {
        var data = $("#form").serialize();
        $.ajax("/document/rawmaterial/register.do",{
            type:"POST",
            data:data
        }).done(function () {
            $("#form").find(":input, select").val("");
            alertify.success("ماده اولیه جدید اضافه شد")
        }).fail(function (data) {
            errors(data);
        });
    }
</script>
