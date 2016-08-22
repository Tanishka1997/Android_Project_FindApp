package com.example.tanishka.findapp;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;


    public AppFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppFragment newInstance() {
        AppFragment fragment = new AppFragment();
        return fragment;
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
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_app, container, false);
         recyclerView=(RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        msetAdapter();
        return view;
    }

  public void msetAdapter(){
      Intent startIntent=new Intent(Intent.ACTION_MAIN);
      startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
      PackageManager pm=getActivity().getPackageManager();
      List<ResolveInfo> activities=pm.queryIntentActivities(startIntent,0);
      Collections.sort(activities, new Comparator<ResolveInfo>() {
          @Override
          public int compare(ResolveInfo lhs, ResolveInfo rhs) {
              PackageManager pm=getActivity().getPackageManager();
              return String.CASE_INSENSITIVE_ORDER.compare(lhs.loadLabel(pm).toString(),rhs.loadLabel(pm).toString());
          }
      });
   Name_adapter adapter=new  Name_adapter(activities);
   recyclerView.setAdapter(adapter);
  }
    private class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mtextView;
        ResolveInfo mresolveInfo;
      public Holder(View itemView) {
          super(itemView);
        mtextView=(TextView) itemView;
      }
    public void bind(ResolveInfo resolveInfo){
        mresolveInfo=resolveInfo;
        PackageManager packageManager=getActivity().getPackageManager();
        String app_name=mresolveInfo.loadLabel(packageManager).toString();
        mtextView.setText(app_name);
        itemView.setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            ActivityInfo activityInfo=mresolveInfo.activityInfo;
            Intent intent=new Intent(Intent.ACTION_MAIN).setClassName(activityInfo.applicationInfo.packageName,activityInfo.name);
            startActivity(intent);
        }
    }
  public class Name_adapter extends RecyclerView.Adapter<Holder>{
     List<ResolveInfo> list;
      public Name_adapter(List<ResolveInfo> ls) {
      list=ls;
      }

      @Override
      public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
          LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
          View v=layoutInflater.inflate(android.R.layout.simple_list_item_1,parent,false);
          return new Holder(v);
      }

      @Override
      public void onBindViewHolder(Holder holder, int position) {
        ResolveInfo item=list.get(position);
        holder.bind(item);
      }

      @Override
      public int getItemCount() {
          return list.size();
      }
  }
}
