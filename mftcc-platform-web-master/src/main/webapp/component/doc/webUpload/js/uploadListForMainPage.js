var query = '';
var uploadCompFlag = false;
var percentages = {};
var supportTransition = (function() {
    var s = document.createElement('p').style, r = 'transition' in s
        || 'WebkitTransition' in s || 'MozTransition' in s
        || 'msTransition' in s || 'OTransition' in s;
    s = null;
    return r;
})();
// 判断浏览器是否支持base64
var isSupportBase64;
// 优化retina, 在retina下这个值是2
var ratio = window.devicePixelRatio || 1,
// 缩略图大小
// thumbnailWidth = 110 * ratio,
// thumbnailHeight = 110 * ratio;
    thumbnailWidth = 120, thumbnailHeight = 80;
var IDMark_upBtn = "_upBtn", IDMark_addLi = "_addLi";
IDMark_A = "_a";
IDMark_Queue = "_queue";
var setting_upload = {
    view : {
        showIcon : false,
        showLine : false,
        dblClickExpand : false,
        addDiyDom : addDiyDom
    },
    data : {
        simpleData : {
            enable : true
        }
    },
    callback : {
        onClick : onClickUpload
    }
};
/**
 * 初始化要件
 */
function initDocNodes() {
    var url = "";
    var postData,filterInInput;
    /**
     * 单独上传要件
     */
    if (typeof (aloneFlag) != "undefined") {
        var dataParm = JSON.stringify(dataDocParm);
        url = webPath + "/docManage/getDocNodesAloneCommForListAjax?ajaxData="
            + encodeURIComponent(encodeURIComponent(dataParm));
        $.ajax({
            url : url,
            type : "get",
            contentType: "text/html;charset=utf-8",
            dataType : "json",
            success : function(data) {
                query = data.query;
                var html = data.tableHtml;
                $("#content").empty();
                html = html.replace("display:none;","")
                $("#content").append(html);
                addFileToList();
                //处理要件的删除权限按钮
                if(typeof(queryFile)!="undefined"){
                    if(queryFile=="query"){
                        $(".formAdd-btnFile").hide();
                        //删除
                        $(".delBtn").html("删除");
                    }

                }
            },
            error : function() {

            }
        });
    } else if (typeof (isCusDoc) != "undefined" && isCusDoc == "cusDoc") {// 客户视角特殊处理（由于需要实时读取后台配置，所以数据源的读取单独处理）
        postData= new Object();
        postData.ajaxData = getFilterValArr();
        postData.pageNo = 1;
        postData.eadis_page = 1;
        postData.pageSize = 10000;
        postData.tableId = "tableDocManageList";
        postData.tableType = "thirdTableTag";
        filterInInput = $('input[name=filter_in_input]').val();
        if(filterInInput != null && filterInInput!=undefined && filterInInput!=""){
            postData.filterInInput = filterInInput;
        }
        url = webPath + "/docManage/getCusDocNodesForListAjax?"+docParm;
        $.ajax({
            url : url,
            type : "POST",
            data: postData,
            dataType : "json",
            success : function(data) {
                query = data.query;
                var html = data.tableHtml;
                $("#content").empty();
                html = html.replace("display:none;","");
                html = html.replace("name=\"docSplitName\"","name=\"docSplitName\" onclick=\"rank(1,2)\"; style=\" cursor: pointer;\"");
                html = html.replace("文件分类","文件分类<i class=\"i i-arrow-updown\"></i>");

                html = html.replace("name=\"regTime\"","name=\"regTime\" onclick=\"rank(3,2)\"; style=\" cursor: pointer;\"");
                html = html.replace("上传时间","上传时间<i class=\"i i-arrow-updown\"></i>");

                html = html.replace("name=\"docSize\"","name=\"docSize\" onclick=\"rank(5,2)\"; style=\" cursor: pointer;\"");
                html = html.replace("文件大小","文件大小<i class=\"i i-arrow-updown\"></i>");
                $("#content").append(html);
                addFileToList();
                $("table td[mytitle]:contains('...')").initMytitle();
                //处理要件的删除权限按钮
                if(typeof(queryFile)!="undefined"){
                    if(queryFile=="query"){
                        $(".formAdd-btnFile").hide();
                        //删除
                        $(".delBtn").html("删除");
                    }

                }
            },
            error : function() {

            }
        });
    } else {
        postData= new Object();
        postData.ajaxData = getFilterValArr();
        postData.pageNo = 1;
        postData.eadis_page = 1;
        postData.pageSize = 10000;
        postData.tableId = "tableDocManageList";
        postData.tableType = "thirdTableTag";
        filterInInput = $('input[name=filter_in_input]').val();
        if(filterInInput != null && filterInInput!=undefined && filterInInput!=""){
            postData.filterInInput = filterInInput;
        }
        url = webPath + "/docManage/getDocNodesForListAjax?" + docParm;
        $.ajax({
            url : url,
            type : "POST",
            data : postData,
            dataType : "json",
            success : function(data) {
                query = data.query;
                var html = data.tableHtml;
                $("#content").empty();
                html = html.replace("display:none;","");
                html = html.replace("name=\"docSplitName\"","name=\"docSplitName\" onclick=\"rank(1,3)\"; style=\" cursor: pointer;\"");
                html = html.replace("文件分类","文件分类<i class=\"i i-arrow-updown\"></i>");

                html = html.replace("name=\"regTime\"","name=\"regTime\" onclick=\"rank(3,3)\"; style=\" cursor: pointer;\"");
                html = html.replace("上传时间","上传时间<i class=\"i i-arrow-updown\"></i>");

                html = html.replace("name=\"docSize\"","name=\"docSize\" onclick=\"rank(5,3)\"; style=\" cursor: pointer;\"");
                html = html.replace("文件大小","文件大小<i class=\"i i-arrow-updown\"></i>");
                $("#content").append(html);
                addFileToList();
                //处理要件的删除权限按钮
                if(typeof(queryFile)!="undefined"){
                    if(queryFile=="query"){
                        $(".formAdd-btnFile").hide();
                        //删除
                        $(".delBtn").html("删除");
                    }

                }
            },
            error : function() {

            }
        });
    }

}
/**
 * 初始化单独要件
 */
function initAloneDocNodes(collateralId) {
    var url = "";
    /**
     * 单独上传要件
     */
    if (typeof (aloneFlag) != "undefined") {
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
                    $("#content"+collateralId).empty();
                    html = html.replace("display:none;","");
                    $("#content"+collateralId).append(html);
                    addFileToList();
                    //处理要件的删除权限按钮
                    if(typeof(queryFile)!="undefined"){
                        if(queryFile=="query"){
                            $(".formAdd-btnFile").hide();
                            //删除
                            $(".delBtn").html("删除");
                        }

                    }
                }else{
                    if (typeof (aloneFlag) != "undefined") {
                        zTreeNodesDoc = JSON.parse(data.zTreeNodes);
                    }else{
                        zTreeNodesDoc = data.zTreeNodes;
                    }
                    console.log(zTreeNodesDoc);
                    var zTreeObj = $.fn.zTree.init($("#uploadTree"+collateralId), setting_upload,
                        zTreeNodesDoc);
                }

            },
            error : function() {

            }
        });
    }
}


function addFileToList() {
    $('.doc-manage-table #tablist tr').each(function (){
        try{
            var docNo = $(this).children('td').eq(0).children('input').val();
            var docBizNo = $(this).children('td').eq(1).children('input').val();
         //   var fileName = $(this).children('td').eq(4).text();
            var docAddr = $(this).children('td').eq(7).children('input').val();
            var docSplitNo = $(this).children('td').eq(8).children('input').val();
            var formId = $(this).children('td').eq(9).children('input').val();
            var docType = $(this).children('td').eq(10).children('input').val();
            var relNo = $(this).children('td').eq(11).children('input').val();
            var fileName=$(this).children('td').eq(14).children('input').val();
            if(typeof (fileName) == 'undefined'){
                return true;
            }
            var src =showOtherFile(fileName,docNo,docBizNo);
            $img  = $("<img/>").attr("src",src).css({
                "height": "40px",
                "padding": "5px 0px",
            });
            $(this).children('td').eq(2).append($img);
            var fileExt = fileName.substr(Number(fileName.lastIndexOf("."))).toLowerCase();

            var imgInfo = new Object();
            imgInfo.formId = formId;
            imgInfo.relNo = relNo;
            imgInfo.docSplitNo = docSplitNo;
            imgInfo.docType = docType;
            imgInfo.src = webPath + "/docUpLoad/viewImage?docNo="
                + docNo + "&docBizNo=" + docBizNo;
            imgInfo.name = fileName;
            imgInfo.cusNo = relNo;

            $img.data("imgInfo", imgInfo);
            $img.data("relUrl", webPath + "/docUpLoad/viewImage?docNo="
                + docNo + "&docBizNo=" + docBizNo);
            if (".doc|.docx|.ppt|.pptx|.xls|.xlsx|.pdf"
                .indexOf(fileExt) > -1) {
                $img.unbind()
                    .bind(
                        "click",
                        function() {
                            // 这几种特殊格式的走特殊方法。
                            if (".doc|.docx|.ppt|.pptx|.xls|.xlsx|.pdf"
                                .indexOf(fileExt) > -1) {
                                var url = webPath
                                    + "/docUpLoad/getFileDownload?docNo="
                                    + docNo + "&docBizNo="
                                    + docBizNo;
                                // download_file(url);
                                var imgObj = new Object();
                                imgObj.docAddr = docAddr;
                                viewFile(imgObj);
                                return false;
                            }
                        });
            }
        }catch (e) {
            console.error(e);
        }
    });
    var options = {};
    $('.doc-manage-table #tablist').viewer(options);
}

function showOtherFile(fileName,docNo,docBizNo){
    var AllImgExt = ".jpg|.jpeg|.gif|.bmp|.png| ";
    var fileExt = fileName.substr(Number(fileName.lastIndexOf(".")))
        .toLowerCase();
    var src = webPath + "/docUpLoad/viewCompressImage?docNo="
        + docNo + "&docBizNo=" + docBizNo;

    if (AllImgExt.indexOf(fileExt + "|") == -1) {
        if (".doc|.docx|".indexOf(fileExt) > -1) {
            src = webPath+"/component/doc/webUpload/image/word.png";
        } else if (".ppt|.pptx|".indexOf(fileExt) > -1) {
            src = webPath+ "/component/doc/webUpload/image/ppt.png";
        } else if (".xls|.xlsx|".indexOf(fileExt) > -1) {
            src = webPath+ "/component/doc/webUpload/image/xls.png";
        } else if (".pdf".indexOf(fileExt) > -1) {
            src = webPath + "/component/doc/webUpload/image/pdf.png";
        } else {
            src = webPath + "/component/doc/webUpload/image/file.png";
        }
    }
    return src;
}

function downloadFile(obj,url) {
    if(url!=""&&url!=null){
        var parm=url.split("?")[1];
        window.top.location.href = webPath
            + "/docUpLoad/getFileDownload?"+parm;
    };
}

function deleteFile(obj,url) {
    if(url!=""&&url!=null) {
        var parm = url.split("?")[1];
        // ajax异步删除成功后调用
        $.ajax({
            type : "post",
            dataType : 'json',
            url : webPath + "/docUpLoad/delFile?"+parm,
            async : false,
            contentType : "application/x-www-form-urlencoded; charset=UTF-8",
            success : function(data) {
                // 删除
                top.alert(data.msg,
                    1);
                $(obj).parents("tr").remove();
            },
            error : function(XMLHttpRequest,
                             textStatus, errorThrown) {
                console
                    .log(XMLHttpRequest.status
                        + "-"
                        + XMLHttpRequest.readyState
                        + "-"
                        + textStatus);
            }
        });
    }

}

function docManageAdd(){
    var docParmNType = docParm;
    if (typeof (aloneFlag) != "undefined") {

        docParmNType= "";
        for (var k in dataDocParm){
            docParmNType+=k+"="+dataDocParm[k]+"&";
        }
        docParmNType+="viewType=single"
    } else if (typeof (isCusDoc) != "undefined" && isCusDoc == "cusDoc") {
        docParmNType+="&viewType=cus"
    }else{
        docParmNType+="&viewType=common"
    }
    top.openBigForm(webPath+'/docManage/docManageListAdd?'+docParmNType,'要件上传', function() {
        initDocNodes();
    });
}

function docManageAloneAdd(collateralId){
    var docParmNType = docParm;
    if (typeof (aloneFlag) != "undefined") {
        docParmNType= "";
        for (var k in dataDocParm){
            docParmNType+=k+"="+dataDocParm[k]+"&";
        }
        docParmNType+="viewType=single"
    }
    top.openBigForm(webPath+'/docManage/docManageListAdd?'+docParmNType,'要件上传', function() {
        dataDocParm={
            relNo:collateralId,
            docType:"collateralDoc",
            docTypeName:"担保资料",
            docSplitName:"担保资料",
            query:''
        };
        initAloneDocNodes(collateralId);
    });
}



















function onClickUpload(e, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("uploadTree");
    zTree.expandNode(treeNode);
}

function addDiyDom(treeId, treeNode) {
    $("#" + treeNode.tId + "_switch").remove();
    $("#" + treeNode.tId + "_ico").remove();
    var aObj = $("#" + treeNode.tId + IDMark_A);
    var titleStr = '';
    var spanObj = $("#" + treeNode.tId + "_span").html(
        '<i class="i i-xing blockDian"></i>' + treeNode.docTypeName);
    spanObj
        .after('<button  class=" btn btn-link  download-btn" onclick="downloadZip(\''
            + treeId + '\',\'' + treeNode.tId + '\');">打包下载</button>');
    spanObj
        .after('<button  class=" btn btn-link pull-right formAdd-btnFile" data-toggle="collapse" data-target="#'
            + treeNode.tId
            + '_div"><i class="i i-close-up"></i><i class="i i-open-down"></button>');
    if (treeNode.level == 0) {
        var uploadDivStr = '<div id="'
            + treeNode.tId
            + '_div" class="upload-div collapse in" aria-expanded="true"><ul id="'
            + treeNode.tId + IDMark_Queue + '" class="filelist '
            + treeNode.tId + IDMark_Queue + '"></ul></div>';
        aObj.after(uploadDivStr);
        if (treeNode.imgs) {
            for (var i = 0; i < treeNode.imgs.length; i++) {
                addImgList(treeId, treeNode, treeNode.imgs[i], i);
            }
            var options = {};
            $('#' + treeNode.tId + IDMark_Queue).viewer(options);
        }
    }
    $("#" + treeNode.tId + "_a").removeAttr("title");
    // docTypeName传空串，去掉标题
    if (treeNode.docTypeName == "") {
        $("#" + treeNode.tId + "_a").remove();
    }
}
// 打包下载,获取参数
function downloadZip(treeId, tId) {
    var nodes = new Array();

    $('.doc-manage-table #tablist tr').each(function (){
            var docNo = $(this).children('td').eq(0).children('input').val();
            var obj = new Object();
            obj.docNo = docNo;
        nodes.push(obj);
    });


    var ajaxData = "";
    $.each(nodes, function(i, obj) {
        if (obj.docNo) {
            ajaxData += obj.docNo + ",";
        }
    });
    try {
        if (ajaxData == "") {
            alert(top.getMessage("CONFIRM_OPERATION_REASON", {
                "reason" : "没有可下载的要件",
                "operation" : "下载"
            }), 2, function() {
                var url = webPath + "/docUpLoad/getZipFileDownloadForDocType?"
                    + docParm + "&ajaxData=" + ajaxData + "&docTypeName="
                    + "要件";
                var elemIF = document.createElement("iframe");
                elemIF.src = url;
                elemIF.style.display = "none";
                document.body.appendChild(elemIF);
            });
        } else {
            var url = webPath + "/docUpLoad/getZipFileDownloadForDocType?"
                + docParm + "&ajaxData=" + ajaxData + "&docTypeName="
                + "要件";
            var elemIF = document.createElement("iframe");
            elemIF.src = url;
            elemIF.style.display = "none";
            document.body.appendChild(elemIF);
        }
    } catch (e) {

    }
}

// 预览文件
function viewImg(curliObj, treeId, treeNode, imgObj, uploader, file, src) {
    var docSplitName = imgObj.docSplitName;
    if (docSplitName.length > 5) {
        docSplitName = docSplitName.substring(0, 5) + "...";
    }

    var $li = $('<li class="upload-li" id=\''
        + file.id
        + '\'>'
        + '<div class="type-title" title=\''
        + imgObj.docSplitName
        + '\'>'
        + docSplitName
        + ' <div class="btn btn-primary" onclick="uploadDiv(this);">上传</div></div>'
        + '<p class="progress">' + '<span class="text">等待上传</span>'
        + '<span class="percentage" >' + '</span>' + '</p>'
        + '<div class="opt-upload has-img">'

        + '</div>' + '</li>');
    var addliObj = $("li.upload-li.li_" + imgObj.docSplitNo + ":last");
    addliObj.after($li);
    var $imgDiv = $("#" + file.id).find(".has-img");

    var $btns = $('<div class="file-panel">'
        + '<span class="cancel" title="取消"></span>' + '</div>');
    $imgDiv.append($btns);

    var img = $('<img class="img-item" src="' + src + '">');
    img.data("treeNode", imgObj);
    $imgDiv.append(img);
    $imgDiv.append('<div class="doc-title" title=\'' + file.name + '\'>'
        + file.name + '</div>');

    $imgDiv.on('mouseenter', function() {
        $btns.stop().animate({
            height : 28
        });
    });

    $imgDiv.on('mouseleave', function() {
        $btns.stop().animate({
            height : 0
        });
    });
    $btns.find(".cancel").bind("click", function() {
        uploader.cancelFile(file);
        if (file.getStatus() == "cancelled") {
            $li.remove();
            delete percentages[file.id];
            uploadCompFlag = true;
            $.each(percentages, function(k, v) {
                if (v[1] == 0) {
                    uploadCompFlag = false;
                }
            });
            if (uploadCompFlag) {
                var treeObj = $.fn.zTree.getZTreeObj(treeId);
                treeObj.refresh();
            }
        }
    });
}
function viewFile(imgInfo) {
    var isWin = (navigator.platform == "Win32")
        || (navigator.platform == "Windows");
    if (isWin) {
        notie
            .alert(4, '如果无法正常打开文档，请在系统右侧工具栏下载pageoffice控件并安装，重启浏览器后再次打开文档',
                -1);
        $.ajax({
            type : "post",
            url : webPath + "/docUpLoad/viewFileAjax",
            dataType : "json",
            data : imgInfo,
            success : function(jsonData) {
                if (jsonData.flag == "success") {
                    // window.top.dhccModalDialog.open(webPath +
                    // '/UIplug/PDFjs/web/viewer.html?file=' + webPath +
                    // '/file/'
                    // +jsonData.viewPath,jsonData.fileName,function(){},"90","90","400","300");

                    var poCntObj = $.parseJSON(jsonData.poCnt);
                    mfPageOffice.openPageOffice(poCntObj);

                } else {
                    window.top.alert(jsonData.msg, 0);
                }
            },
            error : function() {
                window.top.alert("不支持的文档类型或文件不存在！", 0);
            },
            complete : function() {
                setTimeout(function() {
                    notie.alert_hide();
                }, 1500);
            }
        });
    } else {
        window.top.alert("当前操作系统不支持在线预览，请下载到本地查阅！", 0);
    }
}
// 初始化图片
function addImgList(treeId, treeNode, imgObj, i) {
    var docSplitName = imgObj.docSplitName;
    var topDocSplitName = imgObj.docSplitName;
    if (docSplitName.length > 7) {
        docSplitName = docSplitName.substring(0, 7) + "...";
    }
    if (topDocSplitName.length > 4) {
        topDocSplitName = topDocSplitName.substring(0, 4) + "...";
    }
    var docDesc = imgObj.docDesc;
    if (imgObj.docDesc == "") {
        docDesc = imgObj.docSplitName;
    }
    var $li, btnId;
    if (imgObj.imgId != "") {
        $li = $('<li class="upload-li li_' + imgObj.docSplitNo + '" id="'
            + imgObj.imgId + '">' + '<div class="type-title" title=\''
            + docDesc + '\'><span class="title-name">' + topDocSplitName
            + '</span> <div class="btn btn-primary btn_' + imgObj.docNo
            + '" onclick="uploadDiv(this);">上传</div></div>'
            + '<div class="opt-upload has-img">' + '</div>' + '</li>');
        $("#" + treeNode.tId + IDMark_Queue).append($li);

        var $btns = $('<div class="file-panel">'
            + '<span class="cancel" title="删除">删除</span>'
            + '<span class="rotateRight" title="向右旋转">向右旋转</span>'
            + '<span class="rotateLeft" title="向左旋转">向左旋转</span>'
            + '<span class="enlarge" title="下载">下载</span></div>');

        var $imgDiv = $("#" + imgObj.imgId,
            $("#" + treeNode.tId + IDMark_Queue)).find(".has-img");
        $imgDiv.append($btns);
        var AllImgExt = ".jpg|.jpeg|.gif|.bmp|.png| ";
        var fileExt = imgObj.src.substr(Number(imgObj.src.lastIndexOf(".")))
            .toLowerCase();
        var src = webPath + "/docUpLoad/viewImage?docNo=" + imgObj.docNo
            + "&docBizNo=" + imgObj.docBizNo;
        if (imgObj.compressPath) {
            src = webPath + "/docUpLoad/viewCompressImage?docNo="
                + imgObj.docNo + "&docBizNo=" + imgObj.docBizNo;
        }
        if (AllImgExt.indexOf(fileExt + "|") == -1) {
            if (".doc|.docx|".indexOf(fileExt) > -1) {
                src = webPath+"/component/doc/webUpload/image/word.png";
            } else if (".ppt|.pptx|".indexOf(fileExt) > -1) {
                src = webPath+ "/component/doc/webUpload/image/ppt.png";
            } else if (".xls|.xlsx|".indexOf(fileExt) > -1) {
                src = webPath+ "/component/doc/webUpload/image/xls.png";
            } else if (".pdf".indexOf(fileExt) > -1) {
                src = webPath + "/component/doc/webUpload/image/pdf.png";
            } else {
                src = webPath + "/component/doc/webUpload/image/file.png";
            }
        }
        var img = $('<img src="' + src + '">');
        img.data("treeNode", treeNode);
        img.data("imgInfo", imgObj);
        img.data("relUrl", webPath + "/docUpLoad/viewImage?docNo="
            + imgObj.docNo + "&docBizNo=" + imgObj.docBizNo);
        $imgDiv.append(img);
        $imgDiv.append('<div class="doc-title" title=\'' + imgObj.name + '\'>'
            + imgObj.name + '</div><div id="btn_' + imgObj.docNo
            + '" class="btn-upload hide"></div>');
        btnId = "btn_" + imgObj.docNo;

        img.unbind()
            .bind(
                "click",
                function() {
                    // 这几种特殊格式的走特殊方法。
                    if (".doc|.docx|.ppt|.pptx|.xls|.xlsx|.pdf"
                        .indexOf(fileExt) > -1) {
                        var url = webPath
                            + "/docUpLoad/getFileDownload?docNo="
                            + imgObj.docNo + "&docBizNo="
                            + imgObj.docBizNo;
                        // download_file(url);
                        viewFile(imgObj);
                        return false;
                    }
                });
        var $wrap = img;
        $imgDiv.on('mouseenter', function() {
            $btns.stop().animate({
                height : 28
            });
        });

        $imgDiv.on('mouseleave', function() {
            $btns.stop().animate({
                height : 0
            });
        });
        var deg = imgObj.rotation ? imgObj.rotation : 0;
        if (query == 'query' && !treeNode.uploadFlag) {
            $btns.find(".cancel").hide();
        }
        $btns
            .on(
                'click',
                'span',
                function() {
                    var index = $(this).index();

                    switch (index) {
                        case 0:
                            // ajax异步删除成功后调用
                            $
                                .ajax({
                                    type : "post",
                                    dataType : 'json',
                                    url : webPath
                                    + "/docUpLoad/delFile",
                                    data : {
                                        docNo : imgObj.imgId,
                                        docBizNo : imgObj.docBizNo
                                    },
                                    async : false,
                                    contentType : "application/x-www-form-urlencoded; charset=UTF-8",
                                    success : function(data) {
                                        // 删除
                                        // console.log(data);
                                        notie.alert(1, imgObj.name
                                            + "," + data.msg,
                                            0.5);
                                        removeFile(treeId, treeNode, i);
                                    },
                                    error : function(XMLHttpRequest,
                                                     textStatus, errorThrown) {
                                        console
                                            .log(XMLHttpRequest.status
                                                + "-"
                                                + XMLHttpRequest.readyState
                                                + "-"
                                                + textStatus);
                                    }
                                });
                            return;
                        case 1:
                            deg += 90;
                            rotationImg($wrap, deg);
                            break;
                        case 2:
                            deg -= 90;
                            rotationImg($wrap, deg);
                            break;
                        case 3:
                            window.top.location.href = webPath
                                + "/docUpLoad/getFileDownload?docNo="
                                + imgObj.docNo + "&docBizNo="
                                + imgObj.docBizNo;
                            break;
                    }
                    if (deg % 360 == 0) {
                        imgObj.rotation = 0;
                    } else {
                        imgObj.rotation = deg;
                    }
                });
    } else {
        var bitianSpan = "";
        if (imgObj.ifMustInput == "1") {
            bitianSpan = '<span class="i i-sanjiao color_red btspan0"></span><span class="btspan1">必</span>';
        }
        $li = $('<li  class="upload-li li_'
            + imgObj.docSplitNo
            + '">'
            + '<div class="type-title"></div>'
            + '<div class="opt-upload no-img">'
            + '<div title="'
            + docDesc
            + '" data-docname="'
            + imgObj.docSplitName
            + '">'
            + docSplitName
            + bitianSpan
            + '</div>'
            + '<div class="btn-upload-div color_theme btn_'
            + imgObj.docSplitNo
            + '" onclick="uploadDiv(this);"><i class="i i-jia3"></i>上传</div>'
            + '</div>' + '<div id="btn_' + imgObj.docSplitNo
            + '" class="btn-upload hide"></div>' + '</li>');
        btnId = "btn_" + imgObj.docSplitNo;
        $("#" + treeNode.tId + IDMark_Queue).append($li);
    }
    if (query == 'query' && !imgObj.uploadFlag) {
        $("." + btnId).hide();
    }
    var uploader = WebUploader
        .create({
            // runtimeOrder:"flash",
            // 选完文件后，是否自动上传。
            auto : true,
            // swf文件路径
            swf : '/factor/component/doc/webUpload/webuploader/Uploader.swf',
            // 文件接收服务端。
            server : webPath + '/docUpLoad/uploadFileToService',
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick : '#' + btnId,
            // 只允许上传jpg和gif图片。
            accept : {
                title : 'Images',
                extensions : 'jpeg,png,gif,jpg,ppt,pptx,doc,docx,pdf,xls,xlsx,wps,zip,rar,mp3,wav,pbm,bmp,jpe,tif,avi,rmvb,rm,asf,divx,mpg,mpeg,mpe,wmv,mp4,mkv,vob',
                mimeTypes : 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.presentationml.presentation,image/png,image/x-portable-bitmap,image/bmp,audio/x-wav,audio/mpeg,application/zip,application/rar,image/tiff,image/jpeg,image/gif,application/msword,application/pdf,application/vnd.ms-excel,application/vnd.ms-powerpoint,application/vnd.ms-works,video/avi,application/vnd.rn-realmedia-vbr,video/x-ms-asf,video/mpeg,application/vnd.openxmlformats-officedocument.wordprocessingml.document,video/mp4,application/vnd.rn-realmedia,video/x-ms-wmv,video/x-matroska'
            },
            fileSizeLimit : fileSize,// 限制文件大小为fileSize(M)
            // 并发
            threads : 1,
            formData : imgObj,
            fileVal : "upload",
            duplicate : false,
            resize : false,
            // // 开起分片上传。
            // chunked: true,
            // sendAsBinary:true,
            // 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
            allowMagnify : false
        });
    // 当有文件添加进来时执行，负责view的创建
    function addFile(file, response, treeId, treeNode, imgObj) {
        var newNode = response.docManage;
        newNode.imgId = newNode.docNo;
        newNode.name = newNode.docName;
        newNode.formId = treeNode.formId;
        newNode.cusNo = treeNode.cusNo;
        newNode.docSplitName = imgObj.docSplitName;
        newNode.docDesc = imgObj.docDesc;
        newNode.ifMustInput=imgObj.ifMustInput;

        if (newNode.formId == undefined) {
            newNode.formId = imgObj.formId;
        }
        if (treeNode.relNo == undefined) {
            newNode.relNo = imgObj.relNo;
        }
        if (file.type.indexOf("image") != -1) {
            newNode.src = newNode.docAddr;
        } else {
            // 图片传路径，其他的传文件类型即可。
            newNode.src = "file." + file.ext;
        }
        newNode.rotation = 0;
        // treeNode.imgs.push(newNode);
        // 将新增的放到最前面
        var index = 0;
        for (var i = 0; i < treeNode.imgs.length; i++) {
            if (treeNode.imgs[i].docSplitNo == newNode.docSplitNo) {
                index = i;
            }
        }
        if (treeNode.imgs[index].imgId == "") {
            treeNode.imgs.splice(0, 0, newNode);
            treeNode.imgs.remove(index + 1);
        } else {
            treeNode.imgs.splice(0, 0, newNode);
        }
    }
    // 当文件被移除队列后触发
    uploader.onFileDequeued = function(file) {
        notie.alert(1, '取消上传' + file.name + '!', 1);
        // console.log('取消上传'+file.name);
    };
    // 当有文件添加进来的时候
    uploader.on('fileQueued', function(file) {
        uploadCompFlag = false;
        percentages[file.id] = [ file.size, 0 ];
        if (file.type.indexOf("image") == -1) {
            var fileExt = file.ext;
            if (".doc|.docx|".indexOf(fileExt) > -1) {
                src = webPath+"/component/doc/webUpload/image/word.png";
            } else if (".ppt|.pptx|".indexOf(fileExt) > -1) {
                src = webPath+"/component/doc/webUpload/image/ppt.png";
            } else if (".xls|.xlsx|".indexOf(fileExt) > -1) {
                src = webPath+"/component/doc/webUpload/image/xls.png";
            } else if (".pdf".indexOf(fileExt) > -1) {
                src = webPath+"/component/doc/webUpload/image/pdf.png";
            } else {
                src = webPath+"/component/doc/webUpload/image/file.png";
            }
            viewImg($li, treeId, treeNode, imgObj, uploader, file, src);
        } else {
            uploader.makeThumb(file,
                function(error, ret) {
                    if (error) {
                        // console.log("预览错误");
                        notie.alert(3, file.name + '预览错误!', 1.5);
                    } else {
                        viewImg($li, treeId, treeNode, imgObj, uploader,
                            file, ret);
                    }
                });
        }
    });

    uploader.on("error", function(type) {
        if (type == "Q_EXCEED_SIZE_LIMIT") {
            window.top.alert("上传的文件不能大于"+fileSize/1024/1024+"兆", 0);
        }
    });

    // 当有一批文件添加进来的时候
    uploader.on('filesQueued', function(files) {
    });
    // 当开始上传流程时触发。
    uploader.on('startUpload', function() {
    });
    // 某个文件开始上传前触发，一个文件只会触发一次。
    uploader.on('uploadStart', function(file) {
        // console.log('正在上传'+file.name);
        $("#" + treeNode.tId + IDMark_Queue + " #" + file.id).find(".progress")
            .children().css('display', 'block');
    });
    uploader.on('uploadProgress', function(file, percentage) {
        percentages[file.id][1] = percentage;
        // console.log("正在上传："+percentage);
        updateTotalProgress(treeNode.tId, file);
    });
    // 文件上传成功，给item添加成功class, 用样式标记上传成功。
    uploader.on('uploadSuccess', function(file, response) {
        // console.log(file.name+"上传成功");
        // notie.alert_hide();
        // console.log(response);
        if (response.flag) {
            notie.alert(1, file.name + '上传成功!', 1);
            percentages[file.id][1] = 1;
            addFile(file, response, treeId, treeNode, imgObj);
        } else {
            notie.alert(3, file.name + '上传失败!', 1.5);
            fileUplaodFailed(file, uploader, treeNode);
        }
    });

    uploader.on('uploadAccept', function(obj, ret) {
        if (!ret.flag) {
            notie.alert(3, obj.file.name + '上传失败!', 1.5);
            obj.file.msg = ret.msg;
            fileUplaodFailed(obj.file, uploader, treeNode);
        }
    });
    uploader.on('uploadError', function(file, response) {
        notie.alert(3, file.name + '上传失败!', 1.5);
        file.msg = response + "有错误!";
        fileUplaodFailed(file, uploader, treeNode);
    });

    // 文件上传完成时触发，不论是否成功
    uploader.on('uploadComplete', function(file) {
        var spans = $("#" + treeNode.tId + IDMark_Queue + " #" + file.id).find(
            ".progress").children();
        if (file.getStatus() == "complete") {
            spans.eq(0).text('上传成功');
            $('#' + file.id).find('.file-panel').remove();
        } else {
        }
        spans.eq(1).hide().css('width', '0px');
    });
    // 当所有文件上传结束时触发。
    uploader.on('uploadFinished', function() {
        uploadCompFlag = true;
        $.each(percentages, function(k, v) {
            if (v[1] == 0) {
                uploadCompFlag = false;
            }
        });
        if (uploadCompFlag) {
            var treeObj = $.fn.zTree.getZTreeObj(treeId);
            treeObj.refresh();
        }
    });

    function updateTotalProgress(tId, file) {
        var loaded = 0, total = 0, spans = $(
            "#" + tId + IDMark_Queue + " #" + file.id).find(".progress")
            .children(), percent;
        percent = percentages[file.id][1];
        spans.eq(0).text(Math.round(percent * 100) + '%');
        spans.eq(1).css('width', Math.round(percent * 100) + '%');
    }
    function fileUplaodFailed(file, uploader, treeNode) {
        file.setStatus("error");
        percentages[file.id][1] = 0;
        var spans = $("#" + treeNode.tId + IDMark_Queue + " #" + file.id).find(
            ".progress").children();
        spans.eq(0).html('上传失败' + "<br />" + file.msg);
        spans.eq(0).css({
            "background-color" : "rgb(187, 36, 11)",
            "color" : "#ffffff"
        });
        if ($("#" + file.id).find(".file-panel .rotateLeft").length > 0) {
            return false;
        } else {
            var $retry = $('<span  class="rotateLeft" title="重新上传"></span>');
            $("#" + file.id).find(".file-panel").append($retry);
            $retry.bind("click", function() {
                uploader.retry(file);
            });
        }
    }
}

function rotationImg(wrap, deg) {
    if (supportTransition) {
        deg = 'rotate(' + deg + 'deg)';
        wrap.css({
            '-webkit-transform' : deg,
            '-mos-transform' : deg,
            '-o-transform' : deg,
            'transform' : deg
        });
    } else {
        wrap.css('filter',
            'progid:DXImageTransform.Microsoft.BasicImage(rotation='
            + (~~((deg / 90) % 4 + 4) % 4) + ')');
    }
}

// 负责view的销毁
function removeFile(treeId, treeNode, i) {
    var docSplitNo = treeNode.imgs[i].docSplitNo;
    var tmpImg = treeNode.imgs[i];

    treeNode.imgs.remove(i);
    var count = 0;
    for (var j = 0; j < treeNode.imgs.length; j++) {
        var tmpObj = treeNode.imgs[j];
        if (tmpObj.docSplitNo == docSplitNo) {
            count++;
        }
    }
    var treeObj = $.fn.zTree.getZTreeObj(treeId);
    if (count == 0) {
        var newNode = tmpImg;
        newNode.imgId = "";
        treeNode.imgs.push(newNode);
    }
    treeObj.refresh();
}
// 图片放大
var view_div_css;
function enlargeImg(imgObj, treeNode, obj) {
    var $view_div = $("#view-imgs");
    var $img = $("<img />");
    $img.attr("src", imgObj.src);
    sizeImg($img);
    $img.appendTo($view_div.find(".enlarge-img"));
    var $this = $("#" + imgObj.imgId).find("img");
    var vT, vL, vW, vH;
    vW = $this.outerWidth(true);
    vH = $this.outerHeight(true);
    vT = $this.offset().top;
    vL = $this.offset().left;
    view_div_css = {
        "width" : vW + 10,
        "height" : vH + 10,
        "top" : vT,
        "left" : vL
    };
    var view_div_open = {
        "width" : "100%",
        "height" : "100%",
        "top" : 0,
        "left" : 0
    };
    $view_div.find(".enlarge-img").delay(500).fadeIn();
    $view_div.find(".img-tools").delay(500).fadeIn();
    $view_div.css(view_div_css);
    $view_div.animate(view_div_open, "slow");
    var deg = imgObj.rotation;
    $("#view-imgs .img-tools .title").html(imgObj.name);
    $("#view-imgs .img-tools span").unbind();
    $("#view-imgs .img-tools span").bind('click', function() {
        var index = $(this).index();
        switch (index) {
            case 0:
                deg += 90;
                break;
            case 1:
                deg -= 90;
                break;
        }
        rotationImg($img, deg);
    });
    $("#view-imgs .img-tools .view-img").unbind();
    $("#view-imgs .img-tools .view-img").bind('click', function() {
        var index = $(this).index();
        switch (index) {
            case 3:
                lastImg(imgObj, treeNode, $img);
                break;
            case 4:
                nextImg(imgObj, treeNode, $img);
                break;
        }
    });
    rotationImg($img, imgObj.rotation);
}
function close_view() {
    var $this = $("#view-imgs");
    $this.find(".img-tools").hide();
    var $img = $this.find("img");
    $this.animate(view_div_css, "slow");
    vT = view_div_css.height / 2;
    vL = view_div_css.width / 2;
    $this.find(".enlarge-img").delay(500).fadeOut();
    $this.animate({
        "width" : 0,
        "height" : 0,
        top : '+=' + vT + 'px',
        left : '+=' + vL + 'px'
    }, "slow", function() {
        $img.remove();
    });
}
function lastImg(imgObj, treeNode, $img) {
    $.each(treeNode.imgs, function(i, obj) {
        if (imgObj == obj) {
            if (i > 0) {
                changeImg(treeNode.imgs[i - 1], treeNode);
            }
        }
    });
}
function nextImg(imgObj, treeNode, $img) {
    $.each(treeNode.imgs, function(i, obj) {
        if (imgObj == obj) {
            if (i < treeNode.imgs.length - 1) {
                changeImg(treeNode.imgs[i + 1], treeNode);
            }
        }
    });
}
function changeImg(imgObj, treeNode) {
    var vT, vL, vW, vH;
    var $this = $("#" + imgObj.imgId).find("img");
    vW = $this.outerWidth(true);
    vH = $this.outerHeight(true);
    vT = $this.offset().top;
    vL = $this.offset().left;
    view_div_css = {
        "width" : vW + 10,
        "height" : vH + 10,
        "top" : vT,
        "left" : vL
    };
    var $view_div = $("#view-imgs");
    var $img = $view_div.find("img");
    $img.attr("src", imgObj.src);
    sizeImg($img);
    var deg = imgObj.rotation;
    $("#view-imgs .img-tools .title").html(imgObj.name);
    $("#view-imgs .img-tools span").unbind();
    $("#view-imgs .img-tools span").bind('click', function() {
        var index = $(this).index();
        switch (index) {
            case 0:
                deg += 90;
                break;
            case 1:
                deg -= 90;
                break;
        }

        rotationImg($img, deg);
    });
    $("#view-imgs .img-tools .view-img").unbind();
    $("#view-imgs .img-tools .view-img").bind('click', function() {
        var index = $(this).index();
        switch (index) {
            case 3:
                lastImg(imgObj, treeNode, $img);
                break;
            case 4:
                nextImg(imgObj, treeNode, $img);
                break;
        }
    });
    rotationImg($img, imgObj.rotation);
}
function sizeImg(imgObj) {
    var _w = parseInt($(window).width()); // 获取浏览器的宽度
    var _h = parseInt($(window).height()); // 获取浏览器的宽度
    var realWidth; // 真实的宽度
    var realHeight; // 真实的高度
    var $img = $(imgObj);
    $("<img/>").attr("src", $img.attr("src")).load(
        function() {
            realWidth = this.width;
            realHeight = this.height;
            if (realWidth >= _w) {
                $img.css("width", "100%").css("height", "auto");
            } else { // 如果小于浏览器的宽度按照原尺寸显示
                $img.css("width", realWidth + 'px').css("height",
                    realHeight + 'px');
            }
            if (realHeight < _h) {
                $img.css("margin", "calc(25% - " + realHeight / 2
                    + "px) auto 0px auto");
            } else {
                $img.css("margin", "0 auto");
            }
        });
}
function uploadDiv(obj) {
    $(obj).parents(".upload-li").find("input.webuploader-element-invisible")
        .click();
}

function myerror(type) {

}
//客户主视图的正序排序
function rank(num,type) {
    var tableId,tableType;
    if(type=="2"){//客户视角下
        var url = webPath + "/docManage/getCusDocNodesForListAjax?"+docParm;
        tableId = "tableDocManageList";
        tableType = "thirdTableTag";
    }else if(type=="3"){//业务视角下
        url = webPath + "/docManage/getDocNodesForListAjax?" + docParm;
        tableId = "tableDocManageList";
        tableType = "thirdTableTag";
    }
    var postData = new Object();
    postData.ajaxData = getFilterValArr();
    postData.pageNo = 1;
    postData.eadis_page = 1;
    postData.pageSize = 10000;
    postData.tableId = tableId;
    postData.tableType = tableType;
    postData.num = num;
    var filterInInput = $('input[name=filter_in_input]').val();
    if (filterInInput != null && filterInInput != undefined && filterInInput != "") {
        postData.filterInInput = filterInInput;
    }
    $.ajax({
        url: url,
        type: "POST",
        data: postData,
        dataType: "json",
        success: function (data) {
            query = data.query;
            var html = data.tableHtml;
            $("#content").empty();
            html = html.replace("display:none;", "");
            html = html.replace("name=\"docSplitName\"", "name=\"docSplitName\" onclick=\"rankDesc(2,"+type+")\"; style=\" cursor: pointer;\"");
            html = html.replace("文件分类", "文件分类<i class=\"i i-arrow-updown\"></i>");

            html = html.replace("name=\"regTime\"", "name=\"regTime\" onclick=\"rankDesc(4,"+type+")\"; style=\" cursor: pointer;\"");
            html = html.replace("上传时间", "上传时间<i class=\"i i-arrow-updown\"></i>");

            html = html.replace("name=\"docSize\"", "name=\"docSize\" onclick=\"rankDesc(6,"+type+")\"; style=\" cursor: pointer;\"");
            html = html.replace("文件大小", "文件大小<i class=\"i i-arrow-updown\"></i>");
            $("#content").append(html);
            addFileToList();
            //处理要件的删除权限按钮
            if(typeof(queryFile)!="undefined"){
                if(queryFile=="query"){
                    $(".formAdd-btnFile").hide();
                    //删除
                    $(".delBtn").html("删除");
                }

            }
        },
    });
}

//客户主视图的倒序排序
function rankDesc(num,type) {
    var tableId,tableType;
    var url;
    if(type=="2"){
        url = webPath + "/docManage/getCusDocNodesForListAjax?"+docParm;
        tableId = "tableDocManageList";
        tableType = "thirdTableTag";
    }else if(type=="3"){
        url = webPath + "/docManage/getDocNodesForListAjax?" + docParm;
        tableId = "tableDocManageList";
        tableType = "thirdTableTag";
    }
    var postData = new Object();
    postData.ajaxData = getFilterValArr();
    postData.pageNo = 1;
    postData.eadis_page = 1;
    postData.pageSize = 10000;
    postData.tableId = tableId;
    postData.tableType = tableType;
    postData.num = num;
    var filterInInput = $('input[name=filter_in_input]').val();
    if (filterInInput != null && filterInInput != undefined && filterInInput != "") {
        postData.filterInInput = filterInInput;
    }
    $.ajax({
        url: url,
        type: "POST",
        data: postData,
        dataType: "json",
        success: function (data) {
            query = data.query;
            var html = data.tableHtml;
            $("#content").empty();
            html = html.replace("display:none;", "");
            html = html.replace("name=\"docSplitName\"", "name=\"docSplitName\" onclick=\"rank(1,"+type+")\"; style=\" cursor: pointer;\"");
            html = html.replace("文件分类", "文件分类<i class=\"i i-arrow-updown\"></i>");

            html = html.replace("name=\"regTime\"", "name=\"regTime\" onclick=\"rank(3,"+type+")\"; style=\" cursor: pointer;\"");
            html = html.replace("上传时间", "上传时间<i class=\"i i-arrow-updown\"></i>");

            html = html.replace("name=\"docSize\"", "name=\"docSize\" onclick=\"rank(5,"+type+")\"; style=\" cursor: pointer;\"");
            html = html.replace("文件大小", "文件大小<i class=\"i i-arrow-updown\"></i>");
            $("#content").append(html);
            addFileToList();
            //处理要件的删除权限按钮
            if(typeof(queryFile)!="undefined"){
                if(queryFile=="query"){
                    $(".formAdd-btnFile").hide();
                    //删除
                    $(".delBtn").html("删除");
                }

            }
        },
    });
}

/*
 * 方法:Array.remove(dx) 通过遍历,重构数组 功能:删除数组元素. 参数:dx删除元素的下标.
 */
Array.prototype.remove = function(dx) {
    if (isNaN(dx) || dx > this.length) {
        return false;
    }
    for (var i = 0, n = 0; i < this.length; i++) {
        if (this[i] != this[dx]) {
            this[n++] = this[i];
        }
    }
    this.length -= 1;

};




