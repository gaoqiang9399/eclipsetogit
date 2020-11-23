<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" />
</head>	
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<div class="col-xs-8 column block-left">
				<div class="bg-white block-left-div" >
					<!--头部主要信息 -->
					<div class="row clearfix head-info">
						<!--头像 -->
						<div class="col-xs-3 column text-center head-img">
							<div class="btn btn-link">
								<img id="headImgShow" name="headImgShow" class="img-circle" src="${webPath}/themes/factor/images/op_user.jpg"/>
							</div>
						</div>
						<!--概要信息 -->
						<div class="col-xs-7 column head-content">
							<div class="margin_bottom_5">
								<button  class="btn btn-link head-title" onclick="updateCustomerInfo();">
									张三
								</button>
							</div>
							<!--常用操作及查看常用信息 -->
							<div class="margin_bottom_10">
								<button class="btn btn-view cus-tag" type="button" onclick="cusTagHis();">
									<i class="i i-ren2"></i><span  id="cusNameRate-span">同事</span>
								</button>
								<button class="btn btn-dodgerblue btn-view" type="button"  onclick="cusRelation();">
									<i class="i i-guanXi"></i><span>通讯录</span>
								</button>
								<button class="btn btn-lightgray btn-view " type="button" onclick="getEvalDetailResult('0');">
										<i class="i i-xing2"></i>待办
								</button>
								<button class="btn btn-lightgray btn-view " type="button" onclick="getEvalDetailResult('0');">
										<i class="i i-youjian"></i>邮件
								</button>
							</div>
							<div>
								<p>
									<span><i class="i i-youjian"></i><span id = "contactsName">1396839103@qq.com</span></span>
									<span class="vertical-line">|</span>
									<span><i class="i i-dianhua"></i><span id = "contactsTel">18000456568</span></span>
									<span class="vertical-line">|</span>
									<span><i class="i i-idcard2" ></i><span id="idNum">412728199209167519</span></span>
								</p>
							</div>
						</div>
					</div>
					<!-- 常用操作 -->
					<div class="row clearfix btn-opt-group">
						<div class="col-xs-12 column">
							<div class="btn-group pull-right">
								<!--工作中需要使用的一些资料上传至公司服务器上便于保存 和保密-->
								<button class="btn btn-opt" onclick="getPfsDialog();" type="button">
									<i class="i i-wenjianjia1" ></i><span>资料</span>	
								</button>
								<!--查看个人的考勤信息-->
								<button class="btn btn-opt" onclick="cusDocInfo();" type="button">
									<i class="i i-wenjian4"></i><span>考勤</span>
								</button>
								<!--查看与个人工资相关的信息-->
								<button class="btn btn-opt" onclick="cusTrack('0');" type="button">
									<i class="i i-qian"></i><span>工资</span>
								</button>
								<!-- 用于新建和查看日程 -->
								<button class="btn btn-opt" onclick="getAppAuth();" type="button">
									<i class="i i-richengshezhi "></i><span>日程</span>
								</button>
								<!--自己办理的客户便于跟踪服务  -->
								<button class="btn btn-opt" onclick="getInitatEcalApp();" type="button">
									<i class="i i-zhanghu"></i><span>客户</span>
								</button>
							</div>
						</div>
					</div>
					<!--展示个人信息-->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="dynamic-block" title="个人信息" name="MfCusRegisterAction">
								<div class="form-table">
									<div class="title"><span class="formName">个人信息</span><span class="formMust-span">*</span>
									</div>
									<div class="content">
										<form action="" id="">
											<table width="100%" height="auto" title="">
												<tbody>
													<tr>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">职位</span></p><span class="font-smallup bold"><div style="display:inline-block;" class="fieldShow half">管理员</div></span></td>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">姓名</span></p><span class="font-smallup bold"><div style="display:inline-block;" class="fieldShow">张三</div></span></td>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">手机</span></p><span class="font-smallup bold"><div style="display:inline-block;" class="fieldShow">18054454896</div></span></td>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">办公电话</span></p><span class="font-smallup bold"><div style="display:inline-block;" class="fieldShow">0371-65995266</div></span></td>
													</tr>
													<tr>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">身份证号</span></p><span class="font-smallup bold"><div style="display:inline-block;" class="fieldShow">412728199305309865</div></span></td>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">传真号码</span></p><span class="font-smallup bold dblclickflag" ondblclick="changedDbClick(this)"><div style="display:inline-block;" class="fieldShow">0371-65995266</div><div class="fieldReal"  style="display:none"><input type="text" title="传真号码" name="cusFax" datatype="0" mustinput="" maxlength="20" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div><div class="editpen" onclick="changedDbClick(this.parentNode||this.parentElement);" style="display: none;"><i class="i i-editable"></i></div></span></td>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">住址</span></p><span class="font-smallup bold dblclickflag" ondblclick="changedDbClick(this)"><div style="display:inline-block;" class="fieldShow">郑州市科学大道</div><div class="fieldReal"  style="display:none"><input type="text" title="注册地址" name="address" datatype="0" mustinput="0" maxlength="400" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" value="1396839103@qq.com"></div></span></td>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">邮箱</span></p><span class="font-smallup bold dblclickflag" ondblclick="changedDbClick(this)"><div style="display:inline-block;" class="fieldShow">1396839103@qq.com</div><div class="fieldReal" style="display:none"><input type="text" title="邮箱" name="wayArea" datatype="0" mustinput="0" maxlength="400" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div></span></td>
													</tr>
													<tr>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">身份证号</span></p><span class="font-smallup bold"><div style="display:inline-block;" class="fieldShow">412728199305309865</div></span></td>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">欠款总额</span></p><span class="font-smallup bold dblclickflag" ondblclick="changedDbClick(this)"><div style="display:inline-block;" class="fieldShow">266.33</div><div class="fieldReal" primarykeys="cusNo=cus17011016273331610" style="display:none"><input type="text" title="欠款总额" name="cusFax" datatype="0" mustinput="" maxlength="20" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div><div class="editpen" onclick="changedDbClick(this.parentNode||this.parentElement);" style="display: none;"><i class="i i-editable"></i></div></span></td>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">报销总额</span></p><span class="font-smallup bold dblclickflag" ondblclick="changedDbClick(this)"><div style="display:inline-block;" class="fieldShow">13968.00</div><div class="fieldReal" primarykeys="cusNo=cus17011016273331610" style="display:none"><input type="text" title="报销总额" name="address" datatype="0" mustinput="0" maxlength="400" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" value="1396839103@qq.com"></div></span></td>
														<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">办公地点</span></p><span class="font-smallup bold dblclickflag" ondblclick="changedDbClick(this)"><div style="display:inline-block;" class="fieldShow"></div><div class="fieldReal" primarykeys="cusNo=cus17011016273331610" style="display:none"><input type="text" title="办公地点" name="wayArea" datatype="0" mustinput="0" maxlength="400" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div></span></td>
													</tr>
												</tbody>
											</table>
										</form>
									</div>
								</div>
							</div>
							<div class="block-add" style="display: none;"></div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-xs-12 column" id="rotate-body"></div>
					</div> 
				</div>
			</div>
			<!--客户附属信息-->
			<div class="col-xs-4 column block-right">
				<div class="bg-white block-right-div">	
					<div class="row clearfix padding_left_12 his-info">
						<div class="col-xs-12 col-md-12 column">
							<table>
								<tbody>
									<tr>
										<td class="change-time">
											<span class="i i-dian change-dian"></span>
											<span class="change-date">2017-01-12</span><br>
											<span class="time">13:52:28</span>
										</td>
										<td class="his-cont">
											<span>管理员</span><br>
											<span>评级审批-微金时代</span>
										</td>
									</tr>
									<tr>
										<td class="change-time">
											<span class="i i-dian change-dian"></span><span class="change-date">2017-01-12</span><br>
											<span class="time">10:49:06</span>
										</td>
										<td class="his-cont">
											<span>管理员</span><br>
											<span>修改客户信息-郑州瑞达</span>
						
										</td>
									</tr>
									<tr>
										<td class="change-time">
											<span class="i i-dian change-dian"></span><span class="change-date">2017-01-12</span><br>
											<span class="time">10:48:14</span>
										</td>
										<td class="his-cont">
											<span>管理员</span><br>
											<span>新增客户-大众4s店</span>
										</td>
									</tr>
									<tr>
										<td class="change-time">
											<span class="i i-dian change-dian"></span><span class="change-date">2017-01-11</span><br>
											<span class="time">14:56:07</span>
										</td>
										<td class="his-cont">
											<span>业务员DL0005</span><br>
											<span>新增客户-当当书城</span>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>			
				</div>
			</div>
			<div class="col-xs-4 column block-right">
				<div class="bg-white block-right-div">	
					<div class="row clearfix padding_left_12 his-info">
						<div class="col-xs-12 col-md-12 column">
							<div class="container" style="padding-bottom: 34px;">
								<div class="row clearfix">
									<div class="col-md-12 column">
										<h3 class="text-center" style=" margin-left: -25px;">
											工具箱
										</h3>
									</div>
								</div>
								<div class="row clearfix" style=" width: 95%;margin-left: 8px;">
									<div class="col-md-4 column" >
			 							<button type="button" class="btn btn-default btn-primary" style="width: 95%;height: 48px;">
			 								万年历
         								</button>
									</div>
									<div class="col-md-4 column" >
			 							<button type="button" class="btn btn-default btn-primary" style="width: 95%;height: 48px;">万年历
										</button>
									</div>
									<div class="col-md-4 column" s>
          			 					<button type="button" class="btn btn-default btn-primary" style="width: 95%;height: 48px;">
										万年历
										</button>
									</div>
								</div>
							</div>
							<div class="container" style="padding-bottom: 33px;">
								<div class="row clearfix" style=" width: 95%;margin-left: 8px;">
									<div class="col-md-4 column" >
			 							<button type="button" class="btn btn-default btn-primary" style="width: 95%;height: 48px;">
			 								万年历
         								</button>
									</div>
									<div class="col-md-4 column" style="">
			 							<button type="button" class="btn btn-default btn-primary" style="width: 95%;height: 48px;">万年历
										</button>
									</div>
									<div class="col-md-4 column" style="">
          			 					<button type="button" class="btn btn-default btn-primary" style="width: 95%;height: 48px;">
										万年历
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>			
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/layout/view/js/userInfo.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
	$(function(){
		userInfo.init();
	});
</script>
</html>