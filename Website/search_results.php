<?php
$dbconn = pg_connect("host=ec2-54-163-228-58.compute-1.amazonaws.com port=5432 dbname=d1euoqskdhvaef user=ujpdfamgiytpnl password=LMYPpiuM4-WEFYqYRPYvEeFmpg");

if(isset($POST['search']))
{
	$searchq = $_POST['search'];
	$searchq = preg_replace("#[^0-9a-z]#i", "", $searchq);
	
	$query = pg_query($dbconn, "SELECT * FROM 'Tour' WHERE 'tour_Name' LIKE '%$search1%' OR 'country' LIKE '%$search1%'");
	$count = pg_num_rows($query);
	if($count == 0)
	{
		$output = 'No extreme results';
	}
	else
	{
		while($row = pg_fetch_array($query))
		{
			$tname = $row['tour_Name'];
			$tdescription = $row['tour_Desc'];
			$tid = $row['tour_key'];
			
			$output .= '<article class="search-result row">
			<div class="col-xs-12 col-sm-12 col-md-3">
				<a href="#" title="Lorem ipsum" class="thumbnail"><img src="images/"'.$tid.'"/1.jpg" alt="Lorem ipsum"></a>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-2">
				<ul class="meta-search">
					<li><i class="glyphicon glyphicon-calendar"></i> <span>02/15/2014</span></li>
					<li><i class="glyphicon glyphicon-time"></i> <span>4:28 pm</span></li>
					<li><i class="glyphicon glyphicon-tags"></i> <span>Skydiving</span></li>
				</ul>
			</div>
			<div class="col-xs-12 col-sm-12 col-md-7 excerpet">
				<h3><a href="#" title="">.$tname.</a></h3>
				<p>'.$tdescription.'</p>						
                <span class="plus"><a href="#" title="Lorem ipsum"><i class="glyphicon glyphicon-plus"></i></a></span>
			</div>
			<span class="clearfix borda"></span>
		</article>';
		}
	}
}

?>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta name  = "viewport" content = "width=device-width, initial-scale = 1.0">
	<title>My site</title>
    <!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<link href="css/bootstrap.min.css" rel = "stylesheet">
	<link rel="stylesheet" href="css/nmmstyles.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script> 
	<script src="js/bootstrap.js"></script>
	<style type="text/css">
        @import "http://fonts.googleapis.com/css?family=Roboto:300,400,500,700";
    </style>
</head>

<body>
<div class = "navbar navbar-inverse navbar-static-top" style="background-color: #8CC739;border-bottom-width: 0px; margin-bottom: 0px; color:#21BEDE">
  <div class = "container"> <a href ="#" class = "navbar-brand">NoMiddleMan.com</a>
    <button class = "navbar-toggle" data-toggle = "collapse" data-target = ".navHeaderCollapse"> <span class = "icon-bar"></span> <span class = "icon-bar"></span> <span class = "icon-bar"></span> </button>
    <div class = "collapse navbar-collapse navHeaderCollapse">
      <ul class = "nav navbar-nav navbar-right">
        <li class = "active"><a href = '#'>Login</a></li>
        <li class  = "dropdown"> <a href = "#" class = "dropdown-toggle" data-toggle = "dropdown">USD($)<b class = "caret"></b></a>
          <ul class = "dropdown-menu">
            <li><a href = "#">EUR(€)</a></li>
            <li><a href = "#">NZD($)</a></li>
            <li><a href = "#">CAD($)</a></li>
          </ul>
        </li>
        <li class  = "dropdown"> <a style = "padding-top:20px" href = "#" class = "dropdown-toggle" data-toggle = "dropdown"><img src = images/us.png><b class = "caret"></b></a>
          <ul class = "dropdown-menu">
            <li><a href = "#">EUR(€)</a></li>
            <li><a href = "#">NZD($)</a></li>
            <li><a href = "#">CAD($)</a></li>
          </ul>
        </li>
        <li><a href="#"> Cart <span class="glyphicon glyphicon-shopping-cart"></span> </a></li>
      </ul>
    </div>
  </div>
</div>
<div style = "margin-top: 10px;" class="container">
    <div style = "margin-bottom: 10px" class="col-lg-6">
    		<div class="input-group">
      			<input type="text" class="form-control" placeholder="Extreme search...">
      				<span class="input-group-btn">
        				<button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
      				</span>
    		</div><!-- /input-group -->
  		</div><!-- /.col-lg-6 -->	
    <hgroup class="mb20">
    	
		<h1>Search Results</h1>
		<h2 class="lead"><strong class="text-danger">3</strong> results were found for the search for <strong class="text-danger">Skydiving</strong></h2>
    								
	</hgroup>

    <section class="col-xs-12 col-sm-6 col-md-12">
		<?=$output?>				
	</section>
</div>

</body>
</html>
