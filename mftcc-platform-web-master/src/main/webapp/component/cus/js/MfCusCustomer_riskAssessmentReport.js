
$(function(){
	$("table").addClass('ls_list');
	$("body").show();
	$.ajax({
		url:webPath+"/tcReport/getRiskAssessmentReportAjax?cusNo="+cusNo,
		type:'post',
		dataType:'json',
		success:function(data){ 
			if(data.flag=="success"){
				if(data.riskAssessmentTcreditMap){
					var riskMap = data.riskAssessmentTcreditMap;
					var i =0;
					var htmlString = "";
					htmlString +='<tr>';
					htmlString +='<td class="DhbGetSauron_key">身份证号</td><td align="center">'+riskMap.inputs.idcard+'</td>';
					htmlString +='<td class="DhbGetSauron_key">手机号</td><td align="center">'+riskMap.inputs.mobile+'</td>';
					htmlString +='</tr>';
					htmlString +='<tr>';
					htmlString +='<td class="DhbGetSauron_key">第一联系人号码</td><td align="center">'+(riskMap.inputs.contacts1_num?riskMap.inputs.contacts1_num:'无')+'</td>';
					htmlString +='<td class="DhbGetSauron_key">第一联系人号码</td><td align="center">'+(riskMap.inputs.contacts2_num?riskMap.inputs.contacts2_num:'无')+'</td>';
					htmlString +='</tr>';
					for(e in riskMap){
						if(e!='inputs'){
							
							if( i % 2 ==0){
								htmlString += "<tr>";
							}
							switch(e){
								case "occupation_result":
										htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
										if(riskMap[e].value=='yes'){
											htmlString+='<td align="center">属于⾼风险职业</td>';
										}else if(riskMap[e].value=='no'){
											htmlString+='<td align="center">不属于⾼风险职业</td>';
										}else{
											htmlString+='<td align="center">无</td>';
										}
										break;
								case "used_phone":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center">常⽤⼿机号</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">⾮常⽤⼿机号</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "verify_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center">信息验证通过</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">信息验证未通过</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "dishonest_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center">命中失信名单</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">未命中失信名单</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "distract_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center">属于⾼风险地区</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">不属于⾼风险地区</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "social_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center">交往圈异常</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">交往圈正常</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "fake_contact":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center">虚假联系⼈</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">⾮虚假联系⼈</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "blackhit_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center">命中⿊名单</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">未命中⿊名单</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "af_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center">未通过反欺诈</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">通过反欺诈</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "door_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center"> 准⼊通过</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">准⼊不通过</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "final_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='accept'){
										htmlString+='<td align="center">建议通过</td>';
									}else if(riskMap[e].value=='reject'){
										htmlString+='<td align="center">建议拒绝</td>';
									}else if(riskMap[e].value=='cancel'){
										htmlString+='<td align="center">数据异常</td>';
									}else if(riskMap[e].value=='review'){
										htmlString+='<td align="center">建议⼈⼯信审</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								case "score_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									htmlString+='<td align="center">'+riskMap[e].value+'</td>';
									break;
								case "mult_appl_result":
									htmlString+='<td class="DhbGetSauron_key">'+riskMap[e].nameCn+'</td>';
									if(riskMap[e].value=='yes'){
										htmlString+='<td align="center"> 命中多重申请</td>';
									}else if(riskMap[e].value=='no'){
										htmlString+='<td align="center">未命中多重申请</td>';
									}else{
										htmlString+='<td align="center">无</td>';
									}
									break;
								default:break;
							}
							
							if( i % 2 ==1){
								htmlString += "</tr>";
							}
							i++;
						}
					}
					
					$("#riskAssessmentTcreditContent").append(htmlString);
				}else{
					$("#riskAssessmentTcreditContent").append("暂无数据");
				}
			}
		},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	
	myCustomScrollbarForForm({
		obj:".report_content",
		advanced : {
			theme : "minimal-dark",
			updateOnContentResize : true
		}
	});
 });

//个人基本信息
function setMfCusPersBaseInfoHtml(mfCusPersBaseInfo,mfCusPersonJop){
	var trHtml="";
	trHtml +='<tr>';
	trHtml += ''+mfCusPersBaseInfo.cusName+'</td>';
	trHtml += '<td class="DhbGetSauron_key">客户性别</td><td align="center">'+mfCusPersBaseInfo.sex+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">年龄</td><td align="center">'+(mfCusPersBaseInfo.remark?mfCusPersBaseInfo.remark:"未登记")+'</td>';
	trHtml += '<td class="DhbGetSauron_key">出生日期</td><td align="center">'+(mfCusPersBaseInfo.brithday?mfCusPersBaseInfo.brithday:"未登记")+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">学历</td><td align="center">'+(mfCusPersBaseInfo.education?mfCusPersBaseInfo.education:"未登记")+'</td>';
	trHtml += '<td class="DhbGetSauron_key">籍贯</td><td align="center">'+(mfCusPersBaseInfo.regHomeAddre?mfCusPersBaseInfo.regHomeAddre:"未登记")+'</td>';
	
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">婚姻状况</td><td align="center">'+(mfCusPersBaseInfo.marrige?mfCusPersBaseInfo.marrige:"未登记")+'</td>';
	trHtml += '<td class="DhbGetSauron_key">手机号码</td><td align="center">'+(mfCusPersBaseInfo.cusTel?mfCusPersBaseInfo.cusTel:"未登记")+'</td>';
	trHtml += "</tr><tr>";
	if(mfCusPersonJop==null){
		trHtml += '<td class="DhbGetSauron_key">职业</td><td align="center">未登记</td>';
		trHtml += '<td class="DhbGetSauron_key">工作年限</td><td align="center">未登记</td>';
		trHtml += "</tr><tr>";
		trHtml += '<td class="DhbGetSauron_key">单位电话</td><td align="center">未登记</td>';
		trHtml += '<td class="DhbGetSauron_key">单位名称</td><td align="center">未登记</td>';
		trHtml += "</tr><tr>";
		trHtml += '<td class="DhbGetSauron_key">单位规模</td><td align="center">未登记</td>';
		trHtml += '<td class="DhbGetSauron_key">单位地址</td><td align="center">未登记</td>';
		trHtml += "</tr><tr>";
	}else{
		trHtml += '<td class="DhbGetSauron_key">职业</td><td align="center">'+(mfCusPersonJop.workKind?mfCusPersonJop.workKind:"未登记")+'</td>';
		trHtml += '<td class="DhbGetSauron_key">工作年限</td><td align="center">'+(mfCusPersonJop.begYearCorp?mfCusPersonJop.begYearCorp:"未登记")+'</td>';
		trHtml += "</tr><tr>";
		trHtml += '<td class="DhbGetSauron_key">单位电话</td><td align="center">'+(mfCusPersonJop.telephone?mfCusPersonJop.telephone:"未登记")+'</td>';
		trHtml += '<td class="DhbGetSauron_key">单位名称</td><td align="center">'+(mfCusPersonJop.workUnit?mfCusPersonJop.workUnit:"未登记")+'</td>';
		trHtml += "</tr><tr>";
		trHtml += '<td class="DhbGetSauron_key">单位规模</td><td align="center">'+(mfCusPersonJop.companyScale?mfCusPersonJop.companyScale:"未登记")+'</td>';
		trHtml += '<td class="DhbGetSauron_key">单位地址</td><td align="center">'+(mfCusPersonJop.address?mfCusPersonJop.address:"未登记")+'</td>';
		trHtml += "</tr><tr>";
	}
	trHtml += '<td class="DhbGetSauron_key">居住地址</td><td align="center">'+(mfCusPersBaseInfo.commAddress?mfCusPersBaseInfo.commAddress:"未登记")+'</td>';
	trHtml += "</tr><tr>";
	$("#personInfoContent").append(trHtml);
}
//资产信息
function setMfCusPersonAssetsInfoHtml(mfCusPersonAssetsInfoList){
	var trHtml="";
	for(var i =0;i<mfCusPersonAssetsInfoList.length;i++){
		if(mfCusPersonAssetsInfoList[i].assetsType==1){//汽车
			trHtml += "<table class='ls_list' width='100%' cellspacing='1' border='0' align='center'>";
			trHtml += "<tbody>";
			trHtml +='<tr>';
			trHtml += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">汽车</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">所有权人</td><td align="center">'+(mfCusPersonAssetsInfoList[i].cusName?mfCusPersonAssetsInfoList[i].cusName:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">资产名称</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsName?mfCusPersonAssetsInfoList[i].assetsName:"未登记")+'</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">车辆型号</td><td align="center">'+(mfCusPersonAssetsInfoList[i].vehicleBrand?mfCusPersonAssetsInfoList[i].vehicleBrand:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">发动机号</td><td align="center">'+(mfCusPersonAssetsInfoList[i].engineNum?mfCusPersonAssetsInfoList[i].engineNum:"未登记")+'</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">行驶里程</td><td align="center">'+(mfCusPersonAssetsInfoList[i].travlledDistance?mfCusPersonAssetsInfoList[i].travlledDistance:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">车牌号</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsNo?mfCusPersonAssetsInfoList[i].assetsNo:"未登记")+'</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">资产归属地</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsPlace?mfCusPersonAssetsInfoList[i].assetsPlace:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">排量</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsArea?mfCusPersonAssetsInfoList[i].assetsArea:"未登记")+'</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">市值</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsValue?mfCusPersonAssetsInfoList[i].assetsValue:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">购置金额</td><td align="center">'+(mfCusPersonAssetsInfoList[i].buyValue?mfCusPersonAssetsInfoList[i].buyValue:"未登记")+'</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">购置日期</td><td align="center">'+(mfCusPersonAssetsInfoList[i].buyDate?mfCusPersonAssetsInfoList[i].buyDate:"未登记")+'</td>';
			if(mfCusPersonAssetsInfoList[i].isMortgage=="Y"){
				trHtml += '<td class="DhbGetSauron_key">是否按揭</td><td align="center">是</td>';
				trHtml += "</tr><tr>";
				trHtml += '<td class="DhbGetSauron_key">按揭贷款余额</td><td align="center">'+(mfCusPersonAssetsInfoList[i].loanBalance?mfCusPersonAssetsInfoList[i].loanBalance:"未登记")+'</td>';
				trHtml += '<td class="DhbGetSauron_key">按揭剩余期限</td><td align="center">'+(mfCusPersonAssetsInfoList[i].loanPeriod?mfCusPersonAssetsInfoList[i].loanPeriod:"未登记")+'</td>';
			}else{
				trHtml += '<td class="DhbGetSauron_key">是否按揭</td><td align="center">否</td>';
			}
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">备注</td><td align="center">'+(mfCusPersonAssetsInfoList[i].remark?mfCusPersonAssetsInfoList[i].remark:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
			trHtml += "</tr><tr>";
			trHtml += "</tbody>";
			trHtml += "</table>";
		}else if(mfCusPersonAssetsInfoList[i].assetsType==2){
			trHtml += "<table class='ls_list' width='100%' cellspacing='1' border='0' align='center'>";
			trHtml += "<tbody>";
			trHtml +='<tr>';
			trHtml += '<td align="center" colspan="4" style="color:rgb(51, 102, 204)">房屋</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">所有权人</td><td align="center">'+(mfCusPersonAssetsInfoList[i].cusName?mfCusPersonAssetsInfoList[i].cusName:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">资产名称</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsName?mfCusPersonAssetsInfoList[i].assetsName:"未登记")+'</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">房产类型</td><td align="center">'+(mfCusPersonAssetsInfoList[i].houseType?mfCusPersonAssetsInfoList[i].houseType:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">房产证号</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsNo?mfCusPersonAssetsInfoList[i].assetsNo:"未登记")+'</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">资产归属地</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsPlace?mfCusPersonAssetsInfoList[i].assetsPlace:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">排量</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsArea?mfCusPersonAssetsInfoList[i].assetsArea:"未登记")+'</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">市值</td><td align="center">'+(mfCusPersonAssetsInfoList[i].assetsValue?mfCusPersonAssetsInfoList[i].assetsValue:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">购置金额</td><td align="center">'+(mfCusPersonAssetsInfoList[i].buyValue?mfCusPersonAssetsInfoList[i].buyValue:"未登记")+'</td>';
			trHtml += "</tr><tr>";
			trHtml += '<td class="DhbGetSauron_key">购置日期</td><td align="center">'+(mfCusPersonAssetsInfoList[i].buyDate?mfCusPersonAssetsInfoList[i].buyDate:"未登记")+'</td>';
			trHtml += '<td class="DhbGetSauron_key">房产所在地邮编</td><td align="center">'+(mfCusPersonAssetsInfoList[i].postalCode?mfCusPersonAssetsInfoList[i].postalCode:"未登记")+'</td>';
			if(mfCusPersonAssetsInfoList[i].isMortgage=="Y"){
				trHtml += "</tr><tr>";
				trHtml += '<td class="DhbGetSauron_key">是否按揭</td><td align="center">是</td>';
				trHtml += '<td class="DhbGetSauron_key">按揭贷款余额</td><td align="center">'+(mfCusPersonAssetsInfoList[i].loanBalance?mfCusPersonAssetsInfoList[i].loanBalance:"未登记")+'</td>';
				trHtml += "</tr><tr>";
				trHtml += '<td class="DhbGetSauron_key">按揭剩余期限</td><td align="center">'+(mfCusPersonAssetsInfoList[i].loanPeriod?mfCusPersonAssetsInfoList[i].loanPeriod:"未登记")+'</td>';
				trHtml += '<td class="DhbGetSauron_key">备注</td><td align="center">'+(mfCusPersonAssetsInfoList[i].remark?mfCusPersonAssetsInfoList[i].remark:"未登记")+'</td>';
			}else{
				trHtml += "</tr><tr>";
				trHtml += '<td class="DhbGetSauron_key">是否按揭</td><td align="center">否</td>';
				trHtml += '<td class="DhbGetSauron_key">备注</td><td align="center">'+(mfCusPersonAssetsInfoList[i].remark?mfCusPersonAssetsInfoList[i].remark:"未登记")+'</td>';
			}
			trHtml += "</tr><tr>";
			trHtml += "</tbody>";
			trHtml += "</table>";
		}
	}
	$("#mfCusPersonAssetsInfoList").append(trHtml);
}
//运营商
function setOperatorMapHtml(operatorMap){
	var trHtml="";
	trHtml +='<tr>';
	trHtml += '<td class="DhbGetSauron_key">归属城市</td><td align="center">'+(operatorMap.userCity?operatorMap.userCity:"无")+'</td>';
	trHtml += '<td class="DhbGetSauron_key">累计消费总金额</td><td align="center">'+(operatorMap.callingFeeSum?operatorMap.callingFeeSum:"无")+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">最近一次通话距今时长(小时)</td><td align="center">'+(operatorMap.minDateTime?operatorMap.minDateTime:"无")+'</td>';
	trHtml += '<td class="DhbGetSauron_key">最远一次通话距今时长(天)</td><td align="center">'+(operatorMap.maxTimeTime?operatorMap.maxTimeTime:"无")+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">当月消费金额</td><td align="center">'+operatorMap.callingFeeSum1+'</td>';
	trHtml += '<td class="DhbGetSauron_key">最近3月消费金额</td><td align="center">'+operatorMap.callingFeeSum3+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">最近6个月消费金额</td><td align="center">'+operatorMap.callingFeeSum6+'</td>';
	trHtml += '<td class="DhbGetSauron_key">最近1天主叫通话地点和通话次数（由多到少）</td><td align="center">'+operatorMap.callCount1+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">最近15天内通话次数</td><td align="center">'+operatorMap.callCount15+'</td>';
	trHtml += '<td class="DhbGetSauron_key">最近30天内通话次数</td><td align="center">'+operatorMap.callCount30+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">最近3个月内通话次数</td><td align="center">'+operatorMap.callCountMonth3+'</td>';
	trHtml += '<td class="DhbGetSauron_key">最近6个月内通话次数</td><td align="center">'+operatorMap.callCountMonth6+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">最近1天内上网不同地点和上网次数（由多到少）</td><td align="center">无</td>';
	trHtml += '<td class="DhbGetSauron_key">最近15天内上网不同地点次数</td><td align="center">无</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">最近30天内上网不同地点次数</td><td align="center">无</td>';
	trHtml += '<td class="DhbGetSauron_key">最近3个月内上网不同地点次数</td><td align="center">无</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">最近6个月内上网不同地点次数</td><td align="center">无</td>';
	trHtml += '<td class="DhbGetSauron_key">最近15天内与联系人1和联系人2通话的次数</td><td align="center">'+operatorMap.callCountRel1AndRel1+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">最近30天内通话的对方号码不同次数</td><td align="center">'+operatorMap.callCountRel1AndRel2+'</td>';
	trHtml += '<td class="DhbGetSauron_key">最近3个月内通话的对方号码不同次数</td><td align="center">'+operatorMap.callCountRel1AndRel3+'</td>';
	trHtml += "</tr><tr>";
	trHtml += '<td class="DhbGetSauron_key">最近6个月内通话的对方号码不同次数</td><td align="center">'+operatorMap.callCountRel1AndRel4+'</td>';
	trHtml += "</tr><tr>";
	$("#operatorInfoContent").append(trHtml);
};
//公积金
function setGongjijinHtml(baseInfo,billInfos){
	var trHtml_g="";
	trHtml_g +='<tr>';
	trHtml_g += '<td class="DhbGetSauron_key">出生日期</td><td align="center">'+(baseInfo.birthday?baseInfo.birthday:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">姓名</td><td align="center">'+(baseInfo.employeeName?baseInfo.employeeName:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">公积金当前状态／当前账户状态</td><td align="center">'+(baseInfo.houseFundStatus?baseInfo.houseFundStatus:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">月缴金额／月缴存额</td><td align="center">'+(baseInfo.personalMonthPayAmount?baseInfo.personalMonthPayAmount:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">本年补缴</td><td align="center">'+(baseInfo.inYearAddPayAmount?baseInfo.inYearAddPayAmount:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">性别</td><td align="center">'+(baseInfo.gender?baseInfo.gender:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">本年利息</td><td align="center">'+(baseInfo.inYearInterest?baseInfo.inYearInterest:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">本年转入</td><td align="center">'+(baseInfo.inYearTranerin?baseInfo.inYearTranerin:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">本年支取</td><td align="center">'+(baseInfo.inYearReceiveAmount?baseInfo.inYearReceiveAmount:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">开户日期</td><td align="center">'+(baseInfo.openAccountDate?baseInfo.openAccountDate:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">身份证号</td><td align="center">'+(baseInfo.idCard?baseInfo.idCard:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">缴至年月/末次缴存年月</td><td align="center">'+(baseInfo.payUntilDate?baseInfo.payUntilDate:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">公司名称</td><td align="center">'+(baseInfo.companyName?baseInfo.companyName:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">证件类型</td><td align="center">'+(baseInfo.cardType?baseInfo.cardType:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">上年余额</td><td align="center">'+(baseInfo.lastYearBalance?baseInfo.lastYearBalance:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">上年利息</td><td align="center">'+(baseInfo.lastYearInterest?baseInfo.lastYearInterest:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">本年缴交</td><td align="center">'+(baseInfo.inYearPayAmount?baseInfo.inYearPayAmount:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">月缴金额／月缴存额</td><td align="center">'+(baseInfo.monthPayAmount?baseInfo.monthPayAmount:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">绑定手机号</td><td align="center">'+(baseInfo.bindMobile?baseInfo.bindMobile:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">单位月缴额</td><td align="center">'+(baseInfo.companyMonthPayAmount?baseInfo.companyMonthPayAmount:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">月缴基数</td><td align="center">'+(baseInfo.monthPayBaseAmount?baseInfo.monthPayBaseAmount:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">职工账号</td><td align="center">'+(baseInfo.employeeAccountNum?baseInfo.employeeAccountNum:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">公积金余额</td><td align="center">'+(baseInfo.houseFundBalanc?baseInfo.houseFundBalanc:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">银行账号/关联银行卡号</td><td align="center">'+(baseInfo.bankAccount?baseInfo.bankAccount:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">缴存银行</td><td align="center">'+(baseInfo.bankName?baseInfo.bankName:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">单位账号</td><td align="center">'+(baseInfo.companyAccountNum?baseInfo.companyAccountNum:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">缴存管理部</td><td align="center">'+(baseInfo.depositManageDept?baseInfo.depositManageDept:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">所属办事处</td><td align="center">'+(baseInfo.belongOffice?baseInfo.belongOffice:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">缴存比例-个人</td><td align="center">'+(baseInfo.depositRatioPersonal?baseInfo.depositRatioPersonal:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">缴存比例-单位</td><td align="center">'+(baseInfo.depositRatioCompany?baseInfo.depositRatioCompany:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">实名认证信息/实名认证状态</td><td align="center">'+(baseInfo.nameAuth?baseInfo.nameAuth:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">绑定邮箱</td><td align="center">'+(baseInfo.bindEmail?baseInfo.bindEmail:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">公积金账号</td><td align="center">'+(baseInfo.houseFundAccount?baseInfo.houseFundAccount:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">通讯地址</td><td align="center">'+(baseInfo.homeAddress?baseInfo.homeAddress:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	$("#reservedFundInfoContent").append(trHtml_g);
	if(billInfos&&!billInfos.length==0){
		var trHtml="";
		trHtml+='<div class="list-table margin_left_15 content">';
		trHtml+='<table class="ls_list" width="100%" cellspacing="1" border="0" align="center">';
		trHtml+='<thead>';
		trHtml+='<tr>';
		trHtml+='<th scope="col" name="" align="center">业务描述</th>';
		trHtml+='<th scope="col" name="" align="center">缴纳基数</th>';
		trHtml+='<th scope="col" name="" align="center">余额</th>';
		trHtml+='<th scope="col" name="" align="center">入账时间</th>';
		trHtml+='<th scope="col" name="" align="center">转入余额/贷方金额</th>';
		trHtml+='<th scope="col" name="" align="center">缴存年月/所属月份</th>';
		trHtml+='<th scope="col" name="" align="center">转出余额/借方金额</th>';
		trHtml+='</tr>';
		trHtml+='</thead>';
		trHtml+='<tbody>';
		for(var i =0;i<billInfos.length;i++){
			trHtml +='<tr>';
			trHtml += '<td align="center">'+billInfos[i].note+'</td>';
			trHtml += '<td align="center">'+billInfos[i].payBase+'</td>';
			trHtml += '<td align="center">'+billInfos[i].balance+'</td>';
			trHtml += '<td align="center">'+billInfos[i].payArriveDate+'</td>';
			trHtml += '<td align="center">'+billInfos[i].inAmount+'</td>';
			trHtml += '<td align="center">'+billInfos[i].payMonth+'</td>';
			trHtml += '<td align="center">'+billInfos[i].outAmount+'</td>';
			trHtml += "</tr>";
		}
		trHtml+='</tbody>';
		trHtml+='</table>';
		trHtml+='</div>';
		$("#reservedFundInfo").append(trHtml);
	}
}
//银联交易变量及评分
function setunionpayTransactionHtml(data){
	var trHtml_g="";
	trHtml_g +='<tr>';
	trHtml_g += '<td class="DhbGetSauron_key">最近30天消费次数</td><td align="center">'+(data.CDTB045?data.CDTB045:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">最近3个月消费次数</td><td align="center">'+(data.CDTB017?data.CDTB017:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">最近6个月消费次数</td><td align="center">'+(data.CDTB046?data.CDTB046:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">最近1年消费次数</td><td align="center">'+(data.CDTB047?data.CDTB047:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">最近一次消费距今时长（天）</td><td align="center">'+(data.CDTB143?data.CDTB143:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">最早一次消费距今时长（天）</td><td align="center">'+(data.CDTB259?data.CDTB259:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">最近30天消费金额</td><td align="center">'+(data.CDTB031?data.CDTB031:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">最近3个月消费金额</td><td align="center">'+(data.CDTB002?data.CDTB002:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">最近6个月消费金额</td><td align="center">'+(data.CDTB032?data.CDTB032:"无")+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">最近1年消费金额</td><td align="center">'+(data.CDTB033?data.CDTB033:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">第一常用城市</td><td align="center">'+((data.CDTB268==undefined||data.CDTB268=="\"null\"")?"无":data.CDTB268)+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">第一常用城市总金额</td><td align="center">'+(data.CDTB263?data.CDTB263:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">第二常用城市</td><td align="center">'+((data.CDTB269==undefined||data.CDTB269=="\"null\"")?"无":data.CDTB269)+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">第二常用城市总金额</td><td align="center">'+(data.CDTB265?data.CDTB265:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">第三常用城市</td><td align="center">'+((data.CDTB270==undefined||data.CDTB270=="\"null\"")?"无":data.CDTB270)+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">第三常用城市总金额</td><td align="center">'+(data.CDTB267?data.CDTB267:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">近3个月交易金额排名第一的城市</td><td align="center">'+((data.CDTB275==undefined||data.CDTB275=="\"null\"")?"无":data.CDTB275)+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">近3个月交易金额排名第二的城市</td><td align="center">'+((data.CDTB280==undefined||data.CDTB280=="\"null\"")?"无":data.CDTB280)+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">近3个月交易金额排名第三的城市</td><td align="center">'+((data.CDTB285==undefined||data.CDTB285=="\"null\"")?"无":data.CDTB285)+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">近6个月交易城市数</td><td align="center">'+(data.CDTB260?data.CDTB260:"无")+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">近6 个月交易金额排名第一的城市</td><td align="center">'+((data.CDTB272==undefined||data.CDTB272=="\"null\"")?"无":data.CDTB272)+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">近6 个月交易金额排名第二的城市</td><td align="center">'+((data.CDTB277==undefined||data.CDTB277=="\"null\"")?"无":data.CDTB277)+'</td>';
	trHtml_g += "</tr><tr>";
	trHtml_g += '<td class="DhbGetSauron_key">近6 个月交易金额排名第三的城市</td><td align="center">'+((data.CDTB282==undefined||data.CDTB282=="\"null\"")?"无":data.CDTB282)+'</td>';
	trHtml_g += '<td class="DhbGetSauron_key">最近1次消费地点</td><td align="center">'+((data.CDTB153==undefined||data.CDTB153=="\"null\"")?"无":data.CDTB153)+'</td>';
	trHtml_g += "</tr><tr>";
	$("#unionpayTransactionContent").append(trHtml_g);
}
//风云决
function setfenyunjueHtml(data){
	var trHtml_g="";
		trHtml_g +='<tr>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">第二联系人是否在通话列表中</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.IsSecondContactInListResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">一个身份证号码在不同设备申请次数</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countRegistIdcardDifferentDeviceResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近30天内有互通电话联系人数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countExchangedLast30DayResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">同一公司名称30天内借款人数结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countUserOneCompany30DayResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">公积金状态验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.gJJStatusResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近180天内通话次数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countLast180DayResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">现金贷评分可变值</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.scoreRangeResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近90天内主叫次数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.callingCountLast90DayResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近30天内通话次数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countLast30DayResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">过去第2个月消费类交易笔数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countTradeLast2MonResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近三个月平均话费消费金额验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.avgAmountNearly3MonthResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">输入借款人信用卡号校验错误次数结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countErrorInputCreditCardResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近30天内有效语音通话（通话时长>15秒）联系人数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countMoreThan15sLast30DayResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近90天内有互通电话联系人数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countExchangedLast90DayResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">当日同一居住地址申请人数结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countUserOneLocOndDayResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">与联系人1近5个月单月通话次数最小值规则</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.minCountLast5MonthOfFistContactResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">天创黑名单规则</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.tcreditBlackListResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">注册到申请的时间验证</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.timeForRegisterToAppResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">呼出电话次数占所有通话数量的比例验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.rateOfCallingToAllCallsResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">上月话费消费金额验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.amountLastMonthResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近六个月平均话费消费金额验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.avgAmountNearly6MonthResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近6个月消费类交易笔数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countTradeLast6MonResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">同盾黑名单验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.isInTongDunBlackListResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">输入借款人身份证号码校验错误次数结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countErrorInputIdCardResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">24小时内同个设备同时登陆不同银行卡号个数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countLoginDeviceDifferentBankCard24HResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">同一公司名称当日借款人数结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countUserOneCompanyOneDayResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">现金贷评分</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.creditScore)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">芝麻信用行业关注黑名单验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.isInAliWatchListResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">是否为禁止准入的行业验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.IsInForbidIndustryResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近90天内有效语音通话（通话时长>15秒）联系人数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countMoreThan15sLast90DayResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">手机号是否为虚拟运营商号段验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.phoneNumberHeadResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近30天内出现无通话天数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.noRecordCountLast30DayResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">公积金最近6个月缴存月份数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.gjjCountMonthLast6MonthResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">近180天内通话总时长验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.totalTimeLast180DayResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">公积金实名验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.GjjOwnerValidateResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">手机号在网时长规则验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.mobileOnlineTimeResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">申请前3天内的通话地点是否在（3天前到180天内的通话地点列表）之中验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.isLocOfLast3DayIn3To180Result)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">1小时内同一账号是否在不同城市登录验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.isLoginDifferentCity24HResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">现金贷授信额度</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.creditQuota)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">第一联系人是否在通话列表中验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.isFirstContactInListResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">百度金融黑名单验证规则</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.IsInBaiDuJRBlackListResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">芝麻信用欺诈信息黑名单验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.isInAliQZListResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">是否为高风险地区验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.InHighRiskLocResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">身份证是否过期</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.IsExpiredOfIdCardResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">与联系人2近5个月单月通话次数最小值是否为0</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.minCountLast5MonthOfSecondContactResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">公积金月缴存金额验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.gjjMonthPayAmountResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">24小时内同个设备同时登陆不同身份证号码个数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countLoginDeviceDifferentIdCard24HResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">最近30天同一居住地址申请人数结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countUserOneLoc30DayResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">一个注册手机号在不同设备申请次数结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countRegistMobileDifferentDeviceResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">输入借款人借记卡号校验错误次数结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countErrorInputBankCardResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">totalTimeLast30Day验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.totalTimeLast30DayResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">24小时内同个设备同时登陆不同手机号个数验证结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.countLoginDeviceDifferentMobile24HResult)+'</span></td>';
		trHtml_g += "</tr><tr>";
		trHtml_g += '<td class="DhbGetSauron_key wx_report_left">年龄段规则结果</td><td align="center "><span class="wx_report_right">'+getFengyunjueItem(data.ageResult)+'</span></td>';
		trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
//		trHtml_g += '<td class="DhbGetSauron_key">现金贷评分模型输出值</td><td align="center">'+data.modelOut.value+'</td>';
		trHtml_g += "</tr>";
	$("#fengyunjueContent").append(trHtml_g);
}
function getFengyunjueItem(obj){
	if(obj  && !isNaN(obj.value)){
		return obj.value;
	}
	return obj?obj.value?obj.value:'无':'无';
}
//黑名单
function setHeimingdanHtml(data){
	var trHtml_g="";
	detail=data.detail;
	if(detail){
		if(detail.blackLevel){
			trHtml_g +='<tr>';
			trHtml_g += '<td class="DhbGetSauron_key">黑名单等级</td><td align="center">'+detail.blackLevel+'</td>';
			trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
			trHtml_g += "</tr><tr>";
		}
		if(detail.blackReason){
			var blackReason=detail.blackReason.split(":");
            var j;
			for(var i =0;i<blackReason.length;i++){
				if(blackReason[i]=="C06BT020"){
					trHtml_g += '<td class="DhbGetSauron_key">黑名单原因</td><td align="center">法院失信</td>';
					trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
					trHtml_g += "</tr>";
					 var obj = JSON.parse(detail.blackDetails);
					if(obj.C06BT020){
						var C06BT020=obj.C06BT020;
						for(j =0;j<C06BT020.length;j++){
							trHtml_g += '<tr>';
							trHtml_g += '<td class="DhbGetSauron_key">执行义务</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td align="center" colspan="4">'+(C06BT020[j].duty?C06BT020[j].duty:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">公开日期</td><td align="center">'+(C06BT020[j].publishDate?C06BT020[j].publishDate:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">案件编号</td><td align="center">'+(C06BT020[j].case_no?C06BT020[j].case_no:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">公开日期</td><td align="center">'+(C06BT020[j].disruptTypeName?C06BT020[j].disruptTypeName:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
							trHtml_g += "</tr><tr>";
						}
					}
				}else if(blackReason[i]=="C06BT021"){
					trHtml_g += '<td class="DhbGetSauron_key">黑名单原因</td><td align="center">偷税漏税</td>';
					trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
					trHtml_g += "</tr><tr>";
					if(obj.C06BT021){
						var C06BT021=obj.C06BT021;
						for(j =0;j<C06BT021.length;j++){
							trHtml_g += '<td class="DhbGetSauron_key">税务主体</td><td align="center">'+(C06BT021[j].taxpayer_name?C06BT021[j].taxpayer_name:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">纳税识别号</td><td align="center">'+(C06BT021[j].taxpayer_iden_num?C06BT021[j].taxpayer_iden_num:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">公开机构</td><td align="center">'+(C06BT021[j].info_source?C06BT021[j].info_source:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">主要原因</td><td align="center">'+(C06BT021[j].major_fact?C06BT021[j].major_fact:"无")+'</td>';
							trHtml_g += "</tr><tr>";
						}
					}
				}else if(blackReason[i]=="C06BT022"){
					trHtml_g += '<td class="DhbGetSauron_key">黑名单原因</td><td align="center">股权冻结</td>';
					trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
					trHtml_g += "</tr><tr>";
					if(obj.C06BT022){
						var C06BT022=obj.C06BT022;
						for(j =0;j<C06BT022.length;j++){
							trHtml_g += '<td class="DhbGetSauron_key">执行法院</td><td align="center">'+(C06BT022[j].execute_court?C06BT022[j].execute_court:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">处罚编号</td><td align="center">'+(C06BT022[j].adjudicate_no?C06BT022[j].adjudicate_no:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">冻结金额</td><td align="center">'+(C06BT022[j].freeze_amount?C06BT022[j].freeze_amount:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">处罚公开时间</td><td align="center">'+(C06BT022[j].pub_date?C06BT022[j].pub_date:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">合作企业名称</td><td align="center">'+(C06BT022[j].corp_name?C06BT022[j].corp_name:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
							trHtml_g += "</tr><tr>";
						}
					}
				}else if(blackReason[i]=="C06BT023"){
					trHtml_g += '<td class="DhbGetSauron_key">黑名单原因</td><td align="center">股权冻结</td>';
					trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
					trHtml_g += "</tr><tr>";
					if(obj.C06BT023){
						var C06BT023=obj.C06BT023;
						for(j =0;j<C06BT023.length;j++){
							trHtml_g += '<td class="DhbGetSauron_key">处罚编号</td><td align="center">'+(C06BT023[j].punishment_no?C06BT023[j].punishment_no:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">执行原因</td><td align="center">'+(C06BT023[j].cause_of_action?C06BT023[j].cause_of_action:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">处罚部门</td><td align="center">'+(C06BT023[j].handle_org_name?C06BT023[j].handle_org_name:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">公开日期</td><td align="center">'+(C06BT023[j].pub_date?C06BT023[j].pub_date:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">处罚内容</td><td align="center">'+(C06BT023[j].punishment_content?C06BT023[j].punishment_content:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
							trHtml_g += "</tr><tr>";
						}
					}
				}else if(blackReason[i]=="C06BT024"){
					trHtml_g += '<td class="DhbGetSauron_key">黑名单原因</td><td align="center">法院执行人</td>';
					trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
					trHtml_g += "</tr><tr>";
					if(obj.C06BT024){
						var C06BT024=obj.C06BT024;
						for(j =0;j<C06BT024.length;j++){
							trHtml_g += '<td class="DhbGetSauron_key">注册时间</td><td align="center">'+(C06BT024[j].reg_date?C06BT024[j].reg_date:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">案件编号</td><td align="center">'+(C06BT024[j].cause_no?C06BT024[j].cause_no:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">判决法院</td><td align="center">'+(C06BT024[j].court?C06BT024[j].court:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">执行金额</td><td align="center">'+(C06BT024[j].exec_money?C06BT024[j].exec_money:"无")+'</td>';
							trHtml_g += "</tr><tr>";
						}
					}
				}else if(blackReason[i]=="C06BT025"){
					trHtml_g += '<td class="DhbGetSauron_key">黑名单原因</td><td align="center">行政处罚</td>';
					trHtml_g += '<td class="DhbGetSauron_key"></td><td align="center"></td>';
					trHtml_g += "</tr><tr>";
					if(obj.C06BT025){
						var C06BT025=obj.C06BT025;
						for(j =0;j<C06BT025.length;j++){
							trHtml_g += '<td class="DhbGetSauron_key">行政处罚决定书文号</td><td align="center">'+(C06BT025[j].cf_wsh?C06BT025[j].cf_wsh:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">处罚名称</td><td align="center">'+(C06BT025[j].cf_cfmc?C06BT025[j].cf_cfmc:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">处罚事由</td><td align="center">'+(C06BT025[j].cf_sy?C06BT025[j].cf_sy:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">处罚结果</td><td align="center">'+(C06BT025[j].cf_jg?C06BT025[j].cf_jg:"无")+'</td>';
							trHtml_g += "</tr><tr>";
							trHtml_g += '<td class="DhbGetSauron_key">处罚机关</td><td align="center">'+(C06BT025[j].cf_xzjg?C06BT025[j].cf_xzjg:"无")+'</td>';
							trHtml_g += '<td class="DhbGetSauron_key">更新时间</td><td align="center">'+(C06BT025[j].reg_date?C06BT025[j].reg_date:"无")+'</td>';
							trHtml_g += "</tr><tr>";
						}
					}
				}
			}
		}else{
			$("#getPersonalBlacklistContent").html("无黑名单信息");
		}
	}
	$("#getPersonalBlacklistContent").append(trHtml_g);
}