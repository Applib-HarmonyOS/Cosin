package com.nikitagordia.cosin.textadapters;

import com.nikitagordia.cosin.Cosin;
import java.security.SecureRandom;
import java.util.Date;

/**
 * Created by nikitagordia on 05.03.18.
 */
public class DefaultBinaryTextAdapter implements Cosin.TextAdapter {
    private static final char ZERO = '0';
    private static final char ONE = '1';
    private SecureRandom random;

    public DefaultBinaryTextAdapter() {
        random = new SecureRandom(String.valueOf(new Date().getTime()).getBytes());
    }

    @Override
    public char getString(int numOfRect) {
        if (random.nextBoolean()) {
            return ONE;
        } else {
            return ZERO;
        }
    }
}
