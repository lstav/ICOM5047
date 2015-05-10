
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
	
</body>
</html>

<?php
	
	$response = array();
	
	include_once("dbConnect.php");
	
	if($_POST['t_Email']) {
		$email = $_POST['t_Email'];
		//$password = 123;
<<<<<<< Updated upstream
		$pass = substr( md5(rand()), 0, 8);
=======
<<<<<<< HEAD
		$pass = substr( md5(rand()), 0, 7);
=======
		$pass = substr( md5(rand()), 0, 8);
>>>>>>> origin/master
>>>>>>> Stashed changes
		$salt = '6e663cc2478ebdc49cbce5609ba0305b60d10844';
		$password = $pass.$salt;//.$email;
		$password = sha1($password);
			
		$result = pg_query($dbconn, "UPDATE \"Tourist\" as t SET \"t_password\" = '$password' 
		WHERE t.\"t_Email\" = '$email'");
		
		if($result) {
			
			$response['success'] = 1;
			$response['message'] = "Password Changed";
			
			$to      = $email;
			$subject = 'Password Change Request';
			$message = 'This is your new password '.$pass;
			$headers = 'From: luis.tavarez@outlook.com' . "\r\n" .
				'Reply-To: luis.tavarez@outlook.com' . "\r\n" .
				'X-Mailer: PHP/' . phpversion();
			mail($to, $subject, $message, $headers);
			
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
			echo '<div class = "container"><h3>Your new password was sent to: '.$to.'</h3>Please login <a href = "login.php">here</a></div>';
		} else {
			$response['message'] = "Unable to send request";
				
			echo '<div class = "container"><h3>Email not found</h3></div>';
=======
>>>>>>> Stashed changes
			echo "Email notification sent";
		} else {
			$response['message'] = "Unable to send request";
				
			echo json_encode($response);
<<<<<<< Updated upstream
=======
>>>>>>> origin/master
>>>>>>> Stashed changes
		}
	} else {
		$response['message'] = "Required field(s) is missing";
		
<<<<<<< Updated upstream
		echo json_encode($response);
=======
<<<<<<< HEAD
		echo '<div class = "container"><h3>Email not found</h3></div>';
=======
		echo json_encode($response);
>>>>>>> origin/master
>>>>>>> Stashed changes
	}
	pg_close($dbconn);
?>

		