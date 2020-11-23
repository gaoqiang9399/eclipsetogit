<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/view-detail.css?v=v0322" />
	<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
	<style type="text/css">
.score_lable {
    color: #707a8b;
    font-size: 14px;
}

.count_score {
    color: #40b741;
    font-size: 18px;
    margin-left: 5px;
    margin-right: 5px;
}
.bar_div {
  	 	margin-bottom: 10px;
}
.bar_outter {
   background-color: #d9dee0;
   border-radius: 2px;
   display: block;
   height: 18px;
}
.bar_inner {
    background-color: #3c9adc;
    border-radius: 2px 0 0 2px;
    display: block;
    height: 18px;
    width: 0%;
}
.div_line {
    margin: 4px 8px;
}
.show_2 .radio_span {
    background-color: #5fdac7;
}
.radio_span {
    border-radius: 15px;
    display: block;
    float: left;
    height: 15px;
    width: 15px;
}
.radio_span_inner {
    background-color: rgb(235, 235, 235);
    border-radius: 9px;
    display: block;
    height: 9px;
    left: 3px;
    position: relative;
    top: 3px;
    width: 9px;
}
.radio_lable {
    display: block;
    font-size: 12px;
    left: 5px;
    position: relative;
    top: -1px;
    height: 20px;
}
.show_2 .radio_filed {
    color: #5fdac7;
}
.radio_filed {
    display: block;
    float: right;
    font-size: 14px;
    font-weight: 700;
    position: relative;
    top: -26px;
    height: 20px;
}
.show_3 .radio_span {
    background-color: #fa8080;
}
.show_3 .radio_filed {
    color: #fa8080;
}
.show_4 .radio_span {
    background-color: #707a8b;
}
.show_4 .radio_filed {
    color: #707a8b;
}
.cus-type-font {
    position: relative;
    font-size: 70px;
    color: #32b5cb;
}
.type-name-div {
    font-size: 12px;
    color: #32b5cb;
    text-align: center;
    position: absolute;
    right: 5px;
    top: 28px;
    width: 78px;
    transform: rotate(-15deg);
    -ms-transform: rotate(-15deg);
    -moz-transform: rotate(-15deg);
    -webkit-transform: rotate(-15deg);
    -o-transform: rotate(-15deg);
}
	</style>
	<script type="text/javascript">
	var headImg = '${mfCusCustomer.headImg}';
	var ifUploadHead = '${mfCusCustomer.ifUploadHead}';
	var webPath = '${webPath}';
	$(function(){
		$("body").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});
		var totalScore = $(".count_score").text();
		$(".bar_inner").css("width",totalScore+"%");
		/**处理头像信息**/
		var data = headImg;
		if (ifUploadHead != "1") {
			data = "themes/factor/images/" + headImg;
		}
		data = encodeURIComponent(encodeURIComponent(data));
		document.getElementById('headImgShow').src = webPath+ "/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
	
	});
	</script>
</head>
	
<body class="bg-white overflowHidden">
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-10 col-md-offset-1 column ">
				<div class="row clearfix padding_top_20 padding_left_40">
					<div class="col-md-2 column padding_top_20" >
						<img id="headImgShow" name="headImgShow" style="width:100px;height:100px;" class="img-circle" />
					</div>
					<div class="col-md-4 column">
						<p class="font_size_18 margin_bottom_20">${mfCusCustomer.cusName}</p>
						<p>联系人姓名：${mfCusCustomer.contactsName}</p>
						<p>联系电话：${mfCusCustomer.contactsTel}</p>
						<p>通讯地址：${mfCusCustomer.commAddress}</p>
					</div>
					<!--核心企业-->
					<div class="col-md-6 column padding_left_30 padding_top_30">
						<div class="row clearfix">
							<div class="col-md-6 column">
								<div class="block-sm-div backgroundcolor1">
									<p>${dataMap.evalLevel}</p>
									<p>评级级别</p>
								</div>
							</div>
							<div class="col-md-6 column">
								<div class="block-sm-div backgroundcolor2">
									<p>${dataMap.creditSum}</p>
									<p>授信额度(万元)</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="list-table margin_0">
							<div class="title">
								<span>
								<i class="i i-xing blockDian"></i>
								成交业务</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#bushis">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
						<div class="padding_top_15 padding_bottom_15 padding_left_40 collapse in" id="bushis">
							<div class="col-md-3">
								<p>合作企业总数</p>
								<p><span class="color_theme font_size_20">${dataMap.totalCnt}</span> 家</p>
							</div>
							<div class="col-md-3">
								<p>成交总额</p>
								<p><span class="color_theme font_size_20">${dataMap.totalAmt}</span> 万元</p>
							</div>
						</div>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="list-table margin_0">
							<div class="title">
								<span>
								<i class="i i-xing blockDian"></i>
								评级结果总览</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#gradehis">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
						</div>
						<c:if test="${appEval!=null}">
							<div class="padding_top_15 padding_left_40 collapse in" id="gradehis">
								<div class="col-md-6 column" style="padding-right:20px;">
									<div><span>综合评价</span><span style="font-size: 41px;color: #47ADC3;margin-left: 25px;">${dataMap.evalLevel}</span></div>
									<div>
										<p>${dataMap.evalAssess}</p>
									</div>
								</div>
								<div class="col-md-6 column" style="padding-left:20px;">
									<div style="text-align: right;">
										<span class="score_lable">得分</span>
										<span class="count_score">${dataMap.appEval.totalScore}</span>
									</div>
									<div class="bar_div">
										<span class="bar_outter">
											<span class="bar_inner" style="width: 38%;"></span>
										</span>
									</div>
									<div class="div_line  show_2">
										<span class="radio_span"><span class="radio_span_inner"></span></span>
										<span class="radio_lable">定性得分</span>
										<span class="radio_filed">+${dataMap.appEval.dxScore}</span>
									</div>
									<div class="div_line  show_3">
										<span class="radio_span"><span class="radio_span_inner"></span></span>
										<span class="radio_lable">调整得分</span>
										<span class="radio_filed">+${dataMap.appEval.adjScore}</span>
									</div>
									<div class="div_line  show_4">
										<span class="radio_span"><span class="radio_span_inner"></span></span>
										<span class="radio_lable">客户经理调整</span>
										<span class="radio_filed">${dataMap.appEval.manAdjScore}</span>
									</div>
								</div>
						</div>
						</c:if>
						<c:if test="${appEval==null}">
							<div class="padding_top_15 padding_left_40 collapse in" id="gradehis">
								<p>暂无评级信息</p>
							</div>
						</c:if>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="list-table margin_0">
							<div class="title">
								<span>
								<i class="i i-xing blockDian"></i>
								授信结果总览</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#credithis">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
						</div>
						<div class="padding_top_15 padding_left_40 collapse in" id="credithis">
							<p>1、企业成立时间满3年；</p>
							<p>2、近半年开票额：150万左右；</p>
							<p>3、开票(增值税发票)，两年的年报表，最近一个月的月报表，近6个月的发票情况；</p>
							<p>4、申请人近三个月的个人贷款不能逾期，企业负债率不能超过60~70%</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>