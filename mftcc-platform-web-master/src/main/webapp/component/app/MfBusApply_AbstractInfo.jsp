<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String cusNo = (String)request.getParameter("cusNo");
	String pleId = (String)request.getParameter("pleId");
	String frompage = (String)request.getParameter("frompage");
	String busEntrance = (String)request.getParameter("busEntrance");
	String fincId = (String)request.getParameter("fincId") == null ?"":request.getParameter("fincId");
	String creditId = (String)request.getParameter("creditId");
	if(creditId==null){
		creditId="";
	}
	String bidissueId = (String)request.getParameter("bidissueId");
	if(bidissueId==null){
		bidissueId="";
	}
	
%>
<script type="text/javascript">
var cusNo = '<%=cusNo%>';
var appId = '${appId}';
var frompage = '<%=frompage%>';
var busEntrance = '<%=busEntrance%>';
var fincId = '<%=fincId%>';
var creditId = '<%=creditId%>';
var bidissueId = '<%=bidissueId%>';
$(function(){
if(creditId!=""){
   $.ajax({
			url:webPath+"/p2pCreditorPoolManage/getCreditorInfoAjax",
			data:{creditId:creditId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){

			},success:function(data) {
				if (data.flag == "success") {
					if(data.showFlag=="none"){
						$("#busInfono").html("出错了");
						
					}else{
						// 填充业务信息
						setCreditorInfo(data);						
						$("#busInfo").removeClass("hide");
						$("#busInfo").addClass("show");
					}
				}
				
			},
			error:function() {
	
			}
		});

}else if(bidissueId!=''){
	   $.ajax({
			url:webPath+"/p2pBidissueManage/getBidiInfoAjax",
			data:{bidissueId:bidissueId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){

			},success:function(data) {
				if (data.flag == "success") {
					if(data.showFlag=="none"){
						$("#busInfono").html("出错了");
					}else{
						// 填充业务信息
						setBidiInfo(data);						
						$("#busInfo").removeClass("hide");
						$("#busInfo").addClass("show");
					}
				}
				
			},
			error:function() {
					alert('error')
			}
		});
} else{

	if(appId==""){
		$("#busInfono").removeClass("hide");
		$("#busInfono").addClass("show");
	}else{
		$.ajax({
			url:webPath+"/mfBusApply/getBusInfoAjax",
			data:{appId:appId,cusNo:cusNo,fincId:fincId,busEntrance:busEntrance},
			type:"POST",
			dataType:"json",
			beforeSend:function(){

			},success:function(data) {
				if (data.flag == "success") {
					if(data.showFlag=="none"){
						$("#busInfono").removeClass("hide");
						$("#busInfono").addClass("show");
					}else{
						// 填充业务信息
						setBusFincInfo(data);
// 						if(busEntrance=='3'||busEntrance=='6'){
// 						}else{
// 							setBusInfo(data);
// 						}
						$("#busInfo").removeClass("hide");
						$("#busInfo").addClass("show");
					}
				}
				
			},
			error:function() {
	
			}
		});
	}
}
// 填充业务信息
function setBusInfo(data) {
	var rateUnit = data.rateUnit;
	var busInfoObj = data.mfbusInfo;
	var mfBusApplyListTmp = data.mfBusApplyList;
	var appName = busInfoObj.appName;
	if (appName.length > 8) {
		appName = appName.substring(0, 8) + "...";
	}
	var htmlStr="";
	var busId = busInfoObj.appId;	
	if(data.showFlag=="apply"){		
		htmlStr = '<div class="col-xs-3 col-md-3 column">'
				+ '<button type="button" class="btn btn-font-app padding_left_15" onclick="getBusInfo(\''+ busId + '\');">'
				+ '<i class="i i-applyinfo font-icon"></i>'
				+ '<div class="font-text">申请信息</div>' + '</button>' + '</div>';
	}else if(data.showFlag=="pact"){
		var busSts = busInfoObj.pactSts;
		if(busEntrance=='3'||busEntrance=='6'){
			htmlStr = '<div class="col-xs-3 col-md-3 column">'
					+ '<button type="button" class="btn btn-font-pact padding_left_15" onclick="getPactInfo(\''+ busId + '\',\''+busSts+'\');">'
					+ '<i class="i i-pact font-icon"></i>'
					+ '<div class="font-text">借据信息</div>' + '</button>' + '</div>';
		}else{
			htmlStr = '<div class="col-xs-3 col-md-3 column">'
					+ '<button type="button" class="btn btn-font-pact padding_left_15" onclick="getPactInfo(\''+ busId + '\',\''+busSts+'\');">'
					+ '<i class="i i-pact font-icon"></i>'
					+ '<div class="font-text">合同信息</div>' + '</button>' + '</div>';
		}
	}
	// 如果业务笔数大于3笔
	if (data.mfBusApplyList.length > 3 && frompage!="collateral"){
		if(frompage=="collateral"){//如果主体页面是押品页面，业务不展示多笔业务的情况
			if(data.showFlag=="apply"){
				htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
			}else if(data.showFlag=="pact"){
				var busSts = busInfoObj.pactSts;
				htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\',\''+busSts+'\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
			}
		}else{
			htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix padding_top_20">'
					+ '<span>客户共有 <a  class="moreCnt more-apply-count pointer" onclick="getMultiBusList();">'
					+ (data.mfBusApplyList.length + 1)
					+'</a> 笔在履行业务'
					+ '</span>'
				+ '</div>'
			+ '</div>';	
		}
	} else {
		htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix">'
				+ '<div class="col-xs-10 col-md-10 column">';
		if(frompage=="collateral"){//如果主体页面是押品页面，业务不展示多笔业务的情况
			if(data.showFlag=="apply"){
				htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
			}else if(data.showFlag=="pact"){
				htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\',\''+busSts+'\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
			}
		}else{
			// 如果业务笔数为1条
			if (data.mfBusApplyList.length == 0) {
				if(data.showFlag=="apply"){
					htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
				}else if(data.showFlag=="pact"){
					var busSts = busInfoObj.pactSts;
					htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
				}
			} else { 
				var tmpStr = '';
				$.each(data.mfBusApplyList,function(i, appObj) {
					var appObjName = appObj.appName;
					var len = busInfoObj.appName.length;
					if (appObjName.length > len) {
						appObjName = appObjName.substring(0, len)+ "...";
					}
					tmpStr = tmpStr+ '<li class="more-content-li" onclick="switchBus(\''+ appObj.appId+ '\');">'
							+ '<p class="more-title-p"><span>'+ appObjName+ '</span></p>'
							+ '<p class="more-content-p"><span class="more-span">总金额 '+ appObj.appAmt+ '元</span><span class="more-span">期限 '
							+ appObj.termShow + '</span><span class="more-span">利率 '+ appObj.fincRate + rateUnit +'</span></p>'
							+ '</li>';
				});
				htmlStr = htmlStr
						+ '<div class="btn-group">'
						+ '<button type="button" id="apply-name" class="btn btn-link content-title dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="'
						+ busInfoObj.appName + '">'+ appName
						+ '</button>'
						+ '<button type="button" id="more-apply" class="btn btn-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
						+ '<span class="badge">' + data.mfBusApplyList.length+ '</span>' 
						+ '</button>' 
						+ '<ul class="dropdown-menu">'+ tmpStr + '</ul>' + '</div>';
			}
		}
		if(data.showFlag == "apply"){
			htmlStr = htmlStr
					+ '</div><div class="col-xs-2 col-md-2 column">'
					+ '<button type="button" class="btn btn-font-qiehuan"  style="margin-top: -5px;" onclick="getBusInfo(\''+ busId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
					+ '</div>';
		}else if(data.showFlag == "pact"){
			var busSts = busInfoObj.pactSts;
			htmlStr = htmlStr
					+ '</div><div class="col-xs-2 col-md-2 column">'
					+ '<button type="button" class="btn btn-font-qiehuan" style="margin-top: -5px;" onclick="getPactInfo(\''+ busId + '\',\''+busSts+'\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
					+ '</div>';
		}

		var unitStr = "天";
		if (busInfoObj.termType == "1") {
			unitStr = "个月";
		}
		htmlStr = htmlStr + '<p>'
				+ '<span class="content-span"><i class="i i-rmb"></i>'+ busInfoObj.fincAmt+ '</span><span class="unit-span">万</span>'
				+ '<span class="content-span"><i class="i i-richengshezhi"></i>'+ busInfoObj.term + '</span>' + '<span class="unit-span">'+ unitStr + '</span>'
				+ '<span class="content-span"><i class="i i-tongji1"></i>'+ busInfoObj.fincRateStr+ '</span><span class="unit-span">'+rateUnit+'</span>'
				+ '</p>' 
				+ '</div>';
	}
	$("#busInfo .cont-row").html(htmlStr);
};

// 填充贷后业务信息
function setBusFincInfo(data) {
	var rateUnit = data.rateUnit;
	var busInfoObj = data.mfbusInfo;
	var appName = busInfoObj.appName;
    var kindName = busInfoObj.kindName;
	if (kindName.length > 10) {
        kindName = kindName.substring(0, 10) + "...";
	}
	var htmlStr="";
	var busId = busInfoObj.appId;	
	if(data.showFlag=="apply"){		
		htmlStr = '<div class="col-xs-3 col-md-3 column">'
				+ '<button type="button" class="btn btn-font-app padding_left_15" onclick="getBusInfo(\''+ busId + '\');">'
				+ '<i class="i i-applyinfo font-icon"></i>'
				+ '<div class="font-text">申请信息</div>' + '</button>' + '</div>';
	}else if(data.showFlag=="pact"){
		var busSts = busInfoObj.pactSts;
		if(busEntrance=='3'||busEntrance=='6' || busEntrance=='finc'){
			htmlStr = '<div class="col-xs-3 col-md-3 column">'
					+ '<button type="button" class="btn btn-font-pact padding_left_15" onclick="getPactInfo(\''+ busId + '\',\''+busSts+'\');">'
					+ '<i class="i i-pact font-icon"></i>'
					+ '<div class="font-text">借据信息</div>' + '</button>' + '</div>';
		}else{
			htmlStr = '<div class="col-xs-3 col-md-3 column">'
					+ '<button type="button" class="btn btn-font-pact padding_left_15" onclick="getPactInfo(\''+ busId + '\',\''+busSts+'\');">'
					+ '<i class="i i-pact font-icon"></i>'
					+ '<div class="font-text">合同信息</div>' + '</button>' + '</div>';
		}
	}
	
	htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
			+ '<div class="row clearfix">'
			+ '<div class="col-xs-10 col-md-10 column">';
	// 业务不展示多笔业务的情况
	if(data.showFlag=="apply"){
		htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.kindName + '">' + kindName + '</button>';
		htmlStr = htmlStr
				+ '</div><div class="col-xs-2 col-md-2 column">'
				+ '<button type="button" class="btn btn-font-qiehuan"  style="margin-top: -5px;" onclick="getBusInfo(\''+ busId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>';
	}else{
		var busSts = busInfoObj.pactSts;
		htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\',\''+busSts+'\');" title="'+ busInfoObj.kindName + '">' + kindName + '</button>';
		htmlStr = htmlStr
				+ '</div><div class="col-xs-2 col-md-2 column">'
				+ '<button type="button" class="btn btn-font-qiehuan" style="margin-top: -5px;" onclick="getPactInfo(\''+ busId + '\',\''+busSts+'\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>';
	}

	var unitStr = "天";
	if (busInfoObj.termType == "1") {
		unitStr = "个月";
	}
	htmlStr = htmlStr + '</div><div class="row clearfix margin_top_5"><p>'
			+ '<span class="content-span"><i class="i i-rmb"></i>'+ busInfoObj.fincAmt+ '</span><span class="unit-span">万</span>'
			+ '<span class="content-span"><i class="i i-richengshezhi"></i>'+ busInfoObj.term + '</span>' + '<span class="unit-span">'+ unitStr + '</span>'
			+ '<span class="content-span"><i class="i i-tongji1"></i>'+ busInfoObj.fincRateStr+ '</span><span class="unit-span">'+rateUnit+'</span>'
			+ '</p>' 
			+ '</div>'
			+ '</div>';
			
	<%--htmlStr = htmlStr + '<div class="multi-bus pull-right">'--%>
	<%--		+ '客户共有<span class="moreCnt_apply">申请中业务<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList(\'apply\');">' + data.moreApplyCount + '</a> 笔,</span>'--%>
	<%--		+ '<span class="moreCnt_pact">在履行合同<a class="moreCnt more-pact-count pointer" onclick="getMultiBusList(\'pact\');">' + data.morePactCount + '</a> 笔</span>'--%>
	<%--		+ '<span class="moreCnt_finc">, 在履行借据<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList(\'finc\');">' + data.moreFincCount + '</a> 笔 </span>'--%>
    <%--    + '<span class="moreCnt_finc_finish">, 已结清担保<a class="moreCnt more-finc-finish-count pointer" onclick="getMultiBusList(\'fincFinish\');">' + data.moreFincFinishCount + '</a> 笔 </span>'--%>
    <%--    + '<span class="moreCnt_repay_finish">, 已还款<a class="moreCnt more-repay-count pointer" onclick="getMultiBusList(\'repay\');">${dataMap.moreRepayCount}</a> 次 </span>'--%>
    <%--    + '</div>';--%>

	htmlStr = htmlStr + '<div class="multi-bus pull-right">客户共有<span class="moreCnt_apply">申请中项目<a class="moreCnt more-apply-count pointer" onclick="getMultiBusList(\'apply\');">' + data.moreApplyCount + '</a> 笔,</span>'
	+'<span class="moreCnt_finc"> 在保项目<a class="moreCnt more-finc-count pointer" onclick="getMultiBusList(\'finc\');">' + data.moreFincCount + '</a> 笔 </span>'
	+'<span class="moreCnt_pact">,已放款<a class="moreCnt more-pact-count pointer" onclick="getMultiBusList(\'pact\');">' + data.morePactCount + '</a> 次</span>'
	+'<span class="moreCnt_repay">, 已还款<a class="moreCnt more-repay-count pointer" onclick="getMultiBusList(\'repay\');">' + data.moreReapyCount + '</a> 次 </span>'
	+'<span class="moreCnt_finc_finish">, 已解除<a class="moreCnt more-finc-finish-count pointer"'
	+'onclick="getMultiBusList(\'fincFinish\');">' + data.moreFincFinishCount + '</a> 笔 </span>'
	+"</div>";
	$("#busInfo .cont-row").html(htmlStr);
};
//填充债权信息
function setCreditorInfo(data) {
	var rateUnit = data.rateUnit;
	var creditorInfoObj = data.creditorInfo;
	var creditName = creditorInfoObj.creditName;
	if (creditName.length > 10) {
		creditName = creditName.substring(0, 10) + "...";
	}
	var htmlStr="";
	var creditorId = creditorInfoObj.creditId;	
	if(data.showFlag=="creditor"){		
		htmlStr = '<div class="col-xs-3 col-md-3 column">'
				+ '<button type="button" class="btn btn-font-app padding_left_15" onclick="getCreditorInfo(\''+ creditorId + '\');">'
				+ '<i class="i i-applyinfo font-icon"></i>'
				+ '<div class="font-text">债权信息</div>' + '</button>' + '</div>';
	}
	
	htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
			+ '<div class="row clearfix">'
			+ '<div class="col-xs-10 col-md-10 column">';
	// 业务不展示多笔业务的情况
	if(data.showFlag=="creditor"){
		htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getCreditorInfo(\''+ creditorId + '\');" title="'+ creditorInfoObj.creditName + '">' + creditName + '</button>';
		htmlStr = htmlStr
				+ '</div><div class="col-xs-2 col-md-2 column">'
				+ '<button type="button" class="btn btn-font-qiehuan"  style="margin-top: -5px;" onclick="getCreditorInfo(\''+ creditorId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>';
	}

	var unitStr = "天";
	if (creditorInfoObj.originalTermType == "1") {
		unitStr = "个月";
	}
	htmlStr = htmlStr + '</div><div class="row clearfix margin_top_5"><p>'
			+ '<span class="content-span"><i class="i i-rmb"></i>'+ creditorInfoObj.originalAmt/10000+ '</span><span class="unit-span">万</span>'
			+ '<span class="content-span"><i class="i i-richengshezhi"></i>'+ creditorInfoObj.originalTerm + '</span>' + '<span class="unit-span">'+ unitStr + '</span>'
			+ '<span class="content-span"><i class="i i-tongji1"></i>'+ creditorInfoObj.originalRate+ '</span><span class="unit-span">'+rateUnit+'</span>'
			+ '</p>' 
			+ '</div>'
			+ '</div>';
	$("#busInfo .cont-row").html(htmlStr);
};	
//填充标的信息
function setBidiInfo(data) {
//	var rateUnit = data.rateUnit;
	
	var biDiBean = data.biDiBean;
	var appName = biDiBean.appName;
	var rateUnit = biDiBean.businessrate;
	if (appName.length > 10) {
		appName = appName.substring(0, 10) + "...";
	}
	var htmlStr="";
	var bidissueId = biDiBean.bidissueId;	
	if(data.showFlag=="bidissueId"){		
		htmlStr = '<div class="col-xs-3 col-md-3 column">'
				+ '<button type="button" class="btn btn-font-app padding_left_15" onclick="goBidiInfo(\''+ bidissueId + '\');">'
				+ '<i class="i i-applyinfo font-icon"></i>'
				+ '<div class="font-text">标的信息</div>' + '</button>' + '</div>';
	}
	
	htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
			+ '<div class="row clearfix">'
			+ '<div class="col-xs-10 col-md-10 column">';
	// 业务不展示多笔业务的情况
	if(data.showFlag=="bidissueId"){
		htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="goBidiInfo(\''+ bidissueId + '\');" title="'+ biDiBean.appName + '">' + appName + '</button>';
		htmlStr = htmlStr
				+ '</div><div class="col-xs-2 col-md-2 column">'
				+ '<button type="button" class="btn btn-font-qiehuan"  style="margin-top: -5px;" onclick="goBidiInfo(\''+ bidissueId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
				+ '</div>';
	}

	var unitStr = "天";
	/* if (appName.originalTermType == "1") {
		unitStr = "个月";
	} */
	htmlStr = htmlStr + '</div><div class="row clearfix margin_top_5"><p>'
			+ '<span class="content-span"><i class="i i-rmb"></i>'+ data.applyAmt+ '</span><span class="unit-span">万</span>'
			+ '<span class="content-span"><i class="i i-richengshezhi"></i>'+ biDiBean.termMonth + '</span>' + '<span class="unit-span">'+ '个月' + '</span>'
			+ '<span class="content-span"><i class="i i-tongji1"></i>'+ biDiBean.businessrate+ '</span><span class="unit-span">'+'%'+'</span>'
			+ '</p>' 
			+ '</div>'
			+ '</div>';
	$("#busInfo .cont-row").html(htmlStr);
};	


});

function goBidiInfo(bidissueId){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/p2pBidissueManage/getById?bidissueId="+bidissueId;
}

</script>
<!-- 填写申请信息 -->
<div id="busInfono" class="row clearfix no-content hide">
	<div class="col-xs-3 col-md-3 column">
		<button type="button" class="btn btn-font-none padding_left_15" ;">
			<i class="i i-applyinfo font-icon"></i>
			<div class="font-text">申请信息</div>
		</button>
	</div>
	<%--<div class="col-xs-9 col-md-9 column no-content-link">
		<button class="btn btn-link " onclick="applyInsert();">业务申请>></button>
	</div>--%>
	<div class="col-xs-9 col-md-9 column no-content-tip">
		<span >暂无申请信息</span>
	</div>
</div>
<!-- 展示申请信息 -->
<div id ="busInfo" class="row clearfix has-content hide">
	<div class="row clearfix cont-row">
		<!-- 申请信息 展示区域-->
	</div>
</div>
<div class="row clearfix line-row">
	<div class="col-xs-12 col-md-12 column">
		<div class="line-div"></div>
	</div>
</div>						