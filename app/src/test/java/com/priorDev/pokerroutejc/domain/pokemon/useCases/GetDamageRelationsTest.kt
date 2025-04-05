package com.priorDev.pokerroutejc.domain.pokemon.useCases

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import com.priorDev.pokerroutejc.data.TypeRepo
import com.priorDev.pokerroutejc.data.TypeRepoFake
import com.priorDev.pokerroutejc.domain.type.dragonTypeDetails
import com.priorDev.pokerroutejc.domain.type.electricTypeDetails
import com.priorDev.pokerroutejc.domain.type.fairyTypeDetails
import com.priorDev.pokerroutejc.domain.type.ghostTypeDetails
import com.priorDev.pokerroutejc.domain.type.waterTypeDetails
import com.priorDev.pokerroutejc.domain.types.models.DamageValue
import com.priorDev.pokerroutejc.domain.types.models.TypeData
import com.priorDev.pokerroutejc.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.collections.listOf as listOf1

class GetDamageRelationsTest {
    private lateinit var typeRepo: TypeRepo
    private val typeRepoFake = TypeRepoFake()

    @BeforeEach
    fun setUp() {
        typeRepo = typeRepoFake
    }

    @Test
    fun `GetDamageRelations, First type is not valid, Resource.Error`() = runTest {
        val result = GetDamageRelations(typeRepo)
            .invoke(
                listOf1(
                    TypeData(0, "Invalid"),
                    TypeData(electricTypeDetails.id, electricTypeDetails.name)
                )
            )

        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `GetDamageRelations, Second type is not valid, Resource.Error`() = runTest {
        val result = GetDamageRelations(typeRepo)
            .invoke(
                listOf1(
                    TypeData(electricTypeDetails.id, electricTypeDetails.name),
                    TypeData(0, "Invalid"),
                )
            )

        assertThat(result).isInstanceOf(Resource.Error::class.java)
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    fun `GetDamageRelations, 2 valid types, Resource.Success with correct damage values`(
        types: List<TypeData>, expectedResult: List<DamageValue>
    ) = runTest {
        val result = GetDamageRelations(typeRepo).invoke(types)

        assertThat(result).isInstanceOf(Resource.Success::class.java)

        val successResult = result as Resource.Success
        val damageRelations = successResult.data

        println("List size assertion")
        assertThat(damageRelations?.size).isEqualTo(expectedResult.size)

        expectedResult.forEach { expectedType ->
            println("Type assertion expectedType $expectedType")
            assertThat(damageRelations?.firstOrNull { it == expectedType }).isNotNull()
        }
    }

    companion object {
        @JvmStatic
        fun provideTestCases(): Stream<Arguments> = Stream.of(
            // Totodile
            Arguments.of(
                listOf1(
                    TypeData(waterTypeDetails.id, waterTypeDetails.name)
                ),
                waterTypeDetails.damageRelations.takesDamageFrom
            ),
            // Lanturn
            Arguments.of(
                listOf1(
                    TypeData(waterTypeDetails.id, waterTypeDetails.name),
                    TypeData(electricTypeDetails.id, electricTypeDetails.name)
                ),
                listOf1(
                    DamageValue(.25f, TypeData(9, "steel")),
                    DamageValue(.5f, TypeData(11, "water")),
                    DamageValue(.5f, TypeData(15, "ice")),
                    DamageValue(.5f, TypeData(10, "fire")),
                    DamageValue(2f, TypeData(12, "grass")),
                    DamageValue(2f, TypeData(5, "ground")),
                    DamageValue(0.5f, TypeData(3, "flying"))
                )
            ),
            // Dragapult
            Arguments.of(
                listOf1(
                    TypeData(dragonTypeDetails.id, dragonTypeDetails.name),
                    TypeData(ghostTypeDetails.id, ghostTypeDetails.name)
                ),
                listOf1(
                    DamageValue(0.5f, TypeData(11, "water")),
                    DamageValue(0.5f, TypeData(7, "bug")),
                    DamageValue(2f, TypeData(16, "dragon")),
                    DamageValue(0.5f, TypeData(13, "electric")),
                    DamageValue(2f, TypeData(8, "ghost")),
                    DamageValue(0.5f, TypeData(10, "fire")),
                    DamageValue(2f, TypeData(15, "ice")),
                    DamageValue(2f, TypeData(18, "fairy")),
                    DamageValue(0f, TypeData(2, "fighting")),
                    DamageValue(0f, TypeData(1, "normal")),
                    DamageValue(.5f, TypeData(12, "grass")),
                    DamageValue(2f, TypeData(17, "dark")),
                    DamageValue(0.5f, TypeData(4, "poison"))
                )
            ),
            // Mimikyu
            Arguments.of(
                listOf1(
                    TypeData(fairyTypeDetails.id, fairyTypeDetails.name),
                    TypeData(ghostTypeDetails.id, ghostTypeDetails.name)
                ),
                listOf1(
                    DamageValue(2f, TypeData(9, "steel")),
                    DamageValue(0.25f, TypeData(7, "bug")),
                    DamageValue(0f, TypeData(16, "dragon")),
                    DamageValue(2f, TypeData(8, "ghost")),
                    DamageValue(0f, TypeData(1, "normal")),
                    DamageValue(0f, TypeData(2, "fighting"))
                )
            )
        )
    }
}
