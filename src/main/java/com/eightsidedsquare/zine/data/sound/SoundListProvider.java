package com.eightsidedsquare.zine.data.sound;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

/**
 * Extend this class and implement {@link SoundListProvider#generate}.
 *
 * <p>Register an instance of the class with {@link FabricDataGenerator.Pack#addProvider} in a {@link net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint}.
 */
public abstract class SoundListProvider implements DataProvider {

    private final Path outputPath;

    public SoundListProvider(FabricPackOutput output) {
        this.outputPath = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK).resolve(output.getModId()).resolve("sounds.json");
    }

    protected abstract void generate(SoundEntryConsumer consumer);

    protected void putSubtitle(SoundEvent soundEvent, String subtitle) {

    }

    @Override
    public CompletableFuture<?> run(CachedOutput writer) {
        JsonObject jsonObject = new JsonObject();
        this.generate((soundEvent, soundEntry) -> {
            if(soundEntry.subtitle() != null) {
                this.putSubtitle(soundEvent, soundEntry.subtitle());
            }
            SoundEntryRecord.CODEC.encodeStart(JsonOps.INSTANCE, soundEntry)
                    .ifSuccess(soundEntryJson -> jsonObject.add(soundEvent.location().getPath(), soundEntryJson));
        });
        return DataProvider.saveStable(writer, jsonObject, this.outputPath);
    }

    @Override
    public String getName() {
        return "Sound List";
    }
}
