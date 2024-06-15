package com.mika.mikabackend.dto.page;

import lombok.Data;

import java.util.Arrays;

@Data
public class SortOrder {
    private final String property;
    private final Direction direction;

    public SortOrder(String property) {
        this.property = property;
        this.direction = Direction.DESC;
    }

    public SortOrder(String property, Direction direction) {
        this.property = property;
        this.direction = direction;
    }

    public enum Direction {
        ASC, DESC;

        public static Direction lookup(final String id) {
            for (Direction enumValue : values()) {
                if (enumValue.name().equalsIgnoreCase(id)) {
                    return enumValue;
                }
            }
            throw new RuntimeException(String.format("Invalid value for sort order [%s]. " +
                    "It should be %s", id, Arrays.asList(Direction.values())));
        }
    }
}
