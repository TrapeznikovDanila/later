package ru.practicum.item;

import java.time.Instant;

public interface UrlMetaDataRetriever {

    UrlMetaData retrieve(String urlString);
    interface UrlMetaData {
        String getNormalUrl();
        String getResolvedUrl();
        String getMimeType();
        String getTitle();
        boolean isHasImage();
        boolean isHasVideo();
        Instant getDateResolved();
    }
}
