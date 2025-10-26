<?php
require_once 'config.php';

if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}

if(isset($_GET["id"]) && !empty($_GET["id"])){
    $sql = "DELETE FROM servers WHERE id = ?";

    if($stmt = $pdo->prepare($sql)){
        $stmt->bindParam(1, $_GET["id"]);

        if($stmt->execute()){
            header("location: servers.php");
            exit();
        } else{
            echo "Oops! Something went wrong. Please try again later.";
        }
    }

    unset($stmt);
    unset($pdo);
}
?>
