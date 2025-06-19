package org.example.hamlol.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QApiKeyEntity is a Querydsl query type for ApiKeyEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QApiKeyEntity extends EntityPathBase<ApiKeyEntity> {

    private static final long serialVersionUID = -1064526456L;

    public static final QApiKeyEntity apiKeyEntity = new QApiKeyEntity("apiKeyEntity");

    public final StringPath apiKey = createString("apiKey");

    public QApiKeyEntity(String variable) {
        super(ApiKeyEntity.class, forVariable(variable));
    }

    public QApiKeyEntity(Path<? extends ApiKeyEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QApiKeyEntity(PathMetadata metadata) {
        super(ApiKeyEntity.class, metadata);
    }

}

