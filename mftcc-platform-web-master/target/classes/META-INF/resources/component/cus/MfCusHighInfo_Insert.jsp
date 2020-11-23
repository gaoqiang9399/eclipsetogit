<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		    
			<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusHighInfo.js?v=${cssJsVersion}'> </script>
	</head>
	<script type="text/javascript">
	$(function() {
		MfCusHighInfo.init();
		//页面初始化时证件类型默认的是身份证，为证件号码添加验证
		/*var idTypeValue = $("input[name=idType]").val();
		if(idTypeValue == "0"){
		    //身份证
			var blur = $("input[name=idNum]").attr("onblur");
			var blurAdd = blur + "func_uior_valFormat_tips(this,'idnum');";
			$("input[name=idNum]").attr("onblur",blurAdd);
		}*/
		//高管人员类别选择组件
	 $("select[name=highCusType]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false,//单选
				addBtn:{
					"title":"新增",
					"fun":function(hiddenInput, elem){
						$(elem).popupSelection("hideSelect", elem);
						BASE.openDialogForSelect('新增高管人员类别','HIGH_TYPE', elem);
					}
				}
		}); 
		//证件类型选择组件
		$("select[name=idType]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false,//单选
				changeCallback : function (obj, elem) {
					var blur = $("input[name=idNum]").attr("onblur");
					var newAddBlur = blur.substring(0,blur.indexOf("func_uior_valFormat_tips"));
					$("input[name=idNum]").attr("onblur",newAddBlur);
					var idTypeValue = obj.data('values').val();  //当前选中的值
					$("select[name=sex]").val('');
					$("input[name=idNum]").val('');
					$("input[name=idNum]").parent().find('.error').remove();
					$("input[name=brithday]").val('');
					if(idTypeValue == "0"){ //身份证
						var blurAdd = blur + "func_uior_valFormat_tips(this,'idnum');";
						$("input[name=idNum]").attr("onblur",blurAdd);
					}
				}
		});
		//投保情况选择组件
		/* try{
			$("select[name=situationInsurance]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:true//多选
				
			});
		}catch(e){} */
		
		//最高学历选择组件
		/* $("select[name=education]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false//单选
		}); */
	});
</script>
	<body class="overflowHidden bg-white">
		<input id="sysProjectName" name="sysProjectName" type="hidden" value="${sysProjectName}" />
		<div class="container form-container" id="list">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="cusHighInfoInsert" theme="simple" name="operform" action="${webPath}/mfCusHighInfo/insertAjax">
							<dhcc:bootstarpTag property="formcushigh00003" mode="query" />
						</form>
					</div>
				</div>

				<%--//所受教育--%>
				<div class="col-xs-12 column">
					<div class="list-table-replan">
						<div class="title">
							<span>所受教育</span>
							<button class="btn btn-link formAdd-btn"  onclick="MfCusHighInfo.educationAdd()" title="新增"><i class="i i-jia3"></i></button>
						</div>
						<div class="content margin_left_15 collapse in" id="education">
							<dhcc:tableTag property="tablehighChildEducationList" paginate="tablehighChildEducationList" head="true"></dhcc:tableTag>
						</div>
					</div>
				</div>
				<%--//社会活动--%>
				<%--<div class="col-xs-12 column">
					<div class="list-table-replan">
						<div class="title">
							<span>社会活动</span>
							<button class="btn btn-link formAdd-btn"  onclick="MfCusHighInfo.socialAdd()" title="新增"><i class="i i-jia3"></i></button>
						</div>
						<div class="content margin_left_15 collapse in" id="social">
							<dhcc:tableTag property="tablehighChildSocialList" paginate="tablehighChildSocialList" head="true"></dhcc:tableTag>
						</div>
					</div>
				</div>--%>
				<%--//主要工作经历--%>
				<div class="col-xs-12 column">
					<div class="list-table-replan">
						<div class="title">
							<span>主要工作经历</span>
							<button class="btn btn-link formAdd-btn"  onclick="MfCusHighInfo.workAdd()" title="新增"><i class="i i-jia3"></i></button>
						</div>
						<div class="content margin_left_15 collapse in" id="work">
							<dhcc:tableTag property="tablehighChildWorkList" paginate="tablehighChildWorkList" head="true"></dhcc:tableTag>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="saveCusHighInfoInsert('#cusHighInfoInsert','insert');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
		<%--新增页面--%>
		<div class="container form-container" style="display: none;" id="insert">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="cusHighChildInfoInsert" theme="simple" name="operform" action="${webPath}/mfCusHighChildInfo/insertAjax">
							<dhcc:bootstarpTag property="formcusHighChildInfoBase" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="save('#cusHighChildInfoInsert');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="highChildCancel();"></dhcc:thirdButton>
			</div>
		</div>
		<%--编辑页面--%>
		<div class="container form-container" style="display: none;" id="update">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="cusHighChildInfoUpdate" theme="simple" name="operform" action="${webPath}/mfCusHighChildInfo/updateAjax">
							<dhcc:bootstarpTag property="formcusHighChildInfoBase" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="update('#cusHighChildInfoUpdate');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="highChildCancel();"></dhcc:thirdButton>
			</div>
		</div>

	</body>
	<script type="text/javascript">
		$(function () {
            var idTypeValue = $("input[name=idType]").val();
            if(idTypeValue == "0"){
                //身份证
                var blur = $("input[name=idNum]").attr("onblur");
                var blurAdd = blur + "func_uior_valFormat_tips(this,'idnum');";
                $("input[name=idNum]").attr("onblur",blurAdd);
            }
        });
        //子表取消新增
        function highChildCancel(){
            $("#list").css("display","block");
            $("#update").css("display","none");
            $("#isnert").css("display","none");
        }
        //子表保存
        function save(obj){
            var data = JSON.stringify($(obj).serializeArray());
            var flag = submitJsMethod($(obj).get(0), '');
            if (flag) {
                LoadingAnimate.start();
                $.ajax({
                    type : "POST",
                    data:{ajaxData:data},
                    url : "${webPath}/mfCusHighChildInfo/insertAjax",
                    dataType : "json",
                    success : function(data) {
                        LoadingAnimate.stop();
                        if(data.flag=="success"){
                            window.top.alert(data.msg,1);
                            $("#list").css("display","block");
                            $("#insert").css("display","none");
                            MfCusHighInfo.educationList();
                            MfCusHighInfo.workList();
                            /*  MfCusHighInfo.socialList();*/
                        }else{
                            window.top.alert(data.msg,0);
                        }
                    },
                    error : function(xmlhq, ts, err) {
                        loadingAnimate.stop();
                        console.log(xmlhq);
                        console.log(ts);
                        console.log(err);
                    }
                });
            }
        }
        //修改
        function  update(obj) {
            var data = JSON.stringify($(obj).serializeArray());
            var flag = submitJsMethod($(obj).get(0), '');
            if (flag) {
                LoadingAnimate.start();
                $.ajax({
                    type : "POST",
                    data:{ajaxData:data},
                    url : "${webPath}/mfCusHighChildInfo/updateAjax",
                    dataType : "json",
                    success : function(data) {
                        LoadingAnimate.stop();
                        if(data.flag=="success"){
                            window.top.alert(data.msg,1);
                            $("#list").css("display","block");
                            $("#update").css("display","none");
                            MfCusHighInfo.educationList();
                            MfCusHighInfo.workList();
                            /*MfCusHighInfo.socialList();*/
                        }else{
                            window.top.alert(data.msg,0);
                        }
                    },
                    error : function(xmlhq, ts, err) {
                        loadingAnimate.stop();
                        console.log(xmlhq);
                        console.log(ts);
                        console.log(err);
                    }
                });
            }
        }
	</script>
</html>
