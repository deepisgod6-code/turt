# Turtle Master Potion Cooldown Plugin

A Paper Minecraft 1.21 plugin that adds configurable cooldowns to drinking Turtle Master potions.

## Features

- ✅ Per-player cooldown tracking for Turtle Master potion consumption
- ✅ Fully configurable cooldown duration (in seconds)
- ✅ Admin commands to manage cooldowns
- ✅ Customizable messages
- ✅ Enable/disable the feature via config
- ✅ Lightweight and efficient

## Installation

1. Build the plugin:
   ```bash
   mvn clean package
   ```

2. Copy the JAR file from `target/turtlemaster-cooldown-1.0.0.jar` to your `plugins/` folder

3. Restart your server

4. Configure in `plugins/TurtleMaster-Cooldown/config.yml`

## Configuration

Edit `config.yml` to customize:

```yaml
# Enable or disable the feature
enabled: true

# Cooldown duration in seconds
cooldown-duration: 60

# Send notifications to players
send-notifications: true

# Customize messages
messages:
  cooldown-active: '&cYou must wait &e{remaining}&c seconds before drinking another Turtle Master potion!'
  cooldown-expired: '&aYou can now drink another Turtle Master potion!'
  cooldown-set: '&aSet cooldown for {player} to {seconds} seconds'
  cooldown-cleared: '&aCleared cooldown for {player}'
  not-found: '&cPlayer {player} not found'
  prefix: '&b[TurtleMaster]&r '
```

## Commands

### Player Commands
- None (feature is automatic)

### Admin Commands
- `/tmcooldown reload` - Reload configuration from file
- `/tmcooldown status` - View current plugin status
- `/tmcooldown set <player> <seconds>` - Manually set cooldown for a player
- `/tmcooldown clear <player>` - Clear cooldown for a player
- `/tmc` - Alias for `/tmcooldown`

All admin commands require the `turtlemaster.admin` permission (OP by default).

## Permissions

- `turtlemaster.admin` - Access to admin commands (defaults to OP)

## How It Works

1. Player drinks a Turtle Master potion (identified by the Resistance effect)
2. Plugin prevents consumption and cancels the event if player is on cooldown
3. If not on cooldown, the potion is consumed and a cooldown timer starts
4. Player receives a message when cooldown expires (if notifications enabled)
5. Individual cooldown timers per player

## Requirements

- Paper 1.21+
- Java 21+

## Building from Source

Requirements:
- Java 21 JDK
- Maven 3.8+

```bash
mvn clean package
```

The compiled JAR will be in the `target/turtlemaster-cooldown-1.0.0.jar`.

## Support

For issues or suggestions, please create an issue in the repository.
