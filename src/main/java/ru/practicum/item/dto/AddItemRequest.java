package ru.practicum.item.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AddItemRequest {
    private String url;
    private Set<String> tags = new HashSet<>();
}
