<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="${webPath}/themes/factor/css/view-detail.css?v=v0322" />
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
			<div class="col-md-8 col-md-offset-2 column ">
				<div class="row clearfix padding_top_20 padding_left_40">
					<div class="col-xs-2 column padding_top_20" >
						<img id="headImgShow" name="headImgShow" style="width:100px;height:100px;" class="img-circle"/>
					</div>
					<div class="col-md-4 column">
						<p class="font_size_18 margin_bottom_20">${mfCusCustomer.cusName}</p>
						<p>联系人姓名：${mfCusCustomer.contactsName}</p>
						<p>联系电话：${mfCusCustomer.contactsTel}</p>
						<p>通讯地址：${mfCusCustomer.commAddress}</p>
					</div>
					<!--资金机构 -->
					<div class="col-md-6 column padding_top_30 padding_left_30">
						<div class="row clearfix">
							<div class="col-md-6 column">
								<div class="block-sm-div backgroundcolor1">
<%-- 									<p>${mfCusCustomer.cusLevelName}级</p> --%>
									<p>500,000,000.00万</p>
									<p>放款总额(元)</p>
								</div>
							</div>
							<div class="col-md-6 column">
								<div class="block-sm-div backgroundcolor2">
									<p>300,000,000.00万</p>
									<p>待收回总额(元)</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="margin_0 list-table ">
							<div class="title">
								<span>
								<i class="i i-xing blockDian"></i>
								成交业务</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#applyHis">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
						</div>
						<div class="padding_top_15 padding_bottom_15 padding_left_40 collapse in" id="applyHis">
							<div class="col-md-3">
								<p>合作企业总数</p>
								<p><span class="color_theme font_size_20">${dataMap["totalCnt"]}</span> 家</p>
							</div>
							<div class="col-md-3">
								<p>成交总额</p>
								<p><span class="color_theme font_size_20">${dataMap["totalAmt"]}</span> 万元</p>
							</div>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="block-title"><span>放款明细</span></div>
						<div class="table_content padding_top_15 padding_bottom_15 padding_left_40">
							<table width="100%" border="0" align="center" cellspacing="1" class="ls_list">
								<thead>
									<tr>
										<th>客户名称</th>
										<th>放款金额(万元)</th>
										<th>待回收金额(万元)</th>
										<th>逾期金额(万元)</th>
										<th>放款时间</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td align="left">中国移动</td>
										<td align="right">33.0</td>
										<td align="right">10.0</td>
										<td align="right">0.0</td>
										<td align="center">2016-08-18</td>
									</tr>
									<tr>
										<td align="left">张仲景大药房</td>
										<td align="right">12.0</td>
										<td align="right">6.0</td>
										<td align="right">6.0</td>
										<td align="center">2015-04-28</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>