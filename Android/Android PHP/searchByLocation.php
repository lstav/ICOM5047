<?php
	
	$response = array();
	
	include_once("dbconnection.php");
	
	if(isset($_POST['country']) && isset($_POST['state']) && isset($_POST['city']) && isset($_POST['order']) && isset($_POST['by'])) {
		$country = $_POST['country'];
		$state = $_POST['state'];
		$city = $_POST['city'];
		$order = trim($_POST['order']);
		$by = trim($_POST['by']);
		
		
		if($country == "Any") {
			$country = "\"Country\"";
		} else {
			$temp = $country;
			$country = "'$temp'";
		}
		
		if($state == "Any") {
			$state = "\"State-Province\"";
		} else {
			$temp2 = $state;
			$state = "'$temp2'";
		}
		
		if($city == "Any") {
			$city = "\"City\"";
		} else {
			$temp3 = $city;
			$city = "'$temp3'";
		}
		
		$result = pg_query($conn, "Select T.tour_key as Key, upper(T.\"tour_Name\") as Name, 
									T.\"Price\" as Price, T.\"extremeness\" as Extremeness, 
									T.\"tour_photo\" as Photo
									From \"Tour Info\" as T
									Where \"Country\" = $country and 
									\"State-Province\" = $state and 
									\"City\" = $city
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