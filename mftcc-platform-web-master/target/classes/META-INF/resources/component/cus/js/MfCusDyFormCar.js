1;
var MfCusDyFormCar =  function(window, $) {
	var _init = function(mfCollateralTables){
		var tmpDelCnt=0;//移除的个数
		$.each(mfCollateralTables, function(i, mfCollateralTable) {
			// shouType:1是form2是table  tableDes：中文描述    action:对应路径的action  html：后台传过来的html字符串   dataFullFlag：是否填完信息
			//isMust:是否必填 tableName:对应表名  delFlag：表单标志是否显示
			_setBlock(mfCollateralTable.showType, mfCollateralTable.tableDes, mfCollateralTable.action,mfCollateralTable.htmlStr, mfCollateralTable.dataFullFlag, mfCollateralTable.isMust,mfCollateralTable.tableName, i % 4 + 1, mfCollateralTable.delFlag,mfCollateralTable.sort);
			dblclickflag();//单子段编辑事件
			if(mfCollateralTable.delFlag=="1"){
				tmpDelCnt++;
			}
		});
		if(busSubmitCnt=="0"){
			//追加“添加”操作模块
			var cusAdd = '<div class="rotate-div" style="display:none;"><div class="rotate-obj rotate-borderColor2" id="cus-add"><div class="rotate-des rotate-color2"><i class="i i-jia1 pull-left" style="margin-left:162px;margin-top:25px;"></i></div></div></div>';
			$("#rotate-body").append(cusAdd);
			if(tmpDelCnt>0){
				$("#cus-add").parent().show();
			}else{
				$("#cus-add").parent().hide();
			}
			$("#cus-add").click(function() {
				_updateCusFormStas();
			});
		}
		if(typeof(formEditFlag)!="undefined" && formEditFlag=="query"){//业务视角/客户视角登记的表单在editFlag=query时，客户表单块不允许操作（新增、删除）
			$(".formAdd-btn").hide();
			//删除
			$(".delBtn").html("删除");
		}
		
	};
	
	//shouType:1是form2是table  tableDes：中文描述（头部标题）    action：custable的action属性  html：后台传过来的html字符串   dataFullFlag：是否填完信息
	//isMust:是否必填 tableName:对应表名  color:  delFlag：表单标志是否显示
	var _setBlock = function(showType, title, name, htmlStr, dataFullFlag, isMust,tableName, color, delFlag,sort){
		//客户编辑权限
		var pmsBizNo ='cus-edit-'+name;//pms_no 规则:"cus-edit-"+action
		var checkPmsBizFlag=BussNodePmsBiz.checkPmsBiz(pmsBizNo);
		var rotateColor = "rotate-color";
		var rotateBorderColor = "rotate-borderColor";
		var rotateTubiaoBac = "rotate-tubiaoBac";
		var clearDiv = '<div style="clear:both;"></div>';
		var addBtnHtml = "";
		var iconClass = "info-box-icon i ";
		var backColor= "";
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
		//没有已提交的业务的情况下，未完善的客户模块才显示
		if(busSubmitCnt=="0"){
			//判断是否有值
			var dataFlag = dataFullFlagOrNot(dataFullFlag,delFlag,htmlStr,name,showType,title,tableName,isMust,rotateBorderColor,rotateColor,rotateTubiaoBac,sort,iconClass,backColor,checkPmsBizFlag);
			if(dataFlag){
				return false;
			}
			//列表展示的话设置添加按钮
			//个人负债只能录入一条但是以列表形式展示不加新增按钮
			if(name!="MfCusPersonLiabilitiesAction"){
				addBtnHtml = addbutton(showType,addBtnHtml,checkPmsBizFlag);
			}
			if(name=="PledgeBaseInfoAction"){
				"<button class='btn btn-link formAdd-btn' onclick='MfCusDyFormCar.addCusFormInfo(this,\""+ showType+ "\");' title='新增'><i class='i i-jia3'></i></button>";
			}
			//有值的情况下显示出来
			showInformation(showType,name,title,htmlStr,clearDiv,addBtnHtml,sort, tableName,checkPmsBizFlag);
			$("table td[mytitle]:contains('...')").initMytitle();
		}else{
			if(typeof(dataValidate) !== 'undefined'){//业务处校验客户表单相关
				if(name!="MfCusPersonLiabilitiesAction"){
					addBtnHtml = addbutton(showType,addBtnHtml,checkPmsBizFlag);
				}
				if(name=="PledgeBaseInfoAction"){
					addBtnHtml = "<button class='btn btn-link formAdd-btn' onclick='MfCusDyFormCar.selectCollateralData(MfCusDyFormCar.setCollateralData);'><i class='i i-jia3'></i></button>";
				}
				if(showType == '1' && dataFullFlag=='1' && name=="MfCarCheckFormAction"){//如果是表单  并且未完善  同样有新增按钮
					//addBtnHtml = "<button class='btn btn-link formAdd-btn' onclick='MfCusDyFormCar.selectCollateralData(MfCusDyFormCar.setCollateralData);'><i class='i i-jia3'></i></button>";
					addBtnHtml ="";
				} 
				showInformation(showType,name,title,htmlStr,clearDiv,addBtnHtml,sort, tableName,checkPmsBizFlag);
			}else{
				if(dataFullFlag=="1"){
					//列表展示的话设置添加按钮
					//个人负债只能录入一条但是以列表形式展示不加新增按钮
					if(name!="MfCusPersonLiabilitiesAction"){
						addBtnHtml = addbutton(showType,addBtnHtml,checkPmsBizFlag);
					}
					//有值的情况下显示出来
					showInformation(showType,name,title,htmlStr,clearDiv,addBtnHtml,sort, tableName,checkPmsBizFlag);
					$("table td[mytitle]:contains('...')").initMytitle();
				}
			}
		}
	};

	//判断是否有数据
	var dataFullFlagOrNot = function(dataFullFlag,delFlag,htmlStr,name,showType,title,tableName,isMust,rotateBorderColor,rotateColor,rotateTubiaoBac,sort,iconClass,backColor,checkPmsBizFlag){
		var delBtnHtml = "";
		var isMustHtml = "";
		var dataFlag = false;
		if (dataFullFlag == "0") {
			if (checkPmsBizFlag && delFlag == "0") {
					if (isMust == "1") {
						isMustHtml = "<span class='i i-sanjiao rotate-sanjiao color_red'></span><span class='rotate-bitian'>必填</span>";
					} else {
						delBtnHtml = "<button class='rotate-del i i-lajitong' title='删除' onclick='MfCusDyFormCar.deleteRotate(this);'></button>";
					}
					htmlStr = "";
					htmlStr = "<div class='rotate-div' name='"+ name+ "' data-showtype='"+ showType+ "' data-name='"+ name
							+ "' data-title='"+ title+ "' data-tablename='"+ tableName+ "' data-sort='"+ sort+ "' data-ismust='"+ isMust+ "'>"
							+ "<div class='rotate-obj "+ rotateBorderColor+ "'>"
								+ isMustHtml
								+ "<div class='rotate-des "+ rotateColor+ "'><div class='iconCicle' style='background-color:"+ backColor+ ";'>"
									+"<li class='iconStyle "+ iconClass+ "'></li></div><div class='rotate-formName pull-left'>登记"+ title+ "</div>" 
								+"</div>";
					htmlStr+="<div class='rotate-opre' >"
					+ "<button class='rotate-add i i-jia2' onclick='MfCusDyFormCar.addCusFormByRotate(this);'></button>"
					+ delBtnHtml 
					+ "</div>"; 
					htmlStr +="</div>"
							+ "</div>";
					$("#rotate-body").append(htmlStr);
					htmlStr = "";
			}
			dataFlag = true;
		}
		return dataFlag;
	};
	
	//列表展示的话设置添加按钮
	var addbutton = function (showType,addBtnHtml,checkPmsBizFlag){
		if(pledgeNo==""){
			addBtnHtml = "<button class='btn btn-link formAdd-btn' disabled='disabled' onclick='MfCusDyFormCar.addCusFormInfo(this,\""+ showType+ "\");' title='新增'><i class='i i-jia3'></i></button>";
		}else{
			addBtnHtml = "<button class='btn btn-link formAdd-btn' onclick='MfCusDyFormCar.addCusFormInfo(this,\""+ showType+ "\");' title='新增'><i class='i i-jia3'></i></button>";
		}
		return addBtnHtml;
	};
	//有值的模块显示出来
	var showInformation = function(showType,name,title,htmlStr,clearDiv,addBtnHtml,sort, tableName,checkPmsBizFlag){
		//var addBtnHtml1=addbutton(showType,addBtnHtml,checkPmsBizFlag);
		//默认添加信息块的位置 
		var _name = name;
		if(name!=null&&name!=""){
			_name = "/"+name.substring(0,1).toLowerCase()+name.substring(1);
			_name =_name.replace("Action","");
		}
		/**
		 * 系统添加了映射地址，拼接的URL地址需要添加webPath，如果不添加拼接的URL地址不正确导致方法无法访问（404） 2018年5月4日17:49:11
		 */
		_name = webPath +"/"+_name;
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
		if(name == "MfCarCheckFormAction"){
			showType = "1";
		}
		if (showType == "1") {
			var collapseButtonHtml = "<button  class=' btn btn-link pull-right formAdd-btn' data-toggle='collapse' data-target='#"
					+ name+ "'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>"
			htmlStrThis = htmlStrThis
					+ "<div class='dynamic-block' title='"+ title+ "' name='"+ name+ "' data-sort='"+ sort+ "' data-tablename='"+ tableName+ "'>"
					+ "<div class='form-table'>"
						+ "<div class='title' >" 
							+ "<span class='formName'><i class='i i-xing blockDian'></i>"+ title + "</span>"
							+ addBtnHtml+ collapseButtonHtml 
						+ "</div>"
						+ "<div class='content collapse in' id='" + name + "'>"
							+ "<form action='" + _name + "/updateAjaxByOne' id='"+ name + "Ajax_updateByOne_action'>"
								+ htmlStr 
							+ "</form>"
						+ "</div>" 
					+ "</div>" 
					+ "</div>";
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
						+ name+ "'><i class='i i-close-up'></i><i class='i i-open-down'></i></button>"
				htmlStrThis = htmlStrThis
						+ "<div class='dynamic-block' title='"+ title+ "' name='"+ name+ "' data-sort='"+ sort+ "' data-tablename='"+ tableName+ "'>"
						+ "<div class='list-table'>"
						+ "<div class='title' >" 
						+"<span class='formName'><i class='i i-xing blockDian'></i>"+ title + "</span>"
						+ addBtnHtml
						+ collapseButtonHtml2 
						+ "</div>"
						+ "<div class='content collapse in' id='" + name + "' >"
						+ htmlStr 
						+ "</div>" 
						+ "</div>" 
						+ "</div>";
			}
			$blockAdd.before(htmlStrThis);
			$blockAdd.before(clearDiv);
		}
	};
	
	
	var _addCusFormByRotate = function (obj) {
		var $rotateDiv = $(obj).parents(".rotate-div");
		var title = $rotateDiv.data("title");
		var action = $rotateDiv.data("name");
		var tableName = $rotateDiv.data("tablename");
		top.action = action;
		top.title = title;
		top.isMust = $rotateDiv.data("ismust");
		top.showType = $rotateDiv.data("showtype");
		top.sort = $rotateDiv.data("sort");
		//处理action为controller;
		action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
		action =action.replace("Action","");
		var inputUrl = action + "/input?cusNo=" + cusNo+"&relNo="+cusNo;
		if(pageView=="busView"){
			inputUrl = action + "/inputBiz?cusNo=" + cusNo+"&relNo="+appId+"&kindNo="+kindNo;
		}
		top.addFlag = false;
		top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
		top.htmlString = null;
		top.baseInfo = null;
		top.tableName = tableName;
		top.cusSubTypeName=$(".type-name-div").text();
		top.openBigForm(inputUrl, title, addPledgeFormInfoCall);
	};

	var _deleteRotate = function(obj) {
		var $rotateDiv = $(obj).parents(".rotate-div");
		$.ajax({
			url : webPath+"/mfCusTable/updateDelFlagAjax?cusNo=" + cusNo+"&tableName="+$rotateDiv.data("tablename") + "&delFlag=1",
			success : function(data) {
				$("#cus-add").parent().show();
				if (data.flag == "success") {
					$rotateDiv.remove();
				} else {
					alert(top.getMessage("FAILED_DELETE"), 0);
				}
			},
			error : function() {
				alert(top.getMessage("FAILED_DELETE"), 0);
			}
		});

	};

	var _addCusFormInfo = function (obj, showType) {// 当客户信息是列表，继续增加一条记录时
		var $dynamicBlock = $(obj).parents(".dynamic-block");
		var title = $dynamicBlock.attr("title");
		var action = $dynamicBlock.attr("name");
		top.action = action;
		//处理action为controller;
		action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
		action =action.replace("Action","");
		var inputUrl = action + "/inputCar?cusNo=" + cusNo+"&relNo="+cusNo+"&collateralNo="+pledgeNo+"&appId="+appId+"&checkType="+"1";
		if(pageView=="busView"){
			inputUrl = action + "/inputBiz?cusNo=" + cusNo+"&relNo="+appId+"&kindNo="+kindNo;
		}
		top.showType = showType;
		top.title = title;
		top.addFlag = false;
		top.htmlStrFlag = false;// 标识是否将客户表单信息的html字符串放在top下
		top.htmlString = null;
		top.baseInfo = null;
		top.openBigForm(inputUrl, title, addPledgeFormInfoCall);
	};

	//初始化客户信息完整度
	var _initCusIntegrity = function(cusInfIntegrity){
		// 根据客户号获取资料完整度,不再取参数中的值(原因:之前调用的地方过多,且值显示的不正确，因此再公共方法中处理) 
		cusInfIntegrity = _getInitCusIntegrity(cusNo);
		if(isNaN(cusInfIntegrity)){
			cusInfIntegrity = 0;
		}
		var b = (cusInfIntegrity * 100).toFixed(0) + "%";
		$("#integrity-span").text("完整度"+b);	
		var level;
		if (cusInfIntegrity == '' || cusInfIntegrity == '0.00') {
			level = "L1";
		} else if (cusInfIntegrity < '0.50') {
			level = "L2";
		} else if (cusInfIntegrity >= '0.50'&&cusInfIntegrity < '0.80') {
			level = "L3";
		} else {
			level = "L4";
		}
		$(".cus-integrity").attr("level", level);
	};
	//选择客户的押品
	var _selectCollateralData=function(_setCollateralData){
		selectCollateralDataDialog(_setCollateralData,"",appId,pledgeMethod);
	};
	//选择客户押品回调设置押品相关字段。
	var _setCollateralData=function(data){
		pledgeNo = data.pledgeNo;
		jQuery.ajax({
			url :webPath+"/mfBusCollateralRel/relationCollateral",
			data : {pledgeNo:pledgeNo,pledgeMethod:pledgeMethod,appId:appId,cusNo:cusNo,extensionApplyId:'',entrFlag:'businessNoTask',skipFlag:'0',isQuote:'1'},
			type : "POST",
			dataType : "json",
			beforeSend : function() {
			},
			success : function(data) {
                LoadingAnimate.stop();
                top.addFlag = true;
                top.htmlStrFlag = true;
                _eachHtmlList(data.collateralTableList);
			},
			error : function(data) {
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "), 0);
			}
		});
	};
	var _eachHtmlList = function(data){
        jQuery.each(data, function(i,item){
            action = item.action;
            htmlStr = item.htmlStr;
			if(action == "PledgeBaseInfoAction"){
                $("#PledgeBaseInfoAction").find("table").remove();
                $("#PledgeBaseInfoAction").find(".hidden-content").remove();
                $("#PledgeBaseInfoAction").find("form").append(htmlStr);
                $(".formAdd-btn").removeAttr("disabled");
			}else if(action == "MfCarCheckFormAction" && htmlStr != ""){
                $("#MfCarCheckFormAction").find("table").remove();
                $("#MfCarCheckFormAction").find("form").append(htmlStr);
			}else if(action == "MfBusGpsRegAction" && htmlStr != ""){
                $("#MfBusGpsRegAction").find("table").remove();
                $("#MfBusGpsRegAction").html(htmlStr);
            }else if(action == "InsInfoAction" && htmlStr != ""){
                $("#InsInfoAction").find("table").remove();
                $("#InsInfoAction").html(htmlStr);
            }
		})
	}
	
	//根据客户号获取资料完整度
	var _getInitCusIntegrity = function(integrityCusNo){
		var integrityResult = "";
		$.ajax({
			url : webPath+"/mfCusCustomer/getCusByIdAjax",
			type : "POST",
			data : {"cusNo":integrityCusNo},
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.flag == "success") {
					integrityResult = data.cusInfo.infIntegrity;
				}else{
					integrityResult ="0";
				}
			},error : function() {}
		});
		return integrityResult;
	};
	return {
		init : _init,
		setBlock : _setBlock,
		addCusFormByRotate : _addCusFormByRotate,
		deleteRotate : _deleteRotate,
		addCusFormInfo : _addCusFormInfo,
		initCusIntegrity : _initCusIntegrity,
		selectCollateralData : _selectCollateralData,
		setCollateralData:_setCollateralData,
		getInitCusIntegrity : _getInitCusIntegrity,
        eachHtmlList : _eachHtmlList
	};
}(window, jQuery);