package com.fhlj.manmachine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fhlj.wuzichess.R;

import java.util.ArrayList;
import java.util.List;

import static com.fhlj.manmachine.ConstantUtil.MAX_LINE;

public class WuziqiPanel extends View {
	private int mPanelWidth;
	private float mLineHight;

	private Paint mPaint = new Paint();
	private Bitmap mWhitePiece;
	private Bitmap mBlackPiece;

	private List<Point> whiteList = new ArrayList<Point>();
	private List<Point> blackList = new ArrayList<Point>();

	private boolean mIsGemOver;
	private boolean mIsWhiteWinner;

	public WuziqiPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(3f);
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setStyle(Paint.Style.STROKE);

		mWhitePiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_w2);
		mBlackPiece = BitmapFactory.decodeResource(getResources(), R.mipmap.stone_b1);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mIsGemOver) {
			return false;
		}
		int action = event.getAction();
		if (action == MotionEvent.ACTION_UP) {
			int x = (int) event.getX();
			int y = (int) event.getY();
			Point p = getVaLidPoint(x, y);
			if (whiteList.contains(p) || blackList.contains(p)) {
				return false;
			}
			whiteList.add(p);
			invalidate();
			blackList.add(AI.getInstance().getAIStep(blackList, whiteList));
		}
		return true;
	}

	private Point getVaLidPoint(int x, int y) {
		return new Point((int) (x / mLineHight), (int) (y / mLineHight));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int heighSize = MeasureSpec.getSize(heightMeasureSpec);
		int heighMode = MeasureSpec.getMode(heightMeasureSpec);

		int width = Math.min(widthSize, heighSize);

		if (widthMode == MeasureSpec.UNSPECIFIED) {
			width = heighSize;
		} else if (heighMode == MeasureSpec.UNSPECIFIED) {
			width = widthSize;
		}
		setMeasuredDimension(width, width);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mPanelWidth = w;
		mLineHight = mPanelWidth * 1.0f / ConstantUtil.MAX_LINE;
		int PiceWhite = (int) (mLineHight * ConstantUtil.RatioPieceOfLineHight);
		mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, PiceWhite, PiceWhite, false);
		mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, PiceWhite, PiceWhite, false);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawBoard(canvas);
		draePicec(canvas);
		checkGameOver();
	}

	private void checkGameOver() {
		boolean whithWin = chechFiveInLine(whiteList);
		boolean blickWin = chechFiveInLine(blackList);
		if (whithWin || blickWin) {
			mIsGemOver = true;
			mIsWhiteWinner = whithWin;
			String text = mIsWhiteWinner ? "白棋胜利" : "黑棋胜利";
			Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			;
		}
	}

	private boolean chechFiveInLine(List<Point> pointList) {
		for (Point p : pointList) {
			int x = p.x;
			int y = p.y;

			boolean win = checkHorizontal(x, y, pointList);
			if (win) return true;
			 win = checkVertIcal(x, y, pointList);
			if (win) return true;
			 win = checkLeftDiagonal(x, y, pointList);
			if (win) return true;
			 win = checkRightDiagonl(x, y, pointList);
			if (win) return true;
		}
		return false;
	}

	private boolean checkHorizontal(int x, int y, List<Point> pointList) {
		int count = 1;
		for (int i = 1; i < 5; i++) {
			if (pointList.contains(new Point(x-i,y))) {
				count++;
			}else {
				break;
			}
		}
		if (count==5) return true;
		for (int i = 1; i < 5; i++) {
			if (pointList.contains(new Point(x+i,y))) {
				count++;
			}else {
				break;
			}
			if (count==5) return true;
		}
		return false;
	}
	private boolean checkRightDiagonl(int x, int y, List<Point> pointList) {
		int count = 1;
		for (int i = 1; i < 5; i++) {
			if (pointList.contains(new Point(x-i,y-i))) {
				count++;
			}else {
				break;
			}
		}
		if (count==5) return true;
		for (int i = 1; i < 5; i++) {
			if (pointList.contains(new Point(x+i,y+i))) {
				count++;
			}else {
				break;
			}
			if (count==5) return true;
		}
		return false;
	}
	private boolean checkLeftDiagonal(int x, int y, List<Point> pointList) {
		int count = 1;
		for (int i = 1; i < 5; i++) {
			if (pointList.contains(new Point(x-i,y+i))) {
				count++;
			}else {
				break;
			}
		}
		if (count==5) return true;
		for (int i = 1; i < 5; i++) {
			if (pointList.contains(new Point(x+i,y-i))) {
				count++;
			}else {
				break;
			}
			if (count==5) return true;
		}
		return false;
	}
	private boolean checkVertIcal(int x, int y, List<Point> pointList) {
		int count = 1;
		for (int i = 1; i < 5; i++) {
			if (pointList.contains(new Point(x,y-i))) {
				count++;
			}else {
				break;
			}
		}
		if (count==5) return true;
		for (int i = 1; i < 5; i++) {
			if (pointList.contains(new Point(x,y+i))) {
				count++;
			}else {
				break;
			}
			if (count==5) return true;
		}
		return false;
	}

	private void draePicec(Canvas canvas) {
		for (int i = 0, n = whiteList.size(); i < n; i++) {
			Point whitePoint = whiteList.get(i);
			canvas.drawBitmap(mWhitePiece, (whitePoint.x + (1 - ConstantUtil.RatioPieceOfLineHight) / 2) * mLineHight,
					(whitePoint.y + (1 - ConstantUtil.RatioPieceOfLineHight) / 2) * mLineHight, null);
		}
		for (int i = 0, n = blackList.size(); i < n; i++) {
			Point blackPoint = blackList.get(i);
			canvas.drawBitmap(mBlackPiece, (blackPoint.x + (1 - ConstantUtil.RatioPieceOfLineHight) / 2) * mLineHight,
					(blackPoint.y + (1 - ConstantUtil.RatioPieceOfLineHight) / 2) * mLineHight, null);
		}
	}

	private void drawBoard(Canvas canvas) {
		int w = mPanelWidth;
		float lineHeight = mLineHight;
		for (int i = 0; i < MAX_LINE; i++) {
			int startX = (int) (lineHeight / 2);
			int endX = (int) (w - lineHeight / 2);

			int y = (int) ((0.5 + i) * lineHeight);
			canvas.drawLine(startX, y, endX, y, mPaint);
			canvas.drawLine(y, startX, y, endX, mPaint);
		}
	}

}
