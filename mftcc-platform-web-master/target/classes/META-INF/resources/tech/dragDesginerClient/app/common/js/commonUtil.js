/*公共方法类*/
define(function(require, exports, module) {

    /*将px转换为对应数值
    * eg:97px -> 97
    */
    exports.changePxToNum = function(pxNum){
        return new Number(pxNum.replace("px",""));
    }
    /**
     * function：获取str2在str1中的个数
     * parameter：str1 str2
     * return:个数
     */
    exports.getStrInStrLen = function(str1,str2){
        var returnStr = 0;
        if(str1!==undefined&&str1!=null&&str1.length>0&&str2!==undefined&&str2!=null&&str2.length>0){
            if(str1.split(str2).length>0){
                returnStr = str1.split(str2).length-1;
            }
        }
        return returnStr;
    }
    /**
     * function:判断是否是空的
     * parameter:字符串
     * return:false(是空的),true(不是空的)
     **/
    window.isNotNull=function(str){
        var returnStr = false;
        str = $.trim(str);
        if(str!=""&&str!="&nbsp;"){
            returnStr = true;
        }
        return returnStr;
    }
    /**
     * function:获取colspan
     * parameter:td对象
     * return:数字类型
     */
    window.getColSpan=function(tdObj){
        var colspan=1;
        if($(tdObj).attr("colspan")!==undefined){
            colspan = $(tdObj).attr("colspan");
        }
        return colspan;
    }
    /**
     * function:获取rowspan
     * parameter:td对象
     * return:数字类型
     */
    window.getRowSpan=function(tdObj){
        var rowspan=1;
        if($(tdObj).attr("colspan")!==undefined){
            rowspan = $(tdObj).attr("rowspan");
        }
        return rowspan;
    }
});