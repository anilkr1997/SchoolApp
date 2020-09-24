package com.example.schoolapp.libarymanagment.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.schoolapp.R;
import com.example.schoolapp.URLs;
import com.example.schoolapp.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Issuebookstu extends Fragment {
    EditText bookid,Studentid,issueedDate ,Issuedforday ,SubmissionDate ,IssuedBy ,Remarks ;
    String book_id,Student_id,issueed_Date ,Issued_for_day ,Submission_Date ,Issued_By ,Remark_s ;
    String id="0";
    ProgressDialog progressDoalog;
    Button registation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_issuebookstu, container, false);
        bookid =view.findViewById(R.id.BookId);
        Studentid=view.findViewById(R.id.StudentId);
        issueedDate =view.findViewById(R.id.issueedDate);
        Issuedforday =view.findViewById(R.id.Issuedforday);
        SubmissionDate =view.findViewById(R.id.SubmissionDate);
        IssuedBy =view.findViewById(R.id.IssuedBy);
        Remarks=view.findViewById(R.id.Remarks);
        registation=view.findViewById(R.id.registation);
        progressDoalog=new ProgressDialog(getActivity());
        fragmentdata();
        registation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookid.getText().toString().equals("")){
                    bookid.setError(" "); }
                else if(Studentid.getText().toString().equals("")){
                    Studentid.setError(" ");
                }
                else if(issueedDate.getText().toString().equals("")){
                    issueedDate.setError(" ");}
                else if(Issuedforday.getText().toString().equals("")){
                    Issuedforday.setError(" "); }
                else if(SubmissionDate.getText().toString().equals("")){
                    SubmissionDate.setError(" "); }
                else if(IssuedBy.getText().toString().equals("")){
                    IssuedBy.setError(" "); }
                else {
                    intt();
                    registerClass(id); }
            }
        });

        return view;
    }
    private void fragmentdata() {
        try{
            Bundle args = getArguments();
            id= args.getString("ID");
            book_id=args.getString("StudentId");
            Student_id=args.getString("BookId");
            issueed_Date=args.getString("issueedDate");
            Issued_for_day=args.getString("Issuedforday");
            Submission_Date=args.getString("SubmissionDate");
            Issued_By=args.getString("IssuedBy");
            Remark_s=args.getString("Remarks");
            bookid.setText(book_id);
            bookid.setEnabled(false);
            Studentid.setText(Student_id);
            Studentid.setEnabled(false);
            issueedDate.setText(issueed_Date);
            Issuedforday .setText(Issued_for_day);
            SubmissionDate .setText(Submission_Date);
            IssuedBy .setText(Issued_By);
            Remarks .setText(Remark_s);
        }
        catch (Exception e){
            id="0";
        }
    }
    private void intt() {
        book_id=bookid.getText().toString();
        Student_id=Studentid.getText().toString();
        issueed_Date=issueedDate.getText().toString();
        Issued_for_day=Issuedforday.getText().toString();
        Submission_Date=SubmissionDate.getText().toString();
        Issued_By=IssuedBy.getText().toString();
        Remark_s=Remarks.getText().toString();
    }
    private void registerClass(final String id) {
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_InsertBookIssuedetails,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj=new JSONObject(response);
                            progressDoalog.dismiss();
                            if(obj.getString("IsSuccess").equalsIgnoreCase("true"))
                            {
                                Toast.makeText(getActivity(), ""+obj.getString("Message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDoalog.dismiss();
                        //  Toast.makeText(getActivity(),"ERROR"+ error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("ID",id);
                params.put("StudentId",Student_id);
                params.put("BookId",book_id);
                params.put("issueedDate",issueed_Date);
                params.put("Issuedforday",Issued_for_day);
                params.put("SubmissionDate",Submission_Date);
                params.put("IssuedBy",Issued_By);
                params.put("Remarks",Remark_s);
                Log.e("Anil",params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

}
