<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--Modal-->
<div class="modal fade" id="semiModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">سریال دستگاه ایستگاه قبل</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p> کد قسمت</p>
                                        <select name="msc" class="form-control" onchange="setMachineEnterSection(event)"></select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام قسمت</p>
                                        <select name="msn" class="form-control" onchange="setMachineEnterSection(event)"></select>
                                    </div>
                                </div>
                            </div>
                            <div class="row" hidden>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p> کد گروه دستگاه</p>
                                        <select name="mgc" class="form-control" onchange="setMachineEnter(event)"></select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام گروه دستگاه</p>
                                        <select name="mgn" class="form-control" onchange="setMachineEnter(event)"></select>
                                    </div>
                                </div>
                            </div>
                            <div class="row" hidden="true">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <p>کد خروجی دستگاه </p>
                                        <select name="mec" class="form-control" onchange="setMachineExit(event)"></select>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <p>نام خروجی دستگاه </p>
                                        <select name="men" class="form-control" onchange="setMachineExit(event)"></select>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <p>سریال نامبر</p>
                                        <input name="mes" class="form-control"/>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptEnter()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<div class="modal fade" id="rawModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">ماده اولیه</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <p>کد ماده اولیه </p>
                                        <select name="rc" class="form-control" onchange="setRawMaterial(event)"></select>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <p>نام ماده اولیه </p>
                                        <select name="rn" class="form-control" onchange="setRawMaterial(event)"></select>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <p>سریال نامبر </p>
                                        <input name="rs" class="form-control"/>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptRaw()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<!--/Modal-->
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">تحویل نیمه ساخته ها به انبار موقت</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-3">
                        <p>انتخاب قسمت</p>
                        <select name="section" class="form-control" onchange="getGroups(event)">
                        </select>
                    </div>
                    <div class="col-md-3">
                        <p>انتخاب گروه</p>
                        <select name="groupM" class="form-control" onchange="getSemis(event)">
                        </select>
                    </div>
                    <div class="col-md-3">
                        <p>انتخاب نیمه ساخته</p>
                        <select name="semi" class="form-control" onchange="getUnit(event)">
                        </select>
                    </div>
                    <div class="col-md-3">
                        <p>انتخاب ماشین</p>
                        <select name="machine" class="form-control" multiple>
                        </select>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-3">
                        <p>تاریخ تولید</p>
                        <input name="date" class="form-control"/>
                    </div>
                    <div class="col-md-2">
                        <p>مقدار</p>
                        <div class="form-group input-group">
                            <input name="total" class="form-control" placeholder="مقدار" type="number">
                            <span name="unit" class="input-group-addon"></span>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <p>شماره سریال</p>
                        <input name="batch" class="form-control"/>
                    </div>
                    <div class="col-md-1">
                        <p>شیفت</p>
                        <input type="number" name="shift" class="form-control" min="1" max="3"/>
                    </div>
                    <div class="col-md-1">
                        <p>گروه</p>
                        <input name="group" class="form-control"/>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <fieldset>
                        <legend>اطلاعات اپراتور</legend>
                        <div class="col-md-12">
                            <div class="col-md-1">
                                <p>حذف ردیف</p>
                                <a onclick="deleteRowOp(event)"><i class="fa fa-trash-o fa-fw"></i></a>
                            </div>
                            <div class="col-md-2">
                                <p>شماره پرسنلی اپراتور</p>
                                <input name="code" class="form-control"/>
                            </div>
                            <div class="col-md-2">
                                <p>نام اپراتور</p>
                                <input name="name" class="form-control"/>
                            </div>
                            <div class="col-md-2">
                                <p>وظیفه</p>
                                <input name="job" class="form-control"/>
                            </div>
                            <div class="col-md-5">
                                <p>توضیحات</p>
                                <textarea name="opDes" class="form-control" maxlength="255"></textarea>
                            </div>
                        </div>
                    </fieldset>
                </div>
                <div class="row">
                    <div class="col-md-3">
                        <button class="btn btn-outline btn-primary" onclick="newOp(event)"><i class="fa fa-plus"></i></button>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <table id="rawTable" class="table table-responsive table-striped table-bordered table-hover" width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th>کد ماده اولیه</th>
                                <th>نام ماده اولیه</th>
                                <th>شماره سریال</th>
                                <th>ویرایش</th>
                            </tr>
                            </thead>
                        </table>
                        <button class="btn btn-outline btn-primary" data-toggle="modal" href="#rawModal" onclick="newModal('rawModal')"><i class="fa fa-plus"></i></button>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <table id="semiTable" class="table table-responsive table-striped table-bordered table-hover" width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th></th>
                                <th>کد قسمت و گروه</th>
                                <th>نام قسمت و گروه</th>
                                <th>کد و نام نیمه ساخته</th>
                                <th>شماره سریال</th>
                                <th>ویرایش</th>
                            </tr>
                            </thead>
                        </table>
                        <button class="btn btn-outline btn-primary" data-toggle="modal" href="#semiModal" onclick="newModal('semiModal')"><i class="fa fa-plus"></i></button>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-8">
                        <p>توضیحات</p>
                        <textarea name="des" class="form-control" maxlength="255"></textarea>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-12">
                        <fieldset name="udf">
                            <legend>مشخصات اضافی</legend>
                        </fieldset>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-3">
                        <button type="button" class="btn btn-default" onclick="addFieldSectionRegister(this)">فیلد جدید</button>
                    </div>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-md-4">
                        <button class="btn btn-success" onclick="acceptSemiIn()">تایید</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    var listSections = null, listRawMaterials = null;
    var units = [], hashMapRaw = [];
    var semis = new Array(), raws = new Array();
    $(function () {
        $.ajax("/document/semistructured/getSection.do",{
            dataType:"json",
            type:"get"
        }).done(function (data) {
            $.ajax("/document/machine/listSection.do",{
                type:"GET",
                dataType:"json",
            }).done(function (data) {
                listSections = data;
            }).fail(function (data) {
                errors(data);
            });

            for (let obj of data) {
                var option = document.createElement("option");
                $(option).val(obj.code);
                $(option).text(obj.code + " : " + obj.name);
                $("#page-wrapper").find("select[name='section']").append(option);
            }
            refreshSelect($("#page-wrapper").find("select[name='section']"), "لطفا گزینه مورد نظر را انتخاب کنید");
        }).fail(function (data) {
            errors(data);
        });
        $('#page-wrapper').find("input[name='date']").pDatepicker({
            format: 'YYYY/MM/DD'
        });
    });
    function getGroups(event) {
        var sectionArray = [];
        sectionArray.push($(event.target).val());
        $("#page-wrapper").find("select[name='groupM']").prop("disabled", true);

        $.ajax("/document/semistructured/getGroup.do", {
            data:{sections:JSON.stringify(sectionArray)},
            dataType:"json",
            type:"post"
        }).done(function (data) {
            $("#page-wrapper").find("select[name='groupM']").children().remove();
            var option = document.createElement("option");
            $(option).val("");
            $(option).prop("selected", true);
            $(option).text("گروه مورد نظر را انتخاب کنید");
            $("#page-wrapper").find("select[name='groupM']").append(option);
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
                $("#page-wrapper").find("select[name='groupM']").append(optgroup);
            }
            refreshSelect($("#page-wrapper").find("select[name='groupM']"), "گروه مورد نظر را اتخاب کنید");
            $("#page-wrapper").find("select[name='groupM']").prop("disabled", false);
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
                    break;
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

        $.ajax("/document/semistructured/getSemi.do",{
            data:{groups:JSON.stringify(optgroups)},
            dataType:"json",
            type:"post"
        }).done(function (data) {
            units = [];
            $("#page-wrapper").find("select[name='semi']").children().remove();
            var option = document.createElement("option");
            $(option).val("");
            $(option).prop("selected", true);
            $(option).text("نیمه ساخته مورد نظر را انتخاب کنید");
            $("#page-wrapper").find("select[name='semi']").append(option);
            for (let obj of data) {
                for (let obj2 of obj.groups) {
                    for (let obj3 of obj2.semis) {
                        option = document.createElement("option");
                        $(option).val(obj3.code);
                        $(option).text(obj3.code + " : " + obj3.name);
                        units[obj3.code] = obj3.unit;
                        $("#page-wrapper").find("select[name='semi']").append(option);
                    }
                }
            }
            refreshSelect($("#page-wrapper").find("select[name='semi']"), "نیمه ساخته مورد نظر را انتخاب کنید");
        }).fail(function (data) {
            errors(data);
        });

        $.ajax("/document/machine/listMachines.do",{
            data:{groups:JSON.stringify(optgroups)},
            dataType:"json",
            type:"post"
        }).done(function (data) {
            $("#page-wrapper").find("select[name='machine']").children().remove();

            for (let obj of data) {
                for (let obj2 of obj.groups) {
                    for (let obj3 of obj2.machines) {
                        option = document.createElement("option");
                        $(option).val(obj3.code);
                        $(option).text(obj3.code + " : " + obj3.name);
                        $("#page-wrapper").find("select[name='machine']").append(option);
                    }
                }
            }
            refreshSelect($("#page-wrapper").find("select[name='machine']"), "دستگاه مورد نظر را انتخاب کنید");
        }).fail(function (data) {
            errors(data);
        });
    }
    function getUnit(event) {
        var code = $(event.target).val();
        $("#page-wrapper").find("span[name='unit']").text(units[code]);
    }
    function getDate() {
        var year = parseInt($("#page-wrapper").find("input[name='date']").val().substr(0,4));
        var month = parseInt($("#page-wrapper").find("input[name='date']").val().substr(5,2));
        var day = parseInt($("#page-wrapper").find("input[name='date']").val().substr(8,2));

        var d = new persianDate([year, month, day, 0, 0, 0, 0]).toDate();
        return d.getTime();
    }

    function ajaxGroupMachine(modal, sectionCode , value) {
        $.ajax("/document/machine/listGroup.do",{
            data:{code:sectionCode},
            type:"POST",
            dataType:"json",
            async:false
        }).done(function (data) {
            $("#" + modal).find("select[name='mgc']").children().remove();
            $("#" + modal).find("select[name='mgn']").children().remove();
            for(var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).text(data[i].code);
                $(option).val(data[i].code);
                $("#" + modal).find("select[name='mgc']").append(option);
                option = document.createElement("option");
                $(option).text(data[i].name);
                $(option).val(data[i].code);
                $("#" + modal).find("select[name='mgn']").append(option);
            }
            refreshSelect($("#" + modal).find("select[name='mgc']"), codeStmt);
            refreshSelect($("#" + modal).find("select[name='mgn']"), nameStmt);
            if (value !== undefined && value !== null){
                $("#" + modal).find("select[name='mgc']").val(value);
                $("#" + modal).find("select[name='mgn']").val(value);
            }
            $("#" + modal).find("select[name='mgc']").parent().parent().parent().prop("hidden", false);
            return true;
        }).fail(function (data) {
            errors(data);
            return false;
        });
    }
    function ajaxMachineProduct(modal, sectionCode, groupCode, value){
        $.ajax("/document/machine/listProductMachine.do",{
            data:{sectionCode:sectionCode, groupCode:groupCode},
            type:"POST",
            dataType:"json",
            async:false
        }).done(function (data) {
            $("#" + modal).find("select[name='mec']").children().remove();
            $("#" + modal).find("select[name='men']").children().remove();
            for(var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).text(data[i].code);
                $(option).val(data[i].code);
                $("#" + modal).find("select[name='mec']").append(option);
                option = document.createElement("option");
                $(option).text(data[i].name);
                $(option).val(data[i].code);
                $("#" + modal).find("select[name='men']").append(option);
            }
            refreshSelect($("#" + modal).find("select[name='mec']"), codeStmt);
            refreshSelect($("#" + modal).find("select[name='men']"), nameStmt);
            if (value !== undefined && value !== null){
                $("#" + modal).find("select[name='mec']").val(value);
                $("#" + modal).find("select[name='men']").val(value);
            }

            $("#" + modal).find("select[name='mec']").parent().parent().parent().prop("hidden", false);
            return true;
        }).fail(function (data) {
            errors(data);
            return false;
        });
    }

    function setMachineEnterSection(event) {
        $(event.target).parents("fieldset").find("select[name='msc']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='msn']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='msc']"), codeStmt);
        refreshSelect($(event.target).parents("fieldset").find("select[name='msn']"), nameStmt);

        refreshSelect($(event.target).parents("fieldset").find("select[name='mec']"), codeStmt, true);
        refreshSelect($(event.target).parents("fieldset").find("select[name='men']"), nameStmt, true);

        ajaxGroupMachine("semiModal", $(event.target).val());
    }
    function setMachineEnter(event) {
        $(event.target).parents("fieldset").find("select[name='mgc']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='mgn']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='mgc']"), codeStmt);
        refreshSelect($(event.target).parents("fieldset").find("select[name='mgn']"), nameStmt);

        ajaxMachineProduct("semiModal", $("#semiModal").find("select[name='msc']").val(), $(event.target).val())
    }
    function setMachineExit(event) {
        $(event.target).parents("fieldset").find("select[name='mec']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='men']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='mec']"), codeStmt,false,false);
        refreshSelect($(event.target).parents("fieldset").find("select[name='men']"), nameStmt,false,false);
    }
    function setRawMaterial(event) {
        $(event.target).parents("div[class='row']").find("select[name='rc']").val($(event.target).val());
        $(event.target).parents("div[class='row']").find("select[name='rn']").val($(event.target).val());

        refreshSelect($(event.target).parents("div[class='row']").find("select[name='rc']"), codeStmt);
        refreshSelect($(event.target).parents("div[class='row']").find("select[name='rn']"), nameStmt);
    }

    function enterMachineTable() {
        $("#semiTable").DataTable().destroy();
        $("#semiTable").find("tbody").children().remove();
        for (var i = 0; i < semis.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
             
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("value", semis[i]);
            $(a).on("click", function () {
                deleteRowEnter($(this).data("value"));
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semis[i].sectionCode + ":" + semis[i].groupCode);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semis[i].sectionName + ":" + semis[i].groupName);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semis[i].exitCode + ":" + semis[i].exitName);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semis[i].exitSerial);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
             
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", semis[i]);
            $(a).on("click", function () {
                var enterMachine = $(this).data("data");
                $("#semiModal").data("data", enterMachine);
                $("#semiModal").find('select[name="msc"]').val(enterMachine.sectionCode);
                $("#semiModal").find('select[name="msn"]').val(enterMachine.sectionCode);
                ajaxGroupMachine("semiModal", enterMachine.sectionCode, enterMachine.groupCode);
                ajaxMachineProduct("semiModal", enterMachine.sectionCode, enterMachine.groupCode, enterMachine.exitCode)
                $("#semiModal").find('input[name="mes"]').val(enterMachine.exitSerial);
                var selects = $("#semiModal").find('select');
                for (var i = 0; i < selects.length; i++){
                    refreshSelect(selects[i], "لطفا گزینه مورد نظر را انتخاب کنید", false, false);
                }

                addModal("semiModal");
                $("#semiModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#semiTable").find("tbody").append(tr);
        }
        createDataTable($("#semiTable"));
    }
    function rawTable() {
        $("#rawTable").DataTable().destroy();
        $("#rawTable").find("tbody").children().remove();
        for (var i = 0; i < raws.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
             
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("value", raws[i]);
            $(a).on("click", function () {
                deleteRowRaw($(this).data("value"));
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(raws[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(raws[i].name);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(raws[i].serial);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
             
            $(a).text("...");
            $(a).attr("cursor", "pointer");
            $(a).data("data", raws[i]);
            $(a).on("click", function () {
                var rawMaterial = $(this).data("data");
                $("#rawModal").data("data", rawMaterial);
                $("#rawModal").find('select[name="rc"]').val(rawMaterial.code);
                $("#rawModal").find('select[name="rn"]').val(rawMaterial.code);
                $("#rawModal").find('input[name="rs"]').val(rawMaterial.seiral);
                var selects = $("#rawModal").find('select');
                for (var i = 0; i < selects.length; i++){
                    refreshSelect($(selects[i]), "لطفا گزینه مورد نظر را انتخاب کنید");
                }

                addModal("rawModal");
                $("#rawModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#rawTable").find("tbody").append(tr);
        }
        createDataTable($("#rawTable"));
    }

    function deleteRowEnter(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                semis.splice(semis.indexOf(data), 1);
                enterMachineTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowRaw(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                raws.splice(raws.indexOf(data), 1);
                rawTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowOp(event) {
        $(event.target).parent().parent().parent().remove();
    }

    function acceptEnter() {
        if ($("#semiModal").data("data") != null){
            var enter = semis[semis.indexOf($("#semiModal").data("data"))];
            enter.sectionCode = $("#semiModal").find("select[name='msc']").val();
            enter.groupCode = $("#semiModal").find("select[name='mgc']").val();
            enter.sectionName = $("#semiModal").find("select[name='msn']").find(":selected").text();
            enter.groupName = $("#semiModal").find("select[name='mgn']").find(":selected").text();
            enter.exitCode = $("#semiModal").find("select[name='mec']").val();
            enter.exitName = $("#semiModal").find("select[name='men']").find(":selected").text();
            enter.exitSerial = $("#semiModal").find("input[name='mes']").val();
        }else{
            var enter = new Object();
            enter.sectionCode = $("#semiModal").find("select[name='msc']").val();
            enter.groupCode = $("#semiModal").find("select[name='mgc']").val();
            enter.sectionName = $("#semiModal").find("select[name='msn']").find(":selected").text();
            enter.groupName = $("#semiModal").find("select[name='mgn']").find(":selected").text();
            enter.exitCode = $("#semiModal").find("select[name='mec']").val();
            enter.exitName = $("#semiModal").find("select[name='men']").find(":selected").text();
            enter.exitSerial = $("#semiModal").find("input[name='mes']").val();
            semis.push(enter);
        }
        enterMachineTable();
        removeModal();
    }
    function acceptRaw() {
        if ($("#rawModal").data("data") != null){
            var rawMaterial = raws[raws.indexOf($("#rawModal").data("data"))];
            rawMaterial.code = $("#rawModal").find("select[name='rc']").val();
            rawMaterial.name = hashMapRaw[$("#rawModal").find("select[name='rc']").val()];
            rawMaterial.serial = $("#rawModal").find("input[name='rs']").val();
        }else{
            var rawMaterial = new Object();
            rawMaterial.code = $("#rawModal").find("select[name='rc']").val();
            rawMaterial.name = hashMapRaw[$("#rawModal").find("select[name='rc']").val()];
            rawMaterial.serial = $("#rawModal").find("input[name='rs']").val();
            raws.push(rawMaterial);
        }
        rawTable();
        removeModal();
    }
    function acceptSemiIn() {
        var semistructured = new Object();
        semistructured.section = $("#page-wrapper").find("select[name='section']").val();
        semistructured.groupM = $("#page-wrapper").find("select[name='groupM']").val();
        semistructured.semi = $("#page-wrapper").find("select[name='semi']").val();
        semistructured.machines = $("#page-wrapper").find("select[name='machine']").val();
        semistructured.date = getDate();
        semistructured.shift = $("#page-wrapper").find("input[name='shift']").val();
        semistructured.group = $("#page-wrapper").find("input[name='group']").val();
        semistructured.batch = $("#page-wrapper").find("input[name='batch']").val();
        semistructured.total = $("#page-wrapper").find("input[name='total']").val();
        if ($("#page-wrapper").find('input[name="code"]') != null){
            var operators = new Array();
            var codes = $("#page-wrapper").find('input[name="code"]');
            var names = $("#page-wrapper").find('input[name="name"]');
            var jobs = $("#page-wrapper").find('input[name="job"]');
            var opDeses = $("#page-wrapper").find('textarea[name="opDes"]');
            for (var i = 0; i < codes.length; i++){
                var operator = new Object();
                operator.code = $(codes[i]).val();
                operator.name = $(names[i]).val();
                if (jobs[i] !== undefined && jobs[i] !== null){
                    operator.job = $(jobs[i]).val();
                }
                if (opDeses[i] !== undefined && opDeses[i] !== null){
                    operator.des = $(opDeses[i]).val();
                }
                operators.push(operator);
            }
            semistructured.operators = operators;
        }
        semistructured.raws = raws;
        semistructured.semis = semis;
        semistructured.des = $("#page-wrapper").find("textarea[name='des']").val();
        if ($("#page-wrapper").find('input[name="fn"]') != null){
            var udfs = new Array();
            var names = $("#page-wrapper").find('input[name="fn"]');
            var types = $("#page-wrapper").find('select[name="ft"]');
            var values = $("#page-wrapper").find('input[name="fv"]');
            for (var i = 0; i < names.length; i++){
                var udf = new Object();
                udf.name = $(names[i]).val();
                udf.type = $(types[i]).val();
                udf.value = $(values[i]).val();
                udfs.push(udf);
            }
            semistructured.udf = udfs;
        }

        $.ajax("/document/semistructured/enter.do", {
            data:{data:JSON.stringify(semistructured)},
            type:"post"
        }).done(function (data) {
            alertify.success("اطلاعات ثبت شد");
            loadContent("/document/semistructuredEntery.jsp", false);
        }).fail(function (data) {
            errors(data);
        })
    }

    function newOp(event) {
        $(event.target).parent().parent().prev().find("fieldset").append('<div class="col-md-12">\n' +
            '                            <div class="col-md-1">\n' +
            '                                <p></p>\n' +
            '                                <a onclick="deleteRowOp(event)"><i class="fa fa-trash-o fa-fw"></i></a>\n' +
            '                            </div>\n' +
            '                            <div class="col-md-2">\n' +
            '                                <p>شماره پرسنلی اپراتور</p>\n' +
            '                                <input name="code" class="form-control"/>\n' +
            '                            </div>\n' +
            '                            <div class="col-md-2">\n' +
            '                                <p>نام اپراتور</p>\n' +
            '                                <input name="name" class="form-control"/>\n' +
            '                            </div>\n' +
            '                            <div class="col-md-2">\n' +
            '                                <p>وظیفه</p>\n' +
            '                                <input name="job" class="form-control"/>\n' +
            '                            </div>\n' +
            '                            <div class="col-md-5">\n' +
            '                                <p>توضیحات</p>\n' +
            '                                <textarea name="opDes" class="form-control" maxlength="255"></textarea>\n' +
            '                            </div>\n' +
            '                        </div>');
    }
    function newModal(a) {
        $("#" + a).data("data", null);
        var inputs = $("#" + a).find('input');
        for (var i = 0; i < inputs.length; i++){
            $(inputs[i]).val("");
        }
        var selects = $("#" + a).find('select');
        for (var i = 0; i < selects.length; i++){
            $(selects[i]).val("");
            refreshSelect($(selects[i]), "لطفا گزینه مورد نظر را انتخاب کنید");
        }

        if (a == "rawModal"){
            if (listRawMaterials == null){
                $.ajax("/document/rawmaterial/ajaxData.do",{
                    type:"GET",
                    dataType:"json"
                }).done(function (data) {
                    listRawMaterials = data;
                    rawNewModal();
                }).fail(function (data) {
                    errors(data);
                });
            }else{
                rawNewModal();
            }
        }else if(a == "semiModal"){
            $("#semiModal").find("select[name='msc']").children().remove();
            $("#semiModal").find("select[name='msn']").children().remove();
            if (listSections != null){
                for(var i = 0; i < listSections.length; i++){
                    var option = document.createElement("option");
                    $(option).text(listSections[i].code);
                    $(option).val(listSections[i].code);
                    $("#semiModal").find("select[name='msc']").append(option);
                    option = document.createElement("option");
                    $(option).text(listSections[i].name);
                    $(option).val(listSections[i].code);
                    $("#semiModal").find("select[name='msn']").append(option);
                }
                refreshSelect($("#semiModal").find("select[name='msc']"), codeStmt);
                refreshSelect($("#semiModal").find("select[name='msn']"), nameStmt);

                $("#semiModal").find("select[name='msc']").prop("disabled", false);
                $("#semiModal").find("select[name='msn']").prop("disabled", false);
            }else{
                $("#semiModal").find("select[name='msc']").prop("disabled", true);
                $("#semiModal").find("select[name='msn']").prop("disabled", true);
            }
        }
        addModal(a);
    }
    function rawNewModal(){
        $("#rawModal").find("select[name='rc']").children().remove();
        $("#rawModal").find("select[name='rn']").children().remove();
        if (listRawMaterials != null){
            hashMapRaw = {};
            for(var i = 0; i < listRawMaterials.length; i++){
                var option = document.createElement("option");
                $(option).text(listRawMaterials[i].code);
                $(option).val(listRawMaterials[i].code);
                $("#rawModal").find("select[name='rc']").append(option);
                option = document.createElement("option");
                $(option).text(listRawMaterials[i].name);
                $(option).val(listRawMaterials[i].code);
                hashMapRaw[listRawMaterials[i].code] = listRawMaterials[i].name;
                $("#rawModal").find("select[name='rn']").append(option);
            }
            refreshSelect($("#rawModal").find("select[name='rc']"), codeStmt);
            refreshSelect($("#rawModal").find("select[name='rn']"), nameStmt);

            $("#rawModal").find("select[name='rc']").prop("disabled", false);
            $("#rawModal").find("select[name='rn']").prop("disabled", false);
        }else {
            $("#rawModal").find("select[name='rc']").prop("disabled", true);
            $("#rawModal").find("select[name='rn']").prop("disabled", true);
        }
    }
    function addFieldSectionRegister(element) {
        var row = createField();
        $(element).parents("div[class='panel-body']").first().find("fieldset[name='udf']").append(row);
        $(element).parents("div[class='modal-body']").first().find("fieldset[name='udf']").append("<hr/>");
    }
</script>
