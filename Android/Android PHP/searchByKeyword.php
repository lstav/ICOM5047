<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_POST['keyword']) && isset($_POST['order']) && isset($_POST['by'])) {
		$keyword = trim($_POST['keyword']);
		$order = trim($_POST['order']);
		$by = trim($_POST['by']);
		
		$result = pg_query($conn, "Select T.tour_key as Key, upper(T.\"tour_Name\") as Name, 
		T.\"tour_Desc\" as Description, T.\"Price\" as Price, 
		T.\"extremeness\" as Extremeness, T.\"tour_photo\" as Photo FROM \"Tour\" as T 
		Where upper(concat(T.\"tour_Name\",' ',T.\"tour_Desc\")) like upper('%$keyword%')
		Order By (T.\"$order\") $by");
		
		if(pg_num_rows($result) > 0) {
			$response['tours'] = array();
			
			while($row = pg_fetch_array($result)) {
				$tour = array();
				$tour['key'] = $row['key'];
				$tour['name'] = $row['name'];
				$tour['price'] = $row['price'];
				$tour['extremeness'] = $row['extremeness'];
				$tour['photo'] = $row['photo'];
				
				array_push($response['tours'], $tour);
			}
			
			$response['success'] = 1;
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