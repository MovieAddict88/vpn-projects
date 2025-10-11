<?php

namespace App;

class AESCrypt
{
    private const AES_MODE = 'AES-256-CBC';
    private const CHARSET = 'UTF-8';
    private const HASH_ALGORITHM = 'sha256';

    // 16 null bytes IV
    private const IV = "\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00";

    // Custom alphabet decoded from Base64("QCMkXyYtKygpLyoiITo7Pw==") => @#$_&-+()/*"!:;?
    private const CUSTOM_ALPHABET = "@#$_&-+()/*\"!:;?";

    private static function generateKey(string $password): string
    {
        // Jakey(password) -> hex uppercase of password bytes
        $hex = self::toHexUpper($password);
        // SHA-256 of that hex string (as bytes of UTF-8 string)
        return hash(self::HASH_ALGORITHM, $hex, true);
    }

    private static function toHexUpper(string $s): string
    {
        $hex = strtoupper(bin2hex($s));
        return $hex;
    }

    private static function jacodes(string $s): string
    {
        // Map each nybble of bytes of $s (UTF-8 bytes) into CUSTOM_ALPHABET
        $alphabet = self::CUSTOM_ALPHABET;
        $bytes = unpack('C*', $s);
        $out = '';
        foreach ($bytes as $b) {
            $out .= $alphabet[($b & 0xF0) >> 4];
            $out .= $alphabet[$b & 0x0F];
        }
        return $out;
    }

    private static function genString(string $encoded): string
    {
        // Reverse of jacodes(): pairs of chars -> byte using CUSTOM_ALPHABET indices
        $alphabet = self::CUSTOM_ALPHABET;
        $len = strlen($encoded) / 2;
        $out = '';
        for ($i = 0; $i < $len; $i++) {
            $c1 = $encoded[$i * 2];
            $c2 = $encoded[$i * 2 + 1];
            $n1 = strpos($alphabet, $c1);
            $n2 = strpos($alphabet, $c2);
            if ($n1 === false || $n2 === false) {
                throw new \RuntimeException('Invalid character in encoded string');
            }
            $out .= chr((($n1 * 16) + $n2) & 0xFF);
        }
        return $out;
    }

    public static function encrypt(string $password, string $message): string
    {
        $key = self::generateKey($password);
        $cipherText = openssl_encrypt($message, self::AES_MODE, $key, OPENSSL_RAW_DATA, self::IV);
        if ($cipherText === false) {
            throw new \RuntimeException('OpenSSL encrypt failed');
        }
        $b64 = base64_encode($cipherText);
        return self::jacodes($b64);
    }

    public static function decrypt(string $password, string $encoded): string
    {
        $b64 = self::genString($encoded);
        $cipher = base64_decode($b64, true);
        if ($cipher === false) {
            throw new \RuntimeException('Base64 decode failed');
        }
        $key = self::generateKey($password);
        $plain = openssl_decrypt($cipher, self::AES_MODE, $key, OPENSSL_RAW_DATA, self::IV);
        if ($plain === false) {
            throw new \RuntimeException('OpenSSL decrypt failed');
        }
        return $plain;
    }
}
