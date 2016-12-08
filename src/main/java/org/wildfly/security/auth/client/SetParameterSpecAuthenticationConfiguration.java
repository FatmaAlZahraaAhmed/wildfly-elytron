/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.wildfly.security.auth.client;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.wildfly.security.auth.callback.ParameterCallback;

/**
 * @author <a href="mailto:fjuma@redhat.com">Farah Juma</a>
 */
class SetParameterSpecAuthenticationConfiguration extends AuthenticationConfiguration {
    private final AlgorithmParameterSpec parameterSpec;

    SetParameterSpecAuthenticationConfiguration(final AuthenticationConfiguration parent, final AlgorithmParameterSpec parameterSpec) {
        super(parent.without(SetCallbackHandlerAuthenticationConfiguration.class));
        this.parameterSpec = parameterSpec;
    }

    void handleCallback(final Callback[] callbacks, final int index) throws UnsupportedCallbackException, IOException {
        Callback callback = callbacks[index];
        if (callback instanceof ParameterCallback) {
            ParameterCallback parameterCallback = (ParameterCallback) callback;
            if ((parameterCallback.getParameterSpec() == null) && parameterCallback.isParameterSupported(parameterSpec)) {
                parameterCallback.setParameterSpec(parameterSpec);
                return;
            }
        }
        super.handleCallback(callbacks, index);
    }

    @Override
    AuthenticationConfiguration reparent(AuthenticationConfiguration newParent) {
        return new SetParameterSpecAuthenticationConfiguration(newParent, parameterSpec);
    }

    @Override
    StringBuilder asString(StringBuilder sb) {
        return parentAsString(sb).append("ParameterSpec,");
    }

}