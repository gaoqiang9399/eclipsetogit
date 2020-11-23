<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%--<%@ include file="/component/include/pub_view.jsp"%>  --%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>

<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoicemanageFinc_List.js"></script>
    <script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoicemanage_List.js"></script>
    <script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoiceOutmanage_Form.js"></script>
    <script type="text/javascript" src="${webPath}/component/invoicemanage/js/MfBusInvoicemanage_NumberDetailList.js"></script>
    <script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincApp_ExamineStateList.js"></script>
    <script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_EntList.js"></script>
    <script type="text/javascript" >
    $(function(){
        myCustomScrollbar({
            obj : "#content", //页面内容绑定的id
            url : webPath+"/mfBusInvoicemanage/findByPageAjax", //列表数据查询的url
            //  tableId : "tableinvoiceFinc", //列表数据查询的table编号
            tableId : "tablemfbusinvoicemanage", //列表数据查询的table编号
            tableType : "tableTag", //table所需解析标签的种类
            pageSize:30, //加载默认行数(不填为系统默认行数)
            topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
        });
        /*MfBusInvoicemanageFinc_List.init();*/
    });
</script>
</head>
<%--<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12 column">
            <div class="btn-div" >
                <dhcc:pmsTag pmsId="cus-add-button">
                <button  style="float:left" type="button" class="btn btn-primary" onclick="MfBusInvoicemanageFinc_List.getForm();">新增</button>
                </dhcc:pmsTag>
                <div >
                <dhcc:pmsTag pmsId="cus-add-button">
                <button  style="margin-left:10px" type="button" class="btn btn-primary" onclick="MfBusInvoicemanageFinc_List.getDownload();">下载</button>
                  <span class="top-title" style="margin-left:465px">发票管理</span>
                </dhcc:pmsTag>
                </div>
                mfBusFincApp
             &lt;%&ndash;   <div class="col-md-2 text-center">
                </div>
                <div class="col-md-8 text-center">
                    &lt;%&ndash;<span class="top-title">发票管理</span>&ndash;%&gt;
                </div>&ndash;%&gt;
            </div>
            <div class="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/发票号码"/>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div id="content" class="table_content"  style="height: auto;">
            </div>
        </div>
    </div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>--%>


<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12 column">
            <div class="btn-div">
                <dhcc:pmsTag pmsId="apply_invoice">
                    <button  style="float:left" type="button" class="btn btn-primary" onclick="MfBusInvoicemanageFinc_List.getForm();">新增</button>
                </dhcc:pmsTag>
                <dhcc:pmsTag pmsId="down_invoice">
                    <button  style="margin-left:10px" type="button" class="btn btn-primary" onclick="MfBusInvoicemanageFinc_List.getDownload();">下载</button>
                    <span class="top-title" style="margin-left:465px">发票管理</span>
                </dhcc:pmsTag>
            </div>
            <div class="search-div" id="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/发票号码"/>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div id="content" class="table_content"  style="height: auto;">
            </div>
        </div>
    </div>
</div>
<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
    /*我的筛选加载的json*/
    filter_dic = [
        {
            "optName" : "发票类型",
            "parm" : ${invoiceTypeJsonArray},
            "optCode" : "invoiceType",
            "dicType" : "y_n"
        },{
            "optName": "开票时间",
            "parm": [],
            "optCode":"regTime",
            "dicType":"date"
        },{
            "optName": "审批状态",
            "parm": ${invoiceRetypeJsonArray},
            "optCode":"approveResult",
            "dicType":"y_n"
        },{
            "optName": "发票状态",
            "parm": ${invoiceStatusJsonArray},
            "optCode":"invoiceStatus",
            "dicType":"y_n"
        }
    ];
</script>
</html>
