<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/1/2016
  Time: 1:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="application/javascript" src="<c:url value="/resources/js/custom/discontinuedjs.js"/>"></script>
<br/>
<div class="row">
    <div class="col-md-12">
        <form id="form" name="form">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h4>نمایش توقفات</h4>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-md-3">
                            <p>گروه توقف : </p>
                        </div>
                        <div class="col-md-3">
                            <select id="disGroup" name="disGroup" class="form-control" onchange="disKind(event)" multiple="true" disabled>

                            </select>
                        </div>
                        <div class="col-md-3">
                            <p>نوع توقف : </p>
                        </div>
                        <div class="col-md-3">
                            <select id="kind" name="kind" class="form-control" multiple="true" disabled>
                            </select>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>زمان توقف</p>
                        </div>
                        <div class="col-md-6">
                            <div class="input-daterange input-group" id="datepicker">
                                <input id="start" type="text" class="input-sm form-control" name="start" placeholder="شروع" />
                                <span class="input-group-addon">تا</span>
                                <input id="end" type="text" class="input-sm form-control" name="end" placeholder="پایان" />
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="row">
                        <div class="col-md-3">
                            <p>نحوه نمایش</p>
                        </div>
                        <div class="col-md-9">
                            <div class="checkbox">
                                <label class="checkbox-inline">
                                    جدول<input type="checkbox" name="table" id="table"/>
                                </label>
                                <label class="checkbox-inline">
                                    نمودار خطی<input type="checkbox" name="line" id="line" />
                                </label>
                                <label class="checkbox-inline">
                                    نمودار میله ای<input type="checkbox" name="bar" id="bar" />
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-md-3">
                            <button type="button" class="btn btn-default btn-outline" onclick="ajaxCheck()">تایید</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<br/>
<div class="row" id="tableDiv" hidden>
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">جدول توقفات</h4>
            </div>
            <div class="panel-body">
            </div>
        </div>
    </div>
</div>
<br/>
<div class="row" id="lineDiv" hidden>
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">نمودار خطی توقفات</h4>
            </div>
            <div class="panel-body">
            </div>
        </div>
    </div>
</div>
<br/>
<div class="row" id="barDiv" hidden>
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">نمودار میله ای توقفات</h4>
            </div>
            <div class="panel-body">
            </div>
        </div>
    </div>
</div>

