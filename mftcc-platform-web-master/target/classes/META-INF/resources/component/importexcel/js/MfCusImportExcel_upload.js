var MfCusImportExcel_upload = function(window,$){
    var _init = function(){
        $("body").mCustomScrollbar({
            advanced:{
                updateOnBrowserResize:true
            },
            autoHideScrollbar: true
        });
        _getExcelList();
    };


    var _initUpload = function(){
        myCustomScrollbarForForm({
            obj:".scroll-content",
            advanced:{
                theme:"minimal-dark",
                updateOnContentResize:true
            }
        });

        //选项卡添加切换事件
        _liClick();
        $("input[name=uploadFile]").attr("style","width: 500px;");
        //上传操作
        var uploader = WebUploader.create({
            auto : false,
            // swf文件路径
            swf : webPath+'/UIplug/webuploader/js/Uploader.swf',
            // 文件接收服务端。
            server : webPath+'/mfCusImportExcel/checkImportExcel',
            formData: {
                importType: 1,
                dataModel: $("[name=dataModel]").val(),
                remark:"",
                formObjData:"",
            },
            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            //pick : '#picker',
            pick: {
                id: '#picker',
                name:"file", //这个地方 name 没什么用，虽然打开调试器，input的名字确实改过来了。但是提交到后台取不到文件。如果想自定义file的name属性，还是要和fileVal 配合使用。
                label: '点击选择',
                multiple:true //默认为true，就是可以多选
            },
            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            fileVal : 'mfCusImportExcel',
            resize : false,
            fileNumLimit : 5,   //验证文件总数量, 超出则不允许加入队列
            accept : {
                title : 'Excl',
                extensions : 'xls,xlsx',
                mimeTypes : '.xls,.xlsx'
            },
        });
        var $list = $("#thelist");
        //当文件被加入队列之前触 此事件的handler返回值为false，则此文件不会被添加进入队列
        uploader.on('beforeFileQueued', function(file) {
            var fileName = file.name;
            var upLoadFileNameExt = fileName.split(".")[1];
            if (upLoadFileNameExt!="xlsx"&&upLoadFileNameExt!="xls") {
                window.top.alert("上传文件格式不正确！",0);
            }else {
                $("input[name=uploadFile]").val(file.name);
            }
        });
        //当文件被移除队列后触发
        uploader.on('fileDequeued', function(file) {
            $("#showInfo-div").empty();
            $("#"+file.id).remove();
        });
        //上传过程中触发，携带上传进度
        uploader.on('uploadProgress', function(file,percentage) {
            LoadingAnimate.start();
            var $li = $( '#'+file.id ),
                $percent = $li.find('.progress .progress-bar');
            // 避免重复创建
            if (!$percent.length ) {
                $percent = $('<div class="progress progress-striped active">' +
                    '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                    '</div>' +
                    '</div>').appendTo( $li ).find('.progress-bar');
            }
            $percent.css( 'width', percentage * 100 + '%' );
        });
        //当文件上传成功时触发
        uploader.on('uploadSuccess', function(file,data) {
            LoadingAnimate.stop();
            if (data.readFlag){
                hisId = data.id;
                _getCensusListHtmlAjax();
                $(".showprogress ul").find("li[name=checkImportDataLi]").click();
                uploader.removeFile(file.id);
            }else if (!data.readFlag) {
                window.top.alert(data.msg,0);
            }
        });

        //当文件上传出错时触发
        uploader.on('uploadError', function(file) {
            alert(data.result);
            $('#'+file.id ).find('p.state').text('文件导入出错!');
        });

        //不管成功或者失败，文件上传完成时触发
        /*	uploader.on('uploadComplete', function(file) {
                $( '#'+file.id ).find('.progress').fadeOut();
            });*/

        //保存按钮绑定点击事件
        $(".next").bind("click",function(){
            if (importStage == "uploadFileLi"){
                var fileName = $("input[name=uploadFile]").val();
                if (fileName==""){
                    window.top.alert("未选择上传文件格！",0);
                    return;
                }
                uploader.options.formData.dataModel = $("[name=dataModel]").val();
                uploader.options.formData.remark = $("[name=remark]").val();
                uploader.options.formData.formObjData = JSON.stringify($("#saveUploadFileForm").serializeArray());;
                uploader.upload();
            }
        });
    };

    //给选项卡添加事件
    var _liClick=function(){
        $(".showprogress ul li").click(function(){
            var $this = $(this);
            var name = $this.attr("name");
            var changeFlag =  false;
            if (name == "checkImportDataLi") {
                if (_isTrue($this, "uploadFileLi")) {
                    changeFlag = true;
                } else {
                    changeFlag = false;
                }
            } else if (name == "uploadSuccessLi") {
                if (_isTrue($this, "uploadFileLi")) {
                    changeFlag = true;
                } else {
                    changeFlag = false;
                }
            } else {
                changeFlag = true;
            }
            if(changeFlag){
                importStage = $(".selected").attr("name");
                $("#"+importStage+"-div").attr("style","display: none");
                $(".showprogress ul li[name="+importStage+"]").addClass("success");
                if (importStage == "uploadFileLi"){
                    $("#checkImportDataLi-div").attr("style","display: block");
                }else if (importStage == "checkImportDataLi"){
                    $("#uploadSuccessLi-div").attr("style","display: block");
                }else if (importStage == "uploadSuccessLi"){
                    $("#uploadSuccessLi-div").attr("style","display: block");
                }

                $(".selected").removeClass("selected");
                $(this).addClass("selected");
                //var liIndex = $(this).index();
                //$(".content_ul").animate({left:"-"+(liIndex*100)+"%"});
            }else{
                //alert(top.getMessage("FIRST_OPERATION","前面的步骤"),0);
            }
        });
    };

    //判断选项卡内容是否已填写
    var _isTrue=function($this,str){
        var $obj = $this.parent().find("li[name="+str+"]");
        if($obj.length>0){
            if($obj.hasClass("success")){
                return true;
            }else{
                return false;
            }
        }else{
            return true;
        }
    };
    //保存表单时保存上传的excel文件中的数据到数据库中
    var _uploadAndSubmitData = function(finDataVal,cusNo,finRptType,finRptDate,cusName) {
        var url =webPath+"/importexcel/mfCusImportExcel/checkImportExcel";
        $.ajax({
            type:"post",
            url:url,
            async:false,
            data:{
                allPath:finDataVal,
                cusNo:cusNo,
                finRptType:finRptType,
                finRptDate:finRptDate,
                cusName:cusName
            },
            success:function(data){
                if(data.flag=="success"){
                    top.addFlag = true;
                    //触发回调函数
                    $(top.window.document).find("#showDialog .close").click();
                }else if(data.flag=="error"){
                    alert(data.msg,0);
                }
            }
        });
    };

    var _updateHis = function(obj,url){

        $.ajax({
            type:"post",
            url:url,
            async:false,
            success:function(data){
                if(data.flag = 'success'){
                    window.location.reload();
                }else{
                    alert("删除失败,请联系管理员",0);
                }
            }
        });

    }

    var _getExcelList = function(){
        var url = webPath+"/mfCusImportExcel/getHisList";
        $(function(){
            var table = "tableMfCusImportExcelHis";
            myCustomScrollbar({
                obj : "#content",//页面内容绑定的id
                url : url,//列表数据查询的url
                tableId : table,//列表数据查询的table编号
                tableType : "tableTag",//table所需解析标签的种类
                pageSize : 30,//加载默认行数(不填为系统默认行数)
                topHeight : 100 // 顶部区域的高度，用于计算列表区域高度。
            });
        });
    };
    function _againDoc(obj,url){
        window.location.href = webPath+url;
    };

    //跳转历史数据导入校验结果
    var _uploadHisResult = function (obj,url) {
        top.openBigForm(webPath+url,"历史数据导入校验结果", function () {

        });
    };

    //跳转历史数据导入
    var _uploadExcel = function () {
        top.openBigForm(webPath+"/mfCusImportExcel/uploadExcel","历史数据导入", function () {
            _init();
        });
    };
    //临时表中历史数据导入到业务表
    var _saveByTemporaryAjax = function(){
        LoadingAnimate.start();
        $.ajax({
            type:"post",
            url:webPath+"/mfCusImportExcel/saveByTemporaryAjax?id="+hisId,
            dataType: "json",
            success:function(data){
                if(data.flag == 'success'){
                    LoadingAnimate.stop();
                    top.window.alert("数据导入成功!",1);
                    myclose_click();
                }else{
                    LoadingAnimate.stop();
                    alert(data.msg,0);
                }
            }
        });
    };
    //校验历史数据
    var _checkByTemporaryAjax = function(obj,url){
        LoadingAnimate.start();
        $.ajax({
            type:"post",
            url:webPath+"/mfCusImportExcel/checkByTemporaryAjax?id="+hisId,
            dataType: "json",
            success:function(data){
                LoadingAnimate.stop();
                if(data.flag == 'success'){
                    alert(data.msg,1);
                }else if(data.flag == 'error'){
                    alert(data.msg,0);
                }
                $(".showprogress ul").find("li[name=uploadSuccessLi]").click();
                _getCensusListHtmlAjax();
            }
        });
    };
    //下一步
    var _nextSubmit = function () {
        //根据选择卡选中状态获得当前历史数据导入阶段
        importStage = $(".selected").attr("name");
        $(".showprogress ul li[name="+importStage+"]").addClass("success");
        if (importStage == "uploadFileLi"){

        }else if (importStage == "checkImportDataLi"){
            _checkByTemporaryAjax();
        }else if (importStage == "uploadSuccessLi"){
            _saveByTemporaryAjax();
        }
    };
    //获得导入统计明细
    var _getCensusListHtmlAjax = function(){
        LoadingAnimate.start();
        $.ajax({
            type:"post",
            url:webPath+"/mfCusImportExcel/getCensusListHtmlAjax?id="+hisId,
            async:false,
            dataType: "json",
            success:function(data){
                if(data.flag == 'success'){
                    LoadingAnimate.stop();
                    $("#importCensusList").html(data.htmlStr);
                    importStage = $(".selected").attr("name");
                    //如果当前是导入成功的选择卡，则在导入成功页面展示导入统计明细结果
                    if (importStage == "uploadSuccessLi"){
                        $("#importCensusList").empty();
                        $("#importSuccessList").html(data.htmlStr);
                    }
                }else{
                    LoadingAnimate.stop();
                }
            }
        });
    };

    //初始化历史数据详情页面
    var _initDetailUpload = function(){
        //选项卡添加切换事件
        _liClick();
        _getCensusListHtmlAjax();
        $(".showprogress ul li[name=uploadFileLi]").addClass("success");
        $(".showprogress ul").find("li[name=checkImportDataLi]").click();
    };
    return{
        init:_init,
        uploadAndSubmitData:_uploadAndSubmitData,
        updateHis : _updateHis,
        againDoc : _againDoc,
        uploadExcel:_uploadExcel,
        initUpload:_initUpload,
        uploadHisResult:_uploadHisResult,
        saveByTemporaryAjax:_saveByTemporaryAjax,
        checkByTemporaryAjax:_checkByTemporaryAjax,
        nextSubmit:_nextSubmit,
        initDetailUpload:_initDetailUpload,
    };
}(window,jQuery);