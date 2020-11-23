<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript"
	src="${webPath}/layout/view/js/openDiv.js"></script>
<script type="text/javascript"
	src="${webPath}/layout/view/js/fixedDivFun.js"></script>
<link rel="stylesheet"
	href="${webPath}/component/sys/css/SysUser_UserInfo.css?v=${cssJsVersion}" />
</head>
<script type="text/javascript">
	var headImg = '${sysUser.headImg}';
	var ifUploadHead = '${sysUser.ifUploadHead}';
	var opNo = '${sysUser.opNo}';
	$(function() {
		$("body").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		});
	});
</script>
<body class="overflowHidden">
	<div class="row clearfix homepage" id="home-page">
		<div class="col-md-4 column"></div>
		<div class="col-md-8 column">
			<div style="margin-top: 6px;">
				<span>为了方便办公，可以设置当前页面为首页！</span>
				<button class="homebtn">设为首页</button>
				<i class="i i-x3 " id="setHomePageClose"> </i>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-8 column block-left">
				<div class="row clearfix base-info bg-white">
					<div class="col-md-3 column head-img text-center">
						<img id="trenchHeadImgShow" name="headImgShow"
							class="img-circle"
							src="${webPath}/themes/factor/images/op_user.jpg" />
						<div class="margin_top_20 color_theme">
							<!-- <i class="i i-xiaoxi pointer" onclick="sendMessage();"></i>  -->
							<i class="i i-xiaoxi"></i> 
							<i class="i i-youjian1"></i> 
							<!-- <i class="i i-richeng pointer" onclick="FullCalendar();"></i> -->
							<i class="i i-richeng"></i>
							<i class="i i-weixin"></i>
						</div>
					</div>
					<div class="col-md-9 column base-content margin_top_10">
						<div class="row clearfix ">
							<div class="col-md-12 column">
								<div class="font_size_16">${mfBusTrench.trenchName}</div>
							</div>
						</div>
						<div class="row clearfix margin_top_10">
							<div class="col-md-12 column">
								<span><i class=" i i-bumen"></i>${mfBusTrench.trenchButtName}</span>
								<span class="margin_left_20"><i class=" i i-dianhua"></i>${mfBusTrench.trenchButtPhone}</span>
								<span class="margin_left_20"><i class=" i i-location"></i>${mfBusTrench.trenchAddress}</span>
							</div>
						</div>
						<div class="row clearfix head-content margin_top_20">
							<div class="col-md-12 column">
								<div class="col-md-4 column">
									<p class="cont-title">授信可用额度</p>
									<p class="margin_top_15">
										<span class="content-span">${dataMap.creditBal}</span><span
											class="unit-span margin_right_25">万元</span>
									</p>
								</div>
								<div class="col-md-4 column">
									<p class="cont-title">授信总额</p>
									<p class="margin_top_15">
										<span class="content-span">${dataMap.creditAmt}</span><span
											class="unit-span margin_right_25">万元</span>
									</p>
								</div>
								<div class="col-md-4 column">
									<p class="cont-title">剩余比例</p>
									<p class="margin_top_15">
										<span class="content-span">${dataMap.surplusRate}</span><span
											class="unit-span margin_right_25">%</span>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4 column block-right ">
				<div class="bg-white right-info">
					<div class="row clearfix">
					<div class="col-md-12 column margin_top_15 margin_left_15">
							<div class="col-md-4 column">
								<p class="cont-title">融资余额</p>
								<p class="margin_top_15">
									<span class="content-span">${dataMap.unRepayAmt}</span><span
										class="unit-span margin_right_25">万元</span>
								</p>
							</div>
							<div class="col-md-4 column">
								<p class="cont-title">融资总额</p>
								<p class="margin_top_15">
									<span class="content-span">${dataMap.loanAmt}</span><span
										class="unit-span margin_right_25">万元</span>
								</p>
							</div>
							<div class="col-md-4 column">
								<div class="title-div leave-div">
									<button id="buss-btn" class="btn block-view" onclick="TrenchCenterInfo.bussBtn()">
										<span>业务管理</span>
									</button>
								</div>
							</div>
						</div>
						<div class="col-md-12 column margin_top_15 margin_left_15">
							<div class="col-md-4 column">
								<p class="cont-title">融资客户数</p>
								<p class="margin_top_15">
									<span class="content-span">${dataMap.loanCusSum}</span><span
										class="unit-span">人</span>
								</p>
							</div>
							<div class="col-md-4 column">
								<p class="cont-title">客户总数</p>
								<p class="margin_top_15">
									<span class="content-span">${dataMap.cusSum}</span><span
										class="unit-span">人</span>
								</p>
							</div>
							<div class="col-md-4 column">
								<div class="title-div leave-div">
									<button id="cus-btn" class="btn block-view" onclick="TrenchCenterInfo.cusBtn()">
										<span>客户管理</span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column block-whole">
				<div class="whole-div bg-white">
					<div class="title-div"
						style="border-bottom: 1px solid #e3e3e3; height: 45px; margin: 0px 20px;">
						<label>子渠道</label>
					</div>
					<div class="dynamic-block" style="margin-left: 18px;width: 97%;">
						<div class="list-table">
							<div class="list-table">
								<div class="content" id= "childTrenchTable">
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- <div class="col-md-12 column block-whole">
				<div class="whole-div bg-white">
					<div class="title-div"
						style="border-bottom: 1px solid #e3e3e3; height: 45px; margin: 0px 20px;">
						<label>在办业务</label>
					</div>
					<div class="dynamic-block" style="margin-left: 18px;width: 97%;">
						<div class="list-table">
							<div class="list-table">
								<div class="content" id= "bussTable">
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</div> -->
		</div>
	</div>
</body>
<%-- <script type="text/javascript"
	src="${webPath}/component/sys/js/SysUser_UserInfo.js"></script> --%>
<script type="text/javascript"
	src="${webPath}/UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js"></script>
	<script type="text/javascript" src="${webPath}/component/cus/trenchsubsidiary/js/TrenchCenterInfo.js"></script>
<script type="text/javascript">
	var isMousemove = false;//全局变量，用来存贮鼠标移动状态
	var opNo = '${sysUser.opNo}';
	var homePage = '${sysUser.homePage}';
	$(function() {
		//userInfo.init();
		TrenchCenterInfo.init();
		$(".content-div").sortable({
			cursor: "move" ,
			items: ".move-div",
			start : function(event, ui) {
			},
			update : function(event, ui) {
			}
		});
		$( ".content-div" ).sortable( "option", "cursor", "move" );
		$( ".content-div" ).sortable( "option", "items", ".move-div" );
		// 鼠标拖动开始时触发
		$(".content-div").on("sortstart", function(event, ui) {
			isMousemove = true;
		});
		// 拖动事件结束时触发
		$(".content-div").on("sortupdate", function(event, ui) {
			var msgTypes = [];
			$.each($(".move-div"),function(index , item){
				msgTypes[index] = $(item).attr("msgType");
			});
			$.ajax({
				url : "MfDeskMsgItemActionAjax_updateSortAjax.action",
				data : {
					opNo : opNo,
					msgTypes : msgTypes
				},
				type : 'post',
				dataType : 'json',
				traditional: true,
				success : function(data) {
					if(data.flag=="success"){
					}else{
						alert(top.getMessage("FAILED_OPERATION","排序"),0);
					}
				},
				error : function() {
					alert(top.getMessage("FAILED_OPERATION","排序"),0);
				}
			});
		});
	});
	
	function getDetailPage(obj,url){
		top.LoadingAnimate.start();		
		window.location.href=url;
		event.stopPropagation();
	}
</script>
</html>