;
var MfOaBecomeQualified_Insert = function(window, $) {
	var _init = function() {
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	};
	var _ajaxSave = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {
					ajaxData:dataParam
					},
				type : 'post',
				dataType : 'json',
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						myclose_click();
					} else {
						alert(data.msg, 0);
					}
				},
				error : function() {
				}
			});
		}
	};
	var _selectName = function(callback){
		var  tmpheight=$(window.document).height();
		tmpheight=tmpheight*0.80;
		dialog({
			id:"selectNameDialog",
			title:'选择职员',
			url: webPath+"/mfOaArchivesBase/getNameSelect?opSts=2",
			width:800,
			height: tmpheight,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					//返回对象的属性:opNo,opName;如果多个，使用@分隔
					if(typeof(callback)== "function"){
						callback(this.returnValue);
					}else{
					}
				}
			}
		}).showModal();
	};
	var _changeBaseInfo = function(data){
		$.ajax({
			url:webPath+"/mfOaArchivesBase/getByIdAjax",
			data:{baseId:data.baseId},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("input[name = 'name']").val(data.mfOaArchivesBase.opName);
					$("input[name = 'brNo']").val(data.mfOaArchivesBase.brNo);
					$("input[name = 'brName']").val(data.mfOaArchivesBase.brName);
					$("input[name = 'hireDate']").val(data.mfOaArchivesBase.hireDate);
					$("input[name = 'position']").val(data.mfOaArchivesBase.position);
					$("input[name = 'positionShow']").val(data.mfOaArchivesBase.positionShow);
					$("input[name = 'baseId']").val(data.mfOaArchivesBase.baseId);
				}
			},error:function(){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	var _myclose = function(){
		myclose_click();
	};
	return {
		init : _init,
		selectName:_selectName,
		changeBaseInfo:_changeBaseInfo,
		ajaxSave:_ajaxSave,
		myclose:_myclose
	};
}(window, jQuery);
