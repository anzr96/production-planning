<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 2/18/17
  Time: 8:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <h1 class="page-header">کشش پذیری</h1>
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
                            <p>تعداد شیفت در روز</p>
                        </div>
                        <div class="col-md-3">
                            <input type="number" class="form-control" name="shift"/>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-12">
                            <table width="100%" class="table table-striped table-bordered table-hover text-center">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>کد محصول</th>
                                    <th>نام محصول</th>
                                    <th>درصد تولید</th>
                                </tr>
                                </thead>
                                <tbody id="tbody">
                                <tr>
                                    <td>
                                        <a type="button" value="Delete" onclick="deleteRow(event)" style="cursor: pointer"><i class="fa fa-trash-o fa-fw"></i></a>
                                        <br />
                                        <br />
                                        <a type="button" value="Add" onclick="addRow(event)" style="cursor: pointer"><i class="fa fa-plus fa-fw"></i></a>
                                    </td>
                                    <td>
                                        <select name="code" class="form-control" onchange="set(event)">
                                            <option value="">کد محصول را انتخاب کنید</option>
                                        </select>
                                    </td>
                                    <td>
                                        <select class="form-control" onchange="set(event)">
                                            <option value="">نام محصول را انتخاب کنید</option>
                                        </select>
                                    </td>
                                    <td>
                                        <div class="form-group input-group">
                                            <span class="input-group-addon">%</span>
                                            <input min="0" step="any" type="number" name="percent" class="form-control"/>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-default btn-outline" onclick="ajaxGet(event)">تایید</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<br/>
<div class="row" id="result" hidden>
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>نتایج</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-3">
                        <p>تعداد روزهای تولید :</p>
                    </div>
                    <div class="col-md-3">
                        <p id="totalDate"></p>
                    </div>
                </div>
                <br/>
                <div class="row">
                    <div class="col-md-12">
                        <table width="100%" class="table table-striped table-bordered table-hover text-center">
                            <thead>
                            <tr>
                                <th>کد محصول</th>
                                <th>نام محصول</th>
                                <th>تعداد تولیدی</th>
                            </tr>
                            </thead>
                            <tbody id="tbodyResult">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    var d;
    $(document).ready(function () {
        ajaxLoad();
    });
    function ajaxLoad() {
        $.ajax("/document/product/ajaxLoad.do",{
            type:"GET",
            dataType:"JSON"
        }).done(function (data) {
            d = data;
            var code = $($("#tbody").children().first().children()[1]).children().first();
            $(code).children().remove();
            var name = $($("#tbody").children().first().children()[2]).children().first();
            $(name).children().remove();
            var option = document.createElement("option");
            $(option).val("");
            $(option).text("کد محصول را انتخاب کنید");
            $(code).append(option);
            var option = document.createElement("option");
            $(option).val("");
            $(option).text("نام محصول را انتخاب کنید");
            $(name).append(option);
            for (var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].code);
                $(code).append(option);

                option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].size + data[i].design + data[i].pr);
                $(name).append(option);
            }
        }).fail(function (data) {
            errors(data);
        });
    }
    function addRow(event) {
        var table = document.getElementById('tbody');
        var rowCount = table.rows.length;
        var row = table.insertRow(rowCount);

        var newRow = row.insertCell(0);
        var i = $(document.createElement("i"));
        $(i).addClass("fa fa-trash-o fa-fw");
        var a = $(document.createElement("a"));
        $(a).attr("type","button");
        $(a).css("cursor", "pointer");
        $(a).val("Delete");
        $(a).on("click", function (event) {
            deleteRow(event);
        });
        $(a).append(i);
        $(newRow).append(a);
        $(newRow).append("<br /><br />");
        var i = $(document.createElement("i"));
        $(i).addClass("fa fa-plus fa-fw");
        var a = $(document.createElement("a"));
        $(a).attr("type","button");
        $(a).css("cursor", "pointer");
        $(a).val("Add");
        $(a).on("click", function (event) {
            addRow(event);
        });
        $(a).append(i);
        $(newRow).append(a);

        var newRow = row.insertCell(1);
        var select = $(document.createElement("select"));
        $(select).addClass("form-control");
        $(select).attr("name","code");
        $(select).on("change", function (event) {
            set(event);
        });
        $(select).append("<option value=''>کد محصول را انتخاب کنید</option>");
        for (var i = 0; i < d.length; i++){
            var option = document.createElement("option");
            $(option).val(d[i].code);
            $(option).text(d[i].code);
            $(select).append(option);
        }
        $(newRow).append(select);

        var newRow = row.insertCell(2);
        var select = $(document.createElement("select"));
        $(select).addClass("form-control");
        $(select).on("change", function (event) {
            set(event);
        });
        $(select).append("<option value=''>نام محصول را انتخاب کنید</option>");
        for (var i = 0; i < d.length; i++){
            option = document.createElement("option");
            $(option).val(d[i].code);
            $(option).text(d[i].size + d[i].design + d[i].pr);
            $(select).append(option);
        }
        $(newRow).append(select);

        var newRow = row.insertCell(3);
        var div = document.createElement("div");
        $(div).addClass("form-group input-group");
        $(div).append("<input step='any' type='number' name='percent' class='form-control'/>");
        $(div).append('<span class="input-group-addon">%</span>');
        $(newRow).append(div);

        $(event.target).remove();
    }
    function deleteRow(event) {
        var table = document.getElementById("tbody");
        var rowCount = table.rows.length;
        if (rowCount > 1) {
            if ($(event.target).parent().parent().children().last().val() == "Add"){
                var i = $(document.createElement("i"));
                $(i).addClass("fa fa-plus fa-fw");
                var a = $(document.createElement("a"));
                $(a).attr("type","button");
                $(a).css("cursor", "pointer");
                $(a).val("Add");
                $(a).on("click", function (event) {
                    addRow(event);
                });
                $(a).append(i);
                $(event.target).parent().parent().parent().prev().children().first().append(a);
            }
            $(event.target).parent().parent().parent().remove();
        }
        else {
            alertify.error("حداقل یک ردیف باید باقی بماند")
        }
    }
    function set(event) {
        $($(event.target).parent().parent().children()[1]).children().first().val($(event.target).val());
        $($(event.target).parent().parent().children()[2]).children().first().val($(event.target).val());
    }
    function ajaxGet(event) {
        $("#result").attr("hidden", true);
        $.ajax("/document/elasticity/ajaxGet.do",{
            data:$("#form").serialize(),
            type:"POST",
            dataType:"JSON"
        }).done(function (data) {
            createResult(data)
        }).fail(function (data) {
            errors(data);
        });
    }
    function createResult(data) {
        $("#tbodyResult").children().remove();
        $("#totalDate").text(data.d);
        var p = data.p;
        for(var i = 0; i < p.length; i++){
            var tr = document.createElement("tr");

            var td = document.createElement("td");
            $(td).text(p[i].c);
            $(tr).append(td);

            var td = document.createElement("td");
            $(td).text(p[i].n);
            $(tr).append(td);

            var td = document.createElement("td");
            $(td).text(p[i].t);
            $(tr).append(td);

            $("#tbodyResult").append(tr);
        }
        $("#result").attr("hidden", false);
    }
</script>
