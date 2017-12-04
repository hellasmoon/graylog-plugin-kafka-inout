package org.graylog.plugins.kafka.input.gelf;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog.plugins.kafka.KafkaTransport;
import org.graylog2.inputs.codecs.GelfCodec;
import org.graylog2.plugin.LocalMetricRegistry;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.inputs.MessageInput;
import org.graylog2.plugin.inputs.annotations.ConfigClass;
import org.graylog2.plugin.inputs.annotations.FactoryClass;

import javax.inject.Inject;

/**
 * Created by fbalicchia on 21/10/16.
 */
public class GELFKafkaInput extends MessageInput {
    private static final String NAME = "GELF Kafka plugin";

    @AssistedInject
    public GELFKafkaInput(@Assisted Configuration configuration,
                          MetricRegistry metricRegistry,
                          KafkaTransport.Factory transport,
                          GelfCodec.Factory codec,
                          LocalMetricRegistry localRegistry,
                          GELFKafkaInput.Config config,
                          GELFKafkaInput.Descriptor descriptor, ServerStatus serverStatus) {
        this(metricRegistry,
                configuration,
                transport.create(configuration),
                codec.create(configuration),
                localRegistry,
                config,
                descriptor, serverStatus);
    }

    protected GELFKafkaInput(MetricRegistry metricRegistry,
                             Configuration configuration,
                             KafkaTransport kafkaTransport,
                             GelfCodec codec,
                             LocalMetricRegistry localRegistry,
                             MessageInput.Config config,
                             MessageInput.Descriptor descriptor, ServerStatus serverStatus) {
        super(metricRegistry, configuration, kafkaTransport, localRegistry, codec, config, descriptor, serverStatus);
    }


    @Override
    public Boolean isGlobal() {
        return super.isGlobal();
    }

    @FactoryClass
    public interface Factory extends MessageInput.Factory<GELFKafkaInput> {
        @Override
        GELFKafkaInput create(Configuration configuration);

        @Override
        GELFKafkaInput.Config getConfig();

        @Override
        GELFKafkaInput.Descriptor getDescriptor();
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
        public Config(KafkaTransport.Factory transport, GelfCodec.Factory codec) {
            super(transport.getConfig(), codec.getConfig());
        }
    }
}
