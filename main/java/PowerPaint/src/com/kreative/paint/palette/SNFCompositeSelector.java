/*
 * Copyright &copy; 2010-2011 Rebecca G. Bettencourt / Kreative Software
 * <p>
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <a href="http://www.mozilla.org/MPL/">http://www.mozilla.org/MPL/</a>
 * <p>
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * <p>
 * Alternatively, the contents of this file may be used under the terms
 * of the GNU Lesser General Public License (the "LGPL License"), in which
 * case the provisions of LGPL License are applicable instead of those
 * above. If you wish to allow use of your version of this file only
 * under the terms of the LGPL License and not to allow others to use
 * your version of this file under the MPL, indicate your decision by
 * deleting the provisions above and replace them with the notice and
 * other provisions required by the LGPL License. If you do not delete
 * the provisions above, a recipient may use your version of this file
 * under either the MPL or the LGPL License.
 * @since PowerPaint 1.0
 * @author Rebecca G. Bettencourt, Kreative Software
 */

package com.kreative.paint.palette;

import java.awt.*;
import com.kreative.paint.PaintContext;

public class SNFCompositeSelector extends PaintContextPanel {
	private static final long serialVersionUID = 1L;
	
	private static final Font font = new Font("SansSerif", Font.PLAIN, 9);
	
	private static final String[] ruleNames = {
		"composite.Src",
		"composite.SrcOver",
		"composite.SrcIn",
		"composite.SrcOut",
		"composite.SrcAtop",
		"composite.Xor",
		"composite.Dst",
		"composite.DstOver",
		"composite.DstIn",
		"composite.DstOut",
		"composite.DstAtop",
		"composite.Clear",
	};
	private static final int[] rules = {
		AlphaComposite.SRC,
		AlphaComposite.SRC_OVER,
		AlphaComposite.SRC_IN,
		AlphaComposite.SRC_OUT,
		AlphaComposite.SRC_ATOP,
		AlphaComposite.XOR,
		AlphaComposite.DST,
		AlphaComposite.DST_OVER,
		AlphaComposite.DST_IN,
		AlphaComposite.DST_OUT,
		AlphaComposite.DST_ATOP,
		AlphaComposite.CLEAR,
	};
	
	public SNFCompositeSelector(PaintContext pc) {
		super(pc, CHANGED_COMPOSITE|CHANGED_EDITING);
		setToolTipText(PaletteUtilities.messages.getString("snf.composite"));
	}
	
	public void update() {
		repaint();
	}
	
	protected void paintComponent(Graphics g) {
		Insets i = getInsets();
		int x = i.left;
		int y = i.top;
		int w = getWidth() - i.left - i.right;
		int h = getHeight() - i.top - i.bottom;
		Composite cx = pc.getEditedComposite();
		g.setColor(Color.black);
		g.fillRect(x+1, y+1, w-1, h-1);
		g.fillRect(x, y, w-1, h-1);
		g.setColor(Color.white);
		g.fillRect(x+1, y+1, w-3, h-3);
		g.setColor(Color.black);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		int ty = y + ((h-1) - fm.getHeight())/2 + fm.getAscent();
		if (cx instanceof AlphaComposite) {
			int r = ((AlphaComposite)cx).getRule();
			float a = ((AlphaComposite)cx).getAlpha();
			String rs = "";
			for (int j = 0; j < rules.length; j++) {
				if (r == rules[j]) {
					rs = PaletteUtilities.messages.getString(ruleNames[j]);
				}
			}
			String as = Integer.toString((int)Math.floor(a * 100f)) + "%";
			g.drawString(rs, x+4, ty);
			g.drawString(as, x+w-5-fm.stringWidth(as), ty);
		} else {
			g.drawString(cx.getClass().getSimpleName(), x+4, ty);
		}
	}
}
