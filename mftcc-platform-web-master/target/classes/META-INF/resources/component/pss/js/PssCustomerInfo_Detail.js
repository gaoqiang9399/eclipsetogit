var saler;
var cusCategoryArray;
var cusGradeArray;
var originalAccountsReceived = '';
var originalDepositReceived = '';
var PssCustomerInfo_Detail = function(window, $){
	
	var _init = function(){
		saler = ajaxData.saler;
		cusCategoryArray = ajaxData.cusCategoryArray;
		cusGradeArray = ajaxData.cusGradeArray;
		cusCode = ajaxData.cusCode;
		cusNo = ajaxData.cusNo;
		
		originalAccountsReceived = ajaxData.originalAccountsReceived;
		originalDepositReceived = ajaxData.originalDepositReceived;
		
		$("input[name=salerNo]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//弹窗模式
			multiple:false,//单选
			labelShow : false,//已选项显示
			items:saler,
			/*addBtn:{//添加扩展按钮
			"title":"新增",
			"fun":function(hiddenInput, elem){
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增商品类型','PSS_GOODS_TYPE', elem);
				}
			}*/	
		});
		
		$("input[name=cusCategory]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//弹窗模式
			multiple:false,//单选
			labelShow : false,//已选项显示
			items:cusCategoryArray,
			addBtn:{//添加扩展按钮
			"title":"新增",
			"fun":function(hiddenInput, elem){
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增客户类别','PSS_CUS_CATEGORY', elem);
				}
			}
		});
		
		$("input[name=cusGrade]").popupSelection({
			searchOn:true,//启用搜索
			inline:true,//弹窗模式
			multiple:false,//单选
			labelShow : false,//已选项显示
			items:cusGradeArray,
			/**
			addBtn:{//添加扩展按钮
			"title":"新增",
			"fun":function(hiddenInput, elem){
					$(elem).popupSelection("hideSelect", elem);
					BASE.openDialogForSelect('新增客户等级','PSS_CUS_GRADE', elem);
				}
			}**/
		});
		
		$("input[name=cusCode]").val(cusCode);
		
		myCustomScrollbar({
	    	obj : "#content",//页面内容绑定的id
	    	url : webPath+"/pssCustomerContacts/findByPageAjax",
	    	tableId : "tablepsscustomercontacts0001",//列表数据查询的table编号
	    	tableType : "thirdTableTag",//table所需解析标签的种类
	    	pageSize : 30,//加载默认行数(不填为系统默认行数)
	    	data : {"cusNo" : cusNo},
	    	/* ownHeight : true, */
	    	callback : function(options, data) {
	    		Pss.addRowOperateEvent("sequence",null,null,null,refreshFormData);	
	    		
	    		_addIsfirstContactManEvent("isfirstContactManName", "isfirstContactMan");
	    		
	    		var stringThs = new Array("contactsName");
	    		Pss.addStringEvent(stringThs);
	    		
	    		stringThs = new Array("mobilePhone");
	    		Pss.addStringEvent(stringThs);
	    		
	    		stringThs = new Array("telePhone");
	    		Pss.addStringEvent(stringThs);
	    		
	    		stringThs = new Array("otherContactMode");
	    		Pss.addStringEvent(stringThs);
	    		
	    		stringThs = new Array("contactsAddress");
	    		Pss.addStringEvent(stringThs);
	    		
				var tabHeadThs = new Array("contactsName", "isfirstContactManName");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
				
	    	}
	    });
		
		$('.footer_loader').remove();
	    $('.table-float-head').remove();
	    
	    $('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    
	    myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	    
	};
	
	function refreshFormData(){
		
	};

	var _addIsfirstContactManEvent = function (isfirstContactManNameTh, isfirstContactManTh){
		var isfirstContactManNameThIndex = $("#tablist>thead th[name='" + isfirstContactManNameTh + "']").index();
		var isfirstContactManThIndex = $("#tablist>thead th[name='" + isfirstContactManTh + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $isfirstContactManNameTd = $(tr).children("td:eq(" + isfirstContactManNameThIndex + ")");
			var $input = $("<input style='display:none;' id='isfirstContactManInput" + index + "'></input>");
			$isfirstContactManNameTd.append($input);
			
			var $isfirstContactManTd = $(tr).children("td:eq(" + isfirstContactManThIndex + ")");
			var isfirstContactMan = $isfirstContactManTd.children("input[name='" + isfirstContactManTh + "']").val();
			if (isfirstContactMan.length > 0) {
				$input.val(isfirstContactMan);
			}
		});
		
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + isfirstContactManNameThIndex + ")").on('click', function() {
				var $td = $(this);
				var $isfirstContactManTd = $(this).parent("tr").children("td:eq(" + isfirstContactManThIndex + ")");
				var inputLength = $(this).children("input").length;
				if (inputLength == 0) {
					var $input = $("<input style='display:none;' id='isfirstContactManInput" + index + "'></input>");
					$(this).append($input);
				}
				var divLength = $(this).children("div .pops-select").length;
				if (divLength == 0) {
					$(this).children("input").popupSelection({
						searchOn:false, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.isfirstContactMan,
						changeCallback : function (obj, elem) {
							$isfirstContactManTd.children("input[name='" + isfirstContactManTh + "']").val(obj.val());
							Pss.popsSelectSelected($td);
						}
					});
					Pss.addPopsSelectClick($td);
				}
				/*var tdTop = $(this).position().top;
				$(this).children("div .pops-select").css({'top':tdTop});*/
			}).click();
		});
	};
	
	function getTableData(){
		var pssCustomerContactss = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr){
			var pssCustomerContacts = new Object();
			var flag = '0';
			$("#tablist>thead th").each(function(thIndex, th) {
				
				var $td = $(tr).children("td:eq(" + thIndex + ")");
				var colName = $(th).attr("name");
				var inputValue = $td.children("input[name='" + $(th).attr("name") + "']").val();
				var popsValue = $td.children(".pops-value").text();
				
				if (typeof(inputValue) != "undefined" && inputValue.length > 0) {
					inputValue = Pss.ifMoneyToNumber(inputValue);
					pssCustomerContacts[$(th).attr("name")] = inputValue;
				} else if (typeof(popsValue) != "undefined" && popsValue.length > 0) {
					pssCustomerContacts[$(th).attr("name")] = popsValue;
				} else {
					var tdText = $td.text();
					tdText = Pss.ifMoneyToNumber(tdText);
					pssCustomerContacts[$(th).attr("name")] = tdText;
					if(colName == 'contactsName' && (tdText != '')){
						flag = '1';
					} 
				}
			});
			
			if(flag == '1')
				pssCustomerContactss.push(pssCustomerContacts);
		});
		
		return pssCustomerContactss;
	};
	
	function validateSubmitData(pssCustomerContacts) {
		
		var rightCount = 0;
		var firstCount = 0;
		if (pssCustomerContacts != null && pssCustomerContacts.length > 0) {
			for (var index in pssCustomerContacts) {
				
					if (pssCustomerContacts[index].isfirstContactMan == null || pssCustomerContacts[index].isfirstContactMan.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "是否首要联系人"), 3000);
						return false;
					}else{
						if(pssCustomerContacts[index].isfirstContactMan == '1'){
							firstCount++;
						}
					}
					
					if (pssCustomerContacts[index].contactsName == null || pssCustomerContacts[index].contactsName.length == 0) {
						DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "联系人"), 3000);
						return false;
					}
					
					rightCount++;
			}
			if (rightCount == 0) {
				DIALOG.tip(top.getMessage("NOT_FORM_EMPTY", "联系人"), 3000);
				return false;
			}
			if(firstCount > 1){
				DIALOG.tip(top.getMessage("FAILED_OPERATION", "'是否首要联系人标志'只能有一个为:是,"), 3000);
				return false;
			}
		}
		
		return true;
	};
	
	var _saveCustomerInfo = function(obj){
		LoadingAnimate.start();
		
		var pssCustomerContacts = getTableData();
		if(!validateSubmitData(pssCustomerContacts)) {
			LoadingAnimate.stop();
			return false;
		}
		if(originalAccountsReceived == ''){
			originalAccountsReceived = '0';
		}
		if(originalDepositReceived == ''){
			originalDepositReceived = '0';
		}
		
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParm = JSON.stringify($(obj).serializeArray());
			
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParm,
					pssCustomerContactsJson : JSON.stringify(pssCustomerContacts),
					originalAccountsReceived : originalAccountsReceived,
					originalDepositReceived : originalDepositReceived,
					saveOrEditFlag : saveOrEditFlag
				},
				type : "POST",
				dataType : "json",
				success : function(data) {
					LoadingAnimate.stop();
					if (data.success) {
						window.top.alert(data.msg, 1);
						myclose_click();
					} else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
		LoadingAnimate.stop();
	};
	
	return {
		init : _init,
		saveCustomerInfo : _saveCustomerInfo
	};
}(window, jQuery);