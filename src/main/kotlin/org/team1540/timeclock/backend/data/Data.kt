package org.team1540.timeclock.backend.data

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository


// app data storage
@Document(collection = "users")
data class User(@Id val id: String = "", val name: String = "", val email: String = "", val clockEvents: Collection<ClockEvent> = emptyList()) {
    override fun toString() = "$id ($name)"
}

data class ClockEvent(val timestamp: Long = 0L, val clockingIn: Boolean = false)

interface UserRepository : MongoRepository<User, String> {
    fun findByName(name: String): User?
}

// authentication/credential storage
@Document(collection = "credentials")
data class Credential(val accessLevel: AccessLevel = AccessLevel.NONE, @Id val username: String = "", val password: String = "")

interface CredentialRepository : MongoRepository<Credential, String>

enum class AccessLevel(vararg val roleNames: String) {
    NONE(), TIMESHEET("TIMESHEET"), TIMECLOCK("TIMECLOCK", "TIMESHEET"), ADMIN("ADMIN", "TIMECLOCK", "TIMESHEET")
}

// cache
@Document(collection = "timecache")
data class TimeCacheEntry(val id: String = "", val time: Long = -1)

interface TimeCacheEntryRepository : MongoRepository<TimeCacheEntry, String>
