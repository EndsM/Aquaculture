package com.teammetallurgy.aquaculture.misc;

import com.teammetallurgy.aquaculture.Aquaculture;
import com.teammetallurgy.aquaculture.init.AquaItems;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Aquaculture.MOD_ID)
public class NeptunesMight {

    @SubscribeEvent
    public static void onAttack(LivingHurtEvent event) {
        Entity source = event.getSource().getEntity();
        if (source instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) source;
            if (living.isEyeInFluid(FluidTags.WATER)) {
                ItemStack heldStack = living.getMainHandItem();
                if (heldStack.getItem() == AquaItems.NEPTUNIUM_SWORD || heldStack.getItem() == AquaItems.NEPTUNIUM_AXE) {
                    event.setAmount(event.getAmount() * 1.5F);
                }
            }
        }
    }
}