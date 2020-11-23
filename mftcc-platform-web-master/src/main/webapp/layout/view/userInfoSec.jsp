<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>个人中心</title>
</head>
<body>
	<div class="container">
		<div class="row clearfix">
			<div class="col-md-7 column" style="height: 202px;">
				<!--头像 -->
				<div class="col-xs-3 column text-center head-img"
					style="height: 100px; margin-top: 28px;">
					<div class="btn btn-link">
						<img id="headImgShow" name="headImgShow" class="img-circle"
							style="height: 100px;"
							src="${webPath}/themes/factor/images/op_user.jpg" />
					</div>
				</div>
				<div style="margin-top: 40px; font-size: 15px; font-weight: bold;">王力然</div>
				<div>
					<p>
					<div style="width: 176px; float: left;">
						<i class="i i-youjian"></i>出生日期：<span id="contactsName">1992-03-09</span>
					</div>
					<div style="float: left; margin-right: 32px;">
						<i class="i i-dianhua"></i>邮箱：<span id="contactsTel">1396839103@qq.com</span>
					</div>
					<div>
						<i class="i i-idcard2"></i>职务：<span id="idNum">销售经理</span>
					</div>
					</br>
					<div style="float: left; width: 176px;">
						<i class="i i-idcard2"></i>部门：<span id="idNum">销售部</span>
					</div>
					<div>
						<i class="i i-idcard2"></i>手机：<span id="idNum">18000456568</span>
					</div>
					</p>
				</div>
			</div>
			<div class="col-md-5 column"></div>
		</div>
		<div style="height: 12px; background: #E7ECEF;"></div>
		<div class="row clearfix" style="height: 24%;">
			<div class="col-md-12 column">
				<div style="height: 45px; font-size: 23px; border-bottom: 2px solid #E5E5E5; margin-bottom: 26px; margin-top: 13px; margin-left: 22px; margin-right: 33px;">&nbsp&nbsp待办业务
				</div>
				<div class="col-md-3 column"
					style="height: 48px; width: 12%; margin-left: 23px;">
					<button type="button" class="btn btn-default btn-primary"
						style="height: 48px; width: 83%; margin-left: 23px;">
						审批任务</button>
				</div>
				<div class="col-md-3 column"
					style="height: 48px; width: 12%; margin-left: 23px;">
					<button type="button" class="btn btn-default btn-primary"
						style="height: 48px; width: 83%; margin-left: 23px;">
						催收任务</button>
				</div>
				<div class="col-md-3 column"
					style="height: 48px; width: 12%; margin-left: 23px;">
					<button type="button" class="btn btn-default btn-primary"
						style="height: 48px; width: 83%; margin-left: 23px;">
						检查任务</button>
				</div>
				<div class="col-md-3 column"
					style="height: 48px; width: 12%; margin-left: 23px;">
					<button type="button" class="btn btn-default btn-primary"
						style="height: 48px; width: 83%; margin-left: 23px;">
						通知公告</button>
				</div>
			</div>
		</div>
		<div style="height: 12px; background: #E7ECEF;"></div>
		<div class="row clearfix" style="height: 22%;">
			<div class="col-md-12 column">
				<div style="height:45px; font-size: 23px; border-bottom: 2px solid #E5E5E5; margin-bottom: 26px; margin-top: 13px; margin-left: 22px; margin-right: 33px;">低值易耗品</div>
				<div class="col-md-4 column"
					style="height: 48px; width: 12%; margin-left: 23px;">
					<button type="button" class="btn btn-default btn-primary"
						style="height: 48px; width: 56%; margin-left: 23px;">
						新增申领</button>
				</div>
				<div style="float: left; margin-left: 35%; margin-top: 26px;">
					你以申领了 <span> </span>, <span> </span>等 <span> </span>23件物品
				</div>
				<div class="btn" style="float: left; margin-top: 22px; margin-left: 22px; height: 28px; line-height: 16px;background: #D98F29;color: white;">
					详情</div>
			</div>
		</div>
		<div style="height: 12px; background: #E7ECEF;"></div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div style="height: 45px; font-size: 23px; border-bottom: 2px solid #E5E5E5; margin-bottom: 26px; margin-top: 3px; margin-left: 22px; margin-right: 33px;">借款报销</div>
				<div style="float: left; margin-left: 50px;">
					余额 <span style="color:#32B4C8;font-weight:500;font-size: 20px;">50000</span> 元
				</div>
				<div style="float: left;margin-left: 7%;margin-top: 9px;">
					借款总额<span>9565323</span> 元
				</div>
				<div  style="float: left; margin-left: 10%; margin-top: 10px;">
					报销总额 <span>4848888</span> 元
				</div>

				<div class="col-md-4 column" style="float: right;">
					<button type="button" class="btn btn-default btn-primary"
						style="height: 48px;">借款</button>
				</div>
				<div class="col-md-4 column" style="float: right;">
					<button type="button" class="btn btn-default btn-primary"
						style="height: 48px;">报销</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>