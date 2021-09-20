<?php

$db_name = "vrvision_vrvision";
$mysql_username = "vrvision_arun36670";
$mysql_password = "password123";
$server_name = "localhost";
$conn = mysqli_connect($server_name, $mysql_username, $mysql_password, $db_name);
if (mysqli_ping($conn)){
} else {
	echo "Error: " . mysqli_error($conn);
}
?>
