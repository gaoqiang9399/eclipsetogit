;
var OaArchivesFind = function (window,$){
	var _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		if(baseId != ""){
			$("input[name='opNo']").removeAttr("onclick");
			$("input[name='opNo']").removeAttr("readonly");
		}
		_bindInsertAjax("#base_form");
	};
	var _bindInsertAjax = function(obj){
		$(".base_insertAjax").bind("click", function(event){
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				$.ajax({
					url : url,
					data : {
						ajaxData:dataParam
						},
					type : 'post',
					dataType : 'json',
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
								window.top.alert(data.msg, 1);
								top.mfOaArchivesBase = data.mfOaArchivesBase;
								top.htmlStr = data.htmlStr;
								top.baseFlag = true;
								myclose_click();
						} else {
							window.top.alert(data.msg, 0);
						}
					},
					error : function() {
						loadingAnimate.stop();
						
					}
				});
			}
		});
	};
	// 设置亲属部门
	var _setRelationBrName = function(sysOrg){
		$("input[name=relationBrName]").val(sysOrg.brName);
		$("input[name=relationBrNo]").val(sysOrg.brNo);
	};
	// 设置员工部门
	var _setSysOrgInfo = function(sysOrg){
		$("input[name=brName]").val(sysOrg.brName);
		$("input[name=brNo]").val(sysOrg.brNo);
	};
	// 根据银行卡号，获取银行
	var _getBankByCardNumber = function(obj){
			var identifyNumber = obj.value.trim().replace(/\s/g,"");
			$.ajax({
				url:webPath+"/bankIdentify/getByIdAjax",
				data:{identifyNumber:identifyNumber},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						BASE.removePlaceholder($("input[name=bank]"));
						$("input[name=bank]").val(data.bankName);
					}else{
						$("input[name=bank]").val("");
					}	
				},error:function(){
					window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		};
	var _selectSysUser = function(callback){
		var  tmpheight=$(window.document).height();
		tmpheight=tmpheight*0.80;
		dialog({
			id:"selectSysUserDialog",
			title:'选择操作员',
			url: webPath+'/mfOaArchivesBase/getSysUserSelect',
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
			url:webPath+"/sysUser/getSysUserByIdAjax",
			data:{opNo:data.opNo},
			type:'post',
			dataType:'json',
			success:function(data){
				if(data.flag == "success"){
					$("input[name = 'opName']").val(data.sysUser.opName);
					$("input[name = 'opNo']").val(data.sysUser.opNo);
					$("input[name = 'idNum']").val(data.sysUser.idNo);
					$("select[name = 'sex']").val(data.sysUser.sex);
					$("input[name = 'birthday']").val(data.sysUser.birthday);
					$("select[name = 'brNo']").val(data.sysUser.brNo);
					$("input[name = 'brName']").val(data.sysUser.brName);
					$("input[name = 'tel']").val(data.sysUser.mobile);
					$("select[name = 'position']").val(data.sysUser.job);
				}
			},error:function(){
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	return {
		init:_init,
		setRelationBrName:_setRelationBrName,
		setSysOrgInfo:_setSysOrgInfo,
		getBankByCardNumber:_getBankByCardNumber,
		selectSysUser:_selectSysUser,
		changeBaseInfo:_changeBaseInfo
	};
}(window,jQuery);
