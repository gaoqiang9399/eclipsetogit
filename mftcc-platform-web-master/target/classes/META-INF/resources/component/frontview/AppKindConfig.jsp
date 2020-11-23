<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" >
	var factorWebUrl='${factorWebUrl}';
</script>
<script type="text/javascript" src="${webPath}/component/frontview/js/AppKindConfig.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<link rel="stylesheet" href="${webPath}/themes/factor/css/set.css?v=${cssJsVersion}" />
	<%--字体图标--%>
<link rel="stylesheet" href="${webPath}/component/frontview/viewer/css/viewer.min.css" />
<script type="text/javascript" src="${webPath}/component/frontview/viewer/js/viewer-jquery.min.js"></script>
<link id="B1" rel="stylesheet" type="text/css" href="${webPath}/themes/factor/css/B1${skinSuffix}.css?v=${cssJsVersion}" />
<link rel="stylesheet" href="${webPath}/component/doc/webUpload/css/uploadForMainPage.css?v=${cssJsVersion}" />
<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
	<style>
	.p-formItem{
		margin-left:15px;
		width:70%
	}
	.nobottomborder{
		border-bottom:0px; 	
	}
	.topborder{
		border-top:1px solid #ddd; 
	}
	.margintop10{
		margin-top: 10px;
	}
	.marginbottom10{
		margin-bottom: 10px;
	}
	.margintop15{
		margin-top: 15px;
	}
	.marginbottom15{
		margin-bottom: 15px;
	}
	.marginbottom20{
		margin-bottom: 20px;
	}
	.marginleft20{
		margin-left: 20px;
	}
	.height40{
		height: 40px;
	}
	.disShow{
		display:none;
	}
 	.upload-div{ 
		width:60%;
 	} 
/* 	.upload-div .filelist li{ */
/* 		background-color:white; */
/* 		height:90px; */
/* 	} */
	</style>
</head>
<body class="bg-white">
	
	<div class="container">
	<dhcc:pmsTag pmsId="set-product-config">
		<div class="row clearfix tabCont">
			<div class="col-md-12 column">
				<div class="btn-div" >
					<ul class="breadcrumb pull-left padding_left_0" id="cusFormConfig">
						<li class="active"><span name="title">产品管理</span></li>
						<span class="btn btn-link config-font" id="cusTypeAdd"
							onclick="AppKindConfig.addKind('${webPath}/mfFrontKind/getAppKindListPage');"><i class="i i-jia2"></i></span>
					</ul>
				</div>
			</div>
		</div>
		<!-- 产品设置 -->
		 <div class="row clearfix">
				<c:forEach var="mfSysKind" items="${mfFrontKindList}" varStatus="st">
				<div class="list-item marginleft20 marginbottom20">
					<div class="row clearfix">
						<div class="col-md-9 column">
							<div class="title-div nobottomborder height40">
								<ol class="breadcrumb pull-left padding_left_0" id="kindFormConfig${mfSysKind.kindNo}">
									<li class="active"><span name="title">${mfSysKind.kindName}</span></li><span class="active"></span>
								</ol>
							</div>
						</div>
					</div>
					<div class="row clearfix">
						<div class="col-md-12 column">
							<div class="item_content">
								<div id="content_kindNo${mfSysKind.kindNo}">
									<%-- <div id="terminalConfig${mfSysKind.kindNo}">
										<div class="p-title margintop10 marginbottom10"><span class="content_title">适配终端配置</span></div>
										<div class="p-content">
											<div class="item-checkbox">
												<s:if test='#mfSysKind.androidUse==1'>
					                    		    <span class="checkbox-span curChecked" data='<s:property value="mfSysKind.androidUse" />' onclick="AppKindConfig.updateAndroidUseFlag(this,'${mfSysKind.kindNo}','1','${mfSysKind.kindName}');"><i class="i i-gouxuan1"></i></span>
												</s:if>
												<s:else>
					                    		    <span class="checkbox-span" data='<s:property value="mfSysKind.androidUse" />' onclick="AppKindConfig.updateAndroidUseFlag(this,'${mfSysKind.kindNo}','0','${mfSysKind.kindName}');"><i class="i i-gouxuan1"></i></span>
												</s:else>
					                        	<span> 适配安卓端</span>
						                    </div>
						                    <div class="item-checkbox">
												<s:if test='#mfSysKind.iosUse==1'>
					                    		    <span class="checkbox-span curChecked" data='<s:property value="mfSysKind.iosUse" />' onclick="AppKindConfig.updateIosUseFlag(this,'${mfSysKind.kindNo}','1','${mfSysKind.kindName}');"><i class="i i-gouxuan1"></i></span>
												</s:if>
												<s:else>
					                    		    <span class="checkbox-span" data='<s:property value="mfSysKind.iosUse" />' onclick="AppKindConfig.updateIosUseFlag(this,'${mfSysKind.kindNo}','0','${mfSysKind.kindName}');"><i class="i i-gouxuan1"></i></span>
												</s:else>
					                        	<span> 适配苹果端</span>
						                    </div>
										</div>
									</div> --%>
									<div id="formConfig${mfSysKind.kindNo}">
										<div class="p-title margintop10 marginbottom10"><span class="content_title">产品表单配置</span></div>
										<div class="p-content">
											<div class="item-checkbox">
												<c:if test='${mfSysKind.mobileUse eq 1}'>
					                    		    <span class="checkbox-span curChecked" data='${mfSysKind.mobileUse}' onclick="AppKindConfig.updateMobileUseFlag(this,'${mfSysKind.kindNo}','1','${mfSysKind.kindName}');"><i class="i i-gouxuan1"></i></span>
												</c:if>
												<c:if test='${mfSysKind.mobileUse ne 1}'>
					                    		    <span class="checkbox-span" data='${mfSysKind.mobileUse}' onclick="AppKindConfig.updateMobileUseFlag(this,'${mfSysKind.kindNo}','0','${mfSysKind.kindName}');"><i class="i i-gouxuan1"></i></span>
												</c:if>
					                        	<span> 启用移动端表单
					                        	<c:if test='${mfSysKind.mobileUse eq 1}'>
					                    		    <a  class="config-font" href="javascript:void(0);" data ="${mfSysKind.kindNo}" onclick='AppKindConfig.editformItem("${mfSysKind.kindNo}","${mfSysKind.kindName}")'>
														 配置
													</a>
												</c:if>
												<c:if test='${mfSysKind.mobileUse ne 1}'>
					                    		    <a  class="link-disabled" href="javascript:void(0);" data ="${mfSysKind.kindNo}" >
														 配置
													</a>
												</c:if>
												</span>
						                    </div>
						                    <div class="item-checkbox">
												<c:if test='${mfSysKind.pcUse eq 1}'>
					                    		    <span class="checkbox-span curChecked" data='${mfSysKind.pcUse}' onclick="AppKindConfig.updatePcUseFlag(this,'${mfSysKind.kindNo}','1','${mfSysKind.kindName}');"><i class="i i-gouxuan1"></i></span>
												</c:if>
												<c:if test='${mfSysKind.pcUse ne 1}'>
					                    		    <span class="checkbox-span" data='${mfSysKind.pcUse}' onclick="AppKindConfig.updatePcUseFlag(this,'${mfSysKind.kindNo}','0','${mfSysKind.kindName}');"><i class="i i-gouxuan1"></i></span>
												</c:if>
					                        	<span> 启用PC端表单
					                        	<c:if test='${mfSysKind.pcUse eq 1}'>
					                    		    <a  class="config-font" href="javascript:void(0);" data ="${mfSysKind.kindNo}" onclick='AppKindConfig.formDesignThis("${mfSysKind.kindNo}","${webPath}/mfFrontKind/pcKindDesignAjax")'>
														 配置
													</a>
					                    		  <%--   <a  class="config-font clear-form" href="javascript:void(0);" data ="${mfSysKind.kindNo}" onclick='AppKindConfig.resetForm("${mfSysKind.kindNo}","${webPath}/mfFrontKind/deleteFormIdAjax")'>
														 重置
													</a> --%>
												</c:if>
												<c:if test='${mfSysKind.pcUse ne 1}'>
					                    		    <a  class="link-disabled" href="javascript:void(0);" data ="${mfSysKind.kindNo}" >
														 配置
													</a>
													<%--  <a  class="link-disabled clear-form" href="javascript:void(0);" data ="${mfSysKind.kindNo}">
														 重置
													</a> --%>
												</c:if>
												</span>
						                    </div>
										</div>
									</div>
									<div id="describeConfig${mfSysKind.kindNo}">
										<div class="p-title margintop10 marginbottom10"><span class="content_title">产品描述配置</span></div>
										<div class="p-content">
											<div class="p-formItem" id="describeItem${mfSysKind.kindNo}">
												<span id = "formItemYD" class="config-item">
													<a  class="config-font" href="javascript:void(0);" data ="${mfSysKind.kindNo}" onclick='AppKindConfig.addDescribeInfo("${mfSysKind.kindNo}","${mfSysKind.kindName}")'>
														 移动端产品描述
													</a>
												</span>
												<span id = "formItemPC" class="config-item">
													<a  class="config-font" href="javascript:void(0);" data ="${mfSysKind.kindNo}" onclick='AppKindConfig.addPcDescribeInfo("${mfSysKind.kindNo}","${mfSysKind.kindName}")'>
														 PC端产品描述
													</a>
												</span>
											</div>
										</div>
									</div>
									<div id="identificationConfig${mfSysKind.kindNo}">
										<div class="p-title margintop10 marginbottom10"><span class="content_title">认证准入项配置</span></div>
										<div class="p-content">
											<div class="p-formItem" id="identificationItem${mfSysKind.kindNo}">
												<span id = "formItemYD" class="config-item">
													<a  class="config-font" href="javascript:void(0);" data ="${mfSysKind.kindNo}" onclick='AppKindConfig.addIdentificationInfo("${mfSysKind.kindNo}","${mfSysKind.kindName}")'>
														 移动端认证项
													</a>
												</span>
												<span id = "formItemPC" class="config-item">
													<a  class="config-font" href="javascript:void(0);" data ="${mfSysKind.kindNo}" onclick='AppKindConfig.addIdentificationInfo("${mfSysKind.kindNo}","${mfSysKind.kindName}")'>
														 PC端认证项
													</a>
												</span>
											</div>
										</div>
									</div>
									<div id="docConfig${mfSysKind.kindNo}">
										<div class="p-title margintop10 marginbottom10"><span class="content_title">上传要件配置</span></div>
										<div class="p-content">
											<div class="p-formItem" id="docItem${mfSysKind.kindNo}">
												<span id = "formItemYD" class="config-item">
													<a  class="config-font" href="javascript:void(0);" data ="${mfSysKind.kindNo}" onclick='AppKindConfig.addDocInfo("${mfSysKind.kindNo}","${mfSysKind.kindName}")'>
														 移动端需上传要件
													</a>
												</span>
												<span id = "formItemPC" class="config-item">
													<a  class="config-font" href="javascript:void(0);" data ="${mfSysKind.kindNo}" onclick='AppKindConfig.addDocInfo("${mfSysKind.kindNo}","${mfSysKind.kindName}")'>
														 PC端需上传要件
													</a>
												</span>
											</div>
										</div>
									</div>
									<div id="putOutConfig${mfSysKind.kindNo}">
										<div class="p-title margintop10 marginbottom10"><span class="content_title">放款量控制</span></div>
										<div class="p-content">
											<div class="p-formItem" id="putOutItem${mfSysKind.kindNo}">
												<span class="config-item">
													<span id="cmpFltRateDefName1000">当天放款次数控制：</span>
													<input type="text" class="cmpFltRateDef1000" maxlength="10" value='${mfSysKind.putoutCountDay}' onblur='AppKindConfig.updatePutoutCountDay(this,"${mfSysKind.kindNo}")' onkeyup="value=value.replace(/[^\d.]/g,'')">
												</span>
												<span  class="config-item">
													<span id="cmpFltRateDefName1000">当天放款金额控制：</span>
													<input type="text" class="cmpFltRateDef1000" maxlength="20" value='${mfSysKind.putoutAmtDay}' onblur='AppKindConfig.updatePutoutAmtDay(this,"${mfSysKind.kindNo}")' onkeyup="value=value.replace(/[^\d.]/g,'')">
												</span>
											</div>
											<div class="p-formItem margintop10" id="putOutItem${mfSysKind.kindNo}">
												<span  class="config-item">
													<span id="cmpFltRateDefName1000">当月放款次数控制：</span>
													<input type="text" class="cmpFltRateDef1000" maxlength="10" value='${mfSysKind.putoutCountMonth}' onblur='AppKindConfig.updatePutoutCountMonth(this,"${mfSysKind.kindNo}")' onkeyup="value=value.replace(/[^\d.]/g,'')">
												</span>
												<span class="config-item">
													<span id="cmpFltRateDefName1000">当月放款金额控制：</span>
													<input type="text" class="cmpFltRateDef1000" maxlength="20" value='${mfSysKind.putoutAmtMonth}' onblur='AppKindConfig.updatePutoutAmtMonth(this,"${mfSysKind.kindNo}")' onkeyup="value=value.replace(/[^\d.]/g,'')">
												</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div> 
					</div>
				</div>
			</c:forEach>
		</div> 
		</dhcc:pmsTag>
		<dhcc:pmsTag pmsId="set-things-config">
		
		<div class="row clearfix tabCont">
			<div class="col-md-12 column">
				<div class="btn-div" >
					<ol class="breadcrumb pull-left padding_left_0" id="frontViewManage">
						<li class="active"><span name="title">内容管理</span></li>
					</ol>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">网站基础设置</span>
					</div>
					<div class="p-content">
						<div class="form-table" style="width:80%;margin-top:-5px;margin-bottom:-5px;">
							<div class="content collapse in">
							<form action="${webPath}/vwSetManage/updateAjax"  id="vwSetHtml">
							</form>
							<div class="upload-div collapse in">
							<ul class="filelist uploadTree_1_queue" id="viewer">
								<li class="upload-li li_1000000118" id="logoLi">
						                <div class="type-title">
						                <span class="title-name disShow"></span>
						           		<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
						                <div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
						                </div>
						                <form method="post" enctype="multipart/form-data" method="post">
							                <div class="opt-upload no-img">
							                    <div title="网站logo">
							                       	 网站logo
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
							                        <input type="text" name="flag" value="logo">
							                        <input type="text" name="fileType">
							                    </div>
							                </div>
							             </form>
						                <div class="opt-upload has-img disShow">
								           <img src="" class="viewer-toggle" onclick="showImg(this)" alt="网站logo">
								           <div class="doc-title" title="网站logo">
								         		   网站logo
								           </div>
								           <span style="display:none;" class="doc_name"></span>
								        </div>
					            </li>
								<li class="upload-li li_1000000118" id="wxLi">
						                <div class="type-title">
						                <span class="title-name disShow"></span>
						           		<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
						                <div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
						                </div>
						                <form method="post">
							                <div class="opt-upload no-img">
							                    <div title="微信二维码">
							                       	 微信二维码
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
							                        <input type="text" name="flag" value="wx">
							                        <input type="text" name="fileType">
							                    </div>
							                </div>
							             </form>
					                <div class="opt-upload has-img disShow">
							           <img src="" class="viewer-toggle" onclick="showImg(this)" alt="微信二维码">
							           <div class="doc-title" title="微信二维码">
							         		   微信二维码
							           </div>
							           <span style="display:none;" class="doc_name"></span>
							        </div>
					            </li>

					            <li class="upload-li li_1000000118" id="qqLi">
					                <div class="type-title">
					                 	<span class="title-name disShow"></span>
					                 	<div class="btn btn-primary disShow" onclick="deleteDiv(this);" style="right:68px;top:2px;font-size:10px;">删除</div>
						                <div class="btn btn-primary btn_undefined disShow" onclick="uploadDiv(this);">上传</div>
					                </div>
					                	<form method="post">
						                <div class="opt-upload no-img">
						                    <div title="QQ群二维码">QQ群二维码</div>
						                    <div class="btn-upload-div color_theme btn_1000000118" onclick="uploadDiv(this);">
						                    <i class="i i-jia3"></i>
						                      	  上传
						                    </div>
						                </div>
						                <div class="btn-upload hide webuploader-container">
						                    <div class="webuploader-pick"></div>
						                    <div style="position: absolute; top: 0px; left: 0px; width: 1px; height: 1px; overflow: hidden;">
						                        <input type="file" name="upload" class="webuploader-element-invisible" accept="image/*">
						                        <input type="text" name="flag" value="qq">
						                         <input type="text" name="fileType">
						                    </div>
						                </div>
						            	</form>
							            <div class="opt-upload has-img disShow">
								           <img src="" class="viewer-toggle" onclick="showImg(this)" alt="QQ群二维码">
								           <div class="doc-title" title="QQ群二维码">
								         		 QQ群二维码
								           </div>
								            <span style="display:none;" class="doc_name"></span>
								       	</div>
					            </li>
					           </ul>
					          </div>							
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		</dhcc:pmsTag>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">Banner设置</span>
						<a class="config-font" href="${webPath}/vwBannerManage/getListPage">
							 配置
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">移动端客户经理</span>
						<a class="config-font" href="${webPath}/mfAppCusMng/getListPage">
							 配置
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">平台特点设置</span>
					</div>
					<div class="p-content">
						<div class="item-checkbox">
							<span>平台特点1</span>
							<a class="config-font" href="javascript:void(0);" data ="201705261807514881" onclick="AppKindConfig.openFeatureForm(this,1)">
								 配置
							</a>
						</div>
						<div class="item-checkbox">
							<span>平台特点2</span>
							<a class="config-font" href="javascript:void(0);" data ="201705261808256712" onclick="AppKindConfig.openFeatureForm(this,2)">
								 配置
							</a>
						</div>
						<div class="item-checkbox">
							<span>平台特点3</span>
							<a class="config-font" href="javascript:void(0);" data ="201707011836576600" onclick="AppKindConfig.openFeatureForm(this,3)">
								 配置
							</a>
						</div>
						<div class="item-checkbox">
							<span>平台特点4</span>
							<a class="config-font" href="javascript:void(0);" data ="201707071836575874" onclick="AppKindConfig.openFeatureForm(this,3)">
								 配置
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">友情链接设置</span>
						<a class="config-font" href="${webPath}/vwLinkManage/getListPage">
							 配置
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">新闻设置</span>
						<a class="config-font" href="${webPath}/vwContManage/getListPage">
							 配置
						</a>
					</div>
				</div>
			</div>
		</div>

		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">前端提交客户信息</span>
						<a class="config-font" href="${webPath}/vwVisitManage/findByPage">
							 查看
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">移动端分享内容设置</span>
					</div>
					<div class="p-content">
						<div class="form-table" style="width:80%;margin-top:-5px;margin-bottom:-5px;">
							<div class="content collapse in">
							<form action="${webPath}/mfFrontAppSetting/updateAjaxByOne"  id="mobileShareHtml">
							</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">移动端帮助中心设置</span>
						<a class="config-font" href="#">
							 配置
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">PC端设置</span>
						<a class="config-font" href="${webPath}/vwProductManage/getListPage">
							配置
						</a>
					</div>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div class="item_content">
					<div class="p-title margintop10 marginbottom10"><span class="content_title">移动端客服设置</span>
					</div>
					<div class="p-content">
						<div class="form-table" style="width:80%;margin-top:-5px;margin-bottom:-5px;">
							<div class="content collapse in">
							<form action="${webPath}/mfFrontAppSetting/updateAjaxByOne"  id="mobileSettingHtml">
							</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 导航轴 -->
		<div class="row clearfix">
			<div class="work-zone-timeLine" style="position: fixed; margin-top: 3%; margin-right: 79px;">
				<div class="time_contents">
					<div class="time-line-bg">
						<div class="time-line-line"></div>
						<div class="time-line-body">
							<dl class="time-line-dl"></dl>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
$(function () {
  	$("#viewer").viewer();
});
 </script>
<script type="text/javascript">
		var path = '<%=request.getContextPath() %>';
		var basePath = '${webPath}';
		$(function(){
			//调用时间轴
			navLine.createNavLine();
			//MfKindConfig.init();
			AppKindConfig.getFormHtml();
			AppKindConfig.getMobileSettingHtml();
			AppKindConfig.getMobileShareHtml();
			AppKindConfig.init();
		});
	</script>
</html>
