<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="${webPath}/themes/factor/css/view-detail.css?v=v0322" />
<style type="text/css">
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
	right: 2px;
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
					<div class="col-md-2 column padding_top_20">
						<img id="headImgShow" name="headImgShow" style="width: 100px; height: 100px;" class="img-circle" />
					</div>
					<div class="col-md-6 column">
						<p class="font_size_18 margin_bottom_20">${mfCusCustomer.cusName}</p>
						<p>联系人姓名：${mfCusCustomer.contactsName}</p>
						<p>联系电话：${mfCusCustomer.contactsTel}</p>
						<p>通讯地址：${mfCusCustomer.commAddress}</p>
					</div>
					<!--仓储机构 -->
					<div class="col-md-4 column padding_top_30 padding_left_30">
						<div class="block-sm-div backgroundcolor1">
							<p>${dataMap.totalCnt}</p>
							<p>合作企业(家)</p>
						</div>
					</div>
				</div>
				<div class="row clearfix">
					<div class="col-md-12 column">
						<div class="margin_0 list-table ">
							<div class="title">
								<span>
								<i class="i i-xing blockDian"></i>
								业务信息</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#applyHis">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
						</div>
						<div class="table_content padding_top_15 padding_bottom_15 padding_left_40 collapse in" id="applyHis">
							<table width="100%" border="0" align="center" cellspacing="1" class="ls_list">
								<thead>
									<tr>
										<th>客户名称</th>
										<th>货物名称</th>
										<th>货物库存</th>
										<th>入库时间</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td align="left">中国移动</td>
										<td align="left">手机卡</td>
										<td align="center">1000</td>
										<td align="center">2016-08-18</td>
									</tr>
									<tr>
										<td align="left">张仲景大药房</td>
										<td align="left">中药</td>
										<td align="center">2000</td>
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