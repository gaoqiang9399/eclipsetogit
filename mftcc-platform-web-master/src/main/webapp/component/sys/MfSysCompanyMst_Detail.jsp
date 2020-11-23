<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/css/uploadForMainPage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
<style type="text/css">
	.disShow{
		display:none;
	}
 	.upload-div{ 
		width:100%;
 	} 
</style>
	<script type="text/javascript">
		var path = '<%=request.getContextPath() %>';
		var basePath = '${webPath}';
		$(function(){
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					theme : "minimal-dark",
					updateOnContentResize : true
				}
			});
			initVwSetImg();
		});
		function initVwSetImg(){
			var loginPagegBackgroundFileName="";
			var loginPageFileName="";
			var systemLogoFileName="";
			var propagandaFileName="";
			var animationFileName="";
			var smallAnimationFileName="";
			var batchConsoleFileName="";
			var searchPageLogoFileName="";
			$.ajax({
				url:webPath+"/mfSysCompanyMst/getByIdAjax",
				dataType:"json",
				async: false,
				type:"POST",
				success:function(data){
					if(data.flag="success"){
                        loginPagegBackgroundFileName = data.data.loginBackgroundImg;
						loginPageFileName = data.data.loginPageLogoImg;
						systemLogoFileName = data.data.systemLogoImg;
						propagandaFileName = data.data.propagandaLanguageImg;
						animationFileName=data.data.loadAnimationImg;
						smallAnimationFileName=data.data.loadAnimationSmallImg;
						batchConsoleFileName=data.data.batchConsoleLoginImg;
                        searchPageLogoFileName = data.data.searchPageLogoImg;
					}
				}
			})
			var srcLbg=getImg(loginPagegBackgroundFileName);
			if(srcLbg==""){//还未上传图片
				
			}else{
				$("#loginBgLi").find("img").attr("src",srcLbg);
				$("#loginBgLi").find(".disShow").removeClass("disShow");
				$("#loginBgLi").find("form").addClass("disShow");
			}
			var srcWx=getImg(loginPageFileName);
			if(srcWx==""){//还未上传图片

			}else{
				$("#loginLi").find("img").attr("src",srcWx);
				$("#loginLi").find(".disShow").removeClass("disShow");
				$("#loginLi").find("form").addClass("disShow");
			}
			var srcQQ=getImg(systemLogoFileName);
			if(srcQQ==""){//还未上传图片
				
			}else{
				$("#systemLi").find("img").attr("src",srcQQ);
				$("#systemLi").find(".disShow").removeClass("disShow");
				$("#systemLi").find("form").addClass("disShow");
			}
			var srcAnimation=getImg(animationFileName);
			if(srcAnimation==""){//还未上传图片
				
			}else{
				$("#animationLi").find("img").attr("src",srcAnimation);
				$("#animationLi").find(".disShow").removeClass("disShow");
				$("#animationLi").find("form").addClass("disShow");
			}
			var srcSmallAnimation=getImg(smallAnimationFileName);
			if(srcSmallAnimation==""){//还未上传图片
				
			}else{
				$("#smallAnimationLi").find("img").attr("src",srcSmallAnimation);
				$("#smallAnimationLi").find(".disShow").removeClass("disShow");
				$("#smallAnimationLi").find("form").addClass("disShow");
			}
			var srcBatchConsole=getImg(batchConsoleFileName);
			if(srcBatchConsole==""){//还未上传图片
				
			}else{
				$("#batchConsoleLi").find("img").attr("src",srcBatchConsole);
				$("#batchConsoleLi").find(".disShow").removeClass("disShow");
				$("#batchConsoleLi").find("form").addClass("disShow");
			}
			var srcPropaganda=getImg(propagandaFileName);
			if(srcPropaganda==""){//还未上传图片
				
			}else{
				$("#propagandaLi").find("img").attr("src",srcPropaganda);
				$("#propagandaLi").find(".disShow").removeClass("disShow");
				$("#propagandaLi").find("form").addClass("disShow");
			}

            var srcSearchPageLogo=getImg(searchPageLogoFileName);
            if(srcSearchPageLogo==""){//还未上传图片

            }else{
                $("#searchPageLogoLi").find("img").attr("src",srcSearchPageLogo);
                $("#searchPageLogoLi").find(".disShow").removeClass("disShow");
                $("#searchPageLogoLi").find("form").addClass("disShow");
            }
			
		}
		function uploadDiv(dom){
			$(dom).parents("li").find("[name=upload]").click();
		}
		$(document).delegate("[name=upload]","change",function(e){
			$form=$(e.target).parents("form");
			var flag=$form.find("[name=flag]").val();
			var filePath=e.target.value;
			if(null!=filePath&&filePath!=''){
				var arr=filePath.split(".");
				$form.find("[name=fileType]").val(arr[arr.length-1]);
				$form.ajaxSubmit({
					url:webPath+"/mfSysCompanyMst/uploadImg",
					success:function(data){
						if(data.data){
							var src=getImg(data.data);//fileName
							$(e.target).parents("li").find("img").attr("src",src);
							$(e.target).parents("li").find(".disShow").removeClass("disShow");
							$form.addClass("disShow");	
						}else{
							window.top.alert(top.getMessage("ERROR_UPLOAD_FILE_TYPE","图片类型"),0);
						}
						
					}
				})
			}else{
				$form.find("[name=fileType]").val("");
			}
		})
		function showImg(dom){
			var src=$(dom)[0].src;
			var flag=$(dom).parents("li").find("[name=flag]").val();
			$("#template").attr("src",src);
            if("loginBg"==flag){
                $("#template").attr("alt","登陆页面背景图");
			}else if("login"==flag){
				$("#template").attr("alt","登陆页面logo");
			}else if("system"==flag){
				$("#template").attr("alt","系统logo");
			}else if("propaganda"==flag){
				$("#template").attr("alt","宣传语");
			}else if("animation"==flag){
				$("#template").attr("alt","加载动画");
			}else if("smallAnimation"==flag){
				$("#template").attr("alt","小号加载动画");
			}else if("batchConsole"==flag){
				$("#template").attr("alt","批量调度平台-登录logo");
			}else if("searchPageLogo"==flag) {
                $("#template").attr("alt", "综合页面logo");
            }
			$("#template").click();
		}
		function getImg(fileName){//初始化使用
			var src="";
            if(fileName!=""&& fileName!=null){
				//打开文件是验证格式，过滤可能威胁系统安全非法后缀
				if(fileName.endsWith(".exe")||fileName.endsWith(".jsp")||fileName.endsWith(".bat")||fileName.endsWith(".txt")
						||fileName.endsWith(".dll")||fileName.endsWith(".com")||fileName.endsWith(".sh")||fileName.endsWith(".py")){
					return src;
				}
				src="../../"+fileName;//相对路径
			}
			return src;
		}
		function deleteDiv(dom){
			var flag=$(dom).parents("li").find("[name=flag]").val();
			$.ajax({
				url:webPath+'/mfSysCompanyMst/deleteFileAjax?flag='+flag,
				success:function(data){
					$li=$(dom).parents("li");
					if(data.defaultFlag=="true"){
                        $li.find("img").attr("src",data.src);
                    }else{
                        $li.find(".disShow").removeClass("disShow");
                        $li.find(".has-img").addClass("disShow");//隐藏
                        $li.find(".title-name").addClass("disShow");
                        $li.find(".btn-primary").addClass("disShow");
                    }
				}
			})
		}
        function myclose(){
            window.location.href=webPath+"/sysDevView/setC?setType=base";
        }
	</script>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<div class="form-tips">      1.图片格式支持"jpeg","png","gif","jpg","pbm","bmp","jpe","tif" </div>
						<div class="form-tips">      2.登录页面背景建议大小10M以内，尺寸4096*2304或者等比 </div>
						<div class="form-tips">      3.登录页面logo建议尺寸：最大宽度200,最大高度50</div>
						<div class="form-tips">      4.系统logo建议尺寸：最大宽度100,最大高度55</div>
						<div class="form-tips">      5.综合页面logo建议尺寸：最大宽度200,最大高度200</div>
						<form method="post" id="MfSysCompanyMst" theme="simple" name="operform" action="${webPath}/mfSysCompanyMst/updateAjax">
							<dhcc:bootstarpTag property="formsyscompany0001" mode="query"/>
						</form>
					</div>
					<div class="upload-div collapse in">
							<ul class="filelist uploadTree_1_queue" id="viewer">
								<li class="upload-li" id="loginBgLi">
						                <div class="type-title">
						                <span class="title-name disShow"></span>
						           		<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
						                <div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
						                </div>
						                <form method="post" enctype="multipart/form-data" method="post">
							                <div class="opt-upload no-img">
							                    <div title="登陆页面背景(4096*2304)">
							                       	 登陆页面背景(4096*2304)
							                    </div>
							                    <div class="btn-upload-div color_theme btn_1000000118" onclick="uploadDiv(this);">
							                    <i class="i i-jia3"></i>
							                      	  上传
							                    </div>
							                </div>
							                <div class="btn-upload hide webuploader-container">
							                    <div class="webuploader-pick"></div>
							                    <div style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;">
							                        <input type="file" name="upload" class="webuploader-element-invisible" accept="image/*" >
							                        <input type="text" name="flag" value="loginBg">
							                        <input type="text" name="fileType">
							                    </div>
							                </div>
							             </form>
					                <div class="opt-upload has-img disShow">
							           <img src="" class="viewer-toggle" onclick="showImg(this)" alt="登陆页面背景图">
							           <div class="doc-title" title="登陆页面背景(4096*2304)">
										   登陆页面背景(4096*2304)
							           </div>
							           <span style="display:none;" class="doc_name"></span>
							        </div>
					            </li>
								<li class="upload-li li_1000000118" id="loginLi">
						                <div class="type-title">
						                <span class="title-name disShow"></span>
						           		<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
						                <div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
						                </div>
						                <form method="post" enctype="multipart/form-data" method="post">
							                <div class="opt-upload no-img">
							                    <div title="登陆页面logo">
							                       	 登陆页面logo
							                    </div>
							                    <div class="btn-upload-div color_theme btn_1000000118" onclick="uploadDiv(this);">
							                    <i class="i i-jia3"></i>
							                      	  上传
							                    </div>
							                </div>
							                <div class="btn-upload hide webuploader-container">
							                    <div class="webuploader-pick"></div>
							                    <div style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;">
							                        <input type="file" name="upload" class="webuploader-element-invisible" accept="image/*" >
							                        <input type="text" name="flag" value="login">
							                        <input type="text" name="fileType">
							                    </div>
							                </div>
							             </form>
					                <div class="opt-upload has-img disShow">
							           <img src="" class="viewer-toggle" onclick="showImg(this)" alt="登陆页面logo">
							           <div class="doc-title" title="登陆页面logo">
							         		  登陆页面logo
							           </div>
							           <span style="display:none;" class="doc_name"></span>
							        </div>
					            </li>
					            <li class="upload-li li_1000000118" id="systemLi">
					                <div class="type-title">
					                 	<span class="title-name disShow"></span>
					                 	<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
						                <div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
					                </div>
					                	<form method="post">
						                <div class="opt-upload no-img">
						                    <div title="系统logo">
						                       	 系统logo
						                    </div>
						                    <div class="btn-upload-div color_theme btn_1000000118" onclick="uploadDiv(this);">
						                    <i class="i i-jia3"></i>
						                      	  上传
						                    </div>
						                </div>
						                <div class="btn-upload hide webuploader-container">
						                    <div class="webuploader-pick"></div>
						                    <div style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;">
						                        <input type="file" name="upload" class="webuploader-element-invisible" accept="image/*">
						                        <input type="text" name="flag" value="system">
						                         <input type="text" name="fileType">
						                    </div>
						                </div>
						            	</form>
							            <div class="opt-upload has-img disShow">
								           <img src="" class="viewer-toggle" onclick="showImg(this)" alt="系统logo">
								           <div class="doc-title" title="系统logo">
								         		 系统logo
								           </div>
								            <span style="display:none;" class="doc_name"></span>
								       	</div>
					            </li>
					            <li class="upload-li li_1000000118" id="propagandaLi">
						                <div class="type-title">
						                <span class="title-name disShow"></span>
						           		<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
						                <div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
						                </div>
						                <form method="post">
							                <div class="opt-upload no-img">
							                    <div title="宣传语">
							                       	宣传语
							                    </div>
							                    <div class="btn-upload-div color_theme btn_1000000118" onclick="uploadDiv(this);">
							                    <i class="i i-jia3"></i>
							                      	  上传
							                    </div>
							                </div>
							                <div class="btn-upload hide webuploader-container">
							                    <div class="webuploader-pick"></div>
							                    <div style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;">
							                        <input type="file" name="upload" class="webuploader-element-invisible" accept="image/*">
							                        <input type="text" name="flag" value="propaganda">
							                        <input type="text" name="fileType">
							                    </div>
							                </div>
							             </form>
					                <div class="opt-upload has-img disShow">
							           <img src="" class="viewer-toggle" onclick="showImg(this)" alt="宣传语">
							           <div class="doc-title" title="宣传语">
							         		宣传语
							           </div>
							           <span style="display:none;" class="doc_name"></span>
							        </div>
					            </li>
					            <li class="upload-li li_1000000118" id="animationLi">
						                <div class="type-title">
						                <span class="title-name disShow"></span>
						           		<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
						                <div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
						                </div>
						                <form method="post">
							                <div class="opt-upload no-img">
							                    <div title="加载动画">
							                       	加载动画
							                    </div>
							                    <div class="btn-upload-div color_theme btn_1000000118" onclick="uploadDiv(this);">
							                    <i class="i i-jia3"></i>
							                      	  上传
							                    </div>
							                </div>
							                <div class="btn-upload hide webuploader-container">
							                    <div class="webuploader-pick"></div>
							                    <div style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;">
							                        <input type="file" name="upload" class="webuploader-element-invisible" accept="image/*">
							                        <input type="text" name="flag" value="animation">
							                        <input type="text" name="fileType">
							                    </div>
							                </div>
							             </form>
					                <div class="opt-upload has-img disShow">
							           <img src="" class="viewer-toggle" onclick="showImg(this)" alt="加载动画">
							           <div class="doc-title" title="加载动画">
							         		加载动画
							           </div>
							           <span style="display:none;" class="doc_name"></span>
							        </div>
					            </li>
					             <li class="upload-li li_1000000118" id="smallAnimationLi">
						                <div class="type-title">
						                <span class="title-name disShow"></span>
						           		<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
						                <div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
						                </div>
						                <form method="post">
							                <div class="opt-upload no-img">
							                    <div title="小号加载动画">
							                       	小号加载动画
							                    </div>
							                    <div class="btn-upload-div color_theme btn_1000000118" onclick="uploadDiv(this);">
							                    <i class="i i-jia3"></i>
							                      	  上传
							                    </div>
							                </div>
							                <div class="btn-upload hide webuploader-container">
							                    <div class="webuploader-pick"></div>
							                    <div style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;">
							                        <input type="file" name="upload" class="webuploader-element-invisible" accept="image/*">
							                        <input type="text" name="flag" value="smallAnimation">
							                        <input type="text" name="fileType">
							                    </div>
							                </div>
							             </form>
					                <div class="opt-upload has-img disShow">
							           <img src="" class="viewer-toggle" onclick="showImg(this)" alt="小号加载动画">
							           <div class="doc-title" title="小号加载动画">
							         		小号加载动画加载动画
							           </div>
							           <span style="display:none;" class="doc_name"></span>
							        </div>
					            </li>
								<%--综合页面logo--%>
								<li class="upload-li li_1000000118" id="searchPageLogoLi">
									<div class="type-title">
										<span class="title-name disShow"></span>
										<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
										<div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
									</div>
									<form method="post">
										<div class="opt-upload no-img">
											<div title="小号加载动画">
												综合页面logo
											</div>
											<div class="btn-upload-div color_theme btn_1000000118" onclick="uploadDiv(this);">
												<i class="i i-jia3"></i>
												上传
											</div>
										</div>
										<div class="btn-upload hide webuploader-container">
											<div class="webuploader-pick"></div>
											<div style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;">
												<input type="file" name="upload" class="webuploader-element-invisible" accept="image/*">
												<input type="text" name="flag" value="searchPageLogo">
												<input type="text" name="fileType">
											</div>
										</div>
									</form>
									<div class="opt-upload has-img disShow">
										<img src="" class="viewer-toggle" onclick="showImg(this)" alt="综合页面logo">
										<div class="doc-title" title="综合页面logo">
											综合页面logo
										</div>
										<span style="display:none;" class="doc_name"></span>
									</div>
								</li>
					           </ul>
					          </div>
					<div class="bootstarpTag">

					</div>
				</div>	
				
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertCusForm('#MfSysCompanyMst');"></dhcc:thirdButton>
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
	   	</div>
	</body>
</html>