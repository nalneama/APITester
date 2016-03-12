package com.nasserapps.saham.Controllers.Activities.WelcomeScreen;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.nasserapps.saham.R;

public class OnBoardingPagerIndicator extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int mNumOfPages;

    private OnboardingPagerIndicatorInteractionListener mListener;


    //TODO: Rename and change types and number of parameters
    public static OnBoardingPagerIndicator newInstance(int pNumOfPages) {
        OnBoardingPagerIndicator fragment = new OnBoardingPagerIndicator();
        Bundle args = new Bundle();
        args.putInt("pages", pNumOfPages);
        fragment.setArguments(args);
        return fragment;
    }

    public OnBoardingPagerIndicator(){

        this.mNumOfPages = 4;
    }


//    public OnBoardingPagerIndicator(int pNumOfPages) {
//        this.mNumOfPages = pNumOfPages;
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(null != outState){

            outState.putInt("pages",this.mNumOfPages);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            this.mNumOfPages = savedInstanceState.getInt("pages");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (savedInstanceState != null) {
            this.mNumOfPages = savedInstanceState.getInt("pages");
        }

        // Inflate the layout for this fragment
        LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.fragment_on_boarding_pager_indicator, container, false);


        ImageView img = (ImageView) linearLayout.findViewById(R.id.nextButton);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onButtonPressed(null);
            }


        });

        TextView doneTxtView = (TextView) linearLayout.findViewById(R.id.doneTxt);
        doneTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnBoardingPagerIndicator.this.mListener.onDone();
            }
        });

        initPageIndicator(linearLayout);

        return linearLayout;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onNext();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnboardingPagerIndicatorInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updatePage(int pCurrent) {

       /* LinearLayout l =(LinearLayout)getView().findViewById(R.id.pageviewindicatorcircles);
        ImageView v = new ImageView(this.getActivity() );
        v.setBackground(getResources().getDrawable(R.drawable.circle));
        l.addView(v);*/



    }

    private void initPageIndicator(LinearLayout pv){



        LinearLayout l =(LinearLayout)pv.findViewById(R.id.pageviewindicatorcircles);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        );

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(5,5,5,5);


        for(int i=0; i < this.mNumOfPages;i++) {

            ImageView v = new ImageView(this.getActivity());

            v.setBackground(getResources().getDrawable(R.drawable.circle_without_fill));
            l.addView(v);
            v.setPadding(3,3,3,3);
            v.setLayoutParams(params );

        }

        int count = l.getChildCount();
        View v = null;

        v= l.getChildAt(0);
        v.setBackground(getResources().getDrawable(R.drawable.circle));

        //initial page color

    }


    public void
    updateIndicatorCircles(View pV,int pSet,int pUnset){

        LinearLayout l =(LinearLayout)pV.findViewById(R.id.pageviewindicatorcircles);
        View v = null;

        v= l.getChildAt(pSet);
        v.setBackground(getResources().getDrawable(R.drawable.circle));

        v= l.getChildAt(pUnset);
        v.setBackground(getResources().getDrawable(R.drawable.circle_without_fill));

        //Update color here ya nasser


    }



    public void
    updateIndicatorControlViews(View pV,int pCurrPage,int pLastPage){

        //if we have moved back from the last page enable some views
        if( ( pCurrPage + 1 == pLastPage  ) && (this.mNumOfPages - 2 == pCurrPage)){

            ViewSwitcher vS =  (ViewSwitcher)pV.findViewById(R.id.doneViewSwitcher);
            vS.showNext();
            //pV.setBackgroundColor(getResources().getColor(R.color.red600));
        }

        //we have reached the End of the Onboarding slides, hide the skip, show the DONE
        if(pCurrPage == this.mNumOfPages -1){

            //pV.setBackgroundColor(getResources().getColor(R.color.purple));
            ViewSwitcher vS =  (ViewSwitcher)pV.findViewById(R.id.doneViewSwitcher);
            vS.showPrevious();
        }
    }


    public interface OnboardingPagerIndicatorInteractionListener {
        public void onNext();

        public void onDone();

    }
}