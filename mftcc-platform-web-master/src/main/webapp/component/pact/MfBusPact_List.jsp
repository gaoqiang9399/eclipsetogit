<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			var queryType = "${queryType}";
			var url = webPath+"/mfBusPact/findByPageAjax";
			var tableId = "tablepactfinc0001";
			if(typeof(queryType) !="undefined" && queryType == "busQuery"){//业务查询中数据即贷后数据(从渠道操作员进入系统)
				tableId = "tablepactfinclist_trench";
			}
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:url,//列表数据查询的url
			    	tableId:tableId,//列表数据查询的table编号
			    	tableType:"tableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{pactSts:"8"}//指定参数，过滤掉已经封挡的数据
			    });
			 });
			function exportExcel(){
				window.top.location.href = encodeURI(url +  "Excel?tableId=" + tableId);
			};
			function batchRepayment(){
                top.openBigForm(webPath+"/mfBusFincApp/batchRepayment","批量还款", function(){
                });
			};
			/**跳转至还款页面**/
			function toRepayment(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			  	location.href = url;
				event.stopPropagation();
			}
			
			function getDetailPage(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.LoadingAnimate.start();		
				window.location.href=url;
				event.stopPropagation();
			}
			
			
			var timeFunc=null;
			//监听ctrl键
			document.onkeydown=function(event){
				var e = event || window.event || arguments.callee.caller.arguments[0];
				//若点击了ctrl 键则 清除timeFunc
				if(e && e.ctrlKey){ 
					clearTimeout(timeFunc);
				}
			}; 
			
			function trClick(url){		
				clearTimeout(timeFunc);
				timeFunc=setTimeout(function(){
					if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
					top.LoadingAnimate.start();		
					window.location.href=url;
				}, 300);
			}
				 
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<dhcc:pmsTag pmsId="excel-busQuery">
					<div class="btn-div">
						<button type="button" class="btn cancel" onclick="exportExcel();">导出</button>
					</div>
				</dhcc:pmsTag>
				<dhcc:pmsTag pmsId="batch-repayment">
					<div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="batchRepayment();">批量还款</button>
					</div>
				</dhcc:pmsTag>
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
	filter_dic = [ {
		"optName" : "借据金额",
		"parm" : [],
		"optCode" : "putoutAmt",
		"dicType" : "num"
	}, {
		"optName" : "借据期限",
		"parm" : [],
		"optCode" : "termMonth",
		"dicType" : "num"
	}, {
		"optName" : "借据开始日期",
		"parm" : [],
		"optCode" : "intstBeginDate",
		"dicType" : "date"
	}, {
		"optName" : "借据到期日期",
		"parm" : [],
		"optCode" : "intstEndDate",
		"dicType" : "date"
	}, {
		"optName" : "合同到期日期",
		"optCode" : "pactEndDate",
		"parm" : [],
		"dicType" : "date"
	}, {
		"optCode" : "overdueSts",
		"optName" : "逾期状态",
		"parm" : [ {
			"optName" : "未逾期",
			"optCode" : "0"
		}, {
			"optName" : "已逾期",
			"optCode" : "1"
		} ],
		"dicType" : "y_n"
	}, {
		"optCode" : "fincSts",
		"optName" : "借据状态",
		"parm" : ${fincStsJsonArray},
		"dicType" : "y_n"
	}/*, {
		"optCode" : "overDays",
		"optName" : "逾期天数",
		"parm" : [],
		"dicType" : "num"
	} */ ];
</script>
</html>
