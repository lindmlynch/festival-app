package org.wit.festival.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.wit.festival.databinding.LoginActivityBinding
import org.wit.festival.models.UserModel

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.edtEmail.text.toString().trim()
            val password = binding.edtPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val userId = auth.currentUser?.uid
                            userId?.let { id ->
                                firestore.collection("users").document(id).get()
                                    .addOnSuccessListener { documentSnapshot ->
                                        val user = documentSnapshot.toObject(UserModel::class.java)

                                        user?.let {

                                            val intent = Intent(this, FestivalActivity::class.java).apply {
                                                putExtra("user", it)
                                            }
                                            startActivity(intent)
                                            finish()
                                        }
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(baseContext, "Failed to fetch user details.", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {

                            Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }

    }
}

