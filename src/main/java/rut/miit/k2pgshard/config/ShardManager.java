package rut.miit.k2pgshard.config;

import com.github.f4b6a3.uuid.alt.GUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ShardManager {
    private final static int SHARD_COUNT = 3;
    private static final Logger logger = LoggerFactory.getLogger(ShardManager.class);
    private final List<DataSource> dataSources;

    @Autowired
    public ShardManager(List<DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public DataSource getShard(UUID userId) {
        int shardIndex = Math.abs(userId.hashCode()) % SHARD_COUNT;
        logger.info("Calculated shard index {} for user ID: {}", shardIndex, userId);
        return dataSources.get(shardIndex);
    }

    public UUID generateUUIDv7() {
        return GUID.v7().toUUID();
    }
}
