package edu.msu.lawsont2.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class Puzzle {

    /**
     * Percentage of the display width or height that
     * is occupied by the puzzle.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    /**
     * Paint for filling the area the puzzle is in
     */
    private Paint fillPaint;

    /**
     * Paint for outlining the area the puzzle is in
     */
    private Paint outlinePaint;

    /**
     * Completed puzzle bitmap
     */
    private Bitmap puzzleComplete;

    /**
     * Collection of puzzle pieces
     */
    public ArrayList<PuzzlePiece> pieces = new ArrayList<PuzzlePiece>();

    public Puzzle(Context context) {

        // Create paint for filling the area the puzzle will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        // Load the solved puzzle image
        puzzleComplete =
                BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.sparty_done);

        // Load the puzzle pieces
        pieces.add(new PuzzlePiece(context,
                R.drawable.sparty1,
                0.259f,
                0.238f));

        outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        outlinePaint.setColor(0xff008000);
        outlinePaint.setStrokeWidth(3);
    }

    public void draw(Canvas canvas) {
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        int puzzleSize = (int)(minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the puzzle
        int marginX = (wid - puzzleSize) / 2;
        int marginY = (hit - puzzleSize) / 2;

        //
        // Draw the outline of the puzzle
        //

        canvas.drawLine(marginX, marginY, marginX + puzzleSize, marginY, outlinePaint);
        canvas.drawLine(marginX + puzzleSize, marginY, marginX + puzzleSize,
                marginY + puzzleSize, outlinePaint);
        canvas.drawLine(marginX, marginY, marginX, marginY + puzzleSize, outlinePaint);
        canvas.drawLine(marginX, marginY + puzzleSize, marginX + puzzleSize,
                marginY + puzzleSize, outlinePaint);


        canvas.drawRect(marginX, marginY,
                marginX + puzzleSize, marginY + puzzleSize, fillPaint);
        float scaleFactor = (float)puzzleSize /
                (float)puzzleComplete.getWidth();
        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        // canvas.drawBitmap(puzzleComplete, 0, 0, null);
        canvas.restore();

        for(PuzzlePiece piece : pieces) {
            piece.draw(canvas, marginX, marginY, puzzleSize, scaleFactor);
        }
    }

    /**
     * Handle a touch event from the view.
     * @param view The view that is the source of the touch
     * @param event The motion event describing the touch
     * @return true if the touch is handled.
     */
    public boolean onTouchEvent(View view, MotionEvent event) {


        return false;
    }
}