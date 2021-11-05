package com.horses.library.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author briansalvattore on 1/2/2018.
 *    _   _                                       ____                          _
 *   | | | |   ___    _ __   ___    ___   ___    |  _ \    ___  __   __   ___  | |   ___    _ __     ___   _ __
 *   | |_| |  / _ \  | '__| / __|  / _ \ / __|   | | | |  / _ \ \ \ / /  / _ \ | |  / _ \  | '_ \   / _ \ | '__|
 *   |  _  | | (_) | | |    \__ \ |  __/ \__ \   | |_| | |  __/  \ V /  |  __/ | | | (_) | | |_) | |  __/ | |
 *   |_| |_|  \___/  |_|    |___/  \___| |___/   |____/   \___|   \_/    \___| |_|  \___/  | .__/   \___| |_|
 *                                                                                         |_|
 */
@SuppressLint("AppCompatCustomView")
public class MasterSpinner extends Spinner {

    private List<Map<String, String>> array;
    private List<String> descriptions;

    private static final String HINT = "Seleccione una opción";
    private static final String KEY = "description";


    public MasterSpinner(Context context) {
        super(context);
        init();
    }

    public MasterSpinner(Context context, int mode) {
        super(context, mode);
        init();
    }

    public MasterSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MasterSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MasterSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        init();
    }

    private void init() {
        setEnabled(false);
        setAdapter(new ArrayList<Map<String, String>>(), HINT);
    }

    @SuppressWarnings("Convert2streamapi")
    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener listener) {
        if (array == null) throw new NullPointerException("spinner adapter is null");

        setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != descriptions.size()) {

                    String description = descriptions.get(i);

                    for (Map<String, String> item : array) {

                        if (description.equals(item.get(KEY))) {
                            listener.onItemSelected(item);
                            return;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public Map<String, String> getSelectedItem() {
        return getSelectedItemPosition() == array.size() ?
                new HashMap<String, String>() :
                array.get(getSelectedItemPosition());
    }

    public String getSelectedDescription() {
        return getSelectedItem().get(KEY) == null ? "" : getSelectedItem().get(KEY);
    }

    public void setAdapter(List<Map<String, String>> array) {
        setAdapter(array, HINT);
    }

    @SuppressWarnings({"Convert2streamapi", "SameParameterValue"})
    public void setAdapter(List<Map<String, String>> array, String hint) {
        this.array = array;

        List<String> descriptions = new ArrayList<>();

        for (Map<String, String> item : array) {
            descriptions.add(item.get(KEY));
        }

        descriptions.add(hint);
        this.descriptions = descriptions;

        DefaultArrayAdapter adapter = new DefaultArrayAdapter<>(getContext(), descriptions);

        setAdapter(adapter);
        setEnabled(array.size() != 0);
        setSelection(descriptions.size() == 1 ? 0 : adapter.getCount());
    }

    public void setArray(List<Map<String, String>> array) {
        this.array = array;
    }

    public List<Map<String, String>> getArray() {
        return array;
    }

    @SuppressWarnings("Convert2streamapi")
    public void setSelection(String description) {
        if (description != null) {
            for (int i = 0; i < array.size(); i++) {
                Map<String, String> item = array.get(i);
                if (description.equals(item.get(KEY))) {
                    setSelection(i);
                    return;
                }
            }
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(Map<String, String> item);
    }

    public static class DefaultArrayAdapter<T> extends ArrayAdapter<T> {

        DefaultArrayAdapter(Context context, List<T> objects) {
            super(context, android.R.layout.simple_spinner_dropdown_item, objects);
        }

        @Override
        public int getCount() {
            int count = super.getCount();
            return count == 1 ? count : count - 1;
        }
    }

    //region go to top
    private boolean toggleFlag = true;

    @Override
    public int getSelectedItemPosition() {
        if (!toggleFlag) return 0;
        return super.getSelectedItemPosition();
    }

    @Override
    public boolean performClick() {
        toggleFlag = false;
        boolean result = super.performClick();
        toggleFlag = true;
        return result;
    }
    //endregion
}
