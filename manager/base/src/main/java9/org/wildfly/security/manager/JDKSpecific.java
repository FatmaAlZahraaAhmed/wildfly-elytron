/*
 * JBoss, Home of Professional Open Source
 *
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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
package org.wildfly.security.manager;

import java.lang.StackWalker;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JDK-specific classes which are replaced for different JDK major versions. This class is for JDK 9.
 * @author <a href="mailto:jucook@redhat.com">Justin Cook</a>
 */
final class JDKSpecific {
    public static Class<?> getCallerClass(int n){
        List<StackWalker.StackFrame> frames = StackWalker.getInstance().walk(s ->
                s.limit(n).collect(Collectors.toList())
        );
        return frames.get(frames.size() - 1).getClass();
    }

    public static Class<?> lookUpClass(){
        return StackWalker.StackFrame.class;
    }
}
