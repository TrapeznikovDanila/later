package ru.practicum.item;

import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateItemDto {
    private String url;
    private Set<String> tags;
}
