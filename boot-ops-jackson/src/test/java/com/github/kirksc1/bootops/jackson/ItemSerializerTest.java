package com.github.kirksc1.bootops.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.kirksc1.bootops.core.Item;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemSerializerTest {

    @Test
    public void testSerialize_whenUriPresent_thenDoNotSerializeUri() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Item.class, new ItemSerializer());
        mapper.registerModule(module);

        Item item = new Item();
        item.setName("test");
        item.setUri(URI.create("http://www.test.com"));

        item.getAttributes().put("test", "value");

        assertEquals(false, mapper.writeValueAsString(item).contains("http://www.test.com"));
    }

    @Test
    public void testSerialize_whenUriNotPresent_thenDoNotSerializeUri() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Item.class, new ItemSerializer());
        mapper.registerModule(module);

        Item item = new Item();
        item.setName("test");

        item.getAttributes().put("test", "value");

        assertEquals(false, mapper.writeValueAsString(item).contains("uri"));
    }

    @Test
    public void testSerialize_whenCalled_thenSerializeAllDataOtherThanUri() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("custom",
                Version.unknownVersion());
        module.addSerializer(Item.class, new ItemSerializer());
        mapper.registerModule(module);

        Item item = new Item();
        item.setName("test");
        item.setUri(URI.create("http://www.test.com"));

        item.getAttributes().put("test", "value");

        String serialized = mapper.writeValueAsString(item);

        System.out.println(serialized);
        String expected = "{\"name\":\"test\",\"attributes\":{\"test\":\"value\"}}";
        assertEquals(expected, serialized);
    }

}