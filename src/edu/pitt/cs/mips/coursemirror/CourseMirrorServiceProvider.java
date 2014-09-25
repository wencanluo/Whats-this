
package edu.pitt.cs.mips.coursemirror;

import android.accounts.AccountsException;
import android.app.Activity;

import edu.pitt.cs.mips.coursemirror.authenticator.ApiKeyProvider;
import edu.pitt.cs.mips.coursemirror.core.CourseMirrorService;
import edu.pitt.cs.mips.coursemirror.core.UserAgentProvider;

import java.io.IOException;

import retrofit.RestAdapter;

/**
 * Provider for a {@link edu.pitt.cs.mips.coursemirror.core.CourseMirrorService} instance
 */
public class CourseMirrorServiceProvider {

    private RestAdapter restAdapter;
    private ApiKeyProvider keyProvider;

    public CourseMirrorServiceProvider(RestAdapter restAdapter, ApiKeyProvider keyProvider) {
        this.restAdapter = restAdapter;
        this.keyProvider = keyProvider;
    }

    /**
     * Get service for configured key provider
     * <p/>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return CourseMIRROR service
     * @throws IOException
     * @throws AccountsException
     */
    public CourseMirrorService getService(final Activity activity)
            throws IOException, AccountsException {
        // The call to keyProvider.getAuthKey(...) is what initiates the login screen. Call that now.
        keyProvider.getAuthKey(activity);

        // TODO: See how that affects the CourseMIRROR service.
        return new CourseMirrorService(restAdapter);
    }
}
