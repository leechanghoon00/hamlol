package org.example.hamlol.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChampEntity is a Querydsl query type for ChampEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChampEntity extends EntityPathBase<ChampEntity> {

    private static final long serialVersionUID = -1633468254L;

    public static final QChampEntity champEntity = new QChampEntity("champEntity");

    public final StringPath id = createString("id");

    public final StringPath key = createString("key");

    public final StringPath name = createString("name");

    public QChampEntity(String variable) {
        super(ChampEntity.class, forVariable(variable));
    }

    public QChampEntity(Path<? extends ChampEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChampEntity(PathMetadata metadata) {
        super(ChampEntity.class, metadata);
    }

}

