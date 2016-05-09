package com.podbox.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.GroupProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PreDestroy;
import java.io.IOException;

import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

@Configuration
class HazelcastConfig {

    HazelcastInstance hazelcast;

    @Bean
    @Profile("local")
    Config localConfig() {
        return new Config().setNetworkConfig(new NetworkConfig()

                .setPortAutoIncrement(true).setReuseAddress(true)

                .setInterfaces(new InterfacesConfig().setEnabled(true)
                        .addInterface("127.0.0.1"))

                .setJoin(new JoinConfig()
                        .setMulticastConfig(new MulticastConfig()
                                .setEnabled(false))
                        .setTcpIpConfig(new TcpIpConfig()
                                .setMembers(singletonList("127.0.0.1"))
                                .setEnabled(true))));
    }

    @Bean
    @Profile("docker")
    Config dockerConfig() {
        return new Config().setNetworkConfig(new NetworkConfig()

                .setPortAutoIncrement(false).setReuseAddress(false)

                .setJoin(new JoinConfig()
                        .setMulticastConfig(new MulticastConfig()
                                .setEnabled(true))
                        .setTcpIpConfig(new TcpIpConfig()
                                .setEnabled(false))));
    }

    @Bean
    HazelcastInstance hazelcast(final Config config) throws IOException {
        config.setProperty(GroupProperty.LOGGING_TYPE, "slf4j");

        config.setProperty(GroupProperty.ICMP_ENABLED, "true");

        config.setProperty(GroupProperty.PHONE_HOME_ENABLED, "false");
        config.setProperty(GroupProperty.MEMCACHE_ENABLED, "false");
        config.setProperty(GroupProperty.REST_ENABLED, "false");

        return hazelcast = Hazelcast.newHazelcastInstance(config);
    }

    @PreDestroy
    void shutdown() {
        ofNullable(hazelcast).ifPresent(HazelcastInstance::shutdown);
    }
}
