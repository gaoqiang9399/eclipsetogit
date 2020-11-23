;
var showData = {
		index:{//评分卡表头列
			"indexName":"指标名称",
			"stdCore":"指标值"
			},
	};
var gardeCardLength = "";//页面上是否存在评分卡选择卡
var AppEval_InitiateApp = function(window,$){
	var _init = function(){
		var cusTypeClass = $("select[name=cusType]").parents("td").attr("class");
		gardeCardLength = $(".showprogress ul").find("li[name=dx]").length;
		//加载评级模型选择数据源
		_setGradeModelOption();
		//是否是查看 getFlag查看标志  true 为是
		if(getFlag===undefined){
			getFlag = false;
		}else if(getFlag=="true"){
			getFlag = true;
		}
		if(getFlag==true){//查看时将选择财报 不显示
			$(".content_show").find(".content_ul").data("entityData",dataMap.entityData);
			//个人
            var addForm = "appeval0001";
            var detailForm = "eval1001";
			if(cusBaseType=="2"){
                addForm = "appevalpers0001";
                detailForm = "evalpers1001";
			}
			if(useType == "2"){
                addForm = "appRiskCheckAdd";
                detailForm = "appRiskCheckDetail";
            }
			if(useType == "3"){
                addForm = "creditRiskCheckAdd";
                detailForm = "creditRiskCheckDetail";
            }
            _choseFininit(addForm);
            _evalAppFormInit(detailForm);
        }
		var bodyWidth = $("body").width();
		var liLength= $(".content_ul li").length;
		$(".content_ul").css({width:(liLength)*bodyWidth+"px"});
		for(var i=0;i<liLength;i++){
			if(i==liLength-1){//最后一个div的宽度影响ie9的显示的处理
				$(".content_ul li:eq("+i+")").css({width:bodyWidth-3+"px"});
				continue;
			}
			$(".content_ul li:eq("+i+")").css({width:bodyWidth+"px"});
		}
		_liClick()
		//加载滚动条
		//初始化页面的表头
		_initThead("",showData);
		$(window).resize(function() {
			var bodyWidth = $("body").width();
			var bodyheight = $("body").height();
			var liLength= $(".content_ul li").length;
			$(".content_ul").css({width:(liLength)*bodyWidth+"px"});
			for(var i=0;i<liLength;i++){
				if(i==liLength-1){//最后一个div的宽度影响ie9的显示的处理
					$(".content_ul li:eq("+i+")").css({width:bodyWidth-3+"px"});
					continue;
				}
				$(".content_ul li:eq("+i+")").css({width:bodyWidth+"px"});
			}
//			$(".content_ul li").css({width:bodyWidth+"px"});
		});
		
		//调整下拉事件
		$("select[name=restrictLevel]").attr("onfocus","this.defaultIndex=this.selectedIndex;");
		$("select[name=restrictLevel]").attr("onchange","this.selectedIndex=this.defaultIndex;");
		//发起评级阶段
		if(evalStage=="1"){
			//初始化查看数据
			initData(dataMap.listData);
			$(".showprogress ul").find("li[name=chosefin]").addClass("success");
			if(gardeCardLength >0){
				$(".showprogress ul").find("li[name=dx]").click();
			}else if(gardeCardLength == 0){
				$(".showprogress ul").find("li[name=evalapp]").click();
			}
			$.each(dataMap.gradeCardListData,function(i,obj) {
				if (dataMap["codeValue" + obj.gradeCardId]) {
					var str = "codeValue" + obj.gradeCardId;
					var dxList = dataMap[str].scoreList;
					for (i = 0; i < dxList.split("@").length - 1; i++) {
						var dxobj = dxList.split("@")[i];
						var name = dxobj.split(":")[0];
						var vuale = dxobj.split(":")[1];
						$(".content_ul li div[name=" + obj.gradeCardId + "]").find("input[type=radio][name=" + name + "]").each(function (index) {
							if (vuale == $(this).attr("value")) {
								$(this).prop("checked", true);
								$(this).attr("checked", "checked");
								return false;
							} else {//如果选中的不是第一个，去掉第一个默认选中状态
								//$(this).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
							}
						});
					}
				}
			});

		}else if(evalStage=="2"){//已保存评级评分卡
			//初始化查看数据
			initData(dataMap.listData);
			/*//如果没有定性和调整类型的评分卡，直接跳转综合评分
			if(!dataMap["dxData"]&&!dataMap["dxData"]){
				//$(".showprogress ul").find("li[name=evalapp]").click();
			}*/
			$.each(dataMap.gradeCardListData,function(i,obj){
				if(dataMap["codeValue"+obj.gradeCardId]){
					var str="codeValue"+obj.gradeCardId;
					var dxList = dataMap[str].scoreList;
					for(i=0;i<dxList.split("@").length-1;i++){
						var dxobj = dxList.split("@")[i];
						var name = dxobj.split(":")[0];
						var vuale = dxobj.split(":")[1];
						$(".content_ul li div[name="+obj.gradeCardId+"]").find("input[type=radio][name="+name+"]").each(function(index){
							if(vuale==$(this).attr("value")){
								$(this).prop("checked",true);
								$(this).attr("checked","checked");
								return false;
							}else{//如果选中的不是第一个，去掉第一个默认选中状态
								//$(this).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
							}
						});
					}
					$(".content_ul li div[name="+obj.gradeCardId+"]").find("input[type=radio]").attr("disabled","true");
					$(".content_ul li div[name="+obj.gradeCardId+"]").find("input[id^='property_']").attr("readOnly","true");
					$("#editEval").show();
					$("#addEval").hide();
				}
			});
			$(".showprogress ul").find("li[name=chosefin]").addClass("success");
			if(gardeCardLength >0){
				$(".showprogress ul").find("li[name=dx]").addClass("success");
				$("#editEval").show();
				$("#addEval").hide();
			}
			$(".showprogress ul").find("li[name=evalapp]").click();
		}
		//加载完成 显示数据
		$(".eval-content").css({"display":"block"});
		//隐藏财务、定量、定性右上角总分
		$(".content_ul li").find(".li_title .scoreShow").hide();
		//隐藏页面中所有thead
		//$("thead").hide();
		var check = $(".selected").attr("name");
		if(check == 'chosefin'){
			$("div[id=gradeCard] div[id=moreBar]").remove();
			$("div[id=evalapp] div[id=moreBar]").remove();
            //初始化页面时设置提交按钮不可用
            $('#evalappButton').find('input').attr("disabled","disabled");
		}
		if(check == 'dx'){
			$("div[id=evalapp] div[id=moreBar]").remove();
            //初始化页面时设置提交按钮不可用
            $('#evalappButton').find('input').attr("disabled","disabled");
			myCustomScrollbarForForm({
				obj:"#gradeCard",
				advanced : {
					updateOnContentResize : true
				}
			});
		}
		if(check == 'evalapp'){
			$("div[id=gradeCard] div[id=moreBar]").remove();
			myCustomScrollbarForForm({
				obj:"#evalapp",
				advanced : {
					updateOnContentResize : true
				}
			});
		}
		$("#evalappButton").css("position","fixed");

	};
	
	//处理客户类型问题,以后逗号分隔，可以传入多个客户类型
      var  _dealWithCusType  =	function (baseTypes){
		$.ajax({
			url:webPath + '/mfBusTrench/getCusTypeNotShowAjax',
			data:'baseTypes='+baseTypes,
			dataType:'json',
			async:false,
			type:'POST',
			success:function(data){
				var notShowCusTypes=data.cusTypeList;
				if(notShowCusTypes!=null){
					for(var i=0;i<notShowCusTypes.length;i++){
						var typeNo=notShowCusTypes[i].typeNo;
						$("[name=cusType]").find("option[value="+typeNo+"]").remove();
						$("li").removeClass('width');
					}
				}
			}
		});
	}
	
	
	
	
	//评级选项卡绑定onclick事件
	var _liClick = function(){
		$(".showprogress ul li").click(function(){
			var $this = $(this);
			var name = $this.attr("name");
			var changeFlag =  false;
			if(name=="dx"){
				if(isTrue($this,"chosefin")){ changeFlag = true; }else{ changeFlag = false; }
			}else if(name=="evalapp"){
				if(isTrue($this,"chosefin")){ changeFlag = true; }else{ changeFlag = false;}
				if(gardeCardLength>0){
					if(isTrue($this,"dx")){ changeFlag = true; }else{ changeFlag = false;}
				}
			}else{
				changeFlag = true; 
			}
			if(changeFlag){
				var evalFormId = "eval1001";
				//个人
				if(cusBaseType=="2"){
					evalFormId = "evalpers1001";
				}
                if(useType == "2"){
                    evalFormId = "appRiskCheckDetail";
                }
                if(useType == "3"){
                    evalFormId = "creditRiskCheckDetail";
                }
				//发起评级
				if(name=="evalapp"&&query!="query"){
					_evalAppFormInit(evalFormId);
					$("div[name=evalapp]").find(".li_content").eq(0).mCustomScrollbar("update");
				}else{
					$("#bootstarpTag-div").find(".hidden-content").hide();
					$("#bootstarpTag-div").find("i").remove();
				}
				$(".selected").removeClass("selected");
				$(this).addClass("selected");
				var liIndex = $(this).index();
				$(".content_ul").animate({left:"-"+(liIndex*100)+"%"});
				//$("#addEval").hide();
				//$("#editEval").hide();
				$("#gradeCardButton").hide();
				$("#evalappButton").hide();
				$("#chosefinButton").hide();
				if(name=="dx"){
					name="gradeCard";
					$("#addEval").show();
					if(evalStage=="2"){//已保存评级评分卡
						$("#editEval").show();
						$("#addEval").hide();
					}
					$("div[id=evalapp] div[id=moreBar]").remove();
					myCustomScrollbarForForm({
						obj:"#gradeCard",
						advanced : {
							updateOnContentResize : true
						}
					});
				}
				if(name=="evalapp"||name=="chosefin"){
					$("#addEval").hide();
					$("#editEval").hide();
				}
				$("#"+name+"Button").show();
			}else{
				alert(top.getMessage("FIRST_OPERATION","前面的步骤"),0);
			}
		});
	}
	//评级模型赋值
	var _setGradeModelOption = function(){
		_selectDataSource(cusNo,cusBaseType,cusType,useType,gradeType,'1','evalScenceNo');
		_selectDataSource(cusNo,cusBaseType,cusType,useType,gradeType,'2','evalScenceSubNo');
	};


    var _selectDataSource = function (cusNo,cusBaseType,cusType,useType,gradeType,evalClass,selectName) {
		var select = $("select[name='"+selectName+"']");
		$.ajax({
			url:webPath+"/evalScenceConfig/getEvalScenceConfigData?cusNo="+cusNo+"&cusBaseType="+cusBaseType,
			data:{cusType:cusType,useType:useType,gradeType,gradeType,evalClass:evalClass},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					//如果是编辑，只展示选中的模型
					if(getFlag==true){
						$.each(data.dataMap.EvalScenceConfigList, function(key,obj) {
							if(evalScenceNo==obj.evalScenceNo){
								select.append("<option value='"+obj.evalScenceNo+"'>"+obj.evalScenceName+"</option>");
							}
						});
					}else{
						$.each(data.dataMap.EvalScenceConfigList, function(key,obj) {
							select.append("<option value='"+obj.evalScenceNo+"'>"+obj.evalScenceName+"</option>");
						});
					}
				}else{
					window.top.alert("获得评分模型失败",0);
				}
			},error:function(){
				// window.top.alert(top.getMessage("ERROR_REQUEST_URL", getUrl),0);
			}
		});
	}
	var _selectFinMain = function(){
		var cusNo = $("input[name=cusNo]").val();
		dialog({
			id:"selectFinMainDialog",
			url:webPath+"/cusFinMain/getListPage?cusNo="+cusNo+"&gradeType="+gradeType+"&evalClass="+evalClass,
			title:"财务报表",
			width:550,
    		height:300,
    		backdropOpacity:0,
    		onclose:function(){
    			if(typeof(this.returnValue) == "undefined" || typeof(this.returnValue) == null || this.returnValue == ''){
    			}else{
    				$("form[id=choseFinForm]").find("input[name=rptDate]").val(this.returnValue.rptDate);
    				$("form[id=choseFinForm]").find("input[name=rptType]").val(this.returnValue.rptType);
                    $("form[id=choseFinForm]").find("input[name=accountId]").val(this.returnValue.accountId);
                    $("form[id=choseFinForm]").find("input[name=rptDate]").blur();
    			}
    		}
			
		}).showModal();
	};
	//获得评分卡及指标信息
	var _getEvalListData = function(){
		jQuery.ajax({
			url:webPath+"/appEval/getEvalListDataAjax",
			data:{evalAppNo:evalAppNo,evalScenceNo:evalScenceNo},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					dataMap = data;
					_init();
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
				jQuery.ajax({
					url:webPath+"/mfEvalGradeCard/getByIdAjax",
					data:{gradeCardId:obj.gradeCardId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){
					},success:function(data){
						if(data.flag == "success"){
							var tmpShowData;
							if(data.formData.gradeCardNameEn == 'dlpg'){
								var dlData = {index:{"indexName":"指标名称","stdCore":"指标分","score":"指标值"},};
								tmpShowData = dlData;
							}else{
								tmpShowData = showData;
							}
							$.each(tmpShowData, function(index,dataObj) {
								var $liContent =  $(".eval-content .content_ul li").find("div[name="+obj.gradeCardId+"]");
								var $table = $liContent.find("table");
								var $thTr = $('<tr></tr>');
								$.each(dataObj, function(key,dic) {
									var $th = $('<th style="width:30%;float:left;" name='+key+'>'+dic+'</th>');
									$thTr.append($th);
									if(key=="indexName"){
										$th.before('<th style="width:10%;" name="noneshow">&nbsp;</th>');
										$th.addClass("text_align_s");
									}
								});
								$table.find("thead").append($thTr);
							});
						}
					},error:function(data){
						alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});			}
		});
	};
	//初始化评级结论表单
	var _evalAppFormInit = function(evalFormId){
		jQuery.ajax({
			url:webPath+"/appEval/getByIdAjaxForForm",
			data:{ajaxData:"",evalAppNo:evalAppNo,evalFormId:evalFormId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$(".content_ul li div[name=evalapp]").find("#bootstarpTag-div").html(data.formHtml);
					if(typeof (useType) != "undefined" && (useType == "2" || useType == "3")){

                    }else{
                        $("#evalMsg select[name=mangGrade] option:eq(0)").remove();
                    }
					_addGradeCard(data);
					if(query=="query"){//详情
						$("#evalapp").find("i").remove();//隐藏输入框中的小笔或的放大镜图标
						$("#evalapp").find("font").remove();//隐藏输入框中的小笔或的放大镜图标
						$("#evalapp").find("input,select,textarea").attr("readonly","true");
						$("#evalapp").find("input,select,textarea").removeAttr("onclick");
					}
                    //跳转至提交页面后设置提交按钮可用
                    $('#evalappButton').find('input').removeAttr("disabled");
					
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//初始化评级申请选择财务报表
	var _choseFininit = function(evalFormId){
		jQuery.ajax({
			url:webPath+"/appEval/getByIdAjaxForChoseFinForm",
			data:{ajaxData:"",evalAppNo:evalAppNo,evalFormId:evalFormId},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					$(".content_ul li div[name=chosefin]").find("#choseFinDiv").html(data.formHtml);
					_setGradeModelOption();
					$("#choseFinForm").find("i").remove();//隐藏输入框中的小笔或的放大镜图标
					$("#choseFinForm").find("select").attr("readonly","true");
				}else if(data.flag=="error"){
					alert(data.msg,0);
				}
			},error:function(data){
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};	
	//添加评分卡
	var _addGradeCard = function(){
		$.each(dataMap.gradeCardListData,function(i,obj){
			var $tr=$('<tr id='+obj.gradeCardId+' name="gradeCard"></tr>');
			var strTd='<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">'+obj.gradeCardName+'</label></td>'+
			'<td class="tdvalue  half right" colspan="1" rowspan="1"><div class="input-group">';
			if(dataMap["dlData"+obj.gradeCardId]){
				strTd=strTd+'<input type="text" value="'+dataMap["dlData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
				'<input type="hidden" name="dlData'+obj.gradeCardId+'" value="1"></div></td>';
				$tr.append(strTd);
				$("input[name=grade]").parents("tr").eq(0).before($tr);
			}
			if(dataMap["dxData"+obj.gradeCardId]){
				strTd=strTd+'<input type="text" value="'+dataMap["dxData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
				'<input type="hidden" name="dxData'+obj.gradeCardId+'" value="1"></div></td>';
				$tr.append(strTd);
				$("input[name=grade]").parents("tr").eq(0).before($tr);
			}
			if(dataMap["finData"+obj.gradeCardId]){
				strTd=strTd+'<input type="text" value="'+dataMap["finData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
				'<input type="hidden" name="finData'+obj.gradeCardId+'" value="1"></div></td>';
				$tr.append(strTd);
				$("input[name=grade]").parents("tr").eq(0).before($tr);
			}
			if(dataMap["adjData"+obj.gradeCardId]){
				strTd=strTd+'<input type="text" value="'+dataMap["adjData"+obj.gradeCardId].score+'" readonly="" class="form-control">'+
				'<input type="hidden" name="adjData'+obj.gradeCardId+'" value="1"></div></td>';
				$tr.append(strTd);
				$("input[name=grade]").parents("tr").eq(0).before($tr);
			}
		});
		$.each($("tr[name=gradeCard]"),function(i,obj){
			var len = $(obj).find("td").length;
			if(len<4){
				if($(obj).next().attr("name")){
					$(obj).append($(obj).next().html());
					$(obj).next().remove();
				}
			}
		});
	};
	//初始化表头
	var _initThead = function(obj,showData){
		var $obj = $(obj);
		if(dataMap.gradeCardListData){
			$.each(dataMap.gradeCardListData,function(i,obj){

			});
			
		}
	};
	
	var _dxScoreChange = function(obj){
		$(obj).parents("tbody").eq(0).find("input[checked=checked]").removeAttr("checked");
		$(obj).prop("checked","checked");
	};
	
	var _getEvalDxDetailInfo = function(){
		$("input[type=radio]").attr("disabled","true");
		$("#editEval").show();
		$("#addEval").hide();
	};
	
	var _getEvalAdjDetailInfo = function(){
		$("input[type=radio]").attr("disabled","true");
		$("#editEval").show();
		$("#addEval").hide();
	};
	
	var _editEval = function(){
		$("input[type=radio]").removeAttr("disabled");
		$("input[id^='property_']").removeAttr("readOnly");
		$("#editEval").hide();
		$("#addEval").show();
	};
	var nextStep = function(obj){
		var $obj = $(obj);
		var liIndex = $obj.parents("li").index();
		if($obj.parents(".li_content").attr("data-flag") == "success"){
			$obj.parents(".content_ul").animate({left:"-"+((liIndex+1)*100)+"%"});
		}else{
			alert("当前操作未完成",0);
		}
	};
	
	var isTrue = function($this,str){
		var $obj = $this.parent().find("li[name="+str+"]");
		if($obj.length>0){
			if($obj.hasClass("success")){
				return true;
			}else{
				return false;
			}
		}else{
			return true;
		}
	};
	function p(s) {
	    return s < 10 ? '0' + s: s;
	}
	//获取两个后的时间
	var   _HuoQu =  function  (){
		var myDate = new Date();
		//获取当前年
		var year=myDate.getFullYear()+2;
		//获取当前月
		var month=myDate.getMonth()+1;
		//获取当前日
		var date=myDate.getDate(); 
		var now=year+'-'+p(month)+"-"+p(date);
		fPopUpCalendarDlg({min: now});
	} 
	var _checkNum = function (obj) {
		var value = $(obj).val();
		var reg = /^\d+$|^\d*\.\d+$/g;
		if (!reg.test(value)) {
			alert(top.getMessage("ERROR_FORM_FORMAT",""), 0);
			$(obj).val(0);
		}
	}
	return{
		init:_init,
		getEvalListData:_getEvalListData,
		initThead:_initThead,
		getEvalAdjDetailInfo:_getEvalAdjDetailInfo,
		editEval:_editEval,
		dxScoreChange:_dxScoreChange,
		selectFinMain:_selectFinMain,
		HuoQu:_HuoQu,
		dealWithCusType:_dealWithCusType,
		selectDataSource :_selectDataSource,
		checkNum:_checkNum

	};
}(window,jQuery);