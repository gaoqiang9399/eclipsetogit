;
var confirmTuningReport = function(window, $) {
	var _init = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		if(typeof(credtitFlag)!='undefined' &&  credtitFlag == '1'){
        }else{
            bindVouTypeByKindNo($("input[name=vouType]"), kindNo);
        }
		/*if($("input[name=coborrName]").is(':visible')){
            $("input[name=coborrName]").popupList({
                searchOn: true, //启用搜索
                multiple: true, //false单选，true多选，默认多选
                ajaxUrl :  webPath+"/mfLoanApply/getCoborrNumList?cusNo=" + cusNo,// 请求数据URL
                valueElem:"input[name=coborrNo]",//真实值选择器
                title: "共同借款人",//标题
                elemEdit: true,
                changeCallback:function(elem){//回调方法
                    BASE.removePlaceholder($("input[name=coborrNum]"));
                    var sltVal = elem.data("selectData");
                    $("select[name=popsidType]").popupSelection("selectedById", sltVal.legalIdType);
                    $.ajax({
                        type: "post",
                        dataType: 'json',
                        url:webPath+ "/mfBusApply/getCocoborrNum",
                        data:{cusNos:$("input[name='coborrNo']").val()},
                        async: false,
                        success: function(data) {
                            if(data.flag=="success"){
                                $("#tableHtml").empty().html(data.tableData);
                                $("input[name='coborrNum']").val(data.idNums);
                                $("input[name='certificateNum']").val(data.idNums);
                            }else{
                                window.top.alert(data.msg,0);
                                $("input[name='coborrName']").val(null);
                                $("input[name='coborrName']").next().html("");
                            }
                        }
                    });
                },
                tablehead:{//列表显示列配置
                    "cusName":"共同借款人",
                    "idNum":"借款人证件号码"
                },
                returnData:{//返回值配置
                    disName:"cusName",//显示值
                    value:"cusNo"//真实值
                }
            });
		}*/

		//渠道来源选择组件				    
	    /*if($('input[name=channelSource]').length > 0){
			$('input[name=channelSource]').popupList({
				searchOn: true, //启用搜索
				multiple: false, //false单选，true多选，默认多选
				ajaxUrl:webPath+"/mfBusTrench/getChannelAjax",//请求数据URL
				valueElem:"input[name=channelSourceNo]",//真实值选择器
				title: "选择渠道来源",//标题
				labelShow:false,
				tablehead:{//列表显示列配置
					"trenchUid":"渠道编号",
					"trenchName":"渠道名称"
				},
				returnData:{//返回值配置
					disName:"trenchName",//显示值
					value:"trenchUid"//真实值
				}
			});
	    }*/
		
//		$('input[name=manageOpName2]').popupList({
//			searchOn: true, //启用搜索
//			multiple: false, //false单选，true多选，默认多选
//			ajaxUrl:webPath+"/sysUser/findSameDownBrAndRoleByPageAjax",//请求数据URL
//			valueElem:"input[name=manageOpNo2]",//真实值选择器
//			title: "选择办理人员",//标题
//			changeCallback:function(elem){//回调方法
//				BASE.removePlaceholder($("input[name=manageOpName2]"));
//			},
//			tablehead:{//列表显示列配置
//				"opName":"操作员编号",
//				"opNo":"操作员名称"
//			},
//			returnData:{//返回值配置
//				disName:"opName",//显示值
//				value:"opNo"//真实值
//			}
//		});
// 		_bindClose();
	};

	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	};
	



	var _insertTuningReport = function(obj, temporaryStorage) {
		    if(temporaryStorage== '1'){
                var url = $(obj).attr("action");
                var dataParam = JSON.stringify($(obj).serializeArray());
                var datas = [];
                if(busModel=='12') {
                    $("#busfee-list").find("tbody tr")
                        .each(
                            function (index) {
                                var entity = {};
                                $thisTr = $(this);
                                entity.id = $thisTr.find("input[name=id]").val();
                                entity.itemNo = $thisTr.find("input[name=itemNo]")
                                    .val();
                                entity.feeType = $thisTr.find(
                                    "select[name=feeType]").val();
                                entity.takeType = $thisTr.find(
                                    "select[name=takeType]").val();
                                entity.rateScale = $thisTr.find(
                                    "input[name=rateScale]").val().replace(
                                    /,/g, "");
                                if (busModel == '12') {
                                    entity.receivableFeeAmt = $thisTr.find(
                                        "input[name=receivableFeeAmt]").val().replace(
                                        /,/g, "");
                                }
                                datas.push(entity);
                            });
                }
                $.ajax({
                    url : url,
                    data: {ajaxData: dataParam,ajaxDataList : JSON.stringify(datas), appId: confirmTuningReport.appId,'temporaryStorage': temporaryStorage,'nodeNo':nodeNo},
                    async : false,
                    success : function(data) {
                        if (data.flag == "success") {
                            window.top.alert(data.msg, 3);
                            //尽调报告处    处理回调方法要的参数
                            top.tuningReport = true;
                            top.refsh = true;
                            top.appSts = data.appSts;
                            top.applyInfo = data.applyInfo;
                            top.applyDetail = data.applyDetail;
                            top.appreportDetail = data.appreportDetail;
                            top.flag = true;
                            top.coborrNo = data.coborrNo;
                            top.appId = data.appId;
                            window.location.reload();
                        } else {
                            window.top.alert(data.msg, 0);
                        }
                    },error : function() {
                        alert(top.getMessage("ERROR_SERVER"),0);
                    }
                });
            }else{
                var flag = submitJsMethod($(obj).get(0), '');
                if (flag) {
                    next(obj, temporaryStorage);
                }
		    }
	};
	
	function  next(obj, temporaryStorage) {
        //根据流程判断是否必须选择下一岗位人员以及提示是否进行下一步操作    wkf_bus_base.jsp
        doCommitNextUser(wkfAppId,function(next){
            //验证业务视角登记的客户必填表单是否都已经填写完毕
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            var datas = [];
            if(busModel=='12') {
                $("#busfee-list").find("tbody tr")
                    .each(
                        function (index) {
                            var entity = {};
                            $thisTr = $(this);
                            entity.id = $thisTr.find("input[name=id]").val();
                            entity.itemNo = $thisTr.find("input[name=itemNo]")
                                .val();
                            entity.feeType = $thisTr.find(
                                "select[name=feeType]").val();
                            entity.takeType = $thisTr.find(
                                "select[name=takeType]").val();
                            entity.rateScale = $thisTr.find(
                                "input[name=rateScale]").val().replace(
                                /,/g, "");
                            if (busModel == '12') {
                                entity.receivableFeeAmt = $thisTr.find(
                                    "input[name=receivableFeeAmt]").val().replace(
                                    /,/g, "");
                            }
                            datas.push(entity);
                        });
            }
            $.ajax({
                url : url,
                data: {ajaxData: dataParam, appId: confirmTuningReport.appId,ajaxDataList : JSON.stringify(datas),nextUser: next, 'temporaryStorage': temporaryStorage,'nodeNo':nodeNo},
                async : false,
                success : function(data) {
                    if (data.flag == "success") {
                        window.top.alert(data.msg, 3);
                        //尽调报告处    处理回调方法要的参数
                        top.tuningReport = true;
                        top.refsh = true;
                        top.appSts = data.appSts;
                        top.applyInfo = data.applyInfo;
                        top.applyDetail = data.applyDetail;
                        top.appreportDetail = data.appreportDetail;
                        top.flag = true;
                        top.coborrNo = data.coborrNo;
                        top.appId = data.appId;
                        if(temporaryStorage==1){
                            window.location.reload();
                        }else{
                            myclose_click();
                        }

                    } else {
                        if(!data.uploadFalg){
                            window.top.alert(data.msg, 0);
                        }else{
                            if(data.exsitRefused){// 存在业务拒绝
//									$(".cancel").click();
//									top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+appId,'风险拦截信息',function(){});
                                dialog({
                                    id:"userDialog",
                                    title:'风险拦截信息',
                                    url: webPath+'/riskForApp/preventList?relNo='+appId,
                                    width:900,
                                    height:500,
                                    backdropOpacity:0,
                                    onshow:function(){
                                        this.returnValue = null;
                                    },onclose:function(){
                                        top.flag = true;
                                        top.riskFlag=true;
                                        myclose_click();
                                    }
                                }).showModal();

                            }else{
                                top.tuningReport = true;
                                top.refsh = true;
                                top.flag = true;
                                top.appSts = data.appSts;
                                top.applyInfo = data.applyInfo;
                                top.applyDetail = data.applyDetail;
                                window.top.alert(data.msg, 0);
                                if(temporaryStorage==1){
                                    window.location.reload();
                                }else{
                                    myclose_click();
                                }
                            }
                        }
                    }
                },error : function() {
                    alert(top.getMessage("ERROR_SERVER"),0);
                }
            });

        });
    }
    var _changeType = function(){
        $("input[name='firstApprovalUserName']").val("");
        $("input[name='externalCheckUserName']").val("");
        $("input[name='firstApprovalUser']").val("");
        $("input[name='externalCheckUser']").val("");
    }

	/** 客户经理放大镜回调 */
	var _getCusMngNameDialog = function(userInfo) {
		$("input[name=cusMngName]").val(userInfo.opName);
		$("input[name=cusMngNo]").val(userInfo.opNo);
	};

    var _calcQuotaSts = function (){
        $("#confirmTuningReportDiv").css('display','none');
        if(saveOnly4== '0'){
            $("#saveBtn0").css('display','none');
        }else if(saveOnly4== '1'){
            $("#saveBtn1").css('display','none');
        }

        $("#quotaCalcDiv").css('display','block');
        $("#saveBtnCalc").css('display','block');
        $(top.window.document).find("#myModalLabel").text('额度测算');
        $("#quotaCalc").find("input[name='creditSum']").parent().parent().prev().find('label').text('申请金额');
    }

    var _changeFormDisplay = function () {
        $("#quotaCalcDiv").css('display','none');
        $("#saveBtnCalc").css('display','none');

        $("#confirmTuningReportDiv").css('display','block');
        if(saveOnly4== '0'){
            $("#saveBtn0").css('display','block');
        }else if(saveOnly4== '1'){
            $("#saveBtn1").css('display','block');
        }
        $(top.window.document).find("#myModalLabel").text(title);
    }

    var _calcQuotaAjax = function(obj) {
        var flag = submitJsMethod($(obj).get(0), '');
        if (flag) {
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            $.ajax({
                url : url,
                data: {ajaxData: dataParam},
                async : false,
                success : function(data) {
                    if (data.flag == "success") {
                        _changeFormDisplay();
                        window.top.alert(data.msg,1);
                        $("input[name='quotaCalc']").val(data.quotaCalc);
                    }else{
                        window.top.alert(data.msg, 0);
                    }
                },error : function() {
                    alert(top.getMessage("ERROR_SERVER"),0);
                }
            });
        }
    };
    var _glqysqcybChange =function () {
        var glqysqcyb = $("select[name='glqysqcyb']").val();
        if(glqysqcyb==1){
            $("select[name='relCorpApply']").val("0");
        }
    }
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
        changeType : _changeType,
		insertTuningReport:_insertTuningReport,
		getCusMngNameDialog : _getCusMngNameDialog,
        changeFormDisplay : _changeFormDisplay,
        calcQuotaSts:_calcQuotaSts,
        calcQuotaAjax:_calcQuotaAjax,
        glqysqcybChange:_glqysqcybChange
	};
}(window, jQuery);