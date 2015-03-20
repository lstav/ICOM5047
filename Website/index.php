<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name  = "viewport" content = "width=device-width, initial-scale = 1.0">
<title>My site</title>
<link href="css/bootstrap.min.css" rel = "stylesheet">
<link href="css/nmmstyles.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script> 
<script src="js/bootstrap.js"></script>
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
<div id="myCarousel" class="carousel slide" data-ride="carousel"> 
  <!-- Indicators -->
  <div class="col-lg-6-home-search">
    <div class="input-group">
      <form action="search_results.php" method="post"> 
      <input type="text" name="search"class="form-control" placeholder="Extreme search...">
      <span class="input-group-btn">
        <button class="btn btn-default" type="button"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
      </span>
      </form>
    </div><!-- /input-group -->
  </div><!-- /.col-lg-6 -->
  <ol class="carousel-indicators">
    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
    <li data-target="#myCarousel" data-slide-to="1"></li>
    <li data-target="#myCarousel" data-slide-to="2"></li>
    <li data-target="#myCarousel" data-slide-to="3"></li>
  </ol>
  
  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    <div class="item active"> <img src="images/Skydiving_over_Cushing.jpg" alt="Chania"> </div>
    <div class="item"> <img src="images/Skydiving_over_Cushing.jpg" class="img-responsive" alt="Aguadilla Skydiving"> </div>
    <div class="item"> <img src="images/Skydiving_over_Cushing.jpg" class="img-responsive" alt="Aguadilla Skydiving"> </div>
    <div class="item"> <img src="images/Skydiving_over_Cushing.jpg" class="img-responsive" alt="Aguadilla Skydiving"> </div>
  </div>
  <!-- Left and right controls --> 
  <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev"> <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span class="sr-only">Previous</span> </a> <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next"> <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> <span class="sr-only">Next</span> </a> </div>

&nbsp
&nbsp

<div class="container">
  <div class="row">
    <div class="col-sm-4">
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sodales metus semper, consectetur dolor vel, sodales nibh. Vivamus sit amet magna nulla. Donec tristique faucibus nunc, a porttitor leo. Fusce ac cursus justo.  </p>
    </div>
    <div class="col-sm-4">
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sodales metus semper, consectetur dolor vel, sodales nibh. Vivamus sit amet magna nulla. Donec tristique faucibus nunc, a porttitor leo. Fusce ac cursus justo.  </p>
    </div>
    <div class="col-sm-4">
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sodales metus semper, consectetur dolor vel, sodales nibh. Vivamus sit amet magna nulla. Donec tristique faucibus nunc, a porttitor leo. Fusce ac cursus justo.  </p>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-4">
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sodales metus semper, consectetur dolor vel, sodales nibh. Vivamus sit amet magna nulla. Donec tristique faucibus nunc, a porttitor leo. Fusce ac cursus justo.  </p>
    </div>
    <div class="col-sm-4">
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sodales metus semper, consectetur dolor vel, sodales nibh. Vivamus sit amet magna nulla. Donec tristique faucibus nunc, a porttitor leo. Fusce ac cursus justo.  </p>
    </div>
    <div class="col-sm-4">
     <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam sodales metus semper, consectetur dolor vel, sodales nibh. Vivamus sit amet magna nulla. Donec tristique faucibus nunc, a porttitor leo. Fusce ac cursus justo.  </p>
    </div>
  </div>
</div>

<div class = "navbar navbar-default navbar-static-bottom">
	<div class = "container">
    	<p class = "navbar-text pull-left"> Bugatagata Rey Misterio</p>
		<a class = "navbar-btn btn-danger btn pull-right"> Subscribe baby</a>
    </div>
</div>
</body>
</html>
