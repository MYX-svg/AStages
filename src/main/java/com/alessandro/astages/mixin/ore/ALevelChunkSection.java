package com.alessandro.astages.mixin.ore;

import com.alessandro.astages.api.holder.AClientHolder;
import com.alessandro.astages.config.AStagesClient;
import com.alessandro.astages.core.AClientRestrictionManager;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelChunkSection.class)
public class ALevelChunkSection {
    @ModifyReturnValue(method = "getBlockState", at = @At("RETURN"))
    public BlockState astages$getBlockState(BlockState original) {
        // [Fix Start] 修复逻辑 By Gemini
        try {
            // 关键修改：将线程组检查提到最前面！
            // 服务端线程在运行到 Thread.currentThread()... 时会返回 false。
            // 由于 && 的短路特性，后面的 .get() 根本不会被执行，从而避免了 Config 未加载的崩溃。
            if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.CLIENT 
                && AStagesClient.LEVEL_CHUNK_SECTION_EXPERIMENTAL_SETTINGS.get()) {
                
                return AClientRestrictionManager.ORE_INSTANCE.getReplacement(AClientHolder.serverAndPlayer(), original);
            }
        } catch (Exception e) {
            // [Safety Net] 防御性编程
            // 即使上面的逻辑漏网，如果配置抛出 "Cannot get config value" 异常，
            // 这里会捕获它并什么都不做，确保游戏继续运行，返回原版方块。
        }
        // [Fix End]

        return original;
    }
}
