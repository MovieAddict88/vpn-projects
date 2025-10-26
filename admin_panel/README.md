# VPN Admin Panel

This is a simple PHP/MySQL admin panel for managing VPN configurations.

## Installation

1.  Create a MySQL database.
2.  Edit `config.php` with your database credentials.
3.  Upload the contents of this directory to your web server.
4.  Navigate to `install.php` in your web browser to set up the database.
5.  Log in with the default credentials:
    *   **Username:** admin
    *   **Password:** password
6.  **IMPORTANT:** Change the default admin password immediately.

## Security Warning

The API password is hardcoded in `vpn/src/main/java/com/slipkprojects/ultrasshservice/util/securepreferences/model/vagol.java`. For a production environment, you should implement a more secure method of storing and accessing this password.
