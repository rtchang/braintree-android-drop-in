package com.braintreepayments.api.dropin.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.braintreepayments.api.dropin.R;
import com.braintreepayments.api.dropin.interfaces.AddPaymentUpdateListener;

import java.util.Arrays;
import java.util.List;

public class AddCardView extends RelativeLayout {

    private EditText mCardNumber;
    private GridLayout mAcceptedCards;
    private AnimatedButtonView mAnimatedButtonView;
    private AddPaymentUpdateListener mListener;

    private final List<Integer> validCardTypes = Arrays.asList(
            R.drawable.bt_visa,
            R.drawable.bt_mastercard,
            R.drawable.bt_discover,
            R.drawable.bt_amex,
            R.drawable.bt_diners,
            R.drawable.bt_visa, //TODO UnionPay
            R.drawable.bt_jcb,
            R.drawable.bt_maestro

    );

    public AddCardView(Context context) {
        super(context);
        init(context);
    }

    public AddCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AddCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        if(isInEditMode()) {
            return;
        }
        LayoutInflater.from(context).inflate(R.layout.bt_add_card, this, true);
        mCardNumber = (EditText)findViewById(R.id.card_number);
        mAcceptedCards = (GridLayout)findViewById(R.id.accepted_cards);
        mAnimatedButtonView = (AnimatedButtonView) findViewById(R.id.animated_button_view);

        updateValidCardTypes();

        mAnimatedButtonView.setNextButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onPaymentUpdated(AddCardView.this);
                }
            }
        });
    }

    private void updateValidCardTypes() {
        mAcceptedCards.removeAllViews();
        for(int drawable : validCardTypes) {
            ImageView iv = new ImageView(getContext());
            iv.setImageDrawable(getResources().getDrawable(drawable));
            mAcceptedCards.addView(iv);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        mAnimatedButtonView.setVisibility(visibility);
    }

    public String getNumber() {
        return mCardNumber.getText().toString();
    }

    public void setAddPaymentUpdatedListener(AddPaymentUpdateListener listener) {
        mListener = listener;
    }
}
