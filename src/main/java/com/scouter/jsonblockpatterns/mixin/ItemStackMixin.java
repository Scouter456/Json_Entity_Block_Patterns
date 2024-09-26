package com.scouter.jsonblockpatterns.mixin;

import com.scouter.jsonblockpatterns.JsonBlockPatterns;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    public void JsonBlockPatterns$useOn(UseOnContext useOnContext, CallbackInfoReturnable<InteractionResult> cir){
        if(!useOnContext.getLevel().isClientSide) {
            InteractionResult result = JsonBlockPatterns.onUseItemInWorld(useOnContext);
            if(result == InteractionResult.SUCCESS) {
                cir.setReturnValue(result);
            }
        };
    }
}
