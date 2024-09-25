package com.example.practico2.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.practico2.ui.models.Tablero

class Lienzo(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paintViboritaCuerpo = Paint().apply { color = Color.BLACK }
    private val paintViboritaCabeza = Paint().apply { color = Color.RED }
    private val paintComida = Paint().apply { color = Color.GREEN }

    private lateinit var tablero: Tablero

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        tablero = Tablero(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val anchoCelda = width / tablero.columnas.toFloat()
        val altoCelda = height / tablero.filas.toFloat()

        for (i in 0 until tablero.filas) {
            for (j in 0 until tablero.columnas) {
                when (tablero.matriz[i][j]) {
                    1 -> { // Dibujar cuerpo de la serpiente
                        canvas.drawRect(
                            j * anchoCelda, i * altoCelda,
                            (j + 1) * anchoCelda, (i + 1) * altoCelda,
                            paintViboritaCuerpo
                        )
                    }
                    2 -> { // Dibujar cabeza de la serpiente
                        canvas.drawRect(
                            j * anchoCelda, i * altoCelda,
                            (j + 1) * anchoCelda, (i + 1) * altoCelda,
                            paintViboritaCabeza
                        )
                    }
                    3 -> { // Dibujar comida
                        canvas.drawRect(
                            j * anchoCelda, i * altoCelda,
                            (j + 1) * anchoCelda, (i + 1) * altoCelda,
                            paintComida
                        )
                    }
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            val width = width.toFloat()
            val height = height.toFloat()

            // Dividir la pantalla en las cuatro áreas de control
            when {
                y < height / 4 -> {
                    tablero.moverArriba()
                }
                x < width / 2 && y > height / 4 && y < 3 * height / 4 -> {
                    tablero.moverIzquierda()
                }
                x > width / 2 && y > height / 4 && y < 3 * height / 4 -> {
                    tablero.moverDerecha()
                }
                y > 3 * height / 4 -> {
                    tablero.moverAbajo()
                }
            }
            invalidate() // Redibujar el lienzo
        }
        return true
    }
}
