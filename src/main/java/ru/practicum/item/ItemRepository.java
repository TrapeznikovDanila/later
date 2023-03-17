package ru.practicum.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
    List<Item> findByUserId(long userId);

    List<ItemInfo> findAllByUserId(Long userId);

    Optional<Item> findByUserIdAndResolvedUrl(Long userId, String resolvedUrl);

    @Query("SELECT new ru.practicum.item.ItemCountByUser(it.userId, COUNT(it.id)) " +
            "FROM Item AS it " +
            "WHERE it.url like (CONCAT('%', ?1, '%')) " +
            "GROUP BY it.userId " +
            "ORDER BY COUNT(it.id) DESC")
    List<ItemCountByUser> countItemsByUser(String urlPart);

}