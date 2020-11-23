;
var OaConsAppInsert = function(window, $) {
	var _init = function() {
//		$(".scroll-content").mCustomScrollbar({
//			advanced : {
//				theme : "minimal-dark",
//				updateOnContentResize : true
//			}
//		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		_bindClose();
		_bindInsertAjax("#OaConsAppInsert");
		$("select[name='consClass']").append("<option value=''>请选择低值易耗品</option>");
	};

	var _bindClose = function() {
		$(".cancel").bind("click", function(event) {
			myclose();
		});
	};

	var _bindInsertAjax = function(obj) {
		$(".insertAjax").bind("click",function(event) {
			var flag = submitJsMethod($(obj).get(0), '');
			var operateNum = parseInt($("input[name='operateNum']").val());
			var storeNum = parseInt($("input[name='storeNum']").val());
			if(operateNum>storeNum){
				flag = false;
				window.top.alert("申领数量大于库存",0);
			}
			//确认操作类型：领用，借用，为隐藏域中operateType赋值
			var operateType = $("input[name='operateType']");
			var appType = $("input[name='appType']").val();
			if(appType == "1"){
				operateType.val("2");//领用
			}else{
				operateType.val("3");//借用
			}
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj)
						.serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						operateType : operateType.val(),
						formId : "consapp0002"
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							var url = webPath+"/mfOaConsOperate/getAppListPage";
							window.top.alert(data.msg,1);
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
							myclose();
						} else {
							window.top.alert(data.msg,0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}else{
			}
		});
	};
	var _getConsClassDialog = function(consClass){
		$("#cons_content").css("display","none");
		$("input[name='className']").val(consClass.className);
		$("input[name='classNo']").val(consClass.classId);
		$("input[name='appType']").val(consClass.appType);
		var appTypeName = '';
		if(consClass.appType=='1'){
			appTypeName="领用";
			$("input[name='operateNum']").val("").parents("tr").removeAttr("style");
			$(".insertAjax").val("领用");
		}else{
			appTypeName="借用";
			$("input[name='operateNum']").val(1).parents("tr").css("display","none");
			$(".insertAjax").val("借用");
		}
		$("input[name='appTypeName']").val(appTypeName);
		_getCons("input[name='classNo']");
	};
	/**
	 * 获取所选类别下的耗品
	 */
	var _getCons = function(obj){
		_cleanForm();
		$("select[name='consClass']>:not(:first)").remove();
		var consSelect = $("select[name='consClass']");
		var classId = $(obj).val();
		if(classId != ""){
			var url = webPath+"/mfOaCons/getConsByClassAjax";
			LoadingAnimate.start();
			$.ajax({
				url : url,
				type : "post",
				data : {
					ajaxData :classId
				},
				dataType : "json",
				success : function(data) {	
					LoadingAnimate.stop();
					if(data.flag == "noCons"){
						$("select[name='consClass']").html("<option value=''>该类别下无资产</option>");
					}else{
						$.each(data.consList,function(i,cons){
							var consId = cons.consId;
							var consName = cons.consName;
							consSelect.append("<option id='"+consId+"' value='"+consId+"'>"+consName+"</option>");
							$("#"+consId).data("cons",cons);//将相应的cons对象绑定在当前option上
						});
					}
				},
				error : function() {
					LoadingAnimate.stop();
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		}
	};
	var _getAppUserDialog = function(user) {
		
	};
	/**
	 * 品名改变调用该方法
	 */
	var _initCons = function(obj) {
		$("#cons_content").css("display","none");
		//获取绑定在option上的cons对象
		var cons = $(obj).find("option:selected").data("cons");
		if(cons){
			//将表单中相应属性赋值
			$("input[name='price']").val(cons.price);
			$("input[name='storeNum']").val(cons.storeNum);
			$("input[name='unit']").val(cons.unit);
			$("input[name='consNo']").val(cons.consId);
			$("input[name='consName']").val(cons.consName);
			$("input[name='specification']").val(cons.specification);
			$("input[name='barCode']").val(cons.barCode);
			if(cons.storeNum=='0'){
				$("input[name='useState']").val("无库存");
			}else{
				$("input[name='useState']").val("正常使用");
			}
			$("#cons_content").removeAttr("style");
		}
	};
	var _cleanForm = function() {
		$("input[name='price']").val("");
		$("input[name='storeNum']").val("");
		$("input[name='unit']").val("");
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getCons : _getCons,
		initCons : _initCons,
		getConsClassDialog : _getConsClassDialog,
		getAppUserDialog : _getAppUserDialog
	};
}(window, jQuery);