package ru.practicum.item.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GetItemRequest {
    private long userId;
    private LinkState state;
    private ContentType contentType;
    private SortType sort;
    private int limit;
    private List<String> tags;

    public GetItemRequest(long userId, String state, String contentType, String sort, int limit, List<String> tags) {
        this.userId = userId;
        this.state = LinkState.valueOf(state.toUpperCase());
        this.contentType = ContentType.valueOf(contentType.toUpperCase());
        this.sort = SortType.valueOf(sort.toUpperCase());
        this.limit = limit;
        this.tags = tags;
    }

    public boolean hasTags() {
        return tags  != null && !tags.isEmpty();
    }
    public enum LinkState {
        ALL, UNREAD, READ
    }

    public enum ContentType {
        ALL, ARTICLE, IMAGE, VIDEO
    }

    public enum SortType {
        NEWEST, OLDEST, TITLE, SITE
    }
}
