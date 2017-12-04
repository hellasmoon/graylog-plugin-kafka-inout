package org.graylog.plugins.kafka.input.raw;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog.plugins.kafka.KafkaTransport;
import org.graylog2.inputs.codecs.RawCodec;
import org.graylog2.plugin.LocalMetricRegistry;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.inputs.MessageInput;
import org.graylog2.plugin.inputs.annotations.ConfigClass;
import org.graylog2.plugin.inputs.annotations.FactoryClass;

import javax.inject.Inject;

public class RawKafkaInput extends MessageInput {
    private static final String NAME = "Raw/Plaintext Kafka Plugin";

    @AssistedInject
    public RawKafkaInput(@Assisted Configuration configuration,
                         MetricRegistry metricRegistry,
                         KafkaTransport.Factory transport,
                         RawCodec.Factory codec,
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

    protected RawKafkaInput(MetricRegistry metricRegistry,
                            Configuration configuration,
                            KafkaTransport kafkaTransport,
                            RawCodec codec,
                            LocalMetricRegistry localRegistry,
                            MessageInput.Config config,
                            MessageInput.Descriptor descriptor, ServerStatus serverStatus) {
        super(metricRegistry, configuration, kafkaTransport, localRegistry, codec, config, descriptor, serverStatus);
    }

    @FactoryClass
    public interface Factory extends MessageInput.Factory<RawKafkaInput> {
        @Override
        RawKafkaInput create(Configuration configuration);

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
        public Config(KafkaTransport.Factory transport, RawCodec.Factory codec) {
            super(transport.getConfig(), codec.getConfig());
        }
    }
}
