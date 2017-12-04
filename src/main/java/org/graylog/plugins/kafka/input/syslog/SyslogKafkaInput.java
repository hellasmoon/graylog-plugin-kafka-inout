package org.graylog.plugins.kafka.input.syslog;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog.plugins.kafka.KafkaTransport;
import org.graylog2.inputs.codecs.SyslogCodec;
import org.graylog2.plugin.LocalMetricRegistry;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.inputs.MessageInput;
import org.graylog2.plugin.inputs.annotations.ConfigClass;
import org.graylog2.plugin.inputs.annotations.FactoryClass;

import javax.inject.Inject;

public class SyslogKafkaInput extends MessageInput {
    private static final String NAME = "Syslog Kafka plugin";

    @AssistedInject
    public SyslogKafkaInput(@Assisted Configuration configuration,
                            MetricRegistry metricRegistry,
                            KafkaTransport.Factory transport,
                            SyslogCodec.Factory codec,
                            LocalMetricRegistry localRegistry,
                            Config config,
                            Descriptor descriptor, ServerStatus serverStatus) {
        this(metricRegistry,
                configuration,
                transport.create(configuration),
                codec.create(configuration),
                localRegistry,
                config,
                descriptor, serverStatus);
    }

    protected SyslogKafkaInput(MetricRegistry metricRegistry,
                               Configuration configuration,
                               KafkaTransport kafkaTransport,
                               SyslogCodec codec,
                               LocalMetricRegistry localRegistry,
                               MessageInput.Config config,
                               MessageInput.Descriptor descriptor, ServerStatus serverStatus) {
        super(metricRegistry, configuration, kafkaTransport, localRegistry, codec, config, descriptor, serverStatus);
    }

    @FactoryClass
    public interface Factory extends MessageInput.Factory<SyslogKafkaInput> {
        @Override
        SyslogKafkaInput create(Configuration configuration);

        @Override
        Config getConfig();

        @Override
        Descriptor getDescriptor();
    }

    public static class Descriptor extends MessageInput.Descriptor {
        @Inject
        public Descriptor() {
            super(NAME, false, "");
        }
    }

    @ConfigClass
    public static class Config extends MessageInput.Config {
        @Inject
        public Config(KafkaTransport.Factory transport, SyslogCodec.Factory codec) {
            super(transport.getConfig(), codec.getConfig());
        }
    }
}
