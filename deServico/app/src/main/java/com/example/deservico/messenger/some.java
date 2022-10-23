// ChatFromItem.java
package com.example.deservico.message;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.deservico.R.id;
import com.example.deservico.messenger.MessengerUserStruct;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.xwray.groupie.GroupieViewHolder;
import com.xwray.groupie.Item;
import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;

public final class ChatFromItem extends Item {
    @NotNull
    private final String text;
    @NotNull
    private final MessengerUserStruct user;
    public ChatFromItem(String text, MessengerUserStruct user) {

        this.text = text;
        this.user = user;
    }
    public void bind(GroupieViewHolder viewHolder, int position) {

        View var10000;
        TextView chatfromtext;
        ImageView var6;
        if (text.indexOf("image/") != -1) {

            chatfromtext = (TextView)viewHolder.itemView.findViewById(id.chatfromtext);
            Intrinsics.checkNotNullExpressionValue(chatfromtext, "viewHolder.itemView.chatfromtext");
            chatfromtext.setVisibility(8);
            var6 = (ImageView)viewHolder.itemView.findViewById(id.chatfromimg);
            Intrinsics.checkNotNullExpressionValue(var6, "viewHolder.itemView.chatfromimg");
            var6.setVisibility(0);
            var6 = (ImageView)viewHolder.itemView.findViewById(id.chatfromimg);
            String var3 = this.text;
            int var4 = StringsKt.indexOf((CharSequence)this.text, ";base64,", 0, false, 6, (Object)null) + 8;
            if (var3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }

            String var10001 = var3.substring(var4);
            Intrinsics.checkNotNullExpressionValue(var10001, "(this as java.lang.String).substring(startIndex)");
            var6.setImageBitmap(ChatLogActivityKt.convertBase64ToBitmap(var10001));
        } else {
           chatfromtext = (TextView)viewHolder.itemView.findViewById(id.chatfromtext);
            Intrinsics.checkNotNullExpressionValue(chatfromtext, "viewHolder.itemView.chatfromtext");
            chatfromtext.setVisibility(0);
            var6 = (ImageView)viewHolder.itemView.findViewById(id.chatfromimg);
            Intrinsics.checkNotNullExpressionValue(var6, "viewHolder.itemView.chatfromimg");
            var6.setVisibility(8);
            chatfromtext = (TextView)viewHolder.itemView.findViewById(id.chatfromtext);
            Intrinsics.checkNotNullExpressionValue(chatfromtext, "viewHolder.itemView.chatfromtext");
            chatfromtext.setText((CharSequence)this.text);
        }

       chatfromtext = (TextView)viewHolder.itemView.findViewById(id.chatfromtext);
        Intrinsics.checkNotNullExpressionValue(chatfromtext, "viewHolder.itemView.chatfromtext");
        chatfromtext.setText((CharSequence)this.text);
        if (Intrinsics.areEqual(this.user.getProfileImageUrl(), "") ^ true) {
            RequestCreator var8 = Picasso.get().load(this.user.getProfileImageUrl());
            View var7 = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(var7, "viewHolder.itemView");
            var8.into((CircleImageView)var7.findViewById(id.imagechatfromrow));
        } else {
            viewHolder.itemView = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(viewHolder.itemView, "viewHolder.itemView");
            ((CircleImageView)viewHolder.itemView.findViewById(id.imagechatfromrow)).setImageResource(700140);
        }

    }

    public int getLayout() {
        return 1300030;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    @NotNull
    public final MessengerUserStruct getUser() {
        return this.user;
    }


}
// ChatLogActivityKt.java
package com.example.deservico.message;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.util.Base64;
        import java.nio.charset.Charset;
        import kotlin.Metadata;
        import kotlin.jvm.internal.Intrinsics;
        import kotlin.text.Charsets;
        import org.jetbrains.annotations.NotNull;
        import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 4, 3},
        bv = {1, 0, 3},
        k = 2,
        d1 = {"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a\u0010\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"},
        d2 = {"convertBase64ToBitmap", "Landroid/graphics/Bitmap;", "b64", "", "app_debug"}
)
public final class ChatLogActivityKt {
    @Nullable
    public static final Bitmap convertBase64ToBitmap(@NotNull String b64) {
        Intrinsics.checkNotNullParameter(b64, "b64");
        Charset var3 = Charsets.UTF_8;
        byte[] var10000 = b64.getBytes(var3);
        Intrinsics.checkNotNullExpressionValue(var10000, "(this as java.lang.String).getBytes(charset)");
        byte[] imageAsBytes = Base64.decode(var10000, 0);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
// ChatLogActivity.java
package com.example.deservico.message;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Parcelable;
        import android.provider.MediaStore.Images.Media;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import androidx.appcompat.app.ActionBar;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;
        import androidx.recyclerview.widget.RecyclerView;
        import androidx.recyclerview.widget.RecyclerView.Adapter;
        import com.example.deservico.R.id;
        import com.example.deservico.messenger.MessengerUserStruct;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.xwray.groupie.Group;
        import com.xwray.groupie.GroupAdapter;
        import java.util.HashMap;
        import kotlin.Metadata;
        import kotlin.jvm.internal.DefaultConstructorMarker;
        import kotlin.jvm.internal.Intrinsics;
        import org.jetbrains.annotations.NotNull;
        import org.jetbrains.annotations.Nullable;

@Metadata(
        mv = {1, 4, 3},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0006\u0018\u0000 +2\u00020\u0001:\u0001+B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0017H\u0002J\"\u0010\u0018\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\u0012\u0010\u001e\u001a\u00020\u00152\b\u0010\u001f\u001a\u0004\u0018\u00010 H\u0014J-\u0010!\u001a\u00020\u00152\u0006\u0010\u0019\u001a\u00020\u001a2\u000e\u0010\"\u001a\n\u0012\u0006\b\u0001\u0012\u00020$0#2\u0006\u0010%\u001a\u00020&H\u0016¢\u0006\u0002\u0010'J\u000e\u0010(\u001a\u00020\u00152\u0006\u0010)\u001a\u00020$J\b\u0010*\u001a\u00020\u0015H\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006,"},
        d2 = {"Lcom/example/deservico/message/ChatLogActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "adapter", "Lcom/xwray/groupie/GroupAdapter;", "Lcom/xwray/groupie/GroupieViewHolder;", "getAdapter", "()Lcom/xwray/groupie/GroupAdapter;", "selectedPhotoUri", "Landroid/net/Uri;", "getSelectedPhotoUri", "()Landroid/net/Uri;", "setSelectedPhotoUri", "(Landroid/net/Uri;)V", "touser", "Lcom/example/deservico/messenger/MessengerUserStruct;", "getTouser", "()Lcom/example/deservico/messenger/MessengerUserStruct;", "setTouser", "(Lcom/example/deservico/messenger/MessengerUserStruct;)V", "ListenForMessage", "", "isRequestPermissionAllowed", "", "onActivityResult", "requestCode", "", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onRequestPermissionsResult", "permissions", "", "", "grantResults", "", "(I[Ljava/lang/String;[I)V", "performSendMessage", "string", "requestForReadStoragePermission", "Companion", "app_debug"}
)
public final class ChatLogActivity extends AppCompatActivity {
    @NotNull
    private final GroupAdapter adapter = new GroupAdapter();
    @Nullable
    private MessengerUserStruct touser;
    @Nullable
    private Uri selectedPhotoUri;
    private static final int READ_STORAGE_PERMISSION = 1;
    private static final int GALLERY = 2;
    private static final String IMG_URI = "IMG";
    private static final int FIRST_ACTIVITY_REQUEST_CODE = 3;
    @NotNull
    public static final ChatLogActivity.Companion Companion = new ChatLogActivity.Companion((DefaultConstructorMarker)null);
    private HashMap _$_findViewCache;

    @NotNull
    public final GroupAdapter getAdapter() {
        return this.adapter;
    }

    @Nullable
    public final MessengerUserStruct getTouser() {
        return this.touser;
    }

    public final void setTouser(@Nullable MessengerUserStruct var1) {
        this.touser = var1;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(1300105);
        this.touser = (MessengerUserStruct)this.getIntent().getParcelableExtra(NewMessageActivity.Companion.getUSER_KEY());
        ActionBar var10000 = this.getSupportActionBar();
        if (var10000 != null) {
            MessengerUserStruct var10001 = this.touser;
            var10000.setTitle((CharSequence)(var10001 != null ? var10001.getUsername() : null));
        }

        this.ListenForMessage();
        ((Button)this._$_findCachedViewById(id.sendbtn_chat_log)).setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                ChatLogActivity var10000 = ChatLogActivity.this;
                EditText var10001 = (EditText)ChatLogActivity.this._$_findCachedViewById(id.editTextTextPersonName);
                Intrinsics.checkNotNullExpressionValue(var10001, "editTextTextPersonName");
                var10000.performSendMessage(var10001.getText().toString());
            }
        }));
        RecyclerView var2 = (RecyclerView)this._$_findCachedViewById(id.recyclerview_chat_log);
        Intrinsics.checkNotNullExpressionValue(var2, "recyclerview_chat_log");
        var2.setAdapter((Adapter)this.adapter);
        ((Button)this._$_findCachedViewById(id.pic)).setOnClickListener((OnClickListener)(new OnClickListener() {
            public final void onClick(View it) {
                if (ChatLogActivity.this.isRequestPermissionAllowed()) {
                    Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
                    ChatLogActivity.this.startActivityForResult(intent, ChatLogActivity.GALLERY);
                } else {
                    ChatLogActivity.this.requestForReadStoragePermission();
                }

            }
        }));
    }

    private final boolean isRequestPermissionAllowed() {
        int result = ContextCompat.checkSelfPermission((Context)this, "android.permission.READ_EXTERNAL_STORAGE");
        return result == 0;
    }

    private final void requestForReadStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)this, "android.permission.READ_EXTERNAL_STORAGE".toString())) {
            Toast.makeText((Context)this, (CharSequence)"Request Required to Upload Profile Photo", 1).show();
        }

        ActivityCompat.requestPermissions((Activity)this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, READ_STORAGE_PERMISSION);
    }

    private final void ListenForMessage() {
        FirebaseAuth var10000 = FirebaseAuth.getInstance();
        Intrinsics.checkNotNullExpressionValue(var10000, "FirebaseAuth.getInstance()");
        String fromid = var10000.getUid();
        MessengerUserStruct var4 = this.touser;
        String toid = var4 != null ? var4.getUid() : null;
        DatabaseReference var5 = FirebaseDatabase.getInstance().getReference("/user-messages/" + fromid + '/' + toid);
        Intrinsics.checkNotNullExpressionValue(var5, "FirebaseDatabase.getInst…-messages/$fromid/$toid\")");
        DatabaseReference ref = var5;
        ref.addChildEventListener((ChildEventListener)(new ChildEventListener() {
            public void onChildAdded(@NotNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Intrinsics.checkNotNullParameter(snapshot, "snapshot");
                ChatMessage chatMessage = (ChatMessage)snapshot.getValue(ChatMessage.class);
                if (chatMessage != null) {
                    String var10000 = chatMessage.getFromId();
                    FirebaseAuth var10001 = FirebaseAuth.getInstance();
                    Intrinsics.checkNotNullExpressionValue(var10001, "FirebaseAuth.getInstance()");
                    String var10003;
                    GroupAdapter var5;
                    if (Intrinsics.areEqual(var10000, var10001.getUid())) {
                        MessengerUserStruct currentUser = LatestMessageActivity.Companion.getCurrentUser();
                        var5 = ChatLogActivity.this.getAdapter();
                        var10003 = chatMessage.getText();
                        Intrinsics.checkNotNull(currentUser);
                        var5.add((Group)(new ChatToItem(var10003, currentUser)));
                    } else {
                        var5 = ChatLogActivity.this.getAdapter();
                        var10003 = chatMessage.getText();
                        MessengerUserStruct var10004 = ChatLogActivity.this.getTouser();
                        Intrinsics.checkNotNull(var10004);
                        var5.add((Group)(new ChatFromItem(var10003, var10004)));
                    }

                    ((RecyclerView)ChatLogActivity.this._$_findCachedViewById(id.recyclerview_chat_log)).scrollToPosition(ChatLogActivity.this.getAdapter().getItemCount() - 1);
                }

            }

            public void onCancelled(@NotNull DatabaseError error) {
                Intrinsics.checkNotNullParameter(error, "error");
            }

            public void onChildChanged(@NotNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Intrinsics.checkNotNullParameter(snapshot, "snapshot");
            }

            public void onChildMoved(@NotNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Intrinsics.checkNotNullParameter(snapshot, "snapshot");
            }

            public void onChildRemoved(@NotNull DataSnapshot snapshot) {
                Intrinsics.checkNotNullParameter(snapshot, "snapshot");
            }
        }));
    }

    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Intrinsics.checkNotNullParameter(permissions, "permissions");
        Intrinsics.checkNotNullParameter(grantResults, "grantResults");
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_STORAGE_PERMISSION) {
            boolean var6 = false;
            if (grantResults.length != 0 && grantResults[0] == 0) {
                Toast.makeText((Context)this, (CharSequence)"Permission has been Granted", 0).show();
            } else {
                Toast.makeText((Context)this, (CharSequence)"Storage Permission Denied", 0).show();
            }
        }

    }

    @Nullable
    public final Uri getSelectedPhotoUri() {
        return this.selectedPhotoUri;
    }

    public final void setSelectedPhotoUri(@Nullable Uri var1) {
        this.selectedPhotoUri = var1;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY && resultCode == -1) {
            try {
                Intrinsics.checkNotNull(data);
                if (data.getData() != null) {
                    this.selectedPhotoUri = data.getData();
                    MessengerUserStruct user = (MessengerUserStruct)this.getIntent().getParcelableExtra(NewMessageActivity.Companion.getUSER_KEY());
                    Intent intent = new Intent((Context)this, ChatLogImgActivity.class);
                    intent.putExtra("user", (Parcelable)user);
                    intent.putExtra(IMG_URI, String.valueOf(data.getData()));
                    this.startActivityForResult(intent, FIRST_ACTIVITY_REQUEST_CODE);
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        }

    }

    public final void performSendMessage(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        CharSequence var2 = (CharSequence)string;
        if (var2.length() > 0) {
            FirebaseAuth var10000 = FirebaseAuth.getInstance();
            Intrinsics.checkNotNullExpressionValue(var10000, "FirebaseAuth.getInstance()");
            String fromId = var10000.getUid();
            MessengerUserStruct user = (MessengerUserStruct)this.getIntent().getParcelableExtra(NewMessageActivity.Companion.getUSER_KEY());
            String toId = user != null ? user.getUid() : null;
            if (fromId == null) {
                return;
            }

            DatabaseReference var12 = FirebaseDatabase.getInstance().getReference("/user-messages/" + fromId + '/' + toId).push();
            Intrinsics.checkNotNullExpressionValue(var12, "FirebaseDatabase.getInst…es/$fromId/$toId\").push()");
            DatabaseReference ref = var12;
            var12 = FirebaseDatabase.getInstance().getReference("/user-messages/" + toId + '/' + fromId).push();
            Intrinsics.checkNotNullExpressionValue(var12, "FirebaseDatabase.getInst…es/$toId/$fromId\").push()");
            DatabaseReference toref = var12;
            String var10002 = ref.getKey();
            Intrinsics.checkNotNull(var10002);
            Intrinsics.checkNotNullExpressionValue(var10002, "ref.key!!");
            Intrinsics.checkNotNull(toId);
            ChatMessage chatmessage = new ChatMessage(var10002, string, fromId, toId, System.currentTimeMillis() / (long)1000);
            ref.setValue(chatmessage).addOnSuccessListener((OnSuccessListener)(new OnSuccessListener() {
                // $FF: synthetic method
                // $FF: bridge method
                public void onSuccess(Object var1) {
                    this.onSuccess((Void)var1);
                }

                public final void onSuccess(Void it) {
                    EditText var10000 = (EditText)ChatLogActivity.this._$_findCachedViewById(id.editTextTextPersonName);
                    Intrinsics.checkNotNullExpressionValue(var10000, "editTextTextPersonName");
                    var10000.getText().clear();
                    ((RecyclerView)ChatLogActivity.this._$_findCachedViewById(id.recyclerview_chat_log)).scrollToPosition(ChatLogActivity.this.getAdapter().getItemCount() - 1);
                }
            }));
            toref.setValue(chatmessage);
            var12 = FirebaseDatabase.getInstance().getReference("/latest-messages/" + fromId + '/' + toId);
            Intrinsics.checkNotNullExpressionValue(var12, "FirebaseDatabase.getInst…-messages/$fromId/$toId\")");
            DatabaseReference latestMessageFromRef = var12;
            latestMessageFromRef.setValue(chatmessage);
            var12 = FirebaseDatabase.getInstance().getReference("/latest-messages/" + toId + '/' + fromId);
            Intrinsics.checkNotNullExpressionValue(var12, "FirebaseDatabase.getInst…-messages/$toId/$fromId\")");
            DatabaseReference latestMessageToRef = var12;
            latestMessageToRef.setValue(chatmessage);
        }

    }

    public View _$_findCachedViewById(int var1) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }

        View var2 = (View)this._$_findViewCache.get(var1);
        if (var2 == null) {
            var2 = this.findViewById(var1);
            this._$_findViewCache.put(var1, var2);
        }

        return var2;
    }

    public void _$_clearFindViewByIdCache() {
        if (this._$_findViewCache != null) {
            this._$_findViewCache.clear();
        }

    }

    @Metadata(
            mv = {1, 4, 3},
            bv = {1, 0, 3},
            k = 1,
            d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000¨\u0006\t"},
            d2 = {"Lcom/example/deservico/message/ChatLogActivity$Companion;", "", "()V", "FIRST_ACTIVITY_REQUEST_CODE", "", "GALLERY", "IMG_URI", "", "READ_STORAGE_PERMISSION", "app_debug"}
    )
    public static final class Companion {
        private Companion() {
        }

        // $FF: synthetic method
        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
// ChatToItem.java
package com.example.deservico.message;

        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.example.deservico.R.id;
        import com.example.deservico.messenger.MessengerUserStruct;
        import com.squareup.picasso.Picasso;
        import com.squareup.picasso.RequestCreator;
        import com.xwray.groupie.GroupieViewHolder;
        import com.xwray.groupie.Item;
        import de.hdodenhof.circleimageview.CircleImageView;
        import kotlin.Metadata;
        import kotlin.jvm.internal.Intrinsics;
        import kotlin.text.StringsKt;
        import org.jetbrains.annotations.NotNull;

@Metadata(
        mv = {1, 4, 3},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00022\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\u0010H\u0016R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0012"},
        d2 = {"Lcom/example/deservico/message/ChatToItem;", "Lcom/xwray/groupie/Item;", "Lcom/xwray/groupie/GroupieViewHolder;", "text", "", "user", "Lcom/example/deservico/messenger/MessengerUserStruct;", "(Ljava/lang/String;Lcom/example/deservico/messenger/MessengerUserStruct;)V", "getText", "()Ljava/lang/String;", "getUser", "()Lcom/example/deservico/messenger/MessengerUserStruct;", "bind", "", "viewHolder", "position", "", "getLayout", "app_debug"}
)
public final class ChatToItem extends Item {
    @NotNull
    private final String text;
    @NotNull
    private final MessengerUserStruct user;

    public void bind(@NotNull GroupieViewHolder viewHolder, int position) {
        Intrinsics.checkNotNullParameter(viewHolder, "viewHolder");
        View var10000;
        TextView var5;
        ImageView var6;
        if (StringsKt.indexOf$default((CharSequence)this.text, "image/", 0, false, 6, (Object)null) != -1) {
            var10000 = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(var10000, "viewHolder.itemView");
            var5 = (TextView)var10000.findViewById(id.chattotext);
            Intrinsics.checkNotNullExpressionValue(var5, "viewHolder.itemView.chattotext");
            var5.setVisibility(8);
            var10000 = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(var10000, "viewHolder.itemView");
            var6 = (ImageView)var10000.findViewById(id.chattoimg);
            Intrinsics.checkNotNullExpressionValue(var6, "viewHolder.itemView.chattoimg");
            var6.setVisibility(0);
            var10000 = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(var10000, "viewHolder.itemView");
            var6 = (ImageView)var10000.findViewById(id.chattoimg);
            String var3 = this.text;
            int var4 = StringsKt.indexOf$default((CharSequence)this.text, ";base64,", 0, false, 6, (Object)null) + 8;
            if (var3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type java.lang.String");
            }

            String var10001 = var3.substring(var4);
            Intrinsics.checkNotNullExpressionValue(var10001, "(this as java.lang.String).substring(startIndex)");
            var6.setImageBitmap(ChatLogActivityKt.convertBase64ToBitmap(var10001));
        } else {
            var10000 = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(var10000, "viewHolder.itemView");
            var5 = (TextView)var10000.findViewById(id.chattotext);
            Intrinsics.checkNotNullExpressionValue(var5, "viewHolder.itemView.chattotext");
            var5.setVisibility(0);
            var10000 = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(var10000, "viewHolder.itemView");
            var6 = (ImageView)var10000.findViewById(id.chattoimg);
            Intrinsics.checkNotNullExpressionValue(var6, "viewHolder.itemView.chattoimg");
            var6.setVisibility(8);
            var10000 = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(var10000, "viewHolder.itemView");
            var5 = (TextView)var10000.findViewById(id.chattotext);
            Intrinsics.checkNotNullExpressionValue(var5, "viewHolder.itemView.chattotext");
            var5.setText((CharSequence)this.text);
        }

        if (Intrinsics.areEqual(this.user.getProfileImageUrl(), "") ^ true) {
            RequestCreator var8 = Picasso.get().load(this.user.getProfileImageUrl());
            View var7 = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(var7, "viewHolder.itemView");
            var8.into((CircleImageView)var7.findViewById(id.imagechattorow));
        } else {
            var10000 = viewHolder.itemView;
            Intrinsics.checkNotNullExpressionValue(var10000, "viewHolder.itemView");
            ((CircleImageView)var10000.findViewById(id.imagechattorow)).setImageResource(700140);
        }

    }

    public int getLayout() {
        return 1300032;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    @NotNull
    public final MessengerUserStruct getUser() {
        return this.user;
    }

    public ChatToItem(@NotNull String text, @NotNull MessengerUserStruct user) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(user, "user");
        super();
        this.text = text;
        this.user = user;
    }
}
