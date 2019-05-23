package com.zhitar.shortenerrest.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Helper {
    private static final Random GENERATOR = new SecureRandom();

    private Helper() {
    }

    public static String generateRandomString() {
        return new BigInteger(32, GENERATOR).toString(36);
    }
}
