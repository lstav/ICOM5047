<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_POST['key']) && isset($_POST['password'])) {
		$key = $_POST['key'];
		//$res_email = "Select \"GetEmail\"(t_key::bigint)";
		
		//$email = $res_email['t_Email'];
		
		$password = $_POST['password'];
		$salt = '6e663cc2478ebdc49cbce5609ba0305b60d10844';
		$password = $password.$salt; //.$email;
		$password = sha1($password);
			
		$result = pg_query($conn, "UPDATE \"Tourist\" as t SET \"t_password\" = '$password' WHERE t.\"t_key\" = $key");
		
		if($result) {
			
			$response['success'] = 1;
			$response['message'] = "Password Changed";
			
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