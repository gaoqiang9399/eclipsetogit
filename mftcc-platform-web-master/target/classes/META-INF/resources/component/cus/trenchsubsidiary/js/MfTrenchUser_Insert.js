;
var MfTrenchUser_Insert=function(window,$){
	var _init=function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		 $("input[name=ext1]").val("1");//数据权限默认-本人

        $("input[name=cusAccount]")
            .parent(".input-group")
            .append(
                "<div class='error hide' id='cusAccount1'>登陆账号必须是6到20位字母或数字</div>");
      //角色新组件
    	$("input[name=roleNo]").popupSelection({
    			searchOn:true,//启用搜索
    			inline:true,//下拉模式
    			multiple:true,//多选选
    			items:roleArray,
                /*addBtn:{//添加扩展按钮
                    "title":"新增角色",
                    "fun":function(d,elem){
                        top.roleArray = roleArray;
                        top.window.openBigForm(webPath+'/pmsConfigure/configureNew?roleNo=&opNoType='+opNoType,'新增角色',function(){
                            if(top.roleNo!=""&&top.roleName!=""&&top.roleNo!=undefined&&top.roleName!=undefined){
                                var newNode = {"id":top.roleNo,"name":top.roleName};
                                roleArray = roleArray.concat(newNode);
                                top.roleArray = roleArray;
                                $(elem).popupSelection("addItem",newNode);
                                //新增后选择该数据
                                $(elem).popupSelection("selectedItem",newNode);
                                top.roleNo="";
                                top.roleName="";
                            }
                            top.roleArray="";
                        });
                    }
                },*/
                /*labelEdit:function(d){
                    top.window.openBigForm(webPath+'/pmsConfigure/configureNew?roleNo='+d.id+'&opNoType='+opNoType,'角色配置',function(){
                    });
                }*/
    	});
	};
    // 正则表达式
    var reg = /^[a-zA-Z0-9]{6,20}$/;
    // 验证密码是否符合正则表达式
    var _validateCusAccount = function(obj) {
        if (trim($("input[name=cusAccount]").val()) != ""&& !reg.test($("input[name=cusAccount]").val())) {
            $('#cusAccount1').removeClass("hide");
            return false;
        } else {
            $('#cusAccount1').addClass("hide");
            return true;
        }
    };

	
	var _insertAjax=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			if(_validateCusAccount(this)){
			ajaxInsertCusForm(obj);
		  }
		}
	};
	//验证用户账号是否唯一
	var _checkCusAccountUnique=function(obj,type){
		var cusAccount = $(obj).val();
		var cusTel = $(obj).val();
		var cusNickname = $(obj).val();
		if(type=='1'){//验证登录账号
			if(cusAccount==null||cusAccount==""){
				return ;
			}
		}else if(type=='2'){//验证手机号码
			if(cusTel==null||cusTel==""){
				return ;
			}
		}
		jQuery.ajax({
			url:webPath+"/mfTrenchUser/checkUserExistAjax",
			data:{cusAccount:cusAccount,cusTel:cusTel,checkType:type},
			type:"post",
			success:function(data){
				if(data.flag == "success"){
					alert(top.getMessage("EXIST_INFORMATION_EVAL", data.msg),0);
					if(type=='1'){//验证登录账号
						$("input[name=cusAccount]").val("");
					}else if(type=='2'){//验证手机号码
						$("input[name=cusTel]").val("");
					}
				}
			}
		});
	};


    //验证用户账号是否唯一，web_cus_line_reg
    var _checkAccountUnique=function(obj,type){
        var cusAccount = $(obj).val();
        var cusTel = $(obj).val();
        var cusNickname = $(obj).val();
        if(type=='1'){//验证登录账号
            if(cusAccount==null||cusAccount==""){
                return ;
            }
        }else if(type=='2'){//验证手机号码
            if(cusTel==null||cusTel==""){
                return ;
            }
        }
        jQuery.ajax({
            url:webPath+"/mfTrenchUser/checkAccountExistAjax",
            data:{cusAccount:cusAccount,cusTel:cusTel,checkType:type},
            type:"post",
            success:function(data){
                if(data.flag == "success"){
                    alert(top.getMessage("EXIST_INFORMATION_EVAL", data.msg),0);
                    if(type=='1'){//验证登录账号
                        $("input[name=cusAccount]").val("");
                    }else if(type=='2'){//验证手机号码
                        $("input[name=cusTel]").val("");
                    }
                }
            }
        });
    };

	var _resetPasswordAjax=function(obj, url){
		jQuery.ajax({
			url:webPath + url,
			data:{},
			type:"post",
			success:function(data){
				if(data.flag == "success"){
					alert(data.msg, 1);
				}else{
					alert(data.msg, 0);
				}
			}
		});
	}
	
	var _selectAreaCallBack = function(areaInfo){
		$("input[name=areaNo]").val(areaInfo.disNo);
		$("input[name=areaName]").val(areaInfo.disName);
	}
	var _getById = function(url){
		top.openBigForm(webPath+url,"查看头像", function(){
 		});	
	}
	return{
		init:_init,
		insertAjax:_insertAjax,
		checkCusAccountUnique:_checkCusAccountUnique,
		resetPasswordAjax:_resetPasswordAjax,
        validateCusAccount:_validateCusAccount,
        selectAreaCallBack:_selectAreaCallBack,
        getById:_getById,
        checkAccountUnique:_checkAccountUnique,
	}
}(window,jQuery)