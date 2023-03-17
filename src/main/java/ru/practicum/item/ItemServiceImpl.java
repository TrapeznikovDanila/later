package ru.practicum.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.item.dto.AddItemRequest;
import ru.practicum.tag.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;

    private final UrlMetaDataRetriever urlMetaDataRetriever;
    private final ItemUrlStatusProvider itemUrlStatusProvider;
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Item> getItems(long userId) {
        return repository.findByUserId(userId);
    }

    @Transactional
    @Override
    public ItemDto addNewItem(Long userId, AddItemRequest request) {
        UrlMetaDataRetriever.UrlMetaData result = urlMetaDataRetriever.retrieve(request.getUrl());
        Optional<Item> maybeExistingItem = repository.findByUserIdAndResolvedUrl(userId, result.getResolvedUrl());
        Item item;
        if(maybeExistingItem.isEmpty()) {
            item = repository.save(ItemMapper.makeItem(result, userId, request.getTags()));
        } else {
            item = maybeExistingItem.get();
            if (request.getTags() != null && !request.getTags().isEmpty()) {
                item.getTags().addAll(request.getTags());
                repository.save(item);
            }
        }
        return ItemMapper.makeItemDto(item);
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

    @Override
    public List<ItemCountByUser> getItemsCountForUserByUrl(Long userId, String urlPart) {
        urlPart = Optional.ofNullable(urlPart).orElse("");
        return repository.countItemsByUser(urlPart);
    }
}
