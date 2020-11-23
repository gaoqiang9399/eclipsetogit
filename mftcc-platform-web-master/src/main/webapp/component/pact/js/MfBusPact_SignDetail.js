;
var MfBusPact_SignDetail = function(window, $) {
	// 文件打印
	var _filePrint = function() {
		var tempParm = "&cusNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId + "&fincId=" + fincId + "&pleId=" + pleId;
		top.window.openBigForm(encodeURI(webPath+"/mfTemplateBizConfig/getPrintFileList?relNo=" + pactId + "&modelNo=" + pactModelNo + "&generatePhase=05|06|07|08" + tempParm), '文件打印', myclose);
	};

	// 尽调报告按钮初始化
	var _surveyReport_init = function() {
		// 查询当前是否已保存尽调报告
		$.ajax({
			url : webPath+"/mfTemplateBizConfig/getMfTemplateBizConfig?templateNo=1018&temBizNo=" + appId,
			data : {},
			type : 'post',
			dataType : 'json',
			async : true,
			success : function(data) {
				if (data.templateBizConfig) {// 保存过尽调报告
					$("#surveyReport").removeClass("btn-lightgray");// 去掉灰色样式
					$("#surveyReport").addClass("btn-forestgreen");// 添加绿色样式

					_surveyReport_bindClick(data.templateBizConfig.templateBizConfigId);// 尽调报告绑定click事件
				}
			}
		});
	};

	// 尽调报告绑定click事件
	var _surveyReport_bindClick = function(templateBizConfigId) {
		$("#surveyReport").bind("click", function() {
			var temParm = '&cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&fincId=' + fincId;// 文档书签取值依赖条件，目前支持appId pactId cusNo fincId repayDetailId
			var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId + temParm;
			var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
			// 处理跳转office的url
			$.ajax({
				url : url + "&" + temParm,
				data : {
					"returnUrl" : backUrl,
					"functionPoint" : "jdbg"
				},
				type : 'post',
				dataType : 'json',
				async : true,
				beforeSend : function() {
					LoadingAnimate.start();
				},
				complete : function() {
					LoadingAnimate.stop();
				},
				error : function() {
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				},
				success : function(data) {
					var poCntObj = $.parseJSON(data.poCnt);
					mfPageOffice.openPageOffice(poCntObj);
				}
			});
		});
	};

	return {
		filePrint : _filePrint,
		surveyReport_init : _surveyReport_init
	};
}(window, jQuery);

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
	/**显示头部流程**/
	if(operable=="operable"){//可操作页面展示业务流程
		showWkfFlow($("#wj-modeler1"),wkfAppId);
	}else{//审批页面，展示审批流程
		if(fiveFlag=="fiveFlag"){//bug修改：显示五级分类审批流程
			showWkfFlow($("#wj-modeler1"),getfiveclassId);
//			showWkfFlow($("#wj-modeler5"),getfiveclassId);
			showWkfFlowVertical($("#wj-modeler5"),getfiveclassId,"6","fiveclass_approval");
		}else{
			//贷后检查审批流程 
			if(examHisId != null && examHisId != ""){
				showWkfFlow($("#wj-modeler1"),examHisId);
//				showWkfFlow($("#wj-modeler4"),examHisId);
				showWkfFlowVertical($("#wj-modeler4"),examHisId,"7","exam_approval");
			}
			if(pactSts == '2'&&pactProcessId!=''){
				showWkfFlow($("#wj-modeler1"),pactId);
			}
			if(fincSts == '2'&&fincProcessId!=''){
				showWkfFlow($("#wj-modeler1"),fincChidId);
			}
		}
	}
	getNextBusPoint();
	//判断贷后跟踪审批页面跳转过来合同审批历史及放款审批历史不展示
	if(examHis == 'examHis'){
		$("#pactSpInfo-block").hide();
		$("#fincSpInfo-block").hide();
		$("#fiveClass-block").hide();
	}
	//判断合同审批历史模块的显隐（pactSts:0-未补录 1-未提交 2-流程中 3-退回 4-审批通过 5-否决 6-已完结）
	if(pactSts != '0' && pactSts != '1'&&pactProcessId!=''){
//		showWkfFlow($("#wj-modeler2"),pactId);
		showWkfFlowVertical($("#wj-modeler2"),pactId,"4","contract_approval");
	}else{
		$("#pactSpInfo-block").remove();
	}
	//判断放款审批历史模块的显隐（fincSts:0-申请 1-未提交 2-流程中 3-退回 4-审批通过 ）
	if(fincSts!='0' && fincSts!='1' && fincSts!=''&&fincProcessId!=''){
//		showWkfFlow($("#wj-modeler3"),fincChidId);
		showWkfFlowVertical($("#wj-modeler3"),fincChidId,"3","putout_approval");
	}else{
		$("#fincSpInfo-block").remove();
	}
//	if(pactSts=="2"||fincSts=="2"){
//		approvalSubFlag="1";
//	}
			
	//判断有无法律诉讼
	if(hasLawsuit==1){
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
	if(fiveFlag=="fiveFlag"){//bug修改：有此标识，只显示五级分类
		$("#pactSpInfo-block").hide();
		$("#fincSpInfo-block").hide();
		$("#spInfo-block").hide();
	}
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
	setBlock("2","费用标准","busfee",webPath+'/mfBusAppFee/getListPage?appId='+appId);

	MfBusPact_SignDetail.surveyReport_init();// 尽调报告按钮初始化

	top.LoadingAnimate.stop(); 
});


function setBlockTitle(showType,title,name){
	var htmlStr = "";
	var collapseButtonHtml="<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"+name+"'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>";
	if(showType == "1"){
		htmlStr = htmlStr + "<div class='dynamic-block'>"
		+ "<div class='form-table'>"
		+ "<div class='title'><span class='formName'><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"' name='"+name+"'>"
		+"<form action='"+name+"Ajax_updateAjaxByOne.action' id='"+name+"Ajax_updateByOne_action'>"+htmlStr+"</form>"
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
	//刷新合同详情信息
	function  refreshPactDetailInfo(divId,htmlStr){
		var html = '<form id="MfCusCorpBaseInfoActionAjax_updateAjaxByOne_action" name="operform" action="/factor/mfCusCorpBaseInfo/updateAjaxByOne" method="post">'+htmlStr+'</form>';
		$("#"+divId).html(html);
	};
	
	function getNextBusPoint(){
		$.ajax({
			url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId+"&appId="+appId,
			type:'post',
			dataType:'json',
			success:function(data){ 
				wkfFlag = data.wkfFlag;
				if(wkfFlag == '0'){
//						$(".bg-danger").empty();
				}else if(wkfFlag == '1'){
					var url = data.url;
					var title=data.title;
					var nodeName = data.nodeName;
					var tmpUrl=url.split("&")[0];
					var popFlag = tmpUrl.split("?")[1].split("=")[1];
					var result = data.result;
					$(".block-next").empty();
					$(".next-div").unbind(); 
					var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(nodeName);
					if(checkPmsBizFlag){
						if(fincSts== '2' || pactSts == '2' || pactSts == '3'){
							$(".block-next").append("<span>业务在"+title+"阶段</span>");
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
						}else if(pactSts == '5' || fincSts == '3'){
							$(".block-next").append("<span>审批已被否决，业务结束</span>");
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
					}else{
						$(".block-next").append("<span>暂时没有权限操作该节点</span>");
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
					}
					
					
					/*if(menuBtn.indexOf(nodeName)!="-1"){*/
//						if(nodeName=="review_rep" || (popFlag == '0' && approvalSubFlag== '1')){
//							$(".block-next").append("<span>业务在"+title+"阶段</span>");
//							$(".next-div").removeClass("hide");
//							$(".next-div").addClass("show");
//						}else{ 
//							$(".block-next").append("<span id='point'>下一业务步骤：<a>"+title+"&gt;&gt;</a></span>");
//							$(".next-div").removeClass("hide");
//							$(".next-div").addClass("show");
//							$(".next-div").click(function(){
//								toNextBusPoint(url,title,nodeName);
//							}); 
//						}
					/*}else{*/
//						if(nodeName=="review_finc"/*||nodeName=="review_rep"*/ ||nodeName=="review_repay" /*|| (popFlag == '0' && approvalSubFlag== '1')*/ ){
//							$(".block-next").append("<span>业务在"+title+"阶段</span>");
//							$(".next-div").removeClass("hide");
//							$(".next-div").addClass("show");
//						}
						/*else{
							$(".block-next").append("<span>暂时没有权限操作该节点</span>");
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
						}
					}*/
				}
			}
		});
	}
	
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
				/*if(nodeName=="putout_approval"){//放款审批提交
					url=url+"&fincId="+fincId;
				}*/
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
//							approvalSubFlag="0";
							if(data.node=="processaudit"){ 
								if(data.processType == 'pact'){
									pactSts = data.pactSts;
								}
								if(data.processType == 'finc'){
									fincSts = data.fincSts;
								}
//								approvalSubFlag="1";
								window.top.alert(data.msg,3);
							}else if(data.node=="repaying"){
								window.top.alert(data.msg,3);
								$(".next-div").removeClass("show");
								$(".next-div").addClass("hide");
								$("#repay").show();
								wkfAppId = data.wkfAppId;
							}else if(data.node=="repayed"){
								window.top.alert(data.msg,3);
								wkfAppId = data.wkfAppId;
								$(".next-div").removeClass("show");
								$(".next-div").addClass("hide");
							}else if(data.node == "join_zh"){
								window.top.alert(data.msg,3);
							}
							getNextBusPoint();
							$("#wj-modeler1").empty();
//								showWkfFlow(wkfAppId);
							showWkfFlow($("#wj-modeler1"),wkfAppId);
							initDocNodes();//重新初始化要件
						}else{
							alert(data.msg);
						}
					},complete: function(){
   						LoadingAnimate.stop();
   					}
				});
			});
		}else{
			top.window.openBigForm(url,title,wkfCallBack);
		}
	}
		
	//回调函数
	function wkfCallBack(){
		if(top.flag){
            var tableHtml;
			//放款复核后，处理还款节点以及显示收款计划
			if(top.putoutReviewFlag){
				$(".next-div").removeClass("show");
				$(".next-div").addClass("hide");
				$("#repay").show();
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
				fincSts = top.fincSts;
				if(top.showType != null){
					setBlock(top.showType,top.title,top.name,top.getInfoUrl);
				}
			}
			if(top.pactUpdateFlag){
				pactSts = top.pactSts;
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
			//刷新流程放到最后    之前可能会处理一下状态
			if(top.putoutFlag){
				$(".next-div").removeClass("show");
				$(".next-div").addClass("hide");
			}else{
				getNextBusPoint();
				$("#wj-modeler1").empty();
//					showWkfFlow(wkfAppId);
				showWkfFlow($("#wj-modeler1"),wkfAppId);
				initDocNodes();//重新初始化要件
			}
		}
	};
	//跳转至客户的详情页面
	function getCusInfo(cusNo){ 
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&operable="+operable;
	}

	//弹窗查看具体详情信息，关联企业：核心企业、资金机构、仓储机构等
	function getInfoForView(typeThis,id){
		top.window.openBigForm(webPath+'/mfCusCustomer/getByIdForShow?cusType='+typeThis+'&cusNo='+id,'客户信息',function(){});
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
	//查看编辑费用详情
	function getFeeById(obj,url){
		top.window.openBigForm(url,"费用标准",function(){});	
	}
	//法律诉讼:新建
	function lawsuit(){
		top.openBigForm(webPath+"/mfLawsuit/input?pactNo="+pactId, "新增案件",function(){});
	};
	//查看已发起的法律诉讼
	function getLawsuitList(){
		top.openBigForm(webPath+"/mfLawsuit/getByPact?pactNo="+pactId, "案件详情",function(){});
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
		top.window.openBigForm(webPath+'/mfPactFiveclass/input?pactId='+pactId,"五级分类",getFiveclassSts);
	}
	//加载五级分类状态
	function getFiveclassSts(){
		$.ajax({
			url: webPath+"/mfPactFiveclass/getFiveclassStsAjax",
			type:"post",
			data:{"pactId":pactId},
			async: false,
			dataType:"json",
			error:function(){alert('error')},
			success:function(data){
				if(data.flag == "success"){
					var fiveclassSts = data.mfPactFiveclass.fiveclassSts
					fiveclassId = data.mfPactFiveclass.fiveclassId;
					if(fiveclassSts == 0 || fiveclassSts == 1 || fiveclassSts == 4 || fiveclassSts == 5){//0系统初分 1不需要审批 5公司已认定
						$("#fiveclass").addClass("btn-dodgerblue");
						$("#fiveclassUpdate").show();
						$("#fiveclassInsert").hide();
						var fiveclass = data.mfPactFiveclass.fiveclass;
						if(fiveclass == 1){
							fiveclass = "正常";
						}else if(fiveclass == 2){
							fiveclass = "关注";
						}else if(fiveclass == 3){
							fiveclass = "次级";
						}else if(fiveclass == 4){
							fiveclass = "可疑";
						}else if(fiveclass == 5){
							fiveclass = "损失";
						}
						//$("#fiveclass").attr("onclick","getFiveclass()");
						$("#fiveclass").attr("onclick","fiveclassView()");
						$("#fiveclass").text(fiveclass);
					}else if(fiveclassSts == 2){
						$("#fiveclass").text("五级分类审批中");
						$("#fiveclass").attr("onclick","fiveclassApprovalHis()");//还未审批完毕，查看审批历史
						$("#fiveclass").removeClass("btn-dodgerblue");
						$("#fiveclassUpdate").attr("onclick","fiveclassApprovalHis()");
						$("#fiveclassInsert").attr("onclick","fiveclassApprovalHis()");
					}
					//暂不需要公司认定
//					else if(fiveclassSts == 4){
//						$("#fiveclass").text("五级分类公司认定中");
//						$("#fiveclassInsert").attr("onclick","fiveclassFinal()");//修改五级分类操作按钮的调用方法为公司认定
//						$("#fiveclass").attr("onclick","fiveclassApprovalHis()");//还未认定完毕，查看审批历史
//						$("#fiveclass").removeClass("btn-dodgerblue");
//					}
				}else{
					//$("#fiveclass").attr("onclick","");
				}
			}
		});
	}
	
	//多业务大于3条时，弹层列表页面
	function getMultiBusList(){
		top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo="+cusNo,"多笔业务",function(){});
	}
	
	//切换业务（上一笔、下一笔）
	function switchBus(appid,pactid){
		top.LoadingAnimate.start();
		if(pactid==null || pactid ==""){
			window.location.href=webPath+"/mfBusApply/getSummary?appId="+appid;
		}else{
			window.location.href=webPath+"/mfBusPact/getById?appId="+appid;
		}
	}
	
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
	}
	
	
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
