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


public class Addbook extends Fragment {

    EditText Bookname,bookcode,bookauther,bookprice,bookQuantity,Rackno,Subjectcode;
    String Book_name,book_code,book_auther,book_price,book_Quantity,Rack_no,Subject_code;
    Button Registation;
    String id="0";
    String isActive="true";
    String IsDelet="false";
    ProgressDialog progressDoalog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_addbook, container, false);
        Bookname=view.findViewById(R.id.BookName);
        bookcode=view.findViewById(R.id.Bookcode);
        bookauther=view.findViewById(R.id.BookAuthername);
        bookprice=view.findViewById(R.id.BookPrice);
        bookQuantity=view.findViewById(R.id.BookQuatitiy);
        Rackno=view.findViewById(R.id.Rack_No);
        Subjectcode=view.findViewById(R.id.SubjectCood);
        Registation=view.findViewById(R.id.registation);
        progressDoalog=new ProgressDialog(getActivity());
        fragmentdata();
        Registation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Bookname.getText().toString().isEmpty()){
                    Bookname.setError(" ");
                }
                else if(bookcode.getText().toString().isEmpty()){
                    bookcode.setError(" ");
                }else if(bookQuantity.getText().toString().isEmpty()){
                    bookQuantity.setError(" ");
                }else if(Subjectcode.getText().toString().isEmpty()){
                    Subjectcode.setError(" ");
                }else{
                    intt();
                    registerClass(id);
                }

            }
        });
        return view;
    }
    private void fragmentdata() {
        try{
            Bundle args = getArguments();
            id= args.getString("ID");
            Book_name= args.getString("BookName");
            book_code = args.getString("BookCode");
            book_auther= args.getString("AuthorName");
            book_price= args.getString("price");
            book_Quantity= args.getString("Quantity");
            Rack_no= args.getString("RacNo");
            Subject_code=args.getString("SubjectCode");
            isActive=args.getString("IsActive");
            IsDelet=args.getString("IsDeleted");

            Bookname.setText(Book_name);
            bookcode.setText(book_code);
            bookauther.setText(book_auther);
            bookprice.setText(book_price);
            bookQuantity.setText(book_Quantity);
            Rackno.setText(Rack_no);
            Subjectcode.setText(Subject_code);


        }
        catch (Exception e){
            id="0";
        }
    }

    private void registerClass(final String id) {
        progressDoalog.setMessage("Loading....");
        progressDoalog.setTitle("Fetch Data");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_InsertBookadd,
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
                params.put("BookName",Book_name);
                params.put("BookCode",book_code);
                params.put("AuthorName",book_auther);
                params.put("price",book_price);
                params.put("Quantity",book_Quantity);
                params.put("RacNo",Rack_no);
                params.put("SubjectCode",Subject_code);
                params.put("IsActive","true");
                params.put("IsDeleted ","false");
                Log.e("Anil",params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

    private void intt() {
        Book_name=Bookname.getText().toString();
        book_code=bookcode.getText().toString();
        book_auther=bookauther.getText().toString();
        book_price=bookprice.getText().toString();
        book_Quantity=bookQuantity.getText().toString();
        Rack_no=Rackno.getText().toString();
        Subject_code=Subjectcode.getText().toString();
    }

}
