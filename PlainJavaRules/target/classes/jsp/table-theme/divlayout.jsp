<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
<title><tiles:insertAttribute name="title" ignore="true" />
</title>
<body>
<center>
	<div id="container" style="width: 900px">
		<div id="header" style="background-color: #FFA500;">
			<tiles:insertAttribute name="header" />
			<h2 align="center">Plain Java Rules</h2>	
		</div>


		<div id="content"
			style="background-color: #EEEEEE;  width: 900px; float: left;">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
</center>

</body>
</html>