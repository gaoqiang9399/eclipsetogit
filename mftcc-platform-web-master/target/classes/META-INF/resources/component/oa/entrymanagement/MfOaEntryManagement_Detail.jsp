<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<link rel="stylesheet"
	href="${webPath}/component/oa/archive/css/MfOaArchivesDetail.css" />
<script type="text/javascript">
		var baseId = '${baseId}';
		var entryManagementId = '${entryManagementId}';
		var from = "entryApply";
		//转json字符串为json对象511423198801290196
		var archivesbase = eval('('+'${archivesbase}'+')');
		var webPath = '${webPath}';
</script>
</head>
<body class="bg-white" style="height: 100%;">
	<div class="container form-container scroll-content" id="myCustomScrollArchive">
		<div class="col-xs-10 col-xs-offset-1">
			<div class="arch_sort"
				style="height: 200px; background-color: #f7f7f7;">
				<div
					style="float: left; width: 130; height: 170px; background-color: #f7f7f7;">
					<a class="btn" onclick="uploadHeadImg();"><img id="headImgShow"
						class="accountImg" height="160" width="130" /></a>
				</div>
				<div id="headdiv"
					style="float: left; margin-left: -134px; margin-top: 65px;">
					<a class="btn btn-link head-word" onclick="uploadHeadImg();">更换头像</a>
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
			<div class="arch_sort" style="border: 0px;">
				<div class="dynamic-block" title="基本信息" name="MfOaArchivesBaseAction" data-sort="14" data-tablename="mf_oa_archives_base">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>基本信息</span>
							<button class="btn btn-link formAdd-btn" onclick="OaArchivesDetail.updateArchivesBase();" title="编辑">
								<i class="i i-bianji3"></i>
							</button>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfOaArchivesBaseAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div disable="true" class="content collapse in" id="MfOaArchivesBaseAction" style="margin-top: 10px;">
							<form method="post" id="OaArchivesBaseInsert1" theme="simple" name="operform" action="${webPath}/mfOaArchivesBase/insertAjax">
								<dhcc:propertySeeTag property="formentrymanagement" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="arch_sort family_sort" style="border: 0px;">
				<div class="dynamic-block" title="家庭成员" name="MfOaArchivesFamilyAction" data-sort="14" data-tablename="mf_oa_archives_family">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>家庭成员</span>
							<button class="btn btn-link formAdd-btn" onclick="OaArchivesDetail.familyadd();" title="新增">
								<i class="i i-jia3"></i>
							</button>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfOaArchivesFamilyAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div class="content collapse in" id="MfOaArchivesFamilyAction">
							${parmdicMap.familyTableHtml}
						</div>
					</div>
				</div>
			</div>
			<div class="arch_sort work_sort" style="border: 0px;">
				<div class="dynamic-block" title="工作经历" name="MfOaArchivesWorkAction" data-sort="14" data-tablename="mf_oa_archives_work">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>工作经历</span>
							<button class="btn btn-link formAdd-btn" onclick="OaArchivesDetail.workadd();" title="新增">
								<i class="i i-jia3"></i>
							</button>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfOaArchivesWorkAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div class="content collapse in" id="MfOaArchivesWorkAction">
							${parmdicMap.workTableHtml}
						</div>
					</div>
				</div>
			</div>
			<div class="arch_sort education_sort" style="border: 0px;">
				<div class="dynamic-block" title="教育经历" name="MfOaArchivesEducationAction" data-sort="14" data-tablename="mf_oa_archives_education">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>教育经历</span>
							<button class="btn btn-link formAdd-btn" onclick="OaArchivesDetail.educationadd();" title="新增">
								<i class="i i-jia3"></i>
							</button>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfOaArchivesEducationAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div class="content collapse in" id="MfOaArchivesEducationAction">
							${parmdicMap.educationTableHtml}
						</div>
					</div>
				</div>
			</div>
			<div class="arch_sort reward_sort" style="border: 0px;">
				<div class="dynamic-block" title="奖惩记录" name="MfOaArchivesRewardsAction" data-sort="14" data-tablename="mf_oa_archives_rewards">
					<div class="list-table">
						<div class="title">
							<span class="formName"><i class="i i-xing blockDian"></i>奖惩记录</span>
							<button class="btn btn-link formAdd-btn" onclick="OaArchivesDetail.rewardsadd();" title="新增">
								<i class="i i-jia3"></i>
							</button>
							<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfOaArchivesRewardsAction">
								<i class="i i-close-up"></i><i class="i i-open-down"></i>
							</button>
						</div>
						<div class="content collapse in" id="MfOaArchivesRewardsAction">
							${parmdicMap.rewardTableHtml}
						</div>
					</div>
				</div>
			</div>
			<div class="arch_sort">
				<div id="base_div" disable="true" class="bootstarpTag">
				</div>
			</div>
		</div>
		<div style="height: 10px;"></div>
		<div class="formRowCenter"
			style="height: 50px; padding: 5px; position: fixed; bottom: 0px; width: 100%;">
			<dhcc:thirdButton value="提交申请" action="提交申请" typeclass="submitApplyAjax" onclick="submitApplyAjax();"></dhcc:thirdButton>
			<dhcc:thirdButton value="返回" action="关闭" typeclass="cancel" onclick="backList();"></dhcc:thirdButton>
		</div>
	</div>
	</div>
</body>
<script type="text/javascript"
	src="${webPath}/component/oa/archive/js/MfOaDrchivesBase_detail.js"></script>
<script type="text/javascript">
		$(function(){
			OaArchivesDetail.init();
		});	
		function backList(){
			myclose_click();
		}
		function submitApplyAjax(){
			$.ajax({
					url : webPath+"/mfOaEntryManagement/submitApplyAjax",
					data : {
						entryManagementId:entryManagementId
						},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						if (data.flag == "success") {
							window.top.alert(data.msg, 3);
							myclose_click();
						} else {
							alert(data.msg, 0);
						}
					},
					error : function() {
					}
				});
		}
</script>
</html>