package com.peterwayne.toeic900.Fragment;

public class TestPartTwoFragment extends PartTwoFragment{
    @Override
    public void passDataToView() {
        super.passDataToView();
        txt_number.setText(data.getNumber()+".");
    }
}
