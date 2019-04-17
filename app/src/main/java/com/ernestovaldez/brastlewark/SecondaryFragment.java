package com.ernestovaldez.brastlewark;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;

public class SecondaryFragment extends Fragment {



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_secondary, container, false);

        ImageView imageView = view.findViewById(R.id.imageView1);

        new ImageManager.DownloadImageTask(imageView).execute("https://media.licdn.com/dms/image/C5603AQEXulnjxwB8gQ/profile-displayphoto-shrink_200_200/0?e=1560384000&v=beta&t=a4WnMREp3rw7i69nqTKwWBJ6yStVGhcpdIQYV4Tpdx8");

        TableRow tr1 = view.findViewById(R.id.tr1);
        tr1.setOnClickListener(tableLayoutOnClickListener);
        TableRow tr2 = view.findViewById(R.id.tr2);
        tr2.setOnClickListener(tableLayoutOnClickListener);
        TableRow tr3 = view.findViewById(R.id.tr3);
        tr3.setOnClickListener(tableLayoutOnClickListener);

        return view;

    }

    private View.OnClickListener tableLayoutOnClickListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent browserIntent;

                switch (v.getId()){
                    case R.id.tr1:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.contact2)));
                        startActivity(browserIntent);
//                        Log.d("tr1:","pressed");
                        break;
                    case R.id.tr2:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.contact4)));
                        startActivity(browserIntent);
//                        Log.d("tr2:","pressed");
                        break;
                    case R.id.tr3:
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.contact6)));
                        startActivity(browserIntent);
//                        Log.d("tr3:","pressed");
                        break;
                }
        }
    };

}
