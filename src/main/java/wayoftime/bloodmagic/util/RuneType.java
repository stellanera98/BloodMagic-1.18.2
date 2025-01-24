package wayoftime.bloodmagic.util;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum RuneType implements StringRepresentable {
    SPEED,
    SACRIFICE,
    SELF_SACRIFICE,
    DISPLACEMENT,
    CAPACITY,
    AUGMENTED_CAPACITY,
    ORB,
    ACCELERATION,
    CHARGING,
    EFFICIENCY;

    @Override
    public @NotNull String getSerializedName() {
        return this.toString();
    }
}
