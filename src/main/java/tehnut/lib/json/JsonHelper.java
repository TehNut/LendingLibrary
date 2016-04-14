package tehnut.lib.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import tehnut.lib.LendingLibrary;
import tehnut.lib.json.serialization.*;
import tehnut.lib.util.BlockStack;

import javax.annotation.Nullable;
import java.io.*;

public class JsonHelper {

    public static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .registerTypeAdapter(BlockPos.class, new SerializerBlockPos())
            .registerTypeAdapter(ItemStack.class, new SerializerItemStack(false))
            .registerTypeAdapter(BlockStack.class, new SerializerBlockStack())
            .create();

    // Helpers

    /**
     * Attempts to create a default Json file for an object if it doesn't already exist.
     *
     * Ex: {@code JsonHelper.<String>tryCreateDefault(FILE, new String("defaultValue"));}
     *
     * @param jsonFile - The file to create and write to.
     * @param def      - A default object to write to the file.
     * @param <T>      - The type to serialize.
     * @return If the file was created.
     */
    public static <T> boolean tryCreateDefault(File jsonFile, Object def) {
        try {
            if (!jsonFile.exists() && jsonFile.createNewFile()) {
                String json = gson.toJson(def, new TypeToken<T>(){}.getType());
                FileWriter fileWriter = new FileWriter(jsonFile);
                fileWriter.write(json);
                fileWriter.close();
                return true;
            }
        } catch (IOException e) {
            LendingLibrary.getLogger().error("Error handling JSON file {}", jsonFile.toString());
        }

        return false;
    }

    /**
     * Attempts to create an object from a provided Json file.
     *
     * @param jsonFile - The file to read from.
     * @param clazz    - The class type to deserialize.
     * @param <T>      - The type to deserialize.
     * @return The created object
     */
    @Nullable
    public static <T> T fromJson(File jsonFile, Class<T> clazz) {
        try {
            return gson.fromJson(new FileReader(jsonFile), clazz);
        } catch (FileNotFoundException e) {
            LendingLibrary.getLogger().error(e.getLocalizedMessage());
        }

        return null;
    }

    // Getters

    public static boolean getBoolean(JsonElement jsonElement, String memberName, boolean def) {
        if (!jsonElement.getAsJsonObject().has(memberName))
            return def;
        
        return jsonElement.getAsJsonObject().get(memberName).getAsBoolean();
    }

    public static String getString(JsonElement jsonElement, String memberName, String def) {
        if (!jsonElement.getAsJsonObject().has(memberName))
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsString();
    }

    public static char getCharacter(JsonElement jsonElement, String memberName, char def) {
        if (!jsonElement.getAsJsonObject().has(memberName))
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsCharacter();
    }

    public static int getInteger(JsonElement jsonElement, String memberName, int def) {
        if (!jsonElement.getAsJsonObject().has(memberName))
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsInt();
    }

    public static short getShort(JsonElement jsonElement, String memberName, short def) {
        if (!jsonElement.getAsJsonObject().has(memberName))
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsShort();
    }

    public static long getLong(JsonElement jsonElement, String memberName, long def) {
        if (!jsonElement.getAsJsonObject().has(memberName))
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsLong();
    }

    public static double getDouble(JsonElement jsonElement, String memberName, double def) {
        if (!jsonElement.getAsJsonObject().has(memberName))
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsDouble();
    }
    
    public static float getFloat(JsonElement jsonElement, String memberName, float def) {
        if (!jsonElement.getAsJsonObject().has(memberName))
            return def;

        return jsonElement.getAsJsonObject().get(memberName).getAsFloat();
    }
}
