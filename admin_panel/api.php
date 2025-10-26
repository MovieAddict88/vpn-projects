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

header('Content-Type: application/json');
echo json_encode($output);

?>
