package com.example.animalkingdom;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.animalkingdom.helper.FirebaseHelper;
import com.example.animalkingdom.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.santalu.maskara.widget.MaskEditText;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class NewProfileActivity extends AppCompatActivity {

	private EditText edt_nome;
	private MaskEditText edt_telefone;
	private EditText edt_email;
	private ProgressBar progressBar;

	private final int SELECAO_GALERIA = 100;

	private ImageView imagem_perfil;

	private String caminhoImagem;

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_perfil);

		startsComponents();
		configClicks();
		retrieveProfile();
	}

	private void saveImageProfile() {
		StorageReference storageReference = FirebaseHelper.getStorageReference()
				.child("images")
				.child("profile")
				.child(FirebaseHelper.getIdFirebase() + ".jpeg");

		UploadTask uploadTask = storageReference.putFile(Uri.parse(caminhoImagem));
		uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {
			String urlImagem = task.getResult().toString();
			user.setProfileImage(urlImagem);
			user.salvar(progressBar, getBaseContext());
		})).addOnFailureListener(e -> Toast.makeText(this, "Erro de upload, tente novamente mais tarde.", Toast.LENGTH_SHORT).show());
	}

	private void retrieveProfile() {
		progressBar.setVisibility(View.VISIBLE);
		DatabaseReference perfilRef = FirebaseHelper.getDatabaseReference()
				.child("users")
				.child(FirebaseHelper.getIdFirebase());
		perfilRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				user = snapshot.getValue(User.class);
				configDaata();
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	private void configDaata() {

		edt_nome.setText(user.getName());
		edt_telefone.setText(android.telephony.PhoneNumberUtils.formatNumber( user.getTelefone()));
		edt_email.setText(user.getEmail());

		progressBar.setVisibility(View.GONE);

		if (user.getImgprofile() != null) {
			Picasso.get().load(user.getImgprofile()).into(imagem_perfil);
		}

	}

	public void validaData(View view) {
		String nome = edt_nome.getText().toString();
		String t=edt_telefone.getText().toString();
	//	Toast.makeText(this,String.valueOf(t.length()),Toast.LENGTH_LONG).show();
		if (!nome.isEmpty()) {
			if (!t.isEmpty()) {
				if (t.length() == 13) {

					progressBar.setVisibility(View.VISIBLE);

					if (caminhoImagem != null) {
						saveImageProfile();
					} else {
						user.salvar(progressBar, getBaseContext());
					}

				} else {
					edt_telefone.requestFocus();
					edt_telefone.setError("Telefone Invalid");
				}
			} else {
				edt_telefone.requestFocus();
				edt_telefone.setError("Enter Telephone.");
			}
		} else {
		edt_nome.requestFocus();
		edt_nome.setError("Enter Name.");
	}
	}

	private void configClicks() {
		imagem_perfil.setOnClickListener(v -> checkPermissionGallery());
		findViewById(R.id.ib_back).setOnClickListener(v -> finish());
	}

	private void checkPermissionGallery() {
		PermissionListener permissionListener = new PermissionListener() {
			@Override
			public void onPermissionGranted() {
				openGallery();
			}

			private void openGallery() {
				Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, SELECAO_GALERIA);
			}

			@Override
			public void onPermissionDenied(List<String> deniedPermissions) {
					Toast.makeText(NewProfileActivity.this, "Permissions Denied.", Toast.LENGTH_SHORT).show();
			}
		};

		TedPermission.create()
				.setPermissionListener(permissionListener)
				.setDeniedTitle("permissions denied")
				.setDeniedMessage("If you don't accept the permission, you won't be able to access the device's Gallery\n" +
						"want to enable permission now?")
				.setDeniedCloseButtonText("No")
				.setGotoSettingButtonText("Yes")
				.setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
				.check();
	}

	private void startsComponents() {
		TextView text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("Profile");

		edt_nome = findViewById(R.id.edt_nome);
		edt_telefone = findViewById(R.id.edt_telefone);
		edt_email = findViewById(R.id.edt_email);
		progressBar = findViewById(R.id.progressBar);

		imagem_perfil = findViewById(R.id.imagem_perfil);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			Uri imagemSelecionada = data.getData();
			Bitmap imagemRecuperada;

			try {
				imagemRecuperada = MediaStore.Images.Media.getBitmap(getContentResolver(), imagemSelecionada);
				imagem_perfil.setImageBitmap(imagemRecuperada);

				caminhoImagem = imagemSelecionada.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}