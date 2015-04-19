package com.king.minigame.core;

import java.util.Comparator;
import java.util.Map;

/**
 *
 */
public class MapComparatorByValueDesc implements Comparator {

  Map map;

  public MapComparatorByValueDesc(Map map) {
    this.map = map;
  }

  public int compare(Object keyA, Object keyB) {
    Comparable valueA = (Comparable) map.get(keyA);
    Comparable valueB = (Comparable) map.get(keyB);
    return valueA.compareTo(valueB);
  }
}
