package org.wit.festival.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.wit.festival.R
import org.wit.festival.databinding.SignUpActivityBinding
import org.wit.festival.models.UserModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: SignUpActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = SignUpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.btnSignUp.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()
            val username = binding.edtUsername.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser!!.uid
                            val user = UserModel(userId, username, email)
                            saveUserToFirestore(user)
                        } else {
                            Toast.makeText(baseContext, "Sign up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserToFirestore(user: UserModel) {
        firestore.collection("users").document(user.id).set(user)
            .addOnSuccessListener {
                Toast.makeText(baseContext, "Sign up successful", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { exception ->
                Toast.makeText(baseContext, "Error saving user: $exception", Toast.LENGTH_SHORT).show()
            }
    }
}
