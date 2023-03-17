package ru.practicum.item;

import ru.practicum.tag.Tag;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class ItemMapper {

    private static final DateTimeFormatter dtFormatter = DateTimeFormatter
            .ofPattern("yyyy.MM.dd hh:mm:ss")
            .withZone(ZoneOffset.UTC);

    public static Item makeItem(UrlMetaDataRetriever.UrlMetaData urlMetaData,
                                Long userId, Set<String> tags) {
        return Item.builder()
                .userId(userId)
                .url(urlMetaData.getNormalUrl())
                .resolvedUrl(urlMetaData.getResolvedUrl())
                .hasImage(urlMetaData.isHasImage())
                .hasVideo(urlMetaData.isHasVideo())
                .title(urlMetaData.getTitle())
                .mimeType(urlMetaData.getMimeType())
                .tags(tags)
                .dateResolved(urlMetaData.getDateResolved())
                .build();
    }

    public static ItemDto makeItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .title(item.getTitle())
                .normalUrl(item.getUrl())
                .resolvedUrl(item.getResolvedUrl())
                .hasImage(item.isHasImage())
                .hasVideo(item.isHasVideo())
                .mimeType(item.getMimeType())
                .unread(item.isUnread())
                .dateResolved(dtFormatter.format(item.getDateResolved()))
                .tags(new HashSet<>(item.getTags()))
                .build();
    }
}
