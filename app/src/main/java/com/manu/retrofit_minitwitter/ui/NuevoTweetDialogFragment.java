package com.manu.retrofit_minitwitter.ui;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.manu.retrofit_minitwitter.R;
import com.manu.retrofit_minitwitter.common.Constantes;
import com.manu.retrofit_minitwitter.common.SharedPreferencesManager;
import com.manu.retrofit_minitwitter.data.TweetViewModel;

public class NuevoTweetDialogFragment extends DialogFragment implements View.OnClickListener {

    ImageView ivClose, ivAvatar;
    Button btnTwittear;
    EditText edMensaje;


    // En este metodo, aplicamos el estilo
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Aplicamos el estilo
        setStyle(DialogFragment.STYLE_NORMAL,R.style.FullscreenDialogStyle);

    }


    // metodo que nos permite crear la vista personalizado
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Vista a la que le pasamos el layout que queremos cargar, el contenedor(Viewgroup) que recibimos,
        // y false que indica que el layout va a ser el que se cargue
        View view = inflater.inflate(R.layout.nuevo_tweet_full_dialog, container, false);

        ivClose = view.findViewById(R.id.imageViewClose);
        ivAvatar = view.findViewById(R.id.imageViewAvatar);
        btnTwittear = view.findViewById(R.id.buttonTwittear);
        edMensaje = view.findViewById(R.id.editTextMensaje);

        //Eventos
        btnTwittear.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        // Seteamos la imagen del usario de perfil
       String photoURL = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_PHOTOURL);
       if(!photoURL.isEmpty()) {
           Glide.with(getActivity())
                   .load(Constantes.API_MINITWITTER_FILES_URL + photoURL)
                   .into(ivAvatar);
       }
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String mensaje =  edMensaje.getText().toString();
        if(id == R.id.buttonTwittear){

            if(mensaje.isEmpty()){
                Toast.makeText(getActivity(), "Debe escribir un texto en el emnsaje", Toast.LENGTH_SHORT).show();
            }else {

                // Llamada al viewModel
                TweetViewModel tweetViewModel = ViewModelProviders
                        .of(getActivity()).get(TweetViewModel.class);
                // Insertar mensaje
                tweetViewModel.insertTweet(mensaje);
                getDialog().dismiss();

            }
        }else if(id == R.id.imageViewClose){
            if(!mensaje.isEmpty()){
                showDialogConfirm();
            }else {
                getDialog().dismiss();
            }

        }
    }

    private void showDialogConfirm() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("¿Desea realmente eliminar el Tweet? El mensaje se borrará")
                .setTitle("cancelar tweet");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                dialog.dismiss();
                getDialog().dismiss();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

// 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();

        dialog.show();
    }
}
