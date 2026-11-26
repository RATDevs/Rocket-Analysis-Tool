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
- JXMapViewer2 2.8.1 and its Maven dependencies

Third-party license notices are in [`docs/licenses/third-party/`](docs/licenses/third-party/).
SwingX was removed because the map now uses JXMapViewer2 and no remaining code
depends on SwingX.
JXMapViewer2 is licensed under the LGPL-3.0; its source is available at
https://github.com/msteiger/jxmapviewer2. RAT's source and Maven build allow
rebuilding the application with a modified version of the library.

## OpenStreetMap tiles

The map uses the public OpenStreetMap tile service under its
[tile usage policy](https://operations.osmfoundation.org/policies/tiles/).
Tiles are cached in `~/.rocket-analysis-tool/tilecache` and retained until
that directory is deleted. Delete it if updated map tiles are needed. The
service is intended for light use; heavy use requires another tile provider.

## Archived reference material

[`docs/archive/matlab-reference/`](docs/archive/matlab-reference/) contains legacy MATLAB scripts. They are not part of the active Java application or its build.
