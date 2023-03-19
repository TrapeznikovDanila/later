package ru.practicum.item.dto;

import lombok.*;
import ru.practicum.tag.Tag;

import java.util.Set;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDto {
    private Long id;
    private String normalUrl;
    private String resolvedUrl;
    private String mimeType;
    private String title;
    private boolean hasImage;
    private boolean hasVideo;
    private boolean unread;
    private String dateResolved;
    private Set<String> tags;
}
