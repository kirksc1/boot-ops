package com.github.kirksc1.bootops.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.github.kirksc1.bootops.core.AttributeType;
import com.github.kirksc1.bootops.core.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.*;

/**
 * ItemDeserializer is an implementation of the Jackson Deserializer that parses data into and Item instance.
 */
@Slf4j
public class ItemDeserializer extends StdDeserializer<Item> {

    private final Map<String, Class<? extends Object>> attributeTypes = new HashMap<>();

    /**
     * Construct a new instance that can deserialize an Item's named attributes into the proper class.
     * @param attributeTypes List of AttributeTypes, each of which defines an attribute name and the class
     *                       into which the attributes data should be deserialized.
     */
    public ItemDeserializer(List<AttributeType> attributeTypes) {
        super(Item.class);

        Assert.notNull(attributeTypes, "The list of AttributeTypes provided was null");

        attributeTypes.forEach(attributeType -> this.attributeTypes.put(attributeType.getName(), attributeType.getAttributeClass()));
    }

    /**
     * Deserialize the provided parser's content into an Item instance.
     * @param jp The JsonParser containing the content.
     * @param context The DeserializationContext for the Jackson process.
     * @return The Item instance constructed.
     * @throws IOException if the content could not be parsed into the Item.
     */
    @Override
    public Item deserialize(JsonParser jp, DeserializationContext context) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);

        String name = node.get("name").asText();

        Item retVal = new Item();
        retVal.setName(name);

        JsonNode attributesNode = node.get("attributes");
        if (attributesNode != null && attributesNode.isContainerNode()) {
            ContainerNode<? extends Object> attrNode = (ContainerNode) attributesNode;

            Iterator<String> nameIter = attrNode.fieldNames();
            while (nameIter.hasNext()) {
                final String attrName = nameIter.next();
                JsonNode attribNode = attrNode.get(attrName);

                Object value = attribNode;

                Class<?> attrClass = attributeTypes.get(attrName);
                if (attrClass != null) {
                    value = context.readTreeAsValue(attribNode, attrClass);
                }

                retVal.getAttributes().put(attrName, value);
            }
        }

        return retVal;
    }
}
