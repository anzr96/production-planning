<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul class="nav nav-tabs">
    <li class="active">
        <a aria-expanded="" href="#home" data-toggle="tab">مشخصات کلی</a>
    </li>
    <li >
        <a aria-expanded="" href="#company" data-toggle="tab">مشخصات شرکت</a>
    </li>
    <li>
        <a aria-expanded="" href="#detail" data-toggle="tab">جزئیات</a>
    </li>
</ul>
<div class="tab-content">
    <div id="home" class="tab-pane fade active in">
        <br/>
        <div class="row">
            <div class="col-md-3 ">
                <p> کد ماده اولیه</p>
                <input id="code" class="form-control" readonly/>
            </div>
            <div class="col-md-3 ">
                <p> نام ماده اولیه</p>
                <input id="name" class="form-control" readonly/>
            </div>
        </div>
    </div>
    <div id="company" class="tab-pane fade">
        <br/>
        <div class="row">
            <div class="col-md-3 ">
                <p> نام شرکت</p>
                <input id="companyName" class="form-control" readonly/>
            </div>
            <div class="col-md-3 ">
                <p> داخلی یا خارجی </p>
                <input id="inout" class="form-control" readonly/>
            </div>
        </div>
    </div>
    <div id="detail" class="tab-pane fade">
        <br/>
        <div class="row">
            <div class="col-md-3 ">
                <p>نقطه سفارش </p>
                <input id="op" class="form-control" readonly/>
            </div>
            <div class="col-md-3 ">
                <p> زمان آخرین ورودی مواد</p>
                <input id="tlo" class="form-control" readonly/>
            </div>
            <div class="col-md-3 ">
                <p> مقدار آخرین ورودی مواد</p>
                <input id="total"   class="form-control" readonly/>
            </div>
        </div>
        <hr/>
        <div class="row">

        </div>
    </div>
</div>
