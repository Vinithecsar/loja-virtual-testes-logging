package com.virtual.loja.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class UniqueIdGenerator {
  private static final AtomicInteger counter = new AtomicInteger();

  public static int generateId() {
    return counter.incrementAndGet();
  }

  private UniqueIdGenerator() {
  }
}
