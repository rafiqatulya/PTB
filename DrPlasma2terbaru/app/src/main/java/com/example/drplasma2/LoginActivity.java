package com.example.drplasma2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private static String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Button btlogin = findViewById(R.id.btnLogin);
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ((EditText) findViewById(R.id.editeknama)).getText().toString();
                String password = ((EditText) findViewById(R.id.editekuser)).getText().toString();
                loginWithEmailPassword(email, password);
            }
        });

        TextView tvRegis = findViewById(R.id.textvregis);
        tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegisPage = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toRegisPage);
            }
        });
    }

    private void loginWithEmailPassword(final String email, final String password) {
        Query query = databaseReference.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        if (user != null && user.getPassword().equals(password)) {
                            currentUserId = user.getUserId();
                            Log.d("LoginActivity", "User ID saved: " + currentUserId);

                            saveCurrentUserIdToSharedPreferences(currentUserId);

                            Intent toProfileFragment = new Intent(LoginActivity.this, MainActivity.class);
                            toProfileFragment.putExtra("FragmentToLoad", "ProfilFragment");
                            startActivity(toProfileFragment);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Email not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveCurrentUserIdToSharedPreferences(String userId) {
        getPreferences(MODE_PRIVATE).edit().putString("currentUserId", userId).apply();
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }
}
