<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
	<form  id="readReportForm" action="import.do" method="post" enctype="multipart/form-data"  >
		<label for="file">File</label>
		<input id="file" type="file" name="file" />
		<p><button type="submit">Read</button></p>	
	</form>
<P>  The time on the server is ${serverTime}. </P>
</body>
</html>
