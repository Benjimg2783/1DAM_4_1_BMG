data class Modulo(val maxAlumnos: Int) {
    var aMatriculados = arrayOfNulls<Alumno?>(maxAlumnos)
    val notas = Array(4) { DoubleArray(maxAlumnos) { 0.0 } }
    var count = 0

    companion object {
        const val EV_PRIMERA = "0"
        const val EV_SEGUNDA = "1"
        const val EV_TERCERA = "2"
    }

    init {
        require(maxAlumnos > 0 && maxAlumnos != null) { "El numero máximo de alumnos es obligatorio y debe ser positivo" }
    }

    fun establecerNota(idAlumno: String: String, evaluacion: String, nota: Double): Boolean {
        if (evaluacion == EV_PRIMERA && evaluacion == EV_SEGUNDA && evaluacion == EV_TERCERA) {
            notas[evaluacion.toInt()][aMatriculados.indexOfFirst { it?.idAlumno == idAlumno }]=nota
        }
    }

    fun matricularAlumno(a: Alumno): Boolean {
        var comprobarAlumno = false
        for (i in 0 until maxAlumnos) {
            if (aMatriculados[i] == a) comprobarAlumno = false
            else comprobarAlumno = true
        }
        if (count < maxAlumnos && comprobarAlumno) {
            aMatriculados[count] = a
            count++
        }
        return comprobarAlumno
    }

    fun bajaAlumno(idAlumno: String): Boolean {
        var comprobarAlumno = false
        for (i in 0 until maxAlumnos) {
            if (aMatriculados[i]?.idAlumno == idAlumno) {
                aMatriculados[i] = null
                comprobarAlumno = true
            } else comprobarAlumno = false
        }
        return comprobarAlumno
    }
}

class Alumno(val idAlumno: String, val nombre: String, val apellidos: String) {
    init {
        require(idAlumno != null) { "Id del alumno requerido" }
    }
}

fun main() {
    val programacion = Modulo(15)
    val alumno1 = Alumno("A123", "Jesus", "Morales")
    val alumno2 = Alumno("B123", "Juan", "Angulo")
    val alumno3 = Alumno("C123", "Clara", "Gonzalez")
    val alumno4 = Alumno("D123", "Diego", "Martinez")
    val alumno5 = Alumno("E123", "Manuel", "Torres")
    val alumno6 = Alumno("F123", "Daniel", "Coello")
    val alumno7 = Alumno("G123", "Lucia", "Cruz")
    val alumno8 = Alumno("H123", "Isabel", "Carrera")
    val alumno9 = Alumno("I123", "Rosa", "Pedemonte")
    val alumno10 = Alumno("J123", "Samuel", "Lopez")
    programacion.matricularAlumno(alumno1)
    programacion.matricularAlumno(alumno2)
    programacion.matricularAlumno(alumno3)
    programacion.matricularAlumno(alumno4)
    programacion.matricularAlumno(alumno5)
    programacion.matricularAlumno(alumno6)
    programacion.matricularAlumno(alumno7)
    programacion.matricularAlumno(alumno8)
    programacion.matricularAlumno(alumno9)
    programacion.matricularAlumno(alumno10)
}