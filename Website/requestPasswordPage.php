<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php include 'header.php';?>
</head>
<body>
<?php include 'navbar.php';?>
<script>
$(document).ready(function(){
	$("#language").attr("style", "");
});
</script>
	<h2>Enter your email to recover password.</h2>
	<h4>An email will be sent with new password.</h4>
	 <form action="requestPasswordChange.php" method="post">
		email: <input type="text" name="t_Email"><br>
		<input type="submit">
 </form>
</body>
</html>
		