<?php

require "conn.php";

$id = $_POST['id'];
$user_id = $_POST['user_id'];


$data = array();

$sql = "SELECT fov, brightness, size, pos_x, pos_y, off_x, off_y FROM versions WHERE id = $id and user_id = $user_id;";




$stmt = $conn->prepare($sql);

$stmt -> execute();

$stmt->bind_result($fov, $brightness, $size, $pos_x, $pos_y, $off_x, $off_y);

while($stmt->fetch()){
	$temp = [
	'fov'=>$fov,
	'brightness'=>$brightness,
	'size'=>$size,
	'pos_x'=>$pos_x,
	'pos_y'=>$pos_y,
	'off_x'=>$off_x,
	'off_y'=>$off_y,
	];

	array_push($data, $temp);

}

echo json_encode($data);

?>