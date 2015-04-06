<?php 
// Moved here
$tid = 1;
$trating = 4.5;
$ratingScript= '$("#rating'.$tid.'").raty();';
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<?php include 'header.php';?>
</head>
<body>
<?php include 'navbar.php';?>
<div class="container-fluid" style="margin-top: 10px;">
<h1>Write a Review:</h1>
<form method = "post" action = "tourist_account.php">
<div class="control-group">
                            <label class="control-label" for="inputLast">Tour
                            Rating: <div id='rating1'></div></label> 
</div>
<div class="control-group">
                            <label class="control-label" for="inputLast">Tour
                            Description: </label>
							<textarea id="inputLast" class="form-control" rows="5" name = "new-tdesc" placeholder="E.g. Fly away with us..." type="text"></textarea>
                        </div>  
                        <div style = "margin-top:10px" class="control-group">
                            <div class="controls">
                               </label> <button class="btn btn-success" type="submit">Submit Review</button> <!--<button class="btn" type="button">Help</button>-->
                            </div>
                        </div>
</form>
</div>
</body>
<script>$.fn.raty.defaults.path = '../Website/images'; <?php echo $ratingScript;?></script>
</html>