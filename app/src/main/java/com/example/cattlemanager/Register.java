package com.example.cattlemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

public class Register extends AppCompatActivity {
    TextInputEditText editTextUsername, editTextEmail, editTextPassword , editTextConfirm;
    Button Register;
   // TextView Login;

    String User_NAME, User_EMAIL, User_PASSWORD ,User_CONFIRM;

    DocumentReference documentReference ;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername =findViewById(R.id.nameRR);
        editTextEmail = findViewById(R.id.emailRR);
        editTextPassword =findViewById(R.id.passwordRR);
        editTextConfirm =findViewById(R.id.cpasswordRR);
        TextView click_Login = findViewById(R.id.login_here);
        Register = findViewById(R.id.btn_Register);


        click_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toolbox.navigateTo(Register.this, Login.class);
            }
        });


        Register.setOnClickListener(v -> {

            String userName, userEmail, userPassword, userConfirmPassword;

            userName =editTextUsername.getText().toString().trim();
            userEmail = editTextEmail.getText().toString().trim();
            userPassword =editTextPassword.getText().toString().trim();
            userConfirmPassword =editTextConfirm.getText().toString().trim();

            User_NAME =userName;
            User_EMAIL = userEmail;
            User_PASSWORD = userPassword;
            User_CONFIRM =userConfirmPassword;






            if (!userName.isEmpty()&& !userEmail.isEmpty()&& !userPassword.isEmpty()){
                validator();
            }else{
                Toolbox.showToast(Register.getContext(),"No empty fields allowed!");
            }

        });

    }

    private  void validator(){

        if(Patterns.EMAIL_ADDRESS.matcher(User_EMAIL).matches()){
            if(User_PASSWORD.length()<6){
                Toolbox.showToast(Register.getContext(), "The password is too short.");
            }else{
                if(User_PASSWORD.equals(User_CONFIRM)){
                    createUserAccount();
                }else{
                    Toolbox.showToast(Register.getContext(), "The passwords are not matching.");
                }
            }

        }else{
            Toolbox.showToast(Register.getContext(), "The email is invalid.");
        }

    }
    private  void createUserAccount(){

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(User_EMAIL,User_PASSWORD)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            // user created succesfully
                            Toolbox.showToast(Register.this, "Account Created Successfully!");

                            String CURRENTUSER = firebaseAuth.getUid();

                            //add user to firebase database

                            User new_user= new User();
                            new_user.setUsername(User_NAME);
                            new_user.setEmail(User_EMAIL);
                            new_user.setPassword(User_PASSWORD);
                            documentReference = Toolbox.userCollection().document(CURRENTUSER);

                            documentReference.set(new_user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toolbox.showToast(Register.getContext(), "User Details saved successfully");
                                        Toolbox.navigateTo(Register.this, Login.class);
                                    }else {
                                        Toolbox.showToast(Register.getContext(), task.getException().getLocalizedMessage());
                                    }
                                }
                            });

                        }else {
                            // user creation failed

                            Toolbox.showToast(Register.this, task.getException().getLocalizedMessage());

                        }
                    }
                });

    }
}