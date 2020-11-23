;
var parLoanuse = function(window, $) {
	/** 贷款投向细类选择组件(征信用) */
	var _getFincUseSmList = function(fincUseObj, thiz) {
		var fincUse = fincUseObj.value;
		if (!fincUse) {
			alert("请先选择贷款投向", 0);
			return;
		}
		top.dialog({
			id : "areaDialog",
			title : '征信用途',
			url : webPath + '/parLoanuse/getFincUseSmList?fincUse=' + fincUse,
			width : 350,
			height : 420,
			backdropOpacity : 0,
			onshow : function() {
				this.returnValue = null;
			},
			onclose : function() {
				if (this.returnValue) {
					thiz.value = this.returnValue.disName;
					$("[name='fincUseSm']").val(this.returnValue.disNo);
				}
			}
		}).showModal();
	}
	/** 贷款投向细类选择组件(征信用)选择组件 */
	var _getFincUseSmListSelect = function(fincUseObj, thiz) {
		var fincUse = fincUseObj.val();
		var popsfincUseSmDes= $("input[name='popsfincUseSmDes']");
		if (popsfincUseSmDes.length>0) {
            var fincUseSmDes = null;
            $.ajax({
                url: webPath + '/parLoanuse/getFincUseSmListSelect?fincUse=' + fincUse,
                type:"post",
                async:false,
                dataType:"json",
                success:function(data){
                    fincUseSmDes = JSON.parse(data.items);
                    $("input[name=popsfincUseSmDes]").popupSelection("updateItems",fincUseSmDes);
                }
            });
		}else{
            $("input[name=fincUseSmDes]").popupSelection({
                ajaxUrl: webPath + '/parLoanuse/getFincUseSmListSelect?fincUse=' + fincUse,
                searchOn : true,//启用搜索
                multiple : false,
                valueClass : "show-text",//自定义显示值样式
                ztree : true,
                ztreeSetting : setting,
                title : "贷款投向细类",
                changeCallback:function(elem){//回调方法
                    var node = elem.data("treeNode");
                    $("input[name=fincUseSmShow]").val(node.name);
                    $("input[name=fincUseSmDesc]").val(elem.data("values").val());
                }
            });
            var element = $("input[name=fincUseSmDes]").attr('name');
            $('input[name='+element+']').next().click();
		}

	}

    /** 贷款投向细类选择组件(全部细类投向) */
    var _getAllFincUseSmList = function(fincUseObj, thiz) {
        top.dialog({
            id : "areaDialog",
            title : '征信用途',
            url : webPath + '/parLoanuse/getAllFincUseSmList?appId='+appId,
            width : 350,
            height : 420,
            backdropOpacity : 0,
            onshow : function() {
                this.returnValue = null;
            },
            onclose : function() {
                if (this.returnValue) {
                    thiz.value = this.returnValue.disName;
                    $("[name='fincUseSm']").val(this.returnValue.disNo);
                }
            }
        }).showModal();
    }


	return {
		getFincUseSmList : _getFincUseSmList,
        getFincUseSmListSelect : _getFincUseSmListSelect,
        getAllFincUseSmList : _getAllFincUseSmList,
	};
}(window, jQuery);