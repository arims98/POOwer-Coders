# Check Java version and advise about Java 21
Write-Host "Checking java and javac versions..."
try {
    $javaVersion = & java -version 2>&1
    Write-Host $javaVersion
} catch {
    Write-Host "java not found in PATH"
}
try {
    $javacVersion = & javac -version 2>&1
    Write-Host $javacVersion
} catch {
    Write-Host "javac not found in PATH"
}

# Simple heuristic
if ($javaVersion -match 'version "21') {
    Write-Host "Java 21 detected. You're good to go."
} else {
    Write-Host "Java 21 not detected. Follow UPGRADE_JAVA_TO_21.md to install/configure JDK 21." -ForegroundColor Yellow
}
