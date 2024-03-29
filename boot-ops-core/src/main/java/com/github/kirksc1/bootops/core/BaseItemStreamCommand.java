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
package com.github.kirksc1.bootops.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * An BaseItemStreamCommand encapsulates a named command to be executed on a Stream of items referenced by a URI.
 * It provides with additional hooks for pre- and post- processing via start() and complete().  It also provides
 * a simplified execution that operates on a single item via execute(Item).
 */
public abstract class BaseItemStreamCommand extends ItemStreamCommand {

    private final ItemManifestReader reader;
    private final ItemManifestParser parser;
    private final Predicate<Item> filter;
    private final ApplicationEventPublisher publisher;

    private static final Predicate<Item> ACCEPT_ALL = item -> true;

    /**
     * Construct a new BaseItemStreamCommand with the provided implementations.
     * @param name The name of the command.
     * @param reader The ItemManifestReader used for reading item manifests.
     * @param parser The ItemManifestParser used for parsing item manifests into Items.
     * @param filters A List of Item filters that should be applied to the stream, in which only items that
     *                pass the filter test will be processed by the command.
     * @param publisher An application event publisher for publishing command lifecycle events.
     */
    protected BaseItemStreamCommand(String name, ItemManifestReader reader, ItemManifestParser parser, List<Predicate<Item>> filters, ApplicationEventPublisher publisher) {
        super(name);

        Assert.notNull(reader, "The ItemManifestReader provided was null");
        Assert.notNull(parser, "The ItemManifestParser provided was null");
        Assert.notNull(publisher, "The ApplicationEventPublisher provided was null");

        this.reader = reader;
        this.parser = parser;

        Predicate<Item> tempFilter = ACCEPT_ALL;
        if (filters != null) {
            for (Predicate<Item> pred : filters) {
                tempFilter = tempFilter.and(pred);
            }
        }
        this.filter = tempFilter;

        this.publisher = publisher;
    }

    /**
     * Execute the command on each of the items referenced by the provided URIs with additional hooks
     * for pre- and post- processing via start() and complete().  It also provides a simplified execution
     * that operates on a single item via execute(Item).
     * @param uriStream The stream of URIs referencing the items.
     * @param parameters A collection of command configuraton parameters.
     * @param context The contextual data for the Item stream.
     * @return ItemStreamCommandResult The result of command execution on the stream.
     */
    @Override
    public ItemStreamCommandResult execute(Stream<URI> uriStream, Map<String,List<String>> parameters, StreamContext context) {
        ExecutionResult startResult = null;
        ExecutionResult completeResult = null;

        startResult = doStart(context);

        List<ItemCommandResult> itemCommandResults = new ArrayList<>();

        if (startResult.isSuccessful()) {
            uriStream.map(uri -> executeUri(uri, parameters, context))
                    .forEach(itemCommandResults::add);

            completeResult = doComplete(context);
        }

        return new BaseItemStreamCommandResult(startResult, itemCommandResults, completeResult);
    }

    /**
     * Execute the command on the item referenced by the provided URI.
     * @param uri The URI referencing the item manifest.
     * @param parameters A collection of command configuraton parameters.
     * @param context The contextual data for the Item stream.
     * @return ItemCommandResult The result of command execution on the item.
     */
    private ItemCommandResult executeUri(URI uri, Map<String,List<String>> parameters, StreamContext context) {
        ItemCommandResult retVal = null;
        Item item = null;

        try {
            InputStream inputStream = reader.read(uri);
            if (inputStream == null) {
                throw new IllegalStateException("ItemManifestReader.read(URI) returned a null value");
            }

            item = parser.parse(inputStream);
            if (item == null) {
                throw new IllegalStateException("ItemManifestParser.parse(InputStream) returned a null value");
            }

            if (this.filter.test(item)) {
                item.setUri(uri);
                retVal = execute(item, parameters, new DefaultItemContext());
                publisher.publishEvent(new ItemCompletedEvent(this, item));
            } else {
                BaseItemCommandResult result = new BaseItemCommandResult();
                result.setSuccessful(true);
                retVal = result;
            }
        } catch (IOException e) {
            retVal = buildItemFailureResult(e);
            publisher.publishEvent(new BootOpsExceptionEvent(this, item, new BootOpsException("Unable to read manifest data at URI=" + uri, e)));
        } catch (ParseException e) {
            retVal = buildItemFailureResult(e);
            publisher.publishEvent(new BootOpsExceptionEvent(this, item, new BootOpsException("Unable to parse the manifest data into an Item", e)));
        } catch (BootOpsException e) {
            retVal = buildItemFailureResult(e);
            publisher.publishEvent(new BootOpsExceptionEvent(this, item, e));
        } catch (RuntimeException e) {
            retVal = buildItemFailureResult(e);
            publisher.publishEvent(new BootOpsExceptionEvent(this, item,new BootOpsException("Unable to process the Item at URI=" + uri, e)));
        }
        return retVal;
    }

    /**
     * Build an ItemCommandResult to capture the provided failure.
     * @param t The failure that occurred.
     * @return The created ItemCommandResult.
     */
    private ItemCommandResult buildItemFailureResult(Throwable t) {
        BaseItemCommandResult retVal = new BaseItemCommandResult();
        retVal.setSuccessful(false);
        retVal.setFailureCause(t);
        return retVal;
    }

    /**
     * Build an ExecutionResult to capture the provided failure.
     * @param t The failure that occurred.
     * @return The created ExecutionResult.
     */
    private ExecutionResult buildExecutionFailureResult(Throwable t) {
        DefaultExecutionResult retVal = new DefaultExecutionResult();
        retVal.failed(t);

        return retVal;
    }

    /**
     * Perform start() activities.
     * @param context The contextual data for the Item stream.
     * @return An ExecutionResult detailing the results of the start execution.
     */
    protected ExecutionResult doStart(StreamContext context) {
        ExecutionResult retVal = null;
        try {
            retVal = start(context);
        } catch (BootOpsException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, e));
            retVal = buildExecutionFailureResult(e);
        } catch (RuntimeException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, new BootOpsException("Unable to finish Start processing successfully", e)));
            retVal = buildExecutionFailureResult(e);
        }
        return retVal;
    }

    /**
     * Perform complete() activities.
     * @param context The contextual data for the Item stream.
     * @return An ExecutionResult detailing the results of the complete execution.
     */
    protected ExecutionResult doComplete(StreamContext context) {
        ExecutionResult retVal = null;
        try {
            retVal = complete(context);
        } catch (BootOpsException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, e));
            retVal = buildExecutionFailureResult(e);
        } catch (RuntimeException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, new BootOpsException("Unable to finish Complete processing successfully", e)));
            retVal = buildExecutionFailureResult(e);
        }
        return retVal;
    }

    /**
     * Perform logic prior to executing the command on each item.
     * @param context The contextual data for the Item stream.
     * @return An ExecutionResult detailing the results of the start execution.
     */
    protected ExecutionResult start(StreamContext context) {
        //override to include start logic
        DefaultExecutionResult retVal = new DefaultExecutionResult();
        retVal.succeeded();
        return retVal;
    }

    /**
     * Perform command logic on each item.
     * @param item The item on which to perform the command.
     * @param parameters The parameters for use by the command when executing on the item.
     * @param context The contextual data for the Item.
     * @return An ItemCommandResult detailing the results of the command execution on the item.
     */
    protected ItemCommandResult execute(Item item, Map<String,List<String>> parameters, ItemContext context) {
        BaseItemCommandResult retVal = new BaseItemCommandResult();
        retVal.setSuccessful(true);
        return retVal;
    }

    /**
     * Perform logic after executing the command on each item.
     * @param context The contextual data for the Item stream.
     * @return An ExecutionResult detailing the results of the complete execution.
     */
    protected ExecutionResult complete(StreamContext context) {
        //override to include complete logic
        DefaultExecutionResult retVal = new DefaultExecutionResult();
        retVal.succeeded();
        return retVal;
    }

    @Getter
    @Setter
    public static class BaseItemCommandResult implements ItemCommandResult {
        //TODO move to separate Default class
        private boolean successful = false;
        private Throwable failureCause = null;
        private Item item = null;
    }

    /**
     * An implementation of ItemStreamCommandResult that tracks the execution result of the
     * start() and complete() processing within the command.
     */
    static class BaseItemStreamCommandResult extends ItemStreamCommandResult {
        private ExecutionResult startResult;
        private ExecutionResult completeResult;

        /**
         *
         * @param startResult The result of the start() processing.
         * @param itemCommandResults The result of the item processing.
         * @param completeResult The result of the complete() processing.
         */
        public BaseItemStreamCommandResult(ExecutionResult startResult, List<ItemCommandResult> itemCommandResults, ExecutionResult completeResult) {
            super();
            this.startResult = startResult;
            this.completeResult = completeResult;
            itemCommandResults.stream()
                    .forEach(this::addResult);
        }

        /**
         * Check to see if the result was successful.
         * @return True if the processing was successful, otherwise false.
         */
        @Override
        public boolean isSuccessful() {
            return startResult.isSuccessful() && completeResult.isSuccessful() && super.isSuccessful();
        }
    }

}
