<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!--Modal-->
<div class="modal fade" id="productModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">تعریف گروه دستگاه</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-4">
                                    <h4>کد محصول :</h4>
                                    <select name="pc" class="form-control" onchange="setProductCodeName(event)"></select>
                                </div>
                                <div class="col-md-4">
                                    <h4>نام محصول :</h4>
                                    <select name="pn" class="form-control" onchange="setProductCodeName(event)"></select>
                                </div>
                                <div class="col-md-4">
                                    <h4>موجودی محصول :</h4>
                                    <input name="pt" type="number" class="form-control" readonly hidden/>
                                </div>
                            </div>
                            <br/>
                            <div class="row">
                                <div class="col-md-4">
                                    <h4>تاریخ تولید :</h4>
                                    <input name="pd" type="text" class="form-control" onchange="setDate()"/>
                                </div>
                                <div class="col-md-4">
                                    <h4>شماره بچ :</h4>
                                    <select name="pb" class="form-control" onchange="setBatch()"></select>
                                </div>
                                <div class="col-md-4">
                                    <h4>موجودی بچ :</h4>
                                    <input name="pbt" type="number" class="form-control"/>
                                </div>
                            </div>
                            <hr/>
                            <div class="row" name="degreeRow">
                                <div class="col-md-12">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <h4>درجه محصول :</h4>
                                            <select title="a" name="pdeg" class="form-control" onchange="setDegree(event)"></select>
                                        </div>
                                        <div class="col-md-4">
                                            <h4>موجودی درجه :</h4>
                                            <input name="pdegt" type="number" readonly class="form-control"/>
                                        </div>
                                        <div class="col-md-4">
                                            <h4>مقدار تحویل :</h4>
                                            <input name="pv" type="number" class="form-control"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br/>
                            <div class="row">
                                <div class="col-md-3">
                                    <button name="btnadd" type="button" class="btn btn-primary" onclick="addDegree()"><i class="fa fa-plus fa-fw"></i></button>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptProductModal()">تایید</button>
            </div>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
<!--/Modal-->
<div class="row">
    <div class="col-md-12">
        <form id="form">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">تحویل محصول به فروش</h3>
                </div>
                <div class="panel-body">
                    <div class="col-md-12">
                        <table width="100%" class="table table-striped table-bordered table-hover text-center" id="dataTables-example">
                            <thead>
                            <tr>
                                <th></th>
                                <th>کد محصول</th>
                                <th>نام محصول</th>
                                <th>تاریخ تولید</th>
                                <th>شماره بچ</th>
                                <th>مقدار فروش</th>
                                <th>جزییات</th>
                            </tr>
                            </thead>
                            <tbody id="productBody">
                            <tr>
                                <td>
                                    <a type="button" onclick="deleteRow(event)" style="cursor: pointer"><i class="fa fa-trash-o fa-fw"></i></a>
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            </tbody>
                        </table>
                        <button type="button" class="btn btn-primary" data-toggle="modal" href="#productModal" onclick="newModal('productModal')"><i class="fa fa-plus fa-fw"></i></button>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-default" onclick="ajaxSubmit()">تایید</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script language="JavaScript" type="text/javascript">
    var productsName = new Array(), products = new Array();
    var degrees;
    $(document).ready(function () {
        ajaxLoad();
    });
    function ajaxLoad() {
        $.ajax("/document/product/ajaxLoad.do",{
            type:"GET",
            dataType:"JSON"
        }).done(function (data) {
            for (var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).text(data[i].code);
                $(option).val(data[i].code);
                $("#processModal").find("select[name='pc']").append(option);
                option = document.createElement("option");
                $(option).text(data[i].size + data[i].design + data[i].pr);
                $(option).val(data[i].code);
                productsName[data[i].code] = data[i].name;
                $("#processModal").find("select[name='pn']").append(option);
            }
            refreshSelect($("#processModal").find("select[name='pc']"), codeStmt);
            refreshSelect($("#processModal").find("select[name='pn']"), nameStmt);
        }).fail(function (data) {
            errors(data);
        });
    }

    function setProductCodeName(event){
        $(event.target).parents("div[class='row']").first().find("select[name='pc']").val($(event.target).val());
        $(event.target).parents("div[class='row']").first().find("select[name='pn']").val($(event.target).val());

        refreshSelect($(event.target).parents("div[class='row']").first().find("select[name='pc']"), codeStmt);
        refreshSelect($(event.target).parents("div[class='row']").first().find("select[name='pn']"), nameStmt);

        $.ajax("/document/product/ajaxDate.do", {
            data:{prouctCode:$(event.target).val()},
            type:"POST",
            dataType:"JSON"
        }).done(function (data) {
            var total = data.total;

            $(event.target).parents("div[class='row']").first().find("input[name='pt']").val(total);
            $(event.target).parents("div[class='row']").first().find("input[name='pt']").prop("hidden", false);

            var array = [];
            for(var i = 0; i < data.array.length; i++){
                array.push(data.array[i].date);
            }

            var x = $(event.target).parents("div[class='modal-body']").first().find("input[name='pd']");
            $(x).datepicker({
                format: "mm-dd-yyyy",
                minViewMode: 0,
                todayBtn: true,
                clearBtn: true,
                todayHighlight: true,
                toggleActive: true,
                beforeShowDay: function (date) {
                    var String = jQuery.datepicker.formatDate('mm-dd-yy', date);
                    if (array.indexOf(String) == -1){
                        return false;
                    }
                }
            });
            $(event.target).parents("div[class='modal-body']").first().find("input[name='pd']").prop("hidden", false);
        }).fail(function (data) {
            errors(data);
        })
    }
    function setDate() {
        var productCode = $("#productModal").find("select[name='pc']").val();
        var date = $("#productModal").find("input[name='pd']").val();
        if (date == ""){
            alertify.error(" تاریخ انتخاب نشده است !");
            return;
        }
        $.ajax("/document/product/ajaxBatchNumber.do",{
            data:{productCode:productCode, date:date},
            type:"POST",
            dataType:"JSON"
        }).done(function (data) {

            var select = $("#productModal").find("select[name='pb']");
            $(select).children().remove();
            $(select).append("<option value=''>شماره بچ را انتخاب کنید</option>");
            for (var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).val(data[i].batchNumber);
                $(option).text(data[i].batchNumber);
                $(select).append(option);
            }
            $(select).prop("hidden", false);
        }).fail(function (data) {
            errors(data);
        });
    }
    function setBatch() {
        var productCode = $("#productModal").find("select[name='pc']").val();
        var date = $("#productModal").find("input[name='pd']").val();
        var batchNumber = $("#productModal").find("select[name='pb']").val();

        if (batchNumber == ""){
            alertify.error("شماره بچ انتخاب نشده است !");
            return;
        }
        $.ajax("/document/product/ajaxDegree.do",{
            data:{productCode:productCode, date:date, batchNumber:batchNumber},
            type:"POST",
            dataType:"JSON"
        }).done(function (data) {
            degrees = data;
            var select = $("#productModal").find("select[name='pdeg']");
            $(select).children().remove();
            $(select).append("<option value=''>شماره درجه را انتخاب کنید</option>");

            for(var i = 0; i < data.length; i++){
                var option = document.createElement("option");
                $(option).val(data[i].code);
                $(option).text(data[i].code);
                $(select).append(option);
                $(select).data("data", data[i]);
            }
            $(select).prop("hidden", false);
        }).fail(function (data) {
            errors(data);
        });
    }
    function setDegree(event) {
        var degree = $(event.target).data("data");

        $(event.target).parents("div[name='row']").first().find("input[name='pdegt']").val(degree.total);

        $(event.target).parents("div[name='row']").first().find("input[name='pdegt']").prop("hidden", false);
        $(event.target).parents("div[name='row']").first().find("input[name='pv']").prop("hidden", false);
    }
    function addDegree() {
        var row = document.createElement("div");
        $(row).addClass("row");

        var col = document.createElement("div");
        $(col).addClass("col-md-4");

        $(col).append("<h4>درجه محصول :</h4>");

        var select = document.createElement("select");
        $(select).addClass("form-control");
        $(select).attr("name","pdeg");
        $(select).on("change", function (event) {
            setDegree(event);
        });
        $(col).append(select);
        $(row).append(col);

        col = document.createElement("div");
        $(col).addClass("col-md-4");

        $(col).append("<h4>موجودی درجه :</h4>");

        var input = document.createElement("input");
        $(input).addClass("form-control");
        $(input).attr("name", "pdegt");
        $(input).prop("readonly", true);
        $(input).attr("type", "number");
        $(col).append(input);
        $(row).append(col);

        col = document.createElement("div");
        $(col).addClass("col-md-4");

        $(col).append("<h4>مقدار تحویل :</h4>");

        input = document.createElement("input");
        $(input).addClass("form-control");
        $(input).attr("name", "pv");
        $(input).attr("type", "number");
        $(col).append(input);
        $(row).append(col);

        $("#productModal").find("div[name='degreeRow']").find("div[class='col-md-12']").append(row);
        return row;
    }

    function acceptProductModal() {
        if ($("#productModal").data("data") != null){
            var product = $("#productModal").data("data");
            product.code = $("#productModal").find("select[name='pc']").val();
            product.name = productsName[$("#productModal").find("select[name='pn']").val()];
            product.date = $("#productModal").find("input[name='pd']").val();
            product.batch = $("#productModal").find("select[name='pb']").val();
            var degrees = $("#productModal").find("select[name='pdeg']");
            var values = $("#productModal").find("input[name='pv']");
            for (var i = 0; i < degrees.length; i++){
                var degree = new Object();
                degree.code = $(degrees[i]).val();
                degree.value = $(values[i]).val();
                product.degrees.push(degree);
            }
        }else {
            var product = new Object();
            product.code = $("#productModal").find("select[name='pc']").val();
            product.name = productsName[$("#productModal").find("select[name='pn']").val()];
            product.date = $("#productModal").find("input[name='pd']").val();
            product.batch = $("#productModal").find("select[name='pb']").val();
            var degrees = $("#productModal").find("select[name='pdeg']");
            var values = $("#productModal").find("input[name='pv']");
            var total = 0;
            for (var i = 0; i < degrees.length; i++){
                var degree = new Object();
                degree.code = $(degrees[i]).val();
                degree.total = $(degrees[i]).data("data").total;
                degree.value = $(values[i]).val();
                total += degree.value;
                product.degrees.push(degree);
            }
            product.total = total;
        }
        productTable();
        removeModal();
    }

    function deleteRow(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                products.splice(products.indexOf(data), 1);
                productTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }

    function productTable() {
        $("#productBody").children().remove();
        for (var i = 0; i < products.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).attr("type", "button");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", products[i]);
            $(a).on("click", function () {
                deleteRow($(this).data("data"));
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(products[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(products[i].name);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(products[i].date);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(products[i].batch);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(products[i].total);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).attr("type", "button");
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", products[i]);
            $(a).on("click", function () {
                var product = $(this).data("data");
                $("#productModal").data("data", product);
                $("#productModal").find('input[name="pc"]').val(product.code);
                $("#productModal").find('input[name="pn"]').val(product.name);
                $("#productModal").find('input[name="pd"]').val(product.date);
                $("#productModal").find('input[name="pb"]').val(product.batch);

                $("#productModal").find("div[name='degreeRow']").find("div[class='col-md-12']").children().remove();

                var degrees = product.degrees;
                for (var i = 0; i < degrees.length; i++){
                    var row = addDegree();
                    var select = $(row).find("select[name='pdeg']");

                    var option = document.createElement("option");
                    $(option).val(degrees[i].code);
                    $(option).text(degrees[i].code);
                    $(select).append(option);
                    $(select).data("data", degrees[i]);

                    var input = $(row).find("input[name='pdegt']");
                    $(input).val(degrees[i].total);

                    input = $(row).find("input[name='pv']");
                    $(input).val(degrees[i].value);
                }

                addModal("productModal");
                $("#productModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#productBody").append(tr);
        }
    }

    function ajaxSubmit() {
        $.ajax("/document/product/out.do", {
            data:{data:products},
            type:"POST"
        }).done(function (data) {
            resetPage();
            alertify.success("محصولات از انبار کم شدند")
        }).fail(function (data) {
            errors(data);
        });
    }

    function newModal(a) {
        var inputs = $("#" + a).find('input');
        for (var i = 0; i < inputs.length; i++){
            $(inputs[i]).val("");
        }
        var selects = $("#" + a).find('select');
        for (var i = 0; i < selects.length; i++){
            $(selects[i]).val("");
        }
        var textarea = $("#" + a).find('textarea');
        for (var i = 0; i < textarea.length; i++){
            $(textarea[i]).val("");
        }
        $("#" + a).data("data", null);

        $("#" + a).find("input[name='pt']").prop("hidden", true);
        $("#" + a).find("input[name='pd']").prop("hidden", true);
        $("#" + a).find("input[name='pbt']").prop("hidden", true);
        $("#" + a).find("select[name='pb']").prop("hidden", true);
        $("#" + a).find("select[name='pdeg']").prop("hidden", true);
        $("#" + a).find("input[name='pdegt']").prop("hidden", true);
        $("#" + a).find("input[name='pv']").prop("hidden", true);
        $("#" + a).find("button[name='btnadd']").prop("hidden", true);

        addModal(a);
    }
</script>

