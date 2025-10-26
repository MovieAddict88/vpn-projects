<?php

require_once __DIR__ . '/../src/AESCrypt.php';
require_once __DIR__ . '/../src/Store.php';
require_once __DIR__ . '/../src/Config.php';

use App\AESCrypt;
use App\Store;
use App\Config;

$store = new Store(__DIR__ . '/../data/config.json');

$path = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$method = $_SERVER['REQUEST_METHOD'] ?? 'GET';

function json_response($data, int $status = 200) {
    http_response_code($status);
    header('Content-Type: application/json');
    echo json_encode($data, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES);
    exit;
}

if ($path === '/' && $method === 'GET') {
    // Simple admin homepage
    $conf = $store->load();
    header('Content-Type: text/html; charset=utf-8');
    echo '<!doctype html><meta charset="utf-8"><title>Config Admin</title>';
    echo '<h1>Config Admin</h1>';
    echo '<form method="post" action="/save-meta">';
    echo 'Version <input name="Version" value="' . htmlspecialchars($conf['Version']) . '"><br>';
    echo 'ReleaseNotes <input name="ReleaseNotes" value="' . htmlspecialchars($conf['ReleaseNotes']) . '" size="80"><br>';
    echo 'ReleaseNotes1 <input name="ReleaseNotes1" value="' . htmlspecialchars($conf['ReleaseNotes1']) . '" size="80"><br>';
    echo '<button type="submit">Save Meta</button>';
    echo '</form>';

    echo '<h2>Servers</h2>';
    echo '<pre>' . htmlspecialchars(json_encode($conf['Servers'], JSON_PRETTY_PRINT)) . '</pre>';
    echo '<form method="post" action="/add-server">';
    foreach ([
        'Name','FLAG','ServerIP','ServerPort','SSLPort','ProxyPort','ServerUser','ServerPass','Sfrep','sInfo','Slowchave','Nameserver','servermessage'
    ] as $f) {
        echo $f . ' <input name="' . $f . '"><br>';
    }
    echo '<button type="submit">Add Server</button>';
    echo '</form>';

    echo '<h2>Networks</h2>';
    echo '<pre>' . htmlspecialchars(json_encode($conf['Networks'], JSON_PRETTY_PRINT)) . '</pre>';
    echo '<form method="post" action="/add-network">';
    foreach ([
        'Name','Payload','SNI','pInfo','Slowdns','WebProxy','WebPort','isSSL','isPayloadSSL','isSlow','isHatok','isInject','isDirect','isWeb'
    ] as $f) {
        echo $f . ' <input name="' . $f . '"><br>';
    }
    echo '<button type="submit">Add Network</button>';
    echo '</form>';

    echo '<h2>Endpoints</h2>';
    echo '<ul>';
    echo '<li>GET <a href="/config.json">/config.json</a></li>';
    echo '<li>GET /Updater?password=PASSWORD</li>';
    echo '</ul>';
    exit;
}

if ($path === '/save-meta' && $method === 'POST') {
    $conf = $store->load();
    $conf['Version'] = $_POST['Version'] ?? $conf['Version'];
    $conf['ReleaseNotes'] = $_POST['ReleaseNotes'] ?? $conf['ReleaseNotes'];
    $conf['ReleaseNotes1'] = $_POST['ReleaseNotes1'] ?? $conf['ReleaseNotes1'];
    $store->save($conf);
    header('Location: /');
    exit;
}

if ($path === '/add-server' && $method === 'POST') {
    $conf = $store->load();
    $server = [];
    foreach ([
        'Name','FLAG','ServerIP','ServerPort','SSLPort','ProxyPort','ServerUser','ServerPass','Sfrep','sInfo','Slowchave','Nameserver','servermessage'
    ] as $f) {
        $server[$f] = $_POST[$f] ?? '';
    }
    $conf['Servers'][] = $server;
    $store->save($conf);
    header('Location: /');
    exit;
}

if ($path === '/add-network' && $method === 'POST') {
    $conf = $store->load();
    $network = [];
    foreach ([
        'Name','Payload','SNI','pInfo','Slowdns','WebProxy','WebPort','isSSL','isPayloadSSL','isSlow','isHatok','isInject','isDirect','isWeb'
    ] as $f) {
        $val = $_POST[$f] ?? '';
        if (in_array($f, ['isSSL','isPayloadSSL','isSlow','isHatok','isInject','isDirect','isWeb'], true)) {
            $network[$f] = filter_var($val, FILTER_VALIDATE_BOOLEAN);
        } else {
            $network[$f] = $val;
        }
    }
    $conf['Networks'][] = $network;
    $store->save($conf);
    header('Location: /');
    exit;
}

if ($path === '/config.json' && $method === 'GET') {
    $conf = $store->load();
    header('Content-Type: application/json');
    echo json_encode($conf, JSON_UNESCAPED_SLASHES);
    exit;
}

if ($path === '/Updater' && $method === 'GET') {
    // Same path used by the Android app vagol task; require password param
    // default to app's hardcoded password for compatibility
    $password = $_GET['password'] ?? Config::ENCRYPTION_PASSWORD;
    $conf = $store->load();
    $plain = json_encode($conf, JSON_UNESCAPED_SLASHES);
    try {
        $enc = AESCrypt::encrypt($password, $plain);
    } catch (\Throwable $e) {
        http_response_code(500);
        echo 'Encrypt error';
        exit;
    }
    header('Content-Type: text/plain');
    echo $enc;
    exit;
}

http_response_code(404);
header('Content-Type: text/plain');
echo 'Not Found';
