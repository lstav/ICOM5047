<?php
session_start();
include_once("dbConnect.php");

if(isset($_GET['tid']) && isset($_GET['tdatetime']))
{
	$tid = (int)$_GET['tid'];
	$datetime = $_GET['tdatetime'];
	$uid = $_SESSION['uid'];
	$query = pg_query($dbconn, "INSERT INTO \"Cart\" (tour_key, t_key, reserved_time) VALUES($tid, $uid, '$datetime')");
	$tquery = pg_query($dbconn, "SELECT * FROM \"Tour\" WHERE \"tour_key\"= '$tid'");
	$row = pg_fetch_array($tquery);
	$tname = $row['tour_Name'];
	$tdescription = $row['tour_Desc'];
	$tid = $row['tour_key'];
	$tprice = $row['Price'];
	$tcity = $row['city'];
	$tstate = $row['stateprovidence'];
	$item = '<article class="search-result row">
			<div class="col-xs-12 col-sm-12 col-md-3">
				<a title="Lorem ipsum" class="thumbnail" href="tour_page.php?tid='.$tid.'"><img src="images/'.$tid.'/1.jpg" alt="Lorem ipsum"></a>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-2">
				<ul class="meta-search">
					<li><span><h7>'.$tcity.'</h7></span></li>
					<li> <span>'.$tstate.'</span></li>
				</ul>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-7 excerpet">
				<h3><a href="tour_page.php?tid='.$tid.' title="">'.$tname.'</a></h3>
				<p>'.$tdescription.'</p>	
				<h4><strong>Reserved time: '.$datetime.'</strong></h4>					
                <h4>Price: '.$tprice.'</h4>
			</div>
			<span class="clearfix borda"></span>
		</article>';
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
<?php include 'header.php';?>
<script>
$(document).ready(function(){
	$("#language").attr("style", "");
});
</script>
</head>
<body>
<?php include 'navbar.php';?>
<h1> Item added to cart </h1>
<?php echo $item;?>
<a type="button" class="btn btn-default" href="index.php">Continue Shopping</a>
<a type="button" class="btn btn-default" href = "cart.php">Checkout</a> 

</body>
</html>