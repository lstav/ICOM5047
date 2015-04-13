<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_REQUEST['t_Email']) && isset($_REQUEST['t_password'])) {
		$t_Email = $_REQUEST['t_Email'];
		$t_password = $_REQUEST['t_password'];
		
		$result = pg_query($conn, "SELECT T.\"t_key\" as key, T.\"t_password\" as password FROM \"Tourist\" as T WHERE T.\"t_Email\" = '$t_Email'");
		
		if(!empty($result)) {
		
			if(pg_num_rows($result) > 0) {
				$row = pg_fetch_array($result);
					$tour = array();
					$tour['key'] = $row['key'];
					$tour['password'] = $row['password'];
					
					if($t_password - $tour['password'] == 0) {
						$response['success'] = 1;
						$response['login'] = array();
						array_push($response['login'], $tour);
						echo json_encode($response);
					} else {
						$response['success'] = 0;
						$response['message'] = "Wrong Login";
					
						echo json_encode($response);
					}
			} else {
				$response['success'] = 0;
				$response['message'] = "Wrong Login";
					
				echo json_encode($response);
			}
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