/**
 * Created by jzh on 2015/1/22.
 */
define(function(require, exports, module) {
    $(function () {
        var mvObj=false;//移动标记
        /*mydrag set class myDragDiv on div-div拖动*/
        $(".myDragDiv").hover(function(){
            var obj=$(this);
            var _x,_y;//鼠标离控件左上角的相对位置
            var _z=1000;//新对象的z-index
            var _wid= obj.width();
            var _hei= obj.height();
            var docE =document.documentElement;
            obj.mousedown(function(e){
                mvObj=true;
                _x=e.pageX-parseInt(obj.css("left"));//获得左边位置
                _y=e.pageY-parseInt(obj.css("top"));//获得上边位置
            });
            obj.mouseup(function(e){
                mvObj=false;
                obj.css({"z-index":_z-(0)}).fadeTo(10,1);//松开鼠标后停止移动并恢复成不透明
                //console.log(mvObj);
            });
            obj.mousemove(function(e){
                if(mvObj){
                    //console.log(mvObj);
                    //obj.css({"z-index":_z-(-1)}).fadeTo(50,.5);//点击后开始拖动并透明显示
                    var x=e.pageX-_x;//移动时根据鼠标位置计算控件左上角的绝对位置
                    if(x<=0){x=0};
                    x=Math.min(docE.clientWidth-_wid,x)-5;
                    var y=e.pageY-_y;
                    if(y<=0){y=0};
                    y=Math.min(docE.clientHeight-_hei,y)-5;
                    obj.css({
                        top:y,left:x
                    });//控件新位置
                }
            });
        },function(){
            mvObj=false;
        });
    });
});