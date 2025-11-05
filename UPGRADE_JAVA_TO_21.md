Upgrade Java runtime to Java 21 (LTS)

This repository appears to be a plain Java project (no Maven/Gradle files detected).
This document lists manual steps to upgrade your Windows development environment to Java 21 and includes helper PowerShell scripts added to the `scripts/` folder that can verify your installation and compile/run the project using the chosen JDK.

1) Verify current Java

Open PowerShell and run:

```powershell
java -version
javac -version
```

You should see the current Java runtime and compiler versions. If the major version is 21, you're already on JDK 21 and no further OS changes are required.

2) Install JDK 21 (recommended options)

Option A — (recommended) Use winget (Windows package manager)
Replace the package id if needed; the examples below are common package ids but may differ on your system or winget source:

```powershell
# Try Microsoft build of OpenJDK 21
winget install --exact --id Microsoft.OpenJDK.21 -e

# Or install Eclipse Temurin (Adoptium) JDK 21 (package id may vary)
winget install --exact --id EclipseAdoptium.Temurin.21.JDK -e
```

If `winget` doesn't find the package, download an installer from a vendor:
- Eclipse Adoptium (Temurin) — https://adoptium.net/
- Oracle JDK — https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html
- Microsoft OpenJDK — https://learn.microsoft.com/en-us/java/openjdk/

3) Set JAVA_HOME and update PATH

After installation note the installation path (for example: `C:\Program Files\Microsoft\OpenJDK\jdk-21` or `C:\Program Files\Eclipse Adoptium\jdk-21`).

To set JAVA_HOME for the current session:

```powershell
$env:JAVA_HOME = 'C:\Program Files\YourJdkPath\jdk-21'
$env:Path = "$($env:JAVA_HOME)\bin;" + $env:Path
```

To set JAVA_HOME permanently (for your user account):

```powershell
setx JAVA_HOME "C:\Program Files\YourJdkPath\jdk-21"
# Then add java bin to user PATH (replace the path if necessary)
# Note: setx changes will take effect in new shells
setx PATH "%JAVA_HOME%\bin;%PATH%"
```

4) Verify installation

Open a new PowerShell window and run:

```powershell
java -version
javac -version
```

You should see version `21` in output.

5) Compile & run the project with JDK 21

This repo doesn't contain a build tool, so the helper script `scripts/compile-run.ps1` can compile the Java files under `src/` and run `Main` using the `JAVA_HOME` in your environment.

Usage (PowerShell):

```powershell
# From repo root
./scripts/compile-run.ps1
```

6) IDE configuration

- IntelliJ: File -> Project Structure -> Project SDK -> Add JDK -> point to the JDK 21 installation.
- VS Code: Install Extension Pack for Java, then set `java.configuration.runtimes` in `settings.json` or update `JAVA_HOME`.

7) Compatibility checks and gotchas

- If you later add modules (`module-info.java`) ensure the module system is compatible.
- Any removed/renamed internal APIs may cause compile errors; run a full compile (`scripts/compile-run.ps1`) and fix compiler errors.
- If you use external libs or JDBC drivers, verify they work on Java 21.

8) Next steps (optional automated upgrade tool)

I tried to use the repository's automated upgrade plan tool but the environment doesn't permit it (Copilot App Modernization tools require a Pro plan). If you'd like, I can attempt an in-repo upgrade plan locally once you have JDK 21 installed; otherwise follow the manual steps above.

Files added:
- `scripts/check-java21.ps1` — quick check script that prints java version and suggestions
- `scripts/compile-run.ps1` — compiles all `.java` files under `src/` and runs `Main` using `JAVA_HOME` if set

If you want, I can also add a basic Maven or Gradle wrapper to the project to make future upgrades and builds easier. Let me know which build tool you prefer.
