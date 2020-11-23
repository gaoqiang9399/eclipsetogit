<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>运营商报告</title>
		<link rel="stylesheet" href="${webPath}/component/cus/css/MfCusCustomer_certificationReport.css" /> 
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomer_operatorReport.js"></script>
		<script type="text/javascript">
			var cusNo = "${cusNo}";
			
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-12 column">
					<div class="report_content">
						<div class="report_content_box">
						  	<div class="report_items" id="basicInfo">
						  		<div class="item_title"><i class="i i-xing blockDian"></i>基本信息</div>
						  		<div class="basicInfo_content">                                                 
			                         <div class="basicInfo_content_title"><em>用户姓名：</em><span>${mfCusCustomer.cusName}</span></div>
			                         <div class="basicInfo_content_title"><em>身份证号：</em><span>${mfCusCustomer.idNum}</span></div>
			                         <div class="basicInfo_content_title"><em>手机号码：</em><span>${mfCusCustomer.cusTel}</span></div>
			                    </div>
							</div>
							<div class="report_items" id="mfPhoneBook">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>申报信息核查</span>
						  			 <button  class="btn btn-link pull-right" aria-expanded="true" data-toggle="collapse" data-target="#applicationCheckList1">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									 </button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="applicationCheckList1">
									<table id="applicationCheckList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center" style="width:6%;">序号</th>
												<th scope="col" name="check_point" align="left" style="width:30%;text-align:left;">检查项</th>
												<th scope="col" name="evidence" align="left" style="text-align:left;">结果</th>
												<th scope="col" name="result" align="left" style="text-align:left;">依据</th>
											</tr>
										</thead>
										<tbody id="applicationCheckListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							<div class="report_items" id="">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>推断风险点</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#behaviorCheckList1" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
								<div class="list-table margin_left_15 content collapse in" id="behaviorCheckList1">
									<table id="behaviorCheckList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center" style="width:6%;">序号</th>
												<th scope="col" name="check_point" align="left" style="text-align:left;">检查项</th>
												<th scope="col" name="evidence" align="left" style="text-align:left;">结果</th>
												<th scope="col" name="result" align="left" style="text-align:left;">依据</th>
											</tr>
										</thead>
										<tbody id="behaviorCheckListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							<div class="report_items" id="">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>通话次数排名</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#contactList1" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		
						  		<div class="list-table margin_left_15 content collapse in" id="contactList1">
									<table id="contactList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">序号</th>
												<th scope="col" name="phone_num" align="center">号码</th>
												<th scope="col" name="contact_name" align="center">互联网标识</th>
												<th scope="col" name="needs_type" align="center">需求类型</th>
												<th scope="col" name="phone_num_loc" align="center">归属地</th>
												<th scope="col" name="call_cnt" align="center">联系次数</th>
												<th scope="col" name="call_len" align="center">联系时间(分)</th>
												<th scope="col" name="call_out_cnt" align="center">主叫次数</th>
												<th scope="col" name="call_in_cnt" align="center">被叫次数</th>
											</tr>
										</thead>
										<tbody id="contactListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							
							<div class="report_items" id="">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>联系人地区排名</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#contactRegionList1" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="contactRegionList1">
									<table id="contactRegionList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">序号</th>
												<th scope="col" name="region_loc" align="center">地区</th>
												<th scope="col" name="region_uniq_num_cnt" align="center">号码数量</th>
												<th scope="col" name="region_call_in_cnt" align="center">电话呼入次数</th>
												<th scope="col" name="region_call_in_time" align="center">电话呼入时间(分)</th>
												<th scope="col" name="region_call_out_cnt" align="center">电话呼出次数</th>
												<th scope="col" name="region_call_out_time" align="center">电话呼出时间(分)</th>
											</tr>
										</thead>
										<tbody id="contactRegionListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							
							<div class="report_items" id="">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>运营商月账单</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#cellBehaviorList1" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="cellBehaviorList1">
									<table id="cellBehaviorList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">序号</th>
												<th scope="col" name="cell_mth" align="center">月份</th>
												<th scope="col" name="call_out_time" align="center">主叫时间(分钟)</th>
												<th scope="col" name="call_in_time" align="center">被叫时间(分钟)</th>
												<th scope="col" name="call_time" align="center">通话时间(分钟)</th>
												<th scope="col" name="call_cnt" align="center">通话次数</th>
												<th scope="col" name="sms_cnt" align="center">短信数量</th>
												<th scope="col" name="total_amount" align="center">消费金额</th>
											</tr>
										</thead>
										<tbody id="cellBehaviorListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							
							<div class="report_items" id="">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>出行数据</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#tripInfoList1" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="tripInfoList1">
									<table id="tripInfoList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">序号</th>
												<th scope="col" name="trip_leave" align="center">出发地</th>
												<th scope="col" name="trip_dest" align="center">目的地</th>
												<th scope="col" name="trip_type" align="center">出行时间类型</th>
												<th scope="col" name="trip_start_time" align="center">出行开始时间</th>
												<th scope="col" name="trip_end_time" align="center">出行结束时间</th>
											</tr>
										</thead>
										<tbody id="tripInfoListContent">
											
										</tbody>
									</table>
								</div>
							</div>
							
							<div class="report_items" id="">
						  		<div class="item_title">
						  			<i class="i i-xing blockDian"></i>
						  			<span>常用数据</span>
						  			<button class="btn btn-link pull-right" data-toggle="collapse" data-target="#tripConsumeList1" aria-expanded="true"><i class="i i-close-up"></i><i class="i i-open-down"></i></button>
						  		</div>
						  		<div class="list-table margin_left_15 content collapse in" id="tripConsumeList1">
									<table id="tripConsumeList" class="ls_list" width="100%" cellspacing="1" border="0" align="center">
										<thead>
											<tr>
												<th scope="col" name="num" align="center">序号</th>
												<th scope="col" name="order_date" align="center">下单时间</th>
												<th scope="col" name="flight_spend" align="center">机票消费</th>
												<th scope="col" name="train_spend" align="center">火车消费</th>
												<th scope="col" name="hotel_spend" align="center">酒店消费</th>
												<th scope="col" name="count" align="center">月度消费频次(/人次)</th>
											</tr>
										</thead>
										<tbody id="tripConsumeListContent">
											
										</tbody>
									</table>
								</div>
							</div>
						<div>
					</div>
				</div>	
			</div>
			<%-- <div class="formRowCenter">
	   			<dhcc:thirdButton value="确定" action="确定" onclick="myclose();"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div> --%>
   		</div>
	</body>
</html>
