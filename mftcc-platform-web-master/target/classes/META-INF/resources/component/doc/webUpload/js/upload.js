var uploadCompFlag = false;
var percentages = {};
var supportTransition = (function() {
		var s = document.createElement('p').style,
			r = 'transition' in s ||
			'WebkitTransition' in s ||
			'MozTransition' in s ||
			'msTransition' in s ||
			'OTransition' in s;
		s = null;
		return r;
	})();
	// 判断浏览器是否支持base64
var isSupportBase64;
// 优化retina, 在retina下这个值是2
var ratio = window.devicePixelRatio || 1,
	// 缩略图大小
	//thumbnailWidth = 110 * ratio,
	//thumbnailHeight = 110 * ratio;
	thumbnailWidth = 120,
	thumbnailHeight = 80;
var IDMark_upBtn = "_upBtn",
	IDMark_addLi = "_addLi";
	IDMark_A = "_a";
IDMark_Queue = "_queue";
var setting = {
	view: {
		showIcon: false,
		showLine: false,
		dblClickExpand: false,
		addDiyDom: addDiyDom
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		onClick: onClickUpload
	}
};
$(function() {
	console.log(zTreeNodes);
	var zTreeObj = $.fn.zTree.init($("#uploadTree"), setting, zTreeNodes);
	isSupportBase64 = (function() {
		var data = new Image();
		var support = true;
		data.onload = data.onerror = function() {
			if (this.width != 1 || this.height != 1) {
				support = false;
			}
		};
		data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
		return support;
	})();
//	console.log("base64:" + isSupportBase64);
	$(".switch").bind("click",function(){
		parent.window.adjustHeight();
	});
});

function onClickUpload(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("uploadTree");
	zTree.expandNode(treeNode);
}

function addDiyDom(treeId, treeNode) {
	$("#"+treeNode.tId+"_switch").remove();
	$("#"+treeNode.tId+"_ico").remove();
	var aObj = $("#" + treeNode.tId + IDMark_A);
	var titleStr = '';
	var spanObj =$("#" + treeNode.tId +"_span").html('<i class="i i-xing blockDian"></i>'+treeNode.docTypeName);
	spanObj.after('<button  class=" btn btn-link  formAdd-btn" data-toggle="collapse" data-target="#'+treeNode.tId+'_div"><i class="i i-close-up"></i><i class="i i-open-down">');
	if (treeNode.level == 0) {
		var uploadDivStr ='<div id="'+treeNode.tId+'_div" class="upload-div collapse in" aria-expanded="true"><ul id="' + treeNode.tId + IDMark_Queue + '" class="filelist '+ treeNode.tId + IDMark_Queue+'"></ul></div>'; 
		aObj.after(uploadDivStr);
		if (treeNode.imgs) {
			for (var i = 0; i < treeNode.imgs.length; i++) {
				addImgList(treeId, treeNode,treeNode.imgs[i],i);
			}
			var options = {};
			$('#' + treeNode.tId + IDMark_Queue).viewer(options); 
		}
	} 
	//docTypeName传空串，去掉标题
	if(treeNode.docTypeName==""){
		$("#"+treeNode.tId+"_a").remove();
	}
}
//预览文件
function viewImg(curliObj,treeId,treeNode,imgObj,uploader,file,src){
	var docSplitName = imgObj.docSplitName;
	if(docSplitName.length>5){
		docSplitName = docSplitName.substring(0, 5)+ "...";
	}
	
	var $li= $('<li class="upload-li" id=\''+file.id+'\'>'
			+'<div class="type-title" title=\''+imgObj.docSplitName+'\'>'+docSplitName+' <div class="btn btn-primary" onclick="uploadDiv(this);">上传</div></div>'
			+'<p class="progress">'
			+'<span class="text">等待上传</span>'
			+'<span class="percentage" >'
			+'</span>'
			+'</p>'
			+'<div class="opt-upload has-img">'
			
			+'</div>'
			+'</li>');
	var addliObj = $("li.upload-li.li_"+imgObj.docSplitNo+":last");
	addliObj.after($li);
	var $imgDiv = $("#"+file.id).find(".has-img");
	
	var $btns = $('<div class="file-panel">'
				 +'<span class="cancel" title="取消"></span>' 
				+'</div>');
	$imgDiv.append($btns);
	
	var img = $('<img class="img-item" src="' + src + '">');
	img.data("treeNode",imgObj);
	$imgDiv.append(img);
	$imgDiv.append('<div class="doc-title" title=\''+file.name+'\'>'+file.name+'</div>');
	
	$imgDiv.on('mouseenter', function() {
		$btns.stop().animate({
			height: 28
		});
	});

	$imgDiv.on('mouseleave', function() {
		$btns.stop().animate({
			height: 0
		});
	});
	$btns.find(".cancel").bind("click",function(){
		uploader.cancelFile( file );
		if(file.getStatus()=="cancelled"){
			$li.remove();
			delete percentages[ file.id ];
			uploadCompFlag = true;
			$.each(percentages,function(k,v){
				if(v[1]==0){
					uploadCompFlag = false;
				}
			});
			if(uploadCompFlag){
				var treeObj = $.fn.zTree.getZTreeObj(treeId);
				treeObj.refresh();
			}
		}
	});
}
function viewFile(imgInfo){
	var isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
	if(isWin){
		notie.alert(4, '如果无法正常打开文档，请在系统右侧工具栏下载pageoffice控件并安装，重启浏览器后再次打开文档', -1);
		$.ajax({
			type:"post",
			url:webPath+"/docUpLoad/viewFileAjax",
			dataType:"json",
			data:imgInfo,
			success:function(jsonData){
				if(jsonData.flag=="success"){
	//				window.top.dhccModalDialog.open(webPath + '/UIplug/PDFjs/web/viewer.html?file=' + webPath + '/file/'
	//				+jsonData.viewPath,jsonData.fileName,function(){},"90","90","400","300");
					var poCntObj = $.parseJSON(jsonData.poCnt);
					mfPageOffice.openPageOffice(poCntObj);
					
				}else{
					window.top.alert(jsonData.msg,0);
				}
			},
			error:function(){
				window.top.alert("不支持的文档类型或文件不存在！",0);
			},complete:function(){
				setTimeout(function() {
					notie.alert_hide();
				}, 1500);
			}
		});
	}else{
		window.top.alert("当前操作系统不支持在线预览，请下载到本地查阅！",0);
	}
}
//初始化图片
function addImgList(treeId, treeNode,imgObj,i) {
	var docSplitName = imgObj.docSplitName;
	var topDocSplitName = imgObj.docSplitName;
	if(docSplitName.length>7){
		docSplitName = docSplitName.substring(0, 7)+ "...";
	}
	if(topDocSplitName.length>4){
		topDocSplitName = topDocSplitName.substring(0, 4)+ "...";
	}
	var docDesc = imgObj.docDesc;
	if(imgObj.docDesc==""){
		docDesc = imgObj.docSplitName;
	}
	var $li,btnId;
	if(imgObj.imgId!=""){
		$li = $('<li class="upload-li li_'+imgObj.docSplitNo+'" id="' + imgObj.imgId + '">'
				+'<div class="type-title" title=\''+docDesc+'\'><span class="title-name">'+topDocSplitName+'</span> <div class="btn btn-primary btn_'+imgObj.docNo+'" onclick="uploadDiv(this);">上传</div></div>'
				+'<div class="opt-upload has-img">'
				+'</div>'
			+'</li>');
		$("#"+treeNode.tId +IDMark_Queue).append($li);
		
	
		var $btns = $('<div class="file-panel">'
				 +'<span class="cancel" title="删除">删除</span>'
				 +'<span class="rotateRight" title="向右旋转">向右旋转</span>'
				 +'<span class="rotateLeft" title="向左旋转">向左旋转</span>'
				 +'<span class="enlarge" title="下载">下载</span></div>');
	
		var $imgDiv = $("#"+imgObj.imgId, $("#"+treeNode.tId +IDMark_Queue)).find(".has-img");
		$imgDiv.append($btns);
		var  AllImgExt= ".jpg|.jpeg|.gif|.bmp|.png| ";
		var fileExt=imgObj.src.substr(Number(imgObj.src.lastIndexOf("."))).toLowerCase();
		var src = webPath+"/docUpLoad/viewImage?docNo="+imgObj.docNo+"&docBizNo="+imgObj.docBizNo;
		if(imgObj.compressPath){
			src = webPath+"/docUpLoad/viewCompressImage?docNo="+imgObj.docNo+"&docBizNo="+imgObj.docBizNo;
		}
		if(AllImgExt.indexOf(fileExt+ "|")==-1){
			if(".doc|.docx|".indexOf(fileExt)>-1){
				src = webPath +"/component/doc/webUpload/image/word.png";
			}else if(".ppt|.pptx|".indexOf(fileExt)>-1){
				src = webPath +"/component/doc/webUpload/image/ppt.png";
			}else if(".xls|.xlsx|".indexOf(fileExt)>-1){
				src = webPath + "/component/doc/webUpload/image/xls.png";
			}else if(".pdf".indexOf(fileExt)>-1){
				src = webPath+"/component/doc/webUpload/image/pdf.png";
			}else{
				src = webPath+"/component/doc/webUpload/image/file.png";
			}
		}
		var img = $('<img src="' + src + '">');
		img.data("treeNode",treeNode);
		img.data("imgInfo",imgObj);
		img.data("relUrl",webPath+"/docUpLoad/viewImage?docNo="+imgObj.docNo+"&docBizNo="+imgObj.docBizNo);
		$imgDiv.append(img);
		$imgDiv.append('<div class="doc-title" title=\''+imgObj.name+'\'>'+imgObj.name+'</div><div id="btn_'+imgObj.docNo+'" class="btn-upload hide"></div>');
		btnId = "btn_"+imgObj.docNo;

		img.unbind().bind("click",function(){
			// 这几种特殊格式的走特殊方法。
			if (".doc|.docx|.ppt|.pptx|.xls|.xlsx|.pdf".indexOf(fileExt) > -1) {
				var url = webPath+"/docUpLoad/fileDownload?docNo="+imgObj.docNo+"&docBizNo="+imgObj.docBizNo;
				//download_file(url);
				viewFile(imgObj);
				return false;
			}
		});
		var $wrap = img;
		$imgDiv.on('mouseenter', function() {
			$btns.stop().animate({
				height: 28
			});
		});
	
		$imgDiv.on('mouseleave', function() {
			$btns.stop().animate({
				height: 0
			});
		});
		var deg = imgObj.rotation;
		if(query=='query'&&!treeNode.uploadFlag){
			$btns.find(".cancel").hide();
		}
		$btns.on('click', 'span', function() {
			var index = $(this).index();
	
			switch (index) {
				case 0:
					//ajax异步删除成功后调用
					$.ajax({
							type: "post",
							dataType: 'json',
							url: webPath+"/docUpLoad/delFile",
							data:{docNo:imgObj.imgId,docBizNo:imgObj.docBizNo},
							async: false,
							contentType: "application/x-www-form-urlencoded; charset=UTF-8",
							success: function(data) {
								//删除
							//	console.log(data);
								notie.alert(1, imgObj.name+","+data.msg, 0.5);
								removeFile(treeId,treeNode,i);
							},
							error: function(XMLHttpRequest, textStatus, errorThrown) {
								console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
							}
						});
					return;
				case 1:
					deg += 90;
					rotationImg($wrap,deg);
					break;
				case 2:
					deg -= 90;
					rotationImg($wrap,deg);
					break;
				case 3:
					window.top.location.href = webPath+"/docUpLoad/fileDownload?docNo="+imgObj.docNo+"&docBizNo="+imgObj.docBizNo;
					break;
			}
			if (deg%360==0) {
				imgObj.rotation=0;
			}else{
				imgObj.rotation=deg;
			}
		});
	}else{
		var bitianSpan = "";
		if(imgObj.ifMustInput=="1"){
			bitianSpan ='<span class="i i-sanjiao color_red btspan0"></span><span class="btspan1">必</span>';
		}
		$li =$('<li  class="upload-li li_'+imgObj.docSplitNo+'">'
				+'<div class="type-title"></div>'
				+'<div class="opt-upload no-img">'
				+'<div title="'+docDesc+'" data-docname="'+imgObj.docSplitName+'">'+docSplitName+bitianSpan+'</div>'
				+'<div class="btn-upload-div color_theme btn_'+imgObj.docSplitNo+'" onclick="uploadDiv(this);"><i class="i i-jia3"></i>上传</div>'
				+'</div>'
				+'<div id="btn_'+imgObj.docSplitNo+'" class="btn-upload hide"></div>'
				+'</li>');
		btnId ="btn_"+imgObj.docSplitNo;
		$("#"+treeNode.tId +IDMark_Queue).append($li);
	}
	if(query=='query'&&!imgObj.uploadFlag){
		$("."+ btnId).hide();
	}
	var uploader = WebUploader.create({
		//runtimeOrder:"flash",
		// 选完文件后，是否自动上传。
		auto: true,
		// swf文件路径
		swf: '/factor/component/doc/webUpload/webuploader/Uploader.swf',
		// 文件接收服务端。
		server: webPath+'/docUpLoad/uploadFile',
		// 选择文件的按钮。可选。
		// 内部根据当前运行是创建，可能是input元素，也可能是flash.
		pick: '#' + btnId,
	    //只允许上传jpg和gif图片。
		accept: {
			title: 'Images',
			extensions: 'jpeg,png,gif,jpg,ppt,pptx,doc,docx,pdf,xls,xlsx,wps,zip,rar,mp3,wav,pbm,bmp,jpe,tif,avi,rmvb,rm,asf,divx,mpg,mpeg,mpe,wmv,mp4,mkv,vob',
			mimeTypes: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.presentationml.presentation,image/png,image/x-portable-bitmap,image/bmp,audio/x-wav,audio/mpeg,application/zip,application/rar,image/tiff,image/jpeg,image/gif,application/msword,application/pdf,application/vnd.ms-excel,application/vnd.ms-powerpoint,application/vnd.ms-works,video/avi,application/vnd.rn-realmedia-vbr,video/x-ms-asf,video/mpeg,application/vnd.openxmlformats-officedocument.wordprocessingml.document,video/mp4,application/vnd.rn-realmedia,video/x-ms-wmv,video/x-matroska'
		},
		fileSizeLimit: 100 * 1024 * 1024,// 限制文件大小为20M
		//并发
		threads:1,
		formData:imgObj,
		fileVal:"upload",
		duplicate: false,
	  	resize: false,
//	    // 开起分片上传。
//	    chunked: true,
		//sendAsBinary:true,
		// 是否允许放大，如果想要生成小图的时候不失真，此选项应该设置为false.
		allowMagnify: false
	});
	// 当有文件添加进来时执行，负责view的创建
	function addFile(file,response,treeId,treeNode,imgObj) {
		var newNode = response.docManage;
		newNode.imgId = newNode.docNo;
		newNode.name = newNode.docName;
		newNode.formId = treeNode.formId;
		newNode.cusNo = treeNode.cusNo;
		newNode.docSplitName=imgObj.docSplitName;
		newNode.docDesc=imgObj.docDesc;
		newNode.ifMustInput=imgObj.ifMustInput;
		
		if(newNode.formId == undefined){
			newNode.formId = imgObj.formId;
		}
		if(treeNode.relNo == undefined){
			newNode.relNo = imgObj.relNo;
		}
		if(file.type.indexOf("image")!=-1){
			newNode.src = newNode.docAddr;
		}else{
			// 图片传路径，其他的传文件类型即可。
			newNode.src = "file." + file.ext;
		}
		newNode.rotation = 0;
//		treeNode.imgs.push(newNode);
		//将新增的放到最前面
		var index=0;
		for (var i = 0; i < treeNode.imgs.length; i++) {
			if(treeNode.imgs[i].docSplitNo==newNode.docSplitNo){
				index = i;
			}
		}
		if(treeNode.imgs[index].imgId==""){
			treeNode.imgs.splice(0,0,newNode);
			treeNode.imgs.remove(index+1);
		}else{
			treeNode.imgs.splice(0,0,newNode);
		}
	}
	//当文件被移除队列后触发
	uploader.onFileDequeued = function(file) {
		 notie.alert(1, '取消上传'+file.name+'!', 1);
		//console.log('取消上传'+file.name);
	};
	// 当有文件添加进来的时候
	uploader.on('fileQueued', function(file) {
		uploadCompFlag = false;
		percentages[ file.id ] = [ file.size, 0 ];
		if(file.type.indexOf("image")==-1){
			var fileExt=file.ext;
			if(".doc|.docx|".indexOf(fileExt)>-1){
				src = webPath+"/component/doc/webUpload/image/word.png";
			}else if(".ppt|.pptx|".indexOf(fileExt)>-1){
				src = webPath+"/component/doc/webUpload/image/ppt.png";
			}else if(".xls|.xlsx|".indexOf(fileExt)>-1){
				src = webPath+"/component/doc/webUpload/image/xls.png";
			}else if(".pdf".indexOf(fileExt)>-1){
				src = webPath+"/component/doc/webUpload/image/pdf.png";
			}else{
				src = webPath+"/component/doc/webUpload/image/file.png";
			}
			viewImg($li,treeId, treeNode,imgObj,uploader,file, src);
		}else{
			uploader.makeThumb( file, function( error, ret ) {
				if ( error ) {
					//console.log("预览错误");
					notie.alert(3, file.name+'预览错误!', 1.5);
				} else {
					viewImg($li,treeId, treeNode,imgObj,uploader,file,ret);
				}
			});
		}
	});
	
	uploader.on("error", function(type){
		if(type=="Q_EXCEED_SIZE_LIMIT"){
			window.top.alert("上传的文件不能大于20兆",0);
		}
	});
	
	// 当有一批文件添加进来的时候
	uploader.on('filesQueued', function(files) {
	});
	//当开始上传流程时触发。
	uploader.on('startUpload', function() {
	});
	//某个文件开始上传前触发，一个文件只会触发一次。
	uploader.on('uploadStart', function(file) {
		//console.log('正在上传'+file.name);
		$("#" + treeNode.tId + IDMark_Queue+" #"+file.id).find(".progress").children().css('display', 'block');
	});
	uploader.on('uploadProgress', function(file,percentage) {
		percentages[ file.id ][ 1 ] = percentage;
		//console.log("正在上传："+percentage);
		updateTotalProgress(treeNode.tId,file);
	});
		// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function(file,response) {
	//	console.log(file.name+"上传成功");
		//notie.alert_hide();
		//console.log(response);
		if(response.flag){
			notie.alert(1, file.name+'上传成功!', 1);
			percentages[ file.id ][ 1 ] = 1;
			addFile(file,response,treeId,treeNode,imgObj);
		}else{
			notie.alert(3, file.name+'上传失败!', 1.5);
			fileUplaodFailed(file,uploader,treeNode);
		}
	});
	
	uploader.on( 'uploadAccept', function(obj,ret) {
		if(!ret.flag){
			notie.alert(3, obj.file.name+'上传失败!', 1.5);
			obj.file.msg = ret.msg;
			fileUplaodFailed(obj.file,uploader,treeNode);
		}
	});
	uploader.on( 'uploadError', function(file,response) {
		notie.alert(3, file.name+'上传失败!', 1.5);
		file.msg = response+"有错误!";
		fileUplaodFailed(file,uploader,treeNode);
	});
	
	
	
	
	
	
	
	//文件上传完成时触发，不论是否成功
	uploader.on( 'uploadComplete', function(file) { 
		var spans = $("#" + treeNode.tId + IDMark_Queue+" #"+file.id).find(".progress").children();
		if(file.getStatus()=="complete"){
			spans.eq( 0 ).text('上传成功');
			$('#'+file.id).find('.file-panel').remove();
		}else{
		}
        spans.eq( 1 ).hide().css( 'width','0px' );
	});
	//当所有文件上传结束时触发。
	uploader.on( 'uploadFinished', function() { 
		uploadCompFlag = true;
		$.each(percentages,function(k,v){
			if(v[1]==0){
				uploadCompFlag = false;
			}
		});
		if(uploadCompFlag){
			var treeObj = $.fn.zTree.getZTreeObj(treeId);
			treeObj.refresh();
		}
	});
	
	
	function updateTotalProgress(tId,file) {
        var loaded = 0,
            total = 0,
            spans = $("#" + tId + IDMark_Queue+" #"+file.id).find(".progress").children(),
            percent;
        percent = percentages[ file.id ][ 1 ];
        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
    }
	function fileUplaodFailed(file,uploader,treeNode) {
		file.setStatus("error");
		percentages[ file.id ][ 1 ] = 0;
		var spans = $("#" + treeNode.tId + IDMark_Queue+" #"+file.id).find(".progress").children();
		spans.eq( 0 ).html('上传失败'+"<br />"+file.msg);
		spans.eq( 0 ).css({"background-color": "rgb(187, 36, 11)","color": "#ffffff"});
		if($("#"+file.id).find(".file-panel .rotateLeft").length>0){
			return false;
		}else{
			var $retry = $('<span  class="rotateLeft" title="重新上传"></span>');
			$("#"+file.id).find(".file-panel").append($retry);
			$retry.bind("click",function(){
				uploader.retry(file);
			});
		}
    }
}

function rotationImg(wrap,deg){
	if (supportTransition) {
			deg = 'rotate(' + deg + 'deg)';
			wrap.css({
				'-webkit-transform': deg,
				'-mos-transform': deg,
				'-o-transform': deg,
				'transform': deg
			});
		} else {
			wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((deg / 90) % 4 + 4) % 4) + ')');
		}
}

// 负责view的销毁
function removeFile(treeId,treeNode,i) {
	var docSplitNo = treeNode.imgs[i].docSplitNo;
	var tmpImg = treeNode.imgs[i];
	
	treeNode.imgs.remove(i);
	var count=0;
	for (var j = 0; j < treeNode.imgs.length; j++) {
		var tmpObj = treeNode.imgs[j];
		if(tmpObj.docSplitNo==docSplitNo){
			count++;
		}
	}
	var treeObj = $.fn.zTree.getZTreeObj(treeId);
	if(count==0){
		var newNode =tmpImg;
		newNode.imgId ="";
		treeNode.imgs.push(newNode);
	}
	treeObj.refresh();
}
//图片放大
var view_div_css;
function enlargeImg(imgObj,treeNode,obj){
	var $view_div = $("#view-imgs");
	var $img = $("<img />");
	$img.attr("src",imgObj.src);
	sizeImg($img);
	$img.appendTo($view_div.find(".enlarge-img"));
	var $this = $("#"+imgObj.imgId).find("img");
	var vT,vL,vW,vH;
	vW = $this.outerWidth(true);
	vH = $this.outerHeight(true);
	vT = $this.offset().top;
	vL = $this.offset().left;
	view_div_css = {"width":vW+10,"height":vH+10,"top":vT,"left":vL};
	var view_div_open = {"width":"100%","height":"100%","top":0,"left":0};
	$view_div.find(".enlarge-img").delay(500).fadeIn();
	$view_div.find(".img-tools").delay(500).fadeIn();
	$view_div.css(view_div_css);
	$view_div.animate(view_div_open,"slow");
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
		rotationImg($img,deg);
	});
	$("#view-imgs .img-tools .view-img").unbind();
	$("#view-imgs .img-tools .view-img").bind('click', function() {
		var index = $(this).index();
		switch (index) {
			case 3:
				lastImg(imgObj,treeNode,$img);
				break;
			case 4:
				nextImg(imgObj,treeNode,$img);
				break;
		}
	});
	rotationImg($img,imgObj.rotation);
}
function close_view(){
		var $this = $("#view-imgs");
		$this.find(".img-tools").hide();
		var $img = $this.find("img");
		$this.animate(view_div_css,"slow");
		vT = view_div_css.height/2;
		vL = view_div_css.width/2;
		$this.find(".enlarge-img").delay(500).fadeOut();
		$this.animate({"width":0,"height":0,top:'+='+vT+'px',left:'+='+vL+'px'},"slow",function(){
			$img.remove();
		});
	}
function lastImg(imgObj,treeNode,$img){
	$.each(treeNode.imgs, function(i,obj) {
		if(imgObj==obj){
			if (i>0) {
				changeImg(treeNode.imgs[i-1],treeNode);
			}
		}
	});
}
function nextImg(imgObj,treeNode,$img){
	$.each(treeNode.imgs, function(i,obj) {
		if(imgObj==obj){
			if (i<treeNode.imgs.length-1) {
				changeImg(treeNode.imgs[i+1],treeNode);
			}
		}
	});
}
function changeImg(imgObj,treeNode){
	var vT,vL,vW,vH;
	var $this = $("#"+imgObj.imgId).find("img");
	vW = $this.outerWidth(true);
	vH = $this.outerHeight(true);
	vT = $this.offset().top;
	vL = $this.offset().left;
	view_div_css = {"width":vW+10,"height":vH+10,"top":vT,"left":vL};
	var $view_div = $("#view-imgs");
	var $img = $view_div.find("img");
	$img.attr("src",imgObj.src);
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

		rotationImg($img,deg);
	});
	$("#view-imgs .img-tools .view-img").unbind();
	$("#view-imgs .img-tools .view-img").bind('click', function() {
		var index = $(this).index();
		switch (index) {
			case 3:
				lastImg(imgObj,treeNode,$img);
				break;
			case 4:
				nextImg(imgObj,treeNode,$img);
				break;
		}
	});
	rotationImg($img,imgObj.rotation);
}
function sizeImg(imgObj){
	var _w = parseInt($(window).width()); //获取浏览器的宽度
	var _h = parseInt($(window).height()); //获取浏览器的宽度
	var realWidth; //真实的宽度
    var realHeight; //真实的高度
	var $img = $(imgObj);
	$("<img/>").attr("src", $img.attr("src")).load(function() {
        realWidth = this.width;
        realHeight = this.height;
        if (realWidth >= _w) {
            $img.css("width", "100%").css("height", "auto");
        } else { //如果小于浏览器的宽度按照原尺寸显示
            $img.css("width", realWidth + 'px').css("height", realHeight + 'px');
        }
        if(realHeight<_h){
        	$img.css("margin","calc(25% - "+realHeight/2+"px) auto 0px auto");
        }else{
        	$img.css("margin","0 auto");
        }
    });
}
function uploadDiv(obj){
	$(obj).parents(".upload-li").find("input.webuploader-element-invisible").click();
}


function myerror( type){
	
}

/*
*  方法:Array.remove(dx) 通过遍历,重构数组
*  功能:删除数组元素.
*  参数:dx删除元素的下标.
*/
Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i];
        }
    }
    this.length-=1;
} ;