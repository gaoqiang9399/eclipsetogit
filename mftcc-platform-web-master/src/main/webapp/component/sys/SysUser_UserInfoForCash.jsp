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
	href="${webPath}/component/sys/css/SysUser_UserInfoForCash.css?v=${cssJsVersion}" />
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
			<div class="col-md-12 column block-whole">
				<div class="row clearfix base-info bg-white">
					<div class="col-md-12 column base-content margin_top_10">

						<div class="col-md-4 column">
							<div class="col-md-4 column head-img text-center">
								<img id="headImgShow" name="headImgShow" class="img-circle pointer" src="${webPath}/themes/factor/images/op_user.jpg" />
								<div class="margin_top_15 color_theme">
									<i class="i i-xiaoxi pointer" onclick="sendMessage();"></i> <i
										class="i i-youjian1"></i> <i class="i i-richeng pointer"
										onclick="FullCalendar();"></i> <i class="i i-weixin"></i>
								</div>
							</div>
							<div class="col-md-8 column base-content ">
								<div class="row clearfix ">
									<div class="col-md-12 column margin_left_20">
										<div class="font_size_16">${sysUser.opName}</div>
									</div>
								</div>
								<div class="row clearfix margin_top_5 ">
									<div class="col-md-12 column margin_left_20">
										<span>
											<i class="i i-idcard2"></i> 
											<c:if test="${statu.sysUser.job!=''}" >${sysUser.job}</c:if> 
											<c:if test="${statu.sysUser.job==''}" >暂无</c:if>
										</span>
									</div>
									<div class="col-md-12 column margin_top_5 margin_left_20">
										<span >
											<i class=" i i-bumen"></i>${sysUser.brNo}
										</span>
									</div>
									<div class="col-md-12 column margin_top_5 margin_left_20">
										<span class="margin_top_15">
											<i class=" i i-dianhua"></i>${sysUser.mobile}
										</span>
									</div>
								</div>
							</div>	
						</div>
						
						
						<div class="col-md-8 column">
							<div class="row clearfix ">
								<div class="col-md-12 column">
									<div class="font_size_16"><span class="user-ge"></span>今日数据</div>
								</div>
							</div>
							<div class="row clearfix base-content margin_top_10">
								<div class="user-card column">
									<div class="user-card-item column">
										<p class="cont-title">当天注册数</p>
										<p class="content-p">
										<c:if test="${dataMap.isAuth=='0'}">
											<span class="content-span">0</span>人
										</c:if> 
										<c:if test="${dataMap.isAuth=='1'}">
											<a href= "" id="regCount"><span class="content-span">${dataMap.regCount}</span></a>人
										</c:if>
										</p>
									</div>
									<div class="user-card-item column">
										<p class="cont-title">登录数</p>
										<p class="content-p">
											<c:if test="${dataMap.isAuth=='0'}">
												<span class="content-span">0</span>人
											</c:if> 
											<c:if test="${dataMap.isAuth=='1'}">
												<a href= "" id="onlineCount"><span class="content-span">${dataMap.onlineCount}</span></a>人
											</c:if>
										</p>
									</div>
									<div class="user-card-item column">
										<p class="cont-title">借款申请数</p>
										<p class="content-p">
											<c:if test="${dataMap.isAuth=='0'}">
												<span class="content-span">0</span>次
											</c:if> 
											<c:if test="${dataMap.isAuth=='1'}">
												<a href= "" id="applyCount"><span class="content-span">${dataMap.applyCount}</span></a>次
											</c:if>
										</p>
									</div>
									<div class="user-card-item column">
										<p class="cont-title">借款否决数</p>
										<p class="content-p">
											<c:if test="${dataMap.isAuth=='0'}">
												<span class="content-span">0</span>次
											</c:if> 
											<c:if test="${dataMap.isAuth=='1'}">
												<a href= "" id="vetoCount"><span class="content-span">${dataMap.vetoCount}</span></a>次
											</c:if>
										</p>
									</div>
									<div class="user-card-item column">
										<p class="cont-title">通过次数</p>
										<p class="content-p">
											<c:if test="${dataMap.isAuth=='0'}">
												<span class="content-span">0</span>次
											</c:if> 
											<c:if test="${dataMap.isAuth=='1'}">
												<span class="content-span">${dataMap.pactCount}</span>次
											</c:if>
										</p>
									</div>
									<div class="user-card-item column">
										<p class="cont-title">通过率</p>
										<p class="content-p">
											<c:if test="${dataMap.isAuth=='0'}">
												<span class="content-span">0</span>%
											</c:if> 
											<c:if test="${dataMap.isAuth=='1'}">
												<span class="content-span">${dataMap.passRate}</span>%
											</c:if>
										</p>
									</div>
									<div class="user-card-item2 column">
										<p class="cont-title">放款量</p>
										<p class="content-p">
											<c:if test="${dataMap.isAuth=='0'}">
												<span class="content-span">0.00</span>元
											</c:if> 
											<c:if test="${dataMap.isAuth=='1'}">
												<a href= "" id="loanCount"><span class="content-span">${dataMap.putoutAmt}</span>元<a>
											</c:if>
										</p>
									</div>
									<div class="user-card-item2 column">
										<p class="cont-title">还款量</p>
										<p class="content-p">
											<c:if test="${dataMap.isAuth=='0'}">
												<span class="content-span">0.00</span>元
											</c:if> 
											<c:if test="${dataMap.isAuth=='1'}">
												<a href= "" id="repayAmt"><span class="content-span"><span class="content-span">${dataMap.repayAmt}</span></span></a>元
											</c:if>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row clearfix">
			<div class="col-md-5 column block-whole">
				<div class="row clearfix base-info bg-white">
					<div class="col-md-12 column base-content margin_top_10">
						<div class="row clearfix ">
							<div class="col-md-12 column">
								<div class="font_size_16"><span class="user-ge"></span>累计数据</div>
							</div>
						</div>
						<div class="row clearfix base-content margin_top_10 margin_bottom_30">
							<div class="col-md-12 column">
								<div class="col-md-6 column">
									<p class="cont-title">历史放款总量
										<c:if test="${dataMap.isAuth=='0'}">
											<span class="content-span">&nbsp;0</span>笔
										</c:if> 
										<c:if test="${dataMap.isAuth=='1'}">
											<span class="content-span">&nbsp;${dataMap.putoutSumCount}</span>笔
										</c:if>
									</p>
								</div>
								<div class="col-md-6 column">
									<p class="cont-title">历史还款总量
										<c:if test="${dataMap.isAuth=='0'}">
											<span class="content-span">&nbsp;0</span>笔
										</c:if> 
										<c:if test="${dataMap.isAuth=='1'}">
											<span class="content-span">&nbsp;${dataMap.repaySumCount}</span>笔
										</c:if>
									</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-7 column block-whole">
				<div class="row clearfix base-info bg-white">
					<div class="col-md-12 column base-content margin_top_10">
						<div class="row clearfix ">
							<div class="col-md-12 column">
								<div class="font_size_16"><span class="user-ge"></span>实时数据</div>
							</div>
						</div>
						<div class="row clearfix base-content margin_top_10 margin_bottom_30">
							<div class="col-md-12 column">
								<div class="col-md-6 column">
									<p class="cont-title">待还款总余额
										<c:if test="${dataMap.isAuth=='0'}">
											<span class="content-span">&nbsp;0.00</span>元
										</c:if> 
										<c:if test="${dataMap.isAuth=='1'}">
											<a href= "" id="repaySumAmt"><span class="content-span">&nbsp;${dataMap.repaySumAmt}</span></a>元
										</c:if>
										
									</p>
								</div>
								<div class="col-md-6 column">
									<p class="cont-title">逾期未还款总余额
										<c:if test="${dataMap.isAuth=='0'}">
											<span class="content-span">&nbsp;0.00</span>元
										</c:if> 
										<c:if test="${dataMap.isAuth=='1'}">
											<a href= "" id="overdueSumAmt"><span class="content-span">&nbsp;${dataMap.overdueSumAmt}</span></a>元
										</c:if>
									</p>
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
						<label>消息中心</label>
					</div>
					<div class="content-div">
						<c:if test="${mfDeskMsgItemList != null}">
							<c:forEach items="${mfDeskMsgItemList}"
								var="mfDeskMsgItem">
								<dhcc:pmsTag pmsId="${mfDeskMsgItem.msgType}">
								<div class="btn btn-app move-div" 
									url="${mfDeskMsgItem.jumpUrl}"
									isShowDialog="${mfDeskMsgItem.isShowDialog}"
									msgTitle="${mfDeskMsgItem.msgTitle}"
									menuNo="${mfDeskMsgItem.menuNo}"
									msgType="${mfDeskMsgItem.msgType}"
									style="<c:if test="${mfDeskMsgItem.useFlag == 0}">display:none</c:if>;"
									id="${mfDeskMsgItem.msgType}">
								   <c:if test="${mfDeskMsgItem.msgCount > 0}">  
								   		<c:choose>
								   			<c:when test="${mfDeskMsgItem.msgCount>99}">
								   				<span class="badge">99+</span>
								   			</c:when>
								   			<c:otherwise>
								   				<span class="badge" >${mfDeskMsgItem.msgCount}</span>
								   			</c:otherwise>
								   		</c:choose>
								   </c:if> 
									<div>
										<i class="i ${mfDeskMsgItem.msgImg} msg-icon"></i>
									</div>
									<div>${mfDeskMsgItem.msgTitle}</div>
								</div>
								</dhcc:pmsTag>
							</c:forEach>
						</c:if>
						<div class="btn btn-app" id="addItem" >
							<div><i class="i i-jia1 color_theme msg-icon"></i></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${webPath}/component/sys/js/SysUser_UserInfoForCash.js"></script>
<script type="text/javascript"
	src="${webPath}/UIplug/jqueryUI/js/jquery-ui-1.10.1.custom.min.js"></script>
<script type="text/javascript">
	var isMousemove = false;//全局变量，用来存贮鼠标移动状态
	var opNo = '${sysUser.opNo}';
	var homePage = '${sysUser.homePage}';
	$(function() {
		userInfo.init();
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
				url : webPath+"/mfDeskMsgItem/updateSortAjax",
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
</script>
</html>