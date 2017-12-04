package org.graylog.plugins.kafka;

import org.graylog.plugins.kafka.input.gelf.GELFKafkaInput;
import org.graylog.plugins.kafka.input.raw.RawKafkaInput;
import org.graylog.plugins.kafka.input.syslog.SyslogKafkaInput;
import org.graylog.plugins.kafka.inputpre9.gelf.GELFKafkaInputPre9;
import org.graylog.plugins.kafka.inputpre9.raw.RawKafkaInputPre9;
import org.graylog.plugins.kafka.inputpre9.syslog.SyslogKafkaInputPre9;
import org.graylog2.plugin.PluginModule;

import java.util.Set;

/**
 * Extend the PluginModule abstract class here to add you plugin to the system.
 */
public class KafkaModule extends PluginModule {
    /**
     * Returns all configuration beans required by this plugin.
     * <p>
     * Implementing this method is optional. The default method returns an empty {@link Set}.
     */

    @Override
    protected void configure() {
        addTransport("Kafka in-out plugin", KafkaTransport.class);
        addTransport("Kafka in pre 09", KafkaTransportPre9.class);
        addMessageInput(GELFKafkaInput.class);
        addMessageInput(GELFKafkaInputPre9.class);
        addMessageInput(RawKafkaInput.class);
        addMessageInput(RawKafkaInputPre9.class);
        addMessageInput(SyslogKafkaInput.class);
        addMessageInput(SyslogKafkaInputPre9.class);
        addMessageOutput(KafkaOutput.class,KafkaOutput.Factory.class);
    }
}
