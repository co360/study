package com.github.mdssjc.algorithms.utils;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Executor Class.
 *
 * @author Marcelo dos Santos
 *
 */
public class Executor {

  private static final String RESOURCES_PATH = "src/main/resources/";

  public static void execute(final Class<?> clazz) {
    try {
      final Method method = clazz.getMethod("main", String[].class);
      final Annotation[] annotations = clazz
          .getAnnotationsByType(TestDrive.class);

      for (final Annotation annotation : annotations) {
        final TestDrive td = (TestDrive) annotation;
        StdOut.printf("java %s %s%n", clazz.getSimpleName(),
                      Arrays.deepToString(td.value()));

        final String[] value = td.value();
        if (td.valueFile()) {
          for (int i = 0; i < value.length; i++) {
            final boolean exists = Files.exists(Paths.get(RESOURCES_PATH + value[i]));
            if (exists && value[i].length() > 1) {
              value[i] = RESOURCES_PATH + value[i];
            }
          }
        }

        if (td.input().length > 0) {
          System.setIn(new ByteArrayInputStream(
              Arrays.stream(getInputValue(td))
                    .collect(Collectors.joining("\n"))
                    .getBytes()));

          final Method resync = StdIn.class.getDeclaredMethod("resync");
          resync.setAccessible(true);
          resync.invoke(null);
          final Method resync2 = edu.princeton.cs.introcs.StdIn.class.getDeclaredMethod("resync");
          resync2.setAccessible(true);
          resync2.invoke(null);
        }

        final int threads = Thread.activeCount();
        method.invoke(null, (Object) value);
        try {
          while (Thread.activeCount() != threads) {
            Thread.sleep(50);
          }
        } catch (final InterruptedException exception) {
          StdOut.println(exception.getMessage());
          StdOut.println(exception);
        }
      }
    } catch (final IllegalArgumentException | NoSuchMethodException
        | SecurityException | IllegalAccessException exception) {
      StdOut.println(exception.getMessage());
      StdOut.println(exception);
    } catch (final InvocationTargetException exception) {
      StdOut.println(exception.getMessage());
      StdOut.println(exception);
      StdOut.println(exception.getTargetException());
    }
  }

  private static String[] getInputValue(final TestDrive td) {
    if (td.inputFile()) {
      return Arrays.asList(td.input())
            .stream()
            .map(Executor::readValue)
            .flatMap(Stream::of)
            .toArray(String[]::new);
    }
    return td.input();
  }

  private static String[] readValue(final String reference) {
    // TODO: verificar os valores de input dos redirect.
    try {
      return Files.readAllLines(Paths.get(RESOURCES_PATH + reference))
                  .toArray(new String[0]);
    } catch (final IOException e) {
      StdOut.println(e.getMessage());
    }
    return new String[0];
  }

  public static void execute(final Class<?> clazz, final String label) {
    StdOut.println(label);
    execute(clazz);
    StdOut.println();
  }

  public static void execute(final Class<?> clazz, final String[] args) {
    if (args.length == 0) {
      execute(clazz);
      System.exit(0);
    }
  }
}
