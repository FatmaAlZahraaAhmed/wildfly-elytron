/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wildfly.security.password.impl;

import java.security.InvalidKeyException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactorySpi;
import org.wildfly.security.password.interfaces.ClearPassword;
import org.wildfly.security.password.spec.ClearPasswordSpec;

public final class PasswordFactorySpiImpl extends PasswordFactorySpi {

    protected Password engineGeneratePassword(final String algorithm, final KeySpec keySpec) throws InvalidKeySpecException {
        switch (algorithm) {
            case "clear": {
                if (keySpec instanceof ClearPasswordSpec) {
                    return new ClearPasswordImpl(((ClearPasswordSpec)keySpec).getEncodedPassword().clone());
                } else {
                    break;
                }
            }
        }
        throw new InvalidKeySpecException();
    }

    protected <S extends KeySpec> S engineGetKeySpec(final String algorithm, final Password password, final Class<S> keySpecType) throws InvalidKeySpecException {
        if (password instanceof AbstractPasswordImpl) {
            final AbstractPasswordImpl abstractPassword = (AbstractPasswordImpl) password;
            if (algorithm.equals(abstractPassword.getAlgorithm())) {
                return abstractPassword.getKeySpec(keySpecType);
            }
        }
        throw new InvalidKeySpecException();
    }

    protected Password engineTranslatePassword(final String algorithm, final Password password) throws InvalidKeyException {
        if (password instanceof AbstractPasswordImpl) {
            final AbstractPasswordImpl abstractPassword = (AbstractPasswordImpl) password;
            if (algorithm.equals(abstractPassword.getAlgorithm())) {
                return abstractPassword;
            }
        }
        switch (algorithm) {
            case "clear": {
                if (password instanceof ClearPassword) {
                    return new ClearPasswordImpl((ClearPassword) password);
                } else {
                    break;
                }
            }
        }
        throw new InvalidKeyException();
    }

    protected boolean engineVerify(final String algorithm, final Password password, final char[] guess) throws InvalidKeyException {
        if (password instanceof AbstractPasswordImpl) {
            final AbstractPasswordImpl abstractPassword = (AbstractPasswordImpl) password;
            if (algorithm.equals(abstractPassword.getAlgorithm())) {
                return abstractPassword.verify(guess);
            }
        }
        throw new InvalidKeyException();
    }

    protected <S extends KeySpec> boolean engineConvertibleToKeySpec(final String algorithm, final Password password, final Class<S> keySpecType) {
        if (password instanceof AbstractPasswordImpl) {
            final AbstractPasswordImpl abstractPassword = (AbstractPasswordImpl) password;
            if (algorithm.equals(abstractPassword.getAlgorithm())) {
                return abstractPassword.convertibleTo(keySpecType);
            }
        }
        return false;
    }
}