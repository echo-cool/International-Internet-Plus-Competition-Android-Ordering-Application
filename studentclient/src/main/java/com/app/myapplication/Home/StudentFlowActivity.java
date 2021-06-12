
package com.app.myapplication.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.app.myapplication.R;
import com.app.myapplication.ShopActivity;
import com.app.myapplication.merchant.MerchantActivity;
import com.app.myapplication.views.PopSelect;
import com.getbase.floatingactionbutton.FloatingActionButton;



public class StudentFlowActivity extends AppCompatActivity {
    FloatingActionButton detailsBtn;
    FloatingActionButton sltBtn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_flow);
        detailsBtn = findViewById(R.id.flow_details);
        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentFlowActivity.this , SelectActivity.class);
                startActivity(i);
            }
        });
        sltBtn = findViewById(R.id.flow_select);
        sltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopSelect ps = new PopSelect(StudentFlowActivity.this);
                ps.showAtLocation(findViewById(R.id.back_cons),3,325,50);

            }
        });




    }

}