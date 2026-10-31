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

Build from the repository root with Maven:

```sh
mvn clean verify
```

Run the calculation smoke test after building:

```sh
java -cp target/rocket-analysis-tool.jar rat.main.CalculationTest
```

Launch the application from the repository root:

```sh
java -jar target/rocket-analysis-tool.jar
```

The executable JAR includes the language bundles and bundled runtime libraries. Run it with the repository root as the working directory so that mutable files continue to use `config/`, `Rockets/`, and `LastRun/`.

## Bundled libraries

- `Libs/jmathplotOWN.jar`
- `Libs/swingx-all-1.6.4.jar`
- `Libs/swingx-ws-2009_06_14.jar`

Third-party license notices are in [`docs/licenses/third-party/`](docs/licenses/third-party/).

## Compatibility note

`jdeps` on JDK 21 reports removed `sun.misc` BASE64 APIs inside `swingx-ws-2009_06_14.jar`. RAT still uses JXMapKit from that library for the map UI. The Java 21 validation therefore includes a calculation smoke test and a short Swing UI startup smoke test. The map dependency should be refreshed in a follow-up change.

## Archived reference material

[`docs/archive/matlab-reference/`](docs/archive/matlab-reference/) contains legacy MATLAB scripts. They are not part of the active Java application or its build.
