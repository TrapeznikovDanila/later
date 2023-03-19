package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.item.dto.AddItemRequest;
import ru.practicum.item.dto.GetItemRequest;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ModifyItemRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/state")
    public List<ItemInfoWithUrlState> getItemsWithUrlState(@RequestHeader("X-Later-User-Id") long userId) {
        return itemService.getItemsWithUrlState(userId);
    }

    @GetMapping("/count")
    public List<ItemCountByUser> getItemsCountForUserByUrl(@RequestHeader("X-Later-User-Id") Long userId,
                                                           @RequestParam(required = false) String url) {
        return itemService.getItemsCountForUserByUrl(userId, url);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Later-User-Id") Long userId,
                    @RequestBody AddItemRequest itemRequest) {
        return itemService.addNewItem(userId, itemRequest);
    }

    @GetMapping
    public List<ItemDto> get(@RequestHeader("X-Later-User-Id") long userId,
                             @RequestParam(defaultValue = "unread") String state,
                             @RequestParam(defaultValue = "all") String contentType,
                             @RequestParam(defaultValue = "newest") String sort,
                             @RequestParam(defaultValue = "10") int limit,
                             @RequestParam(required = false) List<String> tags) {
        return itemService.getItems(new GetItemRequest(userId, state, contentType, sort, limit, tags));
    }

    @PatchMapping
    public ItemDto patchItem(@RequestHeader("X-Later-User-Id") long userId,
                             @RequestBody ModifyItemRequest request) {
        return null;
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@RequestHeader("X-Later-User-Id") long userId,
                           @PathVariable long itemId) {
        itemService.deleteItem(userId, itemId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Later-User-Id") long userId,
                           @PathVariable long itemId) {
        return ItemMapper.makeItemDto(itemService.getItemById(userId, itemId));
    }
}
