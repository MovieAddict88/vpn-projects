<?php

class AESCrypt
{
    private const AES_MODE = 'aes-256-cbc';
    private const HASH_ALGORITHM = 'sha256';
    private const JA_TEST = 'QCMkXyYtKygpLyoiITo7Pw==';

    private static function getJaAlphabet(): string
    {
        return base64_decode(self::JA_TEST);
    }

    private static function jakey(string $str): string
    {
        return bin2hex($str);
    }

    private static function generateKey(string $password): string
    {
        return hash(self::HASH_ALGORITHM, $password, true);
    }

    private static function jacodes(string $str): string
    {
        $alphabet = self::getJaAlphabet();
        $bytes = str_split($str);
        $result = '';
        foreach ($bytes as $byte) {
            $val = ord($byte);
            $result .= $alphabet[($val & 0xF0) >> 4];
            $result .= $alphabet[$val & 0x0F];
        }
        return $result;
    }

    public static function encrypt(string $password, string $message): string
    {
        $jaPassword = self::jakey($password);
        $key = self::generateKey($jaPassword);

        $iv = openssl_random_pseudo_bytes(openssl_cipher_iv_length(self::AES_MODE));

        $encrypted = openssl_encrypt($message, self::AES_MODE, $key, OPENSSL_RAW_DATA, $iv);

        $encoded = base64_encode($iv . $encrypted);

        return self::jacodes($encoded);
    }

    private static function genString(string $str): string
    {
        $alphabet = self::getJaAlphabet();
        $chars = str_split($str);
        $len = count($chars) / 2;
        $result = '';

        for ($i = 0; $i < $len; $i++) {
            $pos1 = strpos($alphabet, $chars[$i * 2]);
            $pos2 = strpos($alphabet, $chars[$i * 2 + 1]);
            $result .= chr(($pos1 * 16) + $pos2);
        }

        return $result;
    }

    public static function decrypt(string $password, string $encryptedStr): string
    {
        $base64Encoded = self::genString($encryptedStr);

        $jaPassword = self::jakey($password);
        $key = self::generateKey($jaPassword);

        $decoded = base64_decode($base64Encoded);
        $iv = substr($decoded, 0, openssl_cipher_iv_length(self::AES_MODE));
        $ciphertext = substr($decoded, openssl_cipher_iv_length(self::AES_MODE));

        $decrypted = openssl_decrypt($ciphertext, self::AES_MODE, $key, OPENSSL_RAW_DATA, $iv);

        return $decrypted;
    }
}
