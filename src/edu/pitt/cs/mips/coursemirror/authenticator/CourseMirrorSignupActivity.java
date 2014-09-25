package edu.pitt.cs.mips.coursemirror.authenticator;

import static android.R.layout.simple_dropdown_item_1line;
import static android.accounts.AccountManager.KEY_ACCOUNT_NAME;
import static android.accounts.AccountManager.KEY_ACCOUNT_TYPE;
import static android.accounts.AccountManager.KEY_AUTHTOKEN;
import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

import com.github.kevinsawicki.wishlist.Toaster;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import edu.pitt.cs.mips.coursemirror.CourseMirrorModule;
import edu.pitt.cs.mips.coursemirror.R;
import edu.pitt.cs.mips.coursemirror.R.id;
import edu.pitt.cs.mips.coursemirror.R.layout;
import edu.pitt.cs.mips.coursemirror.R.string;
import edu.pitt.cs.mips.coursemirror.core.CourseMirrorService;
import edu.pitt.cs.mips.coursemirror.core.User;
import edu.pitt.cs.mips.coursemirror.events.UnAuthorizedErrorEvent;
import edu.pitt.cs.mips.coursemirror.ui.TextWatcherAdapter;
import edu.pitt.cs.mips.coursemirror.util.Constants;
import edu.pitt.cs.mips.coursemirror.util.Ln;
import edu.pitt.cs.mips.coursemirror.util.SafeAsyncTask;

public class CourseMirrorSignupActivity extends ActionBarActivity {
	
	 private EditText usernameField;
	  private EditText passwordField;
	  private EditText confirmPasswordField;
	  private EditText emailField;
	  //private EditText nameField;
	  private Button createAccountButton;
	  private SafeAsyncTask<Boolean> authenticationTask;
	  private String username;
	  private String password;
	  private String passwordAgain;
	  private String email;
	  
	  /**
	     * PARAM_PASSWORD
	     */
	    public static final String PARAM_PASSWORD = "password";

	    /**
	     * PARAM_USERNAME
	     */
	    public static final String PARAM_USERNAME = "username";

	    CourseMirrorService courseMirrorService;
	    private String token;
	    private AccountManager accountManager;
	    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        setContentView(R.layout.signup_activity);

        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        return super.onOptionsItemSelected(item);
    }

    public void handleSignupSubmit(final View view) {
    	
    	 usernameField = (EditText) this.findViewById(R.id.et_signup_user_name);
    	 passwordField = (EditText) this.findViewById(R.id.et_signuppassword);
    	 confirmPasswordField = (EditText) this
    	        .findViewById(R.id.et_reenter_password);
    	 emailField = (EditText) this.findViewById(R.id.et_signup_email);
    	   // nameField = (EditText) v.findViewById(R.id.signup_name_input);
    	 createAccountButton = (Button) this.findViewById(R.id.b_signup_submit);
    	    
    	 username = usernameField.getText().toString();
    	 password = passwordField.getText().toString();
    	 passwordAgain = confirmPasswordField.getText().toString();
    	 email = emailField.getText().toString();
    	 
    	 if (username.length() == 0) {
    	      showToast(R.string.com_parse_ui_no_username_toast);
    	    } else if (password.length() == 0) {
    	      showToast(R.string.com_parse_ui_no_password_toast);
    	    }  else if (passwordAgain.length() == 0) {
    	      showToast(R.string.com_parse_ui_reenter_password_toast);
    	    } else if (!password.equals(passwordAgain)) {
    	      showToast(R.string.com_parse_ui_mismatch_confirm_password_toast);
    	      confirmPasswordField.selectAll();
    	      confirmPasswordField.requestFocus();
    	    } else if (email != null && email.length() == 0) {
    	      showToast(R.string.com_parse_ui_no_email_toast);
    	    } else {
    	    
    	    
    	    
    	 /** add new user-------
    	  * 
    	  * 
    	  * ---------------------------------------------------------*/
        ParseUser user = new ParseUser();

        // Set standard fields
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Set additional custom fields only if the user filled it out
      /*  if (name.length() != 0) {
          user.put(USER_OBJECT_NAME_FIELD, name);
        }*/

        //loadingStart();
        final Context current = this; 
        user.signUpInBackground(new SignUpCallback() {

          @Override
          public void done(ParseException e) {
          /*  if (isActivityDestroyed()) {
              return;
            }*/

            if (e == null) {
             // loadingFinish();
             // signupSuccess();
 
            	 showToastLong(R.string.login_reminder);
               	 Handler handler = new Handler(); 
               	 handler.postDelayed(new Runnable() { 
               	         public void run() { 
               	             finish(); 
//               	         Intent intent_signin = new Intent(current, CourseMirrorAuthenticatorActivity.class);
//               	         intent_signin.putExtra(CourseMirrorAuthenticatorActivity.PARAM_USERNAME, username);
//               	         startActivity(intent_signin);
//               	         finish();
               	         } 
               	    }, 2000); 
            	//finish();
            	
            } else {
             // loadingFinish();
              if (e != null) {
              //  debugLog(getString(R.string.com_parse_ui_login_warning_parse_signup_failed) +
                  //  e.toString());
                switch (e.getCode()) {
                case ParseException.INVALID_EMAIL_ADDRESS:
                    showToast(R.string.com_parse_ui_invalid_email_toast);
                    break;
                  case ParseException.USERNAME_TAKEN:
                    showToast(R.string.com_parse_ui_username_taken_toast);
                    break;
                  case ParseException.EMAIL_TAKEN:
                    showToast(R.string.com_parse_ui_email_taken_toast);
                    break;
                  default:
                    showToast(R.string.com_parse_ui_signup_failed_unknown_toast);
                }
              }
            }
          }
        });
    	    }
        /**add new user ends------------------------
         * 
         * 
         * -------------------------------------*/
        
    	
    	
    	
    }
    
    protected void showToast(int id) {
    	CharSequence text = getString(id);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
      }
    protected void showToastLong(int id) {
    	CharSequence text = getString(id);
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
      }
    
}

