/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.graylog.plugins.kafka.inputpre9.syslog;

import com.codahale.metrics.MetricRegistry;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.graylog.plugins.kafka.KafkaTransportPre9;
import org.graylog2.inputs.codecs.SyslogCodec;

import org.graylog2.plugin.LocalMetricRegistry;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.inputs.MessageInput;
import org.graylog2.plugin.inputs.annotations.ConfigClass;
import org.graylog2.plugin.inputs.annotations.FactoryClass;

import javax.inject.Inject;

public class SyslogKafkaInputPre9 extends MessageInput {
    private static final String NAME = "Syslog Kafka plugin pre 0.9";

    @AssistedInject
    public SyslogKafkaInputPre9(@Assisted Configuration configuration,
                                MetricRegistry metricRegistry,
                                KafkaTransportPre9.Factory transport,
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

    protected SyslogKafkaInputPre9(MetricRegistry metricRegistry,
                                   Configuration configuration,
                                   KafkaTransportPre9 kafkaTransport,
                                   SyslogCodec codec,
                                   LocalMetricRegistry localRegistry,
                                   MessageInput.Config config,
                                   MessageInput.Descriptor descriptor, ServerStatus serverStatus) {
        super(metricRegistry, configuration, kafkaTransport, localRegistry, codec, config, descriptor, serverStatus);
    }

    @FactoryClass
    public interface Factory extends MessageInput.Factory<SyslogKafkaInputPre9> {
        @Override
        SyslogKafkaInputPre9 create(Configuration configuration);

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
        public Config(KafkaTransportPre9.Factory transport, SyslogCodec.Factory codec) {
            super(transport.getConfig(), codec.getConfig());
        }
    }
}
