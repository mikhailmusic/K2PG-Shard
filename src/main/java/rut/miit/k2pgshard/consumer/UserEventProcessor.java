package rut.miit.k2pgshard.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import rut.miit.k2pgshard.dto.UserDto;
import rut.miit.k2pgshard.dto.UserUpdateDto;
import rut.miit.k2pgshard.service.UserService;


@Component
public class UserEventProcessor {
    private static final Logger LOG = LogManager.getLogger(UserEventProcessor.class);
    private UserService userService;
    private ObjectMapper mapper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Bean
    public KStream<String, String> userEventsStream(StreamsBuilder builder,
                                                    @Value("${kafka.topic.name}") String topicName) {
        KStream<String, String> stream = builder.stream(topicName);
        stream.peek((key, value) -> LOG.info("Received Kafka message: {}", value))
                .foreach((key, value) -> processEvent(value));
        return stream;
    }

    private void processEvent(String message) {
        try {
            JsonNode jsonNode = mapper.readTree(message);
            String eventType = jsonNode.get("eventType").asText();

            switch (eventType) {
                case "UserRegistered" -> {
                    LOG.info("Handling UserRegistered event");
                    UserDto addDto = mapper.treeToValue(jsonNode, UserDto.class);
                    userService.registerUser(addDto);
                    LOG.info("User registered successfully: {}", addDto.id());
                }
                case "UserProfileUpdated" -> {
                    LOG.info("Handling UserProfileUpdated event");
                    UserUpdateDto updateDto = mapper.treeToValue(jsonNode, UserUpdateDto.class);
                    userService.updateUserInfo(updateDto);
                    LOG.info("User profile updated successfully: {}", updateDto.id());
                }
                default -> LOG.warn("Unknown event type received: {}", eventType);
            }
        } catch (JsonProcessingException e) {
            LOG.error("Error parsing JSON from message: {}", message);
        } catch (Exception e) {
            LOG.error("Unexpected error occurred while processing message: {}", message);
        }
    }
}
