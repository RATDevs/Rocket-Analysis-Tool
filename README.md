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

### JMathPlot

The plotting JAR is derived from [JMathPlot](https://github.com/yannrichet/jmathplot)
by Yann Richet. The repository's existing [BSD 3-clause notice](docs/licenses/third-party/jmathplot/LICENSE.txt)
attributes it to Yann Richet, copyright 2003. The binary contains no license,
source, or version metadata. It was already present in RAT's
[initial 2013 commit](https://github.com/RATDevs/Rocket-Analysis-Tool/commit/ba3da45c9ec16532b6c2c6b4772c4c3bf7356bf9);
its exact upstream revision, build process, and any RAT-specific source changes
are not known. The `1.0` in the Maven system dependency is a local placeholder,
not evidence of an upstream version.

Comparison with the Maven Central artifact
[`com.github.yannrichet:JMathPlot:1.0.1`](https://central.sonatype.com/artifact/com.github.yannrichet/JMathPlot/1.0.1)
([upstream tag](https://github.com/yannrichet/jmathplot/tree/1.0.1)):

- SHA-256: bundled JAR `f454855b793195c7591b5f73961f4ff966d0925c9f9db5268a18711bfbac9a5b`;
  upstream artifact `1e6a2e36554f6c03d48406fb5a1a5fe5d6016ad27b3a77e057ddd6f1eb6fd000`.
- Comparing ZIP class entries gives 113 bundled and 110 upstream classes:
  93 names are shared, all with differing bytes; 20 are bundled-only and 17
  upstream-only. The bundled JAR includes `org.math.io` classes, whereas
  the upstream Maven release depends on separate JMathIO and JMathArray
  artifacts. It has `SetScalesFrame`; upstream has `ScalesFrame`, `DataFrame`,
  and extra plotting utilities.
- `javap` shows the same public `Plot2DPanel` signatures, but upstream adds
  bounds-adjustment APIs to `PlotPanel`/`PlotCanvas` and changes the canvas
  data and scales frame APIs. The bundled `PlotPanel` targets Java 7 class
  files; upstream targets Java 6. Compiler differences alone can change
  class bytes, so this comparison does **not** establish which differences
  are local modifications or enumerate their behavioral effects.

To reproduce the comparison, obtain the upstream artifact with
`mvn dependency:get -Dartifact=com.github.yannrichet:JMathPlot:1.0.1`,
compare the JARs with `sha256sum` and `jar tf`, and inspect class APIs with
`javap -classpath <jar> org.math.plot.PlotPanel` (and similarly for
`Plot2DPanel` and `org.math.plot.canvas.PlotCanvas`).

**Decision:** retain the bundled JAR unchanged for now. Replacing it with
the inactive upstream release would not resolve the unknown patch history,
and switching to a maintained library such as JFreeChart would require
revalidating interactions, legends, colors, scaling, and export controls.
Both graph tabs therefore continue using the same binary and behavior:
the main graph supports multiple plots and interactive controls, while the
summary graphs suppress canvas interaction and open a graph on click.
The system-scoped dependency and unmaintained, unreproducible binary remain
risks. Before replacing it, recover the original source/patches if possible
and compare these graph workflows and packaged output against the replacement.

## OpenStreetMap tiles

The map uses the public OpenStreetMap tile service under its
[tile usage policy](https://operations.osmfoundation.org/policies/tiles/).
Tiles are cached in `~/.rocket-analysis-tool/tilecache` and retained until
that directory is deleted. Delete it if updated map tiles are needed. The
service is intended for light use; heavy use requires another tile provider.

## Archived reference material

[`docs/archive/matlab-reference/`](docs/archive/matlab-reference/) contains legacy MATLAB scripts. They are not part of the active Java application or its build.
