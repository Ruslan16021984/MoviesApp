package ru.androidschool.intensiv.kotlin

import io.reactivex.Observable
import org.junit.Test

class CodeLab {
    @Test
    fun filterBookExample() {

        Observable.fromIterable(getBooksList())
            .filter { it.location == "Москва" }
            .filter { it.price > 400 }
            .distinct {it.title}
            .map { "${it.author} ${it.title}" }.subscribe {log -> print(log)}
    }

    private fun getBooksList(): List<Book> {
        return listOf(
            Book("Шантарам", "Грегори Дэвид Робертс", 1, 780.0, "Москва", "₽"),
            Book("Три товарища", "Эрих Мария Ремарк", 2, 480.0, "Москва", "₽"),
            Book("Цветы для Элджернона", "Даниел Киз", 3, 380.0, "Москва", "₽"),
            Book(" Атлант расправил плечи", "Айн Рэнд", 4, 880.0, "Ставрополь", "₽"),
            Book(" Атлант расправил плечи", "Айн Рэнд", 4, 580.0, "Сочи", "₽")
        )
    }

}