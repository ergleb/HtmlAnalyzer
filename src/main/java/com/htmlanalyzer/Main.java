package com.htmlanalyzer;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.htmlanalyzer.FileHelper.*;
import static com.htmlanalyzer.MappingHelper.*;

public class Main {
    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {

        String originalPath, diffCasePath;
        try {
            originalPath = args[0];
            diffCasePath = args[1];
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Not enough arguments");
        }

        String targetElementId = args.length > 2 ? args[2] : "make-everything-ok-button";

        Element button = findElementById(new File(originalPath), targetElementId)
                .orElseThrow(() -> new IllegalArgumentException("Source Element not Found"));


        Elements diffCaseElems = findAllLinks(new File(diffCasePath), "a").
                orElseThrow(() ->new IllegalArgumentException("No Links in Diff File"));

        LOGGER.debug("All links in the doc: {}", diffCaseElems);

        String result = findPath(button, diffCaseElems);

        System.out.print("path " + result);



    }

    private static String findPath (Element button, Elements diffCaseElems) {
        Map<String, String> attrMap = elemToMap(button);

        LOGGER.debug("Source element attributes: {}", attrMap);

        List<Map<String, String>> diffAttrMaps = diffCaseElems.
                stream().
                map(MappingHelper::elemToMap).
                collect(Collectors.toList());

        int[] matches = new int[diffAttrMaps.size()];

        for (int i = 0; i < matches.length; i++) {
            for (String key :
                    attrMap.keySet()) {
                if (attrMap.get(key).equals(diffAttrMaps.get(i).get(key))) {
                    matches[i]++;
                }
            }
        }

        int maxMatchIndex = findMaxPos(matches);

        LOGGER.info("elem with max matches: {}", diffCaseElems.get(maxMatchIndex));

        List<Element> parents = new ArrayList<> (diffCaseElems
                .get(maxMatchIndex)
                .parents());

        Collections.reverse(parents);

        String path = parents
                .stream()
                .map(Element::tagName)
                .collect(Collectors.joining(" > "));

        LOGGER.debug("parents: {}",  path);

        return path;
    }
}
