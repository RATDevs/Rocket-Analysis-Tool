***************************************************************
*       Rocket Analysis Tool - RAT BETA RELEASE - README      *
*                       Version 0.9.2                         *
***************************************************************

This PROGRAMM VERSION IS FREE AND OPEN SOURCE!
Published with Apache License Version 2.0

Find the newest releases:
https://github.com/RATDevs/Rocket-Analysis-Tool/releases

Code hosted:
https://github.com/RATDevs/Rocket-Analysis-Tool/

Requirements:
- JDK 21 (LTS)
- Connection to the internet when using open street maps (grant access in your firewall)

Developer setup:
- Set JAVA_HOME to a JDK 21 installation before compiling or running RAT.
- Example (Linux/macOS): export JAVA_HOME=/path/to/jdk-21
- Example (Windows PowerShell): $env:JAVA_HOME="C:\Program Files\Java\jdk-21"

Build from the RAT directory (Linux/macOS):
- javac --release 21 -cp "Libs/*" -d bin $(find src -name '*.java' | sort)

Run the calculation smoke test from the RAT directory (Linux/macOS):
- java -cp "bin:Libs/*" rat.main.CalculationTest

Run the Swing UI from the RAT directory (Linux/macOS):
- java -cp "bin:Libs/*:lang" rat.main.RATmain

Windows PowerShell equivalents from the RAT directory:
- javac --release 21 -cp "Libs/*" -d bin (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })
- java -cp "bin;Libs/*" rat.main.CalculationTest
- java -cp "bin;Libs/*;lang" rat.main.RATmain

Bundled library inventory checked for the Java 21 upgrade:
- Libs/jdom-2.0.5.jar
- Libs/jmathplotOWN.jar
- Libs/swingx-all-1.6.4.jar
- Libs/swingx-ws-2009_06_14.jar

Compatibility note:
- jdeps on JDK 21 reports removed sun.misc BASE64 APIs inside Libs/swingx-ws-2009_06_14.jar. RAT still uses JXMapKit from that library for the map UI, so the Java 21 validation now includes both a calculation smoke test and a short Swing UI startup smoke test. The dependency should still be refreshed in a follow-up change.
