package com.zdanovich.core.entity.generator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class SequenceIdentifierGenerator implements IdentifierGenerator, Configurable {

    public static final String DEFAULT_ENTITY_SEQUENCE_SUFFIX = "_SEQUENCE";

    private String sequenceNextValString;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        final JdbcEnvironment jdbcEnvironment = serviceRegistry.getService(JdbcEnvironment.class);

        final Dialect dialect = jdbcEnvironment.getDialect();

        final String sequencePerEntitySuffix = ConfigurationHelper.getString(
                SequenceStyleGenerator.CONFIG_SEQUENCE_PER_ENTITY_SUFFIX, params, DEFAULT_ENTITY_SEQUENCE_SUFFIX);

        boolean preferSequencePerEntity = ConfigurationHelper.getBoolean(
                SequenceStyleGenerator.CONFIG_PREFER_SEQUENCE_PER_ENTITY, params,true);

        final String defaultSequenceName = preferSequencePerEntity
                ? params.getProperty(JPA_ENTITY_NAME).toUpperCase().concat(sequencePerEntitySuffix)
                : SequenceStyleGenerator.DEF_SEQUENCE_NAME;

        sequenceNextValString = dialect.getSequenceNextValString(
                ConfigurationHelper.getString(SequenceStyleGenerator.SEQUENCE_PARAM, params, defaultSequenceName));
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        if(object instanceof Identifiable) {
            Identifiable identifiable = (Identifiable) object;
            Serializable id = identifiable.getId();
            if(id != null) {
                return id;
            }
        }
        return  ((Number) session.createNativeQuery(sequenceNextValString).uniqueResult()).longValue();
    }
}
