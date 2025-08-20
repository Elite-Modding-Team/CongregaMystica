# Pech Trade Tweaker
Congrega Mystica includes scripting methods that can be used to modify Pech trades. 

## Importing
```groovy
import mods.congregamystica.PechTrades;
```

## Adding Trades
| Parameter  | Description                                                                 |
|:----------:|:----------------------------------------------------------------------------|
|  pechType  | The Pech variant type. Value can be "MINER", "MAGE", "ARCHER", or "COMMON". |
| tradeLevel | The item trade level. Value must be between 1 and 5.                        |
| tradeItem  | The item that will be generated on trade.                                   |

### CraftTweaker
```zenscript
//mods.congregamystica.PechTrades.addTrade(String pechType, int tradeLevel, IItemStack tradeItem);
mods.congregamystica.PechTrades.addTrade("MINER", 1, <minecraft:diamond>);
```
### GroovyScript
```groovy
//mods.congregamystica.PechTrades.addTrade(String pechType, int tradeLevel, ItemStack tradeItem)
mods.congregamystica.PechTrades.addTrade("MINER", 1, item('minecraft:diamond'))
```

## Removing Trades
> [!IMPORTANT]
> Pechs must have at least one trade registered to each trade level. If a trade level is missing a value, Congrega Mystica will automatically populate the missing trade with scrap items.


### CraftTweaker
```zenscript
//mods.congregamystica.PechTrades.removeTrade(String pechType, IIngredient ingredient);
mods.congregamystica.PechTrades.removeTrade("MINER", <minecraft:blaze_rod>);

//mods.congregamystica.PechTrades.removeAllTrades(String pechType);
mods.congregamystica.PechTrades.removeAllTrades("MINER");

//mods.congregamystica.PechTrades.removeAllTrades();
mods.congregamystica.PechTrades.removeAllTrades();
```
### GroovyScript
```groovy
//mods.congregamystica.PechTrades.removeTrade(String pechType, IIngredient ingredient)
mods.congregamystica.PechTrades.removeTrade("MINER", item('minecraft:blaze_rod'))

//mods.congregamystica.PechTrades.removeAllTrades(String pechType)
mods.congregamystica.PechTrades.removeAllTrades("MINER")

//mods.congregamystica.PechTrades.removeAllTrades()
mods.congregamystica.PechTrades.removeAllTrades()
```
