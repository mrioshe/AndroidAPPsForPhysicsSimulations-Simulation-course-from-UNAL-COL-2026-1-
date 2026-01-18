package com.curso_simulaciones.miterceraapp.componentes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.View;

public class Pizarra extends View {
    private Paint pincel;

    public Pizarra(Context context) {
        super(context);
        pincel = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Configuración del pincel
        pincel.setAntiAlias(true);
        pincel.setStyle(Paint.Style.FILL);
        pincel.setTextSize(50);
        pincel.setColor(Color.BLACK);

        // Dibujar una cadena (String)
        canvas.drawText("Hola Jovenes bienvenidos a sus primeros dibujos", 100, 100, pincel);

        // Dibujar una línea
        pincel.setStrokeWidth(5);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawLine(100, 150, 500, 150, pincel);

        // Dibujar un círculo
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(Color.BLUE);
        canvas.drawCircle(300, 300, 50, pincel);

        // Dibujar un rectángulo relleno y transparente
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(Color.argb(128, 255, 0, 0)); // Rojo semitransparente
        canvas.drawRect(100, 400, 300, 600, pincel);

        // Dibujar un rectángulo relleno y opaco
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(Color.rgb(255, 0, 50));
        canvas.drawRect(350, 250, 850, 450, pincel);

        // Dibujar un rectángulo sin relleno
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setColor(Color.BLACK);
        canvas.drawRect(500, 500, 800, 700, pincel);

        // Dibujar un arco
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setColor(Color.GREEN);
        RectF rectF = new RectF(100, 800, 400, 1100);
        canvas.drawArc(rectF, 0, 90, false, pincel);

        // Dibujar sector circular no relleno
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setColor(Color.MAGENTA);
        RectF rectF1 = new RectF(500, 800, 800, 1100);
        canvas.drawArc(rectF1, 0, 90, true, pincel);

        // Dibujar sector circular relleno
        pincel.setStyle(Paint.Style.FILL);
        pincel.setColor(Color.BLACK);
        RectF rectF2 = new RectF(250, 400, 500, 650);
        canvas.drawArc(rectF2, 90, 135, true, pincel);

        // Dibujar triángulo no relleno
        pincel.setStyle(Paint.Style.STROKE);
        pincel.setColor(Color.BLACK);
        Path path = new Path();
        path.moveTo(600, 1200);
        path.lineTo(900, 1200);
        path.lineTo(750, 1000);
        path.close();
        canvas.drawPath(path, pincel);

        // Dibujar algo especial: texto en un círculo
        Path trazo = new Path();
        trazo.addCircle(800, 400, 100, Path.Direction.CCW);
        pincel.setColor(Color.CYAN);
        pincel.setStrokeWidth(8);
        pincel.setStyle(Paint.Style.STROKE);
        canvas.drawPath(trazo, pincel);
        pincel.setStrokeWidth(1);
        pincel.setStyle(Paint.Style.FILL);
        pincel.setTextSize(20);
        pincel.setTypeface(Typeface.SANS_SERIF);
        pincel.setColor(Color.BLACK);
        canvas.drawTextOnPath("Escuela de Física Universidad Nacional de Colombia -Medellin-", trazo, 10, 40, pincel);
    }
}
