<?php

namespace App;

class Store
{
    private string $file;

    public function __construct(string $file)
    {
        $this->file = $file;
        if (!file_exists($this->file)) {
            $this->save([
                'Version' => '1.0.0',
                'ReleaseNotes' => '',
                'ReleaseNotes1' => '',
                'Servers' => [],
                'Networks' => [],
            ]);
        }
    }

    public function load(): array
    {
        $json = @file_get_contents($this->file);
        if ($json === false || $json === '') {
            return [
                'Version' => '1.0.0',
                'ReleaseNotes' => '',
                'ReleaseNotes1' => '',
                'Servers' => [],
                'Networks' => [],
            ];
        }
        $data = json_decode($json, true);
        if (!is_array($data)) {
            $data = [];
        }
        $data += [
            'Version' => '1.0.0',
            'ReleaseNotes' => '',
            'ReleaseNotes1' => '',
            'Servers' => [],
            'Networks' => [],
        ];
        return $data;
    }

    public function save(array $data): void
    {
        $json = json_encode($data, JSON_UNESCAPED_SLASHES);
        file_put_contents($this->file, $json);
    }
}
