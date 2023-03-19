package ru.practicum.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModifyItemRequest {
    private long itemId;
    private boolean read;
    private Set<String> tags;
    private boolean replaceTags;

    public boolean hasTags() {
        return tags != null && !tags.isEmpty();
    }
}
