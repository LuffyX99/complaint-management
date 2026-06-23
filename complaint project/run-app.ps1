$ErrorActionPreference = "Stop"

$localMaven = "C:\tmp\apache-maven-3.9.16\bin\mvn.cmd"
$maven = if (Test-Path $localMaven) { $localMaven } else { "mvn" }

$env:JAVA_HOME = ""
& $maven spring-boot:run
