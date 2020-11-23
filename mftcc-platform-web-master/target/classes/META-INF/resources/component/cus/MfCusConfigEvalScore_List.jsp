<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src="${webPath}/component/cus/js/MfCusConfig.js"></script>
	<script type="text/javascript" >
        var cusType='${cusType}';
        $(function(){
            myCustomScrollbar({
                obj:"#content",//页面内容绑定的id
                url:webPath+"/evalScoreGradeConfig/getEvalScoreGradeConfig?cusType="+cusType,//列表数据查询的url
                tableId:"tableeval0008",//列表数据查询的table编号
                tableType:"thirdTableTag",//table所需解析标签的种类
                pageSize : 30,//加载默认行数(不填为系统默认行数)
                topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
            });
        });

        //导出方法
        function  outBank(){
            window.location.href=webPath+"/mfBusFincApp/findBankAgriculturalAjaxExcel?tableId=tablemfBusFincAppBank001";
        };

        function   mfBusFincBankDetail(obj,url){
            if(url.substr(0,1)=="/"){
                url =webPath + url;
            }else{
                url =webPath + "/" + url;
            }
            top.openBigForm(url,"农行代扣详情查询", function(){});
        }


        //查询客户详情
        function getDetailCus(obj,url){
            if(url.substr(0,1)=="/"){
                url =webPath + url;
            }else{
                url =webPath + "/" + url;
            }
            window.location.href=url;
        };
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<%--<div class="col-md-2">
					<button type="button" class="btn btn-primary" onclick="outBank()">新增</button>
				</div>--%>
				<div class="col-md-12 text-center">
					<span class="top-title">评级客户分数等级列表</span>
				</div>
			</div>
			<div class="col-xs-9 column">
				<%--<button type="button" class="btn btn-primary pull-left" onclick="finForm_input('/cusFinForm/input');">新增</button>--%>
				<ol class="breadcrumb pull-left">
					<li><a href="${webPath}/mfCusFormConfig/getMfCusConfig">客户设置</a></li>
					<li class="active">分数等级 </li>
				</ol>
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
<!--页面显示区域-->
<div id="content" class="table_content"  style="height: auto;">
</div>
<%--<%@ include file="/component/include/PmsUserFilter.jsp"%>--%>
</body>
<script type="text/javascript">
    filter_dic = [

        {
            "optCode" : "regTime",
            "optName" : "操作时间",
            "parm" :  "",
            "dicType" : "date"
        }


    ];
</script>
</html>
