<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../baselist.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
</head>
	<%-- <link rel="stylesheet" href="${ctx }/components/zTree/css/demo.css" type="text/css"> --%>
	<link rel="stylesheet" href="${ctx }/components/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${ctx }/components/zTree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${ctx }/components/zTree/js/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${ctx }/components/zTree/js/jquery.ztree.excheck-3.5.min.js"></script>
<body>
<script type="text/javascript">

	var setting = {
		check: {
			enable: true
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};
	
	//准备数据
	//{ id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
	//获取数据方式一：后台跳转进入当前页面的时候，将数据传过来
	//.......二：ajax--ajax请求后台的模块数据
	//表示页面加载完成的做法：
	//$().ready();
	//$(document).ready();
	$(function(){
		$.ajax({
			url:'roleAction_roleModuleStr.action?id=${id}',
			type:'get',
			dataType:'text',
			success:initZtree
		});
		
	});
	var zTreeObj;
	function initZtree(data){
		var zNodes = eval("("+data+")");
		
		zTreeObj = $.fn.zTree.init($("#jxTree"), setting, zNodes);
	}
	
	//页面显示的一个个“checkbox”，其实是span，而每个span都是一个node(节点)，每个node上都有id，pId，name，checked，open   5个属性
	function submitCheckedNodes(){
		//获取ztree上选中的节点
		var nodes = zTreeObj.getCheckedNodes(true);
		var str = "";
		for(var i=0;i<nodes.length;i++){
			str+=nodes[i].id+",";
		}
		//截取最后一个逗号
		str = str.substring(0,str.length-1);
		//将页面选中的值的id放入隐藏域，然后一并提交至后台
		$("#moduleIds").val(str);
	}

</script>



<form name="icform" method="post">
	<!-- id：是role的id -->
	<input type="hidden" name="id" value="${id}"/>
	<input type="hidden" id="moduleIds" name="moduleIds" value="" />
<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="submitCheckedNodes();formSubmit('roleAction_module','_self');this.blur();">保存</a></li>
<li id="back"><a href="#" onclick="formSubmit('roleAction_list','_self');this.blur();">返回</a></li>
</ul>
  </div>
</div>
</div>
</div>
   
<div class="textbox" id="centerTextbox">
  <div class="textbox-header">
  <div class="textbox-inner-header">
  <div class="textbox-title">
    配置 [${name}] 角色的模块  
  </div> 
  </div>
  </div>

<div>  
	<ul id="jxTree" class="ztree" style="width:230px; overflow:auto;"></ul>
</div>
 
 
</form>
</body>
</html>

