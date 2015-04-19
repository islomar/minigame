package com.king.minigame.core;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 */
public class Level {

  private final Integer levelId;
  private ListMultimap<Integer, Score> userScores;

  public Level(final Integer levelId) {

    validateParameters(levelId);
    userScores = ArrayListMultimap.create();
    this.levelId = levelId;
  }

  public int getLevelId() {

    return this.levelId;
  }

  public ListMultimap<Integer, Score> getUserScores() {

    return ImmutableListMultimap.copyOf(userScores);
  }

  public void addScoreForUser(Integer userId, Score score) {

    validateUserScoreParameters(userId, score);

    userScores.put(userId, score);
  }

  private void validateUserScoreParameters(Integer userId, Score score) {

    if (userId == null || score == null) {
      throw new IllegalArgumentException("null values are not allowed");
    }
  }

  public List<Score> getScoreListOrderedByValueDesc() {

    final Comparator<Map.Entry<Integer, Score>> sortLevelByScoreDescComparator = (p1, p2) -> Integer
        .compare(p2.getValue().getScoreValue(), p1.getValue().getScoreValue());
    return userScores.entries().stream().sorted(sortLevelByScoreDescComparator).map(s -> s.getValue())
        .collect(Collectors.toList());
  }

  //WORKS PERFECT, but it should be in the Service
  public Map<Integer, Score> getMaximumScorePerUser() {

    Map<Integer, Score> unsortedUserScores = mapToMaximumScorePerUser();
    return sortUserScores(unsortedUserScores);
  }

  private Map<Integer, Score> mapToMaximumScorePerUser() {
    Map<Integer, Score> unsortedUserScores = new HashMap<>();
    userScores.asMap().forEach((k, v) -> unsortedUserScores.put(k, getMaximumScore(v).get()));
    return unsortedUserScores;
  }

  private Map<Integer, Score> sortUserScores(Map<Integer, Score> unsortedUserScores) {


    Map<Integer, Score> sortedUserScores = new TreeMap<>(new MapComparatorByValueDesc(unsortedUserScores));
    sortedUserScores.putAll(unsortedUserScores);

    return sortedUserScores;
  }

  private Optional<Score> getMaximumScore(Collection<Score> v) {

    Optional<Score> max = v.stream().max(Comparator.comparing(item -> item.getScoreValue()));
    return max;
  }

  private void validateParameters(Integer levelId) {

    if (levelId == null) {
      throw new IllegalArgumentException("levelId can not be null");
    }
  }

}
