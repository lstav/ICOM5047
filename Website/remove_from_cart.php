<?php
include_once("dbConnect.php");
$cid  = (int)$_GET['cid'];
$query = pg_query($dbconn, "DELETE FROM \"Cart\" WHERE cid = $cid");
header("Location: cart.php");
?>