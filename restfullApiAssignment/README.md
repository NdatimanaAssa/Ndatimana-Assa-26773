# Maven Wrapper (mvnw.cmd)

This project includes the Maven Wrapper (`mvnw.cmd`), which allows you to run Maven builds without having a global Maven installation. The wrapper automatically downloads the correct version of Maven as defined in the project configuration, ensuring build consistency across different environments.

## Usage

To run Maven commands, use the `mvnw` script located in the root of this project instead of the global `mvn` command.

**Windows Command Prompt:**
```cmd
mvnw clean install
```

**PowerShell:**
```powershell
.\mvnw clean install
```

## Prerequisites

- **Java**: You must have a JDK installed and the `JAVA_HOME` environment variable set, or `java` must be available in your system PATH.

## Configuration

The wrapper configuration (such as the Maven version to use) is located in `.mvn/wrapper/maven-wrapper.properties`.

## Environment Variables

The script supports the following optional environment variables to customize its behavior:

| Variable | Description |
| :--- | :--- |
| `MVNW_VERBOSE` | Set to `true` to enable verbose logging during the wrapper execution. |
| `MVNW_REPOURL` | Specify a custom repository URL base for downloading the Maven distribution (useful for corporate mirrors). |
| `MVNW_USERNAME` | Username for the repository URL if authentication is required. |
| `MVNW_PASSWORD` | Password for the repository URL. |

## Maven Daemon (mvnd)

The wrapper script includes logic to support `maven-mvnd`. If the `distributionUrl` in `maven-wrapper.properties` points to an `mvnd` distribution, the wrapper will execute `mvnd.cmd` instead of standard Maven.

## License

The Maven Wrapper is licensed under the Apache License, Version 2.0.