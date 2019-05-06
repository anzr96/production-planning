<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 2/7/17
  Time: 4:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
    <div class="col-md-12">
        <h2 class="page-title">نمایش pm دستگاه ها</h2>
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
                            <p>تاریخ شروع</p>
                        </div>
                        <div class="col-md-3">
                            <input id="datepickerStart" type="text" name="start" class="control-form"/>
                        </div>
                        <div class="col-md-3">
                            <p>تاریخ پایان</p>
                        </div>
                        <div class="col-md-3">
                            <input id="datepickerEnd" type="text" name="end" class="control-form"/>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-3">
                            <button id="button" type="button" class="btn btn-default btn-outline" onclick="ajaxLoad()">تایید</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<br/>
<div class="row" id="rowTable" hidden>
    <div class="col-md-12">
        <table id="table" width="100%" class="table table-striped table-bordered table-hover text-center">
            <thead>
            <tr>
                <td></td>
                <td>کد دستگاه</td>
                <td>نام دستگاه</td>
                <td>تاریخ pm</td>
            </tr>
            </thead>
            <tbody id="tbody">

            </tbody>
        </table>
    </div>
</div>
<script>
    $(document).ready(function () {
        $("#datepickerStart").datepicker({
            format: "mm-dd-yyyy",
            minViewMode: 0,
            todayBtn: true,
            clearBtn: true,
            autoclose: true,
            todayHighlight: true
        });
        $("#datepickerEnd").datepicker({
            format: "mm-dd-yyyy",
            minViewMode: 0,
            todayBtn: true,
            clearBtn: true,
            autoclose: true,
            todayHighlight: true
        });
    });
    function ajaxLoad() {
        $.ajax("/document/pm/get.do", {
            data:$("#form").serialize(),
            type:"POST",
            dataType:"JSON"
        }).done(function (data) {
            $("#tbody").children().remove();

            for(var i = 0; i< data.length; i++){
                var tr = document.createElement("tr");

                var td = document.createElement("td");
                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(data[i].code);
                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(data[i].name);
                $(tr).append(td);

                td = document.createElement("td");
                $(td).text(data[i].date);
                $(tr).append(td);

                $("#tbody").append(tr);
            }

            $("#table").DataTable({});

            $("#rowTable").attr("hidden", false);
        }).fail(function (data) {
            errors(data);
        });
    }
</script>
