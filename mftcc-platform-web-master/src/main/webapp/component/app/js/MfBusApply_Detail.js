;
var MfBusApply_Detail = function(window, $) {
	// 文件打印
	var _filePrint = function() {
		var tempParm = "&cusNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId + "&fincId=" + fincId + "&pleId" + pleId;
		top.window.openBigForm(webPath+"/mfTemplateBizConfig/getPrintFileList?relNo=" + appId + "&modelNo=" + modelNo + "&generatePhase=" + encodeURI("01|02|03|04") + tempParm, '文件打印', myclose);
	};

	// 尽调报告按钮初始化
	var _surveyReport_init = function() {
		// 查询当前是否已保存尽调报告
		$.ajax({
			url : webPath+"/mfTemplateBizConfig/getMfTemplateBizConfig?appId=" + appId,
			data : {},
			type : 'post',
			dataType : 'json',
			async : true,
			success : function(data) {
				if (data.uploadSize > 0) {// 保存过尽调报告
					$("#surveyReport").removeClass("btn-lightgray");// 去掉灰色样式
					$("#surveyReport").addClass("btn-forestgreen");// 添加绿色样式

					if (data.tbcList.length == 1) {
						// 尽职调查阶段只有一个文档，直接打开
						_surveyReport_bindClick(data.tbcList[0].templateBizConfigId);// 尽调报告绑定click事件
					}

					if (data.tbcList.length > 1) {
						// 尽职调查阶段有多个文档，打开选择窗口，选择打开
						$("#surveyReport").bind("click", function() {
							var tempParm = "&nodeNo=resp_investigation&cusNo=" + cusNo + "&appId=" + appId + "&pactId=" + pactId + "&fincId=" + fincId + "&pleId" + pleId;
							top.window.openBigForm(webPath+"/mfTemplateBizConfig/printSurveyReportList?relNo=" + appId + "&modelNo=" + modelNo + "&generatePhase=" + encodeURI("01|02|03|04") + tempParm, '调查报告', myclose);
						});
					}

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
					var obj = window.open("about:blank");

					var form = '<form  method="post" action="' + data.url + '">';

					var poCnt = '<input id="poCnt" name="poCnt" value=\'' + data.poCnt + '\'></input>';
					form += poCnt;

					var datatmp = '<input id="datatmp" name="datatmp" value=\'' + data.datatmp + '\'></input>';
					form += datatmp;

					form += '</form>';

					obj.document.write(form);
					obj.document.forms[0].submit();
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
	var nodeName;

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
	/**显示流程**/
//	showWkfFlow(wkfAppId);
	if(operable=="operable"){//可操作页面展示业务流程
		showWkfFlow($("#wj-modeler1"),wkfAppId);
	}else{//审批展示审批流程
		showWkfFlow($("#wj-modeler1"),appId);
	}
	//审批信息模块
	if(appSts != '0' && appSts != '1'&&applyProcessId!=''){
		//获得审批信息
//		showWkfFlow($("#wj-modeler2"),appId);
		showWkfFlowVertical($("#wj-modeler2"),appId,"5","apply_approval");
	}else{
		$("#spInfo-block").remove();
	}
	
	
	setBlock("2","费用标准","busfee",webPath+'/mfBusAppFee/getListPage?appId='+appId);
	
	getNextBusPoint();
	$.each(hisTaskObj.hisTaskArray,function(i,hisTask){
		if(hisTask.state = 'completed'){
			
			if(hisTask.approveIdea !="同意" && hisTask.approveIdea !=""){
				var getUrl = hisTask.approveIdea.split('#')[1];
				var showType = hisTask.approveIdea.split('#')[0];
				var title = hisTask.description;
				if(typeof(getUrl) != "undefined" && typeof(getUrl) != null && getUrl != ''){
					
					setBlock(showType,title,hisTask.dbId,getUrl);							
					
				}
			}
		}
		
	}); 
	dblclickflag();

	MfBusApply_Detail.surveyReport_init();// 尽调报告按钮初始化

	top.LoadingAnimate.stop();
});

function setBlockTitle(showType,title,name){
	var htmlStr = "";
	var _name = name;
	if(name!=null&&name!=""){
			_name = "/"+name.substring(0,1).toLowerCase()+name.substring(1);
			_name =_name.replace("Action","");
	}
	/**
	 * 系统添加了映射地址，拼接的URL地址需要添加webPath，如果不添加拼接的URL地址不正确导致方法无法访问（404） 2018年5月4日17:49:11
	 */
	_name = webPath +"/"+_name;
	var collapseButtonHtml="<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"+name+"'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>";
	if(showType == "1"){
		htmlStr = htmlStr + "<div class='dynamic-block' title='"+title+"' name='"+name+"'>"
		+ "<div class='form-table'>"
		+ "<div class='title'><span class='formName'><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"' name='"+name+"'>"
		+"<form  action='"+_name+"/updateAjaxByOne' id='"+name+"Ajax_updateByOne_action'>"+htmlStr+"</form>"
		+"</div>"
		+ "</div>"
		+ "</div>";
		$(".block-new-block").before(htmlStr);
	}else if(showType == "2"){
		var tableStr = "";
		htmlStr = htmlStr + "<div class='dynamic-block' title='"+title+"' name='"+name+"'>"
		+ "<div class='list-table'>"
		+ "<div class='title'><span><i class='i i-xing blockDian'></i>"+title+"</span>"+collapseButtonHtml+"</div>"
		+ "<div class='content collapse in' id='"+name+"' name='"+name+"'>"+tableStr+"</div>"
		+ "</div>"
		+ "</div>";
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
			if(showType == "1"){
				setBlockTitle(showType,title,name);
				var formStr = $html.find("form").prop("outerHTML");
				$(".content[name='"+name+"']").html(formStr);
			}else if(showType == "2"){  
				if($html.find("table #tab tr").length>0){
					setBlockTitle(showType,title,name);
					var tableStr = $html.find("table").prop("outerHTML");
					$(".content[name='"+name+"']").html(tableStr);
				}
			}
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};


function getNextBusPoint(){
	$(".block-next").empty();
	$(".block-next").unbind();
	var busPointInfo = '';
	if(appSts == '1' || appSts == '6'){
		busPointInfo = "<span>申请已提交，正在审批中</span>";
		$(".block-next").append(busPointInfo);
		$(".next-div").removeClass("hide");
		$(".next-div").addClass("show");
	}else if(appSts == '4'){//审批通过
		busPointInfo = "<span>申请已审批通过，请在合同视角中查看信息</span>";
		$(".block-next").append(busPointInfo);
		$(".next-div").removeClass("hide");
		$(".next-div").addClass("show");
	}else if(appSts == '5'){//申请被否决
		busPointInfo = "<span>申请已被否决，业务结束</span>";
		$(".block-next").append(busPointInfo);
		$(".next-div").removeClass("hide");
		$(".next-div").addClass("show");
	}else{
		$.ajax({
			url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId+"&appId="+appId,
			type:'post',
			dataType:'json',
			success:function(data){
				var url = data.url;
				var title=data.title;
				nodeName = data.nodeName;
				if(data.wkfFlag!="0"){
					var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(nodeName);
					if(checkPmsBizFlag){
						//分单特殊处理
						if(data.assign){
							$(".block-next").append("<span>暂时没有权限操作该节点</span>");
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
						}else{
							$(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>"+title+">></a></span>");
							$(".block-next").click(function(){
								toNextBusPoint(url,title,nodeName);
							}); 
							$(".next-div").removeClass("hide");
							$(".next-div").addClass("show");
							if(nodeName!="investigation"){
								//调查资料
								var vouType = '${vouType}';
								if(vouType!="1" && pleFlag=="1"){
									$(".bus-investigate").show();
								}
							}
						}
					}else{
						$(".block-next").append("<span>暂时没有权限操作该节点</span>");
						$(".next-div").removeClass("hide");
						$(".next-div").addClass("show");
				}
				}
			}
		});
	}
};

//跳转至下一业务节点
function toNextBusPoint(url,title,nodeName){
	if(riskLevel == "99"){//riskLevel为99标书拒绝级业务
		var pointInfo = '<div style="height: 120px;padding: 20px;width: 300px;"><div style="height: 40px;">该业务风险过高，无法进行下一步</div><div><a  href="javaScript:void(0);" onclick="busRisk();">查看风险>></a></div></div>';
		top.dialog({
			title:'风险提示',
			id:"riskInfoDialog",
			backdropOpacity:0,
			content:pointInfo
		}).showModal();
		return false;
	}
			top.flag=false;//表示是否进行业务操作
			top.submitFlag=false;
			top.pleFlag = false;
			top.relCorpflag = "";//关联企业标志
			top.getInfoFlag = false;//业务操作后表示是否需要获得信息
			var tmpUrl=url.split("&")[0];
			var popFlag = tmpUrl.split("?")[1].split("=")[1];
			if(popFlag=="0"){
				alert(top.getMessage("CONFIRM_OPERATION",title),2,function(){
					if(!valiDocIsUp(cusNo)){
						return;
					} 
					LoadingAnimate.start();
					$.ajax({
						url:url,
						type:'post',
		    			dataType:'json',
		    			async:false,
		    			complete: function() {
		    				LoadingAnimate.stop();
						},
						success:function(data){
							if(data.flag=="success"){
								if(data.appSts){
									appSts = data.appSts;
								}
								if(data.node=="processaudit"){
									//审批提醒弹窗，不自动关闭
									window.top.alert(data.msg,3);
//									$(".next-div").removeClass("show");
//									$(".next-div").addClass("hide");
									appSts = data.appSts;
									getNextBusPoint();
								}else{
									getNextBusPoint();
									$("#wj-modeler1").empty();
									showWkfFlow($("#wj-modeler1"),wkfAppId);
								}
								initDocNodes();//重新初始化要件
							}else{								    
								alert(data.msg,0);
							}
						}
					});
				});
			} else{
				if(nodeName=="investigation"){
					url = url+"&scNo="+investigateScNo;
					top.window.openBigForm(url,title,function(){
						if(top.investigation){
							$.ajax({
								url:webPath+"/docManage/getDocNodesAjax?"+docParm,
								type:"POST",
								dataType:"json",
								success:function(data){
									query =data.query;
									zTreeNodesDoc = data.zTreeNodes;
									var zTreeObj = $.fn.zTree.init($("#uploadTree"), setting, zTreeNodesDoc);
								},error:function(){
									
								}
							});
							wkfCallBack();
						}						
					});
				}
				//押品登记节点，选择押品类别
				if(nodeName=="pledge_reg"){
					top.collaFlag = false;
					if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
					top.window.openBigForm(url,title,goToCollaDetailInfo);
				}else{
					if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
					top.window.openBigForm(url,title,wkfCallBack);
				}
			}
//			}
//		});
}

//回调函数
function wkfCallBack(){
	if(top.flag){
		if(top.pleFlag){//押品信息回调处理
			setPleInfo(top.pleInfo);
			$(".bus-investigate").show();
		}
		if(top.relCorpflag!=""){//关联企业信息回调处理
			setRelateCorpInfo(top.relCorpflag,top.cusInfo.cusNo,top.cusInfo.cusName);
		}
		if(top.getInfoFlag){
			
			if(top.showType != null){
				setBlock(top.showType,top.title,top.name,top.getInfoUrl);
			}
		}
		if(top.tuningReport){//尽调报告
			appSts = top.appSts;
			if(top.refsh){//需要刷新
				refreshApplyDetailInfo('applyDetailInfo',top.applyDetail);
				oneCallback(eval("("+top.applyInfo+")"));
				top.refsh = false;
			}
			top.tuningReport = false;
		}
		if(top.reinsurance_policy){//分单
			appSts = top.appSts;
			top.reinsurance_policy = false;
		}
		if(top.appSts){
			appSts = top.appSts;
		}
		getNextBusPoint();
		$("#wj-modeler1").empty();
		showWkfFlow($("#wj-modeler1"),wkfAppId);
		initDocNodes();//重新初始化要件
	}
	
}
function openPleDyForm(url,title){
	$.ajax({
		url:webPath+"/mfBusPledge/getPleDyFormInfo",
		data:{appId:appId},
		type:'post',
		dataType:'json',
		success:function(data){
			top.collaFlag=false;
			var formid_new = data.formid_new;
			if(url.substr(0,1)=="/"){
				url =webPath + url; 
			}else{
				url =webPath + "/" + url;
			}
			url = url+'&formid_new='+formid_new;
			top.window.openBigForm(url,title,goToCollaDetailInfo);
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
}


function  refreshApplyDetailInfo(divId,htmlStr){
	var html = '<form  id="MfBusApplyActionAjax_updateAjaxByOne_action" name="operform" action="/factor/MfBusApplyActionAjax_updateAjaxByOne_action" method="post">'+htmlStr+'</form>';
	$("#"+divId).html(html);
};
//获取押品信息
function goToCollaDetailInfo(){
	if(top.collaFlag){
		top.LoadingAnimate.start();
		window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&appId="+appId+"&relId="+appId+"&entrance="+entrance;
	}
};
//选择押品类别
function selectPledgeClass(url,title){
	$.ajax({
		url:webPath+"/mfBusPledge/getIfRegisterPledge",
		data:{appId:appId},
		type:'post',
		dataType:'json',
		success:function(data){
			//没有登记押品信息，打开押品类别；登记过，打开押品清单登记页面
			if(data.flag == "0"){
				top.openBigForm(webPath+'/mfPledgeClass/getAllPledgeClassList?cusNo='+cusNo+'&appId='+appId,'押品类别',wkfCallBack);
			}else{
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.window.openBigForm(url,title,wkfCallBack);
			}
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
}
//关联企业信息回调处理
function setRelateCorpInfo(flag,cusno,cusname){
	var htmlStr = "";
	if(flag =="wareHouseFlag" && menuUrl.indexOf("cuswarehouse")!="-1"){
		htmlStr= htmlStr+'<span  class="relate-corp" data-view="cuswarehouse">'
		+'<i class="i i-cangKu"></i><span>由仓储机构<a href="javascript:void(0);" onclick="getInfoForView(\'103\',\''+cusno+'\',\'仓储机构\');">'+cusname+'</a>保管货物 </span>'
		+'</span>';
	}else if(flag=="coreFlag" && menuUrl.indexOf("cuscore")!="-1"){
		htmlStr= htmlStr+'<span  class="relate-corp" data-view="cuscore">'
		+'<i class="i i-qiYe"></i><span>由核心企业<a href="javascript:void(0);"  onclick="getInfoForView(\'108\',\''+cusno+'\',\'核心企业\');">'+cusname+'</a>推荐 </span>'
		+'</span>';
	}else if(flag=="fundOrgFlag" && menuUrl.indexOf("fundorg")!="-1"){
		htmlStr= htmlStr+'<span  class="relate-corp" data-view="fundorg">'
		+'<i class="i i-fundorg"></i><span>由资金机构<a href="javascript:void(0);"  onclick="getInfoForView(\'109\',\''+cusno+'\',\'资金机构\');">'+cusname+'</a>放款 </span>'
		+'</span>';
	}
	$(".btn-special").append(htmlStr);
}


function setPleInfo(pleInfo){
	$("#pleInfo").empty();
	var htmlStr ='<button type="button" class="btn btn-font-qiehuan pull-right" onclick="getPleInfo();"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
		+'<div class="col-xs-3 col-md-3 column">'
		+'<button type="button" class="btn btn-font-pledge padding_left_15" onclick="getPleInfo();">'
		+'<i class="i i-pledge font-icon"></i>'
		+'</button>'
		+'<div class="font-text">押品信息</div>'
		+'</div>'
		+'<div class="col-xs-9 col-md-9 column">'
		+'<button class="btn btn-link content-title"  title="'+pleInfo.pledgeName+'" onclick="getPleInfo();">'
		+ pleInfo.pledgeName
		+'</button>'
		+'<p>'
		+'<span class="content-span" id="envalueAmt"><i class="i i-rmb" ></i>'+pleInfo.envalueAmt+'</span><span class="unit-span">万</span>'
		+'<span class="content-span" id="receiptsAmount"><i class="i i-danju" ></i>'+pleInfo.receiptsAmount+'</span><span class="unit-span">张单据</span>'
		+'</p>'
		+'</div>'
	$("#pleInfo").html(htmlStr);
	
};

//业务详情页面，切换至客户详情页面
function getCusInfo(cusNoThis){
	top.LoadingAnimate.start();
	window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNoThis+"&appId="+appId+"&operable="+operable;
}


//弹窗查看具体详情信息，关联企业：核心企业、资金机构、仓储机构等
function getInfoForView(custype,id,title){
	top.window.openBigForm(webPath+'/mfCusCustomer/getByIdForShow?cusType='+custype+'&cusNo='+id,title,function(){});
}

//风险拦截
function busRisk(){
	if(!(dialog.get('riskInfoDialog') == null)){
		dialog.get('riskInfoDialog').close();
	}
	top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+appId,'风险拦截信息',function(){});
};


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

//费用信息编辑
function getFeeById(obj,url){
	var $obj = $(obj);
	top.obj = $obj.parents(".dynamic-block");
	top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	if(url.substr(0,1)=="/"){
		url =webPath + url; 
	}else{
		url =webPath + "/" + url;
	}
	top.window.openBigForm(url,'修改费用项',closeCallBack1);
	};
	
	function closeCallBack1(){
	var $obj = $(top.obj);
	if(top.htmlStrFlag){
		$obj.find(".content").empty();
		$obj.find(".content").html(top.htmlString);
		//adjustheight();
	}
};

//查看产品信息
function getKindInfo(){
	top.window.openBigForm(webPath+'/mfBusAppKind/getById?appId='+appId,'产品信息',function(){});
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
	for(var i in data){
		var name = data[i].name;
		var value = data[i].value;
		if(name=="appAmt"){
			value = value.replace(/,/g,"");
			$("span[id="+name+"]").html(parseFloat(value)/10000);
		}
		if(name=="term"){
			$("span[id="+name+"]").html(value);
		}
		if(name=="fincRate"){
			$("span[id="+name+"]").html(value);
		}
	}
	
}
function addedService(){
	LoadingAnimate.start();
	$.ajax({
		url :webPath+"/mfCusCustomer/toAddServicePage",
		type:"post",
		data:{"cusNo":cusNo,"showType":"0"},
		success:function(dataArgs){
			LoadingAnimate.stop();
			if(dataArgs.flag=="success"){
				var url = dataArgs.url;
				var customer = dataArgs.mfCusCustomer;
				var data =customer.headImg;
				if (customer.ifUploadHead != "1") {
					data = "themes/factor/images/" + customer.headImg;
				}
				data = encodeURIComponent(encodeURIComponent(data));
				headImgShowSrc = basePath+webPath+"/uploadFile/viewUploadImageDetail?srcPath=" + data+ "&fileName=user2.jpg";
				if(customer.cusType=="202"){//个人客户
					//url +="?show=0&showType="+data.showType+"&idCardName="+encodeURI(encodeURI(customer.cusName))+"&idCardNum="+customer.idNum+"&phoneNo="+customer.cusTel+"&bankCardNum=622899911919911&blacklistType=person&searchKey="+encodeURI(encodeURI(customer.cusName))+"&dataType=all&reportType=GR";
					url +="?show=0&showType="+data.showType+"&idCardName="+encodeURI(encodeURI(customer.cusName))+"&address="+encodeURI(encodeURI(customer.commAddress))+"&idCardNum="+customer.idNum+"&phoneNo="+customer.cusTel+"&bankCardNum=123456789customer&blacklistType=person&searchKey="+encodeURI(encodeURI(customer.cusName))+"&dataType=all&reportType=GR&cusType="+customer.cusType+""+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));
				}else{
					//url +="?show=0&showType="+data.showType+"&enterpriseName="+encodeURI(encodeURI(customer.cusName))+"&enterpriseNumber="+customer.idNum+"&dataType=all&reportType=QY&address="+encodeURI(encodeURI(customer.commAddress))+"&phoneNo="+customer.contactsTel+"&cusType="+customer.cusType;
					url +="?show=0&showType="+data.showType+"&enterpriseName="+encodeURI(encodeURI(customer.cusName))+"&enterpriseNumber="+customer.idNum+"&dataType=all&reportType=QY&address="+encodeURI(encodeURI(customer.commAddress))+"&phoneNo="+customer.contactsTel+"&cusType="+customer.cusType+"&contactsName="+customer.contactsName+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));
				}
				window.location.href=url;
			}else{
				LoadingAnimate.stop();
				alert(data.msg, 0);
			}
		}
	});
}
