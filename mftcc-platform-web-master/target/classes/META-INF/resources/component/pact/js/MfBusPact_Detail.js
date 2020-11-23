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
			
	//判断详情页面还款按钮的显隐（单借据的情况，多借据时连同页面均需另行设计）
	if(operateflag=="1"){//还款操作不能点击
		$("#repay").attr("disabled",true);
		$("#repay").removeClass("btn-opt");
		$("#repay").addClass("btn-opt-dont");
		$("#repayTrial").attr("disabled",true);
		$("#repayTrial").removeClass("btn-opt");
		$("#repayTrial").addClass("btn-opt-dont");
      }else{
    	$("#repay").attr("disabled",false);
  		$("#repay").removeClass("btn-opt-dont");
  		$("#repay").addClass("btn-opt");
  		$("#repayTrial").attr("disabled",false);
  		$("#repayTrial").removeClass("btn-opt-dont");
  		$("#repayTrial").addClass("btn-opt");
      }
	if(fincSts=="5"){//还款操作能点击
		$("#repay").attr("disabled",false);
		$("#repay").removeClass("btn-opt-dont");
		$("#repay").addClass("btn-opt");
		$("#repayTrial").attr("disabled",false);
		$("#repayTrial").removeClass("btn-opt-dont");
		$("#repayTrial").addClass("btn-opt");
	}
	if(fincSts=="6"){//还款复核中
		$(".app-process").addClass("show");
		$(".app-process").removeClass("hide");
		wkfAppId=wkfRepayId;
		showWkfFlow($("#wj-modeler1"),wkfAppId);
	}
	if(fincSts=="7"){//业务完结,贷后检查不可操作
		$("#loanAfterExamine").attr("disabled",true);
		$("#loanAfterExamine").addClass("btn-opt-dont");
		$("#loanAfterExamine").removeClass("btn-opt");
	}
	/**显示流程**/
	getNextBusPoint();
	
			
	//判断有无法律诉讼
	if(hasLawsuit==1){
		$("#has-lawsuit").removeClass("btn-lightgray");
		$("#has-lawsuit").addClass("btn-dodgerblue");
	}
	//如果进行过催收
	if(hasRecallFlag=="1"){
		$("#recallbase").removeClass("hide");
		if(recallingFlag=="0"){
			$("#recallspan").text("催收完成");
			$("#recallbase").removeClass("btn-danger");
			$("#recallbase").addClass("btn-forestgreen");
		}
	}
	getFiveclassSts();
	
	$.each(hisTaskList,function(i,hisTask){
		if(hisTask.state = 'completed'){
			if(hisTask.approveIdea !="同意" && hisTask.approveIdea !=""){
				var getUrl = hisTask.approveIdea.split("#")[1];
				var showType = hisTask.approveIdea.split("#")[0];
				var title = hisTask.description;
				if(typeof(getUrl) != "undefined" && typeof(getUrl) != null && getUrl != ''){
					setBlock(showType,title,hisTask.dbId,getUrl);
				}
			}
		}
		
	});
	setBlock("2","费用标准","busfee",webPath+"/mfBusAppFee/getListPage?appId="+appId);
	top.LoadingAnimate.stop();
	//判断是否显示 还款历史
	if(ifShowRepayHistory =="1"){//显示还款历史数据
		$("#repayHistoryList").show();
	}
	//合同完结bug4223/4227.add by lwq
	if(pactSts == "7"){
		$("#recallRegist").attr("disabled",true);
		$("#recallRegist").attr("class", "btn btn-opt-dont");
		$("#fiveclassUpdate").attr("disabled",true);
		$("#fiveclassUpdate").attr("class", "btn btn-opt-dont");
		$("#fiveclassInsert").attr("disabled",true);
		$("#fiveclassInsert").attr("class", "btn btn-opt-dont");
		$("#lawsuitAdd").attr("disabled",true);
		$("#lawsuitAdd").attr("class", "btn btn-opt-dont");
	}
	//银行卡号格式话
	$(".incomBank").each(function(i, item) {
	var itemBankNo = $(item).text();
	var itemBankNoHtml = $(item).html();
	if(/\S{5}/.test(itemBankNo)){
		 $(item).html(itemBankNoHtml.replace(itemBankNo,itemBankNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ")));
	}
	});
	//调用贷后检查初始化方法，处理是否已存在贷后检查
	BusExamine.init();
});


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
	//还款
	function repayment(){
//		var fincId = $("input[name=fincId]").val();
		top.flag=false;
		top.window.openBigForm(webPath+'/mfRepayment/repaymentJsp?fincId='+fincId,'还款信息',repayCloseCallBack);
	}
	
	//还款回调处理
	function repayCloseCallBack(){ 
		wkfAppId=top.wkfRepayId;
		if(top.flag){
			var fincId = $("input[name=fincId]").val();
			var url = webPath+"/mfBusPact/getPactFincByRepay?fincId="+fincId;
			$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			success:function(data){ 
//				var $html = $(data);
//				var $putOutContent = $("#fincAppDetail").parent();
//				var formStr = $html.find(".bigForm_content_form").prop("outerHTML");
//				$(".content[name='"+name+"']").html(formStr);
//				$putOutContent.empty();
//				$putOutContent.html(formStr);
				var tableHtmlRepayPlan=data.tableHtmlRepayPlan;//收益计划
				var tableHtmlRepayHistory=data.tableHtmlRepayHistory;
				$("#mfRepayPlanList").html(tableHtmlRepayPlan);
				$("#mfRepayHistoryList").html(tableHtmlRepayHistory);
				getNextBusPoint();
				$("#wj-modeler1").empty();
				$(".app-process").addClass("show");
				$(".app-process").removeClass("hide");
//					showWkfFlow(wkfAppId);
				showWkfFlow($("#wj-modeler1"),wkfAppId);
				$("#repay").attr("disabled",true);
				$("#repay").removeClass("btn-opt");
				$("#repay").addClass("btn-opt-dont");
				$("#repayHistoryList").show();
			},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		}
		
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
            var tableHtml;
			//放款复核后，处理还款节点以及显示收款计划
			if(top.putoutReviewFlag){
				$(".next-div").removeClass("show");
				$(".next-div").addClass("hide");
				$("#repay").attr("disabled",false);
				$("#repay").removeClass("btn-opt-dont");
				$("#repay").addClass("btn-opt");
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
		
	function getCusInfo(cusNo){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfCusCustomer/getById?busEntrance="+busEntrance+"&cusNo="+cusNo+"&appId="+appId+"&type="+type+"&fincId="+fincId;
	}

	//弹窗查看具体详情信息，关联企业：核心企业、资金机构、仓储机构等	
	function getInfoForView(typeThis,id){
		top.window.openBigForm(webPath+'/mfCusCustomer/getByIdForShow?cusType='+typeThis+'&cusNo='+id,'客户信息',function(){});
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
		top.window.openBigForm(encodeURI("MfTemplateModelConfig/getPrintFileList?relNo="+pactId+"&modelNo="+pactModelNo+"&generatePhase=05|06|07|08"+tempParm),'文件打印',myclose);
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
			top.openBigForm(webPath+"/mfLawsuit/getById?pactNo="+pactId+"&flag=pactShow", "案件详情",function(){});
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
	function fiveclassUpdate(){
		top.window.openBigForm(webPath+'/mfPactFiveclass/getById?fiveclassId='+fiveclassId,"五级分类",getFiveclassSts);
	}
	//公司认定五级分类
	function fiveclassFinal(){
		top.window.openBigForm(webPath+'/mfPactFiveclass/getFiveclassById?fiveclassId='+fiveclassId,"五级分类",getFiveclassSts);
	}
	//查看审批历史
	function fiveclassApprovalHis(){
		top.window.openBigForm(webPath+'/mfPactFiveclass/fiveclassApprovalHis?appNo='+fiveclassId,"五级分类",getFiveclassSts);
	}
	//新增五级分类
	function fiveclassInsert(){
		top.window.openBigForm(webPath+'/mfPactFiveclass/input?pactId='+pactId+'&fincId='+fincId,"五级分类",getFiveclassSts);
	}
	//加载五级分类状态
	function getFiveclassSts(){
		var level;
		$.ajax({
			url: webPath+"/mfPactFiveclass/getFiveclassStsAjax",
			type:"post",
			data:{"pactId":pactId,"fincId":fincId},
			async: false,
			dataType:"json",
			error:function(){alert('error')},
			success:function(data){
				if(data.flag == "success"){
					
					var fiveclassSts = data.mfPactFiveclass.fiveclassSts
					fiveclassId = data.mfPactFiveclass.fiveclassId;
					if(fiveclassSts == 0 || fiveclassSts == 1 || fiveclassSts == 4 || fiveclassSts == 5){//0系统初分 1不需要审批 5公司已认定
						//$("#fiveclass").addClass("btn-dodgerblue");
						$("#fiveclassUpdate").show();
						$("#fiveclassInsert").hide();
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
	function getMultiBusList(){
		top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo="+cusNo,"多笔业务",function(){});
	};
	
	//单字段编辑的保存回调方法。
	function oneCallback(data) {
		var beginDate = $("input[name=beginDate]").val();
		var name = data[0].name;
		if(name=="beginDate"){
			var intTerm = parseInt(term);
			var str = "";
            var d;
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
	var archivePactStatus = "01"
	if(pactSts=="7"){
		archivePactStatus="02";
	}
	var dataParam = '?optType=01&archiveBusinessInfo.archivePactStatus='+archivePactStatus+'&archiveBusinessInfo.cusNo='+cusNo+'&archiveBusinessInfo.appId='+appId+'&archiveBusinessInfo.pactId='+pactId;
	top.window.openBigForm(webPath+'/archiveInfoMain/getArchiveNodes'+dataParam,"文件归档",function(){});
}
//文件封档
function fileSeal(){
	var dataParam = '?optType=02&archiveBusinessInfo.cusNo='+cusNo+'&archiveBusinessInfo.appId='+appId+'&archiveBusinessInfo.pactId='+pactId;
	top.window.openBigForm(webPath+'/archiveInfoMain/getArchiveNodes'+dataParam,"文件封档",function(){});
}

