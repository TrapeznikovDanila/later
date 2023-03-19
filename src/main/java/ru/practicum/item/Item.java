package ru.practicum.item;

import lombok.*;
import ru.practicum.tag.Tag;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "items")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "url", length = 1000)
    private String url;
    @ElementCollection
    @CollectionTable(name = "tags", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "name")
    private Set<String> tags = new HashSet<>();
    @Column(name = "resolved_url")
    private String resolvedUrl;
    @Column(name = "mime_type")
    private String mimeType;
    private String title;
    @Column(name = "has_image")
    private boolean hasImage;
    @Column(name = "has_video")
    private boolean hasVideo;
    @Column(name = "date_resolved")
    private Instant dateResolved;
    private boolean unread;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return hasImage == item.hasImage && hasVideo == item.hasVideo && unread == item.unread && Objects.equals(id, item.id) && Objects.equals(userId, item.userId) && Objects.equals(url, item.url) && Objects.equals(tags, item.tags) && Objects.equals(resolvedUrl, item.resolvedUrl) && Objects.equals(mimeType, item.mimeType) && Objects.equals(title, item.title) && Objects.equals(dateResolved, item.dateResolved);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, url, tags, resolvedUrl, mimeType, title, hasImage, hasVideo, dateResolved, unread);
    }
}
