<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_REQUEST['tour_key'])) {
		$tour_key = $_REQUEST['tour_key'];
		
		$result = pg_query($conn, "Select * From \"Tour\" natural join \"Location\" natural join \"Tour Guide\" Where \"tour_key\" = $tour_key");
		
		if(!empty($result)) {
			if(pg_num_rows($result) > 0) {
				$row = pg_fetch_array($result);
				
				$response['tour'] = array();
				
				$tour = array();
				$tour['tour_key'] = $row['tour_key'];
				$tour['tour_Name'] = $row['tour_Name'];
				$tour['tour_Desc'] = $row['tour_Desc'];
				$tour['Facebook'] = $row['Facebook'];
				$tour['Youtube'] = $row['Youtube'];
				$tour['Instagram'] = $row['Instagram'];
				$tour['Twitter'] = $row['Twitter'];
				$tour['Price'] = $row['Price'];				
				$tour['extremeness'] = $row['extremeness'];
				$tour['city'] = $row['city'];
				$tour['state/province'] = $row['state/province'];
				$tour['country'] = $row['country'];
				$tour['g_Email'] = $row['g_Email'];
				$tour['g_FName'] = $row['g_FName'];
				$tour['g_LName'] = $row['g_LName'];
				$tour['g_License'] = $row['g_License'];
				$tour['company'] = $row['company'];
				$tour['g_telephone'] = $row['g_telephone'];
				
				$response['success'] = 1;
				
				array_push($response['tour'], $tour);
				
				echo json_encode($response);
			} else {
				$response['success'] = 0;
				$response['message'] = "No tours found";
				
				echo json_encode($response);
			}
		} else {
				$response['success'] = 0;
				$response['message'] = "No tourist found";
				
				echo json_encode($response);
			}
	} else {
		$response['success'] = 0;
		$response['message'] = "Required field(s) is missing";
		
		echo json_encode($response);
	}
	pg_close($conn);
?>