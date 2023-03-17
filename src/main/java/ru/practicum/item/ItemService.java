package ru.practicum.item;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.item.dto.AddItemRequest;

import java.util.List;
import java.util.Set;

public interface ItemService {
    List<Item> getItems(long userId);
    ItemDto addNewItem(Long userId, AddItemRequest  request);
    void deleteItem(long userId, long itemId);
    List<ItemInfoWithUrlState> getItemsWithUrlState(long userId);
    Item getItemById(long userId, long itemId);
    List<ItemCountByUser> getItemsCountForUserByUrl(Long userId, String urlPart);
}
