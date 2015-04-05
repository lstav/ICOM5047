<?php
include_once("dbConnect.php");
session_start();
$loginOuput = '';
if(isset($_SESSION['uid']))
{
	$uid = $_SESSION['uid'];
	$cartCount = '';
	$uemail = $_SESSION['uemail'];
	$ufname = $_SESSION['ufname'];
	$navLink = "tourist_account.php";
	$loginOuput = '<li class = "dropdown"><a class = "dropdown-toggle" data-toggle = "dropdown"> Hello  '.$ufname.'! <b class = "caret"></b></a>
          <ul class = "dropdown-menu">
            <li><a href = "'.$navLink.'">My Account</a></li>
            <li><a href = "sign_out.php">Sign Out</a></li>
          </ul></li>';
	$cquery = pg_query($dbconn, "SELECT * FROM \"Cart\" WHERE \"t_key\" = '$uid'");
	if(pg_num_rows($cquery) > 0)
	{
		$cartCount = pg_num_rows($cquery);
	}
	
	
}
else
{
	$loginOuput = '<li class = "active"> <a href = "login.php"> Login </a></li>';
}
?>

<div class = "navbar navbar-inverse navbar-static-top" style="background-color: #8CC739;border-bottom-width: 0px; margin-bottom: 0px; color:#21BEDE">
  <div class = "container"> <a href ="index.php" class = "navbar-brand">NoMiddleMan</a>
    <button class = "navbar-toggle" data-toggle = "collapse" data-target = ".navHeaderCollapse"> <span class = "icon-bar"></span> <span class = "icon-bar"></span> <span class = "icon-bar"></span> </button>
    <div class = "collapse navbar-collapse navHeaderCollapse">
      <ul class = "nav navbar-nav navbar-right">
        <?php echo $loginOuput?>
        <li class  = "dropdown"> 
        </li>
        <li class  = "dropdown"> <a id = "language" style = "padding-top:20px" href = "#" class = "dropdown-toggle" data-toggle = "dropdown"><img src = images/us.png><b class = "caret"></b></a>
          <ul class = "dropdown-menu">
            <li><a href = "#">EUR(€)</a></li>
            <li><a href = "#">NZD($)</a></li>
            <li><a href = "#">CAD($)</a></li>
          </ul>
        </li>
        <li><a href="cart.php"> Cart <span class="glyphicon glyphicon-shopping-cart"></span> <?php echo $cartCount;?></a></li>
      </ul>
    </div>
  </div>
</div>