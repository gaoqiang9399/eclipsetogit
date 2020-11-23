<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/anchor.js"></script>
		<style type="text/css">
			.formRang{
				width: 100px;
			}
			input {
				width: 100px;
			}
			.infoTitle{
			    height: 40px;
			    text-align: center;
			    line-height: 40px;
			}
			.bartitle{
			    height: 40px;
			    text-align: center;
			    line-height: 40px;
			}
		</style>
		<script type="text/javascript">
			$(function(){
				var data = [
					{ id: "10", name: "苹果" },
					{ id: "20", name: "香蕉" },
					{ id: "30", name: "西瓜" },
					{ id: "40", name: "桃子" },
					{ id: "50", name: "葡萄" }
				];
				/* //自定义输入域
				new LeasePopCheckBox({
					elem:"#test1",
					data:data
				});
				*/
				//普通表单解析
			/* 	new LeasePopCheckBox({
					elem:"input[name=testBox2]",
					tagType:"formTag",//普通表单标签（横向表单）（bootrapTag也是使用这个）
					btn:function(){//配置新增按钮点击事件，配置显示新增按钮，不配置不显示
						window.top.alert(11111,0);
					},
					selectType:true,//为true是选择为单选，false为复选
					className:"singleSelector"//单选样式
				});
				new LeasePopCheckBox({
					elem:"input[name=testBox]",
					tagType:"formTag",//普通表单标签（横向表单）
					btn:function(){
						window.top.alert(11111,0);
					},
					selectType:false,
					className:"mulitSelector"//复选样式·
				}); */
				//纵向表单解析
				/* new LeasePopCheckBox({
					elem:"input[name=testBox]",
					tagType:"bigForm",//大表单标签（纵向表单）
					btn:function(){
						window.top.alert(11111,0);
					},
					handle:"textarea[name=fieldName]"
				}); */
				
				$('input[name=showVal]').popupList({
					searchOn: true, //启用搜索
					multiple: false, //false单选，true多选，默认多选
					ajaxurl:webPath+"/demo/findByPageForListAjax",//请求数据URL
					handle:"#demo7btn",//其他触发控件
					valueElem:"input[name=realVal]",//真实值选择器
					title: "演示列表",//标题
					changeCallback:function(elem){//回调方法
						console.log(elem.data("values").val());
					},
					tablehead:{//列表显示列配置
						"demoId":"演示编号",
						"demoName":"演示名称",
						"demoTel":{disName:"电话",align:"center",width:"100px"},//disName表头名,align对齐方式,width宽度
						"demoDay":{disName:"累计天数",align:"center"}
					},
					returnData:{//返回值配置
						disName:"demoName",//显示值
						value:"demoId"//真实值
					}
				});
			});
			
		</script>
	</head>
	<body>
		<div class="bigform_content">
			<form  method="post" theme="simple" name="operform" action="${webPath}/demo/insert">
			<%-- 	<dhcc:bigFormTag property="formdemo0011" mode="query"/>  --%>
			<%--	<dhcc:formTag property="formdemo0005" mode="query"/>  --%>
				 <dhcc:bootstarpTag property="formdemo0010" mode="query"/>
				<%-- <dhcc:formTag property="formdemo0010" mode="query"/>--%>
				<div class="formRow">
	    			<dhcc:button value="提交" action="SysUser001" commit="true"></dhcc:button>
	    			<dhcc:button value="ajax提交" action="ajax提交" onclick="ajaxInsert(this.form)"></dhcc:button>
	    		</div>
			</form>	
		</div>
	</body>
</html>
