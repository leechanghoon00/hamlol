package org.example.hamlol.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlayerEntity is a Querydsl query type for PlayerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlayerEntity extends EntityPathBase<PlayerEntity> {

    private static final long serialVersionUID = 1880575364L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlayerEntity playerEntity = new QPlayerEntity("playerEntity");

    public final NumberPath<Integer> assists = createNumber("assists", Integer.class);

    public final NumberPath<Integer> championLevel = createNumber("championLevel", Integer.class);

    public final StringPath championName = createString("championName");

    public final NumberPath<Integer> damageDealtToBuildings = createNumber("damageDealtToBuildings", Integer.class);

    public final NumberPath<Integer> deaths = createNumber("deaths", Integer.class);

    public final NumberPath<Integer> goldEarned = createNumber("goldEarned", Integer.class);

    public final StringPath individualPosition = createString("individualPosition");

    public final StringPath item0 = createString("item0");

    public final StringPath item1 = createString("item1");

    public final StringPath item2 = createString("item2");

    public final StringPath item3 = createString("item3");

    public final StringPath item4 = createString("item4");

    public final StringPath item5 = createString("item5");

    public final StringPath item6 = createString("item6");

    public final NumberPath<Float> kda = createNumber("kda", Float.class);

    public final NumberPath<Integer> kills = createNumber("kills", Integer.class);

    public final QMatchEntity match;

    public final StringPath matchId = createString("matchId");

    public final NumberPath<Long> playerId = createNumber("playerId", Long.class);

    public final StringPath primaryStyle1 = createString("primaryStyle1");

    public final StringPath primaryStyle2 = createString("primaryStyle2");

    public final StringPath primaryStyle3 = createString("primaryStyle3");

    public final StringPath primaryStyle4 = createString("primaryStyle4");

    public final StringPath riotIdGameName = createString("riotIdGameName");

    public final StringPath riotIdTagline = createString("riotIdTagline");

    public final StringPath subStyle1 = createString("subStyle1");

    public final StringPath subStyle2 = createString("subStyle2");

    public final StringPath summoner1Id = createString("summoner1Id");

    public final StringPath summoner2Id = createString("summoner2Id");

    public final QTeamEntity team;

    public final StringPath teamPosition = createString("teamPosition");

    public final StringPath teamType = createString("teamType");

    public final NumberPath<Integer> totalDamageDealtToChampions = createNumber("totalDamageDealtToChampions", Integer.class);

    public final NumberPath<Integer> totalDamageTaken = createNumber("totalDamageTaken", Integer.class);

    public final NumberPath<Integer> totalHealsOnTeammates = createNumber("totalHealsOnTeammates", Integer.class);

    public final NumberPath<Integer> totalMinionsKilled = createNumber("totalMinionsKilled", Integer.class);

    public final NumberPath<Integer> visionScore = createNumber("visionScore", Integer.class);

    public final NumberPath<Integer> visionWardsBoughtInGame = createNumber("visionWardsBoughtInGame", Integer.class);

    public final NumberPath<Integer> wardsKilled = createNumber("wardsKilled", Integer.class);

    public final NumberPath<Integer> wardsPlaced = createNumber("wardsPlaced", Integer.class);

    public final BooleanPath win = createBoolean("win");

    public QPlayerEntity(String variable) {
        this(PlayerEntity.class, forVariable(variable), INITS);
    }

    public QPlayerEntity(Path<? extends PlayerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlayerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlayerEntity(PathMetadata metadata, PathInits inits) {
        this(PlayerEntity.class, metadata, inits);
    }

    public QPlayerEntity(Class<? extends PlayerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.match = inits.isInitialized("match") ? new QMatchEntity(forProperty("match")) : null;
        this.team = inits.isInitialized("team") ? new QTeamEntity(forProperty("team"), inits.get("team")) : null;
    }

}

