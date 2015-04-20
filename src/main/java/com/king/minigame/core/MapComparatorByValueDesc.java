package com.king.minigame.core;

import java.util.Comparator;
import java.util.Map;

/**
 *
 */
public class MapComparatorByValueDesc implements Comparator {

  public int compare(Object entry1, Object entry2) {
    return ((Comparable) ((Map.Entry) (entry1)).getValue()).compareTo(((Map.Entry) (entry2)).getValue());
  }
}
