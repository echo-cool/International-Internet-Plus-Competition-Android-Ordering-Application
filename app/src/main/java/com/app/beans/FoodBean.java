package com.app.beans;


import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;


import com.app.utils.ViewUtils;

import java.io.Serializable;
import java.math.BigDecimal;

public class FoodBean implements Serializable{
	public int id;
	public String foodName;
	public String foodType;
	public String foodSummary;
	public int foodSale;
	public double foodPrice;
	public Bitmap foodImage;
}
