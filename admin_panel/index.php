<?php
include 'config.php';

// Handle form submissions for settings
if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['update_settings'])) {
    $version = $_POST['version'];
    $release_notes = $_POST['release_notes'];
    $release_notes1 = $_POST['release_notes1'];

    $sql = "UPDATE settings SET Version = ?, ReleaseNotes = ?, ReleaseNotes1 = ? WHERE id = 1";
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("sss", $version, $release_notes, $release_notes1);
    $stmt->execute();
}

// Handle form submissions for servers
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($_POST['add_server'])) {
        // Add server logic
        $sql = "INSERT INTO servers (Name, FLAG, ServerIP, ServerPort, SSLPort, ProxyPort, ServerUser, ServerPass, Sfrep, sInfo, Slowchave, Nameserver, servermessage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("sssssssssssss", $_POST['Name'], $_POST['FLAG'], $_POST['ServerIP'], $_POST['ServerPort'], $_POST['SSLPort'], $_POST['ProxyPort'], $_POST['ServerUser'], $_POST['ServerPass'], $_POST['Sfrep'], $_POST['sInfo'], $_POST['Slowchave'], $_POST['Nameserver'], $_POST['servermessage']);
        $stmt->execute();
    } elseif (isset($_POST['edit_server'])) {
        // Edit server logic
        $sql = "UPDATE servers SET Name = ?, FLAG = ?, ServerIP = ?, ServerPort = ?, SSLPort = ?, ProxyPort = ?, ServerUser = ?, ServerPass = ?, Sfrep = ?, sInfo = ?, Slowchave = ?, Nameserver = ?, servermessage = ? WHERE id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("sssssssssssssi", $_POST['Name'], $_POST['FLAG'], $_POST['ServerIP'], $_POST['ServerPort'], $_POST['SSLPort'], $_POST['ProxyPort'], $_POST['ServerUser'], $_POST['ServerPass'], $_POST['Sfrep'], $_POST['sInfo'], $_POST['Slowchave'], $_POST['Nameserver'], $_POST['servermessage'], $_POST['id']);
        $stmt->execute();
    } elseif (isset($_POST['delete_server'])) {
        // Delete server logic
        $sql = "DELETE FROM servers WHERE id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $_POST['id']);
        $stmt->execute();
    }
}

// Handle form submissions for payloads
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (isset($_POST['add_payload'])) {
        // Add payload logic
        $sql = "INSERT INTO payloads (Name, Payload, SNI, pInfo, Slowdns, WebProxy, WebPort, isSSL, isPayloadSSL, isSlow, isHatok, isInject, isDirect, isWeb) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        $stmt = $conn->prepare($sql);
        $isSSL = isset($_POST['isSSL']) ? 1 : 0;
        $isPayloadSSL = isset($_POST['isPayloadSSL']) ? 1 : 0;
        $isSlow = isset($_POST['isSlow']) ? 1 : 0;
        $isHatok = isset($_POST['isHatok']) ? 1 : 0;
        $isInject = isset($_POST['isInject']) ? 1 : 0;
        $isDirect = isset($_POST['isDirect']) ? 1 : 0;
        $isWeb = isset($_POST['isWeb']) ? 1 : 0;
        $stmt->bind_param("sssssssiisiiii", $_POST['Name'], $_POST['Payload'], $_POST['SNI'], $_POST['pInfo'], $_POST['Slowdns'], $_POST['WebProxy'], $_POST['WebPort'], $isSSL, $isPayloadSSL, $isSlow, $isHatok, $isInject, $isDirect, $isWeb);
        $stmt->execute();
    } elseif (isset($_POST['edit_payload'])) {
        // Edit payload logic
        $sql = "UPDATE payloads SET Name = ?, Payload = ?, SNI = ?, pInfo = ?, Slowdns = ?, WebProxy = ?, WebPort = ?, isSSL = ?, isPayloadSSL = ?, isSlow = ?, isHatok = ?, isInject = ?, isDirect = ?, isWeb = ? WHERE id = ?";
        $stmt = $conn->prepare($sql);
        $isSSL = isset($_POST['isSSL']) ? 1 : 0;
        $isPayloadSSL = isset($_POST['isPayloadSSL']) ? 1 : 0;
        $isSlow = isset($_POST['isSlow']) ? 1 : 0;
        $isHatok = isset($_POST['isHatok']) ? 1 : 0;
        $isInject = isset($_POST['isInject']) ? 1 : 0;
        $isDirect = isset($_POST['isDirect']) ? 1 : 0;
        $isWeb = isset($_POST['isWeb']) ? 1 : 0;
        $stmt->bind_param("sssssssiisiiiis", $_POST['Name'], $_POST['Payload'], $_POST['SNI'], $_POST['pInfo'], $_POST['Slowdns'], $_POST['WebProxy'], $_POST['WebPort'], $isSSL, $isPayloadSSL, $isSlow, $isHatok, $isInject, $isDirect, $isWeb, $_POST['id']);
        $stmt->execute();
    } elseif (isset($_POST['delete_payload'])) {
        // Delete payload logic
        $sql = "DELETE FROM payloads WHERE id = ?";
        $stmt = $conn->prepare($sql);
        $stmt->bind_param("i", $_POST['id']);
        $stmt->execute();
    }
}

// Fetch data for display
$settings_result = $conn->query("SELECT * FROM settings WHERE id = 1");
$settings = $settings_result->fetch_assoc();

$servers_result = $conn->query("SELECT * FROM servers");
$payloads_result = $conn->query("SELECT * FROM payloads");
?>
<!DOCTYPE html>
<html>
<head>
    <title>VPN Admin Panel</title>
    <style>
        body { font-family: sans-serif; }
        .container { width: 80%; margin: 0 auto; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        form { margin-bottom: 20px; }
        input[type=text], textarea { width: 100%; padding: 12px 20px; margin: 8px 0; display: inline-block; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        input[type=submit] { width: 100%; background-color: #4CAF50; color: white; padding: 14px 20px; margin: 8px 0; border: none; border-radius: 4px; cursor: pointer; }
        input[type=submit]:hover { background-color: #45a049; }
    </style>
</head>
<body>

<div class="container">
    <h1>VPN Admin Panel</h1>

    <h2>Settings</h2>
    <form method="post">
        <label for="version">Version</label>
        <input type="text" id="version" name="version" value="<?php echo $settings['Version']; ?>">

        <label for="release_notes">Release Notes</label>
        <textarea id="release_notes" name="release_notes"><?php echo $settings['ReleaseNotes']; ?></textarea>

        <label for="release_notes1">Release Notes 1</label>
        <textarea id="release_notes1" name="release_notes1"><?php echo $settings['ReleaseNotes1']; ?></textarea>

        <input type="submit" name="update_settings" value="Update Settings">
    </form>

    <h2>Servers</h2>
    <table>
        <tr>
            <th>Name</th>
            <th>Flag</th>
            <th>Server IP</th>
            <th>Actions</th>
        </tr>
        <?php while ($row = $servers_result->fetch_assoc()): ?>
            <tr>
                <td><?php echo $row['Name']; ?></td>
                <td><?php echo $row['FLAG']; ?></td>
                <td><?php echo $row['ServerIP']; ?></td>
                <td>
                    <form method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<?php echo $row['id']; ?>">
                        <input type="submit" name="delete_server" value="Delete" style="background-color: #f44336;">
                    </form>
                </td>
            </tr>
        <?php endwhile; ?>
    </table>

    <h3>Add Server</h3>
    <form method="post">
        <input type="text" name="Name" placeholder="Name" required>
        <input type="text" name="FLAG" placeholder="FLAG" required>
        <input type="text" name="ServerIP" placeholder="ServerIP" required>
        <input type="text" name="ServerPort" placeholder="ServerPort" required>
        <input type="text" name="SSLPort" placeholder="SSLPort" required>
        <input type="text" name="ProxyPort" placeholder="ProxyPort" required>
        <input type="text" name="ServerUser" placeholder="ServerUser" required>
        <input type="text" name="ServerPass" placeholder="ServerPass" required>
        <input type="text" name="Sfrep" placeholder="Sfrep" required>
        <input type="text" name="sInfo" placeholder="sInfo" required>
        <input type="text" name="Slowchave" placeholder="Slowchave" required>
        <input type="text" name="Nameserver" placeholder="Nameserver" required>
        <textarea name="servermessage" placeholder="servermessage" required></textarea>
        <input type="submit" name="add_server" value="Add Server">
    </form>

    <h2>Payloads</h2>
    <table>
        <tr>
            <th>Name</th>
            <th>Payload</th>
            <th>SNI</th>
            <th>Actions</th>
        </tr>
        <?php while ($row = $payloads_result->fetch_assoc()): ?>
            <tr>
                <td><?php echo $row['Name']; ?></td>
                <td><?php echo $row['Payload']; ?></td>
                <td><?php echo $row['SNI']; ?></td>
                <td>
                    <form method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<?php echo $row['id']; ?>">
                        <input type="submit" name="delete_payload" value="Delete" style="background-color: #f44336;">
                    </form>
                </td>
            </tr>
        <?php endwhile; ?>
    </table>

    <h3>Add Payload</h3>
    <form method="post">
        <input type="text" name="Name" placeholder="Name" required>
        <textarea name="Payload" placeholder="Payload" required></textarea>
        <input type="text" name="SNI" placeholder="SNI">
        <input type="text" name="pInfo" placeholder="pInfo">
        <input type="text" name="Slowdns" placeholder="Slowdns">
        <input type="text" name="WebProxy" placeholder="WebProxy">
        <input type="text" name="WebPort" placeholder="WebPort">
        <label><input type="checkbox" name="isSSL" value="1"> isSSL</label>
        <label><input type="checkbox" name="isPayloadSSL" value="1"> isPayloadSSL</label>
        <label><input type="checkbox" name="isSlow" value="1"> isSlow</label>
        <label><input type="checkbox" name="isHatok" value="1"> isHatok</label>
        <label><input type="checkbox" name="isInject" value="1"> isInject</label>
        <label><input type="checkbox" name="isDirect" value="1"> isDirect</label>
        <label><input type="checkbox" name="isWeb" value="1"> isWeb</label>
        <input type="submit" name="add_payload" value="Add Payload">
    </form>

</div>

</body>
</html>