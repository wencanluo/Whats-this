package edu.pitt.cs.mips.coursemirror.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.core.User;
import com.squareup.picasso.Picasso;


//import static edu.pitt.cs.mips.coursemirror.core.Constants.Extra.USER;

public class CourseActivity extends CourseMirrorActivity {

    protected ImageView avatar;
    protected TextView name;

    private User user;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.course_view);
        
        avatar = (ImageView) this.findViewById(R.id.course_avatar);
        name = (TextView) this.findViewById(R.id.course_name);
        

        if (getIntent() != null && getIntent().getExtras() != null) {
//            user = (User) getIntent().getExtras().getSerializable(USER);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Picasso.with(this).load(user.getAvatarUrl())
                .placeholder(R.drawable.gravatar_icon)
                .into(avatar);

  //      name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

    }


}
