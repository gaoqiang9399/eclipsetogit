
var CwParmEntrance = function(window, $) {
	/**
	 * 在此处定义全局变量-各函数内公共使用的。
	 * 函数作用域内的局部变量还是要通过var在函数内部声明。
	 */
	var tipsTimeoutId;	// 用于重置显示tips框的自动关闭时间。
	
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
	

		// bind event
		$(".btn-app").bind("click", function(event){
			switch ($(this).attr("id")) {
//			case "CwCycleHst":
//				window.location.href = webPath+"/cwCycleHst/getListPage";
//				break;
			case "kemu":
				window.location.href = webPath+"/cwComItem/getListPage?accType=1";
				break;
			case "cwInitBal":
				window.location.href = webPath+"/cwInitBal/getInitPage";
				break;
			case "proofWord":
				window.location.href = webPath+"/cwProofWords/getListPage";
				break;	
			/*case "gangwei":
				window.location.href = "";
				break;*/
			case "sysParam":
				window.location.href = webPath+"/cwSysParam/toSysParamSetPage";
//				window.location.href = webPath+"/cwSysParam/getListDataPage";
				/*window.location.href = webPath+"/cwSysParam/getListPage";*/
				break;
			case "voucherRemark":
				window.location.href = webPath+"/cwVoucherRemarks/getListPage";
				break;
			case "fuzhuhesuan":
				window.location.href = webPath+"/cwFication/getListPage";
				break;
			case "CwCycleHst":
				window.location.href = webPath+"/cwCycleHst/getListPage";
				break;
			case "reInit":
				_reInitCwSystem();
				break;	
			case "sysInit":
//				_cwSystemInit();
				window.location.href = "CwSystemInit.jsp";
				break;	
			case "codeLenth":
				window.parent.openBigForm(webPath+"/cwComItem/getAccnoListPage", '维护科目编码长度',closeCallBack, 60);
				break;
			case "cwVchRule":
				window.location.href = webPath+"/cwVchRuleMst/getListPage";
				break;
			case "cwAssetsClass":
				window.location.href= webPath+"/cwAssetsClass/getListPage";
				break;
			case "personManage":
				window.location.href= webPath + "/sysOrg/listSysOrg";
				break;
			case "ztBookManager":
				window.location.href= webPath+"/cwZtBooks/getListPage";
				break;
			case "cwJTManage":
				window.location.href= webPath+"/cwJiti/getListPage";
				break;
			case "monPriceTax":
				window.location.href= webPath+"/cwPriceTaxSep/getListPage";
				break;
			case "voucherDozen":
				window.location.href= webPath+"/cwPrintTmplItem/getListPage";
				break;
			case "corrBind":
				window.location.href= webPath+"/cwVchOutCorrbind/getListPage";
				break;
			case "vchOutSoftVer":
				window.location.href= webPath+"/cwVchOutSoftver/getListPage";
				break;
			case "bankAccManage":
				window.location.href = webPath+"/cwCusBankAccManage/getListPage";
				break;
			default:
				_showTips(this);
				break;
			}
		});
	};
	
	var _cwSystemInit = function(){
		var date = new Date();
		var year = date.getFullYear();
        var month = date.getMonth() + 1;
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        var week = year + month;
		var $tabdiv = $('<div class="container"></div>');
		var $form = $('<form class="sysInitForm"></form>');
		var $item1 = $('<div class="row form_item">'
					+ '<span>启用期间：</span>'
					+ '<input type="text" class="form-control " readonly name="weeks"'
					+ '	id="weeks" autocomplete="off" onclick="laydatemonth(this);"'
					+ '	onkeydown="enterKey();" value="' + week + '" >'
					+ '</div>');
		var $item2 = $('<div class="row form_item">'
					+ '<span>启用期间：</span>'
					+ '<select class="form-control" id="standNo" name="standNo" >'
					+ '	<option value="10001">标准小贷会计制度</option>'
					+ '</select>'
					+ '</div>');
		var $item3 = $('<div class="row text-center form_item">'
					+ '<button type="button" class="btn btn-info" onclick="cwSystemInit()">确定初始化</button>'
					+ '</div>');
		$form.append($item1).append($item2);
		$tabdiv.append($form).append($item3);
		reinitDialog = dialog({
			id:"userDialog",
			title:'系统提示',
//	    	url: 'CwReInitSystem.jsp',
			content: $tabdiv,
			width:500,
			height:270,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					//返回对象的属性:opNo,opName
					if(typeof(callback)== "function"){
						callback(this.returnValue);
					}else{
					}
				}
			}
		}).showModal();
	}
	
	var _reInitCwSystem = function(){
		var $tabdiv = $('<table class="re_dialog"><tbody></tbody></table>');
		var $tr1 = $('<tr><td class="ui_icon"><img src="'+webPath+'/component/finance/paramset/images/alert.gif" class="ui_icon_bg"></td>'
					+ '<td class="ui_main" style="width: 430px; height: 180px;"><div class="ui_content" style="padding: 20px 10px 0 10px;">'
					+ '<div class="re-initialize"><h4>重新初始化系统将会清空你录入的所有数据，请慎重！</h4>'
					+ '<ul><li>系统将删除您新增的所有科目</li><li>系统将删除您录入的所有凭证</li><li>系统将删除您录入的所有初始化数据</li></ul>'
					+ '<p><input type="checkbox" id="understand"><label for="understand">我已清楚了解将产生的后果</label>'
					+ '</p>'
					+ '<p class="check-confirm">（请先确认并勾选“我已清楚了解将产生的后果”）</p></div></div></td></tr>');
		var $tr2 = $('<tr><td colspan="2"><div class="ui_buttons"><input type="button" value="重新初始化" onclick="reInitSystem();" class="btn btn-info"></div></td></tr>')		
		$tabdiv.append($tr1).append($tr2);
		reinitDialog = dialog({
			id:"userDialog",
    		title:'系统提示',
//	    	url: 'CwReInitSystem.jsp',
			content: $tabdiv,
    		width:500,
    		height:270,
    		backdropOpacity:0,
    		onshow:function(){
    			this.returnValue = null;
    		},onclose:function(){
    			if(this.returnValue){
    				//返回对象的属性:opNo,opName
    				if(typeof(callback)== "function"){
    					callback(this.returnValue);
    				}else{
    				}
    			}
    		}
    	}).showModal();
	}
	
	var _showTips = function (obj) {
		var d = dialog({
			id : "oaInBuilding",
			content : "正在建设中，敬请期待。",
			padding : "3px"
		}).show(obj);
		if (tipsTimeoutId) {
			clearTimeout(tipsTimeoutId);
		}
		tipsTimeoutId = setTimeout(function() {
			d.close().remove();
		}, 1000);
	};
	//财务是否单独使用
	var _cwAloneUse = function () {
		jQuery.ajax({
			url:webPath+"/cwInitSystem/cwAloneUseAjax",
			data:{ajaxData:''},
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				if(data.flag == "success"){
					if(data.aloneUsedCw=='true'){
						$("#personManage").removeClass('hidden');
					}
				}else if(data.flag == "error"){
					$('#personManage').remove();
				}			
			},error:function(data){
				$('#personManage').remove();
			}
		});
	};
	//账套是否显示
	var _ztBooksShow = function () {
		jQuery.ajax({
			url:webPath+"/cwZtBooks/getztShowAjax",
			type:'post',
			data:'',
			async:false,
			success:function(data){
				if(data.flag=="success"){
					$("#ztBookManager").show();
				}else {
					$("#ztBookManager").remove();
				}
			},error:function(){
				$("#ztBookManager").remove();
			}
		});
	};
	//按月计税管理是否显示
	var _monPriceTaxShow = function () {
		jQuery.ajax({
			url:webPath+"/cwPriceTaxSep/getMonPriceTaxShowAjax",
			type:'post',
			data:'',
			async:false,
			success:function(data){
				if(data.flag=="success"){
					if(data.result==2){
						$("#monPriceTax").removeClass('hidden');
					}else{
						$("#monPriceTax").remove();
					}
				}else {
					$("#monPriceTax").remove();
				}
			},error:function(){
				$("#monPriceTax").remove();
			}
		});
	};
	
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		showTips :_showTips,
		cwAloneUse:_cwAloneUse,
		ztBooksShow:_ztBooksShow,
		monPriceTaxShow:_monPriceTaxShow
	};
}(window, jQuery);