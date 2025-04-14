
**Input Data:**
| data_transfer_size | response_time |
|--------------------|---------------|
| "10KB"             | "5ms"         |
| "20MB"             | "2s"          |

**Output:**
| total_size_mb | total_time_sec |
|---------------|----------------|
| 20.009765625  | 2.005          |

## Setup and Commands
Welcome to the Wrangler Enhancements project! This section provides all the commands and steps to set up and run the project on a Windows machine. Whether you're a reviewer or a developer, these instructions will get you up and running smoothly.

### Prerequisites
Before starting, ensure you have the following installed:

1. **Java 8**:
   - Download JDK 8 from [oracle.com](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html) (free account required).
   - Install it (e.g., to `C:\Program Files\Java\jdk1.8.0_281`).
   - Set environment variables:
     ```powershell
     setx JAVA_HOME "C:\Program Files\Java\jdk1.8.0_281"
     setx PATH "%PATH%;%JAVA_HOME%\bin"
     ```
   - Verify installation:
     ```powershell
     java -version
     ```
     **Expected Output**:
     ```
     java version "1.8.0_281"
     Java(TM) SE Runtime Environment (build 1.8.0_281-b09)
     Java HotSpot(TM) 64-Bit Server VM (build 25.281-b09, mixed mode)
     ```

2. **Git**:
   - Download from [git-scm.com](https://git-scm.com/download/win).
   - Install with default options.
   - Verify:
     ```powershell
     git --version
     ```
     **Expected Output**:
     ```
     git version 2.41.0.windows.3
     ```

### Step-by-Step Setup
Follow these steps to clone, compile, and test the project:

1. **Clone the Repository**:
   - Get the project from GitHub:
     ```powershell
     git clone https://github.com/abhishekd17/wrangler.git
     cd wrangler
     ```
   - This creates a `wrangler` folder with all source files (`*.java`, `Directives.g4`, etc.).

2. **Set Up JUnit and Hamcrest Libraries**:
   - Create a `lib/` folder for dependencies:
     ```powershell
     mkdir lib
     ```
   - Download the required JARs:
     - `junit-4.13.2.jar`:
       - URL: [https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar](https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar).
       - Right-click, Save Link As, save to `wrangler\lib\`.
     - `hamcrest-core-1.3.jar`:
       - URL: [https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar](https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar).
       - Save to `wrangler\lib\`.
   - Alternatively, move downloaded files:
     ```powershell
     move path\to\junit-4.13.2.jar lib\
     move path\to\hamcrest-core-1.3.jar lib\
     ```
   - Verify files:
     ```powershell
     dir lib
     ```
     **Expected Output**:
     ```
     dir lib
     dir: lib
     Mode                LastWriteTime         Length Name
     ----                -------------         ------ ----
     -a----       2025-04-15 10:00 AM         343321 junit-4.13.2.jar
     -a----       2025-04-15 10:00 AM          16488 hamcrest-core-1.3.jar
     ```

3. **Compile the Project**:
   - Compile all Java files, including tests:
     ```powershell
     javac -cp "lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" *.java
     ```
   - Check for compiled classes:
     ```powershell
     dir *.class
     ```
   - **Expected Output**: Files like `ByteSize.class`, `AggregateStatsTest.class`, etc.
   - If errors occur, ensure JARs are in `lib/` and Java 8 is used.

4. **Run Unit Tests**:
   - Execute the three test suites:
     ```powershell
     java -cp ".;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore ByteSizeTest
     java -cp ".;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore TimeDurationTest
     java -cp ".;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore AggregateStatsTest
     ```

5. **Verify Test Outputs**:
   - `ByteSizeTest`:
     ```
     JUnit version 4.13.2
     ...
     Time: 0.015
     OK (3 tests)
     ```
     Tests parsing of `10B`, `1.5MB`, invalid inputs.
   - `TimeDurationTest`:
     ```
     JUnit version 4.13.2
     ...
     Time: 0.012
     OK (3 tests)
     ```
     Tests parsing of `5ms`, `2.1s`, invalid inputs.
   - `AggregateStatsTest`:
     ```
     JUnit version 4.13.2
     .Total MB: 20.0098
     Total Sec: 2.005
     Time: 0.02
     OK (2 tests)
     ```
     Tests aggregation of `10KB + 20MB`, `5ms + 2s`, and edge cases.

### Running the Directive
- The project is a library enhancement, so it’s tested via JUnit.
- To use in a larger Wrangler setup:
  - Integrate `ByteSize.java`, `TimeDuration.java`, `AggregateStats.java` into your project.
  - Use the syntax:
    ```
    aggregate-stats :data_transfer_size :response_time total_size_mb total_time_sec
    ```
  - See `AggregateStatsTest.java` for example usage.

### Troubleshooting
- **Error: `java: command not found`**:
  - Check `JAVA_HOME` and `PATH`:
    ```powershell
    echo %JAVA_HOME%
    echo %PATH%
    ```
  - Reinstall JDK 8 if needed.
- **Error: `ClassNotFoundException`**:
  - Verify `lib/` contains `junit-4.13.2.jar`, `hamcrest-core-1.3.jar`.
  - Re-run compile command.
- **Compilation Errors**:
  - Ensure Java 8 (not newer) is used:
    ```powershell
    java -version
    ```
- **Test Failures**:
  - Check `*.java` files are unmodified.
  - Recompile and retry tests.
- **Git Clone Fails**:
  - Ensure internet connection and Git installed:
    ```powershell
    git --version
    ```

## Notes
- **Structure**: Built in a flat directory (`D:\zeotap`) to avoid package issues.
- **Tests**: Cover valid inputs (`10KB`, `5ms`), edge cases (`0B`, empty strings), and errors (`10XX`).
- **Grammar**: `Directives.g4` defines `BYTE_SIZE` and `TIME_DURATION` tokens.
- **Research**: Googled ANTLR grammar syntax and JUnit setup for accuracy.
- **Files**:
  - Source: `ByteSize.java`, `TimeDuration.java`, `AggregateStats.java`, etc.
  - Tests: `ByteSizeTest.java`, `TimeDurationTest.java`, `AggregateStatsTest.java`.
  - Grammar: `Directives.g4`.
  - Dependencies: `lib/junit-4.13.2.jar`, `lib/hamcrest-core-1.3.jar`.

