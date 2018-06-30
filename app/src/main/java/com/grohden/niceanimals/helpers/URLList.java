package com.grohden.niceanimals.helpers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class URLList {

    /**
     * Creates a list of URLs from a list of strings
     * WARNING: If the url is invalid, it is not included in the final list
     *
     * @return A list of valid object urls
     */
    public static List<URL> fromStrings(String... urls) {
        return Stream.of(urls)
                .map(stringUrl -> {
                    try {
                        return new URL(stringUrl);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
