<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>采购销售费用清单</title>
		<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
		<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
		<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
		<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/js/Pss.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssFund.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssBuySaleExpBill_List.js"></script>
		<script type="text/javascript" src="<%=webPath %>/component/pss/fund/js/PssBuySaleExpBill_Input.js"></script>
		<script type="text/javascript">
			var ajaxData = '${ajaxData}';
		    ajaxData =JSON.parse(ajaxData);
		    var jsonArr = '${dataMap.jsonArr}';
		    var sourceBillNo = '${dataMap.sourceBillNo}';
		    
			$(function() {
				pssBuySaleExpBillInput.init();
				
			});
			
			window.onresize = function(){
				setTimeout(function(){
					$('.pss_detail_list').css('height', 'auto');
				    $('.pss_detail_list #mCSB_1').css('height', 'auto');
				    $('.pss_detail_list #mCSB_2').css('height', 'auto');
				    $('.table-float-head').remove();
				},20);
			};
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="row clearfix bg-white tabCont">
					<div class="pss-bigform-table">
						<div id="content" class="table_content pss_detail_list">
						</div>
					</div>
					<div class="pss-bigform-form">
						<div class="bootstarpTag">
							<form method="post" enctype="multipart/form-data" id="formpssbuysaleexpbill0002" theme="simple" name="operform" action="${webPath}/pssBuySaleExpBill/insertAjax">
								<dhcc:bootstarpTag property="formpssbuysaleexpbill0002" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="确定" action="确定" onclick="pssBuySaleExpBillInput.confirmPssBuySaleExp();"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose_showDialog();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>