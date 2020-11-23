;
var ExamineApplySave=function(window,$){
	var _examApplySaveAjax=function(obj){
        var $obj = $(obj);
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            var ajaxUrl = $(obj).attr("action");
            var updateFlag = false;
            if(ajaxUrl===undefined||ajaxUrl==null||ajaxUrl==""){
                ajaxUrl = url;
                updateFlag = true;
            }
            var dataParam = JSON.stringify($(obj).serializeArray());
            //判断页面是否显示检查卡
			var checkCardFlag;
			if($("li[name=dx]").length==0){
                checkCardFlag = 'hide';//页面不显示检查卡
			}else{
                checkCardFlag = 'show';//页面显示检查卡
			}
            LoadingAnimate.start();

			var dataParamJson = JSON.parse(dataParam);
            var pasMinNoStr = { "name": "pasMinNo", "value": "305" };
            dataParamJson.push(pasMinNoStr);
            dataParam = JSON.stringify(dataParamJson);
            jQuery.ajax({
                url:ajaxUrl,
                data:{ajaxData:dataParam,checkCardFlag:checkCardFlag,templateType:templateType},
                type:"POST",
                dataType:"json",
                beforeSend:function(){
                },success:function(data){
                    LoadingAnimate.stop();
                    if(data.flag == "success"){
                        alert(data.msg,1);
                        $obj.find(".from_btn").remove();
                        $obj.parents(".li_content").attr("data-flag","success");
                        $obj.parents(".content_ul").data("entityData",data.entityData);
                        $(".showprogress ul li[name=chosefin]").addClass("success");
                        $obj.removeAttr("action");
                        //第一个选择模型的表单变成不可编辑
                        $("select[name='templateId']").attr("disabled","");
                        $("select[name='examineType']").attr("disabled","");
                        $("input[name='manageOpName1']").attr("disabled","");
                        $("input[name='manageOpName2']").attr("disabled","");
                        dataMap=data;
                        templateId=data.entityData.templateId;
                        examHisId=data.entityData.examHisId;
                        resultFormId=data.baseForm;
                        $("#chosefinButton").remove();//去掉操作按钮，避免重复操作
                        $("#chosefinTaskButton").remove();//去掉操作按钮，避免重复操作
						if($("li[name=dx]").length==0){
							$(".showprogress ul").find("li[name=evalapp]").click();
						}else{
							if(data.indexFlag=="1"){
								$(".showprogress ul li[name=dx]").show();
								_refreshGradeCard(data.examCardListData);
                                ExamineApply.initThead("",ExamineApply.showData);
                                ExamCardInitData.initData(data.listData);
                                $(".showprogress ul").find("li[name=dx]").click();
							}else{
								//如果查询不到评分卡信息，则直接跳转到检查结论页面
                                $(".showprogress ul li[name=dx]").hide();
                                $(".showprogress ul").find("li[name=evalapp]").click();
							}
						}

                  //      $(".showprogress ul").find("li[name=evalapp]").click();//中牟银行去掉检查卡后无法跳转下一进度
                        /*if(data.indexFlag=="1"){
                            $(".showprogress ul li[name=dx]").show();
                            _refreshGradeCard(data.examCardListData);
                            ExamineApply.initThead("",ExamineApply.showData);
                            ExamCardInitData.initData(data.listData);
                            $(".showprogress ul").find("li[name=dx]").click();
                        }else{
                            $(".showprogress ul li[name=dx]").addClass("success");
                            $(".showprogress ul li[name=dx]").hide();
                            $(".showprogress ul").find("li[name=evalapp]").click();
                        }*/
                        //初始化检查要件
                        initDocNodes();
                        //初始化文档
                        templateIncludePage.init();
                        //ExamineApply.template_init();
                        /*$("div[id=gradeCard] div[id=moreBar]").remove();
                        $("div[id=gradeCard]")[0].hasMoreBar=false;
                        $("div[id=evalapp] div[id=moreBar]").remove();
                        $("div[id=evalapp]")[0].hasMoreBar=false;
                        $("div[id=chosefin] div[id=moreBar]").remove();
                        $("div[id=chosefin]")[0].hasMoreBar=false;*/
                        myCustomScrollbarForForm({
                            obj:"#evalapp",
                            advanced : {
                                updateOnContentResize: true
                            },
                            updateImmediate:true
                        });
                    }else if(data.flag=="error"){
                        alert(data.msg,0);
                    }
                },error:function(data){
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION"," "),0);
                }
            });
        }
	};
	var _refreshGradeCard=function(examCardListData){
		var StrHtml="";
		$.each(examCardListData,function(i,obj){
			var divName="";
			var dataTarget="";
			var examineCardName=obj.examineCardName;
			var tableHtml ='<table class="ls_list_a" style="width: 100%;"><thead></thead><tbody class="level1"></tbody></table>';
			divName="dx"+obj.examineCardId;
			dataTarget="dxDetailInfo"+obj.examineCardId;
			tableHtml='<form id="evalDx'+obj.examineCardId+'">'+tableHtml+'</form>';
			StrHtml=StrHtml+'<div class="row clearfix"><div class="col-xs-12 column">'+
			'<div name="'+divName+'" class="panel panel-default li_content_type">'+
			'<div class="panel-heading"><h4 class="panel-title"><span class="span-title">'+examineCardName+'</span>'+
			'<button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#'+dataTarget+'" data-parent="#accordion" style="height: auto;">'+
			'<i class="i i-close-up"></i><i class="i i-open-down"></i></button></h4></div>'+
			'<div id="'+dataTarget+'" class="li_content panel-collapse collapse in">'+tableHtml+
			"</div></div></div></div>";
		})
		$("#gradeCard-content").html(StrHtml);

        $("div[id=gradeCard] div[id=moreBar]").remove();
        $("div[id=gradeCard]")[0].hasMoreBar=false;
        $("div[id=evalapp] div[id=moreBar]").remove();
        $("div[id=evalapp]")[0].hasMoreBar=false;
        $("div[id=chosefin] div[id=moreBar]").remove();
        $("div[id=chosefin]")[0].hasMoreBar=false;
		myCustomScrollbarForForm({
			obj:"#gradeCard",
			advanced : {
                updateOnContentResize: true
            },
            updateImmediate:true
		});
	};
	//保存检查卡评分信息
	var _examCardSave=function(){
		var dataParam="";
		$("form[id^=evalDx]").each(function(i,obj){
			var gradeCardId=$(obj).attr("id");
			dataParam=dataParam+"@"+gradeCardId+"&"+JSON.stringify($(obj).serializeJSON());
		});
		LoadingAnimate.start();
		jQuery.ajax({
			url:webPath+"/mfExamineHis/examCardSaveAjax",
			data:{ajaxData:dataParam,examHisId:examHisId,templateId:templateId},
			type:"POST",
			dataType:"json",
			async:false,
			beforeSend:function(){
			},success:function(data){
				if(data.flag == "success"){
					$(".showprogress").find("li[name=dx]").addClass("success");
					LoadingAnimate.stop();
					$(".showprogress ul").find("li[name=evalapp]").click();
				}else if(data.flag=="error"){
					LoadingAnimate.stop();
					alert(data.msg,0);
				}
			},error:function(data){
				LoadingAnimate.stop();
				alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
		var check = $(".selected").attr("name");
		if(check == 'evalapp'){

            $("div[id=gradeCard] div[id=moreBar]").remove();
            $("div[id=gradeCard]")[0].hasMoreBar=false;
            $("div[id=evalapp] div[id=moreBar]").remove();
            $("div[id=evalapp]")[0].hasMoreBar=false;
            $("div[id=chosefin] div[id=moreBar]").remove();
            $("div[id=chosefin]")[0].hasMoreBar=false;
            myCustomScrollbarForForm({
                obj:"#evalapp",
                advanced : {
                    updateOnContentResize: true
                },
                updateImmediate:true
            });
		}
	};
	//检查提交审批
	var _examApplySubmit=function(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			alert(top.getMessage("CONFIRM_OPERATION","贷后检查提交"),2,function(){
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam,formId:resultFormId},
					type:"POST",
					dataType:"json",
					beforeSend:function(){
					},success:function(data){
						LoadingAnimate.stop();
						if(data.flag == "success"){
							_submitAjax();
						}
					},error:function(data){
						LoadingAnimate.stop();
						window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			});
		}
	};
	var _submitAjax=function(){
		LoadingAnimate.start();
		if (typeof entrance == "undefined") {
			entrance = '';
		}
		jQuery.ajax({
			url:webPath+"/mfExamineHis/submitAjax",
			data:{examHisId:examHisId,entrance: entrance,firstApprovalUser:firstApprovalUser},
			type:"POST",
			dataType:"json",
			beforeSend:function(){
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					//var url=webPath+"/sysTaskinfo/getListPage?pasMaxNo=3";
					//$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
					// myclose_task();
                    if(entrance!='loanAfter'&&entrance==''){
                        window.top.alert(data.msg,3,function(){
							var url=webPath+"/sysTaskInfo/getListPage?pasMaxNo=3";
							$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							setTimeout(function() {
								$(top.window.document).find("#taskShowDialog").remove();
							}, 100);
                        });
                    }else{
                        window.top.alert(data.msg, 3);
                        myclose_click();
					}
				}else{
					window.top.alert(data.msg, 0);
				}
			},error:function(data){
				LoadingAnimate.stop();
				window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
			}
		});
	};
	//检查是否存在正在进行的贷后检查
	var _checkExistExamining=function(){

	};

    /**
     *选择人员后同时指定下一个节点审批人员
     * @param obj 当前input框  this
     * @param type 类型  1：操作员，2共同借款人,3:渠道商
     * @param hide 存放真实编号的隐藏域name
     */
	var _chooseUserToNext = function(obj, type, hide){
        var element = $(obj).attr('name');
        var formId = $(obj).parents('form').find('input[name=formId]').val();
        $(obj).popupList({
            searchOn: true, //启用搜索
            multiple: false, //false单选，true多选，默认多选
            ajaxUrl : webPath+"/mfUserPermission/getOpDataSourceAjax?formId="+formId+"&element=" + element + "&ifFilterFlag=0",// 请求数据URL
            valueElem:"input[name="+hide+"]",//真实值选择器
            title: "选择人员",//标题
            changeCallback:function(elem){//回调方法
                BASE.removePlaceholder($("input[name="+element+"]"));
                firstApprovalUser = $("input[name="+hide+"]").val();
            },
            tablehead:{//列表显示列配置
                "opName":"人员名称",
                "opNo":"人员编号"
            },
            returnData:{//返回值配置
                disName:"opName",//显示值
                value:"opNo"//真实值
            }
        });
        $('input[name='+element+']').next().click();
	};
	

     //改变是否变更联系方式
	 var _changeIsCusTel = function() {
		var isCusTel = $("select[name=isCusTel]").val();
		if (isCusTel == "1") {
			$("input[name=cusTelNew]").removeClass('hidden').parents('td')
					.prev('td').children('label').removeClass('hidden');
			$("input[name=cusTelNew]").attr("mustinput","1");
		} else {
			$("input[name=cusTelNew]").addClass('hidden').parents('td').prev(
					'td').children('label').addClass('hidden');
			$("input[name=cusTelNew]").attr("mustinput","0");
		}

	}
    //改变是否变更客户地址
	var _changeIsCommAddress = function() {
		var isCommAddress = $("select[name=isCommAddress]").val();
		if (isCommAddress == "1") {
			$("input[name=commAddressNew]").removeClass('hidden').parents('td')
					.prev('td').children('label').removeClass('hidden');
			$("input[name=commAddressNew]").attr("mustinput","1");
		} else {
			$("input[name=commAddressNew]").addClass('hidden').parents('td')
					.prev('td').children('label').addClass('hidden');
			$("input[name=commAddressNew]").attr("mustinput","0");
		}

	}

    //检查提交
    var _examUpdate=function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            alert(top.getMessage("CONFIRM_OPERATION","实地核查登记提交"),2,function(){
                var url = $(obj).attr("action");
                var dataParam = JSON.stringify($(obj).serializeArray());
                LoadingAnimate.start();
                jQuery.ajax({
                    url:url,
                    data:{ajaxData:dataParam,formId:resultFormId},
                    type:"POST",
                    dataType:"json",
                    async:false,
                    beforeSend:function(){
                    },success:function(data){
                        LoadingAnimate.stop();
                        if(data.flag == "success"){
                            window.top.alert(data.msg, 3);
                            top.flag = true;
                            myclose_click();
                            //updateTableData();//重新加载列表数据
                        } else {
                            alert(data.msg, 0);
                        }
                    },error:function(data){
                        LoadingAnimate.stop();
                        window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
                    }
                });
            });
        }
    };

    //暂存，不提交流程，生成文档
    var _temporaryUpdate=function(obj){
        var flag = submitJsMethod($(obj).get(0), '');
        if(flag){
            alert(top.getMessage("CONFIRM_OPERATION","保存生成文档"),2,function(){
                var url = $(obj).attr("action");
                var dataParam = JSON.stringify($(obj).serializeArray());
                LoadingAnimate.start();
                jQuery.ajax({
                    url:url,
                    data:{ajaxData:dataParam,formId:resultFormId},
                    type:"POST",
                    dataType:"json",
                    beforeSend:function(){
                    },success:function(data){
                        LoadingAnimate.stop();
                        if(data.flag == "success"){
                            window.top.alert(data.msg, 3);
                        }
                    },error:function(data){
                        LoadingAnimate.stop();
                        window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
                    }
                });
            });
        }
    };

	return{
		examApplySaveAjax:_examApplySaveAjax,
		examCardSave:_examCardSave,
		examApplySubmit:_examApplySubmit,
        chooseUserToNext:_chooseUserToNext,
        changeIsCusTel:_changeIsCusTel,
        changeIsCommAddress:_changeIsCommAddress,
        examUpdate:_examUpdate,
        temporaryUpdate:_temporaryUpdate
	};
}(window,jQuery);