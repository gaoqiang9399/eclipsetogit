<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <style>
        #tablist{
            table-layout:fixed
        }
        #tablist td {
            overflow: hidden;
            white-space: nowrap;
            word-break:keep-all;
            text-overflow:ellipsis;
        }
    </style>
    <script type="text/javascript" >
        $(function(){
            myCustomScrollbar({
                obj:"#content",//页面内容绑定的id
                url:webPath+"/mfBusEntrustedPayment/getListPageAjax",//列表数据查询的url
                tableId:"tableentrustedPayment0001",//列表数据查询的table编号
                tableType:"thirdTableTag",//table所需解析标签的种类
                pageSize:30//加载默认行数(不填为系统默认行数)
            });
        });
        function fkDaYinPrint(obj,url){
          //将方式设置为还款
            top.createShowDialog(url,"选择打印方式",'50','50',function(){
            });
        }
    function eqRegister(obj,url){
        top.openBigForm(url,"受托登记", function(){
            updateTableData();
        });
    }
        function eqDetail(obj,url){
            top.openBigForm(url,"受托登记", function(){
                updateTableData();
            });
        }

        function getDetailPage(obj,url){
            if(url.substr(0,1)=="/"){
                url =webPath + url;
            }else{
                url =webPath + "/" + url;
            }
            top.LoadingAnimate.start();
            top.window.openBigForm (url,'',function(){
            });
            event.stopPropagation();
        }

        function getCusById(url){
            top.window.openBigForm(webPath + url,'客户详情',function(){
            });
        }

        function getPactById(url){
            debugger;
            top.window.openBigForm(webPath + url,'项目详情',function(){
            });

        }
    </script>
</head>
<body class="overflowHidden">
<div class="container">
    <div class="row clearfix bg-white tabCont">
        <div class="col-md-12 column">
            <div class="search-div">
                <jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
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
            "optName": "支付状态",
            "parm": [{
                "optName" : "支付中",
                "optCode" : "0"
            }, {
                "optName" : "支付成功",
                "optCode" : "1"
            }, {
                "optName" : "支付失败",
                "optCode" : "2"
            }, {
                "optName" : "等待支付",
                "optCode" : "3"
            }],
            "optCode":"payState",
            "dicType":"y_n"
        }, {
            "optName": "审批状态",
            "parm": [{
                "optName" : "未审批",
                "optCode" : "0"
            },{
                "optName" : "审批中",
                "optCode" : "1"
            },{
                "optName" : "审批通过",
                "optCode" : "2"
            }],
            "optCode":"appState",
            "dicType":"y_n"
        }
    ];

</script>
</html>
