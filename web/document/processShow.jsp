<%--
  Created by IntelliJ IDEA.
  User: amir
  Date: 7/29/17
  Time: 11:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--cytoscape-->
<script type="application/javascript" src="<c:url value="/resources/js/cytoscape.js"/>"></script>
<div class="row">
    <div class="col-md-12">
        <h1 class="page-header">نمایش فرایند تولید</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<br/>
<div class="row">
    <div class="col-md-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-4">
                        <p>لطفا کد مورد نظر را انتخاب کنید : <select name="pc" class="form-control" onchange="setProduct(event)"></select></p>
                    </div>
                    <div class="col-md-4">
                        <p>لطفا نام مورد نظر را انتخاب کنید : <select name="pn" class="form-control" onchange=""></select></p>
                    </div>
                </div>
            </div>
            <div class="panle-body">
                <div class="row">
                    <div class="col-md-12">
                        <div id="cy"></div>
                    </div>
                </div>
            </div>
            <div class="panel-footer">

            </div>
        </div>

    </div>
</div>
<script type="application/javascript">
    var hashProduct = {};
    $('body').popover({
        placement:"auto",
        content:function () {
            return "نام : " + $(this).data("name")
        },
        selector:'[rel="popover"]'
    });
    $(document).ready(function () {
       $.ajax("/document/product/ajaxLoad.do",{
           type:"POST",
           dataType:"json"
       }).done(function (data) {
           if (data != null && data != undefined && data.length > 0){
               for ( var i = 0; i < data.length; i++){
                   var option = document.createElement("option");
                   $(option).text(data[i].code);
                   $(option).val(data[i].code);
                   $("select[name='pc']").append(option);
                   option = document.createElement("option");
                   $(option).text(data[i].size + data[i].design +data[i].pr);
                   $(option).val(data[i].code);
                   $("select[name='pn']").append(option);

                   hashProduct[data[i].code] = data[i].size + data[i].design +data[i].pr;
               }
           }
           refreshSelect($("select[name='pc']"), codeStmt);
           refreshSelect($("select[name='pn']"), nameStmt);
       }).fail(function (data) {
           errors(data);
       })
    });
    function setProduct(event) {
        $(event.target).parents("div[class='row']").first().find("select[name='pc']").val($(event.target).val());
        $(event.target).parents("div[class='row']").first().find("select[name='pn']").val($(event.target).val());

        refreshSelect($(event.target).parents("div[class='row']").first().find("select[name='pc']"), codeStmt);
        refreshSelect($(event.target).parents("div[class='row']").first().find("select[name='pn']"), nameStmt);

        $.ajax("/document/process/show.do", {
            data:{data:$(event.target).val()},
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

