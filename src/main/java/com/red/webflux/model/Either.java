package com.red.webflux.model;

import com.red.webflux.service.CheckedFunction;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.util.Pair;

/** @Author: Red @Descpription:        @Date: 15:42 2019/12/4 */
public class Either<L, R> {

  private final L left;
  private final R right;

  private Either(L left, R right) {
    this.left = left;
    this.right = right;
  }

  public static <L, R> Either<L, R> Left(L value) {
    return new Either(value, null);
  }

  public static <L, R> Either<L, R> Right(R value) {
    return new Either(null, value);
  }

  public Optional<L> getLeft() {
    return Optional.ofNullable(left);
  }

  public Optional<R> getRight() {
    return Optional.ofNullable(right);
  }

  public boolean isLeft() {
    return left != null;
  }

  public boolean isRight() {
    return right != null;
  }

  public <T> Optional<T> mapLeft(Function<? super L, T> mapper) {
    if (isLeft()) {
      return Optional.of(mapper.apply(left));
    }
    return Optional.empty();
  }

  public <T> Optional<T> mapRight(Function<? super R, T> mapper) {
    if (isRight()) {
      return Optional.of(mapper.apply(right));
    }
    return Optional.empty();
  }

  /**
   * lamber 抛出异常 发生异常时,流的处理会立即停止
   *
   * @param function
   * @param <T>
   * @param <R>
   * @return
   */
  public static <T, R> Function<T, R> warp(CheckedFunction<T, R> function) {
    return t -> {
      try {
        return function.apply(t);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    };
  }

  /**
   * lamber 抛出异常 发生异常时,流的处理会继续 不保存原始值
   *
   * @param function
   * @param <T>
   * @param <R>
   * @return
   */
  public static <T, R> Function<T, Either> lift(CheckedFunction<T, R> function) {
    return t -> {
      try {
        return Either.Right(function.apply(t));
      } catch (Exception e) {
        return Either.Left(e);
      }
    };
  }

  /**
   * lamber 抛出异常 发生异常时,流的处理会继续 异常和原始值都保存在左侧
   *
   * @param function
   * @param <T>
   * @param <R>
   * @return
   */
  public static <T, R> Function<T, Either> liftWithValue(CheckedFunction<T, R> function) {
    return t -> {
      try {
        return Either.Right(function.apply(t));
      } catch (Exception ex) {
        return Either.Left(Pair.of(ex, t));
      }
    };
  }
}
