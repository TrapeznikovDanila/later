package ru.practicum.item;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.item.dto.AddItemRequest;
import ru.practicum.item.dto.GetItemRequest;
import ru.practicum.item.dto.ItemDto;
import ru.practicum.item.dto.ModifyItemRequest;
import ru.practicum.tag.TagRepository;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository repository;
    private final UserRepository userRepository;
    private final UrlMetaDataRetriever urlMetaDataRetriever;
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
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("User with id=%s was not found", userId)));
        Item item = repository.findById(itemId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("Item with id=%s was not found", itemId)));
        if (item.getUserId() != userId) {
            throw new IllegalArgumentException(String.format("Item id=%s was added by another User", itemId));
        }
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

    @Override
    public List<ItemDto> getItems(GetItemRequest req) {
        QItem item = QItem.item;
        List<BooleanExpression> conditions = new ArrayList<>();
        conditions.add(item.userId.eq(req.getUserId()));
        GetItemRequest.LinkState state = req.getState();
        if (!state.equals(GetItemRequest.LinkState.ALL)) {
            conditions.add(makeStateCondition(state));
        }
        GetItemRequest.ContentType contentType = req.getContentType();
        if (!contentType.equals(GetItemRequest.ContentType.ALL)) {
            conditions.add(makeContentTypeConditions(contentType));
        }
        if (req.hasTags()) {
            conditions.add(item.tags.any().in(req.getTags()));
        }
        BooleanExpression finalCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();
        Sort sort = makeOrderBySortType(req.getSort());
        PageRequest pageRequest = PageRequest.of(0, req.getLimit(), sort);
        Iterable<Item> items = repository.findAll(finalCondition, pageRequest);
        return ItemMapper.makeItemDtos(items);
    }

    @Override
    public ItemDto patchItem(ModifyItemRequest request) {
        Item item = repository.findById(request.getItemId())
                .orElseThrow(() -> new NotFoundException(String
                        .format("Item with id =%s was not found", request.getItemId())));
        if (request.isRead()) {
            item.setUnread(false);
        }
        if (request.hasTags()) {
            if (request.isReplaceTags()) {
                item.setTags(request.getTags());
            } else {
                item.getTags().addAll(request.getTags());
            }
        }
        return ItemMapper.makeItemDto(repository.save(item));
    }

    private BooleanExpression makeStateCondition(GetItemRequest.LinkState state) {
        if (state.equals(GetItemRequest.LinkState.READ)) {
            return QItem.item.unread.isFalse();
        } else {
            return QItem.item.unread.isTrue();
        }
    }

    private BooleanExpression makeContentTypeConditions(GetItemRequest
                                                                .ContentType contentType) {
        if (contentType.equals(GetItemRequest.ContentType.ARTICLE)) {
            return QItem.item.mimeType.eq("text");
        } else if (contentType.equals(GetItemRequest.ContentType.VIDEO)) {
            return QItem.item.mimeType.eq("video");
        } else {
            return QItem.item.mimeType.eq("image");
        }
    }

    private Sort makeOrderBySortType(GetItemRequest.SortType sortType) {
        switch (sortType) {
            case TITLE: return Sort.by("title").ascending();
            case NEWEST: return Sort.by("dateResolved").descending();
            case OLDEST: return Sort.by("dateResolved").ascending();
            case SITE:
            default: return Sort.by("resolved").ascending();
        }
    }


}
