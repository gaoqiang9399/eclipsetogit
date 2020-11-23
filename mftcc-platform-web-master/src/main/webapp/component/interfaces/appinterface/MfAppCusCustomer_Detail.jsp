<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>

<!DOCTYPE html>
<html>
	<head>
	<script type="text/javascript" src="/factor/component/interfaces/appinterface/js/MfAppBusApply.js"></script>
	<link rel="stylesheet" href="UIplug/iconmoon/style.css" >
		<title>客户详情</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<style>
	  		html{
		  		background-color:#efeff4;
		  	}
		  	.weui_cell{
		  		padding:10px 15px;
		  	}
		  	.weui_cells{
		  		margin-top:10px;
		  	}
		  	.icon-font{
		  		    margin-right:8px;
				    padding: 5px;
				    float: left;
				    background-color: #FCB865;
				    border-radius: 5px;
 				    font-size:17px; 
				    color:#fff;
		  	}
			.me_cl{
				width:33.33333%;
				float:left;
				background-color:#00BBFF;
				padding-top:10px;
				padding-bottom:10px;
			}
			.me_cl p{
				text-align:center;
			}
			#tab1_top{
				position: fixed;
				top:0;
				z-index:999;
				left:0px;
				right:0px;
			}
			#panel_db_1 {
				background-color:#efeff4;
				margin-bottom:40px;
			}
			.span_1{
				text-align: right;
			}
			.second_part{
				background-color:white;
			}
			.font_blue{
				color:#3bbaff;;
			}
			.xiangmuxinxi{
				margin-top:10px;
			}
			.baozhengxinxi{
				margin-top:10px;
			}
			.form_xinxi_left{
				float: left;
				width: 45%;
				margin-left: 5%;
			}
			.form_xinxi_right{
				float: right;
				width: 45%;
			}
			.clearfloat{
				clear:both
			}
			#weui-ca{
				margin-top:0px;
			}
			.smallgray{
				font-size: 10px;
				color: #999999;
			}
			.marginTop0{
				margin-top: 0px;
			}
			.mf_list_item{
				padding: 0px 15px 0px 15px;
			}
			/* .baner-background{
				background:url(component/interfaces/appinterface/img/personbar.png) no-repeat;
				background-size: cover;
			    height: 150px;
			    width: 100%;	
			} */
			.gery-mid-text{
	         	font-size:13px;
	         	color:#999;
	         	padding-bottom:5px;
	         }
	         .ding-bule{
				color:#38adff;
			}
			.ding_title_item{
/* 				margin-top: 10px; */
			}
			.ding_little_font{
				margin-top:2px;
				font-size: 14px;
			}
			.ding_circle {
			    color: #3bbaff;
				display: inline-block;
			    width: 40px;
			    height: 40px;
			    background: #ffffff;
			    -moz-border-radius: 50px;
			    -webkit-border-radius: 50px;
			    border-radius: 20px;
			    margin-left: 5px;
			}
			.ding_circle_icon{
				font-size: 27px;
			}
			.ding_larger_font{
				font-size: 23px;
			}
			.ding_smaller_font{
				font-size: 12px;
			}
			.ding_complete_icon{
				font-size: 20px;
    			margin-right: 3px;
			}
			.ding_cells_form{
				margin-top: 0px;
				margin-bottom: 0px;
			}
			/* .ding_cells_form.weui_cells:after{
				border-top:3px solid #38adff;
			}
			.ding_cells_form:last-child.weui_cells:after{
				border-top:0px solid #38adff;
			} */
			.ding_cells_form.weui_cells:before{
				border-top:3px solid #38adff;
			}
			.ding_cells_form:first-of-type.weui_cells:before{
				border-top: 1px solid #D9D9D9 !important;
			}
			.baner-background{
				background: -webkit-linear-gradient(#AAD0F9, #2CADFE); /* Safari 5.1 - 6.0 */
				background: -o-linear-gradient(#AAD0F9, #2CADFE); /* Opera 11.1 - 12.0 */
				background: -moz-linear-gradient(#AAD0F9, #2CADFE); /* Firefox 3.6 - 15 */
				background: linear-gradient(#AAD0F9, #2CADFE); /* 标准的语法 */
				background-size: cover;
			    height: 150px;
			    width: 100%;	
			}
			.ding_cells:last-of-type{
				margin-bottom: 20px;
			} 
	  	</style>
	</head>
	<body>
			<div class="weui_cells weui_cells_access marginTop0 baner-background" style="color:#ffffff;">
				<!-- <div style="padding-top:20px;padding-left:40%;  height: 80px;">
					<img  src="themes/factor/images/user_1.jpg" style="width:80px;height:80px;border-radius: 50%;">
				</div> -->
				<div style="margin: 10px 0px 0px 20px;">
					<div class="ding_title_item" id="contactsNameDetailTitle">
						<c:if test="${mfCusCustomer.contactsName ne null and mfCusCustomer.contactsName ne ''}">
							${mfCusCustomer.contactsName}
						</c:if>
						<c:if test="${mfCusCustomer.contactsName eq null or mfCusCustomer.contactsName eq ''}">
							<span class="unregistered" >未登记</span>
						</c:if>
					</div>
					<div class="ding_title_item ding_little_font" id="contactsTelDetailTitle">	
						<c:if test="${mfCusCustomer.contactsTel ne null and mfCusCustomer.contactsTel ne ''}">
							${mfCusCustomer.contactsTel}
						</c:if>
						<c:if test="${mfCusCustomer.contactsTel eq null or mfCusCustomer.contactsTel eq ''}">
							<span class="unregistered" >&nbsp;</span>
						</c:if>
					</div>
					<div class="ding_title_item ding_little_font" id="commAddressTitle" style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;">	
						<c:if test="${mfCusCustomer.commAddress ne null and mfCusCustomer.commAddress ne ''}">
							${mfCusCustomer.commAddress}
						</c:if>
						<c:if test="${mfCusCustomer.commAddress eq null or mfCusCustomer.commAddress eq ''}">
							<span class="unregistered" >&nbsp;</span>
						</c:if>
					</div>
				</div>
				<div style=" position: absolute;    top: 15px;    right: 20px;    text-align: center;    font-size: 27px;">
					<span id="addBus" class="ding_circle"><span>申</span></span><span  onclick="goCusCustomerCompleteInfo();" class="ding_circle">完</span><span id="phone" class="ding_circle "><i class="i i-dianhua ding_circle_icon"></i></span>
				</div>
				<div style="overflow: hidden;    bottom: 5px;    left: 20px;    margin: 12px 20px 0px 20px">
					<!--贷款余额 -->
					<div style="display: inline-block;">
						<div><span class="ding_larger_font"><s:property value="%{formatAmtWan(allLoanbal)}" /></span><span class="ding_smaller_font">万元</span></div>
						<div class="ding_little_font ">贷款余额</div>
					</div>
					<div style="float:right;display: inline-block;">
						<div ><span class="ding_larger_font"><s:property value="curTermYingShouAmt" /></span><span class="ding_smaller_font">元</span></div>
						<div class="ding_little_font " style="text-align: right;">本期账单</div>
					</div>
				</div>
			</div>
			
			<div class="weui_panel" id="appInfoPanel">
				<div class="weui_panel_bd" id="panel_db_1">
				</div>
			</div> 
			<div>
				<div id="addcus" class="weui_cells weui_cells_access" >
				    <a class="weui_cell" href="javascript:;">
				        <div class="weui_cell_hd ">
				          <i class='info-box-icon i i-jibenxinxi icon-font' style="background-color:#FF6263"></i>
				        </div>
				        <div class="weui_cell_bd weui_cell_primary" >
				            <p id="addcus_p">登记基本信息</p>
				        </div>
				        <div class="weui_cell_ft">
				        </div>
				    </a>
				</div>
				<!-- 职业信息  -->
	 	 	<%-- 	<s:if test="jobFlag == 1"> 
					<div  class="weui_cells weui_cells_access">
					    <a class="weui_cell" href="javascript:;">
					        <div class="weui_cell_hd ">
					          <i class='info-box-icon i i-dengji icon-font' style="background-color:#38adff"></i>
					        </div>
					        <div class="weui_cell_bd weui_cell_primary">
					            <p>职业信息</p>
					        </div>
					    </a>
					    <s:iterator value="mfCusPersonJobList" var="cusperJob">
					    	<form method="post" action="${webPath}/dingCusPerson/insertJobAjax" class="weui_cells weui_cells_form ding_cells_form" >
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">职业信息ID1</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="id" value="<s:property value="id" />"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">所在单位</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="workUnit" value="<s:property value="workUnit" />"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">客户ID</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="cusNo" value="<s:property value="cusNo" />"> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">单位性质</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="corpKind">
											<option value="1" <s:if test="corpKind == 1">selected</s:if>>机关单位</option>
											<option value="2" <s:if test="corpKind == 2">selected</s:if>>三资</option>
											<option value="3" <s:if test="corpKind == 3">selected</s:if>>国营</option>
											<option value="4" <s:if test="corpKind == 4">selected</s:if>>集体</option>
											<option value="5" <s:if test="corpKind == 5">selected</s:if>>私营</option>
											<option value="6" <s:if test="corpKind == 6">selected</s:if>>个体</option>
											<option value="7" <s:if test="corpKind == 7">selected</s:if>>其他</option>
										</select>
									</div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">单位电话</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="telephone" value="<s:property value="telephone" />"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">本单位工作起始年份</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="begYearCorp" value="<s:property value="begYearCorp" />" maxlength="4"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">职业</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="workKind" value="<s:property value="workKind" />"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">职务</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="duty" value="<s:property value="duty" />"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">职称</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="techTitle" value="<s:property value="techTitle" />"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">单位地址</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="address" value="<s:property value="address" />"> </div>
								</div>
							</form>
					    </s:iterator>
					</div>
	 			</s:if>  --%>
	 			
	 			<!-- 社会关系信息 -->
	 			<c:if test="${societyFlag eq 1}"> 
					<div  class="weui_cells weui_cells_access ding_cells">
					    <a class="weui_cell" href="javascript:;">
					        <div class="weui_cell_hd ">
					          <i class='info-box-icon i i-guanXi icon-font' style="background-color: #2e782d;"></i>
					        </div>
					        <div class="weui_cell_bd weui_cell_primary">
					            <p>社会关系</p>
					        </div>
					    </a>
					    <c:forEach items="${mfCusFamilyInfoList}" var="cusFamilyInfo">
					    	<form method="post" action="${webPath}/dingCusPerson/updateFamilyByOneAjax" class="weui_cells weui_cells_form ding_cells_form cusFamilyInfo" title="formcusfam00003" >
					    		<input type="hidden" name="id" value = "${cusFamilyInfo.id}">
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">与客户关系</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="relative">
											<option value="1" <c:if test="${cusFamilyInfo.relative eq 1}">selected</c:if>>配偶</option>
											<option value="2" <c:if test="${cusFamilyInfo.relative eq 2}">selected</c:if>>子女</option>
											<option value="3" <c:if test="${cusFamilyInfo.relative eq 3}">selected</c:if>>父母</option>
											<option value="4" <c:if test="${cusFamilyInfo.relative eq 4}">selected</c:if>>亲戚</option>
											<option value="5" <c:if test="${cusFamilyInfo.relative eq 5}">selected</c:if>>其他</option>
											<option value="6" <c:if test="${cusFamilyInfo.relative eq 6}">selected</c:if>>兄弟</option>
										</select>
									</div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">姓名</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="relName" value="${cusFamilyInfo.relName}"> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">证件类型</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="idTypeDesc"  disabled>
											<option value="0" <c:if test="${cusFamilyInfo.idType eq 0}">selected</c:if>>身份证</option>
											<option value="9" <c:if test="${cusFamilyInfo.idType eq 9}">selected</c:if>>警官证</option>
											<option value="1" <c:if test="${cusFamilyInfo.idType eq 1}">selected</c:if>>户口簿</option>
											<option value="2" <c:if test="${cusFamilyInfo.idType eq 2}">selected</c:if>>护照</option>
											<option value="3" <c:if test="${cusFamilyInfo.idType eq 3}">selected</c:if>>军官证</option>
											<option value="4" <c:if test="${cusFamilyInfo.idType eq 4}">selected</c:if>>士兵证</option>
											<option value="5" <c:if test="${cusFamilyInfo.idType eq 5}">selected</c:if>>港澳居民来往内地通行证</option>
											<option value="6" <c:if test="${cusFamilyInfo.idType eq 6}">selected</c:if>>台湾同胞来往内地通行证</option>
											<option value="7" <c:if test="${cusFamilyInfo.idType eq 7}">selected</c:if>>临时身份证</option>
											<option value="8" <c:if test="${cusFamilyInfo.idType eq 8}">selected</c:if>>外国人居留证</option>
										</select>
										<input type="hidden" name="idType" value="${cusFamilyInfo.idType}">
									</div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">证件号码</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="idNum" value="${cusFamilyInfo.idNum}"> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">性别</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="sexDesc" disabled>
											<option value="0" <c:if test="${cusFamilyInfo.sex eq 0}">selected</c:if>>男</option>
											<option value="1" <c:if test="${cusFamilyInfo.sex eq 1}">selected</c:if>>女</option>
											<option value="2" <c:if test="${cusFamilyInfo.sex eq 2}">selected</c:if>>未知</option>
										</select>
										<input type="hidden" name="sex" value="${cusFamilyInfo.sex}">
									</div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">联系电话</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel"  placeholder="请输入" name="relTel" value="${cusFamilyInfo.relTel}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">工作单位</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text"  placeholder="请输入" name="workUnit" value="${cusFamilyInfo.workUnit}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">联系地址</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text"  placeholder="请输入" name="postalAddress" value="${cusFamilyInfo.postalAddress}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">备注</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text"  placeholder="请输入" name="remark" value="${cusFamilyInfo.remark}"> </div>
								</div>
							</form>
					    </c:forEach>
					</div> 
				</c:if>
				<!-- 资产信息 -->
	 			<c:if test="${assetFlag eq 1}"> 
					<div  class="weui_cells weui_cells_access ding_cells">
					    <a class="weui_cell" href="javascript:;">
					        <div class="weui_cell_hd " >
					          <i class='info-box-icon i i-wenjianjia1 icon-font' ></i>
					        </div>
					        <div class="weui_cell_bd weui_cell_primary">
					            <p>资产信息</p>
					        </div>
					    </a>
					    <c:forEach items="${mfCusPersonAssetsInfoList}" var="cusperAssets">
					    	<form method="post" action="${webPath}/dingCusPerson/updateAssetsByOneAjax" class="weui_cells weui_cells_form ding_cells_form cusperAssets" data-car="formcuspersassets0004" data-house="formcuspersassets0003">
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">个人资产ID</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="assetsId" value="${cusperAssets.assetsId}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">资产类型</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="assetsType" value="${cusperAssets.assetsType}" readonly="readonly"> </div>
								</div>
								<div class="weui_cell weui_cell_select" style="display: none;">
									<div class="weui_cell_hd"> <label for="" class="weui_label">资产类型</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="assetsTypeId" readonly="readonly">
										<c:if test="${cusperAssets.assetsType eq '汽车'}">	<option value="1">汽车</option> </c:if>
										<c:if test="${cusperAssets.assetsType eq '房屋'}">	<option value="2">房屋</option> </c:if>
										</select>
										<input type="hidden" value="<s:property value="assetsType" />">
									</div>
								</div> 
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">客户号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="cusNo" value="${cusperAssets.cusNo}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">所有权人</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="cusName" value="${cusperAssets.cusName}" readonly="readonly"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">资产名称</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="assetsName" value="${cusperAssets.assetsName}"> </div>
								</div>
								<c:if test="${cusperAssets.assetsType eq '汽车'}">
									<div class="weui_cell" >
										<div class="weui_cell_hd"> <label class="weui_label">车辆品牌</label> </div>
										<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="vehicleBrand" value="${cusperAssets.vehicleBrand}"> </div>
									</div>
									<div class="weui_cell">
										<div class="weui_cell_hd"> <label class="weui_label">车牌号</label> </div>
										<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="assetsNo" value="${cusperAssets.assetsNo}"> </div>
									</div>
									<div class="weui_cell">
										<div class="weui_cell_hd"><label class="weui_label">排量</label>	</div>
										<div class="weui_cell_bd weui_cell_primary"><input class="weui_input" type="tel"  placeholder="请输入" name="assetsArea" value="<s:property value="assetsArea" />"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">L</span></div>
									</div>
								</c:if>
								<c:if test="${cusperAssets.assetsType eq '房屋'}">
									<div class="weui_cell weui_cell_select" style="display: flex;">
										<div class="weui_cell_hd"> <label for="" class="weui_label">房产类型</label> </div>
										<div class="weui_cell_bd weui_cell_primary">
											<select class="weui_select" name="houseType" mustinput="1">
												<option value="1" <c:if test="${cusperAssets.houseType eq 1}">selected</c:if>>住宅</option>
												<option value="2" <c:if test="${cusperAssets.houseType eq 2}">selected</c:if>>商企</option>
												<option value="3" <c:if test="${cusperAssets.houseType eq 3}">selected</c:if>>厂房</option>
												<option value="4" <c:if test="${cusperAssets.houseType eq 4}">selected</c:if>>办公楼</option>
											</select>
										</div>
									</div>
									<div class="weui_cell">
										<div class="weui_cell_hd"> <label class="weui_label">房产证号</label> </div>
										<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="assetsNo" value="${cusperAssets.assetsNo}"> </div>
									</div>
									<div class="weui_cell">
										<div class="weui_cell_hd"> <label class="weui_label">面积</label> </div>
										<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" mustinput="1" placeholder="请输入" name="assetsArea" value="${cusperAssets.assetsArea}"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">m²</span> </div>
									</div>
								</c:if>
								
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">资产归属地</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="assetsPlace" value="${cusperAssets.assetsPlace}"> </div>
								</div>
								
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">市值</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput="1" placeholder="请输入" name="assetsValue" value="<s:property value="%{formatDouble(assetsValue)}" />"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">元</span> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">购买价值</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)"  placeholder="请输入" name="buyValue" value="<s:property value="%{formatDouble(buyValue)}" />"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">元</span> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">购买日期</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="date"  placeholder="请输入" name="buyDate" value="<s:property value="%{formatDate(buyDate)}" />"> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">是否按揭</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="isMortgage" onchange="isMortgageChanged(this);">
											<option value="Y" <c:if test='${cusperAssets.isMortgage eq "Y"}'>selected</c:if> >是</option>
											<option value="N" <c:if test='${cusperAssets.isMortgage eq "N"}'>selected</c:if> >否</option>
										</select>
									</div>
									<input type="hidden" value="${cusperAssets.isMortgage}">
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">按揭贷款余额</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" placeholder="请输入" name="loanBalance" value="<s:property value="%{formatDouble(loanBalance)}" />"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">元</span> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">按揭剩余期限</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)"  placeholder="请输入" name="loanPeriod" value="${cusperAssets.loanPeriod}"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">个月</span> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">备注</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text"  placeholder="请输入" name="remark" value="${cusperAssets.remark}"> </div>
								</div>
							</form>
					    </c:forEach>
					</div> 
				</c:if>
				
				<!-- 股东信息 -->
	 			<c:if test="${shareholderFlag eq 1}"> 
					<div  class="weui_cells weui_cells_access ding_cells">
					    <a class="weui_cell" href="javascript:;">
					        <div class="weui_cell_hd ">
					          <i class='info-box-icon i i-ren2 icon-font' style="background-color: #2e782d;"></i>
					        </div>
					        <div class="weui_cell_bd weui_cell_primary">
					            <p>股东信息</p>
					        </div>
					    </a>
					    <c:forEach items="${mfCusShareholderList}" var="cusShareholder">
					    	<form method="post" action="${webPath}/dingCusBusiness/updateShareholderByOneAjax" class="weui_cells weui_cells_form ding_cells_form cusShareholder" title="formcussha00004">
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">股东姓名</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="shareholderName" value="${cusShareholder.shareholderName}"> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">股东类型</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="shareholderTypeDesc" >
											<c:if test="${cusShareholder.shareholderType eq 1}"><option value="1">企业</option> </c:if>
											<c:if test="${cusShareholder.shareholderType eq 2}"><option value="2">个人</option> </c:if>
										</select>
										<input type="hidden" name = "shareholderType" value="${cusShareholder.shareholderType}">
									</div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">证件类型</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="idType">
											<c:if test='${cusShareholder.idType eq "A"}'> <option value="A">组织机构代码</option> </c:if>
											<c:if test='${cusShareholder.idType eq "B"}'> <option value="B">社会信用代码</option> </c:if>
											<c:if test='${cusShareholder.idType eq "C"}'> <option value="C">营业执照号码</option> </c:if>
											<c:if test='${cusShareholder.idType eq "0"}'> <option value="0">身份证</option> </c:if>
											<c:if test='${cusShareholder.idType eq "9"}'> <option value="9">警官证</option> </c:if>
											<c:if test='${cusShareholder.idType eq "1"}'> <option value="1">户口簿</option> </c:if>
											<c:if test='${cusShareholder.idType eq "2"}'>  <option value="2">护照</option> </c:if>
											<c:if test='${cusShareholder.idType eq "3"}'> <option value="3">军官证</option> </c:if>
											<c:if test='${cusShareholder.idType eq "4"}'> <option value="4">士兵证</option> </c:if>
											<c:if test='${cusShareholder.idType eq "5"}'> <option value="5">港澳居民来往内地通行证</option> </c:if>
											<c:if test='${cusShareholder.idType eq "6"}'> <option value="6">台湾同胞来往内地通行证</option> </c:if>
											<c:if test='${cusShareholder.idType eq "7"}'> <option value="7">临时身份证</option> </c:if>
											<c:if test='${cusShareholder.idType eq "8"}'> <option value="8">外国人居留证</option> </c:if>
										</select>
									</div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">证件号码</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="idNum" value="${cusShareholder.idNum}"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">股金证号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="sharesMoneyNo" value="${cusShareholder.sharesMoneyNo}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">出资方式</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1"  readonly="readonly"  placeholder="请输入" name="pushCapitalTypeDes" value="<s:property value="%{getPushCapitalType(pushCapitalType)}" />"></div>
									<input class="weui_input" type="hidden" mustinput="1" readonly  placeholder="请输入" name="pushCapitalType" value="<s:property value="pushCapitalType" />">
								</div>
								<!-- <div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">出资方式</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="pushCapitalType">
											<option value="1">货币</option>
											<option value="2">实物</option>
											<option value="3">工业产权</option>
											<option value="4">非专利技术</option>
											<option value="5">土地使用权</option>
											<option value="6">其他</option>
										</select>
									</div>
								</div> -->
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">出资金额</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput="1" placeholder="请输入" name="pushCapitalAmt" value="<s:property value="%{formatDouble(pushCapitalAmt)}" />"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">元</span> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">客户号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="cusNo" value="<s:property value="cusNo" />"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">股权比例</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput="1" placeholder="请输入" name="pushCapitalScale" value="${cusShareholder.pushCapitalScale}"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">%</span> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">出资日期</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="date" mustinput="1" placeholder="请输入" name="pushCapitalDate" value="<s:property value="%{formatDate(pushCapitalDate)}" />"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">备注</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text"  placeholder="请输入" name="remark" value="${cusShareholder.remark}"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">股东信息ID</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="shareholderId" value="${cusShareholder.shareholderId}"> </div>
								</div>
							</form>
					    </c:forEach>
					</div> 
				</c:if>
				<!-- 高管信息 -->
	 			<c:if test="${hightManagerFlag eq 1}"> 
					<div  class="weui_cells weui_cells_access ding_cells">
					    <a class="weui_cell" href="javascript:;">
					        <div class="weui_cell_hd ">
					          <i class='info-box-icon i i-ren2 icon-font' style="background-color: #FCB265;"></i>
					        </div>
					        <div class="weui_cell_bd weui_cell_primary">
					            <p>高管信息</p>
					        </div>
					    </a>
					    <c:forEach items="${mfCusHighInfoList}" var="cusHighInfo">
					    	<form method="post" action="${webPath}/dingCusBusiness/updateHighInfoByOneAjax" class="weui_cells weui_cells_form ding_cells_form cusHighInfo" title="formcushigh00004">
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">高管信息ID</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="highId" value="${cusHighInfo.highId}"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">客户号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="cusNo" value="${cusHighInfo.cusNo}"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">客户名</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="cusName" value="${cusHighInfo.cusName}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">高管人员名称</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="highName" value="${cusHighInfo.highName}"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">labelName</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="fieldName" value="${cusHighInfo.fieldName}"> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">高管人员类别</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="highCusType">
											<option value="1" <c:if test="${cusHighInfo.highCusType eq 1}">selected</c:if>>法人代表</option>
											<option value="2" <c:if test="${cusHighInfo.highCusType eq 2}">selected</c:if> >总经理</option>
											<option value="3" <c:if test="${cusHighInfo.highCusType eq 3}">selected</c:if> >财务负责人</option>
											<option value="4" <c:if test="${cusHighInfo.highCusType eq 4}">selected</c:if> >董事长</option>
										</select>
									</div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">证件类型</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="idType">
											<option value="0" <c:if test="${cusHighInfo.idType eq 0}">selected</c:if>>身份证</option>
											<option value="9" <c:if test="${cusHighInfo.idType eq 9}">selected</c:if>>警官证</option>
											<option value="1" <c:if test="${cusHighInfo.idType eq 1}">selected</c:if>>户口簿</option>
											<option value="2" <c:if test="${cusHighInfo.idType eq 2}">selected</c:if>>护照</option>
											<option value="3" <c:if test="${cusHighInfo.idType eq 3}">selected</c:if>>军官证</option>
											<option value="4" <c:if test="${cusHighInfo.idType eq 4}">selected</c:if>>士兵证</option>
											<option value="5" <c:if test="${cusHighInfo.idType eq 5}">selected</c:if>>港澳居民来往内地通行证</option>
											<option value="6" <c:if test="${cusHighInfo.idType eq 6}">selected</c:if>>台湾同胞来往内地通行证</option>
											<option value="7" <c:if test="${cusHighInfo.idType eq 7}">selected</c:if>>临时身份证</option>
											<option value="8" <c:if test="${cusHighInfo.idType eq 8}">selected</c:if>>外国人居留证</option>
										</select>
									</div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">证件号码</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="idNum" value="${cusHighInfo.idNum}"> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">性别</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="sex">
											<option value="0" <c:if test="${cusHighInfo.sex eq 0}">selected</c:if>>男</option>
											<option value="1" <c:if test="${cusHighInfo.sex eq 1}">selected</c:if>>女</option>
											<option value="2" <c:if test="${cusHighInfo.sex eq 2}">selected</c:if>>未知</option>
										</select>
									</div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">出生日期</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="date" mustinput="1" placeholder="请输入" name="brithday" value="<s:property value="%{formatDate(brithday)}" />"> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">最高学历</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="education">
											<option value="2" <c:if test="${cusHighInfo.education eq 2}">selected</c:if>>大学本科</option>
											<option value="1" <c:if test="${cusHighInfo.education eq 1}">selected</c:if>>研究生</option>
											<option value="0" <c:if test="${cusHighInfo.education eq 0}">selected</c:if>>未知</option>
											<option value="3" <c:if test="${cusHighInfo.education eq 3}">selected</c:if>>大学专科和专科学校</option>
											<option value="4" <c:if test="${cusHighInfo.education eq 4}">selected</c:if>>中等专业学校或中等技术学校</option>
											<option value="5" <c:if test="${cusHighInfo.education eq 5}">selected</c:if>>技术学校</option>
											<option value="6" <c:if test="${cusHighInfo.education eq 6}">selected</c:if>>高中</option>
											<option value="7" <c:if test="${cusHighInfo.education eq 7}">selected</c:if>>初中</option>
											<option value="8" <c:if test="${cusHighInfo.education eq 8}">selected</c:if>>小学</option>
											<option value="9" <c:if test="${cusHighInfo.education eq 9}">selected</c:if>>文盲或半文盲</option>
										</select>
									</div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">工作简历</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text"  placeholder="请输入" name="highCusResume" value="${cusHighInfo.highCusResume}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">备注</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text"  placeholder="请输入" name="remark" value="${cusHighInfo.remark}"> </div>
								</div>
							</form>
					    </c:forEach>
					</div> 
				</c:if>
			<!-- 对外投资信息 -->
	 			<c:if test="${equityFlag eq 1}"> 
					<div  class="weui_cells weui_cells_access ding_cells">
					    <a class="weui_cell" href="javascript:;">
					        <div class="weui_cell_hd ">
					          <i class='info-box-icon i i-credit icon-font' style="background-color: #FC6596;" ></i>
					        </div>
					        <div class="weui_cell_bd weui_cell_primary">
					            <p>对外投资</p>
					        </div>
					    </a>
					    <c:forEach items="${cusEquityInfoList}" var="cusEquityInfo">
					    	<form method="post" action="${webPath}/dingCusBusiness/updateEquityInfoByOneAjax" class="weui_cells weui_cells_form ding_cells_form cusEquityInfo" title = "formcusequ00004">
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">投资情况ID</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="equityInfoId" value="${cusEquityInfo.equityInfoId}"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">客户姓名</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="cusName" value="${cusEquityInfo.cusName}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">投向企业名称</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="toCorpName" value="${cusEquityInfo.toCorpName}"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">客户编号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="cusNo" value="${cusEquityInfo.cusNo}"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">股权证号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="eqCardNo" value="${cusEquityInfo.eqCardNo}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">社会信用代码</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="orgNo" value="${cusEquityInfo.orgNo}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">注册资本</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput="1" placeholder="请输入" name="toCorpRegCapital" value="<fmt:formatNumber type='number' minFractionDigits='2' value='${cusEquityInfo.toCorpRegCapital}' />"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">元</span> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">出资方式</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1"  readonly="readonly"  placeholder="请输入" name="pushCapitalTypeDes" value="<s:property value="%{getPushCapitalType(pushCapitalType)}" />"></div>
									<input class="weui_input" type="hidden" mustinput="1" readonly  placeholder="请输入" name="pushCapitalType" value="${cusEquityInfo.pushCapitalType}">
								</div>
								<!-- <div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">出资方式</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="pushCapitalType">
											<option value="1">货币</option>
											<option value="2">实物</option>
											<option value="3">工业产权</option>
											<option value="4">非专利技术</option>
											<option value="5">土地使用权</option>
											<option value="6">其他</option>
										</select>
									</div>
								</div> -->
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">投资金额</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput="1" placeholder="请输入" name="pushCapitalAtm" value="<fmt:formatNumber type='number' minFractionDigits='2' value='${cusEquityInfo.pushCapitalAtm}' />"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">元</span> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">股权比例</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput="1" placeholder="请输入" name="pushCapitalScale" value="${cusEquityInfo.pushCapitalScale}"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">%</span> </div>
								</div>
								<!-- <div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">登记时间</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="regTime" value=""> </div>
								</div> -->
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">出资日期</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="date" mustinput="1" placeholder="请输入" name="pushCapitalDate" value="<s:property value="%{formatDate(pushCapitalDate)}" />"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">备注</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="remark" value="${cusEquityInfo.remark}"> </div>
								</div>
							</form>
					    </c:forEach>
					</div> 
				</c:if>
			<!-- 账户管理 -->
	 			<c:if test="${bankAccFlag eq 1}"> 
					<div  class="weui_cells weui_cells_access ding_cells">
					    <a class="weui_cell" href="javascript:;">
					        <div class="weui_cell_hd ">
					          <i class='info-box-icon i i-zhanghu icon-font' style="background-color: #9865FC;"></i>
					        </div>
					        <div class="weui_cell_bd weui_cell_primary">
					            <p>账户管理</p>
					        </div>
					    </a>
					    <c:forEach items="${bankAccManageList}" var="bankAccInfo">
					    	<form method="post" action="${webPath}/dingCusBusiness/updateBankAccByOneAjax" class="weui_cells weui_cells_form ding_cells_form bankAccInfo" title="formcusbank00002">
					    		<input class="weui_input" type="hidden" mustinput="1" placeholder="" name="id" value="${bankAccInfo.id}">
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">账户名称</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入账户名称" name="accountName" value="${bankAccInfo.accountName}"> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">账户用途</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="useType">
											<option value="0"  <c:if test="${bankAccInfo.useType eq 0}">selected</c:if>>主账户</option>
											<option value="1"  <c:if test="${bankAccInfo.useType eq 1}">selected</c:if>>退保证金账户</option>
											<option value="2"  <c:if test="${bankAccInfo.useType eq 2}">selected</c:if>>扣保证金账户</option>
											<option value="3"  <c:if test="${bankAccInfo.useType eq 3}">selected</c:if>>放款账户</option>
											<option value="4"  <c:if test="${bankAccInfo.useType eq 4}">selected</c:if>>还款账户</option>
											<option value="5"  <c:if test="${bankAccInfo.useType eq 5}">selected</c:if>>其他账户</option>
											<option value="6"  <c:if test="${bankAccInfo.useType eq 6}">selected</c:if>>收款账户</option>
										</select>
									</div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">卡号/折号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" mustinput="1" placeholder="请输入正确的银行卡号" name="accountNo" value="${bankAccInfo.accountNo}" maxlength="30"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">开户行号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="bankNumbei" value="${bankAccInfo.bankNumbei}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">开户行</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="请输入" name="bank" value="${bankAccInfo.bank}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">开户行地区</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text"  placeholder="请输入" name="bankArea" value="${bankAccInfo.bankArea}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">开户行全称</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text"  placeholder="请输入" name="bankFullName" value="${bankAccInfo.bankFullName}"> </div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">账户类型</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="cardType" value="${bankAccInfo.cardType}"> </div>
								</div>
								<div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">单笔扣款限额</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput="1" placeholder="请输入" name="debitAmount" value="<fmt:formatNumber type='number' minFractionDigits='2' value='${bankAccInfo.debitAmount}' />"><span class="input-group-addon" style="position: absolute;right: 15px;top: 16px;font-size: 13px;">元</span> </div>
								</div>
								<div class="weui_cell weui_cell_select">
									<div class="weui_cell_hd"> <label for="" class="weui_label">启用状态</label> </div>
									<div class="weui_cell_bd weui_cell_primary">
										<select class="weui_select" name="useFlag">
											<option <c:if test="${bankAccInfo.useFlag eq 1}">selected="selected" </c:if> value="1">启用</option>
											<option <c:if test="${bankAccInfo.useFlag eq 0}">selected="selected" </c:if> value="0">禁用</option>
										</select>
									</div>
								</div>
								<div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">客户号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="cusNo" value="${bankAccInfo.cusNo}"> </div>
								</div>
								<%-- <div class="weui_cell" style="display:none;">
									<div class="weui_cell_hd"> <label class="weui_label">唯一编号</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" placeholder="请输入" name="id" value="<s:property value="id" />"> </div>
								</div> --%>
								<%-- <div class="weui_cell">
									<div class="weui_cell_hd"> <label class="weui_label">备注</label> </div>
									<div class="weui_cell_bd weui_cell_primary"> <input class="weui_input" type="text" mustinput="1" placeholder="200字以内" name="remark" value="<s:property value="remark" />"> </div>
								</div> --%>
							</form>
					    </c:forEach>
					</div> 
				</c:if>
			</div>
	</body>
	<script>
		//判断是否已经填写
		var cusCompleteFlag ="${cusCompleteFlag}";//默认已经填写 "${cusCompleteFlag}";
		var cusNo = '${cusNo}';
		var appId = '${appId}';
		var firstKindNo = '${firstKindNo}';
		
		var cusBaseType = '${mfCusCustomer.cusBaseType}';
		$(function(){
			MfAppBusApply.getDDReady();//钉钉配置调用dd jsapi
			dd.ready(function(){
					document.addEventListener('backbutton', function(e) {
		              // 在这里处理你的业务逻辑
						  	 toList();
		              e.preventDefault(); //backbutton事件的默认行为是回退历史记录，如果你想阻止默认的回退行为，那么可以通过preventDefault()实现
		  			 });
					//ios处理
// 					dd.biz.navigation.setLeft({
// 					    show: true,//控制按钮显示， true 显示， false 隐藏， 默认true
// 					    control: true,//是否控制点击事件，true 控制，false 不控制， 默认false
// 					    showIcon: false,//是否显示icon，true 显示， false 不显示，默认true； 注：具体UI以客户端为准
// 					    text: '',//控制显示文本，空字符串表示显示默认文本
// 					    onSuccess : function(result) {
// 							  	 toList();
// 					    },
// 					    onFail : function(err) {}
// 					});
				$("#phone").click(function(){
					var phoneNumber =$("#contactsTelDetailTitle").text();
					if(isNaN(phoneNumber)){
						alert("请录入手机号");
						return false;
					}else{
						dd.biz.telephone.showCallMenu({
						    phoneNumber: phoneNumber, // 期望拨打的电话号码
						    code: '+86', // 国家代号，中国是+86
						    showDingCall: false, // 是否显示钉钉电话
						    onSuccess : function() {},
						    onFail : function() {}
						});
					}
				})
			});	
			//获取业务申请信息
			if(appId && appId != 'null'){
				$.ajax({
					url:"MfAppBusApplyActionAjax_getBusInfoAjax.action",
					data:{appId:appId,cusNo:cusNo},
					type:"POST",
					dataType:"json",
					beforeSend:function(){ 
						
					},success:function(data) {
						if (data.flag == "success") {
							if(data.showFlag=="none"){
							}else{
								// 填充业务信息
								setBusInfo(data);
							}
						}
						
					},
					error:function() {
			
					}
				});
			}else{
				$("#appInfoPanel").hide();
			}
			//新增业务
			$("#addBus").click(function(){
				if(firstKindNo == '' || firstKindNo == null){
					dd.device.notification.alert({
			    	    message: "请先配置产品信息",
			    	    buttonName: "确定",
			    	    onSuccess : function() {
			    	        //onSuccess将在点击button之后回调
			    	    },
			    	    onFail : function(err) {}
			    	});
					return ;
				}
				if(cusCompleteFlag=="1"){// 判断该客户是否完善了基本信息
					window.location.href = "MfAppBusApplyAction_inputQuery.action?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
				}else{
					dd.device.notification.alert({
			    	    message: "请完善基本信息",
			    	    buttonName: "确定",
			    	    onSuccess : function() {
			    	        //onSuccess将在点击button之后回调
			    	    },
			    	    onFail : function(err) {}
			    	});
				}
			});
			$("#addimg").click(function(){
				window.location.href = "DingUploadFileAction_toUploadPage.action?scNo=0000000001&cusNo="+ cusNo;
			});
			
			$("#addcus").empty();
				//个人
			$("#addcus").load("DingCusPersBaseInfoAction_toUpdateBaseInfoDiv.action?cusNo="+cusNo,function(){
				//添加完善资料按钮
// 				$("#insertForm").after("<div style=\"padding-bottom:15px;text-align: center;color: #3bbaff;\" onclick=\"goCusCustomerCompleteInfo();\"><i class=\"i i-bianji3 ding_complete_icon\"></i>完善资料</div>");
			}); 
			/* if(cusCompleteFlag=="1"){
				$("#addcus").empty();
					//个人
				$("#addcus").load("DingCusPersBaseInfoAction_toUpdateBaseInfoDiv.action?cusNo="+cusNo); 
			}else{
				$("#addcus").click(function(){
					window.location.href = "DingCusPersBaseInfoAction_toUpdateBaseInfo.action?cusNo="+cusNo;
				});
			} */	
			$("select[name='isMortgage']").each(function(){
				isMortgageChanged(this);
			});
			//所有只读input 改 颜色样式
			$("input[readonly='readonly']").each(function(){
				var $this = $(this);
				$this.css("color","graytext");
			});
			//社会关系的单字段编辑
			$(".cusFamilyInfo input").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				if(!thisValue || thisValue == ""){
					return;
				}
				//attr has readonly return
				if($this.attr("readonly")){
					return;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='id']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"id\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			$(".cusFamilyInfo select").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				if(!thisValue || thisValue == ""){
					return ;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='id']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"id\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			
			//资产信息
			$(".cusperAssets input").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				if(!thisValue || thisValue == ""){
					return ;
				}
				//attr has readonly return
				if($this.attr("readonly")){
					return;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='assetsId']").val();
				var formId = "";
				var assetsTypeId = $form.find("select[name='assetsTypeId']").val();
				if(assetsTypeId == "1"){//汽车
					formId = $form.data("car");
				}else if(assetsTypeId == "2"){
					formId = $form.data("house");
				}
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"assetsId\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			$(".cusperAssets select").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				if(!thisValue || thisValue == ""){
					return ;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='assetsId']").val();
				var formId = "";
				var assetsTypeId = $form.find("select[name='assetsTypeId']").val();
				if(assetsTypeId == "1"){//汽车
					formId = $form.data("car");
				}else if(assetsTypeId == "2"){
					formId = $form.data("house");
				}
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"assetsId\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			//账户信息的单字段编辑
			$(".bankAccInfo input").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				var thisLabel = $this.parents(".weui_cell").find("label").text();
				var isMust = $this.attr("mustinput");
				if((!thisValue || thisValue == "") && isMust == "1"){
					if(thisName == "debitAmount"){//为空给初始值
						$this.val("0.00");
					}else{
						$.alert(thisLabel+"不能为空！");
// 						$this.focus();
						return ;
					}
				}
				//attr has readonly return
				if($this.attr("readonly")){
					return;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='id']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"id\",\"value\":\""+idValue+"\"}]";
// 				updateFormByOneAjax($form,formId,ajaxData);
				if(updateFormByOneAjax($form,formId,ajaxData) && thisName == "accountNo"){//账号信息同步更新开户行号及开户行
					DingUtil.getBankByCardNumber(this);
				}
			});
			$(".bankAccInfo select").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				if(!thisValue || thisValue == ""){
					return ;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='id']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"id\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			//股东信息的单字段编辑
			$(".cusShareholder input").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				var thisLabel = $this.parents(".weui_cell").find("label").text();
				var isMust = $this.attr("mustinput");
				if((!thisValue || thisValue == "") && isMust == "1"){
					if(thisName == "pushCapitalScale"){//为空给初始值
						$this.val("0.00");
					}else{
						$.alert(thisLabel+"不能为空！");
// 						$this.focus();
						return ;
					}
				}
				//attr has readonly return
				if($this.attr("readonly")){
					return;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='shareholderId']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"shareholderId\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			$(".cusShareholder select").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				if(!thisValue || thisValue == ""){
					return ;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='shareholderId']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"shareholderId\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			//高管信息的单字段编辑
			$(".cusHighInfo input").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				var thisLabel = $this.parents(".weui_cell").find("label").text();
				var isMust = $this.attr("mustinput");
				if((!thisValue || thisValue == "") && isMust == "1"){
					if(thisName == "pushCapitalScale"){//为空给初始值
						$this.val("0.00");
					}else{
						$.alert(thisLabel+"不能为空！");
// 						$this.focus();
						return ;
					}
				}
				//attr has readonly return
				if($this.attr("readonly")){
					return;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='highId']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"highId\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			$(".cusHighInfo select").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				if(!thisValue || thisValue == ""){
					return ;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='highId']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"highId\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			//对外投资的单字段编辑
			$(".cusEquityInfo input").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				var thisLabel = $this.parents(".weui_cell").find("label").text();
				var isMust = $this.attr("mustinput");
				if((!thisValue || thisValue == "") && isMust == "1"){
					if(thisName == "toCorpRegCapital" || thisName == "pushCapitalAtm" || thisName == "pushCapitalScale"){//为空给初始值
						$this.val("0.00");
					}else{
						$.alert(thisLabel+"不能为空！");
// 						$this.focus();
						return ;
					}
				}
				//attr has readonly return
				if($this.attr("readonly")){
					return;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='equityInfoId']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"equityInfoId\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
			$(".cusEquityInfo select").change(function(){
				var $this = $(this);
				var thisName = $this.attr("name");
				var thisValue = $this.val();
				if(!thisValue || thisValue == ""){
					return ;
				}
				var $form = $this.parents("form");
				//[{"name":"relName","value":"qwq123"},{"name":"id","value":"fam17082317544932524"}]
				var idValue= $form.find("input[name='equityInfoId']").val();
				var formId = $form.attr("title");
				var ajaxData = "[{\"name\":\""+thisName+"\",\"value\":\""+thisValue+"\"},{\"name\":\"equityInfoId\",\"value\":\""+idValue+"\"}]";
				updateFormByOneAjax($form,formId,ajaxData);
			});
		});
		//是否按揭控制
		function isMortgageChanged(obj){
			var isMortgage = obj.value;
			var $this = $(obj);
			if(isMortgage && isMortgage != ""){
				if('N'==isMortgage){//没有按揭 隐藏清空值
					$this.parents(".weui_cell").next(".weui_cell").hide();
					$this.parents(".weui_cell").next(".weui_cell").next(".weui_cell").hide();
					$this.parents(".weui_cell").next(".weui_cell").find("input[name='loanBalance']").val('');
					$this.parents(".weui_cell").next(".weui_cell").next(".weui_cell").find("input[name='loanPeriod']").val('');
				}else{
					$this.parents(".weui_cell").next(".weui_cell").show();
					$this.parents(".weui_cell").next(".weui_cell").next(".weui_cell").show();
				}			
			}
		}
		//组装业务信息数据
		function setBusInfo(data){
			var rateUnit = data.rateUnit;
			var busInfoObj = data.mfbusInfo;
			var mfBusApplyListTmp = data.mfBusApplyList;
			var appName = busInfoObj.appName;
			if (appName.length > 22) {
				appName = appName.substring(0, 22) + "...";
			}
			var htmlStr="";
			var busId = busInfoObj.appId;
			/* if(data.showFlag=="apply"){		
				htmlStr = "<div class=\"second_part\" >"+
"						<div class=\"weui_cells weui_cells_access\"  id=\"weui-ca\">"+
"							<a class=\"weui_cell\" href=\"javascript:;\">"+
"								<div class=\"weui_cell_hd\">"+
"								</div>"+
"								<div class=\"weui_cell_bd weui_cell_primary\">"+
"									<span class='info-box-icon i i-applyinfo icon-font' style='background-color: #657FFC;'></span><span class=\"\">申请信息</span>"+
"								</div>"+
"							</a>"+
"						</div>";
			}else if(data.showFlag=="pact"){
				var busSts = busInfoObj.pactSts;
				htmlStr = "<div class=\"second_part\" >"+
"						<div class=\"weui_cells weui_cells_access\"  id=\"weui-ca\">"+
"							<a class=\"weui_cell\" href=\"javascript:;\">"+
"								<div class=\"weui_cell_hd\">"+
"								</div>"+
"								<div class=\"weui_cell_bd weui_cell_primary\">"+
"									<span class='info-box-icon i i-applyinfo' style='margin-right:10px;font-size:24px'></span><span class=\"\">合同信息</span>"+
"								</div>"+
"							</a>"+
"						</div>";
			} */
			
			
				htmlStr = "<div class=\"second_part\" >"+
"						<div class=\"weui_cells weui_cells_access\"  id=\"weui-ca\">"+
"							<a class=\"weui_cell\" href=\"javascript:;\">"+
"								<div class=\"weui_cell_hd\">"+
"								</div>"+
"								<div class=\"weui_cell_bd weui_cell_primary\">"+
"									<span class='info-box-icon i i-applyinfo icon-font' style='background-color: #3bbaff;'></span><span class=\"font_blue\">"+appName+"</span>"+
"								</div>"+
"							</a>"+
"						</div>";
			/* 	htmlStr = htmlStr+ "<div class=\"mf_list_item\">"+
"							<div class=\"xiangmuxinxi ding-bule\" style='padding-left:18px;'>"+
"								 </i><span>"+appName+"</span>"+
"							</div>"+
"						</div>"; */
		
				var unitStr = "天";
				if (busInfoObj.termType == "1") {
					unitStr = "个月";
				}
				htmlStr = htmlStr + 
"						<div class=\"mf_list_item\">"+
"							<div class=\"baozhengxinxi\">"+
"								<div class=\"form_xinxi_left\">"+
"									<span class='gery-mid-text'>申请金额</span>"+
"									<p>"+busInfoObj.fincAmt+" 万元</p>"+
"								</div>"+
"								<div class=\"form_xinxi_right\">"+
"									<span class='gery-mid-text'>申请期限</span>"+
"									<p> "+busInfoObj.term+unitStr+
"									</p>"+
"								</div>"+
"								<div class=\"form_xinxi_left\">"+
"									<span class='gery-mid-text'>申请利率</span>"+
"									<p>"+ busInfoObj.fincRate.toFixed(2)+rateUnit+"</p>"+
"								</div>";
				if(data.showFlag=="apply"){	//如果是申请信息 带上申请时间	
					htmlStr = htmlStr+
"								<div class=\"form_xinxi_right\">"+
"									<span class='gery-mid-text'>申请日期</span>"+
"									<p>"+busInfoObj.appTimeShow+"</p>"+
"								</div>";
				}
				htmlStr = htmlStr + "<div class=\"form_xinxi_left\">"+
"									<span class='gery-mid-text'>逾期利率</span>"+
"									<p>"+busInfoObj.overRate.toFixed(2)+rateUnit+"</p>"+
"								</div>"+
"								<div class=\"form_xinxi_right\">"+
"									<span class='gery-mid-text'>还款方式</span>"+
"									<p> "+data.repayTypeStr+
"									</p>"+
"								</div>"+
"								<div class=\"form_xinxi_left\">"+
"									<span class='gery-mid-text'>担保方式</span>"+
"									<p>"+data.vouTypeStr+"</p>"+
"								</div>"+
"								<div class=\"form_xinxi_right\">"+
"									<span class='gery-mid-text'>客户经理</span>"+
"									<p> "+busInfoObj.cusMngName+
"									</p>"+
"								</div>";
				htmlStr = htmlStr+
"								<div class=\"clearfloat\"></div>"+
"							</div>"+
"						</div>"+
"					</div>";
			$("#panel_db_1").html(htmlStr);
		}
		function goCusCustomerCompleteInfo(){
			location.href="MfAppCusCustomerAction_goCusCustomerCompleteInfo.action?cusNo="+cusNo+"&appId="+appId+"&cusBaseType="+cusBaseType;
		}
		/**
			$form表单jquery对象
			formId 对应xml表单ID
			ajaxData 单字段修改数据
		*/
		function updateFormByOneAjax($form,formId,ajaxData){
			var flag =false;
	  		//添加验证
	  		var url = $form.attr("action");
// 			var dataParam = JSON.stringify($(obj).serializeArray()); 
			$.showLoading("保存中,请稍后...");
			$.ajax({
				url:url,
				data:{ajaxData:ajaxData,formId:formId},
				type:'post',
				dataType:'json',
				async:false,
				success:function(data){
					$.hideLoading();
					if(data.flag=="success"){
						flag = true;
						$.toast("保存成功");
					}else{
						$.toast("保存失败", "cancel");
					}
				},error:function(){
					$.hideLoading();
					$.toast("未连接到服务器，请刷新页面重试！", "cancel");
				}
			});
			return flag;
		}
		function toList(){
			window.location.href="MfAppBusApplyAction_getListPage.action";
		}
	</script>
</html>
