<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<link id="DocModel_Insert" rel="stylesheet" href="${webPath}/component/doc/css/DocModel_Insert${skinSuffix}.css" />
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="docModelForm" theme="simple" name="operform" action="${webPath}/docModel/insertAjax">
							<dhcc:bootstarpTag property="formdocmodel0001" mode="query" />
						</form>
					</div>
					<div class="scence-list" id="scenceList">
						<p>场景配置<span class="btn btn-link-theme" id="scenceAdd" onclick="selectScenceMutiDialog1();"><i class="i i-jia2" ></i></span></p>
						 <c:forEach items="${ scenceList}" var="scence" > 
					    	<div class="scenceItem"  name="${scence.scNo}">
					    		<div class="col-sm-2">
					    			<div class="item-title"> 
						    			<span>${scence.scName}</span>
						    			<span class="span-count">(0)</span>
						    			<!-- 这个input用来保存所有的文档类型编号,使用@分隔 -->
						    			<input type="hidden" name="docTypes">
						    			<input type="hidden" name="scenceNo" value="${scence.scNo}">
					    			</div>
					    		</div>
					    		<div class="col-sm-10 right-doc"> 
					    			<div class="arrow-div left-arrow " ></div>
					    			<div class="content-div">
					    				<ul>
					    				<li class="docTypeItem1">
											<div class="docTypeItem1-div" onclick="getEventObj(this);getDocTypeData(this);">
												<div class="docTypeShow1"><i class="i i-jia2 "></i></div>
											</div>
										</li>
					    				</ul>
					    			</div>
					    			<div class="arrow-div right-arrow"></div> 
					    			<div class="jiaDiv" >
					    				<li class="docTypeItem1">
					    					<div class="docTypeItem1-div" onclick="getEventObj(this);getDocTypeData(this);">
												<div class="docTypeShow1"><i class="i i-jia2" ></i></div>
											</div>
										</li>
					    			</div>  
					    		</div>
					    		<span  class="btn btn-link-theme del-btn" onclick="removeScence(this);">
					    			<i class="i i-x5"></i>
					    		</span>
					    	</div>		
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#docModelForm');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
	<script type="text/javascript">
	var scenceList = '${scenceListAjaxData}';
	scenceList = eval(scenceList);
	var btspan = "<span class='i i-sanjiao color_red btspan0'></span><span class='btspan1'>必</span>";
	$(function() {
		$("#scenceAdd").hide();
		$("#scenceList .scenceItem").show();
		$(".content-div").width($(".right-doc").width()-150);
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				theme:"minimal-dark",
// 				updateOnContentResize:true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		$(".content-div").mCustomScrollbar({
			advanced:{ 
				theme: "minimal-dark"
			},
			horizontalScroll : true
		});
		$(".content-div .docTypeItem1").css("display","inline-block");
		//updateJT();
	});
	function updateJT(){
		$scenceItem = $(".content-div .mCSB_scrollTools:hidden").parents(".scenceItem");
		$scenceItem.find(".content-div .docTypeItem1").css("display","inline-block");
		$scenceItem.find(".jiaDiv .docTypeItem1").hide();
		
		$scenceItem1 = $(".content-div .mCSB_scrollTools:visible").parents(".scenceItem");
		$scenceItem1.find(".content-div .docTypeItem1").hide();
		$scenceItem1.find(".jiaDiv .docTypeItem1").css("display","inline-block");
		$(".content-div").mCustomScrollbar("update");
	};

	</script>
	<!--需要将此js文件引用放在js代码下，因为js文件中用到了上面js代码中的变量  -->
	<script type="text/javascript" src='${webPath}/component/doc/js/DocModel_Insert.js'> </script>
</html>
