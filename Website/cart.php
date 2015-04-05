<?php
session_start();
	include_once("dbConnect.php");
	
	$uid = $_SESSION['uid'];
	$cquery = pg_query($dbconn, "SELECT * FROM \"Cart\" NATURAL INNER JOIN \"Tour\" WHERE t_key = $uid;");
	$count = pg_num_rows($cquery);
	$totalPrice = '';
	if($count == 0)
	{
		$cartList = 'No items in cart';
	}
	else
	{
		$totalPrice = 0;
		while($row = pg_fetch_array($cquery))
		{
			$tname = $row['tour_Name'];
			$tdescription = $row['tour_Desc'];
			$tid = $row['tour_key'];
			$tprice = $row['Price'];
			$totalPrice += (float)preg_replace("/([^0-9\\.])/i", "", $tprice);
			$tcity = $row['city'];
			$tstate = $row['stateprovidence'];
			$cid = $row['cid'];
			$reserved_time = $row['reserved_time'];
			
			
			$cartList .= '<article class="search-result row">
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
				<h4><strong>Reserved time: '.$reserved_time.'</strong></h4>					
                <span style="text-align : right"><h4>'.$tprice.'</h4></span>
				<a style="float:right; color: white; background-color: #248dc1" type="button" class="btn btn-default btn-md" href = "remove_from_cart.php?cid='.$cid.'">
  <span class="glyphicon glyphicon-minus" aria-hidden="true"></span></a>
			</div>
			<span class="clearfix borda"></span>
		</article>';
		}
		setlocale(LC_MONETARY, 'en_US');
		$totalPrice =  money_format('%(#10n', $totalPrice);
		$checkOut = '<div style = "float: right"> <h3> Total price:'.$totalPrice.'</h3> <img src="https://www.paypal.com/en_US/i/btn/btn_xpressCheckout.gif" align="right" style="margin-right:7px;"></div>';
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
<h2>Shopping Cart </h2>
<div style="margin-right: 20px;margin-left: 20px;" class="list-group"> <?php echo $cartList;?></div>
<?php echo $checkOut;?>
</body>
</html>