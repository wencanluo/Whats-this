package edu.pitt.cs.mips.coursemirror.ui;

import android.os.Bundle;
import android.widget.TextView;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.core.Lecture;
import static edu.pitt.cs.mips.coursemirror.util.Constants.Extra.LECTURE_ITEM;

public class LectureActivity extends CourseMirrorActivity {

    private Lecture lectureItem;

    protected TextView title;
    protected TextView content;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.lecture);
        
        
        title = (TextView) this.findViewById(R.id.tv_title);
        content = (TextView)this.findViewById(R.id.tv_content);        

        if (getIntent() != null && getIntent().getExtras() != null) {
            lectureItem = (Lecture) getIntent().getExtras().getSerializable(LECTURE_ITEM);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle(lectureItem.getTitle());

        title.setText(lectureItem.getcid() + " " + "Lecture " + lectureItem.getnumber());
        content.setText(lectureItem.getTitle());

    }

}
