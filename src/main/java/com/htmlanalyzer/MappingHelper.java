package com.htmlanalyzer;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;

import java.util.Map;
import java.util.stream.Collectors;

public class MappingHelper {

    static int findMaxPos (int[] array) {
        int maxPos = 0;
        int maxEl = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxEl) {
                maxEl = array[i];
                maxPos = i;
            }
        }
        return maxPos;
    }

    static Map<String, String> elemToMap(Element element) {
        return element.attributes().asList().stream().collect(Collectors.toMap(Attribute::getKey, Attribute::getValue));

    }
}
