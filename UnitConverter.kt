package converter

fun main() {
  menu()
}

enum class UnitOfMeasurement {
  METERS {
    override fun type() = "distance"

    override fun aliases() = listOf("m", "meter", "meters")

    override fun proportionToBaseUnit() = 1.0

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  KILOMETERS {
    override fun type() = "distance"

    override fun aliases() = listOf("km", "kilometer", "kilometers")

    override fun proportionToBaseUnit() = 1_000.0

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  CENTIMETERS {
    override fun type() = "distance"

    override fun aliases() = listOf("cm", "centimeter", "centimeters")

    override fun proportionToBaseUnit() = 0.01

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  MILLIMETERS {
    override fun type() = "distance"

    override fun aliases() = listOf("mm", "millimeter", "millimeters")

    override fun proportionToBaseUnit() = 0.001

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  MILES {
    override fun type() = "distance"

    override fun aliases() = listOf("mi", "mile", "miles")

    override fun proportionToBaseUnit() = 1609.35

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  YARDS {
    override fun type() = "distance"

    override fun aliases() = listOf("yd", "yard", "yards")

    override fun proportionToBaseUnit() = 0.9144

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  FEET {
    override fun type() = "distance"

    override fun aliases() = listOf("ft", "foot", "feet")

    override fun proportionToBaseUnit() = 0.3048

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  INCHES {
    override fun type() = "distance"

    override fun aliases() = listOf("in", "inch", "inches")

    override fun proportionToBaseUnit() = 0.0254

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  GRAMS {
    override fun type() = "weight"

    override fun aliases() = listOf("g", "gram", "grams")

    override fun proportionToBaseUnit() = 1.0

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  KILOGRAMS {
    override fun type() = "weight"

    override fun aliases() = listOf("kg", "kilogram", "kilograms")

    override fun proportionToBaseUnit() = 1_000.0

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  MILLIGRAMS {
    override fun type() = "weight"

    override fun aliases() = listOf("mg", "milligram", "milligrams")

    override fun proportionToBaseUnit() = 0.001

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  POUNDS {
    override fun type() = "weight"

    override fun aliases() = listOf("lb", "pound", "pounds")

    override fun proportionToBaseUnit() = 453.592

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  OUNCES {
    override fun type() = "weight"

    override fun aliases() = listOf("oz", "ounce", "ounces")

    override fun proportionToBaseUnit() = 28.3495

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]
  },
  CELSIUS {
    override fun type() = "temperature"

    override fun aliases() = listOf("c", "dc", "celsius", "degree Celsius", "degrees Celsius")

    override fun proportionToBaseUnit() = 1.0

    override fun singular() = this.aliases()[3]

    override fun plural() = this.aliases()[4]

    override fun convert(measureNumber: Double, unitOfMeasurement: UnitOfMeasurement): Double {
      val measureNumberConverted =
          when (unitOfMeasurement) {
            FAHRENHEIT -> (measureNumber * 9 / 5) + 32
            KELVIN -> measureNumber + 273.15
            else -> measureNumber
          }
      return measureNumberConverted
    }
  },
  FAHRENHEIT {
    override fun type() = "temperature"

    override fun aliases() =
        listOf("f", "df", "fahrenheit", "degree Fahrenheit", "degrees Fahrenheit")

    override fun proportionToBaseUnit() = 1.0

    override fun singular() = this.aliases()[3]

    override fun plural() = this.aliases()[4]

    override fun convert(measureNumber: Double, unitOfMeasurement: UnitOfMeasurement): Double {
      val measureNumberConverted =
          when (unitOfMeasurement) {
            CELSIUS -> (measureNumber - 32) * 5 / 9
            KELVIN -> (measureNumber + 459.67) * 5 / 9
            else -> measureNumber
          }
      return measureNumberConverted
    }
  },
  KELVIN {
    override fun type() = "temperature"

    override fun aliases() = listOf("k", "kelvin", "kelvins")

    override fun proportionToBaseUnit() = 1.0

    override fun singular() = this.aliases()[1]

    override fun plural() = this.aliases()[2]

    override fun convert(measureNumber: Double, unitOfMeasurement: UnitOfMeasurement): Double {
      val measureNumberConverted =
          when (unitOfMeasurement) {
            CELSIUS -> measureNumber - 273.15
            FAHRENHEIT -> (measureNumber * 9 / 5) - 459.67
            else -> measureNumber
          }
      return measureNumberConverted
    }
  };

  abstract fun type(): String

  abstract fun aliases(): List<String>

  abstract fun proportionToBaseUnit(): Double

  abstract fun singular(): String

  abstract fun plural(): String

  open fun convert(measureNumber: Double, unitOfMeasurement: UnitOfMeasurement): Double {
    return (measureNumber * this.proportionToBaseUnit()) / unitOfMeasurement.proportionToBaseUnit()
  }

  companion object {
    fun getByName(unitToTest: String): List<UnitOfMeasurement> {
      return entries
          .filter { it.aliases().contains(unitToTest.lowercase()) }
          .ifEmpty {
            return emptyList()
          }
    }
  }
}

class InputMeasurement(input: List<String>) {
  private var number: Double = 0.0
  private var unitTypeFrom: UnitOfMeasurement? = null
  private var unitTypeTo: UnitOfMeasurement? = null
  private var valid: Boolean = false

  fun number() = this.number.toDouble()

  fun unitTypeFrom() = this.unitTypeFrom

  fun unitTypeTo() = this.unitTypeTo

  fun valid() = this.valid

  init {
    try {
      number = input[0].toDouble()
      when (input.size) {
        4 -> {
          unitTypeFrom =
              if (UnitOfMeasurement.getByName(input[1]).isNotEmpty())
                  UnitOfMeasurement.getByName(input[1]).first()
              else null
          unitTypeTo =
              if (UnitOfMeasurement.getByName(input[3]).isNotEmpty())
                  UnitOfMeasurement.getByName(input[3]).first()
              else null
          valid =
              UnitOfMeasurement.getByName(input[1]).isNotEmpty() &&
                  UnitOfMeasurement.getByName(input[3]).isNotEmpty()
        }
        5,
        6 -> {
          val inputWithoutDegreeLabels =
              input.filter { !listOf("degree", "degrees").contains(it.lowercase()) }
          if (inputWithoutDegreeLabels.isNotEmpty() && inputWithoutDegreeLabels.size != 4) {
            throw java.lang.Exception("Parse error")
          }
          unitTypeFrom =
              if (UnitOfMeasurement.getByName(inputWithoutDegreeLabels[1]).isNotEmpty())
                  UnitOfMeasurement.getByName(inputWithoutDegreeLabels[1]).first()
              else null
          unitTypeTo =
              if (UnitOfMeasurement.getByName(inputWithoutDegreeLabels[3]).isNotEmpty())
                  UnitOfMeasurement.getByName(inputWithoutDegreeLabels[3]).first()
              else null
          valid =
              UnitOfMeasurement.getByName(inputWithoutDegreeLabels[1]).isNotEmpty() &&
                  UnitOfMeasurement.getByName(inputWithoutDegreeLabels[3]).isNotEmpty()
        }
        else -> throw java.lang.Exception("Parse error")
      }
      if (unitTypeFrom == null || unitTypeTo == null) {
        val message =
            "Conversion from ${unitTypeFrom?.plural() ?: "???"} to ${unitTypeTo?.plural() ?: "???"} is impossible"
        throw java.lang.Exception(message)
      }
    } catch (exception: Exception) {
      println(
          if (exception.message != null &&
              exception.message!!.startsWith("Conversion from ") &&
              exception.message!!.endsWith(" is impossible"))
              exception.message
          else "Parse error")
      println()
    }
  }
}

fun menu() {
  do {
    print("Enter what you want to convert (or exit): ")
    val measureInput = readln()
    if (measureInput == "exit") break
    val inputMeasurement = parseInput(measureInput.split(" "))
    if (!inputMeasurement.valid()) continue
    val unitOfMeasurementsFrom = inputMeasurement.unitTypeFrom()
    val unitOfMeasurementsTo = inputMeasurement.unitTypeTo()
    if (unitOfMeasurementsFrom != null && unitOfMeasurementsTo != null) {
      if (unitOfMeasurementsFrom.type() != unitOfMeasurementsTo.type()) {
        println(
            "Conversion from ${unitOfMeasurementsFrom.plural()} to ${unitOfMeasurementsTo.plural()} is impossible")
        println()
        continue
      }
      val measureNumberFrom = inputMeasurement.number()
      if (measureNumberFrom < 0.0 && unitOfMeasurementsFrom.type() == "distance") {
        println("Length shouldn't be negative")
        println()
        continue
      }
      if (measureNumberFrom < 0.0 && unitOfMeasurementsFrom.type() == "weight") {
        println("Weight shouldn't be negative")
        println()
        continue
      }

      // converter section
      val convertedToFinalUnit =
          unitOfMeasurementsFrom.convert(
              measureNumber = measureNumberFrom, unitOfMeasurement = unitOfMeasurementsTo)
      print("$measureNumberFrom ${unitValueToPrint(unitOfMeasurementsFrom, measureNumberFrom)} is ")
      println(
          "$convertedToFinalUnit ${unitValueToPrint(unitOfMeasurementsTo, convertedToFinalUnit)}")
      println()
    }
  } while (true)
}

fun parseInput(input: List<String>) = InputMeasurement(input)

fun unitValueToPrint(unitOfMeasurement: UnitOfMeasurement, measureNumber: Double): String {
  return if (measureNumber != 1.0) unitOfMeasurement.plural() else unitOfMeasurement.singular()
}
