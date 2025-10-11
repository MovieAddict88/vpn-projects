PHP Admin Panel for ApiGen-compatible Encrypted Config

- AESCrypt-compatible encryption (AES-256-CBC, IV=all zeroes)
- Key derivation: SHA-256 over uppercase hex string of the UTF-8 password
- Custom printable encoding: Base64 of ciphertext is remapped using alphabet: @#$_&-+()/*"!:;?
- Endpoints:
  - GET /           : Minimal admin UI
  - POST /save-meta : Update Version/ReleaseNotes/ReleaseNotes1
  - POST /add-server
  - POST /add-network
  - GET /config.json: Plain JSON (unencrypted)
  - GET /Updater?password=... : Encrypted payload consumed by the Android vpn app

Run with PHP dev server:

```bash
php -S 0.0.0.0:8080 -t public
```

Config JSON schema matches the Android ApiGen output:

```json
{
  "Version": "1.0.0",
  "ReleaseNotes": "",
  "ReleaseNotes1": "",
  "Servers": [
    {
      "Name": "",
      "FLAG": "",
      "ServerIP": "",
      "ServerPort": "",
      "SSLPort": "",
      "ProxyPort": "",
      "ServerUser": "",
      "ServerPass": "",
      "Sfrep": "",
      "sInfo": "",
      "Slowchave": "",
      "Nameserver": "",
      "servermessage": ""
    }
  ],
  "Networks": [
    {
      "Name": "",
      "Payload": "",
      "SNI": "",
      "pInfo": "",
      "Slowdns": "",
      "WebProxy": "",
      "WebPort": "",
      "isSSL": false,
      "isPayloadSSL": false,
      "isSlow": false,
      "isHatok": false,
      "isInject": false,
      "isDirect": false,
      "isWeb": false
    }
  ]
}
```
