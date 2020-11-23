	function getAssName1Dialog(userInfo){
		$("input[name=assName1]").val(userInfo.opName);
		$("input[name=assNo1]").val(userInfo.opNo);
	};
	function updatePactEndDate(){
		var beginDate =  $("input[name=beginDate]").val();
		var signDate = $("input[name=signDate]").val();//签约日期
		//开始日期选择后，默认带出签约日期
		$("input[name=signDate]").val(beginDate);
		var termShow = $("input[name=termShow]").val();
		var term = $("input[name=term]").val();
		var termType = $("[name=termType]").val();
		var intTerm = parseInt(term);
        var d;
        var endDate,str,strEndDate;
		if(1==termType){ //融资期限类型为月 
			d = new Date(beginDate);//合同结束日期展示
			d.setMonth(d.getMonth()+intTerm);
			
			endDate= new Date(beginDate);//合同结束日期
			endDate.setMonth(endDate.getMonth()+intTerm);
			if(2==calcIntstFlag){// 2-首尾都计算
				d.setDate(d.getDate()-1);
				endDate.setDate(endDate.getDate()-1);//合同结束日期
			}else if(1==calcIntstFlag&&2==pactEndDateShowFlag){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为2时值为end_date-1,
				d.setDate(d.getDate()-1);
			}else if(1==calcIntstFlag&& 3==pactEndDateShowFlag){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为 3    实际结束日期减一天，显示结束日期再减一天, 否则和end_date一致
				endDate.setDate(endDate.getDate()-1);//合同结束日期 实际结束日期减一天
				d.setDate(endDate.getDate()-1);//显示结束日期再减一天(在实际结束日期的基础上)
			}
			str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
			$("input[name=endDateShow]").val(str);
			strEndDate= endDate.getFullYear()+"-"+(endDate.getMonth()>=9?endDate.getMonth()+1:"0"+(endDate.getMonth()+1))+"-"+(endDate.getDate()>9?endDate.getDate():"0"+endDate.getDate());
			$("input[name=endDate]").val(strEndDate);
		}else{ //融资期限类型为日 
			d = new Date(beginDate);//合同结束日期展示
		 	d.setDate(d.getDate()+intTerm);
			endDate= new Date(beginDate);//合同结束日期
			endDate.setDate(endDate.getDate()+intTerm);
			if(2==calcIntstFlag){// 2-首尾都计算
				d.setDate(d.getDate()-1);
				endDate.setDate(endDate.getDate()-1);////合同结束日期
			}else if(1==calcIntstFlag&&2==pactEndDateShowFlag){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为2时值为end_date-1,否则和end_date一致
				d.setDate(d.getDate()-1);
			}else if(1==calcIntstFlag&& 3==pactEndDateShowFlag){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为 3    实际结束日期减一天，显示结束日期再减一天, 否则和end_date一致
				endDate.setDate(endDate.getDate()-1);//合同结束日期 实际结束日期减一天
				d.setDate(endDate.getDate()-1);//显示结束日期再减一天(在实际结束日期的基础上)
			}
			str = d.getFullYear()+"-"+(d.getMonth()>=9?d.getMonth()+1:"0"+(d.getMonth()+1))+"-"+(d.getDate()>9?d.getDate():"0"+d.getDate());
			$("input[name=endDateShow]").val(str);
			strEndDate= endDate.getFullYear()+"-"+(endDate.getMonth()>=9?endDate.getMonth()+1:"0"+(endDate.getMonth()+1))+"-"+(endDate.getDate()>9?endDate.getDate():"0"+endDate.getDate());
			$("input[name=endDate]").val(strEndDate);
		}
		//选择合同开始日后，清除合同结束日中的不能为空提示
		$("input[name=endDateShow]").parent().find(".error.required").remove();
	};
	// 字符串替换
	String.prototype.replaceAll = function(s1,s2){ 
		return this.replace(new RegExp(s1,"gm"),s2); 
	}
	function editFollowPactNo(obj , id){
		id = id.substring(10);
		$(obj).hide();
		$(obj).after("<input name=\"followPactNo\" style=\"width:165px;text-align: center;\" value=\"\" maxlength=\"30\" type=\"text\" onblur=\"updateFollowPactNo(this,'" + id + "');\">");
		$("input[name='followPactNo']")[0].focus();
	}
	function updateFollowPactNo(obj , id){
	    var followPactNo = $(obj).val();
	    if(followPactNo != $(obj).prev().text() && "" != followPactNo.replaceAll(" ","")){
			$.ajax({
				url:webPath+"/mfBusCollateralDetailRel/updateFollowPactNoAjax",
				data:{id:id,followPactNo:followPactNo},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						$(obj).hide();
						$(obj).prev().text(followPactNo);
						$(obj).prev().show();
						$(obj).remove();
					}else{
						$(obj).hide();
						$(obj).prev().show();
						$(obj).remove();
						window.top.alert(data.msg,0);
						
					}
				},error:function(){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
	    }else{
	    	$(obj).hide();
			$(obj).prev().show();
			$(obj).remove();
	    }
	}
	function getTemplateBizConfigId(obj , id){
		id = id.substring(10);
		$.ajax({
				url:webPath+"/mfBusCollateralDetailRel/getTemplateBizConfigIdAjax",
				data:{id:id},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						printFollowPactFile(data.templateBizConfigId ,data.repayDetailId);
					}else{
						window.top.alert(data.msg,0);
					}
				},error:function(){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
	}
	var printFollowPactFile = function(templateBizConfigId , repayDetailId) {
		var url = webPath+"/mfTemplateBizConfig/getOfficeUrlAjax?templateBizConfigId=" + templateBizConfigId;
		var backUrl = encodeURIComponent(location.href);// 关闭office文档时返回目前的页面
		var temParm = 'cusNo=' + cusNo + '&appId=' + appId + '&pactId=' + pactId + '&repayDetailId=' + repayDetailId;
		$.ajax({
			url : url + "&" + temParm,
			data : {
				"returnUrl" : backUrl
			},
			type : 'post',
			dataType : 'json',
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
	};
	
	function checkRepayFixDate(obj){
		var name = $(obj).attr("name");
		var title = $(obj).attr("title").split("(")[0];
		//固定还款日
		if(name=="repayDateDef"){
			var repayDateDefVal = $(obj).val();
			if(parseFloat(repayDateDefVal)<parseFloat(1)||parseFloat(repayDateDefVal)>parseFloat(29)){
				$(obj).val(0);
				alert(title+"必须在"+new Number(1)+"到"+new Number(29)+"之间",0);
			}
		}
	};
	
	function getCusMngNameDialog(userInfo){
		$("input[name=cusMngName]").val(userInfo.opName);
		$("input[name=cusMngNo]").val(userInfo.opNo);
	};
;
var MfBusPactInsertForManage = function(window, $) {
	var _init = function () {
		//滚动条
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		
		// 是否隐藏 复利利率上浮字段
		if(cmpdRateType =="0"){//隐藏						
			$('input[name=cmpdFloat]').parent('.input-group').hide();
			$('input[name=cmpdFloat]').parents('.tdvalue').prev('td').find('label').hide();
		}
		//是否要走合同审批，走合同审批并且要指派第一个审批人员时，展示“指派审批人员”字段
		if(processId ==null || processId==''){
           // $("[name=firstApprovalUserName]").parents("td").hide();// 字段td
            //$("[name=firstApprovalUserName]").parents("td").prev("td").hide();// 标签td
            $("[name=firstApprovalUserName]").parents("tr").remove();// 字段td
		}
		//共同借款人处理
		 if($("input[name=coborrNum]").is(':visible')){
			 var $obj = $("input[name=coborrNum]");
			 $("input[name=coborrNum]").popupSelection({
					searchOn:true,//启用搜索
					inline:false,//下拉模式
					items:coborrNum,//请求数据URL
				    multiple:true,//多选
					title:"共同借款人",
					handle:false
			});
			 if(typeof($obj.attr('readonly'))!='undefined'){
			    $obj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
			 }
		}
		//还款方式处理
		var $repayObj = $("input[name=repayType]");
		if($repayObj.is(':visible')){
			$("input[name=repayType]").popupSelection({
				searchOn:false,//启用搜索
				inline:false,//下拉模式
				multiple:false,//多选选
				title:"还款方式",
				valueClass:"show-text",
				items:ajaxData.repayTypeMap,
				changeCallback : function (obj, elem) {
				}
			}); 
			if(typeof($repayObj.attr('readonly'))!='undefined'){
				$repayObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
			} 
		}
		//担保方式处理
		var $vouObj = $("input[name=vouType]");
		if($vouObj.is(':visible')){
			$("input[name=vouType]").popupSelection({
				searchOn:false,//启用搜索
				inline:true,//下拉模式
				multiple:false,//多选选
				title:"担保方式",
				items:ajaxData.vouTypeMap,
				changeCallback : function (obj, elem) {
				}
			}); 
			if(typeof($vouObj.attr('readonly'))!='undefined'){
				$vouObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
			}
		}
//		bindVouTypeByKindNo($("input[name=vouType]"), kindNo);
		
		
		var $repayDateSetObj = $("input[name=repayDateSet]");
		var repayDateSet1 =  $("input[name=repayDateSet]").val();
		if(repayDateSet1!="3"){
			$("input[name=repayDateDef]").addClass('hidden').parents('td').prev('td').children('label').addClass('hidden');
		}
		if($repayDateSetObj.is(':visible')){
			$("input[name=repayDateSet]").popupSelection({
				searchOn:false,//启用搜索
				inline:false,//下拉模式
				multiple:false,//多选选
				title:"还款日设置",
				valueClass:"show-text",
				items:ajaxData.repayDateSetMap,
				changeCallback : function ( elem) {
					var callBackVal = elem.data("values").val();
					var callBackValArr = callBackVal.split("_");
					//还款日类型为固定还款日时，固定还款日字段允许编辑,其他类型不允许编辑
					if(callBackValArr[0] == "3"){
						$("input[name=repayDateDef]").removeClass('hidden').parents('td').prev('td').children('label').removeClass('hidden');
					} else {
						$("input[name=repayDateDef]").addClass('hidden').parents('td').prev('td').children('label').addClass('hidden');
					}
				}
			}); 
			if(typeof($repayDateSetObj.attr('readonly'))!='undefined'){
				$repayDateSetObj.siblings(".pops-value").unbind('click').find('.pops-close').remove();
			}
		}
		if($("input[name=repayMode]").css("display") == "none" || $("input[name=repayMode]").attr("type") == "hidden"){
			
		}else{
			$("input[name=repayMode]").popupSelection({
				searchOn:false,//启用搜索
				inline:false,//下拉模式
				multiple:true,//多选选
				groupFlag:true,//启用分组
				title:"还款渠道",
				labelShow:false,
				items:ajaxData.repayModeMap,
				changeCallback : function (obj, elem) {
				}
			}); 
		}
		
		MfBusBankAccCom.bankAccInit();
		
		
	};

	
	//合同签约保存方法
	var _ajaxUpdate = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				alert(top.getMessage("CONFIRM_OPERATION","提交下一步"),2,function(){
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray()); 
					LoadingAnimate.start();
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam,wkfAppId:wkfAppId,taskId:taskId,appId:appId},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							LoadingAnimate.stop();
							if(data.flag == "success"){
								 top.flag=true;
								 top.pactUpdateFlag=true;//表示是否是合同签约节点
								 top.pactSts = data.pactSts;
								 top.pactDetailInfo = data.pactDetailInfo;
								 myclose_click();
							}
							if(data.flag == "error"){
								alert(data.msg,0);
							}
						},error:function(data){
							 LoadingAnimate.stop();
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				});
			}
	};
	

	return {
		init : _init,
		ajaxUpdate:_ajaxUpdate,
	};
}(window, jQuery);