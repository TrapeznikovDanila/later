package ru.practicum.item;

import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemDto {
    private Long id;
    private Long userId;
    private String url;
    private Set<String> tags;
}
