package ru.practicum.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.tag.TagRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final ItemUrlStatusProvider itemUrlStatusProvider;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Item> getItems(long userId) {
        return repository.findByUserId(userId);
    }

    @Transactional
    @Override
    public Item addNewItem(Long userId, Item item) {
        item.setUserId(userId);
        Item savedItem = repository.save(item);
        itemUrlStatusProvider.checkupForNewItem(savedItem);
        return savedItem;
    }

    @Transactional
    @Override
    public void deleteItem(long userId, long itemId) {
        repository.deleteById(itemId);
    }

    @Override
    public List<ItemInfoWithUrlState> getItemsWithUrlState(long userId) {
        return repository.findByUserIdWithUrlState(userId);
    }

    @Override
    public Item getItemById(long userId, long itemId) {
        Item item = repository.findById(itemId).get();
        item.setTags(tagRepository.findAllByItemId(itemId).stream()
                .map(t -> t.getName()).collect(Collectors.toSet()));
        return item;
    }
}
