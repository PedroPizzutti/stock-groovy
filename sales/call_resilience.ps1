$Url = "http://localhost:9005/api/sale"

$Body = @"
{
    "buyerEmail": "cliente@email.com",
    "items": [
        {
            "product": {
                "id": "144273d7-0db4-4f9d-a850-d9aef3b58049",
                "codBar": "7891000000011",
                "description": "CAFÉ TORRADO E MOÍDO, EMBALAGEM DE 500G",
                "name": "CAFÉ PREMIUM 500G",
                "price": 18.9
            },
            "quantity": 3
        }
    ]
}
"@

$Requests = 20
$jobs = @()

for ($i = 1; $i -le $Requests; $i++) {
    $jobs += Start-Job -ScriptBlock {
        param($url, $body, $idx)
        try {
            $response = Invoke-RestMethod -Uri $url -Method Post -Body $body -ContentType "application/json"
            Write-Host "[$idx] Sucesso:" ($response | ConvertTo-Json -Compress)
        } catch {
            Write-Host "[$idx] Erro / Fallback:" $_.Exception.Message
        }
    } -ArgumentList $Url, $Body, $i
}

# Esperar todos terminarem
$jobs | ForEach-Object { Wait-Job $_ }

# Coletar resultados
$jobs | ForEach-Object { Receive-Job $_ }

# Limpar jobs
$jobs | ForEach-Object { Remove-Job $_ }
