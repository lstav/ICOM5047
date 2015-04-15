<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_POST['t_Email']) && isset($_POST['password']) && isset($_POST['t_FName']) && isset($_POST['t_LName']) && isset($_POST['t_BDate']) && isset($_POST['t_telephone'])) {
		$t_Email = $_POST['t_Email'];
		$password = $_POST['password'];
		$t_FName = $_POST['t_FName'];
		$t_LName = $_POST['t_LName'];
		$t_BDate = $_POST['t_BDate'];
		$t_telephone = $_POST['t_telephone'];
		
		
		$result = pg_query($conn, "Insert into \"Tourist\" (\"t_Email\",\"t_password\",\"t_FName\",\"t_LName\",\"t_BDate\",\"t_telephone\") 
		Values('$t_Email','$password','$t_FName','$t_LName','$t_BDate',$t_telephone) ");
		
		if($result) {
			
			$response['success'] = 1;
			$response['message'] = "Register Completed";
			
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