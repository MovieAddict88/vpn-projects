<?php
require_once 'config.php';

// Check if the user is logged in, otherwise redirect to login page
if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}

// Fetch all networks
$sql = "SELECT * FROM networks";
$networks = [];
if($stmt = $pdo->prepare($sql)){
    if($stmt->execute()){
        $networks = $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}
unset($stmt);
?>

<!DOCTYPE html>
<html>
<head>
    <title>Manage Networks</title>
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
                <li class="nav-item active">
                    <a class="nav-link" href="networks.php">Networks</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="settings.php">Settings</a>
                </li>
            </ul>
        </div>
        <a href="logout.php" class="btn btn-danger">Sign Out</a>
    </nav>
    <div class="container">
        <h2>Manage Networks</h2>
        <a href="network_create.php" class="btn btn-success mb-2">Add New Network</a>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Payload</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <?php foreach($networks as $network): ?>
                <tr>
                    <td><?php echo $network['Name']; ?></td>
                    <td><?php echo $network['Payload']; ?></td>
                    <td>
                        <a href="network_edit.php?id=<?php echo $network['id']; ?>" class="btn btn-primary">Edit</a>
                        <a href="network_delete.php?id=<?php echo $network['id']; ?>" class="btn btn-danger">Delete</a>
                    </td>
                </tr>
                <?php endforeach; ?>
            </tbody>
        </table>
    </div>
</body>
</html>
