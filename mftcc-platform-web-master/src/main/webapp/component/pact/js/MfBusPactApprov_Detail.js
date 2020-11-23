$(function(){
	//判断合同审批信息模块的显隐（pactSts:0-未补录 1-未提交 2-流程中 3-退回 4-审批通过 5-否决 6-已完结）
	if(pactSts != '0' && pactSts != '1'){
//		getSPInfo();//获得审批信息
		showWkfFlow($("#wj-modeler2"),pactId);
		showWkfFlowVertical($("#wj-modeler2"),pactId,"4","contract_approval");
	}else{
		$("#spInfo-block").remove();
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
	
	top.LoadingAnimate.stop(); 
});
		function setBlock(showType,title,name,getUrl){
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
			$.ajax({
				url:getUrl,
				type:'post',
				dataType:'html',
				success:function(data){
					var $html = $(data);
                    var formStr;
					if(showType == "1" ){
						formStr = $html.find("form").prop("outerHTML");
						$(".content[name='"+name+"']").html(formStr);
					}else if(showType == "2"){
						var tableStr = $html.find("table").prop("outerHTML");
						$(".content[name='"+name+"']").html(tableStr);
					}else if(showType == "3"){
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
			var html = '<form id="MfCusCorpBaseInfoActionAjax_updateAjaxByOne_action" name="operform" action="/MfCusCorpBaseInfo/updateAjaxByOne" method="post">'+htmlStr+'</form>';
			$("#"+divId).html(html);
		};
		//还款
		function repayment(){
			var fincId = $("input[name=fincId]").val();
			top.window.openBigForm(webPath+'/mfRepayment/repaymentJsp?fincId='+fincId,'还款信息',repayCloseCallBack);
		}
		//买方还款
		function buyerRepayment(){
			var fincId = $("input[name=fincId]").val();
			top.window.openBigForm(webPath+'/mfRepayment/repaymentJspForBuy?fincId='+fincId,'买方还款信息',repayCloseCallBack);
		}
		//还款回调处理
		function repayCloseCallBack(){ 
			wkfAppId=top.wkfRepayId;
			if(top.flag){
				var fincId = $("input[name=fincId]").val();
				var url = webPath+"/mfBusFincApp/getFincApp?fincId="+fincId;
				$.ajax({
				url:url,
				type:'post',
				dataType:'html',
				success:function(data){ 
					var $html = $(data);
					var $putOutContent = $("#fincAppDetail").parent();
					var formStr = $html.find(".bigForm_content_form").prop("outerHTML");
					$(".content[name='"+name+"']").html(formStr);
					$putOutContent.empty();
					$putOutContent.html(formStr);
					$("#repay").hide();
					getNextBusPoint();
					$("#wj-modeler").empty();
					showWkfFlow(wkfAppId);
				},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}
			
		}
		
		function adjustheight(){
			$(".block-left-div").css("height","auto");
			//调整左边和右边的height，使高度小的适应高度大的
			var leftheight = parseInt($(".block-left-div").outerHeight());
			var rightheight =  parseInt($(".block-right-div").outerHeight());
			if(leftheight > rightheight){
				$(".block-right-div").css("height",leftheight);
			}else if(leftheight < rightheight){
				$(".block-left-div").css("height",rightheight);
			} 
		};
		
			
		function getNextBusPoint(){
			$.ajax({
				url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId,
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
						if(menuBtn.indexOf(nodeName)!="-1"){
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
						}else{
							if(nodeName=="review_finc"||nodeName=="review_rep" ||nodeName=="review_repay" || (popFlag == '0' && result== '1')){
								$(".block-next").append("<span>业务在"+title+"阶段</span>");
								$(".next-div").removeClass("hide");
								$(".next-div").addClass("show");
							}
						}
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
				alert("确定进行“" + title + "”操作?",2,function(){
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
									$("#repay").show();
									wkfAppId = data.wkfAppId;
								}else if(data.node=="repayed"){
									window.top.alert(data.msg,3);
									wkfAppId = data.wkfAppId;
									$(".next-div").removeClass("show");
									$(".next-div").addClass("hide");
								}
								getNextBusPoint();
								$("#wj-modeler").empty();
								showWkfFlow(wkfAppId);
								
							}else{
								alert(data.msg);
							}
						},complete: function(){
	   						LoadingAnimate.stop();
	   					}
					});
				});
			}else if(nodeName=="storage_confirm"){
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
					$("#wj-modeler").empty();
					showWkfFlow(wkfAppId);
				}
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
		}
		
		
		
		
		function getCusInfo(cusNo){ 
			top.LoadingAnimate.start();
			window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId;
		}
	
			//获取押品信息
		function getPleInfo(pleid){
			top.LoadingAnimate.start();
			window.location.href=webPath+"/mfBusPledge/getPledgeById?cusNo="+cusNo+"&appId="+appId;
		}
		
		
		function getInfoForView(typeThis,id){//弹窗查看块状信息
			if(typeThis == 'cus'){
				//处理新增仓储方或核心企业保存后，点击仓储方或核心企业按钮查看详情
				if(cusNoTmp!=""){
					id = cusNoTmp;
				}
				top.window.openBigForm(webPath+'/mfCusCustomer/getByIdForShow?cusNo='+id,'客户信息',function(){});
			}else if(typeThis == 'ple'){
				top.window.openBigForm(webPath+'/mfBusPledge/getByIdForView?appId='+id,'押品信息',function(){});
			}
		}
		//贷后检查
		function loanAfterExamine(){
			top.window.openBigForm(webPath+'/mfExamineHis/input?pactId='+pactId+'&cusNo='+cusNo,"贷后检查",refreshExamHis);
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
			var examHidId =  urlArgs.split("?")[1].split("&")[3].split("=")[1];
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
				top.window.openBigForm(webPath+'/mfExamineHis/getExamDetail?formTemplate='+formTemplate+'examHidId='+examHidId,"检查详情",function(){});
			}
		}
		//合同资料
	 	function pactDocInfo(){
	 		top.window.openBigForm(webPath+'/docManage/pubUpload?relNo='+pactId+'&cusNo='+cusNo,"合同资料",function(){});
	 	}
	 	//合同打印
		function printPactDoc(){
				 $.ajax({
						url: webPath+"/mfTemplateModelRel/getIfSaveModleInfo",
						type:"post",
						data:{"relNo":pactId,"modelNo":pactModelNo},
						async: false,
						dataType:"json",
						error:function(){alert('error')},
						success:function(data){
							var type = "add";
							var filepath = "";
							if(data.flag !="0"){
								filepath = "/component/model/docword/";
							}
							var modelid = data.modelid;
							var filename = data.filename;
							var fincid = "";
							var traceNo = "";
							var returnUrl = window.location.href;
							var urlParm=returnUrl.split("?")[1];
							returnUrl=returnUrl.split("?")[0];
							urlParm = encodeURIComponent(urlParm);
							window.location.href=basePath+"component/model/toPageOffice.jsp?cifno="+cusNo+"&modelid="+modelid+"&filename="+filename+"&pactno="+pactId+"&appno="+appId+"&loanNo="+fincid+"&traceNo="+traceNo+"&returnUrl="+returnUrl+"&type="+type+"&filepath="+filepath+"&urlParm="+urlParm;
						}
				});
			}
			
	//文件打印
	function filePrint(){
		var tempParm = "&cusNo="+cusNo+"&appId="+appId+"&pactId="+pactId+"&fincId="+fincId+"&pleId="+pleId;
		top.createShowDialog(webPath+"/mfTemplateBizConfig/getPrintFileList?relNo="+pactId+"&modelNo="+pactModelNo+"&generatePhase="+encodeURI("01|02|03|04")+tempParm,'选择文件','50','70',myclose);
	}
		
	//获取审批意见	
	function getSPInfo(){
		$.ajax({
			type: "post",
			data:{appNo:pactId},
			dataType: 'json',
			url: webPath+"/wkfApprovalOpinion/getApplyApprovalOpinionList",
			contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success: function(data) {
				Wkf_zTreeNodes=data.zTreeNodes;
				Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
			}
		});
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
        var d;
		if(name=="beginDate"){
			var intTerm = parseInt(term);
			var str = "";
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
