;
var MfCusCoreCompany = function(window, $) {
	/**
	 * 在此处开始定义内部处理函数。
	 */
	var _init = function () {
		  myCustomScrollbar({
		    	obj:"#content",//页面内容绑定的id
		    	url:webPath +"/mfCusCoreCompany/findByPageAjax",//列表数据查询的url
		    	tableId:"tableCoreCompanyList",//列表数据查询的table编号
		    	tableType:"thirdTableTag",//table所需解析标签的种类
		    	pageSize:30,//加载默认行数(不填为系统默认行数)
		    	topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		    });
	};
	
	var _insertInit = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	    dealWithCusType("3");
	    //初始化选择组件
	    _initPopupSelection();
	};
	var _updateInit = function () {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	    //处理客户类型
	    dealWithCusType("3");
	  //初始化选择组件
	    _initPopupSelection();
	};
	
	var _selectAttachBrInfoByTille=function(){
		selectOrgSelectByTilleDialog(MfBusTrench.setAttachBrInfo,'选择归属公司');
	};
	//选择归属公司后回调
	var _setAttachBrInfo=function(attachBrInfo){
		$("input[name=attachBrName]").val(attachBrInfo.brName);
		$("input[name=attachBrNo]").val(attachBrInfo.brNo);
		/*var inputStr = "<input type='text' title='专属客户经理' name='trenchOpNo' datatype='0' " +
				"mustinput='0' class='form-control Required' maxlength='500' readonly='' placeholder='点此选择业务员'" +
				" onblur='func_uior_valTypeImm(this);' onmousedown='enterKey()' onkeydown='enterKey();'>";
		var $obj=$("input[name=trenchOpNo]").parent();
		$obj.html(inputStr);
		//专属客户经理字段选择组件
		$("[name=trenchOpNo]").popupSelection({
			ajaxUrl : "SysUserActionAjax_getSysUserListByBrNosAjax.action?brNo="+attachBrInfo.brNo,
			searchOn : true,//启用搜索
			multiple : true,//单选
			ztree : true,
			ztreeSetting : setting,
			title : "专属操作员",
			handle : BASE.getIconInTd($("input[name=trenchOpNo]")),
			changeCallback : function (elem) {
				BASE.removePlaceholder($("input[name=trenchOpNo]"));
				var AcceptOpNo=elem.data("values").val();
				var nodes = elem.data("treeNode");
				var AcceptOpName="";
				var len = elem.data("treeNode").length;
				for(var i=0;i<len;i++){
					AcceptOpName+=nodes[i].name+"|";
				}
				$("input[name=coreCompanyOpName]").val(AcceptOpName);
			}
		});*/
	};
	//初始化选择组件
	var _initPopupSelection=function(){
	    //业务区域选择组件
	    $("input[name=businessAreaNo]").popupSelection({});
	    //专属客户经理字段选择组件
		$("[name=trenchOpNo]").popupSelection({});
	}
	//根据保证金和保证比例计算授信额度
	var _setCreditAmt=function(){
		var assureAmt=$("input[name=assureAmt]").val();
		var assureRate=$("input[name=assureRate]").val();
		if(assureAmt==""||assureRate==""){
			$("input[name=creditAmt]").val("");
			return;
		}
		assureAmt=Number(assureAmt.replace(/,/g,""));
		assureRate=Number(assureRate.replace(/,/g,""));
		$("input[name=creditAmt]").val((assureAmt*assureRate)/100);
		$("input[name=creditAmt]").attr("id","creditAmt");
		toMoney(document.getElementById("creditAmt"));
		$("#creditAmt").focus();
		$("#creditAmt").blur();
	}
	var _selectBusTrench=function(){
		selectBusTrenchDialog(MfBusTrench.setUpBusTrench,'选择上级渠道');
	};
	//选择上级渠道回调
	var _setUpBusTrench=function(upBusTrench){
		$("input[name=upTrenchId]").val(upBusTrench.trenchUid);
		$("input[name=upTrenchName]").val(upBusTrench.trenchName);
	};
	//渠道商关联业务员
    var _coreCompanyOpName= function(){
    	selectUserCustomTitleDialog("选择业务员?"+$("[name=coreCompanyOpNo]").val(),function(userInfo){
			var users=userInfo.brNo.replace(new RegExp(/(;)/g),'|');
			$('[name=coreCompanyOpNo]').val(users);
			$('[name=coreCompanyOpName]').val(userInfo.brName);
		});
    };
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		insertInit:_insertInit,
		updateInit:_updateInit,
		setAttachBrInfo:_setAttachBrInfo,
		setCreditAmt:_setCreditAmt,
		setUpBusTrench:_setUpBusTrench,
		selectAttachBrInfoByTille:_selectAttachBrInfoByTille,
		selectBusTrench:_selectBusTrench,
		coreCompanyOpName:_coreCompanyOpName,
	};
}(window, jQuery);
function finForm_input(url){//新增弹框
	top.addFlag = false;
	top.createShowDialog(url,"新增核心企业",'90','90',function(){
		if(top.addFlag){
			 updateTableData();//重新加载列表数据
   		}
	});
}
//删除
function deleteBanner(url){
	alert(top.getMessage("CONFIRM_DELETE"),2,function(){
	 	$.ajax({
 		url:url,
 		dataType:'json',
 		type:'post',
 		success : function(data){
 			if (data.flag == "success") {
				window.top.alert(data.msg, 1);
				updateTableData();//重新加载列表数据
			} else {
				window.top.alert(data.msg, 0);
			}
 		}
 	})
 });
	 
}
//新增页面js
function myInsertAjax(dom){//新增方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
						top.addFlag=true;
						$(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
				} else {
					window.top.alert(data.msg, 0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
}
$(".cancel").bind("click", function(event){
	$(top.window.document).find("#showDialog").remove();
});

function myUpdateAjax(dom){//新增方法
	var flag = submitJsMethod($(dom).get(0), '');
	if(flag){
		var url = $(dom).attr("action");
		var dataParam = JSON.stringify($(dom).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if (data.flag == "success") {
						top.updateFlag=true;
						top.htmlStr=data.htmlStr;
						top.cusInfo=data.cusInfo;//更新之后要更新视角页面
						window.top.alert(data.msg, 1);
						$(top.window.document).find("#showDialog .close").click();//点击弹出框的“X”，触发list页面的回调函数,刷新List页面
				} else {
					alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			},
			error : function() {
				LoadingAnimate.stop(); 
				alert(top.getMessage("ERROR_REQUEST_URL", url));
			}
		});
	}
}
//调视角详情页面
function getViewCommon(url){
	window.location.href=url;
}
//处理客户类型问题,以后逗号分隔，可以传入多个客户类型
function dealWithCusType(baseTypes){
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
				}
			}
		}
	})
	$('[name=cusType]').popupSelection({
		searchOn: true, //启用搜索
		inline: true, //下拉模式
		multiple: false, //单选
		changeCallback:chooseCusType
	});
}
function chooseCusType(){
	//$("[name=cusType]").change();
}
$("input[name='accountNumber']").on('keyup input',function(){
	var  accountNo =$(this).val();
	var reg=/^-?[0-9,\s]*$/;//此写法允许首字符为0
	if(!reg.test(accountNo)){
		$(this).val("");
	}else{
		 if(/\S{5}/.test(accountNo)){
	        this.value = accountNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ");
		}
	}		       	
}); 
function getBankByCardNumber(obj){
	var identifyNumber = obj.value.trim().replace(/\s/g,"");
	$.ajax({
		url:webPath + "/bankIdentify/getByIdAjax",
		data:{identifyNumber:identifyNumber},
		type:'post',
		dataType:'json',
		success:function(data){
			if(data.flag == "success"){
				BASE.removePlaceholder($("input[name=bank]"));
				$("input[name=bankName]").val(data.bankName);
			}else{
				$("input[name=bankName]").val("");
			}	
		},error:function(){
			window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
		}
	});
}