<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_POST['t_key']) && isset($_POST['ts_key'])) {
		$t_key = $_POST['t_key'];
		$ts_key = $_POST['ts_key'];
		
		$result = pg_query($conn, "UPDATE \"Participants\" SET \"p_isActive\" = False, \"p_quantity\" = 0
		WHERE \"t_key\" = $t_key AND \"ts_key\" = $ts_key");
		
		if($result) {
			
			$response['success'] = 1;
			$response['message'] = "Removed from Cart";
			
			echo json_encode($response);
		} else {
			$response['success'] = 0;
			$response['message'] = "Remove Failure";
				
			echo json_encode($response);
		}
	} else {
		$response['success'] = 0;
		$response['message'] = "Required field(s) is missing";
		
		echo json_encode($response);
	}
	pg_close($conn);
?>