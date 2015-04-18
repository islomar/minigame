package com.king.minigame.score;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    userScores.put(userId, score);
  }

  //TODO: probably delete it
  public Integer getMaximumScore2() {
    final Comparator<Map.Entry<Integer, Score>> sortLevelByScoreAscComparator = (p1, p2) -> Integer.compare(p1.getValue().getScoreValue(), p2.getValue().getScoreValue());
    return userScores.entries().stream().max(sortLevelByScoreAscComparator).get().getValue().getScoreValue();
  }

  public List<Score> getScoreListOrderedByValueDesc() {
    final Comparator<Map.Entry<Integer, Score>> sortLevelByScoreDescComparator = (p1, p2) -> Integer.compare(p2.getValue().getScoreValue(), p1.getValue().getScoreValue());
    return userScores.entries().stream().sorted(sortLevelByScoreDescComparator).map(s -> s.getValue()).collect(Collectors.toList());
  }

  public Integer getMaximumScore() {
    return userScores.entries().stream().max(Comparator.comparing(item -> item.getValue().getScoreValue())).get().getValue().getScoreValue();
  }

  public Map<Integer, Optional<Score>>getMaximumScoreForUser() {
    //Lists.newArrayList(userScores.values());
    Map<Integer, Optional<Score>> result = new HashMap<>();
    userScores.asMap().forEach( (k,v) -> result.put(k, getMaximumScore(v)) );
    return result;

//    userScores.entries().stream().forEach( s -> s.);
//    List<Score> allUserScores = userScores.entries().stream().map( s -> s. Lists.newArrayList(userScores.values());
//    return allUserScores.stream()
  }

  private Optional<Score> getMaximumScore(Collection<Score> v) {
    return v.stream().max(Comparator.comparing(item -> item.getScoreValue()));}

  private void validateParameters(Integer levelId) {
    if (levelId == null) {
      throw new IllegalArgumentException("levelId can not be null");
    }
  }

}
