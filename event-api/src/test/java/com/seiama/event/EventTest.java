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

import com.seiama.event.bus.EventBus;
import com.seiama.event.bus.SimpleEventBus;
import com.seiama.event.registry.EventRegistry;
import com.seiama.event.registry.SimpleEventRegistry;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventTest {
  private final EventRegistry<Object> registry = new SimpleEventRegistry<>(Object.class);
  private final EventBus<Object> bus = new SimpleEventBus<>(this.registry, TestFailingEventExceptionHandler.INSTANCE);

  @Test
  void testSubscribePostUnsubscribePost() {
    assertFalse(this.registry.subscribed(TestEvent1.class));

    final EventSubscription<TestEvent1> subscription = this.registry.subscribe(TestEvent1.class, event -> event.touches++);

    assertTrue(this.registry.subscribed(TestEvent1.class));

    final TestEvent1 event = new TestEvent1();
    this.bus.post(event);
    assertEquals(1, event.touches);

    subscription.dispose();

    assertFalse(this.registry.subscribed(TestEvent1.class));
    this.bus.post(event);
    assertEquals(1, event.touches);
  }

  @Test
  void testHierarchy() {
    assertFalse(this.registry.subscribed(TestEvent1.class));
    assertFalse(this.registry.subscribed(TestEvent2.class));

    this.registry.subscribe(TestEvent1.class, event -> event.touches++);
    this.registry.subscribe(TestEvent2.class, event -> event.touches++);

    assertTrue(this.registry.subscribed(TestEvent1.class));
    assertTrue(this.registry.subscribed(TestEvent2.class));

    final TestEvent1 event1 = new TestEvent1();
    this.bus.post(event1);
    assertEquals(1, event1.touches);

    final TestEvent2 event2 = new TestEvent2();
    this.bus.post(event2);
    assertEquals(2, event2.touches);
  }

  @Test
  void testCancellable() {
    this.registry.subscribe(TestEvent1.class, EventConfig.defaults().acceptsCancelled(false), event -> event.touches++);

    final TestEvent1 event = new TestEvent1();
    this.bus.post(event);
    assertEquals(1, event.touches);

    event.cancelled(true);

    this.bus.post(event);
    assertEquals(1, event.touches);
  }

  @Test
  void testExact() {
    this.registry.subscribe(TestEvent1.class, EventConfig.defaults().exact(true), event -> event.touches++);

    final TestEvent1 event1 = new TestEvent1();
    this.bus.post(event1);
    assertEquals(1, event1.touches);

    final TestEvent2 event2 = new TestEvent2();
    this.bus.post(event2);
    assertEquals(0, event2.touches);
  }

  @Test
  void testUnsubscribeAll() {
    assertFalse(this.registry.subscribed(TestEvent1.class));
    this.registry.subscribe(TestEvent1.class, event -> event.touches++);
    assertTrue(this.registry.subscribed(TestEvent1.class));
    this.registry.unsubscribeIf(subscription -> true); // removes all subscribers
    assertFalse(this.registry.subscribed(TestEvent1.class));
  }

  @Test
  void testUnsubscribeOwnedInstances() {
    assertFalse(this.registry.subscribed(TestEvent1.class));

    final UUID owner1 = UUID.randomUUID();
    final UUID owner2 = UUID.randomUUID();

    this.registry.subscribe(TestEvent1.class, new OwnedSubscriber<>(owner1, event -> event.touches++));
    this.registry.subscribe(TestEvent1.class, new OwnedSubscriber<>(owner2, event -> event.touches++));

    assertTrue(this.registry.subscribed(TestEvent1.class));

    final TestEvent1 event = new TestEvent1();
    this.bus.post(event);
    assertEquals(2, event.touches);

    this.registry.unsubscribeIf(OwnedSubscriber.unsubscribeOwner(owner2));

    assertTrue(this.registry.subscribed(TestEvent1.class));

    this.bus.post(event);
    assertEquals(3, event.touches); // only 3, since one subscriber is gone
  }
}
