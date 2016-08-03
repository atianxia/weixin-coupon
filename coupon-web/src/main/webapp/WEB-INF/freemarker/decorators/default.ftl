<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${title}</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${resRoot}/jeasyui/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="${resRoot}/jeasyui/themes/default/color.css"/>
<link rel="stylesheet" type="text/css" href="${resRoot}/jeasyui/demo.css">
<link rel="stylesheet" type="text/css" href="${resRoot}/jeasyui/themes/icon.css"/>
<script type="text/javascript" src="${resRoot}/jeasyui/jquery.min.js"></script>
<script type="text/javascript" src="${resRoot}/jeasyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${resRoot}/jeasyui/locale/easyui-lang-zh_CN.js"></script>
${head}
</head> 
<body>
<#--根路径-->
	<input type="hidden" id="rootPath" name="rootPath" value="${rootPath}"/>
	<#--上下文-->
	<input type="hidden" id="contextPath" name="contextPath" value="${contextPath}"/>
${body}
</body> 
</html> 

