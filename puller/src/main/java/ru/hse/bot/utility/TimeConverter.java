package ru.hse.bot.utility;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@UtilityClass
public class TimeConverter {
    public static OffsetDateTime convertFromInstantToOffsetDateTime(Instant instant) {
        return OffsetDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    public static Instant convertFromUtcToInstant(Long utc) {
        return Instant.ofEpochSecond(utc);
    }

    public static OffsetDateTime convertFromUtcToOffsetDateTime(Long utc) {
        return convertFromInstantToOffsetDateTime(convertFromUtcToInstant(utc));
    }
}
