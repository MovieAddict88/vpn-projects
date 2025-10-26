<?php
require_once 'config.php';

if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}

$settings = [];
$sql = "SELECT * FROM settings";
if($stmt = $pdo->prepare($sql)){
    if($stmt->execute()){
        $results = $stmt->fetchAll(PDO::FETCH_ASSOC);
        foreach($results as $row) {
            $settings[$row['key']] = $row['value'];
        }
    }
}

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    foreach($_POST as $key => $value) {
        $sql = "UPDATE settings SET value = ? WHERE `key` = ?";
        if($stmt = $pdo->prepare($sql)){
            $stmt->execute([$value, $key]);
        }
    }
    header("location: settings.php");
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Settings</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">VPN Admin Panel</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="index.php">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="servers.php">Servers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="networks.php">Networks</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link" href="settings.php">Settings</a>
                </li>
            </ul>
        </div>
        <a href="logout.php" class="btn btn-danger">Sign Out</a>
    </nav>
    <div class="container">
        <h2>Manage Settings</h2>
        <form action="settings.php" method="post">
            <div class="form-group">
                <label>Version</label>
                <input type="text" name="Version" class="form-control" value="<?php echo $settings['Version']; ?>">
            </div>
            <div class="form-group">
                <label>Release Notes</label>
                <textarea name="ReleaseNotes" class="form-control"><?php echo $settings['ReleaseNotes']; ?></textarea>
            </div>
            <div class="form-group">
                <label>Release Notes 1</label>
                <textarea name="ReleaseNotes1" class="form-control"><?php echo $settings['ReleaseNotes1']; ?></textarea>
            </div>
            <input type="submit" class="btn btn-primary" value="Save">
        </form>
    </div>
</body>
</html>
