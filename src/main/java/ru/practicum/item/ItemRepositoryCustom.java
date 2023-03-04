package ru.practicum.item;

import java.util.List;

public interface ItemRepositoryCustom {
    List<ItemInfoWithUrlState> findByUserIdWithUrlState(Long userId);
}
