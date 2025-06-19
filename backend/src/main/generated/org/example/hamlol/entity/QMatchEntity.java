package org.example.hamlol.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchEntity is a Querydsl query type for MatchEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchEntity extends EntityPathBase<MatchEntity> {

    private static final long serialVersionUID = 212256424L;

    public static final QMatchEntity matchEntity = new QMatchEntity("matchEntity");

    public final NumberPath<Long> gameCreation = createNumber("gameCreation", Long.class);

    public final NumberPath<Long> gameDuration = createNumber("gameDuration", Long.class);

    public final StringPath gamemode = createString("gamemode");

    public final StringPath matchId = createString("matchId");

    public final ListPath<PlayerEntity, QPlayerEntity> players = this.<PlayerEntity, QPlayerEntity>createList("players", PlayerEntity.class, QPlayerEntity.class, PathInits.DIRECT2);

    public final ListPath<TeamEntity, QTeamEntity> teams = this.<TeamEntity, QTeamEntity>createList("teams", TeamEntity.class, QTeamEntity.class, PathInits.DIRECT2);

    public QMatchEntity(String variable) {
        super(MatchEntity.class, forVariable(variable));
    }

    public QMatchEntity(Path<? extends MatchEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMatchEntity(PathMetadata metadata) {
        super(MatchEntity.class, metadata);
    }

}

