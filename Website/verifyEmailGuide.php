<?php
	
	$response = array();
	
	include_once("dbConnect.php");
	
	if(isset($_POST['g_Email']) && isset($_POST['g_password'])) {
		$g_Email = $_POST['g_Email'];
		$g_password = trim($_POST['g_password']);
		$salt = '6e663cc2478ebdc49cbce5609ba0305b60d10844';
		$g_password = $g_password.$salt;//.$t_Email;
		$g_password = sha1($g_password);	
		
		$result = pg_query($dbconn, "SELECT T.\"g_key\" as key, T.\"verification\" as password 
		FROM \"Tour Guide\" as T WHERE T.\"g_Email\" = '$g_Email'");
		
		if(!empty($result)) {
		
			if(pg_num_rows($result) > 0) {
				$row = pg_fetch_array($result);
					$tour = array();
					$tour['key'] = $row['key'];
					$tour['password'] = trim($row['password']);
					
					if($tour['password'] == $g_password) {
						$response['success'] = 1;
						$response['key'] = $tour['key'];
						$key = $tour['key'];
						//$response['login'] = array();
						//array_push($response['login'], $tour);
						//echo json_encode($response);
						$verify = pg_query($dbconn, "UPDATE \"Tour Guide\" as t 
													SET \"g_isActive\" = True 
													WHERE t.\"g_key\" = $key");
						header("Location: guide_login.php");
							
						
					} else {
						$response['success'] = 0;
						$response['message'] = "Wrong Login";
					
						//echo json_encode($response);
					}
			} else {
				$response['success'] = 0;
				$response['message'] = "No login";
					
				//echo json_encode($response);
			}
		} else {
			$response['success'] = 0;
			$response['message'] = "No tours found";
				
			//echo json_encode($response);
		}
	} else {
		$response['success'] = 0;
		$response['message'] = "Required field(s) is missing";
		
		//echo json_encode($response);
	}
	pg_close($dbconn);
?>