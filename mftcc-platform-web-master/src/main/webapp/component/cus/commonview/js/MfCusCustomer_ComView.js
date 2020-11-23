	
//验证是否必填项
function isMustOrNot(isMust){
	var delBtnHtml1 = "";
	var isMustHtml1 = "";
	if (isMust == "1") {
		isMustHtml1 = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
	} else {
		delBtnHtml1 = "<button class='rotate-del i i-lajitong' title='删除' onclick='deleteRotate(this);'></button>";
	}
	var mustObject = {"delBtnHtml1":delBtnHtml1,"isMustHtml1":isMustHtml1};
	return mustObject; 
}
//判断是否有数据
function dataFullFlagOrNot(dataFullFlag,delFlag,htmlStr,name,showType,title,tableName,isMust,
		rotateBorderColor,rotateColor,rotateTubiaoBac,sort,iconClass,backColor,checkPmsBizFlag){
	var mustObject = isMustOrNot(isMust);
	var dataFlag = false;
	if (dataFullFlag == "0") {
		if (checkPmsBizFlag && delFlag == "0") {
			htmlStr = "";
			htmlStr = "<div class='rotate-div' name='"
					+ name
					+ "' data-showtype='"
					+ showType
					+ "' data-name='"
					+ name
					+ "' data-title='"
					+ title
					+ "' data-tablename='"
					+ tableName
					+ "' data-sort='"
					+ sort
					+ "' data-ismust='"
					+ isMust
					+ "'>"
					+ "<div class='rotate-obj "
					+ rotateBorderColor
					+ "'>"
					+ mustObject.isMustHtml1
					+ "<div class='rotate-des   "
					+ rotateColor
					+ "'><div class='iconCicle' style='background-color:"+backColor+";'><li class='iconStyle "
					+ iconClass
					+ "'></li></div><div class='rotate-formName pull-left'>登记"
					+ title
					+ "</div></div>"
					+ "<div class='rotate-opre' >"
					+ "<button class='rotate-add i i-jia2' onclick='addCusFormByRotate(this);'></button>"
					+ mustObject.delBtnHtml1 + "</div>" + "</div>" + "</div>";
			$("#rotate-body").append(htmlStr);
			htmlStr = "";
		}
		dataFlag = true;
	}
	return dataFlag;
}
//列表展示的话设置添加按钮
function addbutton(showType,addBtnHtml, name){
	var pmsBizNo = 'cus-edit-' + name;// pms_no 规则:"cus-edit-"+action
	var checkPmsBizFlag = BussNodePmsBiz.checkPmsBiz(pmsBizNo);// 检查权限
	if (showType == "2" && checkPmsBizFlag) {
		addBtnHtml = "<button class='btn btn-link formAdd-btn' onclick='addCusFormInfo(this,\""
				+ showType
				+ "\");' title='新增'><i class='i i-jia3'></i></button>";
	}
	return addBtnHtml;
}
//有值的模块显示出来
function showInformation(showType,name,title,htmlStr,clearDiv,addBtnHtml,sort,tableName){
	var addBtnHtml1=addbutton(showType,addBtnHtml, name);
	var _name = name;
	if(name!=null&&name!=""){
		_name = "/"+name.substring(0,1).toLowerCase()+name.substring(1);
		_name =_name.replace("Action","");
	}
	/**
	 * 系统添加了映射地址，拼接的URL地址需要添加webPath，如果不添加拼接的URL地址不正确导致方法无法访问（404） 2018年5月4日17:49:11
	 */
	_name = webPath +"/"+_name;
	//默认添加信息块的位置
	var $blockAdd =$(".block-add");
	var $infoBlock = $(".info-block .dynamic-block");
	if($infoBlock.length>0){
		//重新查找新的信息块应该放置的位置
		$(".info-block .dynamic-block").each(function(index){
			var $this = $(this);
			var thisSort = $(this).data("sort");
			if(thisSort<sort){
				return true;
			}else{
				$blockAdd = $this;
				 return false;
			}
		});
	}
	var htmlStrThis = "";
	if (showType == "1") {
		var collapseButtonHtml = "<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"
				+ name
				+ "'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>"
		htmlStrThis = htmlStrThis
				+ "<div class='dynamic-block' title='"
				+ title
				+ "' name='"
				+ name
				+ "' data-sort='"
				+ sort
				+ "'>"
				+ "<div class='form-table'>"
				+ "<div class='title' ><span class='formName'><i class='i i-xing blockDian'></i>"
				+ title + "</span>"/* +isMustHtml */+ addBtnHtml1
				+ collapseButtonHtml + "</div>"
				+ "<div class='content collapse in' id='" + name + "'>"
				+"<form  action='"+_name+"/updateAjaxByOne' id='"+name+"Ajax_updateByOne_action'>"+htmlStr+"</form>"
				+ "</div>" + "</div>" + "</div>";
		$blockAdd.before(htmlStrThis);
		$blockAdd.before(clearDiv);
	} else if (showType == "2") {
		if (name == "allApp") {
			htmlStrThis = htmlStrThis
					+ "<div class='dynamic-block'>"
					+ "<div class='list-table'>"
					+ "<div class='title'><span class='formName'><i class='i i-xing blockDian'></i>"
					+ title + "</span></div>" + "<div class='content' name='"
					+ name + "'>" + htmlStr + "</div>" + "</div>" + "</div>";
		} else {
			var collapseButtonHtml2 = "<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"
					+ name
					+ "'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>"
			htmlStrThis = htmlStrThis
					+ "<div class='dynamic-block' title='"
					+ title
					+ "' name='"
					+ name
					+ "' data-sort='"
					+ sort
					+"' data-tablename='"
					+ tableName
					+ "'>"
					+ "<div class='list-table'>"
					+ "<div class='title' ><span class='formName'><i class='i i-xing blockDian'></i>"
					+ title + "</span>"/* +isMustHtml */+ addBtnHtml1
					+ collapseButtonHtml2 + "</div>"
					+ "<div class='content collapse in' id='" + name + "' >"
					+ htmlStr + "</div>" + "</div>" + "</div>";
		}
		$blockAdd.before(htmlStrThis);
		$blockAdd.before(clearDiv);
	}
}

//shouType:1是form2是table  tableDes：中文描述（头部标题）    action：custable的action属性  html：后台传过来的html字符串   dataFullFlag：是否填完信息
//isMust:是否必填 tableName:对应表名  color:  delFlag：表单标志是否显示
function setBlock1(showType, title, name, htmlStr, dataFullFlag, isMust,tableName, color, delFlag,sort) {
	var rotateColor = "rotate-color";
	var rotateBorderColor = "rotate-borderColor";
	var rotateTubiaoBac = "rotate-tubiaoBac";
	var clearDiv = '<div style="clear:both;"></div>';
	var addBtnHtml = "";
	var iconClass = "info-box-icon i ";
	var backColor= "";
	var pmsBizNo ='cus-edit-'+name;//pms_no 规则:"cus-edit-"+action
	var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(pmsBizNo);
	
	if (color) {
		rotateColor = "rotate-color" + color;
		rotateBorderColor = "rotate-borderColor" + color;
		rotateTubiaoBac = "rotate-tubiaoBac" + color;
	}
	
	if(color==1){
		iconClass =iconClass + "i-dengji";
		backColor = "#81B960";
	}else if(color==2){
		iconClass =iconClass + "i-jibenxinxi"
		backColor = "#FCB865";
	}else if(color==3){
		iconClass =iconClass + "i-caiji"
		backColor = "#5FC8DB";
	}else if(color==4){
		iconClass =iconClass + "i-qiye"
		backColor = "#8EAFE4";
	}
	//判断是否有值
	var dataFlag = dataFullFlagOrNot(dataFullFlag,delFlag,htmlStr,name,showType,title,tableName,isMust,rotateBorderColor,rotateColor,rotateTubiaoBac,sort,iconClass,backColor,checkPmsBizFlag);
	if(dataFlag){
		return false;
	}
	//有值的情况下显示出来
	showInformation(showType,name,title,htmlStr,clearDiv,addBtnHtml,sort,tableName);
	$("table td[mytitle]:contains('...')").initMytitle();
};
function addCusFormByRotate(obj) {
	var $rotateDiv = $(obj).parents(".rotate-div");
	 
	var title = $rotateDiv.data("title");
	var action = $rotateDiv.data("name");
	if(action == "MfTrenchShareProfitRateAction"){//添加分润规则之前先添加分润配置信息
		var flag = false;
		jQuery.ajax({
            url:webPath+"/mfShareProfitConfig/getByIdAjax",
            data:{cusNo:cusNo},
            type:"post",
            async:false,
            success:function(data){
                if(data.flag == "success"){
                	
                }else{
                	flag = true;
                	alert(top.getMessage("FIRST_OPERATION_ADD","分润配置信息"),3);
                }
            },
            error:function(){
            	flag = true;
            	alert(top.getMessage("FAILED_OPERATION"),0);
            }
        });
		if(flag){
			return;
		}
	}
	top.action = action;
	//处理action为controller;
	action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
	action =action.replace("Action","");
	top.title = title;
	top.isMust = $rotateDiv.data("ismust");
	top.showType = $rotateDiv.data("showtype");
	top.sort = $rotateDiv.data("sort");
	var inputUrl = action + "/input?cusNo=" + cusNo + "&baseType=" + baseType + "&relNo=" + cusNo;
	top.addFlag = false;
	top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	top.baseInfo = null;
	top.updateAssureAmtFlag = false;
	top.openBigForm(inputUrl, title, addCusFormInfoCall);
};
function addCusFormInfo(obj, showType) {// 当客户信息是列表，继续增加一条记录时
	var $dynamicBlock = $(obj).parents(".dynamic-block");
	var title = $dynamicBlock.attr("title");
	var action = $dynamicBlock.attr("name");
	top.action = action;
	//处理action为controller;
	action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
	action =action.replace("Action","");
	var inputUrl = action + "/input?cusNo=" + cusNo+"&baseType="+baseType;
	top.showType = showType;
	top.title = title;
	top.addFlag = false;
	top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	top.baseInfo = null;
	top.getTableUrl = action + "_getListPage.action?cusNo=" + cusNo;
	top.openBigForm(inputUrl, title, addCusFormInfoCall);
};
function addCusFormInfoCall() {
	var action = new Array("MfCusEquityInfoAction","MfCusFamilyInfoAction","MfCusGuaranteeOuterAction","MfCusHighInfoAction","MfCusKeyManAction","MfCusLegalEquityInfoAction","MfCusLegalMemberAction","MfCusShareholderAction","MfCusFamilyInfoAction");
	if (top.addFlag) {
		initCusIntegrity(top.infIntegrity);
		var cusRelation = action.indexOf(top.action);//判断唯一表中是否存入与关联关系有关的信息
		if(cusRelation!="-1"){
			var Relation = true;
			getRelation(Relation);
		}
		$("#rotate-body").find(".rotate-div[name=" + top.action + "]").remove();
		if ($(".dynamic-block[name=" + top.action + "]").length) {
			var $dynamicBlock = $(".dynamic-block[name=" + top.action + "]");
			$dynamicBlock.find(".formDel-btn").remove();
			$dynamicBlock.show();
			if (top.htmlStrFlag) {
				if (top.showType == "1") {
					$dynamicBlock.find(".content form").empty();
					$dynamicBlock.find(".content form").html(top.htmlString);
					$dynamicBlock.find(".formAdd-btn").remove();
				} else {
					$dynamicBlock.find(".content").empty();
					if (top.action == "MfCusBankAccManageAction") {
						$dynamicBlock.find(".content").html(top.htmlString+"<input id='updateByOneUrl' type='hidden' value=webPath+'/mfCusBankAccManage/updateByOneAjax'></input>");
					}else{
						$dynamicBlock.find(".content").html(top.htmlString);
					}	
					
				}
				if(top.action=="MfCusCorpBaseInfoAction"){
					refreshCustomerInfo();
				}
			}
			$("table td[mytitle]:contains('...')").initMytitle();
			
		} else {
			if (top.htmlStrFlag) {
				setBlock1(top.showType, top.title, top.action, top.htmlString,"1", top.isMust, null, null, null,top.sort);
				dblclickflag();
				if(top.action=="MfCusCorpBaseInfoAction"||top.action=="MfCusPersBaseInfoAction"){
					refreshCustomerInfo();
				}
				// adjustheight();
			}
		}
		if (top.action == "MfCusBankAccManageAction") {
			dealBankNo();
		}		
		if (top.action == "MfBusAssureAmtAction" && top.updateAssureAmtFlag == true) {
			$("#MfBusTrenchActionAjax_updateByOne_action").html(top.basehtmlStr);
		}		
	}
};
function deleteRotate(obj) {
	var $rotateDiv = $(obj).parents(".rotate-div");
	$.ajax({
		url : webPath+"/mfCusTable/updateDelFlagAjax?cusNo=" + cusNo
				+ "&tableName=" + $rotateDiv.data("tablename") + "&delFlag=1",
		type : "POST",
		dataType : "json",
		success : function(data) {
			$("#cus-add").parent().show();
			
			if (data.flag == "success") {
				$rotateDiv.remove();
				// adjustheight();
			} else {
				alert(top.getMessage("FAILED_DELETE"), 0);
			}
		},
		error : function() {
			alert(top.getMessage("FAILED_DELETE"), 0);
		}
	});

};

// 获取业务信息
function getBusInfo(appId) {
	LoadingAnimate.start();
	window.location.href = webPath+"/mfBusApply/getSummary?appId=" + appId+"&busEntrance=1&operable="+operable;
};
// 获取合同信息
function getPactInfo(appId) {
	LoadingAnimate.start();
	if(busEntrance=='3'||busEntrance=='6'){
		window.location.href=webPath+"/mfBusPact/getPactFincById?fincId="+fincId+"&appId="+appId+"&busEntrance="+busEntrance+"&operable="+operable;
	}else{
		window.location.href = webPath+"/mfBusPact/getById?appId=" + appId+"&busEntrance="+busEntrance+"&operable="+operable;
	}
};
function getBusDetail(obj, urlThis) {
	var parm = urlThis.split("?")[1];
	var parmArray = parm.split("&");
	var appIdThis = parmArray[0].split("=")[1];
	var appStsThis = parmArray[1].split("=")[1];
	if (appStsThis == "4") {// 表示申请审批通过
		window.location.href = webPath+"/mfBusPact/getById?appId="
				+ appIdThis;
	} else {
		window.location.href = webPath+"/mfBusApply/getSummary?appId="
				+ appIdThis;
	}
};
function addBlockInfo() {
	// 如果客户基础信息不存在，则先录入基本信息
	/*
	 * if(cusBaseFlag == '0'){ addBaseInfo(); return false; }
	 */
	$.ajax({
		url : webPath+"/mfCusTable/checkCusInfoIsFull?cusNo=" + cusNo,
		type : "post",
		dataType : "json",
		success : function(data) {

			if (data.fullFlag == '1') {// 全部都填写了
				alert("客户资料已经全部完善", 0);
			} else if (data.fullFlag == '0') {
				top.addFlag = false;
				top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
				top.htmlString = null;
				top.action = null;
				top.title = null;
				top.name = null;
				top.cusNo = cusNo;
				top.showType = null;
				top.baseInfo = null;
				// top.baseInfo="0";标识 该表单信息是否是客户的基本信息
				top.openBigForm(webPath+'/mfCusTable/getListPage?cusNo='+ cusNo + '&dataFullFlag=0', '选择表单',addCusFormInfoCall);
			}
		},
		error : function() {

		}
	});

};
function updateCusFormStas() {
	top.updateFlag = false;
	top.updateAssureAmtFlag = false;
	top.openBigForm(webPath+'/mfCusTable/getPageUpdateStas?cusNo='+ cusNo+"&cusType="+cusType+"&relNo="+cusNo,'完善资料',function() {
		addCusFormInfoCall();
		if (top.updateFlag) {
			$.ajax({
				url : webPath+"/mfCusTable/getListAjax?cusNo="+ cusNo +"&cusType="+cusType+"&relNo="+cusNo+"&delFlag=0&dataFullFlag=0",
				type : "POST",
				dataType : "json",
				success : function(data) {
					if (data.flag == "success") {
							$("#rotate-body").empty();
							var tmpDelCnt=0;//移除的个数
							$.each(data.cusTableList,function(i,cusTable) {
								setBlock1(cusTable.showType,cusTable.tableDes,cusTable.action,cusTable.htmlStr,cusTable.dataFullFlag,cusTable.isMust,cusTable.tableName,i % 4 + 1,"0",cusTable.sort);
								if(cusTable.delFlag=="1"){
									tmpDelCnt++;
								}
							});
							var cusAdd = '<div class="rotate-div"><div class="rotate-obj rotate-borderColor2" id="cus-add"><div class="rotate-des rotate-color2"><div class="rotate-formName pull-left"><i class="i i-jia1"></i></div></div></div></div>';
							$("#rotate-body").append(cusAdd);
							
							$("#cus-add").click(function() {
								updateCusFormStas();
							});
							if(tmpDelCnt>0){
								$("#cus-add").parent().show();
							}else{
								$("#cus-add").parent().hide();
							}
					}
				},error : function() {}
			});
		}
	});
};
function getCusInfoById(obj, getUrl) {// 根据列表超链接获得信息详情，支持编辑
	var $dynamicBlock = $(obj).parents(".dynamic-block");
	var title = $dynamicBlock.attr("title");
	var action = $dynamicBlock.attr("name");
	top.action = action;
	top.showType = "2";
	top.addFlag = false;
	top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
	top.htmlString = null;
	top.baseInfo = null;
	top.getTableUrl = action + "_getListPage.action?cusNo=" + cusNo;
	top.openBigForm(getUrl, title, addCusFormInfoCall);
};

// 业务办理
function applyInsert() {
	if(!valiDocIsUp(cusNo)){
		return false;
	} 
	if(firstKindNo == '' || firstKindNo == null){
		alert(top.getMessage("FIRST_OPERATION",'产品设置'),0);
		return ;
	}
	// 判断该客户是否完善了基本信息
	$.ajax({
		url : webPath+"/mfCusTable/checkCusInfoMustIsFull?cusNo="+ cusNo,
		type : "post",
		dataType : "json",
		beforeSend: function() {
			LoadingAnimate.start();
		},success : function(data) {
			if (data.fullFlag == '1') {// 全部都填写了
				// 准入拦截
				var parmData = {'nodeNo':'cus_apply', 'relNo': cusNo, 'cusNo': cusNo};
				$.ajax({
					url : webPath+"/riskForApp/getNodeRiskDataForBeginAjax",
					type : "post",
					data : {ajaxData: JSON.stringify(parmData)}, 
					dataType : "json",
					success : function(data) {
						if (data.exsitRefused == true) {// 存在业务拒绝
							top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+cusNo,'风险拦截信息',function(){});
						} else if(data.exsitFX == true){//存在风险项
							alert(top.getMessage("CONFIRM_DETAIL_OPERATION",{"content":"该客户存在风险项","operation":"新增业务"}), 2, function() {
								window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
							});
						}else {
							// top.createShowDialog(webPath+"/mfBusApply/inputQuery?cusNo="+cusNo+"&kindNo="+firstKindNo,"业务申请");
							//传appId时是为了在业务新增页面取消时返回到原来的页面
							window.location.href = webPath+"/mfBusApply/inputQuery?cusNo="+ cusNo+ "&appId="+ appId+ "&kindNo="+ firstKindNo +"&ajaxData=cusbody";
						}
					},
					error : function() {
					}
				});
			} else if (data.fullFlag == '0') {
				alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.infoName),0);
			}
		},error : function() {
		},complete: function(){
			LoadingAnimate.stop();
		}
	});

};

function addBaseInfo() {// 增加客户的基本信息
	top.addFlag = false;
	top.action = "MfCusCorpBaseInfoAction";
	top.showType = '1';
	top.baseInfo = "1";
	top.name = "MfCusCorpBaseInfoAction";
	top.openBigForm(webPath+"/mfCusCorpBaseInfo/input?cusNo=" + cusNo,"基本信息", closeCallBack);
};
function checkCusInfoIsFull() {// 验证客户信息是否已经填写完整
	$.ajax({
		url : webPath+"/mfCusTable/checkCusInfoIsFull?cusNo=" + cusNo,
		type : "post",
		dataType : "json",
		success : function(data) {
			if (data.fullFlag == '1') {// 全部都填写了

			} else if (data.fullFlag == '0') {

			}
		},
		error : function() {

		}
	});
};

// 客户要件信息
function cusDocInfo() {
	window.parent.openBigForm(webPath+'/docManage/pubUpload?relNo=' + cusNo + '&cusNo=' + cusNo + '&scNo=' + scNo, '图文资料', function() {
	});
};

//客户跟进
function cusTrack(type) {
	top.updateFlag = false;
	top.openBigForm(webPath+'/mfCusTrack/getListPage?cusNo=' + cusNo+ "&query=" + type,'客户跟进',function(){
		if (top.updateFlag){
			getCusTrackTopList();
		}
	});
};


// 客户分类
function cusTag() {
	top.updateFlag = false;
	top.classify = false;
	top.openBigForm(webPath+'/mfCusClassify/getByCusNo?cusNo=' + cusNo,'客户分类', function() {
		if (top.updateFlag) {
			if (top.cusClassify == '1') {
				$("#cusNameRate-span").text(top.cusTag);
				$(".cus-tag").addClass("btn-danger");
				$(".cus-tag").removeClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-dodgerblue");
			} else if (top.cusClassify == '2') {
				$("#cusNameRate-span").text(top.cusTag);
				$(".cus-tag").addClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-danger");
				$(".cus-tag").removeClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-dodgerblue");
			} else if (top.cusClassify == '4') {
				$("#cusNameRate-span").text(top.cusTag);
				$(".cus-tag").addClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-danger");
				$(".cus-tag").removeClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-dodgerblue");
			}else if (top.cusClassify == '5') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-dodgerblue");
            } else if (top.cusClassify == '3') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-dodgerblue");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-lightgray");
            } else {
                $("#cusNameRate-span").text("潜在客户");
                $(".cus-tag").addClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }
		}
	}, '90', '90');
};

function cusTagHis() {
	top.openBigForm(webPath+'/mfCusClassify/getListPage?cusNo=' + cusNo,
			'客户分类', function() {
		if (top.updateFlag) {
			if (top.cusClassify == '1') {
				$("#cusNameRate-span").text(top.cusTag);
				$(".cus-tag").addClass("btn-danger");
				$(".cus-tag").removeClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-dodgerblue");
			} else if (top.cusClassify == '2') {
				$("#cusNameRate-span").text(top.cusTag);
				$(".cus-tag").addClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-danger");
				$(".cus-tag").removeClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-dodgerblue");
			}  else if (top.cusClassify == '4') {
				$("#cusNameRate-span").text(top.cusTag);
				$(".cus-tag").addClass("btn-lightgray");
				$(".cus-tag").removeClass("btn-danger");
				$(".cus-tag").removeClass("btn-forestgreen");
				$(".cus-tag").removeClass("btn-dodgerblue");
			}else if (top.cusClassify == '5') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-dodgerblue");
            } else if (top.cusClassify == '3') {
                $("#cusNameRate-span").text(top.cusTag);
                $(".cus-tag").addClass("btn-dodgerblue");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-lightgray");
            } else {
                $("#cusNameRate-span").text("潜在客户");
                $(".cus-tag").addClass("btn-lightgray");
                $(".cus-tag").removeClass("btn-danger");
                $(".cus-tag").removeClass("btn-forestgreen");
                $(".cus-tag").removeClass("btn-dodgerblue");
            }
		}
			});
};

//查看本次授信申请信息
function getCreditHisDataInfo(){
	if(wkfAppId == null || wkfAppId == ""){
		return;
	}
	$.ajax({
		url : webPath+"/mfCusCreditApply/checkWkfEndSts",
		type : 'post',
		dataType : 'json',
		data : {
			wkfAppId:wkfAppId,
			cusNo : cusNo
		},
		success : function(data) {
			top.LoadingAnimate.stop();
			top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId+"&cusNo="+cusNo, '授信申请信息', function() {});
		},
		error : function(data) {
			top.LoadingAnimate.stop();
			alert(data.msg, 0);
		}
	});	
}

// 发起授信 - 客户授信操作
function getAppAuth() {
	top.LoadingAnimate.start();
	$.ajax({
		url : webPath+"/mfCusCreditApply/checkWkfEndSts",
		type : 'post',
		dataType : 'json',
		data : {
			wkfAppId:wkfAppId,
			cusNo : cusNo
		},
		success : function(data) {
			top.LoadingAnimate.stop();
			if(data.status == "0") {  //流程未结束
				top.openBigForm(webPath+'/mfCusCreditApply/openHisData?wkfAppId=' + wkfAppId+"&cusNo="+cusNo, '授信申请历史信息', function() {});
			}else{  //流程结束
				top.LoadingAnimate.start();
				$.ajax({
					url : webPath+"/mfCusCustomer/checkCusBus",
					data : {
						cusNo : cusNo,
						cusType : cusType
					},
					type : "post",
					dataType : "json",
					success : function(data) {
						top.LoadingAnimate.stop();
						//data.fullFlag = "1";
						if (data.fullFlag == "0") {
							alert(top.getMessage("FIRST_COMPLETE_INFORMAATION",data.msg),0);
						} else {
							// 新增
							top.creditFlag=false;
							top.creditType="1";
							top.creditAppId="";
							var url = webPath+"/mfCusCreditApply/input?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo;
							top.openBigForm(url, "授信申请", wkfCallBack);
						}
					},
					error : function(data) {
						top.LoadingAnimate.stop();
						alert(data.msg, 0);
					}
				});
			}
		},
		error : function(data) {
			top.LoadingAnimate.stop();
			alert(data.msg, 0);
		}
	});
}

// 获取下一个业务节点 获取当前业务节点的参数信息
function getNextBusPoint() {
	$.ajax({
		url : webPath+"/mfCusCreditApply/getTaskInfoAjax?wkfAppId=" + wkfAppId,
		type : 'post',
		dataType : 'json',
		success : function(data) {
			var wkfFlag = data.wkfFlag;
			if (wkfFlag == '0') {
				$(".bg-danger").removeClass("show");
				$(".bg-danger").addClass("hide");
			} else if (wkfFlag == '1') {
				var url = data.url;
				var title = data.title;
				var nodeName = data.nodeName;
				$(".block-next").empty();
				$(".next-div").unbind();
				if ((data.creditSts == "2" || data.creditSts == "3") && nodeName == "credit_approval") { // 审批环节
					$(".block-next").append("<span>业务在" + title + "阶段</span>");
					$(".next-div").removeClass("hide");
					$(".next-div").addClass("show");
					$("#showWkf").removeClass("hide");
					$("#showWkf").addClass("show");
				} else {
					$(".block-next").append( "<span id='point'>下一业务步骤：<a class='font_size_20'>" + title + "&gt;&gt;</a></span>");
					$(".next-div").removeClass("hide");
					$(".next-div").addClass("show");
					$("#showWkf").removeClass("hide");
					$("#showWkf").addClass("show");
					$(".next-div").click(function() {
						//toNextBusPoint(url, title, nodeName, wkfAppId);
						toNextBusPoint(url, title, nodeName);
					});
				}
			}
		}
	});
}

// 跳转至下一业务节点
function toNextBusPoint(urlArgs, title, nodeName) {
	var tmpUrl = urlArgs.split("&")[0];
	var popFlag = tmpUrl.split("?")[1].split("=")[1];
	if (popFlag == "0") {// popFlag=1打开新窗口 popFlag=0反之
		if (nodeName == "credit_approval") {
			alert(top.getMessage("CONFIRM_OPERATION",title), 2, function() {
				LoadingAnimate.start();
				$.ajax({
					url : tmpUrl,
					type : 'post',
					dataType : 'json',
					data : {
						cusNo : cusNo,
						wkfAppId : wkfAppId
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							if (data.node == "processaudit") {
								window.top.alert(data.msg, 3);
								//实时更新授信状态
								$.ajax({
									url : webPath+"/mfCusCreditApply/getCreditStsInfo",
									data : {
										wkfAppId : wkfAppId
									},
									type : 'post',
									dataType : 'json',
									success : function(data) {
										var status = data.status;
										var creditSum = data.creditSum;
										var applySum = data.applySum;
										getCreditSts(status,creditSum,applySum);
									},
									error : function() {
										alert(data.msg, 0);
									}
								});
								getNextBusPoint();
//								showWkfFlow(wkfAppId);
								$("#wj-modeler1").empty();
								showWkfFlow($("#wj-modeler1"),wkfAppId);
							} else {
								getNextBusPoint();
//								showWkfFlow(wkfAppId);
								$("#wj-modeler1").empty();
								showWkfFlow($("#wj-modeler1"),wkfAppId);
							}
						} else {
							alert(data.msg);
						}
					}
				});
			});
		}
	} else {
		top.creditFlag=false;
		if (nodeName == "collateral") { // 担保信息
			top.addCreditCollaFlag=false;
			top.creditAppId="";
			if(creditType=="2"){//调整登记担保信息，跳转到详情去新增
				//直接提交下一步流程
				$.ajax({
					url: webPath+"/mfCusCreditApply/doCommitWkf",
					type:"post",
					dataType:"json",
					data:{
						wkfAppId:wkfAppId,
						commitType:"collateral",
					},
					error:function(){
						alert('提交到下一个节点时发生异常', 0);
					},
					success:function(data){
						if(data.flag == "success"){
							window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+creditAppId+"&appId="+appId+"&entrance=credit";
						}
					}
				});
			}else if(creditType=="1"){
				top.window.openBigForm(url, title, toCollateralDetail);
			}
		}
		if (nodeName == "protocolPrint") {// 授信协议
			var url = webPath+"/mfCusCreditApply/protocolPrint?wkfAppId=" + wkfAppId;
			top.window.openBigForm(url, title, wkfCallBack);
		}
		if (nodeName == "report") { // 尽职报告
			//var url = webPath+"/mfCusCreditApply/getOrderDescFirst?wkfAppId=" + wkfAppId + "&cusNo=" + cusNo + "&openType=1";
			top.openBigForm(url+"&wkfAppId="+wkfAppId, "授信尽调报告", wkfCallBack);
		}
	}
}
//登记过押品信息后跳转押品详情
function toCollateralDetail(){
	if(top.addCreditCollaFlag){
		window.location.href=webPath+"/mfBusCollateralRel/getCollateralInfo?cusNo="+cusNo+"&relId="+top.creditAppId+"&appId="+appId+"&entrance=credit";
	}
}
// 回调函数
function wkfCallBack() {
	if(top.creditFlag){
		if (top.wkfAppId != undefined && top.wkfAppId != "") {
			wkfAppId = top.wkfAppId;
		}
		if (top.creditType != undefined && top.creditType != "") {
			creditType = top.creditType;
		}
		if (top.creditAppId != undefined && top.creditAppId != "") {
			creditAppId = top.creditAppId;
		}
		//实时更新授信状态
		$.ajax({
			url : webPath+"/mfCusCreditApply/getCreditStsInfo",
			data : {
				wkfAppId : wkfAppId
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				var status = data.status;
				var creditSum = data.creditSum;
				var applySum = data.applySum;
				getCreditSts(status,creditSum,applySum);
			},
			error : function() {
				alert(data.msg, 0);
			}
		});
		getNextBusPoint();
		$("#wj-modeler1").empty();
		showWkfFlow($("#wj-modeler1"),wkfAppId);
	}
}
//提交审批
function processSubmitAjax(){
	if(top.creditFlag){
			wkfCallBack();
			alert(top.getMessage("CONFIRM_OPERATION","提交到审批"),2,function(){
				LoadingAnimate.start();
				$.ajax({
					url : webPath+"/mfCusCreditApply/processSubmitAjax",
					type : 'post',
					dataType : 'json',
					data : {
						cusNo : cusNo,
						wkfAppId : wkfAppId
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							if (data.node == "processaudit") {
								window.top.alert(data.msg, 3);
								//实时更新授信状态
								$.ajax({
									url : webPath+"/mfCusCreditApply/getCreditStsInfo",
									data : {
										wkfAppId : wkfAppId
									},
									type : 'post',
									dataType : 'json',
									success : function(data) {
										var status = data.status;
										var creditSum = data.creditSum;
										var applySum = data.applySum;
										getCreditSts(status,creditSum,applySum);
									},
									error : function() {
										alert(data.msg, 0);
									}
								});
								getNextBusPoint();
								$("#wj-modeler1").empty();
								showWkfFlow($("#wj-modeler1"),wkfAppId);
							} else {
								getNextBusPoint();
								$("#wj-modeler1").empty();
								showWkfFlow($("#wj-modeler1"),wkfAppId);
							}
						} else {
							alert(data.msg);
						}
					}
				});
			});
	}
}
//获取授信状态及授信额度
function getCreditSts(status,creditSum,applySum){
	if(status == "1"){
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().removeClass("btn-dodgerblue");
		$(".creditBus").parent().addClass("cus-middle");
		$(".creditBus").html("授信中");
	}
	if(status == "2"){
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().removeClass("btn-dodgerblue");
		$(".creditBus").parent().addClass("cus-middle");
		$(".creditBus").html("授信中");			
	}
	if(status == "3"){
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().removeClass("btn-dodgerblue");
		$(".creditBus").parent().addClass("cus-middle");
		//$(".creditBus").html("审批通过");
		$(".creditBus").html("授信中");
	}
	if(status == "5"){ //已签约
		$(".creditBus").parent().removeClass("btn-lightgray");
		$(".creditBus").parent().removeClass("cus-middle");
		$(".creditBus").parent().addClass("btn-dodgerblue");
		$(".creditBus").html(applySum+" 万");
		$("#showWkf").removeClass("show");
		$("#showWkf").addClass("hide");
	}
}

function getInitatEcalApp() {
	LoadingAnimate.start();
	$.ajax({
		url : webPath+"/appEval/getUnfinishedAppEval",
		data : {
			cusNo : cusNo,
			cusBaseType:cusBaseType
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			LoadingAnimate.stop();
			if (data.status == "0") {
				alert(data.msg, 0);
				return;
			}
			if (data.flag == "1") {//未提交
				top.openBigForm(webPath+"/appEval/getDetailInfo?evalAppNo="+ data.evalAppNo+"&cusNo=" + cusNo, "评级申请", function() {});
			}else if(data.flag == "2"){//审批阶段查看详情
				top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=2&useType=1", "评级信息", function() {});
			}else{//新增或审批完成
				top.openBigForm(webPath+"/appEval/initiateApp?cusNo="+ cusNo, "评级申请", function() {});
			}
		},
		error : function() {
			LoadingAnimate.stop();
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

/*外部评级*/
function getManEvalApp() {
	LoadingAnimate.start();
	$.ajax({
		url : webPath+"/appEval/getUnfinishedAppEval",
		data : {
			cusNo : cusNo,
			cusBaseType:cusBaseType
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {
			LoadingAnimate.stop();
			if (data.status == "0") {
				alert(data.msg, 0);
				return;
			}
			if (data.flag == "1") {//未提交
				top.openBigForm(webPath+"/appEval/getDetailInfo?evalAppNo="+ data.evalAppNo+"&cusNo=" + cusNo, "评级申请", function() {});
			}else if(data.flag == "2"){//审批阶段查看详情
				top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=2&useType=1", "评级信息", function() {});
			}else{//新增或审批完成
				top.openBigForm(webPath+"/appEval/initManEavl?cusNo="+ cusNo, "评级申请", function() {});
			}
		},
		error : function() {
			LoadingAnimate.stop();
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

// 获取评级信息
function getEvalDetailResult(parm) {
	if (parm == '1') {
		top.openBigForm(webPath+"/appEval/getDetailResult?cusNo=" + cusNo+ "&appSts=4&useType=1", "评级信息", function() {});
	} else {
		return false;
	}
};

// 上传头像
function uploadHeadImg() {
	window.parent.openBigForm(webPath+'mfCusCustomer/uploadHeadImg?relNo='+ cusNo + '&cusNo=' + cusNo, '客户头像', showNewHeadImg);
};
function showNewHeadImg() {
	var data;
	$.ajax({
				url : webPath+"/mfCusCustomer/getIfUploadHeadImg",
				data : {
					cusNo : cusNo
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "1") {
						data = encodeURIComponent(encodeURIComponent(data.headImg));
						document.getElementById('headImgShow').src = webPath+"/uploadFile/viewUploadImageDetail?srcPath="
								+ data + "&fileName=user2.jpg";
					} else {
						data = "themes/factor/images/" + data.headImg;
						document.getElementById('headImgShow').src = webPath+"/uploadFile/viewUploadImageDetail?srcPath="
								+ data + "&fileName=user2.jpg";
					}
				},
				error : function() {
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
	delFile();
};
//-------------------------------------财务报表模块功能 START-----------------------------------------------------------//
//获得财务报表信息
function getPfsDialog() {
	top.isUpload = false;
	top.openBigForm(webPath+'/cusFinMain/getListPage1?cusNo='+cusNo,'财务报表', function() {
		if(top.isUpload){  //财务报表上传成标志			
			LoadingAnimate.start();
			$.ajax({
				url : webPath+"/cusFinMain/queryCusFinDataAjax",
				data : {
					cusNo : cusNo
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					getFinDataHtml(data);
				},
				error : function() {
					LoadingAnimate.stop();
				}
			});
		}
	});
};
//获得跟名下企业财务报表信息
function getPersonPfsDialog() {
	top.isUpload = false;
	top.openBigForm(webPath+'/cusFinMain/getListPageForPerson?cusNo='+cusNo,'财务报表', function() {
		if(top.isUpload){  //财务报表上传成标志			
			LoadingAnimate.start();
			$.ajax({
				url : webPath+"/cusFinMain/queryCusFinDataAjax",
				data : {
					cusNo : cusNo
				},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					getPersonFinDataHtml(data);
				},
				error : function() {
					LoadingAnimate.stop();
				}
			});
		}
	});
};
//组装财务报表信息块
function getFinDataHtml(data){
	initCusIntegrity(top.infIntegrity);
	if(data.cusFinMainList.length > 0){
		$("div[name=MfCusFinMainAction]").remove();
		var finDiv = '<div name="MfCusFinMainAction" title="财务报表" class="dynamic-block">'+
		'<div class="list-table">'+
			'<div class="title">'+
				'<span class="formName"> <i class="i i-xing blockDian"></i>财务报表</span>'+
				'<button title="新增" onclick="getPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>'+
				'<button data-target="#CusFinMainAction" data-toggle="collapse" class=" btn btn-link pull-right formAdd-btn">'+
					'<i class="i i-close-up"></i><i class="i i-open-down"></i>'+
				'</button>'+
			'</div>'+
			'<div id="CusFinMainAction" class="content collapse in">'+
				'<table cellspacing="1" border="0" align="center" width="100%" class="ls_list" id="tablist">'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<thead>'+
						'<tr>'+
							'<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">报表日期</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>'+
						'</tr>'+
					'</thead>'+
					'<tbody id="tab">';
		var htmlStr = "";
		$.each(data.cusFinMainList,function(i,cusFinMain){
			var viewStr = webPath+"/cusFinMain/inputReportView?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&accRule=1";
			var confirStr = webPath+"/cusFinMain/updateReportConfirmAjax?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo;
			var delStr = webPath+"/cusFinMain/deleteAjax?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo;
			var zcStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',proStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',cashStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>';
            var opStr = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;';
            if(reportConfirmFlag != 2){
                if(cusFinMain.finRptSts != 1){
                    opStr = '<a id="finDataFirm" class="abatch" onclick="confirmFinMain(this,\''+confirStr+'\');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                }else{
                    zcStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',proStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',cashStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>';
                }
            }
            if(cusFinMain.ifShowDel == "1"){
                opStr = opStr + '<a id="finDataDel" class="abatch_del" onclick="FormFactor.deleteTrAjax(this,\''+delStr+'\');return false;" href="javascript:void(0);">删除</a>';
            }
            if(cusFinMain.ifShowDel == "0"){
                opStr = opStr + '<span class="listOpStyle">删除</span>';
            }
			if(!cusFinMain.finCapFlag){
				zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					zcStr = "上传";
				}
			}
			if(!cusFinMain.finProFlag){
				proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					proStr = "上传";
				}
			}
			if(!cusFinMain.finCashFlag){
				cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					cashStr = "上传";
				}
			}
			htmlStr += '<tr>'+
							'<td align="center" width="10%">'+
								'<a class="abatch" onclick="reportView(this,\''+viewStr+'\');return false;" href="javascript:void(0);">'+cusFinMain.finRptDate+'</a>'+
							'</td>'+
							'<td align="center" width="15%">'+
								zcStr+
							'</td>'+
							'<td align="center" width="15%">'+
								proStr+
							'</td>'+
							'<td align="center" width="15%">'+
								cashStr+
							'</td>'+
							'<td align="center" width="20%">'+
								opStr+
							'</td>'+
						'</tr>';
		});
		finDiv = finDiv + htmlStr + '</tbody></table></div></div></div>'; 
		$(".block-add").after(finDiv);
	}
}
//组装名下企业财务报表信息块
function getPersonFinDataHtml(data){
	initCusIntegrity(top.infIntegrity);
	if(data.cusFinMainList.length > 0){
		$("div[name=MfCusFinMainAction]").remove();
		var finDiv = '<div name="MfCusFinMainAction" title="名下企业财务报表" class="dynamic-block">'+
		'<div class="list-table">'+
			'<div class="title">'+
				'<span class="formName"> <i class="i i-xing blockDian"></i>名下企业财务报表</span>'+
				'<button title="新增" onclick="getPersonPfsDialog();return false;" class="btn btn-link formAdd-btn"><i class="i i-jia3"></i></button>'+
				'<button data-target="#CusFinMainAction" data-toggle="collapse" class=" btn btn-link pull-right formAdd-btn">'+
					'<i class="i i-close-up"></i><i class="i i-open-down"></i>'+
				'</button>'+
			'</div>'+
			'<div id="CusFinMainAction" class="content collapse in">'+
				'<table cellspacing="1" border="0" align="center" width="100%" class="ls_list" id="tablist">'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<colgroup style="width: 10%"></colgroup>'+
					'<thead>'+
						'<tr>'+
							'<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">报表日期</th>'+
							'<th align="center" width="10%" scope="col" name="shareholderName" sorttype="0">名下企业名称</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">资产负债表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">利润分配表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">现金流量表</th>'+
							'<th align="center" width="10%" scope="col" name="pushCapitalScale" sorttype="0">操作</th>'+
						'</tr>'+
					'</thead>'+
					'<tbody id="tab">';
		var htmlStr = "";
		$.each(data.cusFinMainList,function(i,cusFinMain){
			var viewStr = webPath+"/cusFinMain/inputReportView?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&accRule=1"+"&relationCorpName="+cusFinMain.relationCorpName+"&relationCorpNo="+cusFinMain.relationCorpNo;
			var confirStr = webPath+"/cusFinMain/updateReportConfirmAjax?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&relationCorpName="+cusFinMain.relationCorpName+"&relationCorpNo="+cusFinMain.relationCorpNo;
			var delStr = webPath+"/cusFinMain/deleteAjax?finRptType="+cusFinMain.finRptType+"&finRptDate="+cusFinMain.finRptDate+"&cusNo="+cusFinMain.cusNo+"&relationCorpName="+cusFinMain.relationCorpName+"&relationCorpNo="+cusFinMain.relationCorpNo;
			var zcStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',proStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>',cashStr = '<i style="line-height: 2.5;" class="i i-gouxuan color_theme"></i>';
            var opStr = '<span class="listOpStyle">数据确认</span>&nbsp;&nbsp;&nbsp;&nbsp;';
            if(reportConfirmFlag != 2){
                if(cusFinMain.finRptSts != 1){
                    opStr = '<a id="finDataFirm" class="abatch" onclick="confirmFinMain(this,\''+confirStr+'\');return false;" href="javascript:void(0);">数据确认</a>&nbsp;&nbsp;&nbsp;&nbsp;';
                }else{
                    zcStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',proStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>',cashStr = '<i style="line-height: 2.5;color:gray;" class="i i-gouxuan color_theme"></i>';
                }
            }
            if(cusFinMain.ifShowDel == "1"){
                opStr = opStr + '<a id="finDataDel" class="abatch_del" onclick="FormFactor.deleteTrAjax(this,\''+delStr+'\');return false;" href="javascript:void(0);">删除</a>';
            }
            if(cusFinMain.ifShowDel == "0"){
                opStr = opStr + '<span class="listOpStyle">删除</span>';
            }
			if(!cusFinMain.finCapFlag){
				zcStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					zcStr = "上传";
				}
			}
			if(!cusFinMain.finProFlag){
				proStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					proStr = "上传";
				}
			}
			if(!cusFinMain.finCashFlag){
				cashStr = '<a class="abatch" href="javascript:void(0);" onclick="getPersonPfsDialog();return false;">上传</a>';
				if(cusFinMain.finRptSts == 1){
					cashStr = "上传";
				}
			}
			htmlStr += '<tr>'+
							'<td align="center" width="10%">'+
								'<a class="abatch" onclick="reportView(this,\''+viewStr+'\');return false;" href="javascript:void(0);">'+cusFinMain.finRptDate+'</a>'+
							'</td>'+
							'<td align="center" width="15%">'+
								cusFinMain.relationCorpName+
							'</td>'+
							'<td align="center" width="15%">'+
								zcStr+
							'</td>'+
							'<td align="center" width="15%">'+
								proStr+
							'</td>'+
							'<td align="center" width="15%">'+
								cashStr+
							'</td>'+
							'<td align="center" width="20%">'+
								opStr+
							'</td>'+
						'</tr>';
		});
		finDiv = finDiv + htmlStr + '</tbody></table></div></div></div>'; 
		$(".block-add").after(finDiv);
	}
}
//打开财务报表查看页面
function reportView(obj,url){
	top.openBigForm(url,'财务报表',false);
};
//数据确认
function confirmFinMain(obj,url){
	LoadingAnimate.start();
	var parm = "?"+url.split("?")[1];
	$.ajax({
		type:"post",
		url:webPath+"/cusFinMain/checkFinDataAjax"+parm,
		dataType:"json",
		success:function(data){
			if(data.flag=="success"){
				if(data.checkFlag == "success"){
					LoadingAnimate.stop();
					alert(top.getMessage("CONFIRM_OPERATION","数据确认"),2,function(){
						LoadingAnimate.start();
						doCofrimData(obj,url);
					});
				}else{
					LoadingAnimate.stop();
					alert(top.getMessage("CONFIRM_FIN_VERIFY"),2,function(){
						LoadingAnimate.start();
						doCofrimData(obj,url);
					});
					
				}
			}else if(data.flag=="error"){
				LoadingAnimate.stop();
				alert(top.getMessage("ERROR_FIN_VERIFY"),0);
			}
		},error:function(){
			LoadingAnimate.stop();
			window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
		}
	});
}

//执行确认操作
function doCofrimData(obj,url){
	var tdObj = $(obj).parent();
	$.ajax({
		type:"post",
		url:url,
		dataType:"json",
		success:function(data){
			LoadingAnimate.stop();
			if(data.flag=="success"){
                $(tdObj).parent().find('td:not(:first)').find(".color_theme").css("color","gray");
                $(tdObj).parent().find('td:not(:first)').find('.abatch').css("color","gray");
                $(tdObj).parent().find('td:not(:first)').find('.abatch').removeAttr('href');//去掉a标签中的href属性
                $(tdObj).parent().find('td:not(:first)').find('.abatch').removeAttr('onclick');//去掉a标签中的onclick事件
			}else if(data.flag=="error"){
				window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
			}
		},error:function(){
			LoadingAnimate.stop();
			window.top.alert(top.getMessage("ERROR_FIN_VERIFY"),0);
		}
	});
}
//-------------------------------------财务报表模块功能 END-----------------------------------------------------------//

// 删除文件
function delFile() {
	var srcPath = "/tmp/";
	$.ajax({
		url : webPath+"/uploadFile/delFile",
		data : {
			srcPath : srcPath
		},
		type : 'post',
		dataType : 'json',
		success : function(data) {

		},
		error : function() {
			alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
		}
	});
};

function cusRelation() {
	top.openBigForm(webPath+'/mfCusRelation/forDetail?cusNo=' + cusNo,
			'关联关系', function(){});
};
//用款
function useFundApp() {
	top.openBigForm(webPath+'/mfBusUseFund/getListPage?cusNo=' + cusNo, '用款下拨', function(){});
};
//保证金释放
function releaseCashDepositApp(){
	top.openBigForm(webPath+'/mfBusReleaseCashDeposit/getDealerListPage?cusNo=' + cusNo, '保证金释放', function(){});
};
//开户
function mfBusOpenAccountApp(){
	top.openBigForm(webPath+'/mfBusOpenAccount/getDealerListPage?cusNo=' + cusNo, '开户', function(){});
};
//销户
function mfBusCloseAccountApp(){
	top.openBigForm(webPath+'/mfBusCloseAccount/getDealerListPage?cusNo=' + cusNo, '销户', function(){});
};
//经销商授信复核
function dealerCreditReCheck(){
	var url = webPath+"/mfCusCreditApply/dealerCreditReCheckAjax?cusNo=" + cusNo;
	$.ajax({
		url : url,
		data : {},
		type : 'post',
		dataType : 'json',
		async : true,
		success : function(data) {
			if(data.flag=="success"){
				alert(data.msg, 1);
			}else{
				alert(data.msg, 0);
			}
		},error : function(){
			alert(top.getMessage("ERROR_REQUEST_URL", url), 0);
		}
	});
};
//经销商赎证申请
function mfBusRedeemCertificateApp(){
	top.openBigForm(webPath+'/mfBusRedeemCertificate/getDealerListPage?cusNo=' + cusNo, '赎证', function(){});
};
function cusRelationIn() {
	top.relation = false;
	window.parent.openBigForm(webPath+'/mfCusRelationType/input?cusNo='+ cusNo, '新增关联关系', function() {
		if(top.relation){
			getRelation(top.relation);
		}
	});
};
// 打开修改客户基本信息页面
function updateCustomerInfo() {
	top.updateFlag = false;
	top.cusName = "";
	top.contactsName = "";
	top.contactsTel = "";
	top.idNum = "";
	top.commAddress = "";
	top.window.openBigForm(webPath+'/mfCusCustomer/toUpdate?cusNo=' + cusNo, '修改客户信息',refreshCustomerInfo);
}
// 刷新客户登记信息以及基本信息
function refreshCustomerInfo() {
	if (top.updateFlag) {
        var BaseInfoFlag;
		if(top.cusType.substring(0,1)=="2"){
			$("span[id=idNum]").html(top.idNum!=null&&top.idNum!=''?top.idNum:"<span class='unregistered'>未登记</span>");
			$("span[id=contactsTel]").html(top.cusTel!=null&&top.cusTel!=''?top.cusTel:"<span class='unregistered'>未登记</span>");
			$("span[id=commAddress]").html(top.commAddress!=null&&top.commAddress!=''?top.commAddress:"<span class='unregistered'>未登记</span>");
			$("span[id=postalCode]").html(top.postalCode!=null&&top.postalCode!=''?top.postalCode:"<span class='unregistered'>未登记</span>");
			// 刷新基本信息
			BaseInfoFlag = $("[name=MfCusPersBaseInfoAction]").length;
			if (BaseInfoFlag == "1") {
				$("#MfCusPersBaseInfoActionAjax_updateByOne_action").html(top.htmlStr);
			}
		}else{
			$("div[id=cusNameShow]").html(top.cusName!=null&&top.cusName!=''?top.cusName:"<span class='unregistered'>未登记</span>");
			$("span[id=contactsName]").html(top.contactsName!=null&&top.contactsName!=''?top.contactsName:"<span class='unregistered'>未登记</span>");
			$("span[id=contactsTel]").html(top.contactsTel!=null&&top.contactsTel!=''?top.contactsTel:"<span class='unregistered'>未登记</span>");
			$("span[id=idNum]").html(top.idNum!=null&&top.idNum!=''?top.idNum:"<span class='unregistered'>未登记</span>");
			$("span[id=commAddress]").html(top.commAddress!=null&&top.commAddress!=''?top.commAddress:"<span class='unregistered'>未登记</span>");
			$("span[id=postalCode]").html(top.postalCode!=null&&top.postalCode!=''?top.postalCode:"<span class='unregistered'>未登记</span>");
		
			// 刷新基本信息
			BaseInfoFlag = $("[name=MfCusCorpBaseInfoAction]").length;
			if (BaseInfoFlag == "1") {
				$("#MfCusCorpBaseInfoActionAjax_updateByOne_action").html(top.htmlStr);
			}
		}
	
	}
};

function selectAreaCallBack(areaInfo) {
	$("input[name=careaCity]").val(areaInfo.disName);
	$("input[name=careaProvice]").val(areaInfo.disNo);
};
function selectWayClassCallBack(waycls) {
	$("input[name=wayClassName]").val(waycls.disName);
	$("input[name=wayClass]").val(waycls.disNo);
};

//填充押品信息
function setPleInfo(data) {
	$("#pleInfo").addClass("hide");
	$("#pleInfono").addClass("hide");
	$("#pleInfo").removeClass("show");
	$("#pleInfono").removeClass("show");
	if (data.mfBusPledge.id != null) {
		// 填充业务信息
		setCollateralInfo(data.mfBusPledge);
		MfBusCollateralRel_AbstractInfo.collateralRelId = data.mfBusPledge.busCollateralId;
		$("#pleInfo").removeClass("hide");
		$("#pleInfo").addClass("show");
		if(data.busModel!="5"){
			if(data.evalFlag=="0"){
				$("#pledgeInfo").html("没有登记评估信息");
			}
		}
	}else{
		$("#pleInfono").removeClass("hide");
		$("#pleInfono").addClass("show");
		if(busModel=="13"||busModel=="5"){
			$("#title").html("应收账款");
			$("#noPledge").html("暂无应收账款");
			$("#titleLi").attr("class","i i-rece font-icon");
		}
	}
};


//填充业务信息
function setBusInfo(data) {
	var busInfoObj = data.mfbusInfo;
	var mfBusApplyListTmp = data.mfBusApplyList;
	var appName = busInfoObj.appName;
	if (appName.length > 8) {
		appName = appName.substring(0, 8) + "...";
	}
	var htmlStr="";
	var busId = data.mfbusInfo.appId;
	if(data.showFlag=="apply"){
		htmlStr = '<div class="col-xs-3 col-md-3 column">'
				+ '<button type="button" class="btn btn-font-app padding_left_15" onclick="getBusInfo(\''+ busId + '\');">'
				+ '<i class="i i-applyinfo font-icon"></i>'
				+ '<div class="font-text">申请信息</div>' + '</button>' + '</div>';
	}else if(data.showFlag=="pact"){
		htmlStr = '<div class="col-xs-3 col-md-3 column">'
				+ '<button type="button" class="btn btn-font-pact padding_left_15" onclick="getPactInfo(\''+ busId + '\');">'
				+ '<i class="i i-pact font-icon"></i>'
				+ '<div class="font-text">合同信息</div>' + '</button>' + '</div>';
	}
	// 如果业务笔数大于3笔
	if (data.mfBusApplyList.length > 3){
			htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix padding_top_20">'
					+ '<span>客户共有 <a  class="moreCnt more-apply-count pointer" onclick="getMultiBusList();">'
					+ (data.mfBusApplyList.length + 1)
					+'</a> 笔在履行业务'
					+ '</span>'
				+ '</div>'
			+ '</div>';	
	} else {
		htmlStr = htmlStr + '<div class="col-xs-9 col-md-9 column">'
				+ '<div class="row clearfix">'
				+ '<div class="col-xs-10 col-md-10 column">';
		if(frompage=="pledge"){//如果主体页面是押品页面，业务不展示多笔业务的情况
			if(data.showFlag=="apply"){
				htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
			}else if(data.showFlag=="pact"){
				htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
			}
		}else{
			// 如果业务笔数为1条
			if (data.mfBusApplyList.length == 0) {
				if(data.showFlag=="apply"){
					htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getBusInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
				}else if(data.showFlag=="pact"){
					htmlStr = htmlStr+ '<button class="btn btn-link content-title" onclick="getPactInfo(\''+ busId + '\');" title="'+ busInfoObj.appName + '">' + appName + '</button>';
				}
			} else {
				var tmpStr = '';
				$.each(data.mfBusApplyList,function(i, appObj) {
					var appObjName = appObj.appName;
					var len = busInfoObj.appName.length;
					if (appObjName.length > len) {
						appObjName = appObjName.substring(0, len)+ "...";
					}
					tmpStr = tmpStr+ '<li class="more-content-li" onclick="switchBus(\''+ appObj.appId+ '\');">'
							+ '<p class="more-title-p"><span>'+ appObjName+ '</span></p>'
							+ '<p class="more-content-p"><span class="more-span">总金额 '+ appObj.appAmt+ '元</span><span class="more-span">期限 '
							+ appObj.termShow + '</span><span class="more-span">利率 '+ appObj.fincRate + '%</span></p>'
							+ '</li>';
				});
				htmlStr = htmlStr
						+ '<div class="btn-group">'
						+ '<button type="button" id="apply-name" class="btn btn-link content-title dropdown-toggle"  data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" title="'
						+ busInfoObj.appName + '">'+ appName
						+ '</button>'
						+ '<button type="button" id="more-apply" class="btn btn-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
						+ '<span class="badge">' + data.mfBusApplyList.length+ '</span>' 
						+ '</button>' 
						+ '<ul class="dropdown-menu">'+ tmpStr + '</ul>' + '</div>';
			}
		}
		if(data.showFlag == "apply"){
			htmlStr = htmlStr
					+ '</div><div class="col-xs-2 col-md-2 column">'
					+ '<button type="button" class="btn btn-font-qiehuan"  style="margin-top: -5px;" onclick="getBusInfo(\''+ busId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
					+ '</div>';
		}else if(data.showFlag == "pact"){
			htmlStr = htmlStr
					+ '</div><div class="col-xs-2 col-md-2 column">'
					+ '<button type="button" class="btn btn-font-qiehuan" style="margin-top: -5px;" onclick="getPactInfo(\''+ busId + '\');"><i class="i i-qiehuan" style="font-size:22px;"></i></button>'
					+ '</div>';
		}

		var unitStr = "天";
		if (busInfoObj.termType == "1") {
			unitStr = "个月";
		}
		htmlStr = htmlStr + '<p>'
				+ '<span class="content-span"><i class="i i-rmb"></i>'+ busInfoObj.fincAmt+ '</span><span class="unit-span">万</span>'
				+ '<span class="content-span"><i class="i i-richengshezhi"></i>'+ busInfoObj.term + '</span>' + '<span class="unit-span">'+ unitStr + '</span>'
				+ '<span class="content-span"><i class="i i-tongji1"></i>'+ busInfoObj.fincRate+ '</span><span class="unit-span">%</span>'
				+ '</p>' 
				+ '</div>';
	}
	$("#busInfo .cont-row").html(htmlStr);
};
	

// 切换业务
function switchBus(appId) {
	// 获取业务以及押品信息，替换原页面相应模块中的内容
	$.ajax({
		type : "post",
		dataType : 'json',
		url : webPath+"/mfBusApply/getSwitchBusInfoAjax",
		data : {appId : appId},
		async : false,
		success : function(data) {
			
			if (data.flag == "success") {
				setBusInfo(data);
				setPleInfo(data);
			} else {
				alert(data.msg, 0);
			}

		},
		error : function() {
			alert(top.getMessage("ERROR_REQUEST_URL", ""), 0);
		}
	});
};

// 多业务大于3条时，弹层列表页面
function getMultiBusList() {
	top.openBigForm(webPath+"/mfBusApply/getMultiBusList?cusNo=" + cusNo,"多笔业务", function() {});
};
//cusNoTmp临时变量，cusNo进到oneCallback就没有了
var cusNoTmp=cusNo;
//单字段编辑的保存回调方法。
function oneCallback(data,disVal) {
	var name = data[0].name;
	var value = data[0].value;
	if("wayClass"==name || "assetSum"==name || "bussIncome"==name || "empCnt"==name){
		//如果修改了行业分类，资产总额 营业收入，从业人员，需要重写企业规模 getByIdAjax
		$.ajax({
			type : "post",
			dataType : 'json',
			url : webPath+"/mfCusCorpBaseInfo/getByIdAjax",
			data : {cusNo: cusNoTmp},
			async : false,
			success : function(data) {
				if(data.mfCusCorpBaseInfo!=null&&data.mfCusCorpBaseInfo!=''){
					var proj=data.mfCusCorpBaseInfo.projSize;
					var proJectName="";
					if("1"==proj){
						proJectName="大型企业";
					}
					if("2"==proj){
						proJectName="中型企业";
					}
					if("3"==proj){
						proJectName="小型企业";
					}
					if("4"==proj){
						proJectName="微型企业";
					}
					$(".projSize").html(proJectName);
				}
				
				
				

			},
			error : function() {
				alert(top.getMessage("ERROR_REQUEST_URL", ""), 0);
			}
		});
	} else if(name=="contactsName"||name=="commAddress"||name=="contactsTel"||name=="postalCode"){
		$("span[id="+name+"]").html(value);
	}else if (name=="accountNo"){
		getBankByCardNumbe(name,data[1].value);
	} else if(name=="useType"||name=="highCusType"||name=="education"||name=="highCusType"||name=="education"||name=="sellArea"||name=="sellWayclass"||name=="isLegal"||"relative"==name||name=="saleArea"||name=="goodsRule"||name=="changeType"||name=="corpNature"||name=="registeredType"){
		BASE.oneRefreshTable(name,disVal);
	}else if(name=="careaCity"){
		var careaProvice = $("input[name=careaProvice]").val();	
		//var cusNo = $("input[name=cusNo]").val();
		$.ajax({		
			url : webPath+"/mfCusCorpBaseInfo/updateCareaAjax",
			data : {
				ajaxData : careaProvice ,cusNo:cusNoTmp
			},
			type : 'post',
			dataType : 'json',
			async:false,
			success : function(data) {					
			},
			error : function() {
			}
		});		
	} else if (name == "marginAmt") {
		$.ajax({
			url : webPath+"/mfCusAssureCompany/getUsableAmtAjax",
			data : {
				assureCompanyId : data[1].value
			},
			type : 'post',
			dataType : 'json',
			async : false,
			success : function(data) {
				$(".usableAmt").text(data.usableAmt);
			},
			error : function() {
			}
		});
	} else if (name == "baseConfigId" || name == "configName") {//分润配置名称
		var cusNo = $("input[name=cusNo]").val();
		$.ajax({
			url : webPath + "/mfShareProfitConfig/getByCusNoAjax?cusNo=" + cusNo,
			type : 'post',
			dataType : 'json',
			async : false,
			success : function(data) {
				if(data.flag = "success"){
					$("#MfShareProfitConfigActionAjax_updateByOne_action").html(data.htmlStr);
					$("#MfTrenchShareProfitRateAction").html(data.shareProfitRateHtmlStr);
					$("#MfTrenchShareProfitRateAction #tablist").show();
				}else{
					alert("刷新分润配置信息失败",0);
				}
			},
			error : function() {
				alert("刷新分润配置信息失败",0);
			}
		});
	}  else {
		BASE.oneRefreshTable(name,value);
	}
	
}
function getBankByCardNumbe(name,bankId) {
		$.ajax({
			url:webPath+"/mfCusBankAccManage/getByIdForOneAjax",
			data:{id:bankId},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					//$("#MfCusBankAccManageAction").html(data.htmlStr);
					if(name=="accountNo"){
						refreshTable(name,data.mfCusBankAccManage);
						
					}else if (name=="useType"){
						var index=	$("th[name=useType]").index();
						$(".listshow-tr").prev().find("td").eq(index).html(data.userType);
					}
					$("input[name=bank]").val(data.mfCusBankAccManage.bank);
					$("input[name=bankNumbei]").val(data.mfCusBankAccManage.bankNumbei);
					$("input[name=bank]").parent().parent().find("div[class=fieldShow]").html(data.mfCusBankAccManage.bank);
					$("input[name=bankNumbei]").parent().parent().find("div[class=fieldShow]").html(data.mfCusBankAccManage.bankNumbei);
					
				}else{
					$("input[name=bank]").val("");
					$("input[name=bankNumbei]").val("");
				}	
			},error:function(){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
};
//
function refreshTable(name,data){
	var index;
	if(name=="accountNo"){
		 index = $("th[name=accountNo]").index();
		$(".listshow-tr").prev().find("td").eq(index).find('a').html(data.accountNo);
		BASE.oneRefreshTable("bank",data.bank);
		dealBankNo();
	}else if (name=="useType"){
		var useType = 	$("select[name=useType]").parent().parent().find("div[class=fieldShow]").html();
		BASE.oneRefreshTable(name,useType);
	}
}
//处理银行卡号
function dealBankNo(){
	$(".bankNo").each(function(i, item) {
		var itemBankNo = $(item).text();
		var itemBankNoHtml = $(item).html();
		if(/\S{5}/.test(itemBankNo)){
			 $(item).html(itemBankNoHtml.replace(itemBankNo,itemBankNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ")));
		}
	});
};

//联网核查跳转到增值服务页面
function addService(){
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
					/*var headImg=customer.headImg;
					var ifUploadHead = customer.ifUploadHead;
					var data = headImg;
					if(ifUploadHead!="1"){
						data = "themes/factor/images/"+headImg;
					}
					data = encodeURIComponent(encodeURIComponent(data));
					var ipandport=window.document.location.href;

					var port=window.location.href;
					var src=webPath+"/UploadFileAction_viewUploadImageDetail.action?srcPath="+data+"&fileName=user2.jpg";*/
					url +="?show=0&showType="+data.showType+"&idCardName="+encodeURI(encodeURI(customer.cusName))+"&address="+encodeURI(encodeURI(customer.commAddress))+"&idCardNum="+customer.idNum+"&phoneNo="+customer.cusTel+"&bankCardNum=123456789customer&blacklistType=person&searchKey="+encodeURI(encodeURI(customer.cusName))+"&dataType=all&reportType=GR&cusType="+customer.cusType+""+"&headImgShowSrc="+encodeURI(encodeURI(headImgShowSrc));
				}else{
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

//风险拦截
function cusRisk(){
	if(!(dialog.get('riskInfoDialog') == null)){
		dialog.get('riskInfoDialog').close();
	}
	top.window.openBigForm(webPath+'/riskForApp/preventList?relNo='+cusNo,'风险拦截信息',function(){});
};

//打开客户基本认证报告页面
function openCustomerCerReport(){
	if(comReportFlag =="3"){
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
	}else{
		top.updateFlag = false;
		top.window.openBigForm(webPath+'/mfPhoneBook/openCustomerCerReport?cusNo='+cusNo, '认证报告',function(){},"75","90");
	}
}
//查看三方记录信息
function getRiskReport(){
		top.window.openBigForm(webPath+"/mfThirdServiceRecord/riskReport?appId=" + appId +"&cusNo="+cusNo+"&query=query",'风控查询', function(){});
	};
	
function getRelation(){
	
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
         },
         error:function(){
        	 alert("认证报告生成失败",0);
         }
    });
	return resultMap;
};
//追加额度
function addTrenchCreditAmt(){
	top.addAmtFlag = false;
	top.window.openBigForm(webPath+'/mfTrenchCreditAmtModifyHis/input?trenchUid='+cusNo, '追加额度',function(){
		if(top.addAmtFlag){
			//刷新渠道基本信
			$.ajax({
				 type : "post",  
		         url : webPath+"/mfBusTrench/getTrenchBaseHtmlAjax?trenchId="+cusNo, 
		         async : false,
		         success : function(data){ 
		        	 if(data.flag=="success"){
		        		 $("#MfBusTrenchAction").html(data.htmlStr);
		        	 }
		         }  
		    });
		}
	});
};
//追加资金机构额度
function addAgenciesCreditAmt(){
	top.addAmtFlag = false;
	top.window.openBigForm(webPath+'/mfTrenchCreditAmtModifyHis/inputAgencies?trenchUid='+cusNo, '追加额度',function(){
		if(top.addAmtFlag){
			//刷新渠道基本信
			$.ajax({
				type : "post",  
				url : webPath+"/mfBusAgencies/getAgenciesBaseHtmlAjax?agenciesUid="+cusNo, 
				async : false,
				success : function(data){ 
					if(data.flag=="success"){
						$("#MfBusAgenciesAction").html(data.htmlStr);
						$("#addCreditHisRowDiv").show(); 
						$("#addCreditHisList").html(data.addCreditHisListHtml);
						$("#addCreditHisList .ls_list").show();
					}
				}  
			});
		}
	});
};

// 分润明细列表
function getCommissionChangeRecordDetailListPage(url){
	top.window.openBigForm(webPath + url + "&cusNo=" + cusNo, '分润明细列表',function(){
		
	});
}

// 分润配置选择回调
function shareProfitConfigCallBack(mfShareProfitConfig){
	$("[name='configName']").val(mfShareProfitConfig.configName);
	$("[name='baseConfigId']").val(mfShareProfitConfig.id);
}

//重写dblUpdateVal，支持单字段编辑同时更新相关字段
function dblUpdateVal(key,data){
	switch(key){
		case "supportProductsName"://资金机构支持产品
			data["supportProducts"] = $("input[name=supportProducts]").val();
			break;
		case "configName"://分润配置名称
			data["baseConfigId"] = $("input[name=baseConfigId]").val();
			break;
        case "roleName"://角色名称
            data["roleNo"] = $("input[name=roleNo]").val();
            break;
		default:
			break;
	}
}
// 选择角色
function selectRole(){
    var roleNo = $("[name='roleNo']").val();
    selectRoleDialog(function(sysRole){
        $("[name='roleNo']").val(sysRole.roleNo);
        $("[name='roleName']").val(sysRole.roleName);
    },roleNo)
}


function cusElectronicEsignBtn(){
    top.window.openBigForm(webPath + "/mfTemplateEsignerList/getListPage?cusNo=" + cusNo, '电子签约',function(){

    });

//获取用户信息变更记录
function openCusInfoChangeRecord(){
	top.window.openBigForm(webPath+"/mfCusInfoChange/getListPage?cusNo="+cusNo,"客户信息变更记录",function(){
	});
};
}