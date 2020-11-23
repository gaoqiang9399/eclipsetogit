$(function(){
	/**滚动条**/
	$("body").mCustomScrollbar({
		advanced:{
			updateOnContentResize:true
		},
		callbacks: {//解决单字段编辑输入框位置随滚动条变化问题
			whileScrolling: function(){
				if ($(".changeval").length>0) {
					$(".changeval").css("top", parseInt($(".changeval").data("top")) + parseInt(this.mcs.top) - $(".changeval").data("msctop"));
				}
			}
		}
	});
	//判断详情页面还款按钮的显隐
	if(huankuanFlag=="1"){//还款操作不能点击
        $("#repay").attr("disabled",false);
        $("#repay").removeClass("btn-opt-dont");
        $("#repay").addClass("btn-opt");
        $("#buyerRepay").attr("disabled",false);
        $("#buyerRepay").removeClass("btn-opt-dont");
        $("#buyerRepay").addClass("btn-opt");
      }else{
        $("#repay").attr("disabled",true);
        $("#repay").removeClass("btn-opt");
        $("#repay").addClass("btn-opt-dont");
        $("#buyerRepay").attr("disabled",true);
        $("#buyerRepay").removeClass("btn-opt");
        $("#buyerRepay").addClass("btn-opt-dont");
	}

	if(jiefuFlag=="1"){
        //尾款结付 按钮相关控制
        $("#tailPayment").attr("disabled",false);
        $("#tailPayment").removeClass("btn-opt-dont");
        $("#tailPayment").addClass("btn-opt");
	}else {
        //尾款结付 按钮相关控制
        $("#tailPayment").attr("disabled",true);
        $("#tailPayment").removeClass("btn-opt");
        $("#tailPayment").addClass("btn-opt-dont");
	}



	top.LoadingAnimate.stop();

});

//展示6个按钮,多的按钮显示到更多里面
function showSixButton(){
	var buttonShowNum = 0;
	$('.btn-group.pull-right').find('button').each(function (i){
		var buttonId = $(this).attr('id');
		var onclick = $(this).attr("onclick");
		if(buttonId!='moreButton'){//更多按钮
			if($(this).css('display') !='none'){
				buttonShowNum++;
			}
		}
		if(buttonShowNum>6){
			if(buttonId!='moreButton'){//更多按钮
				$(this).removeAttr("onclick");
				var thisHtml = $(this).prop("innerHTML");
				$(this).remove();	
				$('#moreUl').append('<li class="btn-opt" role="presentation" onclick='+onclick+'><div id="'+buttonId+'"class="more-li-div">'+thisHtml+'</div></li>');
			}
		}
	});
	if(buttonShowNum>6){
		$('#moreDiv').show();
	}else{
		$('#moreDiv').hide();
	}
	$('#allButtonDiv').show();
}

function setBlockTitle(showType,title,name){
	var htmlStr = "";
	var collapseButtonHtml="<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"+name+"'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>";
	if(showType == "1"){
		htmlStr = htmlStr + "<div class='dynamic-block'>"
		+ "<div class='form-table'>"
		+ "<div class='title'><span class='formName'><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"' name='"+name+"'>"
		+"<form action='"+name+"Ajax_updateAjaxByOne' id='"+name+"Ajax_updateByOne_action'>"+htmlStr+"</form>"
		+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-new-block").before(htmlStr);
	}else if(showType == "2"){
		var tableStr = "";
		htmlStr = htmlStr + "<div class='dynamic-block'>"
		+ "<div class='list-table'>"
		+ "<div class='title'><span><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content margin_left_15 collapse in' id='"+name+"' name='"+name+"'>"+tableStr+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-new-block").before(htmlStr);
	}else if(showType == "3"){
		var formInfo = "<div class='title'><span><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"	
		 + "<div class='content collapse in' style='margin-top:15px;' id='"+name+"' name='"+name+"'></div>";
		htmlStr = htmlStr + "<div class='dynamic-block'><div class='form-table'>"
				+ formInfo +  "</div></div>";
			$(".block-new-block").before(htmlStr);
	
	}
}

	function setBlock(showType,title,name,getUrl){
		$.ajax({
			url:getUrl,
			type:'post',
			dataType:'html',
			success:function(data){
				var $html = $(data);
                var formStr;
				if(showType == "1" ){
					setBlockTitle(showType,title,name);
					formStr = $html.find("form").prop("outerHTML");
					$(".content[name='"+name+"']").html(formStr);
				}else if(showType == "2"){
					if($html.find("table #tab tr").length>0){
						setBlockTitle(showType,title,name);
						var tableStr = $html.find("table").prop("outerHTML");
						$(".content[name='"+name+"']").html(tableStr);
					}
				}else if(showType == "3"){
					setBlockTitle(showType,title,name);
					formStr = $html.find(".bigForm_content_form").prop("outerHTML");
					$(".content[name='"+name+"']").html(formStr);
				}
				
				//adjustheight();
			},error:function(){
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
	};
	//正常还款
	function repayment(){
		top.flag=false;
		top.window.openBigForm(webPath+'/mfBusFincAppMain/repaymentJsp?fincMainId='+fincMainId,'还款信息',repayCloseCallBack);
	}
	//买方付款
	function buyerRepayment(){
		top.flag=false;
        top.window.openBigForm(webPath+'/mfBusFincAppMain/repaymentJspForFincMain?fincMainId='+fincMainId,'买方付款',repayCloseCallBack);
	}
	//尾款结付
	function tailPayment(){
		top.flag=false;
		top.window.openBigForm(webPath+'/mfBusFincAppMain/tailPaymentForFincMain?fincMainId='+fincMainId,'尾款解付',repayCloseCallBack);
	}
	function  repayCloseCallBack() {
        window.location.href=webPath+"/mfBusFincAppMain/getById?pactId="+pactId+"&appId="+appId+"&fincMainId="+fincMainId+"&busEntrance=finc_main";
    }
	/**
	 * 是否允许提前还款 
	 * @param dueNo 借据号
	 * @returns {Boolean}
	 */
	function  ifCanPreRepay(dueNo){
		var flag=true;
		$.ajax({
			url:webPath+'/mfPreRepayApply/ifCanPreRepayAjax',
			data:{"fincId":dueNo},
			type : "POST",
			dataType : "json",
			async:false,
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					var res=data.result;
					if(res=="1"){
						flag=true;
					}else
						flag=false;
				} else {// 不满足提前还款条件
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
		return flag;
	}
	
	// 提前还款页面跳转
	function preRepaymentJsp() {
		//检查提前还款是否需要申请
		var  ifCanPreRepayVal=ifCanPreRepay(fincId);

		if(!ifCanPreRepayVal){
			window.top.alert(top.getMessage("NOT_ALLOW_REPAYMENT", {"content1":"不符合规则","content2":"提前还款"}), 1);
			return ;
		}
		$.ajax({
			url:webPath+'/mfPreRepayApply/checkPreRepayApplyAjax',
			data:{"fincId":fincId},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					toPreRepaymentJsp(data.preRepayApplyFlag,data.preRepayAppId);
				} else {// 不满足提前还款条件
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
	};
	function dataDownload(){
		window.top.location.href = encodeURI(webPath+"/docUpLoad/getFileDownload_ziliao");
	}
	function toPreRepaymentJsp(preRepayApplyFlag,preRepayAppId){
		// 判断是否能够提前还款
		// 计算提前还款 利息 和 违约金
		$.ajax({
			url : webPath+'/mfRepayment/checkTiQianHuanKuanAjax',
			data : {
				"fincId" : fincId
			},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
				if (data.flag == "success") {
					if(preRepayApplyFlag=="1"){//需要提前还款申请
						top.preRepayApplyFlag=preRepayApplyFlag;
						top.preRepayAppId = preRepayAppId;
						top.window.openBigForm(webPath+'/mfPreRepayApply/getById?preRepayAppId=' + preRepayAppId,'提前还款信息',repayCloseCallBack);
					}else{
						// 能够提前还款
						top.window.openBigForm(webPath+'/mfRepayment/mfPrepaymentJsp?fincId=' + fincId,'提前还款信息',repayCloseCallBack);
					}
				} else {// 不满足提前还款条件
					alert(data.msg, 0);
				}
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION", " "), 0);
			}
		});
	};
	

	//更新提前还款申请信息
	function updatePreRepayApply(preRepayAppId){
		$.ajax({
			url:webPath+"/mfPreRepayApply/updateAppStsAjax",
			type:'post',
			data : {"preRepayAppId":preRepayAppId,"preRepayAppSts":"4"},
			dataType:'json',
			success:function(data){
				pubMfRepayPlanList.init();
				pubMfRepayHistoryList.init();
				pubMfRepayFeeHistoryList.init();
				var tableHtmlRepayPlan=data.tableHtmlRepayPlan;//收益计划
				var tableHtmlRepayHistory=data.tableHtmlRepayHistory;
				if(data.fincSts=="7"){					
					$("#repay").attr("disabled",true);
					$("#repay").removeClass("btn-opt");
					$("#repay").addClass("btn-opt-dont");
					$("#prerepay").attr("disabled",true);
					$("#prerepay").removeClass("btn-opt");
					$("#prerepay").addClass("btn-opt-dont");
				}				
			},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
	}
		
			
	function getNextBusPoint(){ 
		$.ajax({
			url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId+"&appId="+appId,
			type:'post',
			dataType:'json',
			success:function(data){  
				wkfFlag = data.wkfFlag;
				if(wkfFlag == '0'){
				}else if(wkfFlag == '1'){
					var url = data.url;
					var title=data.title;
					var nodeName = data.nodeName;
					var tmpUrl=url.split("&")[0];
					var popFlag = tmpUrl.split("?")[1].split("=")[1];
					var result = data.result;
					
					$(".block-next").empty();
					$(".next-div").unbind();
						if(nodeName=="review_rep" || (popFlag == '0' && result== '1')){
							$(".block-next").append("<span>业务在"+title+"阶段</span>");
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
						}else{ 
							$(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>"+title+"&gt;&gt;</a></span>");
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
							$(".next-div").click(function(){
								toNextBusPoint(url,title,nodeName);
							}); 
						}
						if(nodeName=="review_finc"||nodeName=="review_rep"  || (popFlag == '0' && result== '1')){
							$(".block-next").append("<span>业务在"+title+"阶段</span>");
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
						}/*else{
							$(".block-next").append("<span>暂时没有权限操作该节点</span>");
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
						}*/
				}
			}
		});
	};
		
	//跳转至下一业务节点
	function toNextBusPoint(url,title,nodeName){
		top.flag=false;//表示是否进行业务操作
		top.putoutFlag=false;//表示是否是放款申请节点
		top.putoutReviewFlag=false;//表示是否是放款复核节点
		top.getInfoFlag = false;//业务操作后表示是否需要获得信息
		top.pactUpdateFlag=false;//表示是否是合同签约节点
		top.repayReviewFlag=false;//表示是否是还款复核节点
		top.pactDetailInfo = "";
		var tmpUrl=url.split("&")[0];
		var popFlag = tmpUrl.split("?")[1].split("=")[1];
		if(popFlag=="0"){
			alert(top.getMessage("CONFIRM_OPERATION",title),2,function(){
				if(!valiDocIsUp(pactId)){
					return false;
				} 
				$.ajax({
					url:url,
					type:'post',
					async:false,
	    			dataType:'json',
	    			async:false,
	    			beforeSend:function(){  
						LoadingAnimate.start();
					},success:function(data){ 
						if(data.flag=="success"){
							if(data.node=="processaudit"){
								window.top.alert(data.msg,3);
							}else if(data.node=="repaying"){
								window.top.alert(data.msg,3);
								$(".next-div").removeClass("show");
								$(".next-div").addClass("hide");
								$("#repay").attr("disabled",false);
								$("#repay").removeClass("btn-opt-dont");
								$("#repay").addClass("btn-opt");
								$("#repayTrial").attr("disabled",false);
								$("#repayTrial").removeClass("btn-opt-dont");
								$("#repayTrial").addClass("btn-opt");
								$("#prerepay").attr("disabled",false);
								$("#prerepay").removeClass("btn-opt-dont");
								$("#prerepay").addClass("btn-opt");
								
							}else if(data.node=="repayed"){
								window.top.alert(data.msg,3);
								//加载还款历史表单
								$(".next-div").removeClass("show");
								$(".next-div").addClass("hide");
							}
							getNextBusPoint();
							$("#wj-modeler1").empty();
							showWkfFlow($("#wj-modeler1"),wkfAppId);
							$(".app-process").removeClass("show");
							$(".app-process").addClass("hide");
							
							
						}else{
							alert(data.msg);
						}
					},complete: function(){
   						LoadingAnimate.stop();
   					}
				});
			});
		}else if(nodeName=="storage_confirm"&&busModel!="5"&&busModel!="13"){//应收账款融资，保理业务
			Collateral.editCollateral("bussFlow");
		}else{
			top.window.openBigForm(url,title,wkfCallBack);
		}
	}
	
	//回调函数
	function wkfCallBack(){
        var tableHtml;
		if(top.flag){
			if(top.putoutFlag){
				$(".next-div").removeClass("show");
				$(".next-div").addClass("hide");
			}else{
				getNextBusPoint();
				$("#wj-modeler1").empty();
//					showWkfFlow(wkfAppId);
				showWkfFlow($("#wj-modeler1"),wkfAppId);
			}
			//放款复核后，处理还款节点以及显示收款计划
			if(top.putoutReviewFlag){
				$(".next-div").removeClass("show");
				$(".next-div").addClass("hide");
				$("#repay").attr("disabled",false);
				$("#repay").removeClass("btn-opt-dont");
				$("#repay").addClass("btn-opt");
				$("#repayTrial").attr("disabled",false);
				$("#repayTrial").removeClass("btn-opt-dont");
				$("#repayTrial").addClass("btn-opt");
				$("#prerepay").attr("disabled",false);
				$("#prerepay").removeClass("btn-opt-dont");
				$("#prerepay").addClass("btn-opt");
				tableHtml = '<div style="margin-left:15px;font-size:14px;margin-bottom:-20px;">'
								+'<span style="color:#000;font-weight:bold;margin-right:10px;">收款计划</span>'
								+'</div>'
								+'<div class="list-table">'
								+'<div class="content">'
								+ top.tableHtml
								+'</div>'
								+'</div>';
				$("#fincAppDetail").after(tableHtml);
			}
			if(top.getInfoFlag){
				if(top.showType != null){
					setBlock(top.showType,top.title,top.name,top.getInfoUrl);
				}
			}
			if(top.pactUpdateFlag){
				refreshPactDetailInfo("pactDetailInfo",top.pactDetailInfo);
			}
			if( top.repayReviewFlag){//还款复核成功后处理
				$(".next-div").removeClass("show");
				$(".next-div").addClass("hide");
				tableHtml = '<div style="margin-left:15px;font-size:14px;margin-bottom:-20px;">'
					+'<span style="color:#000;font-weight:bold;margin-right:10px;">收款计划</span>'
					+'</div>'
					+'<div class="list-table">'
					+'<div class="content">'
					+ top.tableHtml
					+'</div>'
					+'</div>';
				$("#fincAppDetail").after(tableHtml);
			}
		}
	};
	/******退费相关业务处理***start ***/
	 //打开退费列表页面
	 function mfBusRefundFeeJsp(){
		top.flag=false;
		var tempParm = "&appId=" + appId + "&pactId=" + pactId + "&fincId=" + fincId;
//		top.window.openBigForm(encodeURI("mfRepayPlanTrial/input?" + tempParm), '还款计划试算', myclose);
//		top.window.openBigForm(encodeURI("mfBusRefundFee/getMfBusRefundFee?appId=" + appId+"&pactId="+pactId+"&fincId="+fincId),'退款信息',repayCloseCallBack);
		top.window.openBigForm(webPath+"/mfBusRefundFee/getMfBusRefundFee?appId="+appId+"&pactId="+pactId+"&fincId="+fincId,'退款信息',repayCloseCallBack);
	 }
	/******退费相关业务处理***end***/
	/******还款撤销相关业务处理***start ***/
	//打开还款撤销页面
	function mfBusUndoRecordJsp(url){
		top.flag=false;
		top.window.openBigForm(encodeURI(url),'还款撤销信息',repayCloseCallBack);
	}
	/******还款撤销相关业务处理***end***/
	/*****展期前收息正常还款（只还利息）***start*****/
	 function LiXiRepaymentJsp(){
			// 判断是否能够进行收息
			$.ajax({
				url : webPath+'/mfLiXiRepayment/checkLiXiHuanKuanAjax.action',
				data : {
					"fincId" : fincId
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					if (data.flag == "success") {						
						//能够利随本清正常还款
						top.window.openBigForm(webPath+'/mfLiXiRepayment/repaymentJsp?fincId=' + fincId,'利息还款信息',repayCloseCallBack);
					} else {// 不满足展期前利息还款条件
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION", " "), 0);
				}
			});
		};
	/*****展期前收息正常还款(只还利息)***end*****/
	 
	function getCusInfo(cusNo){ 
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&busEntrance="+busEntrance+"&fincId="+fincId;
	}

	//弹窗查看具体详情信息，关联企业：核心企业、资金机构、仓储机构等	
	function getInfoForView(typeThis,id){
		top.window.openBigForm(webPath+'/mfCusCustomer/getByIdForShow?cusType='+typeThis+'&cusNo='+id,'客户信息',function(){});
	}
	//贷后检查
	function loanAfterExamine(){
		top.window.openBigForm(webPath+'/mfExamineHis/input?pactId='+pactId+'&cusNo='+cusNo+"&fincId="+fincId,"贷后检查",refreshExamHis);
	}
//打开复验验车单页面
function getCarCheckFormList(){
        top.window.openBigForm(webPath+"/mfCarCheckForm/inputCheck?appId="+appId,'复验验车单',function(){});
}
	//刷新检查历史
	function refreshExamHis(){
		$.ajax({
			url: webPath+"/mfExamineHis/getExamHisListAjax?pactId="+pactId,
			type:"post",
			async: false,
			dataType:"json",
			error:function(){alert('error')},
			success:function(data){
				$("#examineHis").html(data.tableData);
			}
		});
	}
	function openExamDetail(obj,urlArgs){
		var templateId = urlArgs.split("?")[1].split("&")[0].split("=")[1];
		var docuTemplate = urlArgs.split("?")[1].split("&")[1].split("=")[1];
		var formTemplate =  urlArgs.split("?")[1].split("&")[2].split("=")[1];
		if(docuTemplate!=""){
			var pathFileName = "/factor/component/examine/saveDocu/"+docuTemplate;
			var savePath = "/factor/component/examine/docuTemplate/";
			var returnUrl = window.location.href;
			var poCntJson = {
				pathFileName : "" + pathFileName,
				savePath : "" + savePath,
				saveFileName : docuTemplate,
				fileType : 0
			};
			poCntJson.returnUrl = returnUrl;
			poCntJson.printBtn="0";//取消打印按钮
			var poCnt = JSON.stringify(poCntJson);
			var url="/pageoffice/pageOfficeFactor.do?method=pageOfficeEdit&poCnt="+encodeURIComponent(encodeURIComponent(poCnt));
			window.open(url, '_self', '');
		}
		if(formTemplate!=""){
			top.window.openBigForm(webPath+'/mfExamineHis/getExamDetail?formTemplate='+formTemplate,"检查详情",function(){});
		}
	};
			
	//文件打印
	function filePrint(){
		var tempParm = "&cusNo="+cusNo+"&appId="+appId+"&pactId="+pactId+"&fincId="+fincId+"&pleId="+pleId;
		top.window.openBigForm(encodeURI(webPath+"/mfTemplateBizConfig/getPrintFileList?relNo="+pactId+"&modelNo="+pactModelNo+"&generatePhase=05|06|07|08"+tempParm),'文件打印',myclose);
	}
		
	//验证文档是否上传
	function valiDocIsUp(relNo){
		var flag = true;
		$.ajax({
			type: "post",
			dataType: 'json',
			url: webPath+"/docBizManage/valiWkfDocIsUp",
			data:{relNo:relNo},
			async: false,
			success: function(data) {
				if(!data.flag){
					window.top.alert(data.msg,0);
				}
				flag = data.flag;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
			}
		});
		return flag;
	}
	
	//查看产品信息
	function getKindInfo(){
		top.window.openBigForm(webPath+'/mfBusAppKind/getById?appId='+appId,'产品信息',function(){});
	}


	function getFeeById(obj,url){
		url = url +"&fincSts="+fincSts;
		top.window.openBigForm(url,"费用标准",function(){});	
	}
	//法律诉讼:新建
	function lawsuit(){
		top.addFlag = false;
		top.openBigForm(webPath+"/mfLawsuit/input?pactNo="+pactId, "新增案件",function(){
			if(top.addFlag){
				hasLawsuit= "1";
				$("#has-lawsuit").removeClass("btn-lightgray");
				$("#has-lawsuit").addClass("btn-dodgerblue");
			}
		});
	};
	//查看已发起的法律诉讼
	function getLawsuitList(){
		if(hasLawsuit==1){
			top.openBigForm(webPath+"/mfLawsuit/getById?pactNo="+pactId+"&flag=show", "案件详情",function(){});
		}else{
			return false;
		}
	};
	//批量进行五级分类
	function batchFiveclass(){
		window.location.href=webPath+"/mfPactFiveclass/batchFiveclass";
	};
	//查看五级分类信息
	function getFiveclass(){
		top.openBigForm(webPath+"/mfPactFiveclass/getFiveclass?fiveclassId="+fiveclassId, "五级分类详情",function(){});
	};

	// 查看五级分类信息
	function fiveclassView() {
		top.openBigForm(webPath + "/mfPactFiveclass/fiveclassView?fincId=" + fincId, "五级分类详情", function () { });
	};

	//客户经理调整五级分类
	function fiveclassUpdate(title){
		var _title = "五级分类";
		if(typeof(title)!="undefined"){
			_title = title
		}
		top.window.openBigForm(webPath+'/mfPactFiveclass/getById?fiveclassId='+fiveclassId,_title,getFiveclassSts);
	}
	//公司认定五级分类
	function fiveclassFinal(){
		top.window.openBigForm(webPath+'/mfPactFiveclass/getFiveclassById?fiveclassId='+fiveclassId,"五级分类",getFiveclassSts);
	}
	//查看审批历史
	function fiveclassApprovalHis(title){
		var _title = "五级分类";
		if(typeof(title)!="undefined"){
			_title = title
		}
		top.window.openBigForm(webPath+'/mfPactFiveclass/fiveclassApprovalHis?appNo='+fiveclassId,_title,getFiveclassSts);
	}
	//新增五级分类
	function fiveclassInsert(title){
		var _title = "五级分类";
		if(typeof(title)!="undefined"){
			_title = title
		}
		top.window.openBigForm(webPath+'/mfPactFiveclass/input?pactId='+pactId+'&fincId='+fincId,_title,getFiveclassSts);
	}
	//加载五级分类状态
	function getFiveclassSts(){
		var level;
		$.ajax({
			url: webPath+"/mfPactFiveclass/getFiveclassStsAjax",
			type:"post",
			data:{"fincId":fincId},
			async: false,
			dataType:"json",
			error:function(){alert('error')},
			success:function(data){
				if(data.flag == "success"){
					var fiveclassSts = data.mfPactFiveclass.fiveclassSts
					fiveclassId = data.mfPactFiveclass.fiveclassId;

					if(fiveclassSts == 0 || fiveclassSts == 1 || fiveclassSts == 4 || fiveclassSts == 5 || fiveclassSts == 3){//0系统初分 1不需要审批 5公司已认定
						//$("#fiveclass").addClass("btn-dodgerblue");
						$("#fiveclassUpdate").show();
                        $("#fiveclassInsert").attr("style","display:none;");
						var fiveclass = data.mfPactFiveclass.fiveclass;
						if(fiveclass == 1){
							fiveclass = "正常";
							level = "L4";
						}else if(fiveclass == 2){
							fiveclass = "关注";
							level = "L4";
						}else if(fiveclass == 3){
							fiveclass = "次级";
							level = "L3";
						}else if(fiveclass == 4){
							fiveclass = "可疑";
							level = "L3";
						}else if(fiveclass == 5){
							fiveclass = "损失";
							level = "L2";
						}
						//$("#fiveclass").attr("onclick","getFiveclass()");
						$("#fiveclass").attr("onclick","fiveclassView()");
						$("#fiveclass-i").text(fiveclass);
					}else if(fiveclassSts == 2){
						$("#fiveclass-i").text("五级分类审批中");
						$("#fiveclass").attr("onclick","fiveclassApprovalHis()");//还未审批完毕，查看审批历史
						$("#fiveclass").removeClass("btn-dodgerblue");
						$("#fiveclassUpdate").attr("onclick","fiveclassApprovalHis()");
						$("#fiveclassInsert").attr("onclick","fiveclassApprovalHis()");
						level = "L1";
					}
					//暂不需要公司认定
//					else if(fiveclassSts == 4){
//						$("#fiveclass").text("五级分类公司认定中");
//						$("#fiveclassInsert").attr("onclick","fiveclassFinal()");//修改五级分类操作按钮的调用方法为公司认定
//						$("#fiveclass").attr("onclick","fiveclassApprovalHis()");//还未认定完毕，查看审批历史
//						$("#fiveclass").removeClass("btn-dodgerblue");
//					}
				}else{
					level = "L1";
					//$("#fiveclass").attr("onclick","");
				}
				$(".pact-fiveclass").attr("level", level);
			}
		});
	}
	
	//多业务大于3条时，弹层列表页面
	function getMultiBusList(flag){
		if('apply'==flag){
			top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"申请中业务",function(){});
		}else if('pact'==flag){
			top.openBigForm(webPath+"/mfBusPact/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在履行合同",function(){});
		}else if('finc'==flag){
			top.openBigForm(webPath+"/mfBusFincApp/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"在履行借据",function(){});
		}else if('assure'==flag){
			top.openBigForm(webPath+"/mfAssureInfo/getMultiBusList?cusNo="+cusNo+"&busEntrance="+busEntrance,"担保项目",function(){});
		}
	};
	
	//单字段编辑的保存回调方法。
	function oneCallback(data) {
		var beginDate = $("input[name=beginDate]").val();
		var name = data[0].name;
        var d,str;
		if(name=="beginDate"){
			var intTerm = parseInt(term);
			str = "";
			if(1==termType){ //融资期限类型为月 
				d = new Date(beginDate);
				d.setMonth(d.getMonth()+intTerm);
				str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
			}else{ //融资期限类型为日 
				d = new Date(beginDate);
			 	d.setDate(d.getDate()+intTerm);
				str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
			}
			$(".endDate").html(str);
		}
		if(name=="extenTerm"){
			var extenBeginDate = $(".extenBeginDate").html();
			var extenTermOriginal = $("input[name=extenTerm]").val();
			var termType = $("#ExtenAppDetail").find("[name=termType]").val();
			var extenTerm = parseInt(extenTermOriginal);
			str = "";
			if(1==termType){ //融资期限类型为月 
				d = new Date(extenBeginDate);
				d.setMonth(d.getMonth()+extenTerm);
				str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
			}else{ //融资期限类型为日 
				d = new Date(extenBeginDate);
			 	d.setDate(d.getDate()+extenTerm);
				str = d.getFullYear()+"-"+(d.getMonth()>=9?beginDate.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate()); 
			}
			$(".extenEndDate").html(str);
		}
	};
	
	
//催收登记
function recallRegist(){
	top.flag = false;
	top.window.openBigForm(webPath+'/recallBase/input?pactId='+pactId+'&cusNo='+cusNo+'&fincId='+fincId,"催收登记",function(){
		if(top.flag){
			if(top.formType=="assign"){
				$("#recallspan").text("待催收");
				$("#recallbase").removeClass("btn-forestgreen");
				$("#recallbase").addClass("btn-danger");
				$("#recallbase").removeClass("hide");
			}else if(top.formType=="regist"){
				if($("#recallbase").hasClass("btn-danger")&&$("#recallbase").hasClass("hide")){
					$("#recallspan").text("催收完成");
					$("#recallbase").removeClass("btn-danger");
					$("#recallbase").addClass("btn-forestgreen");
					$("#recallbase").removeClass("hide");
				}
			}
		}
		
	});
};

//催收详情信息
function getRecallInfo(){
	top.window.openBigForm(webPath+'/recallBase/getRecallInfo?pactId='+pactId+'&cusNo='+cusNo+'&fincId='+fincId,"催收详情",function(){});
}

//文件归档
function fileArchive(){
	var archivePactStatus = "01";
	if(pactSts=="7"){
		archivePactStatus="02";
	}
	var dataParam = '?optType=01&aarchivePactStatus='+archivePactStatus+'&cusNo='+cusNo+'&appId='+appId+'&pactId='+pactId+'&pleId='+pleId;
	top.window.openBigForm(webPath+'/archiveInfoMain/getArchiveNodes'+dataParam,"文件归档",function(){});
}

//文件封档
function fileSeal(){
	var dataParam = '?optType=02&cusNo='+cusNo+'&appId='+appId+'&pactId='+pactId+'&pleId='+pleId;
	top.window.openBigForm(webPath+'/archiveInfoMain/getArchiveNodes'+dataParam,"文件封档",function(){});
}


//打开客户基本认证报告页面
function openCustomerCerReport(){
	//查询是否同盾的报告
	var reportData = tdReport("0");
	if(reportData != null && (reportData.errorCode == "11111" || reportData.errorCode == "00000")){
		if(reportData.errorCode == "00000"){
			alert(reportData.errorMsg, 0);
			return ;
		}
		var TD_data = reportData.data;
		TD_data = $.parseJSON(TD_data);
		$.showTDReport(TD_data);
		return ;
	}
	top.updateFlag = false;
	top.window.openBigForm(webPath+'/mfPhoneBook/openCustomerCerReport?cusNo='+cusNo, '认证报告',function(){},"75","90");
}

//同盾认证报告 ,submitFlag-重新提交查询 0-否 1-是
var tdReport = function (submitFlag){
	var resultMap = null;
	$.ajax({
		 type : "post",  
         url : webPath+"/mfTongDunReportAuth/getTDReportAjax?cusNo="+cusNo+"&appId="+appId+"&submitFlag="+submitFlag,  
         async : false,  
         success : function(data){ 
        	 resultMap = data;
         }  
    });
	return resultMap;
};


var getRiskReport = function(){
	top.window.openBigForm(webPath+"/mfBusApply/riskReport?appId=" + appId +"&cusNo="+cusNo+"&query=query",'风控查询', myclose);
};

function riskRegistration(){
	top.window.openBigForm(webPath+"/mfRiskLevelManage/inputRiskRgt?appId=" + appId +"&cusNo="+cusNo + "&pactNo=" + pactNo + "&fincShowId="+fincShowId+"&comeFrom=2",'风险登记', myclose);
}
