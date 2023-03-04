package ru.practicum.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> get(@RequestHeader("X-Later-User-Id") long userId) {
        return itemService.getItems(userId)
                .stream()
                .map(ItemMapper::makeItemDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/state")
    public List<ItemInfoWithUrlState> getItemsWithUrlState(@RequestHeader("X-Later-User-Id") long userId) {
        return itemService.getItemsWithUrlState(userId);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Later-User-Id") Long userId,
                    @RequestBody CreateItemDto itemDto) {
        return ItemMapper.makeItemDto(itemService.addNewItem(userId, ItemMapper.makeItem(itemDto)));
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
