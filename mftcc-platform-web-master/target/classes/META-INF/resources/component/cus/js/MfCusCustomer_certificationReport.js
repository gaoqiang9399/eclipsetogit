
$(function(){
	/*$.ajax({
		url:webPath+"/mfPhoneBook/findByPageAjax?cusNo="+cusNo,
		type:'post',
		dataType:'json',
		success:function(data){ 
			if(data.mfPhoneBookList.length==0){
				$("#mfPhoneBookList").html("暂无数据");
			}else{
				var tableHtmlPhoneBook=data.tableHtmlPhoneBook;
				$("#mfPhoneBookList").html(tableHtmlPhoneBook);
			}
		},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	*/
	myCustomScrollbarForForm({
		obj:".report_content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
	//进入页面获取所有的第三方信息
	$.ajax({
		url:webPath+"/mfThirdServiceLog/getAllThirdServiceInfoAjax?cusNo="+cusNo,
		type:'post',
		dataType:'json',
		success:function(data){ 
			LoadingAnimate.stop();
			if (data.flag == "success") {
				//获取芝麻分
				var result8=data.resultMap8;
				if(result8.zhimascore){
					$("#zhimascore").val(result8.zhimascore);
				}
				
				//1：百融-多次申请规则评分
				var result1=data.resultMap1;
				var arrStr1 = result1.rules;
				if($("#brRuleApplyLoanContent").html().length>0){
					$("#brRuleApplyLoanContent").html("");
				}
				var trHtml_1 = "";
                var i;
                var idcards,phones,trHtml_6,trHtml;
				if(arrStr1!=undefined){
					for(i =0;i<arrStr1.length;i++){
						trHtml_1 +='<tr>';
						trHtml_1 += '<td align="center">'+(i+1)+'</td>';
						trHtml_1 += '<td align="center">'+arrStr1[i].rule_name+'</td>';
						trHtml_1 += '<td align="center">'+arrStr1[i].rule_weight+'</td>';
						trHtml_1 += "</tr>";
					}
					$("#brRuleApplyLoanContent").append(trHtml_1);
				}else if(result1.result){
					$("#brRuleApplyLoanlist").html(result1.result);
				}else{
					$("#brRuleApplyLoanlist").html("暂无数据");
				}
				//2:圣数-火眼黑名单
				var result2=data.resultMap2;
				var arrStr2 = result2.blackArray;
				if($("#getFireEyesBlackList").html().length>0){
					$("#getFireEyesBlackList").html("");
				}
				var trHtml_2 = "";
				if(result2.status==0){
					$("#getFireEyesBlackList").html(result2.result);
				}else if(result2.status==1){
					for(i =0;i<arrStr2.length;i++){
						trHtml_2 +='<tr>';
						trHtml_2 += '<td align="center">'+(i+1)+'</td>';
						trHtml_2 += '<td align="center">'+arrStr2[i].bid+'</td>';
						trHtml_2 += '<td align="center">'+arrStr2[i].stat+'</td>';
						trHtml_2 += '<td align="center">'+arrStr2[i].msg+'</td>';
						trHtml_2 += "</tr>";
					}
					$("#getFireEyesBlackListContent").append(trHtml_2);
				}else if(arrStr2==undefined){
					$("#getFireEyesBlackList").html("暂无数据");
				}
				
				//3:百融-反欺诈特殊名单
				var result3=data.resultMap3;
				var arrStr3 = result3.rules;
				if($("#getBrRuleSpecialListContent").html().length>0){
					$("#getBrRuleSpecialListContent").html("");
				}
				var trHtml_3 = "";
				if(  result3.result){
					$("#getBrRuleSpecialList").html(result3.result);
				}else if(arrStr3==undefined){
					$("#getBrRuleSpecialList").html("暂无数据");
				}else{
					for(i =0;i<arrStr3.length;i++){
						trHtml_3 +='<tr>';
						trHtml_3 += '<td align="center">'+(i+1)+'</td>';
						trHtml_3 += '<td align="center">'+arrStr3[i].rule_name+'</td>';
						trHtml_3 += '<td align="center">'+arrStr3[i].rule_weight+'</td>';
						trHtml_3 += '<td align="center">'+arrStr3[i].msg+'</td>';
						trHtml_3 += "</tr>";
					}
					$("#getBrRuleSpecialListContent").append(trHtml_3);
				}
				
				//4:通讯录于通话记录分析
				var _result4=data.resultMap4;
				var _arrStr4 = _result4.bookFenXiMap;
				if($("#getBookDataListContent").html().length>0){
					$("#getBookDataListContent").html("");
				}
				var _trHtml_4 = "";
				if(!_arrStr4){
					$("#getBookDataList1").html("暂无数据");
				}else{
					_trHtml_4 +='<tr>';
					_trHtml_4 += '<td align="center"  width="100px">'+_arrStr4.baCnt+'</td>';
					_trHtml_4 += '<td align="center"  width="100px">'+_arrStr4.maCnt+'</td>';
					_trHtml_4 += '<td align="center"  width="100px">'+_arrStr4.shuCnt+'</td>';
					_trHtml_4 += '<td align="center"  width="100px">'+_arrStr4.guCnt+'</td>';
					_trHtml_4 += '<td align="center"  width="100px">'+_arrStr4.jiuCnt+'</td>';
					_trHtml_4 += '<td align="center"  width="100px">'+_arrStr4.qiTaCnt+'</td>';
					_trHtml_4 += '<td align="center">'+_arrStr4.recordCnt+'</td>';
					_trHtml_4 += '<td align="center">'+_arrStr4.zongCnt+'</td>';
					_trHtml_4 += "</tr>";
				}
				$("#getBookDataListContent").append(_trHtml_4);
				
				//4:大圣- 运营商数据采集
				var result4=data.resultMap4;
				var arrStr4 = result4.operatorVoices;
				if($("#getTelecomDataListContent").html().length>0){
					$("#getTelecomDataListContent").html("");
				}
				var trHtml_4 = "";
				if(!arrStr4||arrStr4.length<=0){
					$("#getTelecomDataList1").html("暂无数据");
				}else{
					for(i =0;i<arrStr4.length;i++){
						trHtml_4 +='<tr>';
						trHtml_4 += '<td align="center">'+(i+1)+'</td>';
						trHtml_4 += '<td align="center">'+arrStr4[i].contact_name+'</td>';
						trHtml_4 += '<td align="center">'+arrStr4[i].phone_num+'</td>';
						trHtml_4 += '<td align="center">'+arrStr4[i].phone_num_loc+'</td>';
						if(arrStr4[i].call_cnt == "--"){
							trHtml_4 += '<td align="center">'+arrStr4[i].call_cnt+'</td>';
						}else{
							trHtml_4 += '<td align="center">'+arrStr4[i].call_cnt+'次</td>';
						}
						if(arrStr4[i].call_cnt == "--"){
							trHtml_4 += '<td align="center">'+arrStr4[i].call_len+'</td>';
						}else{
							trHtml_4 += '<td align="center">'+arrStr4[i].call_len+'分钟</td>';
						}
						if(arrStr4[i].call_cnt == "--"){
							trHtml_4 += '<td align="center">'+arrStr4[i].call_out_len+'</td>';
						}else{
							trHtml_4 += '<td align="center">'+arrStr4[i].call_out_len+'分钟</td>';
						}
						if(arrStr4[i].call_cnt == "--"){
							trHtml_4 += '<td align="center">'+arrStr4[i].call_in_len+'</td>';
						}else{
							trHtml_4 += '<td align="center">'+arrStr4[i].call_in_len+'分钟</td>';
						}
						
						/*trHtml_4 += '<td align="center">'+arrStr4[i].contact_1w+'</td>';*/
						if(arrStr4[i].relative_flag){
							if(arrStr4[i].relative_flag == '1'){
								trHtml_4 += '<td align="center"><span class="relative_flag" style="background-color:#228B22;">紧急联系人</span></td>';
							}else{
								trHtml_4 += '<td align="center"><span class="relative_flag" style="background-color:#c9302c;">紧急联系人</span></td>';
							}
						}else{
							trHtml_4 += '<td align="center"></td>';
						}
						
						trHtml_4 += "</tr>";
					}
				}
				$("#getTelecomDataListContent").append(trHtml_4);
				//紧急联系人分析
				/*var result42=data.resultMap4;
				var result7=data.resultMap7.familyInfoList;
				var arrStr42 = result42.familyInfoList;
				if($("#getfamilyInfoListContent").html().length>0){
					$("#getfamilyInfoListContent").html("");
				}
				var trHtml_42 = "";
				if(arrStr42==undefined||""==arrStr42){
					if(result7){
						for(var i =0;i<result7.length;i++){
							trHtml_42 +='<tr>';
							trHtml_42 += '<td align="center">'+(i+1)+'</td>';
							trHtml_42 += '<td align="center">'+result7[i].relName+'</td>';
							trHtml_42 += '<td align="center">'+result7[i].relTel+'</td>';
							trHtml_42 += '<td align="center">'+result7[i].voiceCount+'</td>';
							trHtml_42 += '<td align="center">'+result7[i].voiceTime+'</td>';
							trHtml_42 += "</tr>";
						}
					}else{
						$("#getTelecomDataList2").html("暂无数据");
					}
				}else{
					for(var i =0;i<arrStr42.length;i++){
						trHtml_42 +='<tr>';
						trHtml_42 += '<td align="center">'+(i+1)+'</td>';
						trHtml_42 += '<td align="center">'+arrStr42[i].relName+'</td>';
						trHtml_42 += '<td align="center">'+arrStr42[i].relTel+'</td>';
						trHtml_42 += '<td align="center">'+arrStr42[i].voiceCount+'</td>';
						trHtml_42 += '<td align="center">'+arrStr42[i].voiceTime+'</td>';
						trHtml_42 += "</tr>";
					}
				}
				$("#getfamilyInfoListContent").append(trHtml_42);*/
				
				//5:天行_运营商三要素
				var arrStr5=data.resultMap5;
				if($("#getMobileVerifyThreeContent").html().length>0){
					$("#getMobileVerifyThreeContent").html("");
				}
				var trHtml_5 = "";
				
				if(!arrStr5.name){
					$("#getMobileVerifyThreeList").html("暂无数据");
				}else{
					trHtml_5 +='<tr >';
					trHtml_5 += '<td align="center">'+arrStr5.name+'</td>';
					trHtml_5 += '<td align="center">'+arrStr5.mobile+'</td>';
					trHtml_5 += '<td align="center">'+arrStr5.identityCard+'</td>';
					trHtml_5 += '<td align="center">'+arrStr5.compareStatusDesc+'</td>';
					trHtml_5 += "</tr>";
					$("#getMobileVerifyThreeContent").append(trHtml_5);
				}
				//6:大圣数据-贷后邦-反欺诈
				var arrStr6=data.resultMap6;
				//判断新老数据 
				var methodStr = arrStr6.method;
				if($("#getDhbGetSauronCListContent").html().length>0){
					$("#getDhbGetSauronCListContent").html("");
				}
				if(methodStr == 'getSauronDHB'){//新数据
					var user_basic = arrStr6.user_basic;//基础数据
					var risk_social_network = arrStr6.risk_social_network;//社交风险点
					var risk_blacklist = arrStr6.risk_blacklist;//黑名单
					var history_org = arrStr6.history_org;//历史机构类型
					var history_search = arrStr6.history_search;//历史查询
					idcards=arrStr6.binding_idcards;//绑定身份证情况
					phones=arrStr6.binding_phones;//绑定电话情况
					$("#getDhbGetSauronCList").html("");
					trHtml_6 = "";
					if(!user_basic	|| !user_basic.age){
						$("#getDhbGetSauronCList").html("暂无数据");
					}else{
						trHtml_6 +='<tbody id="getDhbGetSauronCListContent">';
						trHtml_6 +='<tr>';
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">用户基本信息</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">年龄</td><td align="center">'+user_basic.age+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">性别</td><td align="center">'+user_basic.gender+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">生日日期</td><td align="center">'+user_basic.birthday+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">身份证是否是有效身份证</td><td align="center">'+(user_basic.idcard_validate==1?"通过":"未通过")+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证户籍省份</td><td align="center">'+user_basic.idcard_province+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">身份证户籍城市</td><td align="center">'+user_basic.idcard_city+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证户籍地区</td><td align="center">'+user_basic.idcard_region+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机运营商</td><td align="center">'+user_basic.phone_operator+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">手机归属地省份</td><td align="center">'+user_basic.phone_province+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机归属地城市</td><td align="center">'+user_basic.phone_city+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证号记录天数</td><td align="center">'+user_basic.record_idcard_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机号记录天数</td><td align="center">'+user_basic.record_phone_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证最近出现时间</td><td align="center">'+user_basic.last_appear_idcard+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机号最近出现时间</td><td align="center">'+user_basic.last_appear_phone+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">关联身份证数量</td><td align="center">'+user_basic.used_idcards_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">关联手机号数量</td><td align="center">'+user_basic.used_phones_cnt+'</td>';
						trHtml_6 += "</tr>";
						//社交风险点
						trHtml_6 +='<tr>';
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">社交风险点</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">葫芦分</td><td align="center">'+(risk_social_network.sn_score==""?"无":risk_social_network.sn_score)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">直接联系人</td><td align="center">'+(risk_social_network.sn_order1_contacts_cnt==""?"无":risk_social_network.sn_order1_contacts_cnt)+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">直接联系人在黑名单中数量(直接黑人)</td><td align="center">'+(risk_social_network.sn_order1_blacklist_contacts_cnt==""?"无":risk_social_network.sn_order1_blacklist_contacts_cnt)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">间接联系人在黑名单中数量(间接黑人)</td><td align="center">'+(risk_social_network.sn_order2_blacklist_contacts_cnt==""?"无":risk_social_network.sn_order2_blacklist_contacts_cnt)+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">认识间接黑人的直接联系人个数</td><td align="center">'+(risk_social_network.sn_order2_blacklist_routers_cnt==""?"无":risk_social_network.sn_order2_blacklist_routers_cnt)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">认识间接黑人的直接联系人比例</td><td align="center">'+(risk_social_network.sn_order2_blacklist_routers_pct==""?"无":risk_social_network.sn_order2_blacklist_routers_pct)+'</td>';
						trHtml_6 += "</tr><tr>";
						//黑名单
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">黑名单</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证是否命中黑名单</td><td align="center">'+(risk_blacklist.idcard_in_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机号是否命中黑名单</td><td align="center">'+(risk_blacklist.phone_in_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">是否命中法院黑名单</td><td align="center">'+(risk_blacklist.in_court_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">是否命中网贷黑名单</td><td align="center">'+(risk_blacklist.in_p2p_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">是否命中银行黑名单</td><td align="center">'+(risk_blacklist.in_bank_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近该身份证出现在黑名单中时间</td><td align="center">'+(risk_blacklist.last_appear_idcard_in_blacklist==""?"无":risk_blacklist.last_appear_idcard_in_blacklist)+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近该手机号出现在黑名单中时间</td><td align="center">'+(risk_blacklist.last_appear_phone_in_blacklist==""?"无":risk_blacklist.last_appear_phone_in_blacklist)+'</td>';
//						trHtml_6 += '<td class="DhbGetSauron_key">线上消费分期出现次数</td><td align="center">'+risk_blacklist.online_installment_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">历史机构类型</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">线下消费分期出现次数</td><td align="center">'+history_org.offline_installment_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">线上消费分期出现次数</td><td align="center">'+history_org.online_installment_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">小额快速贷出现次数</td><td align="center">'+history_org.payday_loan_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">信用卡代还出现次数</td><td align="center">'+history_org.credit_card_repayment_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">线下现金贷出现次数</td><td align="center">'+history_org.offline_cash_loan_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">线上现金贷出现次数</td><td align="center">'+history_org.online_cash_loan_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">其他</td><td align="center">'+history_org.others_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">历史查询</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">历史查询总次数</td><td align="center">'+history_search.search_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近7天查询次数</td><td align="center">'+history_search.search_cnt_recent_7_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近14天查询次数</td><td align="center">'+history_search.search_cnt_recent_14_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近30天查询次数</td><td align="center">'+history_search.search_cnt_recent_30_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近60天查询次数</td><td align="center">'+history_search.search_cnt_recent_60_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近90天查询次数</td><td align="center">'+history_search.search_cnt_recent_90_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近180天查询次数</td><td align="center">'+history_search.search_cnt_recent_180_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">历史查询总机构数</td><td align="center">'+history_search.org_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近7天查询机构数</td><td align="center">'+history_search.org_cnt_recent_7_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近14天查询机构数</td><td align="center">'+history_search.org_cnt_recent_14_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近30天查询机构数</td><td align="center">'+history_search.org_cnt_recent_30_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近60天查询机构数</td><td align="center">'+history_search.org_cnt_recent_60_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近90天查询机构数</td><td align="center">'+history_search.org_cnt_recent_90_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近180天查询机构数</td><td align="center">'+history_search.org_cnt_recent_180_days+'</td>';
						trHtml_6 += "</tr>";
						trHtml_6 += '</tbody>';
						$("#getDhbGetSauronCList").append(trHtml_6);
                        trHtml;
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
							$("#getDhbGetSauronC").append(trHtml);
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
								trHtml += '<td align="center">'+phones[i].other_phone+'</td>';
								trHtml += '<td align="center">'+phones[i].other_names_cnt+'</td>';
								trHtml += '<td align="center">'+phones[i].search_orgs_cnt+'</td>';
								trHtml += '<td align="center">'+phones[i].phone_operator+'</td>';
								trHtml += '<td align="center">'+phones[i].phone_province+'</td>';
								trHtml += '<td align="center">'+phones[i].phone_city+'</td>';
								trHtml += '<td align="center">'+phones[i].last_appear_phone+'</td>';
								trHtml += "</tr>";
							}
							trHtml+='</tbody>';
							trHtml+='</table>';
							trHtml+='</div>';
							$("#getDhbGetSauronC").append(trHtml);
						}
					}
				
				}else{//老数据
					idcards=arrStr6.binding_idcards;//绑定身份证情况
					phones=arrStr6.binding_phones;//绑定电话情况
					trHtml_6 = "";
					if(!arrStr6.age){
						$("#getDhbGetSauronCList").html("暂无数据");
					}else{
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
						trHtml_6 += '<td class="DhbGetSauron_key">身份证最近出现时间</td><td align="center">'+(arrStr6.last_appear_idcard==""?"无":arrStr6.last_appear_idcard)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机号最近出现时间</td><td align="center">'+(arrStr6.last_appear_phone==""?"无":arrStr6.last_appear_phone)+'</td>';
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
						$("#getDhbGetSauronCListContent").append(trHtml_6);
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
							trHtml+='</div>'
								$("#getDhbGetSauronC").append(trHtml);
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
								trHtml += '<td align="center">'+phones[i].other_phone+'</td>';
								trHtml += '<td align="center">'+phones[i].other_names_cnt+'</td>';
								trHtml += '<td align="center">'+phones[i].search_orgs_cnt+'</td>';
								trHtml += '<td align="center">'+phones[i].phone_operator+'</td>';
								trHtml += '<td align="center">'+phones[i].phone_province+'</td>';
								trHtml += '<td align="center">'+phones[i].phone_city+'</td>';
								trHtml += '<td align="center">'+phones[i].last_appear_phone+'</td>';
								trHtml += "</tr>";
							}
							trHtml+='</tbody>';
							trHtml+='</table>';
							trHtml+='</div>'
							$("#getDhbGetSauronC").append(trHtml);
						}
					}
				}
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
	//贷后邦-反欺诈 改为获取索伦数据
	var _getDhbGetSauronC=function(){
		var url = webPath+"/mfThirdServiceLog/getSauronDHBAjax?cusNo="+cusNo;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){ 
				if (data.flag == "success") {
					var arrStr6 = data.data.user_basic;//基础数据
					var risk_social_network = data.data.risk_social_network;//社交风险点
					var risk_blacklist = data.data.risk_blacklist;//黑名单
					var history_org = data.data.history_org;//历史机构类型
					var history_search = data.data.history_search;//历史查询
					var idcards=data.data.binding_idcards;//绑定身份证情况
					var phones=data.data.binding_phones;//绑定电话情况
					$("#getDhbGetSauronCList").html("");
					var trHtml_6 = "";
                    var trHtml,i;
					if(!arrStr6	|| !arrStr6.age){
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
						trHtml_6 += '<td class="DhbGetSauron_key">葫芦分</td><td align="center">'+(risk_social_network.sn_score==""?"无":risk_social_network.sn_score)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">直接联系人</td><td align="center">'+(risk_social_network.sn_order1_contacts_cnt==""?"无":risk_social_network.sn_order1_contacts_cnt)+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">直接联系人在黑名单中数量(直接黑人)</td><td align="center">'+(risk_social_network.sn_order1_blacklist_contacts_cnt==""?"无":risk_social_network.sn_order1_blacklist_contacts_cnt)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">间接联系人在黑名单中数量(间接黑人)</td><td align="center">'+(risk_social_network.sn_order2_blacklist_contacts_cnt==""?"无":risk_social_network.sn_order2_blacklist_contacts_cnt)+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">认识间接黑人的直接联系人个数</td><td align="center">'+(risk_social_network.sn_order2_blacklist_routers_cnt==""?"无":risk_social_network.sn_order2_blacklist_routers_cnt)+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">认识间接黑人的直接联系人比例</td><td align="center">'+(risk_social_network.sn_order2_blacklist_routers_pct==""?"无":risk_social_network.sn_order2_blacklist_routers_pct)+'</td>';
						trHtml_6 += "</tr><tr>";
						//黑名单
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">黑名单</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">身份证是否命中黑名单</td><td align="center">'+(risk_blacklist.idcard_in_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">手机号是否命中黑名单</td><td align="center">'+(risk_blacklist.phone_in_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">是否命中法院黑名单</td><td align="center">'+(risk_blacklist.in_court_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">是否命中网贷黑名单</td><td align="center">'+(risk_blacklist.in_p2p_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">是否命中银行黑名单</td><td align="center">'+(risk_blacklist.in_bank_blacklist==false?"否":"是")+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近该身份证出现在黑名单中时间</td><td align="center">'+(risk_blacklist.last_appear_idcard_in_blacklist==""?"无":risk_blacklist.last_appear_idcard_in_blacklist)+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近该手机号出现在黑名单中时间</td><td align="center">'+(risk_blacklist.last_appear_phone_in_blacklist==""?"无":risk_blacklist.last_appear_phone_in_blacklist)+'</td>';
//						trHtml_6 += '<td class="DhbGetSauron_key">线上消费分期出现次数</td><td align="center">'+risk_blacklist.online_installment_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">历史机构类型</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">线下消费分期出现次数</td><td align="center">'+history_org.offline_installment_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">线上消费分期出现次数</td><td align="center">'+history_org.online_installment_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">小额快速贷出现次数</td><td align="center">'+history_org.payday_loan_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">信用卡代还出现次数</td><td align="center">'+history_org.credit_card_repayment_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">线下现金贷出现次数</td><td align="center">'+history_org.offline_cash_loan_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">线上现金贷出现次数</td><td align="center">'+history_org.online_cash_loan_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">其他</td><td align="center">'+history_org.others_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">历史查询</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">历史查询总次数</td><td align="center">'+history_search.search_cnt+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近7天查询次数</td><td align="center">'+history_search.search_cnt_recent_7_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近14天查询次数</td><td align="center">'+history_search.search_cnt_recent_14_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近30天查询次数</td><td align="center">'+history_search.search_cnt_recent_30_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近60天查询次数</td><td align="center">'+history_search.search_cnt_recent_60_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近90天查询次数</td><td align="center">'+history_search.search_cnt_recent_90_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近180天查询次数</td><td align="center">'+history_search.search_cnt_recent_180_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">历史查询总机构数</td><td align="center">'+history_search.org_cnt+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近7天查询机构数</td><td align="center">'+history_search.org_cnt_recent_7_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近14天查询机构数</td><td align="center">'+history_search.org_cnt_recent_14_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近30天查询机构数</td><td align="center">'+history_search.org_cnt_recent_30_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近60天查询机构数</td><td align="center">'+history_search.org_cnt_recent_60_days+'</td>';
						trHtml_6 += "</tr><tr>";
						trHtml_6 += '<td class="DhbGetSauron_key">最近90天查询机构数</td><td align="center">'+history_search.org_cnt_recent_90_days+'</td>';
						trHtml_6 += '<td class="DhbGetSauron_key">最近180天查询机构数</td><td align="center">'+history_search.org_cnt_recent_180_days+'</td>';
						trHtml_6 += "</tr>";
						trHtml_6 += '</tbody>';
						$("#getDhbGetSauronCList").append(trHtml_6);
						if(idcards.length!=0){
							trHtml="";
							trHtml+='<div class="list-table margin_left_15 content">';
							trHtml+='<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">';
							trHtml+='<thead>';
							trHtml+='<tr>';
							trHtml+='<th scope="col" name="" align="center"></th>';
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
							$("#getDhbGetSauronC").append(trHtml);
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
								trHtml += '<td align="center">'+phones[i].other_phone+'</td>';
								trHtml += '<td align="center">'+phones[i].other_names_cnt+'</td>';
								trHtml += '<td align="center">'+phones[i].search_orgs_cnt+'</td>';
								trHtml += '<td align="center">'+phones[i].phone_operator+'</td>';
								trHtml += '<td align="center">'+phones[i].phone_province+'</td>';
								trHtml += '<td align="center">'+phones[i].phone_city+'</td>';
								trHtml += '<td align="center">'+phones[i].last_appear_phone+'</td>';
								trHtml += "</tr>";
							}
							trHtml+='</tbody>';
							trHtml+='</table>';
							trHtml+='</div>';
							$("#getDhbGetSauronC").append(trHtml);
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
	// 手动输入芝麻分        保存到第三方日志表中
	var _saveZhimaCreditScore=function(){
		if(appSts&&appSts=='4'){
			window.top.alert(top.getMessage("FAILED_SUBMIT_CONTENT","审批后芝麻分不允许操作！"), 0);
			return;
		}
		var zhimascore=$("#zhimascore").val();
		if(""==zhimascore){
			return;
		}
		var url = webPath+"/mfThirdServiceLog/saveZhimaCreditScoreAjax?cusNo="+cusNo+"&zhimascore="+zhimascore;
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){ 
				if (data.flag == "success") {
					$("#zhimascore").val(data.zhimascore);
					window.top.alert(top.getMessage("SUCCEED_SAVE"), 1);
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
		getDhbGetSauronC:_getDhbGetSauronC,
		saveZhimaCreditScore:_saveZhimaCreditScore
	};
}(window, jQuery);