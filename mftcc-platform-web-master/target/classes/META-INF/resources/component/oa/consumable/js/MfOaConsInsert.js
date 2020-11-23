;
var OaConsInsert = function(window, $) {
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
		_bindInsertAjax("#OaConsInsert",".show_consform");
		$("input[name=classNo]").popupSelection({
			searchOn:true,//启用搜索
			inline:false,//下拉模式
			multiple:false,//多选选
			ztree:true,
			title:"选择资产类别",
			items:ajaxData,
			changeCallback : function (elem) {
				var node = elem.data("treeNode");
				var brName=node.name;
				var consCla=new Object();
				consCla.classId=node.id;
				$("[name=popsclassNo]").val(node.id);
				consCla.className=node.name;
				consCla.appType=node.appType;
				OaConsInsert.getConsClassDialog(consCla);
			},addBtn:{//添加扩展按钮
				"title":"新增",
				"fun":function(d,e){
					top.treeNodePopu="";//存储新增的节点。
					top.window.openBigForm(webPath+'/mfOaConsClass/input','新增类别',function(){
						if(top.treeNodePopu.id){
							var newNode=top.treeNodePopu;
							newNode=d.addNodes(d.getNodeByParam("id",top.treeNodePopu.pId,null),newNode);
							if(newNode.pId!=null){
								$(e).popupSelection("selectedTreeItem",newNode[0]);//默认选择
							}
							top.treeNodePopu="";
						}
					});
				}
			}
		});
	};

	var _bindClose = function() {
		$(".cancel").bind("click", function(event) {
			myclose();
		});
	};
	var _bindInsertAjax = function(obj1,obj2) {
		$(".insertAjax").bind("click",function(event) {
			var flag1 = submitJsMethod($(obj1).get(0), '');
			var flag2 = submitJsMethod($(obj2).get(0), '');
			var flag = (flag1&&flag2);
			var putNum = $("input[name='putNum']").val();
			if (flag) {
				var consNo = $("input[name='consNo").val();//根据ID是否为空判断是是否为第一次入库
				var url = '';
				if(consNo == ''){
					url = $(obj1).attr("action");
				}else{
					url = webPath+'/mfOaCons/updateAjax';
				}
				//将两个表单中数据组合传到后台
				$(obj1).find("input[name=formId]").remove();
				var consArray1 = $(obj1).serializeArray();
				var consArray2 = $(obj2).serializeArray();
				var consArray = consArray2.concat(consArray1);
				var dataParam = JSON.stringify(consArray);
				//后台根据formId传参
				var formId = $("input[name='formId']").val();
				var operateType = $("input[name='operateType']").val();
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						formId : formId,
						operateType : operateType
					},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							var url = webPath+"/mfOaCons/getListPage";
							window.top.alert(data.msg,1);
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src", url);
							myclose_click();
						} else {
							window.top.alert(data.msg,0);
						}
					},
					error : function() {
						LoadingAnimate.stop();
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}
		});
	}
	var _getConsDialog = function(consInfo){
		$("input[name=consName]").val(consInfo.consName);
		$("input[name=consNo]").val(consInfo.consNo);
		$("input[name=consName]").attr("readonly","readonly");
		//调用ajax方法，获取详细资产信息为选择后的表单进行填充
		_ajaxGetById(consInfo.consNo);
		
	};
	var _getConsClassDialog = function(consClass){
		//每改变类别，清空详情表单内容
		$("input[name='specification']").val("");
		$("input[name='barCode']").val("");
		$("input[name='price']").val("");
		$("textarea[name='remark']").val("");
		$("input[name=consName]").val("");
//		$("select[name='unit'] option:selected").removeAttr("selected");
		
		$("input[name=className]").val(consClass.className);
		$("input[name=classNo]").val(consClass.classId);
		var appType = $("input[name=appType]").val();
		//若申领类型未发生改变，则表单不做任何调整
		if(appType == consClass.appType){
			return;
		}else{
			$("input[name=appType]").val(consClass.appType);
			$("input[name=consName]").removeAttr("readonly");
			//若申领类型发生改变，则调整显示表单
			if(consClass.appType=="1"){
				$("input[name=appTypeName]").val("领用");
			}else{
				$("input[name=appTypeName]").val("借用");
				//借用资产是唯一的，无需放大镜
				$("input[name=consName]").next().remove();
			}
			$("#OaConsInsertDetil1").removeClass("show_consform");
			$("#OaConsInsertDetil2").removeClass("show_consform");
			$("#OaConsInsertDetil1")[0].style.display= "none "; 
			$("#OaConsInsertDetil2")[0].style.display= "none "; 
			if(consClass.appType =="1"){
				$("#OaConsInsertDetil2").addClass("show_consform");
				$("#OaConsInsertDetil2")[0].style.display= "block "; 
				$("input[name='formId']").val("consumable0004");
			}
			if(consClass.appType =="2"){
				$("#OaConsInsertDetil1").addClass("show_consform");
				$("#OaConsInsertDetil1")[0].style.display= "block "; 
				$("input[name='formId']").val("consumable0003");
			}
		}
	};
	
	var _ajaxGetById = function(obj) {
		if(obj != null&& obj !=''){
			$.ajax({
				url : webPath+"/mfOaCons/getConsByIdAjax",
				data :{
					consId : obj
				},
				success : function(data){
					if(data.flag == 'success'){
						var cons = data.mfOaCons;
						$("input[name='specification']").val(cons.specification).attr("readonly","readonly");
						$("select[name='unit']").find("option[value='"+cons.unit+"']").attr("selected",true);
						$("input[name='barCode']").val(cons.barCode);
						$("input[name='price']").val(cons.price);
						$("textarea[name='remark']").val(cons.remark);
					}else{
						window.top.alert(data.msg,0);
					}
				},
				error : function() {
					alert(top.getMessage("ERROR_REQUEST_URL", url));
				}
			});
		}
	};
	/**
	 * 在return方法中声明公开接口。
	 */
	return {
		init : _init,
		getConsDialog : _getConsDialog,
		getConsClassDialog : _getConsClassDialog
	};
}(window, jQuery);
function clickClassNo(){
	$(".pops-value").click();
}