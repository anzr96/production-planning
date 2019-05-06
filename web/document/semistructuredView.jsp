<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  ProductInTime: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
    <div class="col-md-12 col-sm-12">
        <h1 class="page-header rtl">نمایش نیمه ساخته ها</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-4">
                        <p>انتخاب بخش مورد نظر</p>
                        <select class="form-control" multiple name="section" onchange="getGroups(event)"></select>
                    </div>
                    <div class="col-md-4">
                        <p>انتخاب گروه مورد نظر</p>
                        <select class="form-control" multiple name="group" disabled onchange="getSemis(event)"></select>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <table id="table" width="100%" class="table table-responsive table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>کد قسمت</th>
                        <th>نام قسمت</th>
                        <th>کد گروه</th>
                        <th>نام گروه</th>
                        <th>کد نیمه ساخته</th>
                        <th>نام نیمه ساخته</th>
                        <th>مقدار نیمه ساخته</th>
                        <th>واحد نیمه ساخته</th>
                    </tr>
                    </thead>
                    <tbody></tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        $.ajax("/document/semistructured/getSection.do",{
            dataType:"json",
            type:"get"
        }).done(function (data) {
            for (let obj of data) {
                var option = document.createElement("option");
                $(option).val(obj.code);
                $(option).text(obj.code + " : " + obj.name);
                $("#page-wrapper").find("select[name='section']").append(option);
            }
            refreshSelect($("#page-wrapper").find("select[name='section']"), "لطفا گزینه مورد نظر را انتخاب کنید");
        }).fail(function (data) {
            errors(data);
        })
    })
    function getGroups(event) {
        var sectionArray = $(event.target).val();
        $("#page-wrapper").find("select[name='group']").prop("disabled", true);

        $.ajax("/document/semistructured/getGroup.do", {
            data:{sections:JSON.stringify(sectionArray)},
            dataType:"json",
            type:"post"
        }).done(function (data) {
            $("#page-wrapper").find("select[name='group']").children().remove();
            for (let obj of data) {
                var optgroup = document.createElement("optgroup");
                $(optgroup).attr("label", obj.code + " : " + obj.name);
                for (let obj2 of obj.groups) {
                    var option = document.createElement("option");
                    $(option).val(obj2.code);
                    $(option).text(obj2.code + " : " + obj2.name);
                    $(option).attr("parent", obj.code);
                    $(optgroup).append(option);
                }
                $("#page-wrapper").find("select[name='group']").append(optgroup);
            }
            refreshSelect($("#page-wrapper").find("select[name='group']"), "لطفا گزینه مورد نظر را انتخاب کنید");
            $("#page-wrapper").find("select[name='group']").prop("disabled", false);
        }).fail(function (data) {
            errors(data);
        })

    }
    function getSemis(event) {
        var opArray = $(event.target).find(":selected");
        var optgroups = [];
        for (let obj of opArray) {
            var b = false;
            for (let obj2 of optgroups) {
                if ($(obj).attr("parent") == obj2.code){
                    var option = new Object();
                    option.code = $(obj).val();
                    obj2.options.push(option);
                    b = true;
                }
            }
            if (!b){
                var optgroup = new Object();
                optgroup.code = $(obj).attr("parent");
                var option = new Object();
                option.code = $(obj).val();
                optgroup.options = [];
                optgroup.options.push(option);
                optgroups.push(optgroup);
            }
        }
        $('#table').DataTable().destroy();

        $.ajax("/document/semistructured/getSemi.do",{
            data:{groups:JSON.stringify(optgroups)},
            dataType:"json",
            type:"post"
        }).done(function (data) {
            $("#table").find("tbody").children().remove();
            for (let obj of data) {
                for (let obj2 of obj.groups) {
                    for (let obj3 of obj2.semis) {
                        var tr = document.createElement("tr");
                        var td = document.createElement("td");
                        $(td).text(obj.code);
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(obj.name);
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(obj2.code);
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(obj2.name);
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(obj3.code);
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(obj3.name);
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(obj3.total);
                        if (obj3.color !== undefined){
                            $(td).css("color", obj3.color);
                        }
                        $(tr).append(td);

                        td = document.createElement("td");
                        $(td).text(obj3.unit);
                        $(tr).append(td);

                        $("#table").find("tbody").append(tr);
                    }
                }
            }
            createDataTable($("#table"));
        }).fail(function (data) {
            errors(data);
        })
    }
</script>
