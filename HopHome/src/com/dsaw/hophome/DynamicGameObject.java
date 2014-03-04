/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.dsaw.hophome;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class DynamicGameObject extends GameObject {
	public final Vector3 velocity;
	public final Vector3 accel;


	public DynamicGameObject (float x, float y, float z, float width, float height, float depth) {
		super(x, y, z, width, height, depth);
		velocity = new Vector3();
		accel = new Vector3();
	}
}
