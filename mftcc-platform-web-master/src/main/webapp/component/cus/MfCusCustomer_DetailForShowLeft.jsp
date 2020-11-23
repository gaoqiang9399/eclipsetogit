<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
	String authFlag = (String) request.getAttribute("authFlag");
	String authFormHtml = "";
	if("1".equals(authFlag)){
		authFormHtml = (String) request.getAttribute("authFormHtml");
	}else{
	}
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/appValue.js"></script>
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" />
		<script type="text/javascript">
			var wkfAppId = '${wkfAppId}';
			var cusType = '${mfCusCustomer.cusType}';
			var cusBaseFlag = '${cusBaseFlag}';//表示客户基本信息是否已经录入
			var cusFullFlag = '${cusFullFlag}';//客户信息所有表单信息是否已经全部录入
			var scNo = '${scNo}';//客户要件场景
			var authFlag = '${authFlag}';
			var headImg = '${headImg}';
			var ifUploadHead = '${ifUploadHead}';
			var	cusNo = '${cusNo}';
			var appId = '${mfBusApply.appId}';
			//dataMap是业务参数，包括客户表单信息和参与业务信息等。
			var dataMap = <%=request.getAttribute("dataMap")%>;
			var cusTableList = dataMap.cusTableList;
			var relNo = "cusNo-"+cusNo;
			var cusClassify= '${mfCusClassify.classifyType}';//客户类别，黑名单或者优质客户
			var rankTypeName = '${mfCusClassify.rankTypeName}';
			var webPath = '${webPath}';
			var authFormHtml = '<%=authFormHtml%>';
			var busSubmitCnt='';
		</script>
	</head>
	
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<div class="col-xs-12 column block-left">
				<div class="bg-white block-left-div" >
					<!--头部主要信息 -->
					<div class="row clearfix head-info">
						<!--头像 -->
						<div class="col-xs-3 column text-center head-img">
							<div class="btn btn-link">
								<img id="headImgShow" name="headImgShow" class="img-circle" />
							</div>
						</div>
						<!--概要信息 -->
						<div class="col-xs-7 column head-content">
							<div class="margin_bottom_5">
								<button  class="btn btn-link head-title" onclick="updateCustomerInfo();">
									${mfCusCustomer.cusName}
								</button>
							</div>
							<!--信息查看入口 -->
							<div class="margin_bottom_10">
								<button class="btn btn-view cus-tag" type="button" onclick="cusTagHis();">
									<i class="i i-ren2"></i><span  id="cusNameRate-span"></span>
								</button>
								<button class="btn btn-dodgerblue btn-view" type="button"  onclick="cusRelation();">
									<i class="i i-guanXi"></i><span>关联关系</span>
								</button>
								<c:if test='${mfCusCustomer.cusLevelName!=null}'>
									<button  class="btn btn-danger btn-view" type="button" onclick="getEvalDetailResult('1');">
										<i class="i i-xing2"></i>${mfCusCustomer.cusLevelName}
									</button>
								</c:if>
								<c:if test='${mfCusCustomer.cusLevelName==null}'>
									<button class="btn btn-lightgray btn-view " type="button" onclick="getEvalDetailResult('0');">
										<i class="i i-xing2"></i>未评估
									</button>
								</c:if>
							</div>
							<div>
								<p>
									<span><i class="i i-ren1"></i><span id = "contactsName">${mfCusCustomer.contactsName}</span></span>
									<span class="vertical-line">|</span>
									<span><i class="i i-dianhua"></i><span id = "contactsTel">${mfCusCustomer.contactsTel}</span></span>
									<span class="vertical-line">|</span>
									<span><i class="i i-idcard2" ></i><span id="idNum">${mfCusCustomer.idNum}</span></span>
								</p>
								<p>
									<i class="i i-location"></i>
									<span id = "commAddress">
										<c:if test="${mfCusCustomer.commAddress!=''}">
											${mfCusCustomer.commAddress}
										</c:if>
										<c:if test="${mfCusCustomer.commAddress==''}">
											未登记
										</c:if>
									</span>
								</p>
							</div>
						</div>
						<!--客户类型图标-->
						<div class="col-xs-2 column">
							<div class="i i-warehouse cus-type-font">
								<div class="type-name-div">${cusTypeName}</div>
							</div>
						</div>
					</div>
					<!--信息登记操作入口 -->
					<%-- <div class="row clearfix btn-opt-group">
						<div class="col-xs-12 column">
							<div class="btn-group pull-right">
								<button class="btn btn-opt cus-add" onclick="addBlockInfo();" stype="display:none;" type="button">
									<i class="i i-jia2"></i><span>完善资料</span>	
								</button>
								<button class="btn btn-opt" onclick="getPfsDialog();" type="button">
									<i class="i i-qian1" ></i><span>财务报表</span>	
								</button>
								<button class="btn btn-opt" onclick="cusDocInfo();" type="button">
									<i class="i i-wenjian4"></i><span>图文资料</span>
								</button>
								<button class="btn btn-opt" onclick="cusTrack();" type="button">
									<i class="i i-dianhua"></i><span>客户跟进</span>
								</button>
								<button class="btn btn-opt" onclick="getAppAuth();" type="button">
									<i class="i i-zhanghu"></i><span>发起授信</span>
								</button>
								<button class="btn btn-opt" onclick="getInitatEcalApp();" type="button">
									<i class="i i-zhanghu"></i><span>发起评级</span>
								</button>
								<button class="btn btn-opt visible-lg-block" onclick="cusTag();" type="button">
									<i class="i i-ren"></i><span id="cusTag">客户分类</span>	
								</button>
								<button class="btn btn-opt visible-lg-block"  onclick="cusRelationIn('${mfCusCustomer.cusName}');">
									<i class="i i-guanXi"></i><span>关联关系</span>
								</button>
								<div class="btn-group hidden-lg">
									<button type="button" class="btn btn-opt dropdown-toggle"  data-toggle="dropdown">
										更多<span class="caret"></span>
									</button>
									<ul class="dropdown-menu btn-opt pull-right" role="menu">
										<li class="btn-opt" role="presentation" onclick="cusTag();">
											<button class="btn btn-opt more-btn-opt" onclick="cusTag();" type="button">
												<i class="i i-ren"></i><span id="cusTag">客户分类</span>	
											</button>
										</li>
										<li class="btn-opt" role="presentation" onclick="cusRelationIn();">
											<button class="btn btn-opt more-btn-opt" onclick="cusRelationIn();" type="button">
												<i class="i i-guanXi"></i><span>关联关系</span>	
											</button>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div> --%>
					<!--客户其他信息-->
					<div class="row clearfix">
						<div class="col-xs-12 column">
							<div class="dynamic-block">
							</div>
							<div class="block-add" style="display: none;">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_Detail.js"></script>
<script type="text/javascript">
	$(function(){
		$("*").removeAttr("onClick");
		$("*").removeAttr("onclick");
	/* 	$("*").removeClass("dblclickflag");
		$("*").removeClass("bolddblclickflag"); */
		$("a").attr("href","javascript:void(0)");
		$("div.dynamic-block").find(".i-jia3").remove();
		$("div.block-left").find("div.block-tip").remove();
		adjustheight();
		$(".container").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});
	});
</script>
</html>