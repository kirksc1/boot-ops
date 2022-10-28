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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

class ConvergeItemStreamCommandTest {

    private ItemManifestReader reader = mock(ItemManifestReader.class);
    private ItemManifestParser parser = mock(ItemManifestParser.class);
    private Predicate<Item> filter = mock(Predicate.class);
    private ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);
    private StreamContext context = mock(StreamContext.class);
    private ItemInitializationExecutor initializationExecutor = mock(ItemInitializationExecutor.class);
    private ItemValidationExecutor validationExecutor = mock(ItemValidationExecutor.class);
    private ItemConvergeExecutor convergeExecutor = mock(ItemConvergeExecutor.class);

    private InputStream inputStream = mock(InputStream.class);
    private Item item = mock(Item.class);

    @BeforeEach
    public void beforeEach() {
        reset(reader, parser, filter, publisher, context);
        reset(initializationExecutor, validationExecutor, convergeExecutor);
        reset(inputStream, item);
    }

    @Test
    public void testConstructor_whenNullReader_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ConvergeItemStreamCommand(null, parser, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);
        });

        Assertions.assertEquals("The ItemManifestReader provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullParser_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ConvergeItemStreamCommand(reader, null, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);
        });

        Assertions.assertEquals("The ItemManifestParser provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullPublisher_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ConvergeItemStreamCommand(reader, parser, null, null, initializationExecutor, validationExecutor, convergeExecutor);
        });

        Assertions.assertEquals("The ApplicationEventPublisher provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullItemInitializationExecutor_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ConvergeItemStreamCommand(reader, parser, null, publisher, null, validationExecutor, convergeExecutor);
        });

        Assertions.assertEquals("The ItemInitializationExecutor provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullItemValidationExecutor_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, null, convergeExecutor);
        });

        Assertions.assertEquals("The ItemValidationExecutor provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullItemConvergeExecutor_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, validationExecutor, null);
        });

        Assertions.assertEquals("The ItemConvergeExecutor provided was null", thrown.getMessage());
    }

    @Test
    public void testExecute_whenNoExceptions_thenCallAllSixLifecycleMethods() throws IOException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        ItemValidationResult validationResult = mock(ItemValidationResult.class);
        when(validationResult.isValid()).thenReturn(true);
        when(validationExecutor.initiateValidation(same(item))).thenReturn(validationResult);

        ConvergeItemStreamCommand command = new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);

        ItemStreamCommandResult result = command.execute(uriStream, new HashMap<>(), context);

        boolean success = result.isSuccessful();

        assertTrue(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(1)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(0)).publishEvent(any(BootOpsExceptionEvent.class));

        verify(initializationExecutor, times(1)).initiateInitialization(same(item));
        verify(initializationExecutor, times(1)).completeInitialization(same(item));

        verify(validationExecutor, times(1)).initiateValidation(same(item));
        verify(validationExecutor, times(1)).completeValidation(same(item), any(ItemValidationResult.class));

        verify(convergeExecutor, times(1)).initiateConverge(same(item));
        verify(convergeExecutor, times(1)).completeConverge(same(item));
    }

    @Test
    public void testExecute_whenInitializationInitiatedExceptions_thenInitializationInitiated() throws IOException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);
        doThrow(new RuntimeException("Forced Failure!")).when(initializationExecutor).initiateInitialization(same(item));

        ConvergeItemStreamCommand command = new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);

        ItemStreamCommandResult result = command.execute(uriStream, new HashMap<>(), context);

        boolean success = result.isSuccessful();

        assertFalse(success);

        verify(initializationExecutor, times(1)).initiateInitialization(same(item));
        verify(initializationExecutor, times(0)).completeInitialization(same(item));

        verify(validationExecutor, times(0)).initiateValidation(same(item));
        verify(validationExecutor, times(0)).completeValidation(same(item), any(ItemValidationResult.class));

        verify(convergeExecutor, times(0)).initiateConverge(same(item));
        verify(convergeExecutor, times(0)).completeConverge(same(item));
    }

    @Test
    public void testExecute_whenInitializationCompletedExceptions_thenInitializationCompleted() throws IOException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);
        doThrow(new RuntimeException("Forced Failure!")).when(initializationExecutor).completeInitialization(same(item));

        ConvergeItemStreamCommand command = new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);

        ItemStreamCommandResult result = command.execute(uriStream, new HashMap<>(), context);

        boolean success = result.isSuccessful();

        assertFalse(success);

        verify(initializationExecutor, times(1)).initiateInitialization(same(item));
        verify(initializationExecutor, times(1)).completeInitialization(same(item));

        verify(validationExecutor, times(0)).initiateValidation(same(item));
        verify(validationExecutor, times(0)).completeValidation(same(item), any(ItemValidationResult.class));

        verify(convergeExecutor, times(0)).initiateConverge(same(item));
        verify(convergeExecutor, times(0)).completeConverge(same(item));
    }

    @Test
    public void testExecute_whenValidationInitiatedExceptions_thenPublishTheFirstThreeEventsOnly() throws IOException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);
        doThrow(new RuntimeException("Forced Failure!")).when(validationExecutor).initiateValidation(same(item));

        ConvergeItemStreamCommand command = new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);

        ItemStreamCommandResult result = command.execute(uriStream, new HashMap<>(), context);

        boolean success = result.isSuccessful();

        assertFalse(success);

        verify(initializationExecutor, times(1)).initiateInitialization(same(item));
        verify(initializationExecutor, times(1)).completeInitialization(same(item));

        verify(validationExecutor, times(1)).initiateValidation(same(item));
        verify(validationExecutor, times(0)).completeValidation(same(item), any(ItemValidationResult.class));

        verify(convergeExecutor, times(0)).initiateConverge(same(item));
        verify(convergeExecutor, times(0)).completeConverge(same(item));
    }

    @Test
    public void testExecute_whenValidationCompletedExceptions_thenPublishTheFourEvents() throws IOException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);
        doThrow(new RuntimeException("Forced Failure!")).when(validationExecutor).completeValidation(same(item), any());

        ConvergeItemStreamCommand command = new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);

        ItemStreamCommandResult result = command.execute(uriStream, new HashMap<>(), context);

        boolean success = result.isSuccessful();

        assertFalse(success);

        verify(initializationExecutor, times(1)).initiateInitialization(same(item));
        verify(initializationExecutor, times(1)).completeInitialization(same(item));

        verify(validationExecutor, times(1)).initiateValidation(same(item));
        verify(validationExecutor, times(1)).completeValidation(same(item), any());

        verify(convergeExecutor, times(0)).initiateConverge(same(item));
        verify(convergeExecutor, times(0)).completeConverge(same(item));
    }

    @Test
    public void testExecute_whenConvergeInitiatedExceptions_thenPublishTheFiveEvents() throws IOException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        ItemValidationResult validationResult = mock(ItemValidationResult.class);
        when(validationResult.isValid()).thenReturn(true);
        when(validationExecutor.initiateValidation(same(item))).thenReturn(validationResult);

        doThrow(new RuntimeException("Forced Failure!")).when(convergeExecutor).initiateConverge(same(item));

        ConvergeItemStreamCommand command = new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);

        ItemStreamCommandResult result = command.execute(uriStream, new HashMap<>(), context);

        boolean success = result.isSuccessful();

        assertFalse(success);

        verify(initializationExecutor, times(1)).initiateInitialization(same(item));
        verify(initializationExecutor, times(1)).completeInitialization(same(item));

        verify(validationExecutor, times(1)).initiateValidation(same(item));
        verify(validationExecutor, times(1)).completeValidation(same(item), any());

        verify(convergeExecutor, times(1)).initiateConverge(same(item));
        verify(convergeExecutor, times(0)).completeConverge(same(item));
    }

    @Test
    public void testExecute_whenConvergeCompletedExceptions_thenPublishTheSixEvents() throws IOException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        ItemValidationResult validationResult = mock(ItemValidationResult.class);
        when(validationResult.isValid()).thenReturn(true);
        when(validationExecutor.initiateValidation(same(item))).thenReturn(validationResult);

        doThrow(new RuntimeException("Forced Failure!")).when(convergeExecutor).completeConverge(same(item));

        ConvergeItemStreamCommand command = new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);

        ItemStreamCommandResult result = command.execute(uriStream, new HashMap<>(), context);

        boolean success = result.isSuccessful();

        assertFalse(success);

        verify(initializationExecutor, times(1)).initiateInitialization(same(item));
        verify(initializationExecutor, times(1)).completeInitialization(same(item));

        verify(validationExecutor, times(1)).initiateValidation(same(item));
        verify(validationExecutor, times(1)).completeValidation(same(item), any());

        verify(convergeExecutor, times(1)).initiateConverge(same(item));
        verify(convergeExecutor, times(1)).completeConverge(same(item));
    }

    @Test
    public void testExecute_whenValidationCompletedButInvalid_thenPublishTheFourEvents() throws IOException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        ItemValidationResult validationResult = mock(ItemValidationResult.class);
        when(validationResult.isValid()).thenReturn(false);
        when(validationExecutor.initiateValidation(same(item))).thenReturn(validationResult);

        ConvergeItemStreamCommand command = new ConvergeItemStreamCommand(reader, parser, null, publisher, initializationExecutor, validationExecutor, convergeExecutor);

        ItemStreamCommandResult result = command.execute(uriStream, new HashMap<>(), context);

        boolean success = result.isSuccessful();

        assertFalse(success);

        verify(initializationExecutor, times(1)).initiateInitialization(same(item));
        verify(initializationExecutor, times(1)).completeInitialization(same(item));

        verify(validationExecutor, times(1)).initiateValidation(same(item));
        verify(validationExecutor, times(1)).completeValidation(same(item), any());

        verify(convergeExecutor, times(0)).initiateConverge(same(item));
        verify(convergeExecutor, times(0)).completeConverge(same(item));
    }
}