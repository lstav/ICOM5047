
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
	
</body>
</html>

<?php
	
	$response = array();
	
	include_once("dbConnect.php");
	
	if($_POST['g_Email']) {
		$email = $_POST['g_Email'];
		//$password = 123;
		$pass = substr( md5(rand()), 0, 8);
		$salt = '6e663cc2478ebdc49cbce5609ba0305b60d10844';
		$password = $pass.$salt;//.$email;
		$password = sha1($password);
			
		$result = pg_query($dbconn, "UPDATE \"Tour Guide\" as g SET \"g_password\" = '$password' 
		WHERE g.\"g_Email\" = '$email'");
		
		if($result) {
			
			$response['success'] = 1;
			$response['message'] = "Password Changed";
			
			$to      = $email;
			$subject = 'Password Change Request for No Middle Man';
			$message = 'You have requested a new password for No Middle Man. Your password has been changed to 
						'.$pass;
			$headers = 'From: luis.tavarez@outlook.com' . "\r\n" .
				'Reply-To: luis.tavarez@outlook.com' . "\r\n" .
				'X-Mailer: PHP/' . phpversion() .
				'Content-type: text/html; charset=utf-8' . "\r\n";
			$m = mail($to, $subject, $message, $headers);
			
			echo '<div class = "container"><h3>Your new password was sent to: '.$to.'</h3>Please login <a href = "guide_login.php">here</a></div>';
		} else {
			$response['message'] = "Unable to send request";
				
			echo '<div class = "container"><h3>Email not found</h3></div>';
		}
	} else {
		$response['message'] = "Required field(s) is missing";
		
		echo '<div class = "container"><h3>Email not entered</h3></div>';
	}
	pg_close($dbconn);
?>

		