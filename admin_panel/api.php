<?php
require_once 'config.php';

// Fetch settings
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

// Fetch servers
$sql = "SELECT * FROM servers";
$servers = [];
if($stmt = $pdo->prepare($sql)){
    if($stmt->execute()){
        $servers = $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}

// Fetch networks
$sql = "SELECT * FROM networks";
$networks = [];
if($stmt = $pdo->prepare($sql)){
    if($stmt->execute()){
        $networks = $stmt->fetchAll(PDO::FETCH_ASSOC);
    }
}

// Combine into the final JSON structure
$output = [
    'Version' => $settings['Version'],
    'ReleaseNotes' => $settings['ReleaseNotes'],
    'ReleaseNotes1' => $settings['ReleaseNotes1'],
    'Servers' => $servers,
    'Networks' => $networks,
];

require_once 'AESCrypt.php';

$password = $_POST['pass'] ?? '';

if (empty($password)) {
    header('HTTP/1.1 400 Bad Request');
    echo json_encode(['error' => 'Password is required']);
    exit;
}

$jsonOutput = json_encode($output);

try {
    $encrypted = AESCrypt::encrypt($password, $jsonOutput);
    header('Content-Type: text/plain');
    echo $encrypted;
} catch (Exception $e) {
    header('HTTP/1.1 500 Internal Server Error');
    echo json_encode(['error' => 'Encryption failed: ' . $e->getMessage()]);
}

?>
