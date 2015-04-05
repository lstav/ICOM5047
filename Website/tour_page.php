<?php 
include_once("dbConnect.php");
$tid = (int) $_GET['tid'];
$query = pg_query($dbconn, "SELECT * FROM \"Tour\" WHERE \"tour_key\" = '$tid'");
$count = pg_num_rows($query);
if($count > 0)
{
	$row = pg_fetch_array($query);
	$tourName = $row['tour_Name'];
	$tdescription = $row['tour_Desc'];
	$tid = $row['tour_key'];
	$tprice = $row['Price'];
	$tcity = $row['city'];
	$trating = $row['avg_rating'];
	$tstate = $row['stateprovidence'];
	$tduration = $row['Duration'];
	$taddress = $row['address'];
	$squery = pg_query($dbconn, "SELECT \"reserved_time\" FROM \"Tour Availability\" WHERE \"tour_key\" = '$tid' AND \"available\" = 'TRUE'");
	while($row = pg_fetch_array($squery))
	{
		$sdate = $row['reserved_time'];
		$datetime = explode(" ",$sdate);
		$date = $datetime[0];
		$time = $datetime[1];
		
		$sessionList .= '<a href="add_to_cart.php?tid='.$tid.'&tdatetime='.$sdate.'" class="list-group-item">Date: '.$date.' Time: '.$time.'</a>';
	}
}
else
{
	echo "Tour not found";
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
<?php include 'header.php';?>
</head>
<body>
<?php include 'navbar.php';?>
<script>
$(document).ready(function(){
	$("#language").attr("style", "");
});
</script>
<div class="container-fluid">
  <div class="content-wrapper">
    <div class="item-container">
      <div class="container">
        <div class ="row">
          <div class="col-md-6"> <img id="item-display" src="images/<?php echo $tid?>/1.jpg" alt="" style="max-width:100%"> </div>
          <div class="col-md-6">
            <div class="product-title"><?php echo $tourName;?></div>
            <div class="product-desc"><?php echo $tdescription.'<br><strong>Estimated Duration: '.$tduration.' hours </strong><br>'.$taddress .'<br>'. $tstate?></div>
            <div class="product-rating"><i class="fa fa-star gold"></i> <i class="fa fa-star gold"></i> <i class="fa fa-star gold"></i> <i class="fa fa-star gold"></i> <i class="fa fa-star-o"></i> </div>
            <hr>
            <div class="product-price"><?php echo $tprice; ?></div>
            <div class="product-stock"></div>
            <hr>
            <div class="btn-group cart">
              <button type="button" class="btn btn-success" data-toggle="modal" data-target="#myModal"> Add to cart </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="container-fluid">
      <div class="col-md-12 product-info">
        <ul id="myTab" class="nav nav-tabs nav_tabs">
          <li class="active"><a href="#service-one" data-toggle="tab">Tour guide</a></li>
          <li><a href="#service-two" data-toggle="tab">Reviews</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
          <div class="tab-pane fade in active" id="service-one">
            <section class="container product-info">
              <h3>Tour Business:</h3>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi pharetra congue commodo. Proin lacinia est at nulla scelerisque, commodo volutpat arcu egestas. Cras facilisis lectus ornare turpis varius, posuere ullamcorper felis sodales. Sed blandit magna nisl.
              <li>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi pharetra congue commodo. Proin lacinia est at nulla scelerisque, commodo volutpat</li>
              <li>Arcu egestas. Cras facilisis lectus ornare turpis varius, posuere ullamcorper felis sodales. Sed blandit magna nisl.</li>
              <li>E commodo. Proin lacinia est at nulla scelerisque, commodo volutpat</li>
            </section>
          </div>
          <div class="tab-pane fade" id="service-two">
            <section class="container"> </section>
          </div>
        </div>
        <hr>
      </div>
    </div>
  </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">Available Sessions</h4>
      </div>
      <div class="modal-body">
        <div class="list-group"> <?php echo $sessionList;?></div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>