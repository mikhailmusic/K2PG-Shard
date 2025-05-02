package rut.miit.k2pgshard.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import rut.miit.k2pgshard.dto.UserAddDto;
import rut.miit.k2pgshard.dto.UserUpdateDto;
import rut.miit.k2pgshard.service.UserService;


@Component
public class UserEventProcessor {
    private static final Logger LOG = LogManager.getLogger(UserEventProcessor.class);
    private  UserService userService;
    private ObjectMapper mapper;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.mapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void processEvent(String message) {
        try {
            JsonNode jsonNode = mapper.readTree(message);
            String eventType = jsonNode.get("eventType").asText();

            switch (eventType) {
                case "UserRegistered" -> {
                    UserAddDto addDto = mapper.treeToValue(jsonNode, UserAddDto.class);
                    userService.registerUser(addDto);
                }
                case "UserProfileUpdated" -> {
                    UserUpdateDto updateDto = mapper.treeToValue(jsonNode, UserUpdateDto.class);
                    userService.updateUserInfo(updateDto);
                }
                default -> LOG.warn("Unknown event type received: {}", eventType);
            }
        } catch (JsonProcessingException e) {
            LOG.error("Error processing message: {}", message, e);
        } catch (Exception e ) {
            LOG.error("Unexpected error occurred while processing message: {}", message);
        }

    }
}
