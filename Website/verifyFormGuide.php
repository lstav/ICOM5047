<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php include 'header.php';?>
</head>
<body>
<?php include 'guide_navbar_login.php';?>
<script>
$(document).ready(function(){
	$("#language").attr("style", "");
});
</script>
<div class="container">
	<h2>Please input your tour guide verification code to verify your account.</h2>
    <form action="verifyEmailGuide.php" method="post" style="width:40%">
 
<div class="form-group">
      <input type="text" class="form-control" name="g_Email" placeholder="Email">
</div>
<div class = "form-group">
      <input type="password" class = "form-control" placeholder="Verification Code" name="g_password"><br>
        <button class="btn btn-default" type="submit">Submit</button>
      </span>
    </div><!-- /input-group --></form>
</div>

</body>
</html>
		
		