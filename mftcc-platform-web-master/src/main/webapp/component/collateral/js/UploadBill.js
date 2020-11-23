var UploadBill = function(window,$){
	var _init = function() {
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced : {
                theme : "minimal-dark",
                updateOnContentResize : true
            }
        });
    }
		var _initupload = function () {
            uploader = WebUploader.create({
                auto : true,
                // swf文件路径
                swf : webPath+'/UIplug/webuploader/js/Uploader.swf',
                // 文件接收服务端。
                server : webPath+'/pledgeBaseInfo/importBillExcel',
                formData : {},
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                //pick : '#picker',
                pick : '#picker-outer',
                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                fileVal : 'file',
                resize : false,
                dupliacate : true,
                fileNumLimit : 1,   //验证文件总数量, 超出则不允许加入队列
                accept : {
                    title : 'Excl',
                    extensions : 'xls,xlsx',
                    mimeTypes : '.xls,.xlsx'
                }
            });
            var $list = $("#thelist");
            //当文件被加入队列之前触 此事件的handler返回值为false，则此文件不会被添加进入队列
            uploader.on('beforeFileQueued', function(file) {
                var files = this.getFiles();
                for ( var i = 0; i < files.length; i++) {
                    this.removeFile(files[i].id, true);
                }
                $("#showMsg").empty();
                $("input[name=uploadFile]").val(file.name);
            });
            //当文件被加入队列以后触发
            uploader.on('fileQueued', function(file) {
                /*var fileId = file.id;
                 //$("input[name=uploadFile]").attr("id",fileId);
                 $("input[name=uploadFile]").val(file.name);
                 $list.append('<a class="delFile" style="float: right; margin-top: -27px;padding-left: 335px;cursor: pointer;">删除</a>');
                //删除文件操作
                $(".delFile").click(function(){
                    var showMsg = $("#showMsg").html();
                    $("#showMsg").empty();
                    var fileName = $("input[name=finData]").val();
                    if(showMsg != ""){
                        $list.find("a").remove();
                        $("input[name=uploadFile]").val("");
                        uploader.removeFile(fileId);
                    }else{
                        $.ajax({
                            type:"post",
                            url:webPath+"/cusFinUploadFiles/deletUploadFileAjax",
                            async:false,
                            data:{ajaxData:fileName},
                            success:function(data){
                                if(data.flag=="success"){
                                    alert(data.msg,1);
                                    $list.find("a").remove();
                                    $("input[name=uploadFile]").val("");
                                    uploader.removeFile(fileId);
                                }else if(data.flag=="error"){
                                    alert(data.msg,0);
                                }
                            },error:function(){
                                alert(top.getMessage("FAILED_DELETE"),0);
                            }
                        });
                    }
                });*/
            });
            //当文件被移除队列后触发
            uploader.on('fileDequeued', function(file) {
                $("#showInfo-div").empty();
                $("#"+file.id).remove();
            });
            //上传过程中触发，携带上传进度
            uploader.on('uploadProgress', function(file,percentage) {
                // LoadingAnimate.start();
                // var $li = $( '#'+file.id ),
                //     $percent = $li.find('.progress .progress-bar');
                // // 避免重复创建
                // if (!$percent.length ) {
                //     $percent = $('<div class="progress progress-striped active">' +
                //         '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                //         '</div>' +
                //         '</div>').appendTo( $li ).find('.progress-bar');
                // }
                // $percent.css( 'width', percentage * 100 + '%' );
            });
            //当文件上传成功时触发
            uploader.on('uploadSuccess', function(file,data) {
                // LoadingAnimate.stop();
                if(data.flag=="success"){
                    top.isUpload = true;  //财务报表上传成标志
                    top.infIntegrity = data.infIntegrity;
                    $(".submitdata").addClass("success");
                    $("#uploadBillList").html(data.tableHtml);
                }else if(data.flag=="error"){
                    LoadingAnimate.stop();
                    top.isUpload = false; //财务报表上传成标志
                    $("#showMsg").empty();
                    $("#showMsg").html(data.resMsg);
                    uploader.removeFile(file.id);
                    $('#'+file.id).find('p.state').text('上传出错');
                }
            });
			//当文件上传出错时触发
			uploader.on('uploadError', function(file) {
				$('#'+file.id ).find('p.state').text('上传出错');
			});
			//不管成功或者失败，文件上传完成时触发
			uploader.on('uploadComplete', function(file) {
				$( '#'+file.id ).find('.progress').fadeOut();
			});
		};



	var _uploadFinReport = function(){
        uploader.options.formData= {appId:appId,collateralId:collateralId};
        $("#picker-outer").find("input.webuploader-element-invisible").click();
	}
	var _saveBill = function(){
        var datas = [];
        var size =0;
        $("#uploadBillList").find("tbody tr")
            .each(
                function(index) {
                    var entity = {};
                    $thisTr = $(this);
                    entity.instockData = $thisTr.find("td").eq(0).html();
                    entity.billName =  $thisTr.find("td").eq(1).html();
                    entity.ascriptionBus = $thisTr.find("td").eq(2).html();
                    entity.storehouse = $thisTr.find("td").eq(3).html();
                    entity.unitPrice =  $thisTr.find("td").eq(4).html().replace(/,/g, "");
                    entity.singleWeight = $thisTr.find("td").eq(5).html().replace(/,/g, "");
                    datas.push(entity);
                    size =size+1;
                });
            if(size<=0){
                alert("请上传合同清单后再进行保存", 0);
            }
        $.ajax({
            url : webPath+"/pledgeBaseInfo/saveBillAjax",
            data : {appId : appId,ajaxDataList : JSON.stringify(datas),collateralId : collateralId},
            success : function(data) {
                if (data.flag == "success") {
                    // 关闭当前弹层。
                    top.addFlag = true;
                    if (data.htmlStrFlag == "1") {
                        top.htmlStrFlag = true;
                        top.htmlString = data.htmlStr;
                        top.tableName="pledge_base_info_bill";
                        top.pledgeNo=data.pledgeNo;
                        top.collateralNo=data.pledgeNo
                        top.flag="add";
                        top.showType="2";
                        top.action="PledgeBaseInfoBillAction";
                        top.title ="合同清单";
                    }
                    myclose_click();
                } else {
                    DIALOG.error(top.getMessage("FAILED_SAVE_CONTENT",{content : "", reason: data.msg}));
                }
            },error : function() {
                alert(top.getMessage("FAILED_SAVE"), 0);
            }
        });

    }
    //下载模板
    var _exportExcel = function(){
        $.ajax({
            url:webPath+"/pledgeBaseInfo/exportExcelAjax",
            dataType:"json",
            type:"POST",
            success:function(data){
                if(data.flag == "success"){
                    if(data.exportFlag == "success"){
                        window.top.location.href = encodeURI(webPath+"/docUpLoad/getFileDownload_new?path="+data.path+"&downType=acountBill");
                    }else{
                        alert(data.msg,0);
                    }
                }else{
                    alert(data.msg,0);
                }
            },error:function(){
                LoadingAnimate.stop();
                alert(data.msg,0);
            }
        });
    };
	return{
		init:_init,
		uploadFinReport:_uploadFinReport,
        initupload:_initupload,
        saveBill:_saveBill,
        exportExcel:_exportExcel
	};
}(window,jQuery);