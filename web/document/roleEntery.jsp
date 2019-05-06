<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <h4 class="page-title">ایجاد سطح کاربری</h4>
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
                            <p>کد سطح</p>
                        </div>
                        <div class="col-md-3">
                            <input id="code" type="text" name="code" class="control-group" placeholder="123"/>
                        </div>
                        <div class="col-md-3">
                            <p>نام سطح</p>
                        </div>
                        <div class="col-md-3">
                            <input id="name" type="text" name="name" class="control-group" placeholder="name"/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-12">
                            <table width="100%" class="table table-striped table-hover table-bordered table-responsive">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>کد دسترسی</th>
                                    <th>نام دسترسی</th>
                                    <th>آدرس دسترسی</th>
                                </tr>
                                </thead>
                                <tbody id="tbodyAccess">

                                </tbody>
                            </table>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-12">
                            <table width="100%" class="table table-striped table-hover table-bordered table-responsive">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>کد دسترسی پیام</th>
                                    <th>نام دسترسی پیام</th>
                                </tr>
                                </thead>
                                <tbody id="tbodyEvent">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-default" onclick="submitForm()">تایید</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script>
    $(document).ready(function () {
        ajaxLoad();
        ajaxEventLoad();
    });
    function ajaxLoad() {
        $.ajax("/document/access/get.do", {
            type:"GET",
            dataType:"JSON"
        }).done(function (data) {
            $("#tbodyAccess").children().remove();
            for (var i = 0; i < data.length; i++){
                var tr = document.createElement("tr");

                var td = document.createElement("td");
                var input = document.createElement("input");
                $(input).attr("type", "checkbox");
                $(input).attr("name", "checkbox");
                $(td).append(input);

                input = document.createElement("input");
                $(input).attr("type", "number");
                $(input).attr("name", "codeAccess");
                $(input).attr("hidden", true);
                $(input).val(data[i].code);
                $(td).append(input);

                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(data[i].code);
                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(data[i].name);
                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(data[i].address);
                $(tr).append(td);

                $("#tbodyAccess").append(tr);
            }
        }).fail(function (data) {
            errors(data);
        });
    }
    function ajaxEventLoad() {
        $.ajax("/document/eventAccessibility/get.do", {
            type:"GET",
            dataType:"JSON"
        }).done(function (data) {
            $("#tbodyEvent").children().remove();
            for (var i = 0; i < data.length; i++){
                var tr = document.createElement("tr");

                var td = document.createElement("td");
                var input = document.createElement("input");
                $(input).attr("type", "checkbox");
                $(input).attr("name", "checkboxEvent");
                $(td).append(input);

                input = document.createElement("input");
                $(input).attr("type", "number");
                $(input).attr("name", "codeEvent");
                $(input).attr("hidden", true);
                $(input).val(data[i].code);
                $(td).append(input);

                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(data[i].code);
                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(data[i].name);
                $(tr).append(td);

                $("#tbodyEvent").append(tr);
            }
        }).fail(function (data) {
            errors(data);
        });
    }
    function submitForm() {
        var accessCode = [];
        var eventCode = [];
        var code = $("#code").val();
        var name = $("#name").val();
        var table = $("#tbodyAccess").children();
        for(var i = 0; i < table.length; i++){
            if ($(table[i]).children().first().children().first().is(":checked")){
                accessCode.push($(table[i]).children().first().children().last().val());
            }
        }
        table = $("#tbodyEvent").children();
        for(var i = 0; i < table.length; i++){
            if ($(table[i]).children().first().children().first().is(":checked")){
                eventCode.push($(table[i]).children().first().children().last().val());
            }
        }
        $.ajax("/document/role/add.do",{
            data:{code:code, name:name, accessCode:accessCode, eventCode:eventCode},
            type:"POST"
        }).done(function () {
            alertify.success("سطح دسترسی ایجاد شد");
        }).fail(function (data) {
            errors(data);
        });
    }
</script>
