# AwakenCurrency API

A secure and efficient Crystal currency system API for Minecraft servers.

## For Plugin Developers

### Adding to Your Project

#### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.vouchme</groupId>
        <artifactId>awakencurrency-api</artifactId>
        <version>1.0.0</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

#### Gradle
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    compileOnly 'com.github.therealaqz:awakencurrency-api:1.0.0'
}
```

### Basic Usage

```java
import me.vouchme.awakenCurrency.api.CurrencyAPIProvider;
import me.vouchme.awakenCurrency.api.ICurrencyAPI;

public class YourPlugin extends JavaPlugin {
    
    private ICurrencyAPI currencyAPI;
    
    @Override
    public void onEnable() {
        // Check if AwakenCurrency is available
        if (!CurrencyAPIProvider.isAPIAvailable()) {
            getLogger().severe("AwakenCurrency not found!");
            return;
        }
        
        currencyAPI = CurrencyAPIProvider.getAPI();
        getLogger().info("Hooked into AwakenCurrency!");
    }
    
    // Give crystals to a player
    public void giveReward(Player player, double amount) {
        if (currencyAPI.giveCrystals(player, amount)) {
            player.sendMessage("You received " + currencyAPI.formatCrystals(amount) + "!");
        }
    }
    
    // Check if player can afford something
    public boolean canAfford(Player player, double cost) {
        return currencyAPI.hasCrystals(player, cost);
    }
}
```

### Plugin Dependencies

Add to your `plugin.yml`:
```yaml
depend: [AwakenCurrency]
# or for optional:
softdepend: [AwakenCurrency]
```

## API Methods

| Method | Description | Returns |
|--------|-------------|---------|
| `giveCrystals(Player, double)` | Give crystals to player | boolean |
| `takeCrystals(Player, double)` | Take crystals from player | boolean |
| `setCrystals(Player, double)` | Set player's balance | void |
| `getCrystals(Player)` | Get player's balance | double |
| `hasCrystals(Player, double)` | Check if player has amount | boolean |
| `formatCrystals(double)` | Format amount for display | String |

*All methods also have UUID versions for offline players*

## Features

- **Thread Safe**: Designed for concurrent access
- **Rate Limited**: Built-in protection against API abuse
- **Offline Support**: Full UUID-based operations
- **Input Validation**: All inputs are validated
- **Easy Integration**: Simple provider pattern

## Requirements

- Java 21+
- Paper/Spigot 1.21+
- AwakenCurrency plugin installed

## Support

- Download AwakenCurrency: [https://github.com/therealaqz/AwakenCurrency-API]
- Documentation: [https://github.com/therealaqz/AwakenCurrency-API]
- Support: [https://discord.awakenmc.com]
  
Copyright (c) 2025 Aqz

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software (the AwakenCurrency API) and associated documentation files,
  to deal in the Software without restriction, subject to the following conditions:

  The above copyright notice and this permission notice shall be included in all
  copies or substantial portions of the Software.

  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
  SOFTWARE.
  
---
*Developed by Aqz for the AwakenCurrency Network*
