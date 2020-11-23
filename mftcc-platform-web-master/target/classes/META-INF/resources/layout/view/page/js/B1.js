var curMenu = null,
  treeObj = null;
var setting = {
  view: {
    showIcon: false,
    showLine: false,
    selectedMulti: false,
    dblClickExpand: false
  },
  data: {
    simpleData: {
      enable: true
    }
  },
  callback: {
    onNodeCreated: this.onNodeCreated
  }
};
var zNodes = [ {
  id: 1,
  pId: 0,
  url:"",
  name: "微收任务"
}, {
  id: 2,
  pId: 0,
  url:"",
  name: "催收任务"
}, {
  id: 3,
  pId: 0,
  url:"",
  name: "审批任务"
}, {
  id: 4,
  pId: 0,
  url:"",
  name: "检查任务"
} ];
