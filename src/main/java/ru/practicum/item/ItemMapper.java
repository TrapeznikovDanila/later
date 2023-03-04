package ru.practicum.item;

import java.util.HashSet;

public class ItemMapper {
    public static Item makeItem(CreateItemDto itemDto) {
        return Item.builder()
                .url(itemDto.getUrl())
                .tags(itemDto.getTags())
                .build();
    }

    public static ItemDto makeItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .userId(item.getUserId())
                .url(item.getUrl())
                .tags(new HashSet<>(item.getTags()))
                .build();
    }
}
