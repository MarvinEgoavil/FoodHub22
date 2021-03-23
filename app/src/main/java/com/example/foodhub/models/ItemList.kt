package com.example.foodhub.models

class ItemList(
    var id: Int,
    var titulo: String,
    var descripcion: String,
    var imgResource: Int,
    var isDisponible: Boolean
) {
    override fun toString(): String {
        return "ItemList{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imgResource=" + imgResource +
                ", isDisponible=" + isDisponible +
                '}'
    }
}