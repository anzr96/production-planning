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
                <p>کد نیمه ساخته</p>
                <input id="oc" class="form-control" />
            </div>
            <div class="col-md-3">
                <p>نام نیمه ساخته</p>
                <input id="on" class="form-control"/>
            </div>
        </div>
    </div>
    <div id="detail" class="tab-pane fade">
        <br/>
        <div class="row">
            <div class="col-md-2">
                <p>واحد نیمه ساخته</p>
                <input id="unit" class="form-control"/>
            </div>
            <div class="col-md-2">
                <p>حداقل age</p>
                <input id="ageMin" class="form-control"/>
            </div>
            <div class="col-md-2">
                <p>حداکثر age</p>
                <input id="ageMax" class="form-control"/>
            </div>
            <div class="col-md-2">
                <p>حداقل مورد نیاز</p>
                <input id="min" class="form-control"/>
            </div>
            <div class="col-md-2">
                <p>حداکثر مورد نیاز</p>
                <input id="max" class="form-control"/>
            </div>
        </div>
        <hr/>
        <div class="row">
            <div class="col-md-12">
                <p>توضیحات</p>
                <textarea id="des" class="form-control" maxlength="255"></textarea>
            </div>
        </div>
        <hr/>
        <div class="row">

        </div>
    </div>
</div>
