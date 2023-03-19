package ru.practicum.item;

import ru.practicum.item.dto.AddItemRequest;
import ru.practicum.item.dto.GetItemRequest;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ModifyItemRequest;

import java.util.List;

public interface ItemService {
    List<Item> getItems(long userId);
    ItemDto addNewItem(Long userId, AddItemRequest  request);
    void deleteItem(long userId, long itemId);
    List<ItemInfoWithUrlState> getItemsWithUrlState(long userId);
    Item getItemById(long userId, long itemId);
    List<ItemCountByUser> getItemsCountForUserByUrl(Long userId, String urlPart);
    List<ItemDto> getItems(GetItemRequest req);
    ItemDto patchItem(ModifyItemRequest request);
}
