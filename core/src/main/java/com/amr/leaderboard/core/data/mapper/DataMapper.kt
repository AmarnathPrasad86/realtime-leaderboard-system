package com.amr.leaderboard.core.data.mapper

interface DataMapper<Domain, Entity> {
    fun toDomain(entity: Entity): Domain
    fun toEntity(domain: Domain): Entity
}
