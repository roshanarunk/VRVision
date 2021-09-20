<?php

require "conn.php";

$doc_id = $_POST['doc_id'];



$data = array();

$sql = "SELECT id FROM clients WHERE doctor_id like '$doc_id';";




$stmt = $conn->prepare($sql);

$stmt -> execute();

$stmt->bind_result($id);

while($stmt->fetch()){
	$temp = [
	'id'=>$id,
	];

	array_push($data, $temp);

}

echo json_encode($data);

?>