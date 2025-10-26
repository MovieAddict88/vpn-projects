<?php
require_once 'config.php';

if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $sql = "INSERT INTO networks (Name, Payload, SNI, pInfo, Slowdns, WebProxy, WebPort, isSSL, isPayloadSSL, isSlow, isHatok, isInject, isDirect, isWeb) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    if ($stmt = $pdo->prepare($sql)) {
        $stmt->execute([$_POST['Name'], $_POST['Payload'], $_POST['SNI'], $_POST['pInfo'], $_POST['Slowdns'], $_POST['WebProxy'], $_POST['WebPort'], $_POST['isSSL'], $_POST['isPayloadSSL'], $_POST['isSlow'], $_POST['isHatok'], $_POST['isInject'], $_POST['isDirect'], $_POST['isWeb']]);
        header("location: networks.php");
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Add Network</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2>Add New Network</h2>
        <form action="network_create.php" method="post">
            <div class="form-group">
                <label>Name</label>
                <input type="text" name="Name" class="form-control">
            </div>
            <div class="form-group">
                <label>Payload</label>
                <textarea name="Payload" class="form-control"></textarea>
            </div>
            <div class="form-group">
                <label>SNI</label>
                <input type="text" name="SNI" class="form-control">
            </div>
            <div class="form-group">
                <label>pInfo</label>
                <textarea name="pInfo" class="form-control"></textarea>
            </div>
            <div class="form-group">
                <label>Slowdns</label>
                <input type="text" name="Slowdns" class="form-control">
            </div>
            <div class="form-group">
                <label>WebProxy</label>
                <input type="text" name="WebProxy" class="form-control">
            </div>
            <div class="form-group">
                <label>WebPort</label>
                <input type="text" name="WebPort" class="form-control">
            </div>
            <div class="form-group">
                <label>isSSL</label>
                <input type="text" name="isSSL" class="form-control">
            </div>
            <div class="form-group">
                <label>isPayloadSSL</label>
                <input type="text" name="isPayloadSSL" class="form-control">
            </div>
            <div class="form-group">
                <label>isSlow</label>
                <input type="text" name="isSlow" class="form-control">
            </div>
            <div class="form-group">
                <label>isHatok</label>
                <input type="text" name="isHatok" class="form-control">
            </div>
            <div class="form-group">
                <label>isInject</label>
                <input type="text" name="isInject" class="form-control">
            </div>
            <div class="form-group">
                <label>isDirect</label>
                <input type="text" name="isDirect" class="form-control">
            </div>
            <div class="form-group">
                <label>isWeb</label>
                <input type="text" name="isWeb" class="form-control">
            </div>
            <input type="submit" class="btn btn-primary" value="Submit">
            <a href="networks.php" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>
