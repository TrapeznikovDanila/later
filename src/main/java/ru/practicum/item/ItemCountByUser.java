package ru.practicum.item;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class ItemCountByUser {

    private Long userId;

    private Long count;

}