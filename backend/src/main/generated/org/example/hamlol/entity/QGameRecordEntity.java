package org.example.hamlol.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QGameRecordEntity is a Querydsl query type for GameRecordEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGameRecordEntity extends EntityPathBase<GameRecordEntity> {

    private static final long serialVersionUID = -457121786L;

    public static final QGameRecordEntity gameRecordEntity = new QGameRecordEntity("gameRecordEntity");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QGameRecordEntity(String variable) {
        super(GameRecordEntity.class, forVariable(variable));
    }

    public QGameRecordEntity(Path<? extends GameRecordEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGameRecordEntity(PathMetadata metadata) {
        super(GameRecordEntity.class, metadata);
    }

}

