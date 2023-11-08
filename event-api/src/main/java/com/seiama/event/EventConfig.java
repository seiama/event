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
   * Creates a new configuration.
   *
   * @param order the post order
   * @param acceptsCancelled if cancelled events are accepted
   * @param exact if only the exact event type is accepted
   * @return a configuration
   * @since 1.0.0
   */
  static EventConfig of(
    final int order,
    final boolean acceptsCancelled,
    final boolean exact
  ) {
    return new EventConfigImpl(order, acceptsCancelled, exact);
  }

  /**
   * Creates a new builder.
   *
   * @return a new builder
   * @since 1.0.0
   */
  static Builder builder() {
    return new EventConfigImpl.BuilderImpl();
  }

  /**
   * Gets the post order.
   *
   * @return the post order
   * @since 1.0.0
   */
  int order();

  /**
   * Sets the post order.
   *
   * @param order the post order
   * @return an {@link EventConfig}
   * @since 1.0.0
   */
  EventConfig order(final int order);

  /**
   * Gets if cancelled events are accepted.
   *
   * @return if cancelled events are accepted
   * @since 1.0.0
   */
  boolean acceptsCancelled();

  /**
   * Sets if cancelled events are accepted.
   *
   * @param acceptsCancelled if cancelled events are accepted
   * @return an {@link EventConfig}
   * @since 1.0.0
   */
  EventConfig acceptsCancelled(final boolean acceptsCancelled);

  /**
   * Gets if only the exact event type is accepted.
   *
   * @return if only the exact event type is accepted
   * @since 1.0.0
   */
  boolean exact();

  /**
   * Sets if only the exact event type is accepted.
   *
   * @param exact if only the exact event type is accepted
   * @return an {@link EventConfig}
   * @since 1.0.0
   */
  EventConfig exact(final boolean exact);

  /**
   * Builder.
   *
   * @since 1.0.0
   */
  interface Builder {
    /**
     * Sets the post order.
     *
     * @param order the post order
     * @return {@code this}
     * @since 1.0.0
     */
    Builder order(final int order);

    /**
     * Sets if cancelled events are accepted.
     *
     * @param acceptsCancelled if cancelled events are accepted
     * @return {@code this}
     * @since 1.0.0
     */
    Builder acceptsCancelled(final boolean acceptsCancelled);

    /**
     * Sets if only the exact event type is accepted.
     *
     * @param exact if only the exact event type is accepted
     * @return {@code this}
     * @since 1.0.0
     */
    Builder exact(final boolean exact);

    /**
     * Builds.
     *
     * @return an {@link EventConfig}
     * @since 1.0.0
     */
    EventConfig build();
  }
}
