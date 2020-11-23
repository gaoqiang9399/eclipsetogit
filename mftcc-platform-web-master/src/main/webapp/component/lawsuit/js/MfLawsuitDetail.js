;
var lawsuitDetail = function(window, $) {
	var _init = function () {

		$(".scroll-content").mCustomScrollbar({
			advanced : {
				updateOnContentResize : true
			}
		});
		/*
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		*/



		_initForm();
		//_followTypeChange();
		_bindClick();
		_bindFollowAjax("#lawsuit-follow");
		_updateAjax("#edit-form");
		//页面加载时查询费用金额
        $.post('/mfLawsuit/getMfLitigationExpenseList',{assetId:caseId},function(r){
            $('#cost').html(r.cost);
        },'json');
		//页面加载时查询诉讼余额
        $.post('/mfLawsuitPerformReg/getPerformRegList',{caseId:caseId},function(res){
        	var loanBalanceStr = parseInt(res.loanBalance);
        	if (loanBalanceStr < 0){
        		var num = 0;
                loanBalanceStr = num.toFixed(2);
			} else {
                loanBalanceStr = loanBalanceStr;
			}
            $('#loanBalance').html(loanBalanceStr);
            $('#actAmt').html(res.actAmt);
        },'json');
	};
    var _bindClose = function () {
        myclose_click();//关闭弹窗
    };
	var _initForm = function(){
		$("textarea").attr("placeholder","请输入内容，不能超过200字");
		$(".case-detail").find("textarea").parent().addClass("textarea-rows");
		$("#lawsuit-follow").find("tr").slice(2,12).css("display","none").find("input,select").attr("disabled","disabled");
	};
	
	//新增案件跟进
	var _insertFollow = function(){
		var url="/mfLawsuitFollow/input?caseId="+caseId;
       	top.addFlag = false;
       	top.isLawsuitSaveSuccess = false;
		top.createShowDialog(url, "案件跟进登记", '80', '70', function() {
			$.ajax({
				url:webPath+"/mfLawsuitFollow/getMfLawsuitFollowList?caseId="+caseId,
				type:'post',
				dataType:'json',
				success:function(data){
					var html = data.htmlStr;
					var execRecovHtml = data.execRecovHtml;
					var htmlStrDecision = data.htmlStrDecision;
					var lawsuitHtml = data.lawsuitHtml;
					var actAmt = data.actAmt;
					$("#mfLawsuitFollowList").empty().html(html);
					if(execRecovHtml != null && execRecovHtml != ""&& execRecovHtml != "undefined"){
					$("#executionRecoveryList").empty().html(execRecovHtml);
					}
					if(htmlStrDecision != null && htmlStrDecision != "" && htmlStrDecision != "undefined"){
					$("#decisionMediateDetail").empty().html(htmlStrDecision);
					}
                    if(lawsuitHtml !=null && lawsuitHtml !=""){
                        $("#edit-form").empty().html(lawsuitHtml);
                    }
                    if(actAmt != null && actAmt !=""){
                        //$("#actAmt").empty().html(actAmt);  20190130执行回收金额以 执行金额登记为准
					}
				}
			});
		});
	}
	var _bindClick = function(){
		//关闭跟进表单
		$(".followClose").bind("click",function(){
			$("#follow-form").css("display","none");
			var infoCnt = $("#follow-info").find(".follow-item").length;
			if(infoCnt=="0"){
				$("#follow-info").append('<div class="message">暂无跟进信息</div>');
			}
		});
		$(".updateClose").bind("click",function(){
			$(".case-detail").css("display","none");
		});
	};
	

	
	var _bindFollowAjax = function(obj){
		$(".followAjax").bind("click",function(){
			var flag = submitJsMethod($(obj).get(0), '');
			$("input[name='caseId']").val(caseId);
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {ajaxData : dataParam},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							window.top.alert(data.msg, 1);
							$("#lawsuit-follow").find("input[type=text],textarea").each(function(index){
								$(this).val("");
							});
							window.location.reload();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});
			}	
		});
	};
	var _updateAjax = function(obj){
		$(".updateAjax").bind("click",function(){
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
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});
			}	
		});
	};
	/**
	 * 跟进类型绑定change事件，动态显示表单字段
	 */
	var _followTypeChange = function(){
		$("select[name='followType']").bind("change",function(){
			var followType = $(this).val();
			_initFollowForm("#follow-form",followType);
		});
	};
	var _deleteFollow = function(obj){
		var followId = $(obj).parent().attr("id");
		var followType = $(obj).parent().parent().find("input").val();
		window.top.alert(top.getMessage("CONFIRM_DELETE"), 2,function(){
			$.ajax({
				url:webPath+"/mfLawsuitFollow/deleteAjax",
				data:{followId:followId},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						window.top.alert(data.msg, 1);
						$("#lawsuit-follow").find("input[type=text],textarea").each(function(index){
							$(this).val("");
						});
						$(obj).parents(".follow-item").remove();
						var infoCnt = $("#follow-info").find(".follow-item").length;
						if(infoCnt=="0"){
							$("#follow-form").css("display","none");
							$("#follow-info").append('<div class="message">暂无跟进信息</div>');
						}
						var followDate = "";
						if(data.followDate == ""){
							followDate = "<em style='color:#b1b1b1; font-style: italic;'>未登记</em>";
						}else{
							followDate = data.followDate;
						}
						if("1" == followType){
							$(".acceptDate").html(followDate);
						}
						else if("2" == followType){
							$(".trialDate").html(followDate);
						}else if("3" == followType){
							$(".judgDate").html(followDate);
						}else if("4" == followType){
							$(".actDate").html(followDate);
						} 
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error:function() {
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		});
	};
	/**
	 * 弹窗中显示跟进详情
	 */
	var _getFollowAjax = function(obj){
		var followId = $(obj).parent().attr("id");
		var followType = $(obj).parents(".follow-info").find(".follow-type").val();
		$.ajax({
			url:webPath+"/mfLawsuitFollow/getByIdAjax",
			data:{
				followId : followId
			},
			type:"post",
			dateType:"json",
			success:function(data){
				LoadingAnimate.stop();
				if (data.flag == "success") {
					var htmlStr = data.htmlStr;
					$("#follow-detail").html(htmlStr);
					artdialog = dialog({
	             		title:'跟进详情',
	             		id : "followDetail",
	             		content:$("#follow-detail"),
	             		width:700,
	             		height:365,
	             		backdropOpacity:0,
	             		onshow:function(){
	             			_initFollowForm("#follow-detail",followType);
//	             			$("#follow-detail").find("textarea").parent().css("width","75%");
	             		},onclose:function(){
	             			
	             		}
	             	});
	             	artdialog.showModal();
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error:function(){
				
			}
		});
	};
	//根据跟进类型显示表单字段
	var _initFollowForm = function(obj,followType) {
		$(obj).find("tr").slice(1,16).css("display","none").find("input,select").attr("disabled","disabled");
		switch(followType){
		case "1":
			$(obj).find("tr").slice(1,2).removeAttr("style").find("input,select").removeAttr("disabled");
			break;
		case "2":
			$(obj).find("tr").slice(2,4).removeAttr("style").find("input,select").removeAttr("disabled");
			break;
		case "3":
			$(obj).find("tr").slice(4,9).removeAttr("style").find("input,select").removeAttr("disabled");
			break;
		case "4":
			$(obj).find("tr").slice(9,17).removeAttr("style").find("input,select").removeAttr("disabled");
			break;
		default :
			break;
		};
	};
	/**
	 * 选择日期
	 */
	var _myselectrili = function(obj){
		var name = $(obj).prev().attr("name");
		selectrili(obj,name);
	};
	/**
	 * 返回案件列表页面
	 */
	var _returnList = function(){
		LoadingAnimate.start();
		if(top.lawsuitFlag=='query'){
			myclose();
		}else{
			var url = webPath+"/mfLawsuit/getByPact?pactNo="+pactId;
			var obj = $(top.window.document).find("body");
			obj.find("#bigFormShowiframe").attr("src",url);
		}
	};
    var _bindInsertAjax = function(obj){
    		//alert($(obj));
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
                            $('#loanBalance').html(data.loanBalance);
                        } else {
                            window.top.alert(data.msg, 0);
                        }
                    },
                    error : function() {
                        LoadingAnimate.stop();
                        window.top.alert(data.msg, 0);
                        //alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
                    }
                });
            }
	};
    /**
     * 费用登记
     */
	var _costReg = function(){
        var url="/mfLawsuit/costReg?caseId="+caseId;
        top.openBigForm(url,"费用登记", function(){
            $.post('/mfLawsuit/getMfLitigationExpenseList',{assetId:caseId},function(r){
                $('#mfLitigationExpenseApplyList').html(r.htmlStr);
                $('#cost').html(r.cost);
            },'json');
		},80,70);
	};
    /**
     * 执行金额登记
     */
    var _performReg = function(){
        var url="/mfLawsuitPerformReg/input?caseId="+caseId;
        top.openBigForm(url,"执行金额登记", function(){
            $.post('/mfLawsuitPerformReg/getPerformRegList',{caseId:caseId},function(r){

                $('#mfLawsuitPerformReg').html(r.htmlStr);
                $('#loanBalance').html(r.loanBalance);
                $('#actAmt').html(r.actAmt);
            },'json');
        },80,70);
    };
    /**
     * 打开费用总额明细表单
     */
    var _getFeeSumDetail = function(){
    	var url =webPath+"/mfLitigationExpenseApply/getFeeSumDetail?assetId="+caseId;
        top.openBigForm(url,"费用总额明细",null,80,70);
    };
    /**
     * 返回催收
     */
    var _returnCollect = function(){

    };
    /**
     * 结案归档
     */
    var _filling = function(){
        var archivePactStatus = "01";
        var pactSts = '6';
        var flag = 'lawfile';
        if(pactSts=="7"){
            archivePactStatus="02";
        }
        var dataParam = '?optType=03&archivePactStatus='+archivePactStatus+'&cusNo='+cusNo+'&appId='+caseId+'&pactId='+assetId+'&flag='+flag;
        top.window.openBigForm(webPath+'/archiveInfoMain/getArchiveNodes'+dataParam,"结案归档",function(){});
    };
    //文件打印
    var _filePrint=function (){
        var tempParm = "&cusNo="+cusNo+"&appId="+'app18071616223922911'+"&pactId="+''+"&fincId="+''+"&pleId="+'';
        top.window.openBigForm(encodeURI(webPath+"/mfTemplateBizConfig/getPrintFileList?relNo="+''+"&modelNo="+'lawsuit'+"&generatePhase=05|06|07|08"+tempParm),'文件打印',function(){});
    }
    // 涉案资产
    var _lawsuitAssets = function(){
    	top.window.openBigForm(webPath + "/mfAssetsPreservation/getListPageByCaseId?caseId=" + caseId,'涉案资产',function(){});
    }
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		deleteFollow : _deleteFollow,
		myselectrili : _myselectrili,
		getFollowAjax : _getFollowAjax,
		returnList : _returnList,
		insertFollow:_insertFollow,
        bindInsertAjax : _bindInsertAjax,
        bindClose : _bindClose,
        costReg : _costReg,
        returnCollect : _returnCollect,
        filling : _filling,
        filePrint : _filePrint,
        lawsuitAssets:_lawsuitAssets,
        getFeeSumDetail:_getFeeSumDetail,
        performReg:_performReg,

	};
}(window, jQuery);