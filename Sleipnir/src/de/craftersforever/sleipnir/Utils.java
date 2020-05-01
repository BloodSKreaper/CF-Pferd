package de.craftersforever.sleipnir;

import java.security.SecureRandom;
import java.util.Random;

public class Utils {
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        SecureRandom random = new SecureRandom();
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }
}
