<#
PowerShell build script para el proyecto POOwer-Coders.
Requisitos:
 - Tener JDK 21 instalado y accesible (JAVA_HOME o en PATH).
 - Ejecutar desde la raíz del repositorio (donde está este script).

Uso:
  .\build.ps1         # compila y ejecuta MainApp
  .\build.ps1 -RunTests $false  # solo compilar (no hay harness de tests por defecto)

El script compila todos los .java bajo ./src y los coloca en ./out/classes
Luego ejecuta la clase vista.MainApp (ajustar si el paquete o clase principal cambia).
#>
param(
    [switch]$RunTests = $true
)

Write-Host "=== Build script: POOwer-Coders ==="

# Comprobar javac
$javac = Get-Command javac -ErrorAction SilentlyContinue
if (-not $javac) {
    Write-Host "ERROR: 'javac' no se encuentra en PATH. Asegúrate de tener JDK 21 instalado y 'javac' accesible." -ForegroundColor Red
    Write-Host "Comprueba JAVA_HOME y PATH. Por ejemplo (PowerShell):"
    Write-Host "  $Env:JAVA_HOME = 'C:\Program Files\Java\jdk-21'  # ajustar ruta"
    Write-Host "  $Env:Path = $Env:JAVA_HOME + '\\bin;' + $Env:Path"
    exit 1
}

# Mostrar versión
$versionInfo = & javac -version 2>&1
Write-Host "javac version: $versionInfo"

# Directorio de salida
$outDir = "$PSScriptRoot/out/classes"
if (Test-Path $outDir) { Remove-Item $outDir -Recurse -Force }
New-Item -ItemType Directory -Path $outDir -Force | Out-Null

# Recoger archivos fuente
$srcFiles = Get-ChildItem -Path "$PSScriptRoot/src" -Recurse -Filter *.java | ForEach-Object { $_.FullName }
if (-not $srcFiles) {
    Write-Host "ERROR: No se han encontrado archivos .java en ./src" -ForegroundColor Red
    exit 1
}

# Compilar con --release 21 (asegura compatibilidad de APIs con Java 21)
Write-Host "Compilando ${($srcFiles).Count} archivos Java con --release 21..."
$compileCmd = @("--release", "21", "-d", $outDir) + $srcFiles
$proc = Start-Process -FilePath javac -ArgumentList $compileCmd -NoNewWindow -Wait -PassThru
if ($proc.ExitCode -ne 0) {
    Write-Host "Fallo en la compilación (exit code $($proc.ExitCode)). Revisa los errores arriba." -ForegroundColor Red
    exit $proc.ExitCode
}
Write-Host "Compilación completada. Clases en: $outDir" -ForegroundColor Green

if ($RunTests) {
    Write-Host "Ejecutando aplicación principal vista.MainApp..."
    try {
        & java -cp $outDir vista.MainApp
    } catch {
        Write-Host "ERROR al ejecutar la clase principal: $_" -ForegroundColor Red
        exit 1
    }
} else {
    Write-Host "RunTests está desactivado; solo compilado." -ForegroundColor Yellow
}

Write-Host "=== Fin build script ==="
