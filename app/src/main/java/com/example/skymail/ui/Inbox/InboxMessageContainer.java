package com.example.skymail.ui.Inbox;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.skymail.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class InboxMessageContainer extends AppCompatActivity {
    private TextView subject,text,remail,name;
    private CircleImageView circleImageView;
    String email,picture,sub,messagetext,fullname;
    private Toolbar toolbar;
    public StreamDownloadTask downloadTask=null;
    private String url="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.message_container );
        setSupportActionBar( toolbar );
        Intent intent = getIntent();
        email = intent.getStringExtra( "remail" );
        picture = intent.getStringExtra( "picture" );
        sub = intent.getStringExtra( "subject" );
        messagetext = intent.getStringExtra( "text" );
        fullname = intent.getStringExtra( "FULLNAME" );
        url = intent.getStringExtra( "url" );


        subject = findViewById( R.id.SubjectHolder );
        text = findViewById( R.id.textholder );
        name = findViewById( R.id.senderfullname );
        remail = findViewById( R.id.recieveremail );
        circleImageView = findViewById( R.id.senderprofilepic );

        subject.setText( sub );
        text.setText( messagetext );
        Picasso.get().load( picture ).into( circleImageView );
        remail.setText( email );
        name.setText( fullname );


    }

    void downloadFile(Context ctx) throws IOException {
        //Download be download manager ta3 android bla firebase direct me URL
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference href = storage.getReferenceFromUrl(url);
        String Filename = href.getName();
        Uri uri= Uri.parse(url);
        DownloadManager downloadManager = (DownloadManager) getSystemService(ctx.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(getString(R.string.app_name));
        request.setDescription(getString(R.string.notification_download));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        //Dossier ta3 telechargement
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,Filename);
        request.setMimeType("*/*");
        downloadManager.enqueue(request);

        //code ta3 download bel firebase la tebghi tsiyi

       /* File rootPath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "file-name");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }

        final File localFile = new File(rootPath,Filename);
        //File localFile = File.createTempFile(getname(Filename), "."+getformat(Filename));
        //com.example.skymail.Data.io.showDialog(this,getname(Filename),"."+getformat(Filename));
        href.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //Dismiss Progress Dialog
                showNotification("T??l??chargement","T??l??chargement fini",0,0,getApplicationContext(),true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Dismiss Progress Dialog\\
                Toast.makeText(getApplicationContext(), "Probl??me r??seau", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                //calculating progress percentage
                long progress = ((taskSnapshot.getBytesTransferred() * 100) / taskSnapshot.getTotalByteCount());
                //displaying percentage in progress dialog
                showNotification("T??l??chargement","en cours",100,(int)progress,getApplicationContext(),true);
            }
        });*/
    }

    public String getname(String uri){
        String tmp="";
        String[] nm = uri.split("\\.");
        for(int i = 0;i<nm.length;i++){
            tmp=tmp+nm[i];
        }
        return tmp;
    }
    public String getformat(String uri){
        String tmp="";
        String[] nm = uri.split("\\.");
        tmp=nm[nm.length-1];
        return tmp;
    }

}
