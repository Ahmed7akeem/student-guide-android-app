package com.example.studentasu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class calculateGPA extends AppCompatActivity {

    LinearLayout parent, hema;
    Button addCourse, calcGpa;
    TextView gpaView, creditHoursView, grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.black));

        setContentView(R.layout.activity_calculate_gpa);
        parent = findViewById(R.id.parent_layout);
        addCourse = findViewById(R.id.add_course);
        calcGpa = findViewById(R.id.calc_gpa);
        gpaView = findViewById(R.id.gpa);
        creditHoursView = findViewById(R.id.credit_hours);
        grade = findViewById(R.id.grade);
        hema =findViewById(R.id.hemaTaha);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a reference to the layout inflater
                LayoutInflater inflater = LayoutInflater.from(calculateGPA.this);
                // Inflate the layout from a file
                View childView = inflater.inflate(R.layout.course_form, parent, false);

                // Add the inflated layout as a child to the parent view
                parent.addView(childView);

                Button remove = childView.findViewById(R.id.remove_box);

                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        parent.removeView(childView);
                    }
                });
            }
        });
        calcGpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcGpa();
            }
        });

    }

    void calcGpa(){
        double gpa = 0;
        int cretidHours = 0;
        //StudentCoursesModel sc = new StudentCoursesModel(MainActivity.this);
        // int totalCH = sc.getTotalCreditHoures();
        //if no child
        if(parent.getChildCount() == 0) {
            Toast.makeText(calculateGPA.this, "Please add courses first", Toast.LENGTH_LONG);
            hema.setBackgroundColor(ContextCompat.getColor(calculateGPA.this, R.color.red));
            return;
        }

        for(int i = 0; i < parent.getChildCount(); i++){
            View child = parent.getChildAt(i);
            Spinner ch = child.findViewById(R.id.spinner_credit_hours);
            Spinner grad = child.findViewById(R.id.spinner_course_grade);

            if(ch.getSelectedItem().toString().equals("Credit Hours") ||
                    grad.getSelectedItem().toString().equals("Course GPA")){
                Toast.makeText(calculateGPA.this, "Please complete all values", Toast.LENGTH_SHORT);
                hema.setBackgroundColor(ContextCompat.getColor(calculateGPA.this, R.color.red));
                return;
            }
            cretidHours += Integer.parseInt(ch.getSelectedItem().toString());
            gpa += Integer.parseInt(ch.getSelectedItem().toString()) * getGrade(grad.getSelectedItem().toString());
        }
        //set credit hours to the view
        creditHoursView.setText(""+cretidHours);

        //set gpa to the view
        gpaView.setText(String.format("%.3f", gpa/cretidHours));

        if(gpa/cretidHours == 4)
        {
            grade.setText("A");
        }
        else if(gpa/cretidHours < 4 && gpa/cretidHours >= 3.67)
        {
            grade.setText("A-");
        }
        else if(gpa/cretidHours < 3.67 && gpa/cretidHours >= 3.33)
        {
            grade.setText("B+");
        }
        else if(gpa/cretidHours < 3.33 && gpa/cretidHours >= 3.00)
        {
            grade.setText("B");
        }
        else if(gpa/cretidHours < 3.00 && gpa/cretidHours >= 2.67)
        {
            grade.setText("C+");
        }
        else if(gpa/cretidHours < 2.67 && gpa/cretidHours >= 2.33)
        {
            grade.setText("C");
        }
        else if(gpa/cretidHours < 2.33 && gpa/cretidHours >= 2)
        {
            grade.setText("D");
        }
        else if(gpa/cretidHours < 2.00)
        {
            grade.setText("F");
        }
        hema.setBackgroundColor(ContextCompat.getColor(calculateGPA.this, R.color.white));
    }

    double getGrade(String x){
        if(x.equals("A")) return 4.0;
        else if(x.equals("A-")) return 3.67;
        else if(x.equals("B+")) return 3.33;
        else if(x.equals("B")) return 3.00;
        else if(x.equals("C+")) return 2.67;
        else if(x.equals("C")) return 2.33;
        else if(x.equals("D")) return 2;
        else return 0.0;
    }
}