
var QueryEntrance = function(window,$){
	var inputValue = "";
	var tipsTimeoutId;	// 用于重置显示tips框的自动关闭时间。
	var _init = function(){
        myCustomScrollbarForForm({
            obj : ".container",
            advanced : {
                updateOnContentResize : true
            }
        });
		//ititSysGlobalSearchData();
		$("#nope1").bind("input propertychange",function(){
			inputValue = $("#nope1").val();
		});
		
		$("#allSearch").find("a").addClass("curr-select");	
		$("#cus").find("a").addClass("myselected");
		
		$(".s_li").click(function(){
			var nowId = $(this).attr("id");
			$(".s_li").find("a").removeClass("curr-select");
			$("#"+nowId).find("a").addClass("curr-select");
			$("#wayclass").val(nowId);
			var list = new Array();
            for (var i = 0; i < top.searchList.length; i++) {
				var obj = top.searchList[i];
				if(nowId =="allSearch"){
					list.push(obj);
				}else if(nowId =="cusSearch"){
					if(obj.type=="cusCustomer"){
                        list.push(obj);
					}
                }else if(nowId =="pactSearch"){
                    if(obj.type=="pact"){
                        list.push(obj);
                    }
                }else if(nowId =="applySearch"){
                    if(obj.type=="apply"){
                        list.push(obj);
                    }
                }
            }
            changeSysGlobalSearch(list);
		}); 
		//搜索按钮点击事件
		$("#search").click(function(){
			_doSearch();
	 	});
	   	$(".search-input").bind("keydown",function(e){
    		if (e.keyCode == 13) {
    			_doSearch();
			}
    	});
		_filtPms();
		//绘制导航栏
        _drawMenuTitle();
        //绘制主要页面
        _drawMenuContent();
        //绘制我的关注
		_drawMenuAttention();


		//查询选项卡绑定点击事件
		$(".search-title li").click(function(){
	 		$(".search-title li").find("a").removeClass("active");
	 		$(this).find("a").addClass("active");
	 		var thisId = $(this).attr("id");
	 		idArray= thisId.split("-");
	 		var typeNo = idArray[idArray.length-1];
	 		$("[id^='content-menu-kind-']").hide();
	 		$("#content-menu-kind-"+typeNo).show();
		});
		
		//查询项绑定点击事件
		_bindItemClick();
		
		//绑定更多按钮的点击事件
        $("#nav-more-menu").unbind();
		$("#nav-more-menu-span").click(function () {
			 var $i = $(this).find("i");
			 if ($(this).data("isOpen")){
                 $(".is-hidden").hide();
                 $(this).data("isOpen",false);
                 $i.removeClass("i-close-up").addClass("i-open-down");
			 }else{
                 $(".is-hidden").show();
                 $(this).data("isOpen",true);
                 $i.removeClass("i-open-down").addClass("i-close-up");
             }
        })
        if($(".search-title").find("li")[0]!=undefined){
            $(".search-title").find("li")[0].click();
        }
	};
	var _filtPms = function () {
        for (var key in queryMap) {
            var menuList = queryMap[key];
            if (typeof (menuList) != "undefined" && menuList.length > 0) {
            	var newMenuList =new Array();
                for (var i = 0; i < menuList.length; i++) {
                    var queryItem = menuList[i];
                    var pmsFlag = BussNodePmsBiz.checkPmsBiz(queryItem.pmsBizNo);
                    if(pmsFlag){
                        newMenuList.push(queryItem);
                    }
                }
                queryMap[key]= newMenuList;
            }
        }
    }
	var _drawMenuTitle = function () {
		var $ul = $("<ul></ul>").addClass("nav nav-tabs");
        $(".search-title").append($ul);
        var ulWidth = $ul.width();
		var liWidth = 0;
		var moreFlag = false;
		var hideFlag = true;
        for (var key in queryMap) {
            var menuList = queryMap[key];
            if(menuList.length>0){
                hideFlag = false;
                var menuName = menuList[0].secMenuName;
                var $li = $("<li></li>").attr({"id":"nav-menu-kind-"+key});
                $li.append($("<a></a>").text(menuName));
                $ul.append($li);
                liWidth= liWidth+ $li.width();
                if(!moreFlag){
					 if(liWidth>ulWidth-150 ) {
						 moreFlag = true;
						 var liMoreWidth = ulWidth - liWidth + $li.width();
						 var $liMore = $("<li></li>").addClass("text-main-color").attr("id", "nav-more-menu").css({
							 "width": liMoreWidth, "text-align": "right",
							 "padding": "10px 40px", "height": "40px"
						 });
						 var $span = $("<span></span>").text("更多").attr("id","nav-more-menu-span").css("cursor","pointer").data("isOpen",false);
						 var $i = $("<i></i>").addClass("i i-open-down");
						 $span.append($i);
						 $liMore.append($span)
						 $li.before($liMore);
                         $li.addClass("is-hidden");
						 $li.hide();
					 }
                }else{
                	$li.addClass("is-hidden");
                    $li.hide();
				}

			}

        }
        if(hideFlag){
            $(".mysearch").hide();
        }
        //第一个默认选中
        $($($ul.find("li")[0]).find("a")[0]).addClass("active");

    }
	var _drawMenuContent = function () {
        for (var key in queryMap) {
        	var menuList = queryMap[key];
            var $mainDiv = $("<div></div>").addClass("mysearch-content").attr({"id":"content-menu-kind-"+key,});
            $mainDiv.hide();
			var $itemMainDiv = $("<div></div>").addClass("myitems-div");
            $mainDiv.append($itemMainDiv);
            var $SearchContentDiv = $("<div></div>").addClass("row clearfix search-content-div");
            $itemMainDiv.append($SearchContentDiv);
            if(typeof (menuList)!="undefined" &&menuList.length>0){
                for (var i = 0; i < menuList.length; i++) {
                	var queryItem = menuList[i];
                    var pmsFlag = BussNodePmsBiz.checkPmsBiz(queryItem.pmsBizNo);
                    if(pmsFlag){
						var $searchItemDiv = $('<div></div>').addClass("col-md-3 search-item").attr("id",queryItem.itemId);
						var $innerDiv = $('<div></div>').addClass("inner-div");
						$searchItemDiv.append($innerDiv);
						var $iconI = $('<i></i>').addClass("i i-"+queryItem.itemIcon+" search-item-icon color"+(i % 4 + 1));
                        $innerDiv.append($iconI);
                        var $nameDiv = $('<div></div>').addClass("search-item-content").attr("title",queryItem.itemName).text(queryItem.itemName);
                        $innerDiv.append($nameDiv);
                        $SearchContentDiv.append($searchItemDiv);
                    }
                }
			}

            $("#query-items").append($mainDiv);

        }
        $($("#query-items").find("div")[0]).show();
    }
    var _drawMenuAttention = function () {
        var menuList = attentionList;
        var $mainDiv = $("<div></div>").addClass("mysearch-content").attr({"id":"content-attention-menu-kind"});
        var $itemMainDiv = $("<div></div>").addClass("myitems-div");
        $mainDiv.append($itemMainDiv);
        var $SearchContentDiv = $("<div></div>").addClass("row clearfix search-content-div");
        $itemMainDiv.append($SearchContentDiv);
        if(typeof (menuList)!="undefined" &&menuList.length>0){
            for (var i = 0; i < menuList.length; i++) {
                var queryItem = menuList[i];
                var pmsFlag = BussNodePmsBiz.checkPmsBiz(queryItem.pmsBizNo);
                if(pmsFlag){
                    var $searchItemDiv = $('<div></div>').addClass("col-md-3 search-item").attr("id",queryItem.itemId);
                    var $innerDiv = $('<div></div>').addClass("inner-div");
                    $searchItemDiv.append($innerDiv);
                    var $iconI = $('<i></i>').addClass("i i-"+queryItem.itemIcon+" search-item-icon color"+(i % 4 + 1));
                    $innerDiv.append($iconI);
                    var $nameDiv = $('<div></div>').addClass("search-item-content").attr("title",queryItem.itemName).text(queryItem.itemName);
                    $innerDiv.append($nameDiv);
                    $SearchContentDiv.append($searchItemDiv);
                }
            }
            var html= '<div style="border-bottom: 1px solid #ddd;font-size: 20px;padding-left: 20px;padding-bottom: 5px;">' +
                '我的关注' +
                '</div>';
            $("#attention-items").before(html);
            $("#attention-items").append($mainDiv);
        }

    }
	var _addMyItems = function(){
		top.itemId="";
		top.flag=false;
		top.window.openBigForm(webPath+"/mfQueryItem/getBaseItemList","添加我的关注",function(){
			if(top.flag){
				_addItemCallBack(top.itemId);
			}
		},"900px","540px");
	};
	
	
	//查询项绑定点击事件
	var _bindItemClick = function(){
		$(".search-item").bind("click",function(){
			var myQuery = $(this).attr("id");
			switch (myQuery){
			case "archivepact" :
				window.location.href=webPath+"/archiveInfoMain/getListPage";
				break;
			case "lawsuit" :
				window.location.href=webPath+"/mfLawsuit/getListPageForQuery";
				break;
			case "blackcus" :
				window.location.href=webPath+"/mfCusCustomer/getBlickListPage";
				break;
			case "hxquery" :
				window.location.href=webPath+"/mfBusCheckoffs/getListPage";
				break;
			case "highqualitycus" :
				window.location.href=webPath+"/mfCusCustomer/getQualityListPage";
				break;
			case "pledge" :
				window.location.href=webPath+"/mfBusCollateral/getListPage";
				break;
			case "fiveclass" :
				window.location.href=webPath+"/mfPactFiveclass/getFiveClassAndPactListPage";
				break;
			case "batch-fiveclass" :
				window.location.href=webPath+"/mfFiveclassSummaryApply/getListPage";
				break;
			case "assetmanage" ://依法收贷
				window.location.href=webPath+"/mfAssetManage/getAssetManageListPage?applyFlag=0";
				break;
			case "lawsuitapply" ://诉讼申请
				window.location.href=webPath+"/mfAssetManage/getAssetManageListPage?applyFlag=1";
				break;
			case "litigationexpenseapply" ://诉讼费用申请
				window.location.href=webPath+"/mfLitigationExpenseApply/getAssetManageListPage?applyFlag=2";
				break;
			case "batch-fiveclass" :
				window.location.href=webPath+"/mfPactFiveclass/getFiveClassAndPactListPageForBatch";
				break;
			case "recall" :
				window.location.href=webPath+"/recallBase/getQueryEntranceListPage";
				break;
			case "recassgin" :
				window.location.href=webPath+"/recallBase/toSendList";
				break;
			case "transquery" :
				window.location.href=webPath+"/mfOaTrans/getListPage";
				break;
			case "dqhk" :
				window.location.href=webPath+"/mfBusFincApp/getRepayToDatePage";
				break;
			case "overRepay" :
				window.location.href=webPath+"/mfBusFincApp/getRepayOverDatePage";
				break;
			case "pleExpireWarn" :
				window.location.href=webPath+"/pledgeBaseInfo/getPledgeToDatePage";
				break;
			case "jkbx" :
				window.location.href=webPath+"/mfOaDebtexpense/getListPage";
				break;
			case "icloud" :
				window.location.href=webPath+"/mfBusIcloudManage/getListPage";
				break;
			case "disagree" :
				window.location.href=webPath+"/mfQueryDisagree/getListPage";
				break;
			case "discoutnCoupon" :
				window.location.href=webPath+"/mfDiscountManage/getListPage";
				break;
			case "increaseCreditLine" :
				window.location.href=webPath+"/mfAppGiveCreditline/getListPage";
				break;
			case "trench":
				window.location.href=webPath+"/mfBusTrench/getListPage";
				break;
			case "amtcapital":
				window.location.href=webPath+"/mfBusPact/getCaptialConfirmListPage";
				break;
			case "aftercheck":
				window.location.href=webPath+"/mfBusFincApp/getExamineStateListPage";
				break;
			case "cuseval":
				window.location.href=webPath+"/mfCusAppEval/getListPage";
				break;
			case "cuscredit":
				window.location.href=webPath+"/mfCusCreditSearch/getListPage";
				break;
			case "returnfee":
				window.location.href=webPath+"/mfBusPact/getReturnFeePage";
				break;
			case "businessStage":
				window.location.href=webPath+"/mfBusStageSearch/getListPage";
				break;
			case "receivablePrincipalAndInterest":
				window.location.href=webPath+"/mfReceivablePrincipalInterest/getListPage";
				break;
			case "preRepayApply":
				window.location.href=webPath+"/mfPreRepayApply/getListPage";
				break;
			case "thirdPartyPayForAnother":
				window.location.href=webPath+"/thirdPartyPayForAnother/getListPage";
				break;
			case "thirdPartyPayGetAnother":
				window.location.href=webPath+"/thirdPartyGetForAnother/getListPage";
				break;
			case "assetProtect":
				window.location.href=webPath+"/mfAssetProtectRecord/getListPage";
				break;
			case "assetProtectAssign":
				window.location.href=webPath+"/mfAssetProtectRecord/getListPageForAssign";
				break;
			case "interestAccrued":
				window.location.href=webPath+"/mfBusFincApp/getInterestAccruedListPage";
				break;
			case "derateRefundApply":
				window.location.href=webPath+"/mfDeductRefundApply/getListPage";
				break;
			case "cusProspective":
				window.location.href=webPath+"/mfCusProspective/getListPage";
				break;
			case "agencies":
				window.location.href=webPath+"/mfBusAgencies/getListPage";
				break;
			case "assureCompany":
				window.location.href=webPath+"/mfCusAssureCompany/getListPage";
				break;
			case "approvalsts":
				window.location.href=webPath+"/mfBusApply/getApprovalStsListPage";
				break;
			case "stampApply"://用印申请
				window.location.href=webPath+"/mfStampPact/getListPage";
				break;
			case "stampSealApply"://盖章管理
                window.location.href==webPath+"/mfStampPact/getListPageForSeal";
				break;
			case "billDelivery"://发票寄送
				window.location.href=webPath+"/mfInvoiceDelivery/getListPage";
				break;
			case "checkManageAccount":
				window.location.href=webPath+"/mfPlanInfoWx/getListPage";
				break;
			case "deductFailInfo":
				window.location.href=webPath+"/mfBusDeductFail/getListPage";
				break;
			case "sharecomment":
				window.location.href=webPath+"/mfCusRecommend/getListCommentPage";
				break;
			case "noTransfer":
				window.location.href=webPath+"/mfBusFincApp/getNoTransferListPage";
				break;
			case "loanInfoSearch":
				window.location.href=webPath+"/mfBusLoanInfo/getListPage";
				break;
			case "repayInfoSearch":
				window.location.href=webPath+"/mfBusRepayInfo/getListPage";
				break;
			case "advances":
				window.location.href=webPath+"/mfBusAdvancesManage/getListPage";
				break;
			case "fincApprovalSts":
				window.location.href=webPath+"/mfBusFincApp/getFincApprovalStsListPage";
				break;
			case "thirdPaylist":
				window.location.href=webPath+"/mfRequestThirdPartyRecord/getListPage";
				break;
			case "claimsInfoSearch"://理赔列表
				window.location.href=webPath+"/apiPaphClaimsRecordDetailFactor/getListPage";
				break;
			case "phoneCollect"://电话催收
				window.location.href=webPath+"/mfBusFincApp/getPhoneCollectListPage";
				break;
			case "batchAddAmt"://批量提额
				window.location.href=webPath+"/mfBusAddAmtRecord/getPactListPage";
				break;
			case "sms":
				window.location.href=webPath+"/mfSmsCusDetail/input";
				break;
			case "fundPlan"://资金计划表
				window.location.href=webPath+"/mfFundPlan/getListPage";
				break;
			case "accountUpdate"://账号变更
				window.location.href=webPath+"/mfBusFincApp/getAccUpdListPage";
				break;
			case "fengDataquery"://引流接口数据查询
				window.location.href=webPath+"/apiBusRecord/getListPage";
				break;
			case "loanInfoThirdSearch"://第三方放款列表
				window.location.href=webPath+"/mfRequestThirdPartyRecord/getThirPaydListPage";
				break;
			case "repayInfoThirdSearch"://第三方还款列表
				window.location.href=webPath+"/apiThirdServiceOrder/getListPage";
				break;
			case "reconciliation"://快钱第三方对账结果
				window.location.href=webPath+"/apiThirdCheckResult/getListPage";
				break;
			case "onlineApply"://线上融资管理
				window.location.href=webPath+"/p2pOnlineApply/getListPage";
				break;
			case "queryCusWhitename"://白名单客户
				window.location.href=webPath+"/mfCusWhitename/getListPage";
				break;
			case "reassignment":// 审批改派
				var flowApprovalNo = 'apply_approval,contract_approval,putout_approval';// 数据来源, 多个用逗号分隔. 融资审批:apply_approval, 合同审批:contract_approval, 放款审批:putout_approval
				window.location.href=webPath+"/reassignment/getListPage?flowApprovalNo=" + flowApprovalNo;
				break;
			case "coreCompany"://核心企业
				window.location.href=webPath+"/mfCusCoreCompany/getListPage";
				break;
			case "creditProject"://立项查询
				window.location.href=webPath+"/mfCusCreditApply/getCreditProjectListPage";
				break;
			case "groupcus"://集团管理
				window.location.href=webPath+"/mfCusGroup/getListPage";
				break;
			case "feePactQuery"://费用合同查询
				window.location.href=webPath+"/mfBusPact/getFeePactQueryListPage";
				break;
			case "whitenameManage"://白名单管理
				window.location.href=webPath+"/mfCusWhitename/getListPage";
				break;
			case "aftercheck-cus"://客户贷后检查
				window.location.href=webPath+"/mfBusFincApp/getExamineStateForCusListPage";
				break;
			case "postProject"://结项管理
				window.location.href=webPath+"/mfBusPact/getOverPactListPage";
				break;
			case "excelimport"://Excel导入
				window.location.href=webPath+"/mfCusImportExcel/getListPage";
				break;
			case "tour"://贷后巡视
				window.location.href=webPath+"/mfbusFincTour/getListPage";
				break;
			case "riskManage"://风险管理业务视角
				window.location.href=webPath+"/mfRiskLevelManage/getListPage?comeFrom=2";
				break;
			case "cusRiskManage"://风险管理客户视角
				window.location.href=webPath+"/mfRiskLevelManage/getListPage?comeFrom=1";
				break;
			case "indStroeQuery"://自主门店查询
				window.location.href=webPath+"/mfBusStore/getListPage";   
				break;
			case "creditorChoose"://债权分配
				window.location.href=webPath+"/mfBusCreditor/getListPage";   
				break;
			case "markPlan"://排标计划
				window.location.href=webPath+"/mfBusArrangeMarkPlan/getListPage";   
				break;
			case "shareCus"://客户共享
				window.location.href=webPath+"/mfCusCustomer/getCusShareListPage";
				break;

			case "ukinfo"://u盾管理
				window.location.href=webPath+"/mfBusUkinfo/getListPage";
				break;

			case "examHisList4risk"://风险登记
				window.location.href=webPath+"/mfExamineHis/getExamList4RiskPage";
				break;
			case "applyCusPer"://申请客户权限
				window.location.href=webPath+"/mfApplyCusPer/getListPage";
				break;
			case "loginhm"://APP白名单
                window.location.href=webPath+"/mfBusLoginHm/getListPage";
                break;
			case "trans"://客户移交
				window.location.href = webPath+"/mfOaTrans/input";
				break;
			case "bus-trans"://客户移交
				window.location.href = webPath+"/mfOaTrans/input?view=bus";
				break;
			case "short"://客户简称
				window.location.href = webPath + "/mfCusCorpBaseInfo/getListPageForShort";
				break;
			case "bankRepay"://银行还款
				//window.location.href = webPath+"/mfBusPact/getListPageBankRepay";
				window.location.href = webPath+"/mfChannelBus/getListPageChannelBusFinc";
				break;
			case "sealConfirmQuery"://用章确认查询
				window.location.href=webPath+"/mfBusSealConfirm/getListPage";
				break;
			case "interestGuarantee"://预收利担费
				window.location.href=webPath+"/mfBusInterGuaran/getListPage";
				break;
			case "serviceFeeQuery"://服务费收取查询
				window.location.href=webPath+"/mfBusServiceFee/getListPage";
				break;
			case "busDocSupplement"://业务要件补充
				window.location.href=webPath+"/mfBusDocSupplement/getListPage";
				break;
			case "riskRegistration"://风险登记
				window.location.href = webPath+"/mfRiskLevelManage/getRiskRgtListPage";
				break;
			case "repayhis-bus"://还款历史查询
				window.location.href=webPath+"/mfRepayHistory/getRepayHisList";
				break;
			case "entrustedPayment"://受托支付管理
				window.location.href=webPath+"/mfBusEntrustedPayment/getListPage";
				break;
			case "mfBusPledgeRemoveDetail"://最高额担保合同
                window.location.href=webPath+"/mfHighGuaranteeContract/getListPage";
                break;
            case "invoicemanage"://发票管理
                window.location.href=webPath+"/mfBusInvoicemanage/getFincListPage";
				break;
            case "jddata"://金电数据上报
				window.location.href=webPath+"/dataReport/getListPage";
                break;
            case "pactChange"://合同变更
				window.location.href=webPath+"/mfBusPactChange/getListPage";
                break;
                case "cooperativeAgency"://通用合作机构(变更为了收益人管理)
				window.location.href=webPath+"/mfCusCooperativeAgency/getListPage";
                break;
            case "borrowsequrey" ://借阅申请
				 window.location.href=webPath+"/mfArchiveBorrow/getListPage";
				 break;
			case "ceasesqurey" :  //停息管理
			     window.location.href=webPath+"/mfBusStopIntstApply/getListPage";
			     break;
			case "useMoneyPrint" ://用款申请单打印
				window.location.href=webPath+"/mfBusPact/getLoanAfterPactListPageForPrint";
				break;
			case "litigationInFeeQuery"://诉讼费用打款
				window.location.href = webPath
						+ "/mfLitigationExpenseInput/getListPage?inoutFlag="+0;
				break;
			case "litigationOutFeeQuery"://诉讼费用打款
				window.location.href = webPath
						+ "/mfLitigationExpenseInput/getListPage?inoutFlag="+1;
                break;
			case "repayRecheck"://还款复核
				window.location.href = webPath
						+ "/mfRepayHistory/getRecheckListPage";
				break;
			case "reconsideration"://复议列表
				window.location.href=webPath+"/mfQueryDisagree/getReconListPage";
				break;
			case "businessQuery"://51业务查询
                top.openBigForm(webPath+"/mfBusApply/getBusinessListPage","51业务查询", function(){
                });
				break;
			case "commonkindQuery"://非常规产品查询
				window.location.href=webPath+"/mfKindAttrExt/getListPage";
				break;
			case "bankFeeQuery"://农行代扣查询功能
				window.location.href=webPath+"/mfBusFincApp/getListPageBank";
				break;
			case "followPactNo"://从合同号查询
				window.location.href=webPath+"/mfBusCollateralPact/getListPage";
                break;
			case "alliance"://联保体
				window.location.href=webPath+"/mfBusAlliance/getListPage";
				break;
			case "queryCollectAdd"://催收登记
				window.location.href=webPath+"/recallBase/getListPage";
				break;
			case "cusWarehouseOrg"://仓储机构
				window.location.href=webPath+"/mfCusWarehouseOrg/getListPage";
				break;
			case "modifyfincUseDes"://修改贷款用途
				window.location.href=webPath+"/mfBusModifyFincUseDes/getListPage";
				break;
			case "queryRepayApply"://还款申请
				window.location.href=webPath+"/mfRepayApply/getListPage";
				break;
			case "aftercheck-new"://贷后检查任务
				window.location.href=webPath+"/mfBusFincApp/getNewExamineStateListPage";
				break;
			case "aftercheck-reg"://贷后检查登记
				window.location.href=webPath+"/MfExamineDetailController/getExamineRecordListPage";
				break;
			case "fieldAudit"://实地核查
				window.location.href=webPath+"/MfExamineDetailController/getAuditListPage";
				break;
			case "loan-after-change"://保后变更
				window.location.href=webPath+"/mfBusLoanChange/getListPage";
				break;
            case "cusManageQuery"://客户经理查询
                window.location.href=webPath+"/mfBusApply/inputCusManage";
                break;
            case "capitalImplement"://资金落实
                window.location.href=webPath+"/mfBusFincApp/getCapitalImplementListPage";
                break;
			case "capitalDetail"://资金落实详情
				window.location.href=webPath+"/mfBusFincApp/getCapitalImplementDetailListPage";
				break;
			case "cpfb"://产品发布
				window.location.href=webPath+"/mfOaNotice/getListPage?menuNo=80";
				break;
			case "nbtx"://	内部通讯
                window.location.href = webPath + "/mfOaInternalCommunication/getAcceptInfoListPage";
                break;
            case "entZoned"://	企业划型
                window.location.href = webPath + "/mfCusEntZoned/getListPage";
                break;
            case "complaintMent"://	申诉管理
                window.location.href = webPath + "/mfCusComplaintMent/getListPage";
                break;
				window.location.href = webPath + "/mfOaInternalCommunication/getAcceptInfoListPage";
				break;
			case "intentionApply"://申请意向管理
				window.location.href = webPath + "/mfBusIntentionApply/getListPage";
				break;
			case "account"://应收账款查询
				window.location.href = webPath + "/pledgeBaseInfo/getListByAccount";
				break;
			case "dissolutionGuarantee"://解保登记
				window.location.href = webPath + "/mfBusDissolutionGuarantee/getListPage";
				break;
			case "dissolutionGuaranteeHis"://解保历史
				window.location.href = webPath + "/mfBusDissolutionGuarantee/getDissolutionListPage";
				break;
			case "filecountersign"://保密协议
				window.location.href=webPath+"/mfOaFileCountersign/getListPage?menuNo=80";
				break;
            case "riskAudit"://风险稽核
				window.location.href=webPath+"/mfBusRiskAudit/getAccurateListPage";
				break;
			case "risk-audit-notice"://风险稽核
                window.location.href=webPath+"/mfBusRiskAudit/getAccurateListPageForNotice";
				break;
			case "riskAuditHis"://风险稽核历史
                window.location.href=webPath+"/mfBusRiskAudit/getListPage";
                break;
			case "risk-audit-gc"://风险稽核工程
				window.location.href=webPath+"/mfBusRiskAudit/getAccurateListPageGc";
				break;
			case "risk-audit-notice-gc"://风险稽核工程
				window.location.href=webPath+"/mfBusRiskAudit/getAccurateListPageForNoticeGc";
				break;
			case "risk-audit-his-gc"://风险稽核历史工程
				window.location.href=webPath+"/mfBusRiskAudit/getListPage";
				break;
			case "closingManage"://结案申请
                window.location.href = webPath + "/mfCusClosingManage/getListPage";
                break;
			case "query-fee-refund"://退费管理
				window.location.href=webPath+"/mfBusFeeRefund/getListPage";
                break;
            case "bankAccManageBus":
            	window.location.href = webPath+"/cwCusBankAccManage/getListPage";
                break;
            case "compensatoryApply"://代偿申请
                window.location.href = webPath + "/mfBusCompensatoryApply/getListPage";
                break;
            case "recourseApply"://追偿申请
                window.location.href = webPath + "/mfBusRecourseApply/getListPage";
                break;
            case "query-fee-collect"://收费管理
                window.location.href=webPath+"/mfBusFeeCollect/getListPage";
                break;
			default :
				if(myQuery!="add-item"){
					_showTips(this);
					break;
				}
				
			}
		});
	};
	//添加我的关注回调处理
	var _addItemCallBack = function(itemId){
		//异步添加或更新我的关注查询项
		top.LoadingAnimate.start();
		$.ajax({
			url: webPath+"/mfQueryItem/insertAjax?funcType=0&itemId="+encodeURI(itemId),
			datatype: "json",
			success: function(data) {
				if(data.flag=="success"){
					var addHtml = $("#add-item").prop("outerHTML");
					var htmlStr = '';
					var len = data.mfQueryItemList.length;
					//没有数据，仅显示添加关注按钮
					if(len == 0){
						htmlStr = htmlStr+'<div class="row clearfix search-content-div">'
								+addHtml
								+'</div>';
					}else{
						$.each(data.mfQueryItemList, function(i, mfQueryItem) {
							var colorIndex =  i% 4 +1;
							if(i%4==0){
								htmlStr = htmlStr + '<div class="row clearfix search-content-div">';
							}
							htmlStr = htmlStr +'<div id="'+mfQueryItem.itemId+'" class="col-md-3 search-item">'
							+'<i class="i i-'+mfQueryItem.itemIcon+' search-item-icon color'+colorIndex+'"></i><div class="search-item-content" title="'+mfQueryItem.itemName+'">'+mfQueryItem.itemName+'</div>'
							+'</div>';
							if(i % 4==3 || i==len-1){
								if(i==len-1 && i % 4==3){
									htmlStr = htmlStr +'</div>'
									+'<div class="row clearfix search-content-div">'
									+addHtml
									+'</div>';
								}else if(i==len-1 && i % 4 < 3 ){
									htmlStr = htmlStr+addHtml+'</div>';
								}else{
									htmlStr = htmlStr +'</div>';
								}
							}
						});
					}
					$(".myitems-div").empty().html(htmlStr);
//					$("#add-item").bind("click",function(event){
//						$(".pops-value").click();
//						return false;
//					});
					//查询项绑定点击事件
					_bindItemClick();
					top.LoadingAnimate.stop();
				}
			},error:function(){
				top.LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION","添加关注"),0);
			}
		});
	
	};
	var _doSearch = function() {
		if (!inputValue) {
			$("#nope1").focus();
			return false;
		}
		LoadingAnimate.start();
		var firstKindNo = $("#firstKindNo").val();
		var wayclass = $("#wayclass").val();
		inputValue = encodeURIComponent(inputValue);
		console.log("MfQueryEntrance:"+searchDatas);
		sreached(searchDatas,wayclass,inputValue,firstKindNo);
	};
	
	var _showTips = function (obj) {
		top.LoadingAnimate.stop();
		var d = dialog({
			id : "oaInBuilding",
			content : "正在建设中，敬请期待。",
			padding : "3px"
		}).show(obj);
		if (tipsTimeoutId) {
			clearTimeout(tipsTimeoutId);
		}
		tipsTimeoutId = setTimeout(function() {
			d.close().remove();
		}, 1000);
	};
	
	return{ 
		init:_init,
		addMyItems:_addMyItems
	};
	 
}(window,jQuery);