package freddy.mymovieapp.controller;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import freddy.mymovieapp.ApplicationConstants;
import freddy.mymovieapp.Interfaces.SortMethodInterface;
import freddy.mymovieapp.R;

/**
 * Created by Freddy on 21/06/2018.
 * email : freddy.lazo@pucp.pe
 */

public class SortMovieOptionsDialogFragment extends DialogFragment {

    public static final String CURRENT_SORT_ORDER = "sort_method";
    public static final String TAG = "select_sort_method";

    @BindView(R.id.popular_method)
    RadioButton popular_method;
    @BindView(R.id.top_rated_method)
    RadioButton top_rated_method;
    @BindView(R.id.upcoming_method)
    RadioButton upcoming_method;
    private SortMethodInterface mListener;


    public static SortMovieOptionsDialogFragment newInstance(int sort_method) {
        SortMovieOptionsDialogFragment dialogFragment = new SortMovieOptionsDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CURRENT_SORT_ORDER, sort_method);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (SortMethodInterface) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        super.onCreateView(inflater, parent, state);
        View v = getActivity().getLayoutInflater().inflate(R.layout.sort_methods_layout, parent, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dialog.getWindow() != null)
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            getDataFromActivity(getArguments());
        }
    }

    /**
     * Get the data from the activity
     * @param arguments
     */
    private void getDataFromActivity(Bundle arguments) {
        initRadioButtonState(arguments.getInt(CURRENT_SORT_ORDER));
    }

    /**
     * Init radio button state
     * @param anInt
     */
    private void initRadioButtonState(int anInt) {
        switch (anInt) {
            case ApplicationConstants.SortMovieMethods.POPULAR:
                popular_method.setChecked(true);
                break;
            case ApplicationConstants.SortMovieMethods.TOP_RATED:
                top_rated_method.setChecked(true);
                break;
            case ApplicationConstants.SortMovieMethods.UPCOMING:
                upcoming_method.setChecked(true);
                break;
            default:
                popular_method.setChecked(true);
                break;
        }
    }

    @OnClick(R.id.popular_method)
    public void popularMethodSelected() {
        mListener.sortMethodSelected(ApplicationConstants.SortMovieMethods.POPULAR);
        dismiss();
    }

    @OnClick(R.id.top_rated_method)
    public void topRatedMethodSelected() {
        mListener.sortMethodSelected(ApplicationConstants.SortMovieMethods.TOP_RATED);
        dismiss();
    }

    @OnClick(R.id.upcoming_method)
    public void upcomingMethodSelected() {
        mListener.sortMethodSelected(ApplicationConstants.SortMovieMethods.UPCOMING);
        dismiss();
    }
}
