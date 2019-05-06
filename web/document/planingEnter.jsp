<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 8/17/17
  Time: 2:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--Modal-->
<div class="modal fade" id="productModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">محصول</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label"> کد محصول</label>
                                        <input name="pc" readonly class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام محصول</p>
                                        <input name="pn" readonly class="form-control"/>
                                    </div>
                                </div>

                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>مقدار انتخاب شده</p>
                                        <input name="pt" class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>وزن انتخاب شده</p>
                                        <input name="pw" class="form-control"/>
                                    </div>
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
<div class="modal fade" id="rawModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">مواد اولیه</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <p>کد ماده اولیه </p>
                                        <input name="c" readonly class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <p>نام ماده اولیه </p>
                                        <input name="n" readonly class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <p>مقدار ماده اولیه مورد نیاز</p>
                                        <input name="t" type="number" class="form-control"/>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptRawModal()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<div class="modal fade" id="semiModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">نیمه ساخته</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label"> کد نیمه ساخته</label>
                                        <input name="c" readonly class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام نیمه ساخته</p>
                                        <input name="n" readonly class="form-control"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>مقدار مورد نیاز</p>
                                        <input name="t" class="form-control"/>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptSemiModal()">تایید</button>
            </div>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
<div class="modal fade" id="sectionModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">قسمت و گروه های مورد نیاز آن</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p> کد قسمت</p>
                                        <input name="sc" class="form-control" readonly/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام قسمت</p>
                                        <input name="sn" class="form-control" readonly/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <table id="groupTable" class="table table-striped table-hover table-responsive table-bordered" width="100%">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>کد گروه</th>
                                            <th>نام گروه</th>
                                            <th>جزییات</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptSectionModal()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<div class="modal fade" id="groupModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">دستگاه های مورد نیاز</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p> کد گروه دستگاه</p>
                                        <input name="gc" class="form-control" readonly/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام گروه دستگاه</p>
                                        <input name="gn" class="form-control" readonly/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <table id="machineTable" class="table table-striped table-hover table-responsive table-bordered" width="100%">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>کد دستگاه</th>
                                            <th>نام دستگاه</th>
                                            <th>ظرفیت دستگاه</th>
                                            <th>مقدار تولید</th>
                                            <th>جزییات</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptGroupModal()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<div class="modal fade" id="machineModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">دستگاه و لیست تولید های آن</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <p> کد دستگاه</p>
                                        <input name="mc" readonly class="form-control" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <p>نام دستگاه</p>
                                        <input name="mn" readonly class="form-control" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <p>ظرفیت دستگاه</p>
                                        <input name="mcap" readonly class="form-control" />
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <div class="form-group">
                                        <p>مقدار تولید</p>
                                        <input name="mt" readonly class="form-control" />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <table id="semiMachineTable" class="table table-striped table-hover table-responsive table-bordered" width="100%">
                                        <thead>
                                        <tr>
                                            <th></th>
                                            <th>کد نیمه ساخته</th>
                                            <th>نام نیمه ساخته</th>
                                            <th>اولویت تولید</th>
                                            <th>مقدار تولید</th>
                                            <th>جزییات</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptMachineModal()">تایید</button>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
</div>
<div class="modal fade" id="semiMachineModal" tabindex="-1" role="dialog" data-backdrop="static" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="removeModal()">×</button>
                <h4 class="modal-title">نیمه ساخته تولید شده در دستگاه</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <fieldset>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label"> کد نیمه ساخته</label>
                                        <input name="c" readonly class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>نام نیمه ساخته</p>
                                        <input name="n" readonly class="form-control"/>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>اولویت</p>
                                        <input name="p" class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <p>مقدار تولید در دستگاه</p>
                                        <input name="t" class="form-control"/>
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal" onclick="removeModal()">لغو</button>
                <button type="button" class="btn btn-primary" onclick="acceptSemiMachineModal()">تایید</button>
            </div>
        </div>
    </div>
    <!-- /.modal-content -->
</div>
<!--/Modal-->
<div class="row">
    <div class="col-md-12">
        <h1 class="page-header">برنامه ریزی تولید</h1>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label" style="width: auto">نوع برنامه تولید</label>
                            <select name="plan" class="form-control" onchange="" required>
                                <option value="" selected>گزینه مورد نظر را انتخاب کنید</option>
                                <option value="1">بر اساس بودجه</option>
                                <option value="2">بر اساس باقی مانده بودجه</option>
                                <option value="3" disabled>بر اساس موجودی مواد اولیه</option>
                                <option value="4" disabled>بر اساس بیشترین تولید</option>
                                <option value="5">برنامه جدید</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">تاریخ</label>
                            <input name="date" class="form-control" placeholder="YYYY/MM/DD" onchange="" required/>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label class="control-label">شیفت</label>
                            <select name="shift" class="form-control" onchange="" required>
                                <option value="" selected>گزینه مورد نظر را انتخاب کنید</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="form-group">
                            <br/>
                            <button type="button" class="btn btn-default" onclick="getProductsNeed()">لیست محصولات</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <h4>محصولات</h4>
                <table id="productTable" class="table table-responsive table-striped table-bordered table-hover" width="100%" cellspacing="0">
                    <thead>
                    <tr>
                        <th></th>
                        <th>کد</th>
                        <th>نام</th>
                        <th>مقدار</th>
                        <th>وزن</th>
                        <th>جزییات</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
            </div>
            <div class="panel-footer">
                <div class="row">
                    <div class="col-md-3">
                        <div class="form-group">
                            <button type="button" class="btn btn-success" onclick="getPlan()">دریافت برنامه پیشنهادی</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-9">
                        <h4>مواد اولیه مورد نیاز</h4>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <table id="rawTable" class="table table-responsive table-striped table-bordered table-hover" width="100%" cellspacing="0">
                    <thead>
                    <tr>
                        <th></th>
                        <th>کد</th>
                        <th>نام</th>
                        <th>مقدار</th>
                        <th>جزییات</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12">
                        <h4>نیمه ساخته های مورد نیاز</h4>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <table id="semiTable" class="table table-responsive table-striped table-bordered table-hover" width="100%" cellspacing="0">
                    <thead>
                    <tr>
                        <th></th>
                        <th>قسمت</th>
                        <th>گروه</th>
                        <th>نیمه ساخته</th>
                        <th>مقدار</th>
                        <th>جزییات</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12">
                        <h4>دستگاه ها و ظرفیت مورد نیاز آنها</h4>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <table id="sectionTable" class="table table-responsive table-striped table-bordered table-hover" width="100%" cellspacing="0">
                    <thead>
                    <tr>
                        <th></th>
                        <th>کد قسمت</th>
                        <th>نام قسمت</th>
                        <th>جزییات</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-4 col-md-pull-4">
                        <div class="form-group">
                            <button type="button" class="btn btn-success" onclick="submitPlan()">تایید برنامه تولید</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var sections = [], products = [], raws = [];
    var groups = [], machines = [], semiMachines = [], machineTemp;

    $(function () {
        $.ajax("/document/workingCalendar/get.do", {
            type:"GET",
            dataType:"json"
        }).done(function (data) {
            var dates = data.wd;
            var eds = data.ed;
            for (let obj of eds) {
                dates.push(obj.unix);
            }
            $("#page-wrapper").find("input[name='date']").pDatepicker({
                format:"YYYY/MM/DD",
                navigator:{
                    text:{
                        btnNextText:'>',
                        btnPrevText:'<'
                    }
                },
                checkDate:function (unix) {
                    if (dates.indexOf(unix) != -1){
                        return true;
                    }else {
                        return false;
                    }
                }
            })
        }).fail(function( jqXHR ) {
            errors(jqXHR);
        })
    });
    function getDate() {
        var year = parseInt($("#page-wrapper").find("input[name='date']").val().substr(0,4));
        var month = parseInt($("#page-wrapper").find("input[name='date']").val().substr(5,2));
        var day = parseInt($("#page-wrapper").find("input[name='date']").val().substr(8,2));

        var d = new persianDate([year, month, day, 0, 0, 0, 0]).toDate();
        return d.getTime();
    }

    function getProductsNeed() {
        var x = new Object();
        x.plan = $("#page-wrapper").find("select[name='plan']").val();
        x.date = getDate();
        x.shift = $("#page-wrapper").find("select[name='shift']").val();

        $.ajax("/document/planing/getData.do", {
            data:{data:JSON.stringify(x)},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            products = data;
            productTable();
        }).fail(function (data) {
            errors(data);
        })
    }
    function getPlan() {
        $.ajax("/document/planing/getPlan.do", {
            data:{products:JSON.stringify(products), date:getDate()},
            type:"post",
            dataType:"json"
        }).done(function (data) {
            sections = data.section;
            raws = data.raw;
            sectionTable();
            rawTable();
            semiTable();
        }).fail(function (data) {
            errors(data);
        })
    }
    function submitPlan() {
        var x = new Object();
        x.products = products;
        x.raws = raws;
        x.sections = sections;
        x.date = getDate();
        x.shift = $("#page-wrapper").find("select[name='shift']").val();

        $.ajax("/document/planing/submit.do", {
            data:{data:JSON.stringify(x)},
            type:"post"
        }).done(function () {
            alertify.success("اطلاعات ثبت شد");
            loadContent("/document/planingEnter.jsp", false);
        }).fail(function (data) {
            errors(data);
        })
    }

    function acceptProductModal() {
        var product = $("#productModal").data("data");
        product.total = $("#productModal").find("input[name='pt']").val();
        product.weight = $("#productModal").find("input[name='pw']").val();
        productTable();
        removeModal();
    }
    function acceptRawModal() {
        var raw = $("#rawModal").data("data");
        raw.total = $("#rawModal").find("input[name='t']").val();
        rawTable();
        removeModal();
    }
    function acceptSemiModal() {
        var semi = $("#semiModal").data("data");
        semi.total = $("#semiModal").find("input[name='t']").val();
        var totalCapacity = 0;
        try {
            for (let section of sections) {
                for (let groups of section.groups) {
                    if (groups.semis.indexOf(semi) != -1){
                        for (let machine of groups.machines) {
                            for (let semiMachine of machine.semiMachines) {
                                if (semiMachine.code === semi.code && semiMachine.name === semi.name){
                                    totalCapacity += machine.capacity;
                                }
                            }
                        }
                    }
                }
            }
            for (let section of sections) {
                for (let groups of section.groups) {
                    if (groups.semis.indexOf(semi) != -1){
                        for (let machine of groups.machines) {
                            for (let semiMachine of machine.semiMachines) {
                                if (semiMachine.code === semi.code && semiMachine.name === semi.name){
                                    machine.total = Math.ceil(machine.total - semiMachine.total + (semi.total * machine.capacity) / totalCapacity);
                                    semiMachine.total = Math.ceil((semi.total * machine.capacity) / totalCapacity);
                                }
                            }
                        }
                    }
                }
            }
        }catch (err){
            alertify.error("در محاسبات خطایی رخ داد لطفا دوباره صفحه را بارگذاری کنید");
        }

        semiTable();
        removeModal();
    }
    function acceptSectionModal() {
        removeModal();
    }
    function acceptGroupModal() {
        removeModal();
    }
    function acceptMachineModal() {
        removeModal();
    }
    function acceptSemiMachineModal() {
        var semiMachine = $("#semiMachineModal").data("data");
        semiMachine.priority = $("#semiMachineModal").find("input[name='p']").val();
        machineTemp.total = parseInt(machineTemp.total - semiMachine.total + $("#semiMachineModal").find("input[name='t']").val());
        semiMachine.total = $("#semiMachineModal").find("input[name='t']").val();
        try {
            for (let group of groups) {
                for (let semi of group.semis) {
                    if (semi.code == semiMachine.code && semi.name == semiMachine.name){
                        var remain = semi.total - semiMachine.total;
                        var totalCapacity = 0;
                        for (let machine of machines) {
                            if (machine !== machineTemp){
                                totalCapacity += machine.capacity;
                            }
                        }
                        for (let machine of machines) {
                            if (machine !== machineTemp){
                                for (let semiMachines2 of machine.semiMachines) {
                                    if (semiMachines2.code === semiMachine.code && semiMachines2.name === semiMachine.name){
                                        machine.total = Math.ceil(machine.total - semiMachines2.total + (remain * machine.capacity) / totalCapacity);
                                        semiMachines2.total = Math.ceil((remain * machine.capacity) / totalCapacity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (err){
            alertify.error("در محاسبات خطایی رخ داد لطفا دوباره صفحه را بارگذاری کنید");
        }
        $("#machineModal").find("input[name='mt']").val(machineTemp.total);
        machineTable();
        semiMachineTable();
        removeModal();
    }

    function productTable() {
        $("#productTable").DataTable().destroy();
        $("#productTable").find('tbody').children().remove();
        for (var i = 0; i < products.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).attr("type", "button");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", products[i]);
            $(a).on("click", function () {
                deleteRowProduct($(this).data("data"));
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
            $(td).append(products[i].total);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(products[i].weight);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", products[i]);
            $(a).on("click", function (event) {
                var product = $(this).data("data");
                $("#productModal").data("data", product);
                $("#productModal").find('input[name="pc"]').val(product.code);
                $("#productModal").find('input[name="pn"]').val(product.name);
                $("#productModal").find('input[name="pt"]').val(product.total);
                $("#productModal").find('input[name="pw"]').val(product.weight);

                addModal("productModal");
                $("#productModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#productTable").find('tbody').append(tr);
        }
        createDataTable($("#productTable"));
    }
    function rawTable() {
        try {
            $("#rawTable").DataTable().destroy();
        }catch (err){

        }

        $("#rawTable").find('tbody').children().remove();
        for (var i = 0; i < raws.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).attr("type", "button");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", raws[i]);
            $(a).on("click", function () {
                deleteRowRaw($(this).data("data"));
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
            $(td).append(raws[i].total);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", raws[i]);
            $(a).on("click", function (event) {
                var raw = $(this).data("data");
                $("#rawModal").data("data", raw);
                $("#rawModal").find('input[name="c"]').val(raw.code);
                $("#rawModal").find('input[name="n"]').val(raw.name);
                $("#rawModal").find('input[name="t"]').val(raw.total);

                addModal("rawModal");
                $("#rawModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#rawTable").find('tbody').append(tr);
        }
        createDataTable($("#rawTable"));
    }
    function semiTable() {
        $("#semiTable").DataTable().destroy();
        $("#semiTable").find('tbody').children().remove();
        for (let section of sections) {
            for (let groups of section.groups) {
                for (let semis of groups.semis) {
                    var tr = document.createElement("tr");

                    var td = document.createElement("td");
                    var a = document.createElement("a");
                    $(a).attr("type", "button");
                    $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
                    $(a).data("data", semis);
                    $(a).on("click", function () {
                        deleteRowSemi($(this).data("data"))
                    });
                    $(td).append(a);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).append(section.code + ":" + section.name);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).append(groups.code + ":" + groups.name);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).append(semis.code + ":" + semis.name);
                    $(tr).append(td);

                    td = document.createElement("td");
                    $(td).append(semis.total);
                    $(tr).append(td);

                    td = document.createElement("td");
                    a = document.createElement("a");
                    $(a).text("...");
                    $(a).css("cursor", "pointer");
                    $(a).data("data", semis);
                    $(a).on("click", function (event) {
                        var semi = $(this).data("data");
                        $("#semiModal").data("data", semi);
                        $("#semiModal").find('input[name="c"]').val(semi.code);
                        $("#semiModal").find('input[name="n"]').val(semi.name);
                        $("#semiModal").find('input[name="t"]').val(semi.total);

                        addModal("semiModal");
                        $("#semiModal").modal("show");
                    });
                    $(td).append(a);
                    $(tr).append(td);

                    $("#semiTable").find('tbody').append(tr);
                }
            }
        }
        createDataTable($("#semiTable"));
    }
    function sectionTable(){
        $("#sectionTable").DataTable().destroy();
        $("#sectionTable").find('tbody').children().remove();
        for (var i = 0; i < sections.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).attr("type", "button");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", sections[i]);
            $(a).on("click", function () {
                deleteRowSection($(this).data("data"))
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(sections[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(sections[i].name);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", sections[i]);
            $(a).on("click", function () {
                var section = $(this).data("data");
                $("#sectionModal").data("data", section);
                $("#sectionModal").find('input[name="sc"]').val(section.code);
                $("#sectionModal").find('input[name="sn"]').val(section.name);
                groups = section.groups;
                groupTable();

                addModal("sectionModal");
                $("#sectionModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#sectionTable").find('tbody').append(tr);
        }
        createDataTable($("#sectionTable"));
    }
    function groupTable(){
        $("#groupTable").DataTable().destroy();
        $("#groupTable").find('tbody').children().remove();
        for (var i = 0; i < groups.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).attr("type", "button");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", groups[i]);
            $(a).on("click", function () {
                deleteRowGroup($(this).data("data"))
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(groups[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(groups[i].name);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", groups[i]);
            $(a).on("click", function (event) {
                var group = $(this).data("data");
                $("#groupModal").data("data", group);
                $("#groupModal").find('input[name="gc"]').val(group.code);
                $("#groupModal").find('input[name="gn"]').val(group.name);
                machines = group.machines;
                machineTable();

                addModal("groupModal");
                $("#groupModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#groupTable").find('tbody').append(tr);
        }
        createDataTable($("#groupTable"));
    }
    function machineTable() {
        $("#machineTable").DataTable().destroy();
        $("#machineTable").find('tbody').children().remove();
        for (var i = 0; i < machines.length; i++){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).attr("type", "button");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", machines[i]);
            $(a).on("click", function () {
                deleteRowMachine($(this).data("data"))
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(machines[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(machines[i].name);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(machines[i].capacity);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(machines[i].total);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", machines[i]);
            $(a).on("click", function () {
                var machine = $(this).data("data");
                $("#machineModal").data("data", machine);
                $("#machineModal").find('input[name="mc"]').val(machine.code);
                $("#machineModal").find('input[name="mn"]').val(machine.name);
                $("#machineModal").find('input[name="mcap"]').val(machine.capacity);
                $("#machineModal").find('input[name="mt"]').val(machine.total);
                semiMachines = machine.semiMachines;
                machineTemp = machine;
                semiMachineTable();

                addModal("machineModal");
                $("#machineModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#machineTable").find('tbody').append(tr);
        }
        createDataTable($("#machineTable"));
    }
    function semiMachineTable() {
        $("#semiMachineTable").DataTable().destroy();
        $("#semiMachineTable").find('tbody').children().remove();
        for (var i = 0; i < semiMachines.length; i++){
            var tr = document.createElement("tr");

            var td = document.createElement("td");
            var a = document.createElement("a");
            $(a).attr("type", "button");
            $(a).append('<i class="fa fa-trash-o fa-fw"></i>');
            $(a).data("data", semiMachines[i]);
            $(a).on("click", function () {
                deleteRowSemiMachine($(this).data("data"))
            });
            $(td).append(a);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semiMachines[i].code);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semiMachines[i].name);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semiMachines[i].priority);
            $(tr).append(td);

            td = document.createElement("td");
            $(td).append(semiMachines[i].total);
            $(tr).append(td);

            td = document.createElement("td");
            a = document.createElement("a");
            $(a).text("...");
            $(a).css("cursor", "pointer");
            $(a).data("data", semiMachines[i]);
            $(a).on("click", function () {
                var semi = $(this).data("data");
                $("#semiMachineModal").data("data", semi);
                $("#semiMachineModal").find('input[name="c"]').val(semi.code);
                $("#semiMachineModal").find('input[name="n"]').val(semi.name);
                $("#semiMachineModal").find('input[name="p"]').val(semi.priority);
                $("#semiMachineModal").find('input[name="t"]').val(semi.total);

                addModal("semiMachineModal");
                $("#semiMachineModal").modal("show");
            });
            $(td).append(a);
            $(tr).append(td);

            $("#semiMachineTable").find('tbody').append(tr);
        }
        createDataTable($("#semiMachineTable"));
    }

    function deleteRowProduct(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                products.splice(products.indexOf(data), 1);
                productTable();
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
    function deleteRowSemi(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                for (let section of sections) {
                    for (let groups of section.groups) {
                        for (let semis of groups.semis) {
                            if (semis.indexOf(data) !== -1){
                                semis.splice(semis.indexOf(data), 1);
                                semiTable();
                            }
                        }
                    }
                }
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowSection(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                sections.splice(sections.indexOf(data), 1);
                sectionTable();
                semiTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowGroup(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                groups.splice(groups.indexOf(data), 1);
                groupTable();
                semiTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowMachine(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                machines.splice(machines.indexOf(data), 1);
                machineTable();
                semiTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }
    function deleteRowSemiMachine(data) {
        alertify.confirm('حذف اطلاعات', 'آیا از حذف اطلاعات مورد نظر مطمئن هستید ؟', function(){
                semiMachines.splice(semiMachines.indexOf(data), 1);
                semiMachineTable();
                semiTable();
                alertify.message('حذف شد');
            }
            , function(){
                alertify.message('حذف نشد');
            });
    }

    function newModal(name) {
        var inputs = $("#" + name).find('input');
        for (var i = 0; i < inputs.length; i++){
            $(inputs[i]).val("");
        }
        var selects = $("#" + name).find('select');
        for (var i = 0; i < selects.length; i++){
            $(selects[i]).val("");
        }
        var textarea = $("#" + name).find('textarea');
        for (var i = 0; i < textarea.length; i++){
            $(textarea[i]).val("");
        }

        try{
            $("#" + name).data("data", null);
        }catch (err){

        }
        addModal(a);
    }
</script>

