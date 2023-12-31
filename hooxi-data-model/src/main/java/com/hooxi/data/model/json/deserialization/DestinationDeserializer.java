package com.hooxi.data.model.json.deserialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.hooxi.data.model.dest.Destination;
import com.hooxi.data.model.dest.DestinationType;
import com.hooxi.data.model.dest.WebhookDestination;
import java.io.IOException;
import java.util.HashMap;

public class DestinationDeserializer extends StdDeserializer<Destination> {

  public DestinationDeserializer() {
    this(null);
  }

  public DestinationDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Destination deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);
    JsonNode destinationTypeNode = node.get("destinationType");
    ObjectMapper mapper = new ObjectMapper();
    if (destinationTypeNode == null) {
      throw new JsonMappingException(p, "destinationType should not be null");
    }
    DestinationType destinationType = DestinationType.valueOf(destinationTypeNode.asText());
    switch (destinationType) {
      case WEBHOOK:
        WebhookDestination wd = new WebhookDestination();
        wd.setDestinationType(DestinationType.WEBHOOK);
        wd.setEndpoint(node.get("endpoint").asText());
        wd.setMetadata(
            mapper.readValue(
                node.get("metadata").toString(), new TypeReference<HashMap<String, String>>() {}));
        return wd;
      default:
        throw new JsonMappingException(p, "failed to parse Destination for " + p.getText());
    }
  }
}
