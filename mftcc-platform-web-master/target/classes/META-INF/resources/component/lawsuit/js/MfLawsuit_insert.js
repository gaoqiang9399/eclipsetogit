;
var lawsuitInsert = function(window, $) {
	var _init = function () {
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//编辑页面不允许编辑
		if(query =="editPage"){
			//$("#lawsuit .form-control").attr("readonly",'true');
			$("select[name=caseType]").removeAttr("readonly");
			$("select[name=caseState]").removeAttr("readonly");
			$("textarea[name=caseRemark]").removeAttr("readonly");
			$("textarea[name=caseResult]").removeAttr("readonly");
		}
		_bindClose();
		_bindInsertAjax("#lawsuit");
	};
	
	var _bindClose = function () {
		$(".cancel").bind("click", function(event){
			myclose();
		});
	};
	var _selectCus = function(obj){
		var callback = new Object();
		var clickName = $(obj).prev().attr("name");//判断选择原告还是被告
		var pactId = $("input[name='pactId']").val();
		if(clickName == 'accuserName'){//选择原告
			callback = lawsuitInsert.getCusDialogYG;
		}else{
			callback = lawsuitInsert.getCusDialogBG;
		}
		selectCusDialog(callback,pactId,"选择客户","3");
	};
	//选择原告
	var _getCusDialogYG = function(obj){
		$("input[name='accuserName']").val(obj.cusName);
		$("input[name='accuserId']").val(obj.cusNo);
	};
	//选择被告
	var _getCusDialogBG = function(obj){
		$("input[name='defendantName']").val(obj.cusName);
		$("input[name='defendantId']").val(obj.cusNo);
	};
	var _controlMax = function(){
		var caseAmtVal = $("input[name='caseAmt']").val();
		if(caseAmtVal>="0"){
			
		}else{
			$("input[name='caseAmt']").val("");
		}
		
	};
	
	var _controlcorpusMax = function(){
		var corpusVal = $("input[name='corpus']").val();
		if(corpusVal>="0"){
			
		}else{
			$("input[name='corpus']").val("");
		}
		
	};
	var _bindInsertAjax = function(obj){
		$(".insertAjax").bind("click",function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							window.top.alert(data.msg, 1);
							 top.addFlag = true;
							 myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}	
		});

        $(".insertAjax1").bind("click",function(event){
            var flag = submitJsMethod($(obj).get(0), '');
            if (flag) {
                var url = $(obj).attr("action");
                var dataParam = JSON.stringify($(obj).serializeArray());
                LoadingAnimate.start();
                $.ajax({
                    url : url,
                    data : {
                        ajaxData : dataParam
                    },
                    type : 'post',
                    dataType : 'json',
                    success : function(data) {
                        LoadingAnimate.stop();
                        if (data.flag == "success") {
                            myclose_click();
                        } else {
                            window.top.alert(data.msg, 0);
                        }
                    },
                    error : function() {
                        LoadingAnimate.stop();
                        alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                    }
                });
            }
        });
	};
   var _click1 = function(){
       var url="/component/lawsuit/AssetManageApply_getList.jsp";
       openCreatShowDialog(url,"选择诉讼申请",70,70,function(data){
           if(data){
               $("input[name=cusName]").val(data.cusName);
               $("input[name=cusNo]").val(data.cusNo);
               $("input[name=assetId]").val(data.assetId);
               $("input[name=caseApplyAmt]").val(data.caseApplyAmt);
               $("input[name=acountManager]").val(data.acountManager);
               $("input[name=company]").val(data.company);


               $("input[name=defendantName]").val(data.cusName);
           }
       })
    };
    var _click2 = function(){

        var url="/component/lawsuit/CusCustomer_getList.jsp";
        openCreatShowDialog(url,"选择客户",70,70,function(data){
            if(data){
                $("input[name=cusName]").val(data.cusName);
                $("input[name=cusNo]").val(data.cusNo);
                $("input[name=acountManager]").val(data.acountManager);
                $("input[name=company]").val(data.company);

                $("input[name=assetId]").val('');
                $("input[name=caseApplyAmt]").val('');

                $("input[name=defendantName]").val(data.cusName);
                _getFincInfoData(data.cusNo);
            }
        })
    }
     var _chooseCusSource = function(){
	    var obj= $('input[name=cusName]');
	     if(obj.next(".pops-value").length>0){//如果选择已经初始化过一次，重新初适化的时候移除前一个
			//$(".pops-value").remove();
			obj.next(".pops-value").css("display","none");
			}
	     	bindDataSource(obj, '61', 'cusNo', '选择客户', false, null, null,false,function(data){
			$("input[name=appName]").val(data.appName);
			$("input[name=pactNo]").val(data.pactNo);
			$("input[name=pactId]").val(data.pactId);
     	});
     }
    //初始化选择借据数据源
	var flag = "0";
	var _getFincInfoData=function(cusNo){
		jQuery.ajax({
			url : webPath+"/mfBusFincApp/getJSONArrayByCusNoAjax?cusNo="+cusNo,
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					if(data.items != null && data.items != ""){
                        if(flag == "0"){
                            $("input[name=pactInfo]").popupSelection({
                                searchOn:true,//启用搜索
                                inline:true,
                                multiple:false,//单选
                                items:data.items,
                                changeCallback:_refreshFincInfoDetailList
                            });
                            flag = "1";
						}else {
                            $("input[name=popspactInfo]").popupSelection("updateItems",data.items);
						}

					} else {
                        $("input[name=popspactInfo]").popupSelection("updateItems","");
						_refreshFincInfoDetailList();
					}
				}else{
				window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(data.msg, 0);
				//window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	var _refreshFincInfoDetailList=function(){//动态刷新表单底部的明细列表
		var fincId=$("input[name=pactInfo]").val();
		if (!fincId) {
			$("#fincInfoDetailList").html("");
			$("#fincInfoDetailListdiv").hide();
			$("input[name=loanNum]").val("");
			$("input[name=totalLoanAmt]").val("");
			$("input[name=loanBalance]").val("");
			$("input[name=overduePrincipal]").val("");
			$("input[name=overdueInterest]").val("");
			$("input[name=totalPenalty]").val("");
			return ;
		}
		jQuery.ajax({
			url : webPath+"/mfBusFincApp/getFincInfoHtmlByfincId",
			type : "POST",
			dataType : "json",
			data:{tableId:"tablecusFincInfoDetailList",fincId:fincId},
			async:false,
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					$("#fincInfoDetailList").html(data.tableData);
					$("#fincInfoDetailListdiv").show();
					$("input[name=loanNum]").val(data.loanNum);
					$("input[name=totalLoanAmt]").val(data.totalLoanAmt);
					$("input[name=loanBalance]").val(data.loanBalance);
					$("input[name=overdueInterest]").val(data.overdueInterest);
					$("input[name=totalPenalty]").val(data.totalPenalty);
					$("input[name=normalInterest]").val(data.normalInterest);
					$("input[name=compoundInterest]").val(data.compoundInterest);
					$("input[name=caseAmt]").val(data.loanBalance);
					$("input[name=pactNo]").val(data.pactNo);
					$("input[name=pactId]").val(data.pactId);
					$("input[name=appName]").val(data.appName);
					$("input[name=appAmt]").val(data.appAmt)

				}else{
				window.top.alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				window.top.alert(data.msg, 0);
				//window.top.alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
		//_sumAmt();
	}
	var _checkCustomer=function(){
		var cusNo =$("input[name=cusNo]").val();
		if(cusNo==null || cusNo=="" || cusNo=="undefined"){
			window.top.alert("请先选择客户", 0);
		}
	}
	var _sumAmt = function(){
		const caseAmtList = [
			$("input[name=loanBalance]").val(),
					$("input[name=overdueInterest]").val(),
					$("input[name=totalPenalty]").val(),
					$("input[name=normalInterest]").val(),
					$("input[name=compoundInterest]").val()
			];
		const caseAmt = CalcUtil.sum(caseAmtList);
		$("input[name='caseAmt']").val(fmoney(caseAmt, 2));
	}
	var _setCaseAmt = function () {//手动输入贷款余额时给诉讼金额赋值
		var loanBalance = $("input[name=loanBalance]").val();
        $("input[name=caseAmt]").val(fmoney(loanBalance, 2));
    }
    //新增授信额度银行地区初始化
    var _caseNameInit = function(){
        $("input[name=cusName]").popupList({
            searchOn : true, //启用搜索
            multiple : false, //false单选，true多选，默认多选
            ajaxUrl : webPath + "/mfBusCompensatoryApply/getLawsuitInitAjax",// 请求数据URL
            valueElem : "input[name=cusNo]",//真实值选择器
            title : "选择客户",//标题
            changeCallback : function(elem) {//回调方法
                var sltVal = elem.data("selectData");
                $("input[name=pactNo]").val(sltVal.pactNo);
                $("input[name=pactId]").val(sltVal.pactId);
            },
            tablehead : {//列表显示列配置
                "cusName" : "客户名称",
                "pactNo" : "合同编号"
            },
            returnData : {//返回值配置
                disName : "cusName",//显示值
                value : "cusNo"//真实值
            }
        });
    }
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		selectCus : _selectCus,
		getCusDialogYG : _getCusDialogYG,
		getCusDialogBG : _getCusDialogBG,
		controlMax : _controlMax,
		controlcorpusMax : _controlcorpusMax,
		click1 : _click1,
        click2 : _click2,
        getFincInfoData:_getFincInfoData,
        checkCustomer:_checkCustomer,
        chooseCusSource:_chooseCusSource,
        sumAmt:_sumAmt,
		setCaseAmt:_setCaseAmt,
        caseNameInit:_caseNameInit
	};
}(window, jQuery);