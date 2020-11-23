<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<style type="text/css">
		.itemS1{
			padding-left: 5px;
		}
		.itemS2{
			padding-left: 30px;
		}
		.itemS3{
			padding-left: 45px;
		}
		.itemS4{
			padding-left: 55px;
		}
		.itemS5{
			padding-left: 65px;
		}
		.itemS6{
			padding-left: 75px;
		}
		.strong{
			font-weight: bold;
		}
		
		</style>
		<script type="text/javascript" >
		var reportTypeId = '${dataMap.reportTypeId}';
		var basePValue='${dataMap.basePValue}';
		var hcUseFlag = '${dataMap.hcUseFlag}';
		$(function(){
			if('001'==reportTypeId){
				//加载列表
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusReportItem/getReportViewListAjax",//列表数据查询的url
			    	tableId:"tablefinreport0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	data:{},//指定参数
			    	ownHeight:true,
			    	callback:function(){
			    		getTableTr();//显示行次
			    		changeTableTh001();
			    		$('#tablist tbody td').find('a').each(function(){
			    			var text = $(this).html();
			    			$(this).after(text).remove();
			    		});
			    		//getTableTr();//显示行次
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			}else{
				//加载列表
				myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusReportItem/getReportViewListAjax",//列表数据查询的url
			    	tableId:"tablefinreport0002",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	data:{},//指定参数
			    	ownHeight:true,
			    	callback:function(){
			    		getTableTr();//显示行次
			    		changeTableTh();
			    		$('#tablist tbody td').find('a').each(function(){
			    			var text = $(this).html();
			    			$(this).after(text).remove();
			    		});
			    		
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			}
			$('.footer_loader').remove();
			
			//$('.table-float-head').remove();
			//table-float-th
			$(".table-float-th").unbind();

		});
		
		//获取 查询条件（方法名固定写法）
		function getFilterValArr(){ 
			return JSON.stringify($('#cwReportForm').serializeArray());
		}
		//yht修改bug(资产负债表表头)
		function changeTableTh001(){
			if(basePValue!='1@2'){
				$("th[name=itemAAmt]").html("期末数");
				$(".table-float-th:eq(1)").html("期末数");
				$("th[name=itemAAmt2]").html("期末数");
                $(".table-float-th:eq(4)").html("期末数");
                $("th[name=itemBAmt]").html("年初数");
                $(".table-float-th:eq(2)").html("年初数");
                $("th[name=itemBAmt2]").html("年初数");
				$(".table-float-th:eq(5)").html("年初数");
			}
		}
		//(其他报表表头)
		function changeTableTh(){
		    if ('002' == reportTypeId){
                $("th[name=itemAAmt]").html("本年数");
                $(".table-float-th:eq(1)").html("本年数");
                $("th[name=itemBAmt]").html("上年数");
                $(".table-float-th:eq(2)").html("上年数");
            }else if ('003' == reportTypeId) {
                $("th[name=itemAAmt]").html("本期数");
                $(".table-float-th:eq(1)").html("本期数");
                $("th[name=itemBAmt]").html("本年数");
                $(".table-float-th:eq(2)").html("本年数");
            }else {

            }
		}
		</script>
	</head>
<body>
	<!--标记点 未后退准备-->
	<dhcc:markPoint markPointName="CwVoucherMst_List" />
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div">
					<div class="col-xs-4 column mysearch-div" id="pills">
						<form id="cwReportForm">
							<input type="hidden" name="reportTypeId" id="reportTypeId" value="${dataMap.reportTypeId}">
						</form>
					</div>
				</div>
			</div>
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content cw_voucher_list"
					style="height: auto;">
					<!--待定是否放置自定义table标签?-->
<%-- 					<c:if test='${dataMap.reportTypeId=="001"}'> --%>
<%-- 						<dhcc:tableTag property="tablefinreport0001" --%>
<%-- 							paginate="CwSearchReportList" head="true"></dhcc:tableTag> --%>
<%-- 					</c:if> --%>
<%-- 					<c:if test='${dataMap.reportTypeId!="001"}'> --%>
<%-- 						<dhcc:tableTag property="tablefinreport0002" --%>
<%-- 							paginate="CwSearchReportList" head="true"></dhcc:tableTag> --%>
<%-- 					</c:if> --%>
				</div>
			</div>
		</div>
	</div>
	<div class="search-title hidden"><span value=""></span></div>
</body>
<script type="text/javascript">
//利润表显示
	function getTableTr(){
		var tit = "";
		if(reportTypeId=='001'){
			tit = "showName,zcTr,itemAAmt,itemBAmt,fzTr,itemAAmt2,itemBAmt2,showName2";
			
		}else if(reportTypeId=='003'){
   			tit = "showName,lrTr,itemAAmt,itemBAmt";
   			if(hcUseFlag==0){//禁用行次
				tit = "showName,itemAAmt,itemBAmt";//显示
			}
			//$('#batchRe_btn').removeAttr('disabled').addClass('btn-primary');
   		}else{
   			tit = "showName,xjllTr,itemAAmt,itemBAmt";
   			if(hcUseFlag==0){//禁用行次
				tit = "showName,itemAAmt,itemBAmt";//显示
			}
			//$('#batchRe_btn').attr('disabled', true).removeClass('btn-primary');
   		}
   		
   		 if(hcUseFlag!=1){
   			tit = "showName,itemAAmt,itemBAmt,itemAAmt2,itemBAmt2,showName2";
   		} 
   		
	$(".search-title").find("span").attr("value", tit);
		showTable(false, ''); 
	}
</script>
</html>