<?php
require_once 'config.php';

if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}

$id = $_GET['id'];
$server = null;

$sql = "SELECT * FROM servers WHERE id = ?";
if ($stmt = $pdo->prepare($sql)) {
    $stmt->execute([$id]);
    $server = $stmt->fetch(PDO::FETCH_ASSOC);
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $sql = "UPDATE servers SET Name = ?, FLAG = ?, ServerIP = ?, ServerPort = ?, SSLPort = ?, ProxyPort = ?, ServerUser = ?, ServerPass = ?, Sfrep = ?, sInfo = ?, Slowchave = ?, Nameserver = ?, servermessage = ? WHERE id = ?";
    if ($stmt = $pdo->prepare($sql)) {
        $stmt->execute([$_POST['Name'], $_POST['FLAG'], $_POST['ServerIP'], $_POST['ServerPort'], $_POST['SSLPort'], $_POST['ProxyPort'], $_POST['ServerUser'], $_POST['ServerPass'], $_POST['Sfrep'], $_POST['sInfo'], $_POST['Slowchave'], $_POST['Nameserver'], $_POST['servermessage'], $id]);
        header("location: servers.php");
    }
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Server</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2>Edit Server</h2>
        <form action="server_edit.php?id=<?php echo $id; ?>" method="post">
            <div class="form-group">
                <label>Name</label>
                <input type="text" name="Name" class="form-control" value="<?php echo $server['Name']; ?>">
            </div>
            <div class="form-group">
                <label>FLAG</label>
                <input type="text" name="FLAG" class="form-control" value="<?php echo $server['FLAG']; ?>">
            </div>
            <div class="form-group">
                <label>Server IP</label>
                <input type="text" name="ServerIP" class="form-control" value="<?php echo $server['ServerIP']; ?>">
            </div>
            <div class="form-group">
                <label>Server Port</label>
                <input type="text" name="ServerPort" class="form-control" value="<?php echo $server['ServerPort']; ?>">
            </div>
            <div class="form-group">
                <label>SSL Port</label>
                <input type="text" name="SSLPort" class="form-control" value="<?php echo $server['SSLPort']; ?>">
            </div>
            <div class="form-group">
                <label>Proxy Port</label>
                <input type="text" name="ProxyPort" class="form-control" value="<?php echo $server['ProxyPort']; ?>">
            </div>
            <div class="form-group">
                <label>Server User</label>
                <input type="text" name="ServerUser" class="form-control" value="<?php echo $server['ServerUser']; ?>">
            </div>
            <div class="form-group">
                <label>Server Pass</label>
                <input type="text" name="ServerPass" class="form-control" value="<?php echo $server['ServerPass']; ?>">
            </div>
            <div class="form-group">
                <label>Sfrep</label>
                <textarea name="Sfrep" class="form-control"><?php echo $server['Sfrep']; ?></textarea>
            </div>
            <div class="form-group">
                <label>sInfo</label>
                <textarea name="sInfo" class="form-control"><?php echo $server['sInfo']; ?></textarea>
            </div>
            <div class="form-group">
                <label>Slowchave</label>
                <textarea name="Slowchave" class="form-control"><?php echo $server['Slowchave']; ?></textarea>
            </div>
            <div class="form-group">
                <label>Nameserver</label>
                <input type="text" name="Nameserver" class="form-control" value="<?php echo $server['Nameserver']; ?>">
            </div>
            <div class="form-group">
                <label>Server Message</label>
                <textarea name="servermessage" class="form-control"><?php echo $server['servermessage']; ?></textarea>
            </div>
            <input type="submit" class="btn btn-primary" value="Submit">
            <a href="servers.php" class="btn btn-secondary">Cancel</a>
        </form>
    </div>
</body>
</html>
