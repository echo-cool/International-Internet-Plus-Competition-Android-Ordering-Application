
package com.app.myapplication.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.myapplication.R;
import com.app.myapplication.views.PopSelect;
import com.app.myapplication.views.SpotMarkerView;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.snackbar.Snackbar;


public class StudentFlowActivity extends AppCompatActivity {
    FloatingActionButton detailsBtn;
    FloatingActionButton sltBtn;
    private View spotMarkerView;
    View thirdBu;
    View libraryBu;
    TextView address;
    SpotMarkerView spv;
    FloatingActionsMenu fltMune;
    PopSelect ps;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_flow);
        spotMarkerView = findViewById(R.id.fourth_building_point);
        thirdBu = findViewById(R.id.third_building_point);
        thirdBu.setVisibility(View.GONE);
        TextView bdName = (TextView)thirdBu.findViewById(R.id.name_devices);
        bdName.setText("第三教学楼");
        libraryBu = findViewById(R.id.library_point);
        libraryBu.setVisibility(View.GONE);
        TextView lbName = (TextView)libraryBu.findViewById(R.id.name_devices);
        lbName.setText("图书馆");
        spotMarkerView.setVisibility(View.GONE);
        address = findViewById(R.id.name_devices);
        spv = findViewById(R.id.spot_marker_view);
        spv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spv.stopLoadingAnima();
                spv.transactionAnimWithMarker().addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        spv.startRippleAnima();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                Snackbar.make(v,"当前四教人数过多，如去自习，请前往图书馆",Snackbar.LENGTH_LONG)
                        .setAction("好的", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //..........用户点击好的之后，程序要做的事
                            }
                        })
                        .show();


        }
            });
        fltMune = findViewById(R.id.floatingActionsMenu);
        detailsBtn = findViewById(R.id.flow_details);
        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StudentFlowActivity.this , SelectActivity.class);
                startActivity(i);
                fltMune.collapse();
            }
        });
        sltBtn = findViewById(R.id.flow_select);
        sltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ps = new PopSelect(StudentFlowActivity.this);
                ps.showAtLocation(findViewById(R.id.back_cons),3,325,50);
                fltMune.collapse();
            }
        });




    }

}