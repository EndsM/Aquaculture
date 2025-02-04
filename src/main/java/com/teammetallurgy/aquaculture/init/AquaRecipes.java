package com.teammetallurgy.aquaculture.init;

import com.teammetallurgy.aquaculture.Aquaculture;
import com.teammetallurgy.aquaculture.item.crafting.BrewingNBT;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;

import static net.minecraft.world.item.alchemy.PotionUtils.setPotion;

@Mod.EventBusSubscriber(modid = Aquaculture.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AquaRecipes {

    @SubscribeEvent
    public static void registerBrewingRecipes(RegistryEvent.Register<RecipeSerializer<?>> event) {
        addBrewingRecipeWithSubPotions(AquaItems.JELLYFISH, Potions.POISON);
        addBrewingRecipeWithSubPotions(AquaItems.LEECH, Potions.HEALING);
    }

    private static void addBrewingRecipeWithSubPotions(Item item, Potion potionType) {
        addBrewingRecipeWithSubPotions(new ItemStack(item), potionType);
    }

    private static void addBrewingRecipeWithSubPotions(@Nonnull ItemStack input, Potion potionType) {
        addRecipe(setPotion(new ItemStack(Items.POTION), Potions.WATER), input, setPotion(new ItemStack(Items.POTION), Potions.MUNDANE));
        addRecipe(setPotion(new ItemStack(Items.POTION), Potions.AWKWARD), input, setPotion(new ItemStack(Items.POTION), potionType));
        addRecipe(setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD), input, setPotion(new ItemStack(Items.SPLASH_POTION), potionType));
        addRecipe(setPotion(new ItemStack(Items.SPLASH_POTION), Potions.WATER), input, setPotion(new ItemStack(Items.SPLASH_POTION), Potions.MUNDANE));
        addRecipe(setPotion(new ItemStack(Items.LINGERING_POTION), Potions.WATER), input, setPotion(new ItemStack(Items.LINGERING_POTION), Potions.MUNDANE));
        addRecipe(setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD), input, setPotion(new ItemStack(Items.LINGERING_POTION), potionType));
    }

    private static void addRecipe(@Nonnull ItemStack input, @Nonnull ItemStack ingredient, @Nonnull ItemStack output) {
        BrewingRecipeRegistry.addRecipe(new BrewingNBT(Ingredient.of(input), Ingredient.of(ingredient), output));
    }
}