package com.github.kirksc1.bootops.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.kirksc1.bootops.core.AttributeType;
import com.github.kirksc1.bootops.core.Item;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemDeserializerTest {

    @Test
    public void testConstructor_whenAttributeListNull_thenThrowIllegalArgumentException() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ItemDeserializer(null);
        });

        Assertions.assertEquals("The list of AttributeTypes provided was null", thrown.getMessage());
    }

    @Test
    public void testDeserialize_whenOnlyNameProvided_thenOnlyNamePopulated() throws IOException {
        String testYaml = "name: test";
        Item item = buildMapper(new ArrayList<>()).readValue(testYaml, Item.class);

        assertEquals("test", item.getName());
        assertEquals(0, item.getAttributes().size());
    }

    @Test
    public void testDeserialize_whenAttributeNotProvided_thenAttributeDataPopulatedAsJsonNode() throws JsonProcessingException {
        String testYaml = "name: test\nattributes:\n  my-attribute: \n    key: value";
        Item item = buildMapper(new ArrayList<>()).readValue(testYaml, Item.class);

        assertEquals("test", item.getName());

        assertEquals(1, item.getAttributes().size());
        JsonNode node = (JsonNode)item.getAttributes().get("my-attribute");
        assertEquals("value", ((ObjectNode)node).get("key").asText());
    }

    @Test
    public void testDeserialize_whenAttributeTypeProvidedButNoAttributeData_thenPopulateNoAttributes() throws JsonProcessingException {
        String testYaml = "name: test";
        Item item = buildMapper(Arrays.asList(buildAttributeType())).readValue(testYaml, Item.class);

        assertEquals("test", item.getName());
        assertEquals(0, item.getAttributes().size());
    }

    @Test
    public void testDeserialize_whenAttributeTypeProvidedAndData_thenPopulateAttributes() throws JsonProcessingException {
        String testYaml = "name: test\nattributes:\n  my-attribute: \n    key: value";
        Item item = buildMapper(Arrays.asList(buildAttributeType())).readValue(testYaml, Item.class);

        assertEquals("test", item.getName());
        assertEquals(1, item.getAttributes().size());

        TestAttribute attribute = (TestAttribute) item.getAttributes().get("my-attribute");
        assertEquals("value", attribute.getKey());
    }

    @Test
    public void testDeserialize_whenAttributeTypeProvidedAndDataInvalid_thenThrowMismatchedInputException() throws JsonProcessingException {
        String testYaml = "name: test\nattributes:\n  my-attribute: \n    fail";

        MismatchedInputException thrown = Assertions.assertThrows(MismatchedInputException.class, () -> {
            buildMapper(Arrays.asList(buildAttributeType())).readValue(testYaml, Item.class);
        });

        MatcherAssert.assertThat(thrown.getMessage(), Matchers.startsWith("Cannot construct instance of"));
    }

    private ObjectMapper buildMapper(List<AttributeType> attributeTypeList) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Item.class, new ItemDeserializer(attributeTypeList));
        mapper.registerModule(module);

        return mapper;
    }

    private AttributeType buildAttributeType() {
        return new AttributeType("my-attribute", TestAttribute.class);
    }

    static class TestAttribute {
        private String key;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}