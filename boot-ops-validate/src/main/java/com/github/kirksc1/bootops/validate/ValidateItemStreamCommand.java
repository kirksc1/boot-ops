/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.kirksc1.bootops.validate;

import com.github.kirksc1.bootops.core.*;
import com.github.kirksc1.bootops.core.init.ItemInitializationExecutor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * A ValidateItemStreamCommand encapsulates a named command to be executed on a Stream of items referenced by a URI.
 * It instantiates the Item, initializes and validates it.
 */
public class ValidateItemStreamCommand extends BaseItemStreamCommand {
    public static final String COMMAND_NAME = "validate";

    private final ItemInitializationExecutor initializationExecutor;
    private final ItemValidationExecutor validationExecutor;

    /**
     * Construct a ValidateItemStreamCommand with the provided details.
     * @param reader The ItemManifestReader used for reading item manifests.
     * @param parser The ItemManifestParser used for parsing item manifests into Items.
     * @param filters A List of Item filters that should be applied to the stream, in which only items that
     *                pass the filter test will be processed by the command.
     * @param publisher An application event publisher for publishing command lifecycle events.
     * @param initializationExecutor An InitializationExecutor used to initialize the Item.
     * @param validationExecutor A ValidationExecutor used to validate the Item.
     */
    public ValidateItemStreamCommand(ItemManifestReader reader, ItemManifestParser parser, List<Predicate<Item>> filters, ApplicationEventPublisher publisher, ItemInitializationExecutor initializationExecutor, ItemValidationExecutor validationExecutor) {
        super(COMMAND_NAME, reader, parser, filters, publisher);

        Assert.notNull(initializationExecutor, "The ItemInitializationExecutor provided was null");
        Assert.notNull(validationExecutor, "The ItemValidationExecutor provided was null");

        this.initializationExecutor = initializationExecutor;
        this.validationExecutor = validationExecutor;
    }

    /**
     * Initialize and Validate the Item.
     * @param item The item on which to perform the command.
     * @param parameters The parameters for use by the command when executing on the item.
     * @param context The contextual data for the Item.
     * @return An ItemCommandResult detailing the results of the command execution on the item.
     */
    @Override
    protected ItemCommandResult execute(Item item, Map<String, List<String>> parameters, ItemContext context) {
        initializationExecutor.initiateInitialization(item);
        initializationExecutor.completeInitialization(item);

        ItemValidationResult result = validationExecutor.initiateValidation(item);
        validationExecutor.completeValidation(item, result);

        return super.execute(item, parameters, context);
    }
}
