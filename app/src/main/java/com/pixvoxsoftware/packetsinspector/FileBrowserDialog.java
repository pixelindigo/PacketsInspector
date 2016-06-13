package com.pixvoxsoftware.packetsinspector;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by PixelIndigo.
 */
public class FileBrowserDialog extends AppCompatDialogFragment {

    private FileBrowserAdapter mFileBrowserAdapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Browse file");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View titleView = inflater.inflate(R.layout.view_filebrowser_header, null);
        final TextView directoryLabel = (TextView) titleView.findViewById(R.id.filebrowser_directory);
        final ImageView arrow = (ImageView) titleView.findViewById(R.id.filebrowser_arrow);

        builder.setCustomTitle(titleView);
        mFileBrowserAdapter = new FileBrowserAdapter(Environment.getExternalStorageDirectory().getPath());

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = mFileBrowserAdapter.goUp();
                mFileBrowserAdapter.notifyDataSetChanged();
                if (!file.getName().equals("0")) {
                    directoryLabel.setText(file.getName());
                } else {
                    directoryLabel.setText(R.string.internal_memory);
                }
            }
        });

        builder.setSingleChoiceItems(mFileBrowserAdapter, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = mFileBrowserAdapter.getItem(which);
                if (file.isDirectory()) {
                    mFileBrowserAdapter.goFolder(file.getPath());
                    mFileBrowserAdapter.notifyDataSetChanged();
                    directoryLabel.setText(file.getName());
                } else {
                    mFileBrowserAdapter.setSelected(which);
                    mFileBrowserAdapter.notifyDataSetChanged();
                }
                if (mFileBrowserAdapter.getSelected() == -1) {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

        builder.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (getActivity().getClass().equals(WelcomeScreenActivity.class)) {
                    WelcomeScreenActivity welcomeScreenActivity =
                            (WelcomeScreenActivity) getActivity();
                    welcomeScreenActivity.onFileSelected(
                            mFileBrowserAdapter.getItem(mFileBrowserAdapter.getSelected())
                                    .getPath());
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        });
        return dialog;
    }

}
