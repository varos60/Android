package com.alvarobasedatosfutbol.myapplication.Alta.Equipo;

/**
 * Created by Álvaro on 12/10/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

    public class Alta_Equipo_Page_Adapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = new String[] { "Registrar", "Modificar"};

        public Alta_Equipo_Page_Adapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {

            Fragment f = null;

            switch(position) {
                case 0:
                    f = Registrar_Equipo.newInstance("","");
                    break;
                case 1:
                    f = Modificar_Equipo.newInstance("","");
                    break;
            }

            return f;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }
}
