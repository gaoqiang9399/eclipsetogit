
$(function(){
	myCustomScrollbarForForm({
		obj:".report_content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
	//进入页面获取所有的第三方信息
	$.ajax({
		url:webPath+"/mfThirdServiceLog/getAllOperatorInfoAjax?cusNo="+cusNo,
		type:'post',
		dataType:'json',
		success:function(data){ 
			LoadingAnimate.stop();
			if (data.flag == "success") {
				//1：申报信息核查
				var application_check=data.application_check;
				if($("#applicationCheckListContent").html().length>0){
					$("#applicationCheckListContent").html("");
				}
				var trHtml_1 = "";
                var i;
				if(application_check && application_check.length>0){
					for(i =0;i<application_check.length;i++){
						trHtml_1 +='<tr>';
						trHtml_1 += '<td align="center" style="width:6%;">'+(i+1)+'</td>';
						trHtml_1 += '<td align="left" style="text-align:left;">'+application_check[i].check_point+'</td>';
						if(application_check[i].score==1){
							trHtml_1 += '<td align="left" style="text-align:left;color:#008000">'+application_check[i].result+'</td>';
						}else{
							trHtml_1 += '<td align="left" style="text-align:left;color:orange;">'+application_check[i].result+'</td>';
						}
						trHtml_1 += '<td align="left" style="text-align:left;">'+application_check[i].evidence+'</td>';
						trHtml_1 += "</tr>";
					}
				}else{
					$("#applicationCheckList1").html("暂无数据");
				}
				$("#applicationCheckListContent").append(trHtml_1);
				
				
				//推断风险点
				var behavior_check=data.behavior_check;
				if($("#behaviorCheckListContent").html().length>0){
					$("#behaviorCheckListContent").html("");
				}
				var trHtml_2 = "";
				if(behavior_check && behavior_check.length>0){
					for(i =0;i<behavior_check.length;i++){
						trHtml_2 +='<tr>';
						trHtml_2 += '<td align="center" style="width:6%;">'+(i+1)+'</td>';
						trHtml_2 += '<td align="left" style="text-align:left;">'+behavior_check[i].check_point+'</td>';
						if(behavior_check[i].score==1){
							trHtml_2 += '<td align="left" style="text-align:left;color:#008000">'+behavior_check[i].result+'</td>';
						}else{
							trHtml_2 += '<td align="left" style="text-align:left;color:orange;">'+behavior_check[i].result+'</td>';
						}
						
						trHtml_2 += '<td align="left" style="text-align:left;">'+behavior_check[i].evidence+'</td>';
						trHtml_2 += "</tr>";
					}
				}else{
					$("#behaviorCheckList1").html("暂无数据");
				}
				$("#behaviorCheckListContent").append(trHtml_2);
				
				//通话次数排名
				var contact_list=data.contact_list;
				if($("#contactListContent").html().length>0){
					$("#contactListContent").html("");
				}
				var trHtml_3 = "";
				if(contact_list && contact_list.length>0){
					for(i =0;i<contact_list.length;i++){
						trHtml_3 +='<tr>';
						trHtml_3 += '<td align="center">'+(i+1)+'</td>';
						trHtml_3 += '<td align="center">'+contact_list[i].phone_num+'</td>';
						trHtml_3 += '<td align="center">'+contact_list[i].contact_name+'</td>';
						trHtml_3 += '<td align="center">'+contact_list[i].needs_type+'</td>';
						trHtml_3 += '<td align="center">'+contact_list[i].phone_num_loc+'</td>';
						trHtml_3 += '<td align="center">'+contact_list[i].call_cnt+'</td>';
						trHtml_3 += '<td align="center">'+contact_list[i].call_len+'</td>';
						trHtml_3 += '<td align="center">'+contact_list[i].call_out_cnt+'</td>';
						trHtml_3 += '<td align="center">'+contact_list[i].call_in_cnt+'</td>';
						trHtml_3 += "</tr>";
					}
				}else{
					$("#contactList1").html("暂无数据");
				}
				$("#contactListContent").append(trHtml_3);
				
				//联系人地区排名
				var contact_region=data.contact_region;
				if($("#contactRegionListContent").html().length>0){
					$("#contactRegionListContent").html("");
				}
				var trHtml_4 = "";
				if(contact_region && contact_region.length>0){
					for(i =0;i<contact_region.length;i++){
						trHtml_4 +='<tr>';
						trHtml_4 += '<td align="center">'+(i+1)+'</td>';
						trHtml_4 += '<td align="center">'+contact_region[i].region_loc+'</td>';
						trHtml_4 += '<td align="center">'+contact_region[i].region_uniq_num_cnt+'</td>';
						trHtml_4 += '<td align="center">'+contact_region[i].region_call_in_cnt+'</td>';
						trHtml_4 += '<td align="center">'+contact_region[i].region_call_in_time+'</td>';
						trHtml_4 += '<td align="center">'+contact_region[i].region_call_out_cnt+'</td>';
						trHtml_4 += '<td align="center">'+contact_region[i].region_call_out_time+'</td>';
						trHtml_4 += "</tr>";
					}
				}else{
					$("#contactRegionList1").html("暂无数据");
				}
				$("#contactRegionListContent").append(trHtml_4);
				
				//运营商月账单
				var cell_behavior=data.cell_behavior;
				if($("#cellBehaviorListContent").html().length>0){
					$("#cellBehaviorListContent").html("");
				}
				var trHtml_5 = "";
				if(cell_behavior && cell_behavior.length>0){
					for(i =0;i<cell_behavior.length;i++){
						trHtml_5 +='<tr>';
						trHtml_5 += '<td align="center">'+(i+1)+'</td>';
						trHtml_5 += '<td align="center">'+cell_behavior[i].cell_mth+'</td>';
						trHtml_5 += '<td align="center">'+cell_behavior[i].call_out_time+'</td>';
						trHtml_5 += '<td align="center">'+cell_behavior[i].call_in_time+'</td>';
						trHtml_5 += '<td align="center">'+cell_behavior[i].call_time+'</td>';
						trHtml_5 += '<td align="center">'+cell_behavior[i].call_cnt+'</td>';
						trHtml_5 += '<td align="center">'+cell_behavior[i].sms_cnt+'</td>';
						trHtml_5 += '<td align="center">'+cell_behavior[i].total_amount+'</td>';
						trHtml_5 += "</tr>";
						
						
					}
				}else{
					$("#cellBehaviorList1").html("暂无数据");
				}
				$("#cellBehaviorListContent").append(trHtml_5);
				
				//出行数据
				var trip_info=data.trip_info;
				if($("#tripInfoListContent").html().length>0){
					$("#tripInfoListContent").html("");
				}
				//var trHtml_5 = "";
                trHtml_5 = "";
				if(trip_info && trip_info.length>0){
					for(i =0;i<trip_info.length;i++){
						trHtml_5 +='<tr>';
						trHtml_5 += '<td align="center">'+(i+1)+'</td>';
						trHtml_5 += '<td align="center">'+trip_info[i].trip_leave+'</td>';
						trHtml_5 += '<td align="center">'+trip_info[i].trip_dest+'</td>';
						trHtml_5 += '<td align="center">'+trip_info[i].trip_type+'</td>';
						trHtml_5 += '<td align="center">'+trip_info[i].trip_start_time+'</td>';
						trHtml_5 += '<td align="center">'+trip_info[i].trip_end_time+'</td>';
						trHtml_5 += "</tr>";
					}
				}else{
					$("#tripInfoList1").html("暂无数据");
				}
				$("#tripInfoListContent").append(trHtml_5);
				
				//常用服务
				var trip_consume=data.trip_consume;
				if($("#tripConsumeListContent").html().length>0){
					$("#tripConsumeListContent").html("");
				}
				trHtml_5 = "";
				if(trip_consume && trip_consume.length>0){
					for(i =0;i<trip_consume.length;i++){
						trHtml_5 +='<tr>';
						trHtml_5 += '<td align="center">'+(i+1)+'</td>';
						trHtml_5 += '<td align="center">'+trip_consume[i].order_date+'</td>';
						trHtml_5 += '<td align="center">'+trip_consume[i].flight_spend+'</td>';
						trHtml_5 += '<td align="center">'+trip_consume[i].train_spend+'</td>';
						trHtml_5 += '<td align="center">'+trip_consume[i].hotel_spend+'</td>';
						trHtml_5 += '<td align="center">'+trip_consume[i].count+'</td>';
						trHtml_5 += "</tr>";
					}
				}else{
					$("#tripConsumeList1").html("暂无数据");
				}
				$("#tripConsumeListContent").append(trHtml_5);
			} else {
				window.top.alert(data.msg, 0);
			}
		},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", 0));
			}
		});
 });
var queryThirdService = function(window, $) {
	
	var _getBrRuleSpecialList = function (){
		var url = webPath+"/mfThirdServiceLog/getBrRuleSpecialListAjax?cusNo="+cusNo;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){ 
				alert("000");
				var url = data.data.url;
				//window.location.href=data.data.url;
			},
			error:function(){
				alert("999");
			}
		});
	};
	// 百融-多次申请规则评分
	var _getBrRuleApplyLoan=function(){
		var url = webPath+"/mfThirdServiceLog/getBrRuleApplyLoanAjax?cusNo="+cusNo;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){ 
				if (data.flag == "success") {
					var arrStr = data.data.rules;
					$("#brRuleApplyLoanlist").html("");
					var trHtml = "";
					if(arrStr!=undefined){
						for(var i =0;i<arrStr.length;i++){
							trHtml +='<thead>';
							trHtml +='<tr>';
							trHtml +='<th scope="col" name="num" align="center" >序号</th>';
							trHtml +='<th scope="col" name="prcpAmt" align="center">命中规则项</th>';
							trHtml +='<th scope="col" name="normInt" align="center">规则评分</th>';
							trHtml +='</tr>';
							trHtml +='</thead>';
							trHtml +='<tbody id="brRuleApplyLoanContent">';
							trHtml +='<tr>';
							trHtml += '<td align="center">'+(i+1)+'</td>';
							trHtml += '<td align="center">'+arrStr[i].rule_name+'</td>';
							trHtml += '<td align="center">'+arrStr[i].rule_weight+'</td>';
							trHtml += "</tr>";
							trHtml +='</tbody>';
						}
						$("#brRuleApplyLoanlist").append(trHtml);
					}else{
						$("#brRuleApplyLoanlist").html(data.data.result);
					}
					
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error:function(){
				window.top.alert(data.msg, 0);
			}
		});
	};
	// 圣数-火眼黑名单
	var _getFireEyesBlack=function(){
		var url = webPath+"/mfThirdServiceLog/getFireEyesBlackAjax?cusNo="+cusNo;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){ 
				if (data.flag == "success") {
					var arrStr = data.data.rules;
					$("#getFireEyesBlackList").html("");
					var trHtml = "";
					if(data.data.status==0){
						$("#getFireEyesBlackList").html(data.data.result);
					}else if(data.data.status==1){
						for(var i =0;i<arrStr.length;i++){
							trHtml +='<thead>';
							trHtml +='<tr>';
							trHtml +='<th scope="col" name="num" align="center">序号</th>';
							trHtml +='<th scope="col" name="prcpAmt" align="center">规则序号</th>';
							trHtml +='<th scope="col" name="normInt" align="center">命中结果</th>';
							trHtml +='<th scope="col" name="normInt" align="center">说明</th>';
							trHtml +='</tr>';
							trHtml +='</thead>';
							trHtml +='<tbody id="getFireEyesBlackListContent">';
							trHtml +='<tr>';
							trHtml_2 += '<td align="center">'+(i+1)+'</td>';
							trHtml_2 += '<td align="center">'+arrStr[i].bid+'</td>';
							trHtml_2 += '<td align="center">'+arrStr[i].stat+'</td>';
							trHtml_2 += '<td align="center">'+arrStr[i].msg+'</td>';
							trHtml += "</tr>";
							trHtml +='</tbody>';
						}
						$("#getFireEyesBlackList").append(trHtml);
					}
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error:function(){
				window.top.alert(data.msg, 0);
			}
		});
	};
	//大圣数据-百融-反欺诈特殊名单
	var _getBrRuleSpecial=function(){
		var url = webPath+"/mfThirdServiceLog/getBrRuleSpecialListAjax?cusNo="+cusNo;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){ 
				if (data.flag == "success") {
					var arrStr = data.data.rules;
					$("#getBrRuleSpecialList").html("");
					var trHtml = "";
					if(arrStr==undefined){
						$("#getBrRuleSpecialList").html(data.data.result);
					}else{
						for(var i =0;i<arrStr.length;i++){
							
							trHtml +='<thead>';
							trHtml +='	<tr>';
							trHtml +='	<th scope="col" name="num" align="center">序号</th>';
							trHtml +='<th scope="col" name="prcpAmt" align="center">命中规则项</th>';
							trHtml +='<th scope="col" name="normInt" align="center">规则评分</th>';
							trHtml +='</tr>';
							trHtml +='</thead>';
							trHtml +='<tbody id="getBrRuleSpecialListContent">';
							trHtml +='<tr>';
							trHtml += '<td align="center">'+(i+1)+'</td>';
							trHtml += '<td align="center">'+arrStr[i].rule_name+'</td>';
							trHtml += '<td align="center">'+arrStr[i].rule_weight+'</td>';
							trHtml += "</tr>";
							trHtml +='</tbody>';
						}
						$("#getBrRuleSpecialList").append(trHtml);
					}
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error:function(){
				window.top.alert(data.msg, 0);
			}
		});
	};
	//天行_运营商三要素
	var _getMobileVerifyThree=function(){
		var url = webPath+"/mfThirdServiceLog/getMobileVerifyThreeAjax?cusNo="+cusNo;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){ 
				if (data.flag == "success") {
					var arrStr = data.data;
					$("#getMobileVerifyThreeList").html("");
					var trHtml = "";
					trHtml +='<thead>'
					trHtml +='<tr>';
					trHtml +='<th scope="col" name="num" align="center">姓名</th>';
					trHtml +='<th scope="col" name="prcpAmt" align="center">手机号</th>';
					trHtml +='<th scope="col" name="normInt" align="center">身份证号</th>';
					trHtml +='<th scope="col" name="normInt" align="center">匹配结果</th>';
					trHtml +='</tr>';
					trHtml +='</thead>';
					trHtml +='<tbody>';
					trHtml +='<tr>';
					trHtml += '<td align="center">'+arrStr.name+'</td>';
					trHtml += '<td align="center">'+arrStr.mobile+'</td>';
					trHtml += '<td align="center">'+arrStr.identityCard+'</td>';
					trHtml += '<td align="center">'+arrStr.compareStatusDesc+'</td>';
					trHtml += "</tr>";
					trHtml +='</tbody>';
					$("#getMobileVerifyThreeList").append(trHtml);
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error:function(){
				window.top.alert(data.msg, 0);
			}
		});
	};
	//贷后邦-反欺诈
	var _getDhbGetSauronC=function(){
		var url = webPath+"/mfThirdServiceLog/getDhbGetSauronCAjax?cusNo="+cusNo;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){ 
				if (data.flag == "success") {
					var arrStr6 = data.data;
					var idcards=data.data.binding_idcards;//绑定身份证情况
					var phones=data.data.binding_phones;//绑定电话情况
					$("#getDhbGetSauronCList").html("");
					var trHtml_6 = "";
					if(!arrStr6.age){
						$("#getDhbGetSauronCList").html("暂无数据");
					}else{
						trHtml_6 +='<tbody id="getDhbGetSauronCListContent">';
						trHtml_6 +='<tr>';
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">用户基本信息</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">年龄</td><td align="center">'+arrStr6.age+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">性别</td><td align="center">'+arrStr6.gender+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">生日日期</td><td align="center">'+arrStr6.birthday+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">身份证是否是有效身份证</td><td align="center">'+(arrStr6.idcard_validate==1?"通过":"未通过")+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证户籍省份</td><td align="center">'+arrStr6.idcard_province+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">身份证户籍城市</td><td align="center">'+arrStr6.idcard_city+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证户籍地区</td><td align="center">'+arrStr6.idcard_region+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机运营商</td><td align="center">'+arrStr6.phone_operator+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">手机归属地省份</td><td align="center">'+arrStr6.phone_province+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机归属地城市</td><td align="center">'+arrStr6.phone_city+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证号记录天数</td><td align="center">'+arrStr6.record_idcard_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机号记录天数</td><td align="center">'+arrStr6.record_phone_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证最近出现时间</td><td align="center">'+arrStr6.last_appear_idcard+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机号最近出现时间</td><td align="center">'+arrStr6.last_appear_phone+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">关联身份证数量</td><td align="center">'+arrStr6.used_idcards_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">关联手机号数量</td><td align="center">'+arrStr6.used_phones_cnt+'</td>';
						trHtml_6 += "</tr>";
						//社交风险点
						trHtml_6 +='<tr>';
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">社交风险点</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">葫芦分</td><td align="center">'+(arrStr6.sn_score==""?"无":arrStr6.sn_score)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">直接联系人</td><td align="center">'+(arrStr6.sn_order1_contacts_cnt==""?"无":arrStr6.sn_order1_contacts_cnt)+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">直接联系人在黑名单中数量(直接黑人)</td><td align="center">'+(arrStr6.sn_order1_blacklist_contacts_cnt==""?"无":arrStr6.sn_order1_blacklist_contacts_cnt)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">间接联系人在黑名单中数量(间接黑人)</td><td align="center">'+(arrStr6.sn_order2_blacklist_contacts_cnt==""?"无":arrStr6.sn_order2_blacklist_contacts_cnt)+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">认识间接黑人的直接联系人个数</td><td align="center">'+(arrStr6.sn_order2_blacklist_routers_cnt==""?"无":arrStr6.sn_order2_blacklist_routers_cnt)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">认识间接黑人的直接联系人比例</td><td align="center">'+(arrStr6.sn_order2_blacklist_routers_pct==""?"无":arrStr6.sn_order2_blacklist_routers_pct)+'</td>';
						trHtml_6 += "</tr><tr>";
						//黑名单
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">黑名单</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证是否命中黑名单</td><td align="center">'+(arrStr6.idcard_in_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机号是否命中黑名单</td><td align="center">'+(arrStr6.phone_in_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">是否命中法院黑名单</td><td align="center">'+(arrStr6.in_court_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">是否命中网贷黑名单</td><td align="center">'+(arrStr6.in_p2p_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">是否命中银行黑名单</td><td align="center">'+(arrStr6.in_bank_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近该身份证出现在黑名单中时间</td><td align="center">'+(arrStr6.last_appear_idcard_in_blacklist==""?"无":arrStr6.last_appear_idcard_in_blacklist)+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近该手机号出现在黑名单中时间</td><td align="center">'+(arrStr6.last_appear_phone_in_blacklist==""?"无":arrStr6.last_appear_phone_in_blacklist)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">线上消费分期出现次数</td><td align="center">'+arrStr6.online_installment_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">历史机构类型</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">线下消费分期出现次数</td><td align="center">'+arrStr6.offline_installment_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">信用卡代还出现次数</td><td align="center">'+arrStr6.credit_card_repayment_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">小额快速贷出现次数</td><td align="center">'+arrStr6.payday_loan_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">线上现金贷出现次数</td><td align="center">'+arrStr6.online_cash_loan_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">线下现金贷出现次数</td><td align="center">'+arrStr6.offline_cash_loan_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">其他</td><td align="center">'+arrStr6.others_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">历史查询</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">历史查询总次数</td><td align="center">'+arrStr6.search_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近7天查询次数</td><td align="center">'+arrStr6.search_cnt_recent_7_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近14天查询次数</td><td align="center">'+arrStr6.search_cnt_recent_14_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近30天查询次数</td><td align="center">'+arrStr6.search_cnt_recent_30_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近60天查询次数</td><td align="center">'+arrStr6.search_cnt_recent_60_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近90天查询次数</td><td align="center">'+arrStr6.search_cnt_recent_90_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近180天查询次数</td><td align="center">'+arrStr6.search_cnt_recent_180_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">历史查询总机构数</td><td align="center">'+arrStr6.org_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近7天查询机构数</td><td align="center">'+arrStr6.org_cnt_recent_7_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近14天查询机构数</td><td align="center">'+arrStr6.org_cnt_recent_14_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近30天查询机构数</td><td align="center">'+arrStr6.org_cnt_recent_30_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近60天查询机构数</td><td align="center">'+arrStr6.org_cnt_recent_60_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近90天查询机构数</td><td align="center">'+arrStr6.org_cnt_recent_90_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近180天查询机构数</td><td align="center">'+arrStr6.org_cnt_recent_180_days+'</td>';
						trHtml_6 += "</tr>";
						trHtml_6 += '</tbody>';
						$("#getDhbGetSauronCList").append(trHtml_6);

                        var trHtml;
                        var i;
						if(idcards.length!=0){
							trHtml="";
							trHtml+='<div class="list-table margin_left_15 content">';
							trHtml+='<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">';
							trHtml+='<thead>';
							trHtml+='<tr>';
							trHtml+='<th scope="col" name="" align="center">绑定其他身份证号码</th>';
							trHtml+='<th scope="col" name="" align="center">此号码绑定其他姓名个数</th>';
							trHtml+='<th scope="col" name="" align="center">查询此身份证的机构数</th>';
							trHtml+='<th scope="col" name="" align="center">身份证是否是有效身份证</th>';
							trHtml+='<th scope="col" name="" align="center">身份证户籍省份</th>';
							trHtml+='<th scope="col" name="" align="center">身份证户籍城市</th>';
							trHtml+='<th scope="col" name="" align="center">身份证户籍地区</th>';
							trHtml+='<th scope="col" name="" align="center">年龄</th>';
							trHtml+='<th scope="col" name="" align="center">性别</th>';
							trHtml+='<th scope="col" name="" align="center">最近此身份证出现时间</th>';
							trHtml+='</tr>';
							trHtml+='</thead>';
							trHtml+='<tbody>';
							for(i =0;i<idcards.length;i++){
								trHtml +='<tr>';
								trHtml += '<td align="center">'+(i+1)+'</td>';
								trHtml += '<td align="center">'+idcards[i].other_idcard+'</td>';
								trHtml += '<td align="center">'+idcards[i].other_names_cnt+'</td>';
								trHtml += '<td align="center">'+idcards[i].search_orgs_cnt+'</td>';
								trHtml += '<td align="center">'+idcards[i].idcard_validate+'</td>';
								trHtml += '<td align="center">'+idcards[i].idcard_province+'</td>';
								trHtml += '<td align="center">'+idcards[i].idcard_city+'</td>';
								trHtml += '<td align="center">'+idcards[i].idcard_region+'</td>';
								trHtml += '<td align="center">'+idcards[i].idcard_age+'</td>';
								trHtml += '<td align="center">'+idcards[i].idcard_gender+'</td>';
								trHtml += '<td align="center">'+idcards[i].last_appear_idcard+'</td>';
								trHtml += "</tr>";
							}
							trHtml+='</tbody>';
							trHtml+='</table>';
							trHtml+='</div>';
							$("#getDhbGetSauronC").append(trHtml_6);
						}
						if(phones.length!=0){
							trHtml="";
							trHtml+='<div class="list-table margin_left_15 content">';
							trHtml+='<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">';
							trHtml+='<thead>';
							trHtml+='<tr>';
							trHtml+='<th scope="col" name="" align="center">绑定其他手机号码</th>';
							trHtml+='<th scope="col" name="" align="center">此号码绑定其他姓名个数</th>';
							trHtml+='<th scope="col" name="" align="center">查询此手机号的机构数</th>';
							trHtml+='<th scope="col" name="" align="center">手机运营商</th>';
							trHtml+='<th scope="col" name="" align="center">手机归属地省份</th>';
							trHtml+='<th scope="col" name="" align="center">手机归属地城市</th>';
							trHtml+='<th scope="col" name="" align="center">最近此手机号出现时间</th>';
							trHtml+='</tr>';
							trHtml+='</thead>';
							trHtml+='<tbody>';
							for(i =0;i<phones.length;i++){
								trHtml +='<tr>';
								trHtml += '<td align="center">'+idcards[i].other_phone+'</td>';
								trHtml += '<td align="center">'+idcards[i].other_names_cnt+'</td>';
								trHtml += '<td align="center">'+idcards[i].search_orgs_cnt+'</td>';
								trHtml += '<td align="center">'+idcards[i].phone_operator+'</td>';
								trHtml += '<td align="center">'+idcards[i].phone_province+'</td>';
								trHtml += '<td align="center">'+idcards[i].phone_city+'</td>';
								trHtml += '<td align="center">'+idcards[i].last_appear_phone+'</td>';
								trHtml += "</tr>";
							}
							trHtml+='</tbody>';
							trHtml+='</table>';
							trHtml+='</div>';
							$("#getDhbGetSauronC").append(trHtml_6);
						}
					}
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error:function(){
				window.top.alert(data.msg, 0);
			}
		});
	};
	return {
		//init : _init,
		getBrRuleSpecialList:_getBrRuleSpecialList,
		getBrRuleApplyLoan:_getBrRuleApplyLoan,
		getFireEyesBlack:_getFireEyesBlack,
		getBrRuleSpecial:_getBrRuleSpecial,
		getMobileVerifyThree:_getMobileVerifyThree,
		getDhbGetSauronC:_getDhbGetSauronC
	};
}(window, jQuery);