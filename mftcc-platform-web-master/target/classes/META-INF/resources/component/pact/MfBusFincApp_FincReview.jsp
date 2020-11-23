<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/component/include/common.jsp"%>
<%
	String wkfAppId = (String)request.getParameter("wkfAppId");
	String taskId = (String)request.getParameter("taskId");
	String appId = (String)request.getParameter("appId");
%>
<!DOCTYPE html>
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src="${webPath}/component/pact/js/MfBusFincApp_FincReview.js"></script>
		<style type="text/css">
		
		.bgColor .title h4 {
	font-size: 14px;
}


#addPlan {
	border: 1px solid #32b5cb;
}

#addPlan:hover {
	background: #009db7;
}

#cancelPlan {
	background: #fff;
	color: #000;
	border: 1px solid #d2d3d6;
}

#cancelPlan:hover {
	background: #e7e7e7;
}

.addRow {
	background: rgba(0, 0, 0, 0) url("component/pms/imgs/tipsico.png")
		no-repeat scroll 1px -39px;
	height: 20px;
	width: 20px;
}

.delRow {
	background: rgba(0, 0, 0, 0) url("component/pms/imgs/tipsico.png")
		no-repeat scroll 1px -19px;
	height: 20px;
	width: 20px;
	margin-left: 10px;
}

.btn {
	padding: 6px 10px;
}
		
		</style>
		<script type="text/javascript">
			var docParm = "cusNo=${mfBusPact.cusNo}&relNo=${mfBusPact.appId}&scNo=${scNo}";// 查询文档信息的url的参数
			var wkfAppId='<%=wkfAppId%>';
			var taskId='<%=taskId%>';
			var appId='<%=appId%>';
			$(function(){
				planInput();
			});
			function fincReviewAjax(obj){
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray());
					
					var checkFlag = true;
					var datas =[];
					$("#replan-div").find(".ls_list tbody tr").each(function(index){
						var termNum = $(this).find("input[name=termNum]").val();
						var beginDate = $(this).find("input[name=beginDate]").val().replace(/-/g,"");
						var endDate = $(this).find("input[name=endDate]").val().replace(/-/g,"");
						var returnSum = $(this).find("input[name=returnSum]").val();
						var rateIncome = $(this).find("input[name=rateIncome]").val();
						if(!termNum || !beginDate || !endDate || !returnSum || !rateIncome){
							checkFlag = false;
							return false;
						}
						var planObj ={};
						planObj.termNum=termNum;
						planObj.beginDate=beginDate;
						planObj.endDate=endDate;
						planObj.returnSum=returnSum.replace(/,/g,"");
						planObj.rateIncome=rateIncome.replace(/,/g,"");
						datas.push(planObj);
					});
					if(datas.length==0 || !checkFlag){
						alert("请完善收款计划",0);
						return false;
					}
					var listParam = JSON.stringify(datas);
					
					var datas1 =[];
					checkFlag = true;
					var indexTh = $("#busFee-div thead tr").find("th[name=realFee]").index();
					$("#busFee-div").find(".ls_list tbody tr").each(function(index){
						$tdThis = $(this).find("td").eq(indexTh);
						if($tdThis.find("input[name=realFee]").val() == "" || isNaN($tdThis.find("input[name=realFee]").val()) ){
							checkFlag = false;
							return false;
						}
						var busFee ={};
						busFee.itemNo = $tdThis.find("input[name=itemNo]").val();
						busFee.realFee = $tdThis.find("input[name=realFee]").val();
						busFee.shouldFee = $tdThis.find("input[name=shouldFee]").val();
						busFee.id = $tdThis.find("input[name=id]").val();
						datas1.push(busFee);
					});
// 					if(!checkFlag){
// 						alert("请填写正确的费用实收金额",0);
// 						return false;
// 					}
					
					LoadingAnimate.start();
					jQuery.ajax({
							url:url,
							data:{ajaxData:dataParam,ajaxList:listParam,ajaxDataList1:JSON.stringify(datas1),wkfAppId:wkfAppId,taskId:taskId,appId:appId},
							type:"POST",
							dataType:"json",
							beforeSend:function(){  
							},success:function(data){
								LoadingAnimate.stop();
								if(data.flag == "success"){ 
									 top.flag=true;
									 top.putoutReviewFlag=true;
									 top.wkfRepayId = data.wkfRepayId;
									 top.tableHtml = data.tableHtml;
									 myclose_click();
								}
							},error:function(data){
								 LoadingAnimate.stop();
								 alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
						});
					
					}
			}
			
			
			function selectBankAcc(){
				var cusNo = $("input[name='cusNo']").val();
				selectBankAccDialog(getBankAccInfoArtDialog,cusNo);
			}
			function getBankAccInfoArtDialog(accountInfo){
				$("input[name='incomAccount']").val(accountInfo.accountNo);
				$("input[name='incomBank']").val(accountInfo.bank);
				
			};
			
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						theme:"minimal-dark",
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			});
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form method="post" id="fincReviewForm" theme="simple" name="operform" action="${webPath}/mfBusFincApp/fincReviewAjax">
							<dhcc:bootstarpTag property="formfincapp0004" mode="query"/>
						</form>
					</div>
					<div class="feeInfo showOrHide">
								<div class="list-table">
									<span>收款计划</span>
									<div class="content_table" id="replan-div">
										<dhcc:tableTag property="tablerepayplan0002" paginate="mfBusRepayPlanList" head="true"></dhcc:tableTag>
									</div>
								</div>
							</div>
			<div class="feeInfo showOrHide">
				<div class="list-table">
					<span>费用收取</span>
					<div class="content_table" id="busfee-div">
						<table width="100%" border="0" align="center" cellspacing="1" class="ls_list">
							<thead> 
								<tr> 
									<th scope="col" align="center" name="itemName">费用名称</th>
									<th scope="col" align="center" name="shouldFee">应收金额</th>
									<th scope="col" align="center" name="realFee">实收金额</th>
								</tr>
							</thead>
							 <tbody> 
								 <c:if test="${fn:length(mfBusAppFeeList)>0}">
								 	<c:forEach items="${mfBusAppFeeList }" varStatus="status" var="mfBusAppFeeThis" >
									 <tr>
										<td align="center">${mfBusAppFeeThis.itemName }</td>
										<td align="center">${mfBusAppFeeThis.rateScale }</td>
										<td align="center">
										<input name="realFee" value="${mfBusAppFeeThis.rateScale }"   datatype='12'  type='text' onblur="num_validate('realFee');"  onkeyup='toMoney(this);' onmousedown='enterKey()' onkeydown='enterKey();'  style='width:auto;text-align: center;height:30px;' >
											<input name="itemNo" value="${mfBusAppFeeThis.itemNo }" style="display: none;">
											<input name="shouldFee" value="${mfBusAppFeeThis.rateScale }" style="display: none;">
											<input name="id" value="${mfBusAppFeeThis.id }" style="display: none;">
										</td>
									</tr>
								 	</c:forEach>
								 </c:if>
								 <c:if test="${fn:length(mfBusAppFeeList)<=0}">
								 	<tr><td style="text-align: center;" colspan="3">暂无数据</td></tr>
								 </c:if>
							 </tbody> 
						</table>	
					</div>
				</div>
			</div>
		
			<div class="row clearfix">
				<div class="col-xs-12 column">
					<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
				</div>
			</div>
		</div>
		<div style="clear:both;"></div>
	</div>
		<div class="formRowCenter">
   			<dhcc:thirdButton value="保存" action="保存"  onclick="fincReviewAjax('#fincReviewForm');"></dhcc:thirdButton>
   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
   		</div>	
	</div>
</body>
<script type="text/javascript">
	$(document.body).height($(window).height());
</script>
</html>
