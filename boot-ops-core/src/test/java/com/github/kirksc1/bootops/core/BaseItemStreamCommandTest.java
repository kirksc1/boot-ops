package com.github.kirksc1.bootops.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

class BaseItemStreamCommandTest {

    private ItemManifestReader reader = mock(ItemManifestReader.class);
    private ItemManifestParser parser = mock(ItemManifestParser.class);
    private Predicate<Item> filter = mock(Predicate.class);
    private ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);
    private StreamContext context = mock(StreamContext.class);

    private InputStream inputStream = mock(InputStream.class);
    private Item item = mock(Item.class);
    private final URI uri = URI.create("https://www.test.com");

    @BeforeEach
    public void beforeEach() {
        Mockito.reset(reader, parser, filter, publisher, context);
        Mockito.reset(inputStream, item);
    }

    @Test
    public void testConstructor_whenNullName_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TestCommand(null, reader, parser, null, publisher);
        });

        Assertions.assertEquals("The name provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullEmpty_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TestCommand("", reader, parser, null, publisher);
        });

        Assertions.assertEquals("The name provided was empty", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullReader_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TestCommand("test", null, parser, null, publisher);
        });

        Assertions.assertEquals("The ItemManifestReader provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullParser_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TestCommand("test", reader, null, null, publisher);
        });

        Assertions.assertEquals("The ItemManifestParser provided was null", thrown.getMessage());
    }

    @Test
    public void testConstructor_whenNullPublisher_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new TestCommand("test", reader, parser, null, null);
        });

        Assertions.assertEquals("The ApplicationEventPublisher provided was null", thrown.getMessage());
    }

    @Test
    public void testExecute_whenOneSuccessfulItemAndNoFilters_thenReturnTrue() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertTrue(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(1)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(0)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(1, items.size());
        assertSame(item, items.get(0));

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenOneItemaAndReadThrowsIOExceptionAndNoFilters_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenThrow(new IOException("Forced Failure"));
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(0)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenOneItemAndReadThrowsBootOpsExceptionAndNoFilters_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenThrow(new BootOpsException("Forced Failure"));
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(0)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenOneItemAndReadReturnsNullAndNoFilters_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(null);
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(0)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenOneItemAndParseThrowsParseExceptionAndNoFilters_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenThrow(new ParseException("Forced Failure"));

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenOneItemAndParseThrowsBootOpsExceptionAndNoFilters_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenThrow(new BootOpsException("Forced Failure"));

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenOneItemAndParseReturnsNullAndNoFilters_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(null);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenOneFilteredOutItem_thenReturnTrue() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        Predicate<Item> predicate = new Predicate<Item>() {
            @Override
            public boolean test(Item it) {
                return it != item;
            }
        };

        TestCommand command = new TestCommand("test", reader, parser, Arrays.asList(predicate), publisher);

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertTrue(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(0)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenOneItemAndFilterThrowsBootOpsException_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        Predicate<Item> predicate = new Predicate<Item>() {
            @Override
            public boolean test(Item it) {
                throw new BootOpsException("Forced Failure");
            }
        };

        TestCommand command = new TestCommand("test", reader, parser, Arrays.asList(predicate), publisher);

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenStartThrowsBootOpsException_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);
        command.startBootOpsException = true;

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(0)).read(same(uri));
        verify(parser, times(0)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertFalse(command.completeCalled);
    }

    @Test
    public void testExecute_whenStartThrowsRuntimeException_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);
        command.startRuntimeException = true;

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(0)).read(same(uri));
        verify(parser, times(0)).parse(same(inputStream));
        verify(publisher, times(0)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(0, items.size());

        assertTrue(command.startCalled);
        assertFalse(command.completeCalled);
    }

    @Test
    public void testExecute_whenCompleteThrowsBootOpsException_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);
        command.completeBootOpsException = true;

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(1)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(1, items.size());
        assertSame(item, items.get(0));

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenCompleteThrowsRuntimeException_thenReturnFalse() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);
        command.completeRuntimeException = true;

        boolean success = command.execute(uriStream, new HashMap<>(), context).isSuccessful();

        assertFalse(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(1)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(1)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(1, items.size());
        assertSame(item, items.get(0));

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testExecute_whenParametersPassed_thenParametersPassThruToCommandLogic() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        Map<String,List<String>> params = new HashMap<>();
        params.put("1", Arrays.asList("one"));
        params.put("2", Arrays.asList("two"));
        boolean success = command.execute(uriStream, params, context).isSuccessful();

        assertTrue(success);
        verify(reader, times(1)).read(same(uri));
        verify(parser, times(1)).parse(same(inputStream));
        verify(publisher, times(1)).publishEvent(any(ItemCompletedEvent.class));
        verify(publisher, times(0)).publishEvent(any(BootOpsExceptionEvent.class));

        List<Item> items = command.getItems();
        assertEquals(1, items.size());
        assertSame(item, items.get(0));

        assertEquals(2, command.parameters.size());
        assertEquals("one", command.parameters.get("1").get(0));
        assertEquals("two", command.parameters.get("2").get(0));

        assertTrue(command.startCalled);
        assertTrue(command.completeCalled);
    }

    @Test
    public void testGetName_whenCreatedWithName_thenNameGettable() {
        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        assertEquals("test", command.getName());
    }

    @Test
    public void testExecute_whenStreamContextProvided_thenStreamContextPassedToStartItemAndCompleteProcessing() throws IOException, ParseException {
        URI uri = URI.create("http://www.test.com");
        Stream<URI> uriStream = Stream.of(uri);

        when(reader.read(same(uri))).thenReturn(inputStream);
        when(parser.parse(same(inputStream))).thenReturn(item);

        TestCommand command = new TestCommand("test", reader, parser, null, publisher);

        Map<String,List<String>> params = new HashMap<>();
        boolean success = command.execute(uriStream, params, context).isSuccessful();

        assertTrue(success);

        assertSame(context, command.startContext);
        assertSame(context, command.itemContext);
        assertSame(context, command.completeContext);
    }

    static class TestCommand extends BaseItemStreamCommand {
        private List<Item> items = new ArrayList<>();
         private HashMap<String,List<String>> parameters = new HashMap<>();
        private boolean startCalled = false;
        private boolean completeCalled = false;

        private boolean startBootOpsException=false;
        private boolean startRuntimeException=false;
        private boolean completeBootOpsException=false;
        private boolean completeRuntimeException=false;

        private StreamContext startContext = null;
        private StreamContext itemContext = null;
        private StreamContext completeContext = null;

        public TestCommand(String name, ItemManifestReader reader, ItemManifestParser parser, List<Predicate<Item>> filters, ApplicationEventPublisher publisher) {
            super(name, reader, parser, filters, publisher);
        }

        public void reset() {
            items.clear();
            parameters.clear();
            startCalled = false;
            completeCalled = false;
            startContext = null;
            itemContext = null;
            completeContext = null;
        }

        public List<Item> getItems() {
            return items;
        }

        @Override
        protected ExecutionResult start(StreamContext context) {
            System.out.println("start");
            startCalled = true;
            startContext = context;
            if (startBootOpsException) {
                throw new BootOpsException("Forced Failure");
            }
            if (startRuntimeException) {
                throw new RuntimeException("Forced Failure");
            }
            DefaultExecutionResult retVal = (DefaultExecutionResult)super.start(context);
            retVal.succeeded();
            return retVal;
        }

        @Override
        protected ItemCommandResult execute(Item item, Map<String,List<String>> parameters, StreamContext context) {
            System.out.println("execute");
            items.add(item);
            this.parameters.putAll(parameters);
            itemContext = context;
            BaseItemCommandResult retVal = (BaseItemCommandResult) super.execute(item, parameters, context);
            retVal.setSuccessful(true);
            return retVal;
        }

        @Override
        protected ExecutionResult complete(StreamContext context) {
            System.out.println("complete");
            completeCalled = true;
            completeContext = context;
            if (completeBootOpsException) {
                throw new BootOpsException("Forced Failure");
            }
            if (completeRuntimeException) {
                throw new RuntimeException("Forced Failure");
            }
            DefaultExecutionResult retVal = (DefaultExecutionResult)super.complete(context);
            retVal.succeeded();
            return retVal;
        }
    }
}