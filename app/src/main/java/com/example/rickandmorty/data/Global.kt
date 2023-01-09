package com.example.rickandmorty.data

import com.example.rickandmorty.data.characters.Character

/**
 * Оправдаюсь немного:
 * Делать так нельзя по ряду причин и вообще я тут в MVP пытаюсь :/
 * Но делаю я это не для себя
 * Вам на соревновании важна скорось и задр*чиваться архитектурой вам там не нужно
 * И в таком случае проще будет сделать обычный singleton
 * через который и будут передаваться данные от одного окна к другому.
 * Оправдался - теперь можно и чаю выпить
 */
object Global {

    private var pickedCharacter: Character? = null

    fun getPickedCharacter(): Character? {
        val output = pickedCharacter
        pickedCharacter = null
        return output
    }

    fun setPickedCharacter(character: Character) {
        pickedCharacter = character
    }

}