<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/wkf/Wkf_Base.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<link rel="stylesheet" href="${webPath}/component/oa/archive/css/MfOaArchivesDetail.css" />
<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
<script type="text/javascript">
		var baseId = '${baseId}';
		var entryManagementId = '${entryManagementId}';
		var from = "entryApply";
		//转json字符串为json对象
		var archivesbase = eval('('+'${archivesbase}'+')');
		//实体list在action中转为jsonarray，放到map中，取出jsonarray字符串后转json数组
		var workjson = eval('('+'${parmdicMap.workjsonarray}'+')');
		var edujson = eval('('+'${parmdicMap.edujsonarray}'+')');
		var redjson = eval('('+'${parmdicMap.redjsonarray}'+')');
		var famjson = eval('('+'${parmdicMap.famjsonarray}'+')');
		var webPath = '${webPath}';
		var entryFlag = '${entryFlag}';
</script>
</head>
<body class="bg-white" style="height: 100%;">
	<div class="mf_contentt scroll-content container form-container" id="myCustomScrollArchive" style="height: 100%;">
		<div class="mf_content">
			<div class="arch_sort"
				style="height: 200px; background-color: #f7f7f7;">
				<div
					style="float: left; width: 130; height: 170px; background-color: #f7f7f7;">
					<a class="btn" ><img id="headImgShow"
						class="accountImg" height="160" width="130" /></a>
				</div>
				<div id="headMessage"
					style="float: left; width: 60%; height: 170px; padding-left: 50px; background-color: #f7f7f7;">
					<div style="font-size: 26px; height: 78px; padding-top: 30px;"
						name="opName">${mfOaArchivesBase.opName}</div>

					<div>
						<span><i class="i i-ren2"></i><span name="sex">${mfOaArchivesBase.sex}</span></span>

						<span class="vertical-line">|</span> <span><i
							class="i i-bi1"></i><span name="education">${mfOaArchivesBase.education}</span></span>
						<span class="vertical-line">|</span> <span><i
							class="i i-youjian1"></i><span name="email">${mfOaArchivesBase.email}</span></span>
					</div>
					</br>
					<div>
						<span><i class="i i-rili"></i><span name="birthday">${mfOaArchivesBase.birthday}</span></span>
						<span class="vertical-line">|</span> <span><i
							class="i i-dianhua"></i><span name="tel">${mfOaArchivesBase.tel}</span></span>
						<span class="vertical-line">|</span> <span><i
							class="i i-idcard2"></i><span name="idNum">${mfOaArchivesBase.idNum}</span></span>
					</div>
				</div>
			</div>
			<div class="arch_sort">
					<div  class="row clearfix">
						<div class="col-xs-12 column info-block">
							<div id="spInfo-block">
								<div class="form-table">
								   <div class="title">
										 <span><i class="i i-xing blockDian"></i>基本信息</span>
										 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										</button>
								   </div>
							  		<div id="base_div" disable="true" class="content collapse in">
										<form method="post" id="OaArchivesBase" theme="simple" name="operform" action="${webPath}/mfOaArchivesBase/insertAjax">
											<dhcc:propertySeeTag property="formentrymanagement" mode="query" />
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<div class="arch_sort family_sort" style="border: 0px;">
					<div  class="row clearfix">
						<div class="col-xs-12 column info-block">
							<div id="spInfo-block">
								<div class="form-table">
								   <div class="title">
										 <span><i class="i i-xing blockDian"></i>家庭成员</span>
										 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										</button>
								   </div>
							  		<div class="family_divclass1" style="padding: 12px 0px; font-size: 14px;">
										<div style="width: 100%; display: block; display: -webkit-box;">
											<div style="height: 40px; line-height: 40px; width: 100%;">
												<div name="relishion" style="padding-left: 50px; width: 50%; float: left;">父亲</div>
												<div name="name" style="width: 50%; float: left;">哈哈</div>
												<input type="hidden" name="num" value="1"></input> 
												<input type="hidden" name="familyId" value="17030310310514913"></input>
											</div> 
											<div style="clear: both;"></div>
										</div>
										<div style="line-height: 30px; width: 100%;">
											<div style="width: 100%; float: left;">
												<div name="profession" style="padding-left: 50px; width: 50%; float: left;">工程师</div>
												<div name="tel" style="width: 50%; float: left;">176498706417</div>
												<div style="clear: both;"></div>
											</div>
										</div>
										<div style="clear: both;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<div class="arch_sort work_sort" style="border: 0px;">
					<div  class="row clearfix">
						<div class="col-xs-12 column info-block">
							<div id="spInfo-block">
								<div class="form-table">
								   <div class="title">
										 <span><i class="i i-xing blockDian"></i>工作经历</span>
										 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										</button>
								   </div>
							  		<div class="work_divclass1" style="padding: 12px 0px; font-size: 14px;">
										<div style="width: 100%; display: block; display: -webkit-box;">
											<div style="height: 40px; line-height: 40px; width: 100%;">
												<div name="time"
													style="padding-left: 50px; width: 50%; float: left;">2012/12-2015/12</div>
												<div name="companyName" style="width: 50%; float: left;">北京微金
													时代科技有限公司</div>
											    <input type="hidden" name="num"
													value="1"></input> <input type="hidden" name="workId"
													value="17030310310514913"></input>
											</div>
										</div>
					
										<div style="clear: both"></div>
										<div style="height: 40px; line-height: 40px; width: 100%;">
											<div style="line-height: 30px; width: 100%; float: left;">
												<div name="jobTitle" style="width: 50%; float: left;padding-left: 50px;">Java软件工程师</div>
												<div style="width: 50%; float: left;">
													<div name="confirmPerson" style="padding-right: 10px; float: left;">汤心敬</div>
													<div style="width: 2%; float: left;">/</div>
													<div name="comfirmTel" style="width: 50%; float: left;">13071059596</div>
												</div>
											</div>
										</div>
										<div style="clear: both"></div>
										<div style="height: 40px; line-height: 40px; width: 100%;">
											<div style="line-height: 30px; width: 100%; float: left;">
												<div name="workContent" style="padding: 0px 50px; width: 100%;">
													Java是最完美的语言</br>Java是最完美的语言Java是最完美的语言Java是最完美的语言Java是最完美的语言Java是最完美的语言。
												</div>
											</div>
										</div>
										<div style="clear: both"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<div class="arch_sort education_sort" style="border: 0px;">
					<div  class="row clearfix">
						<div class="col-xs-12 column info-block">
							<div id="spInfo-block">
								<div class="form-table">
								   <div class="title">
										 <span><i class="i i-xing blockDian"></i>教育情况</span>
										 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										</button>
								   </div>
							  		<div class="education_divclass1" style="padding: 12px 0px; font-size: 14px;">
										<div style="width: 100%; display: block; display: -webkit-box;">
											<div style="height: 40px; line-height: 40px; width: 100%;">
												<div name="beginEnd"
													style="padding-left: 50px; width: 50%; float: left;">2012/09-2016/7</div>
												<div name="gradSchool" style="width: 50%; float: left;">郑州大学</div>
												<input type="hidden" name="num"
													value="1"></input> <input type="hidden" name="educationId"
													value="17030310310514913"></input>
											</div>
											<div style="clear: both;"></div>
										</div>
										<div style="line-height: 30px; width: 100%;">
											<div name="proEdu" style="width: 100%; float: left;padding-left: 50px;">毒品品尝师培养/本科</div>
											<div style="clear: both;"></div>
										</div>
										<div style="line-height: 30px; width: 100%;">
											<div style="line-height: 30px; width: 100%; float: left;">
												<div name="proDescribe"
													style="padding-left: 50px; line-height: 30px; width: 100%;">你真的不骗你真的不骗的好开心.真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你鹤顶红是最好吃的食品,吃的好开心.真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你真的不骗你</div>
											</div>
											<div style="clear: both;"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<div class="arch_sort reward_sort" style="border: 0px;">
					<div  class="row clearfix">
						<div class="col-xs-12 column info-block">
							<div id="spInfo-block">
								<div class="form-table">
								   <div class="title">
										 <span><i class="i i-xing blockDian"></i>奖惩记录</span>
										 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										</button>
								   </div>
								   <div class="reward_divclass1" style="padding: 12px 0px; font-size: 14px;">
										<div style="width: 100%; display: block; display: -webkit-box;">
											<div style="height: 40px; line-height: 40px; width: 100%;">
												<div name="adwardDate" style="padding-left: 50px; width: 50%; float: left;">2012/12</div>
												<div name="rewardOrPunishment" style="width: 50%; float: left;">奖励</div>
												<input type="hidden" name="num" value="1"></input> 
												<input type="hidden" name="rewardId" value="17030310310514913"></input>
											</div>
											<div style="clear: both;"></div>
										</div>
										<div style="line-height: 30px; width: 100%;">
											<div style="width: 100%; float: left;">
												<div name="adwards" style="padding-left: 50px;width: 50%; float: left;">扬帆杯软件设计大赛二等奖</div>
												<div name="grade" style="width: 50%; float: left;">90分</div>
												<div name="reason" style="padding-left: 50px;width: 100%; float: left;">逃课</div>
											</div>
										</div>
										<div style="line-height: 30px; width: 100%;">
											<div style="width: 100%; float: left;">
												<div name="adwardRemark" style="padding-left: 50px; width: 100%;">省级比赛,对编程学习的一种鼓励。省级比赛,对编程学习的一种鼓励。省级比赛,对编程学习的一种鼓励。</div>
												<div style="clear: both;"></div>
											</div>
										</div>
										<div style="clear: both;"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<div class="arch_sort">
				<div id="entryApplyApproveHis" class="row clearfix" style="display: none;">
						<div class="col-xs-12 column info-block">
							<div id="spInfo-block" class="approval-hist">
								<div class="list-table">
								   <div class="title">
										 <span><i class="i i-xing blockDian"></i>入职申请审批历史</span>
										 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
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
			</div>
		</div>
		<div style="height: 10px;"></div>
	</div>
	<c:if test = "${entryFlag != null and entryFlag == '1'}">
		<div id="approvalBtn" class="formRowCenter " style="display:block;">
			<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="backList();"></dhcc:thirdButton>
		</div>
	</c:if>
	</div>
</body>
<script type="text/javascript"
	src="${webPath}/component/oa/archive/js/MfOaDrchivesBase_detail.js"></script>
<script type="text/javascript">
		$(function(){
			OaArchivesDetail.init();
			showApproveHis();
		});	
		function backList(){
			window.location.href = webPath+"/mfOaEntryManagement/getListPage";
		}
		//展示审批历史
	    function showApproveHis(){
			//获得审批历史信息
			showWkfFlowVertical($("#wj-modeler2"),entryManagementId,"19","entry_apply_approval");
			$("#entryApplyApproveHis").show();
		}
</script>
</html>