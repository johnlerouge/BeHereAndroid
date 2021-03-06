package com.example.teamloosers.behereandroid.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.example.teamloosers.behereandroid.R;
import com.example.teamloosers.behereandroid.Fragments.EtudiantsFragment;
import com.example.teamloosers.behereandroid.Fragments.SeancesFragment;
import com.example.teamloosers.behereandroid.Structures.Groupe;
import com.example.teamloosers.behereandroid.Structures.Module;
import com.example.teamloosers.behereandroid.Structures.Seance;
import com.example.teamloosers.behereandroid.Structures.Structurable;
import com.example.teamloosers.behereandroid.Utils.SpotlightSequence;

public class StructureActivity <T extends Structurable> extends AppCompatActivity
        implements View.OnClickListener{

    private Module module;
    private T structure;

    private Toolbar toolbar;
    public SectionsPagerAdapter mSectionsPagerAdapter;
    public ViewPager mViewPager;
    private FloatingActionButton nouveauAppelFloatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_structure);

        module = (Module) getIntent().getExtras().getSerializable("module");
        structure = (T) getIntent().getExtras().getSerializable("structure");
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        nouveauAppelFloatButton = (FloatingActionButton) findViewById(R.id.nouveauAppelFlatButton);
        nouveauAppelFloatButton.setOnClickListener(this);

        SpotlightSequence spotlightSequence = new SpotlightSequence(this);
        spotlightSequence.addSpotlight(nouveauAppelFloatButton, R.string.nouvel_appel_spotlight_title,
                R.string.nouvel_appel_spotlight_subtitle, "nouvelappelspotlight");
        spotlightSequence.startSequence();

    }
    @Override
    public boolean onSupportNavigateUp() {

        finish();
        return true;
    }
    @Override
    protected void onStart() {

        super.onStart();

        String toolbarTitle = String.format("%s - %s", module.getDesignation(),
                structure.getDesignation());
        getSupportActionBar().setTitle(toolbarTitle);
    }

    @Override
    public void onClick(View v) {

        if (v == nouveauAppelFloatButton) {

            Intent appelListIntent = new Intent(StructureActivity.this, AppelListActivity.class);
            appelListIntent.putExtra("module", module);
            appelListIntent.putExtra("structure", structure);

            startActivity(appelListIntent);
        }
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);
        }
        @Override
        public Fragment getItem(int position) {

            if (position == 0)
                return EtudiantsFragment.newInstance(module, structure);
            else if (position == 1) {

                String typeSeance = (structure instanceof Groupe)? Seance.TD: Seance.COURS;
                return SeancesFragment.newInstance(module, structure, typeSeance);
            }
            else
                return null;
        }
        @Override
        public int getCount() {

            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.etudiants_tab_title);
                case 1:
                    return getString(R.string.seance_tab_title);
            }
            return null;
        }


    }
}
