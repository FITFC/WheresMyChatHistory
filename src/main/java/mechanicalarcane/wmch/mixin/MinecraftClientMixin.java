package mechanicalarcane.wmch.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mechanicalarcane.wmch.WMCH;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

/** Injects callbacks to game exit events so cached data can still be saved */
@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Inject(method = "run", at = {
        @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/MinecraftClient;addDetailsToCrashReport(Lnet/minecraft/util/crash/CrashReport;)Lnet/minecraft/util/crash/CrashReport;"
        ),
        @At(
            value = "INVOKE_ASSIGN",
            target = "Lnet/minecraft/client/MinecraftClient;addDetailsToCrashReport(Lnet/minecraft/util/crash/CrashReport;)Lnet/minecraft/util/crash/CrashReport;"
        ),
        @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/MinecraftClient;cleanUpAfterCrash()V"
        )
    })
    private void saveCachedDataOnCrash(CallbackInfo ci) {
        WMCH.writeCachedData(true);
    }
}
