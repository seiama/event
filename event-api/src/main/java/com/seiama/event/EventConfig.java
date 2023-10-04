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
package com.seiama.event;

import org.jspecify.annotations.NullMarked;

/**
 * Event configuration.
 *
 * @since 1.0.0
 */
@NullMarked
public interface EventConfig {
  /**
   * The default value for {@link #order()}.
   *
   * @since 1.0.0
   */
  int DEFAULT_ORDER = 0;
  /**
   * The default value for {@link #acceptsCancelled()}.
   *
   * @since 1.0.0
   */
  boolean DEFAULT_ACCEPTS_CANCELLED = true;
  /**
   * The default value for {@link #exact()}.
   *
   * @since 1.0.0
   */
  boolean DEFAULT_EXACT = false;

  /**
   * Gets the default configuration.
   *
   * @return the default configuration
   * @since 1.0.0
   */
  static EventConfig defaults() {
    return EventConfigImpl.DEFAULTS;
  }

  /**
   * Gets the post order.
   *
   * @return the post order
   * @since 1.0.0
   */
  default int order() {
    return DEFAULT_ORDER;
  }

  /**
   * Sets the post order.
   *
   * @param order the post order
   * @return an {@link EventConfig}
   * @since 1.0.0
   */
  default EventConfig order(final int order) {
    return new EventConfigImpl(order, this.acceptsCancelled(), this.exact());
  }

  /**
   * Gets if cancelled events are accepted.
   *
   * @return if cancelled events are accepted
   * @since 1.0.0
   */
  default boolean acceptsCancelled() {
    return DEFAULT_ACCEPTS_CANCELLED;
  }

  /**
   * Sets if cancelled events are accepted.
   *
   * @param acceptsCancelled if cancelled events are accepted
   * @return an {@link EventConfig}
   * @since 1.0.0
   */
  default EventConfig acceptsCancelled(final boolean acceptsCancelled) {
    return new EventConfigImpl(this.order(), acceptsCancelled, this.exact());
  }

  /**
   * Gets if only the exact event type is accepted.
   *
   * @return if only the exact event type is accepted
   * @since 1.0.0
   */
  default boolean exact() {
    return DEFAULT_EXACT;
  }

  /**
   * Sets if only the exact event type is accepted.
   *
   * @param exact if only the exact event type is accepted
   * @return an {@link EventConfig}
   * @since 1.0.0
   */
  default EventConfig exact(final boolean exact) {
    return new EventConfigImpl(this.order(), this.acceptsCancelled(), exact);
  }
}
