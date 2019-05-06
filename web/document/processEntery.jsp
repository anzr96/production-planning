<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Modal -->
<div class="modal fade" id="processModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">تعریف فرایند</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p> کد قسمت</p>
                                        <select name="msc" class="form-control" onchange="setMachineSection(event)"></select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام قسمت</p>
                                        <select name="msn" class="form-control" onchange="setMachineSection(event)"></select>
                                    </div>
                                </div>
                            </div>
                            <div class="row" hidden="true">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p> کد گروه دستگاه</p>
                                        <select name="mgc" class="form-control" onchange="setMachineGroup(event)"></select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام گروه دستگاه</p>
                                        <select name="mgn" class="form-control" onchange="setMachineGroup(event)"></select>
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
                                        <p>مقدار خروجی دستگاه </p>
                                        <input name="met" class="form-control" />
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel-group" id="accordion">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="false" class="collapsed">ایستگاه قبل</a>
                                    </h4>
                                </div>
                                <div id="collapseOne" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                                    <div class="panel-body">
                                        <table id="enterMachineTable" class="table table-responsive table-striped table-bordered table-hover" width="100%">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>قسمت دستگاه</th>
                                                <th>گروه دستگاه</th>
                                                <th>جزییات</th>
                                            </tr>
                                            </thead>
                                            <tbody id="enterMachineBody">
                                            </tbody>
                                        </table>
                                        <button class="btn btn-outline btn-primary" data-toggle="modal" href="#enterMachineModal" onclick="newModal('enterMachineModal')"><i class="fa fa-plus"></i></button>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="collapsed" aria-expanded="false">ایستگاه بعد</a>
                                    </h4>
                                </div>
                                <div id="collapseTwo" class="panel-collapse collapse" aria-expanded="false" style="height: 0px;">
                                    <div class="panel-body">
                                        <table id="exitMachineTable" class="table table-responsive table-striped table-bordered table-hover" width="100%">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>قسمت دستگاه</th>
                                                <th>گروه دستگاه</th>
                                                <th>جزییات</th>
                                            </tr>
                                            </thead>
                                            <tbody id="exitMachineBody">
                                            </tbody>
                                        </table>
                                        <button class="btn btn-outline btn-primary" data-toggle="modal" href="#exitMachineModal" onclick="newModal('exitMachineModal')"><i class="fa fa-plus"></i></button>
                                    </div>
                                </div>
                            </div>
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h4 class="panel-title">
                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="" aria-expanded="false">مواد اولیه</a>
                                    </h4>
                                </div>
                                <div id="collapseThree" class="panel-collapse collapse" aria-expanded="true" style="">
                                    <div class="panel-body">
                                        <table id="rawTable" class="table table-responsive table-striped table-bordered table-hover" width="100%">
                                            <thead>
                                            <tr>
                                                <th></th>
                                                <th>کد ماده اولیه</th>
                                                <th>نام ماده اولیه</th>
                                                <th>جزییات</th>
                                            </tr>
                                            </thead>
                                            <tbody id="rawBody">
                                            </tbody>
                                        </table>
                                        <button class="btn btn-outline btn-primary" data-toggle="modal" href="#rawModal" onclick="newModal('rawModal')"><i class="fa fa-plus"></i></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /.col-md-12 -->
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptProcessModal()">تایید</button>
            </div>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
<div class="modal fade" id="enterMachineModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">دستگاه ایستگاه قبل</h4>
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
                                        <p>مقدار</p>
                                        <input name="met" type="number" class="form-control"/>
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
<div class="modal fade" id="exitMachineModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">دستگاه ایستگاه بعد</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p> کد قسمت</p>
                                        <select name="msc" class="form-control" onchange="setExitSection(event, 'machineModal')"></select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام قسمت</p>
                                        <select name="msn" class="form-control" onchange="setExitSection(event, 'machineModal')"></select>
                                    </div>
                                </div>
                            </div>
                            <div class="row" hidden>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p> کد گروه دستگاه</p>
                                        <select name="mgc" class="form-control" onchange="setExit(event, 'machineModal')"></select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام گروه دستگاه</p>
                                        <select name="mgn" class="form-control" onchange="setExit(event, 'machineModal')"></select>
                                    </div>
                                </div>
                            </div>
                            <div class="row" hidden="true">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>مقدار خروجی ایستگاه فعلی که به دستگاه انتخاب شده وارد میشود </p>
                                        <div class="col-md-7">
                                            <input name="met" type="number" class="form-control"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptExit()">تایید</button>
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
                                        <p>مقدار ماده اولیه </p>
                                        <input name="rt" type="number" class="form-control"/>
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
<!-- /.modal -->
<div class="row">
    <div class="col-md-12">
        <h1 class="page-header">مشخصات محصول</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h3>فرایند ساخت محصول</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-6">
                        <div class="form-group">
                            <p> کد محصول</p>
                            <select name="pc" class="form-control" onchange="setProduct(event)"></select>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="form-group">
                            <p>نام محصول</p>
                            <select name="pn" class="form-control" onchange="setProduct(event)"></select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">فرایند تولید</div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-12">
                        <table id="productTable" class="table table-responsive table-striped table-bordered table-hover" width="100%">
                            <thead>
                            <tr>
                                <th></th>
                                <th>قسمت دستگاه</th>
                                <th>گروه دستگاه</th>
                                <th>خروجی دستگاه</th>
                                <th>جزییات</th>
                            </tr>
                            </thead>
                            <tbody id="productBody">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <div class="row">
                    <div class="col-md-4">
                        <button type="button" class="btn btn-outline btn-primary" data-toggle="modal" href="#processModal" onclick="newModal('processModal')"><i class="fa fa-plus"></i></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row" >
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-footer">
                <div class="row">
                    <div class="col-md-4">
                        <button type="button" class="btn btn-danger" onclick="resetPage()">لغو</button>
                        <button type="button" class="btn btn-success" onclick="acceptProcess()">تایید</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="application/javascript">
    var listSections = null, listRawMaterials = null;
    var hashMapRaw = {};
    var process = new Object();
    var machines = new Array(), enterMachines = new Array(), exitMachines = new Array(), rawMaterials = new Array();

    $(function () {
        $.ajax("/document/product/data.do", {
            type:"GET",
            dataType:"json"
        }).done(function (data) {
            $("#page-wrapper").find("select[name='pc']").children().remove();
            $("#page-wrapper").find("select[name='pn']").children().remove();
            for(var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).text(data[i].code);
                $(option).val(data[i].code);
                $("#page-wrapper").find("select[name='pc']").append(option);
                option = document.createElement("option");
                $(option).text(data[i].name);
                $(option).val(data[i].code);
                $("#page-wrapper").find("select[name='pn']").append(option);
            }
            refreshSelect($("#page-wrapper").find("select[name='pc']"), codeStmt);
            refreshSelect($("#page-wrapper").find("select[name='pn']"), nameStmt);
        }).fail(function (data){
            errors(data)
        });
    });

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

    function setMachineSection(event) {
        $(event.target).parents("fieldset").find("select[name='msc']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='msn']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='msc']"), codeStmt);
        refreshSelect($(event.target).parents("fieldset").find("select[name='msn']"), nameStmt);

        refreshSelect($(event.target).parents("fieldset").find("select[name='mec']"), codeStmt, true);
        refreshSelect($(event.target).parents("fieldset").find("select[name='men']"), nameStmt, true);

        ajaxGroupMachine("processModal", $(event.target).val());
    }
    function setMachineGroup(event) {
        $(event.target).parents("fieldset").find("select[name='mgc']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='mgn']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='mgc']"), codeStmt);
        refreshSelect($(event.target).parents("fieldset").find("select[name='mgn']"), nameStmt);

        ajaxMachineProduct("processModal", $("#processModal").find("select[name='msc']").val(), $(event.target).val())
    }
    function setMachineEnterSection(event) {
        $(event.target).parents("fieldset").find("select[name='msc']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='msn']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='msc']"), codeStmt);
        refreshSelect($(event.target).parents("fieldset").find("select[name='msn']"), nameStmt);

        refreshSelect($(event.target).parents("fieldset").find("select[name='mec']"), codeStmt, true);
        refreshSelect($(event.target).parents("fieldset").find("select[name='men']"), nameStmt, true);

        ajaxGroupMachine("enterMachineModal", $(event.target).val());
    }
    function setMachineEnter(event) {
        $(event.target).parents("fieldset").find("select[name='mgc']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='mgn']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='mgc']"), codeStmt);
        refreshSelect($(event.target).parents("fieldset").find("select[name='mgn']"), nameStmt);

        ajaxMachineProduct("enterMachineModal", $("#enterMachineModal").find("select[name='msc']").val(), $(event.target).val())
    }
    function setMachineExit(event) {
        $(event.target).parents("fieldset").find("select[name='mec']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='men']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='mec']"), codeStmt,false,false);
        refreshSelect($(event.target).parents("fieldset").find("select[name='men']"), nameStmt,false,false);
    }
    function setExitSection(event){
        $(event.target).parents("fieldset").find("select[name='msc']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='mns']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='msc']"), codeStmt);
        refreshSelect($(event.target).parents("fieldset").find("select[name='msn']"), nameStmt);

        ajaxGroupMachine("exitMachineModal", $(event.target).val());
    }
    function setExit(event) {
        $(event.target).parents("fieldset").find("select[name='mgc']").val($(event.target).val());
        $(event.target).parents("fieldset").find("select[name='mgn']").val($(event.target).val());

        refreshSelect($(event.target).parents("fieldset").find("select[name='mgc']"), codeStmt);
        refreshSelect($(event.target).parents("fieldset").find("select[name='mgn']"), nameStmt);

        $("#exitMachineModal").find("input[name='met']").parent().parent().parent().parent().prop("hidden", false);
    }
    function setProduct(event) {
        $(event.target).parents("div[class='row']").find("select[name='pc']").val($(event.target).val());
        $(event.target).parents("div[class='row']").find("select[name='pn']").val($(event.target).val());

        refreshSelect($(event.target).parents("div[class='row']").find("select[name='pc']"), codeStmt);
        refreshSelect($(event.target).parents("div[class='row']").find("select[name='pn']"), nameStmt);
    }
    function setRawMaterial(event) {
        $(event.target).parents("div[class='row']").find("select[name='rc']").val($(event.target).val());
        $(event.target).parents("div[class='row']").find("select[name='rn']").val($(event.target).val());

        refreshSelect($(event.target).parents("div[class='row']").find("select[name='rc']"), codeStmt);
        refreshSelect($(event.target).parents("div[class='row']").find("select[name='rn']"), nameStmt);
    }

    function productTable() {
        $("#productTable").DataTable().destroy();
        $("#productBody").children().remove();
        for (var i = 0; i < machines.length; i++){
            var tr = document.createElement("tr");
            $(tr).data("data", machines[i]);

            var td = document.createElement("td");
            var a = document.createElement("a");
             
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("value", machines[i]);
            $(a).on("click", function () {
                deleteRowProduct($(this).data("value"));
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(machines[i].sectionCode + ":" + machines[i].sectionName);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(machines[i].groupCode + ":" + machines[i].groupName);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(machines[i].exitCode + ":" + machines[i].exitName);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
             
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", machines[i]);
            $(a).on("click", function () {
                var machine = $(this).data("data");
                $("#processModal").data("data", machine);
                $("#processModal").find('select[name="msc"]').val(machine.sectionCode);
                $("#processModal").find('select[name="msn"]').val(machine.sectionCode);
                ajaxGroupMachine("processModal", machine.sectionCode, machine.groupCode);
                ajaxMachineProduct("processModal", machine.sectionCode, machine.groupCode, machine.exitCode);
                $("#processModal").find('input[name="met"]').val(machine.exitTotal);
                var selects = $("#processModal").find('select');
                for (var i = 0; i < selects.length; i++){
                    refreshSelect($(selects[i]), "لطفا گزینه مورد نظر را انتخاب کنید", false, false);
                }

                enterMachines = machine.enterMachines;
                exitMachines = machine.exitMachines ;
                rawMaterials = machine.rawMaterials ;

                enterMachineTable();
                exitMachineTable();
                rawTable();

                addModal("processModal");
                $("#processModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#productBody").append(tr);
        }
        $('#productBody').contextMenu({
            selector: 'tr',
            callback: function(key, options) {
                switch (key){
                    case "edit":{
                        var machine = $(this).data("data");
                        $("#processModal").data("data", machine);
                        $("#processModal").find('select[name="msc"]').val(machine.sectionCode);
                        $("#processModal").find('select[name="msn"]').val(machine.sectionCode);
                        ajaxGroupMachine("processModal", machine.sectionCode);
                        $("#processModal").find('select[name="mgc"]').val(machine.groupCode);
                        $("#processModal").find('select[name="mgn"]').val(machine.groupCode);
                        ajaxMachineProduct("processModal", machine.sectionCode, machine.groupCode);
                        $("#processModal").find('select[name="mec"]').val(machine.exitCode);
                        $("#processModal").find('select[name="men"]').val(machine.exitCode);
                        $("#processModal").find('input[name="met"]').val(machine.exitTotal);
                        var selects = $("#processModal").find('select');
                        for (var i = 0; i < selects.length; i++){
                            refreshSelect(selects[i], "لطفا گزینه مورد نظر را انتخاب کنید");
                        }

                        enterMachines = machine.enterMachines;
                        exitMachines = machine.exitMachines ;
                        rawMaterials = machine.rawMaterials ;

                        enterMachineTable();
                        exitMachineTable();
                        rawTable();

                        addModal("processModal");
                        $("#processModal").modal("show");
                        break;
                    }
                    case "delete":{
                        deleteRowProduct($(this).data("data"));
                        break;
                    }
                }
            },
            items: {
                "edit": {name: "Edit", icon: "edit"},
                "delete": {name: "Delete", icon: "delete"},
                "sep1": "---------",
                "quit": {name: "Quit", icon: function($element, key, item){ return 'context-menu-icon context-menu-icon-quit'; }}
            }
        });
        createDataTable($("#productTable"));
    }
    function exitMachineTable() {
        $("#exitMachineTable").DataTable().destroy();
        $("#exitMachineBody").children().remove();
        for (var i = 0; i < exitMachines.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
             
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("value", exitMachines[i]);
            $(a).on("click", function () {
                deleteRowExit($(this).data("value"));
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(exitMachines[i].sectionCode + ":" + exitMachines[i].sectionName);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(exitMachines[i].groupCode + ":" + exitMachines[i].groupName);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
             
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", exitMachines[i]);
            $(a).on("click", function () {
                var exitMachine = $(this).data("data");
                $("#exitMachineModal").data("data", exitMachine);
                $("#exitMachineModal").find('select[name="msc"]').val(exitMachine.sectionCode);
                $("#exitMachineModal").find('select[name="msn"]').val(exitMachine.sectionCode);
                ajaxGroupMachine("exitMachineModal", exitMachine.sectionCode, exitMachine.groupCode);
                $("#exitMachineModal").find('input[name="met"]').val(exitMachine.exitTotal);
                var selects = $("#exitMachineModal").find('select');
                for (var i = 0; i < selects.length; i++){
                    refreshSelect(selects[i], "لطفا گزینه مورد نظر را انتخاب کنید", false, false);
                }

                addModal("exitMachineModal");
                $("#exitMachineModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#exitMachineBody").append(tr);
        }
        createDataTable($("#exitMachineTable"));
    }
    function enterMachineTable() {
        $("#enterMachineTable").DataTable().destroy();
        $("#enterMachineBody").children().remove();
        for (var i = 0; i < enterMachines.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
             
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("value", enterMachines[i]);
            $(a).on("click", function () {
                deleteRowEnter($(this).data("value"));
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(exitMachines[i].sectionCode + ":" + exitMachines[i].sectionName);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(exitMachines[i].groupCode + ":" + exitMachines[i].groupName);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
             
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", enterMachines[i]);
            $(a).on("click", function () {
                var enterMachine = $(this).data("data");
                $("#enterMachineModal").data("data", enterMachine);
                $("#enterMachineModal").find('select[name="msc"]').val(enterMachine.sectionCode);
                $("#enterMachineModal").find('select[name="msn"]').val(enterMachine.sectionCode);
                ajaxGroupMachine("enterMachineModal", enterMachine.sectionCode, enterMachine.groupCode);
                ajaxMachineProduct("enterMachineModal", enterMachine.sectionCode, enterMachine.groupCode, enterMachine.exitCode)
                $("#enterMachineModal").find('input[name="met"]').val(enterMachine.exitTotal);
                var selects = $("#enterMachineModal").find('select');
                for (var i = 0; i < selects.length; i++){
                    refreshSelect(selects[i], "لطفا گزینه مورد نظر را انتخاب کنید", false, false);
                }

                addModal("enterMachineModal");
                $("#enterMachineModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#enterMachineBody").append(tr);
        }
        createDataTable($("#enterMachineTable"));
    }
    function rawTable() {
        $("#rawTable").DataTable().destroy();
        $("#rawBody").children().remove();
        for (var i = 0; i < rawMaterials.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
             
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("value", rawMaterials[i]);
            $(a).on("click", function () {
                deleteRowRaw($(this).data("value"));
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(rawMaterials[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(hashMapRaw[rawMaterials[i].code]);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
             
            $(a).text("...");
            $(a).attr("cursor", "pointer");
            $(a).data("data", rawMaterials[i]);
            $(a).on("click", function () {
                var rawMaterial = $(this).data("data");
                $("#rawModal").data("data", rawMaterial);
                $("#rawModal").find('select[name="rc"]').val(rawMaterial.code);
                $("#rawModal").find('select[name="rn"]').val(rawMaterial.code);
                $("#rawModal").find('input[name="rt"]').val(rawMaterial.total);
                var selects = $("#rawModal").find('select');
                for (var i = 0; i < selects.length; i++){
                    refreshSelect($(selects[i]), "لطفا گزینه مورد نظر را انتخاب کنید");
                }

                addModal("rawModal");
                $("#rawModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#rawBody").append(tr);
        }
        createDataTable($("#rawTable"));
    }

    function deleteRowProduct(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                machines.splice(machines.indexOf(data), 1);
                productTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowEnter(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                enterMachines.splice(enterMachines.indexOf(data), 1);
                enterMachineTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowExit(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                exitMachines.splice(exitMachines.indexOf(data))
                exitMachineTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowRaw(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                rawMaterials.splice(rawMaterials.indexOf(data), 1);
                rawTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }

    function acceptProcessModal() {
        if ($("#processModal").data("data") != null){
            var machine = machines[machines.indexOf($("#processModal").data("data"))];
            machine.sectionCode = $("#processModal").find("select[name='msc']").val();
            machine.groupCode = $("#processModal").find("select[name='mgc']").val();
            machine.sectionName = $("#processModal").find("select[name='msn']").find(":selected").text();
            machine.groupName = $("#processModal").find("select[name='mgn']").find(":selected").text();
            machine.exitCode = $("#processModal").find("select[name='mec']").val();
            machine.exitName = $("#processModal").find("select[name='men']").find(":selected").text();
            machine.exitTotal = $("#processModal").find("input[name='met']").val();
            machine.enterMachines = enterMachines;
            machine.exitMachines = exitMachines;
            machine.rawMaterials = rawMaterials;
        }else{
            var machine = new Object();
            machine.sectionCode = $("#processModal").find("select[name='msc']").val();
            machine.groupCode = $("#processModal").find("select[name='mgc']").val();
            machine.sectionName = $("#processModal").find("select[name='msn']").find(":selected").text();
            machine.groupName = $("#processModal").find("select[name='mgn']").find(":selected").text();
            machine.exitCode = $("#processModal").find("select[name='mec']").val();
            machine.exitName = $("#processModal").find("select[name='men']").find(":selected").text();
            machine.exitTotal = $("#processModal").find("input[name='met']").val();
            machine.enterMachines = enterMachines;
            machine.exitMachines = exitMachines;
            machine.rawMaterials = rawMaterials;
            machines.push(machine);
        }
        productTable();
        removeModal();
    }
    function acceptEnter() {
        if ($("#enterMachineModal").data("data") != null){
            var enter = enterMachines[enterMachines.indexOf($("#enterMachineModal").data("data"))];
            enter.sectionCode = $("#enterMachineModal").find("select[name='msc']").val();
            enter.groupCode = $("#enterMachineModal").find("select[name='mgc']").val();
            enter.sectionName = $("#enterMachineModal").find("select[name='msn']").find(":selected").text();
            enter.groupName = $("#enterMachineModal").find("select[name='mgn']").find(":selected").text();
            enter.exitCode = $("#enterMachineModal").find("select[name='mec']").val();
            enter.exitName = $("#enterMachineModal").find("select[name='men']").find(":selected").text();
            enter.exitTotal = $("#enterMachineModal").find("input[name='met']").val();
        }else{
            var enter = new Object();
            enter.sectionCode = $("#enterMachineModal").find("select[name='msc']").val();
            enter.groupCode = $("#enterMachineModal").find("select[name='mgc']").val();
            enter.sectionName = $("#enterMachineModal").find("select[name='msn']").find(":selected").text();
            enter.groupName = $("#enterMachineModal").find("select[name='mgn']").find(":selected").text();
            enter.exitCode = $("#enterMachineModal").find("select[name='mec']").val();
            enter.exitName = $("#enterMachineModal").find("select[name='men']").find(":selected").text();
            enter.exitTotal = $("#enterMachineModal").find("input[name='met']").val();
            enterMachines.push(enter);
        }
        enterMachineTable();
        removeModal();
    }
    function acceptExit() {
        if ($("#exitMachineModal").data("data") != null){
            var exit = exitMachines[exitMachines.indexOf($("#exitMachineModal").data("data"))];
            exit.sectionCode = $("#exitMachineModal").find("select[name='msc']").val();
            exit.groupCode = $("#exitMachineModal").find("select[name='mgc']").val();
            exit.sectionName = $("#exitMachineModal").find("select[name='msn']").find(":selected").text();
            exit.groupName = $("#exitMachineModal").find("select[name='mgn']").find(":selected").text();
            exit.exitTotal = $("#exitMachineModal").find("input[name='met']").val();
        }else{
            var exit = new Object();
            exit.sectionCode = $("#exitMachineModal").find("select[name='msc']").val();
            exit.groupCode = $("#exitMachineModal").find("select[name='mgc']").val();
            exit.sectionName = $("#exitMachineModal").find("select[name='msn']").find(":selected").text();
            exit.groupName = $("#exitMachineModal").find("select[name='mgn']").find(":selected").text();
            exit.exitTotal = $("#exitMachineModal").find("input[name='met']").val();
            exitMachines.push(exit);
        }
        exitMachineTable();
        removeModal();
    }
    function acceptRaw() {
        if ($("#rawModal").data("data") != null){
            var rawMaterial = rawMaterials[rawMaterials.indexOf($("#rawModal").data("data"))];
            rawMaterial.code = $("#rawModal").find("select[name='rc']").val();
            rawMaterial.name = hashMapRaw[$("#rawModal").find("select[name='rc']").val()];
            rawMaterial.total = $("#rawModal").find("input[name='rt']").val();
        }else{
            var rawMaterial = new Object();
            rawMaterial.code = $("#rawModal").find("select[name='rc']").val();
            rawMaterial.name = hashMapRaw[$("#rawModal").find("select[name='rc']").val()];
            rawMaterial.total = $("#rawModal").find("input[name='rt']").val();
            rawMaterials.push(rawMaterial);
        }
        rawTable();
        removeModal();
    }
    function acceptProcess() {
        process.code = $("#page-wrapper").find("select[name='pc']").val();
        process.name = $("#page-wrapper").find("select[name='pn']").find(":selected").text();
        process.machines = machines;
        var data = JSON.stringify(process);
        $.ajax("/document/process/register.do",{
            data:{data:data},
            type:"POST",
            dataType:"json"
        }).done(function (data) {
            alertify.success("اطلاعات با موفقیت ثبت شد");
            loadContent("/document/processEntery.jsp", false);
        }).fail(function (data){
            errors(data)
        });
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

        if (a == "processModal"){
            enterMachines = new Array();
            exitMachines = new Array();
            rawMaterials = new Array();
            enterMachineTable();
            exitMachineTable();
            rawTable();

            if (listSections == null){
                $.ajax("/document/machine/listSection.do",{
                    type:"GET",
                    dataType:"json",
                }).done(function (data) {
                    listSections = data;
                    processNewModal();
                }).fail(function (data) {
                    errors(data);
                });
            }else{
                processNewModal();
            }
        }else if (a == "rawModal"){
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
        }else if(a == "enterMachineModal"){
            $("#enterMachineModal").find("select[name='msc']").children().remove();
            $("#enterMachineModal").find("select[name='msn']").children().remove();
            if (listSections != null){
                for(var i = 0; i < listSections.length; i++){
                    var option = document.createElement("option");
                    $(option).text(listSections[i].code);
                    $(option).val(listSections[i].code);
                    $("#enterMachineModal").find("select[name='msc']").append(option);
                    option = document.createElement("option");
                    $(option).text(listSections[i].name);
                    $(option).val(listSections[i].code);
                    $("#enterMachineModal").find("select[name='msn']").append(option);
                }
                refreshSelect($("#enterMachineModal").find("select[name='msc']"), codeStmt);
                refreshSelect($("#enterMachineModal").find("select[name='msn']"), nameStmt);

                $("#enterMachineModal").find("select[name='msc']").prop("disabled", false);
                $("#enterMachineModal").find("select[name='msn']").prop("disabled", false);
            }else{
                $("#enterMachineModal").find("select[name='msc']").prop("disabled", true);
                $("#enterMachineModal").find("select[name='msn']").prop("disabled", true);
            }
        }else if(a == "exitMachineModal"){
            $("#exitMachineModal").find("select[name='msc']").children().remove();
            $("#exitMachineModal").find("select[name='msn']").children().remove();
            if (listSections != null){
                for(var i = 0; i < listSections.length; i++){
                    var option = document.createElement("option");
                    $(option).text(listSections[i].code);
                    $(option).val(listSections[i].code);
                    $("#exitMachineModal").find("select[name='msc']").append(option);
                    option = document.createElement("option");
                    $(option).text(listSections[i].name);
                    $(option).val(listSections[i].code);
                    $("#exitMachineModal").find("select[name='msn']").append(option);
                }
                refreshSelect($("#exitMachineModal").find("select[name='msc']"), codeStmt);
                refreshSelect($("#exitMachineModal").find("select[name='msn']"), nameStmt);

                $("#exitMachineModal").find("select[name='msc']").prop("disabled", false);
                $("#exitMachineModal").find("select[name='msn']").prop("disabled", false);
            }else{
                $("#exitMachineModal").find("select[name='msc']").prop("disabled", true);
                $("#exitMachineModal").find("select[name='msn']").prop("disabled", true);
            }
        }
        addModal(a);
    }
    function processNewModal(){
        $("#processModal").find("select[name='msc']").children().remove();
        $("#processModal").find("select[name='msn']").children().remove();
        if (listSections != null){
            for(var i = 0; i < listSections.length; i++){
                var option = document.createElement("option");
                $(option).text(listSections[i].code);
                $(option).val(listSections[i].code);
                $("#processModal").find("select[name='msc']").append(option);
                option = document.createElement("option");
                $(option).text(listSections[i].name);
                $(option).val(listSections[i].code);
                $("#processModal").find("select[name='msn']").append(option);
            }
            refreshSelect($("#processModal").find("select[name='msc']"), codeStmt);
            refreshSelect($("#processModal").find("select[name='msn']"), nameStmt);

            $("#processModal").find("select[name='msc']").prop("disabled", false);
            $("#processModal").find("select[name='msn']").prop("disabled", false);
        }else{
            $("#processModal").find("select[name='msc']").prop("disabled", true);
            $("#processModal").find("select[name='msn']").prop("disabled", true);
        }
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
</script>
