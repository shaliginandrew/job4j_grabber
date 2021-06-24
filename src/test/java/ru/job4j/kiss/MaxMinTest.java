package ru.job4j.kiss;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

public class MaxMinTest {
    @Test
    public void WhenMax() {
        Assert.assertThat(MaxMin.max(List.of(5, 4, 3, 2, 1), Comparator.naturalOrder()), Is.is(5));
    }

    @Test
    public void WhenMin() {
        Assert.assertThat(MaxMin.min(List.of(1, 4, 3, 2, 1), Comparator.naturalOrder()), Is.is(1));
    }
}
