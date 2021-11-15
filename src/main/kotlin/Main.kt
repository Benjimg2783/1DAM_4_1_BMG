data class Modulo(val maxAlumnos: Int) {
    private var alumnos = arrayOfNulls<Alumno?>(maxAlumnos)
    private val evaluaciones = Array(4) { DoubleArray(maxAlumnos) { 0.0 } }

    init {
        require(maxAlumnos > 0) { "El numero máximo de alumnos es obligatorio y debe ser positivo" }
    }

    //Establece la nota de un alumno en una evaluacion concreta.
    fun establecerNota(
        idAlumno: String,
        evaluacion: String,
        nota: Double
    ): Boolean {
        if (evaluacion == "0" || evaluacion == "1" || evaluacion == "2" || evaluacion == "3") {
            evaluaciones[evaluacion.toInt()][alumnos.indexOfFirst { it?.id == idAlumno }] = nota
            return true
        } else println("La evauación de ser representada con un 0 si es la primera, un 1 si es la segunda o un 2 si es la tercera.");return false
    }

    //Calcula la nota de la evaluacion final de un alumno.
    fun calculaEvaluacionFinal(idAlumno: String) {
        var evFinal =
            evaluaciones[0][alumnos.indexOfFirst { it?.id == idAlumno }] + evaluaciones[1][alumnos.indexOfFirst { it?.id == idAlumno }] + evaluaciones[2][alumnos.indexOfFirst { it?.id == idAlumno }]
        evFinal /= 3
        evaluaciones[3][alumnos.indexOfFirst { it?.id == idAlumno }] = evFinal
    }

    //Devuelve una lista con el nombre y apellidos de un alumno y su nota en una evaluacion concreta
    private fun listaNotas(evaluacion: String): List<Pair<String, Double>> {
        val listaNota: MutableList<Pair<String, Double>> = mutableListOf()
        if ((evaluacion == "0" || evaluacion == "1" || evaluacion == "2" || evaluacion == "3")) {
            for (i in 0 until maxAlumnos) {
                if (alumnos[i] != null) listaNota.add(
                    Pair(
                        "${alumnos[i]?.nombre!!} ${alumnos[i]?.apellidos!!}",
                        evaluaciones[evaluacion.toInt()][i]
                    )
                )
            }
        }
        return listaNota
    }

    //Devuelve el numero de alumnos aprobados en una evaluacion concreta
    fun numeroAprobados(evaluacion: String): Int {
        return if (evaluacion == "0" || evaluacion == "1" || evaluacion == "2" || evaluacion == "3") {
            evaluaciones[evaluacion.toInt()].count { it > 4.99 }
        } else 0
    }

    //Devuelve la nota mas baja de una evaluacion concreta
    fun notaMasBaja(evaluacion: String): Double {
        return if (evaluacion == "0" || evaluacion == "1" || evaluacion == "2" || evaluacion == "3") evaluaciones[evaluacion.toInt()].filter { it > 0.0 }
            .minOrNull() ?: (-1.0)
        else 0.0
    }

    //Devuelve la nota mas alta de una evaluacion concreta
    fun notaMasAlta(evaluacion: String): Double {
        return if (evaluacion == "0" || evaluacion == "1" || evaluacion == "2" || evaluacion == "3") evaluaciones[evaluacion.toInt()].filter { it > 0.0 }
            .maxOrNull() ?: (-1.0)
        else 0.0
    }

    //Devuelve la nota media de una evaluacion concreta
    fun notaMedia(evaluacion: String): Double {
        var media = 0.0
        var alumnosCalificados = 0
        if (evaluacion == "0" || evaluacion == "1" || evaluacion == "2" || evaluacion == "3") {
            for (i in alumnos.indices) {
                if (evaluaciones[evaluacion.toInt()][i] > 0.0) {
                    media += evaluaciones[evaluacion.toInt()][i]
                    alumnosCalificados++
                }
            }
        }
        media /= alumnosCalificados
        return media
    }

    //Comprueba si hay alumnos con diez
    fun hayAlumnosConDiez(evaluacion: String): Boolean {
        return if (evaluacion == "0" || evaluacion == "1" || evaluacion == "2" || evaluacion == "3") evaluaciones[evaluacion.toInt()].any { it == 10.0 }
        else false
    }

    //Comprueba si hay alumnos aprobados
    fun hayAlumnosAprobados(evaluacion: String): Boolean {
        return if (evaluacion == "0" || evaluacion == "1" || evaluacion == "2" || evaluacion == "3") evaluaciones[evaluacion.toInt()].any { it >= 5.0 }
        else false
    }

    //Devuelve la primera nota que encuentre suspensa
    fun primeraNotaNoAprovada(evaluacion: String): Double {
        return if (evaluacion == "0" || evaluacion == "1" || evaluacion == "2" || evaluacion == "3") evaluaciones[evaluacion.toInt()][evaluaciones[evaluacion.toInt()].indexOfFirst { (it < 5.0) && (it > 0.0) }]
        else 0.0
    }

    //Devuelve una lista con el nombre y apellidos de un alumno y su nota en una evaluacion concreta ordenada de menor a mayor
    fun listaNotasOrdenadas(evaluacion: String = "3"): List<Pair<String, Double>> {
        return listaNotas(evaluacion).sortedBy { it.second }
    }

    //Comprueba si el alumno esta matriculado, si no es así lo matricula
    fun matricularAlumno(a: Alumno): Boolean {
        var comprobarAlumno = false
        var count = 0
        while ((count in 0 until maxAlumnos) && (!comprobarAlumno)) {
            if (alumnos.indexOfFirst { it?.id == a.id } == -1) {
                alumnos[alumnos.indexOfFirst { it?.id == null }] = a
                comprobarAlumno = true
            } else {
                count++
                comprobarAlumno = false
            }
        }
        return comprobarAlumno
    }

    //Comprueba si el alumno está matriculado, si es así, le da de baja
    fun bajaAlumno(idAlumno: String): Boolean {
        var comprobarAlumno = false
        var count = 0
        while ((count in 0 until maxAlumnos) && (!comprobarAlumno)) {
            if (alumnos.indexOfFirst { it?.id == idAlumno } != -1) {
                alumnos[alumnos.indexOfFirst { it?.id == idAlumno }] = null
                comprobarAlumno = true
            } else {
                count++
                comprobarAlumno = false
            }
        }
        return comprobarAlumno
    }
}

class Alumno(val id: String, val nombre: String, val apellidos: String)

fun main()  {
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
    programacion.establecerNota("A123", "0", 7.0)
    programacion.establecerNota("A123", "1", 4.9)
    programacion.establecerNota("B123", "0", 5.0)
    programacion.establecerNota("B123", "1", 7.5)
    programacion.establecerNota("B123", "2", 10.0)
    programacion.calculaEvaluacionFinal("A123")
    programacion.calculaEvaluacionFinal("B123")
    programacion.bajaAlumno("F123")
    if (programacion.hayAlumnosConDiez("2")) println("Hay alumnos con diez en la tercera evaluacion")
    else println("No hay alumnos con diez en la tercera evaluacion")
    if (programacion.hayAlumnosAprobados("1")) println("Si hay alumnos aprobados en la segunda evaluacion")
    else println("No ay alumnos aprobados en la segunda evaluacion")
    println("La primera nota suspensa encontrada es ${programacion.primeraNotaNoAprovada("1")}")
    println("En la primera evaluacion hay ${programacion.numeroAprobados("0")} aprobados")
    println("La nota más baja de la primera evaluacion es ${programacion.notaMasBaja("0")}")
    println("La nota más alta de la evaluacion final es ${programacion.notaMasAlta("3")}")
    println("La nota media de la primera evaluación es ${programacion.notaMedia("0")}")
    println(programacion.listaNotasOrdenadas())
}