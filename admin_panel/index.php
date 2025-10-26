<?php
require_once 'config.php';

// Check if the user is logged in, otherwise redirect to login page
if(!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true){
    header("location: login.php");
    exit;
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Panel</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body{ font: 14px sans-serif; }
        .wrapper{ width: 800px; padding: 20px; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">VPN Admin Panel</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="index.php">Dashboard</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="servers.php">Servers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="networks.php">Networks</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="settings.php">Settings</a>
                </li>
            </ul>
        </div>
        <a href="logout.php" class="btn btn-danger">Sign Out</a>
    </nav>
    <div class="wrapper">
        <h2>Dashboard</h2>
        <p>Welcome to the VPN Admin Panel. Here you can manage your VPN servers and networks.</p>

        <div class="row">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Servers</h5>
                        <p class="card-text">Manage your VPN servers.</p>
                        <a href="servers.php" class="btn btn-primary">Go to Servers</a>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Networks</h5>
                        <p class="card-text">Manage your VPN networks.</p>
                        <a href="networks.php" class="btn btn-primary">Go to Networks</a>
                    </div>
                </div>
            </div>
        </div>

        <h3 class="mt-4">Server Status</h3>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Server Name</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <?php
                // Fetch all servers
                $sql = "SELECT * FROM servers";
                $servers = [];
                if($stmt = $pdo->prepare($sql)){
                    if($stmt->execute()){
                        $servers = $stmt->fetchAll(PDO::FETCH_ASSOC);
                    }
                }
                unset($stmt);

                function ping($host, $port, $timeout) {
                    $tB = microtime(true);
                    $fP = fSockOpen($host, $port, $errno, $errstr, $timeout);
                    if (!$fP) { return "down"; }
                    $tA = microtime(true);
                    return round((($tA - $tB) * 1000), 0)." ms";
                }

                foreach($servers as $server) {
                    echo "<tr>";
                    echo "<td>" . htmlspecialchars($server['Name']) . "</td>";
                    $status = ping($server['ServerIP'], $server['ServerPort'], 1);
                    if ($status == "down") {
                        echo "<td><span class='badge badge-danger'>Down</span></td>";
                    } else {
                        echo "<td><span class='badge badge-success'>Up</span> (" . $status . ")</td>";
                    }
                    echo "</tr>";
                }
                ?>
            </tbody>
        </table>
    </div>
</body>
</html>
