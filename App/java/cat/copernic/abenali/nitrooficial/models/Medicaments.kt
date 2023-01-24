package cat.copernic.abenali.nitrooficial.models

data class Medicaments (val nombre:String, val image: String, val tipus:String, val quantitat:String, val frequencia:String){
    var nombreMedicament : String?= null
    var imageMedicament: String?=null
    var turnDia : String?= null
    var turnTarda : String?= null
    var turnNit : String?= null


    init {
        this.nombreMedicament = nombre
        this.imageMedicament = image
        this.turnDia = tipus
        this.turnTarda = quantitat
        this.turnNit = frequencia
    }

    constructor():this("","","","","")


    override fun toString(): String {
        return "Medicaments(nombre='$nombre', image='$image', tipus='$tipus', quantitat='$quantitat', frequencia='$frequencia', nombreMedicament=$nombreMedicament, imageMedicament=$imageMedicament, turnDia=$turnDia, turnTarda=$turnTarda, turnNit=$turnNit)"
    }


}