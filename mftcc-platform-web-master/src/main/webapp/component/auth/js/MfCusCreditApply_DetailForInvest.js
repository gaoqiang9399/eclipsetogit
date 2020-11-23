var mfCusCreditApply_DetailForInvest = function(window,$){

	var _init = function(){
        if (groupFiled!=''){
            groupFiled =  JSON.parse(groupFiled);
            $.each(groupFiled, function (index1, obj1) {
                var mfCusInvestConfigList = obj1.mfCusInvestConfigList;
               // mfCusInvestConfigList =  JSON.parse(mfCusInvestConfigList);
                var divStr = '<div class="overflowHidden bg-white" id="div'+obj1.groupNo+'">'
                    + '<div class="row clearfix"><div class="upload_body" style="margin-bottom: 10px;margin-top: 0px !important;background: #f8f9fc; height: 50px;line-height: 50px;">'
                    + '<li id="uploadTree_1" class="level0" tabindex="0" hidefocus="true" treenode="" style="font-size: 17px">'
                    +'<i class="i i-xing blockDian"></i>'
                    + '<a id="uploadTree_1_a" class="level0" treenode_a="" onclick="return false" target="_blank" style="margin-top: 0px !important; ">'
                    + '<span id="uploadTree_1_span" style="color: #0a0a0a ">'+obj1.groupName+'</span>'
                    +'<button class=" btn btn-link pull-right formAdd-btn" style="height: 50px;" data-toggle="collapse" data-target="#div2'+obj1.groupNo+'" aria-expanded="true">'
                    + '<i class="i i-close-up"></i><i class="i i-open-down"></i></button>'
                    + '</div>'
                    + '</div><div id="div2'+obj1.groupNo+'" class="collapse in" aria-expanded="true"></div></div></div>';

                $(".configContent").append(divStr);
                $.each(mfCusInvestConfigList, function (index, obj) {
                    var htmlStr = '<div style="padding-bottom: 20px;background: #F8F8F8;margin-bottom: 10px; border: 1px solid #ddd;"><table class="table table-bordered"  style="margin-bottom: 0px !important; margin-top: 0px !important;">' +
                        '<tbody id="configContent' +  obj.reportId + '"><tr><td class="tdlable right" style="width: 195px" colspan="1" rowspan="1"><label class="control-label ">'+obj.fieldName+'</label></td>'+
                        '<td class="tdvalue  right" colspan="3" rowspan="1" style="padding-left: 0px!important;"><div class="input-group"><textarea class="form-control" placeholder="3000字以内" name="'+ obj.reportId+'" title="'+obj.field_name+'" mustinput="0" maxlength="3000" datatype="0" onblur="func_uior_valTypeImm(this);hideCharsInfo(this);" onfocus="showCharsInfo(this);" onkeyup="textareaInputCount(this)" onmousedown="enterKey()" onkeydown="enterKey();"></textarea></div></td>'+
                    '</tr></tbody></table>'+'<div id="configBaseXin' +  obj.reportId + '">' + '</div></div>';
                    var fileStr= '<div id="configBaseXin' +  obj.reportId + '">' + '</div></div>';
                    $("#div2"+obj1.groupNo).append(htmlStr);
                    $("#configBaseXin" + obj.reportId).append(_addPledgeDocInfo(obj.reportId,obj.docSpiltName));
                    _showDocInfo(obj.reportId);
                    // 拼接要件资料结束
                });
            });
		}
        $('button[data-toggle="collapse"]').click();
	};
    //展示要件资料
    var _showDocInfo = function (reportId) {

        _initAloneDocNodes(reportId);
        isSupportBase64 = (function () {
            var data = new Image();
            var support = true;
            data.onload = data.onerror = function () {
                if (this.width != 1 || this.height != 1) {
                    support = false;
                }
            };
            data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
            return support;
        })();
    };
    //关闭
    var _close = function(formObj) {
        var flag = submitJsMethod($(formObj).get(0), '');
        if (flag) {
            alert(top.getMessage("CONFIRM_OPERATION", "提交下一步"), 2, function () {
                var dataForm = JSON.stringify($(formObj).serializeArray());
                $.ajax({
                    url: webPath + "/mfCusCreditApply/doCommitWkfForConfig",
                    type: "post",
                    dataType: "json",
                    data: {
                        wkfAppId: wkfAppId,
                        commitType: "REPORT",
                        nodeNo:nodeNo,
                        ajaxData: dataForm
                    },
                    error: function () {
                        alert('提交到下一个节点时发生异常', 0);
                    },
                    success: function (data) {
                        if (data.flag == "success") {
                            top.creditFlag = true;
                            myclose_click();
                        }
                    }
                });
            });
        }
    }
    //拼接要件资料html
    var _addPledgeDocInfo = function (reportId,docSpileName) {
        var pledgeDocInfoHtml = "";
        //列表形式展示要件
      //  if (docShowType == "1") {
            pledgeDocInfoHtml = '<div class="overflowHidden" style="background: #F8F8F8;">'
                + '<div class="row clearfix"><div class="upload_body" style="margin-top: 0px !important; "><ul class="ztree" id="uploadTree' + reportId + '">'
                + '<li id="uploadTree_1" class="level0" tabindex="0" hidefocus="true" treenode="">'
                + '<a id="uploadTree_1_a" class="level0" treenode_a="" onclick="" target="_blank" style="margin-top: 0px !important; ">'
                // + '<span id="uploadTree_1_span" class="node_name">要件上传</span>'
                // + '<button title="新增" onclick="mfCusCreditApply_DetailForInvest.docManageAloneAdd(\'' + reportId + '\');return false;" style="position: relative;display: inline-block;top: -3px;left: 2px;" class="btn btn-link formAdd-btn">'
                // + '<i class="i i-jia3"></i></button>'
                +  '<button class=" btn btn-link  download-btn1" onclick="mfCusCreditApply_DetailForInvest.docManageAloneAdd(\'' + reportId + '\');return false;">要件上传</button>'
                + '<button class=" btn btn-link  download-btn" onclick="downloadZip(\'uploadTree\',\'uploadTree_1\');">打包下载</button></a></li></ul>'
                + '<div id="content' + reportId + '" class="table_content list-table doc-manage-table" style="height: auto;padding: 0px 10px"></div></div>'
                + '<div id="view-imgs"><div class="enlarge-img"><input class="close_btn" type="button" value="" onclick="close_view();"/>'
                + '<div class="img-tools"><span class="rotateRight">向右旋转</span> <span class="rotateLeft">向左旋转</span>'
                + '<strong class="title"></strong><div class="view-img last-img"><div></div></div>'
                + '<div class="view-img next-img"><div></div></div></div></div></div></div></div>';
        // } else {
        //     pledgeDocInfoHtml = '<div class="overflowHidden bg-white"><div class="row clearfix">'
        //         + '<div class="upload_body"><ul class="ztree" id="uploadTree' + reportId + '"></ul></div>'
        //         + '<div id="view-imgs"><div class="enlarge-img">'
        //         + '<input class="close_btn" type="button" value="" onclick="close_view();"/>'
        //         + '<div class="img-tools"><span class="rotateRight">向右旋转</span> <span class="rotateLeft">向左旋转</span>'
        //         + '<strong class="title"></strong><div class="view-img last-img"><div></div></div>'
        //         + '<div class="view-img next-img"><div></div></div>'
        //         + '</div></div></div></div></div>';
        // }
        return pledgeDocInfoHtml;
    };
    var  _docManageAloneAdd = function(reportId){

        jQuery.ajax({
            url: webPath + "/mfCusCreditApply/getDocInfoByReportId",
            data: {
                reportId: reportId
            },
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            }, success: function (data) {
                LoadingAnimate.stop();
                if (data.flag = "success") {

                   var  dataDocParm={
                        relNo:appId,
                        docType:data.docType,
                        docTypeName:data.docTypeName,
                        docSplitName:data.docSplitName,
                        docSplitNoArr:data.docSpiltNo,
                        scNo:scNo+"_private",
                        query:''
                    };
                    var docParmNType = docParm;
                    docParmNType= "";
                    for (var k in dataDocParm){
                        docParmNType+=k+"="+dataDocParm[k]+"&";
                    }
                    docParmNType+="viewType=single"
                    top.openBigForm(webPath+'/docManage/docManageListAdd?'+docParmNType,'要件上传', function() {
                        _initAloneDocNodes(reportId);
                    });
                }
            }, error: function (data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });
    }
    /**
     * 初始化单独要件
     */
    var  _initAloneDocNodes = function(reportId) {
        var url = "";
        /**
         * 单独上传要件
         */
        jQuery.ajax({
            url: webPath + "/mfCusCreditApply/getDocInfoByReportId",
            data: {
                reportId: reportId
            },
            type: "POST",
            dataType: "json",
            beforeSend: function () {
            }, success: function (data) {
                LoadingAnimate.stop();
                if (data.flag = "success") {
                   var  dataDocParm={
                        relNo:appId,
                        docType:data.docType,
                        docTypeName:data.docTypeName,
                        docSplitName:data.docSplitName,
                       scNo:scNo+"_private",
                       docSplitNoArr:data.docSpiltNo,
                        query:''
                    };
                    var dataParm = JSON.stringify(dataDocParm);
                    url = webPath + "/docManage/getDocNodesAloneCommAjax?ajaxData="
                        + encodeURIComponent(encodeURIComponent(dataParm));
                    if (typeof (docShowType) != "undefined"&&docShowType=="1"){
                        url = webPath + "/docManage/getDocNodesAloneCommForListAjax?ajaxData="
                            + encodeURIComponent(encodeURIComponent(dataParm));
                    }
                    $.ajax({
                        url : url,
                        type : "get",
                        contentType: "text/html;charset=utf-8",
                        dataType : "json",
                        success : function(data) {
                            query = data.query;
                            if (typeof (docShowType) != "undefined"&&docShowType=="1"){
                                var html = data.tableHtml;
                                $("#content"+reportId).empty();
                                html = html.replace("display:none;","");
                                $("#content"+reportId).append(html);
                                addFileToList();
                            }else{
                                if (typeof (aloneFlag) != "undefined") {
                                    zTreeNodesDoc = JSON.parse(data.zTreeNodes);
                                }else{
                                    zTreeNodesDoc = data.zTreeNodes;
                                }
                                console.log(zTreeNodesDoc);
                                var zTreeObj = $.fn.zTree.init($("#uploadTree"+reportId), setting_upload,
                                    zTreeNodesDoc);
                            }

                        },
                        error : function() {

                        }
                    });
                }
            }, error: function (data) {
                alert(top.getMessage("FAILED_OPERATION", " "), 0);
            }
        });

    }

	return{
		init:_init,
        addPledgeDocInfo:_addPledgeDocInfo,
        initAloneDocNodes:_initAloneDocNodes,
        showDocInfo:_showDocInfo,
        docManageAloneAdd:_docManageAloneAdd,
        close:_close
	};
}(window,jQuery);