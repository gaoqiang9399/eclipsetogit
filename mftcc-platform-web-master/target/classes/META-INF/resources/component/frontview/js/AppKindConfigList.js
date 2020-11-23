;
var AppKindConfigList = function(){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".list-item",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	var _openFormActiveSetting = function(obj,kindNo,fieldName,unit,initValue,labelName){
		var $this = $(obj);
		var deploy = $this.data("deploy");//用来判断当前属性是否是已配置
//		$this.find("span").removeClass("i-bianji3");//替换图表
//		$this.find("span").addClass("i-duihao2");
//		top.appformSaveFlag = false;
		var url = path+"/mfFrontAppform/getMfFrontAppformActiveSetting?"+encodeURI("kindNo="+kindNo+"&fieldName="+fieldName+"&fieldUnit="+unit+"&defaultVal="+initValue+"&fieldLabel="+labelName);
		window.parent.openBigForm(url,labelName+"要素设置", function(){
//			$this.find("span").removeClass("i-duihao2");
//			$this.find("span").addClass("i-bianji3");//替换图表
		/*	if(deploy == '0' && top.appformSaveFlag){//未配置过并且点击保存操作
				var appformdata = top.appformDataParam;
				//从下面移到上面
				_deleteItem(obj);
				_addItem(obj,appformdata);
			}else if(deploy == '1' && top.appformSaveFlag){//已配置过的更新html
				var appformdata = top.appformDataParam;
				_updateItem(obj,appformdata);
			}*/
		},80,80);
	};
	//初始化未配置表单直接进入配置项
	var _initFormActiveSetting = function(obj,kindNoVal,fieldNameVal,unit,initValue,labelName){
		var $this = $(obj);
		var deploy = $this.data("deploy");//用来判断当前属性是否是已配置
		var url = path+"/mfFrontAppform/initformActiveSettingAjax";
		var jsonParm = {kindNo:kindNoVal,fieldName:fieldNameVal,fieldUnit:unit,defaultVal:initValue,fieldLabel:labelName};
		$.post(url,jsonParm,function(result){
			if(result.flag=="success"){
				_deleteItem(obj);
				_addItem(obj,jsonParm,"deploy");
			}else{
				  window.top.alert(top.getMessage("ERROR_INSERT"),1);
			}
		},"json");
	};
	var _deleteFormActiveSetting = function(obj,kindNoVal,fieldNameVal,unit,initValue,labelName){
		var $this = $(obj);
		var deploy = $this.data("deploy");//用来判断当前属性是否是已配置
		var url = path+"/mfFrontAppform/delformActiveSettingAjax";
		var jsonParm = {kindNo:kindNoVal,fieldName:fieldNameVal,fieldUnit:unit,defaultVal:initValue,fieldLabel:labelName};
		$.post(url,jsonParm,function(result){
			if(result.flag=="success"){
				_deleteItem(obj);
				_addItem(obj,jsonParm,"undeploy");
			}else{
				  window.top.alert(top.getMessage("ERROR_INSERT"),1);
			}
		},"json");
	};
	//从未配置中移除
	var _deleteItem = function(obj){
		$(obj).parents(".rotate-div").remove();
	};
	//添加到已配置wherePid 要添加到的容器id
	var _addItem = function(obj,jsonObj,wherePid){
		var html = _getItem(obj,jsonObj,wherePid);
		$("#"+wherePid).append(html);
	};
	var _updateItem = function(obj,jsonString){
		var html = _getItem(obj,jsonString);
		$(obj).replaceWith(html);
	};
	//根据参数获取基础html
	var _getItem = function(obj,jsonObj,wherePid){
		var spanIcon = '';
		var buttonHtml = '';
		if(wherePid == "undeploy"){//添加到未配置容器中
			buttonHtml = 
				'<button class="opt-btn i i-gouxuan"  data-deploy="0" onclick="AppKindConfigList.initFormActiveSetting(this,\''+jsonObj.kindNo+'\',\''+jsonObj.fieldName+'\',\''+jsonObj.fieldUnit+'\',\''+jsonObj.defaultVal+'\',\''+jsonObj.fieldLabel+'\')"></button>';
			
		}else if(wherePid == "deploy"){//添加到已配置容器中
			spanIcon ='<span class="rotate-tubiao i i-duihao2"></span>';
			buttonHtml =
				'<button class="opt-btn i i-bianji3"  data-deploy="1" onclick="AppKindConfigList.openFormActiveSetting(this,\''+jsonObj.kindNo+'\',\''+jsonObj.fieldName+'\',\''+jsonObj.fieldUnit+'\',\''+jsonObj.defaultVal+'\',\''+jsonObj.fieldLabel+'\')"></button>'+
				'<button class="opt-btn i i-x42" data-deploy="1" onclick="AppKindConfigList.deleteFormActiveSetting(this,\''+jsonObj.kindNo+'\',\''+jsonObj.fieldName+'\',\''+jsonObj.fieldUnit+'\',\''+jsonObj.defaultVal+'\',\''+jsonObj.fieldLabel+'\')"></button>';
		}else{
			return '';
		}
//		console.log(jsonObj);
		var html = '              <div class="rotate-div " >'+
		'								<div class="rotate-obj">'+
		'									<div class="rotate-des" >'+
		'										'+jsonObj.fieldLabel+
		'                                       '+spanIcon+
		'									</div>'+
		'									<div class="des-hover">'+
		'                                       '+buttonHtml+
		'									</div>'+
		'									'+
		'								</div>'+
		'							</div>';
//		var html = '<div class="rotate-div float_left"  data-deploy="1" onclick="AppKindConfigList.openFormActiveSetting(this,\''+jsonObj.kindNo+'\',\''+jsonObj.fieldName+'\',\'\',\''+jsonObj.defaultVal+'\',\''+jsonObj.fieldLabel+'\')">'+
//		'				<div class="rotate-obj">'+
//		'					<div class="rotate-des" >'+
//		'						'+jsonObj.fieldLabel+
//		'						<span class="rotate-tubiao i i-bianji3"></span>'+
//		'					</div>'+
//		'                   <div class="rotate-content" onclick="event.cancelBubble = true">'+
//		'						<p>默认值：'+jsonObj.defaultVal+'</p>'+
//		'						<p>移动端展示：'+_getString(jsonObj.mobleShow)+'</p>'+
//		'						<p>移动端使用：'+_getString(jsonObj.mobileUse)+'</p>'+
//		'						<p>P&nbsp;C&nbsp;端展示：'+_getString(jsonObj.pcShow)+'</p>'+
//		'						<p>P&nbsp;C&nbsp;端使用：'+_getString(jsonObj.pcUse)+'</p>'+
//		'					</div> '+
		'				</div>'+
		'			</div>';
//		$("#deploy").append(html);
		return html;
	};
	var _getString = function(i){
		if(i=='1'){
			return '启用';
		}else{
			return '禁用';
		}
	};
	return {
		init:_init,
		openFormActiveSetting:_openFormActiveSetting,
		initFormActiveSetting:_initFormActiveSetting,
		deleteFormActiveSetting:_deleteFormActiveSetting
	};
}(window,jQuery);
