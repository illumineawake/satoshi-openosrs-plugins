package net.runelite.client.plugins.webwalker;

import net.runelite.client.config.*;
import net.runelite.client.plugins.webwalker.farming.*;

@ConfigGroup(WebWalker.CONFIG_GROUP)
public interface WebWalkerConfig extends Config {
    String WALKING = "walking";
    String FARMING_PAGE = "farmingPage";

    @ConfigTitle(
            name = "Instructions",
            description = "",
            position = 17
    )
    String instructionsTitle = "Instructions";

    @ConfigItem(
            keyName = "instructions",
            name = "",
            description = "Instructions. Don't enter anything into this field",
            position = 18,
            section = instructionsTitle
    )
    default String instructions() {
        return "Select your location from the drop-down or enter a custom location using x,y,z format. Use Location/Tile Location in Developer Tools to obtain a custom coordinate.";
    }

    default boolean notesTitle() {
        return false;
    }

    @ConfigItem(
            keyName = "notepad",
            name = "",
            description = "Paste custom coords that you want to save for frequent use",
            section = "notesTitle",
            position = 30
    )
    default String notepad() {
        return "Paste custom co-ords that you want to save for frequent use";
    }

    @ConfigItem(
            keyName = "category",
            name = "Category",
            description = "Select the category of destinations",
            position = 100
    )
    default Category category() {
        return Category.BANKS;
    }

    @ConfigItem(
            keyName = "catBanks",
            name = "Location",
            description = "Select the location to walk to",
            position = 101,
            hidden = true,
            unhide = "category",
            unhideValue = "BANKS"
    )
    default Banks catBanks() {
        return Banks.NEAREST;
    }

    @ConfigItem(
            keyName = "catBarcrawl",
            name = "Location",
            description = "Select the location to walk to",
            position = 102,
            hidden = true,
            unhide = "category",
            unhideValue = "BARCRAWL"
    )
    default Barcrawl catBarcrawl() {
        return Barcrawl.BARBARIAN_ASSAULT;
    }

    @ConfigItem(
            keyName = "catCities",
            name = "Location",
            description = "Select the location to walk to",
            position = 103,
            hidden = true,
            unhide = "category",
            unhideValue = "CITIES"
    )
    default Cities catCities() {
        return Cities.LUMBRIDGE_CASTLE;
    }

    @ConfigItem(
            keyName = "catFarming",
            name = "Patch Type",
            description = "Select the Farming category you want",
            position = 110,
            hidden = true,
            unhide = "category",
            unhideValue = "FARMING"
    )
    default Farming catFarming() {
        return Farming.CACTUS;
    }

    @ConfigItem(
            keyName = "catFarmAllotments",
            name = "Patch",
            description = "Select the location to walk to",
            position = 111,
            hidden = true,
            unhide = "catFarming",
            unhideValue = "ALLOTMENTS"
    )
    default Allotments catFarmAllotments() {
        return Allotments.ARDOUGNE;
    }

    @ConfigItem(
            keyName = "catFarmBushes",
            name = "Patch",
            description = "Select the location to walk to",
            position = 112,
            hidden = true,
            unhide = "catFarming",
            unhideValue = "BUSHES"
    )
    default Bushes catFarmBushes() {
        return Bushes.ARDOUGNE;
    }

    @ConfigItem(
            keyName = "catFarmFruitTrees",
            name = "Patch",
            description = "Select the location to walk to",
            position = 113,
            hidden = true,
            unhide = "catFarming",
            unhideValue = "FRUIT_TREES"
    )
    default FruitTrees catFarmFruitTrees() {
        return FruitTrees.BRIMHAVEN;
    }

    @ConfigItem(
            keyName = "catFarmHerbs",
            name = "Patch",
            description = "Select the location to walk to",
            position = 114,
            hidden = true,
            unhide = "catFarming",
            unhideValue = "HERBS"
    )
    default Herbs catFarmHerbs() {
        return Herbs.ARDOUGNE;
    }

    @ConfigItem(
            keyName = "catFarmHops",
            name = "Patch",
            description = "Select the location to walk to",
            position = 115,
            hidden = true,
            unhide = "catFarming",
            unhideValue = "HOPS"
    )
    default Hops catFarmHops() {
        return Hops.LUMBRIDGE;
    }

    @ConfigItem(
            keyName = "catFarmTrees",
            name = "Patch",
            description = "Select the location to walk to",
            position = 116,
            hidden = true,
            unhide = "catFarming",
            unhideValue = "TREES"
    )
    default Trees catFarmTrees() {
        return Trees.FALADOR;
    }

    @ConfigItem(
            keyName = "catGuilds",
            name = "Location",
            description = "Select the location to walk to",
            position = 103,
            hidden = true,
            unhide = "category",
            unhideValue = "GUILDS"
    )
    default Guilds catGuilds() {
        return Guilds.FARMING_GUILD;
    }

    @ConfigItem(
            keyName = "catSkilling",
            name = "Location",
            description = "Select the location to walk to",
            position = 104,
            hidden = true,
            unhide = "category",
            unhideValue = "SKILLING"
    )
    default Skilling catSkilling() {
        return Skilling.ARCEUUS_ESS_MINE;
    }

    @ConfigItem(
            keyName = "catSlayer",
            name = "Location",
            description = "Select the location to walk to",
            position = 105,
            hidden = true,
            unhide = "category",
            unhideValue = "SLAYER"
    )
    default Slayer catSlayer() {
        return Slayer.BURTHORPE_SLAYER;
    }

    @ConfigItem(
            keyName = "catMisc",
            name = "Location",
            description = "Select the location to walk to",
            position = 106,
            hidden = true,
            unhide = "category",
            unhideValue = "MISC"
    )
    default Misc catMisc() {
        return Misc.MISCELLANIA;
    }

    @ConfigItem(
            keyName = "customLocation",
            name = "Custom Location",
            description = "Enter a Coordinate to walk to. Co-ordinate format should be x,y,z. \nTurn on Location or Tile Location in Developer Tools to obtain coordinates.",
            position = 135,
            hidden = true,
            unhide = "category",
            unhideValue = "CUSTOM"
    )
    default String customLocation() {
        return "0,0,0";
    }

    @ConfigItem(
            keyName = "disableRun",
            name = "Disable Running",
            description = "Disable running to arrive at your destination with 100% energy.",
            position = 145
    )
    default boolean disableRun() {
        return false;
    }

    @ConfigItem(
            keyName = "allowTeleports",
            name = "Allow Teleporting",
            description = "Allows walker to use teleport items in your inventory to travel.",
            position = 145
    )
    default boolean allowTeleports() {
        return false;
    }

    @ConfigSection(
            name = "Teleport Movecosts",
            description = "Movecosts define how many tiles a teleport needs to save before its used.",
            position = 146,
            closedByDefault = true
    )
    String teleportMovecosts = "Teleport Movecosts";

    @ConfigItem(
            keyName = "teleportSpellCost",
            name = "Teleport Spell",
            description = "Sets the Movecost for Teleport spells and tablets.",
            position = 146,
            section = teleportMovecosts
    )
    default int teleportSpellCost() {
        return 50;
    }

    @ConfigItem(
            keyName = "teleportScrollCost",
            name = "Teleport Scroll",
            description = "Sets the Movecost for Teleport scrolls.",
            position = 147,
            section = teleportMovecosts
    )
    default int teleportScrollCost() {
        return 100;
    }

    @ConfigItem(
            keyName = "nonrechargableTeleCost",
            name = "Non-Rechargable",
            description = "Sets the Movecost for non-rechargable teleports.(Ring of Duelling)",
            position = 148,
            section = teleportMovecosts
    )
    default int nonrechargableTeleCost() {
        return 80;
    }

    @ConfigItem(
            keyName = "rechargableTeleCost",
            name = "Rechargable",
            description = "Sets the Movecost for rechargable teleports.(Glory)",
            position = 149,
            section = teleportMovecosts
    )
    default int rechargableTeleCost() {
        return 50;
    }

    @ConfigItem(
            keyName = "unlimitedTeleportCost",
            name = "Unlimited Teleport",
            description = "Sets the Movecost for unlimited use teleports.(Ectophial)",
            position = 150,
            section = teleportMovecosts
    )
    default int unlimitedTeleportCost() {
        return 10;
    }

    @ConfigItem(
            keyName = "startButton",
            name = "Start",
            description = "Start walker",
            position = 151
    )
    default Button startButton() {
        return new Button();
    }

    @ConfigItem(
            keyName = "stopButton",
            name = "Stop",
            description = "Stop walker",
            position = 151
    )
    default Button stopButton() {
        return new Button();
    }

}

