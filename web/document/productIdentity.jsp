<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 10/8/2016
  ProductInTime: 7:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="application/javascript" src="<c:url value="/resources/js/cytoscape.js"/>"></script>
<ul class="nav nav-tabs">
    <li class="active in">
        <a aria-expanded="" href="#home" data-toggle="tab">مشخصات کلی</a>
    </li>
    <li >
        <a aria-expanded="" href="#raw" data-toggle="tab">مواد اولیه تشکیل دهنده</a>
    </li>
    <li >
        <a aria-expanded="" href="#semi" data-toggle="tab">نیمه ساخته های تشکیل دهنده</a>
    </li>
    <li >
        <a aria-expanded="" href="#process" data-toggle="tab">فرایند تولید</a>
    </li>
</ul>
<div class="tab-content">
    <div id="home" class="tab-pane fade active in">
        <br/>
        <div class="row">
            <div class="col-md-3">
                <p> کد محصول</p>
                <input id="code" class="form-control" readonly/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-2">
                <p> سایز محصول</p>
                <input id="size" class="form-control" readonly/>
            </div>
            <div class="col-md-2">
                <p> طرح محصول</p>
                <input id="design" class="form-control" readonly/>
            </div>
            <div class="col-md-2">
                <p> pr محصول</p>
                <input id="pr" class="form-control" readonly/>
            </div>
        </div>
        <hr/>
        <div class="row">

        </div>
    </div>
    <div id="raw" class="tab-pane fade">
        <br/>
        <div class="row">
            <div class="col-md-12">
                <table id="rawTable" width="100%" class="table table-responsive table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <td>کد ماده اولیه</td>
                        <td>نام ماده اولیه</td>
                        <td>مقدار مصرفی</td>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div id="semi" class="tab-pane fade">
        <br/>
        <div class="row">
            <div class="col-md-12">
                <table id="semiTable" width="100%" class="table table-responsive table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <td>قسمت</td>
                        <td>گروه</td>
                        <td>نیمه ساخته</td>
                        <td>مقدار</td>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div id="process" class="tab-pane fade">
        <br/>
        <div class="row">
            <div class="col-md-12">
                <div id="cy"></div>
            </div>
        </div>
    </div>
</div>
<script type="application/javascript">
    $('body').popover({
        placement:"auto",
        content:function () {
            return "نام : " + $(this).data("name")
        },
        selector:'[rel="popover"]'
    });
    function setProduct(object) {
        $.ajax("/document/process/show.do", {
            data:{data:JSON.stringify(object)},
            type:"POST",
            dataType:"json"
        }).done(function (data) {
            $("#cy").css("width", "100%");
            $("#cy").css("height", "100%");
            graph(data);
        }).fail(function (data) {
            errors(data);
        })
    }
    function graph(data) {
        var cy = cytoscape({

            container: document.getElementById('cy'), // container to render in

            elements: data,

            style: [
                {
                    selector: 'node',
                    css: {
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'text-halign': 'center'
                    }
                },
                {
                    selector: 'node[type="raw"]',
                    css: {
                        'shape':'triangle',
                        'content': 'data(name)',
                        'text-valign': 'center',
                        'text-halign': 'center'
                    }
                },
                {
                    selector: '$node > node',
                    css: {
                        'padding-top': '10px',
                        'padding-left': '10px',
                        'padding-bottom': '10px',
                        'padding-right': '10px',
                        'text-valign': 'top',
                        'text-halign': 'center',
                        'background-color': '#bbb'
                    }
                },
                {
                    selector: 'edge',
                    css: {
                        'target-arrow-shape': 'triangle'
                    },
                    style: {
                        "label": "data(label)",
                        "width": 3,
                        "line-color": "#ccc"
                    }
                },
                {
                    selector: ':selected',
                    css: {
                        'background-color': 'black',
                        'line-color': 'black',
                        'target-arrow-color': 'black',
                        'source-arrow-color': 'black'
                    }
                }
            ],

            layout: {
                name: 'breadthfirst',
                padding: 5
            },

            // initial viewport state:
            zoom: 1,
            pan: { x: 0, y: 0 },

            // interaction options:
            minZoom: 0.5,
            maxZoom: 2,
            zoomingEnabled: true,
            userZoomingEnabled: true,
            panningEnabled: true,
            userPanningEnabled: true,
            boxSelectionEnabled: true,
            selectionType: 'single',
            touchTapThreshold: 8,
            desktopTapThreshold: 4,
            autounselectify: true,

            // rendering options:
            headless: false,
            styleEnabled: true,
            hideEdgesOnViewport: true,
            hideLabelsOnViewport: true,
            textureOnViewport: false,
            motionBlur: false,
            motionBlurOpacity: 0.2,
            wheelSensitivity: 1,
            pixelRatio: 'auto'

        });
        cy.width("100%");
        cy.height("100%");
        cy.on("tap", "node", function (evt) {
            var node = evt.target;
            $(node).attr("rel", "popover");

        });
        cy.on("tap", "edge", function (evt) {
            var edge = evt.target;
            $(edge).popover({
                placement:"auto",
                content:"مقدار :" + edge.data("label"),
                title:"مقدار منتقل شده"
            });

        });
    }

</script>
