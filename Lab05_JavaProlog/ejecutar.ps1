param([switch]$Compilar)
$ErrorActionPreference = 'Stop'
$javaFolder = Join-Path $PSScriptRoot 'JAVA'
$lib = Join-Path $javaFolder 'lib'
$classes = Join-Path $javaFolder 'target/classes'
New-Item -ItemType Directory -Path $lib, $classes -Force | Out-Null
$dependencias = @(
    'it/unibo/alice/tuprolog/tuprolog/3.3.0/tuprolog-3.3.0.jar',
    'org/apache/commons/commons-lang3/3.18.0/commons-lang3-3.18.0.jar',
    'com/google/code/gson/gson/2.13.2/gson-2.13.2.jar'
)
foreach ($dependencia in $dependencias) {
    $archivo = Join-Path $lib ($dependencia.Split('/')[-1])
    if (!(Test-Path -LiteralPath $archivo)) {
        Invoke-WebRequest -Uri "https://repo.maven.apache.org/maven2/$dependencia" -OutFile $archivo
    }
}
$fuentes = @(Get-ChildItem -LiteralPath (Join-Path $javaFolder 'src/main/java') -Recurse -Filter '*.java' | ForEach-Object { $_.FullName })
& javac -encoding UTF-8 --release 17 -cp "$lib/*" -d $classes @fuentes
if ($LASTEXITCODE -ne 0) { throw 'No se pudo compilar el proyecto.' }
Copy-Item -LiteralPath (Join-Path $PSScriptRoot 'PROLOG/calculo.pl') -Destination $classes -Force
if (!$Compilar) {
    & java -cp "$classes;$lib/*" org.example.Main
    if ($LASTEXITCODE -ne 0) { throw 'No se pudo iniciar el programa.' }
}
