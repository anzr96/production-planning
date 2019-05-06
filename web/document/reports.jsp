<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="application/javascript" src="<c:url value="/resources/js/custom/reports.js"/>"></script>
<br/>
<div class="row">
    <div class="col-md-12">
        <h1 class="page-header">گزارشات</h1>
    </div>
</div>
<br/>
<div class="row">
    <div class="col-md-12">
        <form name="form" id="form" >
            <div class="panel panel-default">
                <div class="panel-body">
                    <div class="row">
                        <div class="form-group">
                            <div class="col-md-3">
                                <p>نوع داده ها</p>
                            </div>
                            <div class="col-md-3">
                                <select id="data" name="data" class="form-control" onchange="data2Set(event)">
                                    <option value="">لطفا نوع داده مورد نظر را انتخاب کنید</option>
                                    <option value="product">محصولات</option>
                                    <option value="semistructured">نیمه ساخته ها</option>
                                    <option value="rawmaterial">مواد اولیه</option>
                                    <option value="machine">دستگاه ها</option>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <select id="data2" name="data2" class="form-control" disabled onchange="selection(event)">
                                    <option value="">لطفا داده مورد نظر را انتخاب کنید</option>
                                </select>
                            </div>
                            <div class="col-md-3" id="divData3">
                                <select name="data3" class="form-control" multiple disabled>
                                </select>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="form-group">
                            <div class="col-md-3">
                                <p>نمایش زمان به کدام صورت باشد : </p>
                            </div>
                            <div class="col-md-3">
                                <select id="viewMode" name="viewMode" class="form-control" disabled onchange="datepick(event)">
                                    <option value="">لطفا حالت نمایش مورد نظر را انتخاب کنید</option>
                                    <option value="year">سالانه</option>
                                    <option value="month">ماهانه</option>
                                    <option value="day">روزانه</option>
                                </select>
                            </div>
                            <div class="col-md-6" id="divDate">
                                <div class="input-daterange input-group" id="datepicker">
                                    <input id="start" type="text" class="input-sm form-control" name="start" placeholder="شروع" disabled/>
                                    <span class="input-group-addon">تا</span>
                                    <input id="end" type="text" class="input-sm form-control" name="end" placeholder="پایان" disabled/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label><p>نوع نمودار</p></label>
                                    <label class="checkbox-inline">
                                        نمودار خطی
                                        <input id="line" type="checkbox"/>
                                    </label>
                                    <label class="checkbox-inline">
                                        نمودار میله ای
                                        <input id="bar" type="checkbox"/>
                                    </label>
                                    <label class="checkbox-inline">
                                        نمودار دایره ای
                                        <input id="donat" type="checkbox"/>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group">
                            <div class="col-md-3">
                                <button type="button" class="btn btn-outline btn-default" onclick="checkData()">تایید</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
