package io.rybalkinsd.kotlinbootcamp

class RawProfile(val rawData: String) {
    fun toProfile(id: Long): Profile {
        val propList = rawData.split('\n').toMutableList()
        val result: Profile
        var source: String? = propList.find { it.split('=')[0] == "source" }
        if (source == null) {
            source = "facebook"
        } else {
            source = source.split('=')[1].substringBefore(',')
        }
        propList.remove(source)
        result = when (source) {
            "facebook" -> FacebookProfile(id)
            "vk" -> VkProfile(id)
            "linkedin" -> LinkedinProfile(id)
            else -> FacebookProfile(1)
        }
        propList.forEach {
            val elem = it.split('=')
            when (elem[0]) {
                "firstName" -> result.firstName = elem[1].substringBefore(',')
                "lastName" -> result.lastName = elem[1].substringBefore(',')
                "age" -> result.age = elem[1].substringBefore(',').toIntOrNull()
            }
        }
        return result
    }
}

/**
 * Here are Raw profiles to analyse
 */
val rawProfiles = listOf(
        RawProfile("""
            firstName=alice,
            age=16,
            source=facebook
            """.trimIndent()
        ),
        RawProfile("""
            firstName=Dent,
            lastName=kent,
            age=88,
            source=linkedin
            """.trimIndent()
        ),
        RawProfile("""
            firstName=alla,
            lastName=OloOlooLoasla,
            source=vk
            """.trimIndent()
        ),
        RawProfile("""
            firstName=bella,
            age=36,
            source=vk
            """.trimIndent()
        ),
        RawProfile("""
            firstName=angel,
            age=I will not tell you =),
            source=facebook
            """.trimIndent()
        ),

        RawProfile(
                """
            lastName=carol,
            source=vk
            age=49,
            """.trimIndent()
        ),
        RawProfile("""
            firstName=bob,
            lastName=John,
            age=47,
            source=linkedin
            """.trimIndent()
        ),
        RawProfile("""
            lastName=kent,
            firstName=dent,
            age=88,
            source=facebook
            """.trimIndent()
        ),
        RawProfile("""
            firstName=alice,
            age=16,
            source=vk
            """.trimIndent()
        ),
        RawProfile("""
            age=16,
            firstName=alice,
            source=linkedin
            """.trimIndent()
        )
)

enum class DataSource {
    FACEBOOK,
    VK,
    LINKEDIN
}

sealed class Profile(
    var id: Long,
    var firstName: String? = null,
    var lastName: String? = null,
    var age: Int? = null,
    var dataSource: DataSource
)

/**
 * Task #1
 * Declare classes for all data sources
 */
class FacebookProfile(id: Long) : Profile(dataSource = DataSource.FACEBOOK, id = id)
class VkProfile(id: Long) : Profile(dataSource = DataSource.VK, id = id)
class LinkedinProfile(id: Long) : Profile(dataSource = DataSource.LINKEDIN, id = id)

/**
 * Task #2
 * Find the average age for each datasource (for profiles that has age)
 *
 * TODO
 */
val allProfiles = rawProfiles.mapIndexed { index, x -> x.toProfile(index.toLong()) }

val avgAge: Map<DataSource, Double> = DataSource.values().associate {
    var count: Long = 0
    val sum: Long = allProfiles.fold(0L) { acc: Long, profile ->
        if ((profile.dataSource == it) && (profile.age != null)) {
            count++
            acc + profile.age!!
        } else
            acc
    }
    val avg: Double = sum.toDouble() / count
    it to if (count > 0) avg else 0.0
}
/**
 * Task #3
 * Group all user ids together with all profiles of this user.
 * We can assume users equality by : firstName & lastName & age
 *
 * TODO
 */
infix fun Profile.equals(x: Profile): Boolean = (x.firstName == firstName) && (x.lastName == lastName) && (x.age == age)

val groupedProfiles: Map<Long, List<Profile>> = allProfiles.associate {
    it.id to allProfiles.filter { x -> x equals it }
}