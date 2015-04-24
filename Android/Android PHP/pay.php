<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_POST['t_key'])) {
		$t_key = $_POST['t_key'];
		
		$result = pg_query($conn, "Select T.ts_key as ts_key, T.p_quantity as quantity FROM \"Shopping Cart\" as T 
		Where T.t_key=$t_key and T.\"s_isActive\" = True and T.\"passed\" = False and T.\"isfull\" = False");

		if(pg_num_rows($result) > 0) {
			//$response['tours'] = array();
			
			while($row = pg_fetch_array($result)) {
				$ts_key = $row['ts_key'];
				$qty = $row['quantity'];
				
				$pay = pg_query($conn, "UPDATE \"Participants\" SET \"Payed\" = (\"Payed\" + $qty), \"p_quantity\" = 0
				WHERE \"t_key\" = $t_key  AND \"ts_key\" = $ts_key "); 
 				/*$quantity = pg_query(" SELECT \"p_quantity\" FROM \"Participants\"  WHERE \"t_key\" = $t_key  AND \"ts_key\" = $ts_key "); 
 				$row = pg_fetch_array($quantity); 
 				$qty = $row['p_quantity']; */
 				$tsupdt = pg_query($conn, " UPDATE \"Tour Session\" SET \"Availability\" = (\"Availability\" - $qty) WHERE \"ts_key\" = $ts_key "); 
				
				//array_push($response['tours'], $tour);
			}
			
			$response['success'] = 1;
			echo json_encode($response);
		} else {
			$response['success'] = 0;
			$response['message'] = "No tours found on cart";
				
			echo json_encode($response);
		}
	} else {
		$response['success'] = 0;
		$response['message'] = "Required field(s) is missing";
		
		echo json_encode($response);
	}
	pg_close($conn);
?>