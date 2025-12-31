package com.alessandro.astages.mixin.ore;

import net.minecraft.world.level.chunk.LevelChunkSection;
import org.spongepowered.asm.mixin.Mixin;

// 我们保留这个空壳类，这样你就不需要去改动其他文件了
@Mixin(LevelChunkSection.class)
public class ALevelChunkSection {
    
    // 里面原本的方法我已经全部删掉了。
    // 现在这个文件什么都不做，就像一个空的集装箱。
    // 游戏加载它时会发现里面是空的，就会直接忽略它。
    
}