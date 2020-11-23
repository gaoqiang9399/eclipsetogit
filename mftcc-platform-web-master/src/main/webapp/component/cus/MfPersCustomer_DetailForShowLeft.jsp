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
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_Detail.css" />
		<script type="text/javascript">
			var wkfAppId = '{wkfAppId}';
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
			$(function(){
				$(".container").mCustomScrollbar({
					advanced:{
						updateOnContentResize:true
					}
				});
			});
		</script>
	</head>
	
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<div class="col-xs-12 column block-left">
				<div class="bg-white block-left-div" >
					<!--客户基本信息提示 -->
						<div class="row clearfix bg-danger block-tip">
							<div class="col-xs-12 column text-center">
								<a class="btn btn-link" onclick="addBaseInfo();">
									请添加客户基本信息>>
								</a>
							</div>
						</div>
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
									<span><i class="i i-ren1"></i><span id = "contactsName">${mfCusPersBaseInfo.cusName}</span></span>
									<span class="vertical-line">|</span>
									<span><i class="i i-dianhua"></i><span id = "contactsTel">${mfCusPersBaseInfo.cusTel}</span></span>
									<span class="vertical-line">|</span>
									<span><i class="i i-idcard2" ></i><span id="idNum">${mfCusPersBaseInfo.idNum}</span></span>
								</p>
								<p>
									<i class="i i-location"></i>
									<span id = "commAddress">
										<c:if test="${mfCusPersBaseInfo.commAddress!=''}">
											${mfCusPersBaseInfo.commAddress}
										</c:if>
										<c:if  test="${mfCusPersBaseInfo.commAddress==''}">
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
				theme:"minimal-dark",
				updateOnContentResize:true
			}
		});
	});
</script>
</html>