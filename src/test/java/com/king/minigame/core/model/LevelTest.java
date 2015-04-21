package com.king.minigame.core.model;

import com.king.minigame.core.model.Level;

import org.testng.annotations.Test;

@Test
public class LevelTest {

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_levelId_is_null() {

    Level level = new Level(null);
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void throw_IllegalArgumentException_if_levelId_is_negative() {

    Level level = new Level(-1);
  }

}
