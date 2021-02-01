package org.itiud.buscaminas;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.gridlayout.widget.GridLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.shape.MaterialShapeDrawable;
import org.itiud.buscaminas.logica.BuscaMInas;
import org.itiud.buscaminas.logica.Celda;
public class MainActivity extends AppCompatActivity {
    int width =0;
    int height=0;
    private GridLayout gridLayout;
    TextView celdas[][] = new TextView[8][8];
    private BuscaMInas bm = new BuscaMInas();
    private  Celda[][] celda  ;
    private Button buton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gridLayout = findViewById(R.id.Grid);
        this.buton=findViewById(R.id.button);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width =size.x;
        height=size.y;
        this.buton.setX(width/2-78);
        this.buton.setY(height-247);
        this.buton.setBackgroundColor(Color.BLACK);
        this.buton.setTextColor(Color.WHITE);
        playing();
    }
    public void playing(){
        this.gridLayout.removeAllViews();
        this.celda = this.bm.getMatrix();
        boolean end = bm.isEnd();
        boolean win = bm.win();

        MaterialShapeDrawable shapeDrawable = new MaterialShapeDrawable();
        shapeDrawable.setFillColor(ContextCompat.getColorStateList(this,android.R.color.darker_gray).withAlpha(15));
        shapeDrawable.setStroke(1.0f, ContextCompat.getColor(this,R.color.black));

        MaterialShapeDrawable shapeDrawabl = new MaterialShapeDrawable();
        shapeDrawabl.setFillColor(ContextCompat.getColorStateList(this,android.R.color.darker_gray).withAlpha(75));
        shapeDrawabl.setStroke(1.5f, ContextCompat.getColor(this,R.color.white));

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                this.celdas[i][j] = new TextView(this);
                ViewCompat.setBackground(this.celdas[i][j],shapeDrawable);
                if(!this.celda[i][j].isEstado()){
                    if(this.celda[i][j].getValue().equals("0")
                    //        || this.celda[i][j].getValue().equals(" ")
                    ){
                        this.celdas[i][j].setText(" ");
                        ViewCompat.setBackground(this.celdas[i][j],shapeDrawabl);
                    }else{
                    this.celdas[i][j].setText(this.celda[i][j].getValue());
                        ViewCompat.setBackground(this.celdas[i][j],shapeDrawable);
                    }
                }
                this.celdas[i][j].setLayoutParams(new LinearLayout.LayoutParams(width/8,height/8-50));
                this.celdas[i][j].setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
                this.celdas[i][j].setTextSize(30);
                this.gridLayout.addView(celdas[i][j]);
                this.celdas[i][j].setOnTouchListener(new View.OnTouchListener() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction()==MotionEvent.ACTION_DOWN){
                            GridLayout parent = (GridLayout) v.getParent();
                            int x = parent.indexOfChild(v) / parent.getColumnCount();
                            int y = parent.indexOfChild(v) % parent.getColumnCount();

                            if(!win) {
                                if (end) {
                                    celda = bm.play(x, y);
                                    playing();
                                }else{
                                    Toast.makeText(getApplicationContext(), "YOU LOSE", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "YOU WIN", Toast.LENGTH_SHORT).show();
                            }


                        }
                        return false;
                    }
                });
            }
        }
}

    public void play(View view) {
        bm = new BuscaMInas();
        playing();
    }
}