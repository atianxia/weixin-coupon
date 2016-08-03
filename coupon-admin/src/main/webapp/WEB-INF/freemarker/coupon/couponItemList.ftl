<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>券发放情况查询</title>
	<#include "/common.ftl">
</head>
<body> 
	<#-- 表单查询 DIV 开始 -->
		<div id="searchPanel" class="easyui-panel" title="券发放情况查询"
			style="text-align:left;width:auto;background: #fafafa;" collapsible="false"
			minimizable="false" maximizable="false" data-options="fit:true">
	<#-- 表单查询 table 开始 -->
	<div class="grid-toolbar" >
	<form id="form1" name="form1" method="post">
	<table class="tblContent"  style="width:60%;">
		<tr>
			<td><span>优惠券ID: </span><input id="couponId" name="couponId" value="${couponId}"/> </td>
			<td><span>优惠券名称: </span> <input id="couponName" name="couponName" value="${couponName}"> </td>
			<td><span>用户编码:</span> <input id="userId" name="userId" value="${userId}"></td>
		</tr>
		<tr>
		
			<td>
				<span>领取时间: </span><input id="receiveStartTime" name="receiveStartTime" class="easyui-datetimebox"></input>
				<br>&nbsp;&nbsp;---&nbsp;
				<input id="receiveEndTime" name="receiveEndTime" class="easyui-datetimebox"></input>
			</td>
			<td>
				<span>创建时间: </span>&nbsp;<input id="createStartTime" name="createStartTime" class="easyui-datetimebox"></input>
				<br>&nbsp;&nbsp;---&nbsp;&nbsp;
				<input id="createEndTime" name="createEndTime" class="easyui-datetimebox"></input>
			</td>
			<td>	
			    <a href="javascript:void(0);"  class="easyui-linkbutton" data-options="iconCls:'icon-search'"  onclick="doSearch()">查询</a>
				<a href="javascript:void(0);"  class="easyui-linkbutton" data-options="iconCls:'icon-no'"  onclick="doReset()">重置</a>				
			</td>
		</tr>
	</table>
	</form>
	</div>
	<div id="tt" class="grid-auto">  
	</div>
 </div>
	<script type="text/javascript" src="${resRoot}/js/coupon/couponItemList.js"></script>
</body>
</html>