var queryView = function (window, $) {

    var _showQueryPage = function (menueClass,menuNo) {
        $("#queryPage").width($(".outer-center").width()/2);
        $("#queryPage").height("90%");
        $(".queryListDiv").height("88%");
        $("#queryPage").css("left",$(".outer-center").position().left);
        $("#queryPage").css("top",$(".middle-center").offset().top+10);

    };
    var _bindQueryMouse = function (queryMenueArr) {
        if(queryMenueArr.length != 0){
            for(var menuNo in queryMenueArr){
                var queryMenueClass = "entranceNo";
                queryMenueClass = queryMenueClass + queryMenueArr[menuNo];
                $("."+queryMenueClass).unbind("click");
                (function(queryMenueClass, menuNo) {
                    $("."+queryMenueClass).hover(function() {
                        _getQueryList(queryMenueArr[menuNo]);
                        _showQueryPage(queryMenueClass,queryMenueArr[menuNo]);
                        $("#queryPage").fadeIn(500);
                    }, function() {
                        $("#queryPage").css("display","none");
                        $('#queryPage').removeClass("hover");
                    })
                    $("#queryPage").hover(function() {
                        $("."+queryMenueClass).addClass("hover");
                        _showQueryPage(queryMenueClass,queryMenueArr[menuNo]);
                        $("#queryPage").css("display","block");
                    }, function() {
                        $("#queryPage").css("display", "none");
                        $("." + queryMenueClass).removeClass("hover");
                    })
                    $("#queryInput").bind("input propertychange", function() {
                        _getQueryList($("input[name='queryMenuNo']").val(),$.trim($(".queryInput").val()));
                    });
                })(queryMenueClass, menuNo);


            }
        }
    };

    var _getQueryList = function (menuNo,ajaxData) {
        if(typeof ajaxData == 'undefined'){
            ajaxData = '';
        }
        var dataParam = [
            {
                customQuery: ajaxData
            }
        ];
        $.ajax({
            url: webPath + "/mfQueryItem/getQueryList",
            data:{"menuNo":menuNo,ajaxData:JSON.stringify(dataParam)},
            type: "post",
            dataType: 'json',
            async: false,
            success: function (data) {
                if (data.flag == 'success') {
                    $(".queryFirDiv").empty();
                    $(".querySecDiv").empty();
                    $(".queryThirDiv").empty();
                    $(".queryAttention").empty();
                    var queryData = data.mfQueryItemMap;
                    if(Object.keys(queryData).length > 0 ){
                        var div1Map = queryData.div1Map;
                        var div2Map = queryData.div2Map;
                        var div3Map = queryData.div3Map;
                        var attenMap = queryData.attenMap;
                        var key,itemList,html,i;
                        if(typeof attenMap != "undefined"){
                            for(key in attenMap){
                                itemList = attenMap[key];
                                html = "";
                                for(i=0;i<itemList.length;i++){
                                    if(i == 0){
                                        html += "<ul><li class='queryMyTile'>"+itemList[i].itemName+"</li>"
                                    }else{
                                        html += "<li id='"+itemList[i].id+"' onmouseover='queryView.myMouseOver(this);' onmouseout='queryView.myMouseOut(this);' style='width: 33%;float: left;cursor:pointer;'><a style='color:#666' onclick=\"queryView.queryClick('"+itemList[i].itemId+"')\" >"+itemList[i].itemName+"</a>";
                                        html += "<span class='content_pasAware'><i onclick='queryView.changeState(this);' id='"+itemList[i].id+"' value='1' class=\"i i-xing\"></i></span></li>"
                                    }
                                }
                                html = html + "</ul></div>";
                                $(".queryAttention").append(html);
                            }
                        }

                        if(typeof div1Map != "undefined"){
                            for(key in div1Map){
                                itemList = div1Map[key];
                                html = "<div id='div1_"+key+"'>";
                                for(i=0;i<itemList.length;i++){
                                    html += queryView.getInnerHtml(i,itemList[i].id,itemList[i].itemId,itemList[i].attentionFlag,itemList[i].itemName);
                                }
                                html = html + "</ul></div>";
                                $(".queryFirDiv").append(html);
                            }
                        }
                        if(typeof div2Map != "undefined"){
                            for(key in div2Map){
                                itemList = div2Map[key];
                                html = "<div id='div2_"+key+"'>";
                                for(i=0;i<itemList.length;i++){
                                    html += queryView.getInnerHtml(i,itemList[i].id,itemList[i].itemId,itemList[i].attentionFlag,itemList[i].itemName);
                                }
                                html = html + "</ul></div>";
                                $(".querySecDiv").append(html);
                            }
                        }
                        if(typeof div3Map != "undefined"){
                            for(key in div3Map){
                                itemList = div3Map[key];
                                html = "<div id='div3_"+key+"'>";
                                for(i=0;i<itemList.length;i++){
                                    html += queryView.getInnerHtml(i,itemList[i].id,itemList[i].itemId,itemList[i].attentionFlag,itemList[i].itemName);
                                }
                                html = html + "</ul></div>";
                                $(".queryThirDiv").append(html);
                            }
                        }
                        $("input[name='queryMenuNo']").val(menuNo);
                        $(".queryAttention").css("height",_calcAttentionHeight()+"px");
                        $(".queryListDiv").find("ul").css("margin","0px").css("padding","0px");
                    }
                } else {

                }
            },
            error: function () {
                alert(top.getMessage("ERROR_REQUEST_URL", url));
            }
        });
    }
    var _myMouseOver = function (obj) {
        $(obj).find('a').css("style","");
        $(obj).find('a').css("color","");
        $(obj).find('a').addClass("blur_a");
        $(obj).find('span').css("display","block");
    }
    var _myMouseOut = function (obj) {
        $(obj).find('a').css("color","#666");
        if($(obj).find('i').attr('value') == '0'){
            $(obj).find('span').css("display","none");
        }
    }
    var _calcAttentionHeight = function () {
        var height = "0";
        var $li = $(".queryAttention li").size();
        if($li > 1 ){
            if((($li-1)%3) == 1 || (($li-1)%3) == 2){
                height = (parseInt(($li-1)/3)+3)*30;
            }else{
                height = (parseInt(($li-1)/3)+2)*30;
            }
            $(".queryAttention").css("display","block");
        }else{
            $(".queryAttention").css("display","none");
        }
        return height;
    }
    var _getInnerHtml = function (i,id,itemId,attentionFlag,itemName) {
        var html = "";
        if(i == 0){
            html += "<ul><li class='queryMyTile'>"+itemName+"</li>"
        }else{
            html += "<li id='"+id+"'style='cursor:pointer;' onmouseover='queryView.myMouseOver(this);' onmouseout='queryView.myMouseOut(this);' ><a style='color:#666' onclick=\"queryView.queryClick('"+itemId+"')\">"+itemName+"</a>";
            if(attentionFlag == '1'){
                html += "<span><i onclick=\"queryView.changeState(this,'"+itemId+"','"+itemName+"');\" id='"+id+"' value='1' class=\"i i-xing\"></i></span></li>"
            }else{
                html += "<span style='display: none' ><i onclick=\"queryView.changeState(this,'"+itemId+"','"+itemName+"');\"  id='"+id+"' value='0' class=\"i i-xing2\" ></i></span></li>"
            }
        }
        return html;
    }

    var _changeState = function (obj,itemId,itemName) {
        var id = $(obj).attr('id');
        var value = $(obj).attr('value');
        if(value == '0'){
            value = '1';
        }else{
            value = '0';
        }
        $.ajax({
            url: webPath + "/mfQueryItem/updateAjax?id=" + id+"&attentionFlag="+value,
            type: "post",
            dataType: 'json',
            async:false,
            success: function (data) {
                if (data.flag == 'success') {
                    if(value == '1'){
                        //选中display 赋值
                        //取消赋值
                        $(obj).removeClass();
                        $(obj).addClass(" i i-xing");
                        $(obj).attr("value",value);
                        $(obj).parent().css("display","block");
                        var html = "<li id='"+id+"' onmouseover='queryView.myMouseOver(this);' onmouseout='queryView.myMouseOut(this);' style='width: 33%;float: left;cursor:pointer;'><a style='color:#666' onclick=\"queryView.queryClick('"+itemId+"')\">"+itemName+"</a>";
                        html += "<span class='content_pasAware'><i onclick='queryView.changeState(this);' id='"+id+"' value='1' class=\"i i-xing\" ></i></span></li>"
                        if($(".queryAttention ul").size()==0){
                            html = "<ul style='margin: 0px; padding: 0px;'><li class='queryMyTile'>我的关注</li>"+html+"</ul>";
                            $(".queryAttention").append(html);
                        }else{
                            $(".queryAttention ul").append(html);
                        }

                        $(".queryAttention").css("height",_calcAttentionHeight()+"px");
                    }
                    if(value == '0'){
                        $(".queryAttention").find("li[id='"+id+"']").remove();
                        $("li[id='"+id+"'] i").removeClass();
                        $("li[id='"+id+"'] i").addClass("i i-xing2");
                        $("li[id='"+id+"']").find('span').css("display","none");
                        $(".queryAttention").css("height",_calcAttentionHeight()+"px");
                        $("li[id='"+id+"'] i").attr("value",value);
                    }
                }else{
                    alert(top.getMessage("ERROR_REQUEST_URL", url));
                }
            },
            error: function () {
                alert(top.getMessage("ERROR_REQUEST_URL", url));
            }
        });
    }

    var _queryClick = function (myQuery) {
        var hrefPath = "";
        //处理贷后和综合选择后菜单没选中的问题
        var queryMenuNo=$("input[name='queryMenuNo']").val();
        $(".menu-active").removeClass("menu-active");
        $(".entranceNo"+queryMenuNo).addClass("menu-active");
        $("#queryPage").css("display","none");
        switch (myQuery){
            case "archivepact" :
                hrefPath=webPath+"/archiveInfoMain/getListPage";
                break;
            case "lawsuit" :
                hrefPath=webPath+"/mfLawsuit/getListPageForQuery";
                break;
            case "blackcus" :
                hrefPath=webPath+"/mfCusCustomer/getBlickListPage";
                break;
            case "hxquery" :
                hrefPath=webPath+"/mfBusCheckoffs/getListPage";
                break;
            case "highqualitycus" :
                hrefPath=webPath+"/mfCusCustomer/getQualityListPage";
                break;
            case "pledge" :
                hrefPath=webPath+"/mfBusCollateral/getListPage";
                break;
            case "fiveclass" :
                hrefPath=webPath+"/mfPactFiveclass/getFiveClassAndPactListPage";
                break;
            case "batch-fiveclass" :
                hrefPath=webPath+"/mfFiveclassSummaryApply/getListPage";
                break;
            case "assetmanage" ://依法收贷
                hrefPath=webPath+"/mfAssetManage/getAssetManageListPage?applyFlag=0";
                break;
            case "lawsuitapply" ://诉讼申请
                hrefPath=webPath+"/mfAssetManage/getAssetManageListPage?applyFlag=1";
                break;
            case "litigationexpenseapply" ://诉讼费用申请
                hrefPath=webPath+"/mfLitigationExpenseApply/getAssetManageListPage?applyFlag=2";
                break;
            case "batch-fiveclass" :
                hrefPath=webPath+"/mfPactFiveclass/getFiveClassAndPactListPageForBatch";
                break;
            case "recall" :
                hrefPath=webPath+"/recallBase/getQueryEntranceListPage";
                break;
            case "recassgin" :
                hrefPath=webPath+"/recallBase/toSendList";
                break;
            case "transquery" :
                hrefPath=webPath+"/mfOaTrans/getListPage";
                break;
            case "dqhk" :
                hrefPath=webPath+"/mfBusFincApp/getRepayToDatePage";
                break;
            case "overRepay" :
                hrefPath=webPath+"/mfBusFincApp/getRepayOverDatePage";
                break;
            case "pleExpireWarn" :
                hrefPath=webPath+"/pledgeBaseInfo/getPledgeToDatePage";
                break;
            case "jkbx" :
                hrefPath=webPath+"/mfOaDebtexpense/getListPage";
                break;
            case "icloud" :
                hrefPath=webPath+"/mfBusIcloudManage/getListPage";
                break;
            case "disagree" :
                hrefPath=webPath+"/mfQueryDisagree/getListPage";
                break;
            case "discoutnCoupon" :
                hrefPath=webPath+"/mfDiscountManage/getListPage";
                break;
            case "increaseCreditLine" :
                hrefPath=webPath+"/mfAppGiveCreditline/getListPage";
                break;
            case "trench":
                hrefPath=webPath+"/mfBusTrench/getListPage";
                break;
            case "amtcapital":
                hrefPath=webPath+"/mfBusPact/getCaptialConfirmListPage";
                break;
            case "aftercheck":
                hrefPath=webPath+"/mfBusFincApp/getExamineStateListPage";
                break;
            case "aftercheck-new":
                hrefPath=webPath+"/mfBusFincApp/getNewExamineStateListPage";
                break;
            case "aftercheck-reg":
                hrefPath=webPath+"/MfExamineDetailController/getExamineRecordListPage";
                break;
            case "cuseval":
                hrefPath=webPath+"/mfCusAppEval/getListPage";
                break;
            case "cuscredit":
                hrefPath=webPath+"/mfCusCreditSearch/getListPage";
                break;
            case "returnfee":
                hrefPath=webPath+"/mfBusPact/getReturnFeePage";
                break;
            case "businessStage":
                hrefPath=webPath+"/mfBusStageSearch/getListPage";
                break;
            case "receivablePrincipalAndInterest":
                hrefPath=webPath+"/mfReceivablePrincipalInterest/getListPage";
                break;
            case "preRepayApply":
                hrefPath=webPath+"/mfPreRepayApply/getListPage";
                break;
            case "thirdPartyPayForAnother":
                hrefPath=webPath+"/thirdPartyPayForAnother/getListPage";
                break;
            case "thirdPartyPayGetAnother":
                hrefPath=webPath+"/thirdPartyGetForAnother/getListPage";
                break;
            case "assetProtect":
                hrefPath=webPath+"/mfAssetProtectRecord/getListPage";
                break;
            case "assetProtectAssign":
                hrefPath=webPath+"/mfAssetProtectRecord/getListPageForAssign";
                break;
            case "interestAccrued":
                hrefPath=webPath+"/mfBusFincApp/getInterestAccruedListPage";
                break;
            case "derateRefundApply":
                hrefPath=webPath+"/mfDeductRefundApply/getListPage";
                break;
            case "cusProspective":
                hrefPath=webPath+"/mfCusProspective/getListPage";
                break;
            case "agencies":
                hrefPath=webPath+"/mfBusAgencies/getListPage";
                break;
            case "assureCompany":
                hrefPath=webPath+"/mfCusAssureCompany/getListPage";
                break;
            case "approvalsts":
                hrefPath=webPath+"/mfBusApply/getApprovalStsListPage";
                break;
            case "checkManageAccount":
                hrefPath=webPath+"/mfPlanInfoWx/getListPage";
                break;
            case "deductFailInfo":
                hrefPath=webPath+"/mfBusDeductFail/getListPage";
                break;
            case "sharecomment":
                hrefPath=webPath+"/mfCusRecommend/getListCommentPage";
                break;
            case "noTransfer":
                hrefPath=webPath+"/mfBusFincApp/getNoTransferListPage";
                break;
            case "loanInfoSearch":
                hrefPath=webPath+"/mfBusLoanInfo/getListPage";
                break;
            case "repayInfoSearch":
                hrefPath=webPath+"/mfBusRepayInfo/getListPage";
                break;
            case "advances":
                hrefPath=webPath+"/mfBusAdvancesManage/getListPage";
                break;
            case "fincApprovalSts":
                hrefPath=webPath+"/mfBusFincApp/getFincApprovalStsListPage";
                break;
            case "thirdPaylist":
                hrefPath=webPath+"/mfRequestThirdPartyRecord/getListPage";
                break;
            case "claimsInfoSearch"://理赔列表
                hrefPath=webPath+"/apiPaphClaimsRecordDetailFactor/getListPage";
                break;
            case "phoneCollect"://电话催收
                hrefPath=webPath+"/mfBusFincApp/getPhoneCollectListPage";
                break;
            case "batchAddAmt"://批量提额
                hrefPath=webPath+"/mfBusAddAmtRecord/getPactListPage";
                break;
            case "sms":
                hrefPath=webPath+"/mfSmsCusDetail/input";
                break;
            case "fundPlan"://资金计划表
                hrefPath=webPath+"/mfFundPlan/getListPage";
                break;
            case "accountUpdate"://账号变更
                hrefPath=webPath+"/mfBusFincApp/getAccUpdListPage";
                break;
            case "fengDataquery"://引流接口数据查询
                hrefPath=webPath+"/apiBusRecord/getListPage";
                break;
            case "fengchaxun"://款计划导出
                hrefPath=webPath+"/apiRepayPlanRecord/getListPage";
                break;
            case "appQuery"://铁甲网意向客户
                hrefPath=webPath+"/mfWechatCusInfo/getListPage";
                break;
            case "loanInfoThirdSearch"://第三方放款列表
                hrefPath=webPath+"/mfRequestThirdPartyRecord/getThirPaydListPage";
                break;
            case "repayInfoThirdSearch"://第三方还款列表
                hrefPath=webPath+"/apiThirdServiceOrder/getListPage";
                break;
            case "reconciliation"://快钱第三方对账结果
                hrefPath=webPath+"/apiThirdCheckResult/getListPage";
                break;
            case "onlineApply"://线上融资管理
                hrefPath=webPath+"/p2pOnlineApply/getListPage";
                break;
            case "p2pbusiness"://业务区域管理
                hrefPath=webPath+"/p2pBusinessArea/getListPage";
                break;
            case "queryCusWhitename"://白名单客户
                hrefPath=webPath+"/mfCusWhitename/getListPage";
                break;
            case "reassignment":// 审批改派
                var flowApprovalNo = 'apply_approval,contract_approval,putout_approval';// 数据来源, 多个用逗号分隔. 融资审批:apply_approval, 合同审批:contract_approval, 放款审批:putout_approval
                hrefPath=webPath+"/reassignment/getListPage?flowApprovalNo=" + flowApprovalNo;
                break;
            case "coreCompany"://核心企业
                hrefPath=webPath+"/mfCusCoreCompany/getListPage";
                break;
            case "creditProject"://立项查询
                hrefPath=webPath+"/mfCusCreditApply/getCreditProjectListPage";
                break;
            case "groupcus"://集团管理
                hrefPath=webPath+"/mfCusGroup/getListPage";
                break;
            case "feePactQuery"://费用合同查询
                hrefPath=webPath+"/mfBusPact/getFeePactQueryListPage";
                break;
            case "whitenameManage"://白名单管理
                hrefPath=webPath+"/mfCusWhitename/getListPage";
                break;
            case "aftercheck-cus"://客户贷后检查
                hrefPath=webPath+"/mfBusFincApp/getExamineStateForCusListPage";
                break;
            case "postProject"://结项管理
                hrefPath=webPath+"/mfBusPact/getOverPactListPage";
                break;
            case "excelimport"://Excel导入
                hrefPath=webPath+"/mfCusImportExcel/getListPage";
                break;
            case "tour"://贷后巡视
                hrefPath=webPath+"/mfbusFincTour/getListPage";
                break;
            case "riskManage"://风险管理业务视角
                hrefPath=webPath+"/mfRiskLevelManage/getListPage?comeFrom=2";
                break;
            case "cusRiskManage"://风险管理客户视角
                hrefPath=webPath+"/mfRiskLevelManage/getListPage?comeFrom=1";
                break;
            case "indStroeQuery"://自主门店查询
                hrefPath=webPath+"/mfBusStore/getListPage";
                break;
            case "creditorChoose"://债权分配
                hrefPath=webPath+"/mfBusCreditor/getListPage";
                break;
            case "markPlan"://排标计划
                hrefPath=webPath+"/mfBusArrangeMarkPlan/getListPage";
                break;
            case "shareCus"://客户共享
                hrefPath=webPath+"/mfCusCustomer/getCusShareListPage";
                break;

            case "ukinfo"://u盾管理
                hrefPath=webPath+"/mfBusUkinfo/getListPage";
                break;

            case "examHisList4risk"://风险登记
                hrefPath=webPath+"/mfExamineHis/getExamList4RiskPage";
                break;
            case "applyCusPer"://申请客户权限
                hrefPath=webPath+"/mfApplyCusPer/getListPage";
                break;
            case "loginhm"://APP白名单
                hrefPath=webPath+"/mfBusLoginHm/getListPage";
                break;
            case "trans"://客户移交
                hrefPath = webPath+"/mfOaTrans/input";
                break;
            case "bus-trans"://客户移交
                hrefPath = webPath+"/mfOaTrans/input?view=bus";
                break;
            case "short"://客户简称
                hrefPath = webPath + "/mfCusCorpBaseInfo/getListPageForShort";
                break;
            case "bankRepay"://银行还款
                //hrefPath = webPath+"/mfBusPact/getListPageBankRepay";
                hrefPath = webPath+"/mfChannelBus/getListPageChannelBusFinc";
                break;
            case "sealConfirmQuery"://用章确认查询
                hrefPath=webPath+"/mfBusSealConfirm/getListPage";
                break;
            case "interestGuarantee"://预收利担费
                hrefPath=webPath+"/mfBusInterGuaran/getListPage";
                break;
            case "serviceFeeQuery"://服务费收取查询
                hrefPath=webPath+"/mfBusServiceFee/getListPage";
                break;
            case "busDocSupplement"://业务要件补充
                hrefPath=webPath+"/mfBusDocSupplement/getListPage";
                break;
            case "riskRegistration"://风险登记
                hrefPath = webPath+"/mfRiskLevelManage/getRiskRgtListPage";
                break;
            case "repayhis-bus"://还款历史查询
                hrefPath=webPath+"/mfRepayHistory/getRepayHisList";
                break;
            case "entrustedPayment"://受托支付管理
                hrefPath=webPath+"/mfBusEntrustedPayment/getListPage";
                break;
            case "mfBusPledgeRemoveDetail"://最高额担保合同
                hrefPath=webPath+"/mfHighGuaranteeContract/getListPage";
                break;
            case "invoicemanage"://发票管理
                hrefPath=webPath+"/mfBusInvoicemanage/getFincListPage";
                break;
            case "jddata"://金电数据上报
                hrefPath=webPath+"/dataReport/getListPage";
                break;
            case "dockingyf"://业务数据打包下载
                hrefPath=webPath+"/mfCusDocKingYf/getListPage";
                break;
            case "pactChange"://合同变更
                hrefPath=webPath+"/mfBusPactChange/getListPage";
                break;
            case "cooperativeAgency"://通用合作机构
                hrefPath=webPath+"/mfCusCooperativeAgency/getListPage";
                break;
            case "borrowsequrey" ://借阅申请
                hrefPath=webPath+"/mfArchiveBorrow/getListPage";
                break;
            case "ceasesqurey" :  //停息管理
                hrefPath=webPath+"/mfBusStopIntstApply/getListPage";
                break;
            case "useMoneyPrint" ://用款申请单打印
                hrefPath=webPath+"/mfBusPact/getLoanAfterPactListPageForPrint";
                break;
            case "litigationInFeeQuery"://诉讼费用打款
                hrefPath = webPath
                    + "/mfLitigationExpenseInput/getListPage?inoutFlag="+0;
                break;
            case "litigationOutFeeQuery"://诉讼费用打款
                hrefPath = webPath
                    + "/mfLitigationExpenseInput/getListPage?inoutFlag="+1;
                break;
            case "repayRecheck"://还款复核
                hrefPath = webPath
                    + "/mfRepayHistory/getRecheckListPage";
                break;
            case "reconsideration"://复议列表
                hrefPath=webPath+"/mfQueryDisagree/getReconListPage";
                break;
            case "businessQuery"://51业务查询
                top.openBigForm(webPath+"/mfBusApply/getBusinessListPage","51业务查询", function(){
                });
                break;
            case "commonkindQuery"://非常规产品查询
                hrefPath=webPath+"/mfKindAttrExt/getListPage";
                break;
            case "bankFeeQuery"://农行代扣查询功能
                hrefPath=webPath+"/mfBusFincApp/getListPageBank";
                break;
            case "followPactNo"://从合同号查询
                hrefPath=webPath+"/mfBusCollateralPact/getListPage";
                break;
            case "alliance"://联保体
                hrefPath=webPath+"/mfBusAlliance/getListPage";
                break;
            case "mfOaTransHis"://移交历史
                hrefPath=webPath+"/mfOaTrans/getHisListPage?typeFlag=1";
                break;
            case "cusWarehouseOrg"://仓储机构
                hrefPath = webPath+"/mfCusWarehouseOrg/getListPage";
                break;
            case "modifyfincUseDes"://修改贷款用途
                hrefPath=webPath+"/mfBusModifyFincUseDes/getListPage";
                break;
            case "queryRepayApply"://还款申请
                hrefPath = webPath+"/mfRepayApply/getListPage";
                break;
            case "cusManageQuery"://客户经理查询
                hrefPath=webPath+"/mfBusApply/inputCusManage";
                break;
            case "query-fee-refund"://退费管理
                hrefPath=webPath+"/mfBusFeeRefund/getListPage";
                break;
            case "query-fee-collect"://收费管理
                hrefPath=webPath+"/mfBusFeeCollect/getListPage";
                break;
            case "vou-pay-manage"://支出管理
                hrefPath=webPath+"/mfVouAfterPayManage/getListPage";
                break;
            case "bankAccManageBus"://账户管理
                hrefPath = webPath+"/cwCusBankAccManage/getListPage";
                break;
            case "riskAudit"://风险稽核
                hrefPath=webPath+"/mfBusRiskAudit/getAccurateListPage";
                break;
            case "risk-audit-notice"://风险稽核
                hrefPath=webPath+"/mfBusRiskAudit/getAccurateListPageForNotice";
                break;
            case "riskAuditHis"://风险稽核历史
                hrefPath=webPath+"/mfBusRiskAudit/getAuditListPage";
                break;
            case "risk-audit-gc"://风险稽核工程
                hrefPath=webPath+"/mfBusRiskAudit/getAccurateListPageGc";
                break;
            case "risk-audit-notice-gc"://风险稽核工程
                hrefPath=webPath+"/mfBusRiskAudit/getAccurateListPageForNoticeGc";
                break;
            case "risk-audit-his-gc"://风险稽核历史工程
                hrefPath=webPath+"/mfBusRiskAudit/getAuditListPage";
                break;
            case "fieldAudit"://实地核查
                hrefPath=webPath+"/MfExamineDetailController/getAuditListPage";
                break;
            case "loan-after-change"://保后变更
                hrefPath=webPath+"/mfBusLoanChange/getListPage";
                break;
            case "account"://应收账款查询
                hrefPath = webPath + "/pledgeBaseInfo/getListByAccount";
                break;
            case "cpfb"://产品发布
                hrefPath=webPath+"/mfOaNotice/getListPage?menuNo=80";
                break;
            case "nbtx"://	内部通讯
                hrefPath = webPath + "/mfOaInternalCommunication/getAcceptInfoListPage";
                break;
            case "stampApply"://用印申请
                hrefPath=webPath+"/mfStampPact/getListPage?queryType=1";
                break;
            case "query-use-seal-manage"://用印管理（工程担保）
                hrefPath=webPath+"/mfStampPact/getListPage?queryType=2";
                break;
            case "stampSealApply"://盖章管理
                hrefPath=webPath+"/mfStampPact/getListPageForSeal";
                break;
            case "billDelivery"://发票寄送
                hrefPath=webPath+"/mfInvoiceDelivery/getListPage";
                break;
            case "dissolutionGuarantee"://解保登记
                hrefPath = webPath + "/mfBusDissolutionGuarantee/getListPage";
                break;
            case "dissolutionGuaranteeHis"://解保历史
                hrefPath = webPath + "/mfBusDissolutionGuarantee/getDissolutionListPage";
                break;
            case "closingManage"://结案申请
                hrefPath=webPath+"/mfCusClosingManage/getListPage";
                break;
            case "compensatoryApply"://代偿申请
                hrefPath=webPath+"/mfBusCompensatoryApply/getListPage";
                break;
            case "compensatoryConfirm"://代偿确认
            hrefPath=webPath+"/mfBusCompensatoryApply/getListConfirmPage";
            break;
            case "recourseRegister"://追偿登记
                hrefPath=webPath+"/mfBusCompensatoryConfirm/getListPage";
                break;
            case "recourseApply"://追偿申请
                hrefPath=webPath+"/mfBusRecourseApply/getListPage";
                break;
            case "entZoned"://	企业划型
                hrefPath=webPath + "/mfCusEntZoned/getListPage";
                break;
            case "complaintMent"://	申诉管理
                hrefPath=webPath+ "/mfCusComplaintMent/getListPage";
                break;
            case "capitalImplement"://资金落实
                hrefPath=webPath+"/mfBusFincApp/getCapitalImplementListPage";
                break;
            case "capitalDetail"://资金落实详情
                hrefPath=webPath+"/mfBusFincApp/getCapitalImplementDetailListPage";
                break;
            case "filecountersign"://保密协议
                hrefPath=webPath+"/mfOaFileCountersign/getListPage?menuNo=80";
                break;
            case "query-guarantee-manage"://保函登记
                hrefPath=webPath+"/mfGuaranteeRegistration/getListPage";
                break;
            case "query-guarantee-received"://保函收函确认
                hrefPath=webPath+"/mfGuaranteeRegistration/getReceivedListPage";
                break;
            case "query-guarantee-info-maintain"://保函信息维护
                hrefPath=webPath+"/mfGuaranteeRegistration/getMaintainInfoListPage";
                break;
            case "query-guarantee-lift"://解保管理
                hrefPath=webPath+"/mfGuaranteeRegistration/getLiftListPage";
                break;
            case "query-guarantee-renew-insurance"://续保
                hrefPath=webPath+"/mfGuaranteeLift/getRenewInsuranceListPage";
                break;
            case "query-guarantee-logout-reopen"://注销重开
                hrefPath=webPath+"/mfGuaranteeLift/getLogoutReopenListPage";
                break;
            case "query-vou-after-track"://保后跟踪
                hrefPath=webPath+"/mfVouAfterTrack/getListPage";
                break;
            case "query-vou-after-risk-adjust-level"://风险调级
                hrefPath=webPath+"/mfVouAfterRiskLevelAdjust/getListPage";
                break;
            case "query-refund-manage"://退费申请
                hrefPath=webPath+"/mfRefundManage/getListPage";
                break;
            case "query-hand-amt-manage"://手续费管理
                hrefPath=webPath+"/mfHandAmtManage/getListPage";
                break;
            case "query-refund-register"://退费登记
                hrefPath=webPath+"/mfRefundRegister/getListPage";
                break;
            case "query-bank-statement-register"://银行结算单登记
                hrefPath=webPath+"/mfBankStatementRegister/getListPage";
                break;
            case "query-meeting-plan"://上会安排
                hrefPath=webPath+"/mfBusApply/getMeetingPlanListPage";
                break;
            case "query-making-meeting-summary"://决策会会议纪要
                hrefPath=webPath+"/mfMakingMeetingSummary/getListPage";
                break;
            default :
                if(myQuery!="add-item"){
                    _showTips(this);
                    break;
                }

        }
        $("#a_iframe").attr('src',hrefPath);
    }
    return {
        showQueryPage: _showQueryPage,
        bindQueryMouse:_bindQueryMouse,
        getQueryList:_getQueryList,
        myMouseOver:_myMouseOver,
        myMouseOut:_myMouseOut,
        changeState:_changeState,
        queryClick:_queryClick,
        getInnerHtml:_getInnerHtml,
        calcAttentionHeight:_calcAttentionHeight,
    };
}(window, jQuery);