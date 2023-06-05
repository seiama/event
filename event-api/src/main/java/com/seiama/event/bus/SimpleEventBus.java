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

import com.seiama.event.Cancellable;
import com.seiama.event.EventConfig;
import com.seiama.event.EventSubscription;
import com.seiama.event.registry.EventRegistry;
import java.util.List;
import java.util.OptionalInt;
import org.jetbrains.annotations.NotNull;

import static java.util.Objects.requireNonNull;

/**
 * A simple implementation of an event bus.
 *
 * @param <E> the base event type
 * @since 1.0.0
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SimpleEventBus<E> implements EventBus<E> {
  protected final EventRegistry<E> registry;
  protected final EventExceptionHandler exceptions;

  /**
   * Constructs a new {@code SimpleEventBus}.
   *
   * @param registry the event registry
   * @param exceptions the event exception handler
   * @since 1.0.0
   */
  public SimpleEventBus(final @NotNull EventRegistry<E> registry, final @NotNull EventBus.EventExceptionHandler exceptions) {
    this.registry = requireNonNull(registry, "registry");
    this.exceptions = requireNonNull(exceptions, "exceptions");
  }

  @Override
  public void post(final @NotNull E event, final @NotNull OptionalInt order) {
    @SuppressWarnings("unchecked")
    final Class<? extends E> type = (Class<? extends E>) event.getClass();
    final List<EventSubscription<? super E>> subscriptions = this.registry.subscriptions(type);
    if (subscriptions.isEmpty()) {
      return;
    }
    for (final EventSubscription<? super E> subscription : subscriptions) {
      if (this.accepts(subscription, event, order)) {
        try {
          subscription.subscriber().on(event);
        } catch (final Throwable t) {
          this.exceptions.eventExceptionCaught(subscription, event, t);
        }
      }
    }
  }

  @SuppressWarnings("RedundantIfStatement")
  protected boolean accepts(final @NotNull EventSubscription<? super E> subscription, final @NotNull E event, final @NotNull OptionalInt order) {
    final EventConfig config = subscription.config();

    if (config.exact() && event.getClass() != subscription.event()) return false;

    if (order.isPresent() && config.order() != order.getAsInt()) return false;

    if (!config.acceptsCancelled() && event instanceof Cancellable && ((Cancellable) event).cancelled()) return false;

    return true;
  }
}
