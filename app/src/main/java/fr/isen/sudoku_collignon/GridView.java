package fr.isen.sudoku_collignon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GridView extends View {

    private int screenWidth;
    private int screenHeight;
    private int n;

    private Paint paintGreen;
    private Paint paintRed;
    private Paint paintBlack;
    private Paint currentPaint;

    private int[][]     matrix          = new int       [9][9];
    private boolean[][] fixIdx          = new boolean   [9][9];
    private boolean[][] fixIdxInitial   = new boolean   [9][9];

    /**
     * Constructor
     * @param context
     * @param attrs
     * @param defStyle
     */
    public GridView( Context context, AttributeSet attrs, int defStyle ) {

        super(context, attrs, defStyle);
        new GridService().execute( this );
        init();
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public GridView( Context context, AttributeSet attrs ) {

        super(context, attrs);
        new GridService().execute( this );
        init();
    }

    /**
     * Constructor
     * @param context
     */
    public GridView( Context context ) {

        super(context);
        new GridService().execute( this );
        init();
    }

    /**
     * Démare la partie
     */
    public void init() {

        // Definie une grille vide en attendant la fin de la requête
        set( "000000000000000000000000000000000000000000000000000000000000000000000000000000000" );

        float font_size = 80;

        paintGreen = new Paint();
        paintGreen.setAntiAlias( true );
        paintGreen.setColor(Color.GREEN );
        paintGreen.setTextSize(font_size );

        paintRed = new Paint();
        paintRed.setAntiAlias( true );
        paintRed.setColor(Color.RED );
        paintRed.setTextSize(font_size );

        paintBlack = new Paint();
        paintBlack.setAntiAlias( true );
        paintBlack.setColor(Color.BLACK );
        paintBlack.setTextSize(font_size );

        currentPaint = paintBlack;
    }

    /**
     * Déssine la matrice
     * @param canvas
     */
    @Override
    protected void onDraw( Canvas canvas ) {

        screenWidth     = getWidth();
        screenHeight    = getHeight();
        int x           = Math.min( screenWidth, screenHeight );
        n               = (x / 9) - (1 - (x % 2));
        float colSize   = x / 9;

        for( int i=0; i<=9; i++ ) {

            // Dessiner les lignes noires
            if( i != 3 && i != 6 ) {
                canvas.drawLine( i * colSize,   0,              i * colSize,    x,              currentPaint);
                canvas.drawLine( 0,             i * colSize,    x,              i * colSize,    currentPaint);

            // Dessiner les lignes rouges
            } else {
                canvas.drawLine( i * colSize,   0,              i * colSize,    x,              paintRed);
                canvas.drawLine( 0,             i * colSize,    x,              i * colSize,    paintRed);
            }
        }

        // Le contenu d'une case
        String s;
        for ( int i = 0; i < 9; i++ ) {
            for ( int j = 0; j < 9; j++ ) {

                s = "" + (matrix[j][i] == 0 ? "" : matrix[j][i]);
                Paint painter;

                if (fixIdxInitial[j][i]) { // Si c'est un chiffre de la grille de départ

                    painter = paintRed;
                } else {
                    painter = paintBlack;
                }
                canvas.drawText( s, i*n + n/3 , j*n + colSize - colSize/4, painter );
            }
        }
        currentPaint = paintBlack;
    }

    /**
     * Retourne l'indice x dans la matrice à partir d'un pixel
     * @param x
     * @return Integer
     */
    public int getXFromMatrix( int x ) {
        // Renvoie l'indice d'une case à  partir du pixel x de sa position h
        return (int) Math.floor( x / n );
    }

    /**
     * Retourne l'indice y dans la matrice à partir d'un pixel
     * @param y
     * @return Integer
     */
    public int getYFromMatrix( int y ) {

        return (int) Math.floor( y / n );
    }

    /**
     * Remplir la n ième ligne de la matrice matrix avec un vecteur String s
     * @param s
     * @param i
     */
    public void set( String s, int i ) {

        int v;
        for( int j = 0; j < 9; j++ ) {

            v = s.charAt(j) - '0';
            matrix[i][j] = v;
            if( v == 0 )
                fixIdx[i][j] = false;
            else
                fixIdx[i][j] = true;
        }
    }

    /**
     * Rempli toute la matrice
     * @param s
     */
    public void set( String s ) {
        // Remplir la matrice matrix à  partir d'un vecteur String s
        for ( int i = 0; i < 9; i++ ) {

            set(s.substring(i * 9, i * 9 + 9), i);
        }

        for( int i=0; i<9; i++ ) {
            for( int j=0; j<9; j++ ) {
                if( fixIdx[i][j] ) {
                    fixIdxInitial[i][j] = true;
                }
            }
        }
    }

    /**
     * Affecter la valeur v à  la case (y,x)
     * @param x
     * @param y
     * @param v
     */
    public void set( int x, int y, int v ) {

        matrix[x][y] = v;
        fixIdx[x][y] = true;
    }


    /**
     * Remet a 0 une case (x, y)
     * @param x
     * @param y
     */
    public void unSet( int x, int y ) {

        matrix[x][y] = 0;
        fixIdx[x][y] = false;
    }

    /**
     * Renvoie si la case (y,x) est fixe
     * @param x
     * @param y
     * @return Boolean
     */
    public boolean isFix( int x, int y ) {

        return fixIdx[x][y];
    }

    /**
     * Renvoi si la case etait définie au départ
     * @param x
     * @param y
     * @return Boolean
     */
    public boolean isFixInitial( int x, int y ) {

        return fixIdxInitial[x][y];
    }

    /**
     * Vérifie si toute la grille est remplie
     * @return Boolean
     */
    public boolean isGridFull() {

        for( int i = 0; i < 9; i++ ) {
            for( int j = 0; j < 9; j++ ) {
                if( !fixIdx[i][j] )
                    return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si le joueur a gagné
     * @return
     */
    public boolean isValid() {

        // 1. VÃ©rifier l'existence de chaque numÃ©ro (de 1 Ã  9) dans chaque
        // ligne et chaque colonne

        currentPaint = paintRed; // ca marche pas

        boolean[] rl = { true, true, true, true, true, true, true, true, true };
        boolean[] rc = { true, true, true, true, true, true, true, true, true };
        for( int i = 0; i < 9; i++ ) {
            for( int j = 0; j < 9; j++ ) {
                if( matrix[i][j] == 0 )
                    return false;
                if( rl[j] && rc[j] )
                    rl[j] = rc[j] = false;
                else
                    return false;
            }
            for( int j = 0; j < 9; j++ ) {

                rl[matrix[i][j] - 1] = true;
                rc[matrix[i][j] - 1] = true;
            }
        }
        // ------
        // 2. VÃ©rifier l'existence de chaque numÃ©ro (de 1 Ã  9) dans chacun
        // des 9 carrÃ©s
        boolean[] r = { true, true, true, true, true, true, true, true, true };
        int w;
        for( int i = 0; i < 3; i++ ) {
            for( int j = 0; j < 3; j++ ) {

                w = 0;
                for( int k = i * 3; k < i * 3 + 3; k++ ) {
                    for( int l = j * 3; l < j * 3 + 3; l++ ) {
                        if( matrix[k][l] == 0 )
                            return false;
                        if( r[w] )
                            r[w++] = false;
                        else
                            return false;
                    }
                }
                for( int k = i * 3; k < i * 3 + 3; k++ ) {
                    for( int l = j * 3; l < j * 3 + 3; l++ ) {

                        r[matrix[k][l] - 1] = true;
                    }
                }
            }
        }
        // ------
        // GagnÃ©
        currentPaint = paintGreen;
        return true;
    }
}
