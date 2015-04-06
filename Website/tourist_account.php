<?php 
// Moved here

function test_input($data)
{
   $data = trim($data);
   $data = stripslashes($data);
   $data = htmlspecialchars($data);
   return $data;
}
session_start();
if($_SESSION['uemail'])
{
	    $uemail = $_SESSION['uemail'];
		$uid = $_SESSION['uid'];
		$ufname = $_SESSION['ufname'];
		$ulname = $_SESSION['ulname'];
		$upass = $_SESSION['upass'];
		$errorMsg = '';
		if(!empty($_POST['new-uemail'])||!empty($_POST['new-ufname'])||!empty($_POST['new-ulname'])||!empty($_POST['new-upass']))
		{
			include_once("dbConnect.php");
			if(!empty($_POST['new-uemail']))
			{
				$newuemail =  test_input(strip_tags($_POST['new-uemail']));
				if (!filter_var($newuemail, FILTER_VALIDATE_EMAIL)) 
				{
  					$errorMsg = "Invalid email format"; 
				}
				else
				{
					$query = pg_query($dbconn, "UPDATE \"Tourist\" SET \"tEmail\" = '$newuemail' WHERE \"tEmail\" = '$uemail'");
					$uemail = $_SESSION['uemail'] = $newuemail;
				}
			}
			if(!empty($_POST['new-ufname']))
			{
				
				$newufname = test_input(strip_tags($_POST["new-ufname"]));
				if (!preg_match("/^[a-zA-Z ]*$/",$newufname)) 
				{
				  $errorMsg = "Only letters and white space allowed"; 
				}
				else
				{
					$query = pg_query($dbconn, "UPDATE \"Tourist\" SET \"t_FName\" = '$newufname' WHERE \"tEmail\" = '$uemail' AND \"t_FName\" = '$ufname'");
					$ufname = $_SESSION['ufname'] = $newufname;
				}
			}
			if(!empty($_POST['new-ulname']))
			{
				$newulname = test_input(strip_tags($_POST["new-ulname"]));
				if (!preg_match("/^[a-zA-Z ]*$/",$newulname)) 
				{
				  $errorMsg = "Only letters and white space allowed"; 
				}
				else
				{
					$query = pg_query($dbconn, "UPDATE \"Tourist\" SET \"t_LName\" = '$newulname' WHERE \"tEmail\" = '$uemail' AND \"t_LName\" = '$ulname'");
					$ulname = $_SESSION['ulname'] = $newulemail;
				}
			}
			if(!empty($_POST['new-upass']))
			{
				$oldupass = md5(strip_tags($_POST['old-upass']));
				$newupass = md5(strip_tags($_POST['new-upass']));
				if($oldupass = $upass)
				{
					$query = pg_query($dbconn, "UPDATE \"Tourist\" SET \"tPassword\" = '$newupass' WHERE \"tEmail\" = '$uemail' AND \"tPassword\" = '$upass'");
				}
				else
				{
					$errorMsg = "Invalid password";
					$upass = $_SESSION['upass'] = $newupass;
				}
			}
		}
				$output = '<div class="control-group">
									<label class="control-label" for="inputFirst">First
									Name</label>
		
									<div class="controls">
										<input id="inputFirst" name = "new-ufname" placeholder="'.$ufname.'">
									</div>
								</div>
		
								<div class="control-group">
									<label class="control-label" for="inputLast">Last
									Name</label>
		
									<div class="controls">
										<input id="inputLast" name = "new-ulname" placeholder="'.$ulname.'" type="text">
									</div>
								</div>
		
								<div class="control-group">
									<label class="control-label" for="inputEmail">Email</label>
		
									<div class="controls">
										<input id="inputEmail" name = "new-uemail" placeholder="'.$uemail.'" type="text">
									</div>
								</div>';
			$tid = 1;
			$tcity = 'Arecibo';
			$tstate = 'PR';
			$tname = 'Arecibo Skydiving';
			$tdescription = 'Jump to the skies and marvel at the beautiful Puerto Rican view.';
			$tprice = '$250.00';
			$orders .= '<article class="search-result row">
			<div class="col-xs-12 col-sm-12 col-md-3">
				<a title="Lorem ipsum" class="thumbnail"><img src="images/'.$tid.'/1.jpg" alt="Lorem ipsum"></a>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-2">
				<ul class="meta-search">
					<li><span><h7>'.$tcity.'</h7></span></li>
					<li> <span>'.$tstate.'</span></li>
				</ul>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-7 excerpet">
				<h3><a title="">'.$tname.'</a></h3>
				<p>'.$tdescription.'</p>	
				<h5>'.$tprice.'</h5>
				<a style="" class="btn btn-default" href="write_review.php?tid='.$tid.'" type="button">Write Review <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></a>			
			</div>
			<span class="clearfix borda"></span>
		</article>';
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php include 'header.php';?>
<script>
$(document).ready(function(){
	$("#pass-btn").click(function(){
        $("#password-field").toggle();
    });
});
</script>
</head>
<body>
<?php include 'navbar.php';?>
<div class="container-fluid" style="margin-top: 10px;">
<h1>My Account</h1>
  <div role="tabpanel"> 
    
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation" class="active"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab" aria-expanded="true">Profile</a></li>
      <li role="presentation" class=""><a href="#tour-orders" aria-controls="tour-orders" role="tab" data-toggle="tab" aria-expanded="false">Tour Orders</a></li>
    </ul>
    
    <!-- Tab panes -->
    <div class="tab-content">
      <div role="tabpanel" class="tab-pane active" id="profile">
        <div class="area">
          <form class="form-horizontal" method = "post" action="tourist_account.php">
            <div><font color="red"><?php echo $errorMsg; ?></font></div>
            <?php echo $output;?>
            <button type = "button" id = "pass-btn" class="btn btn-default btn-sm" style="margin-top: 5px;">Change Password</button>
            <div id = "password-field" style="display: none;">
              <div class="control-group">
                <label class="control-label" for="inputPassword"> Old Password</label>
                <div class="controls">
                  <input id="inputPassword" name="old-upass" placeholder="" type="password">
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" for="inputPassword"> New Password</label>
                <div class="controls">
                  <input id="inputPassword" name = "new-upass" placeholder="" type="password">
                </div>
              </div>
            </div>
            <div class="control-group">
              <div class="controls">
                <button class="btn btn-success" type="submit" style="margin-top: 5px;">Update</button>
              </div>
            </div>
          </form>
        </div>
      </div>
      <div role="tabpanel" class="tab-pane" id="tour-orders">
        <div style="margin-top: 10px;" class="area">
          <?php echo $orders; ?>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>