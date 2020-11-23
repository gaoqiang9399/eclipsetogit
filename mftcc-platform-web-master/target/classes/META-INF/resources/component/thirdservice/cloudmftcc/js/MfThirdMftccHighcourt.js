;
var MfThirdMftccHighcourt=function(window, $){
	//客户详情页面初始化法执查询按钮
	var _init=function(){
		_buttonChange();
	};
	//法制按钮颜色变化
	var _buttonChange=function(){
	$.ajax({
 		url:webPath+"/mfThirdMftccHighcourt/getLawEnforCountByCusNoAjax",
 		dataType:'json',
 		type:'post',
 		data:{cusNo:cusNo},
 		success : function(data){
 			if (data.flag == "success") {//有存在法执查询成功的历史，按钮两色
 				$("#lawEnforcementQuery").attr("class","btn btn-view btn-dodgerblue");
			}
 		}
 	});
	};
	//法执结果页面初始化
	var _initLawContent=function(){
		_showLawHisListByPage();
		var popupSelection = $('input[name=busName]').popupSelection({
			inline: true, //下拉模式
			multiple: false, //单选
			searchOn:true,
			items:ajaxData.lawCusArr,
			changeCallback : function (obj, elem) {
				var cusNoStr=obj.val();
				var cusNoTemp=cusNoStr.split("@")[0];
				var relType=cusNoStr.split("@")[1];
				if(relType=="1"){//借款客户
					cusNo=cusNoTemp;
					$("input[name=relId]").val(cusNo);
				}else if(relType=="2"){//担保人
					$("input[name=relId]").val(cusNoTemp);
				}
				$("input[name=busType]").val(relType);
				$.ajax({
					url:webPath+"/mfThirdMftccHighcourt/getLawQueryCusInfoAjax",
					dataType:'json',
					type:'post',
					data:{cusNo:cusNoTemp,relType:relType},
					success : function(data){
						if (data.flag == "success") {
							if(relType=="3"){//配偶
								$("input[name=relId]").val(data.familyId);
							}
							$("input[name=busNumber]").val(data.busNumber);
							$("input[name=relTypeName]").val(data.IdTypeName);
						} else {
							window.top.alert(data.msg, 0);
						}
					}
				});
			}
		});
		//如果备选项只有1个，则默认选中
		if(ajaxData.lawCusArr&&ajaxData.lawCusArr.length>=1){
            popupSelection.popupSelection('selectedById',ajaxData.lawCusArr[0].id);
		}
	};
	
	//分页展示法执查询历史
	var _showLawHisListByPage=function(){
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url : webPath+"/mfThirdMftccHighcourt/findByPageAjax", //列表数据查询的url
			tableId : "tablelawenforcement", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize:12, //加载默认行数(不填为系统默认行数)
			topHeight:235,
			data:{
				appId:appId,
				cusNo:cusNo
			}
		});
		$(".footer_loader").show();
	};
	//根据查询编号获得法执结果并将法执结果展示页面中
	var _lawEnforQuery=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			window.top.alert(top.getMessage("CONFIRM_QUERY_COST",{"cost":lawEnfo}),2,function(){
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url:webPath+"/mfThirdMftccHighcourt/lawEnforQueryAjax",
				dataType:'json',
				type:'post',
				data:{ajaxData:dataParam,isRequest:"1",thirdFlag:thirdFlag},
				success : function(data){
					if (data.flag == "success") {
						console.log(data.creditContent);
						
						top.window.openBigForm(webPath+"/mfThirdServiceRecord/getQueryHtmlById?id="+data.id,"法执情况",function(){
                            updateTableData();
						});
					} else {
						window.top.alert(data.msg, 0);
					}
				}
			});
		});
		}
	};
	
	//查看详情
	var _tableHeadConfig=function(obj,url){
		 top.openBigForm(url,'法执情况',function(){
			 
		 });
	};
	
	//业务详情中打开客户最新的法执查询结果页面
	var _openLawEorcement=function(){
        top.window.openBigForm(webPath+"/mfThirdMftccHighcourt/getResultById?cusNo="+cusNo+"&appId="+appId+"&busId="+cusNo+"&thirdFlag=lawEnforcement",'法执情况',function(){});
	};
	//刷新当前客户视角
	/*	var _myClose=function(){
			myclose_click();//关闭弹窗
			var url= webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&appId="+appId+"&busEntrance=apply";
			window.location.href=url;

		};*/
	//根据查询编号获得法执结果并将法执结果展示页面中
	var _getCreditContent=function(queryId){
		$.ajax({
	 		url:webPath+"/mfThirdMftccHighcourt/getCreditContentAjax",
	 		dataType:'json',
	 		type:'post',
	 		data:{queryId:queryId},
	 		success : function(data){
	 			if (data.flag == "success") {
	 				$("#creditContent").html(data.creditContent);
	 				$("#explainheader").parent().attr("style","width:720px;background-color:#ffffff;margin-right:auto;margin-left:auto;");
	 				$("a").attr("onclick","");
	 				$("#creditContent").show();
	 				$("#cusAppLyInfo").hide();
	 				$("#lawEnforQueryForm").hide();
	 				$("#againButton").show();
	 				$("#closeButton").hide();
	 				$("#backButton").show();
	 				$(".footer_loader").hide();
	 				$(".table-float-head").hide();
				} else {
					window.top.alert(data.msg, 0);
				}
	 		}
	 	});
	};
	
	//展示法执结果
	var _showCreditContent=function(obj,url){
		var queryId=url.split("?")[1].split("=")[1];
		//_getCreditContent(queryId);
		top.window.openBigForm(webPath+"/mfThirdMftccHighcourt/showCreditResultContentByQueryId?queryId="+queryId,"法执结果",function(){
		});
	};
	
	//展示法执查询页面
	var _showLawEnforQueryForm=function(){
		$("#creditContent").hide();
		$("#cusAppLyInfo").hide();
		$("#againButton").hide();
		$("#bussButton").show();
		$("#showLawEnforQueryButton").show();//
		$("#lawEnforQueryForm").show();//
		$(".footer_loader").hide();
		$(".table-float-head").hide();
	};
	
	//法执结果页面返回到客户业务汇总信息
	var _back=function(){
		//刷新查询历史列表数据
		$("#creditContent").hide();
		$("#cusAppLyInfo").show();
		$("#againButton").hide();
		$("#bussButton").hide();
		$("#lawEnforQueryForm").hide();
		$(".table-float-head").show();
		_showLawHisListByPage();
		$(".upload-li").each(function(i,obj){
			var $this=$(obj);
			var fileName=$this.find(".doc-title").attr("title");
			$this.find(".doc-title").html(fileName);
		});
	};
	
	//打开客户最新的法执查询结果页面。客户详情中
	var _openLawEorcementForCusDatail=function(){
		if(top.creditQueryFlag=="0"){
			// return;
		}
		top.window.openBigForm(webPath+"/mfThirdMftccHighcourt/getCreditHisByCusNo?cusNo="+cusNo+"&appId=&busEntrance="+busEntrance+"&creditQueryFlag=1","法执结果",function(){
		});
	};
	
	//重新进行法执查询
	var _lawQueryAgain=function(){
		alert(top.getMessage("CONFIRM_QUERY"), 2, function() {
		//$("#cusAppLyInfo").show();
		$("#lawEnforQueryForm").show();
		$("#creditContent").hide();
		$("#againButton").hide();
		});
	};
	
	//刷新法执查询历史列表
	var _refreshCreditQueryHis=function(){
		$.ajax({
	 		url:webPath+"/mfCreditQueryRecordInfo/getCreditQueryHisHtmlAjax",
	 		dataType:'json',
	 		type:'post',
	 		data:{appId:appId},
	 		success : function(data){
	 			if (data.flag == "success") {
	 				var htmlStr=data.htmlStr;
	 				$("#creditQueryHisContentList").html(htmlStr);
				} else {
					
				}
	 		}
	 	});
	};
	return{
		init:_init,
		lawEnforQuery:_lawEnforQuery,
		openLawEorcement:_openLawEorcement,
		getCreditContent:_getCreditContent,
		openLawEorcementForCusDatail:_openLawEorcementForCusDatail,
		lawQueryAgain:_lawQueryAgain,
		initLawContent:_initLawContent,
		showCreditContent:_showCreditContent,
		back:_back,
		showLawEnforQueryForm:_showLawEnforQueryForm,
		tableHeadConfig:_tableHeadConfig,
		showLawHisListByPage:_showLawHisListByPage,
		buttonChange:_buttonChange,
	};
	function getLawEnforcement() {// 当客户信息是列表，继续增加一条记录时
	
		$.ajax({
	 		url:webPath+"/mfThirdMftccHighcourt/getLawEnforcement",
	 		dataType:'json',
	 		type:'post',
	 		data:{cusNo:cusNo},
	 		success : function(data){
	 			MfThirdMftccHighcourt.buttonChange();
	 			MfCusDyForm.initCusIntegrity(cusNo);
	 			var list = data.tableList;
	 			if(list!=null){
	 			if($("#MfCusExecNoticeAction").length>0){
	 				$("[name='MfCusExecNoticeAction']").remove();
	 			}
	 			if($("#MfCusJudgmentAction").length>0){
	 				$("[name='MfCusJudgmentAction']").remove();
	 			}
	 			if($("#MfCusCountNoticeAction").length>0){
	 				$("[name='MfCusCountNoticeAction']").remove();
	 			}
	 			if($("#MfCusDishonestInfoAction").length>0){
	 				$("[name='MfCusDishonestInfoAction']").remove();
	 			}
	 				/**客户动态表单处理**/
	 				MfCusDyForm.init(list);
	 		
	 			}
	 			
	 		}
	 	});
	};
}(window, jQuery);