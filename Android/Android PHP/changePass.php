<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_POST['key']) && isset($_POST['password'])) {
		$key = $_POST['key'];
		$password = $_POST['password'];
			
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