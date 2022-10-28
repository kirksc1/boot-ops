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
package com.github.kirksc1.bootops.converge;

import com.github.kirksc1.bootops.core.*;
import com.github.kirksc1.bootops.core.init.ItemInitializationExecutor;
import com.github.kirksc1.bootops.validate.ItemValidationExecutor;
import com.github.kirksc1.bootops.validate.ItemValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * The ConvergeItemStreamCommand is the default implementation of the 'converge' command
 * used within the Boot Ops framework.
 */
@Slf4j
public class ConvergeItemStreamCommand extends BaseItemStreamCommand {
    public static final String COMMAND_NAME = "converge";

    private final ItemInitializationExecutor initializationExecutor;
    private final ItemValidationExecutor validationExecutor;
    private final ItemConvergeExecutor convergeExecutor;

    /**
     * Construct a new ConvergeItemStreamCommand with the provided implementations.
     * @param reader The ItemManifestReader used for reading item manifests.
     * @param parser The ItemManifestParser used for parsing item manifests into Items.
     * @param filters A List of Item filters that should be applied to the stream, in which only items that
     *                pass the filter test will be processed by the command.
     * @param publisher An application event publisher for publishing command lifecycle events.
     * @param initializationExecutor An InitializationExecutor used to initialize the Item.
     * @param validationExecutor A ValidationExecutor used to validate the Item.
     * @param convergeExecutor A ConvergeExecutor used to converge the Item.
     */
    public ConvergeItemStreamCommand(ItemManifestReader reader, ItemManifestParser parser, List<Predicate<Item>> filters, ApplicationEventPublisher publisher, ItemInitializationExecutor initializationExecutor, ItemValidationExecutor validationExecutor, ItemConvergeExecutor convergeExecutor) {
        super(COMMAND_NAME, reader, parser, filters, publisher);

        Assert.notNull(initializationExecutor, "The ItemInitializationExecutor provided was null");
        Assert.notNull(validationExecutor, "The ItemValidationExecutor provided was null");
        Assert.notNull(convergeExecutor, "The ItemConvergeExecutor provided was null");

        this.initializationExecutor = initializationExecutor;
        this.validationExecutor = validationExecutor;
        this.convergeExecutor = convergeExecutor;
    }

    /**
     * Execute the converge process with the provided details.
     * @param item The item on which to perform the command.
     * @param parameters The parameters for use by the command when executing on the item.
     * @param context The contextual data for the Item.
     * @return An ItemCommandResult detailing the results of the command execution on the item.
     */
    @Override
    protected ItemCommandResult execute(Item item, Map<String, List<String>> parameters, ItemContext context) {
        ItemCommandResult retVal = null;

        initializationExecutor.initiateInitialization(item);
        initializationExecutor.completeInitialization(item);

        ItemValidationResult result = validationExecutor.initiateValidation(item);
        validationExecutor.completeValidation(item, result);

        if (result.isValid()) {
            convergeExecutor.initiateConverge(item);
            convergeExecutor.completeConverge(item);
            retVal = super.execute(item, parameters, context);
            log.debug("Validation succeeded on item, name={}", item.getName());
        } else {
            BaseItemCommandResult itemCommandResult = new BaseItemCommandResult();
            itemCommandResult.setSuccessful(false);
            retVal = itemCommandResult;
            log.info("Validation failed on item, name={}", item.getName());
        }

        return retVal;
    }
}
