;
var WkfMakePolicyMeetingViewPoint = function (window,$){
	var _init = function(){
		$(".scroll-content").mCustomScrollbar({
			advanced:{
				//滚动条根据内容实时变化
				updateOnContentResize:true
			}
		});
		//意见类型新版选择组件
		$('select[name=opinionType]').popupSelection({
			inline: true, //下拉模式
			multiple: false, //单选
			changeCallback:WkfApprove.opinionTypeChange
		});
		if(nodeNo == "reviewMeetingSecretary"){
		    if(count == "1"){
                _creditHtml($(".table tbody"),"prepend");
            }else{
                _editHtml($(".table tbody"));
            }
        }
        // else{
        //     _showHtml($(".table tbody"));
        // }
	};
	var _doSubmitAjax = function(formObj){
		var flag = submitJsMethod($(formObj).get(0), '');
		if (flag) {
			var opinionType = $("input[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
			//没有选择审批意见默认同意
			if(opinionType == undefined || opinionType == ''){
				opinionType = "1";
			}
			//审批意见类型opinionType必须传，否则影响后续commitProcess方法中的if判断
			commitProcess(webPath+"/mfMakePolicyMeeting/submitUpdateAjax?appNo="+makePolicyMeetingAppId+"&opinionType="+opinionType,formObj,'MakePolicyMeeting');
		}
	};
    //返回详情页面
    var _approvalBack=function(){
        $("#infoDiv").css("display","block");
        $("#approvalBtn").css("display","block");
        $("#approvalDiv").css("display","none");
        $("#submitBtn").css("display","none");
    };
    //进入审批页面
    var _getApprovaPage=function(){
        $("#infoDiv").css("display","none");
        $("#approvalBtn").css("display","none");
        $("#approvalDiv").css("display","block");
        $("#submitBtn").css("display","block");
    };
    //审批意见改变
    var _reviewMemberOpinionChange = function(obj){
        let reviewMemberOpinion = $(obj).val();
        if(reviewMemberOpinion == "2" || reviewMemberOpinion == "3"){
            $(obj).parents("tr").next().next().show();
            _reviewMemberReasonChange($(obj).parents("tr").next().next().find("[name='reviewMemberReason']"));
        }else{
            $(obj).parents("tr").next().next().hide();
            $(obj).parents("tr").next().next().next().hide();
		}
    };
    //复议或否决的主要理由改变
    var _reviewMemberReasonChange = function(obj){
        let reviewMemberReason = $(obj).val();
        if(reviewMemberReason == "7"){
            $(obj).parents("tr").next().show();
		}else{
            $(obj).parents("tr").next().hide();
		}
    };
    //新增一项
    var _addOne = function(obj){
        _creditHtml($(obj).parents("tr"),"after");
    };
    //删除一项
    var _subOne = function(obj){
        $(obj).parents("tr").prev().remove();
        $(obj).parents("tr").prev().remove();
        $(obj).parents("tr").prev().remove();
        $(obj).parents("tr").prev().remove();
        $(obj).parents("tr").prev().remove();
        $(obj).parents("tr").prev().remove();
        $(obj).parents("tr").prev().remove();
        $(obj).parents("tr").remove();
        $("[name='addBtnDiv']").last().parents("tr").show();
    };
    //选择审批人员
    var _selectApprovalpers = function(obj){
        let reviewMemberType = $(obj).parents("tr").prev().prev().find("[name='reviewMemberType']").val();
        if(reviewMemberType == "1"){
            bindDataSource(obj, '23', '', '选择领导班子委员');
        }else if(reviewMemberType == "2"){
            bindDataSource(obj, '24', '', '选择业务部门委员');
        }else if(reviewMemberType == "3"){
            bindDataSource(obj, '25', '', '选择职能部门委员');
        }else{
            bindDataSource(obj, '26', '', '选择业务部门替补委员');
        }
    };
    //审批人员类型改变
    var _changeReviewMemberType = function(obj){
        $(obj).parents("tr").next().next().find("[name='reviewMemberName']").val("");
        $(obj).parents("tr").next().find("[name='reviewMemberNo']").val("");
        $(obj).parents("tr").next().next().find("[name='reviewMemberName']").show();
        $(obj).parents("tr").next().next().find("[name='reviewMemberName']").next(".pops-value").remove();
    };

	var _creditHtml = function(obj,positionStr){
		let reviewHtml = '';
        //评审人员类型
        reviewHtml = reviewHtml
            + '<tr>'
            + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label "><font color="#FF0000">*</font>评审人员类型</label></td>'
            + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><select name="reviewMemberType" title="评审人员类型" class="form_select form-control " mustinput="1" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" onchange="WkfMakePolicyMeetingViewPoint.changeReviewMemberType(this);">'
            + '<option value="1">领导班子委员</option>'
            + '<option value="2">业务部门委员</option>'
            + '<option value="3">职能部门委员</option>'
            + '<option value="4">业务部门替补委员</option>'
            + '</select>'
            + ' </div></td></tr>';
        // 评审委员编号
         reviewHtml = reviewHtml
            +'<tr style="display: none;">'
            + '<td></td>'
            + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">评审委员编号</label></td>'
            + '<td class="tdvalue  right" colspan="1" rowspan="1"><div class="input-group"><input type="text" title="评审委员编号" name="reviewMemberNo" datatype="0" mustinput="" class="form-control" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div></td>'
            + '<td></td></tr>';
        // 评审委员
        reviewHtml = reviewHtml
            + '<tr>'
            + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">评审委员</label></td>'
            + '<td class="tdvalue  right" colspan="1" rowspan="1"><div class="input-group"><input type="text" title="评审委员" name="reviewMemberName" datatype="0" mustinput="" class="form-control" onblur="func_uior_valTypeImm(this);" onclick="WkfMakePolicyMeetingViewPoint.selectApprovalpers(this);" onmousedown="enterKey()" onkeydown="enterKey();"></div></td>'
            + '<td></td></td>';
        // 意见
        reviewHtml = reviewHtml
            + '<tr>'
            + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">意见</label></td>'
            + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><select name="reviewMemberOpinion" title="意见" class="form_select form-control " mustinput="1" onblur="func_uior_valTypeImm(this);" onchange="WkfMakePolicyMeetingViewPoint.reviewMemberOpinionChange(this)" onmousedown="enterKey()" onkeydown="enterKey();">'
            + '<option value="1">同意</option>'
            + '<option value="2">否决</option>'
            + '<option value="3">复议</option>'
            + '</select>'
            + ' </div></td></tr>';
        // 其他需要补充或落实的内容
        reviewHtml = reviewHtml
            + '<tr>'
            + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">其他需要补充或落实的内容</label></td>'
            + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><textarea class="form-control" name="reviewMemberOtherContent" title="其他需要补充或落实的内容" mustinput="0" maxlength="500" datatype="0" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" onfocus="showCharsInfo(this);" onkeyup="textareaInputCount(this)" onmousedown="enterKey()" onkeydown="enterKey();"></textarea></div></td>'
            + '</tr>';
        // 复议或否决的主要理由
        reviewHtml = reviewHtml
            + '<tr style="display: none;">'
            + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">复议或否决的主要理由</label></td>'
            + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><select name="reviewMemberReason" title="复议或否决的主要理由" class="form-control" mustinput="" onblur="func_uior_valTypeImm(this);" onchange="WkfMakePolicyMeetingViewPoint.reviewMemberReasonChange(this)" onmousedown="enterKey()" onkeydown="enterKey();">'
            + '<option value="1">不符合产业政策或公司业务方向</option>'
            + '<option value="2">有涉诉、负面报道或违约记录较多、有主观违约可能</option>'
            + '<option value="3">管理或履约能力差，影响企业持续经营和还款能力</option>'
            + '<option value="4">负债规模过大，偿债能力预期不足</option>'
            + '<option value="5">反担保严重不足，缺乏控制措施</option>'
            + '<option value="6">信息披露严重不足、需补充</option>'
            + '<option value="7">其他</option>'
            + '</select>'
            + '</div></td></tr>';
        // 其他理由
        reviewHtml = reviewHtml
            + '<tr style="display: none;">'
            + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">其他理由</label></td>'
            + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><textarea class="form-control" name="reviewMemberOtherReason" title="其他理由" mustinput="" maxlength="500" datatype="0" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" onfocus="showCharsInfo(this);" onkeyup="textareaInputCount(this)" onmousedown="enterKey()" onkeydown="enterKey();"></textarea></div></td>'
            + '</tr>';
        let addDiv = '<div style="margin-left:-10px;margin-top:-33px;position:absolute;" name="addBtnDiv">'+
            '<span title="新增"  class="btn btn-link list-add color_theme" style="margin-top: 10px; margin-left: -7px;">'+
            '<i class="i i-jia3" onclick="WkfMakePolicyMeetingViewPoint.addOne(this);return false;"></i>'+
            '</span>'+
            '</div>';
        if(positionStr != "prepend") {//初始化时没有删除
            let subDiv = '<div style="margin-left:-30px;margin-top:-33px;position:absolute;" name="subBtnDiv">' +
                '<span title="删除" class="btn btn-link list-add color_theme" style="margin-top: 10px; margin-left: -20px;">' +
                '<i class="i i-x5" onclick="WkfMakePolicyMeetingViewPoint.subOne(this);return false;"></i>' +
                '</span>' +
                '</div>';
            addDiv = addDiv + subDiv;
        }
        reviewHtml = reviewHtml
            + '<tr><td>' + addDiv +'</td></tr>';

        if(positionStr == "prepend"){
            $(obj).prepend(reviewHtml);
        }else{
        	$("[name='addBtnDiv']").parents("tr").hide();
            $(obj).after(reviewHtml);
		}
	}
	var _showHtml = function(obj){
	    if(mfReviewMemberList != null && mfReviewMemberList.length > 0){
            $.each(mfReviewMemberList,function(key,value){  //遍历键值对
                let reviewHtml = '';
                //评审人员类型
                reviewHtml = reviewHtml
                    + '<tr>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label "><font color="#FF0000">*</font>评审人员类型</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><select name="reviewMemberType" title="评审人员类型" class="form_select form-control " mustinput="1" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();">'
                    if(value.reviewMemberType == "1"){
                        reviewHtml = reviewHtml + '<option value="1" selected>领导班子委员</option>';
                    }else {
                        reviewHtml = reviewHtml + '<option value="1">领导班子委员</option>';
                    }

                    if(value.reviewMemberType == "2"){
                        reviewHtml = reviewHtml +  '<option value="2" selected>业务部门委员</option>';
                    }else{
                        reviewHtml = reviewHtml +  '<option value="2">业务部门委员</option>';

                    }
                    if(value.reviewMemberType == "3"){
                        reviewHtml = reviewHtml +  '<option value="3" selected>职能部门委员</option>';
                    }else{
                        reviewHtml = reviewHtml +  '<option value="3">职能部门委员</option>';

                    }
                    if(value.reviewMemberType == "4"){
                        reviewHtml = reviewHtml +  '<option value="4" selected>业务部门替补委员</option>';
                    }else{
                        reviewHtml = reviewHtml +  '<option value="4">业务部门替补委员</option>';

                    }
                reviewHtml = reviewHtml
                    + '</select>'
                    + ' </div></td></tr>';
                // 评审委员编号
                reviewHtml = reviewHtml
                    +'<tr style="display: none;">'
                    + '<td></td>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">评审委员编号</label></td>'
                    + '<td class="tdvalue  right" colspan="1" rowspan="1"><div class="input-group"><input type="text" title="评审委员编号" name="reviewMemberNo" datatype="0" mustinput="" class="form-control" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" value="'+value.reviewMemberNo+'"></div></td>'
                    + '<td></td></tr>';
                // 评审委员
                reviewHtml = reviewHtml
                    + '<tr>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">评审委员</label></td>'
                    + '<td class="tdvalue  right" colspan="1" rowspan="1"><div class="input-group"><input type="text" title="评审委员" name="reviewMemberName" datatype="0" mustinput="" class="form-control" onblur="func_uior_valTypeImm(this);"  onmousedown="enterKey()" onkeydown="enterKey();"  value="'+value.reviewMemberName+'"></div></td>'
                    + '<td></td></td>';
                // 意见
                reviewHtml = reviewHtml
                    + '<tr>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">意见</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><select name="reviewMemberOpinion" title="意见" class="form_select form-control " mustinput="1" onblur="func_uior_valTypeImm(this);"  onmousedown="enterKey()" onkeydown="enterKey();">'
                    if(value.reviewMemberOpinion == "1"){
                        reviewHtml = reviewHtml + '<option value="1" selected>同意</option>';
                    }else {
                        reviewHtml = reviewHtml + '<option value="1">同意</option>';
                    }

                    if(value.reviewMemberOpinion == "2"){
                        reviewHtml = reviewHtml +  '<option value="2" selected>否决</option>';
                    }else{
                        reviewHtml = reviewHtml +  '<option value="2">否决</option>';

                    }

                    if(value.reviewMemberOpinion == "3"){
                        reviewHtml = reviewHtml + '<option value="3" selected>复议</option>';
                    }else{
                        reviewHtml = reviewHtml + '<option value="3">复议</option>';
                    }
                reviewHtml = reviewHtml
                    + '</select>'
                    + ' </div></td></tr>';
                // 其他需要补充或落实的内容
                reviewHtml = reviewHtml
                    + '<tr>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">其他需要补充或落实的内容</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><textarea class="form-control" name="reviewMemberOtherContent" title="其他需要补充或落实的内容" mustinput="0" maxlength="500" datatype="0" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" onfocus="showCharsInfo(this);" onkeyup="textareaInputCount(this)" onmousedown="enterKey()" onkeydown="enterKey();">'+value.reviewMemberOtherContent+'</textarea></div></td>'
                    + '</tr>';

                // 复议或否决的主要理由
                if(value.reviewMemberOpinion == "2" || value.reviewMemberOpinion == "3" ) {
                    reviewHtml = reviewHtml
                        +'<tr>'
                }else {
                    reviewHtml = reviewHtml
                        +'<tr  style="display: none;">'
                }
                reviewHtml = reviewHtml
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">复议或否决的主要理由</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><select name="reviewMemberReason" title="复议或否决的主要理由" class="form-control" mustinput="" onblur="func_uior_valTypeImm(this);"  onmousedown="enterKey()" onkeydown="enterKey();">'

                if (value.reviewMemberReason == "1") {
                    reviewHtml = reviewHtml + '<option value="1" selected>不符合产业政策或公司业务方向</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="1">不符合产业政策或公司业务方向</option>';
                }
                if (value.reviewMemberReason == "2") {
                    reviewHtml = reviewHtml + +'<option value="2" selected>有涉诉、负面报道或违约记录较多、有主观违约可能</option>';
                } else {
                    reviewHtml = reviewHtml + +'<option value="2">有涉诉、负面报道或违约记录较多、有主观违约可能</option>';
                }
                if (value.reviewMemberReason == "3") {
                    reviewHtml = reviewHtml + '<option value="3" selected>管理或履约能力差，影响企业持续经营和还款能力</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="3">管理或履约能力差，影响企业持续经营和还款能力</option>';
                }
                if (value.reviewMemberReason == "4") {
                    reviewHtml = reviewHtml + '<option value="4" selected>负债规模过大，偿债能力预期不足</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="4">负债规模过大，偿债能力预期不足</option>';
                }
                if (value.reviewMemberReason == "5") {
                    reviewHtml = reviewHtml + '<option value="5" selected>反担保严重不足，缺乏控制措施</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="5">反担保严重不足，缺乏控制措施</option>';
                }
                if (value.reviewMemberReason == "6") {
                    reviewHtml = reviewHtml + '<option value="6" selected>信息披露严重不足、需补充</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="6">信息披露严重不足、需补充</option>';
                }
                if (value.reviewMemberReason == "7") {
                    reviewHtml = reviewHtml + '<option value="7" selected>其他</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="7">其他</option>';
                }
                reviewHtml = reviewHtml
                    + '</select>'
                    + '</div></td></tr>';
                if (value.reviewMemberReason == "7") {
                    reviewHtml = reviewHtml
                        +'<tr>'
                }else{
                    reviewHtml = reviewHtml
                        +'<tr  style="display: none;">'
                }
                // 其他理由
                reviewHtml = reviewHtml
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">其他理由</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><textarea class="form-control" name="reviewMemberOtherReason" title="其他理由" mustinput="" maxlength="500" datatype="0" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" onfocus="showCharsInfo(this);" onkeyup="textareaInputCount(this)" onmousedown="enterKey()" onkeydown="enterKey();">' + value.reviewMemberOtherReason + '</textarea></div></td>'
                    + '</tr>';
                if(key == 0){
                    $(obj).prepend(reviewHtml);
                }else{
                    $("[name='reviewMemberOtherReason']").last().parents("tr").after(reviewHtml);
                }
            });
            if(from == "reviewMeeting"){
                $("[name='reviewMemberName']").attr("readonly","readonly");
                $("[name='reviewMemberOpinion']").attr("readonly","readonly");
                $("[name='reviewMemberOtherContent']").attr("readonly","readonly");
                $("[name='reviewMemberReason']").attr("readonly","readonly");
                $("[name='reviewMemberOtherReason']").attr("readonly","readonly");
                $("[name='reviewMemberType']").attr("readonly","readonly");

            }
        }
	}
	var _editHtml = function(obj){
	    if(mfReviewMemberList != null && mfReviewMemberList.length > 0){
            $.each(mfReviewMemberList,function(key,value){  //遍历键值对
                let reviewHtml = '';
                //评审人员类型
                reviewHtml = reviewHtml
                    + '<tr>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label "><font color="#FF0000">*</font>评审人员类型</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><select name="reviewMemberType" title="评审人员类型" class="form_select form-control " mustinput="1" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" onchange="WkfMakePolicyMeetingViewPoint.changeReviewMemberType(this);">'
                    if(value.reviewMemberType == "1"){
                        reviewHtml = reviewHtml + '<option value="1" selected>领导班子委员</option>';
                    }else {
                        reviewHtml = reviewHtml + '<option value="1">领导班子委员</option>';
                    }

                    if(value.reviewMemberType == "2"){
                        reviewHtml = reviewHtml +  '<option value="2" selected>业务部门委员</option>';
                    }else{
                        reviewHtml = reviewHtml +  '<option value="2">业务部门委员</option>';

                    }
                    if(value.reviewMemberType == "3"){
                        reviewHtml = reviewHtml +  '<option value="3" selected>职能部门委员</option>';
                    }else{
                        reviewHtml = reviewHtml +  '<option value="3">职能部门委员</option>';

                    }
                    if(value.reviewMemberType == "4"){
                        reviewHtml = reviewHtml +  '<option value="4" selected>业务部门替补委员</option>';
                    }else{
                        reviewHtml = reviewHtml +  '<option value="4">业务部门替补委员</option>';

                    }
                reviewHtml = reviewHtml
                    + '</select>'
                    + ' </div></td></tr>';
                // 评审委员编号
                reviewHtml = reviewHtml
                    +'<tr style="display: none;">'
                    + '<td></td>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">评审委员编号</label></td>'
                    + '<td class="tdvalue  right" colspan="1" rowspan="1"><div class="input-group"><input type="text" title="评审委员编号" name="reviewMemberNo" datatype="0" mustinput="" class="form-control" onblur="func_uior_valTypeImm(this);" onmousedown="enterKey()" onkeydown="enterKey();" value="'+value.reviewMemberNo+'"></div></td>'
                    + '<td></td></tr>';
                // 评审委员
                reviewHtml = reviewHtml
                    + '<tr>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">评审委员</label></td>'
                    + '<td class="tdvalue  right" colspan="1" rowspan="1"><div class="input-group"><input type="text" title="评审委员" name="reviewMemberName" datatype="0" mustinput="" class="form-control" onblur="func_uior_valTypeImm(this);"  onmousedown="enterKey()" onkeydown="enterKey();"  value="'+value.reviewMemberName+'" onclick="WkfMakePolicyMeetingViewPoint.selectApprovalpers(this);"></div></td>'
                    + '<td></td></td>';
                // 意见
                reviewHtml = reviewHtml
                    + '<tr>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">意见</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><select name="reviewMemberOpinion" title="意见" class="form_select form-control " mustinput="1" onblur="func_uior_valTypeImm(this);"  onmousedown="enterKey()" onkeydown="enterKey();" onchange="WkfMakePolicyMeetingViewPoint.reviewMemberOpinionChange(this)">'
                    if(value.reviewMemberOpinion == "1"){
                        reviewHtml = reviewHtml + '<option value="1" selected>同意</option>';
                    }else {
                        reviewHtml = reviewHtml + '<option value="1">同意</option>';
                    }

                    if(value.reviewMemberOpinion == "2"){
                        reviewHtml = reviewHtml +  '<option value="2" selected>否决</option>';
                    }else{
                        reviewHtml = reviewHtml +  '<option value="2">否决</option>';

                    }

                    if(value.reviewMemberOpinion == "3"){
                        reviewHtml = reviewHtml + '<option value="3" selected>复议</option>';
                    }else{
                        reviewHtml = reviewHtml + '<option value="3">复议</option>';
                    }
                reviewHtml = reviewHtml
                    + '</select>'
                    + ' </div></td></tr>';
                // 其他需要补充或落实的内容
                reviewHtml = reviewHtml
                    + '<tr>'
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">其他需要补充或落实的内容</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><textarea class="form-control" name="reviewMemberOtherContent" title="其他需要补充或落实的内容" mustinput="0" maxlength="500" datatype="0" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" onfocus="showCharsInfo(this);" onkeyup="textareaInputCount(this)" onmousedown="enterKey()" onkeydown="enterKey();">'+value.reviewMemberOtherContent+'</textarea></div></td>'
                    + '</tr>';

                // 复议或否决的主要理由
                if(value.reviewMemberOpinion == "2" || value.reviewMemberOpinion == "3" ) {
                    reviewHtml = reviewHtml
                        +'<tr>'
                }else {
                    reviewHtml = reviewHtml
                        +'<tr  style="display: none;">'
                }
                reviewHtml = reviewHtml
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">复议或否决的主要理由</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><select name="reviewMemberReason" title="复议或否决的主要理由" class="form-control" mustinput="" onblur="func_uior_valTypeImm(this);"  onmousedown="enterKey()" onkeydown="enterKey();" onchange="WkfMakePolicyMeetingViewPoint.reviewMemberReasonChange(this)">'

                if (value.reviewMemberReason == "1") {
                    reviewHtml = reviewHtml + '<option value="1" selected>不符合产业政策或公司业务方向</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="1">不符合产业政策或公司业务方向</option>';
                }
                if (value.reviewMemberReason == "2") {
                    reviewHtml = reviewHtml + +'<option value="2" selected>有涉诉、负面报道或违约记录较多、有主观违约可能</option>';
                } else {
                    reviewHtml = reviewHtml + +'<option value="2">有涉诉、负面报道或违约记录较多、有主观违约可能</option>';
                }
                if (value.reviewMemberReason == "3") {
                    reviewHtml = reviewHtml + '<option value="3" selected>管理或履约能力差，影响企业持续经营和还款能力</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="3">管理或履约能力差，影响企业持续经营和还款能力</option>';
                }
                if (value.reviewMemberReason == "4") {
                    reviewHtml = reviewHtml + '<option value="4" selected>负债规模过大，偿债能力预期不足</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="4">负债规模过大，偿债能力预期不足</option>';
                }
                if (value.reviewMemberReason == "5") {
                    reviewHtml = reviewHtml + '<option value="5" selected>反担保严重不足，缺乏控制措施</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="5">反担保严重不足，缺乏控制措施</option>';
                }
                if (value.reviewMemberReason == "6") {
                    reviewHtml = reviewHtml + '<option value="6" selected>信息披露严重不足、需补充</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="6">信息披露严重不足、需补充</option>';
                }
                if (value.reviewMemberReason == "7") {
                    reviewHtml = reviewHtml + '<option value="7" selected>其他</option>';
                } else {
                    reviewHtml = reviewHtml + '<option value="7">其他</option>';
                }
                reviewHtml = reviewHtml
                    + '</select>'
                    + '</div></td></tr>';
                if (value.reviewMemberReason == "7") {
                    reviewHtml = reviewHtml
                        +'<tr>'
                }else{
                    reviewHtml = reviewHtml
                        +'<tr  style="display: none;">'
                }
                // 其他理由
                reviewHtml = reviewHtml
                    + '<td class="tdlable right" colspan="1" rowspan="1"><label class="control-label ">其他理由</label></td>'
                    + '<td class="tdvalue  right" colspan="2" rowspan="1"><div class="input-group"><textarea class="form-control" name="reviewMemberOtherReason" title="其他理由" mustinput="" maxlength="500" datatype="0" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" onfocus="showCharsInfo(this);" onkeyup="textareaInputCount(this)" onmousedown="enterKey()" onkeydown="enterKey();">' + value.reviewMemberOtherReason + '</textarea></div></td>'
                    + '</tr>';
                let addDiv = '<div style="margin-left:-10px;margin-top:-33px;position:absolute;" name="addBtnDiv">'+
                    '<span title="新增"  class="btn btn-link list-add color_theme" style="margin-top: 10px; margin-left: -7px;">'+
                    '<i class="i i-jia3" onclick="WkfMakePolicyMeetingViewPoint.addOne(this);return false;"></i>'+
                    '</span>'+
                    '</div>';
                if(key != 0){
                    let subDiv = '<div style="margin-left:-30px;margin-top:-33px;position:absolute;" name="subBtnDiv">' +
                        '<span title="删除" class="btn btn-link list-add color_theme" style="margin-top: 10px; margin-left: -20px;">' +
                        '<i class="i i-x5" onclick="WkfMakePolicyMeetingViewPoint.subOne(this);return false;"></i>' +
                        '</span>' +
                        '</div>';
                    addDiv = addDiv + subDiv;
                }
                reviewHtml = reviewHtml
                    + '<tr><td>' + addDiv +'</td></tr>';
                if(key == 0){
                    $(obj).prepend(reviewHtml);
                }else{
                    $("[name='addBtnDiv']").last().parents("tr").after(reviewHtml);
                }
                if(key != mfReviewMemberList.length -1){
                    $("[name='addBtnDiv']").parents("tr").hide();
                }
            });
        }
	}
	return {
		init:_init,
		doSubmitAjax:_doSubmitAjax,
        approvalBack:_approvalBack,
        getApprovaPage:_getApprovaPage,
        reviewMemberOpinionChange:_reviewMemberOpinionChange,
        reviewMemberReasonChange:_reviewMemberReasonChange,
        addOne:_addOne,
        subOne:_subOne,
        creditHtml:_creditHtml,
        showHtml:_showHtml,
        selectApprovalpers:_selectApprovalpers,
        changeReviewMemberType:_changeReviewMemberType,
	};
}(window,jQuery);
