<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_POST['t_Email']) && isset($_POST['t_password'])) {
		$t_Email = $_POST['t_Email'];
		$t_password = trim($_POST['t_password']);
		
		$result = pg_query($conn, "SELECT T.\"t_key\" as key, T.\"t_password\" as password FROM \"Active Tourist\" as T WHERE T.\"t_Email\" = '$t_Email'");
		
		if(!empty($result)) {
		
			if(pg_num_rows($result) > 0) {
				$row = pg_fetch_array($result);
					$tour = array();
					$tour['key'] = $row['key'];
					$tour['password'] = trim($row['password']);
					
					if($tour['password'] == $t_password) {
						$response['success'] = 1;
						$response['key'] = $tour['key'];
						//$response['login'] = array();
						//array_push($response['login'], $tour);
						echo json_encode($response);
					} else {
						$response['success'] = 0;
						$response['message'] = "Wrong Login";
						$response['passwords'] = "P1 ".$tour['password']." P2 ".$t_password;
					
						echo json_encode($response);
					}
			} else {
				$response['success'] = 0;
				$response['message'] = "No login";
					
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