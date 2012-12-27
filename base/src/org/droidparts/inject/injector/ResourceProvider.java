/**
 * Copyright 2012 Alex Yanchenko
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
package org.droidparts.inject.injector;

import static org.droidparts.reflect.util.TypeHelper.isArray;
import static org.droidparts.reflect.util.TypeHelper.isBoolean;
import static org.droidparts.reflect.util.TypeHelper.isDrawable;
import static org.droidparts.reflect.util.TypeHelper.isInteger;
import static org.droidparts.reflect.util.TypeHelper.isString;

import java.lang.reflect.Field;

import org.droidparts.reflect.ann.inject.InjectResourceAnn;

import android.content.Context;
import android.content.res.Resources;

public class ResourceProvider {

	static Object getVal(Context ctx, InjectResourceAnn ann, Field field)
			throws Exception {
		int resId = ann.value;
		Resources res = ctx.getResources();
		Class<?> cls = field.getType();
		Object val = null;
		if (isBoolean(cls)) {
			val = res.getBoolean(resId);
		} else if (isInteger(cls)) {
			val = res.getInteger(resId);
		} else if (isString(cls)) {
			val = res.getString(resId);
		} else if (isDrawable(cls)) {
			val = res.getDrawable(resId);
		} else if (isArray(cls)) {
			Class<?> type = cls.getComponentType();
			if (isInteger(type)) {
				val = res.getIntArray(resId);
			} else if (isString(type)) {
				val = res.getStringArray(resId);
			}
		}
		if (val == null) {
			throw new Exception("Unsupported resource type '" + cls.getName()
					+ "'.");
		} else {
			return val;
		}
	}
}