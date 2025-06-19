package org.example.hamlol.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamEntity is a Querydsl query type for TeamEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTeamEntity extends EntityPathBase<TeamEntity> {

    private static final long serialVersionUID = 4636672L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamEntity teamEntity = new QTeamEntity("teamEntity");

    public final StringPath bans = createString("bans");

    public final NumberPath<Integer> baronKills = createNumber("baronKills", Integer.class);

    public final NumberPath<Integer> championKills = createNumber("championKills", Integer.class);

    public final NumberPath<Integer> dragonKills = createNumber("dragonKills", Integer.class);

    public final NumberPath<Integer> hordeKills = createNumber("hordeKills", Integer.class);

    public final NumberPath<Integer> inhibitorKills = createNumber("inhibitorKills", Integer.class);

    public final QMatchEntity match;

    public final StringPath matchId = createString("matchId");

    public final ListPath<PlayerEntity, QPlayerEntity> players = this.<PlayerEntity, QPlayerEntity>createList("players", PlayerEntity.class, QPlayerEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> riftHeraldKills = createNumber("riftHeraldKills", Integer.class);

    public final NumberPath<Long> teamId = createNumber("teamId", Long.class);

    public final StringPath teamType = createString("teamType");

    public final NumberPath<Integer> towerKills = createNumber("towerKills", Integer.class);

    public final BooleanPath win = createBoolean("win");

    public QTeamEntity(String variable) {
        this(TeamEntity.class, forVariable(variable), INITS);
    }

    public QTeamEntity(Path<? extends TeamEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamEntity(PathMetadata metadata, PathInits inits) {
        this(TeamEntity.class, metadata, inits);
    }

    public QTeamEntity(Class<? extends TeamEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.match = inits.isInitialized("match") ? new QMatchEntity(forProperty("match")) : null;
    }

}

