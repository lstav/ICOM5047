<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_POST['t_Email']) && isset($_POST['t_password']) && isset($_POST['t_FName']) && isset($_POST['t_LName']) && isset($_POST['t_address']) && isset($_POST['t_telephone'])) {
		$t_Email = $_POST['t_Email'];
		$password = $_POST['t_password'];
		$t_FName = $_POST['t_FName'];
		$t_LName = $_POST['t_LName'];
		$t_address = $_POST['t_address'];
		$t_telephone = $_POST['t_telephone'];
		
		
		$result = pg_query($conn, "Insert into \"Tourist\" (\"t_Email\",\"t_password\",\"t_FName\",\"t_LName\",\"t_telephone\",\"t_Address\") 
		Values('$t_Email','$password','$t_FName','$t_LName',$t_telephone,'$t_address') ");
		
		if($result) {
			
			$response['success'] = 1;
			$response['message'] = "Register Completed";
			
			$to      = $t_Email;
			$subject = 'Verify Email';
			$message = 'Please follow this link to verify your account
						"http://kiwiteam.ece.uprm.edu/NoMiddleMan/Android%20Files/verifyForm.html"';
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