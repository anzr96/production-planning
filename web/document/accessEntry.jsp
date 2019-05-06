<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 2/5/17
  Time: 8:04 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <h4 class="page-title">ایجاد دسترسی</h4>
    </div>
</div>
<br/>
<div class="row">
    <div class="col-md-12">
        <form id="form">
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>کد دسترسی</p>
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="code" class="control-group" placeholder="123"/>
                        </div>
                        <div class="col-md-3">
                            <p>نام دسترسی</p>
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="name" class="control-group" placeholder="name"/>
                        </div>
                        <div class="col-md-3">
                            <p>آدرس دسترسی</p>
                        </div>
                        <div class="col-md-3">
                            <input type="text" name="address" class="control-group" placeholder="/example/example.do"/>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-default" onclick="submitAccess()">تایید</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    function submitAccess() {
        $.ajax("/document/access/add.do", {
            data:$("#form").serialize(),
            type:"POST"
        }).done(function () {
            alertify.success("دسترسی ایجاد شد");
            $("#form").find(":input").val("");
        }).fail(function (data) {
            errors(data);
        });
    }
</script>

