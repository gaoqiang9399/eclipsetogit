/*2016-10-09 czk factor自定义的表单相关js*/
/*增加客户表单信息时调用的公共方法*/
window.ajaxInsertCusForm = function(obj,callback){//obj是form对象
	cusFlag=false
	var flag = submitJsMethod($(obj).get(0), '');
	if(flag){
		var url = $(obj).attr("action");
		var dataParam = JSON.stringify($(obj).serializeArray());
        var arrayUrl = new Array(); //定义一数组
        arrayUrl = url.split("/"); //字符分割
		if(arrayUrl[arrayUrl.length-1]=="updateAjax"){
            var identification = arrayUrl[arrayUrl.length-2];
            identification=identification.slice(0, 1).toUpperCase() + identification.slice(1);
            //是否走审批流程
            jQuery.ajax({
                url:webPath+"/mfCusKeyInfoFields/ifKeyField",
                type:'post',
                data :{ajaxData:dataParam,identification:identification,updateType:"1"},
                success:function(data){
                    var ifKeyField=data.ifKeyField;
                    if("1"==ifKeyField){
                        ifApprovl = data.ifApprovl
                        if (ifApprovl == "0") {
                            ifApprovlRecordInfo(data.mfCusInfoChange)
                            flag=true;
                            submitUpdateMethod(url,flag,dataParam,callback);
                        }else {
                            dataParam=data.updeteJson;
                            top.updateflag = false;
                            top.openBigForm(webPath+"/mfCusInfoChange/input?ajaxData="+encodeURIComponent(data.mfCusInfoChange),"客户信息修改申请", function(){
                                if(top.updateflag){
                                    flag=true;
                                    submitUpdateMethod(url,flag,dataParam,callback);
                                }
                            });}
                    }else{
                        submitUpdateMethod(url,flag,dataParam,callback);
                    }

                }
            });
        }else {
            submitUpdateMethod(url,flag,dataParam,callback);
        }
	}
};

//表单修改方法
var submitUpdateMethod=function (url,flag,dataParam,callback) {
    if(flag){
        jQuery.ajax({
            url:url,
            data:{ajaxData:dataParam},
            success:function(data){
                if(data.flag == "success"){
                    window.top.alert(data.msg, 1);
                    top.addFlag = true;
                    if(data.htmlStrFlag == "1"){
                        top.htmlStrFlag = true;
                        top.htmlString = data.htmlStr;
                    }
                    if( top.action == "MfBusAssureAmtAction"){
                        top.updateAssureAmtFlag = true;
                        top.basehtmlStr = data.basehtmlStr;
                    }
                    if(top.baseInfo){
                        top.updateFlag = true;
                        top.cusName = data.mfCusCustomer.cusName;
                        top.contactsName = data.mfCusCustomer.contactsName;
                        top.contactsTel = data.mfCusCustomer.contactsTel;
                        top.idNum = data.mfCusCustomer.idNum;
                        top.postalCode = data.mfCusCustomer.postalCode;
                        top.htmlStr = data.htmlStr;
                        top.htmlStrFlag = data.htmlStrFlag;
                        top.commAddress = data.mfCusCustomer.commAddress;
                        top.baseInfo = null;
                        top.cusType = data.mfCusCustomer.cusType;
                        top.cusTel = data.mfCusCustomer.cusTel;
                    }
                    /* 20170518 LCL 客户资料完整度刷新回调。 */
                    top.infIntegrity = data.infIntegrity;

                    if($(top.window.document).find(".dhccModalDialog").length>1){
                        $(top.window.document).find(".dhccModalDialog").eq($(top.window.document).find(".dhccModalDialog").length-1).find(".i-x5").click();
                    }else{
                        myclose_click();
                    }
                    if(callback&&typeof(callback)=="function"){
                        callback.call(this,data);
                    }

                }else if(data.flag == "error"){
                    window.top.alert(data.msg, 0);
                }
            }
        });
    }
}

//配置字段但不走审批
var ifApprovlRecordInfo = function(mfCusInfoChange){
    jQuery.ajax({
        url: webPath + "/mfCusInfoChange/ifApprovlRecordInfo",
        data: {ajaxData: mfCusInfoChange},
        type: "POST",
        dataType: "json",
        async: false,//关键
        success: function (data) {
            if (data.flag == "success") {//单字段修改成功后，改变信息变更记录按钮颜色
                $("#cusInfoChangeRecordQuery").removeClass("btn-lightgray");// 去掉灰色样式
                $("#cusInfoChangeRecordQuery").addClass("btn-dodgerblue");// 添加蓝色
            }
        }, error: function () {
            LoadingAnimate.stop();
        }
    });
}

//根据类型切换表单
var changeSubFormBySelect = function (obj,url){
	var objName = obj.name;
	var objValue = $(obj).val();
	var changeTypeParm = objName+"="+objValue;
	var formObj = $(obj).parents('form');
	var cusNo = $(formObj).find('input[name=cusNo]').val();
	var dataObj ={};
	dataObj[objName] = changeTypeParm;
	dataObj['cusNo'] = cusNo;
	var dataParam = JSON.stringify(dataObj);
	$.ajax({
		url:url,
		type:'post',
		data :{ajaxData:dataParam},
		dataType:'json',
		success:function(data){
			if(data.flag == "success"){
				$(formObj).find('.hidden-content').empty();
				$(formObj).find('table').empty().html(data.formData);
				$(formObj).find('table').attr('title',data.formId);
				$(formObj).find('[name='+objName+']').val(objValue);
			}else{
				alert(data.msg, 0);
			}
		},error:function(){
			alert(top.getMessage("ERROR_REQUEST_URL", url),0);
		}
	}); 
};


var FormFactor = (function($) {
	//初始化
	var  _init = function(){
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	};
	var _deleteTrAjax = function(obj, url) {
		alert(top.getMessage("CONFIRM_DELETE"), 2, function() {
			var ajaxParam = {};
			url=webPath+url;
			// 微服务框架中的ajax请求
			if (url.indexOf("Ajax") != -1 && url.indexOf("?") != -1) {// ajax提交
				var urlParams = url.split("?");
				url = urlParams[0];
				$.each(urlParams[1].split("&"), function(index, val) {
					var key = val.split("=")[0];
					var value = val.split("=")[1];
					ajaxParam[key] = value;
				});
			}
			$.post(url, ajaxParam, function(data) {
				if (data.flag == "success") {
					alert(top.getMessage("SUCCEED_OPERATION"), 1);
					var actionName = $(obj).parents(".dynamic-block").attr("name");
					var title = $(obj).parents(".dynamic-block").attr("title");
					if(actionName!="MfCusFinMainAction"){
						if ($(obj).parents("tbody").find("tr").length === 1) {
							// 列表最后一条数据了，（去后台查询是否有更多数据），更新配置和资料完整度，并返回新的资料完整度。
							var upUrl = "../mfCusCustomer/updateCusTableAndIntegrityAjax";
							var tableName = $(obj).parents(".dynamic-block").data("tablename");
							$.post(upUrl, {cusNo: ajaxParam["cusNo"],relNo: ajaxParam["relNo"], tableName: tableName}, function (data) {
								if (data.flag == "success") {
									/* 客户资料完整度刷新回调。 */
									MfCusDyForm.initCusIntegrity(data.infIntegrity);
								}
							});
						}
						if(top.moreCusInfo){
                            window.location.reload();
                        }else{
                            $.ajax({
                                url : webPath+"/mfCusCustomer/getDyFormHtmlPageAjax?action="+actionName+"&formEditFlag="+formEditFlag+"&cusNo="+cusNo,
                                success : function(data) {
                                    if (data.flag == "success") {
                                        var $div = $("div[id="+actionName+"]");
                                        $div.empty();
                                        $div.html(data.htmlStr);
                                        $div.find("table[id='tablist']").css("display","");
                                        _moreCallBack($div,cusNo,actionName,formEditFlag,title)
                                    }
                                },error : function() {
                                    alert(top.getMessage("ERROR_SERVER"),0);
                                }
                            });
                        }
					}else{
                        $(obj).parents("tr").remove();
                    }
                    $("#subjectData").empty(); //删除财报的时候将科目余额重点信息也清除-peng
				} else if (data.flag == "error") {
					alert(data.msg, 0);
				}
			});
		});
	};
	
	var _insertAjax = function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			$.ajax({
				url : url,
				data : {ajaxData:dataParam},
				success : function(data) {
					if (data.flag == "success") {
						window.top.alert(data.msg, 3);
						top.flag = true;
						myclose_click();
					} 
				},error : function() {
					alert(top.getMessage("ERROR_SERVER"),0);
				}
			});
		}
	};
    /*增加客户并继续新增表单信息时调用的公共方法*/
    window.ajaxInserAndAddCusForm = function(obj,inputUrl,callback){//obj是form对象
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            var url = $(obj).attr("action");
            var dataParam = JSON.stringify($(obj).serializeArray());
            jQuery.ajax({
                url:url,
                data:{ajaxData:dataParam},
                success:function(data){
                    if(data.flag == "success"){
                        window.top.alert(data.msg, 1);
                        top.addFlag = true;
                        if(data.htmlStrFlag == "1"){
                            top.htmlStrFlag = true;
                            top.htmlString = data.htmlStr;
                        }
                        if( top.action == "MfBusAssureAmtAction"){
                            top.updateAssureAmtFlag = true;
                            top.basehtmlStr = data.basehtmlStr;
                        }
                        if(top.baseInfo){
                            top.updateFlag = true;
                            top.cusName = data.mfCusCustomer.cusName;
                            top.contactsName = data.mfCusCustomer.contactsName;
                            top.contactsTel = data.mfCusCustomer.contactsTel;
                            top.idNum = data.mfCusCustomer.idNum;
                            top.postalCode = data.mfCusCustomer.postalCode;
                            top.htmlStr = data.htmlStr;
                            top.htmlStrFlag = data.htmlStrFlag;
                            top.commAddress = data.mfCusCustomer.commAddress;
                            top.baseInfo = null;
                            top.cusType = data.mfCusCustomer.cusType;
                            top.cusTel = data.mfCusCustomer.cusTel;
                        }
                        /* 20170518 LCL 客户资料完整度刷新回调。 */
                        top.infIntegrity = data.infIntegrity;
                        window.location.href =inputUrl;
                        if(callback&&typeof(callback)=="function"){
                            callback.call(this,data);
                        }
                    }else if(data.flag == "error"){
                        window.top.alert(data.msg, 0);
                    }
                }
            });
        }
    };

    var _moreCallBack = function ($div,cusNo,action,formEditFlag,title) {
        var $this = $div.find('tbody');
        if ($this.find('tr').length > 10 && $this.attr('id') === 'tab') {
            $this.find('tr:gt(9)').hide();
            $this.parent().parent().append('<p class="more" style="font-size: 12px;color: #418bac;padding-top: 8px;cursor: pointer;text-align: center;">更多</p>');
            $this.parent().parent().find('.more').click(function () {
                top.openBigForm(webPath + "/mfCusCustomer/getCusBlockList?cusNo=" + cusNo + "&action=" + action + "&formEditFlag=" + formEditFlag + "&title=" + title, title, function (data) {
                    top.moreCusInfo = false;
                    $.ajax({
                        url : webPath+"/mfCusCustomer/getDyFormHtmlPageAjax?action="+action+"&formEditFlag="+formEditFlag+"&cusNo="+cusNo,
                        async:false,
                        success : function(data) {
                            if (data.flag == "success") {
                                $div.empty();
                                $div.html(data.htmlStr);
                                $div.find("table[id='tablist']").css("display","");
                                if (typeof(formEditFlag) != "undefined" && formEditFlag == "query") {
                                    $(".formAdd-btn").hide();
                                    $(".editBtn").html("编辑");;
                                    //删除
                                    $(".delBtn").html("删除");
                                }
                                _moreCallBack($div,cusNo,action,formEditFlag,title);
                            }
                        },error : function() {
                            alert(top.getMessage("ERROR_SERVER"),0);
                        }
                    });
                });
            })
        }
    }
	return {
		init:_init,
		deleteTrAjax : _deleteTrAjax,
		insertAjax:_insertAjax,
        moreCallBack:_moreCallBack
	};
})(jQuery);
