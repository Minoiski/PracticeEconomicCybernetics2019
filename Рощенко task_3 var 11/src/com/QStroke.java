package com;

import java.awt.*;
import java.awt.geom.*;
public class QStroke implements Stroke {

	private AffineTransform t = new AffineTransform();
	private static final float DEFAULT_WAVE_LENGTH = 12;
	private static final float DEFAULT_WAVE_AMPLITUDE = 4;

	private float positivePoints[] = { 1, 2, 3, 2, 5, 2, 6, 0, 7, -2, 9,
			-2, 11, -2, 12, 0 };
	private float negativePoints[] = { 1, -2, 3, -2, 5, -2, 6, 0, 7, 2, 9,
			2, 11, 2, 12, 0 };

	private float waveLength, waveAmplitude, width;

	private BasicStroke basicStroke;

	static class AddSegmentControl {
		float x, y, dx, dy, mx, my, startX, startY;
		GeneralPath s;
		boolean start, negativePhase, startOverride, isClose;
	}

	public QStroke(float width, float waveLength, float waveAmplitude) {
		if (waveLength <= 0 || waveAmplitude <= 0 || width <= 0)
			throw new IllegalArgumentException("Неверные аргументы");
		basicStroke = new BasicStroke(width);
		AffineTransform scale = new AffineTransform();
		scale.setToScale(waveLength / DEFAULT_WAVE_LENGTH, waveAmplitude
				/ DEFAULT_WAVE_AMPLITUDE);
		scale.transform(positivePoints, 0, positivePoints, 0,
				positivePoints.length / 2);
		scale.transform(negativePoints, 0, negativePoints, 0,
				negativePoints.length / 2);
		this.waveLength = waveLength;
		this.waveAmplitude = waveAmplitude;
		this.width = width;
	}

	public Shape createWavyOutline(Shape shape) {
		PathIterator pi = new FlatteningPathIterator(
				shape.getPathIterator(null), 1);
		float seg[] = new float[6];
		int segType = 0;
		AddSegmentControl ctl = new AddSegmentControl();
		GeneralPath strokedShape = new GeneralPath();
		ctl.s = strokedShape;
		while (!pi.isDone()) {
			segType = pi.currentSegment(seg);
			switch (segType) {
				case PathIterator.SEG_MOVETO:
					ctl.x = seg[0];
					ctl.y = seg[1];
					ctl.mx = ctl.x;
					ctl.my = ctl.y;
					ctl.start = true;
					break;
				case PathIterator.SEG_LINETO:
					ctl.dx = seg[0];
					ctl.dy = seg[1];
					ctl.isClose = (ctl.dx == ctl.mx && ctl.dy == ctl.my);
					addSegment(ctl);
					ctl.start = false;
					ctl.x = ctl.dx;
					ctl.y = ctl.dy;
					break;
				case PathIterator.SEG_CLOSE:
					ctl.isClose = true;
					ctl.dx = ctl.mx;
					ctl.dy = ctl.my;
					addSegment(ctl);
					ctl.start = false;
					ctl.x = ctl.mx;
					ctl.y = ctl.my;
					break;
			}
			pi.next();
		}
		return strokedShape;
	}

	public Shape createStrokedShape(Shape shape) {
		return basicStroke.createStrokedShape(createWavyOutline(shape));
	}
	private void addSegment(AddSegmentControl ctl) {
		float x = ctl.x, y = ctl.y, dx = ctl.dx, dy = ctl.dy;
		GeneralPath s = ctl.s;
		if (!ctl.start && ctl.startOverride) {
			x = ctl.startX;
			y = ctl.startY;
		}
		if (ctl.start)
			ctl.s.moveTo(x, y);
		float wavePoints[] = positivePoints;
		if (ctl.negativePhase)
			wavePoints = negativePoints;
		if (x == dx && y == dy)
			return;
		float d = (float) Point2D.distance(x, y, dx, dy);

		// Угол поворота
		double theta = Math.atan2((dy - y), (dx - x));

		// Количество помещающихся в сегмент
		float ratio = d / waveLength;
		int n = (int) Math.floor(ratio);

		t.setToScale(1, 1);
		t.translate(x, y);

		float working[] = new float[wavePoints.length];
		t.rotate(theta);

		if (n > 1 && (ratio - n) < 0.5) {
			float scale = ratio / (float) n;
			t.scale(scale, 1);
			ratio = n;
		}

		t.transform(wavePoints, 0, working, 0, wavePoints.length / 2);

		//Повторяем n раз
		float xShift = (dx - x) / ratio;
		float yShift = (dy - y) / ratio;

		int nQuads = working.length / 4;
		t.setToTranslation(xShift, yShift);

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < nQuads; j++)
				s.quadTo(working[4 * j], working[4 * j + 1],
						working[4 * j + 2], working[4 * j + 3]);
			t.transform(working, 0, working, 0, working.length / 2);
		}

		//Если достаточно места, рисуем полволны
		if (ctl.isClose) {
			if (ratio - n > 0.75) {
				s.quadTo(working[0], working[1], working[2], working[3]);
				s.quadTo(working[4], working[5], working[6], working[7]);
				s.quadTo(working[8], working[9], working[10], working[11]);
				s.quadTo(working[12], working[13], dx, dy);
				ctl.startOverride = false;
			} else if (ratio - n > 0.25) {
				s.quadTo(working[0], working[1], working[2], working[3]);
				s.quadTo(working[4], working[5], dx, dy);
				ctl.negativePhase = !ctl.negativePhase;
				ctl.startOverride = false;
			} else {
				s.lineTo(dx, dy);
				ctl.startOverride = false;
			}
		} else {
			if (ratio - n >= 0.5) {
				s.quadTo(working[0], working[1], working[2], working[3]);
				s.quadTo(working[4], working[5], working[6], working[7]);
				ctl.negativePhase = !ctl.negativePhase;
				ctl.startOverride = true;
				ctl.startX = x + (n + 0.5f) * xShift;
				ctl.startY = y + (n + 0.5f) * yShift;
			} else {
				ctl.startOverride = true;
				ctl.startX = x + n * xShift;
				ctl.startY = y + n * yShift;
			}
		}
	}
}

