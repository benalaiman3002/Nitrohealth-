package cat.copernic.abenali.nitrooficial.models

data class Contacto (val name:String, val num: String){
    var nombre : String?= null
    var telefono : String?= null

    constructor():this("", "")

    init {
        this.nombre = nombre
        this.telefono = telefono
    }

    override fun toString(): String {
        return "Contactos(nombre='$nombre', telefono ='$telefono')"

    }


}