<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<script type="text/javascript" src="${webPath}/component/pact/repay/js/MfBusRepayRecheck_List.js"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfRepayHistory/getRepayRecheckList",//列表数据查询的url
			    	tableId:"tablerepayRecheckList",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options,data){
						var val ="";
					    $(".filter-val").each(function(){
					    	var myfilter = eval($(this).find("input[type=hidden]").val());
							if(myfilter!=""){
								val = myfilter[0].value;
							}
						});
						var tit = "";
						if("0"==val){
							tit = "checkAll,cusName,kindName,fincShowId,recvAmt,regDate,buttonCol";
							$('#batchRe_btn').attr('disabled', false).show();
							$('#batch_Print').attr('disabled', true).hide();
                            $('#batch_Print_intst').attr('disabled', true).hide();
							
						}else if("1"==val){
							tit = "checkAll,cusName,kindName,fincShowId,recvAmt,regDate,buttonCol";
							$('#batchRe_btn').attr('disabled', false).hide();
							$('#batch_Print').attr('disabled', false).show();
                            $('#batch_Print_intst').attr('disabled', false).show();
						}
						else{
							tit = "cusName,kindName,fincShowId,recvAmt,regDate,buttonCol";
							$('#batchRe_btn').attr('disabled', false).hide();
							$('#batch_Print').attr('disabled', false).hide();
                            $('#batch_Print_intst').attr('disabled', true).hide();
						}
						$(".search-title").find("span").attr("value", tit);
						showTable(false, '');
						
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			    addCheckAllEvent();
			    
			 });
			
			
			
			//去除表头 点击事件 换成 全选事件
			var isCheckAll = false;
			function addCheckAllEvent() {
			 	$(".table-float-head").delegate("th:first-child","click",function(){
					if (isCheckAll) {
						$(".review_list").find(':checkbox').each(function() {
							this.checked = false;
						});
						isCheckAll = false;
					} else {
						$(".review_list").find(':checkbox').each(function() {
							this.checked = true;
						});
						isCheckAll = true;
					}
				});
			}
            function getDetailPage(obj,url){
                if(url.substr(0,1)=="/"){
                    url =webPath + url;
                }else{
                    url =webPath + "/" + url;
                }
                top.window.openBigForm(url,"还款历史明细",function(){});
            }
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-primary" id="batchRe_btn" onclick="busRecheck();">批量复核</button>
					<button type="button" class="btn btn-primary" id="batch_Print" onclick="batchPrint();">批量打印还款凭证</button>
					<button type="button" class="btn btn-primary" id="batch_Print_intst" onclick="batchPrintIntst();">批量打印利息单</button>
				</div>
				<div class="search-title hidden"><span value=""></span></div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content review_list"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	    <script type="text/javascript">filter_dic =[{"optCode":"cusName","optName":"客户名称","dicType":"val"},
        	 {"optCode":"recheckSts","optName":"还款复核状态0.未复核；1.已复核","parm":[{"optName":"未复核","optCode":"0"},{"optName":"已复核","optCode":"1"}],"dicType":"y_n"}];
         </script>
</html>
