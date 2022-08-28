package com.github.kirksc1.bootops.core;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.text.ParseException;
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
     * @return True if everything executed successfully, otherwise false.
     */
    @Override
    boolean execute(Stream<URI> uriStream, Map<String,String> parameters) {
        boolean retVal = false;

        boolean startSucceeded = doStart();

        if (startSucceeded) {
            boolean executeSucceeded = uriStream
                    .map(uri -> executeUri(uri, parameters))
                    .allMatch(succeeded -> succeeded);

            retVal = doComplete() && executeSucceeded && startSucceeded;
        }

        return retVal;
    }

    /**
     * Execute the command on the item referenced by the provided URI.
     * @param uri The URI referencing the item manifest.
     * @param parameters A collection of command configuraton parameters.
     * @return True if everything executed successfully, otherwise false.
     */
    private boolean executeUri(URI uri, Map<String,String> parameters) {
        boolean retVal = false;
        try {
            OutputStream outputStream = reader.read(uri);
            if (outputStream == null) {
                throw new IllegalStateException("ItemManifestReader.read(URI) returned a null value");
            }

            Item item = parser.parse(outputStream);
            if (item == null) {
                throw new IllegalStateException("ItemManifestParser.parse(OutputStream) returned a null value");
            }

            if (this.filter.test(item)) {
                execute(item, parameters);
                publisher.publishEvent(new ItemCompletedEvent(this, item));
            }
            retVal = true;
        } catch (IOException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, new BootOpsException("Unable to read manifest data at URI=" + uri, e)));
        } catch (ParseException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, new BootOpsException("Unable to parse the manifest data into an Item", e)));
        } catch (BootOpsException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, e));
        } catch (RuntimeException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, new BootOpsException("Unable to process the Item at URI=" + uri, e)));
        }
        return retVal;
    }

    /**
     * Perform start() activities.
     * @return True if everything executed successfully, otherwise false.
     */
    protected boolean doStart() {
        boolean retVal = false;
        try {
            start();
            retVal = true;
        } catch (BootOpsException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, e));
        } catch (RuntimeException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, new BootOpsException("Unable to finish Start processing successfully", e)));
        }
        return retVal;
    }

    /**
     * Perform complete() activities.
     * @return True if everything executed successfully, otherwise false.
     */
    protected boolean doComplete() {
        boolean retVal = false;
        try {
            complete();
            retVal = true;
        } catch (BootOpsException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, e));
        } catch (RuntimeException e) {
            publisher.publishEvent(new BootOpsExceptionEvent(this, new BootOpsException("Unable to finish Complete processing successfully", e)));
        }
        return retVal;
    }

    /**
     * Perform logic prior to executing the command on each item.
     */
    protected void start() {
        //override to include start logic
    }

    /**
     * Perform command logic on each item.
     */
    protected void execute(Item item, Map<String,String> parameters) {
        //override to include execution logic
    }

    /**
     * Perform logic after executing the command on each item.
     */
    protected void complete() {
        //override to include complete logic
    }

}
