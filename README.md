# Rocket Analysis Tool (RAT)

**Version 0.9.2 - Beta**

RAT is free and open-source software published under the [Apache License 2.0](LICENSE.txt).

- [Latest releases](https://github.com/RATDevs/Rocket-Analysis-Tool/releases)
- [Source repository](https://github.com/RATDevs/Rocket-Analysis-Tool/)

## Requirements

- JDK 21 (LTS)
- Internet access when using OpenStreetMap

## Developer setup

Set `JAVA_HOME` to a JDK 21 installation before compiling or running RAT.

Linux/macOS:

```sh
export JAVA_HOME=/path/to/jdk-21
```

Windows PowerShell:

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-21"
```

## Build and run

The source tree follows the standard Maven layout. Until the Maven build is added, compile from the repository root.

Linux/macOS:

```sh
mkdir -p target/classes
javac --release 21 -cp "Libs/*" -d target/classes $(find src/main/java -name '*.java' | sort)
```

Run the calculation smoke test:

```sh
java -cp "target/classes:Libs/*:src/main/resources" rat.main.CalculationTest
```

Run the Swing UI:

```sh
java -cp "target/classes:Libs/*:src/main/resources" rat.main.RATmain
```

Windows PowerShell equivalents:

```powershell
New-Item -ItemType Directory -Force target/classes
javac --release 21 -cp "Libs/*" -d target/classes (Get-ChildItem -Recurse src/main/java -Filter *.java | ForEach-Object { $_.FullName })
java -cp "target/classes;Libs/*;src/main/resources" rat.main.CalculationTest
java -cp "target/classes;Libs/*;src/main/resources" rat.main.RATmain
```

## Bundled libraries

- `Libs/jdom-2.0.5.jar`
- `Libs/jmathplotOWN.jar`
- `Libs/swingx-all-1.6.4.jar`
- `Libs/swingx-ws-2009_06_14.jar`

Third-party license notices are in [`docs/licenses/third-party/`](docs/licenses/third-party/).

## Compatibility note

`jdeps` on JDK 21 reports removed `sun.misc` BASE64 APIs inside `swingx-ws-2009_06_14.jar`. RAT still uses JXMapKit from that library for the map UI. The Java 21 validation therefore includes a calculation smoke test and a short Swing UI startup smoke test. The map dependency should be refreshed in a follow-up change.

## Archived reference material

[`docs/archive/matlab-reference/`](docs/archive/matlab-reference/) contains legacy MATLAB scripts. They are not part of the active Java application or its build.
