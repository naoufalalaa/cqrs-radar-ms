package ma.enset.immatriculationservice.query.cofiguration;

import org.axonframework.common.jdbc.ConnectionProvider;
import org.axonframework.queryhandling.QueryBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryConfig {
    @Bean
    public QueryBus queryBus(ConnectionProvider connectionProvider) {
        return new DistributedQueryBus(connectionProvider, LoadFactor.HASH_CODE);
    }
}
