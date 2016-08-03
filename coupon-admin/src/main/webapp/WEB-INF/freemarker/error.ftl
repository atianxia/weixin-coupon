<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>错误信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head> 
<body>
<#-- 错误信息提示 -->
    <div class="errorMessageShowTop"></div>
    	<span></span>
     <p>
         <b><font color="red" size="5">
         	错误：${(exception.friendlyMessage)!""}${(exception.message)!""}
         </font> 	
       	
    </p>
    <a href="http://www.suning.com/" class="backIndex"></a>
  </div>
    
</body> 
</html> 

