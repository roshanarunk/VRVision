<?php

require "conn.php";

$id = $_POST['id'];



$data = array();

$sql = "SELECT name, username, code, doctor_id, version_id FROM clients WHERE id like '$id';";




$stmt = $conn->prepare($sql);

$stmt -> execute();

$stmt->bind_result($name, $username, $code, $doctor_id, $version_id);

while($stmt->fetch()){
	$temp = [
	'name'=>$name,
	'username'=>$username,
	'code'=>$code,
	'doctor_id'=>$doctor_id,
	'version_id'=>$version_id,
	];

	array_push($data, $temp);

}

echo json_encode($data);

?>