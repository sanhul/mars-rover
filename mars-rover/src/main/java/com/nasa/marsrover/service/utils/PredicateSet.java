package com.nasa.marsrover.service.utils;

import com.nasa.marsrover.entity.enums.Control;
import org.apache.commons.math3.primes.Primes;

import java.util.function.IntPredicate;
import java.util.function.Predicate;
public class PredicateSet {
    public static final Predicate<String> doesNotcontainM = s -> !s.contains(Control.M.toString());
    public static final Predicate<String> doesNotcontainR = s -> !s.contains(Control.R.toString());
    public static final Predicate<String> doesNotcontainL = s -> !s.contains(Control.L.toString());
    public static final IntPredicate notPrimeNumber = value -> !Primes.isPrime(value);

}
