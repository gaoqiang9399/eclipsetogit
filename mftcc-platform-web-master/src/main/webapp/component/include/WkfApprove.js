;
var WkfApprove=function(window,$){
	var _opinionTypeChange=function(){
		var opinionType = $("[name=opinionType]").val();

		var $sendBackNode = $("[name=rollbackName]");// 发回岗位(发回重审)
		$sendBackNode.parents("td").hide();// 字段td
		$sendBackNode.parents("td").prev("td").hide();// 标签td
        var $nextOpNo,$isChairman;
		if (opinionType == "4") {// 发回重审
			if(befNodesjsonArray!=""){
				$sendBackNode.parents("td").show();// 字段td
				$sendBackNode.parents("td").prev("td").show();// 标签td
				if($("[name=nextOpNo]").length > 0){//若有选择下一执行人操作，退回时去掉此选项
					$nextOpNo=$("[name=nextOpNo]");
					$nextOpNo.removeAttr("mustinput");
					$nextOpNo.parents("tr").hide();
				}
				if($("[name=isChairman]").length > 0){//若有选择是否提交董事长操作，退回时去掉此选项
					$isChairman=$("[name=isChairman]");
					$isChairman.removeAttr("mustinput");
					$isChairman.parents("tr").hide();
				}
			}
		} else{
			if($("[name=nextOpNo]").length > 0){
				$nextOpNo=$("[name=nextOpNo]");
				$nextOpNo.attr("mustinput","1");
				$nextOpNo.parents("tr").show();
			}
			if($("[name=isChairman]").length > 0){
				$isChairman=$("[name=isChairman]");
				$isChairman.attr("mustinput","1");
				$isChairman.parents("tr").show();
			}
		}
		
		//展示拒单原因
		if(typeof(approveRefuseType)!="undefined" && approveRefuseType == '1'){
			_approveRefuseChange();
		}
		//根据表单判断是否展示拒单原因
		var refuseTypeClass = $("input[name=refuseType]").parents("td").attr("class");
		if(typeof(refuseTypeClass)!="undefined" && refuseTypeClass.indexOf("judan") != -1){
			_approveRefuseChange();
		}
		//意见类型是否必填
		var approvalRemarkClass = $("textarea[name=approvalRemark]").parents("td").attr("class");
       //业务意见类型是否必填
	  var approvalOpinionClass = $("textarea[name=approvalOpinion]").parents("td").attr("class");

	};
	
	var _approveRefuseChange=function(){
		var opinionType = $("[name=opinionType]").val();
        var $nextOpNo;
		if(opinionType == "2"||opinionType == "6"||opinionType == "4"){//否决、退回、发回重审
			$nextOpNo=$("[name=refuseType]");
			$nextOpNo.attr("mustinput","1");
			$("input[name='popsrefuseType']").parent('.input-group').find('.pops-value.show-text').remove();
			$nextOpNo.parents("tr").show();
			//拒单原因
			$("input[name=refuseType]").popupSelection({				
				ajaxUrl : webPath+"/mfArmourRefuseConfig/getListAllAjax?opinionType=" + opinionType,
				searchOn : true,//启用搜索
				multiple : false,//单选
				valueClass : "show-text",//自定义显示值样式
				ztree : true,
				ztreeSetting : setting,
				title : "拒单原因",
				handle : BASE.getIconInTd($("input[name=refuseType]")),
				changeCallback : function (elem) {
					var node = elem.data("treeNode");
					var parNode =  node.getParentNode();
					var address=node.name;
					while(parNode) {
						address=parNode.name+ "-" + address;
						parNode=parNode.getParentNode();
					}
					var $careaProviceObj = $("input[name=refuseType]").parent(".input-group").find(".pops-label-alt");
					$careaProviceObj.removeClass("pops-label-alt");
					$careaProviceObj.html(address);
					$("textarea[name=approvalOpinion]").val(address);
				}
			});
		}else{
			$nextOpNo=$("[name=refuseType]");
			$nextOpNo.attr("mustinput","0");
			$nextOpNo.parents("tr").hide();
			$("textarea[name=approvalOpinion]").val('');
		}	
	};
	
	//退回岗位下拉框赋值
	var _setRollbackOption=function(befNodes){
		var optionStr = "";
		$.each(befNodes,function(i,node){
			if (node.name == 'supplement_data' || node.name == 'pact_supplement_data' || node.name == 'finc_supplement_data') {
				return;// 发回重审，不再选择补充资料
			}
			optionStr +='<option value="'+node.name+'">'+node.desc+'</option>';
		});
		$("[name=rollbackName]").html(optionStr);
	};
	
	//选择下一步操作员页面
	var _getNextUserPage = function(obj){
		dialog({
			id:"nextUserDialog",
			title:"选择操作员",
			url:webPath+ '/mfBusPact/getUserForFristTaskByPage?processId='+processId,
			width:1000,
			height:500,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					var nextUser = this.returnValue;
					$(obj).val(nextUser.displayName);
					/**
					 * 审批功能，都会审批人员信息字段 approvePartNo、approvePartName，
					 * 所以使用approvePartNo、approvePartName，保存并处理指派的审批人员
					 */
					$("[name=approvePartNo]").val(nextUser.wkfUserName);
					/**
					 * 兼容以前获得流程第一个节点上审批人员公共方法，选择审批人员后回调是给firstApprovalUser赋值的
					 * 且开启流程时提交也是使用firstApprovalUser指定的审批人员。
					 * 以前已有的在提交审批时选择审批人员的地方不需要修改代码，只改表单事件就行
					 */
					$("[name=firstApprovalUser]").val(nextUser.wkfUserName);
				}else{
					$(obj).val("");
				}
			}
		}).showModal();
	};
	/**
	 * 根据流程标识、审批节点名称获得选择下一步操作员页面
	 * processId 流程标识，即流程编号
	 * nodeName 审批流程节点名称
	 * multipleFlag 多选标志
	 * ifFilterFlag 是否过滤当前操作员标志 
	 * committeeFlag 是否需要根据贷审会委员成员来确定数据源
	 * pasSubType 业务小类
	 */
	var _getNextUserByNodePage = function(obj,nodeName,multipleFlag,ifFilterFlag,committeeFlag,pasSubType){
		if(multipleFlag!="1"){//多选标志，不配置默认单选
			multipleFlag="0";
		}
		if(ifFilterFlag!="0"){//不配置默认过滤
			ifFilterFlag="1";
		}
		var committeeMember="";
		if(committeeFlag=="1"){//不配置默认不受其他字段数据源的
			committeeMember = $("input[name=committeeMember]").val();
		}else{
			committeeFlag="0";
		}
		var creditAppId = "";//授信申请号
		if("riskApproval" == nodeName){//授信风险组长，指定审批人员
            creditAppId = $(obj).parents("form").find("input[name='creditAppId']").val();
		}
        if("1531187706206" == nodeName){//授信合同审批，指定审批人员
            creditAppId = $(obj).parents("form").find("input[name='creditAppId']").val();
        }
		dialog({
			id:"nextUserDialog",
			title:"选择审批人员",
			url:webPath+ '/wkfApprovalUser/getUserForTaskList?processId='+processId+'&nodeName='+nodeName+'&multipleFlag='+multipleFlag+'&ifFilterFlag='+ifFilterFlag+'&committeeMember='+committeeMember+'&committeeFlag='+committeeFlag+'&creditAppId='+creditAppId+'&pasSubType='+pasSubType,
			width:1000,
			height:500,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					var nextUser = this.returnValue;
					$(obj).val(nextUser.displayName);
					/**
					 * 审批功能，都会审批人员信息字段 approvePartNo、approvePartName，
					 * 所以使用approvePartNo、approvePartName，保存并处理指派的审批人员
					 */
					$("[name=approvePartNo]").val(nextUser.wkfUserName);
					/**
					 * 兼容以前获得流程第一个节点上审批人员公共方法，选择审批人员后回调是给firstApprovalUser赋值的
					 * 且开启流程时提交也是使用firstApprovalUser指定的审批人员。
					 * 以前已有的在提交审批时选择审批人员的地方不需要修改代码，只改表单事件就行
					 */
					$("[name=firstApprovalUser]").val(nextUser.wkfUserName);
					if(multipleFlag=="1"){//如果是多选
						if(obj.name=="committeeMemberName"){//贷审会委员
							$("[name=committeeMember]").val(nextUser.wkfUserName);
						}else if(obj.name=="agreeMemberName"){//同意人员
							$("[name=agreeMember]").val(nextUser.wkfUserName);
						}else if(obj.name=="disagreeMemberName"){//不同意人员
							$("[name=disagreeMember]").val(nextUser.wkfUserName);
						}else if(obj.name=="waiverMemberName"){//弃权人员
							$("[name=waiverMember]").val(nextUser.wkfUserName);
						}
					}
				}else{
					$(obj).val("");
				}
			}
		}).showModal();
	};
	
	/**
	 * 根据当前任务id获取该节点上配置的审批人员
	 * multipleFlag 多选标志 1-多选 0-单选
	 * ifFilterFlag 是否过滤当前操作员标志 0-不过滤 1-过滤
	 * committeeFlag 是否需要根据贷审会委员成员来确定数据源 0-不需要 1-需要
	 */
	var _getApprovalUserByTaskId = function(obj,multipleFlag,ifFilterFlag,committeeFlag){
		var committeeMember="";
		if(committeeFlag=="1"){
			committeeMember = $("input[name=committeeMember]").val();
		}
		dialog({
			id:"nextUserDialog",
			title:"选择审批人员",
			url:webPath+ '/wkfApprovalUser/getUserForTaskList?taskId='+$("#taskId").val()+'&multipleFlag='+multipleFlag+'&ifFilterFlag='+ifFilterFlag+'&committeeMember='+committeeMember+'&committeeFlag='+committeeFlag,
			width:1000,
			height:500,
			backdropOpacity:0,
			onshow:function(){
				this.returnValue = null;
			},onclose:function(){
				if(this.returnValue){
					var nextUser = this.returnValue;
					$(obj).val(nextUser.displayName);
					/**
					 * 审批功能，都会审批人员信息字段 approvePartNo、approvePartName，
					 * 所以使用approvePartNo、approvePartName，保存并处理指派的审批人员
					 */
					$("[name=approvePartNo]").val(nextUser.wkfUserName);
					/**
					 * 兼容以前获得流程第一个节点上审批人员公共方法，选择审批人员后回调是给firstApprovalUser赋值的
					 * 且开启流程时提交也是使用firstApprovalUser指定的审批人员。
					 * 以前已有的在提交审批时选择审批人员的地方不需要修改代码，只改表单事件就行
					 */
					$("[name=firstApprovalUser]").val(nextUser.wkfUserName);
					if(multipleFlag=="1"){//如果是多选
						if(obj.name=="committeeMemberName"){//贷审会委员
							$("[name=committeeMember]").val(nextUser.wkfUserName);
						}else if(obj.name=="agreeMemberName"){//同意人员
							$("[name=agreeMember]").val(nextUser.wkfUserName);
						}else if(obj.name=="disagreeMemberName"){//不同意人员
							$("[name=disagreeMember]").val(nextUser.wkfUserName);
						}else if(obj.name=="waiverMemberName"){//弃权人员
							$("[name=waiverMember]").val(nextUser.wkfUserName);
						}
					}
				}else{
					$(obj).val("");
				}
			}
		}).showModal();
	};

    /**
	 * levFlag 层级（数据在表中按即层级存储的）
	 * multiFlag 该字段是单选还是多选 （ false--单选 true-- 多选）
	 * childLev 子级联动 true--有联动 false --没有联动
     * @private
     */
    var _getOpinionReason = function(obj,levFlag,multiFlag,childLev){
    	var opinionType = $("[name=opinionType]").val();
		$.ajax({
			url:webPath+"/mfArmourRefuseConfig/getListByTypeAjax?levFlag="+levFlag+"&type="+opinionType,
			success : function(data) {
                $(obj).popupSelection({
                    searchOn:true,//启用搜索
                    inline:true,//下拉模式
                    multiple:multiFlag,//单选
                    items:data.items,
                    changeCallback : function (elem) {
                    	if(childLev){//如果有子级联动
                    		if(opinionType=="2"){//否决
                    			if($(".subRefuse [name=popssubOpinionReason]").length>0){
                    				var opinionReason = $(obj).parents(".input-group").find("input[name=opinionReason]").val();
                                    _getSubOpinionReasonForChange(".subRefuse [name=popssubOpinionReason]",opinionReason);
                                }
							}

						}
                    }
                });
                $(obj).parent(".input-group").find(".pops-value").click();
			},error : function() {

			}
		});


	};
   var  _getSubOpinionReason = function(obj,multiFlag){
   	   var opinionReason  = $(obj).parents("tr").prev("tr").find("[name=opinionReason]").val();
   	   if(opinionReason!=""){
           var opinionType = $("[name=opinionType]").val();
           $.ajax({
               url:webPath+"/mfArmourRefuseConfig/getListByTypeAjax?uplev="+encodeURI(opinionReason)+"&type="+opinionType,
               success : function(data) {
                   $(obj).popupSelection({
                       searchOn:true,//启用搜索
                       inline:true,//下拉模式
                       multiple:multiFlag,//单选
                       items:data.items,
                       changeCallback : function (elem) {
                       }
                   });
                   $(obj).parent(".input-group").find(".pops-value").click();
               },error : function() {

               }
           });
	   }else {
   	   	alert("请先选择上一级理由",0);
	   }
   };

   var _getSubOpinionReasonForChange= function(obj,opinionReason){
       var opinionType = $("[name=opinionType]").val();
       $.ajax({
           url:webPath+"/mfArmourRefuseConfig/getListByTypeAjax?uplev="+encodeURI(opinionReason)+"&type="+opinionType,
           success : function(data) {
               $(obj).popupSelection("updateItems",data.items);
           },error : function() {

           }
       });
	};

	return{
		opinionTypeChange:_opinionTypeChange,
		setRollbackOption:_setRollbackOption,
		getNextUserPage:_getNextUserPage,
		getNextUserByNodePage:_getNextUserByNodePage,
		approveRefuseChange:_approveRefuseChange,
		getApprovalUserByTaskId:_getApprovalUserByTaskId,
		getOpinionReason:_getOpinionReason,
        getSubOpinionReason:_getSubOpinionReason,
	}
}(window,jQuery)