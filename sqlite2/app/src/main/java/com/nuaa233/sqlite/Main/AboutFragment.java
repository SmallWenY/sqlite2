package com.nuaa233.sqlite.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nuaa233.sqlite.About.CheckUpdateActivity;
import com.nuaa233.sqlite.Login.LinkActivity;
import com.nuaa233.sqlite.Login.RegisterActivity;
import com.nuaa233.sqlite.R;

public class AboutFragment extends Fragment {
    View view;
    private TextView updateView, makeProposalView, shareView, welcomeView, agreementView, phoneView, aboutView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_about, container, false);
        initView();
        onClick();

        return view;
    }

    private void initView() {
        updateView = (TextView) view.findViewById(R.id.id_update);
        makeProposalView = (TextView) view.findViewById(R.id.id_makeProposal);
        shareView = (TextView) view.findViewById(R.id.id_shareTofriends);
        welcomeView = (TextView) view.findViewById(R.id.id_welcome);
        agreementView = (TextView) view.findViewById(R.id.agreement);
        phoneView = (TextView) view.findViewById(R.id.id_offical_phone);
        aboutView = (TextView) view.findViewById(R.id.id_about_firm);
    }

    public void onClick() {
        updateView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //从fragment跳转到activity的方法
                Intent intent = new Intent(getActivity().getApplicationContext(), CheckUpdateActivity.class);
                startActivity(intent);
            }
        });

        makeProposalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        agreementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), LinkActivity.class);
                startActivity(intent);
            }
        });
    }

}
