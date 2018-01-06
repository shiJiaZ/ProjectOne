<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../../base.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<script type="text/javascript" src="${ctx }/js/datepicker/WdatePicker.js"></script>
</head>

<body>
<form name="icform" method="post" action="poiAction_upload" enctype="multipart/form-data">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
  <div id="navMenubar">
<!-- <ul>
<li id="save"><a href="#" onclick="formSubmit('outProductAction_print','_self');this.blur();">打印</a></li>
</ul> -->
  </div>
</div>
</div>
</div>
   
  <div class="textbox-title">
	<img src="${ctx }/skin/default/images/icon/folder_edit.png"/>
  批量导入工厂表数据<font color='red'>请先下载模板，并且必须严格按照模板准备数据，否则导入失败</font>
  </div> 

 
    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">模板下载：</td>
	            <td class="tableContent">
					<a href="poiAction_download">工厂模板.xls</a>
				</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">请选择：</td>
	            <td class="tableContent">
					<input type="file" name="upload">
				</td>
	        </tr>			
	        <tr>
	            <td class="columnTitle" colspan="2"><input type="submit" value="一键上传"></td>
	        </tr>	
	        
		</table>
	</div>
 
 
</form>
</body>
</html>

