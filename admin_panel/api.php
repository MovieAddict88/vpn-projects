<?php
include 'config.php';

// Fetch settings
$settings_result = $conn->query("SELECT * FROM settings WHERE id = 1");
$settings = $settings_result->fetch_assoc();

// Fetch servers
$servers_result = $conn->query("SELECT * FROM servers");
$servers = array();
while ($row = $servers_result->fetch_assoc()) {
    $servers[] = $row;
}

// Fetch payloads
$payloads_result = $conn->query("SELECT * FROM payloads");
$payloads = array();
while ($row = $payloads_result->fetch_assoc()) {
    // Convert boolean values
    $row['isSSL'] = (bool)$row['isSSL'];
    $row['isPayloadSSL'] = (bool)$row['isPayloadSSL'];
    $row['isSlow'] = (bool)$row['isSlow'];
    $row['isHatok'] = (bool)$row['isHatok'];
    $row['isInject'] = (bool)$row['isInject'];
    $row['isDirect'] = (bool)$row['isDirect'];
    $row['isWeb'] = (bool)$row['isWeb'];
    $payloads[] = $row;
}

// Combine into the final structure
$output = array(
    'Version' => $settings['Version'],
    'ReleaseNotes' => $settings['ReleaseNotes'],
    'ReleaseNotes1' => $settings['ReleaseNotes1'],
    'Servers' => $servers,
    'Networks' => $payloads
);

// Output the JSON
header('Content-Type: application/json');
echo json_encode($output, JSON_PRETTY_PRINT);
?>