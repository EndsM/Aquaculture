package com.teammetallurgy.aquaculture.init;

import com.google.common.collect.Lists;
import com.teammetallurgy.aquaculture.Aquaculture;
import com.teammetallurgy.aquaculture.api.AquacultureAPI;
import com.teammetallurgy.aquaculture.entity.AquaFishEntity;
import com.teammetallurgy.aquaculture.entity.FishType;
import com.teammetallurgy.aquaculture.item.*;
import com.teammetallurgy.aquaculture.item.neptunium.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import javax.annotation.Nonnull;
import java.util.List;

@Mod.EventBusSubscriber(modid = Aquaculture.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(Aquaculture.MOD_ID)
public class AquaItems {
    private static final List<Item> ITEMS = Lists.newArrayList();

    //Fishing
    public static final Item IRON_FISHING_ROD = register(new AquaFishingRodItem(Tiers.IRON, new Item.Properties().defaultDurability(125).tab(Aquaculture.GROUP)), "iron_fishing_rod");
    public static final Item GOLD_FISHING_ROD = register(new AquaFishingRodItem(Tiers.GOLD, new Item.Properties().defaultDurability(55).tab(Aquaculture.GROUP)), "gold_fishing_rod");
    public static final Item DIAMOND_FISHING_ROD = register(new AquaFishingRodItem(Tiers.DIAMOND, new Item.Properties().defaultDurability(450).tab(Aquaculture.GROUP)), "diamond_fishing_rod");
    public static final Item NEPTUNIUM_FISHING_ROD = register(new AquaFishingRodItem(AquacultureAPI.MATS.NEPTUNIUM, new Item.Properties().defaultDurability(1000).tab(Aquaculture.GROUP)), "neptunium_fishing_rod");
    public static final Item WORM = register(AquacultureAPI.createBait(20, 1, Aquaculture.GROUP), "worm");
    public static final Item FISHING_LINE = register(new DyeableItem(0), "fishing_line");
    public static final Item BOBBER = register(new DyeableItem(13838890), "bobber");

    // Neptunium
    public static final Item NEPTUNIUM_NUGGET = register(new SimpleItem(), "neptunium_nugget");
    public static final Item NEPTUNIUM_INGOT = register(new SimpleItem(), "neptunium_ingot");
    public static final Item NEPTUNIUM_PICKAXE = register(new NeptuniumPickaxe(AquacultureAPI.MATS.NEPTUNIUM, 1, -2.8F), "neptunium_pickaxe");
    public static final Item NEPTUNIUM_SHOVEL = register(new NeptuniumShovel(AquacultureAPI.MATS.NEPTUNIUM, 1.5F, -3.0F), "neptunium_shovel");
    public static final Item NEPTUNIUM_AXE = register(new AxeItem(AquacultureAPI.MATS.NEPTUNIUM, 8.0F, -3.0F, new Item.Properties().tab(Aquaculture.GROUP)), "neptunium_axe");
    public static final Item NEPTUNIUM_HOE = register(new NeptuniumHoe(AquacultureAPI.MATS.NEPTUNIUM, -3, 0.4F), "neptunium_hoe");
    public static final Item NEPTUNIUM_SWORD = register(new SwordItem(AquacultureAPI.MATS.NEPTUNIUM, 3, -2.4F, new Item.Properties().tab(Aquaculture.GROUP)), "neptunium_sword");
    public static final Item NEPTUNIUM_BOW = register(new NeptuniumBow(), "neptunium_bow");
    public static final Item NEPTUNIUM_HELMET = register(new NeptuniumArmor(AquacultureAPI.MATS.NEPTINIUM_ARMOR, EquipmentSlot.HEAD).setArmorTexture("neptunium_layer_1"), "neptunium_helmet");
    public static final Item NEPTUNIUM_PLATE = register(new NeptuniumArmor(AquacultureAPI.MATS.NEPTINIUM_ARMOR, EquipmentSlot.CHEST).setArmorTexture("neptunium_layer_1"), "neptunium_chestplate");
    public static final Item NEPTUNIUM_LEGS = register(new NeptuniumArmor(AquacultureAPI.MATS.NEPTINIUM_ARMOR, EquipmentSlot.LEGS).setArmorTexture("neptunium_layer_2"), "neptunium_leggings");
    public static final Item NEPTUNIUM_BOOTS = register(new NeptuniumArmor(AquacultureAPI.MATS.NEPTINIUM_ARMOR, EquipmentSlot.FEET).setArmorTexture("neptunium_layer_1"), "neptunium_boots");

    // Fillet Knifes
    public static final Item WOODEN_FILLET_KNIFE = register(new ItemFilletKnife(Tiers.WOOD), "wooden_fillet_knife");
    public static final Item STONE_FILLET_KNIFE = register(new ItemFilletKnife(Tiers.STONE), "stone_fillet_knife");
    public static final Item IRON_FILLET_KNIFE = register(new ItemFilletKnife(Tiers.IRON), "iron_fillet_knife");
    public static final Item GOLD_FILLET_KNIFE = register(new ItemFilletKnife(Tiers.GOLD), "gold_fillet_knife");
    public static final Item DIAMOND_FILLET_KNIFE = register(new ItemFilletKnife(Tiers.DIAMOND), "diamond_fillet_knife");
    public static final Item NEPTINIUM_FILLET_KNIFE = register(new ItemFilletKnife(AquacultureAPI.MATS.NEPTUNIUM), "neptunium_fillet_knife");

    // Misc
    public static final Item DRIFTWOOD = register(new SimpleItem(), "driftwood");
    public static final Item TIN_CAN = register(new SimpleItem(), "tin_can");
    public static final Item NESSAGE_IN_A_BOTTLE = register(new ItemMessageInABottle(), "message_in_a_bottle");
    public static final Item BOX = register(new LootBoxItem(AquaLootTables.BOX), "box");
    public static final Item LOCKBOX = register(new LootBoxItem(AquaLootTables.LOCKBOX), "lockbox");
    public static final Item TREASURE_CHEST = register(new LootBoxItem(AquaLootTables.TREASURE_CHEST), "treasure_chest");
    public static final Item ALGAE = register(new Item(new Item.Properties().tab(Aquaculture.GROUP).food(AquaFoods.ALGAE)), "algae");
    public static final Item FISH_BONES = register(new SimpleItem(), "fish_bones");

    // Food
    public static final Item FISH_FILLET = register(new Item(new Item.Properties().tab(Aquaculture.GROUP).food(AquaFoods.FISH_RAW)), "fish_fillet_raw");
    public static final Item COOKED_FILLET = register(new Item(new Item.Properties().tab(Aquaculture.GROUP).food(AquaFoods.FISH_FILLET)), "fish_fillet_cooked");
    public static final Item FROG_LEGS = register(new Item(new Item.Properties().tab(Aquaculture.GROUP).food(AquaFoods.FISH_RAW)), "frog_legs_raw");
    public static final Item COOKED_FROG_LEGS = register(new Item(new Item.Properties().tab(Aquaculture.GROUP).food(AquaFoods.FROG_LEGS)), "frog_legs_cooked");
    public static final Item TURTLE_SOUP = register(new BowlFoodItem(new Item.Properties().stacksTo(1).tab(Aquaculture.GROUP).food(Foods.MUSHROOM_STEW)), "turtle_soup");
    public static final Item SUSHI = register(new Item(new Item.Properties().tab(Aquaculture.GROUP).food(AquaFoods.SUSHI)), "sushi");

    // Fish
    public static final Item ATLANTIC_COD = FishRegistry.register(new FishItem(), "atlantic_cod");
    public static final Item BLACKFISH = FishRegistry.register(new FishItem(), "blackfish");
    public static final Item PACIFIC_HALIBUT = FishRegistry.register(new FishItem(), "pacific_halibut", FishType.HALIBUT);
    public static final Item ATLANTIC_HALIBUT = FishRegistry.register(new FishItem(), "atlantic_halibut", FishType.HALIBUT);
    public static final Item ATLANTIC_HERRING = FishRegistry.register(new FishItem(), "atlantic_herring", FishType.SMALL);
    public static final Item PINK_SALMON = FishRegistry.register(new FishItem(), "pink_salmon");
    public static final Item POLLOCK = FishRegistry.register(new FishItem(), "pollock");
    public static final Item RAINBOW_TROUT = FishRegistry.register(new FishItem(), "rainbow_trout");
    public static final Item BAYAD = FishRegistry.register(new FishItem(), "bayad", FishType.CATFISH);
    public static final Item BOULTI = FishRegistry.register(new FishItem(), "boulti", FishType.SMALL);
    public static final Item CAPITAINE = FishRegistry.register(new FishItem(), "capitaine");
    public static final Item SYNODONTIS = FishRegistry.register(new FishItem(), "synodontis", FishType.SMALL);
    public static final Item SMALLMOUTH_BASS = FishRegistry.register(new FishItem(), "smallmouth_bass");
    public static final Item BLUEGILL = FishRegistry.register(new FishItem(), "bluegill", FishType.SMALL);
    public static final Item BROWN_TROUT = FishRegistry.register(new FishItem(), "brown_trout");
    public static final Item CARP = FishRegistry.register(new FishItem(), "carp", FishType.LARGE);
    public static final Item CATFISH = FishRegistry.register(new FishItem(), "catfish", FishType.CATFISH);
    public static final Item GAR = FishRegistry.register(new FishItem(), "gar", FishType.LONGNOSE);
    public static final Item MINNOW = FishRegistry.register(AquacultureAPI.createBait(50, 1, Aquaculture.GROUP), "minnow", FishType.SMALL);
    public static final Item MUSKELLUNGE = FishRegistry.register(new FishItem(), "muskellunge", FishType.LONGNOSE);
    public static final Item PERCH = FishRegistry.register(new FishItem(), "perch", FishType.SMALL);
    public static final Item ARAPAIMA = FishRegistry.register(new FishItem(), "arapaima", FishType.LONGNOSE);
    public static final Item PIRANHA = FishRegistry.register(new FishItem(), "piranha", FishType.SMALL);
    public static final Item TAMBAQUI = FishRegistry.register(new FishItem(), "tambaqui", FishType.LARGE);
    public static final Item BROWN_SHROOMA = FishRegistry.register(new FishItem(), "brown_shrooma", FishType.SMALL);
    public static final Item RED_SHROOMA = FishRegistry.register(new FishItem(), "red_shrooma", FishType.SMALL);
    public static final Item JELLYFISH = FishRegistry.register(new SimpleItem(), "jellyfish", FishType.JELLYFISH);
    public static final Item RED_GROUPER = FishRegistry.register(new FishItem(), "red_grouper");
    public static final Item TUNA = FishRegistry.register(new FishItem(), "tuna", FishType.LARGE);
    public static final Item FROG = register(new SimpleItem(), "frog");
    public static final Item LEECH = register(AquacultureAPI.createBait(35, 1, Aquaculture.GROUP), "leech");
    public static final Item GOLDFISH = register(new SimpleItem(), "goldfish");
    public static final Item BOX_TURTLE = register(new SimpleItem(), "box_turtle");
    public static final Item ARRAU_TURTLE = register(new SimpleItem(), "arrau_turtle");
    public static final Item STARSHELL_TURTLE = register(new SimpleItem(), "starshell_turtle");

    //Fish Mounting
    public static final Item OAK_FISH_MOUNT = AquacultureAPI.registerFishMount("oak_fish_mount");
    public static final Item SPRUCE_FISH_MOUNT = AquacultureAPI.registerFishMount("spruce_fish_mount");
    public static final Item BIRCH_FISH_MOUNT = AquacultureAPI.registerFishMount("birch_fish_mount");
    public static final Item JUNGLE_FISH_MOUNT = AquacultureAPI.registerFishMount("jungle_fish_mount");
    public static final Item ACACIA_FISH_MOUNT = AquacultureAPI.registerFishMount("acacia_fish_mount");
    public static final Item DARK_OAK_FISH_MOUNT = AquacultureAPI.registerFishMount("dark_oak_fish_mount");

    /**
     * Registers an item
     *
     * @param item The item to be registered
     * @param name The name to register the item with
     * @return The Item that was registered
     */
    public static Item register(@Nonnull Item item, @Nonnull String name) {
        return register(item, new ResourceLocation(Aquaculture.MOD_ID, name));
    }

    public static Item register(@Nonnull Item item, @Nonnull ResourceLocation registryName) {
        item.setRegistryName(registryName);
        ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        for (Item item : ITEMS) {
            event.getRegistry().register(item);
        }
        for (EntityType<AquaFishEntity> fishType : FishRegistry.fishEntities) { //Registers fish buckets
            if (fishType.getRegistryName() != null) {
                Item bucket = new AquaFishBucket(fishType, (new Item.Properties()).stacksTo(1).tab(Aquaculture.GROUP));
                bucket.setRegistryName(fishType.getRegistryName().getPath() + "_bucket");
                event.getRegistry().register(bucket);
                AquaFishEntity.BUCKETS.put(fishType, bucket);
            }
        }
    }
}