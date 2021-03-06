/**
 * Copyright 2017 Alex Yanchenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.droidparts.sample.activity;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import org.json.JSONArray;

import org.droidparts.activity.Activity;
import org.droidparts.annotation.inject.InjectBundleExtra;
import org.droidparts.annotation.inject.InjectDependency;
import org.droidparts.annotation.inject.InjectView;
import org.droidparts.sample.R;
import org.droidparts.sample.json.EntrySerializer;
import org.droidparts.sample.model.Entry;
import org.droidparts.util.L;
import org.droidparts.util.ui.AbstractDialogFactory;

public class JsonViewActivity extends Activity implements OnClickListener {

	private static final String EXTRA_ENTRIES = "entries";

	public static Intent getIntent(Context ctx, ArrayList<Entry> entries) {
		Intent intent = new Intent(ctx, JsonViewActivity.class);
		intent.putExtra(EXTRA_ENTRIES, entries);
		return intent;
	}

	@InjectView(id = R.id.text)
	private TextView textView;

	@InjectDependency
	private EntrySerializer entrySerializer;
	@InjectDependency
	private AbstractDialogFactory dialogFactory;

	@InjectBundleExtra(key = EXTRA_ENTRIES)
	private ArrayList<Entry> entries;

	@Override
	public void onPreInject() {
		setContentView(R.layout.activity_json_view);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_close:
				dialogFactory.showToast("(:");
				finish();
				break;
		}
	}

	private void init() {
		String msg;
		try {
			JSONArray arr = entrySerializer.serializeAll(entries);
			msg = arr.toString();
		} catch (Exception e) {
			L.e(e);
			msg = "o_O";
		}
		textView.setText(msg);
	}

}
