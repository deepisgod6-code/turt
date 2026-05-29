# Build Instructions

To build this plugin into a JAR file:

## Prerequisites
- Java 21 JDK installed
- Maven 3.8 or higher installed
- Git (to clone the repository)

## Steps

1. Clone or download the repository
2. Navigate to the repository directory
3. Run the following command:
   ```bash
   mvn clean package
   ```

4. The compiled JAR file will be generated at:
   ```
   target/turtlemaster-cooldown-1.0.0.jar
   ```

5. Copy this JAR file to your Paper server's `plugins/` folder

6. Restart your server

## Verification

After restart, check your server logs for:
```
[TurtleMaster-Cooldown] TurtleMaster Cooldown plugin enabled!
```

A config file will automatically be created at:
```
plugins/TurtleMaster-Cooldown/config.yml
```

## Troubleshooting

- **Build fails**: Ensure Java 21 and Maven are installed and in your PATH
- **Plugin doesn't load**: Check that the JAR is in the correct `plugins/` folder
- **Permission errors**: Make sure you're OP on the server or have the `turtlemaster.admin` permission
