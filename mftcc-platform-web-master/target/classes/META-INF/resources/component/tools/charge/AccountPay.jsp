<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户充值</title>
<link rel="stylesheet" href="${webPath}/component/tools/charge/cloudManager.css" type="text/css"></link>
<script type="text/javascript">
	$(function() {
		LoadingAnimate.start();
			myCustomScrollbar({	//左边账户已开通服务
				obj : "#content_left",//页面内容绑定的id
				url :webPath+"/accountPay/ServiceAjaxLeft",//列表数据查询的url
				tableId : "tableCM001",//列表数据查询的table编号
				tableType : "thirdTableTag",//table所需解析标签的种类
				pageSize : 30,//加载默认行数(不填为系统默认行数)
				ownHeight : false,
				parentHeight:true,
				callback:function(){
			}
			});
			myCustomScrollbar({	//右边账户使用一览
				obj : "#content_right",//页面内容绑定的id
				url :webPath+"/accountPay/UseViewAjaxRight",//列表数据查询的url
				tableId : "tableCM002",//列表数据查询的table编号
				tableType : "thirdTableTag",//table所需解析标签的种类
				pageSize : 30,//加载默认行数(不填为系统默认行数)
				ownHeight : false,
				parentHeight:true,
				callback:function(){
			}
			});
		LoadingAnimate.stop();


	});
	function showUseDetail(itemNo,itemName,itemType){
		var url="";
		if(itemType=="DX"){
			url=webPath+'/component/tools/charge/DxUsedList.jsp?itemNo='+itemNo;
		}else{
			url=webPath+'/component/tools/charge/SfUsedView.jsp?itemNo='+itemNo;
		}
		top.createShowDialog(url,itemName+" 使用详情",'80','80',function(){
		});
	}
</script>
<style type="text/css">
	.addline{
	text-decoration:underline;
	}
	.top_css_divLeft{
		height: 30%!important;
		float: left;
		background: #F8F9FC;
		width: 49%;
		height: 41%;
	}
	.top_css_divRight{
		height: 30%!important;
		float: right;
		background: #F8F9FC;
		width: 50%;
		height: 41%;
	}
	.balance_css{
		position: relative;
		color: #4BB5FE;
		font-size: 36px;
		font-weight: 700;
	}
	.head-img img {
	    height: 132px;
	    width: 132px;
	}
	.head-img{
		margin-top: 4%;
	}
	.money_view{
		margin-top: 6%;
	}
	.money_normal{
		color:#32b5cb;
	}
	.div_center{
		margin-top: 9%;
	}
	.PayBut{
		padding: 0px 10px 0px 10px;
		left: 290px;
		top: 6px;
	}
	.top_right{
		float: right;
		margin-top: 23px;
	}
	.bottom_css_divLeft{
		height: 43%;
		float: left;
		background: #F8F9FC;
		width: 49%;
		margin-top: 1%;
	}
	.bottom_css_divRight{
		height: 43%;
		float: right;
		background: #F8F9FC;
		width: 50%;
		margin-top: 1%;
	}
	.serviceTitle{
		position: relative;
		top: 38px;
		left: 30px;
		width: 94%;
	}
	.left_show{
		float: right;
	}
	.kt_info{
		padding:0px 20px 0px 20px;
	}
	.kt_info h3{
		font-size: 16px;
		height: 32px;
		line-height: 32px;
		margin: 0px;
	}
	.kt_info div{
		background-color: #F8F9FC;
	}
	.kt_info p{
		font-size: 14px;
		line-height: 28px;
		padding: 0px;
		margin: 0px;
	}
	a:hover{
		color: white;
		text-decoration: none;
	}
</style>
</head>
<body>
	<div class="clearfix">
		<div class="top_css_divLeft">
			<div class="col-xs-4 column text-center head-img">
				<div class="btn btn-link">
					<img id="headImgShow" name="headImgShow" class="img-circle" src="${webPath}/UploadFile/viewUploadImageDetail?srcPath=themes/factor/images/user_1.jpg" />
				</div>
			</div>
			<div class="col-xs-8 column head-content div_center">
				<div class="margin_bottom_5">
					<span  class="head-title">
						公司信息
					</span>
				</div>
				<div>
					<p>
						<span><i class="i i-ren1"></i><span id = "contactsName">${manageDetil.contactName}</span></span>
						<span class="vertical-line">|</span>
						<span><i class="i i-dianhua"></i><span id = "contactsTel">${manageDetil.contactTel}</span></span>
						<span class="vertical-line">|</span>
						<span><i class="i i-idcard2" ></i><span id="eMail">${manageDetil.eMail}</span></span>
					</p>
					<p>
						<i class="i i-location"></i>
						<span id = "companyAds">
							${manageDetil.companyAds}
						</span>
					</p>
				</div>
			</div>
		</div>
		<div class="top_css_divRight">
			<div class="col-xs-10 column head-content top_right">
				<div class="margin_bottom_5 money_view">
					<span  class="head-title" onclick="updateCustomerInfo();">
						账户资产
					</span>
				</div>
				<div>
					<p>
						<span class="unit-span"> 账户余额</span><span class="content-span">${manageDetil.balance}</span><span class="unit-span">元</span> 
						<a class="PayBut cloud_button" href="${webPath}/accountPay/getSendPayPage">充值</a>
					</p>
					<p>
						<span class="unit-span"> 累计使用</span><span class="money_normal" id="useSum">${manageDetil.totalAmt}</span><span class="unit-span">元</span> 
						<span class="unit-span"> 累计充值</span><span class="money_normal"></i>${manageDetil.giveAmt}</span><span class="unit-span">元</span> 
						<span class="unit-span"> 累计赠送</span><span class="money_normal"></i>${manageDetil.buckAmt}</span><span class="unit-span">元</span> 
					</p>
				</div>
			</div>
		</div>
		<div class="bottom_css_divLeft">
			<div class="margin_bottom_5 serviceTitle">
					<span  class="head-title">
						已开通服务
					</span>
				<div class="left_show">
					<a class="PayBut cloud_button" href="${webPath}/accountPay/getServicePage" >开通</a>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column" style="margin-top: 50px;">
					<div id="content_left" class="table_content"  style="height: 200px;">
					</div>
				</div>
			</div>
		</div>
		<div class="bottom_css_divRight">
			<div class="margin_bottom_5 serviceTitle">
					<span  class="head-title">
						使用一览
					</span>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column" style="margin-top: 50px;">
					<div id="content_right" class="table_content"  style="height: 200px;">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="kt_info">
		<h3>开通须知</h3>
		<div>
			<p>1，增值服务可选择开通短信、身份证校验、征信报告等功能；</p>
			<p>2，用户可通过“充值”功能实现微信自动充值；</p>
			<p>3，短信功能不支持发送营销短信，如有需要请联系工作人员；</p>
			<p>4，身份验证、出行数据、电商数据、运营商数据属于公民个人隐私，请勿非法传播；</p>
			<p>5，如需帮助请拨打：400-6712-966</p>
		</div>
	</div>
	<div class="footer_loader" style="display:none;"></div>
</body>
<script type="text/javascript">
function addLine(dom){
	$(dom).addClass("addline");
}	
function removeLine(dom){
	$(dom).removeClass("addline");
}
</script>
</html>