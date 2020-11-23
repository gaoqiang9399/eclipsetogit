<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<title>风控尽调</title>
	<style type="text/css">
		.footer_loader,.backToTop{
			bottom:50px;
		}
		.dropdown-menu li a{
			color:#333;
		}
		.dropdown-menu li{
			height: 25px;
			padding: 2px 5px;
		}
		.dropdown-menu li:hover{
			background-color: #e6e6e6;
		}
	</style>
</head>
<body class="overflowHidden bg-white">
<div class="container block-left padding_right_10">
	<div class="col-md-12 column tabCont">
		<div class="row clearfix head-info ">
			<div class="col-md-6" style="padding:0px 80px;">
				<div class="row clearfix margin_bottom_15 font_size_18">
					${mfCusCustomer.cusName}
				</div>
				<div class="row clearfix padding_left_15">
					<p>
							<span><i class="i i-ren1 "></i>
								<span id = "contactsName">
									<c:if  test="${mfCusCustomer.contactsName!=null&&mfCusCustomer.contactsName!=''}">
										${mfCusCustomer.contactsName}
									</c:if>
									<c:if  test="${mfCusCustomer.contactsName==null||mfCusCustomer.contactsName==''}">
										<span class="unregistered">未登记</span>
									</c:if>
								</span>
							</span>
						<span class="vertical-line"></span>
						<span><i class="i i-dianhua "></i>
							<span id = "contactsTel">
								<c:if  test="${mfCusCustomer.contactsTel!=null&&mfCusCustomer.contactsTel!=''}">
										${mfCusCustomer.contactsTel}
								</c:if>
								<c:if  test="${mfCusCustomer.contactsTel==null||mfCusCustomer.contactsTel==''}">
										<span class="unregistered">未登记</span>
								</c:if>
							<span class="vertical-line"></span>
							<span><i class="i i-idcard2 " ></i>
								<span id="idNum">
									<c:if  test="${mfCusCustomer.idNum!=null&&mfCusCustomer.idNum!=''}">
										${mfCusCustomer.idNum}
									</c:if>
									<c:if  test="${mfCusCustomer.idNum==null||mfCusCustomer.idNum==''}">
										<span class="unregistered">未登记</span>
									</c:if>
								</span>
							</span>
					</p>
					<p>
						<i class="i i-location "></i>
						<span id = "commAddress">
								<c:if  test="${mfCusCustomer.commAddress!=null&&mfCusCustomer.commAddress!=''}">
									${mfCusCustomer.commAddress}
								</c:if>
								<c:if  test="${mfCusCustomer.commAddress==null||mfCusCustomer.commAddress==''}">
									<span class="unregistered">未登记</span>
								</c:if>
							</span>
						<span class="vertical-line"></span>
						<i class="i i-youjian1 "></i>
						<span id = "postalCode">
								<c:if  test="${mfCusCustomer.postalCode!=null&&mfCusCustomer.postalCode!=''}">
									${mfCusCustomer.postalCode}
								</c:if>
								<c:if  test="${mfCusCustomer.postalCode==null||mfCusCustomer.postalCode==''}">
									<span class="unregistered">未登记</span>
								</c:if>
							</span>
					</p>
				</div>
			</div>
			<c:if test="${appId != null and appId != '' }">
				<div class="col-md-6" style="padding:0px 80px;">
					<div class="row clearfix">
						<div class="col-xs-10 col-md-10 column">
							<button class="btn btn-link content-title">${mfBusApply.appName}</button>
						</div>
					</div>
					<div class="row clearfix margin_top_5">
						<span class="content-span"><i class="i i-rmb"></i>${mfBusApply.fincAmt}</span><span class="unit-span">万</span>
						<span class="content-span"><i class="i i-richengshezhi"></i>${mfBusApply.term}</span><span class="unit-span">${dataMap.termUnit}</span>
						<span class="content-span"><i class="i i-tongji1"></i>${mfBusApply.fincRateStr}</span><span class="unit-span">${dataMap.rateUnit}</span>
					</div>
				</div>
			</c:if>
		</div>
		<div class="row clearfix">
			<div class="search-div">
				<div class="col-xs-9 column">
					<div class="dropdown">
						<button type="button" class="btn btn-primary dropdown-toggle" id="dropdownMenu" data-toggle="dropdown">三方数据查询
							<span class="caret"></span>
						</button>
						<ul id="serviceType" class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu" style="max-height:360px;">
						</ul>
					</div>
				</div>
				<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=名称"/>
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12 column">
			<div id="content" class="table_content"  style="height: auto;padding:0px;">
			</div>
		</div>
	</div>
</div>
<div class="formRowCenter">
	<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
</div>
</div>
</body>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_RiskManagement.js"></script>
<script type="text/javascript">
    var cusNo = '${cusNo}'
    var appId = '${appId}';
    var ajaxData = '${ajaxData}';
    var baseType = '${baseType}';
    var typeList = JSON.parse(ajaxData);
    $(function() {
        MfBusApply_RiskManagement.init();
    });
</script>
</html>
