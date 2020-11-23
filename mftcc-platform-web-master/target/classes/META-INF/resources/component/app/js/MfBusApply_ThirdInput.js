;
var MfBusApply_ThirdInput = function(window, $) {
	var _init = function(){
	};
	
	//查询第三方数据
	var _queryThirdData = function(obj){
        var thirdQueryType = $('input[name=thirdQueryType]').val();
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var dataParam = JSON.stringify($(obj).serializeArray());
			var url = "";
			switch(thirdQueryType){
			case "housecnt":// 房产产权数量查询
				url = webPath+"/mfThirdServiceRecord/housecntSearchThiredAjax";
				break;
			case "housests":// 房产产权状态查询
				url = webPath+"/mfThirdServiceRecord/housestsSearchThiredAjax";
				break;
			case "15":// 个人身份认证
				url = webPath+"/mfThirdServiceRecord/identityVerifyThirdAjax";
				break;
			case "1":// 企业工商综合信息
				url = webPath+"/mfThirdServiceRecord/enterpriseQueryAjax";
				break;
			case "57":// 贷款轨迹
				url = webPath+"/mfThirdServiceRecord/loanTrackAjax";
				break;
			case "53":// 企业涉诉案件详情
				url = webPath+"/mfThirdServiceRecord/highCourtCaseEParmsAjax";
				break;
			case "7":// 个人工商综合信息
				url = webPath+"/mfThirdServiceRecord/unifyPersonalEnterpriseResAjax";
				break;
			case "56":// 企业裁判文书解析摘要
				url = webPath+"/mfThirdServiceRecord/entJudgAbstractApiHzAjax";
				break;
			case "55":// 企业风险信息
				url = webPath+"/mfThirdServiceRecord/enterpriseRiskInfoAjax";
				break;
			case "54":// 个人风险信息
				url = webPath+"/mfThirdServiceRecord/unifyPersonRiskInfoAjax";
				break;
			case "32":// 个人裁判文书解析摘要
				url = webPath+"/mfThirdServiceRecord/queryPersonJudgAbstractAjax";
				break;
			case "23":// 个人涉诉案件详情
				url = webPath+"/mfThirdServiceRecord/highCourtCasePParmsAjax";
				break;
			case "70":// 手机号三要素实名认证
				url = webPath+"/mfThirdServiceRecord/mobileVerifyThreeAjax";
				break;
			case "71":// 手机号在网状态
				url = webPath+"/mfThirdServiceRecord/mobileStateAjax";
				break;
			case "72":// 手机号在网时长
				url = webPath+"/mfThirdServiceRecord/mobileTelecomOnlineTimeAjax";
				break;
			case "honeypot":// 蜜罐
				url = webPath+"/mfThirdServiceRecord/honeypotAjax";
				break;
			case "80":// 用户银行画像
				url = webPath+"/mfThirdServiceRecord/bankCardPortrayalAjax";
				break;
			case "90":// 手机号二要素认证
				url = webPath+"/mfThirdServiceRecord/mobileVerifyTwoAjax";
				break;
			case "gps":// GPS查询
				url = webPath+"/mfThirdServiceRecord/simpleObjectTracksAjax";
				break;
			case "idAttestation":// 身份认证（返照片）
				url = webPath+"/mfThirdServiceRecord/idAttestationAjax";
				break;
			case "personalCorpQuery":// 个人名下企业查询
				url = webPath+"/mfThirdServiceRecord/personalCorpQueryAjax";
				break;
			case "censusDataQuery":// 户籍信息查询
				url = webPath+"/mfThirdServiceRecord/censusDataQueryAjax";
				break;
			case "badInfoQuery":// 不良信息查询
				url = webPath+"/mfThirdServiceRecord/badInfoQueryAjax";
				break;
			case "corpLawsuitsAllClass":// 企业涉诉高法全类
				url = webPath+"/mfThirdServiceRecord/corpLawsuitsAllClassAjax";
				break;
			case "multipleLoansQuery":// 多重借贷查询
				url = webPath+"/mfThirdServiceRecord/multipleLoansQueryAjax";
				break;
			case "corpICData":// 企业工商数据查询
				url = webPath+"/mfThirdServiceRecord/corpICDataAjax";
				break;
			case "loseCreditBlacklist":// 失信黑名单查询
				url = webPath+"/mfThirdServiceRecord/loseCreditBlacklistAjax";
				break;
			case "loseCerditPersonInfo":// 失信人信息查询
				url = webPath+"/mfThirdServiceRecord/loseCerditPersonInfoAjax";
				break;
			case "loseCerditBZXRInfo":// 失信被执行人信息查询
				url = webPath+"/mfThirdServiceRecord/loseCerditBZXRInfoAjax";
				break;
			case "creditLoanApply":// 信贷申请详情
				url = webPath+"/mfThirdServiceRecord/creditLoanApplyAjax";
				break;
			case "overdueDebtQuery":// 逾期欠款查询
				url = webPath+"/mfThirdServiceRecord/overdueDebtQueryAjax";
				break;
			default:
				_showTips();
				return false;
				break;
			}
			//处理查询功能
			$.ajax({
				type:"post",    
				url : url,
				data : {ajaxData:dataParam},
				success : function(data) {
					if (data.flag == "success") {
						$("#result_cont").html(data.httpStrs);
						myCustomScrollbarForForm({
							obj:".scroll-content",
							advanced:{
								updateOnContentResize:true
							}
						});
						alert(data.msg, 1);
					}else{
						$("#result_cont").html(data.httpStrs);
						window.top.alert(data.msg, 0);
					}
					top.flag = true;
				},error : function() {
					alert(top.getMessage("ERROR_SERVER"),0);
				}
			});
		}
	};

	var tipsTimeoutId;	// 用于重置显示tips框的自动关闭时间。
	var _showTips = function (obj) {
		top.LoadingAnimate.stop();
		var d = dialog({
			id : "thirdQueryInBuilding",
			content : "正在建设中，敬请期待。",
			padding : "30px"
		}).show(obj);
		if (tipsTimeoutId) {
			clearTimeout(tipsTimeoutId);
		}
		tipsTimeoutId = setTimeout(function() {
			d.close().remove();
		}, 1000);
	};

	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		queryThirdData:_queryThirdData
	};
}(window, jQuery);