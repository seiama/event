/*
 * This file is part of event, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 Seiama
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.seiama.event.bus;

import com.seiama.event.EventSubscription;
import java.util.OptionalInt;
import org.jspecify.annotations.NullMarked;

/**
 * An event bus.
 *
 * @param <E> the base event type
 * @since 1.0.0
 */
@NullMarked
public interface EventBus<E> {
  /**
   * Posts an event to all registered subscribers.
   *
   * @param event the event
   * @since 1.0.0
   */
  default void post(final E event) {
    this.post(event, OptionalInt.empty());
  }

  /**
   * Posts an event to all registered subscribers at the order provided in {@code order}.
   *
   * @param event the event
   * @param order the order
   * @since 1.0.0
   */
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  void post(final E event, final OptionalInt order);

  /**
   * An event exception handler.
   *
   * @since 1.0.0
   */
  @FunctionalInterface
  interface EventExceptionHandler {
    /**
     * Handles a caught exception.
     *
     * @param subscription the event subscription
     * @param event the event
     * @param throwable the exception
     * @param <E> the event type
     * @since 1.0.0
     */
    <E> void eventExceptionCaught(final EventSubscription<? super E> subscription, final E event, final Throwable throwable);
  }
}
