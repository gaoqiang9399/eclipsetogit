<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.BizPubParm" %>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/collateral/css/MfBusCollateral_Detail.css" />
		<link rel="stylesheet" href="${webPath}/component/collateral/css/MfCollateralClass_InsertDetail.css" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<style type="text/css">
			.cover {
				cursor: default;
			}
			.list-table .list-add{
				color: #32b5cb
			}
			.infoTilte{
				margin-left: 14px;
				margin-bottom: 20px;
				margin-top:0px;
			}
			.form-margin{
				margin-top: 20px;
    			margin-left: 4px;
			}
			.set-disabled{
				color:#aaa;
			}
			.button-bac32B5CB{
				background-color:#32B5CB
			}
			.button-bac32B5CB:hover{
			    color: #fff;
			    background-color: #018FA7;
			}
			.button-bacFCB865{
				background-color:#FCB865
			}
			.button-bacFCB865:hover{
				background-color : #E9AA64;
				color : #fff;
			}
			.button-bacE14A47{
				background-color:#E14A47
			}
			.button-bacE14A47:hover{
				color : #fff;
				background-color:#C9302C
			}
			.button-his{
				margin-top: 20px;
			}
			.info-collect{
				margin-top: -30px;
			}
		</style>
	</head>
<body class="overflowHidden">
    <div class="container">
		<div class="row clearfix">	
			<!--申请主信息 -->
			<div class="col-md-8 column block-left">
				<div class="bg-white block-left-div">
					<div class="row clearfix head-info">
						<div class="col-xs-3 column text-center head-img">
							<div>
								<button type="button" class="btn btn-font-pledge font-pledge-div padding_left_15">
									<i class="i i-pledge font-icon"></i>
									<div class="font-text-left">应收账款</div>
								</button>
							</div>
							<div>${mfBusCollateralRel.vouTypeName}</div>
						</div>
						<div class="col-xs-9 column head-content">
						<div class="row clearfix">
							<!--信息查看入口 -->
							<div class="col-xs-10 column button-his">
								<div class="margin_bottom_10">
									<!-- 应收账款 -->
									<c:if test="${mfBusApply.busModel==5||mfBusApply.busModel==1}">
										<dhcc:pmsTag pmsId="pledge-prove">
											<button class="btn btn-view  btn-lightgray" type="button" id= "recePledge" onclick="MfBusCollateralRel_receivables_Detail.getRecePledgeInfo();">
												<i class="i i-ruku"></i><span>质押证明</span>
											</button>
										</dhcc:pmsTag>
									</c:if>
									<!-- 保理 -->
									<c:if test="${mfBusApply.busModel==13}">
										<dhcc:pmsTag pmsId="pledge-prove">
											<button class="btn btn-view  btn-lightgray" type="button" id= "recePledge" onclick="MfBusCollateralRel_receivables_Detail.getReceTranInfo();">
												<i class="i i-ruku"></i><span>转让证明</span>
											</button>
										</dhcc:pmsTag>
									</c:if>
									<c:if test="${mfBusApply.busModel==5||mfBusApply.busModel==13}">
										<dhcc:pmsTag pmsId="pledge-rebate-history">
											<button class="btn btn-view btn-lightgray" type="button" id="rebateHistory" onclick="MfBusCollateralRel_receivables_Detail.getRebateHistory();">
												<i class="i i-zherang1"></i><span>折让历史</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-disputed-history">
											<button class="btn btn-view btn-lightgray" type="button" id="disputedHistory" onclick="MfBusCollateralRel_receivables_Detail.getDisputedHistory();">
												<i class="i i-rebate"></i><span>争议记录</span>
											</button>
										</dhcc:pmsTag>
									</c:if>
									<!-- 动产质押 -->
									<c:if test="${mfBusApply.busModel==1}">
										<dhcc:pmsTag pmsId="pledge-check-inventory">
											<button class="btn btn-view btn-lightgray" type="button" id= "checkInventory" onclick="MfMoveableCommon.getCheckInventoryInfo();">
												<i class="i i-ruku"></i><span>核库信息</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-modify-history">
											<button class="btn btn-view  btn-lightgray " type="button" id="modifyHistory" onclick="MfBusCollateralRel_receivables_Detail.getModifyHistory();">
												<i class="i i-rebate"></i><span>价格变动</span>
											</button>
										</dhcc:pmsTag>
									</c:if>
									<%--<c:if test="${mfBusApply.busModel==13}">--%>
									<%--<dhcc:pmsTag pmsId="pledge-repo-affirm-info">--%>
										<%--<button class="btn btn-view  btn-lightgray" type="button" id="repoAffirmInfo" onclick="MfBusCollateralRel_receivables_Detail.getRepoAffirmInfo();">--%>
											<%--<i class="i i-fanzhuanrang"></i><span>反转让确认信息</span>--%>
										<%--</button>--%>
									<%--</dhcc:pmsTag>--%>
									<%--</c:if>--%>
								</div>
							</div>
							<div class="col-xs-2 column">
								<div class="i i-warehouse cus-type-font">
									<div class="type-name-div">${mfBusCollateralRel.collaStsName}</div>
								</div>
							</div>
						</div>
						<div class="row clearfix">
						<div class="col-xs-12 column info-collect">
								<table>
									<tr>
										
										<td>
											<p class="cont-title">转让总额</p>
											<p><span id='envalueAmt' class="content-span">
												<c:if test='${mfBusCollateralRel.receTransAmt == null}'>0.00</c:if>
												<c:if test='${mfBusCollateralRel.receTransAmt != null}'><fmt:formatNumber type="number" minFractionDigits="2" value="${mfBusCollateralRel.receTransAmt}" /></c:if>
											</span></p>
										</td>
										<td>
											<p class="cont-title">转让余额</p>
											<p><span id='pledgeNum' class="content-span">
												<c:if test='${mfBusCollateralRel.receTransBal == null}'>0.00</c:if>
												<c:if test='${mfBusCollateralRel.receTransBal != null}'><fmt:formatNumber type="number" minFractionDigits="2" value="${mfBusCollateralRel.receTransBal}" /></c:if>
											</span></p>
											
											</p>
										</td>
										<td>
											 <p class="cont-title">账款到期日(最早)</p>
											<p><span id='receiptsAmount' class="content-span">											
											${mfBusCollateralRel.receEndDate}
											</span></p>
										</td>
										<td>
										</td>
									</tr>
								</table>
						</div>
							</div>
						</div>
					</div>
					<%-- <c:if test='${busEntrance=="1" || busEntrance=="2" || busEntrance=="3"|| entrance=="credit"}'> --%>
						<div class="row clearfix btn-opt-group" id="btn-group-div">
							<div class="col-xs-12 column">
								<div class="btn-group pull-right">
									<dhcc:pmsTag pmsId="add-pledge-info">
										<button class="btn btn-opt" id ="addCollateralInfo" onclick="MfBusCollateralRel_receivables_Detail.addCollateralInfo();" type="button">
												<i class="i i-jia1"></i><span>新增</span>
										</button>
									</dhcc:pmsTag>
									<dhcc:pmsTag pmsId="pledge-batch-inStock">
										<button id="batchInStock" class="btn btn-opt" onclick="MfBusCollateralRel_receivables_Detail.inStockCollateralBatch();" type="button">
											<i class="i i-ruku"></i><span>入库</span>
										</button>
									</dhcc:pmsTag>
									<!-- 动产质押业务模式 -->
									<c:if test="${mfBusApply.busModel==1}">
										<dhcc:pmsTag pmsId="pledge-transfer-apply">
											<button id="transferApply" class="btn btn-opt" onclick="MfMoveableCommon.transferApplyInput();" type="button">
												<i class="i i-yiku"></i><span>移库</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-claim-goods-apply">
											<button id="claimGoodsApply" class="btn btn-opt" onclick="MfMoveableCommon.claimGoodsApplyInput();" type="button">
												<i class="i i-tihuo"></i><span>提货</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-claim-goods-affirm">
											<button id="claimGoodsAffirm" class="btn btn-opt" style="display:none" onclick="MfMoveableCommon.claimGoodsAffirm();" type="button">
												<i class="i i-tihuo"></i><span>提货确认</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-moveable-modify">
											<button id="moveableModify" class="btn btn-opt" onclick="MfMoveableCommon.moveableModify();" type="button">
											<i class="i i-tiaojia"></i><span>调价</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-moveable-compentstate">
											<button id="moveableCompentstate" class="btn btn-opt" onclick="MfMoveableCommon.moveableCompentstate();" type="button">
											<i class="i i-buhuo"></i><span>跌价补偿</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-moveable-compentstate-confirm">
											<button id="moveableCompentstateConfirm" class="btn btn-opt" style="display:none" onclick="MfMoveableCommon.moveableCompentstateConfirm();" type="button">
											<i class="i i-buhuo"></i><span>跌价补偿确认</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-patrol-inventory">
											<button id="patrolInventory" class="btn btn-opt" style="" onclick="MfMoveableCommon.patrolInventoryApplyInput();" type="button">
												<i class="i i-xunku"></i><span>巡库</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-account-check">
											<button id="accountCheck" class="btn btn-opt" style="" onclick="MfMoveableCommon.accountCheckInput();" type="button">
												<i class="i i-duizhang"></i><span>对账</span>
											</button>
										</dhcc:pmsTag>
									</c:if>
									<!-- 保兑仓业务模式 -->
									<c:if test="${mfBusApply.busModel==4}">
										<dhcc:pmsTag pmsId="pledge-moveable-buy-back">
											<button id="moveableBuyBack" class="btn btn-opt" onclick="MfBusCollateralRel_receivables_Detail.moveableBuyBack();" type="button">
												<i class="i i-rebate"></i><span>回购申请</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-moveable-buy-back-confirm">
											<button id="moveableBuyBackConfirm" class="btn btn-opt" style="display:none" onclick="MfBusCollateralRel_receivables_Detail.moveableBuyBackConfirm();" type="button">
												<i class="i i-rebate"></i><span>回购确认</span>
											</button>
										</dhcc:pmsTag>
									</c:if>
									<!-- 应收账款融资和保理 -->
									<c:if test="${mfBusApply.busModel==13||mfBusApply.busModel==5}">
										<dhcc:pmsTag pmsId="pledge-rebate-apply">
											<button id="rebatePledge" class="btn btn-opt" onclick="MfBusCollateralRel_receivables_Detail.rebatePledge();" type="button">
												<i class="i i-zherang1"></i><span>折让</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-rebate-affirm">
											<button id="rebateAffirm" class="btn btn-opt" style="display: none;" onclick="MfBusCollateralRel_receivables_Detail.rebateAffirm();" type="button">
												<i class="i i-zherang1"></i><span>折让确认</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-disputed-deal-apply">
											<button id="disputedDealPledge" class="btn btn-opt" onclick="MfBusCollateralRel_receivables_Detail.disputedDealPledge();" type="button">
												<i class="i i-rebate"></i><span>争议处理</span>
											</button>
										</dhcc:pmsTag>
										<dhcc:pmsTag pmsId="pledge-relieve-disputed">
											<button id="relieveDisputed" class="btn btn-opt" style="display: none;" onclick="MfBusCollateralRel_receivables_Detail.relieveDisputed();" type="button">
												<i class="i i-rebate"></i><span>争议解除</span>
											</button>
										</dhcc:pmsTag>
									</c:if>
									<!-- 保理业务 -->
									<%--<c:if test="${mfBusApply.busModel==13}">--%>
										<%--<dhcc:pmsTag pmsId="pledge-buy-back-apply">--%>
											<%--<button id ="buyBackPledge" class="btn btn-opt" onclick="MfBusCollateralRel_receivables_Detail.buyBackPledge();" type="button">--%>
												<%--<i class="i i-fanzhuanrang"></i><span>反转让</span>--%>
											<%--</button>--%>
										<%--</dhcc:pmsTag>--%>
										<%--<dhcc:pmsTag pmsId="pledge-rece-repo-affirm">--%>
											<%--<button id ="receRepoAffirm" class="btn btn-opt" style="display: none;" onclick="MfBusCollateralRel_receivables_Detail.receRepoAffirm();" type="button">--%>
												<%--<i class="i i-fanzhuanrang"></i><span>反转让确认</span>--%>
											<%--</button>--%>
										<%--</dhcc:pmsTag>--%>
									<%--</c:if>--%>
									<dhcc:pmsTag pmsId="pledge-batch-out-stock">
										<button id="batchOutStock" class="btn btn-opt" onclick="MfBusCollateralRel_receivables_Detail.outStockCollateralBatch();" type="button">
											<i class="i i-chuku"></i><span>出库</span>
										</button>
									</dhcc:pmsTag>
									<div class="btn-group hidden-lg hidden-md hidden-sm">
										<button type="button" class="btn btn-opt dropdown-toggle"  data-toggle="dropdown">
											更多<span class="caret"></span>
										</button>
									</div>
								</div>
							</div>
						</div>
					<%-- </c:if> --%>
					<!--押品信息-->
					<div class="row clearfix">
						<div class="col-xs-12 column info-block">
							<div class="block-add" style="display: none;"></div>
						</div>
					</div>
					<div id="inOutStockApproveHis" class="row clearfix" style="display: none;">
						<div class="col-xs-12 column info-block">
							<div id="spInfo-block" class="approval-hist">
								<div class="list-table">
								   <div class="title">
										 <span><i class="i i-xing blockDian"></i>出入库审批历史</span>
										 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
											<i class='i i-close-up'></i>
											<i class='i i-open-down'></i>
										</button>
								   </div>
								   <div class="content margin_left_15 collapse in " id="spInfo-div">
										<div class="approval-process">
											<div id="modeler1" class="modeler">
												<ul id="wj-modeler2" class="wj-modeler" isApp = "false">
												</ul>
											</div>
										</div>
								   </div>
								</div>
							</div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column" >
 							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
 						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 column block-right">
				<div class="bg-white block-right-div">
					<jsp:include page="/component/cus/MfCusCustomer_AbstractInfo.jsp?cusNo=${cusNo}"/>
					<c:choose>
						<c:when test="${busEntrance == 'credit' || busEntrance== 'cus_credit'}">
							<jsp:include page="/component/auth/MfCusCredit_AbstractInfo.jsp?cusNo=${cusNo}&creditAppId=${relId}"/>
						</c:when>
						<c:otherwise>
							<jsp:include page="/component/app/MfBusApply_AbstractInfo.jsp?frompage=collateral&appId=${appId}"/>
						</c:otherwise>
					</c:choose>
					
					<jsp:include page="/component/collateral/MfBusCollateralRel_AbstractInfo.jsp?cusNo=${cusNo}"/>
					
					<!--信息变更记录-->
					<dhcc:pmsTag pmsId="mf-message-change-record">
						<jsp:include page="/component/biz/BizInfoChange_pub.jsp?cusNo=${cusNo}"/>
					</dhcc:pmsTag>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/receivables/js/MfBusCollateralRel_receivables_Detail.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/ShowPledgeDetailInfo.js"></script>
<script type="text/javascript" src="${webPath}/component/collateral/js/MfMoveableCommon.js"></script>
<script type="text/javascript">
		var appId,wkfAppId,cusNo,scNo,fincId;
		var collateralType = '${collateralType}';
		if(collateralType ==''|| collateralType=='null' || collateralType==null){
			collateralType='pledge';
		}
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var creditAppId = '${creditAppId}';
		fincId = "${fincId}";
		cusNo = "${cusNo}";
		var baseType = '${baseType}';
		appId = "${appId}";
		wkfAppId = '${mfBusApply.wkfAppId}';
		scNo = '${scNo}';
		var relId = "${relId}";
		var webPath = '${webPath}';
		var opType = "<%=(String)request.getAttribute("opType")%>";
		var vouType ='${mfBusApply.vouType}';
		var entrance = '${entrance}';
		var appSts= '${mfBusApply.appSts}';
		var busCollateralId='${mfBusCollateralRel.busCollateralId}';
		var docParm = "cusNo="+cusNo+"&relNo="+busCollateralId+"&scNo="+scNo;//查询文档信息的url的参数
		var busModel= '${mfBusApply.busModel}';
		var busSts='${busSts}';
		var instockAllflag='${instockAllflag}';
		var outstockAllflag='${outstockAllflag}';
		var tranFlag='${tranFlag}';
		var collaSts='${mfBusCollateralRel.collaSts}';
		var repoFlag='${repoFlag}';
		var repoSts='${mfPleRepoApply.appSts}';
		var operable='${operable}';
		var pactSts= '${mfBusPact.pactSts}';
		var pactId= '${mfBusPact.pactId}';
		var fincSts= '${fincSts}';
		var evalFlag= '${evalFlag}';
		var busEntrance= '${busEntrance}';
		var inBatchSts='${mfBusCollateralRel.inBatchSts}';
		var outBatchSts='${mfBusCollateralRel.outBatchSts}';
		var keepId='${mfBusCollateralRel.keepId}';
		var busEndDate = '${busEndDate}';
		var authCycle = '${mfBusPact.authCycle}';
		var isAllFincEnd = '${isAllFincEnd}';
		$(function(){
			MfBusCollateralRel_receivables_Detail.init();
		});
		</script>
		<script type="text/javascript">								
				//首次抵押余额 小于 等于 抵押物估值 在首次抵押余额判断
				var judgeOfValue = function(firstPle){									
					var extNumValueStr = $("span[class=inputcontent]").find("input[title='首次抵押余额']").val();											
					var pleOriginalValueStr = $("input[name=pleOriginalValue]").val();//抵押物估值
					
					var flag;
					if(pleOriginalValueStr != "" && extNumValueStr != ""){
						var	pleOriginalValue = parseFloat(pleOriginalValueStr.replace(/,/g,""));//替换掉逗号 并转换成float类型				
						var extNumValue = parseFloat(extNumValueStr.replace(/,/g,""));
						flag = pleOriginalValue < extNumValue?true:false;
					}
					if(flag){					
						window.top.alert("首次抵押余额不能大于抵押物估值！", 0);						
						$("input[title='首次抵押余额']").val("");					
						flag=false;	
					}									
				}
				//首次抵押余额 小于 等于 抵押物估值 在首次抵押余额判断
				var judgeOfValue2 = function(ele){
					var extNumValueStr = $("input[name=extNum03]").val();					
					var pleOriginalValueStr = $("span[class=inputcontent]").find("input[title='抵押物估值']").val();
					
					var flag;
					if(pleOriginalValueStr != "" && extNumValueStr != ""){
						var	pleOriginalValue = parseFloat(pleOriginalValueStr.replace(/,/g,""));//替换掉逗号 并转换成float类型				
						var extNumValue = parseFloat(extNumValueStr.replace(/,/g,""));
						flag = pleOriginalValue < extNumValue?true:false;
					}
					if(flag){					
						window.top.alert("首次抵押余额不能大于抵押物估值！", 0);						
						$("input[title='首次抵押余额']").val("");
						$("input[name=extNum03]").val("");
						$('input[name=extNum03]').parents("tr").eq(1).find('td').eq(0).find('.fieldShow').html("");				
						flag=false;	
					}									
				}
				
				//房屋坐落修改为抵押物地址，选择区域后填详细地址
				var selectAreaCallBack = function(areaInfo){
					$("input[name=pleAddress]").val(areaInfo.disName);
				};
		
		</script>
</html>