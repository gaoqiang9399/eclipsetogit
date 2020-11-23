<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cusNo = (String)request.getParameter("cusNo");
	String creditAppId = (String)request.getParameter("creditAppId");
	String busEntrance = (String)request.getParameter("busEntrance");
	String contextPath = request.getContextPath();
%>
<script type="text/javascript">
var cusNo = '<%=cusNo%>';
var creditAppId = '<%=creditAppId%>';
var busEntrance = '<%=busEntrance%>';
var htmlStr="";
	$(function(){
		if(typeof(creditAppId) != "undefined" && creditAppId != "null" && creditAppId != "" && creditAppId != "@creditAppId@"){
			$.ajax({
				url:webPath+"/mfCusCreditApply/getCreditInfoAjax",
				data:{creditAppId:creditAppId},
				type:"POST",
				dataType:"json",
				success:function(data) {
					if(data.flag == 'success'){
						var creditInfo = "";
						var creditAmt = "";
						var creditTerm = "";
						var creditSts = "";
						var cusName = "";
						if(data.creditType == '1'){
							creditInfo = data.mfCusCreditApply;
							creditAmt = creditInfo.creditSum/10000;
							creditTerm = creditInfo.creditTerm;
						}else{
							creditInfo = data.mfCusCreditAdjustApply;
							creditAmt = creditInfo.adjCreditSum/10000;
							creditTerm = creditInfo.adjCreditTerm;
						}
						cusName = creditInfo.cusName;
						creditSts = creditInfo.creditSts;
						htmlStr = '<div class="col-xs-3 col-md-3 column">'
								+ '<button type="button" class="btn btn-font-app padding_left_15" onclick="getCusCreditInfo(\''+ creditAppId + '\');">'
								+ '<i class="i i-applyinfo font-icon"></i>'
								+ '<div class="font-text">授信信息</div>' + '</button>' + '</div>';
						htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
									+ '<div class="row clearfix"><div class="col-xs-10 col-md-10 column margin_top_20">'
						htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getCusCreditInfo(\''+ creditAppId + '\');" title="'+ cusName + '">' + cusName + '</button>';
						htmlStr = htmlStr
								+ '</div><div class="col-xs-2 col-md-2 column">'
								+ '<button type="button" class="btn btn-font-qiehuan"  style="margin-top: -5px;" onclick="getCusCreditInfo(\''+ creditAppId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
								+ '</div></div>';
						var unitStr = "个月";
						htmlStr = htmlStr + '<p>'
								+ '<span class="content-span"><i class="i i-rmb"></i>'+ creditAmt+ '</span><span class="unit-span">万</span>'
								+ '<span class="content-span"><i class="i i-richengshezhi"></i>'+ creditTerm + '</span>' + '<span class="unit-span">'+ unitStr + '</span>'
								+ '</p>' 
								+ '</div>';
						$("#cusCreditInfo").html(htmlStr);
					}
				},
				error:function() {
					alert(top.getMessage("FAILED_SAVE"),0);
				}
			});
		}else{
				htmlStr = '<div id="pleInfono" class="row clearfix no-content show">'
					+ '<div class="col-xs-3 col-md-3 column">'
					+ '<button type="button" class="btn btn-font-none btn-link padding_left_15" onclick="goAddCredit()" >'
					+ '<i class="i i-pledge font-icon" id="titleLi"></i>'
					+ '<div class="font-text" id="title">授信信息</div>'
					+ '</button>'
					+ '</div>'
					+ '<div class="col-xs-9 col-md-9 column no-content-tip">'
					+ '<span id="noPledge" class="btn btn-link" onclick="goAddCredit()">暂无授信信息</span>'
					+ '</div>'
					+ '</div>';
			$("#cusCreditInfo").html(htmlStr);
		}
		
		
	});
	
	function getCusCreditInfo(creditAppId){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfCusCreditApply/getCusCreditView?cusNo="+cusNo+"&creditAppId="+creditAppId+"&busEntrance=credit"+"&entrance=credit";
	}
	
	// 跳转到新增授信界面
	function goAddCredit(){
		window.location.href = webPath+"/mfCusCreditApply/creditInitInput";
	}
</script>

<!--客户摘要信息-->
<div id="cusCreditInfo"
	class="row clearfix has-content padding_bottom_20 padding_top_10">
</div>
<div class="row clearfix" id="creditLineDiv">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>


