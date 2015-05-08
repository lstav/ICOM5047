<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if($_POST['t_Email']) {
		$email = $_POST['t_Email'];
		//$password = 123;
		$pass = substr( md5(rand()), 0, 7);
		$salt = '6e663cc2478ebdc49cbce5609ba0305b60d10844';
		$password = $pass.$salt;//.$email;
		$password = sha1($password);
			
		$result = pg_query($conn, "UPDATE \"Tourist\" as t SET \"t_password\" = '$password' 
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
			
			echo json_encode($response);
		} else {
			$response['success'] = 0;
			$response['message'] = "No tours found";
				
			echo json_encode($response);
		}
	} else {
		$response['success'] = 0;
		$response['message'] = "Required field(s) is missing";
		
		echo json_encode($response);
	}
	pg_close($conn);
?>