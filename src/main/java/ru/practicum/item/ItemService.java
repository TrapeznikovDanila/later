package ru.practicum.item;

import java.util.List;

public interface ItemService {
    List<Item> getItems(long userId);
    Item addNewItem(Long userId, Item item);
    void deleteItem(long userId, long itemId);
    List<ItemInfoWithUrlState> getItemsWithUrlState(long userId);
    Item getItemById(long userId, long itemId);
}
