;
var OaConsClassInsert = function(window, $) {
	var _init = function() {
		_bindClose();
		_bindInsertAjax("#OaConsClassInsert");
//		_hasSuperClass("[name='isChild']");//页面加载时首先判断有无父类，默认为有
	};

	var _bindClose = function() {
		$(".cancel").bind("click", function(event) {
			myclose();
		});
	};
	
	var _bindInsertAjax = function(obj) {
		$(".insertAjax").bind("click",function(event) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				//>>>yhtbug修改：提交时根据superClass的值自动判断是否有父类。
				var superClass=$("[name=superClass]").val();
				if(superClass==null||superClass==""){
					$("[name='isChild']").val("N");
				}else{
					$("[name='isChild']").val("Y");
				}
				//<<<
				var url = $(obj).attr("action");
				var isChild = $("[name='isChild']").val();
				if(isChild=='Y'){
					//移除APPType的disabled方可提交到后台
					$("select[name='appType']").removeAttr("disabled");
				}
				
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						isChild : isChild
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
//							top.treeNodePopu.id=data.bean.classId;//id
//							top.treeNodePopu.name=data.bean.className;//name
							var pId=data.bean.superClassId;
							var isParent=false;
							if(pId!=null&&pId!=""){//非空
								pId=data.bean.superClassId;
							}else{
								pId="0";
								isParent=true;
							}
//							top.treeNodePopu.ifLeaf="";
//							top.treeNodePopu.checked=true;
//							top.treeNodePopu.appType=data.bean.appType;
							top.treeNodePopu={
									"id":data.bean.classId,
									"name":data.bean.className,
									"pId":pId,
									"isParent":isParent,
									"ifLeaf":"",
//									"checked":true,
									"appType":data.bean.appType==null?"":data.bean.appType
							};
							window.top.alert(data.msg,1);
							myclose_click();
						} else {
							window.top.alert(data.msg,0);
						}
					},
					error : function() {
						loadingAnimateClose();
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}
		});
	};
//	var _hasSuperClass = function(obj){
//		var hasSuper = $(obj).val();
//		if(hasSuper == 'N'){//没有父类
//			$("input[name='superClass']").attr("mustinput","0").parents("tr").css("display","none");
//			$("select[name='appType']").removeAttr("disabled");
//		}else{
//			$("input[name='superClass']").attr("mustinput","1").parents("tr").removeAttr("style");
//		}
//	};
	var _getSuperClassDialog = function(superClass) {
		$("select[name='appType'] option").removeAttr("selected");
		$("select[name='appType']").find("option[value='"+superClass.appType+"']").attr("selected",true);
		$("input[name='superClass']").val(superClass.className);
		$("input[name='superClassId']").val(superClass.classId);
		if(null!=superClass.appType&&superClass.appType!=""){//若父类已申明申领方式，则子类需继承之，并将其置为不可更改
			$("select[name='appType']").attr("mustinput","1").val(superClass.appType).blur().attr("disabled",true).parents("td").prev().find("label").html("<font color='#FF0000'>*</font>申领类型");
		}else{
			$("select[name='appType']").removeAttr("disabled").attr("mustinput","1").parents("td").prev().find("label").html("<font color='#FF0000'>*</font>申领类型");
		}
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getSuperClassDialog : _getSuperClassDialog
//		hasSuperClass : _hasSuperClass
	};
}(window, jQuery);