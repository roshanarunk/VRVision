<?php

require "conn.php";

$user_id = $_POST['user_id'];



$data = array();

$sql = "SELECT size, pos_x, pos_y, off_x, off_y, date FROM versions WHERE user_id like '$user_id';";




$stmt = $conn->prepare($sql);

$stmt -> execute();

$stmt->bind_result($size, $pos_x, $pos_y, $off_x, $off_y, $date);

while($stmt->fetch()){
	$temp = [
	'size'=>$size,
	'pos_x'=>$pos_x,
	'pos_y'=>$pos_y,
	'off_x'=>$off_x,
	'off_y'=>$off_y,
	'date'=>$date,
	];

	array_push($data, $temp);

}

echo json_encode($data);

?>