package ru.netology

//TIP Чтобы <b>запустить</b> код, нажмите <shortcut actionId="Run"/> или
// нажмите на значок <icon src="AllIcons.Actions.Execute"/> в поле.

fun main() {
    printResult(commission(amountTransfers = 150_000.0, cardType = "Mastercard"))
    printResult(commission(amountTransfers = 1_000.0, cardType = "Visa"))
    printResult(commission(amountTransfers = 150_001.0, cardType = "Мир"))
    printResult(commission(amountTransfers = 200_000.0, cardType = "Visa"))
    printResult(commission(amountTransfers = 50_000.0, cardType = "Mastercard"))
}

fun printResult(fee: Double) {
    if (fee < 0) println("Операция заблокирована: превышен лимит")
    else println("Комиссия: $fee ₽")
}

fun commission(
    amountTransfers: Double,
    cardType: String = "Мир",
    amountOfPreviousTransfers: Double = 0.0
): Double {
    val dailyLimit = 150_000.0
    val monthlyLimit = 600_000.0

    if (amountTransfers > dailyLimit) return -1.0
    if (amountOfPreviousTransfers + amountTransfers > monthlyLimit) return -1.0
    
    return when (cardType) {
        "Mastercard" -> {
            val freeLimit = 75_000.0
            val overLimit = when {
                amountOfPreviousTransfers + amountTransfers <= freeLimit -> 0.0
                amountOfPreviousTransfers >= freeLimit -> amountTransfers
                else -> amountOfPreviousTransfers + amountTransfers - freeLimit
            }
            if (overLimit > 0) overLimit * 0.006 + 20.0 else 0.0
        }

        "Visa" -> maxOf(amountTransfers * 0.0075, 35.0)

        "Мир" -> 0.0

        else -> 0.0
    }
}