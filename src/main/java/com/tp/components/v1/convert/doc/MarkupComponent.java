/*
 * Copyright 2017 Robert Winkler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tp.components.v1.convert.doc;

import com.tp.components.v1.convert.SwaggerConverter;
import com.tp.components.v1.convert.config.ConfigBO;
import com.tp.components.v1.convert.config.Labels;
import com.tp.components.v1.convert.config.SwaggerExtensionRegister;
import io.vavr.Function2;

public abstract class MarkupComponent<T> implements Function2<MarkupDocBuilder, T, MarkupDocBuilder> {

    protected static final String COLON = " : ";
    protected SwaggerConverter.Context context;
    protected Labels labels;
    protected ConfigBO config;
    protected SwaggerExtensionRegister extensionRegistry;

    public MarkupComponent(SwaggerConverter.Context context) {
        this.context = context;
        this.config = context.getConfig();
        this.extensionRegistry = context.getExtensionRegistry();
        this.labels = context.getLabels();
    }
}
