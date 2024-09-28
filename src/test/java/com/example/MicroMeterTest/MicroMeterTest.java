package com.example.MicroMeterTest;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Assert;
import org.junit.Test;

public class MicroMeterTest {

    @Test
    public void test() {
        SimpleMeterRegistry register = new SimpleMeterRegistry();
        Metrics.addRegistry(register);

        Counter counter = register.counter("counter");

        counter.increment();

        Assert.assertEquals(counter.count(), 1);
    }
}
