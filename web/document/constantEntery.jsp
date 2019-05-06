<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 2/11/17
  Time: 3:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <form id="form">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4 class="panel-title">وارد کردن ثوابت هر سال</h4>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>تاریخ</p>
                        </div>
                        <div class="col-md-3">
                            <input id="datepicker" name="d" class="control-form" type="text"/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>تعداد روزهای کاری سال مورد نظر</p>
                        </div>
                        <div class="col-md-3">
                            <input id="workingdate" name="w" class="control-form" type="number"/>
                        </div>
                        <div class="col-md-3">
                            <p>مقدار ساعت هر شیفت</p>
                        </div>
                        <div class="col-md-3">
                            <input step="any" id="shift" name="s" class="control-form" type="number"/>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <button class="btn btn-outline btn-default" type="button" onclick="ajaxSubmit()">تایید</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#datepicker").datepicker({
            format: "yyyy",
            minViewMode: 2,
            clearBtn: true,
            todayBtn: true
        });
    });
    function ajaxSubmit() {
        $.ajax("/document/constant/register.do", {
            data:$("#form").serialize(),
            type:"POST"
        }).done(function (data) {
            $("#form").find(":input, select").val("");
            alertify.success("اطلاعات ثبت شدند")
        }).fail(function (data) {
            errors(data)
        })
    }
</script>
