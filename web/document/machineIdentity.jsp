<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="nav nav-tabs">
    <li class="active">
        <a aria-expanded="" href="#home" data-toggle="tab">مشخصات کلی</a>
    </li>
    <li >
        <a aria-expanded="" href="#detail" data-toggle="tab">جزییات</a>
    </li>
</ul>
<div class="tab-content">
    <div id="home" class="tab-pane fade active in">
        <br/>
        <div class="row">
            <div class="col-md-3">
                <p>کد قسمت</p>
                <input id="sc" class="form-control" />
            </div>
            <div class="col-md-3">
                <p>نام قسمت</p>
                <input id="sn" class="form-control"/>
            </div>
        </div>
        <hr/>
        <div class="row">
            <div class="col-md-3">
                <p>کد گروه</p>
                <input id="gc" class="form-control" />
            </div>
            <div class="col-md-3">
                <p>نام گروه</p>
                <input id="gn" class="form-control"/>
            </div>
        </div>
        <hr/>
        <div class="row">
            <div class="col-md-3">
                <p>کد دستگاه</p>
                <input id="mc" class="form-control" />
            </div>
            <div class="col-md-3">
                <p>نام دستگاه</p>
                <input id="mn" class="form-control"/>
            </div>
        </div>
    </div>
    <div id="detail" class="tab-pane fade">
        <br/>
        <div class="row">
            <div class="col-md-3">
                <p> ظرفیت دستگاه</p>
                <input id="capacity" type="number" class="form-control" readonly/>
            </div>
            <div class="col-md-3">
                <p> مقدار تولید در ساعت</p>
                <input id="uph" type="number" class="form-control" readonly/>
            </div>
            <div class="col-md-3">
                <p> تاریخ تولید دستگاه</p>
                <input id="birthday" class="form-control" readonly/>
            </div>
            <div class="col-md-3">
                <p> کشور تولید شده</p>
                <input id="country" class="form-control" readonly />
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <p>توضیحات</p>
                <textarea id="des" class="form-control" maxlength="255" readonly ></textarea>
            </div>
        </div>
        <div class="row">

        </div>
    </div>
</div>
