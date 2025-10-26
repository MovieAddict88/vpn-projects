<?php
// Simple installer for the admin panel

// Database credentials
define('DB_HOST', 'localhost');
define('DB_NAME', 'vpn_panel');
define('DB_USER', 'root');
define('DB_PASS', '');

// Create connection
$conn = new mysqli(DB_HOST, DB_USER, DB_PASS);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Create database
$sql = "CREATE DATABASE IF NOT EXISTS " . DB_NAME;
if ($conn->query($sql) === TRUE) {
    echo "Database created successfully<br>";
} else {
    echo "Error creating database: " . $conn->error . "<br>";
}

// Select the database
$conn->select_db(DB_NAME);

// Read the SQL file
$sql = file_get_contents('install.sql');
if ($sql === false) {
    die("Error reading install.sql");
}

// Execute multi query
if ($conn->multi_query($sql)) {
    echo "Database schema and data imported successfully";
} else {
    echo "Error importing database schema and data: " . $conn->error;
}

$conn->close();
?>
